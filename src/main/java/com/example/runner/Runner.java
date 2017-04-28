package com.example.runner;

import java.util.function.Consumer;

import com.example.guice.Binder;
import com.example.verticle.TestVerticle;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;

public class Runner {
	public static void main(String[] args) {

		JsonObject verticleConfig = new JsonObject();
		verticleConfig.put("guice_binder", Binder.class.getName());

		DeploymentOptions deployOptions = new DeploymentOptions();
		deployOptions.setConfig(verticleConfig);

		VertxOptions vertxOptions = new VertxOptions();

		runExample("java-guice:" + TestVerticle.class.getName(), vertxOptions, deployOptions);
	}

	public static void runExample(String verticleID, VertxOptions options, DeploymentOptions deploymentOptions) {
		if (options == null) {
			// Default parameter
			options = new VertxOptions();
		}
		// Smart cwd detection

		Consumer<Vertx> runner = vertx -> {
			try {
				if (deploymentOptions != null) {
					vertx.deployVerticle(verticleID, deploymentOptions);
				} else {
					vertx.deployVerticle(verticleID);
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		};

		if (options.isClustered()) {
			Vertx.clusteredVertx(options, res -> {
				if (res.succeeded()) {
					Vertx vertx = res.result();
					runner.accept(vertx);
				} else {
					res.cause().printStackTrace();
				}
			});
		} else {
			Vertx vertx = Vertx.vertx(options);
			runner.accept(vertx);
		}
	}
}

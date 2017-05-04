package com.example;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.guice.Binder;
import com.example.verticle.TestVerticle;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class GraphQLTests {

	@Rule
	public RunTestOnContext rule = new RunTestOnContext();

	@Test
	public void testUnion(TestContext context) {
		Async async = context.async(2);

		JsonObject verticleConfig = new JsonObject();
		verticleConfig.put("guice_binder", Binder.class.getName());

		DeploymentOptions deployOptions = new DeploymentOptions();
		deployOptions.setConfig(verticleConfig);

		Vertx vertx = rule.vertx();

		vertx.deployVerticle("java-guice:" + TestVerticle.class.getName(), deployOptions, ar -> {
			if (ar.succeeded()) {
				async.countDown();

				HttpClient client = vertx.createHttpClient();
				HttpClientRequest req = client.get(8082, "127.0.0.1", "/graphql");
				req.exceptionHandler(err -> {
					context.fail(err.getMessage());
				});
				req.handler(resp -> {
					context.assertEquals(200, resp.statusCode());

					resp.bodyHandler(body -> {
						System.out.println(body.toString());
						
						context.assertNotEquals("[]", body.toString());
						client.close();

						async.countDown();
					});

				});
				req.end();

			} else {
				context.fail(ar.cause());
			}
		});

		// async.awaitSuccess();

	}
}

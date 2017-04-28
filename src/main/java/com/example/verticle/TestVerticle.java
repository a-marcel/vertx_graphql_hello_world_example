package com.example.verticle;

import java.util.HashSet;
import java.util.Map;

import com.google.inject.Inject;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLType;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class TestVerticle extends AbstractVerticle {

	@Inject
	Map<String, GraphQLType> types;

	@Override
	public void start(Future<Void> future) throws Exception {
		super.start();

		// get HTTP host and port from configuration, or use default value
		String host = config().getString("http.address", "127.0.0.1");
		int port = config().getInteger("http.port", 8082);

		final Router router = Router.router(vertx);

		router.get("/graphql").handler(this::graphQL);

		// create HTTP server and publish REST service

		HttpServer server = vertx.createHttpServer();
		server.requestHandler(router::accept).listen(port, r -> {
			if (r.failed()) {
				future.fail(r.cause());
			} else {
				future.complete();
			}
		});
	}

	private void graphQL(RoutingContext context) {
		String query = "{hello}";

		GraphQLSchema schema = GraphQLSchema.newSchema().query((GraphQLObjectType) types.get("helloWorldQuery"))
				.build(new HashSet<>(types.values()));

		GraphQL graphQL = GraphQL.newGraphQL(schema).build();

		ExecutionResult executionResult = graphQL.execute(query);

		context.response().putHeader("content-type", "application/json").end(executionResult.getData().toString());

	}
}

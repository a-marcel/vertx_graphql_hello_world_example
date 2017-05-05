package com.example;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.distelli.graphql.MethodDataFetcher;
import com.example.apigen.Contact;
import com.example.apigen.Contact.Impl;
import com.example.apigen.ContactTypeProvider;
import com.example.apigen.DefaultPageInterfaceProvider;
import com.example.apigen.Other1;
import com.example.apigen.PageTypeProvider;
import com.example.apigen.helloWorldQuery;
import com.example.guice.HelloWorldImpl;
import com.example.guice.MyGuiceModule;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provider;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.Scalars;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInterfaceType;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLType;
import graphql.schema.GraphQLTypeReference;
import graphql.schema.GraphQLUnionType;
import graphql.schema.TypeResolver;

public class RawGraphQLWithApiGenObjectsTest {

	protected Injector injector = Guice.createInjector(new AbstractModule() {
		@Override
		protected void configure() {
			install(new MyGuiceModule());
			
			bind(helloWorldQuery.class).toInstance(new HelloWorldImpl());
		}

	});

	GraphQL graphQL;

	@Inject
	Map<String, GraphQLType> types;

	@Inject
	PageTypeProvider pageTypeProvider;

	// @Inject
	// Provider<DefaultPageInterfaceProvider> defaultPageInterfaceProvider;

	@Before
	public void initGraphQLSchema() {

		injector.injectMembers(this);
		
		GraphQLSchema schema = GraphQLSchema.newSchema().query((GraphQLObjectType) types.get("helloWorldQuery"))
				.build(new HashSet<>(types.values()));

		graphQL = GraphQL.newGraphQL(schema).build();

		assertNotNull(graphQL);
	}
	
	@Test
	public void testUnion() {

		// String query = "{page(url:\"xxx\") { ... on Contact {id}}}";
		String query = "{page { ... on Contact {id} ... on Other1 {id}}}";

		ExecutionResult executionResult = graphQL.execute(query);

		assertNotNull(executionResult);

		if (null != executionResult.getErrors()) {
			System.out.println(executionResult.getErrors());
		}

		assertNotNull(executionResult.getData());
		System.out.println(executionResult.getData().toString());

	}
}

package com.example;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;


import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.Scalars;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInterfaceType;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLType;
import graphql.schema.GraphQLTypeReference;
import graphql.schema.GraphQLUnionType;
import graphql.schema.TypeResolver;

public class RawGraphQLTest {

	GraphQL graphQL;

	public static class Contact {
		String id;
		String name;

		public Contact(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Contact withName(String name) {
			this.setName(name);
			return this;
		}
	}

	@Before
	public void initGraphQLSchema() {
		GraphQLInterfaceType defaultPageInterface = GraphQLInterfaceType.newInterface()

				.name("DefaultPage")

				.field(GraphQLFieldDefinition.newFieldDefinition().name("name").type(Scalars.GraphQLString))

				.typeResolver(new TypeResolver() {

					@Override
					public GraphQLObjectType getType(Object object) {
						if (Contact.class.isInstance(object)) {
							// return contactProvider.get();
						}

						return null;
					}
				}).build();

		GraphQLObjectType contactType = GraphQLObjectType.newObject()

				.name("Contact")

				.withInterface(GraphQLInterfaceType.reference("DefaultPage"))

				.field(GraphQLFieldDefinition.newFieldDefinition().type(Scalars.GraphQLString).name("id"))

				.field(GraphQLFieldDefinition.newFieldDefinition().type(Scalars.GraphQLString).name("name"))

				.build();

		GraphQLUnionType pageUnionType = GraphQLUnionType.newUnionType().name("Page")

				.possibleType(GraphQLObjectType.reference("Contact"))

				.typeResolver(new TypeResolver() {

					@Override
					public GraphQLObjectType getType(Object object) {

						if (Contact.class.isInstance(object)) {
							return contactType;
						}


						return null;
					}

				})

				.build();

		GraphQLObjectType queryType = GraphQLObjectType.newObject()

				.name("helloWorldQuery")

				.field(GraphQLFieldDefinition.newFieldDefinition().type(new GraphQLList(pageUnionType))

						.name("page")

						// .argument(Arrays
						// .asList(GraphQLArgument.newArgument().name("url").type(Scalars.GraphQLString).build()))

						.dataFetcher(this.pageDataFetcher()))

				.build();

		Map<String, GraphQLType> types = new HashMap<String, GraphQLType>();
		types.put("helloWorldQuery", GraphQLObjectType.reference("helloWorldQuery"));
		types.put("Contact", contactType);
		types.put("Page", pageUnionType);

		GraphQLSchema schema = GraphQLSchema.newSchema().query((GraphQLObjectType) types.get("helloWorldQuery"))
				.build(new HashSet<>(types.values()));

		graphQL = GraphQL.newGraphQL(schema).build();

		assertNotNull(graphQL);
	}

	DataFetcher pageDataFetcher() {

		DataFetcher pageFetch = new DataFetcher() {
			@Override
			public Object get(DataFetchingEnvironment env) {
				ArrayList<Object> data = new ArrayList<Object>();

				data.add(new Contact("123").withName("test"));

				return data;
			}
		};
		return pageFetch;
	}

	@Test
	public void testUnion() {

		// String query = "{page(url:\"xxx\") { ... on Contact {id}}}";
		String query = "{page { ... on Contact {id}}}";

		ExecutionResult executionResult = graphQL.execute(query);

		assertNotNull(executionResult);

		if (null != executionResult.getErrors()) {
			System.out.println(executionResult.getErrors());
		}

		assertNotNull(executionResult.getData());
		System.out.println(executionResult.getData().toString());

	}
}

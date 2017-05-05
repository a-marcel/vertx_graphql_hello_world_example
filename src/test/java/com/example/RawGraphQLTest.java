package com.example;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.Test;

import com.distelli.graphql.MethodDataFetcher;
import com.example.apigen.Contact;
import com.example.apigen.Other1;
import com.example.apigen.helloWorldQuery;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.Scalars;
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

public class RawGraphQLTest {

	@Test
	public void testUnion() {

		GraphQLInterfaceType defaultPageInterface = GraphQLInterfaceType.newInterface()

				.name("DefaultPage")

				.field(GraphQLFieldDefinition.newFieldDefinition().name("name").type(Scalars.GraphQLString))

				.typeResolver(new TypeResolver() {

					@Override
					public GraphQLObjectType getType(Object object) {
						if (Contact.class.isInstance(object)) {
							// return contactProvider.get();
						}

						if (Other1.class.isInstance(object)) {
							// return other1Provider.get();
						}
						return null;
					}
				}).build();

		GraphQLObjectType contactType = GraphQLObjectType.newObject()

				.name("Contact")

				.withInterface(defaultPageInterface)

				.field(GraphQLFieldDefinition.newFieldDefinition().type(Scalars.GraphQLString).name("id"))

				.build();

		GraphQLUnionType pageUnionType = GraphQLUnionType.newUnionType().name("Page")

				.possibleType(contactType)

				.typeResolver(new TypeResolver() {

					@Override
					public GraphQLObjectType getType(Object object) {

						if (Contact.class.isInstance(object)) {
							return contactType;
						}

						if (Other1.class.isInstance(object)) {
							// return other1Provider.get();
						}

						return null;
					}

				})

				.build();

		GraphQLObjectType queryType = GraphQLObjectType.newObject()

				.name("helloWorldQuery")

				.field(GraphQLFieldDefinition.newFieldDefinition().type(new GraphQLList(new GraphQLTypeReference("Page")))

						.name("page")

						.argument(Arrays.asList(GraphQLArgument.newArgument().name("url").type(Scalars.GraphQLString).build())))
				.build();

		Map<String, GraphQLType> types = new HashMap<String, GraphQLType>();
		types.put("helloWorldQuery", queryType);
		types.put("Contact", contactType);
		types.put("Page", pageUnionType);

		GraphQLSchema schema = GraphQLSchema.newSchema().query((GraphQLObjectType) types.get("helloWorldQuery")).build(new HashSet<>(types.values()));

		GraphQL graphQL = GraphQL.newGraphQL(schema).build();

		assertNotNull(graphQL);

		String query = "{page(url:\"xxx\") { ... on Contact {id}}}";

		ExecutionResult executionResult = graphQL.execute(query);

		assertNotNull(executionResult);

		assertNotNull(executionResult.getData());
		System.out.println(executionResult.getData().toString());

	}
}

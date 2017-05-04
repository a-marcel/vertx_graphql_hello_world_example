package com.example.apigen;

import javax.inject.Inject;
import javax.inject.Provider;

import graphql.Scalars;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInterfaceType;
import graphql.schema.GraphQLObjectType;
import graphql.schema.TypeResolver;

public class DefaultPageInterfaceProvider implements Provider<GraphQLInterfaceType> {

	@Inject
	protected ContactTypeProvider contactProvider;

	@Inject
	protected Other1TypeProvider other1Provider;

	@Override
	public GraphQLInterfaceType get() {
		return GraphQLInterfaceType.newInterface()

				.name("DefaultPage")

				.field(GraphQLFieldDefinition.newFieldDefinition().name("name").type(Scalars.GraphQLString))

				.typeResolver(new TypeResolver() {

					@Override
					public GraphQLObjectType getType(Object object) {
						if (Contact.class.isInstance(object)) {
							return contactProvider.get();
						}

						if (Other1.class.isInstance(object)) {
							return other1Provider.get();
						}
						return null;
					}
				}).build();
	}

}

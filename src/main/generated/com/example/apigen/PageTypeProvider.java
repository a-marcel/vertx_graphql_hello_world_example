package com.example.apigen;

import javax.inject.Inject;
import javax.inject.Provider;

import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLUnionType;
import graphql.schema.TypeResolver;

public class PageTypeProvider implements Provider<GraphQLUnionType> {

	@Inject
	protected ContactTypeProvider contactProvider;

	@Inject
	protected Other1TypeProvider other1Provider;

	@Override
	public GraphQLUnionType get() {

		return GraphQLUnionType.newUnionType().name("Page")

				.possibleType(contactProvider.get()).possibleType(other1Provider.get())

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

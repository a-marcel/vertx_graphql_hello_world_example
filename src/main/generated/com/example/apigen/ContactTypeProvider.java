package com.example.apigen;

import com.distelli.graphql.MethodDataFetcher;
import com.distelli.graphql.ResolverDataFetcher;
import graphql.Scalars;
import graphql.schema.*;
import java.util.Arrays;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Named;
import java.util.Optional;

@Named
public class ContactTypeProvider implements Provider<GraphQLObjectType> {
    @Inject
    private Optional<Contact> _impl;
    @Inject
    protected ContactTypeProvider() {}
    
    @Inject
	protected DefaultPageInterfaceProvider defaultPageInterfaceProvider;
    
    @Override
    public GraphQLObjectType get() {
        return GraphQLObjectType.newObject()
            .name("Contact")
            .withInterface(defaultPageInterfaceProvider.get())
            .field(GraphQLFieldDefinition.newFieldDefinition()
                .type(Scalars.GraphQLString)
                .name("id")
                .dataFetcher(new MethodDataFetcher(
                    "id",
                    null,
                    _impl.orElse(null)))
                .build())
            .build();
    }
}

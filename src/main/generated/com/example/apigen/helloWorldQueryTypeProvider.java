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
public class helloWorldQueryTypeProvider implements Provider<GraphQLObjectType> {
    @Inject
    private Optional<helloWorldQuery> _impl;
    @Inject
    protected helloWorldQueryTypeProvider() {}
    @Override
    public GraphQLObjectType get() {
        return GraphQLObjectType.newObject()
            .name("helloWorldQuery")
            .field(GraphQLFieldDefinition.newFieldDefinition()
                .type(Scalars.GraphQLString)
                .name("id")
                .build())
            .field(GraphQLFieldDefinition.newFieldDefinition()
                .type(Scalars.GraphQLString)
                .name("hello")
                .build())
            .build();
    }
}

package com.example.guice;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.OptionalBinder;
import graphql.schema.GraphQLType;

public class MyGuiceModule extends AbstractModule {
    protected void configure() {
        MapBinder<String, GraphQLType> types =
            MapBinder.newMapBinder(binder(), String.class, GraphQLType.class);
                types.addBinding("helloWorldQuery")
                     .toProvider(com.example.apigen.helloWorldQueryTypeProvider.class);
                OptionalBinder.newOptionalBinder(binder(), com.example.apigen.helloWorldQuery.class);

    }
}

package com.example.guice;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.OptionalBinder;
import graphql.schema.GraphQLType;

public class MyGuiceModule extends AbstractModule {
    protected void configure() {
        MapBinder<String, GraphQLType> types =
            MapBinder.newMapBinder(binder(), String.class, GraphQLType.class);
                types.addBinding("Contact")
                     .toProvider(com.example.apigen.ContactTypeProvider.class);
                OptionalBinder.newOptionalBinder(binder(), com.example.apigen.Contact.class);
                OptionalBinder.newOptionalBinder(binder(), com.example.apigen.Contact.Resolver.class);
                types.addBinding("Other1")
                     .toProvider(com.example.apigen.Other1TypeProvider.class);
                OptionalBinder.newOptionalBinder(binder(), com.example.apigen.Other1.class);
                OptionalBinder.newOptionalBinder(binder(), com.example.apigen.Other1.Resolver.class);
                types.addBinding("helloWorldQuery")
                     .toProvider(com.example.apigen.helloWorldQueryTypeProvider.class);
                OptionalBinder.newOptionalBinder(binder(), com.example.apigen.helloWorldQuery.class);

                
            	/*
        		 * Manually added
        		 */
        		types.addBinding("Page").toProvider(com.example.apigen.PageTypeProvider.class);
        		OptionalBinder.newOptionalBinder(binder(), com.example.apigen.Page.class);
    }
}

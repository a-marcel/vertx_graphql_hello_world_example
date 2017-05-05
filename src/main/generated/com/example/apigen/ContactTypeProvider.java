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
    
    static GraphQLObjectType type;
    
    @Override
    public GraphQLObjectType get() {
    	
    	if ( null == ContactTypeProvider.type) {
	    	System.out.println("create new instance of Contact");
	    	ContactTypeProvider.type = GraphQLObjectType.newObject()
	            .name("Contact")
	            
	            .withInterface(GraphQLInterfaceType.reference("DefaultPage"))
	            
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
    	
    	return type;
    }
}

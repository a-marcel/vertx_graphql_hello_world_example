package com.example.guice;

import java.util.ArrayList;
import java.util.List;

import com.example.apigen.helloWorldQuery;
import com.google.inject.AbstractModule;

public class Binder extends AbstractModule {

	@Override
	protected void configure() {
		install(new MyGuiceModule());

		bind(helloWorldQuery.Resolver.class).toInstance(new helloWorldQueryResolver());

	}

	public class helloWorldQueryResolver implements helloWorldQuery.Resolver {

		@Override
		public List<helloWorldQuery> resolve(List<helloWorldQuery> list) {
			return new ArrayList<helloWorldQuery>() {
				{
					add(new helloWorldQuery.Builder().withHello("World").build());
				}
			};
		}
	}
}

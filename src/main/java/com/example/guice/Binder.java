package com.example.guice;

import com.example.apigen.helloWorldQuery;
import com.google.inject.AbstractModule;

public class Binder extends AbstractModule {

	@Override
	protected void configure() {
		install(new MyGuiceModule());

		bind(helloWorldQuery.class).toInstance(new helloWorldQueryImpl());

	}

	public class helloWorldQueryImpl implements helloWorldQuery {
		public String getHello() {
			return "world";
		}
	}
}

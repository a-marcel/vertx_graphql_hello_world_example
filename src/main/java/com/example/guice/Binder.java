package com.example.guice;

import java.util.ArrayList;
import java.util.List;

import com.example.apigen.helloWorldQuery;
import com.google.inject.AbstractModule;

public class Binder extends AbstractModule {

	@Override
	protected void configure() {
		install(new MyGuiceModule());

//		bind(helloWorldQuery.Resolver.class).toInstance(new HelloWorldResolver());
		bind(helloWorldQuery.class).toInstance(new HelloWorldImpl());
	}
}

//package com.example.guice;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.example.apigen.helloWorldQuery;
//
//public class HelloWorldResolver implements helloWorldQuery.Resolver {
//
//	@Override
//	public List<helloWorldQuery> resolve(List<helloWorldQuery> list) {
//		return new ArrayList<helloWorldQuery>() {
//			{
//				add(new helloWorldQuery.Builder().withHello("World").withId("test").build());
//			}
//		};
//	}
//}
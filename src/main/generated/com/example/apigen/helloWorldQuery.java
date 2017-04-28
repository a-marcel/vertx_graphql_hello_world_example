package com.example.apigen;

import java.util.List;

public interface helloWorldQuery {
    public static class Builder {

        private String _id;

        private String _hello;

        public Builder() {}
        public Builder(helloWorldQuery src) {

            _id = src.getId();

            _hello = src.getHello();

        }


        public Builder withId(String _id) {
            this._id = _id;
            return this;
        }

        public Builder withHello(String _hello) {
            this._hello = _hello;
            return this;
        }

        public helloWorldQuery build() {
            return new Impl(this);
        }
    }
    public static class Impl implements helloWorldQuery {

        private String _id;

        private String _hello;

        protected Impl(Builder builder) {

            this._id = builder._id;

            this._hello = builder._hello;

        }

        @Override
        public String getId() {
            return _id;
        }

        @Override
        public String getHello() {
            return _hello;
        }

        @Override
        public String toString() {
            return "helloWorldQuery{"

                 + "id=" + _id

                 + ", hello=" + _hello


                 + "}";
        }
        // TODO: equals(Object) & hashCode()
    }
    public static class Unresolved implements helloWorldQuery {
        private String _id;
        public Unresolved(String id) {
            this._id = id;
        }
        @Override
        public String getId() {
            return _id;
        }
        @Override
        public String toString() {
            return "helloWorldQuery.Unresolved{"
                 + "id=" + _id
                 + "}";
        }
    }
    public static interface Resolver extends com.distelli.graphql.Resolver<helloWorldQuery> {
        public List<helloWorldQuery> resolve(List<helloWorldQuery> list);
    }
    public default String getId() { return null; }
    public default String getHello() { return null; }

}

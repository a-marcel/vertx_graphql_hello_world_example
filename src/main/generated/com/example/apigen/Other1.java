package com.example.apigen;

import java.util.List;

public interface Other1 {
    public static class Builder {

        private String _id;

        public Builder() {}
        public Builder(Other1 src) {

            _id = src.getId();

        }


        public Builder withId(String _id) {
            this._id = _id;
            return this;
        }

        public Other1 build() {
            return new Impl(this);
        }
    }
    public static class Impl implements Other1 {

        private String _id;

        protected Impl(Builder builder) {

            this._id = builder._id;

        }

        @Override
        public String getId() {
            return _id;
        }

        @Override
        public String toString() {
            return "Other1{"

                 + "id=" + _id


                 + "}";
        }
        // TODO: equals(Object) & hashCode()
    }
    public static class Unresolved implements Other1 {
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
            return "Other1.Unresolved{"
                 + "id=" + _id
                 + "}";
        }
    }
    public static interface Resolver extends com.distelli.graphql.Resolver<Other1> {
        public List<Other1> resolve(List<Other1> list);
    }
    public default String getId() { return null; }

}

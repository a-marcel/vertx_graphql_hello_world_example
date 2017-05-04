package com.example.apigen;

import java.util.List;

public interface Contact {
    public static class Builder {

        private String _id;

        public Builder() {}
        public Builder(Contact src) {

            _id = src.getId();

        }


        public Builder withId(String _id) {
            this._id = _id;
            return this;
        }

        public Contact build() {
            return new Impl(this);
        }
    }
    public static class Impl implements Contact {

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
            return "Contact{"

                 + "id=" + _id


                 + "}";
        }
        // TODO: equals(Object) & hashCode()
    }
    public static class Unresolved implements Contact {
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
            return "Contact.Unresolved{"
                 + "id=" + _id
                 + "}";
        }
    }
    public static interface Resolver extends com.distelli.graphql.Resolver<Contact> {
        public List<Contact> resolve(List<Contact> list);
    }
    public default String getId() { return null; }

}

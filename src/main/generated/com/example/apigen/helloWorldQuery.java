package com.example.apigen;

public interface helloWorldQuery {
    public static class Builder {

        private String _hello;

        public Builder() {}
        public Builder(helloWorldQuery src) {

            _hello = src.getHello();

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

        private String _hello;

        protected Impl(Builder builder) {

            this._hello = builder._hello;

        }

        @Override
        public String getHello() {
            return _hello;
        }

        @Override
        public String toString() {
            return "helloWorldQuery{"

                 + "hello=" + _hello


                 + "}";
        }
        // TODO: equals(Object) & hashCode()
    }
    public default String getHello() { return null; }

}

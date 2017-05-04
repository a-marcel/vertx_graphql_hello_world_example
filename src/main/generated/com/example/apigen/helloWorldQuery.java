package com.example.apigen;

import com.example.apigen.Page;
import java.util.List;
public interface helloWorldQuery {
    public static class Builder {
        public Builder() {}
        public Builder(helloWorldQuery src) {
        }

        public helloWorldQuery build() {
            return new Impl(this);
        }
    }
    public static class Impl implements helloWorldQuery {
        protected Impl(Builder builder) {
        }
        @Override
        public String toString() {
            return "helloWorldQuery{"

                 + "}";
        }
        // TODO: equals(Object) & hashCode()
    }
    // TODO: extend any implemented interfaces...
    interface PageArgs {
        default String getUrl() { return null; }
    }
    public default List<Object> page(PageArgs args) { return null; }

}

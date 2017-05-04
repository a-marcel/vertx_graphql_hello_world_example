package com.example.apigen;

public interface Page {

	public static class Builder {

		private String _id;

		public Builder() {
		}

		public Builder(Page src) {

			_id = src.getId();

		}

		public Builder withId(String _id) {
			this._id = _id;
			return this;
		}

		public Page build() {
			return new Impl(this);
		}
	}

	public static class Impl implements Page {

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
			return "Page{"

					+ "id=" + _id

					+ "}";
		}
		// TODO: equals(Object) & hashCode()
	}

	public default String getId() {
		return null;
	}
}
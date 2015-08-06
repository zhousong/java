package json;

import com.alibaba.fastjson.JSON;

public class FastJsonTrueFalse {
	public static void main(String[] args) {
		//Success
		System.out.println(JSON.parseObject("{\"isAccess\":1}",
				ObjectBoolean.class));
		System.out.println(JSON.parseObject("{\"access\":1}",
				BasicBoolean.class));
		System.out.println(JSON.parseObject("{\"isAccess\":true}",
				ObjectBoolean.class));
		System.out.println(JSON.parseObject("{\"access\":true}",
				BasicBoolean.class));
		//Fail
		System.out.println(JSON.parseObject("{\"isAccess\":\"0\"}",
				ObjectBoolean.class));
		System.out.println(JSON.parseObject("{\"access\":\"0\"}",
				BasicBoolean.class));
	}

	static class ObjectBoolean {
		private Boolean isAccess;

		public Boolean getIsAccess() {
			return isAccess;
		}

		public void setIsAccess(Boolean isAccess) {
			this.isAccess = isAccess;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("ObjectBoolean [isAccess=");
			builder.append(isAccess);
			builder.append("]");
			return builder.toString();
		}
	}

	static class BasicBoolean {
		private boolean isAccess;

		public boolean isAccess() {
			return isAccess;
		}

		public void setAccess(boolean isAccess) {
			this.isAccess = isAccess;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("BasicBoolean [isAccess=");
			builder.append(isAccess);
			builder.append("]");
			return builder.toString();
		}
	}
}

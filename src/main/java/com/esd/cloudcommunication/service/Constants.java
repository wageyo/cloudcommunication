package com.esd.cloudcommunication.service;

/**
 * 常量类
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-5-22
 */
public class Constants {

	public static final String NOTICE = "notice"; // 提示文字key

	/**
	 * json格式前台返回提示符
	 * 
	 * @author yufu
	 * @email ilxly01@126.com 2014-12-11
	 */
	public enum Notice {
		SUCCESS("success"), ERROR("error"), WRONG("wrong"), INFO("info"), FAILURE(
				"failure");
		// 定义私有变量
		private String val;

		// 构造函数, 枚举类型只能为私有
		private Notice(String val) {
			this.val = val;
		}

		@Override
		public String toString() {
			return String.valueOf(val);
		}

		public String getValue() {
			return this.val;
		}
	}

	/**
	 * 投票方式
	 * 
	 * @author yufu
	 * @email ilxly01@126.com 2015-5-22
	 */
	public enum Type {
		// 利用构造函数传参 级.
		CALLING("calling"), MESSAGE("message"), WEB("web");

		// 定义私有变量
		private String val;

		// 构造函数, 枚举类型只能为私有
		private Type(String val) {
			this.val = val;
		}

		@Override
		public String toString() {
			return String.valueOf(val);
		}

		public String getValue() {
			return this.val;
		}
	}
}

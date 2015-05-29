package com.esd.cloudcommunication.service;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-5-22
 */
public class KitService {

	/**
	 * 获得一个UUID
	 * 
	 * @return
	 */
	public static String getRandomPrimaryKey() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 验证是否为5位数字
	 * 
	 * @param digits 
	 * @return
	 */
	public static boolean isNumber(String digits) {
		String regExp = "^[0-9]{5}$";
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(digits);
		return matcher.find();
	}

	/**
	 * 生成四位随机数字
	 * 
	 * @return 四位随机数字
	 */
	public static int getRandomCode() {
		int code = 0;
		while (code < 1000 || code > 9999) {
			code = (int) (Math.random() * 10000);
		}
		return code;
	}
	
}

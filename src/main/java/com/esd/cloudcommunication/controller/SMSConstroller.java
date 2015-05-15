package com.esd.cloudcommunication.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.esd.cloudcommunication.bean.Sms;
import com.esd.cloudcommunication.service.SmsService;

/**
 * 短信验证码controller
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-2-6
 */
@Controller
@RequestMapping("/sms")
public class SMSConstroller {

	private static final Logger logger = LoggerFactory
			.getLogger(SMSConstroller.class);

	@Value("${server.url}")
	String serverUrl;

	@Value("${server.port}")
	String serverPort;

	@Value("${account.sid}")
	String accountSid;

	@Value("${account.token}")
	String accountToken;

	@Value("${appid}")
	String appid;

	@Autowired
	private SmsService smsService;

	//发送短信方法
	@RequestMapping("/sendsms/{cellnumber}/{verifycode}/{timelimit}")
	@ResponseBody
	public Map<String, Object> sendSMS(
			@PathVariable(value = "cellnumber") String cellnumber,
			@PathVariable(value = "verifycode") String verifycode,
			@PathVariable(value = "timelimit") Integer timelimit,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> result = null;
		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
		restAPI.init(serverUrl, serverPort);// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
		restAPI.setAccount(accountSid, accountToken);// 初始化主帐号名称和主帐号令牌
		restAPI.setAppId(appid);// 初始化应用ID
		result = restAPI.sendTemplateSMS(cellnumber, "1", new String[] {
				verifycode, timelimit.toString() });
		Sms sms = new Sms();
		sms.setCellNumber(cellnumber);
		sms.setVerifyCode(verifycode);
		sms.setTimeLimit(timelimit);
		//发送请求成功
		if ("000000".equals(result.get("statusCode"))) {
			smsService.save(sms);//保存短信
			map.put("notice", "success");
			logger.info("***** 短信验证码发送成功! *****");
		} else {
			// 异常返回输出错误码和错误信息
			String wrongMsg = "错误码=" + result.get("statusCode") + " 错误信息= "
					+ result.get("statusMsg");
			map.put("notice",wrongMsg);
			logger.info("***** 短信验证码发送失败! *****");
			logger.info(wrongMsg);
		}
		return map;
	}

//	public static void main(String[] args) {
//		HashMap<String, Object> result = null;
//		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
//		restAPI.init("sandboxapp.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
//		restAPI.setAccount("aaf98f894b353559014b5782d4851275",
//				"cbc9525fe59544b1bea52936fbd919e4");// 初始化主帐号名称和主帐号令牌
//		restAPI.setAppId("aaf98f894b353559014b57840e43127b");// 初始化应用ID
//		result = restAPI.sendTemplateSMS("13804802181", "1", new String[] {
//				"9527", "2" });
//		// result = restAPI.sendSMS("15846538450", "13213");
//
//		System.out.println("SDKTestGetSubAccounts result=" + result);
//		if ("000000".equals(result.get("statusCode"))) {
//			// 正常返回输出data包体信息（map）
//			HashMap<String, Object> data = (HashMap<String, Object>) result
//					.get("data");
//			Set<String> keySet = data.keySet();
//			for (String key : keySet) {
//				Object object = data.get(key);
//				System.out.println(key + " = " + object);
//			}
//		} else {
//			// 异常返回输出错误码和错误信息
//			System.out.println("错误码=" + result.get("statusCode") + " 错误信息= "
//					+ result.get("statusMsg"));
//		}
//	}
}

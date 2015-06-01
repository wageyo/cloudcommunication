package com.esd.cloudcommunication.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.esd.cloudcommunication.common.Sender;
import com.esd.cloudcommunication.service.SMSService;

/**
 * 短信发送服务类
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-5-25
 */
@Service
public class SMSServiceImpl implements SMSService {

	private static Logger log = Logger.getLogger(SMSServiceImpl.class);

	@Value("${sms.username}")
	private String username;

	@Value("${sms.password}")
	private String password;

	/**
	 * ***************使用    容联云通讯    发送短信*******************
	 * 发送短信--不对短信内容进行非法校验更正
	 * 
	 * @param phone格式为单个或多个电话
	 *            , 单个电话时直接传入电话号码即可, 例如 "13812345678"; 多个时中间用","隔开, 例如
	 *            "138123445678,13512312312,13112541251", 末尾不用",", 一次最多99个电话.
	 * @param message
	 *            短信内容, 最大不超过340
	 * @return
	 */
	public Boolean sendMessage(String phone, String message) {
		Sender sender = new Sender(username, password);
		// 发送短信
		String result = sender.massSend(phone, message, null, null);
		// 截取发送条数num
		String num = result.substring(result.indexOf("num") + 4,
				result.indexOf("num") + 5);
		// 截取errid
		String errid = result.substring(result.indexOf("errid") + 6,
				result.indexOf("errid") + 7);
		// 如果发送成功, 则返回true
		if ((Integer.parseInt(num) >= 1) && "0".equals(errid)) {
			log.info("************************保存发送记录******************************");
			// saveSentHistory(phone, message, logUser);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
//	/**
//	 * ***************使用    名商通    发送短信验证码*******************
//	 * 发送短信--不对短信内容进行非法校验更正
//	 * 
//	 * @param phone格式为单个或多个电话
//	 *            , 单个电话时直接传入电话号码即可, 例如 "13812345678"; 多个时中间用","隔开, 例如
//	 *            "138123445678,13512312312,13112541251", 末尾不用",", 一次最多99个电话.
//	 * @param message
//	 *            短信内容, 最大不超过340
//	 * @return
//	 */
//	public Boolean sendMessage(String phone, String message) {
//		Sender sender = new Sender(username, password);
//		// 发送短信
//		String result = sender.massSend(phone, message, null, null);
//		// 截取发送条数num
//		String num = result.substring(result.indexOf("num") + 4,
//				result.indexOf("num") + 5);
//		// 截取errid
//		String errid = result.substring(result.indexOf("errid") + 6,
//				result.indexOf("errid") + 7);
//		// 如果发送成功, 则返回true
//		if ((Integer.parseInt(num) >= 1) && "0".equals(errid)) {
//			log.info("************************保存发送记录******************************");
//			// saveSentHistory(phone, message, logUser);
//			return Boolean.TRUE;
//		}
//		return Boolean.FALSE;
//	}

	// /**
	// * 发送短信--对短信内容进行非法校验更正
	// *
	// * @param phone格式为单个或多个电话
	// * , 单个电话时直接传入电话号码即可, 例如 "13812345678"; 多个时中间用","隔开, 例如
	// * "138123445678,13512312312,13112541251", 末尾不用",", 一次最多99个电话.
	// * @param message
	// * 短信内容, 最大不超过340
	// * @return
	// */
	// public Boolean sendMessage(String phone, String message,
	// String illegalFileUrl, String logUser, SmsAccount smsAccount) {
	//
	// // 处理非法关键字
	// message = dealIllegalContent(message, illegalFileUrl);
	//
	// Sender sender = new Sender(smsAccount.getUsername(),
	// smsAccount.getPassword());
	// // 发送短信
	// String result = sender.massSend(phone, message, null, null);
	// // 截取发送条数num
	// String num = result.substring(result.indexOf("num") + 4,
	// result.indexOf("num") + 5);
	// // 截取errid
	// String errid = result.substring(result.indexOf("errid") + 6,
	// result.indexOf("errid") + 7);
	// // 如果发送成功, 则返回true
	// if ((Integer.parseInt(num) >= 1) && "0".equals(errid)) {
	// log.info("************************短信发送成功******************************");
	// log.info("发送条数: " + num);
	// log.info("************************短信发送成功******************************");
	// log.info("************************保存发送记录******************************");
	// saveSentHistory(phone, message, logUser);
	// return Boolean.TRUE;
	// }
	// String err = result.substring(result.indexOf("err") + 4,
	// result.indexOf("errid") - 1);
	// errid = result.substring(result.indexOf("errid") + 6);
	// log.info("************************短信发送失败******************************");
	// log.info("错误原因: " + err);
	// log.info("错误代码: " + errid);
	// log.info("************************短信发送失败******************************");
	// return Boolean.FALSE;
	// }

}

package com.esd.cloudcommunication.service;

import org.springframework.stereotype.Service;

/**
 * 短信发送服务类
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-5-25
 */
@Service
public interface SMSService {

	/**
	 * ***************使用 名商通 发送短信验证码******************* 发送短信--不对短信内容进行非法校验更正
	 * 
	 * @param phone格式为单个或多个电话
	 *            , 单个电话时直接传入电话号码即可, 例如 "13812345678"; 多个时中间用","隔开, 例如
	 *            "138123445678,13512312312,13112541251", 末尾不用",", 一次最多99个电话.
	 * @param message
	 *            短信内容, 最大不超过340
	 * @return
	 */
	public Boolean sendMessageBySHP(String phone, String message);
	
	/**
	 * ***************使用    速达    发送短信验证码*******************
	 * 发送短信--不对短信内容进行非法校验更正
	 * 
	 * @param phone格式为单个或多个电话
	 *            , 单个电话时直接传入电话号码即可, 例如 "13812345678"; 多个时中间用","隔开, 例如
	 *            "138123445678,13512312312,13112541251", 末尾不用",", 一次最多99个电话.
	 * @param message
	 *            短信内容, 最大不超过340
	 * @return
	 */
	public Boolean sendMessageBySuda(String mobile, String content);

}

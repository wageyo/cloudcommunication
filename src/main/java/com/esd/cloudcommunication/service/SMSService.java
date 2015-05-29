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
	 * 发送短信
	 * 
	 * @param phone
	 * @param message
	 * @return
	 */
	public Boolean sendMessage(String phone, String message);

}

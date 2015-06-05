package com.esd.cloudcommunication.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.esd.cloudcommunication.common.SHPSender;
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

	/********************* 名商通用户密码区 *****************************/
	@Value("${sms.username}")
	private String username;

	@Value("${sms.password}")
	private String password;
	/********************* 名商通用户密码区 *****************************/

	/********************* 速达用户密码区 *****************************/
	@Value("${suda.url}")
	private String sudaurl;

	@Value("${suda.sn}")
	private String sudasn;

	@Value("${suda.pwd}")
	private String sudapwd;

	@Value("${suda.md5key}")
	private String sudamd5key;
	/********************* 速达用户密码区 *****************************/

	/**
	 * ***************使用 速达 发送短信验证码******************* 发送短信--不对短信内容进行非法校验更正
	 * 
	 * @param phone格式为单个或多个电话
	 *            , 单个电话时直接传入电话号码即可, 例如 "13812345678"; 多个时中间用","隔开, 例如
	 *            "138123445678,13512312312,13112541251", 末尾不用",", 一次最多99个电话.
	 * @param message
	 *            短信内容, 最大不超过340
	 * @return
	 */
	public Boolean sendMessageBySuda(String mobile, String content) {
		String url = this.sudaurl;
		String MARK = "|";
		String sn = this.sudasn;
		String pwd = this.sudapwd;
		String ext = "";
		String rrid = "";
		String stime = "";
		String stype = "1";
		String ssafe = "1";
		String scode = "1"; // 1表示UTF-8，2表示GB2312
		String Md5key = this.sudamd5key;
		//对短信内容进行utf-8编码
		try {
			content = new String(content.trim().getBytes("utf-8"), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			pwd = getMD5(pwd);
			// 密码md5加密
			System.out.println("pwd: " + pwd);
			String signInfo = sn + MARK + pwd + MARK + mobile + MARK + content
					+ MARK + ext + MARK + rrid + MARK + stime + MARK + stype
					+ MARK + ssafe + MARK + scode + MARK + Md5key;
			System.out.println("signInfo: " + signInfo);
			char[] strChar2 = signInfo.toCharArray();
			String result2 = "";
			for (int i = 0; i < strChar2.length; i++) {
				result2 += Integer.toBinaryString(strChar2[i]) + " ";
			}
			System.out.println("二进制结果:" + result2);
			// 将拼接好的信息MD5加密
			String Md5Sign = getMD5(signInfo);
			System.out.println("Md5Sign: " + Md5Sign);
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpost = new HttpPost(url);

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			// 不同于登录SendCloud站点的帐号，您需要登录后台创建发信子帐号，使用子帐号和密码才可以进行邮件的发送。
			nvps.add(new BasicNameValuePair("sn", sn));
			nvps.add(new BasicNameValuePair("pwd", pwd));
			nvps.add(new BasicNameValuePair("mobile", mobile));
			nvps.add(new BasicNameValuePair("content", content));
			nvps.add(new BasicNameValuePair("ext", ""));
			nvps.add(new BasicNameValuePair("rrid", ""));
			nvps.add(new BasicNameValuePair("stime", ""));
			nvps.add(new BasicNameValuePair("stype", "1"));
			nvps.add(new BasicNameValuePair("ssafe", "1"));
			nvps.add(new BasicNameValuePair("scode", "1")); // 1表示UTF-8，2表示GB2312
			nvps.add(new BasicNameValuePair("Md5key", Md5key));
			nvps.add(new BasicNameValuePair("Md5Sign", Md5Sign));

			httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			// 请求
			HttpResponse response = httpclient.execute(httpost);

			// 处理响应
			System.out.println(response.getStatusLine());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
				// 读取xml文档
				String result = EntityUtils.toString(response.getEntity());
				log.info("发送返回结果result:" + result);
				// 发送失败, 有表示复数的 -
				if (result.indexOf("-") > -1) {
					log.info("发送短信失败");
					return Boolean.FALSE;
				} else {
					log.info("发送短信成功");
					return Boolean.TRUE;
				}
			} else {
				log.error("error");
				log.error("发送短信出错");
				return Boolean.FALSE;
			}
		} catch (UnsupportedEncodingException e) {
			log.error("md5加密或向post请求中添加参数编码时错误");
			e.printStackTrace();
			return Boolean.FALSE;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			log.error("执行post请求错误");
			return Boolean.FALSE;
		} catch (IOException e) {
			e.printStackTrace();
			log.error("读取返回结果发生错误");
			return Boolean.FALSE;
		}
	}

	// /**
	// * ***************使用 容联云通讯 发送短信*******************
	// !!!!!!!!!!!没有修改完成!!!!!!!!!!!!!!
	// * 发送短信--不对短信内容进行非法校验更正
	// *
	// * @param phone格式为单个或多个电话
	// * , 单个电话时直接传入电话号码即可, 例如 "13812345678"; 多个时中间用","隔开, 例如
	// * "138123445678,13512312312,13112541251", 末尾不用",", 一次最多99个电话.
	// * @param message
	// * 短信内容, 最大不超过340
	// * @return
	// */
	// public Boolean sendMessage(String phone, String message) {
	// SHPSender sender = new SHPSender(username, password);
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
	// log.info("************************保存发送记录******************************");
	// // saveSentHistory(phone, message, logUser);
	// return Boolean.TRUE;
	// }
	// return Boolean.FALSE;
	// }

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
	public Boolean sendMessageBySHP(String phone, String message) {
		SHPSender sender = new SHPSender(username, password);
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
	// SHPSender sender = new SHPSender(smsAccount.getUsername(),
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

	/**
	 * md5加密----速达专用
	 * 
	 * @param sourceStr
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getMD5(String sourceStr) throws UnsupportedEncodingException {
		String resultStr = "";
		try {
			byte[] temp = sourceStr.getBytes("utf-8");
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(temp);
			// resultStr = new String(md5.digest());
			byte[] b = md5.digest();
			for (int i = 0; i < b.length; i++) {
				char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
						'9', 'a', 'b', 'c', 'd', 'e', 'f' };
				char[] ob = new char[2];
				ob[0] = digit[(b[i] >>> 4) & 0X0F]; // 5695509acc6e5242edb52dc8ab7d1fb6
				ob[1] = digit[b[i] & 0X0F]; // 5695509ACC6E5242EDB52DC8AB7D1FB6
				resultStr += new String(ob);
			}
			return resultStr;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		SMSServiceImpl s = new SMSServiceImpl();
		System.out.println(Charset.defaultCharset().name());
		String content = "您已经向该项目投过票啦, 请不要重复投票!";
		// content = "您已经向该项目投过票啦, 请不要重复投票!";
		try {
			content = new String(content.trim().getBytes("utf-8"), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String md5info = "SUD-KEY-001-191|e10adc3949ba59abbe56e057f20f883e|13804802181|"
				+ content + "||||1|1|1|4i9f5m";
		char[] strChar = md5info.toCharArray();
		String result = "";
		for (int i = 0; i < strChar.length; i++) {
			result += Integer.toBinaryString(strChar[i]) + " ";
		}
		System.out.println("二进制结果:" + result);
		System.out.println(s.getMD5(md5info));
	}
}

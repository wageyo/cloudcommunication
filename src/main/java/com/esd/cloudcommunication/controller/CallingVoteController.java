package com.esd.cloudcommunication.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.esd.cloudcommunication.bean.Vote;
import com.esd.cloudcommunication.service.Constants;
import com.esd.cloudcommunication.service.KitService;
import com.esd.cloudcommunication.service.VoteService;

/**
 * 电话投票controller
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-2-6
 */
@Controller
public class CallingVoteController {

	private static final Logger log = LoggerFactory
			.getLogger(CallingVoteController.class);

	@Autowired
	private VoteService voteService;

	// 开始呼入服务
	@RequestMapping(value = "/startservice", method = RequestMethod.POST)
	public void startservice(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		log.info("**************  startservice  ****************");
		String callid = request.getParameter("callid");
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		log.info("callid：{}，来电：{}, 去电：{}, ", callid, from, to);
		// 生成四位数字验证码
		int code = KitService.getRandomCode();
		PrintWriter out = response.getWriter();
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		out.println("<Response>");
		out.println("  <PlayMix type=\"440\">welcome.wav;inputcode.wav;" + code
				+ "</PlayMix>");
		out.println("  <Redirect>/inputcode?code=" + code + "</Redirect>");
		out.println("</Response>");
		out.flush();
		out.close();
	}

	// 输入验证码
	@RequestMapping(value = "/inputcode", method = RequestMethod.POST)
	public void inputcode(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		log.info("**************  inputcode  ****************");
		String code = request.getParameter("code");
		PrintWriter out = response.getWriter();
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		out.println("<Response>");
		// 如输入 四位字母加上# 则进action 的url路径
		out.println("	<Get action=\"/checkcode?code=" + code
				+ "\" finishkey=\"#\" >");
		// out.println("   	<Play>inputcode.wav</Play>");
		out.println("	</Get>");
		// 否则播放再次输入录音
		out.println("	<PlayMix type=\"40\">inputcode.wav;" + code
				+ "</PlayMix>");
		out.println("  <Redirect>/inputcode?code=" + code + "</Redirect>");
		out.println("</Response>");
		out.flush();
		out.close();
	}

	// 校验验证码
	@RequestMapping(value = "/checkcode", method = RequestMethod.POST)
	public void checkcode(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		log.info("**************  checkcode  ****************");
		// 按键内容
		String code = request.getParameter("code");
		log.info("code: " + request.getParameter("code"));
		String digits = request.getParameter("digits"); // 按键内容
		log.info("应输入验证码：" + code);
		log.info("已输入验证码：" + digits);
		PrintWriter out = response.getWriter();
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		out.println("<Response>");
		// 如果按键 不为四位数字+# 则返回重新输入
		// String regExp = "^[0-9]{4}$";
		// Pattern pattern = Pattern.compile(regExp);
		// Matcher matcher = pattern.matcher(digits);
		if (digits == null || "".equals(digits) || digits.length() < 4
				|| !isNumber(digits.substring(0, 4))) {
			out.println("	<PlayMix type=\"40\">wrongcode.wav;" + code
					+ "</PlayMix>");
			out.println("	<Redirect>/inputcode?code=" + code + "</Redirect>");
			out.println("</Response>");
			out.flush();
			out.close();
		} else {
			// 如果输入的按键内容不符合规范, 则返回重新输入
			String inputCode = digits.substring(0, 4);
			// 如果验证码输入不正确,返回重新输入
			if (!inputCode.equals(code)) {
				out.println("	<PlayMix type=\"40\">wrongcode.wav;" + code
						+ "</PlayMix>");
				out.println("	<Redirect>/inputcode?code=" + code
						+ "</Redirect>");
				out.println("</Response>");
				out.flush();
				out.close();
			}
			// 验证码正确则进入投票
			out.println("	<PlayMix type=\"4\">inputprojectno.wav;</PlayMix>");
			out.println("	<Redirect>/inputprojectno</Redirect>");
			out.println("</Response>");
			out.flush();
			out.close();
		}
	}

	// 输入项目编码
	@RequestMapping(value = "/inputprojectno", method = RequestMethod.POST)
	public void inputprojectno(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		log.info("**************  inputprojectno  ****************");
		PrintWriter out = response.getWriter();
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		out.println("<Response>");
		out.println("	<Get action=\"/checkprojectno\" finishkey=\"#\" >");
		out.println("	</Get>");
		out.println("	<PlayMix type=\"4\">inputprojectno.wav;</PlayMix>");
		out.println("	<Redirect>/inputprojectno</Redirect>");
		out.println("</Response>");
		out.flush();
		out.close();
	}

	// 校验/保存投票的项目编码
	@RequestMapping(value = "/checkprojectno", method = RequestMethod.POST)
	public void checkprojectno(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		log.info("**************  inputprojectno  ****************");
		String callid = request.getParameter("callid");
		String digits = request.getParameter("digits");
		PrintWriter out = response.getWriter();
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		out.println("<Response>");
		log.info("输入的项目编号: " + digits);
		// 如果输入的项目编号 不是 五位数字+# 的格式, 则返回重新输入, 待有项目编码列表后进行验证
		// String regExp = "^[0-9]{5}[#]$";
		// Pattern pattern = Pattern.compile(regExp);
		// Matcher matcher = pattern.matcher(digits);
		if (digits == null || "".equals(digits) || digits.length() < 5
				|| !isNumber(digits.substring(0, 5))) {
			out.println("	<PlayMix type=\"4\">wrongprojectno.wav;</PlayMix>");
			out.println("	<Redirect>/inputprojectno</Redirect>");
			out.println("</Response>");
			out.flush();
			out.close();
		} else {
			String from = request.getParameter("from");
			String to = request.getParameter("to");
			
			// 截取五位项目编号
			String projectNo = digits.substring(0, 5);
			// 正确, 则将投票结果插入数据库
			Vote vote = new Vote();
			vote.setCallid(callid);
			vote.setFromCalling(from);
			vote.setToCalling(to);
			vote.setProjectno(projectNo);
			vote.setType(Constants.Type.CALLING.getValue());
			vote.setIsSuccess(Boolean.TRUE);
			log.info(vote.toString());
			Boolean bl = voteService.save(vote);
			if (bl) {
				out.println("	<Play type=\"4\">thanks.wav</Play>");
				out.println("	<Redirect>/stopservice</Redirect>");
			} else {
				out.println("	<PlayMix type=\"4\">inputprojectno.wav;</PlayMix>");
				out.println("	<Redirect>/inputprojectno</Redirect>");
			}
			out.println("</Response>");
			out.flush();
			out.close();
		}
	}

	// 停止服务, 挂机
	@RequestMapping(value = "/stopservice", method = RequestMethod.POST)
	public void stopservice(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		log.info("**************  stopservice  ****************");
		log.info("callid: " + request.getParameter("callid"));
		String callid = request.getParameter("callid");
		PrintWriter out = response.getWriter();
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		out.println("<Response>");
		out.println("	<Hangup callid=\"" + callid + "\" />");
		out.println("</Response>");
		out.flush();
		out.close();
	}

	private boolean isNumber(String digits) {
		String regExp = "^[0-9]{4,5}$";
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(digits);
		return matcher.find();
	}
}

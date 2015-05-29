package com.esd.cloudcommunication.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esd.cloudcommunication.bean.Project;
import com.esd.cloudcommunication.bean.Vote;
import com.esd.cloudcommunication.service.Constants;
import com.esd.cloudcommunication.service.KitService;
import com.esd.cloudcommunication.service.ProjectService;
import com.esd.cloudcommunication.service.SMSService;
import com.esd.cloudcommunication.service.VoteService;

/**
 * 短信投票 controller
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-2-6
 */
@Controller
@RequestMapping("/sms")
public class SMSVoteConstroller {

	private static final Logger logger = LoggerFactory
			.getLogger(SMSVoteConstroller.class);

	@Autowired
	private SMSService smsService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private VoteService voteService;

	/***
	 * 被动接受短信投票
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/receive")
	@ResponseBody
	public String receiveMsg(HttpServletRequest request) {
		String src = request.getParameter("src"); // 回复短信的手机号码
		String dst = request.getParameter("dst");// 回复的特服号码
		String msg = request.getParameter("msg");// 回复短信的内容
		// 一. 检查短信内容是否合法, 即是是否为项目编号
		// 1.手机号码是否正确或其他关于手机的校验
		if (src == null || "".equals(src)) {
			// 此处可以向用户发送提示信息~~~
			logger.info("源手机号码不正确!:{}", src);
			return "-1";
		}
		// 2. 是否为五位数字
		if (!KitService.isNumber(msg)) {
			// 此处可以向用户发送提示信息~~~
			logger.info("短信内容不符合规则!!:{}", msg);
			return "-2";
		}
		// 3. 是否为项目编号
		Project project = projectService.getByProjectno(msg);
		if (project == null) {
			// 此处可以向用户发送提示信息~~~
			logger.info("短信内容不为项目编号!!:{}", msg);
			return "-3";
		}
		// 二. 检查该手机号是否给该项目投过票~~
		Vote vote = voteService.getbyPhoneAndProjectno(src, msg);
		if (vote != null) {
			// 此处可以向用户发送提示信息~~~
			logger.info("该手机已经向该项目投过票啦!!calling phone :{}, project no: {}",
					src, msg);
			return "-2";
		}
		// 三. 没有投过, 则投票, 成功返回 0, 失败进行提示
		vote = new Vote();
		vote.setFromCalling(src); // 投票手机
		vote.setToCalling(dst);// 目标手机
		vote.setProjectno(msg);// 项目编号
		vote.setIsSuccess(Boolean.TRUE);// 成功
		vote.setType(Constants.Type.MESSAGE.getValue()); // 短信投票...
		// 投票成功啦!!!
		if (voteService.save(vote)) {
			// 此处可以向用户发送提示信息~~~
			return "0";
		} else {
			// 此处可以向用户发送提示信息~~~
			logger.info("服务器保存投票信息失败...");
			return "-1";
		}
	}

}

package com.esd.cloudcommunication.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	 * ***************使用    速达    被动接收短信*******************
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/receive",method=RequestMethod.POST)
	@ResponseBody
	public String receiveMsg(HttpServletRequest request) {
		String receiveMsg = request.getParameter("args");
		//65356862,132710,15388650501,????????,2015-6-01 16:29:46
		if(receiveMsg==null || "".equals(receiveMsg)){
			logger.info("我没有收到信息啦!");
			return "-1";
		}
		logger.info("我收到信息啦!内容是:{}",receiveMsg);
		
		//检查是否为多条短信,
		if(receiveMsg.indexOf(";")<0){
			//单条
			saveVote(receiveMsg);
		}else{
			String[] uncombinedArray = receiveMsg.split(";");
			for(int i=0;i<uncombinedArray.length;i++){
				saveVote(uncombinedArray[i]);
			}
		}
		return "0";
	}
	
//	/***
//	 * ***************使用    名商通    被动接受短信*******************
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/receive")
//	@ResponseBody
//	public String receiveMsg(HttpServletRequest request) {
//		String src = request.getParameter("src"); // 回复短信的手机号码
//		String dst = request.getParameter("dst");// 回复的特服号码
//		String msg = request.getParameter("msg");// 回复短信的内容
//		// 一. 检查短信内容是否合法, 即是是否为项目编号
//		// 1.手机号码是否正确或其他关于手机的校验
//		if (src == null || "".equals(src)) {
//			// 此处可以向用户发送提示信息~~~
//			logger.info("源手机号码不正确!:{}", src);
//			return "-1";
//		}
//		// 2. 是否为五位数字
//		if (!KitService.isNumber(msg)) {
//			// 此处可以向用户发送提示信息~~~
//			logger.info("短信内容不符合规则!!:{}", msg);
//			return "-2";
//		}
//		// 3. 是否为项目编号
//		Project project = projectService.getByProjectno(msg);
//		if (project == null) {
//			// 此处可以向用户发送提示信息~~~
//			logger.info("短信内容不为项目编号!!:{}", msg);
//			return "-3";
//		}
//		// 二. 检查该手机号是否给该项目投过票~~
//		Vote vote = voteService.getbyPhoneAndProjectno(src, msg);
//		if (vote != null) {
//			// 此处可以向用户发送提示信息~~~
//			logger.info("该手机已经向该项目投过票啦!!calling phone :{}, project no: {}",
//					src, msg);
//			return "-2";
//		}
//		// 三. 没有投过, 则投票, 成功返回 0, 失败进行提示
//		vote = new Vote();
//		vote.setFromCalling(src); // 投票手机
//		vote.setToCalling(dst);// 目标手机
//		vote.setProjectno(msg);// 项目编号
//		vote.setIsSuccess(Boolean.TRUE);// 成功
//		vote.setType(Constants.Type.MESSAGE.getValue()); // 短信投票...
//		// 投票成功啦!!!
//		if (voteService.save(vote)) {
//			// 此处可以向用户发送提示信息~~~
//			return "0";
//		} else {
//			// 此处可以向用户发送提示信息~~~
//			logger.info("服务器保存投票信息失败...");
//			return "-1";
//		}
//	}

	/**
	 * 投票
	 * @param cellnumber 手机号
	 * @param projectno 项目编号
	 * @return 返回"0"的时候投票成功
	 */
	private boolean saveVote(String uncombinedStr){
		// 单条短信信息
		String[] infoArray = uncombinedStr.split(",");
		String cellnumber = infoArray[2];	//手机号码
		String projectno = infoArray[3];//短信内容
		logger.info("解码前的手机号和短信内容cellnumber:{},projectno: {}",cellnumber,projectno);
		//对短信内容进行解码
		try {
			projectno = URLDecoder.decode(projectno, "gb2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		logger.info("解码后的手机号和短信内容cellnumber:{},projectno: {}",cellnumber,projectno);
//		try {
//			projectno  = new String(projectno.getBytes("gb2312"),"utf-8");
//			logger.info("projectno: {}",projectno);
//		} catch (UnsupportedEncodingException e) {
//			logger.error("编解码出现异常!!!");
//			logger.error(e.getMessage());
//			e.printStackTrace();
//			return Boolean.FALSE;
//		}
		// 一. 检查短信内容是否合法, 即是是否为项目编号
		// 1.手机号码是否正确或其他关于手机的校验
		if (cellnumber == null || "".equals(cellnumber)) {
			// 此处可以向用户发送提示信息~~~
			logger.info("源手机号码不正确!:{}", cellnumber);
			return Boolean.FALSE;
		}
		// 2. 是否为五位数字
		if (!KitService.isNumber(projectno)) {
			// 此处可以向用户发送提示信息~~~
			logger.info("短信内容不符合规则,不为项目编号!:{}", projectno);
			smsService.sendMessageBySuda(cellnumber, "短信内容不符合规则,不为项目编号.");
			return Boolean.FALSE;
		}
		// 3. 是否为项目编号
		Project project = projectService.getByProjectno(projectno);
		if (project == null) {
			// 此处可以向用户发送提示信息~~~
			logger.info("短信内容不为项目编号!!:{}", projectno);
			smsService.sendMessageBySuda(cellnumber, "短信内容不符合规则,不为项目编号.");
			return Boolean.FALSE;
		}
		// 二. 检查该手机号是否给该项目投过票~~
		Vote vote = voteService.getbyPhoneAndProjectno(cellnumber, projectno);
		if (vote != null) {
			// 此处可以向用户发送提示信息~~~
			logger.info("该手机已经向该项目投过票啦!!calling phone :{}, project no: {}",
					cellnumber, projectno);
			smsService.sendMessageBySuda(cellnumber, "您已经向该项目投过票啦, 请不要重复投票!");
			return Boolean.FALSE;
		}
		// 三. 没有投过, 则投票, 成功返回 0, 失败进行提示
		vote = new Vote();
		vote.setFromCalling(cellnumber); // 投票手机
		vote.setProjectno(projectno);// 项目编号
		vote.setIsSuccess(Boolean.TRUE);// 成功
		vote.setType(Constants.Type.MESSAGE.getValue()); // 短信投票...
		// 投票成功啦!!!
		if (voteService.save(vote)) {
			smsService.sendMessageBySuda(cellnumber, "投票成功!");
			return Boolean.TRUE;
		} else {
			// 此处可以向用户发送提示信息~~~
			logger.info("服务器保存投票信息失败...");
			return Boolean.FALSE;
		}
	}
	
	@RequestMapping(value="/1",method=RequestMethod.GET)
	@ResponseBody
	public String receiveMsg1(HttpServletRequest request) {
//		String receiveMsg = request.getParameter("args");
		String receiveMsg = "65356862,132710,13804802181,00001,2015-6-01 16:29:46";
		//65356862,132710,15388650501,????????,2015-6-01 16:29:46
		if(receiveMsg==null || "".equals(receiveMsg)){
			logger.info("我没有收到信息啦!");
			return "-1";
		}
		logger.info("我收到信息啦!内容是:{}",receiveMsg);
		
		//检查是否为多条短信,
		if(receiveMsg.indexOf(";")<0){
			//单条
			if(saveVote(receiveMsg)){
				//保存成功则返回0
				return "0";
			}else{
				return "-1";
			}
		}else{
			String[] uncombinedArray = receiveMsg.split(";");
			for(int i=0;i<uncombinedArray.length;i++){
				saveVote(receiveMsg);
			}
			return "0";
		}
	}
	
	public static void main(String[] args) {
//		logger.info(Charset.defaultCharset().name());
//		String s = "你好";
//		try {
//			byte[] b = s.getBytes("GB2312");
//			logger.info(b.toString());
//			String sa = new String(b,"GB2312");
//			logger.info(sa);
//			b = sa.getBytes("UTF-8");
//			logger.info(b.toString());
//			sa = new String(b,"UTF-8");
//			logger.info(sa);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println(Integer.MAX_VALUE);
		
		try {
			String re = URLDecoder.decode("%b2%e2%ca%d4%c9%cf%d0%d0","gb2312");
			System.out.println(re);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

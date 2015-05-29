package com.esd.cloudcommunication.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.cloudcommunication.bean.Project;
import com.esd.cloudcommunication.bean.ResultModel;
import com.esd.cloudcommunication.bean.Vote;
import com.esd.cloudcommunication.common.QRCoder;
import com.esd.cloudcommunication.service.Constants;
import com.esd.cloudcommunication.service.KitService;
import com.esd.cloudcommunication.service.ProjectService;
import com.esd.cloudcommunication.service.SMSService;
import com.esd.cloudcommunication.service.VoteService;

/**
 * 页面投票和短信验证码controller
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-2-6
 */
@Controller
@RequestMapping("/web")
public class WebVoteController {

	private static final Logger logger = LoggerFactory
			.getLogger(WebVoteController.class);

	@Autowired
	private SMSService smsService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private VoteService voteService;

	/**
	 * 跳转到输入手机号码和验证码页面~~
	 * 
	 * @param projectno
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/vote/{projectno}", method = RequestMethod.GET)
	public ModelAndView vote_get(
			@PathVariable(value = "projectno") String projectno,
			HttpServletRequest request) {
		logger.info("***** 跳转网页投票页面 *****");
		// 根据项目编号获得项目对象
		Project project = projectService.getByProjectno(projectno);
		//判断是电脑访问还是手机访问
		if(JudgeIsMoblie(request)){
			System.out.println("***************************************手机登陆啦***************************************");
			return new ModelAndView("/wapvote", "project", project);
		}else{
			System.out.println("***************************************电脑登陆啦***************************************");
			return new ModelAndView("/vote", "project", project);
		}
	}

	/**
	 * 投票!!!
	 * 
	 * @param projectno
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/vote/{projectno}/{phone}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> vote_post(
			@PathVariable(value = "projectno") String projectno,
			@PathVariable(value = "phone") String phone,
			HttpServletRequest request) {
		logger.info("***** 投票啦!!!!!!!!!!!!!!*****");
		Map<String, Object> map = new HashMap<String, Object>();
		//验证是否投过票~~
		Vote temp = voteService.getbyPhoneAndProjectno(phone, projectno);
		if (temp != null) {
			map.put(Constants.NOTICE, "该号码已经向此项目投过票, 请不要重复投票.");
			return map;
		}
		// 将数据保存到vote对象中
		Vote vote = new Vote();
		vote.setFromCalling(phone);
		vote.setProjectno(projectno);
		vote.setType(Constants.Type.WEB.getValue());
		vote.setIsSuccess(Boolean.TRUE);
		Boolean bl = voteService.save(vote);
		if (bl) {
			map.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			map.put(Constants.NOTICE, Constants.Notice.FAILURE.getValue());
		}
		return map;
	}

	/**
	 * 发送短信验证码!!!!!!!!!!
	 * 
	 * @param cellnumber
	 * @return
	 */
	@RequestMapping(value="/sendsms/{projectno}/{phone}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sendSMS(@PathVariable(value = "projectno") String projectno,
			@PathVariable(value = "phone") String phone,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("***** 发送短信验证码 *****");
		Map<String, Object> map = new HashMap<String, Object>();
		//验证是否投过票~~
		Vote vote = voteService.getbyPhoneAndProjectno(phone, projectno);
		if (vote != null) {
			map.put(Constants.NOTICE, "该号码已经向此项目投过票, 请不要重复投票.");
			return map;
		}
		// 生成四位随机验证码
		int code = KitService.getRandomCode();
		Boolean flag = smsService.sendMessage(phone, String.valueOf(code));
		map.put("code", code);
		if (flag) {
			logger.info("***** 短信验证码发送成功,验证码为：{} *****" + code);
			map.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			logger.info("***** 短信验证码发送失败 *****");
			map.put(Constants.NOTICE, "验证码发送失败, 请重新尝试或联系管理员.");
		}
		return map;
	}

	/**
	 * 跳转到扫描二维码页面~~
	 * 
	 * @param projectno
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/scan2dimensioncode/{projectno}", method = RequestMethod.GET)
	public ModelAndView scan2dimensioncode(
			@PathVariable(value = "projectno") String projectno,
			HttpServletRequest request) {
		logger.info("***** 跳转到扫描二维码页面 *****");
		String requesturl = request.getRequestURL().toString();
		// String getRequestURI = request.getRequestURI();
		// String getLocalAddr = request.getLocalAddr();
		// String getLocalName = request.getLocalName();
		// int getLocalPort = request.getLocalPort();

		ModelAndView mav = new ModelAndView("scan2dimensioncode");
		// 根据项目编号获得项目对象
		Project project = projectService.getByProjectno(projectno);
		// 项目根目录
		String basePath = request.getSession().getServletContext()
				.getRealPath("/");
		// 获得目标文件路径
		String filePath = basePath + "upload" + File.separator
				+ "voting2dimensioncode.png";
		// //二维码图片路径
		// String twoDimensionImgPath =
		// "http://"+request.getLocalAddr()+":"+request.getLocalPort()+"/cloudcommunication/upload/voting2dimensioncode.png";
		// 添加到二维码里面的链接路径
		String contentUrl = requesturl.substring(0,
				requesturl.indexOf("cloudcommunication") + 18)
				+ "/web/vote/" + projectno;
		// 此处生成二维码图片啊~~~
		QRCoder qr = new QRCoder();
		try {
			boolean bl = qr.encoderQRCode(contentUrl, filePath, "png", 4);
			// 图片生成成功 则把图片路径传到前台
			if (bl) {
				logger.info("***** 生成二维码图片成功~~~ *****");
				// 当前时间戳~~
				long timestamp = System.currentTimeMillis();
				mav.addObject("project", project);
				mav.addObject("timestamp", timestamp);
				// mav.addObject("twoDimensionImgPath", twoDimensionImgPath);
				return mav;
			} else {
				logger.info("***** 生成二维码图片失败~~~ *****");
			}
		} catch (Exception e) {
			logger.info("***** 生成二维码图片发生异常~~~ *****");
			e.printStackTrace();
		}
		// 此处应该返回error.jsp页面！！
		return new ModelAndView("/scan2dimensioncode", "projectno", projectno);
	}

	/**
	 * 跳转到项目列表页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/projectlist", method = RequestMethod.GET)
	public ModelAndView projectlist(HttpServletRequest request) {
		logger.info("***** 跳转项目列表页面 *****");
		List<Project> projectList = new ArrayList<Project>();
		for (Project project : projectService.getPaginationRecords(null, null,
				null).getRecords()) {
			projectList.add(project);
		}
		return new ModelAndView("/projectList", "projectList", projectList);
	}

	/**
	 * 异步验证手机号是否已向该项目投过票
	 * 
	 * @param projectno
	 * @param phone
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkvote/{projectno}/{phone}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkvote_post(
			@PathVariable(value = "projectno") String projectno,
			@PathVariable(value = "phone") String phone,
			HttpServletRequest request) {
		logger.info("***** 验证手机是否投过票!!*****");
		Map<String, Object> map = new HashMap<String, Object>();
		Vote vote = voteService.getbyPhoneAndProjectno(phone, projectno);
		if (vote == null) {
			map.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			map.put(Constants.NOTICE, Constants.Notice.FAILURE.getValue());
		}
		return map;
	}

	/**
	 * 判断是否为手机登陆~~~
	 * @param request
	 * @return
	 */
	public boolean JudgeIsMoblie(HttpServletRequest request) {
		boolean isMoblie = false;
		String[] mobileAgents = { "iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
				"opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
				"nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
				"docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
				"techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
				"wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
				"pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
				"240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
				"blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
				"kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
				"mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
				"prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
				"smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
				"voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
				"Googlebot-Mobile" };
		if (request.getHeader("User-Agent") != null) {
			for (String mobileAgent : mobileAgents) {
				if (request.getHeader("User-Agent").toLowerCase().indexOf(mobileAgent) >= 0) {
					isMoblie = true;
					break;
				}
			}
		}
		return isMoblie;
	}
	
	@RequestMapping(value = "/getResult", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getResult(HttpServletRequest request) {
		logger.info("***** 验证手机是否投过票!!*****");
		Map<String, Object> map = new HashMap<String, Object>();
		List<ResultModel> results = voteService.getResult();
		if (results == null) {
			map.put("result",-1);
		} else {
			map.put("result",results);
		}
		return map;
	}
}

package com.esd.cloudcommunication.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esd.cloudcommunication.bean.Vote;
import com.esd.cloudcommunication.service.VoteService;

@Controller
@RequestMapping("/test")
public class TestController {

//	private static final Logger logger = LoggerFactory
//			.getLogger(TestController.class);

	@Autowired
	private VoteService voteService;

	@RequestMapping(value = "/start/startservice", method = RequestMethod.POST)
	public void startservice(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out
				.println("***********************  startservice  *************************");
		System.out.println("callid: " + request.getParameter("callid"));
		System.out.println("from: " + request.getParameter("from"));
		System.out.println("to: " + request.getParameter("to"));
		System.out.println("direction: " + request.getParameter("direction"));
		System.out.println("appid: " + request.getParameter("appid"));
		System.out.println("userdata: " + request.getParameter("userdata"));

		PrintWriter out = response.getWriter();
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		out.println("<Response>");
		out.println("  <PlayMix type=\"0\">1234567890</PlayMix>");
		out.println("  <Redirect>yzm?yzm=0122</Redirect>");
		out.println("</Response>");
		out.flush();
		out.close();
	}

	@RequestMapping(value = "/start2", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> startservice2(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out
				.println("***********************  startservice  *************************");
//		String callid = request.getParameter("callid");
//		String from = request.getParameter("from");
//		String to = request.getParameter("to");
//		String direction = request.getParameter("direction");
//		String appid = request.getParameter("appid");
		// 生成四位数字验证码
		Integer code = 0;
		while (code < 1000 || code > 9999) {
			code = (int) (Math.random() * 10000);
		}
		Vote calling = new Vote();
		calling.setId("1502121333119418000600040000045d");
		calling.setFromCalling("13804802181");
		// calling.setTo("01028554454");
		// calling.setAppid("aaf98f894b353559014b57899228128b");
		calling.setVerificationCode(code.toString());
		voteService.save(calling);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "yufu");
		map.put("id", calling.getId());
		return map;
	}

}

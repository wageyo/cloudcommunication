<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html lang="zh-CN">
<head>

	<title>【${project.name }】项目投票页面</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="keywords" content="投票" />
	<meta name="description" content="网络,投票" />
	
	<link type="text/css" rel="stylesheet" href="${contextPath }/js/bootstrap-3.3.4-dist/css/bootstrap-combined.min.css"/>
	<link type="text/css" rel="stylesheet" href="${contextPath }/css/public.css"/>
	<link type="text/css" rel="stylesheet" href="${contextPath }/css/index.css"/>
	<style type="text/css">
		h1,h3,p,input,button,span{
		}
		input,button{
		
		}
	</style>
	<script type="text/javascript" src="${contextPath }/js/jquery-1.11.1.js"></script>
	<script type="text/javascript" src="${contextPath }/js/bootstrap-3.3.4-dist/js/bootstrap.js"></script>
	<script type="text/javascript" src="${contextPath }/js/common.js"></script>
	<script type="text/javascript" src="${contextPath }/js/vote.js"></script>
	
</head>

<body>
	
	<div class="container-fluid" style="margin-top:5%;">
	<div class="row-fluid">
		<div class="span12">
			<h1 class="text-center" style="font-size:100px;line-height:120px;">
				手机投票页面
				<!-- 项目编号 -->
				<input type="hidden" value="${project.projectno }" id="projectno"/>
			</h1>
			<br/>
			<h3 contenteditable="false" style="font-size:70px;line-height: 100px;">${project.name }</h3>
            <p contenteditable="false" style="font-size:70px;line-height: 100px;">项目名称：${project.name }</p>
            <p contenteditable="false" style="font-size:70px;line-height: 100px;">项目编号：${project.projectno }</p>
            <p contenteditable="false" style="font-size:70px;line-height: 100px;width:820px;">手机号码：<input class="input-xxlarge search-query" type="text" value="" id="phone" style="height: 80px;font-size: 70px;width:450px;"/></p>
            <p contenteditable="false" style="font-size:70px;line-height: 100px;height:100px;">
            	<button class="btn btn-info disabled" type="button" style="font-size: 70px;line-height:80px;padding-left:0px;padding-right:0px;" id="btn-sendverifycode">发验证码</button>：<input class="input-large search-query" type="text" style="height: 80px;font-size: 70px;" id="verifyCode"/><span id="verifycodenotice" style="color:yellow;font-size:40px;display:none;">(<span id="count">60</span>秒后再次发送)</span>
				<input type="hidden" value="" id="confirmVerifyCode"/>
            </p>
            <p style="margin-top: 50px;">
				<button class="btn btn-block btn-success btn-large disabled" type="button" style="height: 100px;font-size: 80px;" id="btn-vote">投&nbsp;&nbsp;&nbsp;&nbsp;票</button>
			</p>
			<p id="notice-msg" style="text-align:center;color:yellow;font-size: 50px;line-height:80px;">&nbsp;</p>
		</div>
	</div>
</div>
</body>
</html>

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

	<script type="text/javascript" src="${contextPath }/js/jquery-1.11.1.js"></script>
	<script type="text/javascript" src="${contextPath }/js/bootstrap-3.3.4-dist/js/bootstrap.js"></script>
	<script type="text/javascript" src="${contextPath }/js/common.js"></script>
	<script type="text/javascript" src="${contextPath }/js/vote.js"></script>
</head>

<body>
	
	<div class="container-fluid" style="margin-top:10%;">
	<div class="row-fluid">
		<div class="span4">
			&nbsp;
		</div>
		<div class="span4">
			<h1 class="text-center">
				网络投票页面
				<!-- 项目编号 -->
				<input type="hidden" value="${project.projectno }" id="projectno"/>
			</h1>
			<br/>
			<h3 contenteditable="false">${project.name }</h3>
            <p contenteditable="false">项目名称：${project.name }</p>
            <p contenteditable="false">项目编号：${project.projectno }</p>
            <p contenteditable="false">项目编号：<input class="input-medium search-query" type="text" id="phone"/>
            </p>
            <p contenteditable="false">
            	<button class="btn btn-info disabled" type="button" id="btn-sendverifycode" style="padding-left:0px;padding-right:0px;font-family:Microsoft YaHei, Arial;font-size:16px;">发验证码</button>：<input class="input-small search-query" type="text" id="verifyCode"/><span id="verifycodenotice" style="color:yellow;font-size:13px;display:none;">(<span id="count">60</span>秒后再次发送)</span>
				<!-- 验证码值域 -->
				<input type="hidden" value="" id="confirmVerifyCode"/>
            </p>
			<form class="form-search">
				
				
				
				
			</form> 
			
			<button class="btn btn-block btn-success btn-large disabled" type="button" id="btn-vote">投&nbsp;&nbsp;&nbsp;&nbsp;票</button>
			<p id="notice-msg" style="text-align:center;color:yellow;">&nbsp;</p>
		</div>
		<div class="span4">
			&nbsp;
		</div>
	</div>
</div>
</body>
</html>

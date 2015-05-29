<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html>
<head>

	<title>为【 ${project.name }】项目投票</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="keywords" content="投票" />
	<meta name="description" content="网络,投票" />
	
	<link type="text/css" rel="stylesheet" href="${contextPath }/css/public.css"/>
	<link type="text/css" rel="stylesheet" href="${contextPath }/css/index.css"/>

	<script type="text/javascript" src="${contextPath }/js/jquery-1.11.1.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>

<body>
	
	<!-- 整个页面div begin -->
	<div class="main-body">
		<!-- 顶部标题 -->
		<div class="title">扫描二维码进行投票</div>
		
		<!-- 中部二维码图片 -->
		<div class="erweima" style="background-color:white;">
			<img id="qrimage" style="width: 280px;" src="${contextPath }/upload/voting2dimensioncode.png?${timestamp}" />
		</div>
		
		<!-- 底部直接跳转链接 -->
		<div class="directlink">
			<div class="directlink-left"></div>
			<div class="directlink-middle">
				<p style="margin: 10px;line-height:28px;"><a href="${contextPath }/web/vote/${project.projectno}">点击此处直接进入投票页面</a></p>
			</div>
			<div class="directlink-right"></div>
		</div>
	
	</div>
	<!-- 整个页面div end -->
</body>
</html>

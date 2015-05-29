<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html>
<head>

	<title>~~~模拟项目列表页面~~~</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="keywords" content="投票" />
	<meta name="description" content="网络,投票" />
	
	<link type="text/css" rel="stylesheet" href="${contextPath }/css/public.css"/>

	<script type="text/javascript" src="${contextPath }/js/jquery-1.11.1.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>

<body>
	
	<!-- 整个页面div begin -->
	<div class="main-body">
		<c:forEach items="${projectList }" var="project">
			<p><a href="${contextPath }/web/scan2dimensioncode/${project.projectno}">${project.name }</a></p>
		</c:forEach>	
	</div>
	<!-- 整个页面div end -->
</body>
</html>

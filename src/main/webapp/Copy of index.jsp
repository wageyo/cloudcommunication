<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
		*{
			font-family:Microsoft YaHei, Arial;
		}
	</style>
	<script type="text/javascript" src="js/jquery-1.11.1.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			var rootPath = getRootPath();
			//初始化项目路径
			$('#actionUrl').html(rootPath);
			
			//
			$('#requestUrl').keyup(function(){
				var behiend = $(this).val();
				var action = rootPath + behiend;
				$('#actionUrl').html(action);
			});
			
			$('#btnTest').click(function(){
				
			});
		});
		
	</script>
</head>
  
  <body>
    This is my JSP page. <br>
    
    <form action="" method="POST">
    	<table border="0">
    		<tr>
    			<td>
    				action路径 : 
    			</td>
    			<td>
    				<span id="actionUrl"></span>
    			</td>
    		</tr>
    		<tr>
    			<td>
    				请求地址 :
    			</td>
    			<td>
    				 <input type="text" id="requestUrl" />
    			</td>
    		</tr>
    		<tr>
    			<td>
    				<input type="button" value="开始测试" id="btnTest" />
    			</td>
    			<td>
    			
    			</td>
    		</tr>
    	</table>
    	<br/>
    	<br/>
    	
    </form>
    
  </body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <dl onmouseout="hideremove()" onmouseover="hideover('cy_color_dl')" class="xian_1" id="cy_color_dl"><dt><a title="白底黑字" class="toolbar_highContrastWhiteBlack" id="toolbar_highContrastWhiteBlack" href="javascript:void(0);"><img src="toolbar/img/choose.png"></a></dt><dd style="background-color:#fff;"><a title="白底黑字" style="color:#000;" class="ESDAssetsTextCon toolbar_highContrastWhiteBlack" href="javascript:void(0);">白底黑字</a></dd><dt><a title="蓝底黄字" class="toolbar_highContrastBlueYellow" id="toolbar_highContrastBlueYellow" href="javascript:void(0);"><img src="toolbar/img/choose.png"></a></dt><dd style="background-color:#0000ff;"><a title="蓝底黄字" style="color:#ffff00;" class="ESDAssetsTextCon toolbar_highContrastBlueYellow" href="javascript:void(0);">蓝底黄字</a></dd><dt><a title="黄底黑字" class="toolbar_highContrastYellowBlack" id="toolbar_highContrastYellowBlack" href="javascript:void(0);"><img src="toolbar/img/choose.png"></a></dt><dd style="background-color:#fefecc;"><a title="黄底黑字" style="color:#000;" class="ESDAssetsTextCon toolbar_highContrastYellowBlack" href="javascript:void(0);">黄底黑字</a></dd><dt><a title="黑底黄字" class="toolbar_highContrastYellow" id="toolbar_highContrastYellow" href="javascript:void(0);"><img src="toolbar/img/choose.png"></a></dt><dd style="background-color:#000;"><a title="黑底黄字" style="color:#ffff00;" class="ESDAssetsTextCon toolbar_highContrastYellow" href="javascript:void(0);">黑底黄字</a></dd><dt><a title="原始配色" class="toolbar_highContrastDefalt" id="toolbar_highContrastDefalt" href="javascript:void(0);"><img style="display: block;" src="toolbar/img/choose.png"></a></dt><dd style="background-color:#000;"><a title="原始配色" style="color:#fff;" class="ESDAssetsTextCon toolbar_highContrastDefalt" href="javascript:void(0);">原始配色</a></dd></dl>
  </body>
</html>

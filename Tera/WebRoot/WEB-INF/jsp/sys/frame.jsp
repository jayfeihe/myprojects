<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>"/>
<title>P2P综合业务系统</title>
<link href="css/global.css" type="text/css" rel="stylesheet"/>



<style type="text/css">
<!--
body{
	background-color:#f8ffd3;
	margin:0px; padding:0px;
}
-->
</style>
</head>
<frameset rows="55,*" cols="*" framespacing="0" frameborder="no" id="topframeset">
	<frame id="topFrame" src="top.do" name="topFrame" scrolling="no" noresize>
	<frameset rows="*" cols="210,*" frameborder="no" id="workframeset">
			<frame id="leftFrame" src="left.do" name="leftFrame" noresize>
			<frame id="mainFrame" src="main.do" name="mainFrame" noresize >
	</frameset>	
</frameset>
<noframes>
		<body>
			<p>
				此网页使用了框架，但您的浏览器不支持框架。
			</p>
		</body>
</noframes>
</html>


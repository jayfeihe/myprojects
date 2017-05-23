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
<body>

<div id="footer">
	<p>更新时间: 2007-06-24 23:17:07 &copy;All Rights Reserved </p>
</div>

</body>
</html>

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
<title>信息提示</title>
<link href="css/global.css" type="text/css" rel="stylesheet"/>
<style type="text/css">
<!--
body{
	background-color:#f8ffd3;
	margin:0px; padding:0px;
}
#messageBottom{
	text-align: right;
}
-->
</style>
</head>
<body>
	<div id="main">

	<div id="part1" class="part">

	
		<div class="doc" id="doc2">
				
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<img src="img/sign_alert.gif"/>${messageData}	
		</div>
	</div>
	
	</body>
</html>


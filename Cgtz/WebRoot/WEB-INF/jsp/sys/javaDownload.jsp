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
<title>jdk download</title>
<link href="css/global.css" type="text/css" rel="stylesheet"/>
<link href="css/queryPage.css" type="text/css" rel="stylesheet"/>
<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.validate.js" type="text/javascript"></script>
	<script src="js/jquery.validate.ms.js" type="text/javascript"></script>
<script src="js/artDialog/artDialog.js?skin=opera"></script>
<script src="js/artDialog/plugins/iframeTools.source.js"></script>
</head>
	<body>
	<div id="main">

	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">JDK6 JRE6 下载 </a></p>
		
		<div id="queryForm">
			<div id="queryFormContent">
			访问规则管理“RMA”(规则维护应用程序)需要jdk6.21的支持，请根据本地操作系统的情况下载安装：<br/><br/>
			
			
			
			<table id="table1" class="datatable" style="width: 500px" >
				<tr>
					<th scope="col">windows平台</th>
					<th scope="col">下载</th>
				</tr>
				
				<tr><td>32位</td><td><a href="data/jdk-6u21-windows-i586.exe">jdk-6u21-windows-i586.exe</a></td></tr>
			<tr><td>64位</td><td><a href="data/jdk-6u21-windows-x64.exe">jdk-6u21-windows-x64.exe</a></td></tr>
			<tr><td>32位</td><td><a href="data/jre-6u21-windows-i586.exe">jre-6u21-windows-i586.exe</a></td></tr>
			<tr><td>64位</td><td><a href="data/jre-6u21-windows-x64.exe">jre-6u21-windows-x64.exe</a></td></tr>
			</table>
			<br/>
			</div>
		</div>
		
	</div>



</div>
	</body>
</html>


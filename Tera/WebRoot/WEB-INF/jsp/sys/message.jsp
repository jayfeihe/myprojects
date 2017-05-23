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
<script src="js/jquery.min.js" type="text/javascript"></script>
<style type="text/css">
<!--
body{
	background-color: #FFFFFF;
	margin:0px; padding:0px;
}
-->
</style>
</head>
<body>
<div id="main">

		<div class="part" id="part1" >
			<p class="title">系统消息</p>			
			<div class="content">
				<table>
					<tr>
						<td> <img src="img/sign_alert.gif"/> </td>
						<td> ${message} </td>
					</tr>
				</table>
			</div>
		</div>
		
	</div>

</div>
</body>
</html>

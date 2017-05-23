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
-->
</style>
<script language="javascript">
function closeMe()
{
	window.opener.location.reload();
	window.close();
}
</script>
</head>
	<body>
	<div id="main">

	<div id="part1" class="part">
		<p class="title"><a href="#">数据添加信息</a></p>
		<div class="doc" id="doc2">
				<table class="formtable" width="100%" border="1">
				<thead><tr><td></td></tr></thead>
				<tbody>
				<tr><td><br/><br/><br/><br/></td></tr>
				<tr>
				
				<td valign="middle">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="img/sign_alert.gif"/>数据添加成功！				
				
				
				</td></tr>
				<tr><td><br/><br/><br/><br/></td></tr>
				</tbody>
				<input value="关闭窗口" type="button" onclick="javascript:closeMe();"/></td></tr></tfoot>
				</table>
		</div>
	</div>
	</body>
</html>


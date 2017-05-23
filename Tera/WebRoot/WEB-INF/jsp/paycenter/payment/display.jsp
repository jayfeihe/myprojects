<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />
		<title>支付明细表查看</title>
		<link href="css/global.css" type="text/css" rel="stylesheet" />
		<link href="css/icon.css" type="text/css" rel="stylesheet" />
		<link href="css/default/easyui.css" type="text/css" rel="stylesheet" />
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
<style type="text/css">

</style>
	</head>
	<body>
	<!-- top -->
<%@include file="../header.jsp" %>
		<div id="main">
			<div id="part1" class="part">
				<p class="title">
					<a href="javascript:void(0);">支付信息</a>
				</p>
				<div class="content">
					<table class="datatable">	
							<tr>
								<td>订单:</td>
								<td>${payFbean.orderNo}</td>
							</tr>
							<tr>
								<td>金额：</td>
								<td>
								<fmt:formatNumber value="${payFbean.payAmount/100}" type="currency"/> 元</td>
							</tr>
							<tr>
								<td>消息：</td>
								<td>${msg}</td>
							</tr>
							<tr>
								<td colspan="2"><input type="button" value="关闭窗口" class="btn" onclick="javascript:window.close();"/></td>
							</tr>
					</table>
				</div>
			</div>
		</div>
	</body>
<script type="text/javascript">
//返回
//页面加载完动作
$(document).ready(function() {

});
</script>
</html>

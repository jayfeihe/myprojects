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
	<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
<title>P2P综合业务系统</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
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
			<p class="title">异常消息</p>			
			<div class="content">
				<table border=0 cellpadding=0 cellspacing=0  width="100%" align="center">
					<tr>
						<td valign="top" height="100%">
							<table width="100%" border="0" cellspacing="1" cellpadding="2">
								<tr>
									<td width="10%"> &nbsp; </td>
									<td width="80%"> &nbsp; </td>
									<td width="10%"> &nbsp; </td>
								</tr>
								<tr>
									<td> &nbsp; </td>
									<td>
										<table width="0%" border="0" cellspacing="0" cellpadding="2">
											<tr>
												<td> <img src="img/sign_alert.gif"/> </td>
												<td style="color:#505050"> <b>操作出现异常，请联系系统管理员！</b></td>
											</tr>
										</table>
										<hr/>
									</td>
									<td> &nbsp;</td>
								</tr>
								<tr>
									<td> &nbsp; </td>
		
									<td>详细信息:
										<font color="#FF0000">
											<c:set value="${exception}" var="ee" /> 
											<jsp:useBean id="ee" type="java.lang.Exception" /> <%=ee.getMessage()%> 
										</font>
									</td>
									<td> &nbsp; </td>
								</tr>
								<tr>
									<td> &nbsp; </td>
									<td>
										<table id="errdiv" style="display: none;" align="center">
											<tr>
												<td> <% ee.printStackTrace(new java.io.PrintWriter(out)); %> </td>
											</tr>
										</table>
										<input type="hidden" id="isHidde" value="true" />
										<input type="button" id="showbtn" value="显示详细信息" class="btn" onclick="showErr();" />
									</td>
									<td> &nbsp; </td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
		
	</div>

</div>
<script type="text/javascript">
function showErr() {
	var isHidde = $("#isHidde").val();
	if( isHidde == "true" ) {
		$("#errdiv").show();
		$("#isHidde").val('false');
		$("#showbtn").val("隐藏详细信息");
	} else {
		$("#errdiv").hide();
		$("#isHidde").val('true');
		$("#showbtn").val("显示详细信息");
	}
}
</script>
</body>
</html>

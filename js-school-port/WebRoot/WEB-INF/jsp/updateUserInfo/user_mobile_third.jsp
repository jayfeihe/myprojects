<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>	
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
<meta http-equiv="expires" content="0">
<link rel="icon" href="images/favicon.ico" type="image/x-icon">
<link rel="stylesheet" href="css/base.css">
<link rel="stylesheet" href="css/input.css">
<link rel="stylesheet" href="css/validator.css">
<link rel="stylesheet" href="css/user.css">
<title>秒趣分期-修改绑定手机</title>
</head>
<body class="body-no-bg">
<!--header-->
<jsp:include page="${basePath}/jsp/frame/header.jsp"></jsp:include>
<!--/header-->

<!--content-->
<div class="user-main">
	<h3 class="user-title user-common-con">绑定手机</h3>
	<div class="user-modify-bar">
		<div class="user-bar-step user-bar-step3"></div>
		<div class="user-bar-text clearfix">
			<div class="bar-text-item">验证身份</div>
			<div class="bar-text-item">绑定新手机</div>
			<div class="bar-text-item">绑定成功</div>
		</div>
	</div>
	<div class="user-main">
	<div class="user-result clearfix" style="padding-bottom: 130px;">
		<div class="user-remind clearfix">
			<div class="clearfix" style="text-align:center;"><i class="user-icon user-success-icon" style="vertical-align:middle;"></i>
			 <span class="user-success-remind">恭喜您，成功绑定新手机号！</span></div>
		</div>
		<div class="clearfix mt10" style="text-align:center;">新手机号：${mobileNumberShow}</div>
		<div class="user-remind clearfix mt20" style="text-align:center;"><button type="button" class="user-btn user-btn-red" id="close_tabs">关闭并返回</button></div>
	</div>

</div>
	<div class="user-result-notice  user-common-con">
		<p class="user-result-p">
			<i class="user-icon user-p-icon"></i>绑定手机后，可用手机号直接登录。绑定手机是保护您的账户和资金安全的重要手段。
		</p>
		<p class="user-result-p">
			<i class="user-icon user-p-icon"></i>绑定手机后，还款信息相关通知短信将发送到新的手机号码！
		</p>
		<p class="user-result-p">
				<i class="user-icon user-p-icon"></i>如您原手机丢失或者无法接受短信校验码，<!-- 请拨打客服热线进行人工帮助！ --> 请发送邮件给秒趣客服获取帮助。
		</p>
	</div>
	
	<!-- <div class="footer-info clearfix user-common-con">
		<span class="footer-customer1 ml20">客服热线：<span class="footer-info-red">400-</span><span class="footer-info-blue">00-000</span></span>
		<span class="footer-workttime">工作时间：<span class="footer-info-red">10:00</span><span class="footer-info-blue">- 18:00</span></span>
	</div> -->

</div>
<!-- footer start-->
<jsp:include page="${basePath}/jsp/frame/footer.jsp"></jsp:include>
<!-- footer end-->
<script src="js/jquery-1.10.2.min.js"></script>
<script>
$(function(){
	//关闭并返回
	$("#close_tabs").click(function(){
		 window.location.href="<%=basePath%>updateUserInfo/viewInfo.jhtml";
	});
});
</script>
</body>
</html>

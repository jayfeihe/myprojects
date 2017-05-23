<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String path = request.getContextPath();
	String basepath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
<meta http-equiv="expires" content="0"> 
 
<link rel="icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/input.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/validator.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/user.css">
<title>秒趣分期-欢迎登录</title>
<script type="text/javascript">
	var contextPath = '${pageContext.request.contextPath}';

</script>
<script src="${pageContext.request.contextPath}/js/md5.js"></script>
<script src="${pageContext.request.contextPath}/js/common.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-1.10.2.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.validator.js"></script>
<script type="text/javascript">
// 兼容FF 
document.onkeydown=keyListener; 
function keyListener(e){ 
e = e ? e : event; 
if(e.keyCode == 13){ 
	//调用登录按钮的登录事件
	  $("button[id^='submit_form']:visible").click();
} 
} 
</script>
<script src="${pageContext.request.contextPath}/js/login/user_login.js"></script>
</head>
<style>
	.header{
		margin-bottom: 0;
	}
	.footer{
		margin-top: 0;
	}
</style>
<body class="body-no-bg"   >
<!--header-->
<div class="header">
  <div class="header-wrap">
    <div class="logo"> <a href="javascript:void(0)" onclick="goLocationHistory('login')"><img  src="<%=basepath %>images/logo.png" alt="秒趣"  /></a> </div>
     <div class="logo-text"> 轻松分期，畅想生活 </div>
  </div>
</div>
<!--/header-->
<!--main start-->
<div class="login-main">
	<div class="login-wrap clearfix">
		<div class="login-left">
			<a href="javascript:void(0);"><img src="<%=basepath %>images/login-ad.jpg" alt="奔跑吧，欢乐go"/></a>
		</div>
		<div class="login-right" style="position: relative; margin-top: 35px;"> <!-- 配合电信广告修改此处style 2015.12.23 -->
			<div class="login-right-in">
				<ul class="login-way-tab" id="login_way_tab">
					<li class="current" id="loginsm">手机登录</li>
					<li id="loginpwd">普通登录</li>
				</ul>
				<div class="login-form">
					<input type="hidden" id="redirectUrl" name="redirectUrl" value="${redirectUrl}"/>
					<form id="login_form0">
					<div class="login-input-box">
						<i class="user-icon user-mobile-icon"></i>
						<input type="text" autocomplete="off" class="login-input" placeholder="请输入绑定的手机号" name="mobile" id="mobile_0" onkeyup="this.value=Trim(this.value);" onafterpaste="this.value=Trim(this.value);"/>
					</div>
					<div class="login-msg-box">
						<span class="msg-box" for="mobile_0"></span>
					</div>
					<div class="login-code-box clearfix" id="login_code0">
						<div class="login-input-box small">
							<input type="text"  autocomplete="off" class="login-input" name="validationCode0" id="validationCode0" placeholder="验证码"/>
						</div>
						<img src="<%=basepath %>images/user-code.jpg" class="user-code-img fl ml10" id="img_validationCode0">
						<a href="javascript:void(0)" class="red-link" id="a_validationCode0">换一张</a>
					</div>
					<div class="login-msg-box">
						<span class="msg-box" for="validationCode0"></span>
					</div>
					<div class="login-code-box clearfix mobile-psw-box password-box">
						<div class="login-input-box small">
							<i class="user-icon user-psw-icon"></i><input type="text"  autocomplete="off" class="login-input"  placeholder="密码" name="password" id="password_0"/>
						</div>
						<button class="user-btn user-btn-default" type="button" id="login_get_code">发送动态密码</button>
					</div>
					<div class="login-msg-box">
						<span class="msg-box" for="password_0"></span>
					</div>
					<div class="login-msg-box hide" id="dev_login_msg_error_0">
						<div class="login-msg-error"><i class="user-icon loan-haspassed-icon ml5" style="margin-right: 5px;"></i><label id="label_login_msg_error_0"></label></div>
					</div>
					<div class="login-btn-area">
						<button type="button" class="user-btn user-btn-red"  id="submit_form0">登录</button>
					</div>
					</form>
					<form id="login_form1" class="hide">
					<div class="login-input-box">
						<i class="user-icon user-mobile-icon"></i>
						<input type="text" autocomplete="off" class="login-input" placeholder="请输入绑定的手机号" name="mobile" id="mobile_1" onkeyup="this.value=Trim(this.value);" onafterpaste="this.value=Trim(this.value);"/>
					</div>
					<div class="login-msg-box">
						<span class="msg-box" for="mobile_1"></span>
					</div>
					<div class="login-input-box  password-box">
						<i class="user-icon user-psw-icon"></i>
						<input type="password" class="login-input" placeholder="密码" name="password" id="password_1"/>
					</div>
					<div class="login-msg-box">
						<span class="msg-box" for="password_1"></span>
					</div>
					<div class="login-code-box clearfix hide" id="login_code1">
						<div class="login-input-box small">
							<input type="text" autocomplete="off" class="login-input" name="validationCode" id="validationCode"/>
						</div>
						<img src="<%=basepath %>images/user-code.jpg" class="user-code-img fl ml10" id="img_validationCode">
						<a href="javascript:void(0)" class="red-link" id="a_validationCode">换一张</a>
					</div>
					<div class="login-msg-box hide" id="login_code1_msg_box">
						<span class="msg-box" for="validationCode"></span>
					</div>
					<div class="login-msg-box hide" id="dev_login_msg_error_1">
						<div class="login-msg-error"><i class="user-icon loan-haspassed-icon ml5" style="margin-right: 5px;"></i><label id="label_login_msg_error_1"></label></div>
					</div>
					<div class="login-btn-area">
						<button type="button" class="user-btn user-btn-red"  id="submit_form1">登录</button>
					</div>
					</form>
					<div class="login-foget-psw"><a href="javascript:void(0)" id="forget_psw_btn">忘记密码？</a></div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--main end-->
<!--忘记密码弹框start-->
 <div class="hide"  id="forget_psw_dialog">
	 <div class="pay-select-mask"></div>
	 <div class="pay-select-bank" style="width:800px;margin-left:-400px;">
		<div class="dialog-title">
			<span class="dialog-title-text">忘记密码</span>
			<button type="button" class="dialog-close" id="close_forget_dialog_r"></button>
		</div>
		<div class="forget-psw-con">
			<div class="forget-psw-half fl ml20">
				<div class="forget-psw-icon"><i class="user-icon user-icon-tel"></i></div>
				<div class="mt20">客服电话</div>
				<div class="mt10 f24">400-091-8877</div>
				<div class="grey-color mt10">工作时间：10:00-17:00</div>
			</div>
			<div class="forget-psw-half fl">
				<div class="forget-psw-icon"><i class="user-icon user-icon-mes mt10"></i></div>
				<div class="mt20">客服邮箱</div>
				<div class="mt10 f24">service@sunseetech.com</div>
				<div class="grey-color mt10" style="text-align: left;line-height:25px;">发送资料：真实姓名、身份证图片、原绑定手机号、新联系电话。请保证上述信息均填写正确，身份证图片清晰，我们的客服专员会在收到您邮件的24小时内为您处理。</div>
			</div>
		</div>

	  </div>
</div> 

<!--
<div class="hide"  id="forget_psw_dialog">
	 <div class="pay-select-mask"></div>
	 <div class="pay-select-bank" style="width:750px;margin-left:-375px;">
		<div class="dialog-title">
			<span class="dialog-title-text">忘记密码</span>
			<button type="button" class="dialog-close" id="close_forget_dialog_r"></button>
		</div>
		<div class="forget-psw-con">
		 	<div class="forget-psw-half fl" style="width: 50%;padding: 40px 0px">
				<div class="forget-psw-icon"><i class="user-icon user-icon-mes mt10"></i></div>
				<div class="mt20 f24">客服电话</div>
				<div class="mt10 f24">4000918877</div>
				<div class="grey-color mt10" style="text-align: center;line-height:25px;padding-left: 40px;width: 300px">
				 <p style="font-size: 15px;">工作时间：10:00-17:00</p></div>
			</div>
			<div class="forget-psw-half fl" style="width: 50%;padding: 40px 0px">
				<div class="forget-psw-icon"><i class="user-icon user-icon-mes mt10"></i></div>
				<div class="mt20 f24">客服邮箱</div>
				<div class="mt10 f24">service@sunseetech.com</div>
				<div class="grey-color mt10" style="text-align: left;line-height:25px;padding-left: 40px;width: 300px">
				 <p style="font-size: 15px;">发送资料：真实姓名、身份证图片、原绑定手机号、新联系电话。请保证上述信息均填写正确，身份证图片清晰，我们的客服专员会在收到您邮件的24小时内为您处理。</p></div>
			</div>
		</div>

	  </div>
</div>
-->
<!--忘记密码弹框end-->
<!-- footer start-->
<jsp:include page="${basePath}/jsp/frame/footer.jsp"></jsp:include>
<!-- footer end-->
</body>
</html>


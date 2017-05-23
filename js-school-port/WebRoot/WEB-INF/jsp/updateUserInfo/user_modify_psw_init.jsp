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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/md5.js"></script>
<title>秒趣分期-修改密码</title>
<script type="text/javascript">
	var contextPath = '${pageContext.request.contextPath}';
</script>
</head>
<body class="body-no-bg">
<!--header-->
<jsp:include page="${basePath}/jsp/frame/header.jsp"></jsp:include>
<!--/header-->

<!--content-->
<div class="user-main">
	<h3 class="user-title user-common-con">修改密码</h3>
	<form id="user_psw_form" action="<%=basePath%>updateUserInfo/updateInitPwdByPwd.jhtml">
	<input type="hidden" value="" id="mobileNumberSend"/>
	<input type="hidden" value="" id="smCodeId"/>
	<div class="pay-input-item clearfix mt40">
		<div class="input-item-text pwd-init-tip">重要提示：修改初始密码以使您的账户更加安全</div>
	</div>
	<div class="pay-input-item clearfix">
	  <div class="input-item-left">旧密码<em>*</em></div>
	  <div class="input-item-right">
		<input type="password" class="input-default fl" name="oldpsw" id="oldpsw"/>
		<span class="input-item-text grey-color ml10 hide">请输入当前登录密码</span>
	  </div>
	</div>
	<div class="pay-input-item clearfix">
	  <div class="input-item-left">新密码<em>*</em></div>
	  <div class="input-item-right">
		<input type="password" class="input-default fl" name="newpsw" id="newPwd1"/>
		<span class="input-item-text grey-color ml10 hide">密码由8~16位字符组成，必须包含大、小写字母、数字、特殊字符！</span>
	  </div>
	</div>
	<div class="pay-input-item clearfix">
	  <div class="input-item-left">重复新密码<em>*</em></div>
	  <div class="input-item-right">
		<input type="password" class="input-default fl" name="againNewpsw" id="newPwd2"/>
		<span class="input-item-text grey-color ml10 hide">再次输入密码，两次密码需保持一致</span>
	  </div>
	</div>
	<div class="pay-input-item clearfix">
	  <div class="input-item-left">校验码<em>*</em></div>
	  <div class="input-item-right">
		<input type="text" autocomplete="off" class="input-default input-short fl" name="code" id="codeId"/>
		<img src="" class="user-code-img fl " id="validationCode"/>
		<span class="input-item-text ml10"><a href="javascript:void(0)" class="red-link" onclick="loadValidationCode()">换一张</a></span>
		<span class="msg-box" for="codeId"></span>
		<span class="input-item-text grey-color ml10 hide">请输入图片中的字符，不区分大小写</span>
	  </div>
	</div>
	<div class="pay-input-item clearfix">
	  <div class="input-item-left">&nbsp;</div>
	  <div class="input-item-right">
		<button type="button" class="user-btn user-btn-red" id="submit-psw">提交</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<span class="error-info"></span>
	  </div>
	</div>
	</form>
</div>
<!-- footer start-->
<jsp:include page="${basePath}/jsp/frame/footer.jsp"></jsp:include>
<!-- footer end-->
<script src="js/jquery-1.10.2.min.js"></script>
<script src="js/jquery.validator.js"></script>
<script src="js/common.js"></script>
<script src="js/updateInfo/user_modify_psw.js"></script>
<script src="js/des.js"></script>

</body>
</html>

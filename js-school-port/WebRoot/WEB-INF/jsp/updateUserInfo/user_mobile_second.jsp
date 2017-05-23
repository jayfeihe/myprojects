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
	<h3 class="user-title user-common-con">绑定手机</h3>
	<form id="mobile_second_form"  action="<%=basePath%>updateUserInfo/updateMobileStepTwoSub.jhtml">
	<div class="user-modify-bar">
		<div class="user-bar-step user-bar-step2"></div>
		<div class="user-bar-text clearfix">
			<div class="bar-text-item">验证身份</div>
			<div class="bar-text-item">绑定新手机</div>
			<div class="bar-text-item">绑定成功</div>
		</div>
	</div>
	<div class="pay-input-item clearfix">
	  <div class="input-item-left">已绑定手机</div>
	  <div class="input-item-right">
		<span class="input-item-text">${mobileNumberShow}</span>
		<input type="hidden" value="${mobileNumberHide}" id="mobileNumberHide"/>
		<input type="hidden" value="${mobileNumberHide}" id="mobileNumberSend"/>
		<input type="hidden" value="Step2" id="currPage"/>
	  </div>
	</div>
	<div class="pay-input-item clearfix">
	  <div class="input-item-left">新绑定手机号<em>*</em></div>
	  <div class="input-item-right">
		<input type="text" autocomplete="off" class="input-default" name="newmobile" placeholder="请输入11位手机号" id="newmobile" onkeyup="this.value=Trim(this.value);" onafterpaste="this.value=Trim(this.value);" onblur="getMobNum()"/>
		
		<span class="msg-box" for="newmobile"></span>
	  </div>
	</div>
	<div class="pay-input-item clearfix">
	  <div class="input-item-left">验证码<em>*</em></div>
	  <div class="input-item-right">
		<input type="text" autocomplete="off" class="input-default input-short fl" name="newcode" id="smCodeId"/>
		<button type="button" class="user-btn ml10"  id="get_code">获取验证码</button>
		<span class="msg-box" for="smCodeId"></span>
	  </div>
	</div>
	<div class="pay-input-item clearfix">
	  <div class="input-item-left">&nbsp;</div>
	  <div class="input-item-right">
		<button type="button" class="user-btn user-btn-red" id="submit_second">提交新绑定手机</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="user-btn user-btn-red" id="back-btn" onclick="goLocationHistory('account')">返回</button>
		<span class="error-info"></span>
	  </div>
	</div>
</form>
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
	<input type="hidden" id="tokenId"  value="${tokenId}">
	<!-- 
	<div class="footer-info clearfix user-common-con">
		<span class="footer-customer1 ml20">客服热线：<span class="footer-info-red">400-</span><span class="footer-info-blue">00-000</span></span>
		<span class="footer-workttime">工作时间：<span class="footer-info-red">10:00</span><span class="footer-info-blue">- 18:00</span></span>
	</div> -->

</div>
 
<!-- footer start-->
<jsp:include page="${basePath}/jsp/frame/footer.jsp"></jsp:include>
<!-- footer end-->
<script src="js/jquery-1.10.2.min.js"></script>
<script src="js/common.js"></script>
<script src="js/jquery.validator.js"></script>
<script src="js/updateInfo/user_mobile.js"></script>
<script type="text/javascript">

function getMobNum(){
	$("#mobileNumberSend").val($("#newmobile").val());
}
	
</script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>

<head>
<base href="<%=basePath%>" />
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
<link rel="stylesheet" href="css/wx-pay.css">
<title>秒趣分期-收银台</title>
<script type="text/javascript">
	var contextPath = '${pageContext.request.contextPath}';
</script>
</head>
<body class="body-no-bg">
	<!--header-->
	<jsp:include page="${basePath}/jsp/frame/header.jsp"></jsp:include>
	<!--/header-->
	<div class="content" style="width:1000px;">
		<div style="margin-left:10px;">
			<span class="pay-font">还款申请提交成功，快快支付吧~</span>
		</div>
		<div style="margin-left:10px;">
			<span class="pay-font">待还款<span style="color:red;" name="orderMoney">${payAmount}</span>元！</span>
		</div>
		
		<div class="payment">
			<!-- 微信支付 -->
			<div class="pay-weixin">
				<div class="p-w-hd">微信支付</div>
				<div class="p-w-bd" style="position:relative;margin:auto auto;">
					<div class="p-w-box" style="margin-top:50px;">
						<div class="pw-box-hd" style="border:0;">
							<img id="weixinPayFalse" src="images/cashier/fail.png" width="298" height="298">
						</div>
					</div>
					<div class="p-w-s-sidebar" style="margin-top: 100px;">
						<p style="font-size: 50px;text-align: left;color:red;">还款单已过期！</p>
						<p style="font-size: 25px;font-weight: 600;margin-top: 30px;">按时还款，提高信用资质！</p>
						<input type="button" onclick="repay();"
							style="font-size:20px;margin-top: 20px;background-color:red;color:#fff;width:220px;height:50px;border:none;margin-left:30px;cursor: pointer;" 
							value="重新还款"/>
					</div>
					<div style="clear:both;"></div>
				</div>
			</div>
		</div>
	</div>
	<!--/content-->

	<!-- footer start-->
	<jsp:include page="${basePath}/jsp/frame/footer.jsp"></jsp:include>
	
	<script type="text/javascript">
		function repay() {
			window.location = "<%=basePath%>instalmentManage/instalmentBill.jhtml";
		}
	</script>
</body>
</html>

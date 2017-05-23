<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
<link rel="stylesheet" href="css/cashier.css">
<title>秒趣分期-收银台</title>
<script type="text/javascript">
	var contextPath = '${pageContext.request.contextPath}';
</script>
</head>
<body class="body-no-bg">
<!--header-->
<jsp:include page="${basePath}/jsp/frame/header.jsp"></jsp:include>
<!--/header-->
<form action="" id="mainForm" method="post">
<input type="hidden" id="paymentInfoToken" name="paymentInfoToken" value="${paymentInfoToken}" />

<!--content-->

<div class="content">
	<div style="margin-left:60px;">
		<span class="pay-font">还款申请提交成功，快快支付吧~</span>
	</div>
	<div style="margin-left:60px;">
		<span class="pay-font">待还款<span style="color:red;" name="orderMoney">${payAmount}</span>元！</span>
	</div>
</div>

<div class="user-main">
<div class="user-common-con clearfix"></div>
	<div class="user-cashier clearfix mt30 pay-stages">
		<div id="cpcn">
			<input type="hidden" id="message" name="message" />
			<input type="hidden" id="signature" name="signature" />
			<span><input type="radio" name="payType" value="cpcn" checked="checked"/></span>
			<span style="font-size: 18px;">网银支付</span>
			<span id="cpcnPayAmount">${payAmount}元</span>
			
			<ul class="bank-list">
				<c:forEach begin="0" items="${bankList }" step="1" var="bank" varStatus="status">
					<c:if test="${status.count lt 12 }">
						<li class="is_show_1">
						  <div class="pay-stages-li"><img class="bank-logo" src="<%=basePath%>${bank.bankImgUrl}"/></div>
						  <i class="user-icon icon-selected"></i>
						  <input type="hidden" name="relationBankId" value="${bank.relationBankId }">
						</li>
					</c:if>
					<c:if test="${status.count eq 12 }">
						<li class="is_show_2">
							<div class="pay-stages-li" style="padding: 20px;" id="moreBank">
								更多银行
							</div>
						</li> 
					</c:if>
					<c:if test="${status.count ge 12 }">
						<li class="is_show_3" style="display: none;">
						  <div class="pay-stages-li"><img class="bank-logo" src="<%=basePath%>${bank.bankImgUrl}"/></div>
						  <i class="user-icon icon-selected"></i>
						  <input type="hidden" name="relationBankId" value="${bank.relationBankId }">
						</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
	</div>
	
	<div class="user-cashier wx-pay">
		<div id="wx">
			<input type="hidden" id="paymentOrderId" name="paymentOrderId" />
			<span><input type="radio" name="payType" value="wx"/></span>
			<img class="cashier-icon-img" alt="微信支付" src="images/cashier/wx-pay-icon.png"/>
			<span id="wxPayAmount" style="display: none;">${payAmount}元</span>
		</div>
	</div>
	
	<div class="mt20 user-common-con"  >
		<!--<button type="button" class="user-btn user-btn-red" id="btnSub" >跳转网银并支付</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		<button type="button" class="user-btn user-btn-red" id="back-btn" onclick="goLocationHistory('instalmentManage')">返回</button> -->
		<a href="javascript:void(0)" id="btnSub"><img class="cashier-icon-img pay-btn-img" alt="支付" src="images/cashier/pay-btn-icon.png"/></a>
		<span class="error-info"></span>
	</div>

</div>
<!--登录网上银行支付弹框start-->
<div class="dialog" id="dialog-pay">
	<div class="pay-select-mask"></div>
	<div class="dialog-content">
		<div class="dialog-title">
			<span class="dialog-title-text">登录网上银行支付</span>
			<button type="button" class="dialog-close" id="dialog_close_r"></button>
		</div>
		<div class="mt30 clearfix textc">请您在新打开的网上银行页面进行支付，支付完成前请不要关闭该窗口</div>
		<div class="mt40 clearfix textc">
			<button class="user-btn user-btn-default" type="button" id="finish_pay">已完成支付</button>
			<button class="user-btn user-btn-default ml10" type="button" id="error_pay"   >支付遇到问题</button>
		</div>
	</div>
</div>
<!--登录网上银行支付弹框end-->
<!--支付成功弹框start-->
<div class="dialog" id="dialog-pay-success">
	<div class="pay-select-mask"></div>
	<div class="dialog-content">
		<div class="textc user-remind clearfix mt80">
			<div class="clearfix"><i class="user-icon user-success-icon" style="vertical-align:middle;margin-top:-4px;"></i><span class="user-success-remind">恭喜您，已成功支付！</span></div>
		</div>
		<div class="textc f16">
			您还可以<a href="javascript:void(0)" onclick="goLocationHistory('instalmentManage');">查看账单状态</a>
		</div>
	</div>
</div>
<!--支付成功弹框end-->
<!--支付失败弹框start-->
<div class="dialog" id="dialog-pay-fail">
	<div class="pay-select-mask"></div>
	<div class="dialog-content">
		<div class="textc user-remind clearfix mt80">
			<div class="clearfix"><i class="user-icon user-fail-icon" style="vertical-align:middle;margin-top:-4px;"></i><span class="user-fail-remind ml10">订单支付未成功！</span></div>
		</div>
		<div class="textc mt30">
			<button type="button" class="user-btn user-btn-red" id="pay-fail-close">确定</button>
		</div>
	</div>
</div>
<!--支付失败弹框end-->
<!--/content-->
</form>
<!-- footer start-->
<jsp:include page="${basePath}/jsp/frame/footer.jsp"></jsp:include>
<script src="js/jquery-1.10.2.min.js"></script>
<script src="js/common.js"></script>
<script src="js/instalmentManage/cashier.js"></script>
</body>
<script type="text/javascript">
</script>
</html>

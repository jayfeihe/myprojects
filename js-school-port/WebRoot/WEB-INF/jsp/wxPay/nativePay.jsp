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
<title>秒趣分期-微信支付</title>
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
				<div class="p-w-bd" style="position:relative">
					<div class="j_weixinInfo font-red" style="position:absolute; top: -30px; left: 130px;display:none;">二维码已过期，<a href="javascript:void(0);">刷新</a>页面重新获取二维码。</div>
					<div class="j_weixinInfo" id="wx-clock-info" style="position:absolute; top: -30px; left: 130px;">距离二维码过期还剩<b style="color: #F00">60</b> 秒，过期后请点击重新获取二维码。</div>
					<div class="p-w-box">
						<div class="pw-box-hd">
							<img id="weixinImageURL" src="${pageContext.request.contextPath}/wxPay/writeQr.jhtml?code_url=${code_url }" width="298" height="298">
						</div>
						<div class="wx-pay-mask">
							<a class="a-btn" href="javascript:void(0)" onclick="getQrCode('${paymentOrderId }', '${paymentInfoToken }')">重新获取二维码</a>
						</div>
						<div class="pw-box-ft">
							<p>请使用微信扫一扫</p>
							<p>扫描二维码支付</p>
						</div>
					</div>
					<div class="p-w-sidebar"></div>
					<div style="clear:both;"></div>
				</div>
			</div>
			<!-- 微信支付 end -->
			<!-- payment-change 变更支付方式 -->
			<div class="payment-change">
				<a class="pc-wrap" id="reChooseUrl" href="javascript:void(0);" onclick="$('#otherPayForm').submit();">
					<i class="pc-w-arrow-left"><</i>
					<strong>选择其他支付方式</strong>
				</a>
				<form id="otherPayForm" method="post" action="<%=basePath%>instalmentManage/getBankList.jhtml">
					<input type="hidden" name="paymentInfoToken" value="${paymentInfoToken }"/>
				</form>
			</div>
		<!-- payment-change 变更支付方式 end -->
		</div>
	</div>
	
	<form id="failForm" method="post" action="<%=basePath%>wxPay/nativePayFail.jhtml">
		<input type="hidden" name="payAmount" value="${payAmount }"/>
	</form>
	<!--/content-->

	<!-- footer start-->
	<jsp:include page="${basePath}/jsp/frame/footer.jsp"></jsp:include>
	
	<script src="js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript">
		var time = 61, t;
		function backTime() {
			time--;
			$("#wx-clock-info").html("距离二维码过期还剩<b style=\"color: #F00\"> " + time + "</b> 秒，过期后请点击重新获取二维码。");
			t = setTimeout(function() {
				backTime()
			}, 1000);
			if (time <= 0) {
				clearTimeout(t);
				$(".wx-pay-mask").show();
				$("#wx-clock-info").html("<b style=\"color: #F00\">二维码已经失效，请重新获取</b>");
				time = 61;
			}
		}
		
		// 重新获取二维码
		function getQrCode(paymentOrderId,paymentInfoToken) {
			// 刷新订单号
			$.ajax({
				url:contextPath+"/"+"instalmentManage/refreshPaymentOrderNo.jhtml",
				type:"POST",
				data:{"paymentInfoToken":paymentInfoToken},
				success:function(data){
					if(data.code==0){
						$.ajax({
							url : contextPath + '/wxPay/getQR.jhtml',
							method : 'get',
							data : {'paymentOrderId': paymentOrderId, 'paymentInfoToken':paymentInfoToken},
							success : function(data) {
								if (data.code == '0') {
									var code_url = data.code_url;
									var paymentOrderId = data.paymentOrderId;
									$(".wx-pay-mask").hide();
									$("#weixinImageURL").attr('src',contextPath + '/wxPay/writeQr.jhtml?code_url=' + code_url);
									$("#wx-clock-info").html("距离二维码过期还剩<b style=\"color: #F00\"> " + time + "</b> 秒，过期后请刷新页面重新获取二维码。");
									backTime();
									checkTradeState(paymentOrderId);
								} else {
									$("#failForm").submit();
								}
							},
							error : function() {
								$("#wx-clock-info").html("<b style=\"color: #F00\">抱歉！获取二维码失败，请点击重新获取或选择其他支付方式。</b>");
							}
						});
					} else {
						$("#wx-clock-info").html(data.desc);
					}
				},
				error:function(){
					$("#wx-clock-info").html("<b style=\"color: #F00\">抱歉！获取二维码失败，请点击重新获取或选择其他支付方式。</b>");
				}
			});
		}
	</script>
	
	<!-- 轮询查看是否支付成功，成功则跳转到成功页面 -->
	<script type="text/javascript">
		var start;
		
		$(function() {
			backTime();
			checkTradeState('${paymentOrderId}');
		});
		
		function checkTradeState(paymentOrderId) {
			start = setTimeout(function() {
				checkTradeState(paymentOrderId)
			}, 2000);
			
			if(time <= 0 || time > 60) {
				clearTimeout(start);
			} 
			
			if(paymentOrderId == undefined || paymentOrderId == ''){
	            window.clearInterval(start);
	        } else {
				$.ajax({
					url : contextPath + '/wxPay/queryOrder.jhtml',
					method : 'post',
					async: false,
					dataType:'json',
					data : {'paymentOrderId':paymentOrderId},
					success : function(data) {
						if (data.code == '0') {
							var trade_state = data.trade_state;
							if ('SUCCESS' == trade_state) {
								window.clearInterval(start);
								window.location = contextPath + '/wxPay/nativePaySuccess.jhtml';
							} else if ('PAYERROR' == trade_state) {
								window.clearInterval(start);
								window.location = contextPath + '/wxPay/nativePayFail.jhtml';
							}
						}
					},
					error : function() {
					}
				});
	        }
		}
	</script>
</body>
</html>

﻿<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<link rel="stylesheet" href="css/user.css">
<title>还款详情</title>
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
  <div class="user-common-con clearfix">
		<div class="user-cashier-infol">
			<p><b> 还款单号：${userPaymentOrderDTO.paymentOrderNo}</b><p>
			<p class="f12">还款时间：<fmt:formatDate value="${userPaymentOrderDTO.bankTxTime }" pattern="yyyy-MM-dd HH:mm:ss"/></p>
		</div>
		<div class="user-cashier-infor">
			<p><b>还款金额：${userPaymentOrderDTO.paymentAmount}元</b><p>
			<p class="f12">还款银行：${userPaymentOrderDTO.bankName }</p>
		</div>
	</div>
                 
	<div class="user-common-con mt30">
		<table class="user-ended-list">
			<thead>
				<tr>
					<td width="22%">分期单号</td>
					<td width="12%">期数</td>
					<td width="16%">本金</td>
					<td width="16%">利息</td>
					<td width="16%">罚息</td>
					<td width="18%">还款状态</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="payItem" varStatus="idx">	
				<tr>
					<%-- <td><span class="ml10">订单号${list.partnerOrderId}</span></td> --%>
					<td>${payItem.instalmentId }</td>
					<td>${payItem.instalmentNumber }/${payItem.instalmentCount }期</td>
					<td>${payItem.receivedPrincipal }<span>元</span></td>
					<td>${payItem.receivedService }<span>元</span></td>
					<td>${payItem.receivedOverdue }<span>元</span></td>
					<td>${payItem.paymentStatus==30 ?"重复还款,待退款":(payItem.paymentStatus==40?"已退款":"已支付") }</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="mt20" style="text-align: center;">
		<button class="user-btn user-btn-default" onClick="backToList()">返回列表</button>
	</div>
</div>
<!-- footer start-->
<jsp:include page="${basePath}/jsp/frame/footer.jsp"></jsp:include>
<!-- footer end-->
<script src="js/jquery-1.10.2.min.js"></script>
 <script type="text/javascript">
 	function backToList(){
 		window.location.href=contextPath+"/instalmentManage/instalmentBill.jhtml?currentLi=2"+"&rnd="+Math.random();
 	}
 
 </script>
</body>
</html>

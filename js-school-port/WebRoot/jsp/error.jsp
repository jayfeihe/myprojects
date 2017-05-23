<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
	String path = request.getContextPath();
	String basepath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<script src="${pageContext.request.contextPath}/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
	var second = null;
	$(function(){
		second = $('#totalsecond').text();
		
		setInterval("redirect()", 1000); 
		
	});
	
	function redirect() { 
		if(second < 0){
			location.href = '${partnerReturnUrl}';
		}else{
			$('#totalsecond').text(second--);
		}
	} 
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
<meta http-equiv="expires" content="0">
<link rel="icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/pay.css">
<title>出错啦!</title>
</head>
<body>
<!--header-->
<div class="header">
  <div class="header-wrap">
    <div class="logo"> <img  src="<%=basepath %>images/logo.png" alt="秒趣 轻松分期，畅想生活" title="秒趣 轻松分期，畅想生活"/> </div>
    <div class="logo-text"> 轻松分期，畅想生活 </div>
  </div>
</div>
<!--/header-->

<!--content-->
<div class="content"> 
		
	<div class="pay-result">
		<div class="pay-remind">
			<i class="pay-icon pay-fail-icon"></i>
		</div>
		<div class="pay-remind">
		<c:choose>
			<c:when test="${flag }">
				您仍有秒趣分期账单未结清，结清后方可继续享受秒趣分期服务，本次请您选择其他付款方式！非常感谢
			</c:when>
			<c:otherwise>
				我们对您的了解还不够刻骨铭心，希望下次可以为您提供信贷服务，本次请您选择其他付款方式！非常感谢
			</c:otherwise>
		</c:choose>
		</div>
		<div class="pay-result-btn clearfix">
			<a type="button" class="btn btn-big">本页面将在（<label id="totalsecond">60</label>s）后自动关闭</a>
		</div>
	</div>
</div>
<!--/content-->
<div class="footer-info clearfix">
	<span class="footer-workttime">工作时间：<span class="footer-info-red">10:00</span><span class="footer-info-blue">- 18:00</span></span>
	<span class="footer-customer">客服热线：<span class="footer-info-red">400-</span><span class="footer-info-blue">00-000</span></span>
	
</div>
<div class="footer">
	<p class="footer-text">Copyright &copy; 2015 秒趣  版权所有</p>
</div>
</body>
</html>


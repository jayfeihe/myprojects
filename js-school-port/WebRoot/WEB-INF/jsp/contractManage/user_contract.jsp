<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<script src="js/jquery-1.10.2.min.js"></script>
<title>秒趣分期-我的合同</title>
<script type="text/javascript">
	var contextPath = '${pageContext.request.contextPath}';
</script>
</head>
<body>
<!--header-->
<jsp:include page="${basePath}/jsp/frame/header.jsp"></jsp:include>
<!--/header-->

<!--content-->
<div class="content">
	<div class="content-top clearfix">
		<div class="content-left">	
			<div class="user-pic"><c:if test="${userInfo.sex==1}">
				<img src="images/user-man.png"/> 
			</c:if>
			<c:if test="${userInfo.sex==0}">
				<img src="images/user-woman.png"/> 
			</c:if>
			<c:if test="${userInfo.sex==2}">
				<img src="images/user-default.png"/>
			</c:if></div>
			<p class="user-name">${userInfo.name}</p>
		</div>
		
		<div class="content-right">
			<div class="user-date">
				<h3 class="user-date-title f16">近30日待付款</h3>
				<div class="user-pay-loan" >
					<span class="one-pay-loan money-color">${topView.currentPayment }</span>
					<span class="all-pay-loan ml20">全部待付款：${topView.allLastPayment }</span>
				</div>
				<div class="user-finance">
					<div class="user-month clearfix">
						<span class="fl"> </span>
						<span class="fr"> </span>
					</div>
					<div class="user-day-line">
						
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="content-bottom clearfix">
		<div class="content-left">	
			<ul class="user-nav">
				<li><a href="<%=basePath%>instalmentManage/instalmentBill.jhtml">分期账单</a></li>
				<li class="current"><a href="<%=basePath%>contractManage/userContract.jhtml">合同管理</a></li>
				<li><a href="<%=basePath%>updateUserInfo/viewInfo.jhtml">账户设置</a></li>
			</ul>
		</div>
		
		<div class="content-right">
			<div class="user-loan">
				<div class="user-loan-tabs clearfix">
					<ul id="user-loan-tabs">
						<li class="current">全部</li>
						<li>执行中</li>
						<li>已关闭</li>
					</ul>
				</div>
				
				<div id="user-loan-tabcontainer">
					
				</div>
				
				
			</div>
		</div>
	</div>


  
</div>
<!--/content-->

<!--合同弹框start-->
<div class="hide"  id="contract_dialog">
	 <div class="pay-select-mask"></div>
	 <div class="pay-select-bank">
		<div class="dialog-title">
			<span class="dialog-title-text">秒趣分期协议</span>
			<button type="button" class="dialog-close" id="close_contract_dialog_r"></button>
		</div>
		<div class="pay-agreement-con" id="contractContent">
			</div>
		<div class="dialog-btn-area">
			<button type="button" class="user-btn user-btn-red" id="close_contract_dialog">关闭</button>
		</div>
	  </div>
</div>	
<!--合同弹框end-->
<!-- footer start-->
<jsp:include page="${basePath}/jsp/frame/footer.jsp"></jsp:include>
<!-- footer end-->
<script src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
  $(function(){
	   if('${topView.eveMapStr }'=="{}") {
		   waitMsg("","","",'${topView.nowDate}',"");
		   return;
	   }
	   try{
		   var jsonObj= eval("(" + '${topView.eveMapStr }' + ")");
		    for(var i in jsonObj){
				waitMsg(jsonObj[i].payMon,jsonObj[i].payDay,jsonObj[i].dayToPayment,'${topView.nowDate}',jsonObj[i].countToPay,jsonObj[i].betweenDays);
			}
	   }catch (e) {
	   } 
   });
</script>
<script src="js/contractManage/user_contract.js"></script>
<script type="text/javascript">

	function modifyBankCard(fqId,bankCardId){
		 
		window.location.href="<%=basePath%>contractManage/modifyBankCard.jhtml?instalmentId="+fqId+"&bankCardId="+bankCardId+"&rnd="+Math.random();
	}
</script>
</body>
</html>

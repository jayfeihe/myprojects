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
<script src="js/common.js"></script>
<title>秒趣分期-我的信息</title>
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
			<div class="user-pic">
			<c:if test="${userInfo.sex==1}">
				<img src="images/user-man.png"/> 
			</c:if>
			<c:if test="${userInfo.sex==0}">
				<img src="images/user-woman.png"/> 
			</c:if>
			<c:if test="${userInfo.sex==2}">
				<img src="images/user-default.png"/>
			</c:if>
			</div>
			<p class="user-name" >${userInfo.name}</p>
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
				<li><a href="<%=basePath%>contractManage/userContract.jhtml">合同管理</a></li>
				<li class="current"><a href="<%=basePath%>updateUserInfo/viewInfo.jhtml">账户设置</a></li>
			</ul>
		</div>
		
		<div class="content-right">
			<div class="user-account">
				<div class="user-account-pic">
					<c:if test="${userInfo.sex==1}">
				<img src="images/user-man.png"/> 
			</c:if>
			<c:if test="${userInfo.sex==0}">
				<img src="images/user-woman.png"/> 
			</c:if>
			<c:if test="${userInfo.sex==2}">
				<img src="images/user-default.png"/>
			</c:if>
				</div>
				<div class="user-account-text">
					<span class="f16">${userInfo.name }（${idCard}）</span>
					<span class="money-color f12">已认证</span>
					<span class="ml10">认证时间：${identificationDate }</span>
				</div>
			</div>
			<div class="user-account-list clearfix">
				<div class="user-account-item">
					<div class="f20">基本信息</div>
					<div><span class="f16">账户名：${mobileNumber }</span><a href="<%=basePath%>updateUserInfo/modifyInfo.jhtml" class="ml10 f12">修改资料</a></div>
					<div class="grey-color f12">修改账户信息，让我们为您更好的推送服务信息。</div>
				</div>
				<div class="user-account-item">
					<div class="f20">绑定手机<span class="money-color ml10 f12">${mobileNumber==""?"未绑定":"已绑定"}</span></div>
					<div><span class="f16">${mobileNumber}</span><a href="<%=basePath%>updateUserInfo/updateMobileStepOne.jhtml" class="ml10 f12">更换绑定手机号</a></div>
					<div class="grey-color f12">若已丢失或停用，请立即更换，避免账户被盗！</div>
				</div>
				<div class="user-account-item">
					<div class="f20">登录密码</div>
					<div><span class="f16">密码强度：</span><span class="user-psw-strong f12">${userInfo.pwdLevel==0?'低':(userInfo.pwdLevel==1?'中':'高')}</span><a href="<%=basePath%>updateUserInfo/updatePwd.jhtml" class="ml10 f12">修改密码</a></div>
					<div class="grey-color f12">互联网账号存在被盗风险，建议您定期更改密码以保护账户安全。</div>
				</div>
			</div>
		</div>
	</div>


  
</div>
<!--忘记密码弹框end-->
<!-- footer start-->
<jsp:include page="${basePath}/jsp/frame/footer.jsp"></jsp:include>
<!-- footer end-->

</body>
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
</html>

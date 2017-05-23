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
<title>秒趣分期-修改银行卡</title>
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
	<h3 class="user-title user-common-con">我的银行卡<span class="user-second-title ml20 f12 "></span></h3>
	<div class="user-bank-list user-common-con clearfix">
	<input type="hidden" id="instalmentId" value='${currentInstalmentId}'/>
	<input type="hidden" id="currentbankCardId" value='${currentbankCardId}'/>
	  <c:forEach items="${listUserBankInfo}" var="bankInfo">
	    <div class="user-bank-card <c:if test="${currentbankCardId==bankInfo.bankCardId }">hover</c:if>">
			<div class="user-bank-cardin">
				<div class="user-bank">
					<span class="bank-logo"><img  src="${bankInfo.bankImgUrl }"/></span><span class="lh34">${bankInfo.cardType==0?"储蓄卡":"信用卡" }</span>
					<input type="hidden" name="bankLogoUrlHide" value="${bankInfo.bankImgUrl }"/>
					<input type="hidden" name="bankCardId" value="${bankInfo.bankCardId }"/>
					<input type="hidden" name="bankCardNumHide" value="${bankInfo.cardNumber }"/>
				</div>
				<div class="user-bank-info bankShow" >${fn:substring(bankInfo.cardNumber, 0, 4) }****${fn:substring(bankInfo.cardNumber,fn:length(bankInfo.cardNumber)-4,fn:length(bankInfo.cardNumber)) }
				</div>
				<div class="user-bank-info">${bankInfo.ownerName }</div>
				<i class="user-icon icon-selected"></i>
			</div>
		</div>
	  </c:forEach>
	  
	  <!-- 
		<div class="user-bank-card hover">
			<div class="user-bank-cardin">
				<div class="user-bank">
					<span class="bank-logo"><img  src="images/banklogo/bank_logo_001.jpg"/></span><span class="lh34">储蓄卡</span>
				</div>
				<div class="user-bank-info">622******89789</div>
				<div class="user-bank-info">张三</div>
				<i class="user-icon icon-selected"></i>
			</div>
		</div>
		
		<div class="user-bank-card">
			<div class="user-bank-cardin">
				<div class="user-bank">
					<span class="bank-logo"><img  src="images/banklogo/bank_logo_002.jpg"/></span><span class="lh34">储蓄卡</span>
				</div>
				<div class="user-bank-info">622******89789</div>
				<div class="user-bank-info">张三</div>
				<i class="user-icon icon-selected"></i>
			</div>
		</div> -->
		
		<div class="add-bank-btn" id="add_bank">
			<div class="add-bank-text">添加银行卡（储蓄卡、借记卡）</div>
		</div>
	</div>
		
	<div class="pay-input-item clearfix mt20">
	  <div class="input-item-left">持卡人姓名<em>*</em></div>
	  <div class="input-item-right">
		<span class="input-item-text">${userInfo.name}</span>
	  </div>
	</div>
	<div class="pay-input-item clearfix">
	  <div class="input-item-left">身份证号码<em>*</em></div>
	  <div class="input-item-right">
		<span class="input-item-text">${idCard }</span>
	  </div>
	</div>
	<div class="pay-input-item clearfix">
	  <div class="input-item-left">所属银行</div>
	  <div class="input-item-right" id="selected_bank">
		<div class="bank-logo-img">
			<img src="" />
		</div>
	  </div>
	</div>
	<div class="pay-input-item clearfix" id="bank_card_num">
	  <div class="input-item-left">储蓄卡号<em>*</em></div>
	  <div class="input-item-right">
		<span class="input-item-text" id="cardNumShow"></span>
	  </div>
	</div>
	<form id="bank_card_form" class="hide">
	<div class="pay-input-item clearfix">
	  <div class="input-item-left">储蓄卡号<em>*</em></div>
	  <div class="input-item-right">
		<input type="text" autocomplete="off" class="input-default fl" name="cardNumber" placeholder="请输入本人持有的储蓄卡号" id="cardNumber" value="" onkeyup="this.value=Trim(this.value);" onafterpaste="this.value=Trim(this.value);"/>
	  </div>
	</div>
	<div class="pay-input-item clearfix">
	  <div class="input-item-left">银行预留手机<em>*</em></div>
	  <div class="input-item-right">
		<input type="text" autocomplete="off" class="input-default fl" name="reserveMobile" placeholder="请输入11位手机号码" id="reserveMobile" value="" onkeyup="this.value=Trim(this.value);" onafterpaste="this.value=Trim(this.value);"/>
		<span class="input-item-text grey-color ml10 hide">请填写办卡时银行预留的手机号码</span>
	  </div>
	</div>
	<div class="pay-input-item clearfix">
	  <div class="input-item-left">短信验证码<em>*</em></div>
	  <div class="input-item-right">
		<input type="text"  autocomplete="off" class="input-default input-short" name="smCode" id="smCode"/>
		<button type="button" class="user-btn ml10"  id="get_code">获取验证码</button>
		<span class="msg-box" for="smCode"></span>
	  </div>
	</div>
	<div class="pay-input-item clearfix">
	  <div class="input-item-left">&nbsp;</div>
	  <div class="input-item-right">
		<button type="button" class="user-btn user-btn-default"  id="bind_card">绑定银行卡</button>
		<span class="error-info" id="pay-step-last-error-info"></span>
	  </div>
	</div>
	</form>
	<div class="pay-input-item clearfix">
	  <div class="input-item-left">&nbsp;</div>
	  <div class="input-item-right">
		<button type="button" class="user-btn user-btn-red"  id="submit-psw" onclick="modifyBankCardOfInstalment()">提交</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="user-btn user-btn-red" id="back-btn" onclick="goLocationHistory('contractManage')">返回</button>
		<span class="error-info" id="pay-step-submit-error-info"></span>
	  </div>
	</div>
	
</div>
<div class="hide" id="pay_select_bank">
	 <div class="pay-select-mask"></div>
	 <div class="pay-select-bank">
		<div class="dialog-title">
			<span class="dialog-title-text">选择银行卡</span>
			<button type="button" class="dialog-close" id="dialog_close_r"></button>
		</div>
		<div class="pay-bank-title">
		请选择储蓄卡作为您绑定的银行卡，要求持卡人必须与申请人保持一致哦！
		</div>
		<div class="pay-bank clearfix">
			<div class="pay-bank-prev">
				<span class="pay-icon prev-btn" id="prev-btn"></span>
			</div>
			<div class="pay-bank-list">
			    <input type="hidden" id="relationBankId"/>
				<div class="pay-stages pay-stages-big clearfix" id="bank-list-ul">
								<c:forEach begin="0" items="${bankList }" step="1" var="bank" varStatus="status">
								<c:if test="${status.count % 12 == 1}">
									<ul class="pay-stages-list bank-list <c:if test="${!status.first}">hide</c:if>">
								</c:if>
								<li>
								  <div class="pay-stages-li"><img class="bank-logo" src="<%=basePath%>${bank.bankImgUrl}"/>
								  <i class="user-iconcon-selected"></i>
								  </div>
 								  <input type="hidden" name="relationBankId" value="${bank.relationBankId }">
								  <input type="hidden" name="bankId" value="${bank.bankId }">
								  <input type="hidden" name="thirdPartyBankId" value="${bank.thirdPartyBankId }">
								  <input type="hidden" name="bankName" value="${bank.bankName }">
								</li>
								<c:if test="${status.count % 12 == 0}">
									</ul>
								</c:if>
							</c:forEach>

				</div>
			</div>
			<div class="pay-bank-next">
				<span class="pay-icon next-btn" id="next-btn"></span>
			</div>
		</div>
		<c:if test="${fn:length(bankList)%12==0 }">
		<div class="pay-bank-num">1/<fmt:formatNumber value="${fn:length(bankList)/12 }" type="number"  /></div>
		</c:if>
		<c:if test="${fn:length(bankList)%12!=0 }">
		<div class="pay-bank-num">1/<fmt:formatNumber value="${fn:length(bankList)/12+1 }" type="number"/></div>
		</c:if>
	  </div>
	</div>
<!-- footer start-->
<jsp:include page="${basePath}/jsp/frame/footer.jsp"></jsp:include>
<!-- footer end-->
<script src="js/jquery-1.10.2.min.js"></script>
<script src="js/jquery.validator.js"></script>
<script src="js/common.js"></script>
<script src="js/contractManage/user_bank_card.js"></script>
</body>
</html>

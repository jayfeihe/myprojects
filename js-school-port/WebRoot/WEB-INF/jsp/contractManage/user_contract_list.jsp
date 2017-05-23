<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!--全部列表start-->
<div class="user-contract">
	<c:if test="${list== null || fn:length(list) == 0}">
	<c:if test="${ queryNum !=''}">
	  <div class="user-contract-search">
		<input type="text" autocomplete="off" class="user-contract-input" id="queryNum" value="${queryNum}"
			placeholder="请输入电商订单号或秒趣分期单号"  onkeyup="this.value=Trim(this.value);" onafterpaste="this.value=Trim(this.value);"/>
		<button type="button" class="user-btn user-btn-red ml10"  onclick="querySub()">查&nbsp;&nbsp;询</button>
	  </div>
	</c:if>
		 <div class="user-norecord"> 
					 <div class="user-norecord-box"><i class="user-icon user-icon-norecord"></i></div> 
					 <div class="grey-color">暂无记录</div> 
					 </div> 
	</c:if><c:if test="${list!= null && fn:length(list) > 0}">
	<div class="user-contract-search">
		<input type="text" autocomplete="off" class="user-contract-input" id="queryNum" value="${queryNum}"
			placeholder="请输入电商订单号或秒趣分期单号"  onkeyup="this.value=Trim(this.value);" onafterpaste="this.value=Trim(this.value);"/>
		<button type="button" class="user-btn user-btn-red ml10"  onclick="querySub()">查&nbsp;&nbsp;询</button>
	</div>
	
	<div class="user-contract-thead clearfix">
		<div class="user-contract-td1">商品信息</div>
		<div class="user-contract-td2">&nbsp;</div>
		<div class="user-contract-td3">分期金额</div>
		<div class="user-contract-td4">交易状态</div>
	</div>
	</c:if>
	<c:forEach items="${list}" var="contractInfo">
		<div class="user-contract-item  clearfix">
			<div class="user-contract-title clearfix">
				<div class="user-contract-ttext">分期单号：${contractInfo.instalmentId }
				</div>
				<%-- <div class="user-contract-ttext ml40">合同生效起始日：${contractInfo.loanDate }
				</div> --%>
				<div class="user-loan-stage">分${contractInfo.periodMax }期</div>
			</div>
			<div class="user-contract-con clearfix">
				<div class="user-contract-td1">
					<img src="${contractInfo.productImgUrl}" class="user-contact-pic" />
				</div>
				<div class="user-contract-td2">
					<p class="user-pro-title">${contractInfo.productName }</p>
					<span class="user-pro-title-hide hide"></span>
					<p>订单号${contractInfo.partnerOrderId }</p>
					<p>${contractInfo.productPrice }元<span class="user-product-num">x${contractInfo.productNum }</span>
					</p>
					<p>
						<!-- <img src="${contractInfo.partnerLogoUrl}" class="user-shop-logo" /> -->
						<span class="user-shop">${contractInfo.partnerName}</span>
					</p>
				</div>
				<div class="user-contract-td3">
					<div class="money-color f16 mt40">${contractInfo.loanAmount }元</div>
				</div>
				 <div class="user-contract-td4">
					<div class="mt40">${(contractInfo.protocolStatus==10) ? "审核中":((contractInfo.protocolStatus==20)? "执行中":"终止")}
						<input type="hidden" name="protocolStatus"
							value="${contractInfo.protocolStatus }" />
					</div>
					<p class="mt20">
						银行卡：${fn:substring(contractInfo.bankCardNum, 0, 4)}****${fn:substring(contractInfo.bankCardNum,fn:length(contractInfo.bankCardNum)-4,fn:length(contractInfo.bankCardNum)) }
					<c:if test='${contractInfo.protocolStatus==20}'>
						<span
							class="link-color ml10"
							onclick="modifyBankCard('${contractInfo.instalmentId }','${contractInfo.bankCardId }')">修改</span></c:if>
					</p>
				</div> 
			</div>
		</div>
	</c:forEach>
</div>
<!--全部列表end-->

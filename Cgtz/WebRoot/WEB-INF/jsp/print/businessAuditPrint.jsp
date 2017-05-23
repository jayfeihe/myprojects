<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务审批单</title>
<style type="text/css">
	table{
		width: 625px;
		border: 1px solid #FFF;
	}
	table.datagrid {
		color:#333333;
		border-width: 1px;
		border-color: #666666;
		border-collapse: collapse;
	}
	table.datagrid td {
		border-width: 1px;
		padding: 5px;
		border-style: solid;
		border-color: #666666;
		background-color: #ffffff;
	}
</style>
</head>
<body>
<div id="business">
	<table align="center" style="border-bottom: 1px solid #000; margin-bottom: 20px;">
		<tr>
			<td colspan="4">
				<img alt="logo..." src="img/logocg.png" style="display: block;width: 180px;">
			</td>
			<td align="right">编号:</td>
			<td width="140px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
	</table>
	<table align="center" style="margin-bottom: 10px;">
		<tr>
			<td align="center" colspan="6"><strong style="font-size: large;">业务审批单(${loanBase.productName })</strong></td>
		</tr>
	</table>
	<table class="datagrid" align="center" border="1">
		
		<tr>
			<td>业务类型</td>
			<td colspan="6">
				${loanBase.productName }
			</td>
		</tr>
		<tr>
			<td>贷款人</td>
			<td>${loanBase.name }</td>
			<td>资金用途</td>
			<td>${loanBase.loanUse }</td>
		</tr>
		<tr>
			<td>证件号码</td>
			<td>${loanBase.idNo }</td>
			<td>手机号码</td>
			<td>${loanApp.tel }</td>
		</tr>
		
		<c:forEach items="${colls }" var="coll">
			<!-- 车 -->
			<c:if test="${coll.type eq '01' }">
				<tr>
					<td>抵/质押物</td>
					<td>${coll.carType }</td>
					<td>车牌号</td>
					<td>${coll.license }</td>
				</tr>
				<tr>
					<td>发动机号</td>
					<td>${coll.engCode }</td>
					<td>车架号</td>
					<td>${coll.frameCode }</td>
				</tr>
				<tr>
					<td>公里数</td>
					<td>${coll.mile }(km)</td>
					<td>车辆年限</td>
					<td>${coll.carAge } 年</td>
				</tr>
				<tr>
					<td>抵/质押物状况描述</td>
					<td colspan="5">${coll.remark }</td>
				</tr>
				<tr>
					<td>停车库</td>
					<td colspan="5">${coll.assetAddr }</td>
				</tr>
				<tr>
					<td>担保措施</td>
					<td colspan="5">${coll.reserveDes }</td>
				</tr>
				<tr>
					<td>评估鉴定人</td>
					<td>${coll.evalName }</td>
					<td>评估金额</td>
					<td> <fmt:formatNumber value="${coll.evalPrice }" type="currency"/>元</td>
				</tr>
			</c:if>
			<!-- 车商 -->
			<c:if test="${coll.type eq '02' }">
				<tr>
					<td>车辆数量</td>
					<td>${coll.size }</td>
					<td>抵/质押物</td>
					<td>${coll.assetAddr }</td>
				</tr>
				<tr>
					<td>抵/质押物状况描述</td>
					<td colspan="5">${coll.remark }</td>
				</tr>
				<tr>
					<td>停车库</td>
					<td colspan="5">${coll.assetAddr }</td>
				</tr>
				<tr>
					<td>担保措施</td>
					<td colspan="5">${coll.reserveDes }</td>
				</tr>
				<tr>
					<td>评估鉴定人</td>
					<td>${coll.evalName }</td>
					<td>评估价值</td>
					<td> <fmt:formatNumber value="${coll.evalPrice }" type="currency"/>元</td>
				</tr>
			</c:if>
			<!-- 房 -->
			<c:if test="${coll.type eq '03' }">
				<tr>
					<td>抵/质押物</td>
					<td colspan="5">${coll.assetAddr }</td>
				</tr>
				<tr>
					<td>抵/质押物状况描述</td>
					<td colspan="5">${coll.remark }</td>
				</tr>
				<tr>
					<td>担保措施</td>
					<td colspan="5">${coll.reserveDes }</td>
				</tr>
				<tr>
					<td>评估鉴定人</td>
					<td>${coll.evalName }</td>
					<td>评估价值</td>
					<td> <fmt:formatNumber value="${coll.evalPrice }" type="currency"/>元</td>
				</tr>
			</c:if>
			<!-- 红木 -->
			<c:if test="${coll.type eq '04' }">
				<tr>
					<td>品种</td>
					<td>${coll.var }</td>
					<td>规格</td>
					<td>${coll.size }</td>
				</tr>
				<tr>
					<td>抵/质押物状况描述</td>
					<td colspan="5">${coll.remark }</td>
				</tr>
				<tr>
					<td>仓库</td>
					<td colspan="5">${coll.assetAddr }</td>
				</tr>
				<tr>
					<td>担保措施</td>
					<td colspan="5">${coll.reserveDes }</td>
				</tr>
				<tr>
					<td>评估鉴定人</td>
					<td>${coll.evalName }</td>
					<td>评估金额</td>
					<td> <fmt:formatNumber value="${coll.evalPrice }" type="currency"/>元</td>
				</tr>
			</c:if>
			<!-- 海鲜 -->
			<c:if test="${coll.type eq '05' }">
				<tr>
					<td>品种</td>
					<td>${coll.var }</td>
					<td>规格</td>
					<td>${coll.size }</td>
				</tr>
				<tr>
					<td>抵/质押物状况描述</td>
					<td colspan="5">${coll.remark }</td>
				</tr>
				<tr>
					<td>仓库</td>
					<td colspan="5">${coll.assetAddr }</td>
				</tr>
				<tr>
					<td>担保措施</td>
					<td colspan="5">${coll.reserveDes }</td>
				</tr>
				<tr>
					<td>评估鉴定人</td>
					<td>${coll.evalName }</td>
					<td>评估金额</td>
					<td> <fmt:formatNumber value="${coll.evalPrice }" type="currency"/>元</td>
				</tr>
			</c:if>
			<!-- 其他 -->
			<c:if test="${coll.type eq '99' }">
				<tr>
					<td>品种</td>
					<td>${coll.var }</td>
					<td>规格</td>
					<td>${coll.size }</td>
				</tr>
				<tr>
					<td>抵/质押物状况描述</td>
					<td colspan="5">${coll.remark }</td>
				</tr>
				<tr>
					<td>仓库</td>
					<td colspan="5">${coll.assetAddr }</td>
				</tr>
				<tr>
					<td>担保措施</td>
					<td colspan="5">${coll.reserveDes }</td>
				</tr>
				<tr>
					<td>评估鉴定人</td>
					<td>${coll.evalName }</td>
					<td>评估金额</td>
					<td> <fmt:formatNumber value="${coll.evalPrice }" type="currency"/>元</td>
				</tr>
			</c:if>
		</c:forEach>
		
		<tr>
			<td>放款额度</td>
			<td> <fmt:formatNumber value="${loanBase.loanAmt }" type="currency"/>元</td>
			<td>放款期限</td>
			<td>${loanBase.endDateStr }</td>
		</tr>
		
		<tr>
			<td>收款人</td>
			<td>${loanBase.acctName }</td>
			<td>收款账号</td>
			<td>${loanBase.acctCode }</td>
		</tr>
		<tr>
			<td>收款银行</td>
			<td colspan="6">${loanBase.acctPrvn }${loanBase.acctCity }${loanBase.acctCtry }${loanBase.acctBank }${loanBase.acctBranch }</td>
		</tr>
		
		<tr>
			<td rowspan="7">费用明细</td>
		</tr>
		<tr>
			<td>利息</td>
			<td colspan="3"><fmt:formatNumber value="${retPlan.planInterest }" type="currency"/>元</td>
		</tr>
		<tr>
			<td>保证金</td>
			<td colspan="3"><fmt:formatNumber value="${retPlan.planMargin }" type="currency"/>元</td>
		</tr>
		<tr>
			<td>律师费</td>
			<td colspan="3"><fmt:formatNumber value="${retPlan.planLawFee }" type="currency"/>元</td>
		</tr>
		<tr>
			<td>会员服务费</td>
			<td colspan="3"><fmt:formatNumber value="${retPlan.planMemFee }" type="currency"/>元</td>
		</tr>
		<tr>
			<td>其他费用</td>
			<td colspan="3"><fmt:formatNumber value="${retPlan.planOtherFee }" type="currency"/>元</td>
		</tr>
		<tr>
			<td>转贷费</td>
			<td colspan="3"><fmt:formatNumber value="${retPlan.planTranFee }" type="currency"/>元</td>
		</tr>
		<tr>
			<td>费用总计</td>
			<td colspan="6"><fmt:formatNumber value="${retPlan.planAmt }" type="currency"/>元</td>
		</tr>
		<tr>
			<td>分公司经理</td>
			<td colspan="6">
				${branchUser }
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<fmt:formatDate value="${branchTime }" type="date"/>
			</td>
		</tr>
		<c:if test="${not empty riskFirstUser }">
			<tr>
				<td>风控经理</td>
				<td colspan="6">
					${riskFirstUser }
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<fmt:formatDate value="${riskFirstTime }" type="date"/>
				</td>
			</tr>
		</c:if>
		<c:if test="${not empty meetFirstAuditUser }">
			<tr>
				<td>评审会秘书</td>
				<td colspan="6">
					${meetFirstAuditUser }
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<fmt:formatDate value="${meetFirstAuditTime }" type="date"/>
				</td>
			</tr>
		</c:if>
		<c:if test="${not empty judgeAdvs }">
			<c:forEach items="${judgeAdvs }" var="adv">
				<tr>
					<td>评审会评委</td>
					<td colspan="6">
						${adv.auditUser }
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<fmt:formatDate value="${adv.auditTime }" type="date"/>
					</td>
				</tr>
			</c:forEach>
		</c:if>
		<tr>
			<td>风控总监</td>
			<td colspan="6">
				${riskReviewUser }
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<fmt:formatDate value="${riskReviewTime }" type="date"/>
			</td>
		</tr>
		<tr>
			<td>法务初审</td>
			<td colspan="6">
				${lawFirstUser }
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<fmt:formatDate value="${lawFirstTime }" type="date"/>
			</td>
		</tr>
		<tr>
			<td>法务内勤</td>
			<td colspan="6">
				${lawInsiderUser }
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<fmt:formatDate value="${lawInsiderTime }" type="date"/>
			</td>
		</tr>
		<tr>
			<td>法务复核</td>
			<td colspan="6">
				${lawReviewUser }
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<fmt:formatDate value="${lawReviewTime }" type="date"/>
			</td>
		</tr>
		<tr>
			<td>财务部</td>
			<td colspan="6">
				${financeUser }
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<fmt:formatDate value="${financeTime }" type="date"/>
			</td>
		</tr>
	</table>
</div>
</body>
</html>
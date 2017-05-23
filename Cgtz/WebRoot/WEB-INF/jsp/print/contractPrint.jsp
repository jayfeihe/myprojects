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
<title>用款申请单</title>
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
			<td align="center" colspan="6"><strong style="font-size: large;">用款申请单</strong></td>
		</tr>
	</table>
	<table class="datagrid" align="center">
		
		<tr>
			<td>申请人</td>
			<td>${loanApplyUser }</td>
			<td>部门</td>
			<td>${deptName }</td>
			<td>申请日期</td>
			<td>${loanApplyTime }</td>
		</tr>
		<tr>
			<td>贷款人</td>
			<td>${loanBase.name }</td>
			<td>资金用途</td>
			<td colspan="3">${loanBase.loanUse }</td>
		</tr>
		<tr>
			<td>合同编号</td>
			<td>${contract.contractId }</td>
			<td>合同金额</td>
			<td colspan="3"><fmt:formatNumber value="${contract.loanAmt }" type="currency"/>元</td>
		</tr>
		<tr>
			<td>大写</td>
			<td colspan="6">${upperLoanAmt }</td>
		</tr>
		<tr>
			<td>收款单位</td>
			<td colspan="6">${loanBase.acctName }</td>
		</tr>
		<tr>
			<td>单位地址</td>
			<td colspan="6">${loanApp.companyPrvn }${loanApp.companyCity }${loanApp.companyCtry }${loanApp.companyAddr }</td>
		</tr>
		<c:if test="${not empty contact }">
			<tr>
				<td>联系人</td>
				<td>${contact.name }</td>
				<td>联系方式</td>
				<td colspan="3">${contact.tel }</td>
			</tr>
		</c:if>
		<tr>
			<td>开户银行</td>
			<td colspan="6">${loanBase.acctPrvn }${loanBase.acctCity }${loanBase.acctCtry }${loanBase.acctBank }${loanBase.acctBranch }</td>
		</tr>
		<tr>
			<td>银行账号</td>
			<td colspan="6">${loanBase.acctCode }</td>
		</tr>
	</table>
	
	<table class="datagrid" align="center">
		<tr>
			<td colspan="8" style="text-align: center;">审批签字</td>
		</tr>
		<tr>
			<td>部门负责人</td>
			<td>${branchUser }</td>
			<td>日期</td>
			<td><fmt:formatDate value="${branchTime }" type="date"/></td>
			<td>风控负责人</td>
			<td>${riskReviewUser }</td>
			<td>日期</td>
			<td><fmt:formatDate value="${riskReviewTime }" type="date"/></td>
		</tr>
		<tr>
			<td>法务负责人</td>
			<td>${lawReviewUser }</td>
			<td>日期</td>
			<td><fmt:formatDate value="${lawReviewTime }" type="date"/></td>
			<td>财务负责人</td>
			<td>${financeUser }</td>
			<td>日期</td>
			<td><fmt:formatDate value="${financeTime }" type="date"/></td>
		</tr>
	</table>
</div>
</body>
</html>
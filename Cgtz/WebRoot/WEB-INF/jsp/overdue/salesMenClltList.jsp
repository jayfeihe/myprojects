<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>"/>
<title>业务员催收列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
		<div id="control" class="control">
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>		
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">申请编号</th>
					<th scope="col">合同编号</th>
					<th scope="col">借款金额</th>
					<th scope="col">姓名/机构名称</th>
					<!-- <th scope="col">证件类型</th>
					<th scope="col">证件号码</th> -->
					<th scope="col">电话号码</th>
					<th scope="col">逾期类型</th>
					<th scope="col">逾期期数</th>
					<th scope="col">产品类型</th>
					<th scope="col">逾期日期</th>
					<th scope="col">逾期天数</th>
					<th scope="col">逾期金额</th>
					<!-- <th scope="col">本金逾期金额</th> -->
					<th scope="col">分公司</th>
					<th scope="col">业务经办人</th>					
					<th scope="col">经理意见</th>
					<th scope="col">报告状态</th>
					<th scope="col">受理状态</th>
					<th scope="col" width="160">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td><a href="cllt/allRead.do?loanId=${data.loanId}&contractId=${data.contractId}" target="_blank" style="text-decoration: underline;">${data.loanId}</a></td>
				        <td>
				        ${data.contractId}
				        </td>
				        <td><fmt:formatNumber value="${data.loanAmt}" type="currency"></fmt:formatNumber>元</td>
				        <td>${data.loanName}</td>
				        <td>${data.tel}</td>
				        
<%-- <td>
<c:choose>
<c:when test="${data.idType eq '01'}">身份证</c:when>
<c:when test="${data.idType eq '02'}">军官证</c:when>
<c:when test="${data.idType eq '03'}">驾驶证</c:when>
<c:when test="${data.idType eq '04'}">营业执照</c:when>
<c:when test="${data.idType eq '05'}">组织机构代码证</c:when>
<c:when test="${data.idType eq '06'}">税务登记证</c:when>
</c:choose>
</td>				        
				        <td>${data.idNo }</td> --%>	
<td>
<c:choose>
<c:when test="${data.overdueType eq '1' }">利息逾期</c:when>
<c:when test="${data.overdueType eq '2' }">本金逾期</c:when>
</c:choose>
</td>
<td>${data.lateNum}</td>				        	
<td>
<c:choose>
<c:when test="${data.product eq '01'}">车贷</c:when>
<c:when test="${data.product eq '02'}">车商贷</c:when>
<c:when test="${data.product eq '03'}">房贷</c:when>
<c:when test="${data.product eq '04'}">红木贷</c:when>
<c:when test="${data.product eq '05'}">海鲜贷</c:when>
<c:when test="${data.product eq '99'}">其他</c:when>
</c:choose>
</td>
<%-- <td><a href="javascript:void(0);" onclick="addTab('【合同号-${data.contractId}】还款计划','<%=basePath%>contract/repayPlanList.do?contractId=${data.contractId}')" style="text-decoration: underline;">还款计划</a></td> --%>
<td>${data.retDateStr}</td>
<td>${data.lateDays}</td>
<td>
<fmt:formatNumber value="${data.dueInterest+data.dueCapital}" type="currency"/>元
</td>
<%-- <td>
<fmt:formatNumber value="${data.dueCapital}" type="currency"/>元
</td> --%>
<td>${data.orgName }</td>
<td>${data.saleName }</td>
<td>
<c:choose>
<c:when test="${data.orgAuditResult eq '0'}">未通过</c:when>
<c:when test="${data.orgAuditResult eq '1'}">已通过</c:when>
<c:otherwise>无</c:otherwise>
</c:choose>
</td>
<td>
<c:choose>
<c:when test="${data.checkReportState eq '0'}">未提交</c:when>
<c:when test="${data.checkReportState eq '1'}">未审核</c:when>
<c:when test="${data.checkReportState eq '2'}">未通过</c:when>
<c:when test="${data.checkReportState eq '3'}">已通过</c:when>
</c:choose>
</td>
<td>
<c:choose>
<c:when test="${data.isAccept eq '0' }">未受理</c:when>
<c:when test="${data.isAccept eq '1' }">已受理</c:when>
</c:choose>
</td>

						<td><c:if test="${not empty roleSale}"><a href="javascript:void(0);" onclick="overdueReport('${ data.loanId}','${data.contractId}','${data.lateNum }')">逾期报告</a>&nbsp;</c:if>
							<c:if test="${not empty roleSale}"><a href="javascript:void(0);" onclick="salesCheck('${ data.loanId}','${data.contractId}','${data.lateNum}')">逾期跟进</a>&nbsp;</c:if>
						    <c:if test="${empty roleSale and admin eq '0'}"><a href="javascript:void(0);" onclick="auditReport('${ data.loanId}','${data.contractId}','${data.lateNum }')">逾期报告审核</a>&nbsp;</c:if>
						    <c:if test="${admin eq '1'}"><a href="javascript:void(0);" onclick="overdueReport('${ data.loanId}','${data.contractId}','${data.lateNum }')">逾期报告</a>&nbsp;</c:if>
							<c:if test="${admin eq '1'}"><a href="javascript:void(0);" onclick="salesCheck('${ data.loanId}','${data.contractId}','${data.lateNum}')">逾期跟进</a>&nbsp;</c:if>
						    <c:if test="${admin eq '1'}"><a href="javascript:void(0);" onclick="auditReport('${ data.loanId}','${data.contractId}','${data.lateNum }')">逾期报告审核</a>&nbsp;</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
	
			<div id="pageStyle">
			${ pm.pageNavigation}
			</div>
		</form>
	</div>
</body>

<script language="javascript">
//逾期报告审核
function auditReport(data_id,contractId,num){
	var title="【合同号-"+contractId+"】逾期报告审核";
    addTab(title,'<%=basePath%>' + 'check/reportAudit/query.do?loanId=' + data_id+'&contractId='+contractId+'&num='+num);
	return;
}
//逾期报告
function overdueReport(data_id,contractId,num){
	var title="【合同号-"+contractId+"】逾期报告";
    addTab(title,'<%=basePath%>' + 'overdue/report/query.do?loanId=' + data_id+'&contractId='+contractId+'&lateNum='+num);
	return;
}
//逾期跟进
function salesCheck(data_id,contractId,num) {
	var title="【合同号-"+contractId+"】逾期跟进";
	addTab(title,'<%=basePath%>' + 'overdue/salesCheck/query.do?loanId=' + data_id+'&contractId='+contractId+'&lateNum='+num);
	return;
}


//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


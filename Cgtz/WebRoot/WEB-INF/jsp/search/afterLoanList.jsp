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
<title>稽查列表</title>
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
<th scope="col">线下编号</th>
<th scope="col">申请编号</th>
<th scope="col">项目名称</th>			
<th scope="col">分公司</th>
<th scope="col">开始时间</th>
<th scope="col">期数</th>
<th scope="col">应收时间</th>
<th scope="col">逾期天数</th>
<th scope="col">类别</th>
<th scope="col">项目金额</th>
<th scope="col">逾期金额</th>
<!-- <th scope="col">本金逾期金额</th> -->
<!-- <th scope="col">历史逾期总金额</th> -->
<th scope="col">业务经办人</th>
<th scope="col">借款人</th>
<th scope="col">处理情况(最新)</th>
<th scope="col">是否提交报告</th>
<th scope="col">是否受理</th>
<th scope="col">操作</th>

				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
<td>
${data.contractId}
</td>
<td><a href="cllt/allRead.do?loanId=${data.loanId}&contractId=${data.contractId}" target="_blank" style="text-decoration: underline;">${data.loanId}</a></td>	
<td>
<c:choose>
<c:when test="${data.product eq '01' }">车贷</c:when>
<c:when test="${data.product eq '02' }">车商贷</c:when>
<c:when test="${data.product eq '03' }">房贷</c:when>
<c:when test="${data.product eq '04' }">海鲜贷</c:when>
<c:when test="${data.product eq '05' }">红木贷</c:when>
<c:when test="${data.product eq '99' }">其他</c:when>
</c:choose>
</td>
<td>${data.orgName }</td>
<td>${data.startDateStr}</td>
<td>${data.lateNum}</td>
<td>${data.retDateStr}</td>
<td>${data.lateDays}</td>
<td>
<c:choose>
<c:when test="${data.overdueType eq '1'}">利息逾期</c:when>
<c:when test="${data.overdueType eq '2'}">本金逾期</c:when>
</c:choose>
</td>
<td><fmt:formatNumber value="${data.loanAmt}" type="currency"/>元</td>
<td><fmt:formatNumber value="${data.retInterest+data.retCapital}" type="currency"/>元</td>
<%-- <td><fmt:formatNumber value="${data.retCapital}" type="currency"/>元</td>
<td><fmt:formatNumber value="${data.sumInterestBe}" type="currency"/>元</td>	 --%>	
<td>${data.saleName}</td>
<td>${data.loanName}</td>

<td>
<c:choose>
<c:when test="${data.checkState eq '1' }">未处理</c:when>
<c:when test="${data.checkState eq '2' }">已处理</c:when>
<c:when test="${data.checkState eq '3' }">移交法务</c:when>
<c:when test="${data.checkState eq '4' }"><span style="color:red;">重点关注</span></c:when>
<c:otherwise>无</c:otherwise>
</c:choose>
</td>
<td>
<c:choose>
<c:when test="${data.checkReportState eq '0' }">未提交</c:when>
<c:when test="${data.checkReportState eq '1' }">未审核</c:when>
<c:when test="${data.checkReportState eq '2' }">未通过</c:when>
<c:when test="${data.checkReportState eq '3' }">已通过</c:when>
<c:otherwise>无</c:otherwise>
</c:choose>
</td>
<td>
<c:choose>
<c:when test="${data.isAccept eq '0' }">未受理</c:when>
<c:when test="${data.isAccept eq '1' }">已受理</c:when>
</c:choose>
</td>
<td><a href="javascript:void(0);" onclick="overdueReport('${ data.loanId}','${data.contractId}')">逾期报告</a>&nbsp;
							<a href="javascript:void(0);" onclick="salesCheck('${ data.loanId}','${data.contractId}')">逾期跟进</a>&nbsp;
							<a href="javascript:void(0);" onclick="overdueAccept('${ data.loanId}','${data.contractId}')">逾期受理</a>&nbsp;
							<%-- <a href="javascript:void(0);" onclick="record('${data.contractId}')">逾期记录</a>&nbsp; --%>
						</td>

					</tr>
				</c:forEach>
				<tr><td colspan="3">逾期统计</td>
<td colspan="3">逾期笔数:${conNum}笔</td>
				
<td colspan="4">逾期总金额:
<fmt:formatNumber value="${sumRetInterest + sumRetCapital }" type="currency"/>元</td>
				
<td colspan="4">利息逾期总金额:
<fmt:formatNumber value="${sumRetInterest}" type="currency"/>元</td>

<td colspan="4">本金逾期总金额:
<fmt:formatNumber value="${sumRetCapital}" type="currency"/>元</td>

</tr>				
			</table>
	
			<div id="pageStyle">
			${ pm.pageNavigation}
			</div>
		</form>
	</div>
</body>

<script language="javascript">

//逾期报告审核
function overdueReport(data_id,contractId){
	var title="【合同号-"+contractId+"】逾期报告";
    addTab(title,'<%=basePath%>' + 'check/reportAudit/query.do?loanId=' + data_id+'&contractId='+contractId);
	return;
}
//逾期跟进
function salesCheck(data_id,contractId) {
	var title="【合同号-"+contractId+"】逾期跟进";
	addTab(title,'<%=basePath%>' + 'check/salesCheck/query.do?loanId=' + data_id+'&contractId='+contractId);
	return;
}
//逾期申请受理
function overdueAccept(data_id,contractId) {
	var title="【合同号-"+contractId+"】逾期受理";
	addTab(title,'<%=basePath%>' + 'check/overdueAccept/query.do?loanId=' + data_id+'&contractId='+contractId);
	return;
}
//逾期记录
function record(contractId) {
	var title="【合同号-"+contractId+"】逾期记录";
	addTab(title,'<%=basePath%>' + 'search/record/list.do?contractId='+contractId);
	return;
}
//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


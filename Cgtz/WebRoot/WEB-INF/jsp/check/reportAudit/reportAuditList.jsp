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
<title>逾期报告列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
			</div>
			<table id="table" class="datatable" summary="list of members in EE Studay">			
				<tr>
					<th scope="col">序号</th>
					<th scope="col">期数</th>
					<th scope="col">提交人</th>
					<th scope="col">提交时间</th>
					<th scope="col">分公司审核结果</th>
					<th scope="col">分公司审核意见</th>
					<th scope="col">分公司审核人</th>
					<th scope="col">审核时间</th>
					<th scope="col">稽查意见</th>
					<th scope="col">稽查人</th>
					<th scope="col">稽查时间</th>
					<th scope="col" width="160">查看详情</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
<td>${data.num}</td>
<td>${data.submitName}</td>
<td>${data.submitTmeStr}</td>
<td>
<c:choose>
<c:when test="${data.orgAuditResult eq '0'}">未通过</c:when>
<c:when test="${data.orgAuditResult eq '1'}">已通过</c:when>
</c:choose>
</td>
<td>${data.orgAuditRemark}</td>
<td>${data.orgAuditName}</td>
<td>${data.orgAuditTimeStr}</td>
<td>${data.auditRemark}</td>
<td>${data.auditName}</td>
<td>${data.auditTimeStr}</td>


						<td><a href="javascript:void(0);" onclick="javascript:readData('${ data.id}');">查看</a>&nbsp;
						<a href="check/reportAudit/print.do?id=${data.id}" target="_blank" style="text-decoration: underline;">打印</a>&nbsp;
							<a href="javascript:void(0);" onclick="javascript:auditReport('${ data.id}');">审核</a>&nbsp;							
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
//查看
function readData(data_id){
    addTab('查看报告','<%=basePath%>' + 'check/reportAudit/read.do?id=' + data_id);
	return;
}
//审核
function auditReport(data_id) {
	addTab('审核报告','<%=basePath%>' + 'check/reportAudit/update.do?id=' + data_id);
	return;
}
//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


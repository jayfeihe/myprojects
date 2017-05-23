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
<title>T_ONLINE_STATES列表</title>
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
<th scope="col">合同号</th>
<th scope="col">申请编号</th>
<th scope="col">借款人/机构</th>
<th scope="col">借款金额</th>
<th scope="col">状态</th>
<th scope="col">是否当前</th>
<th scope="col">备注</th>
<th scope="col">日志时间</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
				
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
<td><a href="javascript:void(0);" onclick="addTab('【合同号-${data.contractId}】日志查看','${basePath}onLineStates/logList.do?contractId=${data.contractId}')">${data.contractId}</a></td>
<td><a href="cllt/allRead.do?loanId=${data.loanId}&contractId=${data.contractId}" target="_blank" style="text-decoration: underline;">${data.loanId}</a></td>					
<td>${data.loanName}</td>
<td>
<fmt:formatNumber value="${data.loanAmt}" type="currency"/>元
</td>
<td>${data.state}</td>
<td>
<c:choose>
<c:when test="${data.isCur eq '1' }">是</c:when>
<c:when test="${data.isCur eq '0' }">否</c:when>
</c:choose>
</td>
<td>${data.remarks}</td>
<td>${data.logTimeStr}</td>
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

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


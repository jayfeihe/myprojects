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
<title>贷后综合查询列表</title>
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
<th scope="col">合同编号</th>
<th scope="col">合同状态</th>
<th scope="col">申请编号</th>				
<th scope="col">分公司</th>
<th scope="col">业务员</th>
<th scope="col">借款人</th>
<th scope="col">借款人电话</th>
<th scope="col">借款金额</th>
<th scope="col">出借时间</th>
<th scope="col">还款时间</th>
<th scope="col">是否续贷</th>
<th scope="col">是否逾期</th>

				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
<td>
${data.contractId}
</td>
<td>
<c:choose>
<c:when test="${data.state eq '1' }">未生效</c:when>
<c:when test="${data.state eq '2' }">合同中</c:when>
<c:when test="${data.state eq '3' }">合同结清</c:when>
<c:when test="${data.state eq '4' }">被拆分</c:when>
</c:choose>
</td>
<td><a href="cllt/allRead.do?loanId=${data.loanId}&contractId=${data.contractId}" target="_blank" style="text-decoration: underline;">${data.loanId}</a></td>				
<td>${data.orgName}</td>
<td>${data.saleName}</td>
<td>${data.loanName}</td>
<td>${data.tel}</td>
<td><fmt:formatNumber value="${data.loanAmt}" type="currency"/>元</td>
<td>${data.startDateStr}</td>
<td>${data.endDateStr}</td>
<td>
<c:choose>
<c:when test="${data.isRenew eq '0' }">否</c:when>
<c:when test="${data.isRenew eq '1' }">是</c:when>
<c:when test="${data.isRenew eq '2' }">被续贷</c:when>
</c:choose>
</td>
<td>
<c:choose>
<c:when test="${data.isLate eq '0' }">否</c:when>
<c:when test="${data.isLate eq '1' }">是</c:when>
</c:choose>
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

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


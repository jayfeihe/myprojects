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
<title>逾期记录列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
				<!-- <a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp; -->
			</div>		
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
<th scope="col">线下编号</th>
<th scope="col">逾期期数</th>
<th scope="col">当期逾期天数</th>			
<th scope="col">逾期类别</th>
<th scope="col">项目金额</th>
<th scope="col">逾期金额</th>
<th scope="col">开始日期</th>
<th scope="col">应收日期</th>
<th scope="col">最后还款日期</th>

				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
<td>
${data.contractId}
</td>
<td>${data.lateNum}</td>

<td>${data.lateDays}</td>
<td>
<c:choose>
<c:when test="${data.overdueType eq '1'}">利息逾期</c:when>
<c:when test="${data.overdueType eq '2'}">本金逾期</c:when>
<c:otherwise>未逾期</c:otherwise>
</c:choose>
</td>
<td><fmt:formatNumber value="${data.loanAmt}" type="currency"/>元</td>
<td><fmt:formatNumber value="${data.retInterest + data.retCapital}" type="currency"/>元</td>		
<td>${data.startDateStr}</td>
<td>${data.retDateStr}</td>
<td>${data.lastDateStr}</td>
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


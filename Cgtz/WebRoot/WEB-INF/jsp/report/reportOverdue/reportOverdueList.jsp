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
<title>分公司逾期统计表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
			<!-- <a href="javascript:void(0);" onclick="exportExcel('queryReportOverdueForm');"><img src="img/square/export.png" class='dotimg' title="导出" /></a>&nbsp; -->
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>		
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
<th scope="col">地区</th>				
<th scope="col">逾期类型</th>
<th scope="col">交易笔数</th>
<th scope="col">项目数</th>
<th scope="col">逾期金额</th>
<th scope="col">本金逾期率</th>
<th scope="col">A(0＜X≤14天)金额</th>
<th scope="col">A(0＜X≤14天)比例</th>
<th scope="col">B(14天＜X≤30天)金额</th>
<th scope="col">B(14天＜X≤30天)比例</th>
<th scope="col">C(30天＜X≤90天)金额</th>
<th scope="col">C(30天＜X≤90天)比例</th>
<th scope="col">D(90天＜X≤180天)金额</th>
<th scope="col">D(90天＜X≤180天)比例</th>
<th scope="col">E(180天＜X)金额</th>
<th scope="col">E(180天＜X)比例</th>
<th scope="col">当天存量</th>


				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
<td>${data.orgName}</td>
<td>
<c:choose>
<c:when test="${data.type eq '1'}">利息逾期</c:when>
<c:when test="${data.type eq '2'}">本金逾期</c:when>
</c:choose>
</td>
<td>${data.dealNum}</td>
<td>${data.projNum}</td>
<td><fmt:formatNumber value="${data.overdueAmt}" type="currency"/>元</td>
<td>
<c:if test="${data.type eq '2'}">
<fmt:formatNumber value="${data.prinRate}" type="percent" pattern="0.0%" maxFractionDigits="2"/>
</c:if>
</td>
<td><fmt:formatNumber value="${data.amt_14}" type="currency"/>元</td>
<td><fmt:formatNumber value="${data.rate_14}" type="percent" pattern="0.0%" maxFractionDigits="2"/></td>
<td><fmt:formatNumber value="${data.amt_30}" type="currency"/>元</td>
<td><fmt:formatNumber value="${data.rate_30}" type="percent" pattern="0.0%" maxFractionDigits="2"/></td>
<td><fmt:formatNumber value="${data.amt_90}" type="currency"/>元</td>
<td><fmt:formatNumber value="${data.rate_90}" type="percent" pattern="0.0%" maxFractionDigits="2"/></td>
<td><fmt:formatNumber value="${data.amt_180}" type="currency"/>元</td>
<td><fmt:formatNumber value="${data.rate_180}" type="percent" pattern="0.0%" maxFractionDigits="2"/></td>
<td><fmt:formatNumber value="${data.amtMore}" type="currency"/>元</td>
<td><fmt:formatNumber value="${data.rateMore}" type="percent" pattern="0.0%" maxFractionDigits="2"/></td>
<td><fmt:formatNumber value="${data.loanAmt}" type="currency"/>元</td>
					</tr>
				</c:forEach>
			</table>
	
			<div id="pageStyle">
			${ pm.pageNavigation}
			</div>
		</form>
	</div>
</body>

</html>


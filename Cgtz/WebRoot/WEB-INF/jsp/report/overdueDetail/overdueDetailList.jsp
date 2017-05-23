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
<title>逾期明细列表</title>
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
<!-- <th scope="col">申请编号</th> -->
<th scope="col">项目名称</th>			
<th scope="col">分公司</th>
<th scope="col">开始时间</th>
<th scope="col">逾期期数</th>
<th scope="col">应收日期</th>
<th scope="col">还款日期</th>
<th scope="col">逾期天数</th>
<th scope="col">类别</th>
<th scope="col">项目金额</th>
<th scope="col">逾期金额</th>
<!-- <th scope="col">本金逾期金额</th> -->
<th scope="col">业务经办人</th>
<th scope="col">借款人</th>

				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
<td>
${data.contractId}
</td>
<%-- <td><a href="cllt/allRead.do?loanId=${data.loanId}&contractId=${data.contractId}" target="_blank" style="text-decoration: underline;">${data.loanId}</a></td>	 --%>
<td>
<%-- <c:choose>
<c:when test="${data.product eq '01' }">车贷</c:when>
<c:when test="${data.product eq '02' }">车商贷</c:when>
<c:when test="${data.product eq '03' }">房贷</c:when>
<c:when test="${data.product eq '04' }">海鲜贷</c:when>
<c:when test="${data.product eq '05' }">红木贷</c:when>
<c:when test="${data.product eq '99' }">其他</c:when>
</c:choose> --%>
${data.projectName }
</td>
<td>${data.orgName }</td>
<td>${data.startDateStr}</td>
<td>${data.lateNum}</td>
<td>${data.retDateStr}</td>
<td>${data.lastDateStr}</td>
<td>${data.lateDays}</td>
<td>
<c:choose>
<c:when test="${data.overdueType eq '1'}">利息逾期</c:when>
<c:when test="${data.overdueType eq '2'}">本金逾期</c:when>
</c:choose>
</td>
<td><fmt:formatNumber value="${data.loanAmt}" type="currency"/>元</td>
<td><fmt:formatNumber value="${data.retInterest+data.retCapital}" type="currency"/>元</td>
<%-- <td><fmt:formatNumber value="${data.retCapital}" type="currency"/>元</td> --%>		
<td>${data.saleName}</td>
<td>${data.loanName}</td>
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


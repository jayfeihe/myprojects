<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/table_sorter.css">
<input type="hidden" id="totalCount" value="${fn:length(datas) }"/>
<table class="table sa tablesorter">
	<thead>
		<tr>
			<th class="left" id="firstOne">
				<c:choose>
					<c:when test="${login_company.type eq 1 }">媒体</c:when>
					<c:when test="${login_company.type eq 2 }">广告主</c:when>
				</c:choose>
			</th>
			<th class="ri">违法广告活动数<!-- <i class="fa fa-caret-down" aria-hidden="true"></i> --></th>
			<th class="ri">违法广告创意数<!-- <i class="fa fa-caret-down" aria-hidden="true"></i> --></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${datas }" var="data" varStatus="status">
			<tr>
				<td class="left moren">${data.relatedName }</td>
				<td>${data.adCount }</td>
				<td>${data.creativeCount }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<script type="text/javascript" src="<%=basePath%>js/jquery/jquery.tablesorter.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('table').tablesorter();
	});
</script>
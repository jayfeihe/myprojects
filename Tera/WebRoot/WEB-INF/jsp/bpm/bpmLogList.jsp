<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<table width="100%" class="datatable">
	<thead>
		<tr>
			<th>序号</th>
			<th>处理人</th>
			<th>接收时间</th>
			<th>处理时间</th>
			<th>节点</th>
			<th>备注</th>
		</tr>
	</thead>
	<c:forEach items="${bpmLogs }" var="data" varStatus="status">
		<tbody>
			<tr>
				<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
				<td>${data.operatorName}</td>
				<td><fmt:formatDate value="${data.intime}" type="both"/></td>
				<td><fmt:formatDate value="${data.outtime}" type="both"/></td>
				<td>${data.state}</td>
				<td>${data.logContent5}</td>
			</tr>
		</tbody>
	</c:forEach>
</table>
<script type="text/javascript">
$(document).ready(function(){

});
 </script>
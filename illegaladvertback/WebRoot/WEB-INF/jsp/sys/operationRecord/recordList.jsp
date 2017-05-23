<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<table class="table table-bordered table-hover">
 	<thead>
 		<tr>
 			<th style="text-align:center;">序号</th>
 			<th style="text-align:center;">ip</th>
 			<th style="text-align:center;">登录账号</th>
 			<th style="text-align:center;">昵称</th>
 			<th style="text-align:center;">操作描述</th>
 			<th style="text-align:center;">操作时间</th>
 		</tr>
 	</thead>
 	<tbody>
 		<c:if test="${pager.total eq 0 }">
 			<tr>
 				<td colspan="7" align="center" style="color: red; font-weight: bold;">未查到数据</td>
 			</tr>
 		</c:if>
 		<c:if test="${pager.total gt 0 }">
		 	<c:forEach items="${pager.datas }" var="data" varStatus="status">
		 		<tr>
		 			<td>${status.index+pager.pageOffset+1}</td>
		 			<td>${data.ipAddress }</td>
		 			<td>${data.account }</td>
		 			<td>${data.nickName }</td>
		 			<td>${data.operationDesc }</td>
		 			<td> <fmt:formatDate value="${data.createTime }" type="both"/></td>
		 		</tr>
		 	</c:forEach>
	 	</c:if>
 	</tbody>
</table>
<%@ include file="/WEB-INF/jsp/page.jsp"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<table class="table table-bordered table-hover">
 	<thead>
 		<tr>
 			<th style="text-align:center;">序号</th>
 			<th style="text-align:center;">媒体全称</th>
 			<th style="text-align:center;">URL地址</th>
 			<th style="text-align:center;">状态</th>
 			<th style="text-align:center;">所属行业</th>
 			<th style="text-align:center;">操作</th>
 		</tr>
 	</thead>
 	<tbody>
 		<c:if test="${pager.total eq 0 }">
 			<tr>
 				<td colspan="6" align="center" style="color: red; font-weight: bold;">未查到数据</td>
 			</tr>
 		</c:if>
 		<c:if test="${pager.total gt 0 }">
		 	<c:forEach items="${pager.datas }" var="data" varStatus="status">
		 		<tr>
		 			<td>${status.index+pager.pageOffset+1}</td>
		 			<td>${data.companyName }</td>
		 			<td>${data.companyUrl }</td>
		 			<td>
		 				<c:choose>
		 					<c:when test="${data.status eq 0}">无效</c:when>
		 					<c:when test="${data.status eq 1}">有效</c:when>
		 				</c:choose>
		 			</td>
		 			<td>${data.industryName }</td>
		 			<td>
		 				<c:if test="${data.status eq 0}">
		 					<input type="button" class="btn btn-primary updateStatus" curStatus="${data.status }" companyId="${data.companyId }" value="启用">
		 				</c:if>
		 				<c:if test="${data.status eq 1}">
		 					<input type="button" class="btn btn-primary updateStatus" curStatus="${data.status }" companyId="${data.companyId }" value="暂停">
		 				</c:if>
		 				&nbsp;&nbsp;&nbsp;&nbsp;
		 				<input type="button" class="btn btn-primary updateCompanyBtn" value="编辑" companyId="${data.companyId }">
		 			</td>
		 		</tr>
		 	</c:forEach>
	 	</c:if>
 	</tbody>
</table>
<%@ include file="/WEB-INF/jsp/page.jsp"%>

<script type="text/javascript" src="<%=basePath%>js/main/company.js"></script>
<script type="text/javascript">
	$(function(){
		company.openUpdateModal();
		company.detail.updateCompany({
			'redirect_url':'/company/media/query'
		});
		company.detail.updateStatus({
			'redirect_url':'/company/media/query'
		});
	})
</script>
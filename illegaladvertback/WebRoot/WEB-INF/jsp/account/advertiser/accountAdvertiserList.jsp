<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<table class="table table-bordered table-hover">
 	<thead>
 		<tr>
 			<th style="text-align:center;">序号</th>
 			<th style="text-align:center;">帐号</th>
 			<th style="text-align:center;">广告主全称</th>
 			<th style="text-align:center;">广告主URL</th>
 			<th style="text-align:center;">状态</th>
 			<th style="text-align:center;">手机</th>
 			<th style="text-align:center;">操作</th>
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
		 			<td>${data.userAccount.account }</td>
		 			<td>${data.userCompany.companyName }</td>
		 			<td>${data.userCompany.companyUrl }</td>
		 			<td>
		 				<c:choose>
		 					<c:when test="${data.userAccount.status eq 0}">无效</c:when>
		 					<c:when test="${data.userAccount.status eq 1}">有效</c:when>
		 				</c:choose>
		 			</td>
		 			<td>${data.userAccount.mobile }</td>
		 			<td>
		 				<input type="button" class="btn btn-primary updateAccountBtn" value="编辑" accountId="${data.userAccount.id }">
		 			</td>
		 		</tr>
		 	</c:forEach>
	 	</c:if>
 	</tbody>
</table>
<%@ include file="/WEB-INF/jsp/page.jsp"%>

<script type="text/javascript" src="<%=basePath%>js/main/account.js"></script>
<script type="text/javascript">
	$(function(){
		userAccount.openUpdateModal();
		userAccount.detail.updateAccount({
			'redirect_url':'/account/advertiser/query'
		});
	})
</script>
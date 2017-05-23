<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<table class="table table-bordered table-hover">
 	<thead>
 		<tr>
 			<th style="text-align:center;">序号</th>
 			<th style="text-align:center;">登录帐号</th>
 			<th style="text-align:center;">创建时间</th>
 			<th style="text-align:center;">修改时间</th>
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
		 			<td>${data.account }</td>
		 			<td> <fmt:formatDate value="${data.createTime }" type="both"/></td>
		 			<td> <fmt:formatDate value="${data.updateTime }" type="both"/></td>
		 			<td>
		 				<input type="button" class="btn btn-primary updatePwdBtn" value="重置密码" userId="${data.userId }">
		 			</td>
		 		</tr>
		 	</c:forEach>
	 	</c:if>
 	</tbody>
</table>
<%@ include file="/WEB-INF/jsp/page.jsp"%>

<script type="text/javascript" src="<%=basePath%>js/main/sysUser.js"></script>
<script type="text/javascript">
	$(function(){
		sysUser.openUpdatePwdModal();
		sysUser.detail.updatePwd({
			'redirect_url':'/sys/user/query'
		});
	})
</script>
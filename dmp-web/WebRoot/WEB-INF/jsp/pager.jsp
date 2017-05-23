<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.greenkoo.dmp.sys.model.SystemContext"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<style>
	a {
		text-decoration: none;
	}
</style>
<pg:pager export="curPage=pageNumber" items="${param.totalRecord }"
	maxPageItems="<%=SystemContext.getPageSize() %>" url="${param.url }">
	<c:forEach items="${param.params }" var="p">
		<pg:param name="${p }" />
	</c:forEach> 
	
	<pg:first>
		<a href="${pageUrl }" class="pager_link">首页</a>
	</pg:first> 
	<pg:skip pages="-10">
		<a href="${pageUrl }" class="pager_link">前跳10页</a>
	</pg:skip>
	<pg:prev>
		<a href="${pageUrl }" class="pager_link">上一页</a>
	</pg:prev> 
	<pg:pages>
		<c:if test="${curPage eq pageNumber }">
			[${pageNumber }]
		</c:if>
		<c:if test="${curPage != pageNumber }">
			<a href="${pageUrl }" class="pager_link">${pageNumber }</a>
		</c:if>
	</pg:pages> 
	<pg:next>
		<a href="${pageUrl }" class="pager_link">下一页</a>
	</pg:next> 
	<pg:skip pages="10">
		<a href="${pageUrl }" class="pager_link">后跳10页</a>
	</pg:skip>
	<pg:last>
		<a href="${pageUrl }" class="pager_link">尾页</a>
	</pg:last>

	<span> 共
		 <pg:last>
			${pageNumber } 页[${param.totalRecord }条记录]
			<%-- ,每页显示<%=SystemContext.getPageSize() %>条 --%>
		</pg:last> 
	</span>
</pg:pager>

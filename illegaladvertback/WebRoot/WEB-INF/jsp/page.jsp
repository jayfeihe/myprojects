<%@page import="com.greenkoo.sys.model.Pager"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
    <%
    	Pager pager = (Pager) request.getAttribute("pager"); 
    	int total = pager.getTotal();
      	int totalPage = pager.getTotalPage();
      	int curPage = pager.getCurPage();
    %>
    
    <style>
		a.disabled{
			pointer-events: none;
		}
		
		.inav {
		  text-align: center; }
		
		.illegal.pagination {
		  width: auto !important;
		  }
		
		.illegal.nav.nav-tabs {
		  /* margin-bottom: 20px; */
		  border-bottom: 0; }
		
		.illegal.nav-tabs li {
		  margin-right: 25px !important;
		  font-size: 14px;
		  background: #fff;
		  border-radius: 0 !important; }
					
	</style>
<%if(total != 0) {%>    
	<nav class="inav">
	    <ul class="pagination illegal">
	    	<!-- 上一页 -->
	    	<%if(curPage > 1) {%>
	   			<li><a href="javascript:void(0);" onclick="nextPage(-1)">上一页</a></li>
	    	<%} else { %>
	    		<li class="disabled"><a class="disabled" href="javascript:void(0);" onclick="nextPage(-1)">上一页</a></li>
	    	<%} %>
	    	
	    	<!-- 页数 -->
	        <%
	          	for(int i=1;i<=totalPage;i++){
	          		if(curPage == i) {
	        %>
	        	<li class="active"><a class="disabled" href="javascript:void(0);" onclick="goPage('<%=i %>')"><%=i %></a></li>
	        <%} else { %>
	        	<li><a href="javascript:void(0);" onclick="goPage('<%=i %>')"><%=i %></a></li>
	        <%}} %>
	        
	        <!-- 下一页 -->
	        <%if(curPage != totalPage) {%>
	   			<li><a href="javascript:void(0);" onclick="nextPage(1)">下一页</a></li>
	    	<%} else { %>
	    		<li class="disabled"><a class="disabled" href="javascript:void(0);" onclick="nextPage(1)">下一页</a></li>
	    	<%} %>
	    </ul>
	    <input type="hidden" id="curPage" name="curPage" value="${pager.curPage}">
	    <input type="hidden" id="totalPage" name="totalPage" value="${pager.totalPage}">
	</nav>
<%} %>

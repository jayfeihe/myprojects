<%@page import="com.greenkoo.sys.model.Pager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	</style>
<%if(total != 0) {%>    
	<nav class="inav">
	    <ul class="pagination illegal">
	    	<%if(curPage > 1) {%>
		    	<!-- 上一页 -->
		    	<li><a href="javascript:void(0);" onclick="goPage${param.type}(1)">首页</a></li>
	   			<li><a href="javascript:void(0);" onclick="nextPage${param.type}(-1)">上一页</a></li>
	    	<%} else { %>
	    		<li class="disabled"><a class="disabled" href="javascript:void(0);">首页</a></li>
	    		<li class="disabled"><a class="disabled" href="javascript:void(0);">上一页</a></li>
	    	<%} %>
	    	
	    	<!-- 页数 -->
	        <%
	          	for(int i=1;i<=totalPage;i++){
	          		if(i <= 10) {
	          			if(curPage == i) {
	        %>
	        	<li class="active"><a class="disabled" href="javascript:void(0);" onclick="goPage${param.type}('<%=i %>')"><%=i %></a></li>
	        <%} else { %>
	        	<li><a href="javascript:void(0);" onclick="goPage${param.type}('<%=i %>')"><%=i %></a></li>
	        <%
	        			}
	          		} else {
	        %>
	          			<li><a href="javascript:void(0);">...</a></li>
	        <%  		}
	          	}
	        %>
	        
	        <!-- 下一页 -->
	        <%if(curPage != totalPage) {%>
	   			<li><a href="javascript:void(0);" onclick="nextPage${param.type}(1)">下一页</a></li>
		    	<li><a href="javascript:void(0);" onclick="goPage${param.type}('<%=totalPage%>')">末页</a></li>
	    	<%} else { %>
	    		<li class="disabled"><a class="disabled" href="javascript:void(0);">下一页</a></li>
	    		<li class="disabled"><a class="disabled" href="javascript:void(0);">末页</a></li>
	    	<%} %>
	    </ul>
	    <input type="hidden" id="curPage" name="curPage" value="${pager.curPage}">
	    <input type="hidden" id="totalPage" name="totalPage" value="${pager.totalPage}">
	</nav>
<%} %>
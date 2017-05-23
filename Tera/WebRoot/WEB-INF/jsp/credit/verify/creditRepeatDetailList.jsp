<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<div>
	<table>
		<c:forEach items="${ pm.data}" var="data" varStatus="status">
			<tr>
				<td>
					<c:choose>
						<c:when test="${data.type=='1'}">单位名称：</c:when>
						<c:when test="${data.type=='2'}">单位地址：</c:when>
						<c:when test="${data.type=='3'}">居住地址：</c:when>
						<c:when test="${data.type=='4'}">联系方式：</c:when>
						<c:when test="${data.type=='5'}">身份证号：</c:when>
						<c:otherwise>未知</c:otherwise>
					</c:choose>
				</td>
				<td>${data.value}</td>
				<td>重复 ${data.number}次</td> 
				<td><a href="javascript:void(0);" onclick="javascript:artOpenPage('详细信息','credit/verify/repeatDetailPopup.do?appId=${data.appId}&value=${data.value}')">查看详细信息</a></td>
			</tr>
		</c:forEach>
	</table>
</div>
<script language="javascript">
//页面加载完动作
$(document).ready(function (){
	
});
//原页面弹出框
function artOpenPage(_title,_url) {
	if($("body").find("#dialogDiv").length==0){
		 $('body').append($("<div id='dialogDiv' style='top:150px;'></div>"));
	}
	$('#dialogDiv').dialog({
	    title: _title,
	    height: 460,
	    width: 800,
	    closed: false,
	    cache: false,
	    href: encodeURI(_url),
	    modal: true,
	    resizable: true
	});
}
</script>
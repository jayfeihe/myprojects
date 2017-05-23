<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div >
    <table>
		<c:forEach items="${ pm.data}" var="data" varStatus="status">
			<tr>
				<td>
					<c:choose>
						<c:when test="${data.type=='1'}">单位名称：</c:when>
						<c:when test="${data.type=='2'}">单位地址：</c:when>
						<c:when test="${data.type=='3'}">居住地址：</c:when>
						<c:when test="${data.type=='4'}">手机号：</c:when>
						<c:when test="${data.type=='5'}">身份证号：</c:when>
						<c:otherwise>未知</c:otherwise>
					</c:choose>
				</td>
				<td>${data.value}</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>说明：${data.comment}</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<c:if test="${data.currentBpmState eq '录入申请' }">
					<td><a href="car/app/read.do?id=${data.id}" target="_blank">查看重复人详情</a></td>
				</c:if>
				<c:if test="${data.currentBpmState eq '审核' }">
					<td><a href="car/verify/read.do?id=${data.id}" target="_blank">查看重复人详情</a></td>
				</c:if>
				<c:if test="${data.currentBpmState eq '审批' }">
					<td><a href="car/query/read.do?id=${data.id}" target="_blank">查看重复人详情</a></td>
				</c:if>
			</tr>
		</c:forEach>
	</table>   
</div>
<script language="javascript">
//页面加载完动作
$(document).ready(function (){

});
</script>

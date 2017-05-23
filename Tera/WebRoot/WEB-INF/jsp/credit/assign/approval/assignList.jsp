<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>"/>
<title>审批分单列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col"><input type="checkbox" id="SelectAll"  onclick="selectAll();"/></th>
					<th scope="col">序号</th>
					<th scope="col">申请编号</th>
					<th scope="col">客户姓名</th>
					<th scope="col">进件时间</th>
					<th scope="col">金额</th>
					<th scope="col">渠道</th>
					<th scope="col">产品</th>
					<th scope="col">期限</th>
					<th scope="col">当前审批人</th>
					<th scope="col">营业部</th>
					<th scope="col">审核人员</th>
				</tr>

				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<th scope="col"><input type="checkbox" id="item" name="appIds" value="${data.appId}" /></th>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td><a href="credit/app/read.do?id=${data.id}" target="_blank">${data.appId}</a></td>
						<td>${data.name}</td>
						<td>${data.inputTimeStr}</td>
<%--						<td>${data.amount/10000}</td>--%>
						<td><fmt:formatNumber value="${data.amount/10000}" type="currency"/>万元</td>
						<td>${data.belongChannelName}</td>
						<td>${data.product}</td>
						<td>${data.period}个月</td>
<%--						<td>--%>
<%--							<c:choose>--%>
<%--	                        <c:when test="${data.period=='1'}">12个月</c:when>--%>
<%--	                        <c:when test="${data.period=='2'}">18个月</c:when>--%>
<%--	                        <c:when test="${data.period=='3'}">24个月</c:when>--%>
<%--	                        <c:when test="${data.period=='4'}">36个月</c:when>--%>
<%--	                        <c:otherwise>未知</c:otherwise>--%>
<%--	                        </c:choose>--%>
<%--						</td>--%>
						<td>${data.taskProcesser}</td>
						<td>${data.orgName}</td>
						<td>${data.verifyName}</td>
					</tr>
				</c:forEach>
			</table>
	
			<div id="pageStyle">
			${ pm.pageNavigation}
			</div>
		</form>
		
	</div>
	
</body>

<script language="javascript">

//全选、取消全选的事件  
function selectAll(){  
    if ($("#SelectAll").attr("checked")) {  
        $("input[id='item']").attr("checked", true); 
    } else {
        $("input[id='item']").attr("checked", false); 
    }  
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


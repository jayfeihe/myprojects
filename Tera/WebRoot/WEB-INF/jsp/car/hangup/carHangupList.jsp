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
<title>用户信息列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<%--<body>a</body>--%>

<body>

	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
				<!-- <a href="javascript:void(0);" onclick="add();"><img src="img/square/but_add.png" class='dotimg' title="添加" /></a>&nbsp; -->
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">登录ID</th>
					<th scope="col">姓名</th>
					<th scope="col">状态</th>
					<th scope="col" width="160">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td style="text-align: center;">${data.loginId}</td>
						<td style="text-align: center;">${data.name}</td>
						<td style="text-align: center;">
							<c:choose>
								<c:when test="${data.state=='0'}">离职</c:when>
								<c:when test="${data.state=='1'}">正常</c:when>
								<c:when test="${data.state=='2'}">挂起</c:when>
								<c:otherwise>未知</c:otherwise>
							</c:choose>	
						</td>
						<c:if test="${data.state=='0'}">
							<td style="text-align: center;">
								已离职
							</td>
						</c:if>
						<c:if test="${data.state=='1'}">
							<td style="text-align: center;">
								<a href="javascript:void(0);" onclick="javascript:upordown('${ data.id}');">点击挂起</a>
							</td>
						</c:if>
						<c:if test="${data.state=='2'}">
							<td style="text-align: center;">
								<a href="javascript:void(0);" onclick="javascript:upordown('${ data.id}');">解除挂起</a>
							</td>
						</c:if>
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
//挂起与解除
function upordown(data_id) {
	window.location = "<%=basePath%>car/hangup/upordown.do?id=" + data_id;
	//window.location.reload();
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


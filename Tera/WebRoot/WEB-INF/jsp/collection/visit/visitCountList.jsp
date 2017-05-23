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
<title>落地催分单统计列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr> 
					<th scope="col">序号</th>
					<th scope="col">分单总计</th>
					<th scope="col">催收总额</th>
					<th scope="col">最早分单日期</th>
					<th scope="col">催收人</th>
					<th scope="col">部门</th>
				</tr>
				
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						 <td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						 <td>${data.taskNum}</td>
						 <td>${data.monthAmountAll}</td>
						 <td>${data.distributionDateStr}</td>
						 <td>${data.collectionUidName}</td>
						 <td>${data.orgName}</td>
				</c:forEach>
			</table>
	
			<div id="pageStyle">
			${ pm.pageNavigation}
			</div>
		</form>
	</div>
</body>
</html>


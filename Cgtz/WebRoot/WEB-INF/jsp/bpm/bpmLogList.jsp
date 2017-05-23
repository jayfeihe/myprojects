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
<title>流程日志列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<table width="100%" class="datatable">
	<thead>
		<tr>
			<th>序号</th>
			<th>处理人</th>
			<th>接收时间</th>
			<th>处理时间</th>
			<th>节点</th>
			<th>决策结果</th>
			<th>决策说明</th>
		</tr>
	</thead>
	<c:forEach items="${bpmLogs }" var="data" varStatus="status">
		<tbody>
			<tr>
				<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
				<td>${data.operatorName}</td>
				<td><fmt:formatDate value="${data.intime}" type="both"/></td>
				<td><fmt:formatDate value="${data.outtime}" type="both"/></td>
				<td>${data.state}</td>
				<td>${data.decision }</td>
				<td>${data.remark }</td>
			</tr>
		</tbody>
	</c:forEach>
</table>
</body>
</html>
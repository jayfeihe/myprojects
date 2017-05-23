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
<title>接口对接,日志列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
				
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
		
<th scope="col">接口类型</th>
<th scope="col">业务标识</th>
<th scope="col">参数</th>
<th scope="col">发送次数</th>
<th scope="col">反馈代码</th>
<th scope="col">反馈信息</th>
<th scope="col">状态</th>
<th scope="col">创建时间</th>


					
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
<td>${data.type}</td>
<td>${data.bizKey}</td>
<td>${data.para}</td>
<td>${data.count}</td>
<td>${data.rspCode}</td>
<td>${data.rspMsg}</td>
<td>
	 <c:choose>
			<c:when test="${data.state eq 2}">成功</c:when>
			<c:when test="${data.state eq 3}">失败</c:when>
		</c:choose>
</td>
<td>${data.createTime}</td>


						
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

</script>
</html>


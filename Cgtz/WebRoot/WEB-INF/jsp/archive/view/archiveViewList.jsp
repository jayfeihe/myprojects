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
<title>贷前核帐列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">合同编号</th>
					<th scope="col">姓名/机构名称</th>
					<th scope="col">借款金额</th>
					<th scope="col">类型</th>
					<th scope="col">合同开始日期</th>
					<th scope="col">合同结束日期</th>
					
					<th scope="col">分公司</th>

					<th scope="col" width="160">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>
							${data.contractId}
						</td>
						<td>${data.name}</td>
						<td>
							<fmt:formatNumber value="${data.loanAmt}" type="currency"/>元
						</td>
						<td>${data.type}</td>
						<td>
							<fmt:formatDate value="${data.startDate }" type="date"/>
						</td>
						<td>
							<fmt:formatDate value="${data.endDate }" type="date"/>
						</td>
						<td>${data.orgName}</td>
						<td>
							<a href="javascript:void(0);" onclick="javascript:updateData('${ data.id}','${data.name }');">查看</a>
						</td>
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

//更新
function updateData(data_id,name) {
	var url = "<%=basePath%>" + "archive/view/update.do?id=" + data_id;
	addTab(name,url);
	return;
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


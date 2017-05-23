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
<title>复核分单列表</title>
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
					<th scope="col">合同编号</th>
					<th scope="col">决策渠道</th>
					<th scope="col">决策产品</th>
					<th scope="col">进件时间</th>
					<th scope="col">营业部名称</th>
					<th scope="col">营销人员</th>
					<th scope="col">当前复核人</th>
				</tr>

				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<th scope="col"><input type="checkbox" id="item" name="appIds" value="${data.appId}" /></th>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td><a href="house/app/read.do?id=${data.id}" target="_blank">${data.appId}</a></td>
						<td>${data.name}</td>
						<td>${data.contractNo}</td>
						<td>${data.decisionChannelName}</td>
						<td>${data.decisionProduct}</td>
						<td>${data.inputTimeStr}</td>
						<td>${data.orgName}</td>
						<td>${data.staffName}</td>
						<td>${data.taskProcesser}</td>
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


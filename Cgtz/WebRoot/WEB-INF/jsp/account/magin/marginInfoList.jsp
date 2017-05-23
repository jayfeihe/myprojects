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
<title>保证金信息表列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
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
					<th scope="col">分公司</th>
					<th scope="col">合同号</th>
<th scope="col">借款人</th>
<th scope="col">开始时间</th>
<th scope="col">结束时间</th>
<th scope="col">合同金额</th>
<th scope="col">保证金</th>
<th scope="col">状态</th>
<th scope="col">处理时间</th>
<th scope="col">说明</th>
					<th scope="col" width="160">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
<td>${data.orgName}</td>
<td>${data.contractId}</td>
<td>${data.loanName}</td>
<td>${data.startDateStr}</td>
<td>${data.endDateStr}</td>
<td>
<fmt:formatNumber type="currency">${data.conAmt}</fmt:formatNumber>元
</td>
<td>
<fmt:formatNumber type="currency">${data.amt}</fmt:formatNumber>元
</td>
<td>
<c:choose>
<c:when test="${data.state eq '1'}">未处理</c:when>
<c:when test="${data.state eq '2'}">退还借款人</c:when>
<c:when test="${data.state eq '3'}">垫付本金</c:when>
</c:choose>
</td>
<td>${data.handleTimeStr}</td>
<td>${data.remarks}</td>

						<td><%-- <a href="javascript:void(0);" onclick="javascript:readData('${ data.id}');">查看</a>&nbsp; --%>
							<c:if test="${data.state eq '1'}"><a href="javascript:void(0);" onclick="javascript:updateData('${ data.id}','${data.num }');">处理</a>&nbsp;</c:if>
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
function readData(data_id){
	var title="【处理编号-"+data_id+"】查看处理";
	var url="<%=basePath%>" + "account/magin/read.do?id=" + data_id;
	addTab(title,url);
	return;
}
//更新
function updateData(data_id,num) {
	var title="【处理编号-"+data_id+"】保证金处理";
	var url= "<%=basePath%>" + "account/magin/update.do?id=" + data_id+"&num="+num;
	addTab(title,url);
	return;
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


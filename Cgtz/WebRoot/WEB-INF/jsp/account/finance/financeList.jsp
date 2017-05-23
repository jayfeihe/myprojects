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
					<th scope="col">申请编号</th>
					<th scope="col">姓名/机构名称</th>
					<th scope="col">证件号码</th>
					<th scope="col">提交审核时间</th>
					<th scope="col">借款金额</th>
					<th scope="col">产品</th>
					<th scope="col">是否续贷</th>
					<th scope="col">续贷次数</th>
					<th scope="col">业务员</th>
					<th scope="col">所属机构</th>
					<th scope="col">状态</th>

					<th scope="col" width="160">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr <c:if test="${data.isEnough eq '0' }">style="color:red"</c:if> >
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>
							<a href="loan/allRead.do?id=${data.id }" target="_blank">${data.loanId}</a> 
						</td>
						<td>${data.name}</td>
						<td>${data.idNo}</td>
						<td>${data.acctTimeStr}</td>
						<td>
							<fmt:formatNumber value="${data.loanAmt}" type="currency"/>元
						</td>
						<td>${data.productName}</td>
						<td>
							<c:choose>
								<c:when test="${data.isRenew eq '0' }">否</c:when>
								<c:when test="${data.isRenew eq '1' }">是</c:when>
							</c:choose>
						</td>
						<td>${data.renewNum}</td>
						<td>${data.salesman}</td>
						<td>${data.orgName}</td>
						<td>${data.appState}</td>
						<td>
							<a href="javascript:void(0);" onclick="javascript:updateData('${ data.id}','${data.contractId }','${data.name }','${data.productName }');">审核</a>&nbsp;
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
function updateData(data_id,cont_id,name,product_type) {
	var url = "<%=basePath%>" + "account/finance/update.do?id=" + data_id + "&contractId=" + cont_id;
	addTab(name+"("+product_type+")",url);
	return;
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


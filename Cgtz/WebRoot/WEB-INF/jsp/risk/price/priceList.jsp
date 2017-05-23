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
<title>质押、抵押物信息列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
				<a href="javascript:void(0);" onclick="refresh();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
		
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">押品编号</th>
					<th scope="col">类型</th>
					<th scope="col">品种</th>
					<th scope="col">录入时间</th>
					<th scope="col">初始估值</th>
					<th scope="col">最新估值</th>
					<th scope="col">资产所在地址</th>
					
					<th scope="col">当前状态</th>
					<th scope="col">核价状态</th>
					<th scope="col" width="160">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.id }</td>
						<td>
							<c:choose>
								<c:when test="${data.type eq '01'}">车</c:when>
								<c:when test="${data.type eq '02'}">车商</c:when>
								<c:when test="${data.type eq '03'}">房</c:when>
								<c:when test="${data.type eq '04'}">红木</c:when>
								<c:when test="${data.type eq '05'}">海鲜</c:when>
								<c:when test="${data.type eq '99'}">其他</c:when>
							</c:choose>
						</td>
						<td>${data.var}</td>
						<td>${data.createTimeStr}</td>
						<td>
							<fmt:formatNumber value="${data.evalPrice}" type="currency"/>元
						</td>
						<td>
							<fmt:formatNumber value="${data.latestPrice}" type="currency"/>元
						</td>
						<td>${data.assetAddr}</td>
						<td>
							<c:choose>
								<c:when test="${data.state eq '0'}">未处置</c:when>
								<c:when test="${data.state eq '1'}">正常</c:when>
								<c:when test="${data.state eq '2'}">已处置</c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${data.auditPriceState eq '0'}">未处理</c:when>
								<c:when test="${data.auditPriceState ne '0'}">已处理</c:when>
							</c:choose>
						</td>
						<td>
							<a href="javascript:void(0);" onclick="javascript:updateData('${ data.id}','${loanId }','${data.type }');">核价</a>&nbsp;
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
function updateData(data_id,loan_id,collateral_type) {
	var url = "<%=basePath%>" + "risk/price/update.do?id=" + data_id +"&loanId="+loan_id+"&type="+collateral_type;;
	var title = "";
	if("01" == collateral_type) {
		title = "车";
	}
	if("02" == collateral_type) {
		title = "车商";
	}
	if("03" == collateral_type) {
		title = "房";
	}
	if("04" == collateral_type) {
		title = "红木";
	}
	if("05" == collateral_type) {
		title = "海鲜";
	}
	if("99" == collateral_type) {
		title = "其他";
	}
	addPriceTab(title+"-"+data_id,url);
	return;
}

function refresh() {
	refreshTab('riskFirstUpdateTabs');
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


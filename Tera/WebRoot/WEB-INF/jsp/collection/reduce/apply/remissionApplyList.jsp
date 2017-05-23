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
<title>减免申请列表</title>
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
					<th scope="col">客户姓名</th>
					<th scope="col">身份证号</th>
					<th scope="col">合同金额</th>
					<th scope="col">产品</th>
					<th scope="col">还款日期</th>
					<th scope="col">逾期天数</th>
					<th scope="col">营业部</th>
					<th scope="col">催收员</th>
					<th scope="col">状态</th>

					<th scope="col" width="auto">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.contractNo}</td>
						<td>${data.name}</td>
						<td>${data.idNo}</td>
						<td>
							<fmt:formatNumber value="${data.contractAmount/10000}" type="currency"/>万元	
						</td>
						<td>${data.product}</td>
						<td>${data.firstRepayDate}</td>
						<td>${data.overdueDays} 天</td>
						<td>${data.orgName}</td>
						<td>${data.collectionUid}</td>
						<td>
							<c:choose>
								<c:when test="${data.currentState eq '1'}">减免复核</c:when>
								<c:when test="${data.currentState eq '2'}">减免审批</c:when>
								<c:when test="${data.currentState eq '3'}">高级审批</c:when>
								<c:when test="${data.currentState eq '4'}">减免生效</c:when>
								<c:when test="${data.currentState eq '5'}">复核否决</c:when>
								<c:when test="${data.currentState eq '6'}">审批否决</c:when>
								<c:when test="${data.currentState eq '0'}">减免无效</c:when>
							</c:choose>
						</td>

						<td>
							<a href="javascript:void(0);" onclick="javascript:updateData('${data.contractNo}','${data.name}');">详情</a>&nbsp;
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
	var url="<%=basePath%>collection/reduce/apply/repayPlanDetail.do?contractNo="+data_id;
	addTab(name, url);
    return null;
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


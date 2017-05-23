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
<title>车贷反欺诈表列表</title>
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
					<th scope="col">申请ID</th>
					<th scope="col">姓名</th>
<!-- 					<th scope="col">提交说明</th> -->
<!-- 					<th scope="col">结果</th> -->
<!-- 					<th scope="col">结果说明</th> -->
<!-- 					<th scope="col">提交人</th> -->
					<th scope="col">借款金额</th>
					<th scope="col">渠道</th>
					<th scope="col">产品</th>
<%--					<th scope="col">期限</th>--%>
<%--					<th scope="col">营销人员</th>--%>
					<th scope="col">操作员</th>
					<th scope="col">状态</th>
					<th scope="col">所属机构</th>
					<th scope="col">创建日期</th>
					<th scope="col">修改日期</th>
<%--					<th scope="col">操作</th>--%>
					<c:if test="${stateType=='waitTask'}">
						<th scope="col">操作</th>
					</c:if>
					<c:if test="${stateType=='inTask'||stateType=='offTask'}">
						<th scope="col">当前处理状态</th>
					</c:if>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td><a href="car/app/read.do?id=${data.id}" target="_blank">${data.appId}</a></td>
						<td>${data.name}</td>
<%-- 						<td>${data.submitInfo}</td> --%>
<%-- 						<td>${data.result}</td> --%>
<%-- 						<td>${data.resultInfo}</td> --%>
<%-- 						<td>${data.submitOperator}</td> --%>
						<td>
<%--							${data.amount/10000}万元--%>
							<fmt:formatNumber value="${data.amount/10000}" type="currency"/>万元
						</td>
						<td>${data.belongChannelName}</td>
						<td>${data.product}</td>
<%--						<td>${data.period}个月</td>--%>
<%--						<td>${data.staffNo}</td>--%>
						<td>${data.operator}</td>
						<td>
<%--							${data.state}--%>
							<c:choose>
								<c:when test="${data.state=='0'}">前端拒贷</c:when>
								<c:when test="${data.state=='1'}">录入申请</c:when>
								<c:when test="${data.state=='2'}">审核退回</c:when>
								<c:when test="${data.state=='5'}">核价退回</c:when>
								<c:when test="${data.state=='8'}">核价</c:when>
								<c:when test="${data.state=='3'}">审核</c:when>
								<c:when test="${data.state=='4'}">审批退回</c:when>
								<c:when test="${data.state=='6'}">审批</c:when>
								<c:when test="${data.state=='7'}">特殊审批退回</c:when>
								<c:when test="${data.state=='10'}">特殊审批</c:when>
								<c:when test="${data.state=='13'}">签约</c:when>
								<c:when test="${data.state=='14'}">撮合已完成</c:when>
								<c:when test="${data.state=='15'}">复核退回</c:when>
								<c:when test="${data.state=='17'}">复核</c:when>
								<c:when test="${data.state=='18'}">放款退回</c:when>
								<c:when test="${data.state=='19'}">放款</c:when>
								<c:when test="${data.state=='20'}">已确认放款</c:when>
								<c:when test="${data.state=='21'}">放款成功</c:when>
								<c:when test="${data.state=='22'}">放款失败</c:when>
								<c:when test="${data.state=='23'}">放款完成</c:when>
								<c:when test="${data.state=='24'}">拒贷</c:when>
								<c:otherwise>未知</c:otherwise>
							</c:choose>	
						</td>
						<td>${data.orgId}</td>
						<td>${data.createTimeStr}</td>
						<td>${data.updateTimeStr}</td>
<%--						<td>--%>
<%--							<a href="javascript:void(0);" onclick="javascript:updateData('${ data.appId}');">更新</a>&nbsp;--%>
<%--						</td>--%>
						
						<c:if test="${stateType=='waitTask'}">
							<td>
								<a href="javascript:void(0);" onclick="javascript:updateData('${data.appId}');">更新</a>&nbsp;
							</td>
						</c:if>
						<c:if test="${stateType=='inTask'||stateType=='offTask'}">
							<td>
								${data.taskState}
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
//更新
function updateData(data_appId) {
	window.location = "<%=basePath%>" + "car/antifraud/update.do?appId=" + data_appId;
	return;
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


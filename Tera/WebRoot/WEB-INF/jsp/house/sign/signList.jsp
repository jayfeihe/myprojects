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
<title>签约列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">申请编号</th>
					<th scope="col">客户姓名</th>
 					<th scope="col">身份证号</th>
					<th scope="col">进件时间</th>
					<th scope="col">决策时间</th>
					<th scope="col">金额</th>
					<th scope="col">渠道</th>
					<th scope="col">产品</th>
					<th scope="col">期限</th>
					<th scope="col">营业部</th>
					<th scope="col">营销人员</th>
					<th scope="col">签约状态</th>
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
						<td><a href="house/app/read.do?id=${data.id}" target="_blank">${data.appId}</a></td>
						<td>${data.name}</td>
						<td>${data.idNo}</td>
						<td>${data.inputTimeStr}</td>
						<td>${data.decisionUpdateTimeStr}</td>
						<td>
<%--							${data.decisionAmount/10000}--%>
							<fmt:formatNumber value="${data.decisionAmount/10000}" type="currency"/>万元	
						</td>
						<td>${data.decisionChannelName}</td>
						<td>${data.decisionProduct}</td>
						<td>${data.decisionPeriod}个月</td>
<%--						<td>--%>
<%--							<c:choose>--%>
<%--	                        <c:when test="${data.decisionPeriod=='1'}">12个月</c:when>--%>
<%--	                        <c:when test="${data.decisionPeriod=='2'}">18个月</c:when>--%>
<%--	                        <c:when test="${data.decisionPeriod=='3'}">24个月</c:when>--%>
<%--	                        <c:when test="${data.decisionPeriod=='4'}">36个月</c:when>--%>
<%--	                        <c:otherwise>未知</c:otherwise>--%>
<%--	                        </c:choose>--%>
<%--						</td>--%>
						<td>${data.orgName}</td>
						<td>${data.staffName}</td>
						<td>
							<c:choose>
								<c:when test="${'13'==data.state}">
									未撮合
								</c:when>
								<c:when test="${'14'==data.state}">
									已撮合
								</c:when>
								<c:when test="${'15'==data.state}">
									复核退回
								</c:when>
							</c:choose>
						</td>
<%--						<td>--%>
<%--							<a href="javascript:void(0);" onclick="javascript:updateData('${ data.id}');">签约</a>&nbsp;--%>
<%--						</td>--%>
						<c:if test="${stateType=='waitTask'}">
						<td>
							<a href="javascript:void(0);" onclick="javascript:updateData('${ data.id}');">签约</a>&nbsp;
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

//全选、取消全选的事件  
function selectAll(){  
    if ($("#SelectAll").attr("checked")) {  
        $("input[id='item']").attr("checked", true); 
    } else {
        $("input[id='item']").attr("checked", false); 
    }  
}

//更新
function updateData(data_id) {
	window.location = "<%=basePath%>" + "house/sign/update.do?id=" + data_id;
	return;
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


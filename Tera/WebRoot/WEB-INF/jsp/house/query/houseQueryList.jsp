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
<title>房贷查询列表</title>
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
					<th scope="col">姓名</th>
					<th scope="col">进件时间</th>
					<th scope="col">借款金额</th>
					<th scope="col">申请渠道</th>
					<th scope="col">申请产品</th>
					<th scope="col">申请期限</th>
					
					
					<th scope="col">决策时间</th>
					<th scope="col">决策渠道</th>
					<th scope="col">决策产品</th>
					<th scope="col">决策期限</th>
					<th scope="col">到手金额</th>
					<th scope="col">月还款额</th>
					<th scope="col">合同金额</th>
					
					
					<th scope="col">状态</th>
					<th scope="col">营业部</th>
				</tr>

				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td><a href="house/query/read.do?id=${data.id}" target="_blank">${data.appId}</a></td>
						<td>${data.name}</td>
						<td>${data.inputTimeStr}</td>
						<td>
							<fmt:formatNumber value="${data.amount/10000}" type="currency"/>万元	
						</td>
						<td>${data.belongChannelName}</td>
						<td>${data.product}</td>
						<td>${data.period}期</td>
						<td>${data.decisionDateStr}</td>
						<td>${data.decisionChannelName}</td>
						<td>${data.decisionProduct}</td>
						<td>
							<c:if test="${data.decisionPeriod != 0}">${data.decisionPeriod }期</c:if>
						</td>
						<td>
							<c:if test="${null != data.decisionAmount && data.decisionAmount != 0.0  }"><fmt:formatNumber value="${data.decisionAmount}" type="currency"/>元</c:if>
						</td>
						<td>
							<c:if test="${null != data.methodAmount && data.methodAmount != 0.0  }"><fmt:formatNumber value="${data.methodAmount}" type="currency"/>元</c:if>
						</td>
						<td>
							<c:if test="${null != data.contractAmount && data.contractAmount != 0.0  }"><fmt:formatNumber value="${data.contractAmount}" type="currency"/>元</c:if>
						</td>
						<td>
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
//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


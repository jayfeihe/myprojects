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
<title>审批统计列表</title>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="">
			<div id="control" class="control">
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">审批人员</th>
					<th scope="col">处理总量</th>
					<th scope="col">通过量</th>
					<th scope="col">拒贷量</th>
					<th scope="col">待处理量</th>
					<th scope="col">回退量1</th>
					<th scope="col">回退量2</th>
					<th scope="col">通过率</th>
					<th scope="col">回退率</th>
					<th scope="col">放款金额</th>
				</tr>

				<c:forEach items="${statisticsList}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.count}</td>
						<td>${data.name}</td>
						<td>${data.totalAmount}</td>
						<td>${data.passAmount}</td>
						<td>${data.denyAmount}</td>
						<td>${data.opreateAmount}</td>
						<td>${data.backAmount1}</td>
						<td>${data.backAmount2}</td>
						<td>${data.passPercent}%</td>
						<td>${data.backPercent}%</td>
						<td>
							<fmt:formatNumber value="${data.loanAmount}" type="currency"/>万元
						</td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</div>
</body>
</html>


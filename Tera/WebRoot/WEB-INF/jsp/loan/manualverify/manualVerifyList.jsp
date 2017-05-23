<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />
		</style>
	</head>
	<body>
		
		
		<c:if test="${! empty pm}">
		
				<div class="content">
					<form id="selectedLoanPerson" >
					<div id="control" class="control">
					</div>						
						<table id="table" class="datatable" summary="list of members in EE Studay">
							<tr>
								<th scope="col">序号</th>
								<th scope="col">借款申请号</th>
								<th scope="col">客户姓名</th>
								<th scope="col">申请时间</th>
								<th scope="col">用途</th>
								<th scope="col">产品</th>
								<th scope="col">金额</th>
								<th scope="col">期限</th>
								<th scope="col">撮合次数</th>
								<th scope="col">操作</th>
							</tr>
							<c:forEach items="${ pm.data}" var="data" varStatus="status">
								<tr>
									<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
									<td>${data.loanAppId}</td>
									<td>${data.name}</td>
									<td>
										<fmt:formatDate value="${data.appTimeStr}" pattern="yyyy-MM-dd HH:mm:ss"/>

									</td>
									<td>${data.useage}</td>
									<td>${data.loanProduct}</td>
<%--									<td>${data.loanAmount}</td>--%>
									<td><fmt:formatNumber value="${data.loanAmount}" type="currency"/></td>
									<td>${data.loanPeriod}个月</td>
									<td>${data.times}</td>
									<td>
										<a href="javascript:void(0);" onclick="javascript:updateData('${data.loan_id}');">操作</a>&nbsp;
									</td>
								</tr>
							</c:forEach>
						</table>
				
						<div id="pageStyle">
							${ pm.pageNavigation}
						</div>
					</from>
					
				</div>
			</c:if>
				
	</body>

<script type="text/javascript">

//更新
function updateData(data_id) {
	
	window.location = "<%=basePath%>loan/manualverify/update.do?id=" + data_id;
	
	return;
}

$(document).ready(function() {

});
</script>
</html>
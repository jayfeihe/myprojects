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
<title>线下还本计划表</title>
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
					<th scope="col">项目编号</th>
					<th scope="col">线下债权编号</th>
					<th scope="col">项目标题</th>
					<th scope="col">合同开始时间</th>
					<th scope="col">合同结束时间</th>
					<th scope="col">项目开始时间</th>
					<th scope="col">已销售金额</th>
					<th scope="col">债权人</th>
					<th scope="col">区域</th>
					<th scope="col">借款人</th>
					<th scope="col">项目天数</th>
					<th scope="col">本期天数</th>
					<th scope="col">利率</th>
					<th scope="col">付息期数</th>
					<th scope="col">类型</th>
					<th scope="col">应付息时间</th>
					<th scope="col">应付息金额</th>
					<th scope="col">应付本金额</th>
           
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.project_id }</td>
						<td>${data.serial_number }</td>
						<td>${data.title }</td>
						<td>${data.start_time }</td>
						<td>${data.end_time }</td>
						<td>${data.online_time }</td>
						<td><fmt:formatNumber value="${data.sale_amout_money }" type="currency"/>元</td>
						<td>${data.original_creditor_name }</td>
						<td>${data.area }</td>
						<td>${data.borrower_people_name }</td>
						<td>${data.project_days }</td>
						<td>${data.current_period }</td>
						<td>${data.check_annualized_rate }</td>
						<td>${data.debt_interest_no }</td>
						<td>
							<c:choose>
								<c:when test="${data.debt_type eq '0' }">债权转让</c:when>
								<c:when test="${data.debt_type eq '1' }">直投</c:when>
							</c:choose> 
						</td>
						<td>${data.rate_end_time }</td>
						<td><fmt:formatNumber value="${data.sum_payable_interest }" type="currency"/>元</td>  
						<td><fmt:formatNumber value="${data.payable_principal }" type="currency"/>元</td>  
					</tr>
				</c:forEach>
			</table>
	
			<div id="pageStyle">
			${ pm.pageNavigation}
			</div>
		</form>
	</div>
</body>
</html>


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
<title>还款计划计算</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" >
			<!-- <div id="control" class="control">
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div> -->
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<!-- <th scope="col">合同号</th>   -->
					<th scope="col">还款日</th> 
					<th scope="col">期数</th>      
					<th scope="col">类别</th> 
					<th scope="col">应收合计</th>     
					<th scope="col">应收本金</th>  
					<th scope="col">应收利息</th> 
					<th scope="col">应收会员服务费</th>
					<th scope="col">应收法律服务费</th>
					<th scope="col">应收转贷费</th> 
					<th scope="col">应收保证金</th>  
					<th scope="col">应收管理服务费</th> 
					<th scope="col">应收他项权证费</th> 
					<th scope="col">应收评估费</th> 
					<th scope="col">应收中介费</th> 
					<th scope="col">应收其他费</th>
					
				</tr>
				<c:forEach items="${retList}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.count}</td>
						<%-- <td>${data.contractId  }</td> --%>
						<td>${data.retDateStr  }</td>
						<td>${data.num         }</td>
						<td>
							<c:choose>
								<c:when test="${data.type eq '1' }">收利息</c:when>
								<c:when test="${data.type eq '2' }">收本金</c:when>
							</c:choose>
						</td>
						<td><fmt:formatNumber value="${data.planAmt }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.planCapital }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.planInterest }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.planMemFee  }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.planLawFee  }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.planTranFee }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.planMargin  }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.planMgrFee  }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.planCertFee  }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.planEvalFee  }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.planAgentFee  }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.planOtherFee }" type="currency"/>元</td>
						
					</tr>
				</c:forEach>
			</table>
		</form>
	</div>
</body>
</html>


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
<title>综合查询列表</title>
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
					<th scope="col">借款金额</th>
					<th scope="col">产品</th>
					<th scope="col">是否续贷</th>
					<th scope="col">续贷次数</th>
					<th scope="col">抵押物是否足额</th>
					
					<th scope="col">申请录入时间</th>
					<th scope="col">提交分公司时间</th>
					<th scope="col">提交风控初审时间</th>
					<th scope="col">提交风控复核时间</th>
					<th scope="col">提交评审会初审时间</th>
					<th scope="col">提交评审会复核时间</th>
					
					<th scope="col">提交法务时间</th>
					<th scope="col">提交出纳时间</th>
					<th scope="col">提交财务时间</th>
					<th scope="col">放款时间</th>
					
					
					<th scope="col">业务员</th>
					<th scope="col">分公司审批人员</th>
					<th scope="col">风控初审人员</th>
					<th scope="col">风控复核人员</th>
					<th scope="col">评审会初审人员</th>
					<th scope="col">评审会复核人员</th>
					
					<th scope="col">法务初审人员</th>
					<th scope="col">法务内勤人员</th>
					<th scope="col">法务复核人员</th>
					<th scope="col">出纳核帐人员</th>
					<th scope="col">财务核帐人员</th>
					<th scope="col">放款人员</th>
					
					<th scope="col">待处理人</th>
					
					<th scope="col">所属机构</th>
					<!-- <th scope="col">节点状态</th> -->
					<th scope="col">申请状态</th>

				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr <c:if test="${data.isEnough eq '0' }">style="color:red"</c:if> >
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>
							<a href="integrated/read.do?id=${data.id }&contractId=${data.contractId}" target="_blank">${data.loanId}</a> 
						</td>
						<td>${data.name}</td>
						<td>${data.idNo}</td>
						<td>
							<fmt:formatNumber value="${data.loanAmt}" type="currency"/>元
						</td>
						<td>${data.productName}</td>
						<td>
							<c:choose>
								<c:when test="${data.isRenew eq '0' }">否</c:when>
								<c:when test="${data.isRenew eq '1' }">是</c:when>
								<c:when test="${data.isRenew eq '2' }">有续贷</c:when>
							</c:choose>
						</td>
						<td>${data.renewNum}</td>
						<td>
							<c:choose>
								<c:when test="${data.isEnough eq '0' }">否</c:when>
								<c:when test="${data.isEnough eq '1' }">是</c:when>
							</c:choose>
						</td>
						
						<td>${data.inputTimeStr}</td>
						<td>${data.branchAuditTimeStr}</td>
						<td>${data.riskFirstTimeStr}</td>
						<td>${data.riskCheckTimeStr}</td>
						<td>${data.meetFirstTimeStr}</td>
						<td>${data.meetCheckTimeStr}</td>
						
						<td>${data.lawTimeStr}</td>
						<td>${data.cashTimeStr}</td>
						<td>${data.acctTimeStr}</td>
						<td>${data.loanTimeStr}</td>
						
						<td>${data.salesman}</td>
						<td>${data.branchAuditUser}</td>
						<td>${data.riskFirstAuditUser}</td>
						<td>${data.riskCheckUser}</td>
						<td>${data.meetFirstAuditUser}</td>
						<td>${data.meetCheckUser}</td>
						
						<td>${data.lawFirstUser}</td>
						<td>${data.lawInsideUser}</td>
						<td>${data.lawReviewUser}</td>
						<td>${data.cashUser}</td>
						<td>${data.acctUser}</td>
						<td>${data.loanUser}</td>
						
						<td>${data.processerName}</td>
						
						<td>${data.orgName}</td>
						<%-- <td>${data.bpmState}</td> --%>
						<td>${data.appState}</td>
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


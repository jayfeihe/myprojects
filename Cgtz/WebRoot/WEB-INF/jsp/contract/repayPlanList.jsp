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
<title>还款计划列表</title>
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
					<th scope="col">合同号</th>  
					<th scope="col">还款日</th> 
					<th scope="col">逾期天数</th> 
					<th scope="col">期数</th>      
					<th scope="col">类别</th>    
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
					<th scope="col">应收贷前其他费</th>
					<th scope="col">实收本金</th>  
					<th scope="col">实收利息</th> 
					<th scope="col">实收会员服务费</th>
					<th scope="col">实收法律服务费</th>
					<th scope="col">实收转贷费</th> 
					<th scope="col">实收保证金</th>  
					<th scope="col">实收管理服务费</th>  
					<th scope="col">实收他项权证费</th>  
					<th scope="col">实收评估费</th>  
					<th scope="col">实收中介费</th>  
					<th scope="col">实收贷前其他费</th>
					<th scope="col">收罚息</th>
					<th scope="col">收滞纳金</th>
					<th scope="col">收仓储费</th>
					<th scope="col">收贷后其他费</th>
					
			
					<th scope="col">最后还款日期</th>  
					<th scope="col">状态</th>
				</tr>
				<jsp:useBean id="nowDate" class="java.util.Date"/>
				<c:forEach items="${planList}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.count}</td>
						<td>${data.contractId  }</td>
						<td>${data.retDateStr  }</td>
						<td>
					
				  <c:choose>
					<c:when test="${data.state eq '1' }">		
		                  <fmt:formatDate  var="nowStr" value="${nowDate}" pattern="yyyy-MM-dd "/>
						  <fmt:parseDate var="date1"  value="${nowStr}" pattern="yyyy-MM-dd "/>	
						  <fmt:parseDate var="date2"  value="${data.retDateStr}" pattern="yyyy-MM-dd"/>
						  <c:set var="timeMis" value="${date1.time - date2.time}"/>
						   <c:if test="${timeMis >= 0}">
							    <fmt:formatNumber value="${timeMis/1000/3600/24}" pattern="#0"/>
							</c:if>
							 <c:if test="${timeMis < 0}">
							    0
							</c:if>
						 
					</c:when>
					<c:when test="${data.state eq '2' }">0</c:when>	
					<c:when test="${data.state eq '3' }">0</c:when>
					<c:when test="${data.state eq '4' }">0</c:when>
					<c:when test="${data.state eq '5' }">
						  <fmt:parseDate var="date1"  value="${data.lastDateStr}" pattern="yyyy-MM-dd "/>	
						  <fmt:parseDate var="date2"  value="${data.retDateStr}" pattern="yyyy-MM-dd"/>
						  <c:set var="timeMis" value="${date1.time - date2.time}"/>
						 <fmt:formatNumber value="${timeMis/1000/3600/24}" pattern="#0"/>
						
					</c:when>
				</c:choose>
						</td>
						<td>${data.num}</td>
						<td>
							<c:choose>
								<c:when test="${data.type eq '1' }">收利息</c:when>
								<c:when test="${data.type eq '2' }">收本金</c:when>
							</c:choose>
						</td>
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
						<td><fmt:formatNumber value="${data.realCapital }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.realInterest }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.realMemFee  }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.realLawFee  }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.realTranFee }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.realMargin  }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.realMgrFee  }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.realCertFee  }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.realEvalFee  }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.realAgentFee  }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.realOtherFee }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.defaultFee  }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.penaltyFee  }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.storFee  }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.loanOtherFee  }" type="currency"/>元</td>
					
						<td>${data.lastDateStr }</td>
						<td>
							<c:choose>
								<c:when test="${data.state eq '1' }">未还清</c:when>
								<c:when test="${data.state eq '2' }">正常结清</c:when>
								<c:when test="${data.state eq '3' }">提前还款</c:when>
								<c:when test="${data.state eq '4' }">续贷转移</c:when>
								<c:when test="${data.state eq '5' }">逾期结清</c:when>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</div>
</body>
</html>


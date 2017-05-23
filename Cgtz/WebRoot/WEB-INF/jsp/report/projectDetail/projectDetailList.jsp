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
<title>项目明细表</title>
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
					<th scope="col">债权线上编号</th>          
					<th scope="col">债权线下编号</th>          
					<th scope="col">项目名称</th>          
					<th scope="col">合同开始时间</th>  
					<th scope="col">合同结束时间</th>  
					<th scope="col">上线时间</th>    
					<th scope="col">合同实际结束时间</th>  
					<th scope="col">合同天数</th>        
					<th scope="col">合同状态</th>          
					<th scope="col">借款金额</th>          
					<!-- <th scope="col">线上借款金额</th>  -->         
					<th scope="col">原始债权人</th>          
					<th scope="col">借款人</th>          
					<th scope="col">借款利率</th>          
					<th scope="col">线上利率</th>          
					<th scope="col">还款方式</th>          
					<th scope="col">贷款类别</th>          
					<th scope="col">所属分公司</th>          
					<!-- <th scope="col">融资方式</th>          
					<th scope="col">线下类别</th>  -->         
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.onlineConId }</td>    
						<td>${data.contractId }</td>      
						<td>${data.projectName }</td>    
						<td><fmt:formatDate value="${data.startDate }" type="date"/></td>      
						<td><fmt:formatDate value="${data.endDate }" type="date"/></td>        
						<td><fmt:formatDate value="${data.onlineStartDate }" type="date"/></td>
						<td><fmt:formatDate value="${data.realEndDate }" type="date"/></td>    
						<td>${data.contractDays }</td>   
						<td>
							<c:choose>
								<c:when test="${data.contractState eq '1' }">未生效</c:when>
								<c:when test="${data.contractState eq '2' }">合同中</c:when>
								<c:when test="${data.contractState eq '3' }">合同结清</c:when>
								<c:when test="${data.contractState eq '4' }">被拆分</c:when>
							</c:choose>
						</td>  
						<td><fmt:formatNumber value="${data.loanAmt }" type="currency"/>元</td>        
						<%-- <td><fmt:formatNumber value="${data.onlineAmt }" type="currency"/>元</td> --%>      
						<td>${data.lendUser }</td>       
						<td>${data.loanUser }</td>       
						<td>${data.loanRate }%</td>       
						<td>${data.onlineRate }%</td>     
						<%-- <td>${data.onlineRetway }</td>
						<td>${data.onlineType }</td>  --%>  
						<td>
							<c:choose>
								<c:when test="${data.getLoanWay eq '1' }">直投</c:when>
								<c:when test="${data.getLoanWay eq '2' }">债权转让</c:when>
								<c:when test="${data.getLoanWay eq '3' }">线下</c:when>
							</c:choose>
						</td>    
						<td>${data.product }</td>       
						<td>${data.orgName }</td>        
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


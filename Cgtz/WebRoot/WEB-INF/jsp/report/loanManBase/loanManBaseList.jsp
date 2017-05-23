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
<title>借款人基本情况表</title>
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
					<th scope="col">线上编号</th> 
					<th scope="col">线下编号</th> 
					<th scope="col">项目名称</th> 
					<th scope="col">上线时间</th> 
					<th scope="col">开始时间</th> 
					<th scope="col">结束时间</th> 
					<th scope="col">合同金额</th>
					<th scope="col">期数</th> 
					<th scope="col">债权人</th> 
					<th scope="col">借款人</th> 
					<th scope="col">线下利率</th> 
					<th scope="col">线上利率</th>
					<th scope="col">应收本金</th> 
					<th scope="col">实收本金</th> 
					<th scope="col">项目类型</th> 
					<th scope="col">应收利息</th> 
					<th scope="col">实收利息</th> 
					<th scope="col">线上垫付利息</th>
					<th scope="col">收到时间</th> 
					<th scope="col">备注</th>  
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.onlineConId }</td> 	   
						<td>${data.contractId }</td>      
						<td>${data.projectName }</td>     
						<td><fmt:formatDate value="${data.onlineStartDate }" type="date"/></td> 
						<td><fmt:formatDate value="${data.startDate }" type="date"/></td> 	     
						<td><fmt:formatDate value="${data.endDate }" type="date"/></td> 
						<td><fmt:formatNumber value="${data.loanAmt }" type="currency"/>元</td>	       
						<td>${data.num }</td>   
						<td>${data.lendUser }</td>        
						<td>${data.name }</td>            
						<td>${data.rate }%</td>   			   
						<td>${data.onlineRateOut }%</td> 	 
						<td><fmt:formatNumber value="${data.planCapital }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.realCapital }" type="currency"/>元</td> 
						<td>${data.isRenew }</td>  			 
						<td><fmt:formatNumber value="${data.planInterest }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.realInterest }" type="currency"/>元</td>
						<td><fmt:formatNumber value="${data.payOutInterest }" type="currency"/>元</td> 
						<td><fmt:formatDate value="${data.collectDate }" type="date"/></td> 	 
						<td>${data.remark }</td>  	
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


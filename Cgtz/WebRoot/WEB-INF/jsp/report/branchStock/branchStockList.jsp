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
<title>分公司存量统计表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<strong>未到期未收回笔数合计：</strong>${noExpireTotalNum }笔&nbsp;&nbsp;
			<strong>未到期未收回金额合计：</strong><fmt:formatNumber value="${noExpireTotalAmt }" type="currency"/>元&nbsp;&nbsp;
			<br />
			<strong>到期未收回笔数合计：</strong>${expireTotalNum }笔&nbsp;&nbsp;
			<strong>到期未收回金额合计：</strong><fmt:formatNumber value="${expireTotalNum }" type="currency"/>元&nbsp;&nbsp;
			<br />
			<strong>总笔数合计：</strong>${totalTotalNum }笔&nbsp;&nbsp;
			<strong>总金额合计：</strong><fmt:formatNumber value="${totalTotalAmt }" type="currency"/>元&nbsp;&nbsp;
			<br />
			<strong>转贷笔数合计：</strong>${tranTotalNum }笔&nbsp;&nbsp;
			<strong>转贷金额合计：</strong><fmt:formatNumber value="${tranTotalAmt }" type="currency"/>元&nbsp;&nbsp;
			<br />
			
			<div id="control" class="control">
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>		
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">分公司</th>                   
					<th scope="col">产品</th>                     
					<th scope="col">未到期未收回笔数</th>         
					<th scope="col">未到期未收回金额</th>         
					<th scope="col">到期未收回笔数</th>           
					<th scope="col">到期未收回金额</th>           
					<th scope="col">总笔数</th>                   
					<th scope="col">总金额</th>                   
					<th scope="col">转贷笔数</th>                 
					<th scope="col">转贷金额</th>                 
					<th scope="col">转贷比</th>                   
					<th scope="col">转贷次数小于等于3次的金额</th>
					<th scope="col">转贷次数大于3次的金额</th>           
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.orgName }</td>         
						<td>${data.productName }</td>      
						<td>${data.noExpireNum }</td>  
						<td><fmt:formatNumber value="${data.noExpireAmt }" type="currency"/>元</td>  
						<td>${data.expireNum }</td>   
						<td><fmt:formatNumber value="${data.expireAmt }" type="currency"/>元</td>   
						<td>${data.totalNum }</td>     
						<td><fmt:formatNumber value="${data.totalAmt }" type="currency"/>元</td>     
						<td>${data.tranNum }</td>     
						<td><fmt:formatNumber value="${data.tranAmt }" type="currency"/>元</td>     
						<td>${data.tranRate }%</td>   
						<td><fmt:formatNumber value="${data.low3Amt }" type="currency"/>元</td> 
						<td><fmt:formatNumber value="${data.high3Amt }" type="currency"/>元</td> 
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


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
<title>分公司成交量统计表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<strong>续贷项目笔数合计：</strong>${renewTotalNum }笔&nbsp;&nbsp;
			<strong>续贷项目金额合计：</strong><fmt:formatNumber value="${renewTotalAmt }" type="currency"/>元&nbsp;&nbsp;
			<br />
			<strong>新增项目笔数合计：</strong>${newTotalNum }笔&nbsp;&nbsp;
			<strong>新增项目金额合计：</strong><fmt:formatNumber value="${newTotalAmt }" type="currency"/>元&nbsp;&nbsp;
			<br />
			<strong>总计项目笔数合计：</strong>${totalTotalNum }笔&nbsp;&nbsp;
			<strong>总计项目金额合计：</strong><fmt:formatNumber value="${totalTotalAmt }" type="currency"/>元&nbsp;&nbsp;
			<div id="control" class="control">
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>		
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">分公司</th> 
					<th scope="col">项目</th>
					<th scope="col">续贷项目笔数/笔</th>
					<th scope="col">续贷项目金额/元</th>
					<th scope="col">新增项目笔数/笔</th>
					<th scope="col">新增项目金额/元</th>
					<th scope="col">总项目笔数/笔</th>
					<th scope="col">总项目金额/元</th>         
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.orgName }</td>      
						<td>${data.productName }</td>    
						<td>${data.renewNum }</td>    
						<td><fmt:formatNumber value="${data.renewAmt }" type="currency"/>元</td>  
						<td>${data.newNum }</td>    
						<td><fmt:formatNumber value="${data.newAmt }" type="currency"/>元</td>  
						<td>${data.totalNum }</td>    
						<td><fmt:formatNumber value="${data.totalAmt }" type="currency"/>元</td>        
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


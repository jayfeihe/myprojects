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
<title>流水表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
			<!-- <a href="javascript:void(0);" onclick="exportExcel('queryBillAcctFlowForm');"><img src="img/square/export.png" class='dotimg' title="导出" /></a>&nbsp; -->
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>		
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
<th scope="col">线上编号</th>				
<th scope="col">线下编号</th>
<th scope="col">项目名称</th>
<th scope="col">期数</th>
<th scope="col">类别</th>
<th scope="col">金额</th>
<th scope="col">债权人</th>
<th scope="col">借款人</th>
<th scope="col">凭证号</th>
<th scope="col">操作人</th>
<th scope="col">操作时间</th>
<th scope="col">备注</th>
<th scope="col">分公司</th>



				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
<td>${data.onlineConId}</td>
<td>${data.contractId}</td>
<td>${data.projectName}</td>
<td>${data.num}</td>
<td>${data.subject}</td>
<td><fmt:formatNumber value="${data.amt}" type="currency"/>元</td>
<td>${data.lendName}</td>
<td>${data.loanName }</td>
<td>${data.proof }</td>
<td>${data.operName}</td>
<td>${data.operTimeStr}</td>
<td>${data.remark}</td>
<td>${data.orgName}</td>


	
					</tr>
				</c:forEach>
			</table>
	
			<div id="pageStyle">
			${ pm.pageNavigation}
			</div>
		</form>
	</div>
</body>

<script language="javascript">
//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


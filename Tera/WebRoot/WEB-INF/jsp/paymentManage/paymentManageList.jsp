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
<title>还款管理</title>
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
					<th scope="col">合同编号</th>
					<th scope="col">借款人</th>
					<th scope="col">身份证号</th>
					<th scope="col">合同金额</th>
					<th scope="col">渠道</th>
					<th scope="col">产品</th>
					<th scope="col">期限</th>
					<th scope="col">未还期数</th>
					<th scope="col">违约总期数</th>
					<th scope="col">营业部</th>
					<th scope="col">操作</th>

					
				</tr>

				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${status.index+pm.rowS+1}</td>
						<td>
<%-- 						<a href="credit/app/read.do?id=${data.id}" target="_blank"> --%>
						${data.contractNo}
<!-- 						</a> -->
						</td>
						<td>${data.loanName}</td>
						<td>${data.showLoanIdNo}</td>
						<td><fmt:formatNumber value="${data.loanAmount}" type="currency"/>元</td>
						<td>${data.decisionChannelName}</td>
						<td>${data.loanProduct}</td>
						<td>${data.loanPeriod}期</td>
						<td>${data.noPeriodNum}期</td>
						<td>${data.defaultPeriodNum}期</td>
						<td>${data.orgId}</td>
						<td>
							<a href="javascript:void(0);" onclick="javascript:updateData('${data.id}','${data.loanName}');">详情</a>&nbsp;
						</td>
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
//更新
function updateData(data_id,name) {
    var url="<%=basePath%>paymentManage/update.do?id="+data_id;
    addTab(name, url);
    return null;
} 


//页面加载完动作
$(document).ready(function (){
	
});
</script>
</html>


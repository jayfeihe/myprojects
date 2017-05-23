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
<title>签约列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">申请编号</th>
					<th scope="col">客户姓名</th>
 					<th scope="col">身份证号</th>
					<th scope="col">合同编号</th>
					<th scope="col">进件时间</th>
					<th scope="col">审批金额</th>
					<th scope="col">合同金额</th>
					<th scope="col">渠道</th>
					<th scope="col">产品</th>
					<th scope="col">期限</th>
					<th scope="col">营业部</th>
					<th scope="col">操作</th>
				</tr>

				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td><a href="credit/app/read.do?id=${data.appId}" target="_blank">${data.loanAppId}</a></td>
						<td>${data.loanName}</td>
						<td>${data.loanIdNo}</td>
						<td>${data.contractNo}</td>
						<td>${data.inputTimeStr}</td>
						<td>
							<fmt:formatNumber value="${data.decisionAmount/10000}" type="currency"/>万元
						</td>
						<td>
							<fmt:formatNumber value="${data.loanAmount}" type="currency"/>元
						</td>
						<td>${data.decisionChannelName}</td>
						<td>${data.loanProduct}</td>
						<td>${data.loanPeriod}个月</td>
						<td>${data.orgName}</td>
						<td>
							<a href="javascript:void(0);" onclick="javascript:updateData('${ data.id}');">修改</a>&nbsp;
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

//全选、取消全选的事件  
function selectAll(){  
    if ($("#SelectAll").attr("checked")) {  
        $("input[id='item']").attr("checked", true); 
    } else {
        $("input[id='item']").attr("checked", false); 
    }  
}

//更新
function updateData(data_id) {
	window.location = "<%=basePath%>" + "contract/update.do?id=" + data_id;
	return;
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


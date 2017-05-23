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
<title>车贷推送列表</title>
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
					<th scope="col">进件时间</th>
					<th scope="col">决策时间</th>
					<th scope="col">金额</th>
					<th scope="col">渠道</th>
					<th scope="col">产品</th>
					<th scope="col">期限</th>
					<th scope="col">营业部</th>
					<th scope="col">营销人员</th>
					<th scope="col">操作</th>
				</tr>

				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td><a href="car/app/read.do?id=${data.id}" target="_blank">${data.appId}</a></td>
						<td>${data.name}</td>
						<td>${data.idNo}</td>
						<td>${data.inputTimeStr}</td>
						<td>${data.decisionUpdateTimeStr}</td>
						<td>
							<fmt:formatNumber value="${data.decisionAmount/10000}" type="currency"/>万元	
						</td>
						<td>${data.decisionChannelName}</td>
						<td>${data.decisionProduct}</td>
						<td>${data.decisionPeriod}个月</td>
						<td>${data.orgName}</td>
						<td>${data.staffName}</td>
						<td>
							<c:if test="${stateType eq '0'}">
								<a href="javascript:void(0);" onclick="javascript:updateData('${data.id}');">操作</a>&nbsp;
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
	
			<div id="pageStyle">
			${ pm.pageNavigation}
			</div>
		</form>
		
	</div>
	
	<script language="javascript">
		//更新
		function updateData(data_id) {
			window.location = "car/push/update.do?id=" + data_id;
			return;
		}
		
		//页面加载完动作
		$(document).ready(function (){
					
		});
	</script>
</body>
</html>


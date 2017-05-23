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
<title>诉讼列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<c:if test="${opt ne 'read' }">
				<div id="control" class="control">
					<a href="javascript:void(0);" onclick="refresh();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
				</div>
			</c:if>
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">核查对象</th>
					<th scope="col">证件号码</th>
					<th scope="col">手机号</th>
					<th scope="col">诉讼情况</th>
					<th scope="col">诉讼复核</th>

					<th scope="col" width="160">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.checkTarget}</td>
						<td>${data.idNo}</td>
						<td>${data.tel}</td>
						<td>
							<c:choose>
								<c:when test="${empty data.lawState }">未处理</c:when>
								<c:when test="${data.lawState eq '0' }">无</c:when>
								<c:when test="${data.lawState eq '1'}">有</c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${empty data.lawCheckState }">未处理</c:when>
								<c:when test="${data.lawCheckState eq '0' }">不通过</c:when>
								<c:when test="${data.lawCheckState eq '1'}">通过</c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${opt eq 'read' or '1' eq data.isOrig }">
									<a href="javascript:void(0);" onclick="javascript:viewLawData('${ data.id}','${data.checkTarget }','${loanId }','${data.targetType }');">查看</a>&nbsp;
								</c:when>
								<c:otherwise>
									<c:if test="${'0' eq data.isOrig }">
										<a href="javascript:void(0);" onclick="javascript:updateLawData('${ data.id}','${data.checkTarget }','${loanId }','${data.targetType }');">处理</a>&nbsp;
									</c:if>
								</c:otherwise>
							</c:choose>
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
function updateLawData(data_id,name,loan_id,type) {
	url = "<%=basePath%>loan/law/update.do?id=" + data_id + "&loanId=" + loan_id +"&type=" + type;
	addLawTab(name,url);
	return;
}
function viewLawData(data_id,name,loan_id,type) {
	url = "<%=basePath%>loan/law/read.do?id=" + data_id + "&loanId=" + loan_id +"&type=" + type;
	addLawTab(name,url);
	return;
}

function refresh() {
	refreshTab('appUpdateTabs');
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


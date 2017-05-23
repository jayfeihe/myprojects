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
<title>逾期报告列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
				<input type="button" value="添加逾期报告" class="btn" onclick="addTab('添加报告','<%=basePath%>' + 'overdue/report/update.do?loanId=${loanId}&contractId=${contractId}&lateNum=${lateNum}')"/>
			</div>
			<table id="table" class="datatable" summary="list of members in EE Studay">
			
				<tr>
					<th scope="col">序号</th>
					<th scope="col">期数</th>
					<th scope="col">提交人</th>
					<th scope="col">提交时间</th>
					<th scope="col">分公司审核结果</th>
					<th scope="col">分公司审核意见</th>
					<th scope="col">分公司审核人</th>
					<th scope="col">审核时间</th>
					<th scope="col">审批结果</th>
					<th scope="col">审批人</th>
					<th scope="col">审批时间</th>
					<th scope="col" width="160">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
<td>${data.num}</td>
<td>${data.submitName}</td>
<td>${data.submitTmeStr}</td>
<td>
<c:choose>
<c:when test="${data.orgAuditResult eq '0'}">未通过</c:when>
<c:when test="${data.orgAuditResult eq '1'}">已通过</c:when>
</c:choose>
</td>
<td>${data.orgAuditRemark}</td>
<td>${data.orgAuditName}</td>
<td>${data.orgAuditTimeStr}</td>
<td>${data.auditRemark}</td>
<td>${data.auditName}</td>
<td>${data.auditTimeStr}</td>

						<td><a href="javascript:void(0);" onclick="javascript:readData('${ data.id}');">查看</a>&nbsp;
							<c:if test="${data.auditResult ne '3'}"><a href="javascript:void(0);" onclick="javascript:updateData('${ data.id}');">更新</a>&nbsp;</c:if>
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
//查看
function readData(data_id){
    addTab('查看报告','<%=basePath%>' + 'overdue/report/read.do?id=' + data_id);
	return;
}
//更新
function updateData(data_id) {
	addTab('报告添加修改','<%=basePath%>' + 'overdue/report/update.do?id=' + data_id);
	return;
}

//删除
function deleteData(data_id) {
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
				url : "<%=basePath%>" + "overdue/report/delete.do",
				// 防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
				data : encodeURI("id=" + data_id + "&timestamp=" + (new Date()).getTime()),
				async : false,// 同步提交
				success : function(data) {
					if ("true"==data.success) {
						// form刷新防提示
						$.messager.alert('消息', data.message,"info", function(){
			              		window.location = window.location + "&timestamp=" + (new Date()).getTime();
								window.location.reload();
		            	});
					} else {
						$.messager.alert('消息', data.message);
					}
				},
				error : function() {
					$.messager.confirm('消息', '数据加载失败,请联系系统管理员！');
				}
			});
        }
    });
}


//添加
function add(){
	window.location = "<%=basePath%>" + "overdue/report/update.do";
	return;
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


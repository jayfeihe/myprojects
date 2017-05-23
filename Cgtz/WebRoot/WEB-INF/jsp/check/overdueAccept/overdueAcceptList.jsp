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
<title>逾期受理列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
				<input type="button" value="添加逾期申请受理" class="btn" onclick="addTab('添加逾期申请受理','<%=basePath%>' + 'check/overdueAccept/update.do?loanId=${loanId}&contractId=${contractId}')"/>
			</div>
			<table id="table" class="datatable" summary="list of members in EE Studay">		
				<tr>
					<th scope="col">序号</th>
					<th scope="col">申请编号</th>
					<th scope="col">合同编号</th>
					<th scope="col">期数</th>
					<th scope="col">性质</th>
					<th scope="col">产品类型</th>
					<th scope="col">逾期时间</th>
					<th scope="col">金额</th>
					<th scope="col">逾期报告摘要</th>
					<th scope="col" width="160">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
<td>${data.loanId}</td>
<td>${data.contractId}</td>
<td>${data.num}</td>
<td>
<c:choose>
<c:when test="${data.property eq '1' }">利息逾期</c:when>
<c:when test="${data.property eq '2' }">本金逾期</c:when>
</c:choose>
</td>
<td>
<c:choose>
<c:when test="${data.proType eq '01' }">车贷</c:when>
<c:when test="${data.proType eq '02' }">车商贷</c:when>
<c:when test="${data.proType eq '03' }">房贷</c:when>
<c:when test="${data.proType eq '04' }">海鲜贷</c:when>
<c:when test="${data.proType eq '05' }">红木贷</c:when>
<c:when test="${data.proType eq '99' }">其他</c:when>
</c:choose>
</td>
<td>${data.lateDateStr}</td>
<td>
<fmt:formatNumber  type="currency">${data.amt}</fmt:formatNumber>元
</td>
<td>${data.reportSummary}</td>

						<td><a href="javascript:void(0);" onclick="javascript:readData('${ data.id}');">查看</a>&nbsp;
						<a href="check/overdueAccept/print.do?id=${data.id}" target="_blank" style="text-decoration: underline;">打印</a>&nbsp;
							<c:if test="${data.state ne '2'}"><a href="javascript:void(0);" onclick="javascript:updateData('${ data.id}');">修改</a>&nbsp;</c:if>
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
    addTab('查看受理','<%=basePath%>' + 'check/overdueAccept/read.do?id=' + data_id);
	return;
}
//更新
function updateData(data_id) {
	addTab('修改受理','<%=basePath%>' + 'check/overdueAccept/update.do?id=' + data_id);
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


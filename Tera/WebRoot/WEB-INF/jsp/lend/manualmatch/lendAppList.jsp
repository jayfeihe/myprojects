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
<title>财富端申请表列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${pm.url}">
			<div id="control" class="control">
<%-- 				<a href="javascript:thisHref('<%=basePath%>lendApp/update.do?customerType=01');"><img src="img/square/but_add.png" class='dotimg' title="添加个人客户" /></a>&nbsp; --%>
<%-- 				<a href="javascript:thisHref('<%=basePath%>lendApp/update.do?customerType=02');"><img src="img/square/but_add.png" class='dotimg' title="添加机构客户" /></a>&nbsp; --%>
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">申请ID</th>
					<th scope="col">客户类型</th>
					<th scope="col">客户姓名</th>
					<th scope="col">证件号码</th>
					<th scope="col">操作员</th>
					<th scope="col">状态</th>
					<th scope="col">出借金额</th>
					<th scope="col">产品ID</th>
					<th scope="col">服务截至日期</th>
					<th scope="col">创建日期</th>
					<th scope="col">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
<%--						<td><A href="javascript:read('${data.id}','${data.customerType}');">${data.appId}</A></td>--%>
						<td>${data.appId}</td>
						<td>${(data.customerType=='01')?"个人":"企业"}</td>
						<td>${data.name}</td>
						<td>${data.idNo}</td>
						<td>${data.operator}</td>
						<td>${data.state}</td>
<!--						<td>${data.amount}</td>-->
						<td>
<%--							<fmt:parseNumber value="${data.amount}" var="fmtAmount"/>--%>
<%--										${fmtAmount}--%>
							<fmt:formatNumber value="${data.amount}" type="currency"/>
						</td>
						<td>${data.product}</td>
						<td>
							<fmt:formatDate value="${data.serviceEndDate}" pattern="yyyy-MM-dd"/>
						</td>
						<td>
							<fmt:formatDate value="${data.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<a href="javascript:void(0);" onclick="javascript:updateData('${data.id}');">撮合</a>&nbsp;
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
function updateData(data_id) {
	window.location = "<%=basePath%>lend/manualmatch/update.do?id=" + data_id;
	
	return;
}
//删除
function deleteData(data_id) {
	
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){	
			$.ajax({
				url : "<%=basePath%>" + "lendApp/delete.do",
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
						$.messager.alert('消息', data.message,"info");
					}
				}
			});
		}
	});
}
function read(data_id,type){
	window.location = "<%=basePath%>" + "lendApp/read.do?id=" + data_id+"&customerType=" + type;
	
	return;
}
//添加
function add(){
	window.location = "<%=basePath%>" + "lendApp/update.do";
	
	return;
}
function thisHref(url){
	window.location.href=url;
}
</script>

</html>


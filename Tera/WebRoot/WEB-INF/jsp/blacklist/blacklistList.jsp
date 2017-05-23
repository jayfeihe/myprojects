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
<title>黑名单表列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
				<a href="javascript:void(0);" onclick="add();"><img src="img/square/sys_but_add.png" class='dotimg' title="添加" /></a>&nbsp;
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">姓名</th>
					<th scope="col">证件号码</th>
					<th scope="col">申请号</th>
					<th scope="col">手机</th>
					<th scope="col">地址</th>
					<th scope="col">单位名称</th>
					<th scope="col">逾期平台</th>
					<th scope="col">逾期天数</th>
					<th scope="col">逾期笔数</th>
					<th scope="col">逾期金额</th>
					<th scope="col">状态</th>

					<th scope="col">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.name}</td>
						<td>${data.idNo}</td>
						<td>${data.appId}</td>
						<td>${data.mobile}</td>
						<td>${data.allAddress}</td>
						<td>${data.company}</td>
						<td>${data.platform}</td>
						<td>${data.defaultDays}</td>
						<td>${data.defaultNum}</td>
						<td>${data.defaultAmount}</td>
						<td>
							<c:choose>
								<c:when test="${data.state eq '1'}">有效</c:when>
								<c:when test="${data.state eq '0'}">无效</c:when>
							</c:choose>
						</td>

						<td>
							<c:if test="${data.state eq '1' }">
								<a href="javascript:void(0);" onclick="javascript:updateData('${ data.id}');">更新</a>&nbsp;
								<a href="javascript:void(0);" onclick="javascript:deleteOrActiveData('${ data.id}','0');">删除</a>&nbsp;
							</c:if>
							<c:if test="${data.state eq '0' }">
								<a href="javascript:void(0);" onclick="javascript:deleteOrActiveData('${ data.id}','1');">启用</a>&nbsp;
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
</body>

<script language="javascript">
//更新
function updateData(data_id) {
	window.location = "<%=basePath%>" + "blacklist/update.do?id=" + data_id;
	return;
}

//删除
function deleteOrActiveData(data_id,state) {
	var confirmMsg = "您确认要删除？";
	if(state == '1') {
		confirmMsg = "您确认要启用？";
	}
	$.messager.confirm('消息', confirmMsg, function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
				url : "<%=basePath%>" + "blacklist/deleteOrActive.do",
				// 防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
				data : encodeURI("id=" + data_id + "&state=" + state + "&timestamp=" + (new Date()).getTime()),
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
	window.location = "<%=basePath%>" + "blacklist/update.do";
	return;
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


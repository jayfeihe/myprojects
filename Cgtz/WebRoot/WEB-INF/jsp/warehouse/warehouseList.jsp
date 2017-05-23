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
<title>T_WAREHOUSE列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
				<a href="javascript:void(0);" onclick="addTab('添加仓库','<%=basePath%>' + 'warehouse/update.do')"><img src="img/square/sys_but_add.png" class='dotimg' title="添加仓库" /></a>&nbsp;
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
<th scope="col">仓库名称</th>
<th scope="col">所属机构</th>
<th scope="col">所在省</th>
<th scope="col">所在市</th>
<th scope="col">所在县</th>
<th scope="col">所在地址</th>
					<th scope="col" width="160">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
				
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						
<td><a href="javascript:void(0)" onclick="addTab('【仓库编号-'+${data.id}+'】查看仓库','<%=basePath%>/warehouse/read.do?id=${data.id}')" style="text-decoration: underline;">${data.name}</a></td>
<td>${data.orgName}</td>
<td>${data.prvn}</td>
<td>${data.city}</td>
<td>${data.ctry}</td>
<td>${data.addr}</td>
						<td>
							<a href="javascript:void(0);" onclick="javascript:addTab('【仓库编号-'+${data.id}+'】修改仓库','<%=basePath%>warehouse/update.do?id= ${ data.id}')">更新</a>&nbsp;
							<a href="javascript:void(0);" onclick="javascript:deleteData('${ data.id}');">删除</a>&nbsp;
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
	window.location = "<%=basePath%>" + "warehouse/update.do?id=" + data_id;
	return;
}

//删除
function deleteData(data_id) {
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
				url : "<%=basePath%>" + "warehouse/updateState.do",
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
	window.location = "<%=basePath%>" + "warehouse/update.do";
	return;
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


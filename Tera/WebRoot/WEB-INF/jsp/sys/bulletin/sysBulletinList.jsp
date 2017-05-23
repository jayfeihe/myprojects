<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<div class="content">
<form name="queryList" id="queryList" method="post" action="${pm.url}">
	<div id="control" class="control">
		<a href="javascript:void(0);" onclick="javascript:addData();"><img
			src="img/square/sys_but_add.png" class='dotimg' title="添加公告" /></a>&nbsp; <a
			href="javascript:void(0);"
			onclick="javascript:window.location.reload();"><img
			src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
	</div>
	<table id="table" class="datatable"
		summary="list of members in EE Studay">
		<tr>
			<th scope="col" width="30">序号</th>
			<th scope="col">标题</th>
			<th scope="col">作者</th>
			<th scope="col">发布时间</th>
			<th scope="col">状态</th>
			<th scope="col" width="160">操作</th>
		</tr>
		<c:forEach items="${pm.data}" var="data" varStatus="status">
			<tr>
				<td style="text-align: center;">${status.index+pm.rowS+1}</td>
				<td>${data.title}</td>
				<td>${data.writer}</td>
				<td><fmt:formatDate value="${data.publishTime}"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td>${data.bulState}</td>
				<td><a href="javascript:void(0);"
					onclick="javascript:delData('${data.id}');" title="删除">删除</a>&nbsp;
					<a href="javascript:void(0);"
					onclick="javascript:updateData('${data.id}');" title="更新">更新</a>&nbsp;
					<%--
				<a href="javascript:void(0);" onclick="javascript:delData('${data.id}');" title="删除"><img src="img/deleteItem0.gif" class='dotimg'/></a>&nbsp;
				<a href="javascript:void(0);" onclick="javascript:updateData('${data.id}');" title="更新"><img src="img/updateItem.gif" class='dotimg'/></a>&nbsp;
 --%></td>
			</tr>
		</c:forEach>
	</table>
	<div id="pageStyle">${pm.pageNavigation}</div>
</form>
</div>
<script language="javascript">
//删除数据
function delData(data_id){
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
	 			url:"<%=basePath%>sys/bulletin/delbulletin.do",
	 			//防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
	 			data:encodeURI("ids=" + data_id  + "&timestamp=" + (new Date()).getTime()),
	 			//dataType:"json",
	 			async: false,//同步提交
	 			success:function(data){
	 				if("true"==data.success){
	 					//form刷新防提示
						$.messager.alert('消息', data.message,"info", function(){
			                   	window.location = window.location + "&timestamp=" + (new Date()).getTime();
								window.location.reload();
		            	});
	 				}else{
						$.messager.alert('消息', data.message);
	 				}
	 			},
	 			error : function() {
					$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
				}
	 		});
	    }
	});
}
//更新数据
function updateData(data_id){
	var url="<%=basePath%>sys/bulletin/update.do?id="+data_id;
	//var winName = "更新角色";
	//openwindow(url,winName,600,400);
	window.location=url;
}
//更新数据
function addData(){
	var url="<%=basePath%>sys/bulletin/add.do";
	window.location=url;
}

//分配菜单
function settleMenu(data_id){
	var url="<%=basePath%>sys/role/setmenu.do?id="+data_id;
	window.location=url;
}
</script>
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
<title>出借人资金变动记录列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
	<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>近期资金收支记录</strong></div><hr color="#D3D3D3" />
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
				
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
<th scope="col">出借人标识</th>					
<th scope="col">类型</th>
<th scope="col">金额</th>
<th scope="col">凭证号</th>
<th scope="col">说明</th>
<th scope="col">时间</th>

				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<%-- <td>${data.id}</td> --%>
<td>${lendUserName}</td>
<td>
<c:choose>
	<c:when test="${data.type eq '2'}">支出</c:when>
	<c:when test="${data.type eq '1'}">收入</c:when>							
</c:choose>
</td>
<td><fmt:formatNumber value="${data.amt}" type="currency"/>元</td>
<td>${data.proof}</td>
<td>${data.remark}</td>
<td>${data.createTimeStr}</td>

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
function updateData(data_id,lendUserId) {
	addTab('更改变动日志','<%=basePath%>' + 'lenduser/lenduserlog/update.do?id=' + data_id+'&lendUserId='+lendUserId);
	return;
}

//删除
function deleteData(data_id) {
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
				url : "<%=basePath%>" + "lenduser/lenduserlog/delete.do",
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
function add(data_id){
	addTab('添加变动记录','<%=basePath%>' + 'lenduser/lenduserlog/update.do?lendUserId='+data_id);
	return;
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


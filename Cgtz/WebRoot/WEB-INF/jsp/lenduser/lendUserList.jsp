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
<title>出借人基本信息维护列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
				<a href="javascript:void(0);" onclick="addTab('添加出借人','<%=basePath%>' + 'lenduser/update.do')"><img src="img/square/sys_but_add.png" class='dotimg' title="添加出借人" /></a>&nbsp;
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
<th scope="col">标识</th>
<th scope="col">真实姓名</th>
<th scope="col">性别</th>
<th scope="col">手机</th>
<th scope="col">现居住地址</th>
<th scope="col">开户名</th>
<th scope="col">开户行</th>
<th scope="col">收款账号</th>
<th scope="col">余额</th>
<th scope="col">说明</th>
					<th scope="col" width="160">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
<td><a href="javascript:void(0)" onclick="readData('${data.name}','${data.id}')" style="text-decoration: underline;">${data.name}</a></td>
<td>${data.realName}</td>
<td><c:choose>
	<c:when test="${data.gender eq 'M'}">男</c:when>
	<c:when test="${data.gender eq 'F'}">女</c:when>							
	</c:choose></td>
<td>${data.mobile}</td>
<td>${data.nowPrvn}${data.nowCity}${data.nowCtry}${data.nowAddr}</td>
<td>${data.acctName}</td>
<td>${data.acctBank}</td>
<td>${data.acctCode}</td>
<td><fmt:formatNumber value="${data.amt}" type="currency"/>元</td>

<td>${data.remark}</td>

						<td>
						<a href="javascript:void(0);" onclick="javascript:updateAmt('${data.name }','${ data.id}');">资金管理</a>&nbsp;
							<a href="javascript:void(0);" onclick="javascript:updateData('${data.name }','${ data.id}');">修改</a>&nbsp;							
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
//查看出借人信息
function readData(name,id){
   addTab('【'+name+'】信息查看','<%=basePath%>' + 'lenduser/read.do?id=' + id);
   return ;
}
//更新出借人信息
function updateData(lendUserName,data_id) {
	addTab('【'+lendUserName+'】信息修改','<%=basePath%>' + 'lenduser/update.do?id=' + data_id);
	return;
}
//更新出借人资金信息
function updateAmt(lendUserName,data_id) {
	addTab('【'+lendUserName+'】资金管理','<%=basePath%>' + 'lenduser/lenduserlog/query.do?lendUserId=' + data_id);
	return;
}
//删除(出借人状态更改)
function deleteData(data_id) {
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
				url : "<%=basePath%>" + "lenduser/updateState.do",
				// 防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
				data : encodeURI("id=" + data_id + "&timestamp=" + (new Date()).getTime()),
				async : false,// 同步提交
				success : function(data) {
					if ("true"==data.success) {
						// form刷新防提示
						$.messager.alert('消息', data.message,"info", function(){
			              		//window.location = window.location + "&timestamp=" + (new Date()).getTime();
								//window.location.reload();
								
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
   
	window.location = "<%=basePath%>" + "lenduser/update.do";
	return;
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


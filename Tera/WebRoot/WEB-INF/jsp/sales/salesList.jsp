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
<title>营销人员表列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
			<c:if test="${login_org.orgId!=86}">
				<a href="javascript:void(0);" onclick="add();"><img src="img/square/but_add.png" class='dotimg' title="添加" /></a>&nbsp;
			</c:if>
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">员工卡号</th>
					<th scope="col">姓名</th>
					<th scope="col">性别</th>
					<th scope="col">机构</th>
					<th scope="col">销售团队</th>
					<th scope="col">岗位级别</th>
					<th scope="col">入职时间</th>
					<th scope="col">状态</th>
					<th scope="col" width="160">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.staffNo}</td>
						<td>${data.name}</td>
						<td>
							<c:choose>
		                        <c:when test="${data.gender=='1'}">男</c:when>
		                        <c:when test="${data.gender=='0'}">女</c:when>
		                        <c:otherwise>未知</c:otherwise>
	                        </c:choose>
						</td>
						<td>${data.orgName}</td>
						<td>${data.departName }</td>
						<td>
							<c:choose>
		                        <c:when test="${data.level=='1'}">助理</c:when>
		                        <c:when test="${data.level=='2'}">专员</c:when>
		                        <c:when test="${data.level=='3'}">主管</c:when>
		                        <c:when test="${data.level=='4'}">经理</c:when>
		                        <c:when test="${data.level=='5'}">高级经理</c:when>
		                        <c:when test="${data.level=='6'}">副总监</c:when>
		                        <c:when test="${data.level=='7'}">总监</c:when>
		                        <c:otherwise>未知</c:otherwise>
	                        </c:choose>
                        </td>
						<!--<td>${data.level}</td>-->
						<td>${data.entryDateStr}</td>
						<td>
							<c:choose>
		                        <c:when test="${data.state=='1'}">在职</c:when>
		                        <c:when test="${data.state=='2'}">挂起</c:when>
		                        <c:when test="${data.state=='0'}">离职</c:when>
	                        	<c:otherwise>未知</c:otherwise>
	                        </c:choose>
                        </td>
						<td>
							<c:if test="${data.state!='0'&&login_org.orgId==data.orgId}">
								<a href="javascript:void(0);" onclick="javascript:updateData('${ data.id}');">更新</a>&nbsp;
								<%-- <a href="javascript:void(0);" onclick="javascript:deleteData('${ data.id}');">删除</a>&nbsp; --%>
								<a href="javascript:void(0);" onclick="javascript:departSetFn('${ data.id}');">部门设置</a>&nbsp;
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
	window.location = "<%=basePath%>" + "sales/update.do?id=" + data_id;
	return;
}

//删除
function deleteData(data_id) {
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
				url : "<%=basePath%>" + "sales/delete.do",
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
					$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
				}
			});
        }
    });
}

//部门设置
function departSetFn(data_id) {
	if($("body").find("#dialogDiv").length==0){
		$('body').append($("<div id='dialogDiv'></div>"));
	}
	$('#dialogDiv').dialog({
	    title: "部门设置",
	    width: 400,
	    closed: false,
	    cache: false,
	    href: "<%=basePath%>" + "sales/skipDepartSet.do?id=" + data_id,
	    modal: true
	});
	return;
}

//添加
function add(){
	window.location = "<%=basePath%>" + "sales/update.do";
	return;
}

//页面加载完动作
$(document).ready(function (){
});
</script>
</html>


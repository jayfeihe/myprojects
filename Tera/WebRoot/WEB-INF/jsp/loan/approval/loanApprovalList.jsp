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
<title>借款申请审批表列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
<!-- 				<a href="javascript:void(0);" onclick="add();"><img src="img/square/but_add.png" class='dotimg' title="添加" /></a>&nbsp; -->
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">申请ID</th>
					<th scope="col">客户姓名</th>
					<th scope="col">证件号码</th>
					<th scope="col">机构代码</th>
					<th scope="col">申请时间</th>
					<th scope="col">金额</th>
					<th scope="col">产品</th>
					<th scope="col">期限</th>
					<th scope="col">状态</th>

					<th scope="col" width="160">操作</th>
				</tr>
				<c:forEach items="${pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.appId}</td>
						<td>${data.name}</td>
						<td>${data.idNo}</td>
						<td>${data.orgId}</td>
						<td>${data.createTimeStr}</td>
						<td>
<%--							<fmt:parseNumber value="${data.amount}" var="fmtAmount"/>--%>
<%--							${fmtAmount}--%>
						<fmt:formatNumber value="${data.amount}" type="currency"/>
						</td>
						<td>${data.product}</td>
						<td>${data.period}</td>
						<td>
						<c:choose>
							<c:when test="${data.state=='1'}">正常</c:when>
							<c:when test="${data.state=='2'}">拒件</c:when>
							<c:otherwise>复核退回</c:otherwise>
					    </c:choose>
						</td>
						<td>
							<c:if test="${data.taskProcesser==loginId}">
							<a href="javascript:void(0);" 
							onclick="javascript:updateData('${ data.id}');">审批</a>&nbsp;
							</c:if>
							<c:if test="${isLead}">
							<a href="javascript:void(0);" 
							onclick="javascript:updateTaskProcesser('${data.appId}','总部审批人');">调整处理人</a>&nbsp;
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

    
<div id='ProcesserDiv'></div>

<script language="javascript">

//更新
function updateData(data_id) {
	window.location = "<%=basePath%>" + "loan/approval/update.do?id=" + data_id;
}

//更新
function updateTaskProcesser(appId,roleName) {
	
// 	var pdiv=$("#ProcesserDiv");
// 	if(pdiv==null){
// 		 $('body').append($(""));
// 	}
	var url= "<%=basePath%>bpm/updateProcesser.do?bizKey="+appId+"&roleName="+encodeURI(roleName);;
    $('#ProcesserDiv').dialog({
    title: '调整处理人',
    width: 400,
    closed: false,
    cache: false,
    href: url,
    modal: true
    });
}


//删除
function deleteData(data_id) {
	
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
				url : "<%=basePath%>" + "loan/approval/delete.do",
				// 防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
				data : encodeURI("appId=" + data_id + "&timestamp=" + (new Date()).getTime()),
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


//添加
function add(){
	window.location = "<%=basePath%>" + "loan/approval/update.do";
	
	return;
}

//页面加载完动作
$(document).ready(function (){
	
});


</script>
</html>


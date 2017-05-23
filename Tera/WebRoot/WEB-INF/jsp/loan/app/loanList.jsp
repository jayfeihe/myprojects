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
<title>借款端申请表列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
				<a href="javascript:void(0);" onclick="add('per');"><img src="img/square/but_add.png" class='dotimg' title="添加个人客户" /></a>&nbsp;
				<a href="javascript:void(0);" onclick="add('org');"><img src="img/square/but_addd.png" class='dotimg' title="添加机构客户" /></a>&nbsp;
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
						<th scope="col">
							申请号
						</th>
						<th scope="col">
							姓名/机构全称
						</th>
						<th scope="col">
							证件号码
						</th>
						<th scope="col">
							申请时间
						</th>
						<th scope="col">
							借款金额
						</th>
						<th scope="col">
							产品
						</th>
						<th scope="col">
							期限
						</th>
						<c:if test="${stateType=='waitTask'}">
							<th scope="col" width="160">操作</th>
						</c:if>
						<c:if test="${stateType=='inTask'}">
							<th scope="col" width="160">当前处理状态</th>
						</c:if>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.appId}</td>
						<td>${data.name}</td>
						<td>${data.idNo}</td>
						<td>${data.createTimeStr}</td>
						<td>
<%--							<fmt:parseNumber value="${data.amount}" var="fmtAmount"/>--%>
<%--							${fmtAmount}--%>
						<fmt:formatNumber value="${data.amount}" type="currency"/>
						</td>
						<td>${data.product}</td>
						<td>${data.period}</td>
						<c:if test="${stateType=='waitTask'}">
						<td>
							<a href="javascript:void(0);" onclick="javascript:updateData('${data.id}');">更新</a>&nbsp;
							<a href="javascript:void(0);" onclick="javascript:deleteData('${data.appId}');">删除</a>&nbsp;
						</td>
						</c:if>
						<c:if test="${stateType=='inTask'}">
						<td>
							${data.taskState}
						</td>
						</c:if>
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
	window.location.href = "<%=basePath%>loan/app/update.do?id=" + data_id;
}
//删除
function deleteData(data_id) {
	
	$.messager.confirm('消息', '您确认要删除？', function(ok){
	//点击确定做删除
		if (ok){
			$.ajax({
				url: "loan/app/delete.do",
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
function add(param){
	window.location.href = "<%=basePath%>" + "loan/app/update.do?paramVal="+param;
	return;
}
</script>
</html>


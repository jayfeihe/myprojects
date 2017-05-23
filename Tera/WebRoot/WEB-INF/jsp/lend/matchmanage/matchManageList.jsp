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
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">申请ID</th>
					<th scope="col">撮合类型</th>
					<th scope="col">申请时间</th>
					<th scope="col">金额</th>
					<th scope="col">利率</th>
					<th scope="col">服务费率</th>
					<th scope="col">期限</th>
					<th scope="col">开始日期</th>
					<th scope="col">撮合次数</th>

					<th scope="col">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.appId}</td>
						<td>
						<c:choose>  
			                <c:when test="${data.matchType=='1'}">人工撮合</c:when>  
			                <c:otherwise>自动撮合</c:otherwise>  
			            </c:choose>  
						</td>
						<td>${data.appTimeStr}</td>
						<td>
						<fmt:formatNumber value="${data.amount}" type="currency"/>
						</td>
						<td>${data.interestRate}</td>
						<td>${data.serviceRate}</td>
						<td>${data.period}</td>
						<td>${data.startDateStr}</td>
						<td>${data.times}</td>
						
						<td>
							<a href="javascript:void(0);" onclick="javascript:changeMatchType('${data.id}');">撮合调整</a>&nbsp;
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

//删除
function changeMatchType(data_id) {
	
	$.messager.confirm('消息', '您确认要转换撮合类型吗？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
				url : "<%=basePath%>" + "lend/matchmanage/changeMatchType.do",
				// 防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
				data : encodeURI("matchState=${matchState}&id=" + data_id + "&timestamp=" + (new Date()).getTime()),
				async : false,// 同步提交
				success : function(data) {
					if ("true"==data.success) {
						// form刷新防提示
						
						$.messager.confirm('操作完成', data.message, function(ok){
			                if (ok){
			                	submitForm('queryForm');
			                }
		            	});
					} else {
						
						$.messager.alert('操作失败', data.message);
					}
				},
				error : function() {
					
					$.messager.alert('消息', '操作出现错误,请联系系统管理员！');
				}
			});
        }
    });
}
</script>

</html>


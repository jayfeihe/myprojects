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
<title>支付明细表列表</title>
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
					<th scope="col">合同号</th>
					<th scope="col">收付</th>
					<th scope="col">科目</th>
					<th scope="col">计划金额</th>
					<th scope="col">实际金额</th>
					<th scope="col">期数</th>
					<th scope="col">支付日期</th>
					<th scope="col">状态</th>
					<th scope="col">原因</th>
					<th scope="col" width="160">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.contractNo}</td>
						<td>
							<c:choose>
								<c:when test="${'1'==data.inOut}">
									收
								</c:when>
								<c:when test="${'2'==data.inOut}">
									付
								</c:when>
							</c:choose>
						</td>
						<td>${data.subject}</td>
						<td><fmt:formatNumber value="${data.planAmount}" type="currency"/></td>
						<td><fmt:formatNumber value="${data.actualAmount}" type="currency"/></td>
						<td>${data.periodNum}</td>
						<td><fmt:formatDate value="${data.repaymentDate}" pattern="yyyy-MM-dd"/></td>
						<td>
							<c:choose>
								<c:when test="${'1'==data.state}">
									待支付
								</c:when>
								<c:when test="${'2'==data.state}">
									已发盘
								</c:when>
								<c:when test="${'3'==data.state}">
									发盘失败
								</c:when>
								<c:when test="${'4'==data.state}">
									发盘成功
								</c:when>
								<c:when test="${'5'==data.state}">
									支付成功
								</c:when>
								<c:when test="${'6'==data.state}">
									支付失败
								</c:when>
								<c:when test="${'9'==data.state}">
									未确认
								</c:when>
								<c:when test="${'10'==data.state}">
									人工确认问题
								</c:when>
							</c:choose>
						</td>
						<td>${data.reason}</td>
						<td>
						 <c:set var="newDate" value="<%=System.currentTimeMillis()%>"></c:set> 
						 <c:if test="${newDate-data.repaymentDate.time > 0 and '9'==data.state}">
							<a href="javascript:void(0);" onclick="javascript:approve('${ data.id}');">确定支付</a>&nbsp;
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

//删除
function approve(data_id) {
	
	$.messager.confirm('消息', '是否确定支付？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
				url : "<%=basePath%>" + "payment/approve.do",
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

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


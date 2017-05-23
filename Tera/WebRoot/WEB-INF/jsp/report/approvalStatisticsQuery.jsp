<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<base href="<%=basePath%>" />
	<title>审批统计</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
	
</head>
<body style="height: '100%'">
	<div class="easyui-tabs" data-options="fit:true">
		<div title="审批统计">
			<div id="main">
				<div id="part1" class="part">
					<p class="title"><a href="javascript:void(0);">审批统计查询</a></p>
		
					<div class="content">
						<form id="queryForm" action="report/approvalStatisticsList.do" method="get" target="queryContent">
						</form>
					</div>
		
					<div id="queryContent">
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
function submitForm(fromId) {
	var formAction = $('#' + fromId).attr("action");
	var targetDiv = $('#' + fromId).attr("target");
	var params = $('#' + fromId).serialize();
	openMask();
	$.ajax({
		type : "POST",
		url : formAction,
		data : params + "&targetDiv=" + targetDiv,
		dataType : "html",
		success : function(data) {
			closeMask();
			$('#' + targetDiv).html(data);
		},
		error : function() {
			closeMask();
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
		}
	});
}

//页面加载完动作
$(document).ready(function() {
	submitForm("queryForm");
});
</script>
</html>


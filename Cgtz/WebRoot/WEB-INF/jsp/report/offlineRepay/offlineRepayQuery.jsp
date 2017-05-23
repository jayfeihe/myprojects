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
<title>线下还本计划表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
<style type="text/css">

</style>
</head>
<body>
<div class="easyui-tabs" id="queryTabs" data-options="fit:true">
<div title="线下还本计划表">
	<div id="main">
		<div id="part1" class="part">
			<p class="title"><a href="javascript:void(0);">查询</a></p>
			
			<div class="content">
				<form id="queryForm" action="" method="post" target="queryContent">
					<input type="hidden" id="orgName" name="orgName"/>
					<table>
						<tr>
							<td>项目编号:</td>
							<td>
								<input type="text" class="textbox easyui-numberbox" id="project_id" name="project_id" />
							</td>
							<td>应付息时间:</td>
							<td>
								<input id="rateTimeMin" name="rateTimeMin" type="text" class="textbox easyui-datebox" 
									data-options="editable:false" style="width: 90px;"/>
								&nbsp;-&nbsp;
								<input id="rateTimeMax" name="rateTimeMax" type="text" class="textbox easyui-datebox" 
									data-options="editable:false" style="width: 90px;"/>
							</td>
							<td colspan="3">
								<input type="button" value="查询" class="btn" onclick="submitForm('queryForm')"/>
								<input type="button" value="导出" class="btn" onclick="excelExport('queryForm')"/>
								<input type="button" value="重置" class="btn" onclick="$('#queryForm').form('clear');"/>
							</td>
						</tr>
					</table>	
				</form>
			</div>
			
			<div id="queryContent" ></div>
		</div>
	</div>
</div>
</div>
<script type="text/javascript">
function submitForm(fromId) {
	
	//去掉 input 输入的 前后空格
	$('#queryForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	
	var formAction = "<%=basePath%>report/offlinerepay/list.do";
	var targetDiv = $('#' + fromId).attr("target");
	var params = $('#' + fromId).serialize();
	//弹出异步加载 遮罩
	openMask();
	$.ajax( {
		type : "POST",
		url  : formAction,
		data : params + "&targetDiv=" + targetDiv,
		dataType : "html",
		success : function(data) {
			closeMask();
			$('#' + targetDiv).html(data);
		},
		error : function() {
			closeMask();
			$.messager.confirm('消息', '数据加载失败,请联系系统管理员！');
		}
	});
}

//导出
function excelExport(fromId) {
	$('#' + fromId).attr("action","<%=basePath%>report/offlinerepay/export.do");
	$('#' + fromId).attr("method","post");
	$('#' + fromId).submit();
}

//页面加载完动作
$(document).ready(function() {
	submitForm("queryForm");
});
</script>
</body>
</html>


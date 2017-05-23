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
		<title>司法复核</title>
		<link href="css/global.css" type="text/css" rel="stylesheet" />
		<link href="css/icon.css" type="text/css" rel="stylesheet" />
		<link href="css/default/easyui.css" type="text/css" rel="stylesheet" />
		<script src="js/jquery.min.js" type="text/javascript"></script>
		<script src="js/jquery.form.js" type="text/javascript"></script>
		<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
		<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
		
		<style type="text/css"></style>
	</head>
	<body>
		<div id="main">
			<div id="part1" class="part">
				<p class="title"><a href="javascript:void(0);">司法复核查询</a></p>

				<div class="content">
					<form id="queryForm" action="collection/judicial/review/list.do" method="post" target="queryContent">
						<table>
							<tr>
								<td>合同编号:</td>
								<td><input id="contractNo" name="contractNo" type="text"
										data-options="validType:['length[0,30]']"
										class="textbox easyui-validatebox" /></td>
										
								<td>客户姓名:</td>
								<td><input id="customerName" name="customerName" type="text"
										data-options="validType:['length[0,50]']"
										class="textbox easyui-validatebox" /></td>
										
								<td>身份证:</td>
								<td><input id="idNo" name="idNo" type="text"
										data-options="validType:['idcard']"
										class="textbox easyui-validatebox" /></td>
								
								<td>当前催收人:</td>
								<td><input id="applyUid" name="applyUid" type="text"
										data-options="validType:['length[0,50]']"
										class="textbox easyui-validatebox" />
										</td>
								
							</tr>
							<tr>
								<td>处理状态:</td>
								<td>
									<select class="easyui-combobox" name="state" editable="false" style="width:130px;">
										<option value="0" selected="selected">待处理</option>
										<option value="1">已处理</option>
									</select>
								</td>
								<td>逾期天数:</td>
								<td>
									<input id="lateDaysMin" name="lateDaysMin" type="text"
										style="width: 90px;" class="textbox easyui-validatebox" />
										至
									 <input id="lateDaysMax" name="lateDaysMax" type="text"
										style="width: 90px;" class="textbox easyui-validatebox" /> 
								</td>
								<td colspan="2">
									<input type="button" value="查询" class="btn" onclick="submitForm('queryForm')" />
									<input type="button" value="重置" class="btn" onclick="$('#queryForm').form('clear');"/>
								</td>
							</tr>
						</table>
					</form>
				</div>

				<div id="queryContent">
					<%--
					查询列表
					 --%>
				</div>
			</div>
		</div>
	</body>

<script type="text/javascript">

function submitForm(fromId) {
	//去掉 input 输入的 前后空格
	$('#queryForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	
	var formAction = $('#' + fromId).attr("action");
	var targetDiv = $('#' + fromId).attr("target");
	var params = $('#' + fromId).serialize();
	//弹出异步加载 遮罩
	openMask();
	$.ajax( {
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

/* //打开Loading遮罩并修改样式
function openLoading() {
	$('#loading').window('open');
	$("#loading").attr("class", "");
	$("div[class='panel window']").css("position", "absolute");
	$("div[class='panel window']").attr("class", "");
	$("div[class='window-shadow']").attr("class", "");
} */


//页面加载完动作
$(document).ready(function() {
	submitForm("queryForm");
});
</script>
</html>


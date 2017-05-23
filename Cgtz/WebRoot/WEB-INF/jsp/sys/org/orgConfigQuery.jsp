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
<title>机构配置查询</title>
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
	<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">机构配置查询</a></p>
		
		<div class="content">
			<form id="queryForm" action="sys/orgconfig/list.do" method="post" target="queryContent">
				<table>
					<tr>
						<td>机构简称:</td>
						<td><input id="orgName" name="orgName" type="text" class="textbox"/></td>
						
						
						<td>机构全称:</td>
						<td><input id="orgFullName" name="orgFullName" type="text"  class="textbox"/></td>
						
						<td>机构代码:</td>
						<td><input id="code" name="code" type="text"  class="textbox"/></td>
						
						<td>审批额度:</td>
						<td><input id="aduitAmt" name="aduitAmt" type="text" 
								class="textbox easyui-numberbox" data-options="min:0,precision:2" />元&nbsp;&nbsp;</td>
					</tr>
					<tr>
						<td>月债权:</td>
						<td><input id="loanAmt" name="loanAmt" type="text" 
								class="textbox easyui-numberbox" data-options="min:0,precision:2"/>元&nbsp;&nbsp;</td>
						<td>利息差比:</td>
						<td><input id="intRate" name="intRate" type="text" 
								class="textbox easyui-numberbox" data-options="min:0,precision:2"/>元&nbsp;&nbsp;</td>
						<td colspan="2">
							<input type="button" value="查询" class="btn" onclick="submitForm('queryForm')"/>
							<input type="reset" value="重置" class="btn"/>
						</td>
					</tr>
				</table>		
			</form>
		</div>
		
		<div id="queryContent" >
		<%--
		查询列表
		 --%>
		</div>
	</div>
</div>
	
<script type="text/javascript">

function submitForm(fromId) {
	var formAction = $('#' + fromId).attr("action");
	var targetDiv = $('#' + fromId).attr("target");
	var params = $('#' + fromId).serialize();
	//弹出异步加载 遮罩
	openMask();
	$.ajax({
		type : "POST",
		url : formAction,
		data : encodeURI(params + "&targetDiv=" + targetDiv),
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

function getParamJson(fromId){
	var params = $('#' + fromId).serialize();
}

$(document).ready(function() {
		submitForm('queryForm');
	});
</script>
</body>
</html>


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
<title>数据字典查询</title>
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
		<p class="title"><a href="javascript:void(0);">数据字典查询</a></p>
		
		<div class="content">
			<form id="queryForm" action="sys/datadictionary/list.do" method="post" target="queryContent">
				<table>
					<tr>
<td class="td01">字典名称:</td>
<td><input id="keyName" name="keyName" type="text" data-options="validType:['length[0,18]']" class="textbox easyui-validatebox"/></td>

<td class="td01">字典属性:</td>
<td><input id="keyProp" name="keyProp" type="text" data-options="validType:['length[0,18]']" class="textbox easyui-validatebox"/></td>

<td class="td01">字典值:</td>
<td><input id="keyValue" name="keyValue" type="text" data-options="validType:['length[0,18]']" class="textbox easyui-validatebox"/></td>
</tr>
<tr>
<td class="td01">描述:</td>
<td><input id="description" name="description" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"/></td>
<td class="td01">字典父属性:</td>
<td><input id="parentKeyProp" name="parentKeyProp" type="text" data-options="validType:['length[0,18]']" class="textbox easyui-validatebox"/></td>
<td></td><td></td>
</tr>
<tr>

						<td colspan="6">
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
		data : encodeURI(params + "&targetDiv=" + targetDiv),
		dataType : "html",
		success : function(data) {
			$('#' + targetDiv).html(data);
			closeMask();
		},
		error : function() {
			alert("数据加载失败！");
			closeMask();
		}
	});
}

$(document).ready(function() {
		submitForm('queryForm');
	});
</script>
</html>


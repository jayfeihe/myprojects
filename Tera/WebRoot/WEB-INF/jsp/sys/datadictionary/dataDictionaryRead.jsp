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
<title>数据字典查看</title>
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
		<p class="title"><a href="javascript:void(0);">查看</a></p>
		<div class="content">
			<table>
				<tbody>
				<input id="id" name="id" type="hidden" size="35" value="${ bean.id}" />
				<tr>
<td><SPAN style="color:red">*</SPAN>ID:</td>
<td><input id="id" name="id" type="text" data-options="required:true,min:0,precision:0" editable="false" class="textbox easyui-numberbox"value="${bean.id}" disabled="disabled" /></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>字典名称:</td>
<td><input id="keyName" name="keyName" type="text" data-options="required:true,validType:['length[0,18]']" class="textbox easyui-validatebox"value="${bean.keyName}" disabled="disabled" /></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>字典属性:</td>
<td><input id="keyProp" name="keyProp" type="text" data-options="required:true,validType:['length[0,18]']" class="textbox easyui-validatebox"value="${bean.keyProp}" disabled="disabled" /></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>字典值:</td>
<td><input id="keyValue" name="keyValue" type="text" data-options="required:true,validType:['length[0,18]']" class="textbox easyui-validatebox"value="${bean.keyValue}" disabled="disabled" /></td>
</tr>
<tr>
<td>描述:</td>
<td><input id="description" name="description" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.description}" disabled="disabled" /></td>
</tr>
<tr>
<td>字典父属性:</td>
<td><input id="parentKeyProp" name="parentKeyProp" type="text" data-options="validType:['length[0,18]']" class="textbox easyui-validatebox"value="${bean.parentKeyProp}" disabled="disabled" /></td>
</tr>

				<tr>
					<td>
						<input type="button" value="返回" class="btn" onclick="javascript:back()"/>
					</td>
					<td></td>
				</tr>
				</tbody>
			</table>			
		</div>
	</div>
</div>
</body>

<script type="text/javascript">
//返回
function back(){
	window.history.go(-1);
}
</script>
</html>


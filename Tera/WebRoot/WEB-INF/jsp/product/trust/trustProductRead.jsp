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
<title>信托产品查看</title>
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
<td><SPAN style="color:red">*</SPAN>产品:</td>
<td><input id="productId" name="productId" type="text" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.productId}" disabled="disabled" /></td>
</tr>
<tr>
<td>期限:</td>
<td><input id="period" name="period" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox"value="${bean.period}" disabled="disabled" /></td>
</tr>
<tr>
<td>机构名称:</td>
<td><input id="company" name="company" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.company}" disabled="disabled" /></td>
</tr>
<tr>
<td>利率:</td>
<td><input id="interestRate" name="interestRate" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox"value="${bean.interestRate}" disabled="disabled" /></td>
</tr>
<tr>
<td>起点金额:</td>
<td><input id="startAmount" name="startAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox"value="${bean.startAmount}" disabled="disabled" /></td>
</tr>
<tr>
<td>用途:</td>
<td><input id="useage" name="useage" type="text" data-options="validType:['length[0,200]']" class="textbox easyui-validatebox"value="${bean.useage}" disabled="disabled" /></td>
</tr>
<tr>
<td>操作员:</td>
<td><input id="operator" name="operator" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.operator}" disabled="disabled" /></td>
</tr>
<tr>
<td>所属机构:</td>
<td><input id="orgId" name="orgId" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.orgId}" disabled="disabled" /></td>
</tr>
<tr>
<td>创建日期:</td>
<td><input id="createTime" name="createTime" type="text" editable="false" class="textbox easyui-datetimebox"value="${bean.createTimeStr}" disabled="disabled" /></td>
</tr>
<tr>
<td>修改日期:</td>
<td><input id="updateTime" name="updateTime" type="text" editable="false" class="textbox easyui-datetimebox"value="${bean.updateTimeStr}" disabled="disabled" /></td>
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


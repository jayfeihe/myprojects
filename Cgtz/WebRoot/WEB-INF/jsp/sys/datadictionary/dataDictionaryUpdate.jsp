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
<title>数据字典更新</title>
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
		<p class="title"><a href="javascript:void(0);">更新</a></p>
		<div class="content">
			<form id="updateForm" >
				<table>
					<tbody>
					<input id="id" name="id" type="hidden" size="35" value="${ bean.id}" />
					<tr>
<td><SPAN style="color:red">*</SPAN>字典名称:</td>
<td><input id="keyName" name="keyName" type="text" data-options="required:true,validType:['length[0,18]']" class="textbox easyui-validatebox"value="${bean.keyName}"/></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>字典属性:</td>
<td><input id="keyProp" name="keyProp" type="text" data-options="required:true,validType:['length[0,18]']" class="textbox easyui-validatebox"value="${bean.keyProp}"/></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>字典值:</td>
<td><input id="keyValue" name="keyValue" type="text" data-options="required:true,validType:['length[0,18]']" class="textbox easyui-validatebox"value="${bean.keyValue}"/></td>
</tr>
<tr>
<td>描述:</td>
<td><input id="description" name="description" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.description}"/></td>
</tr>
<tr>
<td>字典父属性:</td>
<td><input id="parentKeyProp" name="parentKeyProp" type="text" data-options="validType:['length[0,18]']" class="textbox easyui-validatebox"value="${bean.parentKeyProp}"/></td>
</tr>

					<tr>
						<td>
							<input type="button" value="提交" class="btn" onclick="updateFunction()"/>
							<input type="button" value="返回" class="btn" onclick="javascript:back()"/>
						</td>
						<td></td>
					</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>
</body>

<script type="text/javascript">
//更新保存
function updateFunction() {
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		$(this).val($.trim($(this).val()));
	});
	//验证表单验证是否通过
	if(false == $('#updateForm').form('validate') ){
		return;
	}
	openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", "disabled");
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "sys/datadictionary/save.do",
		data : encodeURI(params),
		success : function(data) {
			if ("true"==data.success) {

//关闭遮罩，弹出消息框
				closeMask();
				$.messager.confirm('消息', data.message, function(ok){
	                if (ok){
//	                   	var url= "<%=basePath%>" + "sys/datadictionary/query.do";
//						window.location=url;
						//window.history.go(-1);
	                	window.parent.removeTab();
	                }
            	});
            } else {
            	closeMask();
				
				$.messager.confirm('消息', data.message);
				//按钮生效
				$(".btn").removeAttr("disabled");
            }
		},
		error : function() {
			alert("数据加载失败！");
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
	});
}
//返回
function back(){
	window.history.go(-1);
}
</script>
</html>


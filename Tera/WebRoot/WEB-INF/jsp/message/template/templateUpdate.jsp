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
<title>模板表更新</title>
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
						<td>类型:</td>
						<td><input id="type" name="type" type="text" data-options="required:true,validType:['length[0,50]']" editable="false" class="easyui-combobox" value="${bean.type}"/></td>
						<td>模板名称:</td>
						<td><input id="name" name="name" type="text" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.name}"/></td>
					</tr>
					<tr>
						<td >内容:</td>
						<td colspan="3">
						<textarea rows="5" style="width:98%" name="content" id="content" data-options="required:true,validType:['length[0,500]']" class="easyui-validatebox">${bean.content}</textarea>
						</td>
					</tr>
					<tr>
						<td>状态:</td>
						<td>
						<input id="state" name="state" type="text" data-options="required:true,validType:['length[0,2]']" editable="false" class="easyui-combobox" value="${bean.state}"/></td>
						<td>提醒时间:</td>
						<td>
							<input id="remindTime" name="remindTime" type="text" editable="false" data-options="required:true,showSeconds:true" class="easyui-timespinner" value="${bean.remindTimeStr}"/></td>
					</tr>
					<tr>
					<td>备注:</td>
					<td colspan="3">
					<input id="remark" name="remark" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" style="width: 95%;" value="${bean.remark}"/></td>
					</tr>
					<tr>
						<td>
							<input type="button" value="保存" class="btn" onclick="updateFunction()"/>
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
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	//验证表单验证是否通过
	if(false == $('#updateForm').form('validate') ){
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}
	//弹出异步加载 遮罩
	openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "message/template/save.do",
		data : encodeURI(params),
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
//	                var url= "<%=basePath%>" + "message/template/query.do";
//					window.location=url;
					window.history.go(-1);
            	});
            } else {				
    			$.messager.alert('消息', data.message);
				//按钮生效
				$(".btn").removeAttr("disabled");
            }
		},
		error : function() {
			//关闭遮罩，弹出消息框
			closeMask();
			$.messager.confirm('消息', '数据加载失败,请联系系统管理员！');
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
	});
}

//返回
function back(){
	window.history.go(-1);
}

//页面加载完动作
$(document).ready(function (){
	//填充select数据样例
	/*<%--
	var tsurl="sys/datadictionary/listjason.do?keyName=repaymethod";
	$("#repayMethod").combobox("clear");
	$('#repayMethod').combobox({url:tsurl, valueField:'keyProp', textField:'keyValue'});
	--%>*/
	//填充select数据 状态
    var stateurl = "sys/datadictionary/listjason.do?keyName=validstate";
	$("#state").combobox("clear");
	$('#state').combobox({
		url: stateurl,
		valueField: 'keyProp',
		textField: 'keyValue'
	});
	//填充select数据 类型
    var typeurl = "sys/datadictionary/listjason.do?keyName=templatetype";
	$("#type").combobox("clear");
	$('#type').combobox({
		url: typeurl,
		valueField: 'keyProp',
		textField: 'keyValue'
	});
});

</script>
</html>


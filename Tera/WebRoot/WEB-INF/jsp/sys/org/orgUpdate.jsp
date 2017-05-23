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
<title>数据权限更新</title>
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
						<td>数据权限代码:</td>
						<td><input id="orgId" name="orgId" type="text" data-options="required:true,validType:['length[0,18]']" class="textbox easyui-validatebox"value="${bean.orgId}"/></td>
					</tr>
					<tr>
						<td>名称:</td>
						<td><input id="orgName" name="orgName" type="text" data-options="validType:['length[0,18]']" class="textbox easyui-validatebox"value="${bean.orgName}"/></td>
					</tr>
					<tr>
						<td>级别:</td>
						<td><input id="level" name="level" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox"value="${bean.level}"/></td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="button" value="提交" class="btn" onclick="updateFunction()"/>
							<input type="button" value="返回" class="btn" onclick="javascript:back()"/>
						</td>
					</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>


<div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
	<img src="img/loading.gif" alt="加载中..." />
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
	//弹出异步加载 遮罩
	openLoading();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "sys/org/save.do",
		data : encodeURI(params),
		success : function(data) {
			if ("true"==data.success) {
				//关闭遮罩，弹出消息框
				$('#loading').window('close');
				
				$.messager.confirm('消息', data.message, function(ok){
	                if (ok){
//	                   	var url= "<%=basePath%>" + "sys/org/query.do";
//						window.location=url;
						window.history.go(-1);
	                }
            	});
            } else {
                $('#loading').window('close');
				
				$.messager.confirm('消息', data.message);
				//按钮生效
				$(".btn").removeAttr("disabled");
            }
		},
		error : function() {
			$('#loading').window('close');
			
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
	});
}

//打开Loading遮罩并修改样式
function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
}

//返回
function back(){
	window.history.go(-1);
}
</script>
</html>


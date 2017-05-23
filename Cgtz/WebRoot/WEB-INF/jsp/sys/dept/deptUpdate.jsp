<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>
<div id="main">
		<div class="content">
			<form id="updateForm" >
				<input id="id" name="id" type="hidden" size="35" value="${ bean.id}" />
				<table>
					<tbody>
					<tr>
						<td>部门名称:</td>
						<td><input id="name" name="name" type="text" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.name}"/></td>
					</tr>
					<tr>
						<td>描述:</td>
						<td><input id="description" name="description" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.description}"/></td>
					</tr>
					</tbody>
				</table>
			</form>
			
			<hr color="#D3D3D3"/>
			<div align="right">
				<input type="button" value="提交" class="btn" onclick="updateFunction()"/>
				<input type="button" value="取消" class="btn" onclick="$('#dialogDiv').dialog('close');"/> 
			</div>
		</div>
</div>

<script type="text/javascript">
//开启遮罩
function openMask(){
	$("<div class=\"datagrid-mask\" id='mask_id'></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
	$("<div class=\"datagrid-mask-msg\" id='mask_msg_id'></div>").html("请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
}
//关闭遮罩
function closeMask(){
	$("#mask_id").remove();
	$("#mask_msg_id").remove();
}
</script>

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
		url : "<%=basePath%>" + "sys/dept/save.do",
		data : encodeURI(params),
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
	                var url= "<%=basePath%>" + "sys/dept/query.do";
					window.location=url;
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
});

</script>

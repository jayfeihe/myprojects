<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

	<div id="main">
		<div style="width: 400px;height: 200px">
				<form id="addDataId">
				<ta:formToken/>
				<table>
				<tr><td>名称:</td><td><input data-options="required:true"  id="name" name="name" type="text" class="textbox easyui-validatebox" /></td></tr>
				<tr><td>描述:</td><td><input data-options="required:true" id="description" name="description" type="text" class="textbox easyui-validatebox" /></td></tr>
				<tr><td>类型:</td><td>
				<select class="easyui-combobox" id="roleType" style="width:148px;" editable="false"  name="type" >
					<option value="1" selected >菜单、权限、岗位</option>
				</select>
				</td></tr>
				<tr>
					<td>等级:</td>
					<td>
						<input class="easyui-combobox" name="orgRoleLever" 
							id="orgRoleLever" style="width:148px;" editable="false"/>
					</td>
				</tr>
				<tr>
					<td>标记:</td>
					<td>
						
						<input  class="easyui-combobox" id="flag" name="flag"
							style="width:148px;" editable="false"  
							value="0"  />
					
					</td>
				</tr>
				</table>	
				
				</form>
		</div>
		<hr/>
		<div align="right">
			<input value="提交" type="button" id="submitId" class="btn" onclick="submit()"/> 
			<input value="重置" type="button" id="resetId" class="btn" onclick="$('#addDataId').form('clear');"/>
		</div>
	</div>

<script type="text/javascript">

function submit() {
	//去掉 input 输入的 前后空格
	$("div[name='inputInfo']").find('input').each(function(){
		$(this).val($.trim($(this).val()));
	});
	//验证表单验证是否通过
	if(false == $('#addDataId').form('validate')){
		
		$.messager.alert('消息', "页面有不符合规范内容，请正确填写！");
		return;
	}
	var fromdata=$('#addDataId').serialize();
	//按钮失效防点击
	$.ajax({
		type : "POST",
		data : fromdata,
		url:'sys/role/addaction.do',
		success : function(data) {
			$('#loading').window('close');
			if ("true"==data.success) {
				
				$.messager.alert('消息', data.message,"info", function(){
                	$('#dialogDiv').dialog('close');
                	//flushAfterSubmit();
					window.location.reload();
	        	});
	
            } else {
				
				$.messager.alert('消息', data.message);
            }
			
		},
		error : function() {
			
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
		}
		});

}
</script>


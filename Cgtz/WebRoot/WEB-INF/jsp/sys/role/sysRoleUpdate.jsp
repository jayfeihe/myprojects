<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	<div id="main">
		<div style="width: 350px;height: 200px" name="inputInfo">
			<form id="addDataId" >
			<input type="hidden" value="${data.id}" id="id" name="id" />
			<table>
				<%-- <tr><td>角色代码:</td><td><input data-options="required:true"  id="roleId" name="roleId" value="${data.roleId }" type="text" class="textbox easyui-validatebox" /></td></tr> --%>
				<c:if test="${data.lever eq 3}">
					<tr><td>角色名称:</td><td><input data-options="required:true"  id="name" name="name" value="${data.name }" type="text" class="textbox easyui-validatebox" /></td></tr>
					<tr><td>角色描述:</td><td><input data-options="required:true" id="description" name="description" value="${data.description }" type="text" class="textbox easyui-validatebox" /></td></tr>
				</c:if>
				<c:if test="${data.lever ne 3}">
					<tr><td>角色名称:</td><td><input data-options="required:true"  id="name" name="name" value="${data.name }" type="text" class="textbox easyui-validatebox" style="background-color:rgb(235, 235, 228)" readonly="readonly" /></td></tr>
					<tr><td>角色描述:</td><td><input data-options="required:true" id="description" name="description" value="${data.description }" type="text" class="textbox easyui-validatebox" style="background-color:rgb(235, 235, 228)"readonly="readonly"/></td></tr>
				</c:if>
				
				<tr>
					<td>角色等级:</td>
					<td>
						<input class="textbox easyui-combobox" id="lever" disabled="disabled" name="lever" value="${data.lever }"/>
					</td>
				</tr>
				<tr>
				 <input type="hidden" name="flag" value="${data.flag }"/>
					</tr>
			</table>
			</form>
		</div>
<hr/>
<div align="right">
	<input value="提交" type="button" onclick="submit()" id="submitId" class="btn"/><!-- 
	<input value="重置" type="button" id="resetId" onclick="$('#addDataId').form('clear');" name="btnReset" title="请单击重新输入" class="btn"/>
--></div>
	</div>
	<script type="text/javascript">
	function submit(){
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
		url:'sys/role/updateaction.do',
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
			$.messager.alert('消息',"操作失败，请联系管理员！");
		}
		});
}

	
</script>


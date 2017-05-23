<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	<div id="main">
		<div style="width: 350px;height: 200px" name="inputInfo">
			<form id="addDataId">
			<input id="state" name="state" value="1"  type="hidden"/>
				<ta:formToken/>
				<c:if test="${parentMenu == null}">
					<table>
						<tr><td>名称:</td><td><input id="name" name="name" type="text" data-options="required:true"
										class="textbox easyui-validatebox" /></td></tr>
						<tr><td>地址:</td><td><input id="url" name="url" type="text" 
										class="textbox easyui-validatebox"  /></td></tr>
						<tr>
							<td>上级菜单:</td>
							<td>
								<select id="parentId" name="parentId" class="sysSelectCss">
								<c:forEach items="${pmenus}" var="menu">
								<option value="${menu.id}">${menu.name}</option>
								</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td>类型:</td>
							<td>
								<select id="type" name="type" class="sysSelectCss">
									<option value="0">模块</option>
									<option value="1">页面</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>描述:</td>
							<td><input id="description" name="description" type="text" 
										class="textbox easyui-validatebox" /></td>
						</tr>
					</table>
				</c:if>
				<c:if test="${parentMenu != null}">
					<table>
						<tr><td>名称:</td><td><input id="name" name="name" type="text" data-options="required:true"
										class="textbox easyui-validatebox" /></td></tr>
						<tr><td>地址:</td><td><input id="url" name="url" type="text" data-options="required:true"
										class="textbox easyui-validatebox"/></td></tr>
						<tr>
							<td>上级页面:</td>
							<td>
								<input type="hidden" name="parentId" value="${parentMenu.id}" />
								${parentMenu.name}
							</td>
						</tr>
						<tr>
							<td>类型:</td>
							<td>
								<input type="hidden" name="type"  value="2" />
								按钮事件
							</td>
						</tr>
						<tr>
							<td>描述:</td>
							<td><input id="description" name="description" type="text" 
										class="textbox easyui-validatebox" /></td>
						</tr>
					</table>
				</c:if>	
				<hr/>
	<div align="right">
		<input type="button" value="提交" class="btn" onclick="submitForm()" /> 
		<input value="重置" type="button" id="resetId" class="btn" onclick="$('#addDataId').form('clear');"/>

	</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
function submitForm(){
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
		url  : "<%=basePath%>sys/menu/addaction.do",
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
			$('#loading').window('close');
			$.messager.alert('消息',"操作失败，请联系管理员！");
		}
		});
}

</script>	


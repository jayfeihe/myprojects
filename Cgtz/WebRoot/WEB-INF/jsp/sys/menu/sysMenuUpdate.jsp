<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	<div id="main">
		<div style="width: 350px;height: 200px" name="inputInfo">
			<form id="addDataId" >
				<input type="hidden" name="ordernum" value="${data.ordernum}"/>
				<input type="hidden" name="type" value="${data.type}"/>
				<table>
				<tbody>
				<ta:formToken/>
				<input id="id" name="id" type="hidden" size="35" value="${data.id }" />
					<tr><td>名称:</td><td><input id="name" name="name" type="text" data-options="required:true"
										class="textbox easyui-validatebox" value="${data.name }" /></td></tr>
					<tr><td>地址:</td><td><input id="url" name="url" type="text" 
										class="textbox easyui-validatebox" value="<c:if test="${'javascript:void(0);' != data.url}">${data.url}</c:if>" /></td></tr>
					<c:if test="${data.type != 2}">
					<tr>
						<td>上级菜单:</td>
						<td>
							<select id="parentId" name="parentId">
							<c:forEach items="${pmenus}" var="menu">
							<option value="${menu.id}" <c:if test="${menu.id==data.parentId}">selected="selected"</c:if> >${menu.name}</option>
							</c:forEach>
							</select>
						</td>
					</tr>
					</c:if>
					<c:if test="${data.type == 2}">
					<tr>
						<input type="hidden" value="${data.parentId}" id="parentId" name="parentId">
					</tr>
					</c:if>
					<tr>
						<td>描述:</td>
						<td><input id="description" name="description" type="text" data-options="required:true"
										class="textbox easyui-validatebox" value="${data.description }"/></td>
					</tr>
				</tbody>
				</table>
				</form>
		</div>
	
	</div>
	<hr/>
<div align="right">
	<input value="提交" type="button" id="submitId" class="btn" onclick="submit()" /> 
	<input value="重置" type="button" id="resetId" class="btn" onclick="$('#addDataId').form('clear');"/>
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
		url  : "sys/menu/updateaction.do",
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


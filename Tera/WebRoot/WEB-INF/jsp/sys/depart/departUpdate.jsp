<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	<div id="main">
		<div style="width: 360px;height: 205px" name="inputInfo">
			<form id="addDataId">
				<ta:formToken/>
				<c:if test="${bean == null}">
					<table>
<!-- 						<tr> -->
<!-- 							<td>组织代码:</td> -->
<!-- 							<td> -->
<!-- 								<input id="departCode" name="departCode" type="text" data-options="required:true" class="textbox easyui-validatebox" /> -->
<!-- 							</td> -->
<!-- 						</tr> -->
						<tr>
							<td>组织名称:</td>
							<td>
								<input id="id" name="id" type="hidden" value="0"/>
								<input id="departName" name="departName" type="text" data-options="required:true" class="textbox easyui-validatebox" style="width: 245px;"/>
							</td>
						</tr>
						<tr>
							<td>上级菜单:</td>
							<td>
								<input id="parentId" name="parentId" class="easyui-combotree" data-options="url:'sys/depart/listData.do?parentId=-1',method:'get',required:true" style="width: 250px;"/>
							</td>
						</tr>
						<tr>
							<td>数据权限:</td>
							<td>
								<input id="orgId" name="orgId" class="easyui-combotree" data-options="url:'sys/org/selectList.do',method:'get',required:true" style="width: 250px;"/>
							</td>
						</tr>
						<tr>
							<td>说明:</td>
							<td>
								<textarea id="remarks" name="remarks" class="textbox" style="resize: none; height: 80px!important; width: 245px;"></textarea>
							</td>
						</tr>
					</table>
				</c:if>
				<c:if test="${bean != null}">
					<table>
<!-- 						<tr> -->
<!-- 							<td>组织代码:</td> -->
<!-- 							<td> -->
<%-- 								<input id="departCode" name="departCode" type="text" data-options="required:true" class="textbox easyui-validatebox" value="${bean.departCode }"/> --%>
<!-- 							</td> -->
<!-- 						</tr> -->
						<tr>
							<td>组织名称:</td>
							<td>
								<input id="id" name="id" hidden="true" value="${bean.id }"/>
								<input id="departName" name="departName" type="text" data-options="required:true" class="textbox easyui-validatebox" value="${bean.departName }" style="width: 245px;"/>
							</td>
						</tr>
						<tr>
							<td>上级菜单:</td>
							<td>
								<input id="parentId" name="parentId" class="easyui-combotree" data-options="url:'sys/depart/listData.do?parentId=-1&id=${bean.id }',method:'get',required:true" value="${bean.parentId }" style="width: 250px;"/>
							</td>
						</tr>
						<tr>
							<td>数据权限:</td>
							<td>
								<input id="orgId" name="orgId" class="easyui-combotree" data-options="url:'sys/org/selectList.do',method:'get',required:true" value="${bean.orgId }" style="width: 250px;"/>
							</td>
						</tr>
						<tr>
							<td>说明:</td>
							<td>
								<textarea id="remarks" name="remarks" class="textbox" style="resize: none; height: 80px!important; width: 245px;">${bean.remarks }</textarea>
							</td>
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
	$("#addDataId").find('input').each(function(){
		$(this).val($.trim($(this).val()));
	});
	//验证表单验证是否通过
	if(false == $('#addDataId').form('validate')){
		$.messager.alert('消息', "页面有不符合规范内容，请正确填写！");
		return;
	}
	var fromdata=$('#addDataId').serialize();
	$.ajax({
		type : "POST",
		data : fromdata,
		url  : "<%=basePath%>sys/depart/checkRepeatDepartName.do",
		success : function(data) {
			if ("true"==data.success) {
				//按钮失效防点击
				$.ajax({
					type : "POST",
					data : fromdata,
					url  : "<%=basePath%>sys/depart/save.do",
					success : function(data) {
						$('#loading').window('close');
						if ("true"==data.success) {
							$.messager.alert('消息', data.message,"info", function(){
			                	$('#dialogDiv').dialog('close');
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
            } else {
            	$('#loading').window('close');
            	$('#addDataId').find('#departName').val("");
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


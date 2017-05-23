<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	<div id="main">

			<div style="width: 350px;" name="inputInfo">
				<form id="addDataId" >
				<table>
				<tbody>
				<ta:formToken/>
				<input id="id" name="id" type="hidden" size="35" value="${data.id }" />
				<tr><td>登录ID:</td><td><input id="loginID_id" name="loginId" type="text"  data-options="required:true,validType:['length[2,20]']"
										class="textbox easyui-validatebox" value="${data.loginId }" /></td></tr>
				<tr><td>密码:</td><td><input id="password_id" name="password" type="password" data-options="required:true,validType:['length[5,20]']"
										class="textbox easyui-validatebox" value="${data.password }" /></td></tr>
				<tr><td>重复密码:</td><td><input id="confirm_password_id" name="confirm_password" type="password" data-options="required:true,validType:['length[5,20]']"
										class="textbox easyui-validatebox" validType="equalTo['#password_id']" invalidMessage="两次输入密码不匹配" value="${data.password }"/></td></tr>
				<tr><td>姓名:</td><td><input id="name_id" name="name" type="text" data-options="required:true,validType:['length[2,20]']"
										class="textbox easyui-validatebox" value="${data.name }"/></td></tr>
				<tr><td>性别:</td><td>
				<select id="gender_id" name="gender" class="sysSelectCss">
						<option value="M" <c:if test="${data.gender=='M'}">selected="selected"</c:if> >男</option>
						<option value="F" <c:if test="${data.gender=='F'}">selected="selected"</c:if> >女</option>
				</select>
				</td></tr>
					
				<tr><td>电邮:</td><td><input id="email_id" name="email" type="text" class="txt"  value="${data.email }"/></td></tr>
				<tr><td>电话:</td><td><input id="phone_id" name="phone" type="text" class="txt" value="${data.phone }"/></td></tr>
				<tr><td>移动电话:</td><td><input id="mobile_id" name="mobile" type="text" class="txt" value="${data.mobile }"/></td></tr>
				<%--
				<tr><td>单位:</td><td><input id="orgID_id" name="orgID" type="text" class="txt"  value="${data.orgID }"/></td></tr>
				 --%>
				<tr>
					<td>是否可用:</td>
					<td>
						<input name="state" type="radio" <c:if test='${"1"==data.state}'>checked='checked'</c:if> value="1"/>启用&nbsp;&nbsp;
						<input name="state" type="radio" <c:if test='${"0"==data.state}'>checked='checked'</c:if> value="0"/>关闭
						
					</td>
				</tr>
				<tr><td>是否管理员:</td><td><input id="isAdmin_id" name="isAdmin" type="checkbox" <c:if test="${data.isAdmin==1}">checked='checked'</c:if> size="35" value="1"/></td></tr>
				<%--
				<tr><td>
				<input value="提交" type="button" id="submitId" class="btn"/>
				<input value="重置" type="button" id="resetId" class="btn"/>
				</td><td>
				</td></tr>
				 --%>
				</tbody>
				</table>
				</form>
		</div>
<hr/>
<div align="right">
	<input value="提交" type="button" id="submitId" onclick="submitForm()" class="btn"/> 
	<input value="重置" type="button" id="resetId" onclick="$('#addDataId').form('clear');" class="btn"/>
</div>
	</div>
<script type="text/javascript">
	function submitForm(){
	//去掉 input 输入的 前后空格
	$("div[name='inputInfo']").find('input').each(function(){
		$(this).val($.trim($(this).val()));
	});
	// unique('loginID_id','unique.html','name');  
	//验证表单验证是否通过
	if(false == $('#addDataId').form('validate')){
		
		$.messager.alert('消息', "页面有不符合规范内容，请正确填写！");
		return;
	}
	
	
	
	var userId=$("#loginID_id").val();
	$.ajax({
		type : "POST",
		data : encodeURI("origindata=${data.loginId}&loginId=" + userId  + "&timestamp=" + (new Date()).getTime()),
		url:'sys/user/userbyusername.do',
		success : function(isUser) {
			if ("true"==isUser.success) {
				var fromdata=$('#addDataId').serialize();
				$.ajax({
					type : "POST",
					data : fromdata,
					url:'sys/user/updateaction.do',
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
			}else{
				
				$.messager.alert('消息', isUser.message,"info", function(){
                	
	        	});
			}
	
		}
	});
	
	
	
	  }


</script>


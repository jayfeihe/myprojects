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
<title>用户管理</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
<link href="css/global.css" type="text/css" rel="stylesheet" />
<link href="css/icon.css" type="text/css" rel="stylesheet" />
<link href="css/default/easyui.css" type="text/css" rel="stylesheet" />
<script src="js/jquery.min.js" type="text/javascript"></script>
<script src="js/jquery.form.js" type="text/javascript"></script>
<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
<style type="text/css">
<!--
body{
	background-color:#ffffff;
	margin:0px; padding:0px;
}
label.error{
	font-weight: bold;
	color: red;
}
-->
</style>

</head>

	<body style="padding: 0px;margin: 0px">
	<div id="main">
		<div style="width: 350px;" name="inputInfo">
				<form id="addDataId">
					<ta:formToken/>
					<table>
					<tr><td>登录ID:</td><td><input id="loginID_id" name="loginId" type="text" data-options="required:true,validType:['length[2,20]']"
										class="textbox easyui-validatebox" />
										</td></tr>
					<tr><td>密码:</td><td><input id="password_id" name="password" type="password" data-options="required:true,validType:['length[5,20]']"
										class="textbox easyui-validatebox" /></td></tr>
					<tr><td>重复密码:</td><td><input id="confirm_password_id" name="confirm_password" type="password" data-options="required:true,validType:['length[5,20]']"
										class="textbox easyui-validatebox" validType="equalTo['#password_id']" invalidMessage="两次输入密码不匹配"/></td></tr>
					<tr><td>姓名:</td><td><input id="name_id" name="name" type="text" data-options="required:true,validType:['length[2,20]']"
										class="textbox easyui-validatebox"/></td></tr>
					<tr><td>性别:</td><td>
					<select id="gender_id" name="gender" class="sysSelectCss">
						<option value="M">男</option>
						<option value="F">女</option>
					</select>
					</td></tr>
					<tr><td>电邮:</td><td><input id="email_id" name="email" type="text" 
										class="textbox easyui-validatebox" /></td></tr>
					<tr><td>电话:</td><td><input id="phone_id" name="phone" type="text" 
										class="textbox easyui-validatebox"/></td></tr>
					<tr><td>移动电话:</td><td><input id="mobile_id" name="mobile" type="text" 
										class="textbox easyui-validatebox"/></td></tr>
					<%--
					<tr><td>单位:</td><td><input id="orgID_id" name="orgID" type="text" class="txt" /></td></tr>
					 --%>
					 <tr>
					<td>是否可用:</td>
					<td>
						<input name="state" type="radio" value="1" checked='checked'/>启用&nbsp;&nbsp;
						<input name="state" type="radio" value="0"/>关闭
						
					</td>
				</tr>
					<tr><td>是否管理员:</td><td><input id="isAdmin_id" name="isAdmin" type="checkbox" value="1" /></td>
					</tr>
					
			<%--
					<tr>
					<td></td><td></td>
					</tr>
			 --%>		
					</table>	
				
				</form>
		</div>
<hr/>
<div align="right">
	<input value="提交" type="button" id="submitId" onclick="submit()" class="btn"/> 
	<input value="重置" type="button" id="resetId" onclick="$('#addDataId').form('clear');" class="btn"/>
</div>
	</div>
	
	<%--
	<input type="button" value="fff" onclick="javascript:alert(document.getElementById('addDataId').innerHTML)"/>
	 --%>
	 
	 
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
	
	var userId=$("#loginID_id").val();
	$.ajax({
		type : "POST",
		data : encodeURI("loginId=" + userId  + "&timestamp=" + (new Date()).getTime()),
		url:'sys/user/userbyusername.do',
		success : function(isUser) {
			if ("true"==isUser.success) {
				var fromdata=$('#addDataId').serialize();
				$.ajax({
					type : "POST",
					data : fromdata,
					url:'sys/user/addaction.do',
					success : function(data) {
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
			}else{
				
				$.messager.alert('消息', isUser.message,"info", function(){
                	
	        	});
			}
			
		}
	});
	
}

	
</script>
	</body>

</html>


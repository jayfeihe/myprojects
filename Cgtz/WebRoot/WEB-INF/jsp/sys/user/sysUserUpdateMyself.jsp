<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String serverAddress = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />
		<title>用户管理</title>
		<link href="css/global.css" type="text/css" rel="stylesheet" />
		<script src="js/artDialog/artDialog.js?skin=opera"></script>
		<script src="js/artDialog/plugins/iframeTools.source.js"></script>
		<script src="js/jquery.min.js" type="text/javascript"></script>
		<script src="js/jquery.form.js" type="text/javascript"></script>
		<script src="js/jquery.validate.js" type="text/javascript"></script>
		<script src="js/jquery.validate.ms.js" type="text/javascript"></script>
		<script src="js/jquery.form.js" type="text/javascript"></script>
		<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
		<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
		<link href="css/default/easyui.css" type="text/css" rel="stylesheet" />
		<style type="text/css">
<!--
body {
	background-color: #ffffff;
	margin: 0px;
	padding: 0px;
}

label.error {
	font-weight: bold;
	color: red;
}
-->
</style>
	</head>
	<body>
		<div id="main">
			<div id="part1" class="part">
				<p class="title"><a href="#">更新用户</a></p>
				<div class="content">
					<div class="doc" id="doc2">
						<form id="addDataId">
							<table>
								<tbody>
									<ta:formToken />
									<input id="id" name="id" type="hidden" size="35"
										value="${data.id }" />
									<input name="state" type="hidden" size="35" value="${data.state }" />
									<input name="isAdmin" type="hidden" size="35" value="${data.isAdmin }" />
									<input id="loginID_id" name="loginId" type="hidden" value="${data.loginId }" />
									<tr>
										<td>密码:</td>
										<td><input id="password_id" name="password" type="password"
												data-options="required:true,validType:['length[5,20]']"
												class="textbox easyui-validatebox" value="" /></td>
										<td>重复密码:</td>
										<td><input id="confirm_password_id" name="confirm_password" type="password"
												data-options="required:true,validType:['length[5,20]']"
												class="textbox easyui-validatebox" validType="equalTo['#password_id']"
												invalidMessage="两次输入密码不匹配" value="" /></td>
									</tr>
									<tr>
										<td>姓名:</td>
										<td><input id="name_id" name="name" type="text" readonly="readonly"
												data-options="required:true,validType:['length[2,20]']"
												class="textbox easyui-validatebox" value="${data.name }" /></td>
										<td>性别:</td>
										<td><select id="gender_id" name="gender" class="sysSelectCss">
											<option value="M"<c:if test="${data.gender=='M'}">selected="selected"</c:if>>男</option>
											<option value="F"<c:if test="${data.gender=='F'}">selected="selected"</c:if>>女</option>
										</select></td>
									</tr>
									<tr>
										<td>电话:</td>
										<td><input id="phone_id" name="phone" type="text" class="txt" value="${data.phone }" /></td>
										<td>移动电话:</td>
										<td><input id="mobile_id" name="mobile" type="text" class="txt" value="${data.mobile }" /></td>
									</tr>
									<tr>
										<td>电邮:</td>
										<td><input id="email_id" name="email" type="text" class="txt" value="${data.email }" data-options="required:true,validType:['length[5,30]']" /></td>
									</tr>
									<tr>
										<td colspan="2">
											<input value="提交" type="button" onclick="submitForm()" id="submitId" class="btn" />
											<input value="重置" type="button" onclick="$('#addDataId').form('clear');" id="resetId" class="btn" />
										</td>
										<td>
										</td>
									</tr>
								</tbody>
							</table>
						</form>
					</div>
				</div>
			</div>
	</body>
	<script type="text/javascript">
function submitForm() {
	//去掉 input 输入的 前后空格
	$("div[name='inputInfo']").find('input').each(function() {
		$(this).val($.trim($(this).val()));
	});
	// unique('loginID_id','unique.html','name');  
	//验证表单验证是否通过
	if (false == $('#addDataId').form('validate')) {
		$.messager.alert('消息', "页面有不符合规范内容，请正确填写！");
		return;
	}
	var fromdata = $('#addDataId').serialize();
	$.ajax( {
		type : "POST",
		data : fromdata,
		url : 'sys/user/updatemyselfaction.do',
		success : function(data) {
			$('#loading').window('close');
			if ("true" == data.success) {
				$.messager.alert('消息', data.message, "info", function() {
					$('#dialogDiv').dialog('close');
					//flushAfterSubmit();
					 // window.location = "login.do";
					 allExit();
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


function allExit(){
	$.ajax({ 
		url: "<%out.write(serverAddress); %>/teamserver/faces/logout.jsp", 
		type: "POST",
		success: function(data){
		}
		});
	var url="logout.do";
	window.location=url;
	
}
</script>
</html>

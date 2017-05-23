<%@page import="com.tera.sys.constant.BusinessConstants"%>
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
<link href="css/global.css" type="text/css" rel="stylesheet" />
<link href="css/icon.css" type="text/css" rel="stylesheet" />
<link href="css/default/easyui.css" type="text/css" rel="stylesheet" />
<script src="js/jquery.min.js" type="text/javascript"></script>
<script src="js/jquery.form.js" type="text/javascript"></script>
<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
</head>
<body>
	<div id="main">
		<div id="part1" class="part">
			<p class="title"><a href="javascript:void(0);">修改密码</a></p>
			<div class="content">
				<form id="updatePwd" >
					<input type="hidden" name="id" value="${data.id }" />
					<table>
						<tr>
							<td>登录ID:</td><td><input id="loginID_id" name="loginId" type="text" value="${data.loginId }" style="background-color:rgb(235, 235, 228)" readonly="readonly"   data-options="required:true,validType:['length[2,20]']"
												class="textbox easyui-validatebox" />
												</td>
						</tr>
						<tr>						
							<td>密码:</td><td><input id="password_id" name="password" type="password" value="" data-options="required:true,validType:['length[5,20]']"
												class="textbox easyui-validatebox" /></td>
						</tr>
						<tr>						
							<td>重复密码:</td><td><input id="confirm_password_id" name="confirm_password" value="" type="password" data-options="required:true,validType:['length[5,20]']"
												class="textbox easyui-validatebox" validType="equalTo['#password_id']" invalidMessage="两次输入密码不匹配"/></td>
						</tr>
					</table>
				</form>
<div align="left">
	<input value="提交" type="button" id="submitId" onclick="submitPwdForm()" class="btn"/> 
</div>
</div>
</div>
</div>
<script type="text/javascript">
	function submitPwdForm(){
	//去掉 input 输入的 前后空格
	$("div[name='inputInfo']").find('input').each(function(){
		$(this).val($.trim($(this).val()));
	});
	// unique('loginID_id','unique.html','name');  
	//验证表单验证是否通过
	if(false == $('#updatePwd').form('validate')){
		
		$.messager.alert('消息', "页面有不符合规范内容，请正确填写！");
		return;
	}
	var fromdata=$('#updatePwd').serialize();
			$.ajax({
				type : "POST",
				data : fromdata,
				url:'sys/user/updatePwd.do',
				success : function(data) {
					closeMask();
					if ("true"==data.success) {
						
						$.messager.alert('消息', data.message,"info", function(){
							//window.location = "sys/user/query.do";
			        	window.parent.removeTab();
						});
			
		            } else {
						$.messager.alert('消息', data.message);
		            }
					
				},
				error : function() {
					$.messager.alert('消息',"操作失败，请联系管理员！");
					closeMask();
				}
		   });
}
</script>
</body>
</html>

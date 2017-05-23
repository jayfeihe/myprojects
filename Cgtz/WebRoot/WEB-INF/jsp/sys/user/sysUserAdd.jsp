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
<title>用户管理</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type"/>
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
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">添加用户</a></p>
		<div class="content">
				<form id="addDataId">
					<table>
						<tr>
							<td>登录ID:</td><td><input id="loginID_id" name="loginId" type="text" data-options="required:true,validType:['length[2,20]']"
												class="textbox easyui-validatebox" />
												</td>
							<td>密码:</td><td><input id="password_id" name="password" type="password" data-options="required:true,validType:['length[5,20]']"
												class="textbox easyui-validatebox" /></td>
							<td>重复密码:</td><td><input id="confirm_password_id" name="confirm_password" type="password" data-options="required:true,validType:['length[5,20]']"
												class="textbox easyui-validatebox" validType="equalTo['#password_id']" invalidMessage="两次输入密码不匹配"/></td>
						</tr>
						<tr>
							<td>姓名:</td><td><input id="name_id" name="name" type="text" data-options="required:true,validType:['length[2,20]']"
											class="textbox easyui-validatebox"/></td>
							<td>性别:</td><td>
							<select id="gender_id" name="gender" class="textbox easyui-combobox" data-options="panelHeight:'auto',editable:false">
								<option value="M">男</option>
								<option value="F">女</option>
							</select>
							</td><td>电邮:</td><td><input id="email_id" name="email" type="text"  data-options="required:true,validType:['length[5,30]']"
												class="textbox easyui-validatebox" /></td>
						</tr>
						<tr>
							<td>电话:</td><td><input id="phone_id"  name="phone" type="text" 
											class="textbox easyui-validatebox"/></td>
							<td>移动电话:</td>
							<td colspan="3"><input id="mobile_id" validType="mobile" name="mobile" type="text" 
											class="textbox easyui-validatebox"/></td>
						</tr>
						 <tr>
							<td>状态:</td>
							<td colspan="5">
								<input name="state" type="radio" value="1" checked="checked"/>启用&nbsp;&nbsp;
								<input name="state" type="radio" value="2"/>挂起&nbsp;&nbsp;
								<input name="state" type="radio" value="0"/>关闭
								
							</td>
						</tr>
						<tr>
							<td>是否管理员:</td><td colspan="5"><input id="isAdmin_id" name="isAdmin" type="checkbox" value="1" /></td>
						</tr>
						<tr id="roleLevel" class="dataPermission">
							<td>所属层次:</td>
							<td colspan="5">
								<input name="roleLevel" type="radio" value="1" />总部
								<input name="roleLevel" type="radio" value="2" />分公司
								<input name="roleLevel" type="radio" value="3" />自定义
							</td>
						</tr>
					</table>
					
					<table class="dataPermission" id="zb" style="display: none;">
						<tr>
						  <td>选择部门:</td>
							<td>
								<input type="text" id="deptId" name="deptId" class="textbox easyui-combobox"/>
							</td>
							<td>选择角色:</td>
							<td>
								<input class="textbox easyui-combobox" id="roleId" name="roleId" />
							</td>
						</tr>
						<tr id="orgArea" > 
							<td>选择机构:</td>
							<td colspan="3">
								<c:forEach items="${orgs }" var="org">
									<input name="orgId" type="checkbox" value="${org.orgId }" />${org.orgName }
								</c:forEach>
							</td>
							
						</tr>
						
					</table>
					
					<table class="dataPermission" id="fgs" style="display: none;">
						<tr>
							<td>选择机构:</td>
							<td>
								<input class="textbox easyui-combotree" id="orgId" name="orgId" />
							</td>
							<td>选择部门:</td>
							<td>
								<input type="text" id="deptId" name="deptId" class="textbox easyui-combobox"/>
							</td>
							<td>选择角色:</td>
							<td>
								<input class="textbox easyui-combobox" id="roleId" name="roleId" />
							</td>
						</tr>
					</table>
					
					<table class="dataPermission" id="customer" style="display: none;">
						<tr>
							<td>选择机构:</td>
							<td>
								<input class="textbox easyui-combotree" id="orgId" name="orgId" />
							</td>
							<td>选择部门:</td>
							<td>
								<input type="text" id="deptId" name="deptId" class="textbox easyui-combobox"/>
							</td>
							<td>选择角色:</td>
							<td>
								<input class="textbox easyui-combobox" id="roleId" name="roleId" />
							</td>
						</tr>
					</table>
				</form>
	<hr color="#D3D3D3"/>
	
	<div align="left">
		<input value="提交" type="button" id="submitId" onclick="submit()" class="btn"/> 
		<input value="重置" type="button" id="resetId" onclick="$('#addDataId').form('clear');" class="btn"/>
	</div>
</div>
</div>
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
	
	var userId=$("#loginID_id").val();
	$.ajax({
		type : "POST",
		data : encodeURI("loginId=" + userId  + "&timestamp=" + (new Date()).getTime()),
		url:'sys/user/userbyusername.do',
		success : function(isUser) {
			if ("true"==isUser.success) {
				var fromdata=$('#addDataId').serialize();
				openMask();
				$.ajax({
					type : "POST",
					data : fromdata,
					url:'sys/user/addaction.do',
					success : function(data) {
						if ("true"==data.success) {
							$.messager.alert('消息', data.message,"info", function(){
								//window.location = "sys/user/query.do";
								window.parent.removeTab();
				        	});
			            } else {
							$.messager.alert('消息', data.message);
			            }
						closeMask();
					},
					error : function() {
						$.messager.alert('消息',"操作失败，请联系管理员！");
					}
					});
			}else{
				$.messager.alert('消息', isUser.message,"info", function(){
				closeMask();
                	
	        	});
			}
			
		}
	});
}

$(document).ready(function(){
	$("input[name='roleLevel']").on('change',function(){
		var rVal = $(this).val();
		if('1' == rVal) {
			$('#zb').show();
			$('#fgs').hide();
			$('#customer').hide();
			
			$('#fgs').find("#roleId").combobox("setValue",null);
			$('#fgs').find("#orgId").combotree("setValue",null);
			$('#fgs').find("#deptId").combobox("setValue",null);
			$('#customer').find("#roleId").combobox("setValue",null);
			$('#customer').find("#orgId").combotree("setValue",null);
			$('#customer').find("#deptId").combobox("setValue",null);
		}
		if('2' == rVal) {
			$('#fgs').show();
			$('#zb').hide();
			$('#customer').hide();
			
			$('#zb').find("#roleId").combobox("setValue",null);
			$('#zb').find("input[name='orgId']").attr('checked',false);
			$('#zb').find("#deptId").combobox("setValue",null);
			$('#customer').find("#roleId").combobox("setValue",null);
			$('#customer').find("#orgId").combotree("setValue",null);
			$('#customer').find("#deptId").combobox("setValue",null);
		}
		if('3' == rVal) {
			$('#customer').show();
			$('#fgs').hide();
			$('#zb').hide();
			
			$('#zb').find("#roleId").combobox("setValue",null);
			$('#zb').find("input[name='orgId']").attr('checked',false);
			$('#zb').find("#deptId").combobox("setValue",null);
			$('#fgs').find("#roleId").combobox("setValue",null);
			$('#fgs').find("#orgId").combotree("setValue",null);
			$('#fgs').find("#deptId").combobox("setValue",null);
		}
	});
	
	$("#zb").find("#roleId").combobox({
		url:'sys/role/queryRoleByLever.do?lever=1',
		textField:'name',
		valueField:'id',
		multiple:true,
		panelHeight:'auto',
		editable:false
		
	});
	$("#zb").find("#deptId").combobox({
		url:'sys/dept/listDept.do',
		textField:'name',
		valueField:'id',
		panelHeight:'auto',
		editable:false
	});
	
	$("#fgs").find("#orgId").combotree({
		url:"sys/org/selectList.do?nodeLevel=3",
		method:'get',
		required:false,
		panelHeight:'auto',
		//选择树节点触发事件  
	
	   onBeforeSelect : function(node) {  
	     //返回树对象  
	        /* var tree = $(this).tree;  
	        //选中的节点是否为叶子节点,如果不是叶子节点,清除选中  
	        var isLeaf = tree('isLeaf', node.target);  
	        if (!isLeaf) {  
	            //清除选中  
	            return false;
	        } */
	        if(node.id == '86') {
	        	return false;
	        }
	   }  
	});
	
	$("#fgs").find("#roleId").combobox({
		url:'sys/role/queryRoleByLever.do?lever=2',
		textField:'name',
		valueField:'id',
		multiple:true,
		panelHeight:'auto',
		editable:false
	});
	
	$("#fgs").find("#deptId").combobox({
		url:'sys/dept/listDept.do',
		textField:'name',
		valueField:'id',
		panelHeight:'auto',
		editable:false
	});
	
	$("#customer").find("#orgId").combotree({
		url:"sys/org/selectList.do?nodeLevel=3",
		method:'get',
		required:false,
		panelHeight:'auto',
		//选择树节点触发事件  
	    onBeforeSelect : function(node) {
	        //返回树对象  
	        /* var tree = $(this).tree;  
	        //选中的节点是否为叶子节点,如果不是叶子节点,清除选中  
	        var isLeaf = tree('isLeaf', node.target);  
	        if (!isLeaf) {  
	            //清除选中  
	            return false;
	        } */
	        /* if(node.id == '86') {
	        	return false;
	        } */
	    }
	});
	
	$("#customer").find("#roleId").combobox({
		url:'sys/role/queryRoleByLever.do?lever=3',
		textField:'name',
		valueField:'id',
		multiple:true,
		panelHeight:'auto',
		editable:false
	});
	
	$("#customer").find("#deptId").combobox({
		url:'sys/dept/listDept.do',
		textField:'name',
		valueField:'id',
		panelHeight:'auto',
		editable:false
	});
});

function isDataAccess(role_id) {
	$.ajax({
		url:'sys/role/isDataAccess.do',
		data:{'id':role_id},
		async:false,
		success:function(data){
			var flag = data.flag;
			// 有
			if('1' == flag) {
				$("#orgArea").show();
			}
			// 无
			if('0' == flag) {
				$("#orgArea").hide();
			}
		}
	});
}
	
</script>

<script type="text/javascript">
	$(function(){
		$("#isAdmin_id").on('click',function(){
			var checked = $(this).attr('checked');
			if (checked) {
				$(".dataPermission").hide();
				resetVal(".dataPermission");
			} else {
				$("#roleLevel").show();
			}
		});
	});
	
	function resetVal(ele) {
		var element = $(ele);
		element.find("input[type='text']").each(function(){
			$(this).val("");
		});
		element.find("input[type='radio']").each(function(){
			$(this).removeAttr("checked");
		});
		element.find("input[type='checkbox']").each(function(){
			$(this).removeAttr("checked");
		});
		element.find(".easyui-combobox").each(function(){
			if($(this).combobox("getValue") != null && $(this).combobox("getValue") != '' ){
				$(this).combobox("setValue","");
			}
		});
	}
</script>
</body>

</html>


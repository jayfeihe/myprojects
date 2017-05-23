<%@page import="com.tera.audit.common.constant.CommonConstant"%>
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
<title>档案录入</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
</head>
<body>
	<div class="easyui-tabs" id="preLoanUpdateTabs" style="width: 100%;" data-options="fit:true">
		<div title="档案录入" style="width: 100%;padding:2px">
			<form id="updateForm">
				<input type="hidden" name="id" value="${bean.id}"/>
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>档案信息</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>合同编号:</td>
						<td>
							<input id="contractId" type="text" class="textbox easyui-validatebox" data-options="required:true" name="contractId" value="${bean.contractId }"/>
						</td>
						<td>姓名/机构名称:</td>
						<td>
							<input id="name" type="text" class="textbox easyui-validatebox" data-options="required:true" name="name" value="${bean.name }"/>
						</td>
						<td>借款金额:</td>
						<td>
							<input id="loanAmt" type="text" class="textbox easyui-numberbox" name="loanAmt" value="${bean.loanAmt }"/> 元
						</td>
						<td>类型:</td>
						<td>
							<input id="type" type="text" class="textbox" name="type" value="${bean.type }"/>
						</td>
					</tr>
					<tr>
						<td>合同开始日期:</td>
						<td>
							<input id="startDate" type="text" class="textbox easyui-datebox" data-options="editable:false" name="startDate" value="${bean.startDateStr }"/>
						</td>
						<td>合同结束日期:</td>
						<td>
							<input id="endDate" type="text" class="textbox easyui-datebox" data-options="editable:false" name="endDate" value="${bean.endDateStr }"/>
						</td>
						<td>分公司:</td>
						<td>
							<input id="orgId" type="text" class="textbox easyui-combotree" name="orgId" value="${bean.orgId }"/>
						</td>
					</tr>
					
					<tr>
						<td>备注:</td>
						<td colspan="6">
							<textarea name="remarks" class="textbox easyui-validatebox"
								data-options="validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;">${bean.remarks }</textarea>
						</td>
					</tr>
				</table>
			</form>
			<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>操作</strong></div><hr color="#D3D3D3"/>
			<table>
				<tr>
					<td>
						<input type="button" value="保存" class="btn" onclick="updateFunction()"/>
					</td>
				</tr>
			</table>
			
			<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>影像</strong></div><hr color="#D3D3D3"/>
			<jsp:include page="/files/load.do">
				<jsp:param value="${bean.contractId}" name="loId"/>
				<jsp:param value="filesce14" name="sec"/>
				<jsp:param value="${bean.contractId}" name="bizKey"/>
				<jsp:param value="0" name="opt"/>
			</jsp:include>
	</div>
	</div>

<script type="text/javascript">
//更新保存
function updateFunction() {
	//验证表单验证是否通过
	if(false == $('#updateForm').form('validate')){
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}
	
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	
	//弹出异步加载 遮罩
	openMask();
	
	var params = $('#updateForm').serialize();
	
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "archive/save.do",
		data : params,
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			$(".btn").removeAttr("disabled");
			if ("true"==data.success) {
				$.messager.alert('消息', "成功", 'info', function(){
					location.replace("<%=basePath%>archive/update.do?id="+data.message);
					window.parent.submitForm('queryForm');
            	});
            } else {				
    			$.messager.alert('消息', data.message);
            }
		},
		error : function() {
			//关闭遮罩，弹出消息框
			closeMask();
			$.messager.confirm('消息', '数据加载失败,请联系系统管理员！');
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
	});
}

//页面加载完动作
$(document).ready(function (){
	$("#orgId").combotree({
		url:"sys/org/selectList.do",
		method:'get',
		required:false,
		panelHeight:'auto'
	});
}); 
</script>

<script type="text/javascript" >

window.parent.openMask();
$(window).load(function (){
	window.parent.closeMask();
});
</script>
</body>
</html>


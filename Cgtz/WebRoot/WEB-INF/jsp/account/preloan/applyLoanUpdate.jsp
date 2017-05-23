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
<title>法务初审</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
</head>
<body>
	<form id="applyLoanForm">
		<input type="hidden" name="loanId" value="${loanBase.loanId}"/>
		<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>申请放款</strong></div><hr color="#D3D3D3"/>
		<table>
			<tr>
				<td>决策:</td>
				<td>
					<input type="text" id="decision" name="decision" class="textbox easyui-combobox" 
						data-options="required:true,editable:false,panelHeight:'auto'" />
					<span id="nodeArea" style="visibility: hidden;" >
						<input type="text" id="node" name="node" class="textbox easyui-combobox" value="<%=CommonConstant.PROCESS_A %>"
							data-options="editable:false,panelHeight:'auto'"/>
					</span>
				</td>
			</tr>
			<tr>
				<td>说明:</td>
				<td colspan="6">
					<textarea name="remark" class="textbox easyui-validatebox" 
						data-options="required:true,validType:['length[0,500]']" 
						style="resize: none;width:625px;height:50px!important;"></textarea>
				</td>
			</tr>
			<tr>
				<td>
					<input type="button" value="提交" class="btn" onclick="updateApplyLoanFunction()"/>&nbsp;&nbsp;
				</td>
			</tr>
		</table>
	</form>

<script type="text/javascript">
//更新保存
function updateApplyLoanFunction() {
	//验证表单验证是否通过
	if(false == $('#applyLoanForm').form('validate')){
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}
	
	//去掉 input 输入的 前后空格
	$('#applyLoanForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	
	//弹出异步加载 遮罩
	openMask();
	
	var params = $('#applyLoanForm').serialize();
	
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "account/preloan/applyLoan.do",
		data : params,
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			//按钮生效
			$(".btn").removeAttr("disabled");
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					window.parent.removeTab();
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
	var type = '${nodeType}';
	
	$("#decision").combobox({
		textField:'keyValue',
		valueField:'keyProp',
		data:dataDictJson.decision,
		onChange:function(nVal,oVal){
			if("0" == nVal) {
				$("#nodeArea").css("visibility","visible");
				var url = "common/nodeorder/listNode.do?name=" + "<%=CommonConstant.PROCESS_H%>" + "&type="+type;
				$('#node').combobox('clear');
				$("#node").combobox('reload',url);
				var node = "<%=CommonConstant.PROCESS_A %>";
				$("#node").combobox("setValue",node);
			} else {
				$("#nodeArea").css("visibility","hidden");
				$("#node").combobox("setValue","");
			}
		}
	});
	
	$("#node").combobox({
		url:'common/nodeorder/listNode.do?name=<%=CommonConstant.PROCESS_H%>&type='+type,
		textField:'keyValue',
		valueField:'keyProp'
	});
}); 
</script>

<script type="text/javascript" >

window.parent.openMask();
$.parser.onComplete = function(){
	window.parent.closeMask();
}
</script>
</body>
</html>


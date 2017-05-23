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
<title>分公司审核</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
</head>
<body>
	<div class="easyui-tabs" id="riskFirstUpdateTabs" style="width: 100%;" data-options="fit:true">
		<div title="审核" style="width: 100%;padding:2px">
			<form id="riskUpdateForm">
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>处理</strong></div><hr color="#D3D3D3"/>
				<input type="hidden" name="loanId" value="${loanBase.loanId}"/>
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
					<tr id="judgeUserArea" style="display: none;">
						<td>选择评审会成员:</td>
						<td>
							<c:forEach items="${judgeUsers }" var="user">
								<input type="checkbox" name="judgeUsers" value="${user.loginId }"/>${user.name }
							</c:forEach>
						</td>
					</tr>
					<tr>
						<td>说明:</td>
						<td>
							<textarea name="remark" class="textbox easyui-validatebox" 
								data-options="required:true,validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;"></textarea>
						</td>
					</tr>
					<tr>
						<td>
							<input type="button" value="提交" class="btn" onclick="updateFunction()"/>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</form>
		</div>
		<c:if test="${loanBase.isRenew eq '1' }">
			<div title="续贷历史" data-options="href:'${basePath}loan/renew/readQuery.do?origLoanId=${loanBase.origLoanId}'" style="width: 100%;padding:10px"></div>
		</c:if>
		<div title="申请信息" data-options="href:'${basePath}loan/read.do?id=${loanBase.id}'" style="width: 100%;padding:10px"></div>
		<c:if test="${loanBase.isRenew eq '1' }">
			<div title="原申请信息" data-options="href:'${basePath}loan/origRead.do?id=${origLoanBase.id}'" style="width: 100%"></div>
		</c:if>
		<div title="申请影像资料" data-options="href:'${basePath}files/read2.do?loanId=${loanBase.loanId}&sec=${sec }&bizKey=${loanBase.loanId}'" style="padding:10px"></div>
		<c:if test="${isTgth eq '1' }">
			<div title="共同借款信息" data-options="href:'${basePath}loan/common/query.do?loanId=${origLoanBase.loanId}&opt=read'" style="width: 100%;padding:2px""></div>
		</c:if>
		<div title="质/抵押物" data-options="href:'${basePath}loan/collateral/query.do?loanId=${loanBase.loanId}&origLoanId=${origLoanBase.loanId }&opt=read'" style="width: 100%;padding:2px""></div>
		<div title="担保情况" data-options="href:'${basePath}loanguar/query.do?loanId=${loanBase.loanId}&origLoanId=${origLoanBase.loanId }&opt=read'" style="width: 100%;padding:2px""></div>
		<div title="诉讼情况" data-options="href:'${basePath}loan/law/query.do?loanId=${loanBase.loanId}&origLoanId=${origLoanBase.loanId }&opt=read'" style="width: 100%;padding:2px""></div>
		<div title="流程报告" data-options="href:'${basePath}bpm/getBpmLogs.do?bizKey=${loanBase.loanId}'" style="width: 100%;padding:10px"></div>
	</div>

<script type="text/javascript">
//更新保存
function updateFunction() {
	//验证表单验证是否通过
	if(false == $('#riskUpdateForm').form('validate')){
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}
	
	//去掉 input 输入的 前后空格
	$('#riskUpdateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	
	//弹出异步加载 遮罩
	openMask();
	
	var params = $('#riskUpdateForm').serialize();
	
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "judge/first/save.do",
		data : params,
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					window.parent.removeTab();
            	});
            } else {				
    			$.messager.alert('消息', data.message);
				//按钮生效
				$(".btn").removeAttr("disabled");
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
	
	$("#decision").combobox({
		textField:'keyValue',
		valueField:'keyProp',
		data:dataDictJson.decision,
		onChange:function(nVal,oVal){
			if("0" == nVal) {
				$("#nodeArea").css("visibility","visible");
				var url = "common/nodeorder/listNode.do?name=" + "<%=CommonConstant.PROCESS_D%>" + "&type=2";
				$('#node').combobox('clear');
				$("#node").combobox('reload',url);
				var node = "<%=CommonConstant.PROCESS_A %>";
				$("#node").combobox("setValue",node);
				$("#judgeUserArea").hide();
			} else {
				$("#nodeArea").css("visibility","hidden");
				$("#node").combobox("setValue","");
				$("#judgeUserArea").show();
			}
		}
	});
	
	$("#node").combobox({
		url:'common/nodeorder/listNode.do?name=<%=CommonConstant.PROCESS_D%>&type=2',
		textField:'keyValue',
		valueField:'keyProp'
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


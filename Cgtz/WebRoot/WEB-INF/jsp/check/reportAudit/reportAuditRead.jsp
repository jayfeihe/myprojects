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
<title>逾期报告查看</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
<style type="text/css">
</style>
</head>
<body>
<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">查看</a></p>
		<div class="content">
			<form id="updateForm" >
				<table>
					<tbody>
					<input id="id" name="id" type="hidden" size="35" value="${ bean.id}" />
					<tr>
<td>申请ID:</td>
<td><input id="loanId" name="loanId" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.loanId}"/></td>
<td>合同ID:</td>
<td><input id="contractId" name="contractId" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.contractId}"/></td>

<td>期数:</td>
<td><input id="num" name="num" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.num}"/></td>
</tr>
<tr>
<td>提交人:</td>
<td><input id="submitName" name="submitName" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.submitName}"/></td>

<td>提交时间:</td>
<td><input id="submitTme" name="submitTme" type="text" editable="false" class="textbox easyui-datetimebox" value="${bean.submitTmeStr}"/></td>
</tr>
<tr>
<td>分公司审核人:</td>
<td><input id="orgAuditName" name="orgAuditName" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.orgAuditName}"/></td>

<td>分公司审核时间:</td>
<td><input id="orgAuditTime" name="orgAuditTime" type="text" editable="false" class="textbox easyui-datetimebox" value="${bean.orgAuditTimeStr}"/></td>
</tr>
<tr>
<td>审批人:</td>
<td><input id="auditName" name="auditName" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.auditName}"/></td>
<td>审批时间:</td>
<td><input id="auditTime" name="auditTime" type="text" editable="false" class="textbox easyui-datetimebox" value="${bean.auditTimeStr}"/></td>
</tr>
<tr>
<td>提交说明:</td>
<td colspan="5"><textarea id="submitRemark" name="submitRemark" disabled="disabled" type="text" data-options="validType:['length[0,500]']" class="textbox easyui-validatebox" 
    style="resize:none;width:550px;height:100px!important;" value="${bean.submitRemark}">${bean.submitRemark}</textarea></td>
</tr>
<tr>
<td>分公司审核说明:</td>
<td colspan="5"><textarea id="orgAuditRemark" name="orgAuditRemark" disabled="disabled" type="text" data-options="validType:['length[0,500]']" class="textbox easyui-validatebox" 
    style="resize:none;width:550px;height:100px!important;">${bean.orgAuditRemark}</textarea></td>
</tr>
<tr>
<td>分公司审核结果:</td>
<td colspan="5"><input id="orgAuditResult" name="orgAuditResult" type="text" data-options="validType:['length[0,3]']" class="textbox easyui-validatebox" 
<c:choose>
<c:when test="${bean.orgAuditResult eq '0'}">value="未通过"</c:when>
<c:when test="${bean.orgAuditResult eq '1'}">value="已通过"</c:when>
<c:otherwise>value="无"</c:otherwise>
</c:choose>
/></td>
</tr>
<tr>
<td>审批说明:</td>
<td colspan="5"><textarea id="auditRemark" name="auditRemark" disabled="disabled" type="text" data-options="validType:['length[0,500]']" class="textbox easyui-validatebox" 
    style="resize:none;width:550px;height:100px!important;" value="${bean.auditRemark}">${bean.auditRemark}</textarea></td>
</tr>
<tr>
<td>审批结果:</td>
<td colspan="5"><input id="auditResult" name="auditResult" type="text" data-options="validType:['length[0,3]']" class="textbox easyui-validatebox" 
<c:choose>
<c:when test="${bean.auditResult eq '1'}">value="未审核"</c:when>
<c:when test="${bean.auditResult eq '2'}">value="未通过"</c:when>
<c:when test="${bean.auditResult eq '3'}">value="已通过"</c:when>
</c:choose>
/></td>
</tr>

					<tr>
						<td>
							<!-- <input type="button" value="返回" class="btn" onclick="javascript:back()"/> -->
						</td>
						<td></td>
					</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>

<div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
	<img src="img/loading.gif" alt="加载中..." />
</div>

</body>

<script type="text/javascript">

//返回
function back(){
	window.history.go(-1);
}

//打开Loading遮罩并修改样式
function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
}


//页面加载完动作
$(document).ready(function (){
	//将Form元素禁用
	$("#updateForm").find("input").attr("disabled", "disabled");
	$("#updateForm").find("select").attr("disabled", "disabled");
	//将easyui控件禁用
	$("#updateForm").find(".easyui-combobox").combo('disable');
	$("#updateForm").find(".easyui-numberbox").numberbox('disable');
	$("#updateForm").find(".easyui-numberspinner").numberspinner('disable');
	$("#updateForm").find(".easyui-datebox").datebox('disable');
	$("#updateForm").find(".easyui-datetimebox").datetimebox('disable');
	
	//填充select数据样例
	/*
	var tsurl="sys/datadictionary/listjason.do?keyName=repaymethod";
	$("#repayMethod").combobox("clear");
	$('#repayMethod').combobox({url:tsurl, valueField:'keyProp', textField:'keyValue'});
	*/
});

</script>
</html>


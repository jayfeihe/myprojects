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
<title>短信日志表查看</title>
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
						<td>模板名称:</td>
						<td><input id="templateName" name="templateName" type="text" class="textbox easyui-validatebox" value="${bean.templateName}"/></td>
						<td>信息ID:</td>
						<td><input id="msgId" name="msgId" type="text" data-options="validType:['length[0,32]']" class="textbox easyui-validatebox" value="${bean.msgId}"/></td>
						<td>类型:</td>
						<td><input id="type" name="type" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-combobox" value="${bean.type}"/></td>
					</tr>
					<tr>
						<td>合同编号:</td>
						<td><input id="contractNo" name="contractNo" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.contractNo}"/></td>
						<td>客户姓名:</td>
						<td><input id="customerName" name="customerName" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.customerName}"/></td>
						<td>联系方式:</td>
						<td><input id="mobileTel" name="mobileTel" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.mobileTel}"/></td>
					</tr>
					<tr>
						<td>证件类型:</td>
						<td><input id="idType" name="idType" type="text" data-options="validType:['length[0,20]']" class="easyui-combobox" value="${bean.idType}"/></td>
						<td>证件号码:</td>
						<td><input id="idNo" name="idNo" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.idNo}"/></td>
						<td>还款日:</td>
						<td><input id="repaymentDate" name="repaymentDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.repaymentDateStr}"/></td>
					</tr>
					<tr>
						<td>发送状态:</td>
						<td><input id="sendState" name="sendState" type="text" class="textbox easyui-validatebox" value="${bean.sendState}"/></td>
						<td>发送时间:</td>
						<td><input id="sendTime" name="sendTime" type="text" editable="false" class="textbox easyui-datetimebox" value="${bean.sendTimeStr}"/></td>
						<td>天数:</td>
						<td><input id="days" name="days" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.days}"/></td>
					</tr>
					<tr>
						<td>接收状态:</td>
						<td><input id="receiveState" name="receiveState" type="text" class="textbox easyui-validatebox" value="${bean.receiveState}"/></td>
						<td>接收时间:</td>
						<td><input id="receiveTime" name="receiveTime" type="text" editable="false" class="textbox easyui-datetimebox" value="${bean.receiveTimeStr}"/></td>
						<td>备注:</td>
						<td><input id="remark" name="remark" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.remark}"/></td>
					</tr>
					<tr>
						<td>信息内容:</td>
						<td colspan="5">
						<textarea rows="5" style="width:98%" name="msgContent" id="msgContent" data-options="validType:['length[0,500]']" class="easyui-validatebox">${bean.msgContent}</textarea>
						</td>
					</tr>
					<tr>
						<td colspan="6">
							<input id="btn_id" type="button" value="返回" class="btn" onclick="javascript:back()"/>
						</td>
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
	
    var idTypeurl = "sys/datadictionary/listjason.do?keyName=personidtype";
	$("#idType").combobox("clear");
	$('#idType').combobox({
		url: idTypeurl,
		valueField: 'keyProp',
		textField: 'keyValue'
	});
	$('#idType').combobox('select', '${bean.idType}');
    var typeurl = "sys/datadictionary/listjason.do?keyName=messagetype";
	$("#type").combobox("clear");
	$('#type').combobox({
		url: typeurl,
		valueField: 'keyProp',
		textField: 'keyValue'
	});
	$('#type').combobox('select', '${bean.type}');
	
	//将Form元素禁用
	$("#updateForm").find("input").attr("disabled", "disabled");
	$("#updateForm").find("textarea").attr("disabled", "disabled");
	$("#updateForm").find("select").attr("disabled", "disabled");
	//将easyui控件禁用
	$("#updateForm").find(".easyui-combobox").combo('disable');
	$("#updateForm").find(".easyui-numberbox").numberbox('disable');
	$("#updateForm").find(".easyui-numberspinner").numberspinner('disable');
	$("#updateForm").find(".easyui-datebox").datebox('disable');
	$("#updateForm").find(".easyui-datetimebox").datetimebox('disable');
	
	$("#btn_id").removeAttr("disabled");
	//填充select数据样例
	/*
	var tsurl="sys/datadictionary/listjason.do?keyName=repaymethod";
	$("#repayMethod").combobox("clear");
	$('#repayMethod').combobox({url:tsurl, valueField:'keyProp', textField:'keyValue'});
	*/
});

</script>
</html>


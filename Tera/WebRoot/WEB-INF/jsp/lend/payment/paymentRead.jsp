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
<title>支付明细表查看</title>
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
			<table>
				<tbody>
				<input id="id" name="id" type="hidden" size="35" value="${ bean.id}" />
				<tr>
<td><SPAN style="color:red">*</SPAN>ID:</td>
<td><input id="id" name="id" type="text" data-options="required:true,min:0,precision:0" editable="false" class="textbox easyui-numberbox" value="${bean.id}" disabled="disabled" /></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>合同号:</td>
<td><input id="contractNo" name="contractNo" type="text" data-options="required:true,validType:['length[0,25]']" class="textbox easyui-validatebox" value="${bean.contractNo}" disabled="disabled" /></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>收付:</td>
<td><input id="inOut" name="inOut" type="text" data-options="required:true,validType:['length[0,1]']" class="textbox easyui-validatebox" value="${bean.inOut}" disabled="disabled" /></td>
</tr>
<tr>
<td>科目:</td>
<td><input id="subject" name="subject" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.subject}" disabled="disabled" /></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>计划金额:</td>
<td><input id="planAmount" name="planAmount" type="text" required="true" editable="false" data-options="required:true,min:0,precision:2" class="textbox easyui-numberbox" value="${bean.planAmount}" disabled="disabled" /></td>
</tr>
<tr>
<td>实际金额:</td>
<td><input id="actualAmount" name="actualAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.actualAmount}" disabled="disabled" /></td>
</tr>
<tr>
<td>来源:</td>
<td><input id="source" name="source" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.source}" disabled="disabled" /></td>
</tr>
<tr>
<td>期数:</td>
<td><input id="periodNum" name="periodNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.periodNum}" disabled="disabled" /></td>
</tr>
<tr>
<td>发盘:</td>
<td><input id="sendFlag" name="sendFlag" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.sendFlag}" disabled="disabled" /></td>
</tr>
<tr>
<td>状态:</td>
<td><input id="state" name="state" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.state}" disabled="disabled" /></td>
</tr>
<tr>
<td>原因:</td>
<td><input id="reason" name="reason" type="text" data-options="validType:['length[0,200]']" class="textbox easyui-validatebox" value="${bean.reason}" disabled="disabled" /></td>
</tr>
<tr>
<td>操作员:</td>
<td><input id="operator" name="operator" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.operator}" disabled="disabled" /></td>
</tr>
<tr>
<td>所属机构:</td>
<td><input id="orgId" name="orgId" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.orgId}" disabled="disabled" /></td>
</tr>
<tr>
<td>创建日期:</td>
<td><input id="createTime" name="createTime" type="text" editable="false" class="textbox easyui-datetimebox" value="${bean.createTimeStr}" disabled="disabled" /></td>
</tr>
<tr>
<td>修改日期:</td>
<td><input id="updateTime" name="updateTime" type="text" editable="false" class="textbox easyui-datetimebox" value="${bean.updateTimeStr}" disabled="disabled" /></td>
</tr>

				<tr>
					<td>
						<input type="button" value="返回" class="btn" onclick="javascript:back()"/>
					</td>
					<td></td>
				</tr>
				</tbody>
			</table>			
		</div>
	</div>
</div>
</body>

<script type="text/javascript">
//返回
function back(){
	window.history.go(-1);
}

//页面加载完动作
$(document).ready(function (){
	//填充select数据样例
	/*
	var tsurl="sys/datadictionary/listjason.do?keyName=repaymethod";
	$("#repayMethod").combobox("clear");
	$('#repayMethod').combobox({url:tsurl, valueField:'keyProp', textField:'keyValue'});
	*/	
});
</script>
</html>


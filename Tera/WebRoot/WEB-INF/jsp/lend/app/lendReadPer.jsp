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
<title>财富端个人客户申请表</title>
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
		<p class="title"><a href="javascript:void(0);">个人客户录入申请</a></p>
		<div class="content">
			<table>
				<tbody>
				<tr>
					<td>证件类型:</td>
					<td><input value="${bean.idType}" disabled="disabled" class="easyui-combobox" name="idType" id="idType" style="width:148px;" editable="false" data-options="required:true,validType:['length[0,18]']"/></td>
					<td>证件号码:</td>
					<td><input value="${bean.idNo}" disabled="disabled" id="idNo" onblur="getCustomer();" name="idNo" type="text" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" /></td>
					<td>申请类型:</td>
					<td><input value="${bean.appType}" disabled="disabled" id="appType" name="appType" data-options="required:true,validType:['length[0,18]']" class="easyui-combobox" style="width:148px;" editable="false" /></td>
				</tr>
				<tr>
					<td>姓名:</td>
					<td><input value="${bean.name}" disabled="disabled" id="name" name="name" type="text" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" /></td>
					<td>手机号:</td>
					<td><input value="${bean.mobile}" disabled="disabled" id="mobile" name="mobile" type="text" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" /></td>
					<td>电话:</td>
					<td><input value="${bean.phone}" disabled="disabled" id="phone" name="phone" type="text" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>出借金额:</td>
					<td><input value="${bean.amount}" disabled="disabled" id="amount" name="amount" type="text" editable="false" data-options="required:true,min:0,precision:2" class="textbox easyui-numberbox" /></td>
					<td>服务截至日期:</td>
					<td><input value="${bean.serviceEndDate}" disabled="disabled" id="serviceEndDate" name="serviceEndDate" type="text" data-options="required:true" editable="false" class="textbox easyui-datebox" /></td>
					<td>产品:</td>
					<td><input value="${bean.product}" disabled="disabled" id="product" name="product" data-options="required:true,validType:['length[0,50]']" class="easyui-combobox" style="width:148px;" editable="false" /></td>
				</tr>
				<tr>
					<td>出借账户名:</td>
					<td><input value="${bean.lendAccName}" disabled="disabled" id="lendAccName" name="lendAccName" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" /></td>
					<td>出借开户银行:</td>
					<td><input value="${bean.lendAccBank}" disabled="disabled" id="lendAccBank" name="lendAccBank" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" /></td>
					<td>出借银行账号:</td>
					<td><input value="${bean.lendAccount}" disabled="disabled" id="lendAccount" name="lendAccount" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>回款账户名:</td>
					<td><input value="${bean.repayAccName}" disabled="disabled" id="repayAccName" name="repayAccName" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" /></td>
					<td>回款开户银行:</td>
					<td><input value="${bean.repayAccBank}" disabled="disabled" id="repayAccBank" name="repayAccBank" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" /></td>
					<td>回款银行账号:</td>
					<td><input value="${bean.repayAccount}" disabled="disabled" id="repayAccount" name="repayAccount" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>撮合类型:</td>
					<td><input value="${bean.matchType}" disabled="disabled" id="matchType" name="matchType" data-options="required:true,validType:['length[0,50]']" class="easyui-combobox" style="width:148px;" editable="false" /></td>
				</tr>
				<tr>
					<td>
						<input type="button" value="返回" class="btn" onclick="javascript:back()"/>
					</td>
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
$(document).ready(function(){
	//填充select数据 个人证件类型
//var	tsurl="sys/datadictionary/listjason.do?keyName=personidtype";
	$("#idType").combobox("clear");
	$('#idType').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.personidtype
	});
	
	//tsurl="sys/datadictionary/listjason.do?keyName=lendapptype";
	$("#appType").combobox("clear");
	$('#appType').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.lendapptype
	});

	//tsurl="sys/datadictionary/listjason.do?keyName=matchtype";
	$("#matchType").combobox("clear");
	$('#matchType').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.matchtype
	});
	
	tsurl="product/hedao/listjason.do?type=1";
	$("#product").combobox("clear");
	$('#product').combobox({url:tsurl, valueField:'name', textField:'name'});
});


</script>
</html>


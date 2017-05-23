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
<title>财富端申请表查看</title>
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
<td><SPAN style="color:red">*</SPAN>申请ID:</td>
<td><input id="appId" name="appId" type="text" data-options="required:true,validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.appId}" disabled="disabled" /></td>
</tr>
<tr>
<td>客户ID:</td>
<td><input id="customerNo" name="customerNo" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.customerNo}" disabled="disabled" /></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>类型-个人/机构:</td>
<td><input id="customerType" name="customerType" type="text" data-options="required:true,validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.customerType}" disabled="disabled" /></td>
</tr>
<tr>
<td>客户等级:</td>
<td><input id="customerLever" name="customerLever" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.customerLever}" disabled="disabled" /></td>
</tr>
<tr>
<td>姓名:</td>
<td><input id="name" name="name" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.name}" disabled="disabled" /></td>
</tr>
<tr>
<td>手机号:</td>
<td><input id="mobile" name="mobile" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.mobile}" disabled="disabled" /></td>
</tr>
<tr>
<td>电话:</td>
<td><input id="phone" name="phone" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.phone}" disabled="disabled" /></td>
</tr>
<tr>
<td>证件类型:</td>
<td><input id="idType" name="idType" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.idType}" disabled="disabled" /></td>
</tr>
<tr>
<td>证件号码:</td>
<td><input id="idNo" name="idNo" type="text" data-options="validType:['length[0,18]','idNo']"  class="textbox easyui-validatebox" value="${bean.idNo}" disabled="disabled" /></td>
</tr>
<tr>
<td>财务负责人姓名:</td>
<td><input id="cfoName" name="cfoName" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.cfoName}" disabled="disabled" /></td>
</tr>
<tr>
<td>产品:</td>
<td><input id="product" name="product" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.product}" disabled="disabled" /></td>
</tr>
<tr>
<td>服务截至日期:</td>
<td><input id="serviceEndDate" name="serviceEndDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.serviceEndDateStr}" disabled="disabled" /></td>
</tr>
<tr>
<td>申请类型:</td>
<td><input id="appType" name="appType" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.appType}" disabled="disabled" /></td>
</tr>
<tr>
<td>出借金额:</td>
<td><input id="amount" name="amount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.amount}" disabled="disabled" /></td>
</tr>
<tr>
<td>出借账户名:</td>
<td><input id="lendAccName" name="lendAccName" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.lendAccName}" disabled="disabled" /></td>
</tr>
<tr>
<td>出借开户银行:</td>
<td><input id="lendAccBank" name="lendAccBank" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.lendAccBank}" disabled="disabled" /></td>
</tr>
<tr>
<td>出借银行账号:</td>
<td><input id="lendAccount" name="lendAccount" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.lendAccount}" disabled="disabled" /></td>
</tr>
<tr>
<td>回款账户名:</td>
<td><input id="repayAccName" name="repayAccName" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.repayAccName}" disabled="disabled" /></td>
</tr>
<tr>
<td>回款开户银行:</td>
<td><input id="repayAccBank" name="repayAccBank" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.repayAccBank}" disabled="disabled" /></td>
</tr>
<tr>
<td>回款银行账号:</td>
<td><input id="repayAccount" name="repayAccount" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.repayAccount}" disabled="disabled" /></td>
</tr>
<tr>
<td>撮合类型:</td>
<td><input id="matchType" name="matchType" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox" value="${bean.matchType}" disabled="disabled" /></td>
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
<td>状态:</td>
<td><input id="state" name="state" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox" value="${bean.state}" disabled="disabled" /></td>
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
</script>
</html>


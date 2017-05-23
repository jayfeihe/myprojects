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
<title>借款申请审批表查看</title>
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
				<input id="appId" name="appId" type="hidden" size="35" value="${ bean.appId}" />
				<tr>
<td><SPAN style="color:red">*</SPAN>申请ID:</td>
<td><input id="appId" name="appId" type="text" data-options="required:true,validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.appId}" disabled="disabled" /></td>
</tr>
<tr>
<td>借款人信用正常标志:</td>
<td><input id="selfCreditMark" name="selfCreditMark" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox" value="${bean.selfCreditMark}" disabled="disabled" /></td>
</tr>
<tr>
<td>借款人贷记卡违约值:</td>
<td><input id="selfCreditCardDvalue" name="selfCreditCardDvalue" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox" value="${bean.selfCreditCardDvalue}" disabled="disabled" /></td>
</tr>
<tr>
<td>借款人贷记卡金额:</td>
<td><input id="selfCreditCardDamount" name="selfCreditCardDamount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.selfCreditCardDamount}" disabled="disabled" /></td>
</tr>
<tr>
<td>借款人贷记卡备注:</td>
<td><input id="selfCreditCardRemark" name="selfCreditCardRemark" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.selfCreditCardRemark}" disabled="disabled" /></td>
</tr>
<tr>
<td>借款人贷款违约值:</td>
<td><input id="selfLoanDvalue" name="selfLoanDvalue" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox" value="${bean.selfLoanDvalue}" disabled="disabled" /></td>
</tr>
<tr>
<td>借款人贷款金额:</td>
<td><input id="selfLoanDamount" name="selfLoanDamount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.selfLoanDamount}" disabled="disabled" /></td>
</tr>
<tr>
<td>借款人贷款备注:</td>
<td><input id="selfLoanRemark" name="selfLoanRemark" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.selfLoanRemark}" disabled="disabled" /></td>
</tr>
<tr>
<td>配偶信用正常标志:</td>
<td><input id="spouseCreditMark" name="spouseCreditMark" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox" value="${bean.spouseCreditMark}" disabled="disabled" /></td>
</tr>
<tr>
<td>配偶贷记卡违约值:</td>
<td><input id="spouseCreditCardDvalue" name="spouseCreditCardDvalue" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox" value="${bean.spouseCreditCardDvalue}" disabled="disabled" /></td>
</tr>
<tr>
<td>配偶贷记卡金额:</td>
<td><input id="spouseCreditCardDamount" name="spouseCreditCardDamount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.spouseCreditCardDamount}" disabled="disabled" /></td>
</tr>
<tr>
<td>配偶贷记卡备注:</td>
<td><input id="spouseCreditCardRemark" name="spouseCreditCardRemark" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.spouseCreditCardRemark}" disabled="disabled" /></td>
</tr>
<tr>
<td>配偶贷款违约值:</td>
<td><input id="spouseLoanDvalue" name="spouseLoanDvalue" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox" value="${bean.spouseLoanDvalue}" disabled="disabled" /></td>
</tr>
<tr>
<td>配偶贷款金额:</td>
<td><input id="spouseLoanDamount" name="spouseLoanDamount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.spouseLoanDamount}" disabled="disabled" /></td>
</tr>
<tr>
<td>配偶贷款备注:</td>
<td><input id="spouseLoanRemark" name="spouseLoanRemark" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.spouseLoanRemark}" disabled="disabled" /></td>
</tr>
<tr>
<td>企业信用正常标志:</td>
<td><input id="entLoanMark" name="entLoanMark" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox" value="${bean.entLoanMark}" disabled="disabled" /></td>
</tr>
<tr>
<td>企业贷款违约值:</td>
<td><input id="entLoanDvalue" name="entLoanDvalue" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox" value="${bean.entLoanDvalue}" disabled="disabled" /></td>
</tr>
<tr>
<td>企业贷款余额:</td>
<td><input id="entLoanDamount" name="entLoanDamount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.entLoanDamount}" disabled="disabled" /></td>
</tr>
<tr>
<td>企业贷款到期日:</td>
<td><input id="entLoanExpiryDate" name="entLoanExpiryDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.entLoanExpiryDateStr}" disabled="disabled" /></td>
</tr>
<tr>
<td>企业贷款备注:</td>
<td><input id="entLoanRemark" name="entLoanRemark" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.entLoanRemark}" disabled="disabled" /></td>
</tr>
<tr>
<td>法院执行-公司:</td>
<td><input id="courtCompany" name="courtCompany" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox" value="${bean.courtCompany}" disabled="disabled" /></td>
</tr>
<tr>
<td>法院执行-公司备注:</td>
<td><input id="courtCompanyRemark" name="courtCompanyRemark" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.courtCompanyRemark}" disabled="disabled" /></td>
</tr>
<tr>
<td>法院执行-公司法人:</td>
<td><input id="courtLegal" name="courtLegal" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox" value="${bean.courtLegal}" disabled="disabled" /></td>
</tr>
<tr>
<td>法院执行-公司法人备注:</td>
<td><input id="courtLegalRemark" name="courtLegalRemark" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.courtLegalRemark}" disabled="disabled" /></td>
</tr>
<tr>
<td>法院执行-抵押担保人:</td>
<td><input id="courtGuarantee" name="courtGuarantee" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox" value="${bean.courtGuarantee}" disabled="disabled" /></td>
</tr>
<tr>
<td>法院执行-抵押担保人备注:</td>
<td><input id="courtGuaranteeRemark" name="courtGuaranteeRemark" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.courtGuaranteeRemark}" disabled="disabled" /></td>
</tr>
<tr>
<td>工商网:</td>
<td><input id="industryNetwork" name="industryNetwork" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox" value="${bean.industryNetwork}" disabled="disabled" /></td>
</tr>
<tr>
<td>工商网备注:</td>
<td><input id="industryNetworkRemark" name="industryNetworkRemark" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.industryNetworkRemark}" disabled="disabled" /></td>
</tr>
<tr>
<td>组织结构网:</td>
<td><input id="orgNetwork" name="orgNetwork" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox" value="${bean.orgNetwork}" disabled="disabled" /></td>
</tr>
<tr>
<td>组织结构网备注:</td>
<td><input id="orgNetworkRemark" name="orgNetworkRemark" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.orgNetworkRemark}" disabled="disabled" /></td>
</tr>
<tr>
<td>114查询备注:</td>
<td><input id="network114" name="network114" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.network114}" disabled="disabled" /></td>
</tr>
<tr>
<td>网查备注:</td>
<td><input id="network114Remark" name="network114Remark" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.network114Remark}" disabled="disabled" /></td>
</tr>
<tr>
<td>电话调查-法人:</td>
<td><input id="telLegal" name="telLegal" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox" value="${bean.telLegal}" disabled="disabled" /></td>
</tr>
<tr>
<td>电话调查-法人备注:</td>
<td><input id="telLegalRemark" name="telLegalRemark" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.telLegalRemark}" disabled="disabled" /></td>
</tr>
<tr>
<td>电话调查-代理人:</td>
<td><input id="telAgent" name="telAgent" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox" value="${bean.telAgent}" disabled="disabled" /></td>
</tr>
<tr>
<td>电话调查-代理人备注:</td>
<td><input id="telAgentRemark" name="telAgentRemark" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.telAgentRemark}" disabled="disabled" /></td>
</tr>
<tr>
<td>电话调查-财务主管:</td>
<td><input id="telCfo" name="telCfo" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox" value="${bean.telCfo}" disabled="disabled" /></td>
</tr>
<tr>
<td>电话调查-财务主管备注:</td>
<td><input id="telCfoRemark" name="telCfoRemark" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.telCfoRemark}" disabled="disabled" /></td>
</tr>
<tr>
<td>电话调查-购销合同:</td>
<td><input id="telContract" name="telContract" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox" value="${bean.telContract}" disabled="disabled" /></td>
</tr>
<tr>
<td>电话调查-购销合同备注:</td>
<td><input id="telContractRemark" name="telContractRemark" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.telContractRemark}" disabled="disabled" /></td>
</tr>
<tr>
<td>审批金额:</td>
<td><input id="approvalAmount" name="approvalAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.approvalAmount}" disabled="disabled" /></td>
</tr>
<tr>
<td>审批期限:</td>
<td><input id="approvalPeriod" name="approvalPeriod" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.approvalPeriod}" disabled="disabled" /></td>
</tr>
<tr>
<td>审批服务费率:</td>
<td><input id="approvalServiceRate" name="approvalServiceRate" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.approvalServiceRate}" disabled="disabled" /></td>
</tr>
<tr>
<td>需要第三方购销合同:</td>
<td><input id="thirdPartyContract" name="thirdPartyContract" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox" value="${bean.thirdPartyContract}" disabled="disabled" /></td>
</tr>
<tr>
<td>担保条件:</td>
<td><input id="guaranteeCondition" name="guaranteeCondition" type="text" data-options="validType:['length[0,500]']" class="textbox easyui-validatebox" value="${bean.guaranteeCondition}" disabled="disabled" /></td>
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


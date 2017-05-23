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
<title>借款端申请表查看</title>
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
<td><input id="id" name="id" type="text" data-options="required:true,min:0,precision:0" editable="false" class="textbox easyui-numberbox"value="${bean.id}" disabled="disabled" /></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>申请ID:</td>
<td><input id="appId" name="appId" type="text" data-options="required:true,validType:['length[0,30]']" class="textbox easyui-validatebox"value="${bean.appId}" disabled="disabled" /></td>
</tr>
<tr>
<td>客户ID:</td>
<td><input id="customerNo" name="customerNo" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.customerNo}" disabled="disabled" /></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>主借款人标识:</td>
<td><input id="mainFlag" name="mainFlag" type="text" data-options="required:true,validType:['length[0,1]']" class="textbox easyui-validatebox"value="${bean.mainFlag}" disabled="disabled" /></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>类型-抵押/信用:</td>
<td><input id="type" name="type" type="text" data-options="required:true,validType:['length[0,1]']" class="textbox easyui-validatebox"value="${bean.type}" disabled="disabled" /></td>
</tr>
<tr>
<td>咨询ID:</td>
<td><input id="consultId" name="consultId" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox"value="${bean.consultId}" disabled="disabled" /></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>类型-个人/机构:</td>
<td><input id="customerType" name="customerType" type="text" data-options="required:true,validType:['length[0,1]']" class="textbox easyui-validatebox"value="${bean.customerType}" disabled="disabled" /></td>
</tr>
<tr>
<td>姓名/机构全称:</td>
<td><input id="name" name="name" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.name}" disabled="disabled" /></td>
</tr>
<tr>
<td>手机号:</td>
<td><input id="mobile" name="mobile" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.mobile}" disabled="disabled" /></td>
</tr>
<tr>
<td>电话:</td>
<td><input id="phone" name="phone" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.phone}" disabled="disabled" /></td>
</tr>
<tr>
<td>证件类型:</td>
<td><input id="idType" name="idType" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.idType}" disabled="disabled" /></td>
</tr>
<tr>
<td>证件号码:</td>
<td><input id="idNo" name="idNo" type="text" data-options="validType:['length[0,18]','idNo']"  class="textbox easyui-validatebox"value="${bean.idNo}" disabled="disabled" /></td>
</tr>
<tr>
<td>婚姻:</td>
<td><input id="marriage" name="marriage" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox"value="${bean.marriage}" disabled="disabled" /></td>
</tr>
<tr>
<td>通讯地址-省:</td>
<td><input id="addProvince" name="addProvince" type="text" data-options="validType:['length[0,10]']" class="textbox easyui-validatebox"value="${bean.addProvince}" disabled="disabled" /></td>
</tr>
<tr>
<td>通讯地址-市:</td>
<td><input id="addCity" name="addCity" type="text" data-options="validType:['length[0,10]']" class="textbox easyui-validatebox"value="${bean.addCity}" disabled="disabled" /></td>
</tr>
<tr>
<td>通讯地址-区县:</td>
<td><input id="addCounty" name="addCounty" type="text" data-options="validType:['length[0,10]']" class="textbox easyui-validatebox"value="${bean.addCounty}" disabled="disabled" /></td>
</tr>
<tr>
<td>通讯地址:</td>
<td><input id="address" name="address" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox"value="${bean.address}" disabled="disabled" /></td>
</tr>
<tr>
<td>行业代码1:</td>
<td><input id="industry1" name="industry1" type="text" data-options="validType:['length[0,10]']" class="textbox easyui-validatebox"value="${bean.industry1}" disabled="disabled" /></td>
</tr>
<tr>
<td>行业代码2:</td>
<td><input id="industry2" name="industry2" type="text" data-options="validType:['length[0,10]']" class="textbox easyui-validatebox"value="${bean.industry2}" disabled="disabled" /></td>
</tr>
<tr>
<td>行业代码3:</td>
<td><input id="industry3" name="industry3" type="text" data-options="validType:['length[0,10]']" class="textbox easyui-validatebox"value="${bean.industry3}" disabled="disabled" /></td>
</tr>
<tr>
<td>借款金额:</td>
<td><input id="amount" name="amount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox"value="${bean.amount}" disabled="disabled" /></td>
</tr>
<tr>
<td>用途:</td>
<td><input id="useage" name="useage" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox"value="${bean.useage}" disabled="disabled" /></td>
</tr>
<tr>
<td>详细用途:</td>
<td><input id="detailUseage" name="detailUseage" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox"value="${bean.detailUseage}" disabled="disabled" /></td>
</tr>
<tr>
<td>产品:</td>
<td><input id="product" name="product" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.product}" disabled="disabled" /></td>
</tr>
<tr>
<td>期限:</td>
<td><input id="period" name="period" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox"value="${bean.period}" disabled="disabled" /></td>
</tr>
<tr>
<td>服务费率:</td>
<td><input id="sreviceFeeRate" name="sreviceFeeRate" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox"value="${bean.sreviceFeeRate}" disabled="disabled" /></td>
</tr>
<tr>
<td>借款利率:</td>
<td><input id="interestRate" name="interestRate" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox"value="${bean.interestRate}" disabled="disabled" /></td>
</tr>
<tr>
<td>收款账户名:</td>
<td><input id="lendAccName" name="lendAccName" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.lendAccName}" disabled="disabled" /></td>
</tr>
<tr>
<td>收款开户银行:</td>
<td><input id="lendAccBank" name="lendAccBank" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.lendAccBank}" disabled="disabled" /></td>
</tr>
<tr>
<td>收款银行账号:</td>
<td><input id="lendAccount" name="lendAccount" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.lendAccount}" disabled="disabled" /></td>
</tr>
<tr>
<td>还款账户名:</td>
<td><input id="repayAccName" name="repayAccName" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.repayAccName}" disabled="disabled" /></td>
</tr>
<tr>
<td>还款开户银行:</td>
<td><input id="repayAccBank" name="repayAccBank" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.repayAccBank}" disabled="disabled" /></td>
</tr>
<tr>
<td>还款银行账号:</td>
<td><input id="repayAccount" name="repayAccount" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.repayAccount}" disabled="disabled" /></td>
</tr>
<tr>
<td>注册时间:</td>
<td><input id="regDate" name="regDate" type="text" editable="false" class="textbox easyui-datebox"value="${bean.regDateStr}" disabled="disabled" /></td>
</tr>
<tr>
<td>注册资本:</td>
<td><input id="regAmount" name="regAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox"value="${bean.regAmount}" disabled="disabled" /></td>
</tr>
<tr>
<td>实缴资本:</td>
<td><input id="acctualAmount" name="acctualAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox"value="${bean.acctualAmount}" disabled="disabled" /></td>
</tr>
<tr>
<td>组织机构代码证:</td>
<td><input id="orgCodeNo" name="orgCodeNo" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.orgCodeNo}" disabled="disabled" /></td>
</tr>
<tr>
<td>组织机构代码证有效期:</td>
<td><input id="orgCodeExpiryDate" name="orgCodeExpiryDate" type="text" editable="false" class="textbox easyui-datebox"value="${bean.orgCodeExpiryDateStr}" disabled="disabled" /></td>
</tr>
<tr>
<td>税务登记证:</td>
<td><input id="taxRegNo" name="taxRegNo" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.taxRegNo}" disabled="disabled" /></td>
</tr>
<tr>
<td>基本账户开户银行:</td>
<td><input id="baiscAccountBank" name="baiscAccountBank" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.baiscAccountBank}" disabled="disabled" /></td>
</tr>
<tr>
<td>基本账户账号:</td>
<td><input id="baiscAccount" name="baiscAccount" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.baiscAccount}" disabled="disabled" /></td>
</tr>
<tr>
<td>经营范围:</td>
<td><input id="bizzScope" name="bizzScope" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.bizzScope}" disabled="disabled" /></td>
</tr>
<tr>
<td>经营实体属性:</td>
<td><input id="companyType" name="companyType" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox"value="${bean.companyType}" disabled="disabled" /></td>
</tr>
<tr>
<td>主要销售产品:</td>
<td><input id="mainProduct" name="mainProduct" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.mainProduct}" disabled="disabled" /></td>
</tr>
<tr>
<td>上一年度营业额:</td>
<td><input id="lastYearTurnover" name="lastYearTurnover" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox"value="${bean.lastYearTurnover}" disabled="disabled" /></td>
</tr>
<tr>
<td>近三个月营业额:</td>
<td><input id="last3mTurnover" name="last3mTurnover" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox"value="${bean.last3mTurnover}" disabled="disabled" /></td>
</tr>
<tr>
<td>以往主要合作银行或公司:</td>
<td><input id="cooperateBankCompany" name="cooperateBankCompany" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.cooperateBankCompany}" disabled="disabled" /></td>
</tr>
<tr>
<td>日常结算银行:</td>
<td><input id="dailyClearBank" name="dailyClearBank" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.dailyClearBank}" disabled="disabled" /></td>
</tr>
<tr>
<td>贷款融资余额:</td>
<td><input id="loanBalance" name="loanBalance" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox"value="${bean.loanBalance}" disabled="disabled" /></td>
</tr>
<tr>
<td>融资公司名称:</td>
<td><input id="financeCompany" name="financeCompany" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.financeCompany}" disabled="disabled" /></td>
</tr>
<tr>
<td>融资银行名称:</td>
<td><input id="financeBank" name="financeBank" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.financeBank}" disabled="disabled" /></td>
</tr>
<tr>
<td>近三个月电费单1月:</td>
<td><input id="nearly3mEleBill1" name="nearly3mEleBill1" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox"value="${bean.nearly3mEleBill1}" disabled="disabled" /></td>
</tr>
<tr>
<td>近三个月电费单2月:</td>
<td><input id="nearly3mEleBill2" name="nearly3mEleBill2" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox"value="${bean.nearly3mEleBill2}" disabled="disabled" /></td>
</tr>
<tr>
<td>近三个月电费单3月:</td>
<td><input id="nearly3mEleBill3" name="nearly3mEleBill3" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox"value="${bean.nearly3mEleBill3}" disabled="disabled" /></td>
</tr>
<tr>
<td>撮合类型:</td>
<td><input id="matchType" name="matchType" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox"value="${bean.matchType}" disabled="disabled" /></td>
</tr>
<tr>
<td>操作员:</td>
<td><input id="operator" name="operator" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.operator}" disabled="disabled" /></td>
</tr>
<tr>
<td>所属机构:</td>
<td><input id="orgId" name="orgId" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.orgId}" disabled="disabled" /></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>创建日期:</td>
<td><input id="createTime" name="createTime" type="text" data-options="required:true" editable="false" class="textbox easyui-datetimebox"value="${bean.createTimeStr}" disabled="disabled" /></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>修改日期:</td>
<td><input id="updateTime" name="updateTime" type="text" data-options="required:true" editable="false" class="textbox easyui-datetimebox"value="${bean.updateTimeStr}" disabled="disabled" /></td>
</tr>
<tr>
<td>状态:</td>
<td><input id="state" name="state" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox"value="${bean.state}" disabled="disabled" /></td>
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


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
<title>财富客户表查看</title>
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
<td><input id="id" name="id" type="text" class="textbox"value="${bean.id}"  disabled="disabled"/></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>客户编号:</td>
<td><input id="customerNo" name="customerNo" type="text" class="textbox"value="${bean.customerNo}"  disabled="disabled"/></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>姓名/机构全称:</td>
<td><input id="name" name="name" type="text" class="textbox"value="${bean.name}"  disabled="disabled"/></td>
</tr>
<tr>
<td>简称:</td>
<td><input id="shortName" name="shortName" type="text" class="textbox"value="${bean.shortName}"  disabled="disabled"/></td>
</tr>
<tr>
<td>客户类型:</td>
<td><input id="customerType" name="customerType" type="text" class="textbox"value="${bean.customerType}"  disabled="disabled"/></td>
</tr>
<tr>
<td>英文名称:</td>
<td><input id="engName" name="engName" type="text" class="textbox"value="${bean.engName}"  disabled="disabled"/></td>
</tr>
<tr>
<td>性别:</td>
<td><input id="gender" name="gender" type="text" class="textbox"value="${bean.gender}"  disabled="disabled"/></td>
</tr>
<tr>
<td>生日:</td>
<td><input id="birthday" name="birthday" type="text" class="textbox"value="${bean.birthday}"  disabled="disabled"/></td>
</tr>
<tr>
<td>国籍:</td>
<td><input id="nationality" name="nationality" type="text" class="textbox"value="${bean.nationality}"  disabled="disabled"/></td>
</tr>
<tr>
<td>语言:</td>
<td><input id="language" name="language" type="text" class="textbox"value="${bean.language}"  disabled="disabled"/></td>
</tr>
<tr>
<td>母亲姓氏:</td>
<td><input id="motherFiratName" name="motherFiratName" type="text" class="textbox"value="${bean.motherFiratName}"  disabled="disabled"/></td>
</tr>
<tr>
<td>婚姻:</td>
<td><input id="marriage" name="marriage" type="text" class="textbox"value="${bean.marriage}"  disabled="disabled"/></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>证件类型:</td>
<td><input id="idType" name="idType" type="text" class="textbox"value="${bean.idType}"  disabled="disabled"/></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>证件号码:</td>
<td><input id="idNo" name="idNo" type="text" class="textbox"value="${bean.idNo}"  disabled="disabled"/></td>
</tr>
<tr>
<td>签发日期:</td>
<td><input id="idIssueDate" name="idIssueDate" type="text" class="textbox"value="${bean.idIssueDate}"  disabled="disabled"/></td>
</tr>
<tr>
<td>失效日期:</td>
<td><input id="idExpiryDate" name="idExpiryDate" type="text" class="textbox"value="${bean.idExpiryDate}"  disabled="disabled"/></td>
</tr>
<tr>
<td>签发机关:</td>
<td><input id="idIssueGov" name="idIssueGov" type="text" class="textbox"value="${bean.idIssueGov}"  disabled="disabled"/></td>
</tr>
<tr>
<td>学历:</td>
<td><input id="education" name="education" type="text" class="textbox"value="${bean.education}"  disabled="disabled"/></td>
</tr>
<tr>
<td>职业:</td>
<td><input id="job" name="job" type="text" class="textbox"value="${bean.job}"  disabled="disabled"/></td>
</tr>
<tr>
<td>行业代码1:</td>
<td><input id="industry1" name="industry1" type="text" class="textbox"value="${bean.industry1}"  disabled="disabled"/></td>
</tr>
<tr>
<td>行业代码2:</td>
<td><input id="industry2" name="industry2" type="text" class="textbox"value="${bean.industry2}"  disabled="disabled"/></td>
</tr>
<tr>
<td>行业代码3:</td>
<td><input id="industry3" name="industry3" type="text" class="textbox"value="${bean.industry3}"  disabled="disabled"/></td>
</tr>
<tr>
<td>单位名称:</td>
<td><input id="companyName" name="companyName" type="text" class="textbox"value="${bean.companyName}"  disabled="disabled"/></td>
</tr>
<tr>
<td>工作年限:</td>
<td><input id="workYears" name="workYears" type="text" class="textbox"value="${bean.workYears}"  disabled="disabled"/></td>
</tr>
<tr>
<td>单位规模:</td>
<td><input id="companyScale" name="companyScale" type="text" class="textbox"value="${bean.companyScale}"  disabled="disabled"/></td>
</tr>
<tr>
<td>职务:</td>
<td><input id="jobDuty" name="jobDuty" type="text" class="textbox"value="${bean.jobDuty}"  disabled="disabled"/></td>
</tr>
<tr>
<td>固定电话:</td>
<td><input id="phone" name="phone" type="text" class="textbox"value="${bean.phone}"  disabled="disabled"/></td>
</tr>
<tr>
<td>移动电话:</td>
<td><input id="mobile" name="mobile" type="text" class="textbox"value="${bean.mobile}"  disabled="disabled"/></td>
</tr>
<tr>
<td>EMAIL:</td>
<td><input id="email" name="email" type="text" class="textbox"value="${bean.email}"  disabled="disabled"/></td>
</tr>
<tr>
<td>通讯地址-省:</td>
<td><input id="addProvince" name="addProvince" type="text" class="textbox"value="${bean.addProvince}"  disabled="disabled"/></td>
</tr>
<tr>
<td>通讯地址-市:</td>
<td><input id="addCity" name="addCity" type="text" class="textbox"value="${bean.addCity}"  disabled="disabled"/></td>
</tr>
<tr>
<td>通讯地址-区县:</td>
<td><input id="addCounty" name="addCounty" type="text" class="textbox"value="${bean.addCounty}"  disabled="disabled"/></td>
</tr>
<tr>
<td>通讯地址:</td>
<td><input id="address" name="address" type="text" class="textbox"value="${bean.address}"  disabled="disabled"/></td>
</tr>
<tr>
<td>邮编:</td>
<td><input id="postcode" name="postcode" type="text" class="textbox"value="${bean.postcode}"  disabled="disabled"/></td>
</tr>
<tr>
<td>家庭情况:</td>
<td><input id="family" name="family" type="text" class="textbox"value="${bean.family}"  disabled="disabled"/></td>
</tr>
<tr>
<td>家庭收入:</td>
<td><input id="familyIncome" name="familyIncome" type="text" class="textbox"value="${bean.familyIncome}"  disabled="disabled"/></td>
</tr>
<tr>
<td>文件接收方式:</td>
<td><input id="fileReceive" name="fileReceive" type="text" class="textbox"value="${bean.fileReceive}"  disabled="disabled"/></td>
</tr>
<tr>
<td>资源需求:</td>
<td><input id="requirements" name="requirements" type="text" class="textbox"value="${bean.requirements}"  disabled="disabled"/></td>
</tr>
<tr>
<td>经营范围:</td>
<td><input id="bizzScope" name="bizzScope" type="text" class="textbox"value="${bean.bizzScope}"  disabled="disabled"/></td>
</tr>
<tr>
<td>注册地址-省:</td>
<td><input id="regProvince" name="regProvince" type="text" class="textbox"value="${bean.regProvince}"  disabled="disabled"/></td>
</tr>
<tr>
<td>注册地址-市:</td>
<td><input id="regCity" name="regCity" type="text" class="textbox"value="${bean.regCity}"  disabled="disabled"/></td>
</tr>
<tr>
<td>注册地址-区县:</td>
<td><input id="regCounty" name="regCounty" type="text" class="textbox"value="${bean.regCounty}"  disabled="disabled"/></td>
</tr>
<tr>
<td>注册地址:</td>
<td><input id="regAddress" name="regAddress" type="text" class="textbox"value="${bean.regAddress}"  disabled="disabled"/></td>
</tr>
<tr>
<td>企业性质:</td>
<td><input id="companyType" name="companyType" type="text" class="textbox"value="${bean.companyType}"  disabled="disabled"/></td>
</tr>
<tr>
<td>信托资产:</td>
<td><input id="trustAssets" name="trustAssets" type="text" class="textbox"value="${bean.trustAssets}"  disabled="disabled"/></td>
</tr>
<tr>
<td>信托委托人:</td>
<td><input id="trustSettlor" name="trustSettlor" type="text" class="textbox"value="${bean.trustSettlor}"  disabled="disabled"/></td>
</tr>
<tr>
<td>信托委托人电话:</td>
<td><input id="trustSettlorPhone" name="trustSettlorPhone" type="text" class="textbox"value="${bean.trustSettlorPhone}"  disabled="disabled"/></td>
</tr>
<tr>
<td>信托受益人:</td>
<td><input id="trustBenefit" name="trustBenefit" type="text" class="textbox"value="${bean.trustBenefit}"  disabled="disabled"/></td>
</tr>
<tr>
<td>信托受益人电话:</td>
<td><input id="trustBenefitPhone" name="trustBenefitPhone" type="text" class="textbox"value="${bean.trustBenefitPhone}"  disabled="disabled"/></td>
</tr>
<tr>
<td>客户经理:</td>
<td><input id="customerManager" name="customerManager" type="text" class="textbox"value="${bean.customerManager}"  disabled="disabled"/></td>
</tr>
<tr>
<td>操作员:</td>
<td><input id="operator" name="operator" type="text" class="textbox"value="${bean.operator}"  disabled="disabled"/></td>
</tr>
<tr>
<td>所属机构:</td>
<td><input id="orgId" name="orgId" type="text" class="textbox"value="${bean.orgId}"  disabled="disabled"/></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>创建日期:</td>
<td><input id="createTime" name="createTime" type="text" class="textbox"value="${bean.createTimeStr}"  disabled="disabled"/></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>修改日期:</td>
<td><input id="updateTime" name="updateTime" type="text" class="textbox"value="${bean.updateTimeStr}"  disabled="disabled"/></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>提交日期:</td>
<td><input id="appTime" name="appTime" type="text" class="textbox"value="${bean.appTime}"  disabled="disabled"/></td>
</tr>
<tr>
<td>状态:</td>
<td><input id="state" name="state" type="text" class="textbox"value="${bean.state}"  disabled="disabled"/></td>
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


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
<title>房贷申请信息</title>
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
		<p class="title"><a href="javascript:void(0);">个人房贷申请信息</a></p>
		<div class="content">
				<table id="appInfo">
					<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>借款信息</strong></div><hr/>
					<tr>
						<td>客户来源:</td>
						<td><input id="customerSource" name="houseApp.customerSource" type="text" 
						class="easyui-combobox" value="${bean.customerSource}" /></td>
						<td>营销人员:</td>
						<td><input id="staffNo" name="houseApp.staffNo" type="text" 
						class="easyui-combobox" value="${bean.staffNo}" /></td>
						<td>申请编码:</td>
						<td><input id="appCode" name="houseApp.appCode" type="text" 
						class="textbox easyui-validatebox" value="${bean.appCode}"/></td>
					</tr>
					<tr>
						<td align="right">渠道:</td>
						<td><input id="belongChannel" name="houseApp.belongChannel" type="text" 
						class="easyui-combobox" editable="false" value="${bean.belongChannel}"/></td>
						<td align="right">产品:</td>
						<td><input id="product" name="houseApp.product" type="text" 
						class="easyui-combobox" value="${bean.product}" /></td>
					</tr>
					<tr>
						<td>借款金额:</td>
						<td><input id="amount" name="houseApp.amount" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
						class="textbox easyui-numberbox" value="${bean.amount/10000}" style="width:128px;"/>万元</td>
						<td>借款期限:</td>
						<td><input id="period" name="houseApp.period" type="text" 
						class="textbox easyui-numberbox" value="${bean.period}" style="width:128px;"/>个月</td>
						<td>借款用途:</td>
						<td colspan="3"><input id="useage1" name="houseApp.useage1" type="text" 
						class="easyui-combobox" value="${bean.useage1}" />
						<input id="useage2" name="houseApp.useage2" type="text" 
						class="easyui-combobox" value="${bean.useage2}" /></td>
					</tr>
				</table>
				<table id="basicInfo">
					<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>申请人基本信息</strong></div><hr/>
					<tr>
						<td><SPAN style="color:red">*</SPAN>姓名:</td>
						<td><input id="name" name="houseApp.name" type="text" 
							class="textbox easyui-validatebox" value="${bean.name}"/></td>
						<td>身份证号:</td>
						<td><input id="idNo" name="houseApp.idNo" onChange="getCustomer();" type="text" 
						class="textbox easyui-validatebox" value="${bean.idNo}"/></td>
						<td><SPAN style="color:red">*</SPAN>手机号:</td>
						<td><input id="mobile" name="houseApp.mobile" type="text" 
						class="textbox easyui-validatebox" value="${bean.mobile}"/></td>
						<td>学历:</td>
						<td><input id="diploma" name="houseApp.diploma" type="text" 
						class="easyui-combobox" value="${bean.diploma}" /></td>
					</tr>	
					<tr>	
						<td>微信:</td>
						<td><input id="wechat" name="houseApp.wechat" type="text" 
						class="textbox easyui-validatebox" value="${bean.wechat}"/></td>
						<td>邮箱:</td>
						<td><input id="email" name="houseApp.email" type="text" 
						class="textbox easyui-validatebox" value="${bean.email}"/></td>
						<td>子女信息:</td>
						<td>
							<input id="withoutChildren" name="houseApp.withoutChildren" 
							type="radio" value="0" <c:if test="${bean.withoutChildren==0}">checked="checked"</c:if> />无
							<input id="withoutChildren" name="houseApp.withoutChildren" 
							type="radio" value="1" <c:if test="${bean.withoutChildren==1}">checked="checked"</c:if>/>有
							<input id="childrenSize" name="houseApp.childrenSize" type="text" 
							class="textbox easyui-numberbox" value="${bean.childrenSize}" 
							style="width:52px;"/>个
						</td>
						<td><SPAN style="color:red">*</SPAN>婚姻状况:</td>
						<td><input id="marriage" name="houseApp.marriage" type="text" 
						class="easyui-combobox" value="${bean.marriage}" /></td>
					</tr>
					<tr>
						<td>户籍:</td>
						<td colspan="3"><input id="kosekiProvince" name="houseApp.kosekiProvince" type="text" 
						class="easyui-combobox" value="${bean.kosekiProvince}" style="width:140px;" />省
						<input id="kosekiCity" name="houseApp.kosekiCity" type="text" 
						class="easyui-combobox" value="${bean.kosekiCity}" style="width:140px;" />市</td>
						<td>居住性质:</td>
						<td><input id="addNature" name="houseApp.addNature" type="text" 
						class="easyui-combobox" value="${bean.addNature}" /></td>
						<td>居住时间:</td>
						<td><input id="addYear" name="houseApp.addYear" type="text" 
						class="textbox easyui-numberbox" value="${bean.addYear}" style="width:140px;"/>年</td>	
					</tr>
					<tr>
						<td>居住地址:</td>
						<td colspan="5"><input id="addProvince" name="houseApp.addProvince" type="text" 
						class="easyui-combobox" value="${bean.addProvince}" style="width:140px;"/>省
						<input id="addCity" name="houseApp.addCity" type="text" 
						class="easyui-combobox" value="${bean.addCity}" style="width:140px;"/>市
						<input id="addCounty" name="houseApp.addCounty" type="text" 
						class="easyui-combobox" value="${bean.addCounty}" style="width:128px;"/>区县
						<input id="address" name="houseApp.address" type="text" 
						class="textbox easyui-validatebox" value="${bean.address}" /></td>
					</tr>
					<tr>
						<td>住宅电话:</td>
						<td><input id="phone" name="houseApp.phone" type="text" 
						class="textbox easyui-validatebox" value="${bean.phone}"/></td>
						<td>单位名称:</td>
						<td><input id="comName" name="houseApp.comName" type="text" 
						class="textbox easyui-validatebox" value="${bean.comName}"/></td>
						<td>单位类型:</td>
						<td><input id="comType" name="houseApp.comType" type="text" 
						class=easyui-combobox value="${bean.comType}" /></td>
						<td>单位电话:</td>
						<td><input id="comPhone" name="houseApp.comPhone" type="text" 
						class="textbox easyui-validatebox" value="${bean.comPhone}"/></td>
					</tr>
					<tr>
						<td>单位地址:</td>
						<td colspan="5"><input id="comProvince" name="houseApp.comProvince" type="text" 
						class="easyui-combobox" value="${bean.comProvince}" style="width:140px;"/>省
						<input id="comCity" name="houseApp.comCity" type="text" 
						class="easyui-combobox" value="${bean.comCity}" style="width:140px;"/>市
						<input id="comCounty" name="houseApp.comCounty" type="text" 
						class="easyui-combobox" value="${bean.comCounty}" style="width:130px;"/>区县
						<input id="comAddress" name="houseApp.comAddress" type="text" 
						class="textbox easyui-validatebox" value="${bean.comAddress}" /></td>
					</tr>
					<tr>
						<td>单位担任职务:</td>
						<td><input id="comDuty" name="houseApp.comDuty" type="text" 
						class="textbox easyui-validatebox" value="${bean.comDuty}"/></td>
						<td>入职时间:</td>
						<td><input id="comAddDate" name="houseApp.comAddDate" type="text" 
						class="textbox easyui-datebox" value="${bean.comAddDateStr}"/></td>
						<td>年收入:</td>
						<td><input id="income" name="houseApp.income" type="text" data-options="required:true,min:0,precision:2"
						class="textbox easyui-numberbox" value="${bean.income/10000}" style="width:128px;"/>万元</td>
						<td>月还款额:</td>
						<td><input id="monthlyPayments" name="houseApp.monthlyPayments" type="text" 
						class="textbox easyui-numberbox" value="${bean.monthlyPayments}" style="width:130px;"/>元</td>
					</tr>
					<tr>
						<td>开户行名称:</td>
						<td><input id="accName" name="houseApp.accName" type="text" 
						class="textbox easyui-validatebox" value="${bean.accName}"/></td>
						<td>开户行帐号:</td>
						<td><input id="account" name="houseApp.account" type="text" 
						class="textbox easyui-validatebox" value="${bean.account}"/></td>
					</tr>
				</table>
			<div id = "marriageDiv" <c:if test="${bean.marriage!='02'}">style="display: none;"</c:if>>
				<table id="spouse">	
					<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>申请人配偶信息</strong></div><hr/>
					<tr>
						<td><SPAN style="color:red">*</SPAN>姓名:</td>
						<td><input id="spouseName" name="houseApp.spouseName" type="text" 
						class="textbox easyui-validatebox" value="${bean.spouseName}"/></td>
						<td>身份证号:</td>
						<td><input id="spouseIdNo" name="houseApp.spouseIdNo" type="text"
						class="textbox easyui-validatebox" value="${bean.spouseIdNo}"/></td>
						<td><SPAN style="color:red">*</SPAN>手机:</td>
						<td><input id="spouseMobile" name="houseApp.spouseMobile" type="text" 
						class="textbox easyui-validatebox" value="${bean.spouseMobile}"/></td>
					</tr>
					<tr>
						<td>单位名称:</td>
						<td><input id="spouseComName" name="houseApp.spouseComName" type="text" 
						class="textbox easyui-validatebox" value="${bean.spouseComName}"/></td>
						<td>年收入:</td>
						<td><input id="spouseIncome" name="houseApp.spouseIncome" type="text" data-options="required:true,min:0,precision:2"
						class="textbox easyui-numberbox" value="${bean.spouseIncome/10000}" style="width:128px;"/>万元</td>
						<td>职业:</td>
						<td><input id="spouseJob" name="houseApp.spouseJob" type="text" 
						class="textbox easyui-validatebox" value="${bean.spouseJob}"/></td>
					</tr>
					<tr>
						<td>单位地址:</td>
						<td colspan="5"><input id="spouseComProvince" name="houseApp.spouseComProvince" type="text" 
						class="easyui-combobox" value="${bean.spouseComProvince}" style="width:140px;" />省
						<input id="spouseComCity" name="houseApp.spouseComCity" type="text" 
						class="easyui-combobox" value="${bean.spouseComCity}" style="width:140px;" />市
						<input id="spouseComCounty" name="houseApp.spouseComCounty" type="text" 
						class="easyui-combobox" value="${bean.spouseComCounty}" style="width:128px;" />区县
						<input id="spouseComAddress" name="houseApp.spouseComAddress" type="text" 
						class="textbox easyui-validatebox" value="${bean.spouseComAddress}" /></td>			
					</tr>
				</table>
			</div>
				<table class="datatable" id="commonContacts" style="width: auto;" >
					<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;">
					<strong>常用联系人（至少有1名直系亲属）</strong></div><hr/>
					<tr>
						<th scope="col">姓名</th>
						<th scope="col">关系</th>
						<th scope="col">年龄</th>
						<th scope="col">工作单位</th>
						<th scope="col">联系方式</th>
					</tr>
					<tr id="commonContact_0">
						<td>
							<input id="name" name="commonContacts[0].name" 
							type="text" 
							class="textbox easyui-validatebox" 
							value="${commonContacts[0].name}"/>
						</td>
						<td>
<%--							<input id="relation" name="commonContacts[0].relation" --%>
<%--							type="text" --%>
<%--							class="textbox easyui-validatebox" value="${commonContacts[0].relation}" --%>
<%--							 />--%>
							<input id="relation" name="commonContacts[0].relation" type="text" 
							class="easyui-combobox" value="${commonContacts[0].relation}" /> 
						</td>
						<td>
							<input id="age" name="commonContacts[0].age" 
							type="text" 
							class="textbox easyui-numberbox" value="${commonContacts[0].age}"/>
						</td>
						<td>
							<input id="comName" name="commonContacts[0].comName" 
							type="text" 
							class="textbox easyui-validatebox" 
							value="${commonContacts[0].comName}"/>
						</td>
						<td>
							<input id="mobile" name="commonContacts[0].mobile" 
							type="text" 
							class="textbox easyui-validatebox" 
							value="${commonContacts[0].mobile}"/>
						</td>
					</tr>
					<tr id="commonContact_1">
						<td>
							<input id="name" name="commonContacts[1].name" 
							type="text" 
							class="textbox easyui-validatebox" 
							value="${commonContacts[1].name}"/>
						</td>
						<td>
<%--							<input id="relation" name="commonContacts[1].relation" --%>
<%--							type="text" --%>
<%--							class="textbox easyui-validatebox" value="${commonContacts[1].relation}" --%>
<%--							 />--%>
							<input id="relation" name="commonContacts[1].relation" type="text" 
							class="easyui-combobox" value="${commonContacts[1].relation}" /> 
						</td>
						<td>
							<input id="age" name="commonContacts[1].age" 
							type="text" 
							class="textbox easyui-numberbox" value="${commonContacts[1].age}"/>
						</td>
						<td>
							<input id="comName" name="commonContacts[1].comName" 
							type="text" 
							class="textbox easyui-validatebox" 
							value="${commonContacts[1].comName}"/>
						</td>
						<td>
							<input id="mobile" name="commonContacts[1].mobile" 
							type="text" 
							class="textbox easyui-validatebox" 
							value="${commonContacts[1].mobile}"/>
						</td>
					</tr>
					<tr id="commonContact_2">
						<td>
							<input id="name" name="commonContacts[2].name" 
							type="text" 
							class="textbox easyui-validatebox" 
							value="${commonContacts[2].name}"/>
						</td>
						<td>
							<input id="relation" name="commonContacts[2].relation" type="text" 
							class="easyui-combobox" value="${commonContacts[2].relation}" /> 
						</td>
						<td>
							<input id="age" name="commonContacts[2].age" 
							type="text" 
							class="textbox easyui-numberbox" value="${commonContacts[2].age}"/>
						</td>
						<td>
							<input id="comName" name="commonContacts[2].comName" 
							type="text" 
							class="textbox easyui-validatebox" 
							value="${commonContacts[2].comName}"/>
						</td>
						<td>
							<input id="mobile" name="commonContacts[2].mobile" 
							type="text" 
							class="textbox easyui-validatebox" 
							value="${commonContacts[2].mobile}"/>
						</td>
					</tr>
			<c:forEach items="${commonContacts}" var="commonContact" varStatus="status">
				<c:if test="${status.index>2}">
					<tr id="commonContact_${status.index}">
						<td>
							<input id="name" name="commonContacts[${status.index}].name" 
							type="text" 
							class="textbox easyui-validatebox" value="${commonContact.name}"/>
						</td>
						<td>
							<input id="relation" name="commonContacts[${status.index}].relation" type="text" 
							class="easyui-combobox" value="${commonContact.relation}" /> 
						</td>
						<td>
							<input id="age" name="commonContacts[${status.index}].age" 
							type="text" 
							class="textbox easyui-numberbox" value="${commonContact.age}"/>
						</td>
						<td>
							<input id="comName" name="commonContacts[${status.index}].comName" 
							type="text" 
							class="textbox easyui-validatebox" 
							value="${commonContact.comName}"/>
						</td>
						<td>
							<input id="mobile" name="commonContacts[${status.index}].mobile" 
							type="text" 
							class="textbox easyui-validatebox" 
							value="${commonContact.mobile}"/>
						</td>
					</tr>
				</c:if>
			</c:forEach>
</table>
<div id="mainBusinessInfoDiv" <c:if test="${fn:contains(bean.product,'业主贷')==false}">style="display: none;"</c:if>>
	<table>	
		<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>主营业务信息（业主贷必填）</strong></div><hr/>
		<tr>
			<td>商号名称:</td>
			<td><input id="firmName" name="houseApp.firmName" type="text" 
			class="textbox easyui-validatebox" value="${bean.firmName}"/></td>
			<td>主营业务:</td>
			<td><input id="firmMainBusiness" name="houseApp.firmMainBusiness" type="text" 
			class="textbox easyui-validatebox" value="${bean.firmMainBusiness}"/></td>
			<td>年营业额:</td>
			<td><input id="firmIncome" name="houseApp.firmIncome" type="text" 
			class="textbox easyui-numberbox" value="${bean.firmIncome/10000}" style="width:128px;"/>万元</td>
			<td>占股比例:</td>
			<td><input id="firmShare" name="houseApp.firmShare" type="text" 
			class="textbox easyui-numberbox" value="${bean.firmShare}" style="width:140px;"/>%</td>				
		</tr>
		<tr>
			<td>商号地址:</td>
			<td colspan="5" ><input id="firmProvince" name="houseApp.firmProvince" type="text" 
			class="easyui-combobox" value="${bean.firmProvince}" style="width:140px;" />省
			<input id="firmCity" name="houseApp.firmCity" type="text" 
			class="easyui-combobox" value="${bean.firmCity}" style="width:140px;" />市
			<input id="firmCounty" name="houseApp.firmCounty" type="text" 
			class="easyui-combobox" value="${bean.firmCounty}" style="width:128px;" />区县
			<input id="firmAddress" name="houseApp.firmAddress" type="text" 
			class="textbox easyui-validatebox" value="${bean.firmAddress}" /></td>
		</tr>
		<tr>
			<td>本地经营时间:</td>
			<td><input id="firmManageDate" name="houseApp.firmManageDate" type="text" 
			class="textbox easyui-datebox" value="${bean.firmManageDateStr}"/></td>					
		</tr>
	</table>
</div>
				
<div id="dealingsContactDiv" <c:if test="${fn:contains(bean.product,'业主贷')==false}">style="display: none;"</c:if>>				
<table class="datatable" id="dealingsContacts" style="width: auto;" >
	<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;">
	<strong>经营往来联系人（业主贷必填）</strong></div><hr/>
	<tr>
		<th scope="col">姓名</th>
		<th scope="col">联系方式</th>
		<th scope="col">单位名称</th>
		<th scope="col">合作年限</th>
		<th scope="col">合作方式</th>
	</tr>
	<tr id="dealingsContact_0">
		<td>
			<input id="name" name="dealingsContacts[0].name" 
			type="text" 
			class="textbox easyui-validatebox" 
			value="${dealingsContacts[0].name}"/>
		</td>
		<td>
			<input id="phone" name="dealingsContacts[0].phone" 
			type="text" 
			class="textbox easyui-validatebox" 
			value="${dealingsContacts[0].phone}"/>
		</td>
		<td>
			<input id="comName" name="dealingsContacts[0].comName" 
			type="text" 
			class="textbox easyui-validatebox" 
			value="${dealingsContacts[0].comName}"/>
		</td>
		<td>
			<input id="cooperationYear" name="dealingsContacts[0].cooperationYear" 
			type="text" 
			class="textbox easyui-numberbox" value="${dealingsContacts[0].cooperationYear}"/>
		</td>
		<td>
			<input id="cooperationType" name="dealingsContacts[0].cooperationType" 
			type="text" 
			class="textbox easyui-validatebox" value="${dealingsContacts[0].cooperationType}" 
			 style="width:140px;" />
		</td>
	</tr>
	<tr id="dealingsContact_1">
		<td>
			<input id="name" name="dealingsContacts[1].name" 
			type="text" 
			class="textbox easyui-validatebox" 
			value="${dealingsContacts[1].name}"/>
		</td>
		<td>
			<input id="phone" name="dealingsContacts[1].phone" 
			type="text" 
			class="textbox easyui-validatebox" 
			value="${dealingsContacts[1].phone}"/>
		</td>
		<td>
			<input id="comName" name="dealingsContacts[1].comName" 
			type="text" 
			class="textbox easyui-validatebox" 
			value="${dealingsContacts[1].comName}"/>
		</td>
		<td>
			<input id="cooperationYear" name="dealingsContacts[1].cooperationYear" 
			type="text" 
			class="textbox easyui-numberbox" value="${dealingsContacts[1].cooperationYear}"/>
		</td>
		<td>
			<input id="cooperationType" name="dealingsContacts[1].cooperationType" 
			type="text" 
			class="textbox easyui-validatebox" value="${dealingsContacts[1].cooperationType}" 
			 style="width:140px;" />
		</td>
	</tr>
	<tr id="dealingsContact_2">
		<td>
			<input id="name" name="dealingsContacts[2].name" 
			type="text" 
			class="textbox easyui-validatebox" 
			value="${dealingsContacts[2].name}"/>
		</td>
		<td>
			<input id="phone" name="dealingsContacts[2].phone" 
			type="text" 
			class="textbox easyui-validatebox" 
			value="${dealingsContacts[2].phone}"/>
		</td>
		<td>
			<input id="comName" name="dealingsContacts[2].comName" 
			type="text" 
			class="textbox easyui-validatebox" 
			value="${dealingsContacts[2].comName}"/>
		</td>
		<td>
			<input id="cooperationYear" name="dealingsContacts[2].cooperationYear" 
			type="text" 
			class="textbox easyui-numberbox" value="${dealingsContacts[2].cooperationYear}"/>
		</td>
		<td>
			<input id="cooperationType" name="dealingsContacts[2].cooperationType" 
			type="text" 
			class="textbox easyui-validatebox" value="${dealingsContacts[2].cooperationType}" 
			 style="width:140px;" />
		</td>
	</tr>
	<c:forEach items="${dealingsContacts}" var="dealingsContact" varStatus="status">
	<c:if test="${status.index>2}">
		<tr id="dealingsContact_${status.index}">
			<td>
				<input id="name" name="dealingsContacts[${status.index}].name" 
				type="text" 
				class="textbox easyui-validatebox" 
				value="${dealingsContact.name}"/>
			</td>
			<td>
				<input id="phone" name="dealingsContacts[${status.index}].phone" 
				type="text" 
				class="textbox easyui-validatebox" 
				value="${dealingsContact.phone}"/>
			</td>
			<td>
				<input id="comName" name="dealingsContacts[${status.index}].comName" 
				type="text" 
				class="textbox easyui-validatebox" 
				value="${dealingsContact.comName}"/>
			</td>
			<td>
				<input id="cooperationYear" name="dealingsContacts[${status.index}].cooperationYear" 
				type="text" 
				class="textbox easyui-numberbox" value="${dealingsContact.cooperationYear}"/>
			</td>
			<td>
				<input id="cooperationType" name="dealingsContacts[${status.index}].cooperationType" 
				type="text" 
				class="textbox easyui-validatebox" value="${dealingsContact.cooperationType}" 
				 style="width:140px;" />
			</td>
		</tr>
	</c:if>
</c:forEach>			
</table>
</div>
				<table>	
					<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>重要情况说明</strong></div><hr/>
					<tr>
						<td>是否有未结清借款:</td>
						<td id="houseExt_1">
							<input type="radio" id="value" name="houseExts[0].value" value='0'
								<c:if test="${houseExts[0].value=='0'||houseExts[0].value==null}">checked='checked'</c:if> />否
							<input type="radio" id="entLoanMark" name="houseExts[0].value" value="1"
								<c:if test="${houseExts[0].value=='1'}">checked='checked'</c:if> />是
							<input id="remarks" name="houseExts[0].remarks" type="text" 
							class="textbox easyui-validatebox" value="${houseExts[0].remarks}"/>
						</td>
											
						<td>是否有资产已被抵押:</td>
						<td id="houseExt_2">
							<input type="radio" id="value" name="houseExts[1].value" value='0'
								<c:if test="${houseExts[1].value=='0'||houseExts[1].value==null}">checked='checked'</c:if> />否
							<input type="radio" id="entLoanMark" name="houseExts[1].value" value="1"
								<c:if test="${houseExts[1].value=='1'}">checked='checked'</c:if> />是
							<input id="remarks" name="houseExts[1].remarks" type="text" 
							class="textbox easyui-validatebox" value="${houseExts[1].remarks}"/>
						</td>
					</tr>
					<tr>
						<td>是否涉及诉讼:</td>
						<td id="houseExt_3">
							<input type="radio" id="value" name="houseExts[2].value" value='0'
								<c:if test="${houseExts[2].value=='0'||houseExts[2].value==null}">checked='checked'</c:if> />否
							<input type="radio" id="entLoanMark" name="houseExts[2].value" value="1"
								<c:if test="${houseExts[2].value=='1'}">checked='checked'</c:if> />是
							<input id="remarks" name="houseExts[2].remarks" type="text" 
							class="textbox easyui-validatebox" value="${houseExts[2].remarks}"/>
						</td>			
						<td>近期是否有对生意有较大影响的事件:</td>
						<td id="houseExt_4">
							<input type="radio" id="value" name="houseExts[3].value" value='0'
								<c:if test="${houseExts[3].value=='0'||houseExts[3].value==null}">checked='checked'</c:if> />否
							<input type="radio" id="entLoanMark" name="houseExts[3].value" value="1"
								<c:if test="${houseExts[3].value=='1'}">checked='checked'</c:if> />是
							<input id="remarks" name="houseExts[3].remarks" type="text" 
							class="textbox easyui-validatebox" value="${houseExts[3].remarks}"/>
						</td>
					</tr>
					<tr>
						<td>是否为其他人提供担保:</td>
						<td id="houseExt_5">
							<input type="radio" id="value" name="houseExts[4].value" value='0'
								<c:if test="${houseExts[4].value=='0'||houseExts[4].value==null}">checked='checked'</c:if> />否
							<input type="radio" id="entLoanMark" name="houseExts[4].value" value="1"
								<c:if test="${houseExts[4].value=='1'}">checked='checked'</c:if> />是
							<input id="remarks" name="houseExts[4].remarks" type="text" 
							class="textbox easyui-validatebox" value="${houseExts[4].remarks}"/>
						</td>			
						<td>本人或配偶是否有“曾用名”:</td>
						<td id="houseExt_6">
							<input type="radio" id="value" name="houseExts[5].value" value='0'
								<c:if test="${houseExts[5].value=='0'||houseExts[5].value==null}">checked='checked'</c:if> />否
							<input type="radio" id="entLoanMark" name="houseExts[5].value" value="1"
								<c:if test="${houseExts[5].value=='1'}">checked='checked'</c:if> />是
							<input id="remarks" name="houseExts[5].remarks" type="text" 
							class="textbox easyui-validatebox" value="${houseExts[5].remarks}"/>
						</td>
					</tr>
					<tr>
						<td>
							申请描述：
						</td>
						<td colspan="3">
							<textarea value="${bean.remark}" class="textbox easyui-validatebox"  data-options="validType:['length[0,500]']" 
								style="resize: none;width:100%;height:50px!important;background-color: #F0F0F0;" disabled="disabled">${bean.remark}</textarea>
						</td>
					</tr>
					<br/>
					<c:if test="${null != returnReasons && '' != returnReasons}">
						<tr>
							<td>
								审核退回原因:
							</td>
							<td colspan="3">
								<textarea value="${returnReasons}" class="textbox easyui-validatebox" 
									style="resize: none;width:625px;height:50px!important;background-color: #F0F0F0;" disabled="disabled">${returnReasons}</textarea>
							</td>
						</tr>
					</c:if>
					<c:if test="${null != sp_fkxsms && '' != sp_fkxsms && bean.state=='24'}">
						<tr>
							<td>
								反馈销售描述:
							</td>
							<td colspan="3">
								<textarea value="${sp_fkxsms}" class="textbox easyui-validatebox" 
									style="resize: none;width:625px;height:50px!important;background-color: #F0F0F0;" disabled="disabled">${sp_fkxsms}</textarea>
							</td>
						</tr>
					</c:if>
					<c:if test="${null != tssp_fkxsms && '' != tssp_fkxsms && bean.state=='24'}">
						<tr>
							<td>
								反馈销售描述:
							</td>
							<td colspan="3">
								<textarea value="${tssp_fkxsms}" class="textbox easyui-validatebox" 
									style="resize: none;width:625px;height:50px!important;background-color: #F0F0F0;" disabled="disabled">${tssp_fkxsms}</textarea>
							</td>
						</tr>
					</c:if>
				</table>
				<table width="100%">
					<tr>
						<td id="imgDiv">
						<jsp:include page="/img/imgSlidePath.do?appId=${bean.appId}" />
						</td>
					</tr>
				</table>
		</div>
	</div>
</div>
</body>
<script type="text/javascript" >
function zhezhao(){
		$("<div class=\"datagrid-mask\" id='chushiZhezhao'></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
		$("<div class=\"datagrid-mask-msg\" id='chushiZhezhaoMsg'></div>").html("正在加载，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
}
zhezhao();
function rmZhezhao(){
		$("#chushiZhezhao").remove();
		$("#chushiZhezhaoMsg").remove();
}

$(window).load(function (){
	tabsReadOnly($("body"));
	rmZhezhao();
});

//返回
function back(){
	window.history.go(-1);
}

function tabsReadOnly(redinfo){
	redinfo.find("input[type='radio']").attr('disabled',true);
// 	redinfo.find("input[type='button']").attr("disabled",true);
	redinfo.find('.easyui-validatebox').attr('disabled',true);
	redinfo.find('.easyui-combobox').combo('disable');
	redinfo.find('.easyui-numberbox').numberbox('disable');
	redinfo.find('.easyui-datebox').datebox('disable');
}
function initRelation(objId){
	var obj = $("#"+objId).find("#relation");
	obj.combobox("clear");
	obj.combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.relation
	});	
}
//页面加载完动作
$(document).ready(function (){
	//$.parser.parse($("#commonContactsDiv"));
	//初始化 营销人员
		var sales="sales/listjason.do?state=1&orgId=${login_org.orgId}";
		$('#staffNo').combobox({url:sales, valueField:'staffNo', textField:'name'});
	//以下都为填充数据代码
	initRelation("commonContact_0");
	initRelation("commonContact_1");
	initRelation("commonContact_2");
	<c:forEach items="${commonContacts}" var="commonContact" varStatus="status">
		<c:if test="${status.index>2}">
			initRelation("commonContact_${status.index}");
		</c:if>
	</c:forEach>
	
	//填充select数据 婚姻状况
	$("#marriage").combobox("clear");
	$('#marriage').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.marriage
	});
	
	//填充select数据 客户来源
	$("#customerSource").combobox("clear");
	$('#customerSource').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.customerSource
	});
	
	//填充select数据 客户来源
	$("#comType").combobox("clear");
	$('#comType').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.companytype
	});

	//填充select数据 居住性质
	$("#addNature").combobox("clear");
	$('#addNature').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.houseNature
	});

	//填充select数据 学历
	$("#diploma").combobox("clear");
	$('#diploma').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.education
	});
/*
	//填充select数据 借款期限
	$("#period").combobox("clear");
	$('#period').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:[{"keyProp":"1","keyValue":"12"},{"keyProp":"2","keyValue":"18"},{"keyProp":"3","keyValue":"24"},{"keyProp":"4","keyValue":"36"}]
	});
*/
	//填充select数据 借款用途1
 var useage1url = "sys/datadictionary/listjason.do?keyName=creditusage1";
		$("#useage1").combobox("clear");
		$('#useage1').combobox({
			url: useage1url,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
		//填充select数据 借款用途2
		var useage1 = $('#useage1').combobox('getValue');
		var useage2url = "sys/datadictionary/listjason.do?keyName=creditusage2&parentKeyProp=" + encodeURI(useage1);
		 $("#useage2").combobox("clear");
		$('#useage2').combobox({
			url: useage2url,
			valueField: 'keyProp',
			textField: 'keyValue'
		});

	//填充select数据 户籍省份
 var provinceurl = "sys/datadictionary/listjason.do?keyName=province";
		$("#kosekiProvince").combobox("clear");
		$('#kosekiProvince').combobox({
			url: provinceurl,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
 //填充select数据 户籍市
 var province = $('#kosekiProvince').combobox('getValue');
		var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(province);
		$("#kosekiCity").combobox("clear");
		$('#kosekiCity').combobox({
			url: cityurl,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
	
 //填充select数据 居住地省份
 var provinceurl = "sys/datadictionary/listjason.do?keyName=province";
		$("#addProvince").combobox("clear");
		$('#addProvince').combobox({
			url: provinceurl,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
 //填充select数据 居住地市
 var province = $('#addProvince').combobox('getValue');
		var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(province);
		$("#addCity").combobox("clear");
		$('#addCity').combobox({
			url: cityurl,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
 //填充select数据 居住地区县
 var city = $('#addCity').combobox('getValue');
		var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(city);
		$("#addCounty").combobox("clear");
		$('#addCounty').combobox({url: countyurl, valueField: 'keyProp',textField: 'keyValue'});
 
 //填充select数据 申请人单位地省份
 var provinceurl = "sys/datadictionary/listjason.do?keyName=province";
		$("#comProvince").combobox("clear");
		$('#comProvince').combobox({
			url: provinceurl,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
 //填充select数据 申请人单位地市
 var province = $('#comProvince').combobox('getValue');
		var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(province);
		$("#comCity").combobox("clear");
		$('#comCity').combobox({
			url: cityurl,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
 //填充select数据 申请人单位地区县
 var city = $('#comCity').combobox('getValue');
		var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(city);
		$("#comCounty").combobox("clear");
		$('#comCounty').combobox({url: countyurl, valueField: 'keyProp',textField: 'keyValue'});
	
	//填充select数据 配偶单位地省份
 var provinceurl = "sys/datadictionary/listjason.do?keyName=province";
		$("#spouseComProvince").combobox("clear");
		$('#spouseComProvince').combobox({
			url: provinceurl,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
 //填充select数据 配偶单位地市
 var province = $('#spouseComProvince').combobox('getValue');
		var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(province);
		$("#spouseComCity").combobox("clear");
		$('#spouseComCity').combobox({
			url: cityurl,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
 //填充select数据 配偶单位地区县
 var city = $('#spouseComCity').combobox('getValue');
		var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(city);
		$("#spouseComCounty").combobox("clear");
		$('#spouseComCounty').combobox({url: countyurl, valueField: 'keyProp',textField: 'keyValue'});
	//填充select数据 商号地址省份
 var provinceurl = "sys/datadictionary/listjason.do?keyName=province";
		$("#firmProvince").combobox("clear");
		$('#firmProvince').combobox({
			url: provinceurl,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
 //填充select数据 商号地址市
 var province = $('#firmProvince').combobox('getValue');
		var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(province);
		$("#firmCity").combobox("clear");
		$('#firmCity').combobox({
			url: cityurl,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
 //填充select数据 商号地址区县
 var city = $('#firmCity').combobox('getValue');
var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(city);
$("#firmCounty").combobox("clear");
$('#firmCounty').combobox({url: countyurl, valueField: 'keyProp',textField: 'keyValue'});


	//填充select数据 渠道
	var channelurl="channeltotal/listjason.do";
	$("#belongChannel").combobox("clear");
	$('#belongChannel').combobox({
		url:channelurl,
		valueField:'channelCode', 
		textField:'channelName',
		onChange: function(newValue, oldValue){             
			$('#product').combobox('clear');
			var producturl = "product/hedao/listjason.do?type=5&belongChannel=" + encodeURI(newValue);
			$('#product').combobox('reload',producturl); 
		}
	});
	
	//填充select数据 产品
	var belongChannel = $('#belongChannel').combobox('getValue');
	var producturl = "product/hedao/listjason.do?type=5&belongChannel=" + encodeURI(belongChannel);
	$("#product").combobox("clear");
	$('#product').combobox({
		url:producturl,
		valueField:'name', 
		textField:'name'
	});

		
});
</script>

</html>


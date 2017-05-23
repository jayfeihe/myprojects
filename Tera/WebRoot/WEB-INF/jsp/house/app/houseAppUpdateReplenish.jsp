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
<title>房贷申请表更新</title>
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
		<p class="title"><a href="javascript:void(0);">个人房贷申请</a></p>
		<div class="content">
			<form id="updateForm" name="houseApp.updateForm" action="" AUTOCOMPLETE="off">
				<input id="appId" name="houseApp.appId" type="hidden" value="${bean.appId}"/>
				<input id="customerId" name="houseApp.customerId" type="hidden" value="${bean.customerId}"/>
				<input id="id" name="houseApp.id" type="hidden" size="35" value="${bean.id}" />
				<table id="appInfo">
					<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>借款信息</strong></div><hr/>
					<tr>
						<td>客户来源:</td>
						<td><input id="customerSource" name="houseApp.customerSource" type="text" data-options="required:true,validType:['length[0,10]']" 
						class="easyui-combobox"  value="${bean.customerSource}" editable="false"/></td>
						<td>营销人员:</td>
						<td><input id="staffNo" name="houseApp.staffNo" type="text" data-options="required:true,validType:['length[0,50]']" 
						class="easyui-combobox"  value="${bean.staffNo}" editable="false"/></td>
						<td>申请编码:</td>
						<td><input id="appCode" name="houseApp.appCode" type="text" data-options="validType:['length[0,30]']" 
						class="textbox easyui-validatebox" value="${bean.appCode}"/></td>
						<td align="right">产品:</td>
						<td><input id="product" name="houseApp.product" type="text" data-options="required:true,validType:['length[0,50]']" 
						class="easyui-combobox"  value="${bean.product}" editable="false"/></td>
					</tr>
					<tr>
						<td>借款金额:</td>
						<td><input id="amount" name="houseApp.amount" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
						class="textbox easyui-numberbox" value="${bean.amount/10000}" style="width:128px;"/>万元</td>
<%--						<td>借款期限:</td>--%>
<%--						<td><input id="period" name="houseApp.period" type="text" editable="false"  data-options="required:true,min:0,precision:0" --%>
<%--						class="easyui-combobox"  value="${bean.period}" editable="false"/></td>--%>
						<td>借款用途:</td>
						<td colspan="3"><input id="useage1" name="houseApp.useage1" type="text" data-options="required:true,validType:['length[0,10]']" 
						class="easyui-combobox"  value="${bean.useage1}" editable="false"/>
						<input id="useage2" name="houseApp.useage2" type="text" data-options="required:true,validType:['length[0,10]']" 
						class="easyui-combobox"  value="${bean.useage2}" editable="false"/></td>
					</tr>
				</table>
				<table id="basicInfo">
					<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>申请人基本信息</strong></div><hr/>
					<tr>
						<td><SPAN style="color:red">*</SPAN>姓名:</td>
						<td><input id="name" name="houseApp.name" type="text" data-options="required:true,validType:['length[0,50]']" 
							class="textbox easyui-validatebox" value="${bean.name}"/></td>
						<td>身份证号:</td>
						<td>
<%--						<input id="idType" name="houseApp.idType" type="hidden" value="01" />--%>
						<input id="idNo" name="houseApp.idNo" onChange="getCustomer();" type="text" data-options="required:true,validType:['idcard']" 
						class="textbox easyui-validatebox" value="${bean.idNo}"/></td>
						<td><SPAN style="color:red">*</SPAN>手机号:</td>
						<td><input id="mobile" name="houseApp.mobile" type="text" data-options="required:true,validType:['mobile']" 
						class="textbox easyui-numberbox" value="${bean.mobile}"/></td>
						<td>学历:</td>
						<td><input id="diploma" name="houseApp.diploma" type="text" data-options="required:true,validType:['length[0,10]']" 
						class="easyui-combobox" value="${bean.diploma}" editable="false"/></td>
					</tr>	
					<tr>	
						<td>微信:</td>
						<td><input id="wechat" name="houseApp.wechat" type="text" data-options="validType:['length[0,50]']" 
						class="textbox easyui-validatebox" value="${bean.wechat}"/></td>
						<td>邮箱:</td>
						<td><input id="email" name="houseApp.email" type="text" data-options="validType:['email','length[0,50]']" 
						class="textbox easyui-validatebox" value="${bean.email}"/></td>
						<td>子女信息:</td>
						<td>
							<input id="withoutChildren" name="houseApp.withoutChildren" 
							type="radio" value="0" <c:if test="${bean.withoutChildren==0}">checked="checked"</c:if> />无
							<input id="withoutChildren" name="houseApp.withoutChildren" 
							type="radio" value="1" <c:if test="${bean.withoutChildren==1}">checked="checked"</c:if>/>有
							<input id="childrenSize" name="houseApp.childrenSize" type="text" editable="false"  data-options="min:0,precision:0" 
							class="textbox easyui-numberbox" value="${bean.childrenSize}" 
							style="width:52px;"/>个
						</td>
						<td><SPAN style="color:red">*</SPAN>婚姻状况:</td>
						<td><input id="marriage" name="houseApp.marriage" type="text" data-options="required:true,validType:['length[0,2]']" 
						class="easyui-combobox" value="${bean.marriage}" editable="false"/></td>
					</tr>
					<tr>
						<td>户籍:</td>
						<td colspan="3"><input id="kosekiProvince" name="houseApp.kosekiProvince" type="text" data-options="required:true,validType:['length[0,10]']" 
						class="easyui-combobox" value="${bean.kosekiProvince}" editable="false" style="width:140px;" />省
						<input id="kosekiCity" name="houseApp.kosekiCity" type="text" data-options="required:true,validType:['length[0,10]']" 
						class="easyui-combobox" value="${bean.kosekiCity}" editable="false" style="width:140px;" />市</td>
						<td>居住性质:</td>
						<td><input id="addNature" name="houseApp.addNature" type="text" data-options="required:true,validType:['length[0,10]']" 
						class="easyui-combobox" value="${bean.addNature}" editable="false"/></td>
						<td>居住时间:</td>
						<td><input id="addYear" name="houseApp.addYear" type="text" editable="false"  data-options="required:true,min:0,precision:0" 
						class="textbox easyui-numberbox" value="${bean.addYear}" style="width:140px;"/>年</td>	
					</tr>
					<tr>
						<td>居住地址:</td>
						<td colspan="5"><input id="addProvince" name="houseApp.addProvince" type="text" data-options="required:true,validType:['length[0,10]']" 
						class="easyui-combobox" value="${bean.addProvince}" editable="false" style="width:140px;"/>省
						<input id="addCity" name="houseApp.addCity" type="text" data-options="required:true,validType:['length[0,10]']" 
						class="easyui-combobox" value="${bean.addCity}" editable="false" style="width:140px;"/>市
						<input id="addCounty" name="houseApp.addCounty" type="text" data-options="required:true,validType:['length[0,10]']" 
						class="easyui-combobox" value="${bean.addCounty}" editable="false" style="width:128px;"/>区县
						<input id="address" name="houseApp.address" type="text" data-options="required:true,validType:['length[0,100]']" 
						class="textbox easyui-validatebox" value="${bean.address}" editable="false"/></td>
					</tr>
					<tr>
						<td>住宅电话:</td>
						<td><input id="phone" name="houseApp.phone" type="text" data-options="required:true,validType:['length[0,50]']" 
						class="textbox easyui-validatebox" value="${bean.phone}"/></td>
						<td>单位名称:</td>
						<td><input id="comName" name="houseApp.comName" type="text" data-options="required:true,validType:['length[0,50]']" 
						class="textbox easyui-validatebox" value="${bean.comName}"/></td>
						<td>单位类型:</td>
						<td><input id="comType" name="houseApp.comType" type="text" data-options="required:true,validType:['length[0,50]']" 
						class=easyui-combobox value="${bean.comType}" editable="false"/></td>
						<td>单位电话:</td>
						<td><input id="comPhone" name="houseApp.comPhone" type="text" data-options="required:true,validType:['length[0,50]']" 
						class="textbox easyui-validatebox" value="${bean.comPhone}"/></td>
					</tr>
					<tr>
						<td>单位地址:</td>
						<td colspan="5"><input id="comProvince" name="houseApp.comProvince" type="text" data-options="required:true,validType:['length[0,10]']" 
						class="easyui-combobox" value="${bean.comProvince}" editable="false" style="width:140px;"/>省
						<input id="comCity" name="houseApp.comCity" type="text" data-options="required:true,validType:['length[0,10]']" 
						class="easyui-combobox" value="${bean.comCity}" editable="false" style="width:140px;"/>市
						<input id="comCounty" name="houseApp.comCounty" type="text" data-options="required:true,validType:['length[0,10]']" 
						class="easyui-combobox" value="${bean.comCounty}" editable="false" style="width:130px;"/>区县
						<input id="comAddress" name="houseApp.comAddress" type="text" data-options="required:true,validType:['length[0,100]']" 
						class="textbox easyui-validatebox" value="${bean.comAddress}" editable="false"/></td>
					</tr>
					<tr>
						<td>单位担任职务:</td>
						<td><input id="comDuty" name="houseApp.comDuty" type="text" data-options="required:true,validType:['length[0,50]']" 
						class="textbox easyui-validatebox" value="${bean.comDuty}"/></td>
						<td>入职时间:</td>
						<td><input id="comAddDate" name="houseApp.comAddDate" type="text" editable="false" data-options="required:true" 
						class="textbox easyui-datebox" value="${bean.comAddDateStr}"/></td>
						<td>年收入:</td>
						<td><input id="income" name="houseApp.income" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
						class="textbox easyui-numberbox" value="${bean.income/10000}" style="width:128px;"/>万元</td>
						<td>月还款额:</td>
						<td><input id="monthlyPayments" name="houseApp.monthlyPayments" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
						class="textbox easyui-numberbox" value="${bean.monthlyPayments}" style="width:130px;"/>元</td>
					</tr>
					<tr>
						<td>开户行名称:</td>
						<td><input id="accName" name="houseApp.accName" type="text" data-options="required:true,validType:['length[0,100]']" 
						class="textbox easyui-validatebox" value="${bean.accName}"/></td>
						<td>开户行帐号:</td>
						<td><input id="account" name="houseApp.account" type="text" data-options="required:true,validType:['number','length[0,100]']"
						class="textbox easyui-validatebox" value="${bean.account}"/></td>
					</tr>
				</table>
			<div id = "marriageDiv" <c:if test="${bean.marriage!='02'}">style="display: none;"</c:if>>
				<table id="spouse">	
					<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>申请人配偶信息</strong></div><hr/>
					<tr>
						<td><SPAN style="color:red">*</SPAN>姓名:</td>
						<td><input id="spouseName" name="houseApp.spouseName" type="text" data-options="required:true,validType:['length[0,50]']" 
						class="textbox easyui-validatebox" value="${bean.spouseName}"/></td>
						<td>身份证号:</td>
						<td>
<%--						<input id="spouseIdType" name="houseApp.spouseIdType" type="hidden" value="01" />--%>
						<input id="spouseIdNo" name="houseApp.spouseIdNo" type="text" data-options="required:true,validType:['idcard']" 
						class="textbox easyui-validatebox" value="${bean.spouseIdNo}"/></td>
						<td><SPAN style="color:red">*</SPAN>手机:</td>
						<td><input id="spouseMobile" name="houseApp.spouseMobile" type="text" data-options="required:true,validType:['mobile']" 
						class="textbox easyui-numberbox" value="${bean.spouseMobile}"/></td>
					</tr>
					<tr>
						<td>单位名称:</td>
						<td><input id="spouseComName" name="houseApp.spouseComName" type="text" data-options="required:true,validType:['length[0,50]']" 
						class="textbox easyui-validatebox" value="${bean.spouseComName}"/></td>
						<td>年收入:</td>
						<td><input id="spouseIncome" name="houseApp.spouseIncome" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
						class="textbox easyui-numberbox" value="${bean.spouseIncome/10000}" style="width:128px;"/>万元</td>
						<td>职业:</td>
						<td><input id="spouseJob" name="houseApp.spouseJob" type="text" data-options="required:true,validType:['length[0,50]']" 
						class="textbox easyui-validatebox" value="${bean.spouseJob}"/></td>
					</tr>
					<tr>
						<td>单位地址:</td>
						<td colspan="5"><input id="spouseComProvince" name="houseApp.spouseComProvince" type="text" data-options="required:true,validType:['length[0,10]']" 
						class="easyui-combobox" value="${bean.spouseComProvince}" editable="false" style="width:140px;" />省
						<input id="spouseComCity" name="houseApp.spouseComCity" type="text" data-options="required:true,validType:['length[0,10]']" 
						class="easyui-combobox" value="${bean.spouseComCity}" editable="false" style="width:140px;" />市
						<input id="spouseComCounty" name="houseApp.spouseComCounty" type="text" data-options="required:true,validType:['length[0,10]']" 
						class="easyui-combobox" value="${bean.spouseComCounty}" editable="false" style="width:128px;" />区县
						<input id="spouseComAddress" name="houseApp.spouseComAddress" type="text" data-options="required:true,validType:['length[0,100]']" 
						class="textbox easyui-validatebox" value="${bean.spouseComAddress}" editable="false"/></td>			
					</tr>
				</table>
			</div>
				
				
				
				
<%--			<div id="commonContactsDiv">--%>
				<table class="datatable" id="commonContacts" style="width: auto;" >
					<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;">
					<strong>常用联系人（至少有1名直系亲属）
						<span>
<!-- 							<img src="img/addItem.gif" onclick="addObj('commonContacts');" /> -->
						</span>
					</strong></div><hr/>
					<tr>
						<input type="hidden"  id="commonContactsIndex" value="${commonContacts==null?3:(fn:length(commonContacts)>3?fn:length(commonContacts):3)}" />
						<th scope="col">姓名</th>
						<th scope="col">关系</th>
						<th scope="col">年龄</th>
						<th scope="col">工作单位</th>
						<th scope="col">联系方式</th>
					</tr>
					<tr id="commonContact_0">
						<td>
						<input type="hidden" name="commonContacts[0].id" value="${commonContacts[0].id}" />
						<input type="hidden" name="commonContacts[0].appId" value="${commonContacts[0].appId}" />
						<input type="hidden" name="commonContacts[0].type" value="1" />
						<input type="hidden" id="state" name="commonContacts[0].state" value="1" />
							<input id="name" name="commonContacts[0].name" 
							type="text" data-options="required:true,validType:['length[0,50]']" 
							class="textbox easyui-validatebox" 
							value="${commonContacts[0].name}"/>
						</td>
						<td>
<%--							<input id="relation" name="commonContacts[0].relation" --%>
<%--							type="text" data-options="required:true,validType:['length[0,2]']" --%>
<%--							class="textbox easyui-validatebox" value="${commonContacts[0].relation}" --%>
<%--							editable="false" />--%>
							<input id="relation" name="commonContacts[0].relation" type="text" data-options="required:true,validType:['length[0,10]']" 
							class="easyui-combobox" value="${commonContacts[0].relation}" editable="false" /> 
						</td>
						<td>
							<input id="age" name="commonContacts[0].age" 
							type="text" editable="false"  data-options="required:true,min:0,precision:0" 
							class="textbox easyui-numberbox" value="${commonContacts[0].age}"/>
						</td>
						<td>
							<input id="comName" name="commonContacts[0].comName" 
							type="text" data-options="required:true,validType:['length[0,50]']" 
							class="textbox easyui-validatebox" 
							value="${commonContacts[0].comName}"/>
						</td>
						<td>
							<input id="mobile" name="commonContacts[0].mobile" 
							type="text" data-options="required:true,validType:['length[0,50]']" 
							class="textbox easyui-validatebox" 
							value="${commonContacts[0].mobile}"/>
						</td>
					</tr>
					<tr id="commonContact_1">
						<td>
						<input type="hidden" name="commonContacts[1].id" value="${commonContacts[1].id}" />
						<input type="hidden" name="commonContacts[1].appId" value="${commonContacts[1].appId}" />
						<input type="hidden" name="commonContacts[1].type" value="1" />
						<input type="hidden" id="state" name="commonContacts[1].state" value="1" />
							<input id="name" name="commonContacts[1].name" 
							type="text" data-options="required:true,validType:['length[0,50]']" 
							class="textbox easyui-validatebox" 
							value="${commonContacts[1].name}"/>
						</td>
						<td>
<%--							<input id="relation" name="commonContacts[1].relation" --%>
<%--							type="text" data-options="required:true,validType:['length[0,2]']" --%>
<%--							class="textbox easyui-validatebox" value="${commonContacts[1].relation}" --%>
<%--							editable="false" />--%>
							<input id="relation" name="commonContacts[1].relation" type="text" data-options="required:true,validType:['length[0,10]']" 
							class="easyui-combobox" value="${commonContacts[1].relation}" editable="false" /> 
						</td>
						<td>
							<input id="age" name="commonContacts[1].age" 
							type="text" editable="false"  data-options="required:true,min:0,precision:0" 
							class="textbox easyui-numberbox" value="${commonContacts[1].age}"/>
						</td>
						<td>
							<input id="comName" name="commonContacts[1].comName" 
							type="text" data-options="required:true,validType:['length[0,50]']" 
							class="textbox easyui-validatebox" 
							value="${commonContacts[1].comName}"/>
						</td>
						<td>
							<input id="mobile" name="commonContacts[1].mobile" 
							type="text" data-options="required:true,validType:['length[0,50]']" 
							class="textbox easyui-validatebox" 
							value="${commonContacts[1].mobile}"/>
						</td>
					</tr>
					<tr id="commonContact_2">
						<td>
						<input type="hidden" name="commonContacts[2].id" value="${commonContacts[2].id}" />
						<input type="hidden" name="commonContacts[2].appId" value="${commonContacts[2].appId}" />
						<input type="hidden" name="commonContacts[2].type" value="1" />
						<input type="hidden" id="state" name="commonContacts[2].state" value="1" />
							<input id="name" name="commonContacts[2].name" 
							type="text" data-options="required:true,validType:['length[0,50]']" 
							class="textbox easyui-validatebox" 
							value="${commonContacts[2].name}"/>
						</td>
						<td>
<%--							<input id="relation" name="commonContacts[2].relation" --%>
<%--							type="text" data-options="required:true,validType:['length[0,2]']" --%>
<%--							class="textbox easyui-validatebox" value="${commonContacts[2].relation}" --%>
<%--							editable="false" />--%>
							<input id="relation" name="commonContacts[2].relation" type="text" data-options="required:true,validType:['length[0,10]']" 
							class="easyui-combobox" value="${commonContacts[2].relation}" editable="false" /> 
						</td>
						<td>
							<input id="age" name="commonContacts[2].age" 
							type="text" editable="false"  data-options="required:true,min:0,precision:0" 
							class="textbox easyui-numberbox" value="${commonContacts[2].age}"/>
						</td>
						<td>
							<input id="comName" name="commonContacts[2].comName" 
							type="text" data-options="required:true,validType:['length[0,50]']" 
							class="textbox easyui-validatebox" 
							value="${commonContacts[2].comName}"/>
						</td>
						<td>
							<input id="mobile" name="commonContacts[2].mobile" 
							type="text" data-options="required:true,validType:['length[0,50]']" 
							class="textbox easyui-validatebox" 
							value="${commonContacts[2].mobile}"/>
						</td>
					</tr>
			<c:forEach items="${commonContacts}" var="commonContact" varStatus="status">
				<c:if test="${status.index>2}">
					<tr id="commonContact_${status.index}">
						<td>
						<input type="hidden" name="commonContacts[${status.index}].id" value="${commonContact.id}" />
						<input type="hidden" name="commonContacts[${status.index}].appId" value="${commonContact.appId}" />
						<input type="hidden" name="commonContacts[${status.index}].type" value="1" />
						<input type="hidden" id="state" name="commonContacts[${status.index}].state" value="1" />
							<input id="name" name="commonContacts[${status.index}].name" 
							type="text" data-options="required:true,validType:['length[0,50]']" 
							class="textbox easyui-validatebox" value="${commonContact.name}"/>
						</td>
						<td>
<%--							<input id="relation" name="commonContacts[${status.index}].relation" --%>
<%--							type="text" data-options="validType:['length[0,2]']" --%>
<%--							class="textbox easyui-validatebox" value="${commonContact.relation}" --%>
<%--							editable="false" />--%>
							<input id="relation" name="commonContacts[${status.index}].relation" type="text" data-options="required:true,validType:['length[0,10]']" 
							class="easyui-combobox" value="${commonContact.relation}" editable="false" /> 
						</td>
						<td>
							<input id="age" name="commonContacts[${status.index}].age" 
							type="text" editable="false"  data-options="required:true,min:0,precision:0" 
							class="textbox easyui-numberbox" value="${commonContact.age}"/>
						</td>
						<td>
							<input id="comName" name="commonContacts[${status.index}].comName" 
							type="text" data-options="required:true,validType:['length[0,50]']" 
							class="textbox easyui-validatebox" 
							value="${commonContact.comName}"/>
						</td>
						<td>
							<input id="mobile" name="commonContacts[${status.index}].mobile" 
							type="text" data-options="required:true,validType:['length[0,50]']" 
							class="textbox easyui-validatebox" 
							value="${commonContact.mobile}"/>
<%-- 							<img src="img/deleteItem2.png"  onclick="rmObj('commonContact_${status.index}');" /> --%>
						</td>
					</tr>
				</c:if>
			</c:forEach>
<%--		</div>--%>
					
<tr style="display: none;"><td>
<textarea id="commonContacts_textarea" disabled="disabled">
	<tr id="commonContact_commonContactsIndex">
		<td>
		<input type="hidden" name="commonContacts[commonContactsIndex].id" value="" />
		<input type="hidden" name="commonContacts[commonContactsIndex].appId" value="${bean.appId}" />
		<input type="hidden" name="commonContacts[commonContactsIndex].type" value="1" />
		<input type="hidden" id="state" name="commonContacts[commonContactsIndex].state" value="1" />
			<input id="name" name="commonContacts[commonContactsIndex].name" 
			type="text" data-options="required:true,validType:['length[0,50]']" 
			class="textbox easyui-validatebox" 
			value=""/>
		</td>
		<td>
<%--			<input id="relation" name="commonContacts[commonContactsIndex].relation" --%>
<%--			type="text" data-options="validType:['length[0,2]']" --%>
<%--			class="textbox easyui-validatebox" value="" --%>
<%--			editable="false"/>--%>
			<input id="relation" name="commonContacts[commonContactsIndex].relation" type="text" data-options="required:true,validType:['length[0,10]']" 
			class="easyui-combobox" value="" editable="false" /> 
		</td>
		<td>
			<input id="age" name="commonContacts[commonContactsIndex].age" 
			type="text" editable="false"  data-options="required:true,min:0,precision:0" 
			class="textbox easyui-numberbox" value=""/>
		</td>
		<td>
			<input id="comName" name="commonContacts[commonContactsIndex].comName" 
			type="text" data-options="required:true,validType:['length[0,50]']" 
			class="textbox easyui-validatebox" 
			value=""/>
		</td>
		<td>
			<input id="mobile" name="commonContacts[commonContactsIndex].mobile" 
			type="text" data-options="required:true,validType:['length[0,50]']" 
			class="textbox easyui-validatebox" 
			value=""/>
<!-- 			<img src="img/deleteItem2.png"  onclick="rmObj('commonContact_commonContactsIndex');" /> -->
		</td>
	</tr>
</textarea>
</td></tr>
</table>
<div id="mainBusinessInfoDiv" <c:if test="${fn:contains(bean.product,'业主贷')==false}">style="display: none;"</c:if>>
	<table>	
		<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>主营业务信息（业主贷必填）</strong></div><hr/>
		<tr>
			<td>商号名称:</td>
			<td><input id="firmName" name="houseApp.firmName" type="text" data-options="required:true,validType:['length[0,100]']" 
			class="textbox easyui-validatebox" value="${bean.firmName}"/></td>
			<td>主营业务:</td>
			<td><input id="firmMainBusiness" name="houseApp.firmMainBusiness" type="text" data-options="required:true,validType:['length[0,100]']" 
			class="textbox easyui-validatebox" value="${bean.firmMainBusiness}"/></td>
			<td>年营业额:</td>
			<td><input id="firmIncome" name="houseApp.firmIncome" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
			class="textbox easyui-numberbox" value="${bean.firmIncome/10000}" style="width:128px;"/>万元</td>
			<td>占股比例:</td>
			<td><input id="firmShare" name="houseApp.firmShare" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
			class="textbox easyui-numberbox" value="${bean.firmShare}" style="width:140px;"/>%</td>				
		</tr>
		<tr>
			<td>商号地址:</td>
			<td colspan="5" ><input id="firmProvince" name="houseApp.firmProvince" type="text" data-options="required:true,validType:['length[0,10]']" 
			class="easyui-combobox" value="${bean.firmProvince}" editable="false" style="width:140px;" />省
			<input id="firmCity" name="houseApp.firmCity" type="text" data-options="required:true,validType:['length[0,10]']" 
			class="easyui-combobox" value="${bean.firmCity}" editable="false" style="width:140px;" />市
			<input id="firmCounty" name="houseApp.firmCounty" type="text" data-options="required:true,validType:['length[0,10]']" 
			class="easyui-combobox" value="${bean.firmCounty}" editable="false" style="width:128px;" />区县
			<input id="firmAddress" name="houseApp.firmAddress" type="text" data-options="required:true,validType:['length[0,100]']" 
			class="textbox easyui-validatebox" value="${bean.firmAddress}" editable="false"/></td>
		</tr>
		<tr>
			<td>本地经营时间:</td>
			<td><input id="firmManageDate" name="houseApp.firmManageDate" type="text" editable="false" data-options="required:true"
			class="textbox easyui-datebox" value="${bean.firmManageDateStr}"/></td>					
		</tr>
	</table>
</div>
				
<div id="dealingsContactDiv"  <c:if test="${fn:contains(bean.product,'业主贷')==false}">style="display: none;"</c:if>>				
<table class="datatable" id="dealingsContacts" style="width: auto;" >
	<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;">
	<strong>经营往来联系人（业主贷必填）
		<span>
<!-- 			<img src="img/addItem.gif" onclick="addObj('dealingsContacts');" /> -->
		</span>
	</strong></div><hr/>
	<tr>
		<input type="hidden"  id="dealingsContactsIndex" value="${dealingsContacts==null?3:(fn:length(dealingsContacts)>3?fn:length(dealingsContacts):3)}" />
		<th scope="col">姓名</th>
		<th scope="col">联系方式</th>
		<th scope="col">单位名称</th>
		<th scope="col">合作年限</th>
		<th scope="col">合作方式</th>
	</tr>
	<tr id="dealingsContact_0">
		<td>
		<input type="hidden" name="dealingsContacts[0].id" value="${dealingsContacts[0].id}" />
		<input type="hidden" name="dealingsContacts[0].appId" value="${dealingsContacts[0].appId}" />
		<input type="hidden" name="dealingsContacts[0].type" value="2" />
		<input type="hidden" id="state" name="dealingsContacts[0].state" value="1" />
			<input id="name" name="dealingsContacts[0].name" 
			type="text" data-options="required:true,validType:['length[0,50]']" 
			class="textbox easyui-validatebox" 
			value="${dealingsContacts[0].name}"/>
		</td>
		<td>
			<input id="phone" name="dealingsContacts[0].phone" 
			type="text" data-options="required:true,validType:['length[0,50]']" 
			class="textbox easyui-validatebox" 
			value="${dealingsContacts[0].phone}"/>
		</td>
		<td>
			<input id="comName" name="dealingsContacts[0].comName" 
			type="text" data-options="required:true,validType:['length[0,50]']" 
			class="textbox easyui-validatebox" 
			value="${dealingsContacts[0].comName}"/>
		</td>
		<td>
			<input id="cooperationYear" name="dealingsContacts[0].cooperationYear" 
			type="text" editable="false"  data-options="required:true,min:0,precision:0" 
			class="textbox easyui-numberbox" value="${dealingsContacts[0].cooperationYear}"/>
		</td>
		<td>
			<input id="cooperationType" name="dealingsContacts[0].cooperationType" 
			type="text" data-options="required:true,validType:['length[0,10]']" 
			class="textbox easyui-validatebox" value="${dealingsContacts[0].cooperationType}" 
			editable="false" style="width:140px;" />
		</td>
	</tr>
	<tr id="dealingsContact_1">
		<td>
		<input type="hidden" name="dealingsContacts[1].id" value="${dealingsContacts[1].id}" />
		<input type="hidden" name="dealingsContacts[1].appId" value="${dealingsContacts[1].appId}" />
		<input type="hidden" name="dealingsContacts[1].type" value="2" />
		<input type="hidden" id="state" name="dealingsContacts[1].state" value="1" />
			<input id="name" name="dealingsContacts[1].name" 
			type="text" data-options="required:true,validType:['length[0,50]']" 
			class="textbox easyui-validatebox" 
			value="${dealingsContacts[1].name}"/>
		</td>
		<td>
			<input id="phone" name="dealingsContacts[1].phone" 
			type="text" data-options="required:true,validType:['length[0,50]']" 
			class="textbox easyui-validatebox" 
			value="${dealingsContacts[1].phone}"/>
		</td>
		<td>
			<input id="comName" name="dealingsContacts[1].comName" 
			type="text" data-options="required:true,validType:['length[0,50]']" 
			class="textbox easyui-validatebox" 
			value="${dealingsContacts[1].comName}"/>
		</td>
		<td>
			<input id="cooperationYear" name="dealingsContacts[1].cooperationYear" 
			type="text" editable="false"  data-options="required:true,min:0,precision:0" 
			class="textbox easyui-numberbox" value="${dealingsContacts[1].cooperationYear}"/>
		</td>
		<td>
			<input id="cooperationType" name="dealingsContacts[1].cooperationType" 
			type="text" data-options="required:true,validType:['length[0,10]']" 
			class="textbox easyui-validatebox" value="${dealingsContacts[1].cooperationType}" 
			editable="false" style="width:140px;" />
		</td>
	</tr>
	<tr id="dealingsContact_2">
		<td>
		<input type="hidden" name="dealingsContacts[2].id" value="${dealingsContacts[2].id}" />
		<input type="hidden" name="dealingsContacts[2].appId" value="${dealingsContacts[2].appId}" />
		<input type="hidden" name="dealingsContacts[2].type" value="2" />
		<input type="hidden" id="state" name="dealingsContacts[2].state" value="1" />
			<input id="name" name="dealingsContacts[2].name" 
			type="text" data-options="required:true,validType:['length[0,50]']" 
			class="textbox easyui-validatebox" 
			value="${dealingsContacts[2].name}"/>
		</td>
		<td>
			<input id="phone" name="dealingsContacts[2].phone" 
			type="text" data-options="required:true,validType:['length[0,50]']" 
			class="textbox easyui-validatebox" 
			value="${dealingsContacts[2].phone}"/>
		</td>
		<td>
			<input id="comName" name="dealingsContacts[2].comName" 
			type="text" data-options="required:true,validType:['length[0,50]']" 
			class="textbox easyui-validatebox" 
			value="${dealingsContacts[2].comName}"/>
		</td>
		<td>
			<input id="cooperationYear" name="dealingsContacts[2].cooperationYear" 
			type="text" editable="false"  data-options="required:true,min:0,precision:0" 
			class="textbox easyui-numberbox" value="${dealingsContacts[2].cooperationYear}"/>
		</td>
		<td>
			<input id="cooperationType" name="dealingsContacts[2].cooperationType" 
			type="text" data-options="required:true,validType:['length[0,10]']" 
			class="textbox easyui-validatebox" value="${dealingsContacts[2].cooperationType}" 
			editable="false" style="width:140px;" />
		</td>
	</tr>
	<c:forEach items="${dealingsContacts}" var="dealingsContact" varStatus="status">
	<c:if test="${status.index>2}">
		<tr id="dealingsContact_${status.index}">
			<td>
			<input type="hidden" name="dealingsContacts[${status.index}].id" value="${dealingsContact.id}" />
			<input type="hidden" name="dealingsContacts[${status.index}].appId" value="${dealingsContact.appId}" />
			<input type="hidden" name="dealingsContacts[${status.index}].type" value="2" />
			<input type="hidden" id="state" name="dealingsContacts[${status.index}].state" value="1" />
				<input id="name" name="dealingsContacts[${status.index}].name" 
				type="text" data-options="required:true,validType:['length[0,50]']"
				class="textbox easyui-validatebox" 
				value="${dealingsContact.name}"/>
			</td>
			<td>
				<input id="phone" name="dealingsContacts[${status.index}].phone" 
				type="text" data-options="required:true,validType:['length[0,50]']" 
				class="textbox easyui-validatebox" 
				value="${dealingsContact.phone}"/>
			</td>
			<td>
				<input id="comName" name="dealingsContacts[${status.index}].comName" 
				type="text" data-options="required:true,validType:['length[0,50]']" 
				class="textbox easyui-validatebox" 
				value="${dealingsContact.comName}"/>
			</td>
			<td>
				<input id="cooperationYear" name="dealingsContacts[${status.index}].cooperationYear" 
				type="text" editable="false"  data-options="required:true,min:0,precision:0" 
				class="textbox easyui-numberbox" value="${dealingsContact.cooperationYear}"/>
			</td>
			<td>
				<input id="cooperationType" name="dealingsContacts[${status.index}].cooperationType" 
				type="text" data-options="required:true,validType:['length[0,10]']" 
				class="textbox easyui-validatebox" value="${dealingsContact.cooperationType}" 
				editable="false" style="width:140px;" />
<%-- 				<img src="img/deleteItem2.png"  onclick="rmObj('dealingsContact_${status.index}');" /> --%>
			</td>
		</tr>
	</c:if>
</c:forEach>			
<tr style="display: none;"><td>
<textarea id="dealingsContacts_textarea" disabled="disabled">
<tr id="dealingsContact_dealingsContactsIndex">
		<td>
		<input type="hidden" name="dealingsContacts[dealingsContactsIndex].id" value="" />
		<input type="hidden" name="dealingsContacts[dealingsContactsIndex].appId" value="${bean.appId}" />
		<input type="hidden" name="dealingsContacts[dealingsContactsIndex].type" value="2" />
		<input type="hidden" id="state" name="dealingsContacts[dealingsContactsIndex].state" value="1" />
			<input id="name" name="dealingsContacts[dealingsContactsIndex].name" 
			type="text" data-options="required:true,validType:['length[0,50]']" 
			class="textbox easyui-validatebox" 
			value=""/>
		</td>
		<td>
			<input id="phone" name="dealingsContacts[dealingsContactsIndex].phone" 
			type="text" data-options="required:true,validType:['length[0,50]']" 
			class="textbox easyui-validatebox" 
			value=""/>
		</td>
		<td>
			<input id="comName" name="dealingsContacts[dealingsContactsIndex].comName" 
			type="text" data-options="required:true,validType:['length[0,50]']" 
			class="textbox easyui-validatebox" 
			value=""/>
		</td>
		<td>
			<input id="cooperationYear" name="dealingsContacts[dealingsContactsIndex].cooperationYear" 
			type="text" editable="false"  data-options="required:true,min:0,precision:0" 
			class="textbox easyui-numberbox" value=""/>
		</td>
		<td>
			<input id="cooperationType" name="dealingsContacts[dealingsContactsIndex].cooperationType" 
			type="text" data-options="required:true,validType:['length[0,10]']" 
			class="textbox easyui-validatebox" value="" 
			editable="false" style="width:140px;" />
<!-- 			<img src="img/deleteItem2.png"  onclick="rmObj('dealingsContact_dealingsContactsIndex');" /> -->
		</td>
	</tr>
</textarea>
</td></tr>
</table>
</div>
				<table>	
					<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>重要情况说明</strong></div><hr/>
					<tr>
						<td>是否有未结清借款:</td>
						<td id="houseExt_1">
							<input type="hidden" name="houseExts[0].id" value="${houseExts[0].id}" />
							<input type="hidden" name="houseExts[0].appId" value="${bean.appId}" />
							<input type="hidden" name="houseExts[0].type" value="1" />
							<input type="hidden" name="houseExts[0].key" value="1" />
							<input type="hidden" name="houseExts[0].name" value="是否有未结清借款" />
							<input type="radio" id="value" name="houseExts[0].value" value='0'
								<c:if test="${houseExts[0].value=='0'||houseExts[0].value==null}">checked='checked'</c:if> />否
							<input type="radio" id="entLoanMark" name="houseExts[0].value" value="1"
								<c:if test="${houseExts[0].value=='1'}">checked='checked'</c:if> />是
							<input id="remarks" name="houseExts[0].remarks" type="text" data-options="min:0,precision:0,validType:['length[0,500]']" 
							class="textbox easyui-validatebox" value="${houseExts[0].remarks}"/>
						</td>
											
						<td>是否有资产已被抵押:</td>
						<td id="houseExt_2">
							<input type="hidden" name="houseExts[1].id" value="${houseExts[1].id}" />
							<input type="hidden" name="houseExts[1].appId" value="${bean.appId}" />
							<input type="hidden" name="houseExts[1].type" value="1" />
							<input type="hidden" name="houseExts[1].key" value="2" />
							<input type="hidden" name="houseExts[1].name" value="是否有资产已被抵押" />
							<input type="radio" id="value" name="houseExts[1].value" value='0'
								<c:if test="${houseExts[1].value=='0'||houseExts[1].value==null}">checked='checked'</c:if> />否
							<input type="radio" id="entLoanMark" name="houseExts[1].value" value="1"
								<c:if test="${houseExts[1].value=='1'}">checked='checked'</c:if> />是
							<input id="remarks" name="houseExts[1].remarks" type="text" data-options="min:0,precision:0,validType:['length[0,500]']" 
							class="textbox easyui-validatebox" value="${houseExts[1].remarks}"/>
						</td>
					</tr>
					<tr>
						<td>是否涉及诉讼:</td>
						<td id="houseExt_3">
							<input type="hidden" name="houseExts[2].id" value="${houseExts[2].id}" />
							<input type="hidden" name="houseExts[2].appId" value="${bean.appId}" />
							<input type="hidden" name="houseExts[2].type" value="1" />
							<input type="hidden" name="houseExts[2].key" value="3" />
							<input type="hidden" name="houseExts[2].name" value="是否涉及诉讼" />
							<input type="radio" id="value" name="houseExts[2].value" value='0'
								<c:if test="${houseExts[2].value=='0'||houseExts[2].value==null}">checked='checked'</c:if> />否
							<input type="radio" id="entLoanMark" name="houseExts[2].value" value="1"
								<c:if test="${houseExts[2].value=='1'}">checked='checked'</c:if> />是
							<input id="remarks" name="houseExts[2].remarks" type="text" data-options="min:0,precision:0,validType:['length[0,500]']" 
							class="textbox easyui-validatebox" value="${houseExts[2].remarks}"/>
						</td>			
						<td>近期是否有对生意有较大影响的事件:</td>
						<td id="houseExt_4">
							<input type="hidden" name="houseExts[3].id" value="${houseExts[3].id}" />
							<input type="hidden" name="houseExts[3].appId" value="${bean.appId}" />
							<input type="hidden" name="houseExts[3].type" value="1" />
							<input type="hidden" name="houseExts[3].key" value="4" />
							<input type="hidden" name="houseExts[3].name" value="近期是否有对生意有较大影响的事件" />
							<input type="radio" id="value" name="houseExts[3].value" value='0'
								<c:if test="${houseExts[3].value=='0'||houseExts[3].value==null}">checked='checked'</c:if> />否
							<input type="radio" id="entLoanMark" name="houseExts[3].value" value="1"
								<c:if test="${houseExts[3].value=='1'}">checked='checked'</c:if> />是
							<input id="remarks" name="houseExts[3].remarks" type="text" data-options="min:0,precision:0,validType:['length[0,500]']" 
							class="textbox easyui-validatebox" value="${houseExts[3].remarks}"/>
						</td>
					</tr>
					<tr>
						<td>是否为其他人提供担保:</td>
						<td id="houseExt_5">
							<input type="hidden" name="houseExts[4].id" value="${houseExts[4].id}" />
							<input type="hidden" name="houseExts[4].appId" value="${bean.appId}" />
							<input type="hidden" name="houseExts[4].type" value="1" />
							<input type="hidden" name="houseExts[4].key" value="5" />
							<input type="hidden" name="houseExts[4].name" value="是否为其他人提供担保" />
							<input type="radio" id="value" name="houseExts[4].value" value='0'
								<c:if test="${houseExts[4].value=='0'||houseExts[4].value==null}">checked='checked'</c:if> />否
							<input type="radio" id="entLoanMark" name="houseExts[4].value" value="1"
								<c:if test="${houseExts[4].value=='1'}">checked='checked'</c:if> />是
							<input id="remarks" name="houseExts[4].remarks" type="text" data-options="min:0,precision:0,validType:['length[0,500]']" 
							class="textbox easyui-validatebox" value="${houseExts[4].remarks}"/>
						</td>			
						<td>本人或配偶是否有“曾用名”:</td>
						<td id="houseExt_6">
							<input type="hidden" name="houseExts[5].id" value="${houseExts[5].id}" />
							<input type="hidden" name="houseExts[5].appId" value="${bean.appId}" />
							<input type="hidden" name="houseExts[5].type" value="1" />
							<input type="hidden" name="houseExts[5].key" value="6" />
							<input type="hidden" name="houseExts[5].name" value="本人或配偶是否有“曾用名”" />
							<input type="radio" id="value" name="houseExts[5].value" value='0'
								<c:if test="${houseExts[5].value=='0'||houseExts[5].value==null}">checked='checked'</c:if> />否
							<input type="radio" id="entLoanMark" name="houseExts[5].value" value="1"
								<c:if test="${houseExts[5].value=='1'}">checked='checked'</c:if> />是
							<input id="remarks" name="houseExts[5].remarks" type="text" data-options="min:0,precision:0,validType:['length[0,500]']" 
							class="textbox easyui-validatebox" value="${houseExts[5].remarks}"/>
						</td>
					</tr>
					<tr>
						<td>
							申请描述：
						</td>
						<td colspan="3">
							<textarea id="remark" name="houseApp.remark" value="${bean.remark}" class="textbox easyui-validatebox" data-options="required:true,validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;">${bean.remark}</textarea>
						</td>
					</tr>
					<br/>
					<c:if test="${null != returnReasons && '' != returnReasons}">
						<tr>
						<td>
							审核退回原因:
						</td>
						<td colspan=3">
							<textarea value="${returnReasons}" class="textbox easyui-validatebox" data-options="validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;background-color: #F0F0F0;" disabled="disabled">${returnReasons}</textarea>
						</td>
					</tr>
					</c:if>
					<tr>
						<td colspan=3">
<!-- 							<input type="button" value="保存" class="btn" onclick="javascript:updateFunction('save');"/>&nbsp;&nbsp; -->
							<input type="button" value="前端拒贷" class="btn" onclick="javascript:artOpenPage('前端拒贷','house/app/frontRefusePopupReplenish.do?id=${bean.id}');" title="拒贷"/>&nbsp;&nbsp;
<!-- 							<input type="button" value="客户放弃" class="btn" onclick="updateFunction('waive')"/>&nbsp;&nbsp; -->
<!-- 							<input type="button" value="提交" class="btn" onclick="updateFunction('submit')"/>&nbsp;&nbsp; -->
							<input type="button" value="返回" class="btn" onclick="javascript:back()"/>
						</td>
					</tr>
				</table>
				<table>	
					<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>申请资料上传</strong></div><hr/>
					<tr>
						<td>
							<span style="float:left ;"  id="yingxiangUP">
								附件：&nbsp; &nbsp;
<%--								<input onchange="fileForm();" id="file" name="file" type="file" accept="application/x-zip-compressed" />--%>
								<input id="file" name="file" type="file" accept="application/x-zip-compressed" />&nbsp; &nbsp;
							</span>
<!-- 								<input onclick="fileForm();"  value="上传" type="button" class="btn" /> -->
						</td>
					</tr>
					<tr>
						<td><SPAN style="color:red">上传提示：压缩文件不要大于10M 并且压缩包内单个图片不能大于1M</SPAN></td>
					</tr>
				</table>	
				<table width="100%">
<%--					<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>影像查看</strong></div><hr/>--%>
					<tr>
						<td id="imgDiv">
						<jsp:include page="/img/imgSlidePath.do?appId=${bean.appId}" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
<form id="fileSmt" action="file/upload/zipupload.do"  enctype="multipart/form-data" style="display: none;" >
	<input type="hidden" id="fileappId" name="appId" <c:if test="${bean!=null}">value="${bean.appId}"</c:if>/>
</form>
<div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
	<img src="img/loading.gif" alt="加载中..." />
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

function tabsReadOnly(redinfo){
	redinfo.find("input[type='radio']").attr('disabled',true);
// 	redinfo.find("input[type='button']").attr("disabled",true);
	redinfo.find('.easyui-validatebox').attr('disabled',true);
	redinfo.find('.easyui-combobox').combo('disable');
	redinfo.find('.easyui-numberbox').numberbox('disable');
	redinfo.find('.easyui-datebox').datebox('disable');
}
</script>
<script type="text/javascript" >
	//添加对象
	function addObj(key){
		var html=$("#"+key+"_textarea").val();		//得到 特约驾驶员 HTML
		var keyIndex=key+"Index";
		var index=$("#"+keyIndex).val();					//得到索引值
		html=html.replace(eval('/'+keyIndex+'/gm'),index);	//把索引占位符 替换
		var newDriver=$(html);					//转换成 JQuery 对象
		newDriver.appendTo("#"+key);				//添加到 对应地点
		$.parser.parse(newDriver);						//初始化 EasyUI
		if(key=="commonContacts"){
			initRelation("commonContact_"+index);
		}
		
		$("#"+keyIndex).val(++index);	//索引递增
		
	}
	//删除元素 实际上是隐藏元素
	function rmObj(objId,sm){
		$.messager.confirm('消息', "是否确认删除", function(ok){
            if (ok){
            	var obj = $("#"+objId);
        		obj.find("#state").val("0");
        		obj.find('.easyui-combobox').combo('disableValidation');
        		obj.find('.easyui-numberbox').numberbox('disableValidation');
        		obj.find('.easyui-datebox').datebox('disableValidation');
        		obj.find('.easyui-validatebox').validatebox('disableValidation');
        		obj.hide();
            }
		});
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
</script>

<script type="text/javascript">
//更新保存
function updateFunction(buttonType) {
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});

	//保存不验证 
	if("save"!=buttonType){
		//验证表单验证是否通过
		if(false == $('#updateForm').form('validate') ){
			$.messager.alert('消息', "页面有必输字段，但未填值！");
			return;
		}
	}
	//弹出异步加载 遮罩
	openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "house/app/save.do",
		data : params+"&buttonType="+buttonType,
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					// 保存的时候 当前页面刷新， 解决第一次保存APPID 与ID 为空的问题
					if(buttonType=="save") {
						location.replace("<%=basePath%>house/app/update.do?id="+data.id);
					}else{
						window.history.go(-1);
					}
            	});
            } else {				
    			$.messager.alert('消息', data.message);
            }
		},
		error : function() {
			//关闭遮罩，弹出消息框
			closeMask();
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
		}
	});
}
//文件上传
function fileForm(){
	$('#isStart').val('');
	var appid=$('#appId').val();
	if(appid!=null&&appid!=''){
		var thisfile=$('#yingxiangUP').find("#file");
		var fileValue=$('#file').val();
		if((fileValue.substring(fileValue.lastIndexOf("."))).toUpperCase()!=".ZIP"){
			$.messager.alert('消息', "文件类型必须为 ZIP格式");
			return;
		}
		
		$.messager.confirm('消息', "是否确定上传？", function(ok){
            if (ok){
            	var $file=$("#fileSmt input[name='file']");
        		if($file!=null){
        			$file.remove();
        		}
        		var fcolne=thisfile.clone();
        		$('#fileSmt').append(thisfile);
        		$('#yingxiangUP').append(fcolne);
        		$('#fileSmt').submit();
            }
    	});
	}else{
		$.messager.alert('消息', "请先保存，然后才能上传附件。");
	}
}
$('#fileSmt').submit(function() {
	openMask();
	// 上传文件 必须用 这个 异步提交
    $(this).ajaxSubmit({
    		type : "POST",
    		contentType:"multipart/form-data",
    		url : "file/upload/zipupload.do",
    		//beforeSubmit:function(type) {
//     			alert(type);
    		//},
    	    success:function(data) {
    	    	data=jQuery.parseJSON(data);
    			if ("true"==data.success) {
    				
    				$.messager.alert('消息', data.message,"info", function(){
    	                	imgpartLoad($('#appId').val());
    	        	});
                } else {
    				
    				$.messager.alert('消息', data.message);
                }
    			closeMask();
    		}
    });
    return false;
});

//返回
function back(){
	window.history.go(-1);
}

/* //打开Loading遮罩并修改样式
function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
} */

//切换 是否验证
function toggleValidate(objId,isValidete){
	var state=!isValidete;
	var obj=$(objId);
	obj.find('.easyui-validatebox').validatebox({novalidate:state});
	obj.find('.easyui-combobox').combobox({novalidate:state});
	obj.find('.easyui-numberbox').validatebox({novalidate:state});
	obj.find('.easyui-datebox').datebox({novalidate:state});
}

//设置姓名的只读和可编辑状态
function setNameReadOnly(){
	if($("#customerId").val() != '')
		$("#basicInfo").find("#name").attr("readonly","readonly");//将input元素设置为readonly
	else
		$("#basicInfo").find("#name").removeAttr("readonly");//去除input元素的readonly属性
}
function getCustomer(){
	 $.ajax({
		type : "POST",
		url : "customer/readJson.do",
		data : encodeURI("customerType=01&idType=01&zjNumber=" + $("#idNo").val()),
// 		dataType : "json",
		success : function(data) {
			if(data.bean){
				$("#customerId").val(data.bean.id);
				$("#name").val(data.bean.name);//姓名
				$("#mobile").val(data.bean.mobile);//手机号
				$("#diploma").combobox('setValue', data.bean.education);//学历
				$("#email").val(data.bean.email);//邮箱
				$("#marriage").combobox('setValue', data.bean.marriage);//婚姻状况
				$("#addProvince").combobox('setValue', data.bean.addProvince);//居住地址-省
				$("#addCity").combobox('setValue', data.bean.addCity);//居住地址-市
				$("#addCounty").combobox('setValue', data.bean.beanounty);//居住地址-区
				$("#address").val(data.bean.address);//居住地址-详细
				$("#phone").val(data.bean.phone);//住宅电话
				$("#comName").val(data.bean.companyName);//单位名称
				$("#comDuty").val(data.bean.jobDuty);//单位担任职务
			}else{
				$("#customerId").val(null);
				/* $("#name").val(null);//姓名
				$("#mobile").val(null);//手机号
				$("#diploma").combobox('setValue', null);//学历
				$("#email").val(null);//邮箱
				$("#marriage").combobox('setValue', null);//婚姻状况
				$("#addProvince").combobox('setValue', null);//居住地址-省
				$("#addCity").combobox('setValue', null);//居住地址-市
				$("#addCounty").combobox('setValue', null);//居住地址-区
				$("#address").val(null);//居住地址-详细
				$("#phone").val(null);//住宅电话
				$("#comName").val(null);//单位名称
				$("#comDuty").val(null);//单位担任职务 */
//					$.messager.alert('消息', '没有检索到客户，请先添加。',"info", function(){
//						window.history.go(-1);
//		        	});
			}
			setNameReadOnly(); 
		}
	}); 
}	

//原页面弹出框前端拒贷
function artOpenPage(_title,_url) {
	if($("body").find("#dialogDiv").length==0){
		 $('body').append($("<div id='dialogDiv' style='top:150px;'></div>"));
	}
	$('#dialogDiv').dialog({
	    title: _title,
	    height: 350,
	    width: 500,
	    closed: false,
	    cache: false,
	    href: encodeURI(_url),
	    modal: true,
	    resizable: true,
	    onLoad: function() {
			//填充select数据 拒件码01
		    var saleRefuseCode01url = "sys/datadictionary/listjason.do?keyName=saleRefuseCode01";
		    $("#saleRefuseCode1").combobox("clear");
		    $('#saleRefuseCode1').combobox({
		        url: saleRefuseCode01url,
		        valueField: 'keyProp',
		        textField: 'keyValue',
		        onChange: function(newValue, oldValue){
		            $('#saleRefuseCode2').combobox('clear');
		            var saleRefuseCode02url = "sys/datadictionary/listjason.do?keyName=saleRefuseCode02&parentKeyProp=" + encodeURI(newValue);
		            $('#saleRefuseCode2').combobox('reload',saleRefuseCode02url); 
		        }
		    });
		    //填充select数据 拒件码02
		    var saleRefuseCode1 = $('#saleRefuseCode1').combobox('getValue');
		    var saleRefuseCode02url = "sys/datadictionary/listjason.do?keyName=saleRefuseCode02&parentKeyProp=" + encodeURI(saleRefuseCode1);
		    $("#saleRefuseCode2").combobox("clear");
		    $('#saleRefuseCode2').combobox({
		        url: saleRefuseCode02url,
		        valueField: 'keyProp',
		        textField: 'keyValue'
		    }); 
	 	}
	});
	$('#dialogDiv').window('center');
}

//页面加载完动作
$(document).ready(function (){
	//$.parser.parse($("#commonContactsDiv"));
	//初始化 营销人员
		var sales="sales/listSelect.do?orgId=${login_org.orgId}";
		$('#staffNo').combobox({url:sales, valueField:'staffNo', textField:'name'});
		
	setNameReadOnly(); 
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
		data:dataDictJson.marriage,
		onChange: function(marriageVal){
			if(marriageVal == '02'){
				$("#marriageDiv").show();//显示div 
				toggleValidate("#marriageDiv",true);
			}else{
				$("#marriageDiv").hide();//隐藏div  
				toggleValidate("#marriageDiv",false);
			}
    	}
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
	
	//填充select数据 产品
	tsurl="product/hedao/listjason.do?type=5&state=1";
	$("#product").combobox("clear");
	$('#product').combobox({url:tsurl,
		valueField:'name', 
		textField:'name',
		onChange: function(product){
		if(product.indexOf('业主贷')==0){
			$("#mainBusinessInfoDiv").show();//显示div 
			toggleValidate("#mainBusinessInfoDiv",true);
			$("#dealingsContactDiv").show();//显示div   
			toggleValidate("#dealingsContactDiv",true);
		}else{
			$("#mainBusinessInfoDiv").hide();//显示div  
			toggleValidate("#mainBusinessInfoDiv",false);
			$("#dealingsContactDiv").hide();//显示div 
			toggleValidate("#dealingsContactDiv",false);
		}
   	}});
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
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
	            $('#useage2').combobox('clear');
	            var useage2url = "sys/datadictionary/listjason.do?keyName=creditusage2&parentKeyProp=" + encodeURI(newValue);
	            $('#useage2').combobox('reload',useage2url); 
       		}
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
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
            $('#kosekiCity').combobox('clear');
            $('#kosekiCounty').combobox('clear');
            var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
            $('#kosekiCity').combobox('reload',cityurl); 
        }
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
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
            $('#addCity').combobox('clear');
            $('#addCounty').combobox('clear');
            var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
            $('#addCity').combobox('reload',cityurl); 
        }
		});
    //填充select数据 居住地市
    var province = $('#addProvince').combobox('getValue');
		var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(province);
		$("#addCity").combobox("clear");
		$('#addCity').combobox({
			url: cityurl,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
            $('#addCounty').combobox('clear');
            var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
            $('#addCounty').combobox('reload',countyurl); 
        }
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
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
            $('#comCity').combobox('clear');
            $('#comCounty').combobox('clear');
            var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
            $('#comCity').combobox('reload',cityurl); 
        }
		});
    //填充select数据 申请人单位地市
    var province = $('#comProvince').combobox('getValue');
		var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(province);
		$("#comCity").combobox("clear");
		$('#comCity').combobox({
			url: cityurl,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
            $('#comCounty').combobox('clear');
            var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
            $('#comCounty').combobox('reload',countyurl); 
        }
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
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
            $('#spouseComCity').combobox('clear');
            $('#spouseComCounty').combobox('clear');
            var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
            $('#spouseComCity').combobox('reload',cityurl); 
        }
		});
    //填充select数据 配偶单位地市
    var province = $('#spouseComProvince').combobox('getValue');
		var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(province);
		$("#spouseComCity").combobox("clear");
		$('#spouseComCity').combobox({
			url: cityurl,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
            $('#spouseComCounty').combobox('clear');
            var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
            $('#spouseComCounty').combobox('reload',countyurl); 
        }
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
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
            $('#firmCity').combobox('clear');
            $('#firmCounty').combobox('clear');
            var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
            $('#firmCity').combobox('reload',cityurl); 
        }
		});
    //填充select数据 商号地址市
    var province = $('#firmProvince').combobox('getValue');
		var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(province);
		$("#firmCity").combobox("clear");
		$('#firmCity').combobox({
			url: cityurl,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
            $('#firmCounty').combobox('clear');
            var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
            $('#firmCounty').combobox('reload',countyurl); 
        }
		});
    //填充select数据 商号地址区县
    var city = $('#firmCity').combobox('getValue');
		var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(city);
		$("#firmCounty").combobox("clear");
		$('#firmCounty').combobox({url: countyurl, valueField: 'keyProp',textField: 'keyValue'});
		
		
	//判断 婚姻状况 ，取消隐藏域 必填验证
	var marriageValue= $("#marriage").combobox("getValue");
		if(marriageValue!='02'){// 02是已婚
			toggleValidate("#marriageDiv",false);
		}
	//判断  产品类型 ，取消隐藏域 必填验证
	var productValue=$("#product").combobox("getValue");

	if(productValue.indexOf('业主贷')==-1){
		//if(productValue!='业主贷'){
			toggleValidate("#dealingsContactDiv",false);
			toggleValidate("#mainBusinessInfoDiv",false);
		}else{
			toggleValidate("#dealingsContactDiv",true);
			toggleValidate("#mainBusinessInfoDiv",true);
		}
		
});
</script>
</html>


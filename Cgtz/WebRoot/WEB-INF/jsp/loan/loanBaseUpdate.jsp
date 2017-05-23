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
<title>个人借款业务申请</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
</head>
<body>
	<div class="easyui-tabs" id="appUpdateTabs" style="width: 100%;" data-options="fit:true">
		<c:if test="${loanBase.isRenew eq '0' or empty loanBase.isRenew }">
		<div title="申请信息"  style="width: 100%;">
			<div id="main">
				<div class="content">
					<form id="updateForm" >
						<input id="id" name="loanBase.id" type="hidden" value="${ loanBase.id}" />
						<input type="hidden" name="loanBase.loanId" value="${loanBase.loanId }" />
						<input type="hidden" name="loanBase.isRenew" value="${loanBase.isRenew }" />
						
						<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>借款信息</strong></div><hr color="#D3D3D3"/>
						<table id="loanInfo">
							<tr>
								<td>借款金额:</td>
								<td>
									<input id="loanAmt" name="loanBase.loanAmt" type="text"  data-options="required:true,min:0,precision:2" 
											class="textbox easyui-numberbox" value="${loanBase.loanAmt}"/>元
								</td>
								<td>产品:</td>
								<td><input id="product" name="loanBase.product" type="text" class="textbox easyui-combobox"
										data-options="required:true,editable:false,panelHeight:'auto'" value="${loanBase.product }"/>
								</td>
								<td>到期日期:</td>
								<td>
									<input id="endDate" name="loanBase.endDate" type="text" class="textbox easyui-datebox" 
										data-options="required:true,editable:false" value="${loanBase.endDateStr}"/>
								</td>
								<td>是否有共同借款人:</td>
								<td>
									<input name="loanBase.isTgth" type="radio" value="0" <c:if test="${loanBase.isTgth eq '0' }">checked='checked'</c:if>  checked='checked'/>无
									&nbsp;
									<input name="loanBase.isTgth" type="radio" value="1" <c:if test="${loanBase.isTgth eq '1' }">checked='checked'</c:if> />有
								</td>
							</tr>
							<tr>
								<td>借款用途:</td>
								<td>
									<input id="loanUse" name="loanBase.loanUse" type="text" class="textbox easyui-validatebox"
										data-options="required:true, validType:['length[0,200]']" value="${loanBase.loanUse}"/>
								</td>
							</tr>
						</table>
						
						<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>还款方式</strong></div><hr color="#D3D3D3"/>
					<table>	
				   <tr>
						<td>还款方式:</td>
						<td>
							<input id="retWay" name="loanBase.retWay" type="text" 
								data-options="required:true,editable:false,panelHeight:'auto'" 
								class="textbox easyui-combobox" 
								<c:if test="${empty loanBase.retWay }">value='01'</c:if><c:if test="${not empty loanBase.retWay }">value='${loanBase.retWay }'</c:if>/>
						</td>
					</tr>
					</table>
						<table id="repayInfo">
							
							
							<tr>
								<td>年化利率:</td>
								<td>
									<input id="rate" type="text" name="loanBase.rate" class="textbox easyui-numberbox" 
										data-options="required:true,min:0,max:99,precision:2" value="${loanBase.rate}"/>%
								</td>
								<td class="deRate" style="display: none;">等额利率:</td>
								<td class="deRate" style="display: none;">
									<input id="deRate" name="loanBase.deRate" type="text" class="textbox easyui-numberbox" 
										data-options="required:true,min:0,max:99,precision:2" value="${loanBase.deRate}"/>%
								</td>
								<td>计息天数:</td>
								<td><input id="inteDays" type="text" name="loanBase.inteDays" class="textbox easyui-combobox" 
										data-options="required:true,editable:false,
													panelHeight:'auto',
													textField:'keyValue',
													valueField:'keyProp',
													data:dataDictJson.inteDays" value="${loanBase.inteDays}"/>
								</td>
							</tr>
							<tr>
								<td>会员服务费:</td>
								<td><input id="memFee"  name="loanBase.memFee" type="text" class="textbox easyui-numberbox" 
									 	data-options="required:true,min:0,precision:2" value="${loanBase.memFee}"/>元
								</td>
								<td>法律服务费:</td>
								<td><input id="lawFee" name="loanBase.lawFee" type="text" class="textbox easyui-numberbox" 
										 data-options="required:true,min:0,precision:2" value="${loanBase.lawFee}"/>元
								</td>
								<td>保证金:</td>
								<td><input id="magin" name="loanBase.magin" type="text" class="textbox easyui-numberbox" 
										 data-options="required:true,min:0,precision:2" value="${loanBase.magin}"/>元
								</td>
								<td>其他费用:</td>
								<td><input id="otherFee" name="loanBase.otherFee" type="text" class="textbox easyui-numberbox" 
										 data-options="required:true,min:0,precision:2" value="${loanBase.otherFee}"/>元
								</td>
							</tr>
							<tr>
								<td>管理服务费:</td>
								<td><input id="mgrFee" name="loanBase.mgrFee" type="text" class="textbox easyui-numberbox" 
										 data-options="required:true,min:0,precision:2" value="${loanBase.mgrFee}"/>元
								</td>
								<td>他项权证费:</td>
								<td><input id="certFee" name="loanBase.certFee" type="text" class="textbox easyui-numberbox" 
										 data-options="required:true,min:0,precision:2" value="${loanBase.certFee}"/>元
								</td>
								<td>评估费:</td>
								<td><input id="evalFee" name="loanBase.evalFee" type="text" class="textbox easyui-numberbox" 
										 data-options="required:true,min:0,precision:2" value="${loanBase.evalFee}"/>元
								</td>
								<td>中介费:</td>
								<td><input id="agentFee" name="loanBase.agentFee" type="text" class="textbox easyui-numberbox" 
										 data-options="required:true,min:0,precision:2" value="${loanBase.agentFee}"/>元
								</td>
							</tr>
							<tr>
								<td>周期方案:</td>
								<td>
									<input name="loanBase.retLoanSol" type="radio" value="01" <c:if test="${loanBase.retLoanSol eq '01' }">checked='checked'</c:if> checked='checked'/>月付
									<input name="loanBase.retLoanSol" type="radio" value="02" <c:if test="${loanBase.retLoanSol eq '02' }">checked='checked'</c:if> />季付
									<input name="loanBase.retLoanSol" type="radio" value="03" <c:if test="${loanBase.retLoanSol eq '03' }">checked='checked'</c:if> />首付
									<input name="loanBase.retLoanSol" type="radio" value="04" <c:if test="${loanBase.retLoanSol eq '04' }">checked='checked'</c:if> />末付
								</td>
							</tr>
							<!-- <tr>
								<td colspan="4">
									<table id="lxde" style="display: none;">
									</table>
									
									<table id="xxhde" style="display: none;">
										
									</table>
								</td>
							</tr> -->
						</table>
						
						<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>银行卡信息</strong></div><hr color="#D3D3D3"/>
						<table id="bankInfo">
							<tr>
								<td>开户名:</td>
								<td>
									<input id="acctName" name="loanBase.acctName" type="text" class="textbox easyui-validatebox" 
										data-options="required:true,validType:['length[0,20]']" value="${loanBase.acctName}"/>
								</td>
								<td>账号:</td>
								<td><input id="acctCode" name="loanBase.acctCode" type="text" class="textbox easyui-validatebox" style="width:150px;"
										data-options="required:true" value="${loanBase.acctCode}"/>
								</td>
								<td>账户类型:</td>
								<td>
									<input id="ext2" name="loanBase.ext2" type="text" class="textbox easyui-combobox"
											data-options="required:true,editable:false,panelHeight:'auto',
														textField:'keyValue',
														valueField:'keyProp',
														data:dataDictJson.bankAccountType" value="${loanBase.ext2 }"/>
								</td>
							</tr>
							<tr>
								<td>开户行:</td>
								<td colspan="6">
									<input id="acctPrvn" name="loanBase.acctPrvn" type="text" class="textbox easyui-combobox"
										data-options="required:true,editable:false" value="${loanBase.acctPrvn}"/>省
									<input id="acctCity" name="loanBase.acctCity" type="text" class="textbox easyui-combobox" 
										data-options="required:true,editable:false" value="${loanBase.acctCity}"/>市
									<%-- <input id="acctCtry" name="loanBase.acctCtry" type="text" class="textbox easyui-combobox" 
										data-options="required:true,editable:false" value="${loanBase.acctCtry}"/>区/县 --%>
									<input id="acctBank" name="loanBase.acctBank" type="text" class="textbox easyui-combobox"
										data-options="required:true" value="${loanBase.acctBank}"/>开户行
									<input id="acctBranch" name="loanBase.acctBranch" type="text" class="textbox easyui-combobox"
										data-options="required:true,editable:true" value="${loanBase.acctBranch}" style="width: 250px;"/>支行
								</td>
							</tr>
						</table>
						
						
						<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>申请基本信息</strong></div><hr color="#D3D3D3"/>
						<table>
							<tr>
								<td>借款人类型:</td>
								<td>
									<input id="loanType" name="loanBase.loanType" type="text" 
										data-options="required:true,editable:false,panelHeight:'auto'" 
										class="textbox easyui-combobox" 
										<c:if test="${empty loanBase.loanType }">value='01'</c:if><c:if test="${not empty loanBase.loanType }">value='${loanBase.loanType }'</c:if>/>
								</td>
							</tr>
						</table>
						
						<div id="appTypeArea" style="width:100%;">
							<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>申请人信息</strong></div><hr color="#D3D3D3"/>
							<input type="hidden" name="appTypeLoan.id" value="${appId }" />
							<input type="hidden" name="appTypeLoan.loanId" value="${appTypeLoan.loanId }" />
							<table id="applyInfo">
								<tr>
									<td>姓名:</td>
									<td><input id="name" name="appTypeLoan.name" type="text" class="textbox easyui-validatebox" 
											data-options="required:true,validType:['length[0,50]']" value="${appTypeLoan.name}"/>
									</td>
									<td>性别:</td>
									<td>
										<input id="sex" name="appTypeLoan.sex" type="text" class="textbox easyui-combobox"
											data-options="required:true,editable:false,panelHeight:'auto',
														textField:'keyValue',
														valueField:'keyProp',
														data:dataDictJson.gender" value="${appTypeLoan.sex}"/>
									</td>
									<td>年龄:</td>
									<td><input id="age" name="appTypeLoan.age" type="text" class="textbox easyui-validatebox" 
											data-options="required:true,validType:['length[0,50]']" value="${appTypeLoan.age}"/>
									</td>
								</tr>
								<tr>
									<td>证件类型:</td>
									<td>
										<input id="idType" name="appTypeLoan.idType" type="text" 
											data-options="required:true,editable:false,panelHeight:'auto',
															textField:'keyValue',
															valueField:'keyProp',
															data:dataDictJson.applyIdType" 
											class="textbox easyui-combobox" value="${appTypeLoan.idType}"/>
									</td>
									<td>证件号码:</td>
									<td>
										<input id="idNo" name="appTypeLoan.idNo" type="text" class="textbox easyui-validatebox"
											data-options="required:true,validType:['length[0,30]']" value="${appTypeLoan.idNo}"/>
									</td>
									<td>证件有效期:</td>
									<td>
										<input id="validDate" name="appTypeLoan.validDate" type="text" class="textbox easyui-datebox" 
												data-options="editable:false" value="${appTypeLoan.validDateStr}"/>
									</td>
								</tr>
								<tr>
									<td>手机号:</td>
									<td>
										<input id="tel" name="appTypeLoan.tel" validType="mobile" type="text" class="textbox easyui-validatebox"  
											data-options="required:true,validType:['mobile','length[0,20]']"value="${appTypeLoan.tel}"/>
									</td>
									<td>备用手机号:</td>
									<td>
										<input id="tel2" name="appTypeLoan.tel2" validType="mobile" type="text" class="textbox easyui-validatebox"
											data-options="validType:['mobile','length[0,20]']" value="${appTypeLoan.tel2}"/>
									</td>
									<td>家庭固话:</td>
									<td>
										<input id="phone" name="appTypeLoan.phone" type="text" class="textbox easyui-validatebox"
											data-options="validType:['length[0,20]']" value="${appTypeLoan.phone}"/>
									</td>
								</tr>
								<tr>
									<td>婚姻状况:</td>
									<td>
										<input id="marriage" name="appTypeLoan.marriage" type="text" class="textbox easyui-combobox" 
											data-options="required:true,editable:false,panelHeight:'auto',
															url:'sys/datadictionary/listjason.do?keyName=marriage',
															textField:'keyValue',
															valueField:'keyProp',
															loadFilter:function(data){
																var comVal = $(this).combobox('getValue');
																if(comVal == '' || comVal == null) {
															   		var opts = $(this).combobox('options');
															   		var emptyRow = {};
																	emptyRow[opts.valueField] = '';
																	emptyRow[opts.textField] = '请选择';
																	data.unshift(emptyRow);
																	$(this).combobox('setValue','');
																}
																return data;
															}" value="${appTypeLoan.marriage}"/>
									</td>
									<td>子女信息:</td>
									<td>
										<input id="hasChil" name="appTypeLoan.hasChil" type="radio" value="0" <c:if test="${appTypeLoan.hasChil eq '0' }">checked='checked'</c:if> checked='checked' />无
										&nbsp;
										<input id="hasChil" name="appTypeLoan.hasChil" type="radio" value="1" <c:if test="${appTypeLoan.hasChil eq '1' }">checked='checked'</c:if> />有
										<input id="chilNum" name="appTypeLoan.chilNum" type="text" class="textbox easyui-validatebox" style="width: 45px;"
											data-options="validType:['length[0,2]']" value="${appTypeLoan.chilNum}"/>个	
									</td>
									<td>学历:</td>
									<td>
										<input id="edu" name="appTypeLoan.edu" type="text" class="textbox easyui-combobox" 
											data-options="required:true,editable:false,panelHeight:'auto',
														url:'sys/datadictionary/listjason.do?keyName=education',
														textField:'keyValue',
														valueField:'keyProp',
														loadFilter:function(data){
															var comVal = $(this).combobox('getValue');
															if(comVal == '' || comVal == null) {
														   		var opts = $(this).combobox('options');
														   		var emptyRow = {};
																emptyRow[opts.valueField] = '';
																emptyRow[opts.textField] = '请选择';
																data.unshift(emptyRow);
																$(this).combobox('setValue','');
															}
															return data;
														}" value="${appTypeLoan.edu}"/></td>
								</tr>
								<tr>
									<td>居住情况:</td>
									<td>
										<input id="live" name="appTypeLoan.live" type="text" class="textbox easyui-combobox"
											data-options="required:true,editable:false,panelHeight:'auto',
														url:'sys/datadictionary/listjason.do?keyName=live',
														textField:'keyValue',
														valueField:'keyProp',
														loadFilter:function(data){
															var comVal = $(this).combobox('getValue');
															if(comVal == '' || comVal == null) {
														   		var opts = $(this).combobox('options');
														   		var emptyRow = {};
																emptyRow[opts.valueField] = '';
																emptyRow[opts.textField] = '请选择';
																data.unshift(emptyRow);
																$(this).combobox('setValue','');
															}
															return data;
														}" value="${appTypeLoan.live}"/>
									</td>
									<td>本市房产情况:</td>
									<td>
										<input id="nativeHouse" name="appTypeLoan.nativeHouse" type="text" class="textbox easyui-combobox"
											data-options="required:true,editable:false,panelHeight:'auto',
														url:'sys/datadictionary/listjason.do?keyName=nativeHouse',
														textField:'keyValue',
														valueField:'keyProp',
														loadFilter:function(data){
															var comVal = $(this).combobox('getValue');
															if(comVal == '' || comVal == null) {
														   		var opts = $(this).combobox('options');
														   		var emptyRow = {};
																emptyRow[opts.valueField] = '';
																emptyRow[opts.textField] = '请选择';
																data.unshift(emptyRow);
																$(this).combobox('setValue','');
															}
															return data;
														}" value="${appTypeLoan.nativeHouse}"/>
									</td>
									<td>本地居住时间:</td>
									<td>
										<input id="nativeTime" name="appTypeLoan.nativeTime" type="text" class="textbox easyui-numberbox" value="${appTypeLoan.nativeTime}"/>年
									</td>
								</tr>
								<tr>
									<td>户籍地址:</td>
									<td colspan="6">
										<input id="homePrvn" name="appTypeLoan.homePrvn" type="text" class="textbox easyui-combobox"
											data-options="required:true,editable:false" value="${appTypeLoan.homePrvn}"/>省
										<input id="homeCity" name="appTypeLoan.homeCity" type="text" class="textbox easyui-combobox" 
											data-options="required:true,editable:false" value="${appTypeLoan.homeCity}"/>市
										<input id="homeCtry" name="appTypeLoan.homeCtry" type="text" class="textbox easyui-combobox" 
											data-options="required:true,editable:false" value="${appTypeLoan.homeCtry}"/>区/县
										<input id="homeAddr" name="appTypeLoan.homeAddr" type="text" class="textbox easyui-validatebox" style="width: 200px"
											data-options="required:true,validType:['length[0,100]']" value="${appTypeLoan.homeAddr}"/>
									</td>
								</tr>
								<tr>
									<td>现居地址:</td>
									<td colspan="6">
										<input id="nowPrvn" name="appTypeLoan.nowPrvn" type="text" class="textbox easyui-combobox"
											data-options="required:true,editable:false" value="${appTypeLoan.nowPrvn}"/>省
										<input id="nowCity" name="appTypeLoan.nowCity" type="text" class="textbox easyui-combobox" 
											data-options="required:true,editable:false" value="${appTypeLoan.nowCity}"/>市
										<input id="nowCtry" name="appTypeLoan.nowCtry" type="text" class="textbox easyui-combobox" 
											data-options="required:true,editable:false" value="${appTypeLoan.nowCtry}"/>区/县
										<input id="nowAddr" name="appTypeLoan.nowAddr" type="text" class="textbox easyui-validatebox" style="width: 200px"
											data-options="required:true,validType:['length[0,100]']" value="${appTypeLoan.nowAddr}"/>
									</td>
								</tr>
								<tr>
									<td>毕业院校:</td>
									<td colspan="3">
										<input id="school" name="appTypeLoan.school" type="text" class="textbox easyui-validatebox" style="width: 385px"
											data-options="validType:['length[0,50]']" value="${appTypeLoan.school}"/>
									</td>
									<td>毕业时间:</td>
									<td>
										<input id="eduTime" name="appTypeLoan.eduTime" type="text" class="textbox easyui-datebox"
											 data-options="editable:false" value="${appTypeLoan.eduTimeStr}"/>
									</td>
								</tr>
								<tr>
									<td>微信:</td>
									<td>
										<input id="wechat" name="appTypeLoan.wechat" type="text" class="textbox easyui-validatebox" 
											data-options="validType:['length[0,20]']" value="${appTypeLoan.wechat}"/>
									</td>
									<td>邮箱:</td>
									<td>
										<input id="email" name="appTypeLoan.email" type="text" class="textbox easyui-validatebox" 
											data-options="validType:['email','length[0,30]']" value="${appTypeLoan.email}"/>
									</td>
									<td>QQ:</td>
									<td>
										<input id="qq" name="appTypeLoan.qq" type="text" class="textbox easyui-validatebox"
											data-options="validType:['QQ','length[0,20]']" value="${appTypeLoan.qq}"/>
									</td>
									<td>邮编:</td>
									<td>
										<input id="postcode" name="appTypeLoan.postcode" type="text" class="textbox easyui-validatebox"
											data-options="validType:['ZIP','length[0,10]']" value="${appTypeLoan.postcode}"/>
									</td>
								</tr>
							</table>
							
							<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>申请人职业信息</strong></div><hr color="#D3D3D3"/>
							<table id="jobInfo">
								<tr>
									<td>单位名称:</td>
									<td colspan="3">
										<input id="companyName" name="appTypeLoan.companyName" type="text" class="textbox easyui-validatebox" style="width: 385px"
											data-options="validType:['length[0,50]']" value="${appTypeLoan.companyName}"/>
									</td>
									<td>单位类型:</td>
									<td>
										<input id="companyType" name="appTypeLoan.companyType" type="text" class="textbox easyui-combobox" 
											data-options="required:true,editable:false,panelHeight:'auto',
														url:'sys/datadictionary/listjason.do?keyName=companyType',
														textField:'keyValue',
														valueField:'keyProp',
														loadFilter:function(data){
															var comVal = $(this).combobox('getValue');
															if(comVal == '' || comVal == null) {
														   		var opts = $(this).combobox('options');
														   		var emptyRow = {};
																emptyRow[opts.valueField] = '';
																emptyRow[opts.textField] = '请选择';
																data.unshift(emptyRow);
																$(this).combobox('setValue','');
															}
															return data;
														}" value="${appTypeLoan.companyType}"/>
									</td>
									<td>部门:</td>
									<td>
										<input id="dept" name="appTypeLoan.dept" type="text" class="textbox easyui-validatebox" 
											data-options="validType:['length[0,20]']" value="${appTypeLoan.dept}"/>
									</td>
								</tr>
								<tr>
									<td>入职时间:</td>
									<td>
										<input id="inTime" name="appTypeLoan.inTime" type="text" class="textbox easyui-datebox" 
											data-options="editable:false" value="${appTypeLoan.inTimeStr}"/>
									</td>
									<td>职位:</td>
									<td>
										<input id="job" name="appTypeLoan.job" type="text" class="textbox easyui-validatebox" 
											data-options="validType:['length[0,20]']" value="${appTypeLoan.job}"/>
									</td>
									<td>单位电话:</td>
									<td>
										<input id="companyTel" name="appTypeLoan.companyTel" type="text" class="textbox easyui-validatebox" 
											data-options="validType:['length[0,20]']" value="${appTypeLoan.companyTel}"/>
									</td>
								</tr>
								<tr>
									<td>单位地址:</td>
									<td colspan="6">
										<input id="companyPrvn" name="appTypeLoan.companyPrvn" type="text" class="textbox easyui-combobox"
											data-options="editable:false" value="${appTypeLoan.companyPrvn}"/>省
										<input id="companyCity" name="appTypeLoan.companyCity" type="text" class="textbox easyui-combobox" 
											data-options="editable:false" value="${appTypeLoan.companyCity}"/>市
										<input id="companyCtry" name="appTypeLoan.companyCtry" type="text" class="textbox easyui-combobox" 
											data-options="editable:false" value="${appTypeLoan.companyCtry}"/>区/县
										<input id="companyAddr" name="appTypeLoan.companyAddr" type="text" class="textbox easyui-validatebox" style="width: 200px"
											data-options="validType:['length[0,100]']" value="${appTypeLoan.companyAddr}"/>
									</td>
								</tr>
								<tr>
									<td>年收入:</td>
									<td>
										<input id="yearIncome" name="appTypeLoan.yearIncome" type="text" class="textbox easyui-numberbox" 
											data-options="min:0,precision:2" value="${appTypeLoan.yearIncome}"/>元
									</td>
									<td>月工作收入:</td>
									<td>
										<input id="monthIncome" name="appTypeLoan.monthIncome" type="text" class="textbox easyui-numberbox" 
											data-options="min:0,precision:2" value="${appTypeLoan.monthIncome}"/>元
									</td>
									<td>月其他收入:</td>
									<td>
										<input id="monthOther" name="appTypeLoan.monthOther" type="text" class="textbox easyui-numberbox" 
											data-options="min:0,precision:2" value="${appTypeLoan.monthOther}"/>元
									</td>
								</tr>
							</table>
						</div>
						
						<div id="companyTypeArea" style="display: none;">
							<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>机构信息</strong></div><hr color="#D3D3D3"/>
							<input type="hidden" name="comTypeLoan.id" value="${appId }" />
							<input type="hidden" name="comTypeLoan.loanId" value="${comTypeLoan.loanId }" />
							<table id="orgInfo">
								<tr>
									<td>机构名称:</td>
									<td><input id="name" name="comTypeLoan.name" type="text" class="textbox easyui-validatebox" 
											data-options="required:true,validType:['length[0,50]']" value="${comTypeLoan.name}"/>
									</td>
									<td>注册时间:</td>
									<td>
										<input id="orgRegTime" name="comTypeLoan.orgRegTime" type="text" class="textbox easyui-datebox" 
										 	data-options="editable:false" value="${comTypeLoan.orgRegTime}"/>
									</td>
									<td>注册资本:</td>
									<td>
										<input id="orgRegAmt" name="comTypeLoan.orgRegAmt" type="text" class="textbox easyui-numberbox"
											data-options="min:0,precision:2" value="${comTypeLoan.orgRegAmt}"/>元
									</td>
								</tr>
								<tr>
									<td>证件类型:</td>
									<td>
										<input id="idType" type="text" name="comTypeLoan.idType"
											data-options="required:true,editable:false,panelHeight:'auto',
														textField:'keyValue',
														valueField:'keyProp',
														data:dataDictJson.companyIdType" 
											class="textbox easyui-combobox" value="${comTypeLoan.idType}"/>
									</td>
									<td>证件编码:</td>
									<td>
										<input id="idNo" name="comTypeLoan.idNo" type="text" class="textbox easyui-validatebox"
											data-options="required:true,validType:['length[0,30]']" value="${comTypeLoan.idNo}"/>
									</td>
								</tr>
								<tr>
									<td>法人姓名:</td>
									<td>
										<input id="legalName" name="comTypeLoan.legalName" type="text" class="textbox easyui-validatebox" 
											data-options="required:true,validType:['length[0,20]']" value="${comTypeLoan.legalName}"/>
									</td>
									<td>法人手机号:</td>
									<td>
										<input id="legalTel" name="comTypeLoan.legalTel" type="text" class="textbox easyui-validatebox" 
											data-options="required:true,validType:['mobile','length[0,20]']" value="${comTypeLoan.legalTel}"/>
									</td>
									<td>法人证件类型:</td>
									<td>
										<input id="legalIdType" name="comTypeLoan.legalIdType" type="text"
											data-options="required:true,editable:false,panelHeight:'auto',
															textField:'keyValue',
															valueField:'keyProp',
															data:dataDictJson.applyIdType" 
											class="textbox easyui-combobox" value="${comTypeLoan.legalIdType}"/>
									</td>
									<td>法人证件号码:</td>
									<td>
										<input id="legalIdNo" name="comTypeLoan.legalIdNo" type="text" class="textbox easyui-validatebox" 
											data-options="required:true,validType:['length[0,20]']" value="${comTypeLoan.legalIdNo}"/>
									</td>
								</tr>
								<tr>
									<td>股东姓名:</td>
									<td>
										<input id="shareName" name="comTypeLoan.shareName" type="text" class="textbox easyui-validatebox" 
											data-options="validType:['length[0,20]']" value="${comTypeLoan.shareName}"/>
									</td>
									<td>股东手机号:</td>
									<td>
										<input id="shareTel" name="comTypeLoan.shareTel" type="text" class="textbox easyui-validatebox" 
											data-options="validType:['mobile','length[0,20]']" value="${comTypeLoan.shareTel}"/>
									</td>
									<td>股东证件类型:</td>
									<td>
										<input id="shareIdType" name="comTypeLoan.shareIdType" type="text"
											data-options="editable:false,panelHeight:'auto',
															textField:'keyValue',
															valueField:'keyProp',
															data:dataDictJson.applyIdType" 
											class="textbox easyui-combobox" value="${comTypeLoan.shareIdType}"/>
									</td>
									<td>股东证件号码:</td>
									<td>
										<input id="shareIdNo" name="comTypeLoan.shareIdNo" type="text" class="textbox easyui-validatebox" 
											data-options="validType:['length[0,20]']" value="${comTypeLoan.shareIdNo}"/>
									</td>
								</tr>
								<tr>
									<td>经营年限:</td>
									<td>
										<input id="orgPeriod" name="comTypeLoan.orgPeriod" type="text" class="textbox easyui-numberbox" 
											data-options="required:true,min:0,precision:0" value="${comTypeLoan.orgPeriod}"/> 年
									</td>
									<td>年经营额:</td>
									<td>
										<input id="orgSalesAmt" name="comTypeLoan.orgSalesAmt" type="text" class="textbox easyui-numberbox" 
											data-options="required:true,min:0,precision:2" value="${comTypeLoan.orgSalesAmt}"/>元
									</td>
								</tr>
								<tr>
									<td>机构经营范围:</td>
									<td colspan="6">
										<textarea name="comTypeLoan.orgBus" class="textbox easyui-validatebox" 
											data-options="validType:['length[0,300]']" 
											style="resize: none;width:625px;height:50px!important;">${comTypeLoan.orgBus}</textarea>
									</td>
								</tr>
								
								<tr>
									<td>注册地址:</td>
									<td colspan="6">
										<input id="homePrvn" name="comTypeLoan.homePrvn" type="text" class="textbox easyui-combobox"
											data-options="required:true,editable:false" value="${comTypeLoan.homePrvn}"/>省
										<input id="homeCity" name="comTypeLoan.homeCity" type="text" class="textbox easyui-combobox" 
											data-options="required:true,editable:false" value="${comTypeLoan.homeCity}"/>市
										<input id="homeCtry" name="comTypeLoan.homeCtry" type="text" class="textbox easyui-combobox" 
											data-options="required:true,editable:false" value="${comTypeLoan.homeCtry}"/>区/县
										<input id="homeAddr" name="comTypeLoan.homeAddr" type="text" class="textbox easyui-validatebox" style="width: 200px"
											data-options="required:true,validType:['length[0,100]']" value="${comTypeLoan.homeAddr}"/>
									</td>
								</tr>
								<tr>
									<td>经营地址:</td>
									<td colspan="6">
										<input id="nowPrvn" name="comTypeLoan.nowPrvn" type="text" class="textbox easyui-combobox"
											data-options="required:true,editable:false" value="${comTypeLoan.nowPrvn}"/>省
										<input id="nowCity" name="comTypeLoan.nowCity" type="text" class="textbox easyui-combobox" 
											data-options="required:true,editable:false" value="${comTypeLoan.nowCity}"/>市
										<input id="nowCtry" name="comTypeLoan.nowCtry" type="text" class="textbox easyui-combobox" 
											data-options="required:true,editable:false" value="${comTypeLoan.nowCtry}"/>区/县
										<input id="nowAddr" name="comTypeLoan.nowAddr" type="text" class="textbox easyui-validatebox" style="width: 200px"
											data-options="required:true,validType:['length[0,100]']" value="${comTypeLoan.nowAddr}"/>
									</td>
								</tr>
							</table>
						</div>
						
						<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>常用联系人</strong>
							<span style="cursor: pointer;">
								<img src="img/addItem.gif" onclick="addObj('contacts');" />
							</span>
						</div><hr color="#D3D3D3"/>
						<input type="hidden" id="contactsIndex" value="${contacts==null?3:(fn:length(contacts)>3?fn:length(contacts):3)}" />
						<table class="datatable" id="contacts" style="width: auto;" >
							<tr>
								<th scope="col">姓名</th>
								<th scope="col">关系</th>
								<th scope="col">工作单位</th>
								<th scope="col">联系方式</th>
							</tr>
							<tr id="contact_0">
								<td>
									<input type="hidden" name="contacts[0].id" value="${contacts[0].id}" />
									<input type="hidden" name="contacts[0].loanId" value="${contacts[0].loanId}" />
									<input type="hidden" id="state" name="contacts[0].state" value="1" />
									<input id="name" name="contacts[0].name" 
										type="text" data-options="validType:['length[0,50]']" 
										class="textbox easyui-validatebox" value="${contacts[0].name}"/>
								</td>
								<td>
									<input id="relation" name="contacts[0].relation" type="text" 
										data-options="required:true,
													  validType:['length[0,10]'],
													  editable:false,
													  panelHeight:'auto',
													  url:'sys/datadictionary/listjason.do?keyName=relation',
													  textField:'keyValue',
													  valueField:'keyProp',
													  loadFilter:function(data){
															var comVal = $(this).combobox('getValue');
															if(comVal == '' || comVal == null) {
														   		var opts = $(this).combobox('options');
														   		var emptyRow = {};
																emptyRow[opts.valueField] = '';
																emptyRow[opts.textField] = '请选择';
																data.unshift(emptyRow);
																$(this).combobox('setValue','');
															}
															return data;
														}" 
										class="textbox easyui-combobox" value="${contacts[0].relation}"/> 
								</td>
								<td>
									<input id="company" name="contacts[0].company" style="width: 300px"
										type="text" data-options="validType:['length[0,50]']" 
										class="textbox easyui-validatebox" value="${contacts[0].company}"/>
								</td>
								<td>
									<input id="tel" name="contacts[0].tel" 
										type="text" data-options="validType:['length[0,50]']" 
										class="textbox easyui-validatebox" value="${contacts[0].tel}"/>
								</td>
							</tr>
							
							<tr id="contact_1">
								<td>
									<input type="hidden" name="contacts[1].id" value="${contacts[1].id}" />
									<input type="hidden" name="contacts[1].loanId" value="${contacts[1].loanId}" />
									<input type="hidden" id="state" name="contacts[1].state" value="1" />
									<input id="name" name="contacts[1].name" 
										type="text" data-options="validType:['length[0,50]']" 
										class="textbox easyui-validatebox" value="${contacts[1].name}"/>
								</td>
								<td>
									<input id="relation" name="contacts[1].relation" type="text" 
										data-options="required:true,
													  validType:['length[0,10]'],
													  editable:false,
													  panelHeight:'auto',
													  url:'sys/datadictionary/listjason.do?keyName=relation',
													  textField:'keyValue',
													  valueField:'keyProp',
													  loadFilter:function(data){
															var comVal = $(this).combobox('getValue');
															if(comVal == '' || comVal == null) {
														   		var opts = $(this).combobox('options');
														   		var emptyRow = {};
																emptyRow[opts.valueField] = '';
																emptyRow[opts.textField] = '请选择';
																data.unshift(emptyRow);
																$(this).combobox('setValue','');
															}
															return data;
														}" 
										class="textbox easyui-combobox" value="${contacts[1].relation}"/> 
								</td>
								<td>
									<input id="company" name="contacts[1].company" style="width: 300px"
										type="text" data-options="validType:['length[0,50]']" 
										class="textbox easyui-validatebox" value="${contacts[1].company}"/>
								</td>
								<td>
									<input id="tel" name="contacts[1].tel" 
										type="text" data-options="validType:['length[0,50]']" 
										class="textbox easyui-validatebox" value="${contacts[1].tel}"/>
								</td>
							</tr>
							
							<tr id="contact_2">
								<td>
									<input type="hidden" name="contacts[2].id" value="${contacts[2].id}" />
									<input type="hidden" name="contacts[2].loanId" value="${contacts[2].loanId}" />
									<input type="hidden" id="state" name="contacts[2].state" value="1" />
									<input id="name" name="contacts[2].name" 
										type="text" data-options="validType:['length[0,50]']" 
										class="textbox easyui-validatebox" value="${contacts[2].name}"/>
								</td>
								<td>
									<input id="relation" name="contacts[2].relation" type="text" 
										data-options="required:true,
													  validType:['length[0,10]'],
													  editable:false,
													  panelHeight:'auto',
													  url:'sys/datadictionary/listjason.do?keyName=relation',
													  textField:'keyValue',
													  valueField:'keyProp',
													  loadFilter:function(data){
															var comVal = $(this).combobox('getValue');
															if(comVal == '' || comVal == null) {
														   		var opts = $(this).combobox('options');
														   		var emptyRow = {};
																emptyRow[opts.valueField] = '';
																emptyRow[opts.textField] = '请选择';
																data.unshift(emptyRow);
																$(this).combobox('setValue','');
															}
															return data;
														}" 
										class="textbox easyui-combobox" value="${contacts[2].relation}"/> 
								</td>
								<td>
									<input id="company" name="contacts[2].company" style="width: 300px"
										type="text" data-options="validType:['length[0,50]']" 
										class="textbox easyui-validatebox" value="${contacts[2].company}"/>
								</td>
								<td>
									<input id="tel" name="contacts[2].tel" 
										type="text" data-options="validType:['length[0,50]']" 
										class="textbox easyui-validatebox" value="${contacts[2].tel}"/>
								</td>
							</tr>
							
							<c:forEach items="${contacts}" var="contact" varStatus="status">
								<c:if test="${status.index gt 2}">
									<tr id="contact_${status.index}">
										<td>
											<input type="hidden" name="contacts[${status.index}].id" value="${contact.id}" />
											<input type="hidden" name="contacts[${status.index}].loanId" value="${contact.loanId}" />
											<input type="hidden" id="state" name="contacts[${status.index}].state" value="1" />
											<input id="name" name="contacts[${status.index}].name" 
												type="text" data-options="required:true,validType:['length[0,50]']" 
												class="textbox easyui-validatebox" value="${contact.name}"/>
										</td>
										<td>
											<input id="relation" name="contacts[${status.index}].relation" type="text" 
												data-options="required:true,
															  validType:['length[0,10]'],
															  editable:false,
															  panelHeight:'auto',
															  url:'sys/datadictionary/listjason.do?keyName=relation',
															  textField:'keyValue',
															  valueField:'keyProp',
															  loadFilter:function(data){
																var comVal = $(this).combobox('getValue');
																if(comVal == '' || comVal == null) {
															   		var opts = $(this).combobox('options');
															   		var emptyRow = {};
																	emptyRow[opts.valueField] = '';
																	emptyRow[opts.textField] = '请选择';
																	data.unshift(emptyRow);
																	$(this).combobox('setValue','');
																}
																return data;
															}" 
												class="textbox easyui-combobox" value="${contact.relation}"/> 
										</td>
										<td>
											<input id="company" name="contacts[${status.index}].company" style="width: 300px"
												type="text" data-options="required:true,validType:['length[0,50]']" 
												class="textbox easyui-validatebox" value="${contact.company}"/>
										</td>
										<td>
											<input id="tel" name="contacts[${status.index}].tel" 
												type="text" data-options="required:true,validType:['length[0,50]']" 
												class="textbox easyui-validatebox" value="${contact.tel}"/>
											<img src="img/deleteItem2.png"  onclick="rmObj('contact_${status.index}');" />
										</td>
									</tr>
								</c:if>
							</c:forEach>

<!-- 动态添加联系人区域  -->								
<tr style="display: none;">
<td>
	<textarea id="contacts_textarea" disabled="disabled">
		<tr id="contact_contactsIndex">
			<td>
				<input type="hidden" name="contacts[contactsIndex].id" />
				<input type="hidden" name="contacts[contactsIndex].loanId" value="${loanBase.loanId}" />
				<input type="hidden" id="state" name="contacts[contactsIndex].state" value="1" />
				<input id="name" name="contacts[contactsIndex].name" 
					type="text" data-options="required:true,validType:['length[0,50]']" 
					class="textbox easyui-validatebox"/>
			</td>
			<td>
				<input id="relation" name="contacts[contactsIndex].relation" type="text" 
					data-options="required:true,
								  validType:['length[0,10]'],
								  editable:false,
								  panelHeight:'auto',
								  url:'sys/datadictionary/listjason.do?keyName=relation',
								  textField:'keyValue',
								  valueField:'keyProp',
								  loadFilter:function(data){
									var comVal = $(this).combobox('getValue');
									if(comVal == '' || comVal == null) {
								   		var opts = $(this).combobox('options');
								   		var emptyRow = {};
										emptyRow[opts.valueField] = '';
										emptyRow[opts.textField] = '请选择';
										data.unshift(emptyRow);
										$(this).combobox('setValue','');
									}
									return data;
								}" 
					class="textbox easyui-combobox"/> 
			</td>
			<td>
				<input id="company" name="contacts[contactsIndex].company" style="width: 300px"
					type="text" data-options="required:true,validType:['length[0,50]']" 
					class="textbox easyui-validatebox" />
			</td>
			<td>
				<input id="tel" name="contacts[contactsIndex].tel" 
					type="text" data-options="required:true,validType:['length[0,50]']" 
					class="textbox easyui-validatebox"/>
				<img src="img/deleteItem2.png"  onclick="rmObj('contact_contactsIndex');" />
				</td>
			</tr>
		</textarea>
	</td>
</tr>
<!-- 动态添加联系人区域 END-->
							
						</table>
						
						<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>业务员备注</strong></div><hr color="#D3D3D3"/>
						<table>
							<tr>
								<td>债权描述:</td>
								<td>
									<textarea name="saleRemark" class="textbox easyui-validatebox" 
										data-options="validType:['length[0,500]']" 
										style="resize: none;width:625px;height:50px!important;"><c:if test="${not empty appTypeLoan}">${appTypeLoan.saleRemark}</c:if>${comTypeLoan.saleRemark}</textarea>
								</td>
							</tr>
						</table>
						
						<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>操作</strong></div><hr color="#D3D3D3"/>
						<table>
							<tr>
								<td colspan=3">
									<input type="button" value="保存" class="btn" onclick="javascript:updateFunction('save');"/>&nbsp;&nbsp;
									<input type="button" value="提交" class="btn" onclick="updateFunction('submit')"/>&nbsp;&nbsp;
									<input type="button" value="客户放弃" class="btn" onclick="updateFunction('giveUp')"/>&nbsp;&nbsp;
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
		</c:if>
		
		<!-- 续贷显示 -->
		<c:if test="${loanBase.isRenew eq '1' }">
			<div title="申请信息"  style="width: 100%;">
				<div id="main">
					<div class="content">
						<form id="updateForm" >
							<input id="id" name="loanBase.id" type="hidden" value="${ loanBase.id}" />
							<input type="hidden" name="loanBase.loanId" value="${loanBase.loanId }" />
							<input type="hidden" name="loanBase.isRenew" value="${loanBase.isRenew }" />
							
							<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>续贷信息</strong></div><hr color="#D3D3D3"/>
							<table id="loanInfo">
								<tr>
									<td>续贷金额:</td>
									<td>
										<input id="loanAmt" name="loanBase.loanAmt" type="text"  data-options="required:true,min:0,precision:2" 
												class="textbox easyui-numberbox" value="${loanBase.loanAmt}"/>元
									</td>
									<td>产品:</td>
									<td><input id="product" name="loanBase.product" type="text" class="textbox easyui-combobox"
											data-options="required:true,editable:false,panelHeight:'auto'" value="${loanBase.product }"/>
									</td>
									<td>到期日期:</td>
									<td>
										<input id="endDate" name="loanBase.endDate" type="text" class="textbox easyui-datebox" 
											data-options="required:true,editable:false" value="${loanBase.endDateStr}"/>
									</td>
									<td>借款用途:</td>
									<td>
										<input id="loanUse" name="loanBase.loanUse" type="text" class="textbox easyui-validatebox"
											data-options="required:true, validType:['length[0,200]']" value="${loanBase.loanUse}"/>
									</td>
								</tr>
							</table>
							
							<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>还款方式</strong></div><hr color="#D3D3D3"/>
							<table>
							<tr>
									<td>还款方式:</td>
									<td>
										
										<input id="retWay" name="loanBase.retWay" type="text" 
											data-options="required:true,editable:false,panelHeight:'auto'" 
											class="textbox easyui-combobox" 
											<c:if test="${empty loanBase.retWay }">value='01'</c:if><c:if test="${not empty loanBase.retWay }">value='${loanBase.retWay }'</c:if>/>
									</td>
								</tr>
							</table>				
							<table id="repayInfo">
								
								
								<tr>
									<td>年化利率:</td>
									<td>
										<input id="rate" name="loanBase.rate" type="text" class="textbox easyui-numberbox" 
											data-options="required:true,min:0,max:99,precision:2" value="${loanBase.rate}"/>%
									</td>
									<td class="deRate" style="display: none;">等额利率:</td>
									<td class="deRate" style="display: none;">
										<input id="deRate" name="loanBase.deRate" type="text" class="textbox easyui-numberbox" 
											data-options="required:true,min:0,max:99,precision:2" value="${loanBase.deRate}"/>%
									</td>
									<td>计息天数:</td>
									<td><input id="inteDays" name="loanBase.inteDays" type="text" class="textbox easyui-combobox" 
											data-options="required:true,editable:false,
														panelHeight:'auto',
														textField:'keyValue',
														valueField:'keyProp',
														data:dataDictJson.inteDays" value="${loanBase.inteDays}"/>
									</td>
									<td>会员服务费:</td>
									<td><input id="memFee" name="loanBase.memFee" type="text" class="textbox easyui-numberbox" 
										 	data-options="required:true,min:0,precision:2" value="${loanBase.memFee}"/>元
									</td>
								</tr>
								<tr class="lxde">
									<td>法律服务费:</td>
									<td><input id="lawFee" name="loanBase.lawFee" type="text" class="textbox easyui-numberbox" 
											 data-options="required:true,min:0,precision:2" value="${loanBase.lawFee}"/>元
									</td>
									<td>保证金:</td>
									<td><input id="magin" name="loanBase.magin"  type="text" class="textbox easyui-numberbox" 
											 data-options="required:true,min:0,precision:2" value="${loanBase.magin}"/>元
									</td>
									<td>转贷费:</td>
									<td><input id="tranFee" name="loanBase.tranFee" type="text" class="textbox easyui-numberbox" 
											 data-options="required:true,min:0,precision:2" value="${loanBase.tranFee}"/>元
									</td>
									<td>其他费用:</td>
									<td><input id="otherFee" name="loanBase.otherFee" type="text" class="textbox easyui-numberbox" 
											 data-options="required:true,min:0,precision:2" value="${loanBase.otherFee}"/>元
									</td>
								</tr>
								<tr>
									<td>管理服务费:</td>
									<td><input id="mgrFee" name="loanBase.mgrFee" type="text" class="textbox easyui-numberbox" 
											 data-options="required:true,min:0,precision:2" value="${loanBase.mgrFee}"/>元
									</td>
									<td>他项权证费:</td>
									<td><input id="certFee" name="loanBase.certFee" type="text" class="textbox easyui-numberbox" 
											 data-options="required:true,min:0,precision:2" value="${loanBase.certFee}"/>元
									</td>
									<td>评估费:</td>
									<td><input id="evalFee" name="loanBase.evalFee" type="text" class="textbox easyui-numberbox" 
											 data-options="required:true,min:0,precision:2" value="${loanBase.evalFee}"/>元
									</td>
									<td>中介费:</td>
									<td><input id="agentFee" name="loanBase.agentFee" type="text" class="textbox easyui-numberbox" 
											 data-options="required:true,min:0,precision:2" value="${loanBase.agentFee}"/>元
									</td>
								</tr>
								<tr>
									<td>周期方案:</td>
									<td>
										<input name="loanBase.retLoanSol" type="radio" value="01" <c:if test="${loanBase.retLoanSol eq '01' }">checked='checked'</c:if> />月付
										<input name="loanBase.retLoanSol" type="radio" value="02" <c:if test="${loanBase.retLoanSol eq '02' }">checked='checked'</c:if> />季付
										<input name="loanBase.retLoanSol" type="radio" value="03" <c:if test="${loanBase.retLoanSol eq '03' }">checked='checked'</c:if> />首付
										<input name="loanBase.retLoanSol" type="radio" value="04" <c:if test="${loanBase.retLoanSol eq '04' }">checked='checked'</c:if> />末付
									</td>
								</tr>
							</table>
							
							<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>银行卡信息</strong></div><hr color="#D3D3D3"/>
							<table id="bankInfo">
								<tr>
									<td>开户名:</td>
									<td>
										<input id="acctName" name="loanBase.acctName" type="text" class="textbox easyui-validatebox" 
											data-options="required:true,validType:['length[0,20]']" value="${loanBase.acctName}"/>
									</td>
									<td>账号:</td>
									<td><input id="acctCode" name="loanBase.acctCode" type="text" class="textbox easyui-validatebox" style="width:150px;"
											data-options="required:true" value="${loanBase.acctCode}"/>
									</td>
									<td>账户类型:</td>
									<td>
										<input id="ext2" name="loanBase.ext2" type="text" class="textbox easyui-combobox"
												data-options="required:true,editable:false,panelHeight:'auto',
															textField:'keyValue',
															valueField:'keyProp',
															data:dataDictJson.bankAccountType" value="${loanBase.ext2 }"/>
									</td>
								</tr>
								<tr>
									<td>开户行:</td>
									<td colspan="6">
										<input id="acctPrvn" name="loanBase.acctPrvn" type="text" class="textbox easyui-combobox"
											data-options="required:true,editable:false" value="${loanBase.acctPrvn}"/>省
										<input id="acctCity" name="loanBase.acctCity" type="text" class="textbox easyui-combobox" 
											data-options="required:true,editable:false" value="${loanBase.acctCity}"/>市
										<%-- <input id="acctCtry" name="loanBase.acctCtry" type="text" class="textbox easyui-combobox" 
											data-options="required:true,editable:false" value="${loanBase.acctCtry}"/>区/县 --%>
										<input id="acctBank" name="loanBase.acctBank" type="text" class="textbox easyui-combobox"
											data-options="required:true" value="${loanBase.acctBank}"/>开户行
										<input id="acctBranch" name="loanBase.acctBranch" type="text" class="textbox easyui-combobox"
											data-options="required:true,editable:true" value="${loanBase.acctBranch}" style="width: 250px;"/>支行
									</td>
								</tr>
							</table>
							
							<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>业务员备注</strong></div><hr color="#D3D3D3"/>
							<table>
								<tr>
									<td>说明:</td>
									<td>
										<textarea name="loanBase.ext1" class="textbox easyui-validatebox" 
											data-options="validType:['length[0,500]']" 
											style="resize: none;width:625px;height:50px!important;">${loanBase.ext1}</textarea>
									</td>
								</tr>
							</table>
							
							<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>操作</strong></div><hr color="#D3D3D3"/>
							<table>
								<tr>
									<td colspan=3">
										<input type="button" value="保存" class="btn" onclick="javascript:updateFunction('save');"/>&nbsp;&nbsp;
										<input type="button" value="提交" class="btn" onclick="updateFunction('submit')"/>&nbsp;&nbsp;
										<input type="button" value="客户放弃" class="btn" onclick="updateFunction('giveUp')"/>&nbsp;&nbsp;
									</td>
								</tr>
							</table>
						</form>
					</div>
				</div>
			</div>
			
			<div title="原申请信息" data-options="href:'${basePath}loan/origRead.do?id=${origLoanBase.id}'" style="width: 100%"></div>
		</c:if>
		
		<div title="申请影像资料" 
		<%-- data-options="href:'${basePath}files/load.do?loanId=${loanBase.loanId}&sec=${loanBase.product }&bizKey=${loanBase.loanId}'" --%> 
			style="padding:10px">
			<c:if test="${empty sec }">
			<jsp:include page="/files/load.do">
				<jsp:param value="${loanBase.loanId }" name="loId"/>
				<jsp:param value="filesce1" name="sec"/>
				<jsp:param value="${ loanBase.loanId}" name="bizKey"/>
				<jsp:param value="0" name="opt"/>
			</jsp:include>
			</c:if>
			<c:if test="${not empty sec }">
				<jsp:include page="/files/load.do">
				<jsp:param value="${loanBase.loanId }" name="loId"/>
				<jsp:param value="${sec }" name="sec"/>
				<jsp:param value="${ loanBase.loanId}" name="bizKey"/>
				<jsp:param value="0" name="opt"/>
			</jsp:include>
			</c:if>
		</div>
		<c:if test="${loanBase.isTgth eq '1' }">
			<c:if test="${loanBase.isRenew eq '0' or empty loanBase.isRenew}">
				<div title="共同借款信息" data-options="href:'${basePath}loan/common/query.do?loanId=${loanBase.loanId}'" style="width: 100%;padding:2px""></div>
			</c:if>
			<!-- 续贷显示原来的 -->
			<c:if test="${loanBase.isRenew eq '1' }">
				<div title="共同借款信息" data-options="href:'${basePath}loan/common/query.do?loanId=${origLoanBase.loanId}'" style="width: 100%;padding:2px""></div>
			</c:if>
		</c:if>
		<div title="质/抵押物" data-options="href:'${basePath}loan/collateral/query.do?loanId=${loanBase.loanId}&origLoanId=${origLoanBase.loanId }'" style="width: 100%;padding:2px""></div>
		<div title="担保情况" data-options="href:'${basePath}loanguar/query.do?loanId=${loanBase.loanId}&origLoanId=${origLoanBase.loanId }'" style="width: 100%;padding:2px""></div>
		<div title="诉讼情况" data-options="href:'${basePath}loan/law/query.do?loanId=${loanBase.loanId}&origLoanId=${origLoanBase.loanId }'" style="width: 100%;padding:2px""></div>
		<!--  <div title="流程报告" data-options="href:'${basePath}bpm/getBpmLogs.do?bizKey=${loanBase.loanId}'" style="width: 100%;padding:10px"></div>  -->
	</div>

<script type="text/javascript">
//更新保存
function updateFunction(buttonType) {
	if("submit"==buttonType){
		var id = $("input[name='loanBase.id']").val();
		if('' == id) {
			$.messager.alert('消息','请先保存申请');
			return;
		}
		
		//验证表单验证是否通过
		if(false == $('#updateForm').form('validate') ){
			$.messager.confirm('消息', "页面有必输字段，但未填值！");
			return;
		}
		var now=new Date();
		var endDate=$("#endDate").datebox('getValue');
		var myendate=new Date(endDate);
		if(myendate<now){
			$.messager.confirm('消息',"到期日期不能早于当前日期！");
			return;
		}
	}
	
	<c:if test="${loanBase.isRenew eq '0' or empty loanBase.isRenew}">
		/* //到期日期不能早于当前日期
		var now=new Date();
		var endDate=$("#endDate").datebox('getValue');
		var myendate=new Date(endDate);
		if(myendate<now){
			$.messager.confirm('消息',"到期日期不能早于当前日期！");
			return;
		} */
		//证件有效期不能早于当前日期
		var validDate=$("#validDate").datebox('getValue');
		var myvalidate=new Date(validDate);
		if(myvalidate<now){
			$.messager.confirm('消息',"证件有效期不能早于当前日期！");
			return;
		}
		//公司注册时间不能晚于当前日期
		var orgRegTime=$("#orgRegTime").datebox('getValue');
		var myregtime=new Date(orgRegTime);
		if(myregtime>now){
			$.messager.confirm('消息',"公司注册时间不能晚于当前日期!");
			return;
		}
	</c:if>
	
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	
	//弹出异步加载 遮罩
	openMask();
	//分行信息可以保存
	$('#acctBank').combobox('setValue',$('#acctBank').combobox('getText') )
	$('#acctBranch').combobox('setValue',$('#acctBranch').combobox('getText') )
	var params = $('#updateForm').serialize();
	
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "loan/save.do",
		data : params+"&buttonType="+buttonType,
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					if(buttonType=="save") {
						location.replace("<%=basePath%>loan/update.do?id="+data.id);
						window.parent.submitForm('queryForm');
					} else {
						window.parent.removeTab();
					}
            	});
            } else {				
    			$.messager.alert('消息', data.message);
				//按钮生效
				$(".btn").removeAttr("disabled");
            }
		},
		error : function() {
			//关闭遮罩，弹出消息框
			closeMask();
			$.messager.confirm('消息', '数据加载失败,请联系系统管理员！');
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
	});
}

//返回
function back(){
	window.history.go(-1);
}

//页面加载完动作
$(document).ready(function (){
	
	// 产品
	$("#product").combobox({
		url:'sys/datadictionary/listjason.do?keyName=productType',
		textField:'keyValue',
		valueField:'keyProp',
		loadFilter:function(data){
			var comVal = $(this).combobox("getValue");
			if(comVal == '' || comVal == null) {
		   		var opts = $(this).combobox('options');
		   		var emptyRow = {};
				emptyRow[opts.valueField] = '';
				emptyRow[opts.textField] = '请选择';
				data.unshift(emptyRow);
				$(this).combobox("setValue",'');
			}
			return data;
		}
	});
	
	// 还款方式
	var retWay = $("#retWay").combobox('getValue');
	// 利息等额
	if('01' == retWay) {
		$(".deRate").hide();
		toggleValidate(".deRate",false);
	}
	if('02' == retWay) {
		$(".deRate").show();
		toggleValidate(".deRate",true);
	}
	
	var tsurl="sys/datadictionary/listjason.do?keyName=repayMethod";
	$("#retWay").combobox("clear");
	$('#retWay').combobox({
		url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		onChange: function(nVal,oVal) {
			// 利息等额
			if('01' == nVal) {
				$(".deRate").hide();
				toggleValidate(".deRate",false);
				resetElement("#repayInfo");
				$('input:radio:first').attr('checked', 'checked');
			}
			
			// 利息先部分后等额
			if('02' == nVal) {
				$(".deRate").show();
				toggleValidate(".deRate",true);
				resetElement("#repayInfo");
				$('input:radio:first').attr('checked', 'checked');
			}
		}/* ,
		loadFilter:function(data){
			var comVal = $(this).combobox("getValue");
			if(comVal == '' || comVal == null) {
		   		var opts = $(this).combobox('options');
		   		var emptyRow = {};
				emptyRow[opts.valueField] = '';
				emptyRow[opts.textField] = '请选择';
				data.unshift(emptyRow);
				$(this).combobox("setValue",'');
			}
			return data;
		} */
	});
	
	<c:if test="${loanBase.isRenew eq '0' or empty loanBase.isRenew}">
		$("#loanType").combobox("clear");
		$('#loanType').combobox({
			valueField:'keyProp', 
			textField:'keyValue',
			data:dataDictJson.loanType,
			onChange: function(nVal,oVal) {
				// 个人
				if('01' == nVal) {
					$("#appTypeArea").show();
					$("#companyTypeArea").hide();
					toggleValidate("#appTypeArea",true);
					toggleValidate("#companyTypeArea",false);
					resetElement("#companyTypeArea");
				}
				
				// 公司
				if('02' == nVal) {
					$("#companyTypeArea").show();
					$("#appTypeArea").hide();
					toggleValidate("#companyTypeArea",true);
					toggleValidate("#appTypeArea",false);
					resetElement("#appTypeArea");
					initAddr("orgInfo");
				}
			}/* ,
			loadFilter:function(data){
				var comVal = $(this).combobox("getValue");
				if(comVal == '' || comVal == null) {
			   		var opts = $(this).combobox('options');
			   		var emptyRow = {};
					emptyRow[opts.valueField] = '';
					emptyRow[opts.textField] = '请选择';
					data.unshift(emptyRow);
					$(this).combobox("setValue",'');
				}
				return data;
			} */
		});
		
		// 申请类型
		var loanType = $("#loanType").combobox('getValue');
		// 个人
		if('01' == loanType) {
			$("#appTypeArea").show();
			$("#companyTypeArea").hide();
			toggleValidate("#companyTypeArea",false);
			
			initAddr("applyInfo");
			
			// 单位地址-省
			var provinceUrl = "sys/datadictionary/listjason.do?keyName=province";
			$("#companyPrvn").combobox("clear");
			$('#companyPrvn').combobox({
				url: provinceUrl,
				valueField: 'keyProp',
				textField: 'keyValue',
				onChange: function(newValue, oldValue){
			        $('#companyCity').combobox('clear');
			        $('#companyCtry').combobox('clear');
			        var cityUrl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
			        $('#companyCity').combobox('reload',cityUrl); 
		    	},
		    	loadFilter:function(data){
					var comVal = $(this).combobox("getValue");
					if(comVal == '' || comVal == null) {
				   		var opts = $(this).combobox('options');
				   		var emptyRow = {};
						emptyRow[opts.valueField] = '';
						emptyRow[opts.textField] = '请选择';
						data.unshift(emptyRow);
						$(this).combobox("setValue",'');
					}
					return data;
				}
			});
			
			// 单位地址-市
			var homePrvn = $('#companyPrvn').combobox('getValue');
			var cityUrl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(homePrvn);
			$("#companyCity").combobox("clear");
			$('#companyCity').combobox({
				url: cityUrl,
				valueField: 'keyProp',
				textField: 'keyValue',
				onChange: function(newValue, oldValue){
		            $('#companyCtry').combobox('clear');
		            var countyUrl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
		            $('#companyCtry').combobox('reload',countyUrl); 
				}
			});
			
			// 单位地址-区/县
			var homeCity = $('#companyCity').combobox('getValue');
			var countyUrl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(homeCity);
			$("#companyCtry").combobox("clear");
			$('#companyCtry').combobox({
				url: countyUrl, 
				valueField: 'keyProp',
				textField: 'keyValue'
			});
		}
		
		// 公司
		if('02' == loanType) {
			$("#companyTypeArea").show();
			$("#appTypeArea").hide();
			toggleValidate("#appTypeArea",false);
			initAddr("orgInfo");
		}
	</c:if>
	
	initAcct();
}); 

</script>

<script type="text/javascript">
	function initAcct(){
		// 开户行省
		var bankPrv = "sys/datadictionary/listjason.do?keyName=province";
		$("#bankInfo").find("#acctPrvn").combobox('clear');
		$("#bankInfo").find("#acctPrvn").combobox({
			url: bankPrv,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
		        $("#bankInfo").find('#acctCity').combobox('clear');
		       /*  $("#bankInfo").find('#acctCtry').combobox('clear');  */
		       $("#bankInfo").find("#acctBank").combobox("clear");
		       $("#bankInfo").find("#acctBranch").combobox("clear");
		        var cUrl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
		        $("#bankInfo").find('#acctCity').combobox('reload',cUrl);
	    	},
	    	loadFilter:function(data){
				var comVal = $(this).combobox("getValue");
				if(comVal == '' || comVal == null) {
			   		var opts = $(this).combobox('options');
			   		var emptyRow = {};
					emptyRow[opts.valueField] = '';
					emptyRow[opts.textField] = '请选择';
					data.unshift(emptyRow);
					$(this).combobox("setValue",'');
				}
				return data;
			}
		});
		
		// 开户行市
		var bankPrvVal = $("#bankInfo").find('#acctPrvn').combobox('getValue');
		var bankCity = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(bankPrvVal);
		$("#bankInfo").find("#acctCity").combobox("clear");
		$("#bankInfo").find('#acctCity').combobox({
			url: bankCity,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
	            /* $("#bankInfo").find('#acctCtry').combobox('clear');
	            var cUrl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
	            $("#bankInfo").find('#acctCtry').combobox('reload',cUrl);  */
				$("#bankInfo").find("#acctBank").combobox("clear");
			    $("#bankInfo").find("#acctBranch").combobox("clear");
			}
		});
		
		
		// 开户行区县
		var bankCityVal = $("#bankInfo").find('#acctCity').combobox('getValue');
		/* var bankCtry = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(bankCityVal);
		$("#bankInfo").find("#acctCtry").combobox("clear");
		$("#bankInfo").find('#acctCtry').combobox({
			url: bankCtry, 
			valueField: 'keyProp',
			textField: 'keyValue'
		});	 */
		
		// 银行
		var bank = "sys/datadictionary/listjason.do?keyName=bank";
		$("#bankInfo").find("#acctBank").combobox("clear");
		$("#bankInfo").find('#acctBank').combobox({
			url: bank, 
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
	            var bankProvince = $("#bankInfo").find('#acctPrvn').combobox('getValue');
	            var bankCity = $("#bankInfo").find('#acctCity').combobox('getValue');
				$("#bankInfo").find('#acctBranch').combobox('clear');
	            var cUrl = "common/listBranchBank.do?province="+encodeURI(bankProvince)+"&city="+encodeURI(bankCity)+"&bank_name="+ encodeURI(newValue);
	            $("#bankInfo").find('#acctBranch').combobox({
					url: cUrl, 
					valueField: 'keyProp',
					textField: 'keyValue',
					filter: function (q, row) {
						var opts = $(this).combobox('options');
						return row[opts.textField].indexOf(q) == 0;
		            }
				});
			}
		});	
		
		if(bankPrvVal != '' && bankCityVal != '' && bankVal != '') {
			// 支行
			var bankVal = $("#bankInfo").find('#acctBank').combobox('getValue');
			var branch = "common/listBranchBank.do?province="+encodeURI(bankPrvVal)+"&city="+encodeURI(bankCityVal)+"&bank_name="+encodeURI(bankVal);
			$("#bankInfo").find("#acctBranch").combobox("clear");
			$("#bankInfo").find('#acctBranch').combobox({
				url: branch, 
				valueField: 'keyProp',
				textField: 'keyValue',
				filter: function (q, row) {
					var opts = $(this).combobox('options');
					return row[opts.textField].indexOf(q) == 0;
	            }
			});
		}
	}
</script>

<script type="text/javascript">
	function initAddr(type) {
		// 户籍地址-省
		var provinceUrl = "sys/datadictionary/listjason.do?keyName=province";
		$("#"+type).find("#homePrvn").combobox("clear");
		$("#"+type).find('#homePrvn').combobox({
			url: provinceUrl,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
		        $("#"+type).find('#homeCity').combobox('clear');
		        $("#"+type).find('#homeCtry').combobox('clear');
		        var cityUrl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
		        $("#"+type).find('#homeCity').combobox('reload',cityUrl); 
	    	},
	    	loadFilter:function(data){
				var comVal = $(this).combobox("getValue");
				if(comVal == '' || comVal == null) {
			   		var opts = $(this).combobox('options');
			   		var emptyRow = {};
					emptyRow[opts.valueField] = '';
					emptyRow[opts.textField] = '请选择';
					data.unshift(emptyRow);
					$(this).combobox("setValue",'');
				}
				return data;
			}
		});
		
		// 户籍地址-市
		var homePrvn = $("#"+type).find('#homePrvn').combobox('getValue');
		var cityUrl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(homePrvn);
		$("#"+type).find("#homeCity").combobox("clear");
		$("#"+type).find('#homeCity').combobox({
			url: cityUrl,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
	            $("#"+type).find('#homeCtry').combobox('clear');
	            var countyUrl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
	            $("#"+type).find('#homeCtry').combobox('reload',countyUrl); 
			}
		});
		
		// 户籍地址-区/县
		var homeCity = $("#"+type).find('#homeCity').combobox('getValue');
		var countyUrl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(homeCity);
		$("#"+type).find("#homeCtry").combobox("clear");
		$("#"+type).find('#homeCtry').combobox({
			url: countyUrl, 
			valueField: 'keyProp',
			textField: 'keyValue'
		});	
		
		// 现居地址-省
		var provinceUrl = "sys/datadictionary/listjason.do?keyName=province";
		$("#"+type).find("#nowPrvn").combobox("clear");
		$("#"+type).find('#nowPrvn').combobox({
			url: provinceUrl,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
		        $("#"+type).find('#nowCity').combobox('clear');
		        $("#"+type).find('#nowCtry').combobox('clear');
		        var cityUrl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
		        $("#"+type).find('#nowCity').combobox('reload',cityUrl); 
	    	},
	    	loadFilter:function(data){
				var comVal = $(this).combobox("getValue");
				if(comVal == '' || comVal == null) {
			   		var opts = $(this).combobox('options');
			   		var emptyRow = {};
					emptyRow[opts.valueField] = '';
					emptyRow[opts.textField] = '请选择';
					data.unshift(emptyRow);
					$(this).combobox("setValue",'');
				}
				return data;
			}
		});
		
		// 现居地址-市
		var homePrvn = $("#"+type).find('#nowPrvn').combobox('getValue');
		var cityUrl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(homePrvn);
		$("#"+type).find("#nowCity").combobox("clear");
		$("#"+type).find('#nowCity').combobox({
			url: cityUrl,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
	            $("#"+type).find('#nowCtry').combobox('clear');
	            var countyUrl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
	            $("#"+type).find('#nowCtry').combobox('reload',countyUrl); 
			}
		});
		
		// 现居地址-区/县
		var homeCity = $("#"+type).find('#nowCity').combobox('getValue');
		var countyUrl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(homeCity);
		$("#"+type).find("#nowCtry").combobox("clear");
		$("#"+type).find('#nowCtry').combobox({
			url: countyUrl, 
			valueField: 'keyProp',
			textField: 'keyValue'
		});
	}
</script>

<c:if test="${loanBase.isRenew eq '0' or empty loanBase.isRenew}">
<script type="text/javascript" >
	//添加对象
	function addObj(key){
		var html=$("#"+key+"_textarea").val();
		var keyIndex=key+"Index";
		var index=$("#"+keyIndex).val();					//得到索引值
		html=html.replace(eval('/'+keyIndex+'/gm'),index);	//把索引占位符 替换
		var newDriver=$(html);								//转换成 JQuery对象
		newDriver.appendTo("#"+key);						//添加到对应位置
		$.parser.parse(newDriver);							//初始化 EasyUI
		if(key=="contacts"){
			initRelation("contact_"+index);
		}
		
		$("#"+keyIndex).val(++index);						//索引递增
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
			url:'sys/datadictionary/listjason.do?keyName=relation',
			valueField:'keyProp', 
			textField:'keyValue',
		});	
	}
</script>
</c:if>
<script type="text/javascript" >
openMask();
$(window).load(function (){
	closeMask();
});

function refreshTab(tabs) {
	var currTab =  self.$('#'+tabs).tabs('getSelected'); //获得当前tab
    var url = $(currTab.panel('options').content).attr('src');
    var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
    self.$('#'+tabs).tabs('update', {
      tab : currTab,
      options : {
       content : content,
       //closable:true,  
       fit:true,  
       selected:true 
      }
     });
}
</script>

<script language="javascript">
		//处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外
	    function banBackSpace(e){   
	        var ev = e || window.event;//获取event对象   
	        var obj = ev.target || ev.srcElement;//获取事件源   
	        var t = obj.type || obj.getAttribute('type');//获取事件源类型  
	        //获取作为判断条件的事件类型
	        var vReadOnly = obj.getAttribute('readonly');
	        //处理null值情况
	        vReadOnly = (vReadOnly == "") ? false : vReadOnly;
	        //当敲Backspace键时，事件源类型为密码或单行、多行文本的，
	        //并且readonly属性为true或enabled属性为false的，则退格键失效
	        var flag1=(ev.keyCode == 8 && (t=="password" || t=="text" || t=="textarea") 
	                    && vReadOnly=="readonly")?true:false;
	        //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
	        var flag2=(ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea")
	                    ?true:false;        
	        
	        //判断
	        if(flag2){
	            return false;
	        }
	        if(flag1){   
	            return false;   
	        }   
	    }

		window.onload=function(){
		    //禁止后退键 作用于Firefox、Opera
		    document.onkeypress=banBackSpace;
		    //禁止后退键  作用于IE、Chrome
		    document.onkeydown=banBackSpace;
		}
	</script> 
</body>
</html>


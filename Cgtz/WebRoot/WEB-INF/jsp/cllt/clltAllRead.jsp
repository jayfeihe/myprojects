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
	<div class="easyui-tabs" id="appReadTabs" style="width: 100%;" data-options="fit:true">
		<div title="申请信息"  style="width: 100%;">
			<div id="main">
				<div class="content">
					<form id="updateForm" >
						<input id="id" name="loanBase.id" type="hidden" value="${ loanBase.id}" />
						<input type="hidden" name="loanBase.loanId" value="${loanBase.loanId }" />
						
						<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>借款信息</strong></div><hr color="#D3D3D3"/>
						<table id="loanInfo">
							<tr>
								<td>借款金额:</td>
								<td>
									<input id="loanAmt" name="loanBase.loanAmt" type="text" readonly="readonly"  data-options="min:0,precision:2" 
											class="textbox easyui-numberbox" value="${loanBase.loanAmt}"/>元
								</td>
								<td>产品:</td>
								<td><input id="product" name="loanBase.product" type="text" readonly="readonly" class="textbox easyui-combobox"
										data-options="editable:false,panelHeight:'auto'" value="${loanBase.product }"/>
								</td>
								<td>到期日期:</td>
								<td>
									<input id="endDate" name="loanBase.endDate" type="text" readonly="readonly" class="textbox easyui-datebox" 
										data-options="editable:false" value="${loanBase.endDateStr}"/>
								</td>
								<td>是否有共同借款人:</td>
								<td>
									<input name="loanBase.isTgth" type="radio" disabled="disabled" value="0" <c:if test="${loanBase.isTgth eq '0' }">checked='checked'</c:if> />无
									&nbsp;
									<input name="loanBase.isTgth" type="radio" disabled="disabled" value="1" <c:if test="${loanBase.isTgth eq '1' }">checked='checked'</c:if> />有
								</td>
							</tr>
							<tr>
								<td>借款用途:</td>
								<td>
									<input id="loanUse" name="loanBase.loanUse" type="text" readonly="readonly" class="textbox easyui-validatebox"
										data-options=" validType:['length[0,200]']" value="${loanBase.loanUse}"/>
								</td>
							</tr>
						</table>
						
						<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>还款方式</strong></div><hr color="#D3D3D3"/>
						<table id="repayInfo">
							<tr>
								<td>还款方式:</td>
								<td>
									<!-- <input type="hidden" name="loanBase.rate"/>
									<input type="hidden" name="loanBase.inteDays"/>
									<input type="hidden" name="loanBase.memFee" />
									<input type="hidden" name="loanBase.lawFee" />
									<input type="hidden" name="loanBase.otherFee"/> -->
									
									<input id="retWay" name="loanBase.retWay" type="text" readonly="readonly" 
										data-options="editable:false,panelHeight:'auto'" 
										class="textbox easyui-combobox" value="${loanBase.retWay}"/>
								</td>
							</tr>
							
							<tr>
								<td>年化利率:</td>
								<td>
									<input id="rate" type="text" readonly="readonly" class="textbox easyui-numberbox" 
										data-options="min:0,precision:2" value="${loanBase.rate}"/>%
								</td>
								
								<c:if test="${loanBase.retWay eq '02'}">
									<td>等额利率:</td>
									<td>
										<input id="deRate" name="loanBase.deRate" type="text" class="textbox easyui-numberbox"  readonly="readonly"
											data-options="min:0,precision:2" value="${loanBase.deRate}"/>%
									</td>
							</c:if>
								
								<td>计息天数:</td>
								<td><input id="inteDays" type="text" readonly="readonly" class="textbox easyui-combobox" 
										data-options="editable:false,
													panelHeight:'auto',
													textField:'keyValue',
													valueField:'keyProp',
													data:dataDictJson.inteDays" value="${loanBase.inteDays}"/>
								</td>
								<td>会员服务费:</td>
								<td><input id="memFee" type="text" readonly="readonly" class="textbox easyui-numberbox" 
									 	data-options="min:0,precision:2" value="${loanBase.memFee}"/>元
								</td>
							</tr>
							<tr>
								<td>法律服务费:</td>
								<td><input id="lawFee" type="text" readonly="readonly" class="textbox easyui-numberbox" 
										 data-options="min:0,precision:2" value="${loanBase.lawFee}"/>元
								</td>
								<td>其他费用:</td>
								<td><input id="otherFee" type="text" readonly="readonly" class="textbox easyui-numberbox" 
										 data-options="min:0,precision:2" value="${loanBase.otherFee}"/>元
								</td>
							</tr>
							<tr>
								<td>周期方案:</td>
								<td>
									<input name="loanBase.retLoanSol" type="radio" disabled="disabled" value="01" <c:if test="${loanBase.retLoanSol eq '01' }">checked='checked'</c:if> />月付
									<input name="loanBase.retLoanSol" type="radio" disabled="disabled" value="02" <c:if test="${loanBase.retLoanSol eq '02' }">checked='checked'</c:if> />季付
									<input name="loanBase.retLoanSol" type="radio" disabled="disabled" value="03" <c:if test="${loanBase.retLoanSol eq '03' }">checked='checked'</c:if> />首付
									<input name="loanBase.retLoanSol" type="radio" disabled="disabled" value="04" <c:if test="${loanBase.retLoanSol eq '04' }">checked='checked'</c:if> />末付
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
									<input id="acctName" name="loanBase.acctName" type="text" class="textbox easyui-validatebox" readonly="readonly"
										data-options="validType:['length[0,20]']" value="${loanBase.acctName}"/>
								</td>
								<td>账号:</td>
								<td><input id="acctCode" name="loanBase.acctCode" type="text" class="textbox easyui-numberbox" readonly="readonly"
										 value="${loanBase.acctCode}"/>
								</td>
							</tr>
							<tr>
								<td>开户行:</td>
								<td colspan="6">
									<input id="acctPrvn" name="loanBase.acctPrvn" type="text" class="textbox easyui-combobox" readonly="readonly"
										data-options="editable:false" value="${loanBase.acctPrvn}"/>省
									<input id="acctCity" name="loanBase.acctCity" type="text" class="textbox easyui-combobox" readonly="readonly"
										data-options="editable:false" value="${loanBase.acctCity}"/>市
									<input id="acctCtry" name="loanBase.acctCtry" type="text" class="textbox easyui-combobox" readonly="readonly"
										data-options="editable:false" value="${loanBase.acctCtry}"/>区/县
									<input id="acctBank" name="loanBase.acctBank" type="text" class="textbox easyui-combobox" readonly="readonly" 
										data-options="editable:false" value="${loanBase.acctBank}"/>开户行
									<input id="acctBranch" name="loanBase.acctBranch" type="text" class="textbox easyui-combobox" readonly="readonly"
										data-options="editable:true" value="${loanBase.acctBranch}" style="width: 250px;"/>支行
								</td>
							</tr>
						</table>
						
						
						<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>申请基本信息</strong></div><hr color="#D3D3D3"/>
						<table>
							<tr>
								<td>借款人类型:</td>
								<td>
									<input id="loanType" name="loanBase.loanType" type="text" readonly="readonly" 
										data-options="editable:false,panelHeight:'auto'" 
										class="textbox easyui-combobox" value="${loanBase.loanType}"/>
								</td>
							</tr>
						</table>
						
						<div id="appTypeArea" style="width:100%;display: none;">
							<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>申请人信息</strong></div><hr color="#D3D3D3"/>
							<input type="hidden" name="appTypeLoan.id" value="${appId }" />
							<input type="hidden" name="appTypeLoan.loanId" value="${appTypeLoan.loanId }" />
							<table id="applyInfo">
								<tr>
									<td>姓名:</td>
									<td><input id="name" name="appTypeLoan.name" type="text" readonly="readonly" class="textbox easyui-validatebox" 
											data-options="validType:['length[0,50]']" value="${appTypeLoan.name}"/>
									</td>
									<td>性别:</td>
									<td>
										<input id="sex" name="appTypeLoan.sex" type="text" readonly="readonly" class="textbox easyui-combobox"
											data-options="editable:false,panelHeight:'auto',
														textField:'keyValue',
														valueField:'keyProp',
														data:dataDictJson.gender" value="${appTypeLoan.sex}"/></td>
									<td>年龄:</td>
									<td><input id="age" name="appTypeLoan.age" type="text" class="textbox easyui-validatebox" readonly="readonly" 
											data-options="validType:['length[0,50]']" value="${appTypeLoan.age}"/>
									</td>
								</tr>
								<tr>
									<td>证件类型:</td>
									<td>
										<input id="idType" name="appTypeLoan.idType" type="text" readonly="readonly" 
											data-options="editable:false,panelHeight:'auto'," 
											class="textbox easyui-combobox" value="${appTypeLoan.idType}"/>
									</td>
									<td>证件号码:</td>
									<td>
										<input id="idNo" name="appTypeLoan.idNo" type="text" readonly="readonly" class="textbox easyui-validatebox"
											data-options="validType:['length[0,30]']" value="${appTypeLoan.idNo}"/>
									</td>
									<td>证件有效期:</td>
									<td>
										<input id="validDate" name="appTypeLoan.validDate" type="text" readonly="readonly" class="textbox easyui-datebox" 
												data-options="editable:false" value="${appTypeLoan.validDateStr}"/>
									</td>
								</tr>
								<tr>
									<td>手机号:</td>
									<td>
										<input id="tel" name="appTypeLoan.tel" type="text" readonly="readonly" class="textbox easyui-validatebox"  
											data-options="validType:['mobile','length[0,20]']"value="${appTypeLoan.tel}"/>
									</td>
									<td>备用手机号:</td>
									<td>
										<input id="tel2" name="appTypeLoan.tel2" type="text" readonly="readonly" class="textbox easyui-validatebox"
											data-options="validType:['mobile','length[0,20]']" value="${appTypeLoan.tel2}"/>
									</td>
									<td>家庭固话:</td>
									<td>
										<input id="phone" name="appTypeLoan.phone" type="text" readonly="readonly" class="textbox easyui-validatebox"
											data-options="validType:['length[0,20]']" value="${appTypeLoan.phone}"/>
									</td>
								</tr>
								<tr>
									<td>婚姻状况:</td>
									<td>
										<input id="marriage" name="appTypeLoan.marriage" type="text" readonly="readonly" class="textbox easyui-combobox" 
											data-options="editable:false,panelHeight:'auto',
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
										<input id="hasChil" name="appTypeLoan.hasChil" type="radio" disabled="disabled" value="0" <c:if test="${appTypeLoan.hasChil eq '0' }">checked='checked'</c:if> />无
										&nbsp;
										<input id="hasChil" name="appTypeLoan.hasChil" type="radio" disabled="disabled" value="1" <c:if test="${appTypeLoan.hasChil eq '1' }">checked='checked'</c:if> />有
										<input id="chilNum" name="appTypeLoan.chilNum" type="text" readonly="readonly" class="textbox easyui-validatebox" style="width: 45px;"
											data-options="validType:['length[0,2]']" value="${appTypeLoan.chilNum}"/>个	
									</td>
									<td>学历:</td>
									<td>
										<input id="edu" name="appTypeLoan.edu" type="text" readonly="readonly" class="textbox easyui-combobox" 
											data-options="editable:false,panelHeight:'auto',
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
										<input id="live" name="appTypeLoan.live" type="text" readonly="readonly" class="textbox easyui-combobox"
											data-options="editable:false,panelHeight:'auto',
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
										<input id="nativeHouse" name="appTypeLoan.nativeHouse" type="text" readonly="readonly" class="textbox easyui-combobox"
											data-options="editable:false,panelHeight:'auto',
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
										<input id="nativeTime" name="appTypeLoan.nativeTime" type="text" readonly="readonly" class="textbox easyui-numberbox" value="${appTypeLoan.nativeTime}"/>年
									</td>
								</tr>
								<tr>
									<td>户籍地址:</td>
									<td colspan="6">
										<input id="homePrvn" name="appTypeLoan.homePrvn" type="text" readonly="readonly" class="textbox easyui-combobox"
											data-options="editable:false" value="${appTypeLoan.homePrvn}"/>省
										<input id="homeCity" name="appTypeLoan.homeCity" type="text" readonly="readonly" class="textbox easyui-combobox" 
											data-options="editable:false" value="${appTypeLoan.homeCity}"/>市
										<input id="homeCtry" name="appTypeLoan.homeCtry" type="text" readonly="readonly" class="textbox easyui-combobox" 
											data-options="editable:false" value="${appTypeLoan.homeCtry}"/>区/县
										<input id="homeAddr" name="appTypeLoan.homeAddr" type="text" readonly="readonly" class="textbox easyui-validatebox" style="width: 200px"
											data-options="validType:['length[0,100]']" value="${appTypeLoan.homeAddr}"/>
									</td>
								</tr>
								<tr>
									<td>现居地址:</td>
									<td colspan="6">
										<input id="nowPrvn" name="appTypeLoan.nowPrvn" type="text" readonly="readonly" class="textbox easyui-combobox"
											data-options="editable:false" value="${appTypeLoan.nowPrvn}"/>省
										<input id="nowCity" name="appTypeLoan.nowCity" type="text" readonly="readonly" class="textbox easyui-combobox" 
											data-options="editable:false" value="${appTypeLoan.nowCity}"/>市
										<input id="nowCtry" name="appTypeLoan.nowCtry" type="text" readonly="readonly" class="textbox easyui-combobox" 
											data-options="editable:false" value="${appTypeLoan.nowCtry}"/>区/县
										<input id="nowAddr" name="appTypeLoan.nowAddr" type="text" readonly="readonly" class="textbox easyui-validatebox" style="width: 200px"
											data-options="validType:['length[0,100]']" value="${appTypeLoan.nowAddr}"/>
									</td>
								</tr>
								<tr>
									<td>毕业院校:</td>
									<td colspan="3">
										<input id="school" name="appTypeLoan.school" type="text" readonly="readonly" class="textbox easyui-validatebox" style="width: 385px"
											data-options="validType:['length[0,50]']" value="${appTypeLoan.school}"/>
									</td>
									<td>毕业时间:</td>
									<td>
										<input id="eduTime" name="appTypeLoan.eduTime" type="text" readonly="readonly" class="textbox easyui-datebox"
											 data-options="editable:false" value="${appTypeLoan.eduTimeStr}"/>
									</td>
								</tr>
								<tr>
									<td>微信:</td>
									<td>
										<input id="wechat" name="appTypeLoan.wechat" type="text" readonly="readonly" class="textbox easyui-validatebox" 
											data-options="validType:['length[0,20]']" value="${appTypeLoan.wechat}"/>
									</td>
									<td>邮箱:</td>
									<td>
										<input id="email" name="appTypeLoan.email" type="text" readonly="readonly" class="textbox easyui-validatebox" 
											data-options="validType:['email','length[0,30]']" value="${appTypeLoan.email}"/>
									</td>
									<td>QQ:</td>
									<td>
										<input id="qq" name="appTypeLoan.qq" type="text" readonly="readonly" class="textbox easyui-validatebox"
											data-options="validType:['QQ','length[0,20]']" value="${appTypeLoan.qq}"/>
									</td>
									<td>邮编:</td>
									<td>
										<input id="postcode" name="appTypeLoan.postcode" type="text" readonly="readonly" class="textbox easyui-validatebox"
											data-options="validType:['ZIP','length[0,10]']" value="${appTypeLoan.postcode}"/>
									</td>
								</tr>
							</table>
							
							<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>申请人职业信息</strong></div><hr color="#D3D3D3"/>
							<table id="jobInfo">
								<tr>
									<td>单位名称:</td>
									<td colspan="3">
										<input id="companyName" name="appTypeLoan.companyName" type="text" readonly="readonly" class="textbox easyui-validatebox" style="width: 385px"
											data-options="validType:['length[0,50]']" value="${appTypeLoan.companyName}"/>
									</td>
									<td>单位类型:</td>
									<td>
										<input id="companyType" name="appTypeLoan.companyType" type="text" readonly="readonly" class="textbox easyui-combobox" 
											data-options="editable:false,panelHeight:'auto',
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
										<input id="dept" name="appTypeLoan.dept" type="text" readonly="readonly" class="textbox easyui-validatebox" 
											data-options="validType:['length[0,20]']" value="${appTypeLoan.dept}"/>
									</td>
								</tr>
								<tr>
									<td>入职时间:</td>
									<td>
										<input id="inTime" name="appTypeLoan.inTime" type="text" readonly="readonly" class="textbox easyui-datebox" 
											data-options="editable:false" value="${appTypeLoan.inTimeStr}"/>
									</td>
									<td>职位:</td>
									<td>
										<input id="job" name="appTypeLoan.job" type="text" readonly="readonly" class="textbox easyui-validatebox" 
											data-options="validType:['length[0,20]']" value="${appTypeLoan.job}"/>
									</td>
									<td>单位电话:</td>
									<td>
										<input id="companyTel" name="appTypeLoan.companyTel" type="text" readonly="readonly" class="textbox easyui-validatebox" 
											data-options="validType:['length[0,20]']" value="${appTypeLoan.companyTel}"/>
									</td>
								</tr>
								<tr>
									<td>单位地址:</td>
									<td colspan="6">
										<input id="companyPrvn" name="appTypeLoan.companyPrvn" type="text" readonly="readonly" class="textbox easyui-combobox"
											data-options="editable:false" value="${appTypeLoan.companyPrvn}"/>省
										<input id="companyCity" name="appTypeLoan.companyCity" type="text" readonly="readonly" class="textbox easyui-combobox" 
											data-options="editable:false" value="${appTypeLoan.companyCity}"/>市
										<input id="companyCtry" name="appTypeLoan.companyCtry" type="text" readonly="readonly" class="textbox easyui-combobox" 
											data-options="editable:false" value="${appTypeLoan.companyCtry}"/>区/县
										<input id="companyAddr" name="appTypeLoan.companyAddr" type="text" readonly="readonly" class="textbox easyui-validatebox" style="width: 200px"
											data-options="validType:['length[0,100]']" value="${appTypeLoan.companyAddr}"/>
									</td>
								</tr>
								<tr>
									<td>年收入:</td>
									<td>
										<input id="yearIncome" name="appTypeLoan.yearIncome" type="text" readonly="readonly" class="textbox easyui-numberbox" 
											data-options="min:0,precision:2" value="${appTypeLoan.yearIncome}"/>元
									</td>
									<td>月工作收入:</td>
									<td>
										<input id="monthIncome" name="appTypeLoan.monthIncome" type="text" readonly="readonly" class="textbox easyui-numberbox" 
											data-options="min:0,precision:2" value="${appTypeLoan.monthIncome}"/>元
									</td>
									<td>月其他收入:</td>
									<td>
										<input id="monthOther" name="appTypeLoan.monthOther" type="text" readonly="readonly" class="textbox easyui-numberbox" 
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
									<td><input id="name" name="comTypeLoan.name" type="text" readonly="readonly" class="textbox easyui-validatebox" 
											data-options="validType:['length[0,50]']" value="${comTypeLoan.name}"/>
									</td>
									<td>注册时间:</td>
									<td>
										<input id="orgRegTime" name="comTypeLoan.orgRegTime" type="text" readonly="readonly" class="textbox easyui-datebox" 
										 	data-options="editable:false" value="${comTypeLoan.orgRegTime}"/>
									</td>
									<td>注册资本:</td>
									<td>
										<input id="orgRegAmt" name="comTypeLoan.orgRegAmt" type="text" readonly="readonly" class="textbox easyui-numberbox"
											data-options="min:0,precision:2" value="${comTypeLoan.orgRegAmt}"/>元
									</td>
								</tr>
								<tr>
									<td>证件类型:</td>
									<td>
										<input id="idType" type="text" readonly="readonly" name="comTypeLoan.idType"
											data-options="editable:false,panelHeight:'auto',
														textField:'keyValue',
														valueField:'keyProp',
														data:dataDictJson.companyIdType" 
											class="textbox easyui-combobox" value="${comTypeLoan.idType}"/>
									</td>
									<td>证件编码:</td>
									<td>
										<input id="idNo" name="comTypeLoan.idNo" type="text" readonly="readonly" class="textbox easyui-validatebox"
											data-options="validType:['length[0,30]']" value="${comTypeLoan.idNo}"/>
									</td>
								</tr>
								<tr>
									<td>法人姓名:</td>
									<td>
										<input id="legalName" name="comTypeLoan.legalName" type="text" readonly="readonly" class="textbox easyui-validatebox" 
											data-options="validType:['length[0,20]']" value="${comTypeLoan.legalName}"/>
									</td>
									<td>法人手机号:</td>
									<td>
										<input id="legalTel" name="comTypeLoan.legalTel" type="text" readonly="readonly" class="textbox easyui-validatebox" 
											data-options="validType:['mobile','length[0,20]']" value="${comTypeLoan.legalTel}"/>
									</td>
									<td>法人证件类型:</td>
									<td>
										<input id="legalIdType" name="comTypeLoan.legalIdType" type="text" readonly="readonly"
											data-options="editable:false,panelHeight:'auto',
															textField:'keyValue',
															valueField:'keyProp',
															data:dataDictJson.applyIdType" 
											class="textbox easyui-combobox" value="${comTypeLoan.legalIdType}"/>
									</td>
									<td>法人证件号码:</td>
									<td>
										<input id="legalIdNo" name="comTypeLoan.legalIdNo" type="text" readonly="readonly" class="textbox easyui-validatebox" 
											data-options="validType:['length[0,20]']" value="${comTypeLoan.legalIdNo}"/>
									</td>
								</tr>
								<tr>
									<td>股东姓名:</td>
									<td>
										<input id="shareName" name="comTypeLoan.shareName" type="text" readonly="readonly" class="textbox easyui-validatebox" 
											data-options="validType:['length[0,20]']" value="${comTypeLoan.shareName}"/>
									</td>
									<td>股东手机号:</td>
									<td>
										<input id="shareTel" name="comTypeLoan.shareTel" type="text" readonly="readonly" class="textbox easyui-validatebox" 
											data-options="validType:['mobile','length[0,20]']" value="${comTypeLoan.shareTel}"/>
									</td>
									<td>股东证件类型:</td>
									<td>
										<input id="shareIdType" name="comTypeLoan.shareIdType" type="text" readonly="readonly"
											data-options="editable:false,panelHeight:'auto',
															textField:'keyValue',
															valueField:'keyProp',
															data:dataDictJson.applyIdType" 
											class="textbox easyui-combobox" value="${comTypeLoan.shareIdType}"/>
									</td>
									<td>股东证件号码:</td>
									<td>
										<input id="shareIdNo" name="comTypeLoan.shareIdNo" type="text" readonly="readonly" class="textbox easyui-validatebox" 
											data-options="validType:['length[0,20]']" value="${comTypeLoan.shareIdNo}"/>
									</td>
								</tr>
								<tr>
									<td>经营年限:</td>
									<td>
										<input id="orgPeriod" name="comTypeLoan.orgPeriod" type="text" class="textbox easyui-numberbox" readonly="readonly"
											data-options="min:0,precision:0" value="${comTypeLoan.orgPeriod}"/> 年
									</td>
									<td>年经营额:</td>
									<td>
										<input id="orgSalesAmt" name="comTypeLoan.orgSalesAmt" type="text" class="textbox easyui-numberbox" readonly="readonly"
											data-options="min:0,precision:2" value="${comTypeLoan.orgSalesAmt}"/>元
									</td>
								</tr>
								<tr>
									<td>机构经营范围:</td>
									<td colspan="6">
										<textarea readonly="readonly" name="comTypeLoan.orgBus" class="textbox easyui-validatebox" 
											data-options="validType:['length[0,300]']" 
											style="resize: none;width:625px;height:50px!important;">${comTypeLoan.orgBus}</textarea>
									</td>
								</tr>
								<tr>
									<td>注册地址:</td>
									<td colspan="6">
										<input id="homePrvn" name="comTypeLoan.homePrvn" type="text" class="textbox easyui-combobox" readonly="readonly"
											data-options="editable:false" value="${comTypeLoan.homePrvn}"/>省
										<input id="homeCity" name="comTypeLoan.homeCity" type="text" class="textbox easyui-combobox" readonly="readonly"
											data-options="editable:false" value="${comTypeLoan.homeCity}"/>市
										<input id="homeCtry" name="comTypeLoan.homeCtry" type="text" class="textbox easyui-combobox" readonly="readonly"
											data-options="editable:false" value="${comTypeLoan.homeCtry}"/>区/县
										<input id="homeAddr" name="comTypeLoan.homeAddr" type="text" class="textbox easyui-validatebox" style="width: 200px" readonly="readonly"
											data-options="validType:['length[0,100]']" value="${comTypeLoan.homeAddr}"/>
									</td>
								</tr>
								<tr>
									<td>经营地址:</td>
									<td colspan="6">
										<input id="nowPrvn" name="comTypeLoan.nowPrvn" type="text" class="textbox easyui-combobox" readonly="readonly"
											data-options="editable:false" value="${comTypeLoan.nowPrvn}"/>省
										<input id="nowCity" name="comTypeLoan.nowCity" type="text" class="textbox easyui-combobox" readonly="readonly"
											data-options="editable:false" value="${comTypeLoan.nowCity}"/>市
										<input id="nowCtry" name="comTypeLoan.nowCtry" type="text" class="textbox easyui-combobox" readonly="readonly"
											data-options="editable:false" value="${comTypeLoan.nowCtry}"/>区/县
										<input id="nowAddr" name="comTypeLoan.nowAddr" type="text" class="textbox easyui-validatebox" style="width: 200px" readonly="readonly"
											data-options="validType:['length[0,100]']" value="${comTypeLoan.nowAddr}"/>
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
										type="text" readonly="readonly" data-options="validType:['length[0,50]']" 
										class="textbox easyui-validatebox" value="${contacts[0].name}"/>
								</td>
								<td>
									<input id="relation" name="contacts[0].relation" type="text" readonly="readonly" 
										data-options="
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
										type="text" readonly="readonly" data-options="validType:['length[0,50]']" 
										class="textbox easyui-validatebox" value="${contacts[0].company}"/>
								</td>
								<td>
									<input id="tel" name="contacts[0].tel" 
										type="text" readonly="readonly" data-options="validType:['length[0,50]']" 
										class="textbox easyui-validatebox" value="${contacts[0].tel}"/>
								</td>
							</tr>
							
							<tr id="contact_1">
								<td>
									<input type="hidden" name="contacts[1].id" value="${contacts[1].id}" />
									<input type="hidden" name="contacts[1].loanId" value="${contacts[1].loanId}" />
									<input type="hidden" id="state" name="contacts[1].state" value="1" />
									<input id="name" name="contacts[1].name" 
										type="text" readonly="readonly" data-options="validType:['length[0,50]']" 
										class="textbox easyui-validatebox" value="${contacts[1].name}"/>
								</td>
								<td>
									<input id="relation" name="contacts[1].relation" type="text" readonly="readonly" 
										data-options="
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
										type="text" readonly="readonly" data-options="validType:['length[0,50]']" 
										class="textbox easyui-validatebox" value="${contacts[1].company}"/>
								</td>
								<td>
									<input id="tel" name="contacts[1].tel" 
										type="text" readonly="readonly" data-options="validType:['length[0,50]']" 
										class="textbox easyui-validatebox" value="${contacts[1].tel}"/>
								</td>
							</tr>
							
							<tr id="contact_2">
								<td>
									<input type="hidden" name="contacts[2].id" value="${contacts[2].id}" />
									<input type="hidden" name="contacts[2].loanId" value="${contacts[2].loanId}" />
									<input type="hidden" id="state" name="contacts[2].state" value="1" />
									<input id="name" name="contacts[2].name" 
										type="text" readonly="readonly" data-options="validType:['length[0,50]']" 
										class="textbox easyui-validatebox" value="${contacts[2].name}"/>
								</td>
								<td>
									<input id="relation" name="contacts[2].relation" type="text" readonly="readonly" 
										data-options="
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
										type="text" readonly="readonly" data-options="validType:['length[0,50]']" 
										class="textbox easyui-validatebox" value="${contacts[2].company}"/>
								</td>
								<td>
									<input id="tel" name="contacts[2].tel" 
										type="text" readonly="readonly" data-options="validType:['length[0,50]']" 
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
												type="text" readonly="readonly" data-options="validType:['length[0,50]']" 
												class="textbox easyui-validatebox" value="${contact.name}"/>
										</td>
										<td>
											<input id="relation" name="contacts[${status.index}].relation" type="text" readonly="readonly" 
												data-options="
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
												type="text" readonly="readonly" data-options="validType:['length[0,50]']" 
												class="textbox easyui-validatebox" value="${contact.company}"/>
										</td>
										<td>
											<input id="tel" name="contacts[${status.index}].tel" 
												type="text" readonly="readonly" data-options="validType:['length[0,50]']" 
												class="textbox easyui-validatebox" value="${contact.tel}"/>
											<img src="img/deleteItem2.png"  onclick="rmObj('contact_${status.index}');" />
										</td>
									</tr>
								</c:if>
							</c:forEach>

						</table>
						
						<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>业务员备注</strong></div><hr color="#D3D3D3"/>
						<table>
							<tr>
								<td>客服备注：</td>
								<td>
									<textarea readonly="readonly" name="saleRemark" class="textbox easyui-validatebox" 
										data-options="validType:['length[0,500]']" 
										style="resize: none;width:625px;height:50px!important;"><c:if test="${not empty appTypeLoan}">${appTypeLoan.saleRemark}</c:if>${comTypeLoan.saleRemark}</textarea>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
		<c:if test="${loanBase.isRenew eq '1' }">
			<div title="续贷历史" data-options="href:'${basePath}loan/renew/readQuery.do?origLoanId=${loanBase.origLoanId}'" style="width: 100%;padding:10px"></div>
			<div title="原申请信息" data-options="href:'${basePath}loan/origRead.do?id=${origLoanBase.id}'" style="width: 100%"></div>
		</c:if>
		<div title="申请影像资料" data-options="href:'${basePath}files/read2.do?loanId=${loanBase.loanId}&sec=${sec }&bizKey=${loanBase.loanId}'" style="padding:10px"></div>
		<c:if test="${loanBase.isTgth eq '1' }">
			<div title="共同借款信息" data-options="href:'${basePath}loan/common/query.do?loanId=${loanBase.loanId}&opt=read'" style="width: 100%;padding:2px""></div>
		</c:if>
		<div title="质/抵押物" data-options="href:'${basePath}loan/collateral/query.do?loanId=${loanBase.loanId}&opt=read'" style="width: 100%;padding:2px""></div>
		<div title="担保情况" data-options="href:'${basePath}loanguar/query.do?loanId=${loanBase.loanId}&opt=read'" style="width: 100%;padding:2px""></div>
		<div title="诉讼情况" data-options="href:'${basePath}loan/law/query.do?loanId=${loanBase.loanId}&opt=read'" style="width: 100%;padding:2px""></div>
	    <div title="合同信息" data-options="href:'${basePath}contract/read.do?contractId=${contractId}'" style="width: 100%;padding:2px""></div>
	    <div title="还款计划" data-options="href:'${basePath}contract/repayPlanList.do?contractId=${contractId}'" style="width: 100%;padding:2px""></div>
	    <div title="交易记录" data-options="href:'${basePath}account/afterloan/acctQuery.do?loanId=${loanBase.loanId }&contractId=${contract.contractId}'" style="width: 100%;padding:2px"></div>
	</div>

<script type="text/javascript">

//页面加载完动作
$(document).ready(function (){
	// 产品
	$("#product").combobox({
		url:'sys/datadictionary/listjason.do?keyName=productType',
		textField:'keyValue',
		valueField:'keyProp'
	});

	initAcct();
	
	var tsurl="sys/datadictionary/listjason.do?keyName=repayMethod";
	$("#retWay").combobox("clear");
	$('#retWay').combobox({
		url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue'
	});
	
	$("#loanType").combobox("clear");
	$('#loanType').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.loanType
	});
	
	// 申请类型
	var loanType = $("#loanType").combobox('getValue');
	// 个人
	if('01' == loanType) {
		$("#appTypeArea").show();
		toggleValidate("#companyTypeArea",false);
		initAddr("applyInfo");
		
		// 单位地址-省
		var provinceUrl = "sys/datadictionary/listjason.do?keyName=province";
		$("#companyPrvn").combobox("clear");
		$('#companyPrvn').combobox({
			url: provinceUrl,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
		
		// 单位地址-市
		var homePrvn = $('#companyPrvn').combobox('getValue');
		var cityUrl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(homePrvn);
		$("#companyCity").combobox("clear");
		$('#companyCity').combobox({
			url: cityUrl,
			valueField: 'keyProp',
			textField: 'keyValue'
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
		toggleValidate("#appTypeArea",false);
		initAddr("orgInfo");
	}
	
	// 证件类型
	$("#applyInfo").find("#idType").combobox({
		textField:'keyValue',
		valueField:'keyProp',
		data:dataDictJson.applyIdType
	});
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
			textField: 'keyValue'
		});
		
		// 开户行市
		var bankPrvVal = $("#bankInfo").find('#acctPrvn').combobox('getValue');
		var bankCity = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(bankPrvVal);
		$("#bankInfo").find("#acctCity").combobox("clear");
		$("#bankInfo").find('#acctCity').combobox({
			url: bankCity,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
		
		
		// 开户行区县
		var bankCityVal = $("#bankInfo").find('#acctCity').combobox('getValue');
		var bankCtry = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(bankCityVal);
		$("#bankInfo").find("#acctCtry").combobox("clear");
		$("#bankInfo").find('#acctCtry').combobox({
			url: bankCtry, 
			valueField: 'keyProp',
			textField: 'keyValue'
		});	
		
		// 银行
		var bank = "sys/datadictionary/listjason.do?keyName=bank";
		$("#bankInfo").find("#acctBank").combobox("clear");
		$("#bankInfo").find('#acctBank').combobox({
			url: bank, 
			valueField: 'keyProp',
			textField: 'keyValue'
		});	
		
		if(bankPrvVal != '' && bankCityVal != '' && bankVal != '') {
			// 支行
			var bankVal = $("#bankInfo").find('#acctBank').combobox('getValue');
			var branch = "common/listBranchBank.do?province="+encodeURI(bankPrvVal)+"&city="+encodeURI(bankCityVal)+"&bank_name="+encodeURI(bankVal);
			$("#bankInfo").find("#acctBranch").combobox("clear");
			$("#bankInfo").find('#acctBranch').combobox({
				url: branch, 
				valueField: 'keyProp',
				textField: 'keyValue'
			});
		}
	}
</script>
<script type="text/javascript">
	function initAddr(type) {
		// 户籍地址-省
		var provinceUrl = "sys/datadictionary/listjason.do?keyName=province";
		$("#homePrvn").combobox("clear");
		$('#homePrvn').combobox({
			url: provinceUrl,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
		
		// 户籍地址-市
		var homePrvn = $('#homePrvn').combobox('getValue');
		var cityUrl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(homePrvn);
		$("#homeCity").combobox("clear");
		$('#homeCity').combobox({
			url: cityUrl,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
		
		// 户籍地址-区/县
		var homeCity = $('#homeCity').combobox('getValue');
		var countyUrl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(homeCity);
		$("#homeCtry").combobox("clear");
		$('#homeCtry').combobox({
			url: countyUrl, 
			valueField: 'keyProp',
			textField: 'keyValue'
		});	
		
		// 现居地址-省
		var provinceUrl = "sys/datadictionary/listjason.do?keyName=province";
		$("#nowPrvn").combobox("clear");
		$('#nowPrvn').combobox({
			url: provinceUrl,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
		
		// 现居地址-市
		var homePrvn = $('#nowPrvn').combobox('getValue');
		var cityUrl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(homePrvn);
		$("#nowCity").combobox("clear");
		$('#nowCity').combobox({
			url: cityUrl,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
		
		// 现居地址-区/县
		var homeCity = $('#nowCity').combobox('getValue');
		var countyUrl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(homeCity);
		$("#nowCtry").combobox("clear");
		$('#nowCtry').combobox({
			url: countyUrl, 
			valueField: 'keyProp',
			textField: 'keyValue'
		});
	}
</script>	

<script type="text/javascript" >
openMask();
$(window).load(function (){
	closeMask();
});
</script>
</body>
</html>

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
<style type="text/css">
</style>
</head>
<body>
	<div id="main">
		<div class="content">
			<form id="updateCommonForm" >
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>申请人信息</strong></div><hr color="#D3D3D3"/>
				<input type="hidden" name="appTypeLoan.id" value="${appTypeLoan.id }" />
				<input type="hidden" name="appTypeLoan.loanId" value="${loanId }" />
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
											data:dataDictJson.gender,
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
											}" value="${appTypeLoan.sex}"/></td>
					</tr>
					<tr>
						<td>证件类型:</td>
						<td>
							<input id="idType" name="appTypeLoan.idType" type="text" 
								data-options="required:true,
											textField:'keyValue',
											valueField:'keyProp',
											data:dataDictJson.applyIdType,
											editable:false,
											panelHeight:'auto',
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
							<input id="tel" name="appTypeLoan.tel" type="text" class="textbox easyui-validatebox"  
								data-options="required:true,validType:['mobile','length[0,20]']"value="${appTypeLoan.tel}"/>
						</td>
						<td>备用手机号:</td>
						<td>
							<input id="tel2" name="appTypeLoan.tel2" type="text" class="textbox easyui-validatebox"
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
							<input id="hasChil" name="appTypeLoan.hasChil" type="radio" value="0" <c:if test="${appTypeLoan.hasChil eq '0' }">checked='checked'</c:if> />无
							&nbsp;
							<input id="hasChil" name="appTypeLoan.hasChil" type="radio" value="1" <c:if test="${appTypeLoan.hasChil eq '1' }">checked='checked'</c:if> />有
							<input id="chilNum" name="appTypeLoan.chilNum" type="text" class="textbox easyui-numberbox" style="width: 45px;"
								data-options="min:0,validType:['length[0,2]']" value="${appTypeLoan.chilNum}"/>个	
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
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>操作</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td colspan=3">
							<input type="button" value="保存" class="btn" onclick="javascript:updateFunction('save');"/>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</form>
			<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>影像</strong></div><hr color="#D3D3D3"/>
			<jsp:include page="/files/load.do">
				<jsp:param value="${loanId }" name="loId"/>
				<jsp:param value="filesce3" name="sec"/>
				<jsp:param value="${appTypeLoan.id}" name="bizKey"/>
				<jsp:param value="0" name="opt"/>
			</jsp:include>
		</div>
	</div>

<script type="text/javascript">
//更新保存
function updateFunction(buttonType) {
	if("save"!=buttonType){
		//验证表单验证是否通过
		if(false == $('#updateCommonForm').form('validate') ){
			$.messager.confirm('消息', "页面有必输字段，但未填值！");
			return;
		}
	}
	var now=new Date();
	//证件有效期
	var validDate=$("#validDate").datebox('getValue');
	var myvalidDate=new Date(validDate);
	if(myvalidDate<now){
		$.messager.confirm('消息', "证件有效期不能早于当前日期！");
		return;
	}
	//月收入不能大于年收入
	var monthIncome=$("#monthIncome").numberbox('getValue');
	var monthOther=$("#monthOther").numberbox('getValue');
	var yearIncome=$("#yearIncome").numberbox('getValue');
	if('' == monthOther || null == monthOther) {
		if(parseFloat(monthIncome)>parseFloat(yearIncome)){
			$.messager.confirm('消息', "月工作收入不能超过年收入！");
			return;
		}
	}
	if('' != monthOther || null != monthOther){
		if((parseFloat(monthIncome)+parseFloat(monthOther))>parseFloat(yearIncome)*10000){
			$.messager.confirm('消息', "月工作收入和月其他收入不能超过年收入！");
			return;
		}
	}
	
	//去掉 input 输入的 前后空格
	$('#updateCommonForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});

	
	//弹出异步加载 遮罩
	openMask();
	
	var params = $('#updateCommonForm').serialize();
	
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "loan/common/save.do",
		data : params+"&buttonType="+buttonType,
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', "成功", 'info', function(){
					//window.parent.refreshTab("appUpdateTabs");
					var url = "loan/common/update.do?id=" + data.id +"&loanId="+data.loanId;
					window.parent.refreshCommonTab('appCommonQueryTabs',url);
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
	
	// 户籍地址-省
	var provinceUrl = "sys/datadictionary/listjason.do?keyName=province";
	$("#homePrvn").combobox("clear");
	$('#homePrvn').combobox({
		url: provinceUrl,
		valueField: 'keyProp',
		textField: 'keyValue',
		onChange: function(newValue, oldValue){
	        $('#homeCity').combobox('clear');
	        $('#homeCtry').combobox('clear');
	        var cityUrl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
	        $('#homeCity').combobox('reload',cityUrl); 
    	},
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
		}
	});
	
	// 户籍地址-市
	var homePrvn = $('#homePrvn').combobox('getValue');
	var cityUrl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(homePrvn);
	$("#homeCity").combobox("clear");
	$('#homeCity').combobox({
		url: cityUrl,
		valueField: 'keyProp',
		textField: 'keyValue',
		onChange: function(newValue, oldValue){
            $('#homeCtry').combobox('clear');
            var countyUrl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
            $('#homeCtry').combobox('reload',countyUrl); 
		}
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
		textField: 'keyValue',
		onChange: function(newValue, oldValue){
	        $('#nowCity').combobox('clear');
	        $('#nowCtry').combobox('clear');
	        var cityUrl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
	        $('#nowCity').combobox('reload',cityUrl); 
    	},
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
		}
	});
	
	// 现居地址-市
	var homePrvn = $('#nowPrvn').combobox('getValue');
	var cityUrl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(homePrvn);
	$("#nowCity").combobox("clear");
	$('#nowCity').combobox({
		url: cityUrl,
		valueField: 'keyProp',
		textField: 'keyValue',
		onChange: function(newValue, oldValue){
            $('#nowCtry').combobox('clear');
            var countyUrl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
            $('#nowCtry').combobox('reload',countyUrl); 
		}
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
}); 

</script>

<script type="text/javascript" >
openMask();
$(window).load(function (){
	closeMask();
});
</script>
</body>
</html>


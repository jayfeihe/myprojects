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
						<td><input id="name" name="appTypeLoan.name" type="text" readonly="readonly" readonly="readonly" class="textbox easyui-validatebox" 
								data-options="validType:['length[0,50]']" value="${appTypeLoan.name}"/>
						</td>
						<td>性别:</td>
						<td>
							<input id="sex" name="appTypeLoan.sex" type="text" readonly="readonly" class="textbox easyui-combobox"
								data-options="editable:false,panelHeight:'auto',
											textField:'keyValue',
											valueField:'keyProp',
											data:dataDictJson.gender" value="${appTypeLoan.sex}"/></td>
					</tr>
					<tr>
						<td>证件类型:</td>
						<td>
							<input id="idType" name="appTypeLoan.idType" type="text" readonly="readonly" 
								data-options="
											textField:'keyValue',
											valueField:'keyProp',
											data:dataDictJson.applyIdType" 
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
												valueField:'keyProp'" value="${appTypeLoan.marriage}"/>
						</td>
						<td>子女信息:</td>
						<td>
							<input id="hasChil" name="appTypeLoan.hasChil" type="radio" value="0" <c:if test="${appTypeLoan.hasChil eq '0' }">checked='checked'</c:if> />无
							&nbsp;
							<input id="hasChil" name="appTypeLoan.hasChil" type="radio" value="1" <c:if test="${appTypeLoan.hasChil eq '1' }">checked='checked'</c:if> />有
							<input id="chilNum" name="appTypeLoan.chilNum" type="text" readonly="readonly" class="textbox easyui-validatebox" style="width: 45px;"
								data-options="validType:['length[0,2]']" value="${appTypeLoan.chilNum}"/>个	
						</td>
						<td>学历:</td>
						<td>
							<input id="edu" name="appTypeLoan.edu" type="text" readonly="readonly" class="textbox easyui-combobox" 
								data-options="editable:false,panelHeight:'auto',
											url:'sys/datadictionary/listjason.do?keyName=education',
												textField:'keyValue',
												valueField:'keyProp'" value="${appTypeLoan.edu}"/></td>
					</tr>
					<tr>
						<td>居住情况:</td>
						<td>
							<input id="live" name="appTypeLoan.live" type="text" readonly="readonly" class="textbox easyui-combobox"
								data-options="editable:false,panelHeight:'auto',
											url:'sys/datadictionary/listjason.do?keyName=live',
												textField:'keyValue',
												valueField:'keyProp'" value="${appTypeLoan.live}"/>
						</td>
						<td>本市房产情况:</td>
						<td>
							<input id="nativeHouse" name="appTypeLoan.nativeHouse" type="text" readonly="readonly" class="textbox easyui-combobox"
								data-options="editable:false,panelHeight:'auto',
											url:'sys/datadictionary/listjason.do?keyName=nativeHouse',
												textField:'keyValue',
												valueField:'keyProp'" value="${appTypeLoan.nativeHouse}"/>
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
												valueField:'keyProp'" value="${appTypeLoan.companyType}"/>
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
			</form>
			<jsp:include page="/files/load.do">
				<jsp:param value="${loanId }" name="loId"/>
				<jsp:param value="filesce3" name="sec"/>
				<jsp:param value="${appTypeLoan.id}" name="bizKey"/>
				<jsp:param value="1" name="opt"/>
			</jsp:include>
		</div>
	</div>

<script type="text/javascript">

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
</body>
</html>


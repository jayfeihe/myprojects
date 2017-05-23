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
</style>
</head>
<body>
    <table>
        <tbody>
            <input id="id" name="id" type="hidden" size="35" value="${ bean.id}" />
            <input type="hidden" name="customerType" value="02"/>
<tr>
<td><SPAN style="color:red">*</SPAN>机构全称:<input id="name" name="name" type="text" data-options="required:true,validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.name}"/></td>
<td>机构简称:<input id="shortName" name="shortName" type="text" data-options="validType:['length[0,10]']" class="textbox easyui-validatebox" value="${bean.shortName}"/></td>
<td></td>
<td></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>证件类型:
<input id="idType" name="idType" type="text" data-options="required:true" editable="false"  class="textbox easyui-combobox" value="${bean.idType}"/></td>

<td><SPAN style="color:red">*</SPAN>证件号码:
<input id="idNo" name="idNo" type="text" data-options="required:true,validType:['length[0,18]','idNo']" class="textbox easyui-validatebox" value="${bean.idNo}"/></td>

<td>证件失效日期:
<input id="idExpiryDate" name="idExpiryDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.idExpiryDateStr}"/></td>
<td></td>
</tr>

<tr>
<td>一级行业代码:<input id="industry1" name="industry1" type="text" data-options="" editable="false"  class="textbox easyui-combobox" value="${bean.industry1}"/></td>

<td>二级行业代码:<input id="industry2" name="industry2" type="text" data-options="" editable="false"  class="textbox easyui-combobox" value="${bean.industry2}"/></td>

<td>三级行业代码:<input id="industry3" name="industry3" type="text" data-options="" editable="false"  class="textbox easyui-combobox" value="${bean.industry3}"/></td>
<td></td>
</tr>

<tr>
<td colspan="4">经营范围:
<input id="bizzScope" name="bizzScope" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.bizzScope}"/></td>
</tr>

<tr>
<td colspan="4">资源需求:
<input id="requirements" name="requirements" type="text" data-options="validType:['length[0,200]']" class="textbox easyui-validatebox" value="${bean.requirements}"/></td>
</tr>

<tr>
<td colspan="4">注册地址：
<input id="regProvince" name="regProvince" type="text" data-options="required:true" editable="false"  class="textbox easyui-combobox" value="${bean.regProvince}"/>
省
<input id="regCity" name="regCity" type="text" data-options="required:true" editable="false"  class="textbox easyui-combobox" value="${bean.regCity}"/>
市
<input id="regCounty" name="regCounty" type="text" data-options="required:true" editable="false"  class="textbox easyui-combobox" value="${bean.regCounty}"/>
区县
<input id="regAddress" name="regAddress" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.regAddress}"/>
</td>
</tr>

<tr>
<td colspan="4">通讯地址：
<input id="addProvince" name="addProvince" type="text" data-options="required:true" editable="false"  class="textbox easyui-combobox" value="${bean.addProvince}"/>
省
<input id="addCity" name="addCity" type="text" data-options="required:true" editable="false"  class="textbox easyui-combobox" value="${bean.addCity}"/>
市
<input id="addCounty" name="addCounty" type="text" data-options="required:true" editable="false"  class="textbox easyui-combobox" value="${bean.addCounty}"/>
区县
<input id="address" name="address" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.address}"/>
</td>
</tr>

<tr>
<td>邮编:
<input id="postcode" name="postcode" type="text" data-options="validType:['ZIP']" class="textbox easyui-validatebox"value="${bean.postcode}"/></td>
<td>固定电话:
<input id="phone" name="phone" type="text" data-options="validType:['length[0,18]']" class="textbox easyui-validatebox"value="${bean.phone}"/></td>
<td>移动电话:
<input id="mobile" name="mobile" type="text" data-options="validType:['length[0,18]']" class="textbox easyui-validatebox"value="${bean.mobile}"/></td>
<td>EMAIL: 
<input id="email" name="email" type="text" data-options="validType:['email','length[0,50]']" class="textbox easyui-validatebox"value="${bean.email}"/></td>
</tr>

<tr>
<td colspan="4">企业性质:
<input id="companyType" name="companyType" type="text" editable="false"  class="textbox easyui-combobox" value="${bean.companyType}"/></td>
</tr>
<tr>
<td colspan="4">信托资产:
<input id="trustAssets" name="trustAssets" type="radio" value='0' <c:if test="${bean.trustAssets=='0'}">checked='checked'</c:if> />否
<input id="trustAssets" name="trustAssets" type="radio" value="1" <c:if test="${bean.trustAssets=='1'}">checked='checked'</c:if> />是
</tr>

<tr>
<td>若是信托资产，信托委托人:
<input id="trustSettlor" name="trustSettlor" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.trustSettlor}"/></td>

<td>信托委托人电话:
<input id="trustSettlorPhone" name="trustSettlorPhone" type="text" data-options="validType:['length[0,18]']" class="textbox easyui-validatebox" value="${bean.trustSettlorPhone}"/></td>
<td></td>
<td></td>
</tr>

<tr>
<td>信托受益人:
<input id="trustBenefit" name="trustBenefit" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.trustBenefit}"/></td>

<td>信托受益人电话:
<input id="trustBenefitPhone" name="trustBenefitPhone" type="text" data-options="validType:['length[0,18]']" class="textbox easyui-validatebox" value="${bean.trustBenefitPhone}"/></td>
<td></td>
<td></td>
</tr>

<tr>
<td colspan="4" align="left">&nbsp;</td>
</tr>

<tr>
<td colspan="4" align="left"><b>法定代表人信息</b><hr width="100%" size=1 color=#E0ECFF align=center noshade></td>
</tr>
<tr>

<tr>
<input id="farenId" name="farenId" type="hidden" size="35" value="${ farenContact.id}" />
<td><SPAN style="color:red">*</SPAN>姓名:
<input id="farenName" name="farenName" type="text" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" value="${farenContact.name}"/></td>
<td><SPAN style="color:red">*</SPAN>证件类型:
<input id="farenIdType" name="farenIdType" type="text" data-options="required:true" editable="false"  class="textbox easyui-combobox" value="${farenContact.idType}"/></td>

<td colspan="2"><SPAN style="color:red">*</SPAN>证件号码:
<input id="farenIdNo" name="farenIdNo" type="text" data-options="required:true,validType:['length[0,18]','idNo']" class="textbox easyui-validatebox" value="${farenContact.idNo}"/></td>
</tr>

<tr>
<td>证件签发日期:
<input id="farenIdIssueDate" name="farenIdIssueDate" type="text" editable="false" class="textbox easyui-datebox" value="${farenContact.idIssueDateStr}"/></td>

<td colspan="3">签发机关:
<input id="farenIssueGov" name="farenIssueGov" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox"value="${farenContact.idIssueGov}"/></td>
</tr>

<tr>
<td colspan="4" align="left">&nbsp;</td>
</tr>

<tr>
<td colspan="4" align="left"><b>财务负责人信息</b><hr width="100%" size=1 color=#E0ECFF align=center noshade></td>
</tr>
<tr>
<input id="cfoId" name="cfoId" type="hidden" size="35" value="${ cfoContact.id}" />
<td>姓名:
<input id="cfoName" name="cfoName" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${cfoContact.name}"/></td>

<td>证件类型:
<input id="cfoIdType" name="cfoIdType" type="text"  editable="false"  class="textbox easyui-combobox" value="${cfoContact.idType}"/>
</td>
<td colspan="2">证件号码:
<input id="cfoIdNo" name="cfoIdNo" type="text" data-options="validType:['length[0,18]','idNo']" class="textbox easyui-validatebox" value="${cfoContact.idNo}"/></td>
</tr>
<tr>
<td>证件签发日期:
<input id="cfoIdIssueDate" name="cfoIdIssueDate" type="text" editable="false" class="textbox easyui-datebox" value="${cfoContact.idIssueDateStr}"/></td>
<td colspan="3">签发机关:
<input id="cfoIssueGov" name="cfoIssueGov" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox"value="${cfoContact.idIssueGov}"/></td>
</tr>

<tr>
<td colspan="4" align="left">&nbsp;</td>
</tr>

<tr>
<td colspan="4" align="left"><b>控股股东或实际控制人信息</b><hr width="100%" size=1 color=#E0ECFF align=center noshade></td>
</tr>
<tr>
<input id="kongzhiId" name="kongzhiId" type="hidden" size="35" value="${ kongzhiContact.id}" />
<td>姓名:
<input id="kongzhiName" name="kongzhiName" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${kongzhiContact.name}"/></td>

<td>证件类型:
<input id="kongzhiIdType" name="kongzhiIdType" type="text" editable="false"  class="textbox easyui-combobox" value="${kongzhiContact.idType}"/>
</td>
<td colspan="2">证件号码:<input id="kongzhiIdNo" name="kongzhiIdNo" type="text" data-options="validType:['length[0,18]','idNo']" class="textbox easyui-validatebox" value="${kongzhiContact.idNo}"/></td>
</tr>
<tr>
<td>证件签发日期:
<input id="kongzhiIdIssueDate" name="kongzhiIdIssueDate" type="text" editable="false" class="textbox easyui-datebox" value="${kongzhiContact.idIssueDateStr}"/></td>

<td colspan="3">签发机关:<input id="kongzhiIssueGov" name="kongzhiIssueGov" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox"value="${kongzhiContact.idIssueGov}"/></td>
</tr>
        </tbody>
    </table>
</body>

<script type="text/javascript">

$(document).ready(function(){
	
	//填充select数据 机构证件类型
//	var idTypeurl="sys/datadictionary/listjason.do?keyName=companyidtype";
	$("#idType").combobox("clear");
	$('#idType').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.companyidtype
	});
	$('#idType').combobox('select', '${bean.idType}');

	//填充select数据 省份
	var provinceurl = "sys/datadictionary/listjason.do?keyName=province";
	$("#addProvince").combobox("clear");
	$('#addProvince').combobox({url: provinceurl,valueField: 'keyProp',textField: 'keyValue'});
	$('#addProvince').combobox('select', '${bean.addProvince}');

	//填充select数据 市
	var province = $('#addProvince').combobox('getValue');
	var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(province);
	$("#addCity").combobox("clear");
	$('#addCity').combobox({url: cityurl,valueField: 'keyProp',textField: 'keyValue'});
	$('#addCity').combobox('select', '${bean.addCity}');

	//填充select数据 区县
	var city = $('#addCity').combobox('getValue');
	var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(city);
	$("#addCounty").combobox("clear");
	$('#addCounty').combobox({url: countyurl, valueField: 'keyProp', textField: 'keyValue' });
	$('#addCounty').combobox('select', '${bean.addCounty}');
	
	//填充select数据 注册省份
	var regprovinceurl = "sys/datadictionary/listjason.do?keyName=province";
	$("#regProvince").combobox("clear");
	$('#regProvince').combobox({url: regprovinceurl,valueField: 'keyProp',textField: 'keyValue'});
	$('#regProvince').combobox('select', '${bean.regProvince}');

	//填充select数据注册 市
	var regprovince = $('#regProvince').combobox('getValue');
	var regcityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(regprovince);
	$("#regCity").combobox("clear");
	$('#regCity').combobox({url: regcityurl,valueField: 'keyProp',textField: 'keyValue'});
	$('#regCity').combobox('select', '${bean.regCity}');

	//填充select数据注册 区县
	var regcity = $('#regCity').combobox('getValue');
	var regcountyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(regcity);
	$("#regCounty").combobox("clear");
	$('#regCounty').combobox({url: regcountyurl, valueField: 'keyProp', textField: 'keyValue' });
	$('#regCounty').combobox('select', '${bean.regCounty}');
	
	//填充select数据企业性质companyType
//	var companytypeurl="sys/datadictionary/listjason.do?keyName=companytype";
	$("#companyType").combobox("clear");
	$('#companyType').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.companytype
		});
	$('#companyType').combobox('select', '${bean.companyType}');
	
	//填充select数据 法人证件类型
//	var farenIdTypeurl="sys/datadictionary/listjason.do?keyName=personidtype";
	$("#farenIdType").combobox("clear");
	$('#farenIdType').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.personidtype});
	$('#farenIdType').combobox('select', '${farenContact.idType}');
	
	//填充select数据 财务负责人证件类型
//	var cfoIdTypeurl="sys/datadictionary/listjason.do?keyName=personidtype";
	$("#cfoIdType").combobox("clear");
	$('#cfoIdType').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.personidtype});
	$('#cfoIdType').combobox('select', '${cfoContact.idType}');
	
	//填充select数据 控制人证件类型
//	var kongzhiIdTypeurl="sys/datadictionary/listjason.do?keyName=personidtype";
	$("#kongzhiIdType").combobox("clear");
	$('#kongzhiIdType').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.personidtype});
	$('#kongzhiIdType').combobox('select', '${kongzhiContact.idType}');
	
	
	//填充select数据 一级行业
	var industry1url = "sys/datadictionary/listjason.do?keyName=industry1";
	$("#industry1").combobox("clear");
	$('#industry1').combobox({url: industry1url,valueField: 'keyProp',textField: 'keyValue'});
	$('#industry1').combobox('select', '${bean.industry1}');

	//填充select数据 二级行业
	var industry1 = $('#industry1').combobox('getValue');
	var industry2url = "sys/datadictionary/listjason.do?keyName=industry2&parentKeyProp=" + industry1;
	$("#industry2").combobox("clear");
	$('#industry2').combobox({url: industry2url,valueField: 'keyProp',textField: 'keyValue'});
	$('#industry2').combobox('select', '${bean.industry2}');

	//填充select数据 三级行业
	var industry2 = $('#industry2').combobox('getValue');
	var industry3url = "sys/datadictionary/listjason.do?keyName=industry3&parentKeyProp=" + industry2;
	$("#industry3").combobox("clear");
	$('#industry3').combobox({url: industry3url, valueField: 'keyProp', textField: 'keyValue' });
	$('#industry3').combobox('select', '${bean.industry3}');
});

$('#addProvince').combobox({
    onChange: function(newValue, oldValue){
		$('#addCity').combobox('clear');
		$('#addCounty').combobox('clear');
		var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
		$('#addCity').combobox('reload',cityurl); 
    }
});
$('#addCity').combobox({
    onChange: function(newValue, oldValue){
		$('#addCounty').combobox('clear');
		var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
		$('#addCounty').combobox('reload',countyurl); 
    }
});

$('#regProvince').combobox({
    onChange: function(newValue, oldValue){
		$('#regCity').combobox('clear');
		$('#regCounty').combobox('clear');
		var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
		$('#regCity').combobox('reload',cityurl);
    }
});

$('#regCity').combobox({
    onChange: function(newValue, oldValue){
		$('#regCounty').combobox('clear');
		var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
		$('#regCounty').combobox('reload',countyurl);
    }
});

$('#industry1').combobox({
    onChange: function(newValue, oldValue){
		$('#industry2').combobox('clear');
		$('#industry3').combobox('clear');
		var industry2url = "sys/datadictionary/listjason.do?keyName=industry2&parentKeyProp=" + encodeURI(newValue);
		$('#industry2').combobox('reload',industry2url);
    }
});

$('#industry2').combobox({
    onChange: function(newValue, oldValue){
		$('#industry3').combobox('clear');
		var industry3url = "sys/datadictionary/listjason.do?keyName=industry3&parentKeyProp=" + encodeURI(newValue);
		$('#industry3').combobox('reload',industry3url);
    }
});

</script>
</html>
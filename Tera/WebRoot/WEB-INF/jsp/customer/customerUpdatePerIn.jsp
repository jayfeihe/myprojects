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
            <input type="hidden" name="customerType" value="01"/>
            <tr>
                <td><SPAN style="color:red">*</SPAN>姓名(中文):</td>
                <td><input id="name" name="name" type="text" data-options="required:true,validType:['length[0,100]']" class="textbox easyui-validatebox"value="${bean.name}"/></td>
                <td>姓名(英文):</td>
                <td><input id="engName" name="engName" type="text" data-options="validType:['length[0,18]']" class="textbox easyui-validatebox"value="${bean.engName}"/></td>
            
            	<td>性别:</td>
				<td>
					<select class="easyui-combobox" name="gender" id="gender" style="width:152px;" editable="false"/>
				</td>
				<td>生日:</td>
				<td><input id="birthday" name="birthday" type="text" editable="false" class="textbox easyui-datebox" value="${bean.birthdayStr}"/></td>
            </tr>
            <tr>
				<td>国籍:</td>
				<td><input id="nationality" name="nationality" type="text" data-options="validType:['length[0,10]']" class="textbox easyui-validatebox"value="${bean.nationality}"/></td>
				<td>语言:</td>
				<td><input id="language" name="language" type="text" data-options="validType:['length[0,10]']" class="textbox easyui-validatebox"value="${bean.language}"/></td>
           
				<td>母亲姓氏:</td>
				<td><input id="motherFiratName" name="motherFiratName" type="text" data-options="validType:['length[0,10]']" class="textbox easyui-validatebox"value="${bean.motherFiratName}"/></td>
				<td>婚姻状况:</td>
				<td>
					<select class="easyui-combobox" name="marriage" id="marriage" style="width:152px;" editable="false"/>
				</td>
            </tr>
            <tr>
				<td><SPAN style="color:red">*</SPAN>证件类型:</td>
				<td>
					<select class="easyui-combobox" name="idType" id="idType" data-options="required:true" style="width:152px;" editable="false"></select>	
				</td>
				<td><SPAN style="color:red">*</SPAN>证件号码:</td>
				<td><input id="idNo" name="idNo" type="text" data-options="required:true,validType:['length[0,18]','idNo']" class="textbox easyui-validatebox"value="${bean.idNo}"/></td>
            
				<td>签发日期:</td>
				<td><input id="idIssueDate" name="idIssueDate" editable="false" class="easyui-datebox" value="${bean.idIssueDateStr}"/></td>
				<td>失效日期:</td>
				<td><input id="idExpiryDate" name="idExpiryDate" editable="false" class="easyui-datebox" value="${bean.idExpiryDateStr}"/></td>
            </tr>
            <tr>
				<td>签发机关:</td>
				<td colspan="3"><input id="idIssueGov" name="idIssueGov" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox"value="${bean.idIssueGov}"/></td>
				<td>学历:</td>
				<td>
					<input id="education" name="education" type="text" data-options="validType:['length[0,10]']" class="easyui-combobox" value="${bean.education}"/>
				</td>
				<td>职业:</td>
				<td><input id="job" name="job" type="text" data-options="validType:['length[0,18]']" class="textbox easyui-validatebox"value="${bean.job}"/></td>
            </tr>
            <tr>
				<td>一级行业代码:</td>
				<td><input id="industry1" name="industry1" type="text" editable="false"  class="textbox easyui-combobox" value="${bean.industry1}"/></td>
          
				<td>二级行业代码:</td>
				<td><input id="industry2" name="industry2" type="text" editable="false" class="textbox easyui-combobox" value="${bean.industry2}"/></td>
           
				<td>三级行业代码:</td>
				<td><input id="industry3" name="industry3" type="text" editable="false"  class="textbox easyui-combobox" value="${bean.industry3}"/></td>
	            <td></td>
				<td></td>
            </tr>
            <tr>
				<td>单位名称:</td>
				<td><input id="companyName" name="companyName" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.companyName}"/></td>
				<td>工作年限:</td>
				<td><input id="workYears" name="workYears" type="text" editable="false" data-options="min:0,precision:0" class="textbox easyui-numberbox"value="${bean.workYears}"/></td>
            
				<td>单位规模:</td>
				<td>
				<select id="companyScale" editable="false" name="companyScale" class="easyui-combobox">
					<option value="" <c:if test="${bean.companyScale == ''}">selected="selected"</c:if>>...</option>
					<option value="0" <c:if test="${bean.companyScale == '0'}">selected="selected"</c:if>>100人以下</option>
					<option value="1" <c:if test="${bean.companyScale == '1'}">selected="selected"</c:if>>100-500人</option>
					<option value="2" <c:if test="${bean.companyScale == '2'}">selected="selected"</c:if>>500-1000人</option>
					<option value="3" <c:if test="${bean.companyScale == '3'}">selected="selected"</c:if>>1000-3000人</option>
					<option value="4" <c:if test="${bean.companyScale == '4'}">selected="selected"</c:if>>3000人以上</option>
				</select>
				</td>
				<td>职务:</td>
				<td><input id="jobDuty" name="jobDuty" type="text" data-options="validType:['length[0,18]']" class="textbox easyui-validatebox"value="${bean.jobDuty}"/></td>
                </tr>
            <tr>
				<td>固定电话:</td>
				<td><input id="phone" name="phone" type="text" data-options="validType:['length[0,18]']" class="textbox easyui-validatebox"value="${bean.phone}"/></td>
				<td>移动电话:</td>
				<td><input id="mobile" name="mobile" type="text" data-options="validType:['length[0,18]']" class="textbox easyui-validatebox"value="${bean.mobile}"/></td>
				<td>EMAIL:</td>
				<td colspan="3"><input id="email" name="email" type="text" data-options="validType:['email','length[0,50]']" class="textbox easyui-validatebox"value="${bean.email}"/></td>
            </tr>
            <tr>
				<td>居住地址-省:</td>
				<td><input id="addProvince" name="addProvince" class="easyui-combobox" value="${bean.addProvince}" editable="false" /></td>
            
				<td>居住地址-市:</td>
				<td><input id="addCity" name="addCity"  class="textbox easyui-combobox" value="${bean.addCity}" editable="false" /></td>
           
				<td>居住地址-区县:</td>
				<td><input id="addCounty" name="addCounty"  class="textbox easyui-combobox" value="${bean.addCounty}" editable="false" /></td>
            
				<td>居住地址:</td>
				<td><input id="address" name="address" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox"value="${bean.address}"/></td>
            </tr>
            <tr>
				<td>邮编:</td>
				<td><input id="postcode" name="postcode" type="text" data-options="validType:['ZIP']" class="textbox easyui-validatebox"value="${bean.postcode}"/></td>
            
				<td>家庭情况:</td>
				<td>
					<select class="easyui-combobox" name="family" id="family" style="width:152px;" editable="false"></select>
				</td>
				<td>家庭收入:</td>
				<td>
					<select class="easyui-combobox" name="familyIncome" id="familyIncome" style="width:152px;" editable="false"></select>
				</td>
			
				<td>文件接收方式:</td>
				<td>
				<input id="fileReceive" name="fileReceive" type="radio" value='01' <c:if test="${bean.fileReceive=='01'}">checked='checked'</c:if> />信件
				<input id="fileReceive" name="fileReceive" type="radio" value="02" <c:if test="${bean.fileReceive=='02'}">checked='checked'</c:if> />电子邮件(感谢支持低碳生活)
				<input id="fileReceive" name="fileReceive" type="radio" value="03" <c:if test="${bean.fileReceive=='03'}">checked='checked'</c:if> />两者都选
				</td>
            </tr>
            <tr>
				<td>资源需求:</td>
				<td colspan="7"><input id="requirements" name="requirements" type="text" data-options="validType:['length[0,200]']" class="textbox easyui-validatebox"value="${bean.requirements}"/></td>
            </tr>
        </tbody>
    </table>
</body>

<script type="text/javascript">

$(document).ready(function(){
	//填充select数据 婚姻
	//var tsurl="sys/datadictionary/listjason.do?keyName=marriage";
	$("#marriage").combobox("clear");
	$('#marriage').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.marriage
	});
	$('#marriage').combobox('select', '${bean.marriage}');
	
	//填充select数据 学历
	$("#education").combobox("clear");
	$('#education').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.education
	});

	//填充select数据 个人证件类型
	//var tsurl="sys/datadictionary/listjason.do?keyName=personidtype";
	$("#idType").combobox("clear");
	$('#idType').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.personidtype
	});
	$('#idType').combobox('select', '${bean.idType}');

	//填充select数据 家庭情况
	//var tsurl="sys/datadictionary/listjason.do?keyName=familyinfo";
	$("#family").combobox("clear");
	$('#family').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.familyinfo
	});
	$('#family').combobox('select', '${bean.family}');
	
	//填充select数据 家庭收入
	//var tsurl="sys/datadictionary/listjason.do?keyName=familyincome";
	$("#familyIncome").combobox("clear");
	$('#familyIncome').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.familyincome
	});
	$('#familyIncome').combobox('select', '${bean.familyIncome}');
	//填充select数据 性别
	//var tsurl="sys/datadictionary/listjason.do?keyName=gender";
	$("#gender").combobox("clear");
	$('#gender').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.gender	
	});
	$('#gender').combobox('select', '${bean.gender}');

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
	$('#addCounty').combobox({url: countyurl,valueField: 'keyProp',textField: 'keyValue'});
	$('#addCounty').combobox('select', '${bean.addCounty}');
	
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
})
$('#addCity').combobox({
    onChange: function(newValue, oldValue){
		$('#addCounty').combobox('clear');
		var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
		$('#addCounty').combobox('reload',countyurl);
    }
})

$('#industry1').combobox({
    onChange: function(newValue, oldValue){
		$('#industry2').combobox('clear');
		$('#industry3').combobox('clear');
		var industry2url = "sys/datadictionary/listjason.do?keyName=industry2&parentKeyProp=" + newValue;
		$('#industry2').combobox('reload',industry2url);
    }
});

$('#industry2').combobox({
    onChange: function(newValue, oldValue){
		$('#industry3').combobox('clear');
		var industry3url = "sys/datadictionary/listjason.do?keyName=industry3&parentKeyProp=" + newValue;
		$('#industry3').combobox('reload',industry3url);
    }
});

</script>
</html>
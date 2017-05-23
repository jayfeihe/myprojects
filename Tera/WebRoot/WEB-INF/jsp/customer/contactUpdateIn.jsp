<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
</head>
<body>
<table>
    <tbody>
        <tr>
            <input id="contactId" name="contactId" type="hidden" size="35" value="${ contact.id}" />
            <td>
                姓名:
            </td>
            <td>
                <input id="contactName" name="contactName" type="text" data-options="required:true,validType:['length[0,10]']" class="textbox easyui-validatebox"value="${contact.name}"/>
            </td>
        </tr>
        <tr>
            <td>
                英文名:
            </td>
            <td>
                <input id="contactEngName" name="contactEngName" type="text" data-options="validType:['length[0,10]']" class="textbox easyui-validatebox" value="${contact.engName}"/>
            </td>
            <td>
                性别:
            </td>
            <td>
				<input id="contactGender" name="contactGender" style="width:152px;"   class="easyui-combobox"   editable="false" value="${contact.gender}" />
            </td>
        </tr>
        <tr>
            <td>
                生日:
            </td>
            <td>
                <input id="contactBirthday" name="contactBirthday" type="text" data-options="validType:['length[0,10]']" class="textbox easyui-datebox" editable="false" value="${contact.birthday}"/>
            </td>
            <td>
                证件类型:
            </td>
            <td>
            	<input id="contactIdType" name="contactIdType"  data-options="required:true" class="easyui-combobox" style="width:152px;" editable="false"  value="${contact.idType}"/>
            </td>
        </tr>
        <tr>
            <td>
                证件号码:
            </td>
            <td>
                <input id="contactIdNo" name="contactIdNo" type="text" data-options="required:true,validType:['length[0,18]','idNo']" class="textbox easyui-validatebox"value="${contact.idNo}"/>
            </td>
            <td>
                签发日期:
            </td>
            <td>
                <input id="contactIdIssueDate" name="contactIdIssueDate" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-datebox" editable="false" value="${contact.idIssueDateStr}"/>
            </td>
        </tr>
        <tr>
            <td>
                有效期:
            </td>
            <td>
                <input id="contactIdExpiryDate" name="contactIdExpiryDate" type="text" editable="false" data-options="validType:['length[0,100]']" class="textbox easyui-datebox"value="${contact.idExpiryDateStr}"/>
            </td>
            <td>
                签发机关:
            </td>
            <td>
                <input id="contactIdIssueGov" name="contactIdIssueGov" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox"value="${contact.idIssueGov}"/>
            </td>
        </tr>
        <tr>
            <td>
                移动电话:
            </td>
            <td>
                <input id="contactMobile" name="contactMobile" type="text" data-options="validType:['length[0,18]']" class="textbox easyui-validatebox"value="${contact.mobile}"/>
            </td>
            <td>
                固定电话:
            </td>
            <td>
                <input id="contactPhone" name="contactPhone" type="text" data-options="validType:['length[0,18]']" class="textbox easyui-validatebox"value="${contact.phone}"/>
            </td>
        </tr>
        <tr>
            <td>
                E-mail:
            </td>
            <td>
                <input id="contactEmail" name="contactEmail" type="text" data-options="validType:['email','length[0,50]']" class="textbox easyui-validatebox"value="${contact.email}"/>
            </td>
            <td>
                关系:
            </td>
            <td>
                <input id="contactRelation" name="contactRelation" type="text" data-options="validType:['length[0,10]']" class="textbox easyui-validatebox"value="${contact.relation}"/>
            </td>
        </tr>
        <tr>
            <td>
                省:
            </td>
            <td>
                <input id="contactAddProvice" name="contactAddProvice" type="text" data-options="validType:['length[0,10]']"  editable="false"  class="textbox easyui-combobox"value="${contact.addProvince}"/>
            </td>
            <td>
                市:
            </td>
            <td>
                <input id="contactAddCity" name="contactAddCity" type="text" data-options="validType:['length[0,10]']"   editable="false" class="textbox easyui-combobox"value="${contact.addCity}"/>
            </td>
            <td>
                区县:
            </td>
            <td>
                <input id="contactAddCounty" name="contactAddCounty" type="text" data-options="validType:['length[0,10]']" editable="false" class="textbox easyui-combobox"value="${contact.addCounty}"/>
            </td>
            <td>
                地址:
            </td>
            <td>
                <input id="contactAddress" name="contactAddress" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${contact.address}"/>
            </td>
        </tr>
		<tr>
            <td>
                邮编:
            </td>
            <td>
                <input id="contactPostcode" name="contactPostcode" type="text" data-options="validType:['ZIP']" class="textbox easyui-validatebox"value="${contact.postcode}"/>
            </td>
        </tr>
    </tbody>
</table>
</body>
<script type="text/javascript">
$(document).ready(function(){
	//填充select数据 性别
	//var tsurl="sys/datadictionary/listjason.do?keyName=gender";
	$("#contactGender").combobox("clear");
	$('#contactGender').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.gender
	});
	$('#contactGender').combobox('select', '${contact.gender}');
	
	//填充select数据 个人证件类型
	//var tsurl="sys/datadictionary/listjason.do?keyName=personidtype";
	$("#contactIdType").combobox("clear");
	$('#contactIdType').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.personidtype
	});
	$('#contactIdType').combobox('select', '${contact.idType}');	
	
	//填充select数据 省份
	var provinceurl = "sys/datadictionary/listjason.do?keyName=province";
	$("#contactAddProvice").combobox("clear");
	$('#contactAddProvice').combobox({url: provinceurl,valueField: 'keyProp',textField: 'keyValue'});
	$('#contactAddProvice').combobox('select', '${contact.addProvince}');

	//填充select数据 市
	var province = $('#contactAddProvice').combobox('getValue');
	var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(province);
	$("#contactAddCity").combobox("clear");
	$('#contactAddCity').combobox({url: cityurl,valueField: 'keyProp',textField: 'keyValue'});
	$('#contactAddCity').combobox('select', '${contact.addCity}');

	//填充select数据 区县
	var city = $('#contactAddCity').combobox('getValue');
	var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(city);
	$("#contactAddCounty").combobox("clear");
	$('#contactAddCounty').combobox({url: countyurl, valueField: 'keyProp',textField: 'keyValue'});
	$('#contactAddCounty').combobox('select', '${contact.addCounty}');

});
	
$('#contactAddProvice').combobox({
    onChange: function(newValue, oldValue){
		$('#contactAddCity').combobox('clear');
		$('#contactAddCounty').combobox('clear');
		var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
		$('#contactAddCity').combobox('reload',cityurl);
    }
});

$('#contactAddCity').combobox({
    onChange: function(newValue, oldValue){
		$('#contactAddCounty').combobox('clear');
		var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
		$('#contactAddCounty').combobox('reload',countyurl);
    }
});

</script>
</html>

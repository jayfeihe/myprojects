<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div id="appInfoReadDiv">
<table id="appInfo">
	<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>申请信息</strong></div>
	<hr color="#D3D3D3" width="100%" size="2" />
	<tr>
		<td>进件单位:</td>
		<td><input id="orgId" name="houseApp.orgId" type="text" data-options="required:true,validType:['length[0,50]']" 
		class="textbox easyui-validatebox" value="${bean.orgId}"/></td>
		<td>进件日期:</td>
		<td><input id="inputTime" name="houseApp.inputTime" type="text" data-options="required:true,validType:['length[0,50]']" 
		class="textbox easyui-datebox" value="${bean.inputTimeStr}"/></td>
	</tr>
	<tr>
		<td>借款金额:</td>
		<td><input id="amount" name="houseApp.amount" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
		class="textbox easyui-numberbox" value="${bean.amount/10000}" style="width:128px;"/>万元</td>
		<td>借款期限:</td>
		<td><input id="period" name="houseApp.period" type="text" editable="false"  data-options="required:true,min:0,precision:0" 
		class="textbox easyui-numberbox"  value="${bean.period}" editable="false" style="width:128px;"/>个月</td>
	</tr>
	<tr>
		<td align="right">渠道:</td>
		<td><input id="belongChannel" name="houseApp.belongChannel" type="text" 
		class="easyui-combobox" editable="false" value="${bean.belongChannel}"/></td>
		<td align="right">产品:</td>
		<td><input id="product" name="houseApp.product" type="text" data-options="required:true,validType:['length[0,50]']" 
		class="easyui-combobox"  value="${bean.product}" editable="false"/></td>
	</tr>
	<tr>
		<td>月还款额:</td>
		<td><input id="monthlyPayments" name="houseApp.monthlyPayments" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
		class="textbox easyui-numberbox" value="${bean.monthlyPayments}" style="width:130px;"/>元</td>
	</tr>
	<tr>
		<td>借款用途:</td>
		<td colspan="3"><input id="useage1" name="houseApp.useage1" type="text" data-options="required:true,validType:['length[0,2]']" 
		class="easyui-combobox"  value="${bean.useage1}" editable="false"/>
		<input id="useage2" name="houseApp.useage2" type="text" data-options="required:true,validType:['length[0,10]']" 
		class="easyui-combobox"  value="${bean.useage2}" editable="false"/></td>
	</tr>
</table>
<table id="basicInfo">
	<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>个人信息</strong></div>
<hr color="#D3D3D3" width="100%" size="2" />
	<tr>
		<td>姓名:</td>
		<td><input id="name" name="houseApp.name" type="text" data-options="required:true,validType:['length[0,50]']" 
			class="textbox easyui-validatebox" value="${bean.name}"/></td>
		<td>证件号码:</td>
		<td><input id="idNo" name="houseApp.idNo" type="text" data-options="required:true,validType:['length[0,50]']" 
		class="textbox easyui-validatebox" value="${bean.idNo}"/></td>
	</tr>	
	<tr>
		<td>性别:</td>
		<td><input id="sex" name="houseApp.sex" type="text" data-options="required:true,validType:['length[0,2]']" 
		class="textbox easyui-validatebox" value="${bean.sex}"/></td>
		<td>学历:</td>
		<td><input id="diploma" name="houseApp.diploma" type="text" data-options="required:true,validType:['length[0,10]']" 
		class="easyui-combobox" value="${bean.diploma}" editable="false"/></td>
	</tr>	
	<tr>	
		<td>婚姻状况:</td>
		<td><input id="marriage" name="houseApp.marriage" type="text" data-options="required:true,validType:['length[0,2]']" 
		class="easyui-combobox" value="${bean.marriage}" editable="false"/></td>
		
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
	</tr>
	<c:if test="${bean.marriage eq '02'}">
		<tr>
			<td>配偶姓名:</td>
			<td><input id="spouseName" type="text" value="${bean.spouseName}" disabled="disabled" class="textbox"/></td>
			<td>配偶身份证号:</td>
			<td><input id="spouseIdNo" type="text" value="${bean.spouseIdNo}" disabled="disabled" class="textbox"/></td>
		</tr>
	</c:if>
	<tr>
		<td>户籍:</td>
		<td colspan="3"><input id="kosekiProvince" name="houseApp.kosekiProvince" type="text" data-options="required:true,validType:['length[0,10]']" 
		class="easyui-combobox" value="${bean.kosekiProvince}" editable="false" style="width:140px;" style="width:100px;"/>省
		<input id="kosekiCity" name="houseApp.kosekiCity" type="text" data-options="required:true,validType:['length[0,10]']" 
		class="easyui-combobox" value="${bean.kosekiCity}" editable="false" style="width:140px;" style="width:100px;"/>市</td>
	</tr>
	<tr>
		<td>住宅电话:</td>
		<td><input id="phone" name="houseApp.phone" type="text" data-options="required:true,validType:['length[0,50]']" 
		class="textbox easyui-validatebox" value="${bean.phone}"/></td>
	</tr>
	<tr>
		<td>居住地址:</td>
		<td colspan="3"><input id="addProvince" name="houseApp.addProvince" type="text" data-options="required:true,validType:['length[0,10]']" 
		class="easyui-combobox" value="${bean.addProvince}" editable="false" style="width:140px;"/>省
		<input id="addCity" name="houseApp.addCity" type="text" data-options="required:true,validType:['length[0,10]']" 
		class="easyui-combobox" value="${bean.addCity}" editable="false" style="width:140px;"/>市</td>
	</tr>
	<tr>
		<td></td>
		<td colspan="3"><input id="addCounty" name="houseApp.addCounty" type="text" data-options="required:true,validType:['length[0,10]']" 
		class="easyui-combobox" value="${bean.addCounty}" editable="false" style="width:128px;"/>区县
		<input id="address" name="houseApp.address" type="text" data-options="required:true,validType:['length[0,100]']" 
		class="textbox easyui-validatebox" value="${bean.address}" editable="false"/></td>
	</tr>
	<tr>
		<td>手机号:</td>
		<td><input id="mobile" name="houseApp.mobile" type="text" data-options="required:true,validType:['mobile']" 
		class="textbox easyui-validatebox" value="${bean.mobile}"/></td>
		<td>单位名称:</td>
		<td><input id="comName" name="houseApp.comName" type="text" data-options="required:true,validType:['length[0,50]']" 
		class="textbox easyui-validatebox" value="${bean.comName}"/></td>
	</tr>
	<tr>
		<td>单位地址:</td>
		<td colspan="3"><input id="comProvince" name="houseApp.comProvince" type="text" data-options="required:true,validType:['length[0,10]']" 
		class="easyui-combobox" value="${bean.comProvince}" editable="false" style="width:140px;"/>省
		<input id="comCity" name="houseApp.comCity" type="text" data-options="required:true,validType:['length[0,10]']" 
		class="easyui-combobox" value="${bean.comCity}" editable="false" style="width:140px;"/>市</td>
	</tr>
	<tr>
		<td></td>
		<td colspan="3"><input id="comCounty" name="houseApp.comCounty" type="text" data-options="required:true,validType:['length[0,10]']" 
		class="easyui-combobox" value="${bean.comCounty}" editable="false" style="width:128px;"/>区县
		<input id="comAddress" name="houseApp.comAddress" type="text" data-options="required:true,validType:['length[0,100]']" 
		class="textbox easyui-validatebox" value="${bean.comAddress}" editable="false"/></td>
	</tr>
	<tr>
		<td>担任职务:</td>
		<td><input id="comDuty" name="houseApp.comDuty" type="text" data-options="required:true,validType:['length[0,50]']" 
		class="textbox easyui-validatebox" value="${bean.comDuty}"/></td>
		<td>单位电话:</td>
		<td><input id="comPhone" name="houseApp.comPhone" type="text" data-options="required:true,validType:['length[0,20]']" 
		class="textbox easyui-validatebox" value="${bean.comPhone}"/></td>
	</tr>
	<tr>
		<td>年收入:</td>
		<td><input id="income" name="houseApp.income" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
		class="textbox easyui-numberbox" value="${bean.income/10000}" style="width:128px;"/>万元</td>
		<td>工作年限:</td>
		<td><input id="workAge" name="houseApp.workAge" type="text" editable="false" data-options="required:true" 
		class="textbox easyui-numberbox" value="${bean.workAge}" style="width:140px;"/>年</td>
	</tr>
	<tr>
		<td>
			申请描述：
		</td>
		<td colspan="3">
			<textarea value="${bean.remark}" class="textbox easyui-validatebox"  data-options="required:true,validType:['length[0,500]']" 
				style="resize: none;width:100%;height:50px!important;background-color: #F0F0F0;" disabled="disabled">${bean.remark}</textarea>
		</td>
	</tr>
	<br/>
	<c:if test="${null != returnReasons && '' != returnReasons}">
		<tr>
		<td>
			审核退回原因:
		</td>
		<td colspan=3">
			<textarea value="${returnReasons}" 
				style="resize: none;width:625px;height:50px;background-color: #F0F0F0;" disabled="disabled">${returnReasons}</textarea>
		</td>
	</tr>
	</c:if>
</table>
</div>
<script type="text/javascript">
$.parser.parse($("#appInfoReadDiv"));
//页面加载完动作
$(document).ready(function(){
	

	//填充select数据 婚姻状况
	$("#marriage").combobox("clear");
	$('#marriage').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.marriage
	});

	//填充select数据 学历
	$("#diploma").combobox("clear");
	$('#diploma').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.education
	});
	
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
		var useage2url = "sys/datadictionary/listjason.do?keyName=creditusage2&parentKeyProp=" +useage1;
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
		
		
	
});
</script>
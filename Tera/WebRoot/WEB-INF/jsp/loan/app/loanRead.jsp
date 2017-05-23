<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.Map"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<div title="申请信息" name=redInfo style="padding:10px">
	<c:if test="${appId!=null && appId!=''}">
		<c:forEach items="${mainFlagList}" var="mainFlag" varStatus="status">
			<c:if test="${!status.first}">
				</div><div title="共同借款人(${mainFlag})" name="redInfo" style="padding:10px">
			</c:if>
			<jsp:include page="/loan/app/loanUpdateBorr.do?appId=${appId}&mainFlag=${mainFlag}&paramType=${paramType}" flush="false" />
			<c:if test="${status.first}">		
			<table id="acceptStaff">
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"> <strong>受理人员</strong></div>
				<hr />
				<tr>
					<td>&nbsp;营销人员:</td>
					<td>
						<input value="${bean.sales}" class="easyui-combobox" 
						name="sales" id="sales" data-options="required:true" 
						style="width:152px;" editable="false" />
					</td>
				</tr>
			</table>
		</c:if>	
		</c:forEach>
	</c:if>
</div>
<div title="担保物" style="padding:10px" name=redInfo >
	<jsp:include page="/loan/app/collateraList.do?appId=${appId}&paramType=${paramType}" flush="false" />
</div>
<script type="text/javascript">
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
	$("#collateralTool").hide();
	rmZhezhao();
});
	function xianshi(mainFlag){
		//var tsurl="sys/datadictionary/listjason.do?keyName=marriage";
		$("#person_"+mainFlag).find("#marriage").combobox("clear");
		$("#person_"+mainFlag).find("#marriage").combobox({
			//url:tsurl, 
			valueField:'keyProp', 
			textField:'keyValue',
			data:dataDictJson.marriage
		});
		
		//var tsurl="sys/datadictionary/listjason.do?keyName=personidtype";
		$("#person_"+mainFlag).find("#idType").combobox("clear");
		$("#person_"+mainFlag).find("#idType").combobox({
			//url:tsurl, 
			valueField:'keyProp', 
			textField:'keyValue',
			data:dataDictJson.personidtype
		});
		
		//var tsurl="sys/datadictionary/listjason.do?keyName=personidtype";
		$("#personSpouseInfo_"+mainFlag).find("#contactIdType").combobox("clear");
		$("#personSpouseInfo_"+mainFlag).find("#contactIdType").combobox({
			//url:tsurl, 
			valueField:'keyProp', 
			textField:'keyValue',
			data:dataDictJson.personidtype
		});
		
		//var tsurl="sys/datadictionary/listjason.do?keyName=loanusage";
		$("#personVal_"+mainFlag).find("#useage").combobox("clear");
		$("#personVal_"+mainFlag).find("#useage").combobox({
			//url:tsurl, 
			valueField:'keyProp', 
			textField:'keyValue',
			data:dataDictJson.loanusage
		});
		
		//个人信息填充
		//填充select数据 省份
		var provinceurl = "sys/datadictionary/listjason.do?keyName=province";
		$("#person_"+mainFlag).find("#addProvince").combobox("clear");
		$("#person_"+mainFlag).find('#addProvince').combobox({url: provinceurl,valueField: 'keyProp',textField: 'keyValue'});
		
		//填充select数据 市
		var province = $("#person_"+mainFlag).find('#addProvince').combobox('getValue');
		var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(province);
		$("#person_"+mainFlag).find("#addCity").combobox("clear");
		$("#person_"+mainFlag).find('#addCity').combobox({url: cityurl,valueField: 'keyProp',textField: 'keyValue'});
		//填充select数据 区县
		var city = $("#person_"+mainFlag).find('#addCity').combobox('getValue');
		var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(city);
		$("#person_"+mainFlag).find("#addCounty").combobox("clear");
		$("#person_"+mainFlag).find('#addCounty').combobox({url: countyurl, valueField: 'keyProp',textField: 'keyValue'});
		
		//var companyTypeurl = "sys/datadictionary/listjason.do?keyName=companyType";
		$("#orgInfo_"+mainFlag).find("#orgCompanyType").combobox("clear");
		$("#orgInfo_"+mainFlag).find("#orgCompanyType").combobox({
			//url: companyTypeurl,
			valueField: 'keyProp',
			textField: 'keyValue',
			data:dataDictJson.businessattr
		});
		
		var industry1url = "sys/datadictionary/listjason.do?keyName=industry1";
		$("#orgInfo_"+mainFlag).find("#orgIndustry1").combobox("clear");
		$("#orgInfo_"+mainFlag).find("#orgIndustry1").combobox({url: industry1url,valueField: 'keyProp',textField: 'keyValue'});
		//填充select数据 二级行业
		var industry1 =$("#orgInfo_"+mainFlag).find('#orgIndustry1').combobox('getValue');
		var industry2url = "sys/datadictionary/listjason.do?keyName=industry2&parentKeyProp=" + industry1;
		$("#orgInfo_"+mainFlag).find("#orgIndustry2").combobox("clear");
		$("#orgInfo_"+mainFlag).find('#orgIndustry2').combobox({url: industry2url,valueField: 'keyProp',textField: 'keyValue'});
		
		//填充select数据 三级行业
		var industry2 = $("#orgInfo_"+mainFlag).find('#orgIndustry2').combobox('getValue');
		var industry3url = "sys/datadictionary/listjason.do?keyName=industry3&parentKeyProp=" + industry2;
		$("#orgInfo_"+mainFlag).find("#orgIndustry3").combobox("clear");
		$("#orgInfo_"+mainFlag).find('#orgIndustry3').combobox({url: industry3url, valueField: 'keyProp', textField: 'keyValue' });
		//填充 机构 
		//var tsurl="sys/datadictionary/listjason.do?keyName=companyidtype";
		$("#groupInfo_"+mainFlag).find("#orgIdType").combobox("clear");
		$("#groupInfo_"+mainFlag).find("#orgIdType").combobox({
			//url:tsurl, 
			valueField:'keyProp', 
			textField:'keyValue',
			data:dataDictJson.companyidtype
		});
		
		//tsurl="sys/datadictionary/listjason.do?keyName=personidtype";
		$("#groupInfo_"+mainFlag).find("#fdorgIdType").combobox("clear");
		$("#groupInfo_"+mainFlag).find("#fdorgIdType").combobox({
			//url:tsurl, 
			valueField:'keyProp', 
			textField:'keyValue',
			data:dataDictJson.personidtype
		});
		$("#groupInfo_"+mainFlag).find("#sqOrgIdType").combobox("clear");
		$("#groupInfo_"+mainFlag).find("#sqOrgIdType").combobox({
			//url:tsurl, 
			valueField:'keyProp', 
			textField:'keyValue',
			data:dataDictJson.personidtype
		});
		$("#groupInfo_"+mainFlag).find("#cwOrgIdType").combobox("clear");
		$("#groupInfo_"+mainFlag).find("#cwOrgIdType").combobox({
			//url:tsurl, 
			valueField:'keyProp', 
			textField:'keyValue',
			data:dataDictJson.personidtype
		});
		
		
		$("#person_"+mainFlag).find('#addProvince').combobox({
		    onChange: function(newValue, oldValue){
				$("#person_"+mainFlag).find('#addCity').combobox('clear');
				$("#person_"+mainFlag).find('#addCounty').combobox('clear');
				var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
				$("#person_"+mainFlag).find('#addCity').combobox('reload',cityurl); 
				
		    }
		});
		$("#person_"+mainFlag).find('#addCity').combobox({
		    onChange: function(newValue, oldValue){
				$("#person_"+mainFlag).find('#addCounty').combobox('clear');
				var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
				$("#person_"+mainFlag).find('#addCounty').combobox('reload',countyurl); 
		    }
		});
		//填充select数据 一级行业
		var industry1url = "sys/datadictionary/listjason.do?keyName=industry1";
		$("#person_"+mainFlag).find("#industry1").combobox("clear");
		$("#person_"+mainFlag).find('#industry1').combobox({url: industry1url,valueField: 'keyProp',textField: 'keyValue'});
		
		//填充select数据 二级行业
		var industry1 =$("#person_"+mainFlag).find('#industry1').combobox('getValue');
		var industry2url = "sys/datadictionary/listjason.do?keyName=industry2&parentKeyProp=" + industry1;
		$("#person_"+mainFlag).find("#industry2").combobox("clear");
		$("#person_"+mainFlag).find('#industry2').combobox({url: industry2url,valueField: 'keyProp',textField: 'keyValue'});
		
		//填充select数据 三级行业
		var industry2 = $("#person_"+mainFlag).find('#industry2').combobox('getValue');
		var industry3url = "sys/datadictionary/listjason.do?keyName=industry3&parentKeyProp=" + industry2;
		$("#person_"+mainFlag).find("#industry3").combobox("clear");
		$("#person_"+mainFlag).find('#industry3').combobox({url: industry3url, valueField: 'keyProp', textField: 'keyValue' });
		
		//个人信息填充
		//填充select数据 省份
		var provinceurl = "sys/datadictionary/listjason.do?keyName=province";
		$("#personSpouseInfo_"+mainFlag).find("#contactAddProvice").combobox("clear");
		$("#personSpouseInfo_"+mainFlag).find('#contactAddProvice').combobox({url: provinceurl,valueField: 'keyProp',textField: 'keyValue'});
		
		//填充select数据 市
		var province = $("#personSpouseInfo_"+mainFlag).find('#contactAddProvice').combobox('getValue');
		var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(province);
		$("#personSpouseInfo_"+mainFlag).find("#contactAddCity").combobox("clear");
		$("#personSpouseInfo_"+mainFlag).find('#contactAddCity').combobox({url: cityurl,valueField: 'keyProp',textField: 'keyValue'});
		//填充select数据 区县
		var city = $("#personSpouseInfo_"+mainFlag).find('#contactAddCity').combobox('getValue');
		var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(city);
		$("#personSpouseInfo_"+mainFlag).find("#contactAddCounty").combobox("clear");
		$("#personSpouseInfo_"+mainFlag).find('#contactAddCounty').combobox({url: countyurl, valueField: 'keyProp',textField: 'keyValue'});
		
		$("#personSpouseInfo_"+mainFlag).find('#contactAddProvice').combobox({
		    onChange: function(newValue, oldValue){
				$("#personSpouseInfo_"+mainFlag).find('#contactAddCity').combobox('clear');
				$("#personSpouseInfo_"+mainFlag).find('#contactAddCounty').combobox('clear');
				var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
				$("#personSpouseInfo_"+mainFlag).find('#contactAddCity').combobox('reload',cityurl); 
				
		    }
		});
		$("#personSpouseInfo_"+mainFlag).find('#contactAddCity').combobox({
		    onChange: function(newValue, oldValue){
				$("#personSpouseInfo_"+mainFlag).find('#contactAddCounty').combobox('clear');
				var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
				$("#personSpouseInfo_"+mainFlag).find('#contactAddCounty').combobox('reload',countyurl); 
		    }
		});
		//人员信息行业代码
	 	$("#person_"+mainFlag).find('#industry1').combobox({
	    	onChange: function(newValue, oldValue){
				$("#person_"+mainFlag).find('#industry2').combobox('clear');
				$("#person_"+mainFlag).find('#industry3').combobox('clear');
				var industry2url = "sys/datadictionary/listjason.do?keyName=industry2&parentKeyProp=" + newValue;
				$("#person_"+mainFlag).find('#industry2').combobox('reload',industry2url);
	    	}	
		});
		$("#person_"+mainFlag).find('#industry2').combobox({
		    onChange: function(newValue, oldValue){
				$("#person_"+mainFlag).find('#industry3').combobox('clear');
				var industry3url = "sys/datadictionary/listjason.do?keyName=industry3&parentKeyProp=" + newValue;
				$("#person_"+mainFlag).find('#industry3').combobox('reload',industry3url);
		    }
		});
		//机构信息行业代码
	 	$("#orgInfo_"+mainFlag).find('#orgIndustry1').combobox({
	    	onChange: function(newValue, oldValue){
				$("#orgInfo_"+mainFlag).find('#orgIndustry2').combobox('clear');
				$("#orgInfo_"+mainFlag).find('#orgIndustry3').combobox('clear');
				var industry2url = "sys/datadictionary/listjason.do?keyName=industry2&parentKeyProp=" + newValue;
				$("#orgInfo_"+mainFlag).find('#orgIndustry2').combobox('reload',industry2url);
	    	}	
		});
		$("#orgInfo_"+mainFlag).find('#orgIndustry2').combobox({
		    onChange: function(newValue, oldValue){
				$("#orgInfo_"+mainFlag).find('#orgIndustry3').combobox('clear');
				var industry3url = "sys/datadictionary/listjason.do?keyName=industry3&parentKeyProp=" + newValue;
				$("#orgInfo_"+mainFlag).find('#orgIndustry3').combobox('reload',industry3url);
		    }
		});
		$("#person_"+mainFlag).find('#marriage').combobox({
			    onChange: function(marriageVal){
					if(marriageVal == '02'){
						$("#marriageDiv"+mainFlag).show();//显示div   
					}
					if(marriageVal == '01' || marriageVal =='03'){
						$("#marriageDiv"+mainFlag).hide();//隐藏div  
					}
		    	}
			});
		}
	$(document).ready(function(){
		<c:if test="${appId!=null && appId!=''}">
		<c:forEach items="${mainFlagList}" var="mainFlag" varStatus="status">
			xianshi('${mainFlag}');
		</c:forEach></c:if><c:if test="${appId==null}" >
			xianshi('0');
		</c:if>
		
		//var tsurl="sys/datadictionary/listjason.do?keyName=loanchannel";
	 	$("#appChannel").combobox("clear");
	 	$('#appChannel').combobox({
	 		//url:tsurl, 
	 		valueField:'keyProp', 
	 		textField:'keyValue',
	 		data:dataDictJson.loanchannel
	 	});
	 	
	 	tsurl="product/hedao/listjason.do?type=2";
		$("#product").combobox("clear");
		$('#product').combobox({url:tsurl, valueField:'name', textField:'name'});
		
		$('#product').combobox({
			    onChange: function(product){
					$.ajax({
					type : "POST",
					url : "product/hedao/listjason.do?name="+encodeURI(product),
					success : function(data) {
						$('#interestRate').val(data[0].interestRate);
						$('#period').val(data[0].period);
					}
				})
		    }
		});
		//初始化 营销人员
		var sales="sales/listjason.do?state=1&orgId=${login_org.orgId}";
		$('#sales').combobox({url:sales, valueField:'staffNo', textField:'name'});
		
		
		//嵌套的回显信息。 设置为disabled 只用于查看， 删除 EasyUI 表单验证
		
		var redinfo=$("div[name='redInfo']");
		redinfo.find("input").attr("disabled", "disabled");
		redinfo.find("select").attr("disabled", "disabled");
		//删除  担保物动态添加 操作
		redinfo.find("img[src='img/addItem.gif']").remove();
		redinfo.find("img[src='img/deleteItem2.png']").remove();
// 		redinfo.find(".combo-arrow").removeAttr("onclick");
		
		redinfo.find(".easyui-validatebox").validatebox('disableValidation');//将验证取消
		redinfo.find(".easyui-combobox").combo('disableValidation');//将验证取消
		redinfo.find(".easyui-numberbox").numberbox('disableValidation');//将验证取消
		
		redinfo.find(".easyui-combobox").combo('disable');//将验证取消
		redinfo.find(".easyui-numberbox").numberbox('disable');//将验证取消
		redinfo.find(".easyui-numberspinner").numberspinner('disable');//将验证取消
		redinfo.find(".easyui-datebox").datebox('disable');//将验证取消
		
		
	});
	
</script>

<script type="text/javascript">
function getCustomer(){
	//不能删除
}
</script>


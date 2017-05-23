<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>" />
<title>信用贷款复核</title>
	<link href="css/global.css" type="text/css" rel="stylesheet" />
	<link href="css/icon.css" type="text/css" rel="stylesheet" />
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet" />
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
			<p class="title"><a href="javascript:void(0);">复核</a></p>
			<div class="content">
				<div class="easyui-layout" id="easyui_layout"
					style="width: 100%; height: 730px;" data-options="fit:true">
					<div data-options="region:'center',split:true,border:false">
						<div class="easyui-tabs" data-options="fit:true">
							<div title="影像资料"
								data-options="href:'${basePath}img/imgSlidePath.do?appId=${bean.appId}'"
								style="width: 100%; padding: 10px"></div>
						</div>
					</div>
					<div data-options="region:'east',split:true,border:false" style="width: 800px;">
						<form id="updateForm">
							<input type="hidden" name="id" value="${bean.id}" />
							<input type="hidden" name="appId" value="${bean.appId}" />
							<div class="easyui-tabs" id="verify_tabs" style="height: 730px;" data-options="fit:true">
								<div title="合同信息" style="width: 100%; padding: 10px">
									<div id="showInfo" style="font-size: 14px;">
										<strong>合同基本信息</strong>
										<hr width="100%" size=2 color="#D3D3D3" noshade>
										<table width="100%">
											<tr>
												<td>合同编号:</td>
												<td>
													<input id="contractNo" name="contractNo" type="text" class="textbox easyui-validatebox" 
														value="${contract.contractNo}"/>
												</td>
												<td>要求放款时间:</td>
												<td>
													<input id="lendingDate" name="contract.lendingDate" type="text" editable="false" class="textbox easyui-datebox"
														value="${contract.lendingDate}" />
												<td>
											</tr>
											<tr>
												<td>客户姓名:</td>
												<td>
													<input id="name" name="bean.name" type="text" editable="false" class="textbox easyui-validatebox"
														value="${bean.name}" />
												</td>
												<td>身份证号:</td>
												<td>
													<input id="idNo" name="bean.idNo" type="text" class="textbox easyui-validatebox"
														value="${bean.idNo}" data-options="validType:['idcard']"/>
												</td>
											</tr>
											<tr>
												<td>借款金额:</td>
												<td>
													<input id="amount" name="amount" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"
														value="${jkje}" style="width: 140px;" />元
												</td>
												<td>客户实际获得金额:</td>
												<td>
													<input id="decision.amount" name="decision.amount" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"
														value="${decision.amount}" style="width: 140px;" />元
												</td>
											</tr>
											<tr>
												<td>分期数:</td>
												<td>
													<input id="period" name="contract.loanPeriod" editable="false" editable="false" class="textbox easyui-validatebox"
														value="${contract.loanPeriod}" style="width:128px;"/>个月
												</td>
												<td>月还款额:</td>
												<td>
													<input id="monthlyPayments" name="monthlyPayments" data-options="min:0,precision:2" class="textbox easyui-numberbox"
														value="${MAmount}" style="width:140px;"/>元
												</td>
											</tr>
											<tr>
												<td>渠道:</td>
												<td><input id="belongChannel" name="product.belongChannel" type="text" class="easyui-combobox" editable="false" value="${product.belongChannel}" disabled="disabled"/>
												</td>
												<td>产品:</td>
												<td>
													<input id="loanProduct" name="contract.loanProduct" editable="false" class="textbox easyui-validatebox" 
														value="${contract.loanProduct}"/>
												</td>
											</tr>
											<tr>
												<td>利息:</td>
												<td>
													<input id="loanInterestRate" name="contract.loanInterestRate" type="text" style="width: 140px;" class="textbox easyui-numberbox" 
														value="${contract.loanInterestRate}" data-options="min:0,precision:2" />%
												</td>
												<td>账户管理费:</td>
												<td>
													<input id="sreviceFeeRate2" name="product.sreviceFeeRate2" class="textbox easyui-numberbox" style="width: 140px;"
														value="${product.sreviceFeeRate2}" data-options="min:0,precision:2"/>%
												</td>
											</tr>
											<tr>
												<td>融资服务费:</td>
												<td>
													<input id="sreviceFeeRate3" name="product.sreviceFeeRate3" class="textbox easyui-numberbox" style="width: 140px;"
														data-options="min:0,precision:2"
														value="${product.sreviceFeeRate3}"/>%
												</td>
											</tr>
											<tr>
												<td>借款用途:</td>
												<td colspan="3">
													<input id="useage1" name="useage1" class="easyui-combobox" editable="false" 
														value="${bean.useage1}" disabled="disabled"/> 
													<input id="useage2" name="useage2"  class="easyui-combobox" editable="false"
														value="${bean.useage2}" disabled="disabled"/>
												</td>
											</tr>
											<tr>
												<td>开户行信息:</td>
												<td colspan="5">
													<input id="bankProvince" name="contract.bankProvince" type="text" class="easyui-combobox" 
														data-options="required:true" value="${contract.bankProvince}" style="width: 140px;" editable="false" disabled="disabled"/>
													<input id="bankCity" name="contract.bankCity" type="text" class="easyui-combobox" 
														data-options="required:true" value="${contract.bankCity}" style="width: 140px;" editable="false" disabled="disabled"/>
													<input id="bankCode" name="contract.bankCode" type="text" class="textbox easyui-combobox" data-options="required:true" value="${contract.bankCode}" editable="false" disabled="disabled"/>
													<input name="contract.bankBranch" type="text" class="textbox easyui-validatebox" 
														value="${contract.bankBranch}" style="width: 140px;" editable="false"/>（支行全称）
												</td>
											</tr>
											<tr>
												<td>银行卡号:</td>
												<td>
													<input id="bankAccount" name="contract.bankAccount" class="textbox easyui-validatebox"
														value="${contract.bankAccount}" />
												</td>
<%--												<td>开户行名称:</td>--%>
<%--												<td>--%>
<%--													<input id="bankName" name="contract.bankName" class="textbox easyui-validatebox"--%>
<%--														value="${contract.bankName}" />--%>
<%--												<td>--%>
												<td>银行账户姓名:</td>
												<td>
													<input id="bankAccountName" name="contract.bankAccountName" class="textbox easyui-validatebox" 
														value="${contract.bankAccountName}" />
											</tr>
											<tr>
												</td>
												<td>首次还款日期:</td>
												<td>
													<input class="textbox easyui-datebox" value="${scfksj}" />
												</td>
												<td>合同下载时间:</td>
												<td>
													<input id="downloadTime" name="contract.downloadTime" class="textbox easyui-datebox" editable="false"
														value="${contract.downloadTime}" />
												<td>
											</tr>
											<tr>
												<td>签约地点:</td>
												<td colspan="5">
													<input id="signProvince" name="contract.signProvince" type="text" class="textbox easyui-validatebox" 
														value="${contract.signProvince}"/>
													<input id="signCity" name="contract.signCity" type="text" class="textbox easyui-validatebox" 
														value="${contract.signCity}"/>
													<input id="signCounty" name="contract.signCounty" type="text" class="textbox easyui-validatebox" 
														value="${contract.signCounty}"/>
												</td>
											</tr>
											<c:if test="${!empty returnReasons }"> 
												<tr>
													<td>放款退回原因:</td>
													<td colspan=3">
														<textarea value="${returnReasons}" class="textbox easyui-validatebox" data-options="validType:['length[0,500]']" 
															style="resize: none;width:600px;height:50px !important;vertical-align:middle;background-color: #F0F0F0;" disabled="disabled">${returnReasons}</textarea>
													</td>
												</tr>
											</c:if>
											<c:if test="${!empty reviewReasons }"> 
												<tr>
													<td>复核退回原因:</td>
													<td colspan=3">
														<textarea value="${reviewReasons}" class="textbox easyui-validatebox" data-options="validType:['length[0,500]']" 
															style="resize: none;width:600px;height:50px !important;vertical-align:middle;background-color: #F0F0F0;" disabled="disabled">${reviewReasons}</textarea>
													</td>
												</tr>
											</c:if>
										</table>
										<div style="font-size: 14px;">
											<strong>出借人清单</strong>
											<hr width="100%" size=2 color="#D3D3D3" noshade>
												<table id="spouse" class="datatable" width="100%">	
													<jsp:include page="/credit/sign/bringTogetherList.do?creditAppId=${bean.appId}&state=display" />
												</table>
										</div>
									</div>
									<div id="" style="font-size: 14px;">
										<strong>复核</strong>
										<hr width="100%" size=2 color="#D3D3D3" noshade>
											<table>
												<tr>
													<td>
														<input type="button" value="退回" class="btn" onclick="updateFunction('back')" />
													</td>
													<td>退回原因：</td>
													<td>
														<textarea id="backMsg" name="backMsg" value="${creditVerify.returnMsg}" class="textbox easyui-validatebox"
															data-options="validType:['length[0,500]']" 
															style="resize: none;width:600px;height:50px !important;vertical-align:middle;">${creditVerify.returnMsg}</textarea>
													</td>
												</tr>
												<tr>
													<td colspan="4">
														<input type="button" value="通过" class="btn" onclick="updateFunction('submit')" />
														<input type="button" value="返回" class="btn" onclick="javascript:back()" />
													</td>
												</tr>
											</table>
									</div>

								</div>

							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
<script type="text/javascript" >

window.parent.openMask();
$(window).load(function (){
	window.parent.closeMask();
});
</script>

<script type="text/javascript">
//只读页 设置为只读
function showInfoReadOnly(){
	var redinfo=$("#showInfo");
	redinfo.find('.easyui-validatebox').attr('disabled', 'disabled');
	redinfo.find('.easyui-validatebox').validatebox('disableValidation');
	redinfo.find('.easyui-combobox').combo('disableValidation');
	redinfo.find('.easyui-numberbox').numberbox('disableValidation');
	redinfo.find('.easyui-datebox').datebox('disableValidation');
	redinfo.find('.easyui-datetimebox').datebox('disableValidation');
	redinfo.find('.easyui-combobox').combo('disable');
	redinfo.find('.easyui-numberbox').numberbox('disable');
	redinfo.find('.easyui-datebox').datebox('disable');
	redinfo.find('.easyui-datetimebox').datebox('disable');
	

	redinfo.find('div.panel div.panel-tool>a.icon-cancel').hide();
	
}
//更新保存
function updateFunction(buttonType) {
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	//弹出异步加载 遮罩
	window.parent.openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "credit/review/save.do",
		data : params + "&buttonType=" + buttonType,
		success : function(data) {
				//关闭遮罩，弹出消息框
				window.parent.closeMask();
				if ("true" == data.success) {
					$.messager.alert('消息', data.message, 'info', function() {
						//back();
						window.parent.removeTab();
					});
				} else {
					$.messager.alert('消息', data.message);
					//按钮生效
					$(".btn").removeAttr("disabled");
				}
			},
			error : function() {
				//关闭遮罩，弹出消息框
				window.parent.closeMask();
				$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
				//按钮生效
				$(".btn").removeAttr("disabled");
			}
		});
	}

	//返回
	function back() {
		//window.history.go(-1);
		window.parent.removeTab();
	}
	//页面加载完动作
$(document).ready(function(){
	
	showInfoReadOnly(); //相关信息 设置为只读
	//拖动时 调节 下拉框 宽度
	$('#easyui_layout').layout('panel', 'east').panel({
		onResize : function(w, h) {
			$("#verify_tabs").tabs('getSelected').find(".easyui-accordion").accordion("resize");
			return true;
		}
	});
				
/*
	//填充select数据 借款期限
	$("#verifylAppInfo").find("#period").combobox("clear");
	$("#verifylAppInfo").find("#period").combobox({valueField : 'keyProp',textField : 'keyValue',
		data : [{"keyProp" : "1","keyValue" : "12"}, 
		        {"keyProp" : "2","keyValue" : "18"},
				{"keyProp" : "3","keyValue" : "24"},
				{"keyProp" : "4","keyValue" : "36"}]
	});
*/

	//填充select数据 渠道
	var channelurl="channeltotal/listjason.do";
	$("#belongChannel").combobox("clear");
	$('#belongChannel').combobox({
		url:channelurl,
		valueField:'channelCode', 
		textField:'channelName'
	});

/*
	$("#verifyInfo").find("#period").combobox("clear");
	$("#verifyInfo").find('#period').combobox({
		valueField : 'keyProp',textField : 'keyValue',
		data : [{"keyProp" : "1","keyValue" : "12"}, 
		        {"keyProp" : "2","keyValue" : "18"},
				{"keyProp" : "3","keyValue" : "24"},
				{"keyProp" : "4","keyValue" : "36"}]
	});
*/
	var tsurl = "product/hedao/listjason.do?type=3";
	$("#verifyInfo").find("#product").combobox("clear");
	$("#verifyInfo").find('#product').combobox({url : tsurl,valueField : 'name',textField : 'name'});

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
	//填充select数据 开户行信息省份
    var provinceurl = "sys/datadictionary/listjason.do?keyName=bankProv";
		$("#bankProvince").combobox("clear");
		$('#bankProvince').combobox({
			url: provinceurl,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
	            $('#bankCity').combobox('clear');
	            var cityurl = "sys/datadictionary/listjason.do?keyName=bankCity&parentKeyProp=" + encodeURI(newValue);
	            $('#bankCity').combobox('reload',cityurl); 
        }
	});
    //填充select数据 开户行信息市
    var province = $('#bankProvince').combobox('getValue');
		var cityurl = "sys/datadictionary/listjason.do?keyName=bankCity&parentKeyProp=" + encodeURI(province);
		$("#bankCity").combobox("clear");
		$('#bankCity').combobox({
			url: cityurl,
			valueField: 'keyProp',
			textField: 'keyValue'
	});
	//填充select数据 银行机构
	$("#bankCode").combobox("clear");
	$('#bankCode').combobox({
		url:"sys/datadictionary/listjason.do?keyName=bankcode",
		valueField:'keyProp', 
		textField:'keyValue'
	});

});

</script>
</html>


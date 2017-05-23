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
<title>信用贷款查询</title>
<link href="img/hedaoBaiduMap/mapStyle.css" type="text/css" rel="stylesheet"/>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=FFhzMZoN2G8omALYw5GdwmVZ"></script>	
<style type="text/css">
</style>
</head>
<body>
<div id="main">
<div id="part1" class="part">
	<p class="title">
		<a href="javascript:void(0);">信用贷款查询</a>
<!-- 		<span style="float: right; padding-right: 20px;"> -->
<!-- 			<input type="button" value="返回" class="btn" onclick="javascript:back()"/> -->
<!-- 		</span> -->
<span style="padding-left: 20px; color: red;">
预审批评分等级：${bean.ruleScore}
<c:if test="${fn:contains(creditDecision1.product,'精英贷') and not empty bean.gradeRemind}">&nbsp;&nbsp;|&nbsp;&nbsp;${bean.gradeRemind}</c:if>
</span>
	</p>
<div class="content">
<div class="easyui-layout" id="easyui_layout" style="width:100%;height:730px;" data-options="fit:true">
<div data-options="region:'center',split:true,border:false" >
	<div class="easyui-tabs" data-options="fit:true,onLoad:tabsReadOnly">
		<div title="影像资料" data-options="href:'${basePath}img/imgSlidePath.do?appId=${bean.appId}'" style="width: 100%;padding:10px"></div><!--
		//<div title="申请信息" data-options="href:'${basePath}credit/app/read1.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		<div title="综合信息" data-options="href:'${basePath}credit/verify/complexInfo.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
	--></div>
</div>

<div data-options="region:'east',split:true,border:false" style="width:800px;">
<form id="updateForm" >
	<input type="hidden" name="appId" value="${bean.appId}" />
	
	<div class="easyui-tabs" id="verify_tabs" style="height:730px;" data-options="fit:true,onLoad:tabsReadOnly">
		<!--<div title="信息核查" data-options="href:'${basePath}credit/verify/infoCheck.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		<div title="影像摘要" data-options="href:'${basePath}credit/verify/imageSummary.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		<div title="信用报告" data-options="href:'${basePath}credit/verify/creditReport.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		--><div title="面审调查" data-options="href:'${basePath}credit/verify/interviewSurvey.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		  
		 <!-- 
		<div title="流程报告" data-options="href:'${basePath}credit/verify/processReport.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
	--></div>
</form>
</div>
</div>
</div>
</div>
</div>

<div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
	<img src="img/loading.gif" alt="加载中..." />
</div>

</body>
<script type="text/javascript" >
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
	rmZhezhao();
});
</script>

<script type="text/javascript">
//只读页 设置为只读
function tabsReadOnly(redinfo){

	redinfo.find("input[type='radio']").attr('disabled',true);
	redinfo.find("input[type='hidden']").attr('disabled',true);
// 	redinfo.find("input[type='button']").attr("disabled",true);
	redinfo.find("input[type='checkbox']").attr('disabled',true);
	redinfo.find("textarea").attr('disabled',true);
	redinfo.find("input[value='算']").attr('disabled',true);
	redinfo.find("input[value='全']").attr('disabled',true);

	redinfo.find('.easyui-validatebox').attr('disabled', 'disabled');
	redinfo.find('.easyui-combobox').combo('disableValidation');
	redinfo.find('.easyui-numberbox').numberbox('disableValidation');
	redinfo.find('.easyui-datebox').datebox('disableValidation');
	redinfo.find('.easyui-combobox').combo('disable');
	redinfo.find('.easyui-numberbox').numberbox('disable');
	redinfo.find('.easyui-datebox').datebox('disable');
	
	redinfo.find('div.panel div.panel-tool>a.icon-cancel').hide();
	redinfo.find('div.panel div.panel-tool>a.icon-add').hide();
	redinfo.find('div.panel div[class="panel-body accordion-body"]>table img[src$="deleteItem2.png"]').hide();
	redinfo.find('div.panel div[class="panel-body accordion-body"]>table img[src$="img/addItem.gif"]').hide();
// 	redinfo.find('div.panel div[class="panel-body accordion-body"]>table a').hide();
	
}

//切换 是否验证
function toggleValidate(objId,isValidete){
	var state=!isValidete;
	var obj=$(objId);
	obj.find('.easyui-validatebox').validatebox({novalidate:state});
	obj.find('.easyui-numberbox').validatebox({novalidate:state});
	obj.find('.easyui-datebox').datebox({novalidate:state});
}

//返回
function back(){
	window.history.go(-1);
}

//打开Loading遮罩并修改样式
function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
}
//页面加载完动作
$(document).ready(function (){
	
	if('${creditDecision1.decision}' == '02'){
		$('#noPassReasonDiv1').show();//显示div   
		$('#decisionCodeDiv1').hide();//隐藏div  
		$('#returnCodeDiv1').hide();//隐藏div 
	}else if('${creditDecision1.decision}' == '03'){
		$('#noPassReasonDiv1').show();//显示div   
		$('#decisionCodeDiv1').hide();//隐藏div  
		$('#returnCodeDiv1').hide();//隐藏div  
	}else if('${creditDecision1.decision}' == '00'){
		$('#noPassReasonDiv1').show();//显示div   
		$('#decisionCodeDiv1').hide();//隐藏div  
		$('#returnCodeDiv1').hide();//隐藏div 
	}
	
	if('${creditDecision2.decision}' == '00'){
		$('#noPassReasonDiv2').show();//显示div   
		$('#decisionCodeDiv2').hide();//隐藏div 
		$('returnCodeDiv2').hide();//隐藏div 
	}else if('${creditDecision2.decision}' == '01'){
		$('#noPassReasonDiv2').hide();//隐藏div    
		$('#decisionCodeDiv2').hide();//隐藏div 
		$('returnCodeDiv2').hide();//隐藏div  
	}else if('${creditDecision2.decision}' == '05'){
		$('#noPassReasonDiv2').hide();//隐藏div    
		$('#decisionCodeDiv2').hide();//隐藏div 
		$('returnCodeDiv2').show();//显示div  
	}else if('${creditDecision2.decision}' == '04'){
		$('#noPassReasonDiv2').hide();//隐藏div    
		$('#decisionCodeDiv2').hide();//隐藏div 
		$('returnCodeDiv2').hide();//隐藏div   
	}else{
		$('#noPassReasonDiv2').hide();//隐藏div    
		$('#decisionCodeDiv2').hide();//隐藏div 
		$('returnCodeDiv2').hide();//隐藏div  
	}
	
	if('${creditDecision3.decision}' == '00'){
		toggleValidate('#specialApprovalInfo',false);
	}else{
		toggleValidate('#specialApprovalInfo',true);			
	}
	
	if('${creditDecision3.decision}' == '' || '${creditDecision3.decision}' == '01'){
		$('#noPassReasonDiv').hide();//隐藏div 
		toggleValidate('#noPassReasonDiv',false);
		$('#returnCodeDiv').hide();//隐藏div 
		toggleValidate('#returnCodeDiv',false);
		toggleValidate('amountDiv',true);
	}else if('${creditDecision3.decision}' == '00'){
		$('#noPassReasonDiv').show();//显示div 
		toggleValidate('#noPassReasonDiv',true);
		$('#returnCodeDiv').hide();//隐藏div 
		toggleValidate('#returnCodeDiv',false);
		toggleValidate('amountDiv',false);
	}else{
		$('#noPassReasonDiv').hide();//隐藏div 
		toggleValidate('#noPassReasonDiv',false);
		$('#returnCodeDiv').show();//显示div  
		toggleValidate('#returnCodeDiv',true);
		toggleValidate('amountDiv',true);
	}
	
	//拖动时 调节 下拉框 宽度
	$('#easyui_layout').layout('panel', 'east').panel({
		onResize:function(w,h){
			$("#verify_tabs").tabs('getSelected').find(".easyui-accordion").accordion("resize");
			return true;
		  }
		});


	//填充select数据 产品
	var tsurl="product/hedao/listjason.do?type=3";
	$("#verifylAppInfo").find("#product").combobox("clear");
	$("#verifylAppInfo").find('#product').combobox({url:tsurl, valueField:'name', textField:'name'});


	//填充select数据 产品
	var tsurl="product/hedao/listjason.do?type=3";
	$("#verifyInfo").find("#product").combobox("clear");
	$("#verifyInfo").find('#product').combobox({url:tsurl, valueField:'name', textField:'name'});

	//填充select数据 产品
	var tsurl="product/hedao/listjason.do?type=3";
	$("#approvalInfo").find("#product").combobox("clear");
	$("#approvalInfo").find('#product').combobox({url:tsurl, valueField:'name', textField:'name'});

	$('#verifyInfo').find('#decision').combobox('setValue', '${creditDecision1.decision}');
	$('#approvalInfo').find('#decision1').combobox('setValue', '${creditDecision2.decision}');
	$('#specialApprovalInfo').find('#decision1').combobox('setValue', '${creditDecision3.decision}'); 

	//填充select数据 产品类型
	var tsurl="product/hedao/listjason.do?type=3";
		$("#specialApprovalInfo").find("#product").combobox("clear");
		$("#specialApprovalInfo").find('#product').combobox({url:tsurl, valueField:'name', textField:'name',
			onChange: function(productVal){
				if($('#specialApprovalInfo').find('#amount').val() != '')
					updateJe("<%=basePath%>" + "credit/verify/getYhkje.do", "fkje=" + $('#specialApprovalInfo').find('#amount').val() + "&productName=" + productVal, "yhkje");
			}	
		});
		
	//填充select数据 拒件码01
    var refusedCode01url = "sys/datadictionary/listjason.do?keyName=refusedCode01";
		$('#verifyInfo').find("#decisionCode3").combobox("clear");
		$('#verifyInfo').find('#decisionCode3').combobox({
			url: refusedCode01url,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
	            $('#verifyInfo').find('#decisionCode4').combobox('clear');
	            var refusedCode02url = "sys/datadictionary/listjason.do?keyName=refusedCode02&parentKeyProp=" + encodeURI(newValue);
	            $('#verifyInfo').find('#decisionCode4').combobox('reload',refusedCode02url); 
       		}
		});
		//填充select数据 拒件码02
		var decisionCode3 = $('#verifyInfo').find('#decisionCode3').combobox('getValue');
		var refusedCode02url = "sys/datadictionary/listjason.do?keyName=refusedCode02&parentKeyProp=" + encodeURI(decisionCode3);
		    $('#verifyInfo').find("#decisionCode4").combobox("clear");
			$('#verifyInfo').find('#decisionCode4').combobox({
			url: refusedCode02url,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
	//填充select数据 拒件码01
    var refusedCode01url = "sys/datadictionary/listjason.do?keyName=refusedCode01";
		$('#approvalInfo').find("#decisionCode3").combobox("clear");
		$('#approvalInfo').find('#decisionCode3').combobox({
			url: refusedCode01url,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
	            $('#approvalInfo').find('#decisionCode4').combobox('clear');
	            var refusedCode02url = "sys/datadictionary/listjason.do?keyName=refusedCode02&parentKeyProp=" + encodeURI(newValue);
	            $('#approvalInfo').find('#decisionCode4').combobox('reload',refusedCode02url); 
       		}
		});
		//填充select数据 拒件码02
		var decisionCode3 = $('#approvalInfo').find('#decisionCode3').combobox('getValue');
		var refusedCode02url = "sys/datadictionary/listjason.do?keyName=refusedCode02&parentKeyProp=" + encodeURI(decisionCode3);
		    $('#approvalInfo').find("#decisionCode4").combobox("clear");
			$('#approvalInfo').find('#decisionCode4').combobox({
			url: refusedCode02url,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
	//填充select数据 拒件码01
    var refusedCode01url = "sys/datadictionary/listjason.do?keyName=refusedCode01";
		$('#specialApprovalInfo').find("#decisionCode3").combobox("clear");
		$('#specialApprovalInfo').find('#decisionCode3').combobox({
			url: refusedCode01url,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
		//填充select数据 拒件码02
	var refusedCode02url = "sys/datadictionary/listjason.do?keyName=refusedCode02";
	    $('#specialApprovalInfo').find("#decisionCode4").combobox("clear");
		$('#specialApprovalInfo').find('#decisionCode4').combobox({
			url: refusedCode02url,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
	
	//精英贷测试方案显示
	var pVal = $("#approvalInfo").find("#product").val();
	if((pVal.indexOf('精英贷')!=-1) && (pVal.indexOf('JM')!=-1)){
		$("#approvalInfo").find(".JYDTestScheme").css("display","");
		$("#approvalInfo").find("#secondTestCode").addClass("validate");
		$("#approvalInfo").find("#secondTestCode").combobox({ 
				url:"sys/datadictionary/listjason.do?keyName=second_test_code&parentKeyProp=A01&random=Math.random()",
				valueField:'keyProp', 
				textField:'keyValue',
				onSelect: function(){
					var secondText = $(this).combobox("getText");
					$("#approvalInfo").find("#secondTestValue").val(secondText);
				}
		});
	}else {
		$("#approvalInfo").find(".JYDTestScheme").css("display","none");
		$("#approvalInfo").find("#secondTestCode").removeClass("validate");
	}
});
</script>
</html>


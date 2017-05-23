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
<title>信用贷款决策表更新</title>
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
<div id="part1" class="part">
	<p class="title">
		<a href="javascript:void(0);">审核</a>
		<span style="float: right; padding-right: 20px;">
			<input type="button" value="保存" class="btn" onclick="updateFunction('save')"/>
			<input type="button" value="返回" class="btn" onclick="javascript:back()"/>
		</span>
	</p>
<div class="content">
<div class="easyui-layout" id="easyui_layout" style="width:100%;height:730px;" data-options="fit:true">
<div data-options="region:'center',split:true,border:false" >
	<div class="easyui-tabs" data-options="fit:true,onLoad:tabsReadOnly">
		<div title="影像资料" data-options="href:'${basePath}img/imgSlidePath.do?appId=${bean.appId}'" style="width: 100%;padding:10px"></div>
		<div title="申请信息" data-options="href:'${basePath}credit/app/read1.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		<div title="综合信息" data-options="href:'${basePath}credit/verify/complexInfo.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
	</div>
</div>

<div data-options="region:'east',split:true,border:false" style="width:800px;">
<form id="updateForm" >
	<input type="hidden" name="id" value="${bean.id}" />
	<input type="hidden" name="appId" value="${bean.appId}" />

	<div class="easyui-tabs" id="verify_tabs" style="height:730px;" data-options="fit:true">
		<div title="影像摘要" data-options="href:'${basePath}credit/verify/imageSummary.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		<div title="信用报告" data-options="href:'${basePath}credit/verify/creditReport.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
	</div>
</form>
</div>
</div>
</div>
</div>
</div>

</body>
<script type="text/javascript" >

openMask();

$(window).load(function (){
	closeMask();
});

</script>

<script type="text/javascript">
//只读页 设置为只读
function tabsReadOnly(redinfo){

	redinfo.find("input[type='radio']").attr('disabled',true);
	redinfo.find("input[type='hidden']").attr('disabled',true);
// 	redinfo.find("input[type='button']").attr("disabled",true);

	redinfo.find('.easyui-validatebox').attr('disabled', 'disabled');
	redinfo.find('.easyui-validatebox').validatebox('disableValidation');
	redinfo.find('.easyui-combobox').combo('disableValidation');
	redinfo.find('.easyui-numberbox').numberbox('disableValidation');
	redinfo.find('.easyui-datebox').datebox('disableValidation');
	redinfo.find('.easyui-combobox').combo('disable');
	redinfo.find('.easyui-numberbox').numberbox('disable');
	redinfo.find('.easyui-datebox').datebox('disable');

	redinfo.find('div.panel div.panel-tool>a.icon-cancel').hide();
}
//更新保存
function updateFunction(buttonType) {
	$('#updateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	if(false == $('#updateForm').form('validate') ){
		$.messager.alert('消息', "页面有必输字段，但未填值！");
		return;
	}
	openMask();
	var params = $('#updateForm').serialize();
	$.ajax({
		type : "POST",
		url : "<%=basePath%>credit/verify/save.do",
		data : params+"&buttonType=save",
		success : function(data) {
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					if(buttonType=="save"){
						location.replace(location.href);
						return;
					}
            	});
            } else {				
    			$.messager.alert('消息', data.message);
            }
		},
		error : function() {
			closeMask();
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
		}
	});
}

/**
 * 改变月还款金额或反推放款金额
 * url		访问地址
 * params	出入参数
 * type 	yhkje fkje
 */
function updateJe(url, params, type){
	$.ajax({
		type : "POST",
		url : url,
		data : params,
		success : function(data) {
			//关闭遮罩，弹出消息框
			if ("true"==data.success) {
				if('yhkje' == type)
					$("#verifyInfo").find("#yhkje").numberbox('setValue', data.message);
				else
					$("#verifyInfo").find("#amount").numberbox('setValue', data.message/10000);
            }
		},
		error : function() {
		}
	});
}


//返回
function back(){
	//window.history.go(-1);
	window.parent.removeTab();
}

//切换 是否验证
function toggleValidate(objId,isValidete, type){
	var state=!isValidete;
	var obj=$(objId);
	obj.find('.easyui-validatebox').validatebox({novalidate:state});
	//obj.find('.easyui-combobox').combobox({novalidate:state});
	obj.find('.easyui-numberbox').validatebox({novalidate:state});
	obj.find('.easyui-datebox').datebox({novalidate:state});
}

//原页面弹出框影像的补充资料
function artOpenPage(_title,_url) {
	if($("body").find("#dialogDiv").length==0){
		 $('body').append($("<div id='dialogDiv' style='top:150px;'></div>"));
	}
	$('#dialogDiv').dialog({
	    title: _title,
	    height: 460,
	    width: 1000,
	    closed: false,
	    cache: false,
	    href: encodeURI(_url),
	    modal: true,
	    resizable: true
	});
}


//页面加载完动作
$(document).ready(function (){
	$("#imageClassSelectDiv").empty();
	$("#imageClassSelectDiv").append("<span style='font-weight:bold;'>已选的影像分类：</span> ");
	var imgs = $('#imageSummarys').val().split(',');
	var imgsName = $('#imageSummarysName').val().split(',');
	for(var i=0;i<imgs.length;i++){
		$("#imageClassListDiv").find("#checkbox_" + imgs[i]).attr("checked", "checked");
		$("#imageClassSelectDiv").append("<span style='color: green;'>" + imgs[i] + "_" + imgsName[i] + "</span>");
		$("#imageClassSelectDiv").append("&nbsp; &nbsp;");
	}
	
	if('${creditVerify.decision}' == '00'){
		$('#noPassReasonDiv').show();//显示div   
		toggleValidate('#noPassReasonDiv',true);
		$("#imageClassListDiv").show();
		$("#imageClassSelectDiv").show();
		$('#decisionCodeDiv').hide();//隐藏div  
		toggleValidate('#decisionCodeDiv',false);
		toggleValidate('#amountDiv',false);
	}else if('${creditVerify.decision}' == ''){
		$('#noPassReasonDiv').hide();//div 
		toggleValidate('#noPassReasonDiv',false);
		$("#imageClassListDiv").hide();
		$("#imageClassSelectDiv").hide();
		$('#decisionCodeDiv').hide();//div 
		toggleValidate('#decisionCodeDiv',false);
		toggleValidate('#amountDiv',true);
	}else{
		$('#noPassReasonDiv').hide();//div 
		toggleValidate('#noPassReasonDiv',false);
		$("#imageClassListDiv").hide();
		$("#imageClassSelectDiv").hide();
		$('#decisionCodeDiv').show();//div  
		toggleValidate('#decisionCodeDiv',true);
		toggleValidate('#amountDiv',true);
	}
	
	//拖动时 调节 下拉框 宽度
	$('#easyui_layout').layout('panel', 'east').panel({
		onResize:function(w,h){
			$("#verify_tabs").tabs('getSelected').find(".easyui-accordion").accordion("resize");
			return true;
		  }
		});

});
</script>
</html>


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
		<a href="javascript:void(0);">反欺诈</a>
		<span style="float: right; padding-right: 20px;">
<%--			<input type="button" value="保存" class="btn" onclick="updateFunction('save')"/>--%>
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

	<div class="easyui-tabs" id="verify_tabs" style="height:730px;" data-options="fit:true,onLoad:tabsReadOnly">
		<div title="风险提示" data-options="href:'${basePath}credit/rule/read.do'" style="width: 100%;padding:10px"></div>
		<div title="信息核查" data-options="href:'${basePath}credit/verify/infoCheck.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		<div title="影像摘要" data-options="href:'${basePath}credit/verify/imageSummary.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		<div title="面审调查" data-options="href:'${basePath}credit/verify/interviewSurvey.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		<div title="审核决策"  style="width: 100%;padding:10px">
			 <table width="100%">
				<tr>
					<td colspan="9">
						<div style="font-size: 14px;" >
							<strong>申请信息</strong>
							<hr width="100%" size=2 color="#D3D3D3" noshade>
						</div>
					</td>
				</tr>
			 	<tr><td>
			 		<table id="verifylAppInfo">
						<tr>
							<td align="right">借款金额：</td>
							<td><input type="text" id="amount" value="${bean.amount/10000}" style="width:128px;" class="textbox easyui-numberbox" disabled="disabled"/>万元</td>
<%--							<td align="right">借款期限：</td>--%>
<%--							<td><input id="period" type="text" data-options="required:true,validType:['length[0,10]']" --%>
<%--								class="textbox easyui-validatebox"  value="${bean.period}" editable="false" style="width:128px;" disabled="disabled"/>个月</td>--%>
							<td align="right">渠道：</td>
							<td><input id="belongChannel" type="text" data-options="required:true,validType:['length[0,50]']" 
								class="easyui-combobox"  value="${bean.belongChannel}" editable="false" style="width:152px;" disabled="disabled"/></td>
							<td align="right">产品：</td>
							<td><input id="product" type="text" data-options="required:true,validType:['length[0,50]']" 
								class="easyui-combobox"  value="${bean.product}" editable="false" style="width:152px;" disabled="disabled"/></td>
						</tr>
					</table>
				</td></tr>
			 	<tr>
					<td colspan="9">
						<div style="font-size: 14px;" >
							<strong>审核</strong>
								<input type="hidden" name="decision.id" value="${creditVerify.id}" />
								<input type="hidden" name="decision.appId" value="${bean.appId}" />
								<input type="hidden" name="decision.type" value="1" />
								<input type="hidden" id="state" name="decision.state" value="1" />
							<hr width="100%" size=2 color="#D3D3D3" noshade>
						</div>
					</td>
				</tr>
			 	<tr><td>
			 		<table id="verifyInfo">
			 			<tr>
							<td align="right">借款金额:</td>
							<td><input id="amount" name="decision.amount" value="${creditVerify.amount/10000}" type="text"
									data-options="validType:['length[0,30]']"
									class="textbox easyui-validatebox" style="width:128px;"/>万元
							</td>
<%--							<td align="light">借款期限:</td>--%>
<%--							<td ><input id="period" name="decision.period" value="${creditVerify.period}" type="text" data-options="validType:['length[0,10]']" --%>
<%--								class="textbox easyui-validatebox" editable="false" style="width:128px;"/>个月--%>
<%--							</td>--%>
							<td align="right">渠道:
								<input id="belongChannel" name="decision.belongChannel" type="text" class="easyui-combobox" disabled="disabled"
								data-options="validType:['length[0,50]']" editable="false" value="${creditVerify.belongChannel}"/>
							</td>
							<td align="right">产品：
								<input id="product" name="decision.product" value="${creditVerify.product}" type="text" 
								 data-options="validType:['length[0,50]']"  disabled="disabled"
								class="easyui-combobox" editable="false" style="width:152px;"/>
							</td>
						</tr>
						<tr>
							<td align="right">决策:</td>
							<td>
								<select id="decision" name="decision.decision" value="${creditVerify.decision}"
										data-options="validType:['length[0,50]']
										,onChange: function(decisionVal){
												if(decisionVal == '00'){
													$('#noPassReasonDiv').show();//显示div   
													$('#decisionCodeDiv').hide();//隐藏div  
												}else{
													$('#noPassReasonDiv').hide();//div 
													$('#decisionCodeDiv').show();//div  
												}
									    	}" 
										class="easyui-combobox" editable="false" style="width:152px;">
										<option value="">请选择</option>
										<option value="00" <c:if test="${'00' eq creditVerify.decision}">selected</c:if>>不通过</option>
										<option value="02" <c:if test="${'02' eq creditVerify.decision}">selected</c:if>>拟通过</option>
										<option value="03" <c:if test="${'03' eq creditVerify.decision}">selected</c:if>>拟拒贷</option>
									</select>
							</td>
							<td align="right" colspan="4">
								<div id ="noPassReasonDiv">
									不通过原因:<input id="returnMsg" name="decision.returnMsg" 
									value="${creditVerify.returnMsg}" type="text"
									data-options="validType:['length[0,30]']"
									class="textbox easyui-validatebox" style="width:304px;"/>
								</div>
								<c:if test="${'03' eq creditVerify.decision}">
									<div id ="decisionCodeDiv">
										违例码:
										<input id="decisionCode1" name="decision.decisionCode1" 
											value="${creditVerify.decisionCode1}" type="text" 
											data-options="validType:['length[0,50]']" 
											class="easyui-combobox" editable="false" style="width:152px;"/>
										<input id="decisionCode2" name="decision.decisionCode2" 
											value="${creditVerify.decisionCode2}" type="text" 
											data-options="validType:['length[0,50]']" 
											class="easyui-combobox" editable="false" style="width:152px;"/>
									</div>
								</c:if>
							</td>
						</tr>
						<tr>
							<td>备注：</td>
							<td colspan="4"><textarea id="remarks" name="decision.remarks"  
							class="textbox easyui-validatebox" data-options="validType:['length[0,500]']" 
							style="resize: none;width:625px;height:50px!important;">${creditVerify.remarks}</textarea></td>
						</tr>
						<tr>
							<td colspan="6">
<%--								<input type="button" value="提交" class="btn" onclick="updateFunction('submit')"/>--%>
							</td>
						</tr>
						<%--<tr>
							<td><input type="button" name="decision" value="拒贷"/></td>
							<td align="right">拒件码：</td>
							<td colspan="3">
								<input id="decisionCode3" name="decision.decisionCode3" 
									value="${creditVerify.decisionCode3}"
									type="text" data-options="validType:['length[0,50]']" 
									class="easyui-combobox" editable="false" style="width:152px;"/>
								<input id="decisionCode4" name="decision.decisionCode4" 
									value="${creditVerify.decisionCode4}"
									type="text" data-options="validType:['length[0,50]']" 
									class="easyui-combobox" editable="false" style="width:152px;"/>
							</td>
						</tr>--%>
					</table>
				</td></tr>
				<tr>
					<td colspan="9">
						<div style="font-size: 14px;" >
							<strong>反欺诈处理</strong>
							<hr width="100%" size=2 color="#D3D3D3" noshade>
						</div>
					</td>
				</tr>
			 	<tr><td>
			 		<table id="creditAntifraud">
<%--			 			<tr>--%>
<%--							<td>--%>
<%--								<input type="button" value="提交反欺诈" class="btn" onclick="submitAntifraud()"/>--%>
<%--							</td>--%>
<%--							<td>--%>
<%--								<input id="submitInfo" name="submitInfo" type="text"--%>
<%--								data-options="validType:['length[0,30]']"--%>
<%--								class="textbox easyui-validatebox" style="width:304px;"/>--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--						<tr>--%>
						<tr>
							<td align="right">提交说明 :</td>
							<td><input id="submitInfo" name="creditAntifraud.submitInfo" type="text" data-options="validType:['length[0,500]']" 
								class="textbox easyui-validatebox" value="${creditAntifraud.submitInfo}" editable="false"/></td>
							<td>提交时间 :</td>
							<td><input id="createTimeStr" name="creditAntifraud.createTimeStr" type="text" data-options="validType:['length[0,30]']" 
								class="textbox easyui-datetimebox" value="${creditAntifraud.createTimeStr}" editable="false"/></td>
						</tr>
						<tr>
							<td>
								<input type="hidden" name="appId" value="${bean.appId}" />
								<input type="button" value="解除反欺诈" class="btn" onclick="submitRelieveAntifraud('relieveAntifraud')"/>
							</td>
							<td>
								<input id="result" name="result" type="text" data-options="required:true,validType:['length[0,500]']" 
								class="easyui-combobox" editable="false"/>
							</td>
						</tr>
						<tr>
							<td align="right">备注：</td>
							<td colspan="3">
								<textarea id="resultInfo" name="resultInfo" class="textbox easyui-validatebox" data-options="required:true,validType:['length[0,500]']" 
								style="resize: none;width:304px;height:60px!important;"></textarea>
							</td>
						</tr>
					</table>
				</td></tr>
			</table>
		</div>
	</div>
</form>
</div>
</div>
</div>
</div>
</div>

<!-- <div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
	<img src="img/loading.gif" alt="加载中..." />
</div> -->

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
function submitRelieveAntifraud(buttonType){
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	
	//验证表单验证是否通过
	if(false == $('#updateForm').form('validate') ){
		$.messager.alert('消息', "页面有必输字段，但未填值！");
		return;
	}
	
	//弹出异步加载 遮罩
	openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>credit/antifraud/relieveAntifraud.do",
		data : params+"&buttonType="+buttonType,
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					//location.replace(location.href);
					//return;
					window.history.go(-1);
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
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
	});
}

//只读页 设置为只读
function tabsReadOnly(redinfo){
	redinfo.find('.easyui-validatebox').attr('disabled', 'disabled');
	redinfo.find('.easyui-validatebox').validatebox('disableValidation');
	redinfo.find('.easyui-combobox').combo('disableValidation');
	redinfo.find('.easyui-numberbox').numberbox('disableValidation');
	redinfo.find('.easyui-datebox').datebox('disableValidation');
	redinfo.find('.easyui-combobox').combo('disable');
	redinfo.find('.easyui-numberbox').numberbox('disable');
	redinfo.find('.easyui-datebox').datebox('disable');
}

//更新保存
function updateFunction(buttonType) {
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	if(buttonType!="save"){
		//验证表单验证是否通过
		if(false == $('#updateForm').form('validate') ){
			$.messager.alert('消息', "页面有必输字段，但未填值！");
			return;
		}
		if ($(".tab_show_infoChecky").length == 0) { 
			$.messager.alert('消息', "信息核查没有加载。");
			return;
		}
		 if ($(".tab_show_summary").length == 0) { 
			$.messager.alert('消息', "影像摘要没有加载。");
			return;
		}
		if ($(".tab_show_interview").length == 0) { 
			$.messager.alert('消息', "面审调查没有加载。");
			return;
		}
	}
	//弹出异步加载 遮罩
	openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>credit/verify/save.do",
		data : params+"&buttonType="+buttonType,
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					if(buttonType=="save"){
						location.replace(location.href);
						return;
					}
					window.history.go(-1);
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
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
	});
}

//返回
function back(){
	window.history.go(-1);
}

/* //打开Loading遮罩并修改样式
function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
} */
//页面加载完动作
$(document).ready(function (){
	
	$("#verifyInfo").find("input").attr('disabled', 'disabled');
	$("#verifyInfo").find(".easyui-combobox").combo('disable');
	$("#verifyInfo").find("textarea").attr('disabled', 'disabled');
	
	if('${creditVerify.decision}' == '00'){
		$('#noPassReasonDiv').show();//显示div   
		$('#decisionCodeDiv').hide();//隐藏div  
	}else{
		$('#noPassReasonDiv').hide();//div 
		$('#decisionCodeDiv').show();//div  
	}
	
	//拖动时 调节 下拉框 宽度
	$('#easyui_layout').layout('panel', 'east').panel({
		onResize:function(w,h){
			$("#verify_tabs").tabs('getSelected').find(".easyui-accordion").accordion("resize");
			return true;
		  }
		});
//填充select数据 解除反欺诈结果
	$("#creditAntifraud").find("#result").combobox("clear");
	$("#creditAntifraud").find('#result').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		panelHeight:"auto",
		//业务人员提供反欺诈结果数据字典后，解除反欺诈时，bpmlog的LOG_CONTENT1有结果记录，需注意是否需要再处理
		data:[{"keyProp":"1","keyValue":"无问题"},{"keyProp":"2","keyValue":"有问题"}],
			//添加空白行
		loadFilter:function(data){
	   		var opts = $(this).combobox('options');
	   		var emptyRow = {};
			emptyRow[opts.valueField] = '';
			emptyRow[opts.textField] = '请选择';
			data.unshift(emptyRow);
			return data;
		}
	});
/*	
//填充select数据 借款期限
	$("#verifylAppInfo").find("#period").combobox("clear");
	$("#verifylAppInfo").find("#period").combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:[{"keyProp":"1","keyValue":"12"},{"keyProp":"2","keyValue":"18"},{"keyProp":"3","keyValue":"24"},{"keyProp":"4","keyValue":"36"}]
	});
*/

	//填充select数据 渠道
	var channelurl="channeltotal/listjason.do";
	$("#verifylAppInfo").find("#belongChannel").combobox("clear");
	$("#verifylAppInfo").find('#belongChannel').combobox({
		url:channelurl,
		valueField:'channelCode', 
		textField:'channelName',
		onChange: function(newValue, oldValue){             
			$("#verifylAppInfo").find('#product').combobox('clear');
			var producturl = "product/hedao/listjason.do?type=3&belongChannel=" + encodeURI(newValue);
			$("#verifylAppInfo").find('#product').combobox('reload',producturl); 
		}
	});
	
	//填充select数据 产品
	var belongChannel = $("#verifylAppInfo").find('#belongChannel').combobox('getValue');
	var producturl = "product/hedao/listjason.do?type=3&belongChannel=" + encodeURI(belongChannel);
	$("#verifylAppInfo").find("#product").combobox("clear");
	$("#verifylAppInfo").find('#product').combobox({
		url:producturl,
		valueField:'name', 
		textField:'name'
	});		
	
<%--	//填充select数据 决策信息--%>
<%--	$("#verifyInfo").find('#decision').combobox("clear");--%>
<%--	$("#verifyInfo").find('#decision').combobox({--%>
<%--		valueField:'keyProp', --%>
<%--		textField:'keyValue',--%>
<%--		data:dataDictJson.decision,--%>
<%--		onChange: function(decisionVal){--%>
<%--			if(decisionVal == '00'){--%>
<%--				$("#noPassReasonDiv").show();//显示div   --%>
<%--				$("#decisionCodeDiv").hide();//隐藏div  --%>
<%--			}else{--%>
<%--				$("#noPassReasonDiv").hide();//div --%>
<%--				$("#decisionCodeDiv").show();//div  --%>
<%--			}--%>
<%--    	}--%>
<%--	});--%>
	/*
	$("#verifyInfo").find("#period").combobox("clear");
	$("#verifyInfo").find('#period').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:[{"keyProp":"1","keyValue":"12"},{"keyProp":"2","keyValue":"18"},{"keyProp":"3","keyValue":"24"},{"keyProp":"4","keyValue":"36"}]
	});
	*/
	
	//填充select数据 渠道
	var channelurl="channeltotal/listjason.do?state=1";
	$("#verifyInfo").find("#belongChannel").combobox("clear");
	$("#verifyInfo").find('#belongChannel').combobox({
		url:channelurl,
		valueField:'channelCode', 
		textField:'channelName',
		onChange: function(newValue, oldValue){             
			$("#verifyInfo").find('#product').combobox('clear');
			var producturl = "product/hedao/listjason.do?type=3&belongChannel=" + encodeURI(newValue);
			$("#verifyInfo").find('#product').combobox('reload',producturl); 
		}
	});

	//填充select数据 产品
	var belongChannel = $("#verifyInfo").find('#belongChannel').combobox('getValue');
	var producturl = "product/hedao/listjason.do?type=3&belongChannel=" + encodeURI(belongChannel);
	$("#verifyInfo").find("#product").combobox("clear");
	$("#verifyInfo").find('#product').combobox({
		url:producturl,
		valueField:'name', 
		textField:'name'
	});
	
});
</script>
</html>


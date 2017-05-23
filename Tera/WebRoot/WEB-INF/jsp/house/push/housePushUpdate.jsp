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
<title>房贷推送处理</title>
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
		<p class="title"><a href="javascript:void(0);">房贷推送线上</a></p>
		<div class="content">
			<form id="pushForm">
				<input type="hidden" name="loanAppId" value="${bean.appId}" />
				<input type="hidden" name="loanName" value="${bean.name}" />
				<input type="hidden" name="loanIdType" value="${bean.idType}" />
				<input type="hidden" name="loanIdNo" value="${bean.idNo}" />
				<input type="hidden" name="loanPeriod" value="${decision.period}" />
				<input type="hidden" name="loanProduct" value="${decision.product}" />
				<input type="hidden" name="loanAmount" value="${decision.amount}" />
				<table>
					<tr>
						<td>客户姓名:</td>
						<td><input type="text" class="textbox easyui-validatebox" value="${bean.name}" disabled="disabled"/></td>
						<td>身份证号:</td>
						<td><input type="text" class="textbox easyui-validatebox" value="${bean.idNo}" disabled="disabled"/></td>
						<td>借款用途:</td>
						<td><input id="useage1" type="text" class="easyui-combobox"  value="${bean.useage1}" disabled="disabled"/>
							<input id="useage2" type="text" class="easyui-combobox"  value="${bean.useage2}" disabled="disabled"/></td>
					</tr>
					<tr>
						<td>借款金额:</td>
						<td><input type="text" data-options="precision:2"  disabled="disabled"
								class="textbox easyui-numberbox" value="${actualAmount}"/>元</td>
						<td>分期数:</td>
						<td><input id="period" type="text" class="easyui-combobox" 
								 value="${decision.period}" disabled="disabled"/></td>
						<td>渠道产品:</td>
						<td>
							<input id="belongChannel" type="text" class="textbox easyui-combobox" 
								value="${decision.belongChannel}" disabled="disabled"/>
							<input id="product" type="text" class="textbox easyui-combobox" 
								value="${decision.product}" disabled="disabled"/>
						</td>
					</tr>
					<tr>
						<td>客户实际获得金额:</td>
						<td><input type="text" data-options="min:0,precision:2"  disabled="disabled"
								class="textbox easyui-numberbox" value="${decision.amount}"/>元</td>
					</tr>
					<tr>
						<td>处理结果：</td>
						<td>
							<input type="text" id="operateResult" name="operateResult" class="easyui-combobox" data-options="editable:false"/>
						</td>
						<td class="pcArea" style="visibility: hidden;">推送渠道：</td>
						<td class="pcArea" style="visibility: hidden;">
							<input type="text" id="pushChannel" name="channelType" class="easyui-combobox" data-options="editable:false"/>
							<input type="hidden" id="channelName" name="appChannelName"/>
						</td>
					</tr>
					<tr>
						<td>推送备注：</td>
						<td colspan="4">
							<textarea id="pushRemark" name="channelRemark" class="textbox" 
								style="resize: none;width:400px;height:50px!important;font-size: 13px;"></textarea>
						</td>							
					</tr>
					<tr>
						<td>
							<input type="button" value="提交" class="btn" onclick="javascript:updateFunction()"/>
							<input type="button" value="返回" class="btn" onclick="javascript:back()"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
<form id="fileSmt" action="file/upload/zipupload.do"  enctype="multipart/form-data" style="display: none;" >
	<input type="hidden" id="fileappId" name="appId" value="${bean.appId}" />
</form>

<script type="text/javascript">
//更新保存
function updateFunction() {
	//去掉 input 输入的 前后空格
	$('#pushForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	//验证表单验证是否通过
	if(false == $('#pushForm').form('validate') ){
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}
	
	
	//弹出异步加载 遮罩
	openMask();
	var params = $('#pushForm').serialize();
	//按钮失效防点击
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "house/push/save.do",
		data : params,
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					back();
            	});
            } else {				
    			$.messager.alert('消息', data.message);
            }
		},
		error : function() {
			//关闭遮罩，弹出消息框
			closeMask();
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
		}
	});
}

//页面加载完动作
$(document).ready(function (){
	
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

	//填充select数据 借款期限
	$("#period").combobox("clear");
	$('#period').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:[{"keyProp":"1","keyValue":"12"},{"keyProp":"2","keyValue":"18"},{"keyProp":"3","keyValue":"24"},{"keyProp":"4","keyValue":"36"}]
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
	
	$('#operateResult').combobox({
		textField:'text',
		valueField:'value',
		panelHeight:'auto',
		data:[{'text':'请选择','value':''},
		      {'text':'推送','value':'1'},
		      {'text':'放弃','value':'0'}],
		onChange:function(newVal,oldVal) {
			if('1' == newVal) {
				$(".pcArea").css('visibility','visible');
				$('#pushChannel').combobox({
					url:channelurl,
					textField:'channelName',
					valueField:'channelCode',
					panelHeight:'auto',
					onSelect:function() {
						var channelName = $('#pushChannel').combobox('getText');
						$('#channelName').val(channelName);
					}
				});
			} else {
				$('#pushChannel').combobox('clear');
				$(".pcArea").css('visibility','hidden');
			}
		}
	});
});
</script>
<script type="text/javascript">
	//返回
	function back(){
		window.history.go(-1);
	}
</script>
</body>
</html>


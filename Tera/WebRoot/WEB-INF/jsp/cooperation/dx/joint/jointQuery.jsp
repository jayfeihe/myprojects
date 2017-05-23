<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />
		<title>合作方订单查询</title>
		<link href="css/global.css" type="text/css" rel="stylesheet" />
		<link href="css/icon.css" type="text/css" rel="stylesheet" />
		<link href="css/default/easyui.css" type="text/css" rel="stylesheet" />
		<script src="js/jquery.min.js" type="text/javascript"></script>
		<script src="js/jquery.form.js" type="text/javascript"></script>
		<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
		<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
		
		<style type="text/css"></style>
	</head>
	<body>
		<div id="main">
			<div id="part1" class="part">
				<p class="title"><a href="javascript:void(0);">合作方订单查询</a></p>

				<div class="content">
					<form id="queryForm" action="cooperation/dx/joint/list.do" method="post" target="queryContent">
						<table>
							<tr>
								<td>申请编号:</td>
								<td><input id="appId" name="appId" type="text"
										data-options="validType:['length[0,30]']"
										class="textbox easyui-validatebox" /></td>
								<td>合同号:</td>
								<td><input id="contractNo" name="contractNo" type="text"
										data-options="validType:['length[0,50]']"
										class="textbox easyui-validatebox" /></td>	
								<td>产品:</td>
								<td id="channel">
									<input id="platformName" name="platformName" type="text"
										data-options="validType:['length[0,50]']"
										value="${partnerCode}" editable="false" class="easyui-combobox" />
								</td> 
								<td><input id="decisionProduct" name="product1" type="text"
										data-options="validType:['length[0,50]']"
										editable="false" class="easyui-combobox" /></td>
							</tr>
							<tr>
								<td>姓名:</td>
								<td><input id="name" name="name" type="text"
										data-options="validType:['length[0,50]']"
										class="textbox easyui-validatebox" /></td>
										
								<td>身份证:</td>
								<td><input id="idNo" name="idNo" type="text"
										data-options="validType:['idcard']"
										class="textbox easyui-validatebox" /></td>
							</tr>
							<tr>
								<td>处理状态:</td>
								<td>
									<select class="easyui-combobox" name="stateTask" editable="false" style="width:130px;">
										<option value="waitTask" selected="selected">处理中</option>
										<option value="inTask">拒贷</option>
										<option value="offTask">已完成</option>
									</select>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<input type="button" value="查询" class="btn" onclick="submitForm('queryForm')" />
									<input type="button" value="重置" class="btn" onclick="$('#queryForm').form('clear');"/>
								</td>
							</tr>
						</table>
					</form>
				</div>

				<div id="queryContent">
					<%--
					查询列表
					 --%>
				</div>
				
			</div>
		</div>

		<div id="loading" class="easyui-window" title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false">
			<img src="img/loading.gif" alt="加载中..." />
		</div>

	</body>

<script type="text/javascript">

function submitForm(fromId) {
	//去掉 input 输入的 前后空格
	$('#queryForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	
	var formAction = $('#' + fromId).attr("action");
	var targetDiv = $('#' + fromId).attr("target");
	var params = $('#' + fromId).serialize();
	//弹出异步加载 遮罩
	openMask();
	$.ajax( {
		type : "POST",
		url : formAction,
		data : params + "&targetDiv=" + targetDiv,
		dataType : "html",
		success : function(data) {
			closeMask();
			$('#' + targetDiv).html(data);
		},
		error : function() {
			closeMask();
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
		}
	});
}

/* //打开Loading遮罩并修改样式
function openLoading() {
	$('#loading').window('open');
	$("#loading").attr("class", "");
	$("div[class='panel window']").css("position", "absolute");
	$("div[class='panel window']").attr("class", "");
	$("div[class='window-shadow']").attr("class", "");
} */



//页面加载完动作
$(document).ready(function() {
	//产品的平台，后台传值，有值隐藏平台框，无值（admin）不隐藏。
	var belongPartner = '${partnerCode}' ;
	if(belongPartner != ''){
		$("#channel").hide();
	}else if(belongPartner == ''){
		$("#channel").show();
	}

	//填充select数据 渠道
	var channelurl="channeltotal/listjason.do";
	$("#platformName").combobox("clear");
	$('#platformName').combobox({
		url:channelurl,
		valueField:'channelCode', 
		textField:'channelName',
		onChange: function(newValue, oldValue){             
			$('#decisionProduct').combobox('clear');
			var producturl = "product/hedao/listjason.do?type=3&belongChannel=" + encodeURI(newValue);
			$('#decisionProduct').combobox('reload',producturl); 
		},
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
	//填	充select数据 产品
	var belongChannel = $('#platformName').combobox('getValue');
	var producturl = "product/hedao/listjason.do?type=3&belongChannel=" + encodeURI(belongChannel);
	$("#decisionProduct").combobox("clear");
	$('#decisionProduct').combobox({
		url:producturl,
		valueField:'name', 
		textField:'productName',
		//添加空白行
		loadFilter:function(data){
	   		var opts = $(this).combobox('options');
	   		var emptyRow = {};
			emptyRow[opts.valueField] = '';
			emptyRow[opts.textField] = '全部';
			data.unshift(emptyRow);
				return data;
		}
	});
	
	submitForm("queryForm");
});
</script>
</html>


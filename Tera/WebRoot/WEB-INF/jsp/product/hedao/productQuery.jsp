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
<title>产品表查询</title>
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
		<p class="title"><a href="javascript:void(0);">产品查询</a></p>
		
		<div class="content">
			<form id="queryForm" action="product/hedao/list.do" method="post" target="queryContent">
				<table width="100%">
					<tr>
						<td class="td01">产品名称:</td>
						<td><input id="name" name="name" type="text" data-options="validType:['length[0,18]']" class="textbox easyui-validatebox"/></td>
						<td class="td01">产品类型:</td>
						<td><input id="type" name="type" type="text" class="easyui-combobox" editable="false"/></td>
						<td class="td01">期限:</td>
						<td><input id="period" name="period" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
						<td class="td01">服务费率:</td>
						<td><input id="sreviceFeeRate" name="sreviceFeeRate" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
					</tr>
					<tr>
						<td class="td01">利率:</td>
						<td><input id="interestRate" name="interestRate" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
						<td class="td01">还款/付息方式:</td>
						<td><input id="repayMethod" name="repayMethod" type="text" class="easyui-combobox" editable="false"/></td>
						<td class="td01">所属渠道:</td>
						<td><input id="belongChannel" name="belongChannel" type="text" class="easyui-combobox" editable="false"/></td>							
						<td class="td01">状态:</td>
						<td>
							<select class="easyui-combobox" name="state" editable="false" style="width:152px;">
									<option value="" selected="selected">全部</option>
									<option value="1">正常</option>
									<option value="2">挂起</option>
									<option value="0">停用</option>
							</select>
						</td>							
					</tr>
					<tr>
						<td colspan="2"><input type="button" value="查询" class="btn" onclick="submitForm('queryForm')"/>
							<input type="reset" value="重置" class="btn" onclick="$('#queryForm').form('clear');"/></td>
					</tr>
				</table>	
			</form>
		</div>
		
		<div id="queryContent" >
		<%--
		查询列表
		 --%>
		</div>
	</div>
</div>

<!-- <div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
	<img src="img/loading.gif" alt="加载中..." />
</div> -->

</body>
	
<script type="text/javascript">

function submitForm(fromId) {
	var formAction = $('#' + fromId).attr("action");
	var targetDiv = $('#' + fromId).attr("target");
	var params = $('#' + fromId).serialize();
	//弹出异步加载 遮罩
	openMask();
	$.ajax({
		type : "POST",
		url : formAction,
		data : encodeURI(params + "&targetDiv=" + targetDiv),
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
function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
} */


//页面加载完动作
$(document).ready(function(){
	//填充select数据 渠道
	var channelurl="channeltotal/listjason.do?state=1";
	$("#belongChannel").combobox("clear");
	$('#belongChannel').combobox({
		url:channelurl,
		valueField:'channelCode', 
		textField:'channelName',
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
	
	//填充select数据
	//var tsurl="sys/datadictionary/listjason.do?keyName=repaymethod";
	$("#repayMethod").combobox("clear");
	$('#repayMethod').combobox({
		//url:tsurl,
		valueField:'keyProp',
		textField:'keyValue',
		data:dataDictJson.repaymethod,
		//添加空白行
		loadFilter:function(data){
       		var opts = $(this).combobox('options');
       		var emptyRow = {};
			emptyRow[opts.valueField] = '&nbsp;';
			emptyRow[opts.textField] = '...';
			data.unshift(emptyRow);
 			return data;
		}
	});
	$('#repayMethod').combobox('select', '${bean.repayMethod}');

	$("#type").combobox("clear");
	$('#type').combobox({
		//url:tsurl,
		valueField:'keyProp',
		textField:'keyValue',
		data:dataDictJson.producttype,
		//添加空白行
		loadFilter:function(data){
       		var opts = $(this).combobox('options');
       		var emptyRow = {};
			emptyRow[opts.valueField] = '&nbsp;';
			emptyRow[opts.textField] = '...';
			data.unshift(emptyRow);
 			return data;
		}
	});
	$('#type').combobox('select', '${bean.type}');
	submitForm("queryForm");
});
</script>
</html>


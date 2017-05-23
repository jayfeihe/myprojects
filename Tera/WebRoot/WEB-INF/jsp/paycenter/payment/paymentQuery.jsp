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
<title>支付明细表查询</title>
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
<!-- top -->
<%@include file="../header.jsp" %>

<!-- body -->
	<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">等待支付列表</a></p>
		<div class="content" style="display: none;">
			<form id="queryForm" action="<%=basePath%>paycenter/payment/list.htm" method="post" target="queryContent">
				<table>
					<tr>
<td>合同号:</td>
<td><input id="contractNo" name="contractNo" type="text" data-options="validType:['length[0,25]']" class="textbox easyui-validatebox"/></td>
</tr>
<tr>
<td>收付:</td>
<td><input id="inOut" name="inOut" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox"/></td>
</tr>
<tr>
<td>科目:</td>
<td><input id="subject" name="subject" type="text" data-options="validType:['length[0,18]']" class="textbox easyui-validatebox"/></td>
</tr>
<tr>
<td>计划金额:</td>
<td><input id="planAmount" name="planAmount" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>实际金额:</td>
<td><input id="actualAmount" name="actualAmount" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>来源:</td>
<td><input id="source" name="source" type="text" data-options="validType:['length[0,18]']" class="textbox easyui-validatebox"/></td>
</tr>
<tr>
<td>期数:</td>
<td><input id="periodNum" name="periodNum" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>发盘:</td>
<td><input id="sendFlag" name="sendFlag" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox"/></td>
</tr>
<tr>
<td>状态:</td>
<td><input id="state" name="state" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox"/></td>
</tr>
<tr>
<td>原因:</td>
<td><input id="reason" name="reason" type="text" data-options="validType:['length[0,200]']" class="textbox easyui-validatebox"/></td>
</tr>
<tr>
<td>操作员:</td>
<td><input id="operator" name="operator" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"/></td>
</tr>
<tr>
<td>所属机构:</td>
<td><input id="orgId" name="orgId" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"/></td>
</tr>
<tr>
<td>创建日期:</td>
<td><input id="createTime" name="createTime" type="text" editable="false" class="textbox easyui-datetimebox"/></td>
</tr>
<tr>
<td>修改日期:</td>
<td><input id="updateTime" name="updateTime" type="text" editable="false" class="textbox easyui-datetimebox"/></td>
</tr>

					<tr>
						<td>
							<input type="button" value="查询" class="btn" onclick="submitForm('queryForm')"/>
							<input type="reset" value="重置" class="btn" onclick="$('#queryForm').form('clear');"/>
						</td>
						<td></td>
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
	submitForm("queryForm");
	//填充select数据样例
	/*
	var tsurl="sys/datadictionary/listjason.do?keyName=repaymethod";
	$("#repayMethod").combobox("clear");
	$('#repayMethod').combobox({
		url:tsurl,
		valueField:'keyProp',
		textField:'keyValue',
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
	*/
});
</script>
</html>


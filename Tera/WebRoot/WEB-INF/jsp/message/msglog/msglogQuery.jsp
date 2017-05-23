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
<title>短信日志表查询</title>
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
		<p class="title"><a href="javascript:void(0);">查询</a></p>
		
		<div class="content">
			<form id="queryForm" action="message/msglog/list.do" method="post" target="queryContent">
				<table>
					<tr>
						<td>类型:</td>
						<td><input id="type" name="type" type="text" class="easyui-combobox"/></td>
						<td>合同编号:</td>
						<td><input id="contractNo" name="contractNo" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"/></td>
						<td>客户姓名:</td>
						<td><input id="customerName" name="customerName" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox"/></td>
						<td>联系方式:</td>
						<td><input id="mobileTel" name="mobileTel" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>发送状态:</td>
						<td><input id="sendState" name="sendState" type="text" editable="false" class="easyui-combobox"/></td>
						<td>发送时间:</td>
						<td><input id="sendTimeMin" name="sendTimeMin" style="width: 90px;"  
								type="text" editable="false" class="textbox easyui-datebox" />
							至
						<input id="sendTimeMax" name="sendTimeMax" style="width: 90px;"  
								type="text" editable="false" class="textbox easyui-datebox" /></td>
						<td>接收状态:</td>
						<td><input id="receiveState" name="receiveState" type="text" editable="false" class="easyui-combobox"/></td>
						<td>接收时间:</td>
						<td><input id="receiveTimeMin" name="receiveTimeMin" style="width: 90px;"  
								type="text" editable="false" class="textbox easyui-datebox" />
							至
						<input id="receiveTimeMax" name="receiveTimeMax" style="width: 90px;"  
							type="text" editable="false" class="textbox easyui-datebox" /></td>
					</tr>

					<tr>
						<td colspan="6">
							<input type="button" value="查询" class="btn" onclick="submitForm('queryForm')"/>
							<input type="button" value="重置" class="btn" onclick="$('#queryForm').form('clear');"/>
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
</body>
<script type="text/javascript">
//添加选项卡
function addTab(title, url){
    if ($('#verifyTable').tabs('exists', title)){
        $('#verifyTable').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#verifyTable').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeTab(){
	var tab = $('#verifyTable').tabs('getSelected');
	var tabIndex=$('#verifyTable').tabs('getTabIndex',tab);
	$('#verifyTable').tabs('close',tabIndex);
	submitForm("queryForm");//解决Tab提交关闭列表页刷新的问题
}
</script>

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
		url  : formAction,
		data : params + "&targetDiv=" + targetDiv,
		dataType : "html",
		success : function(data) {
			closeMask();
			$('#' + targetDiv).html(data);
		},
		error : function() {
			closeMask();
			$.messager.confirm('消息', '数据加载失败,请联系系统管理员！');
		}
	});
}
//页面加载完动作
$(document).ready(function() {
//填充select数据样例
/*<%--
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
--%>*/
	
    var typeurl = "sys/datadictionary/listjason.do?keyName=messagetype";
	$("#type").combobox("clear");
	$('#type').combobox({
		url: typeurl,
		valueField: 'keyProp',
		textField: 'keyValue'
	});
	$("#sendState").combobox("clear");
	$('#sendState').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:[{"keyProp":"0","keyValue":"待发送"},{"keyProp":"1","keyValue":"已发送"}]
	});
	$("#receiveState").combobox("clear");
	$('#receiveState').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:[{"keyProp":"DELIVRD","keyValue":"成功"},{"keyProp":"UNDELIV","keyValue":"失败"}]
	});
	
	submitForm("queryForm");
});
</script>

</html>


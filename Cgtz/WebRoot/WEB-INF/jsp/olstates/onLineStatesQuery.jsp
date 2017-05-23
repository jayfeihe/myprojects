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
<title>T_ONLINE_STATES查询</title>
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
<div class="easyui-tabs" data-options="fit:true" id="queryOnLineStatesTabs">
<div title="线上状态查询列表">
	<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">查询</a></p>
		
		<div class="content">
			<form id="queryOnLineStatesForm" action="onLineStates/list.do" method="post" target="queryOnLineStatesContent">
				<table>
<tr>
<td>申请编号:</td>
<td><input id="loanId" name="loanId" type="text" class="textbox easyui-validatebox"/></td>
<td>合同编号:</td>
<td><input id="contractId" name="contractId" type="text" class="textbox easyui-validatebox"/></td>
<td>状态:</td>
<td><input id="state" name="state" type="text" class="textbox easyui-validatebox"/></td>
</tr>
<tr>
<td>是否当前:</td>
<td><input id="isCur" name="isCur" type="text" class="textbox easyui-combobox"
data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'1','keyValue':'是'},{'keyProp':'0','keyValue':'否'}]"
/></td>
<td>时间:</td>	
<td><input id="minLogDate" name="minLogDate" type="text" editable="false" class="textbox easyui-datebox" style="width:100px;"/>
				-<input id="maxLogDate" name="maxLogDate" type="text" editable="false" class="textbox easyui-datebox" style="width:100px;"/></td>		
</tr>

<tr>
					    <td></td>
						<td>
							<input type="button" value="查询" class="btn" onclick="submitForm('queryOnLineStatesForm')"/>
							<input type="button" value="重置" class="btn" onclick="$('#queryOnLineStatesForm').form('clear');"/>
						</td>
						<td></td>
					</tr>
				</table>	
			</form>
		</div>
		
		<div id="queryOnLineStatesContent" >
		<%--
		查询列表
		 --%>
		</div>
	</div>
</div>
</div>
</div>
</body>
<script type="text/javascript">
//添加选项卡
function addTab(title, url){
    if ($('#queryOnLineStatesTabs').tabs('exists', title)){
        $('#queryOnLineStatesTabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#queryOnLineStatesTabs').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeTab(){
	var tab = $('#queryOnLineStatesTabs').tabs('getSelected');
	var tabIndex=$('#queryOnLineStatesTabs').tabs('getTabIndex',tab);
	$('#queryOnLineStatesTabs').tabs('close',tabIndex);
	submitForm("queryOnLineStatesForm");//解决Tab提交关闭列表页刷新的问题
}

//////////////       ////////////////
//开启遮罩
function openMask(){
	$("<div class=\"datagrid-mask\" id='mask_id'></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
	$("<div class=\"datagrid-mask-msg\" id='mask_msg_id'></div>").html("请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
}
//关闭遮罩
function closeMask(){
	$("#mask_id").remove();
	$("#mask_msg_id").remove();
}
</script>

<script type="text/javascript">
//添加选项卡
function addTab(title, url){
    if ($('#queryOnLineStatesTabs').tabs('exists', title)){
        $('#queryOnLineStatesTabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#queryOnLineStatesTabs').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeTab(){
	var tab = $('#queryOnLineStatesTabs').tabs('getSelected');
	var tabIndex=$('#queryOnLineStatesTabs').tabs('getTabIndex',tab);
	$('#queryOnLineStatesTabs').tabs('close',tabIndex);
	submitForm("queryOnLineStatesForm");//解决Tab提交关闭列表页刷新的问题
}

function submitForm(fromId) {
	//去掉 input 输入的 前后空格
	$('#queryOnLineStatesForm').find('input').each(function(){
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

	submitForm("queryOnLineStatesForm");
	
});
</script>

</html>


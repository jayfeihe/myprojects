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
<title>保证金信息表查询</title>
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
<div class="easyui-tabs" data-options="fit:true" id="maginTabs">
<div title="保证金信息列表">
	<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">查询</a></p>
		<form id="queryMaginForm" action="account/magin/list.do" method="post" target="queryContent">
		<table>
		<tr>
		<td>分公司:</td>
<td><input id="orgId" name="orgId" <c:if test="${login_org.orgId ne '86'  }">value='${login_org.orgId }' readonly='readonly'</c:if> class="textbox easyui-combotree"
data-options="editable:false,panelHeight:'auto',url:'<%=basePath%>sys/org/selectList.do?nodeLevel=2',method:'get'"/></td>
<td>状态:</td>
<td><input id="state" name="state"  class="textbox easyui-combobox"
data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'1','keyValue':'未处理'},{'keyProp':'2','keyValue':'退还借款人'},{'keyProp':'3','keyValue':'垫付本金'}]"/></td>
<td>合同号:</td>
<td><input id="contractId" name="contractId" class="textbox easyui-validatebox"/></td>
<td>借款人:</td>
<td><input id="loanName" name="loanName" class="textbox easyui-validatebox"/></td>
</tr>
<tr>
<td>合同开始时间:</td>
<td colspan="2"><input id="minStartDate" name="minStartDate" type="text" editable="false" class="textbox easyui-datebox" style="width:100px;"/>
-<input id="maxStartDate" name="maxStartDate" type="text" editable="false" class="textbox easyui-datebox" style="width:100px;"/></td>
</tr>
<tr>
<td>合同结束时间:</td>
<td colspan="2"><input id="minEndDate" name="minEndDate" type="text" editable="false" class="textbox easyui-datebox" style="width:100px;"/>
-<input id="maxEndDate" name="maxEndDate" type="text" editable="false" class="textbox easyui-datebox" style="width:100px;"/></td>
</tr>
<tr>
<td>处理时间:</td>
<td colspan="2"><input id="minHandleDate" name="minHandleDate" type="text" editable="false" class="textbox easyui-datebox" style="width:100px;"/>
-<input id="maxHandleDate" name="maxHandleDate" type="text" editable="false" class="textbox easyui-datebox" style="width:100px;"/></td>
</tr>
<tr>
<td></td>
<td>
<input type="button" value="查询" class="btn" onclick="submitForm('queryMaginForm')"/>
<input type="button" value="重置" class="btn" onclick="$('#queryMaginForm').form('clear');"/>
<input type="button" value="导出" class="btn" onclick="exportExcel()"/>
</td></tr>
</table>		
		</form>
		<div class="content">
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
//导出表格
function exportExcel(){
	$('#queryMaginForm').attr("action","<%=basePath%>account/magin/excel.do");
	$('#queryMaginForm').attr("method","post");
	$('#queryMaginForm').submit();
}
//添加选项卡
function addTab(title, url){
    if ($('#maginTabs').tabs('exists', title)){
        $('#maginTabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#maginTabs').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeTab(){
	var tab = $('#maginTabs').tabs('getSelected');
	var tabIndex=$('#maginTabs').tabs('getTabIndex',tab);
	$('#maginTabs').tabs('close',tabIndex);
	submitForm("queryMaginForm");//解决Tab提交关闭列表页刷新的问题
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
function submitForm(fromId) {
	//去掉 input 输入的 前后空格
	$('#queryMaginForm').find('input').each(function(){
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

	submitForm("queryMaginForm");
});
</script>

</html>


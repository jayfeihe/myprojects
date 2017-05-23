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
<title>逾期查询</title>
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
<div class="easyui-tabs" data-options="fit:true" id="overdueTabs">
<div title="逾期列表">
	<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">查询</a></p>
		
		<div class="content">
			<form id="queryOverDueForm" action="overdue/list.do" method="post" target="queryOverDueContent">					
			<table>
			<tr>
				<td>合同编号:</td>
				<td><input id="contractId" name="contractId" type="text" class="textbox"/></td>
				<td>申请编号:</td>
				<td><input id="loanId" name="loanId" type="text" class="textbox"/></td>
				<td>申请人/机构名称：</td>
			    <td><input id="loanName" name="loanName" type="text" class="textbox easyui-validatebox"/></td>
			
			    <td>证件号码:</td>
				<td><input id="idNo" name="idNo" type="text" class="textbox easyui-validatebox"/></td>
				</tr>
				<tr>
				<td>产品类型:</td>
			    <td><input id="product" name="product" type="text" class="textbox easyui-combobox"
			         data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
			         url:'sys/datadictionary/listjason.do?keyName=productType'"/></td>
			    <td>逾期类型:</td>
			    <td><input id="overdueType" name="overdueType" type="text" class="textbox easyui-combobox"
			         data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
			         data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'1','keyValue':'利息逾期'},{'keyProp':'2','keyValue':'本金逾期'}]"/></td>
				<td>报告状态:</td>
				<td><input id="checkReportState" name="checkReportState" type="text" class="textbox easyui-combobox"
				data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
				             data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'0','keyValue':'未提交'},{'keyProp':'1','keyValue':'未审核'},
				             {'keyProp':'2','keyValue':'未通过'},{'keyProp':'3','keyValue':'已通过'}]"/>
				</td>
				<td>受理状态:</td>
				<td><input id="isAccept" name="isAccept" type="text" class="textbox easyui-combobox"
				data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
				             data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'0','keyValue':'未受理'},{'keyProp':'1','keyValue':'已受理'}]"/></td>
			</tr>
			<tr>
				<td>逾期还款日:</td>
				<td><input id="minRetDate" name="minRetDate" type="text" editable="false" class="textbox easyui-datebox" style="width:100px;"/>
				-<input id="maxRetDate" name="maxRetDate" type="text" editable="false" class="textbox easyui-datebox" style="width:100px;"/></td>
				</tr>
				<tr><td></td>
				<td colspan="2">
						<input type="button" value="查询" class="btn" onclick="submitForm('queryOverDueForm')"/>
						<input type="button" value="重置" class="btn" onclick="$('#queryOverDueForm').form('clear');"/>
					</td></tr>
				</table>
			</form>
		</div>
		
		<div id="queryOverDueContent" >
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
    if ($('#overdueTabs').tabs('exists', title)){
        $('#overdueTabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#overdueTabs').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeTab(){
	var tab = $('#overdueTabs').tabs('getSelected');
	var tabIndex=$('#overdueTabs').tabs('getTabIndex',tab);
	$('#overdueTabs').tabs('close',tabIndex);
	submitForm("queryOverDueForm");//解决Tab提交关闭列表页刷新的问题
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
	$('#queryOverDueForm').find('input').each(function(){
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


	submitForm("queryOverDueForm");
});
</script>

</html>


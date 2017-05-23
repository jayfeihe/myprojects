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
<title>催收列表</title>
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
<div class="easyui-tabs" data-options="fit:true" id="clltTabs">
<div title="催收列表">
	<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">查询</a></p>	
		<div class="content">
			<form id="queryClltForm" action="cllt/list.do" method="post" target="queryClltContent">			
			<table>
			<tr>		   
			    <td>合同编号:</td>
				<td><input id="contractId" name="contractId" type="text" class="textbox easyui-validatebox"/></td>
				<td>申请编号:</td>
				<td><input id="loanId" name="loanId" type="text" class="textbox easyui-validatebox"/></td>
			    <td>申请人/机构名称：</td>
			    <td><input id="loanName" name="loanName" type="text" class="textbox easyui-validatebox"/></td>
			
			    <td>证件号码:</td>
				<td><input id="idNo" name="idNo" type="text" class="textbox easyui-validatebox"/></td>
			</tr>
			<tr>
			    <td>机构:</td>
			    <td><input id="org" name="org" type="text" class="textbox easyui-combotree"
			         data-options="editable:false,panelHeight:'auto',valueField:'orgId',textField:'orgName',
			         url:'sys/org/selectList.do'"/></td>
			
			    <td>产品类型:</td>
			    <td><input id="product" name="product" type="text" class="textbox easyui-combobox"
			         data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
			         url:'sys/datadictionary/listjason.do?keyName=productType'"/></td>		     
			    <td>是否续贷:</td>
			    <td><input id="isRenew" name="isRenew" type="text" class="textbox easyui-combobox"
			        data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
			        data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'0','keyValue':'否'},{'keyProp':'1','keyValue':'是'},{'keyProp':'2','keyValue':'被续贷'}]"/></td>
			</tr>
			<tr>
			<td>逾期还款日：</td>
			    <td colspan="3"><input id="minRetDate" name="minRetDate" editable="false" type="text" class="textbox easyui-datebox" style="width:100px;"/>-
			    <input id="maxRetDate" name="maxRetDate" editable="false" type="text" class="textbox easyui-datebox" style="width:100px;"/>
			    </td></tr>
				<tr><td></td>
				<td colspan="2">
						<input type="button" value="查询" class="btn" onclick="submitForm('queryClltForm')"/>
						<input type="button" value="重置" class="btn" onclick="$('#queryClltForm').form('clear');"/>
					</td></tr>
				</table>
			</form>
		</div>
		
		<div id="queryClltContent" >
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
    if ($('#clltTabs').tabs('exists', title)){
        $('#clltTabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#clltTabs').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeTab(){
	var tab = $('#clltTabs').tabs('getSelected');
	var tabIndex=$('#clltTabs').tabs('getTabIndex',tab);
	$('#clltTabs').tabs('close',tabIndex);
	submitForm("queryClltForm");//解决Tab提交关闭列表页刷新的问题
}

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
	$('#queryClltForm').find('input').each(function(){
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


	submitForm("queryClltForm");
});
</script>

</html>


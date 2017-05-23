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
<div class="easyui-tabs" data-options="fit:true" id="checkTabs">
<div title="稽查任务">
	<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">查询</a></p>
		
		<div class="content">
			<form id="queryCheckForm" action="check/list.do" method="post" target="queryCheckContent">			
			<table>
			<tr>
				<td>合同编号:</td>
				<td><input id="contractId" name="contractId" type="text" class="textbox"/></td>
				<td>申请编号:</td>
				<td><input id="loanId" name="loanId" type="text" class="textbox"/></td>
				<td>分公司:</td>
				<td><input id="org" name="org" type="text" class="textbox easyui-combotree"
                data-options="url:'sys/org/selectList.do',method:'get',required:false,panelHeight:'auto',editable:false"/></td>
				<td>标识:</td>
				<td><input id="checkState" name="checkState" type="text" class="textbox easyui-combobox"
				data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
				             data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'1','keyValue':'未处理'},{'keyProp':'2','keyValue':'已处理'},{'keyProp':'3','keyValue':'移交法务'}]"/>
				</td>
				
			</tr>
			<tr>
				<td>产品类型:</td>
			    <td><input id="product" name="product" type="text" class="textbox easyui-combobox"
			         data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
			         url:'sys/datadictionary/listjason.do?keyName=productType'"/></td>	
				<td>业务经办人:</td>
				<td><input id="saleName" name="saleName" type="text" class="textbox"/></td>
				<td>报告提交:</td>
				<td><input id="checkReportState" name="checkReportState" type="text" class="textbox easyui-combobox"
				data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
				             data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'0','keyValue':'未提交'},{'keyProp':'1','keyValue':'未审核'},
				             {'keyProp':'2','keyValue':'未通过'},{'keyProp':'3','keyValue':'已通过'}]"/>
				</td>
				<td>逾期类型:</td>
				<td><input id="overdueType" name="overdueType" type="text" class="textbox easyui-combobox"
				data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
				             data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'1','keyValue':'利息逾期'},
				             {'keyProp':'2','keyValue':'本金逾期'}]"/>
				</td>
				</tr>
				<tr>
				<td>逾期还款日:</td>
				<td><input id="startTime" name="startTime" type="text" editable="false" class="textbox easyui-datebox" style="width:100px;"/>
				-<input id="endTime" name="endTime" type="text" editable="false" class="textbox easyui-datebox" style="width:100px;"/></td>
				</tr>			
				<tr><td></td>
				<td colspan="2">
						<input type="button" value="查询" class="btn" onclick="submitForm('queryCheckForm')"/>
						<input type="button" value="重置" class="btn" onclick="$('#queryCheckForm').form('clear');"/>
					</td></tr>
				</table>
			</form>
		</div>
		
		<div id="queryCheckContent" >
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
    if ($('#checkTabs').tabs('exists', title)){
        $('#checkTabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#checkTabs').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeTab(){
	var tab = $('#checkTabs').tabs('getSelected');
	var tabIndex=$('#checkTabs').tabs('getTabIndex',tab);
	$('#checkTabs').tabs('close',tabIndex);
	submitForm("queryCheckForm");//解决Tab提交关闭列表页刷新的问题
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
	$('#queryCheckForm').find('input').each(function(){
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


	submitForm("queryCheckForm");
});
</script>

</html>


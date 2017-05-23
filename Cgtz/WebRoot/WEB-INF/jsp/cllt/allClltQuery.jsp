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
<div class="easyui-tabs" data-options="fit:true" id="allClltTabs">
<div title="催收列表">
	<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">查询</a></p>	
		<div class="content">
			<form id="queryAllClltForm" action="cllt/listAll.do" method="post" target="queryAllClltContent">			
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
			    <td>催收人:</td>
			    <td><input id="clltUid" name="clltUid" type="text" class="textbox easyui-combobox"
			    data-options="editable:false,panelHeight:'auto',valueField:'loginId',textField:'name',url:'<%=basePath%>/cllt/user/listUserByOrgAndRole.do'"/></td>
			</tr>
			<tr>
			<td>逾期还款日：</td>
			    <td colspan="3"><input id="minRetDate" name="minRetDate" editable="false" type="text" class="textbox easyui-datebox" style="width:100px;"/>-
			    <input id="maxRetDate" name="maxRetDate" editable="false" type="text" class="textbox easyui-datebox" style="width:100px;"/>
			    </td></tr>
				<tr><td></td>
				<td colspan="2">
						<input type="button" value="查询" class="btn" onclick="submitForm('queryAllClltForm')"/>
						<input type="button" value="重置" class="btn" onclick="$('#queryAllClltForm').form('clear');"/>
					</td></tr>
				</table>
			</form>
		</div>
		
		<div id="queryAllClltContent" >
		<%--
		查询列表
		 --%>
		</div>
		<div class="content">
					<table>
						<tr>
							<td><span>选择催收人：</span>
							<input id="clltMan" name="clltMan" class="textbox easyui-combobox" />
								<span><input class="btn" type="button" value="全部分配" onclick="assignUser()"/></span>
							</td>
						</tr>
					</table>
		</div>
	</div>
</div>
</div>
</div>
</body>
<script type="text/javascript">
//分配任务
function assignUser(){
			var params = $('#queryList').serialize();
			var clltMan=$("#clltMan").combobox('getValue');
			var size = $("input[id='item']:checked").length;
			if(size==0) {
				$.messager.confirm('消息', "请选择待催收请单！");
				return;
			}
			if(clltMan=='') {
				$.messager.confirm('消息', "请选择催收人！");
				return;
			}
			openMask();
			$.ajax( {
				type : "POST",
				url : "cllt/distr/updateAssign.do",
				data : params+"&clltMan="+clltMan,
				success : function(data) {
					closeMask();
					if(data.success){
						$.messager.alert('成功信息',data.message,"info",function(){
							submitForm("queryAllClltForm");
						});
					}else{
						$.messager.alert('失败信息',data.message);
					}
				},
				error : function() {
					closeMask();
					$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
				}
			});

}
//添加选项卡
function addTab(title, url){
    if ($('#allClltTabs').tabs('exists', title)){
        $('#allClltTabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#allClltTabs').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeTab(){
	var tab = $('#allClltTabs').tabs('getSelected');
	var tabIndex=$('#allClltTabs').tabs('getTabIndex',tab);
	$('#allClltTabs').tabs('close',tabIndex);
	submitForm("queryAllClltForm");//解决Tab提交关闭列表页刷新的问题
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
	$('#queryAllClltForm').find('input').each(function(){
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
//填充处理人
var tsurl='<%=basePath%>cllt/user/listUserByOrgAndRole.do';
	$("#clltMan").combobox("clear");
	$('#clltMan').combobox({
		url:tsurl,
		editable:false,
		panelHeight:'auto',
		valueField:'loginId',
		textField:'name',
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

	submitForm("queryAllClltForm");
});
</script>

</html>


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
<title>申请查询</title>
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
<div class="easyui-tabs" id="appRenewQueryTabs" data-options="fit:true">
	<div title="续贷申请列表">
		<div id="main">
			<div id="part1" class="part">
				<p class="title"><a href="javascript:void(0);">查询</a></p>
				
				<div class="content">
					<form id="queryForm" action="loan/renew/list.do" method="post" target="queryContent">
						<table>
							<tr>
								<td>合同编号:</td>
								<td><input id="contractId" name="contractId" type="text" class="textbox"/></td>
								<td>姓名/机构名称:</td>
								<td><input id="name" name="name" type="text" class="textbox"/></td>
								<td>证件号码:</td>
								<td><input id="idNo" name="idNo" type="text" class="textbox"/></td>
							</tr>
							<tr>
								<td>产品:</td>
								<td><input id="product" name="product" type="text" class="textbox easyui-combobox"
										data-options="url:'sys/datadictionary/listjason.do?keyName=productType',
													textField:'keyValue',
													valueField:'keyProp',
													value:'',
													editable:false,
													panelHeight:'auto',
													loadFilter:function(data){
												   		var opts = $(this).combobox('options');
												   		var emptyRow = {};
														emptyRow[opts.valueField] = '';
														emptyRow[opts.textField] = '请选择';
														data.unshift(emptyRow);
														return data;
													}"/>
								</td>
								<!-- <td>续贷时间:</td>
								<td colspan="2">
									<input id="inputTimeMin" name="inputTimeMin" type="text" class="textbox easyui-datebox" 
										data-options="editable:false" style="width: 90px;"/>
									&nbsp;-&nbsp;
									<input id="inputTimeMax" name="inputTimeMax" type="text" class="textbox easyui-datebox" 
										data-options="editable:false" style="width: 90px;"/>
								</td> -->				
								<td colspan="2">
									<input type="button" value="查询" class="btn" onclick="submitForm('queryForm')"/>
									<input type="button" value="重置" class="btn" onclick="$('#queryForm').form('clear');"/>
								</td>
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
	</div>
</div>
</body>
<script type="text/javascript">
//添加选项卡
function addTab(title, url){
    if ($('#appRenewQueryTabs').tabs('exists', title)){
        $('#appRenewQueryTabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#appRenewQueryTabs').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeTab(){
	var tab = $('#appRenewQueryTabs').tabs('getSelected');
	var tabIndex=$('#appRenewQueryTabs').tabs('getTabIndex',tab);
	$('#appRenewQueryTabs').tabs('close',tabIndex);
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
	submitForm("queryForm");
});
</script>

</html>


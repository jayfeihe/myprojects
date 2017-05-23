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
<title>T_WAREHOUSE查看</title>
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
		<p class="title"><a href="javascript:void(0);">查看</a></p>
		<div class="content">
			<form id="updateForm" >
				<table>
					<tbody>
					<input id="" name="" type="hidden" size="35" value="${ bean.id}" />
					<tr>
<td>仓库名称:</td>
<td><input id="name" name="name" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.name}"/></td>

<td>所属机构:</td>
<td><input id="orgName" name="orgName" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.orgName}"/></td>

<tr>
<td>负责人:</td>
<td><input id="owner" name="owner" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.owner}"/></td>

<td>联系方式:</td>
<td><input id="tel" name="tel" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.tel}"/></td>

</tr>

</tr>
<tr>
<td>所在省:</td>
<td><input id="prvn" name="prvn" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.prvn}"/></td>

<td>所在市:</td>
<td><input id="city" name="city" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.city}"/></td>
</tr>
<tr>
<td>所在区/县:</td>
<td><input id="ctry" name="ctry" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.ctry}"/></td>

<td>所在地址:</td>
<td><input id="addr" name="addr" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.addr}"/></td>
</tr>

<tr>
<td>说明:</td>
<td colspan="3"><textarea id="remark" name="remark" disabled="disabled" data-options="validType:['length[0,500]']" class="textbox easyui-validatebox" value="${bean.remark}" style="resize:none;width:415px;height:50px!important;">${bean.remark}</textarea></td>
</tr>

					<tr>
						<td>
							<input type="button" value="返回" class="btn" onclick="javascript:back()"/>
						</td>
						<td></td>
					</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>

<div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
	<img src="img/loading.gif" alt="加载中..." />
</div>

</body>

<script type="text/javascript">

//返回
function back(){
	window.history.go(-1);
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
	//将Form元素禁用
	$("#updateForm").find("input").attr("disabled", "disabled");
	$("#updateForm").find("select").attr("disabled", "disabled");
	//将easyui控件禁用
	$("#updateForm").find(".easyui-combobox").combo('disable');
	$("#updateForm").find(".easyui-numberbox").numberbox('disable');
	$("#updateForm").find(".easyui-numberspinner").numberspinner('disable');
	$("#updateForm").find(".easyui-datebox").datebox('disable');
	$("#updateForm").find(".easyui-datetimebox").datetimebox('disable');
	
	//填充select数据样例
	/*
	var tsurl="sys/datadictionary/listjason.do?keyName=repaymethod";
	$("#repayMethod").combobox("clear");
	$('#repayMethod').combobox({url:tsurl, valueField:'keyProp', textField:'keyValue'});
	*/
});

</script>
</html>


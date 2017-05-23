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
<title>保证金信息表查看</title>
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
<tr>
<td>合同编号:</td>
<td><input id="contractId" name="contractId" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.contractId}"/></td>
</tr>
<tr>
<td>保证金:</td>
<td><input id="amt" name="amt" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.amt}"/>元</td>
</tr>
<tr>
<td>时间:</td>
<td><input id="getTime" name="getTime" type="text" editable="false" class="textbox easyui-datetimebox" value="${bean.getTimeStr}"/></td>
</tr>
<tr>
<td>处理状态:</td>
<td><input id="state" name="state" type="text"  class="textbox easyui-validatebox" 
<c:choose>
<c:when test="${bean.state eq '1'}">value="未处理"</c:when>
<c:when test="${bean.state eq '2'}">value="退还借款人"</c:when>
<c:when test="${bean.state eq '3'}">value="垫付本金"</c:when>
</c:choose>/>
</td>
</tr>
<tr>
<td>处理说明:</td>
<td><textarea id="remarks" name="remarks" type="text" data-options="validType:['length[0,300]']" class="textbox easyui-validatebox" style="resize:none;width:625px;height:50px!important;">
${bean.remarks}
</textarea>
</td>
</tr>
<tr>
<td>处理时间:</td>
<td><input id="handleTime" name="handleTime" type="text" editable="false" class="textbox easyui-datetimebox" value="${bean.handleTimeStr}"/></td>
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
	$("#updateForm").find("textarea").attr("disabled","disabled");
	//将easyui控件禁用
	$("#updateForm").find(".easyui-combobox").combo('disable');
	$("#updateForm").find(".easyui-numberbox").numberbox('disable');
	$("#updateForm").find(".easyui-numberspinner").numberspinner('disable');
	$("#updateForm").find(".easyui-datebox").datebox('disable');
	$("#updateForm").find(".easyui-datetimebox").datetimebox('disable');
	
});

</script>
</html>


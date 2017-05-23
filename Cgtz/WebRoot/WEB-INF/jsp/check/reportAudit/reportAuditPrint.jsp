<%@ page contentType="text/html; charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>"/>
<title>逾期报告打印页面</title>
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
<div align="center"  style="margin:0 auto;width:625px;border-bottom: 1px solid black;">【草根金融信息服务（杭州）有限公司】</div>
<br/>	
<table align="center" border="1px" cellspacing="0" cellpadding="0" width="625px" style="border:1px solid black;line-height: 2;">
<tr>
<td colspan="4"><img src="img/logocg.png" width="150px" height="60px" style="margin-right:100px; "/>内部工作联系函</td>
</tr>
<tr>
<td align="center">发文部门</td><td>${bean.orgName}</td><td align="center">接收部门</td><td>稽查部门</td>
</tr>
<tr>
<td align="center" height="300px">正文</td><td colspan="3">${bean.submitRemark}</td>
</tr>
<tr>
<td align="center" height="100px">备注</td><td colspan="3"></td>
</tr>
<tr>
<td align="center">发文人</td><td>${bean.saleName}:${bean.tel}</td><td rowspan="2" align="center" height="150px">发文部门经理意见</td><td rowspan="2">${bean.orgAuditRemark }</td>
</tr>
<tr>
<td align="center">发文时期</td><td>${bean.submitTmeStr}</td>
</tr>
<tr>
<td height="150px" align="center">公司领导意见</td><td colspan="3"></td>
</tr>
</table>
<br/>
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
});

</script>
</html>


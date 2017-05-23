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
<title>网络下放资金明细查询</title>
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
<div class="easyui-tabs" id="netFundsTabs" data-options="fit:true">
<div title="网络下放资金明细列表">
	<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">查询</a></p>
		
		<div class="content">
			<form id="queryNetFundsForm" action="report/netFunds/list.do" method="post" target="queryContent">
				<table>
<tr>
<td>贷款类别:</td>
<td><input id="loanType" name="loanType" type="text"  class="textbox easyui-combobox" 
    data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
    data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'01','keyValue':'个人'},{'keyProp':'02','keyValue':'公司'}]"/>
</td>
<td>债权人卡号:</td>
<td><input id="acctCode" name="acctCode" type="text"  class="textbox easyui-validatebox"/>
</td>
</tr>
<tr>
<td>融资方式:</td>
<td><input id="getLoanWay" name="getLoanWay" type="text"  class="textbox easyui-combobox" 
    data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
    data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'1','keyValue':'直投'},
    {'keyProp':'2','keyValue':'债权转让'},{'keyProp':'3','keyValue':'线下'}]"/>
</td>
<td>放款时间:</td>
<td><input id="minCreateTime" name="minCreateTime" type="text" editable="false"  class="textbox easyui-datebox" style="width:90px;"/>
-<input id="maxCreateTime" name="maxCreateTime" type="text" editable="false"  class="textbox easyui-datebox" style="width:90px;"/>
</td>

</tr>

					<tr><td></td>
						<td>
							<input type="button" value="查询" class="btn" onclick="submitForm('queryNetFundsForm')"/>
							<input type="button" value="导出" class="btn" onclick="exportExcel('queryNetFundsForm')"/>
							<input type="button" value="重置" class="btn" onclick="$('#queryNetFundsForm').form('clear');"/>
						</td>
						<td></td>
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
	$('#queryNetFundsForm').find('input').each(function(){
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
//导出
function exportExcel(fromId) {
	$('#' + fromId).attr("action","<%=basePath%>report/netFunds/excel.do");
	$('#' + fromId).attr("method","post");
	$('#' + fromId).submit();
}
//页面加载完动作
$(document).ready(function() {
//填充select数据样例


	submitForm("queryNetFundsForm");
});
</script>

</html>


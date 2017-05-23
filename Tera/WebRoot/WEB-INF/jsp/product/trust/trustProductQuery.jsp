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
<title>信托产品查询</title>
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
		<p class="title"><a href="javascript:void(0);">信托产品查询</a></p>
		
		<div class="content">
			<form id="queryForm" action="product/trust/list.do" method="post" target="queryContent">
				<table>
					<tr>
<td>产品:</td>
<td><input id="productId" name="productId" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"/></td>
</tr>
<tr>
<td>期限:</td>
<td><input id="period" name="period" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>机构名称:</td>
<td><input id="company" name="company" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"/></td>
</tr>
<tr>
<td>利率:</td>
<td><input id="interestRate" name="interestRate" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>起点金额:</td>
<td><input id="startAmount" name="startAmount" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>用途:</td>
<td><input id="useage" name="useage" type="text" data-options="validType:['length[0,200]']" class="textbox easyui-validatebox"/></td>
</tr>

					<tr>
						<td>
							<input type="button" value="查询" class="btn" onclick="submitForm('queryForm')"/>
							<input type="reset" value="重置" class="btn" onclick="$('#queryForm').form('clear');"/>
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

<!-- <div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
	<img src="img/loading.gif" alt="加载中..." />
</div> -->

</body>
	
<script type="text/javascript">

function submitForm(fromId) {
	var formAction = $('#' + fromId).attr("action");
	var targetDiv = $('#' + fromId).attr("target");
	var params = $('#' + fromId).serialize();
	//弹出异步加载 遮罩
	openMask();
	$.ajax({
		type : "POST",
		url : formAction,
		data : encodeURI(params + "&targetDiv=" + targetDiv),
		dataType : "html",
		success : function(data) {
			closeMask();
			$('#' + targetDiv).html(data);
		},
		error : function() {
			closeMask();
			
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
		}
	});
}

//打开Loading遮罩并修改样式
/* function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
} */


//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd"); 
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>"/>
<title>逾期计算器弹窗</title>
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
		<p class="title"><a href="javascript:void(0);">逾期计算器&nbsp;&nbsp;&nbsp;<%=request.getParameter("contractNo") %></a></p>
		
		<div class="content">
			<form id="queryForm" action="collectionBase/phone/lateDaysCalList.do" method="post" target="queryContent">
				<table style="width: 100%">
					<input id="id" name="id" type="hidden"  size="35" value="<%=request.getParameter("id") %>" />
					<tr align="left">
						<td width="10%">预计还款日期:</td>
						<td width="10%"><input id="loanDate" name="loanDate" type="text" class="textbox easyui-datebox"  value="<%=sim.format(new Date()) %>" /></td>
						<td width="10%">应收合计:</td>
						<td width="10%"><input id="bqyhkhjAll" name="bqyhkhjAll" disabled="disabled" type="text" data-options="min:0,precision:2"  class="textbox easyui-numberbox" value=""/></td>
						<td width="10%">
							<input type="button" value="查询" class="btn"  style="margin-left: 100px" onclick="submitForm('queryForm')"/>
						</td>
						<td width="10%">
							<input type="button" value="重置" class="btn" style="margin-left: 50px" onclick="formClear();"/>
						</td>			
						<td></td>				
					</tr>		
					<tr>
						<td>滞纳金:</td>
						<td><input id="znAllAmount" name="znAllAmount" disabled="disabled" type="text" data-options="min:0,precision:2"  class="textbox easyui-numberbox" value=""/></td>
						<td>罚息:</td>
						<td><input id="dbAllamount" name="dbAllamount" disabled="disabled" type="text" data-options="min:0,precision:2"  class="textbox easyui-numberbox" value=""/></td>
									
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
</body>
<script type="text/javascript">
function formClear(){
	$('#loanDate').datetimebox('clear');
	$('#bqyhkhjAll').attr('value','');
	$('#znAllAmount').attr('value','');
	$('#dbAllamount').attr('value','');
	
}
//添加选项卡
function addTab(title, url){
    if ($('#verifyTable').tabs('exists', title)){
        $('#verifyTable').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#verifyTable').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeTab(){
	var tab = $('#verifyTable').tabs('getSelected');
	var tabIndex=$('#verifyTable').tabs('getTabIndex',tab);
	$('#verifyTable').tabs('close',tabIndex);
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
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
		}
	});
}
//页面加载完动作
$(document).ready(function() {
//填充select数据样例
<%--
	var tsurl="sys/datadictionary/listjason.do?keyName=repaymethod";
	$("#repayMethod").combobox("clear");
	$('#repayMethod').combobox({
		url:tsurl,
		valueField:'keyProp',
		textField:'keyValue',
		//添加空白行
		loadFilter:function(data){
	   		var opts = $(this).combobox('options');
	   		var emptyRow = {};
			emptyRow[opts.valueField] = '&nbsp;';
			emptyRow[opts.textField] = '...';
			data.unshift(emptyRow);
			return data;
		}
	});
--%>
submitForm("queryForm");	
});
</script>

</html>


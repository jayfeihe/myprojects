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
<title>人工撮合审批查询</title>
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
		<p class="title"><a href="javascript:void(0);">人工撮合审批查询</a></p>
		<div class="content">
<!--			<form id="queryForm" action="loan/manualverify/loan2matchList.do" method="post" target="queryContent">-->
			<form id="queryForm" action="loan/manualverify/list.do" method="post" target="queryContent">
				<table>
					<tr>
						<td>
							借款申请号:
						</td>
						<td>
							<input id="loanAppId" name="loanAppId" type="text"
								data-options="validType:['length[0,50]']"
								class="textbox easyui-validatebox" />
						</td>
						<td>
							姓名:
						</td>
						<td>
							<input id="name" name="name" type="text"
								data-options="validType:['length[0,50]']"
								class="textbox easyui-validatebox" />
						</td>
						<td>所属机构:</td>
						<td>
							<input id="orgId" name="orgId" type="text" class="easyui-combobox" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"value="${bean.orgId}"/>
						</td>
					</tr>
					<tr>
						<td>
							身份证号/组织机构代码证:
						</td>
						<td>
							<input id="idNo" name="idNo" type="text"
								data-options="validType:['length[0,18]','idNo']" 
								class="textbox easyui-validatebox" />
						</td>
						<td>
							手机号:
						</td>
						<td>
								<input id="mobile" name="mobile" type="text"
								data-options="validType:['length[0,50]']"
								class="textbox easyui-validatebox" />
								
						</td>
					</tr>
	
					<tr>
						<td>
							<input type="button" value="查询" class="btn"
								onclick="submitForm('queryForm')"/>
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
	openMask();
	
 $.ajax({
		type : "POST",
		url : formAction,
		data : encodeURI(params + "&targetDiv=" + targetDiv),
		dataType : "html",
		success : function(data) {
			$('#' + targetDiv).html(data);
			closeMask();
		},
		error : function() {
			closeMask();
			
			$.messager.confirm('消息', '加载失败。', function(ok){
	            if (ok){
//	 				window.history.go(-1);
	            }
	    	});
		}
	}); 
}
/* function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
} */

$(document).ready(function(){
	submitForm("queryForm");
	var tsurl="<%=basePath%>sys/org/allOrgList.do?loginid=all&timestamp="+(new Date()).getTime();
	$("#orgId").combobox("clear");
	$("#orgId").combobox({url:tsurl,valueField:'orgId',textField:'orgName'});
});

</script>
</html>


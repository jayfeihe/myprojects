<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />
		<title>签约</title>
		<link href="css/global.css" type="text/css" rel="stylesheet" />
		<link href="css/icon.css" type="text/css" rel="stylesheet" />
		<link href="css/default/easyui.css" type="text/css" rel="stylesheet" />
		<script src="js/jquery.min.js" type="text/javascript"></script>
		<script src="js/jquery.form.js" type="text/javascript"></script>
		<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
		<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
		
		<style type="text/css"></style>
	</head>
	<body>
		<div id="main">
			<div id="part1" class="part">
				<p class="title"><a href="javascript:void(0);">信用借款签约查询</a></p>

				<div class="content">
					<form id="queryForm" action="contract/list.do" method="post" target="queryContent">
						<table>
							<tr>
								<td>申请编号:</td>
								<td>
									<input name="appId" type="text" class="textbox easyui-validatebox" />
								</td>
								<td>姓名:</td>
								<td>
									<input name="name" type="text" class="textbox easyui-validatebox" />
								</td>
								<td>身份证:</td>
								<td>
									<input name="idNo" type="text" class="textbox easyui-validatebox" data-options="validType:['idcard']"/>
								</td>
							</tr>
							<tr>
								<td>合同编号:</td>
								<td>
									<input name="contractNo" type="text" class="textbox easyui-validatebox" />
								</td>
								<td>营业部:</td>
								<td>
									<input id="orgId" name="orgId" type="text" class="easyui-combobox" editable="false"/>
								</td>
								<!-- <td>营销人员:</td>
								<td>
									<input id="staffNo" name="staffNo" type="text" editable="false" class="easyui-combobox"/>
								</td> -->
							</tr>
							<tr>
								<td colspan="2">
									<input type="button" value="查询" class="btn" onclick="submitForm('queryForm')" />
									<input type="button" value="重置" class="btn" onclick="$('#queryForm').form('clear');"/>
								</td>
							</tr>
						</table>
					</form>
				</div>

				<div id="queryContent">
					<%--
					查询列表
					 --%>
				</div>
				
			</div>
		</div>

		<!-- <div id="loading" class="easyui-window" title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false">
			<img src="img/loading.gif" alt="加载中..." />
		</div> -->

	</body>

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
		url : formAction,
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

/* //打开Loading遮罩并修改样式
function openLoading() {
	$('#loading').window('open');
	$("#loading").attr("class", "");
	$("div[class='panel window']").css("position", "absolute");
	$("div[class='panel window']").attr("class", "");
	$("div[class='window-shadow']").attr("class", "");
} */

//页面加载完动作
$(document).ready(function() {
	
	/* //填充select数据 产品
	var tsurl="product/hedao/listjason.do?type=3";
	$("#product1").combobox("clear");
	$('#product1').combobox({url:tsurl,
		valueField:'name', 
		textField:'name'
   	}) */;
	
	/* //初始化 营销人员,query
	var sales="sales/listjason.do?state=1&orgId=${login_org.orgId}";
	$('#staffNo').combobox({url:sales, valueField:'staffNo', textField:'name'});
	 */
	var tsurl="<%=basePath%>sys/org/subOrg.do?orgId=${login_org.orgId}&timestamp="+(new Date()).getTime();
	$("#orgId").combobox("clear");
	$("#orgId").combobox({url:tsurl,valueField:'orgId',textField:'orgName'});
	
	submitForm("queryForm");
	
});
</script>
</html>


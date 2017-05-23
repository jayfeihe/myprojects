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
		<title>借款端申请表查询</title>
		<link href="css/global.css" type="text/css" rel="stylesheet" />
		<link href="css/icon.css" type="text/css" rel="stylesheet" />
		<link href="css/default/easyui.css" type="text/css" rel="stylesheet" />
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
				<p class="title">
					<a href="javascript:void(0);">风险初审查询</a>
				</p>

				<div class="content">
					<form id="queryForm" action="" method="post" target="queryContent">
						<table>
							<tr>
								<td class="td01">姓名/机构全称:</td>
								<td><input id="name" name="name" type="text"
										data-options="validType:['length[0,50]']"
										class="textbox easyui-validatebox"/></td>
								<td class="td01">借款金额:</td>
								<td><input id="amountMin" name="amountMin" style="width: 90px;" type="text" editable="false"
										data-options="min:0,precision:2"
										class="textbox easyui-numberbox" />
									至
									<input id="amountMax" name="amountMax" style="width: 90px;" type="text" editable="false"
										data-options="min:0,precision:2"
										class="textbox easyui-numberbox" />
								</td>
								<td class="td01">机构:</td>
								<td><input id="orgId" name="orgId" type="text"
										class="easyui-combobox" style="width:152px;" editable="false"  />
								</td>
								<td class="td01">手机号:</td>
								<td><input id="mobile" name="mobile" type="text"
										data-options="validType:['length[0,50]']"
										class="textbox easyui-validatebox" /></td>
							</tr>
							<tr>
								<td class="td01">身份证/组织机构代码:</td>
								<td><input id="idNo" name="idNo" type="text"
										data-options="validType:['length[0,18]','idNo']" 
										class="textbox easyui-validatebox" /></td>
								
								<td class="td01">申请时间:</td>
								<td><input id="createTimeMin" name="createTimeMin" style="width: 90px;"  type="text" editable="false"
										data-options=""
										class="textbox easyui-datebox" />
										至
									<input id="createTimeMax" name="createTimeMax" style="width: 90px;" type="text"
										data-options=""
										class="textbox easyui-datebox" editable="false"  />
										
								</td>
								<%--<td class="td01">
									状态:
								</td>
								<td>
									<input id="state" name="state" type="text"
										data-options="validType:['length[0,2]']"
										class="textbox easyui-validatebox" />
								</td>
								--%><td></td>
								<td>
									
								</td>
							</tr>
							<tr>
								<td colspan="8"><input type="button" value="查询" class="btn"
										onclick="submitForm('queryForm')" />
									<input type="reset" value="重置" class="btn" onclick="$('#queryForm').form('clear');"/></td>
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

		<!-- <div id="loading" class="easyui-window" title=""
			data-options="border:false,modal:true,closed:true,draggable:false,resizable:false">
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
	$.ajax( {
		type : "POST",
		url : "loan/firstverify/list.do",
		data : encodeURI(params + "&targetDiv=" + targetDiv),
		dataType : "html",
		success : function(data) {
			closeMask();
			$('#' + targetDiv).html(data);
		},
		error : function() {
			closeMask();
			alert("数据加载失败！");
		}
	});
}

//打开Loading遮罩并修改样式
/* function openLoading() {
	$('#loading').window('open');
	$("#loading").attr("class", "");
	$("div[class='panel window']").css("position", "absolute");
	$("div[class='panel window']").attr("class", "");
	$("div[class='window-shadow']").attr("class", "");
} */

//页面加载完动作
$(document).ready(function() {

	var tsurl="sys/org/subOrg.do?orgId=${login_org.orgId}";
	$("#orgId").combobox("clear");
	$('#orgId').combobox({
		url:tsurl,
		valueField:'orgId',
		textField:'orgName',
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
	
	
	submitForm("queryForm");
});
</script>
</html>


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
		<title>欺诈分单</title>
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
				<p class="title"><a href="javascript:void(0);">欺诈分单查询</a></p>

				<div class="content">
					<form id="queryForm" action="collection/cheat/assign/list.do" method="post" target="queryContent">
						<table>
							<tr>
								<td>合同编号:</td>
								<td><input id="contractNo" name="contractNo" type="text"
										data-options="validType:['length[0,30]']"
										class="textbox easyui-validatebox" />
								</td>
										
								<td>客户姓名:</td>
								<td><input id="customerName" name="customerName" type="text"
										data-options="validType:['length[0,50]']"
										class="textbox easyui-validatebox" />
								</td>
										
								<td>身份证:</td>
								<td><input id="idNo" name="idNo" type="text"
										data-options="validType:['idcard']"
										class="textbox easyui-validatebox" />
								</td>
								<td>营业部:</td>
								<td><input id="orgId" name="orgId" type="text" 
										editable="false"  class="easyui-combobox"/>
								</td>
							</tr>
							<tr>
							<td>当前催收人:</td>
								<td><input id="applyUid" name="applyUid" type="text"
										data-options="validType:['length[0,50]']"
										class="textbox easyui-validatebox" />
								</td>
								<td>分配状态:</td>
								<td>
									<select class="easyui-combobox" name="distributionState" editable="false" style="width:130px;">
										<option value="0" selected="selected">未分配</option>
										<option value="1">已分配</option>
									</select>
								</td>
								<td>状态:</td>
								<td>
									<select class="easyui-combobox" name="state" editable="false" style="width:130px;">
										<option value="" selected="selected">全部</option>
										<option value="1">欺诈申请</option>
										<option value="2">欺诈复核</option>
										<option value="3">欺诈审批</option>
										<option value="10">欺诈审批退回</option>
										<option value="4">欺诈生效</option>
									</select>
								</td>
							</tr>
							<tr>
							
							<td>逾期天数:</td>
								<td>
									<input id="lateDaysMin" name="lateDaysMin" type="text"
										style="width: 90px;" class="textbox easyui-validatebox" />
										至
									 <input id="lateDaysMax" name="lateDaysMax" type="text"
										style="width: 90px;" class="textbox easyui-validatebox" /> 
								</td>
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
				
				<div class="content">
					<table>
						<tr>
							<td><span>选择复核人：</span>
							<input id="newprocesser" name="newprocesser" type="text" data-options="" 
									class="easyui-combobox" editable="false"/>
									&nbsp;&nbsp;
									<span><input class="btn" type="button" value="全部分配" onclick="assignUser()"/></span>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>

		<div id="loading" class="easyui-window" title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false">
			<img src="img/loading.gif" alt="加载中..." />
		</div>

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

//分配任务
function assignUser(){
		var params = $('#queryList').serialize();
		var newprocesser=$("#newprocesser").combobox('getValue');
		var size = $("input[id='item']:checked").length;
		if(size==0) {
			$.messager.confirm('消息', "请选择待司法申请单！");
			return;
		}
		if(newprocesser=='') {
			$.messager.confirm('消息', "请选择分配人！");
			return;
		}
		openMask();
		$.ajax( {
			type : "POST",
			url : "collection/cheat/assign/update.do",
			data : params+"&newprocesser="+newprocesser,
			success : function(data) {
				closeMask();
				if(data.success){
					$.messager.alert('成功信息',data.message,"info",function(){
						submitForm("queryForm");
					});
				}else{
					$.messager.alert('失败信息',data.message);
				}
			},
			error : function() {
				closeMask();
				$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
			}
		});
}


//打开Loading遮罩并修改样式
function openLoading() {
	$('#loading').window('open');
	$("#loading").attr("class", "");
	$("div[class='panel window']").css("position", "absolute");
	$("div[class='panel window']").attr("class", "");
	$("div[class='window-shadow']").attr("class", "");
}

//页面加载完动作
$(document).ready(function() {

	var tsurl=encodeURI("sys/user/listUserByOrgAndRole.do?orgId=86&roleNames=欺诈复核专员");
	//填充复核人
	$("#newprocesser").combobox("clear");
	$('#newprocesser').combobox({
		url:tsurl,
		valueField:'loginId',
		textField:'name',
		//添加空白行
		loadFilter:function(data){
	   		var opts = $(this).combobox('options');
	   		var emptyRow = {};
			emptyRow[opts.valueField] = '';
			emptyRow[opts.textField] = '请选择';
			data.unshift(emptyRow);
			return data;
		}
	});
	//营业部
	$("#orgId").combotree({
		url:"sys/org/listDataByLevelAndOrgId.do?level=4",
		method:'get',
		required:false

	});
	//var tsurl="<%=basePath%>sys/org/subOrg.do?orgId=${login_org.orgId}&timestamp="+(new Date()).getTime();
	//$("#orgId").combobox("clear");
	//$("#orgId").combobox({url:tsurl,valueField:'orgId',textField:'orgName'});

	submitForm("queryForm");
});
</script>
</html>


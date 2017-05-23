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
		<title>落地催列表</title>
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
				<p class="title"><a href="javascript:void(0);">落地催列表</a></p>

				<div class="content">
					<form id="queryForm" action="collection/visit/detail/list.do" method="post" target="queryContent">
						<table>
							<tr>
								<td>客户姓名:</td>
								<td><input id="customerName" name="customerName" type="text"
										data-options="validType:['length[0,20]']"
										class="textbox easyui-validatebox" /></td>
										
								<td>身份证:</td>
								<td><input id="idNo" name="idNo" type="text"
										data-options="validType:['idcard']"
										class="textbox easyui-validatebox" /></td>
								<td>合同编号:</td>
								<td><input id="contractNo" name="contractNo" type="text"
										data-options="validType:['length[0,50]']"
										class="textbox easyui-validatebox" /></td>
								<td>联系方式:</td>
								<td><input id="customerTel" name="customerTel" type="text" 
										data-options="validType:['length[0,20]']" 
										class="textbox easyui-validatebox"/></td>
								</tr>
								<tr>
								<td>产品:</td>
								<td><input id="product" name="product" type="text"
										data-options="validType:['length[0,50]']"
										editable="false" class="easyui-combobox" /></td>
								<td>营业部:</td>
								<td>
									<input   id="orgId" name="orgId" type="text"  class="easyui-combotree"  editable="false"/>
								</td>
								<td>落地催摘要:</td>
								<td>
									<input id="phoneSummary" name="phoneSummary" type="text" class="easyui-combobox" editable="false"/>
								</td>
								<td>当前状态:</td>
								<td>
								<select class="easyui-combobox" id="state" name="state" editable="false" style="width:150px;">
										<option value="" selected="selected">全部</option>
										
										<option value="3"  >落地催申请</option>
										<option value="4"  >落地催处理中</option>
										
										<option value="7"  >催收完成</option>
										<option value="8"  >欺诈申请</option>
										<option value="9"  >欺诈复核处理中</option>
									 	<option value="10" >欺诈审批处理中</option>
										 
										<option value="12" >司法申请</option>
										<option value="13" >司法复核处理中</option>
										<option value="14" >司法审批处理中</option>
										 <option value="16" >外包待审核</option>
								</select>
								</td>
								</tr>
								<tr>
								<td>放款平台:</td>
								<td>
									<input id="loanPlatform" name="loanPlatform" type="text" class="easyui-combobox" editable="false"/>
								</td>
								
								<td>逾期天数:</td>
								<td>
								<input id="lateDaysMin" name="lateDaysMin" type="text" data-options="min:0,precision:0" 
									class="textbox easyui-numberbox" style="width:65px;"/>
								至
								<input id="lateDaysMax" name="lateDaysMax" type="text" data-options="min:0,precision:0" 
									class="textbox easyui-numberbox" style="width:65px;"/></td>
								<td>处理状态:</td>
								<td>
									<select class="easyui-combobox" id="handleState" name="handleState" editable="false" style="width:150px;">
										<option value="" selected="selected">全部</option>
										<option value="N"  >未处理</option>
										<option value="Y"  >已处理</option>
								</select>
								 
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
	var tsurl=encodeURI("sys/user/listUserByOrgAndRole.do?orgId=86&roleNames=落地催收专员");
 
	$("#collectionUidCur").combobox("clear");
	$('#collectionUidCur').combobox({
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
		 
	$("#phoneSummary").combobox({
		url:"sys/datadictionary/listjason.do?keyName=collectionsummary",
		valueField:'keyProp',    
   	 	textField:'keyValue',
		method:'get',
		required:false

	});
	$("#orgId").combotree({
		url:"sys/org/listDataByLevelAndOrgId.do?level=4",
		method:'get',
		required:false

	});
	///*var tsurl="<%=basePath%>sys/org/subOrg.do?orgId=${login_org.orgId}&timestamp="+(new Date()).getTime();
	//$("#orgId").combobox("clear");
	//$("#orgId").combobox({url:tsurl,valueField:'orgId',textField:'orgName'});*/	
	
	//填充select数据 产品
	tsurl="product/hedao/listjason.do?type=3";
		$("#product").combobox("clear");
		$('#product').combobox({url:tsurl, valueField:'name', textField:'name'});
	//填充select数据 平台
	tsurl="channeltotal/listjason.do?type=3";
		$("#loanPlatform").combobox("clear");
		$('#loanPlatform').combobox({url:tsurl, valueField:'channelCode', textField:'channelName'});	 
	submitForm("queryForm");
});
</script>
</html>


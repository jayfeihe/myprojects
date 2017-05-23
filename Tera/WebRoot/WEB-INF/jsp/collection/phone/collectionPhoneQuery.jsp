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
<title>催收信息基本表查询</title>
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
		<p class="title"><a href="javascript:void(0);">查询</a></p>
		
		<div class="content">
			<form id="queryForm" action="collectionBase/phone/actionList.do" method="post" target="queryContent">
				<table>
				
					<tr>
						<td>客户姓名:</td>
						<td><input id="customerName" name="customerName" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox"/></td>
						<td>证件号码:</td>
						<td><input id="idNo" name="idNo" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox"/></td>
						<td>合同编号:</td>
						<td><input id="contractNo" name="contractNo" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"/></td>
						<td>联系方式:</td>
						<td><input id="customerTel" name="customerTel" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox"/></td>
						
					</tr>		
					<tr>
						<td>产品:</td>
						<td><input id="product" name="product" type="text" data-options="validType:['length[0,20]']" class="easyui-combobox"/></td>
						<td>营业部:</td>
						<td><input id="orgName" name="orgName" type="text"  class="easyui-combotree"/></td>
						
						<td>电催摘要:</td>
						<td><input id="phoneSummary" name="phoneSummary" type="text"  class="easyui-combobox"/>
						
						</td>
						<td>状态:</td>
						<td><select class="easyui-combobox" id="state" name="state" editable="false" style="width:152px;">
										<option value="" selected="selected">全部</option>
									<!-- 	<option value="1">待催收</option> -->
										<option value="2">电催处理中</option>
									<!--	<option value="3">落地催收待分配</option> -->
									<!--	<option value="4">落地催处理中</option> -->
									<!--	<option value="5">协催待分配</option> -->
									<!--	<option value="6">协催处理中</option> -->
										<option value="7">催收完成</option>
										<option value="8">欺诈申请</option>
										<option value="9">欺诈复核处理中</option>		
										<option value="10">欺诈审批处理中</option>
									<!--	<option value="11">欺诈认定生效</option>-->
										<option value="12">司法申请</option>
										<option value="13">司法复核处理中</option>
										<option value="14">司法审批处理中</option>
									<!--	<option value="15">司法认定生效</option>-->
									</select>
						</td>
						
					</tr>		
					<tr>
						<td>放款平台:</td>
						<td><input id="loanPlatform" name="loanPlatform" type="text"  class="easyui-combobox"/></td>
						<td>逾期天数:</td>
						<td><input id="minLateDays" name="minLateDays" type="text" data-options="min:0,precision:0" style="width: 70px" class="textbox easyui-numberbox" onblur="timeCompare()"/>&nbsp;-
							<input id="maxLateDays" name="maxLateDays" type="text" data-options="min:0,precision:0" style="width: 70px" class="textbox easyui-numberbox" onblur="timeCompare()"/></td>
						<td>处理状态:</td>
						<td><select class="easyui-combobox" id="handleState" name="handleState" editable="false" style="width:152px;">
										<option value="" >全部</option>
										<option value="N" >未处理</option>
										<option value="Y">已处理</option>
							</select>
						</td>
					</tr>		

					<tr>
						<td>
							<input type="button" value="查询" class="btn" onclick="submitForm('queryForm')"/>
							<input type="button" value="重置" class="btn" onclick="$('#queryForm').form('clear');"/>
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
</body>
<script type="text/javascript">
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
			/**if(data.success==true){
			}else{
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！')
			}**/
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
	 getOrg();
	
	submitForm("queryForm");
});
//树形式显示营销团队
function getOrg() {
	$("#orgName").combotree({
		url:"sys/org/listDataByLevelAndOrgId.do?level=4",
		method:'get',
		required:false

	});
	
	$("#phoneSummary").combobox({
		url:"sys/datadictionary/listjason.do?keyName=collectionsummary",
		valueField:'keyProp',    
   	 	textField:'keyValue',
		method:'get',
		required:false

	});
	$("#loanPlatform").combobox({
		url:"channeltotal/selectList.do?state=1",
		valueField:'channelCode',    
   	 	textField:'channelName',
		method:'get',
		required:false

	});
	var tsurl="<%=basePath%>sys/org/subOrg.do?orgId=${login_org.orgId}&timestamp="+(new Date()).getTime();
	$("#orgId").combobox("clear");
	$("#orgId").combobox({url:tsurl,valueField:'orgId',textField:'orgName'});	
	
	//填充select数据 产品
	tsurl="product/hedao/listjason.do?type=3";
		$("#product").combobox("clear");
		$('#product').combobox({url:tsurl, valueField:'name', textField:'name'});
}
function timeCompare(){
	if(($('#minLateDays').val()!="")&&($('#maxLateDays').val()!="")&&($('#minLateDays').val()>$('#maxLateDays').val())){
		$.messager.alert('消息', '最小逾期天数不能大于最大逾期天数！');
		}
	}
</script>

</html>


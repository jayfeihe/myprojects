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
<title>信用贷款复核查询</title>
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
    <body style="height: '100%'">
	<div id="reviewTable" class="easyui-tabs" data-options="fit:true">
	<div title="复核"> 
	<div id="main">  
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">信用借款复核查询</a></p>
		
		<div class="content">
			<form id="queryForm" action="credit/review/list.do" method="post" target="queryContent">
				<table>
					<tr>
<%--						<td>合同编号:</td>--%>
<%--						<td><input id="" name="" type="text" data-options="validType:['length[0,30]']" --%>
<%--								class="textbox easyui-validatebox"/></td>--%>
						<td>申请编号:</td>
						<td><input id="appId" name="appId" type="text" data-options="validType:['length[0,30]']" 
								class="textbox easyui-validatebox"/></td>
						<td>姓名:</td>
						<td><input id="name" name="name" type="text" data-options="validType:['length[0,50]']"
								class="textbox easyui-validatebox" /></td>
								
						<td>身份证:</td>
						<td><input id="idNo" name="idNo" type="text" data-options="validType:['idcard']"
								class="textbox easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>进件时间:</td>
						<td><input id="inputTimeMin" name="inputTimeMin" style="width: 90px;" type="text" editable="false" 
								class="textbox easyui-datebox" />
							至
						<input id="inputTimeMax" name="inputTimeMax" style="width: 90px;" type="text" editable="false" 
								class="textbox easyui-datebox" /></td>
						<td>决策渠道:</td>
						<td><input id="platformName" name="platformName" type="text" class="easyui-combobox" editable="false"/></td>
						<td>决策产品:</td>
						<td><input id="product1" name="product1" type="text"  data-options="validType:['length[0,50]']" 
								class="easyui-combobox" editable="false"/></td>
					</tr>
					<tr>
						<td>复核时间:</td>
						<td><input id="updateTimeMin" name="updateTimeMin" style="width: 90px;" type="text" editable="false" 
								class="textbox easyui-datebox" />
							至
						<input id="updateTimeMax" name="updateTimeMax" style="width: 90px;" type="text" editable="false" 
								class="textbox easyui-datebox" /></td>
						<td>处理状态:</td>
						<td>
							<select class="easyui-combobox" name="stateTask" editable="false" style="width:152px;">
								<option value="waitTask" selected="selected">待处理</option>
								<option value="inTask">处理中</option>
								<option value="offTask">已完成</option>
							</select>
						</td>
<%--						<td>复核人:</td>--%>
<%--						<td><input id="operator" name="operator" type="text" data-options="validType:['length[0,50]']"--%>
<%--								class="textbox easyui-validatebox" /></td>--%>
					</tr>
					<tr>
						<td colspan="2">
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
	</div>
	</div>
</body>
	
<script type="text/javascript">
//添加选项卡
function addTab(title, url){
    if ($('#reviewTable').tabs('exists', title)){
        $('#reviewTable').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#reviewTable').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeTab(){
    var tab = $('#reviewTable').tabs('getSelected');
    var tabIndex=$('#reviewTable').tabs('getTabIndex',tab);
    $('#reviewTable').tabs('close',tabIndex);
	submitForm("queryForm");
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
	$.ajax({
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

//页面加载完动作
$(document).ready(function (){
	//填充select数据样例
	/*
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
	*/
	
	//填充select数据 渠道
	 var channelurl="channeltotal/listjason.do";
	 $("#platformName").combobox("clear");
	 $('#platformName').combobox({
	 	url:channelurl,
	 	valueField:'channelCode', 
	 	textField:'channelName',
	 	onChange: function(newValue, oldValue){             
	 		$('#product1').combobox('clear');
	 		var producturl = "product/hedao/listjason.do?type=3&belongChannel=" + encodeURI(newValue);
	 		$('#product1').combobox('reload',producturl); 
	 	},
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
	 //填充select数据 产品
	 var belongChannel = $('#platformName').combobox('getValue');
	 var producturl = "product/hedao/listjason.do?type=3&belongChannel=" + encodeURI(belongChannel);
	 $("#product1").combobox("clear");
	 $('#product1').combobox({
	 	url:producturl,
	 	valueField:'name', 
	 	textField:'productName',
		//添加空白行
		loadFilter:function(data){
       		var opts = $(this).combobox('options');
       		var emptyRow = {};
			emptyRow[opts.valueField] = '';
			emptyRow[opts.textField] = '全部';
			data.unshift(emptyRow);
 			return data;
		}
	 });
	
	submitForm("queryForm");
});
</script>
</html>


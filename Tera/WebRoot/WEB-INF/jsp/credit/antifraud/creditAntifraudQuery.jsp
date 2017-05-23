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
<title>信用贷款反欺诈表查询</title>
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
		<p class="title"><a href="javascript:void(0);">反欺诈查询</a></p>
		
		<div class="content">
			<form id="queryForm" action="credit/antifraud/list.do" method="post" target="queryContent">
				<table>
					<tr>
						<td>申请编号:</td>
						<td><input id="appId" name="appId" type="text" data-options="validType:['length[0,30]']" 
						class="textbox easyui-validatebox"/></td>
<%--						<td>提交说明:</td>--%>
<%--						<td><input id="submitInfo" name="submitInfo" type="text" data-options="validType:['length[0,100]']" --%>
<%--						class="textbox easyui-validatebox"/></td>--%>
<%--						<td>结果:</td>--%>
<%--						<td><input id="result" name="result" type="text" data-options="validType:['length[0,10]']" --%>
<%--							class="easyui-combobox" editable="false" />--%>
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
						<td>创建时间:</td>
						<td><input id="inputTimeMin" name="inputTimeMin" style="width: 90px;"  
								type="text" editable="false" class="textbox easyui-datebox" />
							至
						<input id="inputTimeMax" name="inputTimeMax" style="width: 90px;"  
								type="text" editable="false" class="textbox easyui-datebox" /></td>
						<td>渠道:</td>
						<td><input id="belongChannel" name="belongChannel" type="text" class="easyui-combobox" editable="false"/></td>
						<td>产品:</td>
								<td><input id="product" name="product" type="text"
										data-options="validType:['length[0,50]']"
										editable="false" class="easyui-combobox" /></td>
<%--						<td>所属机构:</td>--%>
<%--						<td><input id="orgId" name="orgId" type="text" data-options="validType:['length[0,50]']"--%>
<%--							editable="false" class="easyui-combobox"/></td>--%>
<%--					<tr>--%>
<%--						<td>结果说明:</td>--%>
<%--						<td><input id="resultInfo" name="resultInfo" type="text" data-options="validType:['length[0,100]']" --%>
<%--						class="textbox easyui-validatebox"/></td>--%>
<%--						<td>提交人:</td>--%>
<%--						<td><input id="submitOperator" name="submitOperator" type="text" data-options="validType:['length[0,50]']" --%>
<%--						class="textbox easyui-validatebox"/></td>--%>
<%--						<td>操作员:</td>--%>
<%--						<td><input id="operator" name="operator" type="text" data-options="validType:['length[0,50]']" --%>
<%--						class="textbox easyui-validatebox"/></td>--%>
<%--					</tr>--%>
					</tr>
					<tr>
						<td>修改时间:</td>
						<td><input id="updateTimeMin" name="updateTimeMin" style="width: 90px;"  
								type="text" editable="false" class="textbox easyui-datebox" />
							至
						<input id="updateTimeMax" name="updateTimeMax" style="width: 90px;"  
								type="text" editable="false" class="textbox easyui-datebox" /></td>
<%--						<td><input id="updateTime" name="updateTime" type="text" editable="false" class="textbox easyui-datetimebox"/></td>--%>
						<td>处理状态:</td>
						<td>
							<select class="easyui-combobox" name="stateTask" editable="false" style="width:152px;">
								<option value="waitTask" selected="selected">待处理</option>
								<option value="inTask">处理中</option>
								<option value="offTask">已完成</option>
							</select>							
						</td>
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

<!-- <div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
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

/* //打开Loading遮罩并修改样式
function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
} */


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
	//填充select数据 解除反欺诈结果
	$("#queryForm").find("#result").combobox("clear");
	$("#queryForm").find('#result').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:[{"keyProp":"1","keyValue":"无问题"},{"keyProp":"2","keyValue":"有问题"}]
	});
/*
	//填充select数据  所属机构
	var tsurl="<%=basePath%>sys/org/allOrgList.do?loginid=all&timestamp="+(new Date()).getTime();
	$("#orgId").combobox("clear");
	$("#orgId").combobox({url:tsurl,valueField:'orgId',textField:'orgName'});
*/

	//填充select数据 渠道
	var channelurl="channeltotal/listjason.do";
	$("#belongChannel").combobox("clear");
	$('#belongChannel').combobox({
		url:channelurl,
		valueField:'channelCode', 
		textField:'channelName',
		onChange: function(newValue, oldValue){             
			$('#product').combobox('clear');
			var producturl = "product/hedao/listjason.do?type=3&belongChannel=" + encodeURI(newValue);
			$('#product').combobox('reload',producturl); 
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
	var belongChannel = $('#belongChannel').combobox('getValue');
	var producturl = "product/hedao/listjason.do?type=3&belongChannel=" + encodeURI(belongChannel);
	$("#product").combobox("clear");
	$('#product').combobox({
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


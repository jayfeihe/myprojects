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
		<title>还款管理</title>
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
<div id="paymentManageTable" class="easyui-tabs" data-options="fit:true">
<div title="还款管理"> 
		<div id="main">
			<div id="part1" class="part">
				<p class="title"><a href="javascript:void(0);">还款管理查询</a></p>
				<div class="content">
					<form id="queryForm" action="paymentManage/list.do" method="post" target="queryContent">
						<table>
							<tr>
								<td align="right">合同编号:</td>
								<td><input id="contractNo" name="contractNo" type="text"
									data-options="validType:['length[0,30]']" 
									class="textbox easyui-validatebox" /></td>
								<td align="right">姓名:</td>
								<td><input id="bankAccountName" name="bankAccountName" type="text" 
									data-options="validType:['length[0,50]']" 
									class="textbox easyui-validatebox" /></td>
								<td align="right">身份证:</td>
								<td><input id="loanIdNo" name="loanIdNo" type="text" 
									data-options="validType:['idcard']" 
									class="textbox easyui-validatebox" /></td>
							</tr>
							<tr>
								<td align="right">决策渠道:</td>
								<td><input id="belongChannel" name="belongChannel" type="text" class="easyui-combobox" editable="false"/></td>
								<td align="right">决策产品:</td>
								<td><input id="loanProduct" name="loanProduct" editable="false"
									data-options="validType:['length[0,50]']"
									class="easyui-combobox" value=""/>
							</tr>
							<tr>
								<td align="right">还款日期:</td>
								<td>
									<input id="repayMentDate" name="repayMentDate" style="width: 152px;"  
										type="text" editable="false" class="textbox easyui-datebox" />
								</td>
								<td align="right">营业部:</td>
								<td>
									<input id="orgId" name="orgId" type="text"
										class="easyui-combobox" style="width:152px;" editable="false"  />
								</td>
								<td align="right">是否逾期:</td>
								<td>
									<select class="easyui-combobox" name="isOverdue" editable="false" style="width:152px;">
										<option value="" selected="selected">全部</option>
										<option value="noOverdue">未逾期</option>
										<option value="overdue">已逾期</option>
									</select>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<input type="button" value="查询" class="btn" onclick="subForm();" />
									<input type="button" value="重置" class="btn" onclick="$('#queryForm').form('clear');"/>
								</td>
							</tr>
						</table>
					</form>
				</div>

				<div id="queryContent">
					<%--查询列表--%>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 		<div id="loading" class="easyui-window" title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false"> -->
<!-- 			<img src="img/loading.gif" alt="加载中..." /> -->
<!-- 		</div> -->
	</body>
	
<script type="text/javascript">
//添加选项卡
function addTab(title, url){
    if ($('#paymentManageTable').tabs('exists', title)){
        $('#paymentManageTable').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#paymentManageTable').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeTab(){
	var tab = $('#approvalTable').tabs('getSelected');
	var tabIndex=$('#approvalTable').tabs('getTabIndex',tab);
	$('#approvalTable').tabs('close',tabIndex);
	submitForm("queryForm");
}
</script>
<script type="text/javascript">
var paramsForAsc = null;
function submitForm(fromId) {
	//去掉 input 输入的 前后空格
	$('#queryForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	var formAction = $('#' + fromId).attr("action");
	var targetDiv = $('#' + fromId).attr("target");
	var params = $('#' + fromId).serialize()+"&isAsc="+i;
	paramsForAsc = $('#' + fromId).serialize();
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


function submitFormAsc(fromId,paramsForAsc) {
	//去掉 input 输入的 前后空格
	$('#queryForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	var formAction = $('#' + fromId).attr("action");
	var targetDiv = $('#' + fromId).attr("target");
	var params = paramsForAsc+"&isAsc="+i;
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
	//填充select数据 渠道
	var channelurl="channeltotal/listjason.do";
	$("#belongChannel").combobox("clear");
	$('#belongChannel').combobox({
		url:channelurl,
		valueField:'channelCode', 
		textField:'channelName',
		onChange: function(newValue, oldValue){             
			$('#loanProduct').combobox('clear');
			var producturl = "product/hedao/listjason.do?type=3&belongChannel=" + encodeURI(newValue);
			$('#loanProduct').combobox('reload',producturl); 
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
	$("#loanProduct").combobox("clear");
	$('#loanProduct').combobox({
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
	var orgUrl = "<%=basePath%>sys/org/allOrgList.do?loginid=all&timestamp="+(new Date()).getTime();
		$("#orgId").combobox("clear");
		$("#orgId").combobox({
			url:orgUrl,
			valueField:'orgId',
			textField:'orgName',
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

function a(){
	if(i == 0 || i == 2){
		//正
		i=1;
		submitFormAsc("queryForm",paramsForAsc);
	}else if(i == 1){
		//反
		i=2;
		submitFormAsc("queryForm",paramsForAsc);
	}
}

function subForm(){
		i=0;
		submitForm("queryForm");
}


var i=0;
</script>
</html>


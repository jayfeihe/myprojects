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
<title>出借人资金变动记录查询</title>
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
<div class="easyui-tabs" data-options="fit:true" id="lendUserLogTabs">
<div title="资金变动日志">
	<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">资金变动</a></p>
		
		<div class="content">
		<form id="queryLendUserLogForm" action="lenduser/lenduserlog/list.do" method="post" target="queryLendUserLogContent">
		   <input type="hidden" value="${lendUserId}" name="lendUserId"/>
		</form>
			<form id="updateLendUserLogForm" action="lenduser/lenduserlog/list.do" method="post">				
				<input type="hidden" value="${lendUserId}" name="lendUserId"/>
				<table>
					<tr>
<td>类型:</td>
<td><input id="type" name="type" type="text" data-options="required:true,editable:false,panelHeight:'auto',
																			textField:'keyValue',
																			valueField:'keyProp',
																			data:[
																				{'keyProp':'2','keyValue':'支出'},
																				{'keyProp':'1','keyValue':'收入'}]"
class="textbox easyui-combobox"/></td>
</tr>
<tr>
<td>变动金额:</td>
<td><input id="amt" name="amt" type="text" editable="false" required="true"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.amt}"/>元</td>
</tr>
<tr>
<td>凭证号:</td>
<td><input id="proof" name="proof" type="text" data-options="required:true,validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.proof}" /></td>
</tr>
<tr>
<td>说明:</td>
<td><textarea id="remark" name="remark" type="text" data-options="validType:['length[0,500]']" class="textbox easyui-validatebox" value="${bean.remark}" style="resize:none;width:500px;height:100px!important;">${bean.remark}</textarea></td>
</tr>
					<tr><td></td>
						<td  align="right">
							<input type="button" value="提交" class="btn" onclick="updateFunction('updateLendUserLogForm')"/>						
						</td>
						<td></td>
					</tr>
				</table>	
			</form>
		</div>
		
		<div id="queryLendUserLogContent" >
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
    if ($('#lendUserLogTabs').tabs('exists', title)){
        $('#lendUserLogTabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#lendUserLogTabs').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeTab(){
	var tab = $('#lendUserLogTabs').tabs('getSelected');
	var tabIndex=$('#lendUserLogTabs').tabs('getTabIndex',tab);
	$('#lendUserLogTabs').tabs('close',tabIndex);
	submitForm("queryLendUserLogForm");//解决Tab提交关闭列表页刷新的问题
}

//////////////       ////////////////
//开启遮罩
function openMask(){
	$("<div class=\"datagrid-mask\" id='mask_id'></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
	$("<div class=\"datagrid-mask-msg\" id='mask_msg_id'></div>").html("请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
}
//关闭遮罩
function closeMask(){
	$("#mask_id").remove();
	$("#mask_msg_id").remove();
}
</script>

<script type="text/javascript">
//提交表单
function updateFunction() {
	//去掉 input 输入的 前后空格
	$('#updateLendUserLogForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	//验证表单验证是否通过
	if(false == $('#updateLendUserLogForm').form('validate') ){
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}
	//弹出异步加载 遮罩
	openMask();
	var params = $('#updateLendUserLogForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "lenduser/lenduserlog/save.do",
		data : encodeURI(params),
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
	                var url= "<%=basePath%>" + "lenduser/lenduserlog/query.do";
					/* window.location=url;
					//window.history.go(-1);
					window.location = window.location + "&timestamp=" + (new Date()).getTime();
				    window.location.reload();
				    window.parent.submitForm("queryLendUserForm"); */
				    window.parent.removeTab();
            	});
            } else {				
    			$.messager.alert('消息', data.message);
				//按钮生效
				$(".btn").removeAttr("disabled");
            }
		},
		error : function() {
			//关闭遮罩，弹出消息框
			closeMask();
			$.messager.confirm('消息', '数据加载失败,请联系系统管理员！');
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
	});
}


function submitForm(fromId) {
	//去掉 input 输入的 前后空格
	$('#queryLendUserLogForm').find('input').each(function(){
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
			$.messager.confirm('消息', '数据加载失败,请联系系统管理员！');
		}
	});
}
//页面加载完动作
$(document).ready(function() {
//填充select数据样例
/*<%--
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
--%>*/

	submitForm("queryLendUserLogForm");
});
</script>

</html>


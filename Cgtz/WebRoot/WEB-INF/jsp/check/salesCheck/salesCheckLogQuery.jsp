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
<title>稽查人员催收跟进记录表查询</title>
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
		<p class="title"><a href="javascript:void(0);">逾期跟进</a></p>
		
		<div class="content">
			<form id="querySalesCheckForm" action="check/salesCheck/list.do" method="post" target="querySalesCheckContent">
			<input type="hidden" value="${loanId}" name="loanId" id="loanId"/>
			</form>
			<form id="updateSalesCheckForm" action="check/salesCheck/list.do" method="post">
			<input type="hidden" value="${loanId}" name="loanId" id="loanId"/>
			<table>
			<tr><td colspan="2">说明:
			<textarea name="remark" id="remark" type="text" data-options="required:true,validType:['length[0,500]']" class="textbox easyui-validatebox" style="resize:none;width:500px;height:100px!important;"></textarea></td>
			</tr>
			<tr>
			 <td>查看权限:
				<input name="readFlag" type="radio" value="1"/>公开
				&nbsp;
				<input name="readFlag" type="radio" value="2" checked="checked" />稽查部
			</td>
			</tr>
			<tr>
			 <td>状态标记:
				<input name="checkState" type="radio" value="1"/>未处理
				&nbsp;
				<input name="checkState" type="radio" value="2"/>已处理
				&nbsp;
				<input name="checkState" type="radio" value="3"/>移交法务
				&nbsp;
				<input name="checkState" type="radio" value="4"/>重点关注
			</td>
			</tr>				
			<tr><td></td>
						<td  align="right">
							<input type="button" value="提交" class="btn" onclick="updateSalsCheckFunction('${loanId}','${contractId}')"/>						
						</td>
						<td></td>
			</tr>
			</table>
			</form>
		</div>
		
		<div id="querySalesCheckContent" >
		<%--
		查询列表
		 --%>
		</div>
	</div>
</div>
</body>
<script type="text/javascript">

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
function submitForm(fromId) {
	//去掉 input 输入的 前后空格
	$('#querySalesCheckForm').find('input').each(function(){
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
//更新保存
function updateSalsCheckFunction(loanId,contractId) {
	//去掉 input 输入的 前后空格
	$('#updateSalesCheckForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	//验证表单验证是否通过
	if(false == $('#updateSalesCheckForm').form('validate') ){
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}
	//弹出异步加载 遮罩
	openMask();
	var params = $('#updateSalesCheckForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : '<%=basePath%>' + 'check/salesCheck/save.do?contractId='+contractId,
		data : encodeURI(params),
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
	                var url= "<%=basePath%>" + "check/salesCheck/query.do";
					window.location=url;
					window.location.reload();
					window.parent.submitForm('queryAfterLoanForm');
					window.parent.submitForm('queryCheckTaskForm');
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
//页面加载完动作
$(document).ready(function() {

	submitForm("querySalesCheckForm");
});
</script>

</html>


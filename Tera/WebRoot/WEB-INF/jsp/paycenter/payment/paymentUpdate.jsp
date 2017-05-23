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
<title>支付明细表更新</title>
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
		<p class="title"><a href="javascript:void(0);">更新</a></p>
		<div class="content">
			<form id="updateForm" >
				<table>
					<tbody>
					<input id="id" name="id" type="hidden" size="35" value="${ bean.id}" />
					<tr>
<td><SPAN style="color:red">*</SPAN>合同号:</td>
<td><input id="contractNo" name="contractNo" type="text" data-options="required:true,validType:['length[0,25]']" class="textbox easyui-validatebox" value="${bean.contractNo}"/></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>收付:</td>
<td><input id="inOut" name="inOut" type="text" data-options="required:true,validType:['length[0,1]']" class="textbox easyui-validatebox" value="${bean.inOut}"/></td>
</tr>
<tr>
<td>科目:</td>
<td><input id="subject" name="subject" type="text" data-options="validType:['length[0,18]']" class="textbox easyui-validatebox" value="${bean.subject}"/></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>计划金额:</td>
<td><input id="planAmount" name="planAmount" type="text" required="true" editable="false" data-options="required:true,min:0,precision:2" class="textbox easyui-numberbox" value="${bean.planAmount}"/></td>
</tr>
<tr>
<td>实际金额:</td>
<td><input id="actualAmount" name="actualAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.actualAmount}"/></td>
</tr>
<tr>
<td>来源:</td>
<td><input id="source" name="source" type="text" data-options="validType:['length[0,18]']" class="textbox easyui-validatebox" value="${bean.source}"/></td>
</tr>
<tr>
<td>期数:</td>
<td><input id="periodNum" name="periodNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.periodNum}"/></td>
</tr>
<tr>
<td>发盘:</td>
<td><input id="sendFlag" name="sendFlag" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.sendFlag}"/></td>
</tr>
<tr>
<td>状态:</td>
<td><input id="state" name="state" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.state}"/></td>
</tr>
<tr>
<td>原因:</td>
<td><input id="reason" name="reason" type="text" data-options="validType:['length[0,200]']" class="textbox easyui-validatebox" value="${bean.reason}"/></td>
</tr>
<tr>
<td>操作员:</td>
<td><input id="operator" name="operator" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.operator}"/></td>
</tr>
<tr>
<td>所属机构:</td>
<td><input id="orgId" name="orgId" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.orgId}"/></td>
</tr>
<tr>
<td>创建日期:</td>
<td><input id="createTime" name="createTime" type="text" editable="false" class="textbox easyui-datetimebox" value="${bean.createTimeStr}"/></td>
</tr>
<tr>
<td>修改日期:</td>
<td><input id="updateTime" name="updateTime" type="text" editable="false" class="textbox easyui-datetimebox" value="${bean.updateTimeStr}"/></td>
</tr>

					<tr>
						<td>
							<input type="button" value="保存" class="btn" onclick="updateFunction()"/>
							<input type="button" value="返回" class="btn" onclick="javascript:back()"/>
						</td>
						<td></td>
					</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>

<!-- <div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
	<img src="img/loading.gif" alt="加载中..." />
</div> -->

</body>

<script type="text/javascript">
//更新保存
function updateFunction() {
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		$(this).val($.trim($(this).val()));
	});
	//验证表单验证是否通过
	if(false == $('#updateForm').form('validate') ){
		
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}
	//弹出异步加载 遮罩
	openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "payment/save.do",
		data : encodeURI(params),
		success : function(data) {
			if ("true"==data.success) {
				//关闭遮罩，弹出消息框
				closeMask();
				
				$.messager.alert('消息', data.message,"info", function(){
//	                   	var url= "<%=basePath%>" + "payment/query.do";
//						window.location=url;
						window.history.go(-1);
            	});
            } else {
            	closeMask();
				
				$.messager.alert('消息', data.message);
				//按钮生效
				$(".btn").removeAttr("disabled");
            }
		},
		error : function() {
			closeMask();
			
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
	});
}

//返回
function back(){
	window.history.go(-1);
}

//打开Loading遮罩并修改样式
/* function openLoading(){
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
	$('#repayMethod').combobox({url:tsurl, valueField:'keyProp', textField:'keyValue'});
	*/
});

</script>
</html>


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
<title>短信日志表更新</title>
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
<td>模板ID:</td>
<td><input id="templateId" name="templateId" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.templateId}"/></td>
</tr>
<tr>
<td>类型:</td>
<td><input id="type" name="type" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.type}"/></td>
</tr>
<tr>
<td>合同编号:</td>
<td><input id="contractNo" name="contractNo" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.contractNo}"/></td>
</tr>
<tr>
<td>客户姓名:</td>
<td><input id="costomerName" name="costomerName" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.costomerName}"/></td>
</tr>
<tr>
<td>证件类型:</td>
<td><input id="idType" name="idType" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.idType}"/></td>
</tr>
<tr>
<td>证件号码:</td>
<td><input id="idNo" name="idNo" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.idNo}"/></td>
</tr>
<tr>
<td>联系方式:</td>
<td><input id="mobileTel" name="mobileTel" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.mobileTel}"/></td>
</tr>
<tr>
<td>信息内容:</td>
<td><input id="msgContent" name="msgContent" type="text" data-options="validType:['length[0,200]']" class="textbox easyui-validatebox" value="${bean.msgContent}"/></td>
</tr>
<tr>
<td>发送状态:</td>
<td><input id="sendState" name="sendState" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.sendState}"/></td>
</tr>
<tr>
<td>发送时间:</td>
<td><input id="sendTime" name="sendTime" type="text" editable="false" class="textbox easyui-datetimebox" value="${bean.sendTimeStr}"/></td>
</tr>
<tr>
<td>备注:</td>
<td><input id="remark" name="remark" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.remark}"/></td>
</tr>
<tr>
<td>创建人:</td>
<td><input id="createUid" name="createUid" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.createUid}"/></td>
</tr>
<tr>
<td>创建时间:</td>
<td><input id="createTime" name="createTime" type="text" editable="false" class="textbox easyui-datetimebox" value="${bean.createTimeStr}"/></td>
</tr>
<tr>
<td>修改人:</td>
<td><input id="updateUid" name="updateUid" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.updateUid}"/></td>
</tr>
<tr>
<td>修改时间:</td>
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

</body>

<script type="text/javascript">
//更新保存
function updateFunction() {
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
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
		url : "<%=basePath%>" + "message/msglog/save.do",
		data : encodeURI(params),
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
//	                var url= "<%=basePath%>" + "message/msglog/query.do";
//					window.location=url;
					window.history.go(-1);
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

//返回
function back(){
	window.history.go(-1);
}

//页面加载完动作
$(document).ready(function (){
	//填充select数据样例
	/*<%--
	var tsurl="sys/datadictionary/listjason.do?keyName=repaymethod";
	$("#repayMethod").combobox("clear");
	$('#repayMethod').combobox({url:tsurl, valueField:'keyProp', textField:'keyValue'});
	--%>*/
});

</script>
</html>


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
<title>诉讼管理</title>
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
		<div class="content">
			<form id="updateLawForm" >
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>诉讼管理</strong></div><hr color="#D3D3D3"/>
				<input type="hidden" name="id" value="${bean.id }" />
				<input type="hidden" name="loanId" value="${loanId }" />
				<table id="lawInfo">
					<tr>
						<td>网站链接：</td>
						<td><a href="http://shixin.court.gov.cn/" target="_blank">中国执行信息公开网</a>&nbsp;&nbsp;&nbsp;
							<a href="http://zhixing.court.gov.cn/search/" target="_blank">全国法院被执行人信息查询</a>&nbsp;&nbsp;&nbsp;
							<a href="http://www.itslaw.com/" target="_blank">无讼案例</a>&nbsp;&nbsp;&nbsp;
							<a href="https://sf.taobao.com/" target="_blank">司法拍卖</a>
						</td>
					</tr>
					<tr>
						<td>诉讼情况:</td>
						<td>
							<input id="lawState" name="lawState" type="text" class="textbox easyui-combobox" 
								data-options="required:true,editable:false,panelHeight:'auto',
											textField:'keyValue',
											valueField:'keyProp',
											data:[{'keyProp':'','keyValue':'请选择'},
												{'keyProp':'0','keyValue':'无'},
												{'keyProp':'1','keyValue':'有'},]" value="${bean.lawState}"/>
						</td>
					</tr>
					<tr>
						<td>说明:</td>
						<td colspan="6">
							<textarea name="lawRemark" class="textbox easyui-validatebox" 
										data-options="validType:['length[0,500]']" 
										style="resize: none;width:625px;height:50px!important;">${bean.lawRemark }</textarea>
						</td>
					</tr>
				</table>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>操作</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td colspan="3">
							<input type="button" value="保存" class="btn" onclick="javascript:updateFunction('${type}');"/>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</form>
			<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>影像</strong></div><hr color="#D3D3D3"/>
			<jsp:include page="/files/load.do">
				<jsp:param value="${loanId }" name="loId"/>
				<jsp:param value="filesce7" name="sec"/>
				<jsp:param value="${ bean.id}" name="bizKey"/>
				<jsp:param value="0" name="opt"/>
			</jsp:include>
		</div>
	</div>

<script type="text/javascript">
//更新保存
function updateFunction(type) {
	//去掉 input 输入的 前后空格
	$('#updateLawForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	
	//验证表单验证是否通过
	if(false == $('#updateLawForm').form('validate') ){
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}
	
	//弹出异步加载 遮罩
	openMask();
	
	var params = $('#updateLawForm').serialize();
	
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "loan/law/save.do",
		data : params+"&type="+type,
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', "成功", 'info', function(){
					//window.parent.refreshTab("appUpdateTabs");
					var url = "loan/law/update.do?id=" + data.id + "&loanId=" + data.loanId +"&type=" + data.type;;
					window.parent.refreshLawTab('loanLawQueryTabs',url);
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
	
});
</script>

<script type="text/javascript" >
openMask();
$(window).load(function (){
	closeMask();
});
</script>
</body>
</html>


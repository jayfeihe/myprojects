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
<title>产品表更新</title>
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
						<td><SPAN style="color:red">*</SPAN>产品名称:</td>
						<td><input id="name" name="name" type="text" data-options="required:true,validType:['length[0,20]']" 
						class="textbox easyui-validatebox" value="${bean.name}"/></td>
						<td>产品类型:</td>
						<td><input id="type" name="type" type="text" data-options="required:true" editable="false"  
						class="textbox easyui-combobox" value="${bean.type}"/></td>
						<td>期限:</td>
						<td><input id="period" name="period" type="text" editable="false"  data-options="min:0,precision:0" 
						class="textbox easyui-numberbox" value="${bean.period}" style="width:128px;"/>个月</td>
					</tr>
					<tr>
						<td>总服务费率:</td>
						<td><input id="sreviceFeeRate" name="sreviceFeeRate" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${bean.sreviceFeeRate}" style="width:138px;"/>%</td>
						<td>账户管理费率:</td>
						<td><input id="sreviceFeeRate2" name="sreviceFeeRate2" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${bean.sreviceFeeRate2}" style="width:138px;"/>%</td>
						<td>融资服务费率:</td>
						<td><input id="sreviceFeeRate3" name="sreviceFeeRate3" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${bean.sreviceFeeRate3}" style="width:138px;"/>%</td>
					</tr>
					<tr>
						<td>利率:</td>
						<td><input id="interestRate" name="interestRate" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${bean.interestRate}" style="width:138px;"/>%</td>
						<td>还款/付息方式:</td>
						<td><input id="repayMethod" name="repayMethod" type="text" class="easyui-combobox" editable="false" 
						value="${bean.repayMethod}"/></td>
						<td>罚息:</td>
						<td><input id="penaltyRate" name="penaltyRate" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${bean.penaltyRate}" style="width:138px;"/>%</td>
						<td>滞纳金:</td>
						<td><input id="delayRate" name="delayRate" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${bean.delayRate}" style="width:138px;"/>%</td>
					</tr>
					<tr>
						<td>违约金比例1:</td>
						<td><input id="defaultRate1" name="defaultRate1" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${bean.defaultRate1}" style="width:138px;"/>%</td>
						<td>违约金比例2:</td>
						<td><input id="defaultRate2" name="defaultRate2" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${bean.defaultRate2}" style="width:138px;"/>%</td>
						<td>违约金比例3:</td>
						<td><input id="defaultRate3" name="defaultRate3" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${bean.defaultRate3}" style="width:138px;"/>%</td>
						<td>违约金比例4:</td>
						<td><input id="defaultRate4" name="defaultRate4" type="text" editable="false"  data-options="min:0,precision:2" 
						class="textbox easyui-numberbox" value="${bean.defaultRate4}" style="width:138px;"/>%</td>
					</tr>
					<tr>
						<td>所属渠道:</td>
						<td><input id="belongChannel" name="belongChannel" type="text" data-options="required:true,validType:['length[0,50]']" 
						class=easyui-combobox value="${bean.belongChannel}" editable="false"/></td>
						<td>状态:</td>
						<td>
							<select class="easyui-combobox" name="state" editable="false" style="width:152px;">
									<option value="1">正常</option>
									<option value="2">挂起</option>
									<option value="0">停用</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>备注:</td>
						<td colspan="5">
<!-- 							<input id="remark" name="remark" type="text" data-options="validType:['length[0,100]']"  -->
<%-- 							class="textbox easyui-validatebox" value="${bean.remark}"/> --%>
							<textarea id="remark" name="remark" value="${bean.remark}" class="textbox easyui-validatebox" data-options="validType:['length[0,100]']" 
							style="resize: none;width:625px;height:50px!important;">${bean.remark}</textarea>
						</td>
					</tr>
					<tr>
						<td colspan="2">
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
		url : "<%=basePath%>" + "product/hedao/save.do",
		data : encodeURI(params),
		success : function(data) {
			if ("true"==data.success) {
				//关闭遮罩，弹出消息框
				closeMask();
				
				$.messager.alert('消息', data.message,"info", function(){
//	                   	var url= "<%=basePath%>" + "product/hedao/query.do";
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

/* //打开Loading遮罩并修改样式
function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
} */


//页面加载完动作
$(document).ready(function(){
	//填充select数据 渠道
	var channelurl="channeltotal/listjason.do";
	$("#belongChannel").combobox("clear");
	$('#belongChannel').combobox({
		url:channelurl,
		valueField:'channelCode', 
		textField:'channelName'
   	});
	
	//填充select数据
	//var tsurl="sys/datadictionary/listjason.do?keyName=repaymethod";
	$("#type").combobox("clear");
	$('#type').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.producttype
	});
	$('#type').combobox('select', '${bean.type}');
	
	$("#repayMethod").combobox("clear");
	$('#repayMethod').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.repaymethod
	});
	$('#repayMethod').combobox('select', '${bean.repayMethod}');
});
</script>
</html>


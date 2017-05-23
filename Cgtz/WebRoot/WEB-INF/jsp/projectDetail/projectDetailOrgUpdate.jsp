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
<title>T_PROJECT_DETAIL更新</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>

</head>
<body>
<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">项目信息</a></p>
		<div class="content">
				<table>
					<tbody>
<tr>
<td>债权线上编号:</td>
<td><input id="projectId" name="projectId"   type="text" readonly="readonly"  data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.projectId}"/></td>
<td>债权线下编号:</td>
<td><input id="contractNo" name="contractNo"   type="text" readonly="readonly"  data-options="required:true,validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.contractNo}"/></td>
<td>项目名称:</td>
<td><input id="projectName" name="projectName"   type="text" readonly="readonly"  data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.projectName}"/></td>
</tr>
<tr>
<td>合同开始时间:</td>
<td><input id="startDate" name="startDate"   type="text" readonly="readonly"  data-options="required:true,editable:false" class="textbox easyui-datebox" value="${bean.startDateStr}"/></td>
<td>合同结束时间:</td>
<td><input id="endDate" name="endDate"   type="text" readonly="readonly"  data-options="required:true,editable:false" class="textbox easyui-datebox" value="${bean.endDateStr}"/></td>
</tr>
<tr>
<td>实际结束时间:</td>
<td><input id="realEndDate" name="realEndDate"   type="text" readonly="readonly"  data-options="editable:false" class="textbox easyui-datebox" value="${bean.realEndDateStr}"/></td>
<td>上线时间:</td>
<td><input id="onlineDate" name="onlineDate"   type="text" readonly="readonly"  data-options="editable:false" class="textbox easyui-datebox" value="${bean.onlineDateStr}"/></td>
</tr>
<tr>
<td>借款金额:</td>
<td><input id="loanAmt" name="loanAmt"   type="text" readonly="readonly"  data-options="min:0,validType:['length[0,18]']" class="textbox easyui-numberbox" value="${bean.loanAmt}"/>元</td>
<td>原始债权人:</td>
<td><input id="lendName" name="lendName"   type="text" readonly="readonly"  data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.lendName}"/></td>
<td>债权人证件号码:</td>
<td><input id="lendNo" name="lendNo"   type="text" readonly="readonly"  data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.lendNo}"/></td>
</tr>
<tr>
<td>借款人:</td>
<td><input id="loanName" name="loanName" readonly="readonly"   type="text"  data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.loanName}"/></td>
<td>借款人证件号码:</td>
<td><input id="loanNo" name="loanNo"   type="text" readonly="readonly"  data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.loanNo}"/></td>
<td>借款利率:</td>
<td><input id="loanRate" name="loanRate"   type="text" readonly="readonly"  data-options="min:0,max:100,precision:2,validType:['length[0,18]']" class="textbox easyui-numberbox" value="${bean.loanRate}"/>%</td>
</tr>
<tr>
<td>结算利率:</td>
<td><input id="onlineRate" name="onlineRate"   type="text" readonly="readonly"  data-options="min:0,max:100,precision:2,validType:['length[0,18]']" class="textbox easyui-numberbox" value="${bean.onlineRate}"/>%</td>
<td>还款方式:</td>
<td><input id="retWay" name="retWay"   type="text" readonly="readonly"  data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.retWay}"/></td>
<td>贷款类别:</td>
<td><input id="type" name="type"   type="text" readonly="readonly"  data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.type}"/></td>
</tr>
<tr>
<td>财务说明:</td>
<td colspan="8"><textarea id="acctRemark" name="acctRemark" readonly="readonly"  data-options="validType:['length[0,200]']" class="textbox easyui-validatebox" style="resize: none;width:625px;height:50px!important;" value="${bean.acctRemark}">${bean.acctRemark}</textarea></td>
</tr>
</tbody>
</table>
<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>填写修改信息</strong></div><hr color="#D3D3D3" />
<form id="updateForm" >
<input id="id" name="id" type="hidden" size="35" value="${ bean.id}" />
<table>
<tbody>
<tr>
<td>实际结束时间:</td>
<td><input id="realEndDate" name="realEndDate"   type="text"  data-options="editable:false" class="textbox easyui-datebox" value="${bean.realEndDateStr}"/></td>
</tr>
<tr>
<td>&nbsp;&nbsp;分公司说明:</td>
<td colspan="8"><textarea id="branchRemark" name="branchRemark"  data-options="validType:['length[0,200]']" class="textbox easyui-validatebox" style="resize: none;width:625px;height:50px!important;" >${bean.branchRemark}</textarea></td>
</tr>
	<tr>
		<td>
			<input type="button" value="保存" class="btn" onclick="updateFunction()"/>
		</td>
	</tr>
					</tbody>
				</table>
			</form>
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
		url : "<%=basePath%>" + "projectInfoDetail/save.do",
		data : encodeURI(params),
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
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

//返回
function back(){
	window.history.go(-1);
}

//页面加载完动作
$(document).ready(function() {

});

</script>
</html>


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
<title>信用贷款人行报告公共信息明细更新</title>
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
<td>申请ID:</td>
<td><input id="appId" name="appId" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.appId}"/></td>
</tr>
<tr>
<td>公积金参缴地:</td>
<td><input id="fundPlace" name="fundPlace" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.fundPlace}"/></td>
</tr>
<tr>
<td>公积金参缴日期:</td>
<td><input id="fundStartDate" name="fundStartDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.fundStartDateStr}"/></td>
</tr>
<tr>
<td>公积金初缴月份:</td>
<td><input id="fundFirstDate" name="fundFirstDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.fundFirstDateStr}"/></td>
</tr>
<tr>
<td>公积金缴至月份:</td>
<td><input id="fundPaidDate" name="fundPaidDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.fundPaidDateStr}"/></td>
</tr>
<tr>
<td>公积金缴费状态:</td>
<td><input id="fundState" name="fundState" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.fundState}"/></td>
</tr>
<tr>
<td>公积金月缴存额:</td>
<td><input id="fundAmount" name="fundAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.fundAmount}"/></td>
</tr>
<tr>
<td>公积金个人缴存比例:</td>
<td><input id="fundPersonPercent" name="fundPersonPercent" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.fundPersonPercent}"/></td>
</tr>
<tr>
<td>公积金单位缴存比例:</td>
<td><input id="fundComPercent" name="fundComPercent" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.fundComPercent}"/></td>
</tr>
<tr>
<td>公积金缴费单位:</td>
<td><input id="fundCompany" name="fundCompany" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.fundCompany}"/></td>
</tr>
<tr>
<td>公积金信息更新日期:</td>
<td><input id="fundUpdateDate" name="fundUpdateDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.fundUpdateDateStr}"/></td>
</tr>
<tr>
<td>养老保险参保地:</td>
<td><input id="pensionPlace" name="pensionPlace" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.pensionPlace}"/></td>
</tr>
<tr>
<td>养老保险参保日期:</td>
<td><input id="pensionStartDate" name="pensionStartDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.pensionStartDateStr}"/></td>
</tr>
<tr>
<td>养老保险缴费月数:</td>
<td><input id="pensionPayMonth" name="pensionPayMonth" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.pensionPayMonth}"/></td>
</tr>
<tr>
<td>养老保险参加工作月份:</td>
<td><input id="pensionWorkDate" name="pensionWorkDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.pensionWorkDateStr}"/></td>
</tr>
<tr>
<td>养老保险缴费状态:</td>
<td><input id="pensionState" name="pensionState" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.pensionState}"/></td>
</tr>
<tr>
<td>养老保险个人缴费基数:</td>
<td><input id="pensionBaseAmount" name="pensionBaseAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.pensionBaseAmount}"/></td>
</tr>
<tr>
<td>养老保险本月缴费金额:</td>
<td><input id="pensionPayAmount" name="pensionPayAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.pensionPayAmount}"/></td>
</tr>
<tr>
<td>养老保险信息更新日期:</td>
<td><input id="pensionUpdateDate" name="pensionUpdateDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.pensionUpdateDateStr}"/></td>
</tr>
<tr>
<td>养老保险缴费单位:</td>
<td><input id="pensionCompany" name="pensionCompany" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.pensionCompany}"/></td>
</tr>
<tr>
<td>养老保险中断终止原因:</td>
<td><input id="pensionInterruptReason" name="pensionInterruptReason" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.pensionInterruptReason}"/></td>
</tr>
<tr>
<td>备注:</td>
<td><input id="remarks" name="remarks" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.remarks}"/></td>
</tr>
<tr>
<td>状态:</td>
<td><input id="state" name="state" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.state}"/></td>
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
		url : "<%=basePath%>" + "renhang/publicDetail/save.do",
		data : encodeURI(params),
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
//	                var url= "<%=basePath%>" + "renhang/publicDetail/query.do";
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


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
<title>信用贷款人行报告更新</title>
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
<td>有无标记:</td>
<td><input id="flag" name="flag" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.flag}"/></td>
</tr>
<tr>
<td>报告编号:</td>
<td><input id="reportNo" name="reportNo" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.reportNo}"/></td>
</tr>
<tr>
<td>请求时间:</td>
<td><input id="requestTime" name="requestTime" type="text" editable="false" class="textbox easyui-datetimebox" value="${bean.requestTimeStr}"/></td>
</tr>
<tr>
<td>报告时间:</td>
<td><input id="reportTime" name="reportTime" type="text" editable="false" class="textbox easyui-datetimebox" value="${bean.reportTimeStr}"/></td>
</tr>
<tr>
<td>姓名:</td>
<td><input id="name" name="name" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.name}"/></td>
</tr>
<tr>
<td>证件类型:</td>
<td><input id="idType" name="idType" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.idType}"/></td>
</tr>
<tr>
<td>证件号码:</td>
<td><input id="idNo" name="idNo" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.idNo}"/></td>
</tr>
<tr>
<td>查询操作员:</td>
<td><input id="queryName" name="queryName" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.queryName}"/></td>
</tr>
<tr>
<td>查询原因:</td>
<td><input id="queryReason" name="queryReason" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.queryReason}"/></td>
</tr>
<tr>
<td>性别:</td>
<td><input id="gender" name="gender" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.gender}"/></td>
</tr>
<tr>
<td>生日:</td>
<td><input id="birthday" name="birthday" type="text" editable="false" class="textbox easyui-datebox" value="${bean.birthdayStr}"/></td>
</tr>
<tr>
<td>婚姻状况:</td>
<td><input id="marriage" name="marriage" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.marriage}"/></td>
</tr>
<tr>
<td>手机号:</td>
<td><input id="mobile" name="mobile" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.mobile}"/></td>
</tr>
<tr>
<td>单位电话:</td>
<td><input id="comPhone" name="comPhone" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.comPhone}"/></td>
</tr>
<tr>
<td>住宅电话:</td>
<td><input id="homePhone" name="homePhone" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.homePhone}"/></td>
</tr>
<tr>
<td>学历:</td>
<td><input id="education" name="education" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.education}"/></td>
</tr>
<tr>
<td>学位:</td>
<td><input id="degree" name="degree" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.degree}"/></td>
</tr>
<tr>
<td>通讯地址:</td>
<td><input id="address" name="address" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.address}"/></td>
</tr>
<tr>
<td>户籍地址:</td>
<td><input id="hkAddress" name="hkAddress" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.hkAddress}"/></td>
</tr>
<tr>
<td>配偶姓名:</td>
<td><input id="mateName" name="mateName" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.mateName}"/></td>
</tr>
<tr>
<td>配偶证件类型:</td>
<td><input id="mateIdType" name="mateIdType" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.mateIdType}"/></td>
</tr>
<tr>
<td>配偶证件号码:</td>
<td><input id="mateIdNo" name="mateIdNo" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.mateIdNo}"/></td>
</tr>
<tr>
<td>配偶工作单位:</td>
<td><input id="mateCom" name="mateCom" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.mateCom}"/></td>
</tr>
<tr>
<td>配偶联系电话:</td>
<td><input id="matePhone" name="matePhone" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.matePhone}"/></td>
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
		url : "<%=basePath%>" + "renhang/report/save.do",
		data : encodeURI(params),
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
//	                var url= "<%=basePath%>" + "renhang/report/query.do";
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


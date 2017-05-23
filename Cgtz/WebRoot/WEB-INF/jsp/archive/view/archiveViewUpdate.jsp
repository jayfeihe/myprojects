<%@page import="com.tera.audit.common.constant.CommonConstant"%>
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
<title>档案录入</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
</head>
<body>
	<div class="easyui-tabs" id="preLoanUpdateTabs" style="width: 100%;" data-options="fit:true">
		<div title="档案信息" style="width: 100%;padding:2px">
			<form id="updateForm">
				<input type="hidden" name="id" value="${bean.id}"/>
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>档案信息</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>合同编号:</td>
						<td>
							<input id="contractId" type="text" class="textbox" name="contractId" value="${bean.contractId }" readonly="readonly"/>
						</td>
						<td>姓名/机构名称:</td>
						<td>
							<input id="name" type="text" class="textbox" name="name" value="${bean.name }" readonly="readonly"/>
						</td>
						<td>借款金额:</td>
						<td>
							<input id="loanAmt" type="text" class="textbox easyui-numberbox" name="loanAmt" value="${bean.loanAmt }" readonly="readonly"/> 元
						</td>
						<td>类型:</td>
						<td>
							<input id="type" type="text" class="textbox" name="type" value="${bean.type }" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>合同开始时间:</td>
						<td>
							<input id="startDate" type="text" class="textbox easyui-datebox" name="startDate" value="${bean.startDateStr }" readonly="readonly"/>
						</td>
						<td>合同结束时间:</td>
						<td>
							<input id="endDate" type="text" class="textbox easyui-datebox" name="endDate" value="${bean.endDateStr }" readonly="readonly"/>
						</td>
						<td>分公司:</td>
						<td>
							<input id="orgId" type="text" class="textbox easyui-combotree" name="orgId" value="${bean.orgId }" readonly="readonly"/>
						</td>
					</tr>
					
					<tr>
						<td>备注:</td>
						<td colspan="6">
							<textarea name="remarks" class="textbox easyui-validatebox" readonly="readonly"
								data-options="validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;">${bean.remarks }</textarea>
						</td>
					</tr>
				</table>
			</form>
			
			<jsp:include page="/files/load.do">
				<jsp:param value="${bean.contractId}" name="loId"/>
				<jsp:param value="filesce14" name="sec"/>
				<jsp:param value="${bean.contractId}" name="bizKey"/>
				<jsp:param value="1" name="opt"/>
			</jsp:include>
	</div>
</div>

<script type="text/javascript">
//页面加载完动作
$(document).ready(function (){
	$("#orgId").combotree({
		url:"sys/org/selectList.do",
		method:'get',
		required:false,
		panelHeight:'auto'
	});
}); 
</script>

<script type="text/javascript" >

window.parent.openMask();
$(window).load(function (){
	window.parent.closeMask();
});
</script>
</body>
</html>


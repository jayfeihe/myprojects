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
<title>借款人基本情况表查询</title>
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
<div class="easyui-tabs" id="queryTabs" data-options="fit:true">
<div title="借款人基本情况表">
	<div id="main">
		<div id="part1" class="part">
			<p class="title"><a href="javascript:void(0);">查询</a></p>
			
			<div class="content">
				<form id="queryForm" action="" method="post" target="queryContent">
					<input type="hidden" id="orgName" name="orgName"/>
					<table>
						<tr>
							<%-- <td>分公司:</td>
							<td>
								<input type="text" 
									<c:if test="${login_org.orgId ne '86'  }">value='${login_org.orgId }' readonly='readonly'</c:if>
									class="textbox easyui-combotree" id="orgId" name="orgId" />
							</td> --%>
							<td>分公司:</td>
							<c:choose>
							<c:when test="${login_org.orgId ne '86'}">
							<td><input type="text" class="textbox easyui-validatebox" value="${loginOrgName}" readonly="readonly"/></td>
							</c:when>
							<c:otherwise>
							<td><input id="orgId" name="orgId"   class="textbox easyui-combobox" 
							    data-options="editable:false,panelHeight:'auto',valueField:'orgId',textField:'orgName',
							    url:'roleDataRelOrgs/listOrgs.do'"/></td>
							</c:otherwise>
							</c:choose>
							<td>合同编号:</td>
							<td><input type="text" name="contractId" class="textbox"/></td>
							<td>借款人:</td>
							<td><input type="text" name="name" class="textbox"/></td>
						</tr>
						<tr>
							<td>合同开始时间:</td>
							<td>
								<input id="startDateMin" name="startDateMin" type="text" class="textbox easyui-datebox" 
									data-options="editable:false" style="width: 90px;"/>
								&nbsp;-&nbsp;
								<input id="startDateMax" name="startDateMax" type="text" class="textbox easyui-datebox" 
									data-options="editable:false" style="width: 90px;"/>
							</td>
							<td>合同结束时间:</td>
							<td>
								<input id="endDateMin" name="endDateMin" type="text" class="textbox easyui-datebox" 
									data-options="editable:false" style="width: 90px;"/>
								&nbsp;-&nbsp;
								<input id="endDateMax" name="endDateMax" type="text" class="textbox easyui-datebox" 
									data-options="editable:false" style="width: 90px;"/>
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<input type="button" value="查询" class="btn" onclick="submitForm('queryForm')"/>
								<input type="button" value="导出" class="btn" onclick="excelExport('queryForm')"/>
								<input type="button" value="重置" class="btn" onclick="$('#queryForm').form('clear');"/>
							</td>
						</tr>
					</table>
				</form>
			</div>
			
			<div id="queryContent" ></div>
		</div>
	</div>
</div>
</div>
<script type="text/javascript">
function submitForm(fromId) {
	//去掉 input 输入的 前后空格
	$('#queryForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	
	var formAction = "<%=basePath%>report/loanManBase/list.do";
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

//导出
function excelExport(fromId) {
	$('#' + fromId).attr("action","<%=basePath%>report/loanManBase/export.do");
	$('#' + fromId).attr("method","post");
	$('#' + fromId).submit();
}

//页面加载完动作
$(document).ready(function() {
	$("#orgId").combotree({
		url:"sys/org/selectList.do?nodeLevel=2",
		method:'get',
		required:false,
		panelHeight:'auto',
		onSelect:function(node) {
	    	$("#orgName").val(node.text);
	    }
	});
	submitForm("queryForm");
});
</script>
</body>
</html>


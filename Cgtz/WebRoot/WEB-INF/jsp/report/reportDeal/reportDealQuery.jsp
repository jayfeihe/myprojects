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
<title>分公司每月成交量统计表</title>
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
<div class="easyui-tabs" id="netFundsTabs" data-options="fit:true">
<div title="分公司每月成交量统计表">
	<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">查询</a></p>
		
		<div class="content">
			<form id="queryReportDealForm" action="report/reportDeal/list.do" method="post" target="queryContent">
				<table>
<tr>
<%-- <td>所属机构:</td>
<td><input id="orgId" name="orgId" <c:if test="${login_org.orgId ne '86'  }">value='${login_org.orgId }' readonly='readonly'</c:if> class="textbox easyui-combotree"
data-options="editable:false,panelHeight:'auto',url:'<%=basePath%>sys/org/selectList.do?nodeLevel=2',method:'get'"/></td>
<td></td> --%>
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
						<td>
							<input type="button" value="查询" class="btn" onclick="submitForm('queryReportDealForm')"/>
							<input type="button" value="导出" class="btn" onclick="exportExcel('queryReportDealForm')"/>
							<input type="button" value="重置" class="btn" onclick="$('#queryReportDealForm').form('clear');"/>
						</td>
						<td></td>
					</tr>
				</table>	
			</form>
		</div>
		
		<div id="queryContent" >
		<%--
		查询列表
		 --%>
		</div>
	</div>
</div>
</div>
</div>
</body>
<script type="text/javascript">

//////////////       ////////////////
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
	$('#queryReportDealForm').find('input').each(function(){
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
//导出
function exportExcel(fromId) {
	$('#' + fromId).attr("action","<%=basePath%>report/reportDeal/excel.do");
	$('#' + fromId).attr("method","post");
	$('#' + fromId).submit();
}
//页面加载完动作
$(document).ready(function() {
//填充select数据样例


	submitForm("queryReportDealForm");
});
</script>

</html>


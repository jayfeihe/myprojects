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
<title>质押、抵押物信息查询</title>
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
<div class="easyui-tabs" id="collateralAccountTabs" data-options="fit:true">
<div title="抵押物台账统计">
	<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">查询</a></p>
		
		<div class="content">
			<form id="queryCollateralAccountForm" action="report/collateralAccount/list.do" method="post" target="queryContent">
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
						<td>合同状态:</td>
						<td><input id="state" name="state" type="text" editable="false" class="textbox easyui-combobox"
						data-options="panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
						data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'1','keyValue':'未生效'},{'keyProp':'2','keyValue':'合同中'},{'keyProp':'3','keyValue':'合同结清'},{'keyProp':'4','keyValue':'被拆分'}]"/></td>
						<td>项目类型:</td>
						<td><input id="product" name="product" type="text"  class="textbox easyui-combobox" 
						    data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
						    url:'sys/datadictionary/listjason.do?keyName=productType'"/>
						</td>
					</tr>
					
					<tr>
						<td>出借日期:</td>
						<td><input id="minStartDate" name="minStartDate" type="text" editable="false"  class="textbox easyui-datebox" style="width:90px;"/>
							&nbsp;-&nbsp;
							<input id="maxStartDate" name="maxStartDate" type="text" editable="false"  class="textbox easyui-datebox" style="width:90px;"/>
						</td>
						<td>还款日期:</td>
						<td><input id="minEndDate" name="minEndDate" type="text" editable="false"  class="textbox easyui-datebox" style="width:90px;"/>
							&nbsp;-&nbsp;
							<input id="maxEndDate" name="maxEndDate" type="text" editable="false"  class="textbox easyui-datebox" style="width:90px;"/>
						</td>
						<td>押品状态:</td>
						<td><input id="colState" name="colState" type="text" editable="false" class="textbox easyui-combobox"
						data-options="panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
						data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'1','keyValue':'库存中'},{'keyProp':'2','keyValue':'正常出库'},{'keyProp':'3','keyValue':'资产处置'}]"/>
						</td>
					</tr>

					<tr>
						<td colspan="3">
							<input type="button" value="查询" class="btn" onclick="submitForm('queryCollateralAccountForm')"/>
							<input type="button" value="导出" class="btn" onclick="exportExcel('queryCollateralAccountForm')"/>
							<input type="button" value="重置" class="btn" onclick="$('#queryCollateralAccountForm').form('clear');"/>
						</td>
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
	$('#queryCollateralAccountForm').find('input').each(function(){
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
	$('#' + fromId).attr("action","<%=basePath%>report/collateralAccount/excel.do");
	$('#' + fromId).attr("method","post");
	$('#' + fromId).submit();
}
//页面加载完动作
$(document).ready(function() {
//填充select数据样例
$("#orgId").combotree({
		url:"sys/org/selectList.do?nodeLevel=2",
		method:'get',
		required:false,
		panelHeight:'auto',
	    onSelect:function(node) {
	    	$("#orgName").val(node.text);
	    }
	});

	submitForm("queryCollateralAccountForm");
});
</script>

</html>


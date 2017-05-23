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
<title>逾期明细表</title>
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
<div id="afterLoanTabs" class="easyui-tabs" data-options="fit:true">
<div title="逾期明细表">
	<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">查询</a></p>
		
		<div class="content">
			<form id="queryCheckTaskForm" action="report/overdueDetail/list.do" method="post" target="queryContent">
				<table>
<tr>
<%-- <td>分公司:</td>
<td><input id="org" name="org" <c:if test="${login_org.orgId ne '86'  }">value='${login_org.orgId }' readonly='readonly'</c:if> class="textbox easyui-combotree"
data-options="editable:false,panelHeight:'auto',url:'<%=basePath%>sys/org/selectList.do?nodeLevel=2',method:'get'"/></td> --%>
<td>分公司:</td>
							<c:choose>
							<c:when test="${login_org.orgId ne '86'}">
							<td><input type="text" class="textbox easyui-validatebox" value="${loginOrgName}" readonly="readonly"/></td>
							</c:when>
							<c:otherwise>
							<td><input id="org" name="org"   class="textbox easyui-combobox" 
							    data-options="editable:false,panelHeight:'auto',valueField:'orgId',textField:'orgName',
							    url:'roleDataRelOrgs/listOrgs.do'"/></td>
							</c:otherwise>
							</c:choose>
<td>逾期类型:</td>
				<td><input id="overdueType" name="overdueType" type="text"  class="textbox easyui-combobox" style="width:100px;"
				data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
				data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'1','keyValue':'利息逾期'},{'keyProp':'2','keyValue':'本金逾期'}]"/>
               </td>	      
<td>应收日期:</td>
<td colspan="2"><input id="minRetDate" name="minRetDate" type="text" editable="false" class="textbox easyui-datebox" style="width:100px;"/>
-<input id="maxRetDate" name="maxRetDate" type="text" editable="false" class="textbox easyui-datebox" style="width:100px;"/></td>		
</tr>					

					<tr><td></td>
						<td>
							<input type="button" value="查询" class="btn" onclick="submitForm('queryCheckTaskForm')"/>
							<input type="button" value="导出" class="btn" onclick="exportExcel('queryCheckTaskForm')"/>
							<input type="button" value="重置" class="btn" onclick="$('#queryCheckTaskForm').form('clear');"/>
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
//添加选项卡
function addTab(title, url){
    if ($('#afterLoanTabs').tabs('exists', title)){
        $('#afterLoanTabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#afterLoanTabs').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeTab(){
	var tab = $('#afterLoanTabs').tabs('getSelected');
	var tabIndex=$('#afterLoanTabs').tabs('getTabIndex',tab);
	$('#afterLoanTabs').tabs('close',tabIndex);
	submitForm("queryCheckTaskForm");//解决Tab提交关闭列表页刷新的问题
}

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
	$('#queryCheckTaskForm').find('input').each(function(){
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
function refreshTab(tabs) {
	var currTab =  self.$('#'+tabs).tabs('getSelected'); //获得当前tab
    var url = $(currTab.panel('options').content).attr('src');
    var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
    self.$('#'+tabs).tabs('update', {
      tab : currTab,
      options : {
       content : content,
       //closable:true,  
       fit:true,  
       selected:true 
      }
     });
}
//导出
function exportExcel(fromId) {
	$('#' + fromId).attr("action","<%=basePath%>report/overdueDetail/excel.do");
	$('#' + fromId).attr("method","post");
	$('#' + fromId).submit();
}
//页面加载完动作
$(document).ready(function() {
	submitForm("queryCheckTaskForm");
});
</script>

</html>


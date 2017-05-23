<%@page import="com.tera.audit.common.constant.CommonConstant"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>
<div class="easyui-tabs" id="billAcctQueryTabs" data-options="fit:true">
	<div title="交易记录列表">
		<div id="main">
			<div class="content">
				<form id="queryBillAcctForm" action="account/afterloan/acctList.do?loanId=${loanId }&contractId=${contractId }" method="post" target="queryBillAcctContent">
						
				</form>
			</div>
			
			<div id="queryBillAcctContent" >
			<%--
			查询列表
			 --%>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
//添加选项卡
function addTab(title, url){
    if ($('#billAcctQueryTabs').tabs('exists', title)){
        $('#billAcctQueryTabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#billAcctQueryTabs').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeTab(){
	var tab = $('#billAcctQueryTabs').tabs('getSelected');
	var tabIndex=$('#billAcctQueryTabs').tabs('getTabIndex',tab);
	$('#billAcctQueryTabs').tabs('close',tabIndex);
	submitForm("queryBillAcctForm");//解决Tab提交关闭列表页刷新的问题
}
</script>

<script type="text/javascript">
function submitForm(fromId) {
	//去掉 input 输入的 前后空格
	$('#queryBillAcctForm').find('input').each(function(){
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
//页面加载完动作
$(document).ready(function() {
	submitForm("queryBillAcctForm");
});
</script>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>
<div class="tab_show_loanlaw" style="display: none;" title="核价" ></div>
<div class="easyui-tabs" id="loanLawAuditQueryTabs" data-options="fit:true">
	<div title="诉讼列表">
		<div id="main">
				<div id="part1" class="part">
					<p class="title"><a href="javascript:void(0);">诉讼列表</a></p>
					<div class="content">
						<form id="queryLawAuditForm" action="law/loanlaw/list.do?loanId=${loanId }&opt=${opt}" method="post" target="queryLawAuditContent"></form>
					<div id="queryLawAuditContent" >
						<%--
						查询列表
						 --%>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function submitForm(fromId) {
	//去掉 input 输入的 前后空格
	$('#queryLawAuditForm').find('input').each(function(){
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
	submitForm("queryLawAuditForm");
});
</script>
<script type="text/javascript">
//添加选项卡
function addLawTab(title, url){
    if ($('#loanLawAuditQueryTabs').tabs('exists', title)){
        $('#loanLawAuditQueryTabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#loanLawAuditQueryTabs').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeLawTab(){
	var tab = $('#loanLawAuditQueryTabs').tabs('getSelected');
	var tabIndex=$('#loanLawAuditQueryTabs').tabs('getTabIndex',tab);
	$('#loanLawAuditQueryTabs').tabs('close',tabIndex);
	submitForm("queryLawAuditForm");//解决Tab提交关闭列表页刷新的问题
}
</script>

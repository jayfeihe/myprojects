<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>
<div class="easyui-tabs" id="renewHistoryQueryTabs" data-options="fit:true">
	<div title="续贷历史列表">
		<div id="main">
			<div id="part1" class="part">
				<p class="title"><a href="javascript:void(0);">续贷历史列表</a></p>
				
				<div class="content">
					<form id="queryRenewHistoryForm" action="loan/renew/readList.do?origLoanId=${origLoanId }" method="get" target="queryRenewHistotyContent">
					</form>
				</div>
				
				<div id="queryRenewHistotyContent" >
				<%--
				查询列表
				 --%>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
//添加选项卡
function addTab(title, url){
    if ($('#renewHistoryQueryTabs').tabs('exists', title)){
        $('#renewHistoryQueryTabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#renewHistoryQueryTabs').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeTab(){
	var tab = $('#renewHistoryQueryTabs').tabs('getSelected');
	var tabIndex=$('#renewHistoryQueryTabs').tabs('getTabIndex',tab);
	$('#renewHistoryQueryTabs').tabs('close',tabIndex);
	submitForm("queryRenewHistoryForm");//解决Tab提交关闭列表页刷新的问题
}
</script>

<script type="text/javascript">
function submitForm(fromId) {
	//去掉 input 输入的 前后空格
	$('#queryRenewHistoryForm').find('input').each(function(){
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
	submitForm("queryRenewHistoryForm");
});
</script>

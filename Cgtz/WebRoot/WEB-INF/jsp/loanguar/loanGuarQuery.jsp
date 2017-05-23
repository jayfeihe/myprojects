<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>
<!--tab标签  -->
<div class="easyui-tabs" id="guarQueryTabs" data-options="fit:true">
	<div title="担保列表">
		<div id="main">
			<div id="part1" class="part">
				<p class="title"><a href="javascript:void(0);">担保列表</a></p>
				<div class="content">
					<form id="queryGuarForm" action="loanguar/list.do?loanId=${loanId }&origLoanId=${origLoanId }&opt=${opt}" method="post" target="queryGuarContent">
					</form>
				
					<div id="queryGuarContent">
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
//添加选项卡
function addGuarTab(title, url){
    if ($('#guarQueryTabs').tabs('exists', title)){
        $('#guarQueryTabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#guarQueryTabs').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeGuarTab(){
	var tab = $('#guarQueryTabs').tabs('getSelected');
	var tabIndex=$('#guarQueryTabs').tabs('getTabIndex',tab);
	$('#guarQueryTabs').tabs('close',tabIndex);
	submitForm("queryGuarForm");//解决Tab提交关闭列表页刷新的问题
}

function refreshGuarTab(tabs,url) {
	var currTab =  $('#'+tabs).tabs('getSelected'); //获得当前tab
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
    
    submitForm("queryGuarForm");//解决Tab提交关闭列表页刷新的问题
}
</script>

<script type="text/javascript">
function submitForm(fromId) {
	//去掉 input 输入的 前后空格
	$('#queryGuarForm').find('input').each(function(){
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
	submitForm("queryGuarForm");
});
</script>

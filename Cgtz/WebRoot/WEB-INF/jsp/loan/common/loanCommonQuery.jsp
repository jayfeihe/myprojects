<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>
<div class="easyui-tabs" id="appCommonQueryTabs" data-options="fit:true">
	<div title="共同借款人列表">
		<div id="main">
				<div id="part1" class="part">
					<p class="title"><a href="javascript:void(0);">共同借款人列表</a></p>
					<div class="content">
						<form id="queryCommonForm" action="loan/common/list.do?loanId=${loanId }&opt=${opt}" method="post" target="queryCommonContent"></form>
					<div id="queryCommonContent" >
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
	$('#queryCommonForm').find('input').each(function(){
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
	submitForm("queryCommonForm");
});
</script>
<script type="text/javascript">
//添加选项卡
function addCommonTab(title, url){
    if ($('#appCommonQueryTabs').tabs('exists', title)){
        $('#appCommonQueryTabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#appCommonQueryTabs').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeCommonTab(){
	var tab = $('#appCommonQueryTabs').tabs('getSelected');
	var tabIndex=$('#appCommonQueryTabs').tabs('getTabIndex',tab);
	$('#appCommonQueryTabs').tabs('close',tabIndex);
	submitForm("queryCommonForm");//解决Tab提交关闭列表页刷新的问题
}

function refreshCommonTab(tabs,url) {
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
    
    submitForm("queryCommonForm");//解决Tab提交关闭列表页刷新的问题
}
</script>

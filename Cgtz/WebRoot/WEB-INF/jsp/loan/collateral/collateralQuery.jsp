<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>
<div class="easyui-tabs" id="collQueryTabs" data-options="fit:true">
	<div title="质/抵押物列表">
	<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">查询</a></p>
		
		<div class="content">
			<form id="queryCollateralForm" action="loan/collateral/list.do?loanId=${loanId }&origLoanId=${origLoanId }&opt=${opt}" method="post" target="queryCollateralContent">
				<table>
					<tr>
						<td>类型:</td>
						<td>
							<input type="text" id="type" name="type" class="textbox easyui-combobox"
								data-options="editable:false,panelHeight:'auto',
										textField:'keyValue',
										valueField:'keyProp',
										data:dataDictJson.collateralType"/>
						</td>
						<td>
							<input type="button" value="查询" class="btn" onclick="submitForm('queryCollateralForm');"/>
							<input type="button" value="重置" class="btn" onclick="$('#queryCollateralForm').form('clear');"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div id="queryCollateralContent" >
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
function addCollateralTab(title, url){
    if ($('#collQueryTabs').tabs('exists', title)){
        $('#collQueryTabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#collQueryTabs').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeCollateralTab(){
	var tab = $('#collQueryTabs').tabs('getSelected');
	var tabIndex=$('#collQueryTabs').tabs('getTabIndex',tab);
	$('#collQueryTabs').tabs('close',tabIndex);
	submitForm("queryCollateralForm");//解决Tab提交关闭列表页刷新的问题
}

function refreshCollateralTab(tabs,url) {
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
    
    submitForm("queryCollateralForm");//解决Tab提交关闭列表页刷新的问题
}
</script>

<script type="text/javascript">
function submitForm(fromId) {
	//去掉 input 输入的 前后空格
	$('#queryCollateralForm').find('input').each(function(){
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
	submitForm("queryCollateralForm");
});
</script>

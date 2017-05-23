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
<div class="easyui-tabs" id="assetManageTabs" data-options="fit:true">
<div title="资产管理列表">
	<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">查询</a></p>
		
		<div class="content">
			<form id="queryAssetForm" action="asset/manage/list.do" method="post" target="queryAssetContent">
				<table>
					<tr>
<td>合同编号:</td>
<td><input id="contractId" name="contractId" type="text"  class="textbox easyui-validatebox"/></td>
<td>申请编号:</td>
<td><input id="loanId" name="loanId" type="text"  class="textbox easyui-validatebox"/></td>
<td>房产证号:</td>
<td><input id="housePropertyCode" name="housePropertyCode" type="text"  class="textbox easyui-validatebox"/></td>
<td>车牌号:</td>
<td><input id="license" name="license" type="text"  class="textbox easyui-validatebox"/></td>
</tr>
<tr>
<td>类别:</td>
<td><input id="type" name="type" type="text"  class="textbox easyui-combobox" 
    data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
    data:dataDictJson.collateralType"/>
</td>
<td>所处仓库:</td>
<td><input id="warehouseId" name="warehouseId" type="text"  class="textbox easyui-combobox"
    data-options="editable:false,panelHeight:'auto',valueField:'id',textField:'name',
    url:'warehouse/listWarehouse.do'"/></td>
<td>检查状态:</td>
<td><input id="latestCheck" name="latestCheck" type="text"  class="textbox easyui-combobox"
     data-options="panelHeight:'auto',editable:false,valueField:'keyProp',textField:'keyValue',
     data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'0','keyValue':'未检查'},{'keyProp':'1','keyValue':'正常'},{'keyProp':'2','keyValue':'不正常'}]"/>
</td>
<td>资产状态:</td>
<td><input id="state" name="state" type="text"  class="textbox easyui-combobox"
     data-options="panelHeight:'auto',editable:false,valueField:'keyProp',textField:'keyValue',
     data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'1','keyValue':'库存中'},{'keyProp':'2','keyValue':'正常出库'},{'keyProp':'3','keyValue':'资产处置'}]"/>
</td>
</tr>
<tr>
<td>检查时间</td>
<td colspan="2"><input id="beginCheckTime" name="beginCheckTime" class="textbox easyui-datebox" style="width:100px;" editable="false"/>-<input id="endCheckTime" name="endCheckTime" class="textbox easyui-datebox"  style="width:100px;" editable="false"/></td>
</tr>
					<tr><td></td>
						<td>
							<input type="button" value="查询" class="btn" onclick="submitForm('queryAssetForm')"/>
							<input type="button" value="重置" class="btn" onclick="$('#queryAssetForm').form('clear');"/>
						</td>
						<td></td>
					</tr>
				</table>	
			</form>
		</div>
		
		<div id="queryAssetContent" >
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
    if ($('#assetManageTabs').tabs('exists', title)){
        $('#assetManageTabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#assetManageTabs').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeTab(){
	var tab = $('#assetManageTabs').tabs('getSelected');
	var tabIndex=$('#assetManageTabs').tabs('getTabIndex',tab);
	$('#assetManageTabs').tabs('close',tabIndex);
	submitForm("queryAssetForm");//解决Tab提交关闭列表页刷新的问题
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
	$('#queryAssetForm').find('input').each(function(){
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
function refreshAssetManageTabs(tabs,url) {
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
}
//页面加载完动作
$(document).ready(function() {


	submitForm("queryAssetForm");
});
</script>

</html>


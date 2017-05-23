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
<title>营销人员表查询</title>
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
	<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">销售人员查询</a></p>
		
		<div class="content">
			<form id="queryForm" action="sales/list.do" method="post" target="queryContent">
				<table>
					<tr>
						<td>员工卡号:</td>
						<td>
						<input id="staffNo" name="staffNo" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"/>
						</td>
						<td>姓名:</td>
						<td><input id="name" name="name" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"/></td>
						<td>所属机构:</td>
						<td><input id="orgId" name="orgId" type="text" class="easyui-combobox" style="" editable="false" value="${login_org.orgId}" /></td>
						<td>营销团队:</td>
						<td><input id="departId" name="departId" type="text" class="easyui-combotree" style="" editable="false" value="" /></td>
						
					</tr>
					<tr>
						<td>在职状态:</td>
						<td>
							<select class="easyui-combobox" name="state" editable="false" style="width:152px;">
									<option value="1" selected="selected">在职</option>
									<option value="2">挂起</option>
									<option value="0">离职</option>
							</select>
						</td>
						<td></td>
						<td>
							<input type="button" value="查询" class="btn" onclick="submitForm('queryForm')"/>
							<input type="reset" value="重置" class="btn" onclick="$('#queryForm').form('clear');"/>
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

<!-- <div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
	<img src="img/loading.gif" alt="加载中..." />
</div> -->

</body>
	
<script type="text/javascript">
$(document).ready(function(){
	var tsurl="sys/org/subOrg.do?orgId=86";
	$("#orgId").combobox("clear");
	$('#orgId').combobox({
		url:tsurl,
		valueField:'orgId',
		textField:'orgName',
		//添加空白行
		loadFilter:function(data){
       		var opts = $(this).combobox('options');
       		var emptyRow = {};
			emptyRow[opts.valueField] = '&nbsp;';
			emptyRow[opts.textField] = '...';
			data.unshift(emptyRow);
 			return data;
		},
		onChange: function(nVal,oVal) {
			getSaleTeamTree(nVal);
		}
	});
	var orgId = $("#orgId").val();
	getSaleTeamTree(orgId);
	//getSaleTeamBox(orgId);
	
	submitForm("queryForm");
});
function submitForm(fromId) {
	var formAction = $('#' + fromId).attr("action");
	var targetDiv = $('#' + fromId).attr("target");
	var params = $('#' + fromId).serialize();
	//弹出异步加载 遮罩
	openMask();
	$.ajax({
		type : "POST",
		url : formAction,
		data : encodeURI(params + "&targetDiv=" + targetDiv),
		dataType : "html",
		success : function(data) {
			closeMask();
			$('#' + targetDiv).html(data);
		},
		error : function() {
			closeMask();
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
		}
	});
}
/* //打开Loading遮罩并修改样式
function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
} */

// 下拉框形式显示营销团队
function getSaleTeamBox(org_id) {
	$("#departId").combobox("clear");
	$('#departId').combobox({
		url:"sys/depart/getSaleTeam.do?orgId="+org_id,
		valueField:'id',
		textField:'departName'
	});
}
//树形式显示营销团队
function getSaleTeamTree(org_id) {
	$("#departId").combotree({
		url:'sys/depart/listDataUseForSales.do?orgId='+org_id,
		method:'get',
		required:false,
	    //选择树节点触发事件  
	    onSelect : function(node) {  
	        //返回树对象  
	        var tree = $(this).tree;  
	        //选中的节点是否为叶子节点,如果不是叶子节点,清除选中  
	        var isLeaf = tree('isLeaf', node.target);  
	        if (!isLeaf) {  
	            //清除选中  
	             $('#departId').treegrid("unselect");
	        }  
	    }  
	});
}
</script>
</html>


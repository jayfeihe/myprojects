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
<title>用户管理</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
	
</head>
	<body>
	<div id="main">

	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">用户查询</a></p>
		
		<div id="queryForm" class="content">
			<div id="queryFormContent">
			<form id="queryCondition" method="post" action="sys/user/list.do" target="queryContent">
				<table>
					<tr>
					<td class="td01">登录ID:</td><td><input id='loginID' name='loginId' type='text' class="txt" value='${pm.model.loginId}'/></td>
					
					<td>姓名:</td><td><input id='name' name='name' type='text' class="txt" value='${pm.model.name}'/></td>
					<td>数据权限:</td>
								<td><input id="orgId" name="orgId" type="text" class="easyui-combobox" editable="false" />
					</td>
					<td>角色:</td>
								<td><input id="roleId" name="roleId" type="text" class="easyui-combobox" editable="false" />
					</td>
					<td>是否管理员：</td><td><input id='isAdmin' name='isAdmin' type="checkbox" value="1" /></td>
					
					</tr>
					<tr>
						<td colspan="6">
					
					<input type="button" value="提交" class="btn" onclick="submitForm('queryCondition')"/>
					<input type="button" value="重置" class="btn" 
					onclick="$('#queryCondition').form('clear');"  />
					</td>
					</tr>
				</table>
					
			</form>
			</div>
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

$(document).ready(function() {

	var tsurl="sys/role/queryAllRole.do";
	$("#roleId").combobox("clear");
	$('#roleId').combobox({
		url:tsurl,
		valueField:'id',
		textField:'name',
		//添加空白行
		loadFilter:function(data){
       		var opts = $(this).combobox('options');
       		var emptyRow = {};
			emptyRow[opts.valueField] = '&nbsp;';
			emptyRow[opts.textField] = '...';
			data.unshift(emptyRow);
 			return data;
		}
	});

	//数据权限
	$("#orgId").combotree({
		url:"sys/org/listDataByLevelAndOrgId.do?level=4",
		method:'get',
		required:false

	});
	
	submitForm('queryCondition');
});
 	 
</script>
</html>


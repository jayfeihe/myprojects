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
	<div id="userTabs" class="easyui-tabs" data-options="fit:true">
	<div title="用户管理列表">
	<div id="main">
	<div id="part1" class="part" >
		<p class="title"><a href="javascript:void(0);">用户查询</a></p>
		
		<div id="queryForm" class="content">
			<div id="queryFormContent">
			<form id="queryCondition" method="post" action="sys/user/list.do" target="queryContent">
				<table>
					<tr>
						<td class="td01">登录ID:</td><td><input id='loginID' name='loginId' type='text' class="txt" value='${pm.model.loginId}'/></td>
						<td>姓名:</td><td><input id='name' name='name' type='text' class="txt" value='${pm.model.name}'/></td>
						<td>所属机构:</td>
						<td><input id="orgId" name="orgId" type="text" class="easyui-combobox" editable="false" /></td>
						<td>是否管理员：<input id='isAdmin' name='isAdmin' type="checkbox" value="1" /></td>
					</tr>
					<tr>
						<td>角色:</td>
						<td><input id="roleId" name="roleId" type="text" class="easyui-combobox" editable="false" /></td>
						<td>状态:</td>
						<td><input id="state" name="state" type="text" class="easyui-combobox" 
									data-options="textField:'text',
												valueField:'value',
												data:[{'text':'请选择','value':''},
													{'text':'启用','value':'1'},
													{'text':'挂起','value':'2'},
													{'text':'关闭','value':'0'}],
												editable:false,
												panelHeight:'auto'"/></td>
						<td  colspan="2">
							<input type="button" value="查询" class="btn" onclick="submitForm('queryCondition')"/>
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
</div>
</div>
<!-- <div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
	<img src="img/loading.gif" alt="加载中..." />
</div> -->
</body>

<script type="text/javascript">
//添加选项卡
function addTab(title, url){
    if ($('#userTabs').tabs('exists', title)){
        $('#userTabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#userTabs').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeTab(){
	var tab = $('#userTabs').tabs('getSelected');
	var tabIndex=$('#userTabs').tabs('getTabIndex',tab);
	$('#userTabs').tabs('close',tabIndex);
	submitForm("queryCondition");//解决Tab提交关闭列表页刷新的问题
}

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
		textField:'name'
		/* //添加空白行
		loadFilter:function(data){
       		var opts = $(this).combobox('options');
       		var emptyRow = {};
			emptyRow[opts.valueField] = '&nbsp;';
			emptyRow[opts.textField] = '...';
			data.unshift(emptyRow);
 			return data;
		} */
	});

	//数据权限
	$("#orgId").combotree({
		url:"sys/org/selectList.do",
		method:'get',
		required:false,
		panelHeight:'auto',
		//选择树节点触发事件  
		/*
	    onBeforeSelect : function(node) {  
	        //返回树对象  
	        var tree = $(this).tree;  
	        //选中的节点是否为叶子节点,如果不是叶子节点,清除选中  
	        var isLeaf = tree('isLeaf', node.target);  
	        if (!isLeaf) {  
	            //清除选中  
	            return false;
	        }  
	    }  */
	});
	
	submitForm('queryCondition');
});
 	 
</script>
</html>


<%@page import="com.tera.sys.model.Org"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>" />
<title>用户管理</title>
<link href="css/global.css" type="text/css" rel="stylesheet" />
<script src="js/jquery.min.js" type="text/javascript"></script>
<script src="js/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript" src="js/dtree.js"></script>
<link href="css/dtree.css" rel="stylesheet" type="text/css"></link>
<script src="js/artDialog/artDialog.js?skin=opera"></script>
<script src="js/artDialog/plugins/iframeTools.source.js"></script>
<script src="js/jquery.validate.js" type="text/javascript"></script>
<script src="js/jquery.validate.ms.js" type="text/javascript"></script>
<link href="css/global.css" type="text/css" rel="stylesheet"/>
<link href="css/icon.css" type="text/css" rel="stylesheet" />
<link href="css/default/easyui.css" type="text/css" rel="stylesheet" />
<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>

<style type="text/css">
<!--
body {
	background-color: #ffffff;
	margin: 0px;
	padding: 0px;
}

#roletree {
	border: 1px solid #85B6E2;
	margin: 5px;
	padding: 5px;
}

#rolecontrl {
	margin: 5px;
	padding: 5px;
}

.cx {
	
}
-->
</style>
<style type="text/css">
	.content table td {
		padding: 0;
	}
</style>
<script type="text/javascript">
	function cc(id){
		alert(id);
	}
</script>
</head>
<body>
<div id="main">

	<div class="part" id="part1" >
		<p class="title">当前设置用户:${user.name}</p>
		<div class="content">
			<form action="">
				<table class="easyui-datagrid" data-options="fitColumns:true" id="treegrid"></table>
				<div id="rolecontrl" align="right">
					<input type="button" class="btn" value="提交" onclick="submitForm()" />
					<input type="button" class="btn" value="返回" onclick="javascript:back();" />
				</div>
			</form>
		</div>
	</div>

</div>

<script type="text/javascript">
//加载列表方法
function gridLoad(){
	openMask();
	$('#treegrid').treegrid({    
	    url:'sys/depart/listData.do?parentId=-1',   
	    singleSelect:false,
	    selectOnCheck:true,
	    checkOnSelect:true,
	    idField:'id',    
	    treeField:'departName', 
	    columns:[[ 
	        {title:'组织名称', field:'departName', width:$(this).width() * 0.3,formatter: function(value,row,index){
				return '<input type=\"checkbox\" name="departId" value=\"' +row.id+ '\">' + row.departName ;
			}}   
	   ]],
	   onLoadSuccess: function() {
		   var departIdStr = '${departIds}';
		   var departIds =  departIdStr.split(",");
		   for(var i=0;i<departIds.length;i++) {
			   $("input[name='departId']").each(function(){
	    			if($(this).val() == departIds[i]) {
	    				$(this).attr("checked","checked");
	    			}
	    		});
		   }
		   closeMask();
	    }
	});  
};

//提交
function submitForm(){
	var departIdArray = new Array();
	$("input[name='departId']").each(function() {
           if ($(this).attr('checked')) {
        	   departIdArray.push($(this).val());
           }
       });
	var departIds = departIdArray.join(",");
	$.ajax({
			url:"<%=basePath%>sys/user/setDepartAction.do",
			//防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
			data:encodeURI("loginId=${user.loginId}&name=${user.name}&departIds="+departIds+"&timestamp="+(new Date()).getTime()),
			dataType:"json",
			success:function(data){
				if("true" == data.success){
					$.messager.alert('消息', data.message,"info", function(){
						window.history.go(-1);
		        	});
		         } else {
					$.messager.alert('消息', data.message);
	            }
			},
			error : function() {
				$.messager.alert('消息',"操作失败，请联系管理员！");
			}
	});
}

//页面加载完动作
$(document).ready(function (){
	//加载列表
    gridLoad();
    $(window).resize(function(){
	 	$('#treegrid').datagrid('resize');
	});
});

//返回
function back(){
	window.history.go(-1);
}
</script>
</body>
</html>


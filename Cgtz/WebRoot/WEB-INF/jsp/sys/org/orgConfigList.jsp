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
<title>机构信息表列表</title>

<style type="text/css">
	.content table td {
		padding: 0;
	}
</style>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${pm.url}">
			<table class="easyui-treegrid" data-options="fitColumns:true" id="treegrid"></table>
		</form>
	</div>
</body>

<script language="javascript">
//更新
function updateData(data_id) {
	if($("body").find("#dialogDiv").length==0){
		 $('body').append($("<div id='dialogDiv' style='top:150px;'></div>"));
	}
	$('#dialogDiv').dialog({
	    title: "机构配置",
	    width: 400,
	    closed: false,
	    cache: false,
	    href: "<%=basePath%>" + "sys/orgconfig/update.do?orgId="+data_id,
	    modal: true
	});
}

//加载列表方法
function gridLoad(){
	var params = $('#queryForm').serialize();
	$('#treegrid').treegrid({    
	    url:'sys/orgconfig/selectList.do?'+params,
	    singleSelect:true,
	    selectOnCheck:false,
	    checkOnSelect:false,
	    idField:'orgId',    
	    treeField:'orgName', 
	    columns:[[    
	        {title:'ID', field:'id', hidden:true, width:50},    
	        {title:'机构简称', field:'orgName', width:$(this).width() * 0.25}, 
	        {title:'机构全称', field:'orgFullName', width:$(this).width() * 0.25},
	        {title:'审批额度/元', field:'aduitAmt', width:$(this).width() * 0.25},
	        {title:'月债权/元', field:'loanAmt', width:$(this).width() * 0.25},
	        {title:'利息差比/%', field:'intRate', width:$(this).width() * 0.25},
	        {title:'操作', field:'操作',width:$(this).width() * 0.25, 
	        	formatter: function(value,row,index){
	        		if(row.level != 0){
						return '<a href="javascript:;" onclick="updateData('+ row.id +');">配置</a>';
	        		}else{
	        			return;
	        		}
				}	
	        }
	    ]],
	    onLoadSuccess:function(row, data){
	    	$('#loading').window('close');
	    }
	});  
}

$(function(){
	gridLoad();
	$(window).resize(function(){
	 	$('#treegrid').datagrid('resize');
	});
});
</script>
</html>


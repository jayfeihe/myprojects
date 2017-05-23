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
<title>组织管理列表</title>
<style type="text/css">
	.content table td {
		padding: 0;
	}
</style>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${url }">
			<div id="control" class="control">
				<a href="javascript:void(0);" onclick="add('添加组织','sys/depart/update.do');"><img src="img/square/sys_but_add.png" class='dotimg' title="添加" /></a>&nbsp;
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			<table class="easyui-treegrid" data-options="fitColumns:true" id="treegrid"></table>
		</form>
	</div>
</body>

<script language="javascript">
//更新
function updateData(data_id) {
	add('修改组织', 'sys/depart/update.do?id=' + data_id);
}

//删除
function deleteData(data_id) {
	$.messager.confirm('消息', '您确认要删除该组织及以下的组织吗？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
				url : "<%=basePath%>" + "sys/depart/delete.do",
				// 防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
				data : encodeURI("id=" + data_id + "&timestamp=" + (new Date()).getTime()),
				async : false,// 同步提交
				success : function(data) {
					if ("true"==data.success) {
						// form刷新防提示
						$.messager.alert('消息', data.message,"info", function(){
			              		window.location = window.location + "&timestamp=" + (new Date()).getTime();
								window.location.reload();
		            	});
					} else {
						$.messager.alert('消息', data.message);
					}
				},
				error : function() {
					$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
				}
			});
        }
    });
}


//添加
function add(_title, _url){
	if($("body").find("#dialogDiv").length==0){
		$('body').append($("<div id='dialogDiv'></div>"));
	}
	$('#dialogDiv').dialog({
	    title: _title,
	    width: 400,
	    closed: false,
	    cache: false,
	    href: _url,
	    modal: true
	});
	return;
}

//加载列表方法
function gridLoad(){
	var paramsJson = $('#queryForm').serializeArray();
	var flag = false;
	for(var i=0;i<paramsJson.length;i++) {
		if (paramsJson[i].value != '') {
			flag = true;
			break;
		}
	}
	var _url = 'sys/depart/listData.do?parentId=-1';
	if(flag) {
		var params = $('#queryForm').serialize();
		_url = 'sys/depart/listData.do?'+params;
	}
	$('#treegrid').treegrid({    
	    url:_url,   
	    singleSelect:true,
	    selectOnCheck:true,
	    checkOnSelect:true,
	    idField:'departCode',    
	    treeField:'departName', 
	    columns:[[    
	        {title:'ID', field:'id', hidden:true, width:50},    
// 	        {title:'组织代码', field:'departCode', width:100},    
	        {title:'组织名称', field:'departName', width:$(this).width() * 0.3},    
	        {title:'说明', field:'remarks', width:$(this).width() * 0.4},
	        {title:'操作', field:'departCode',width:$(this).width() * 0.3,
	        	formatter: function(value,row,index){
	        		if(row.parentId != -1){
						return '<a href="javascript:;" onclick="deleteData('+ row.id +');">删除</a>&nbsp; &nbsp;' + 
						'<a href="javascript:;" onclick="updateData('+ row.id +');">修改</a>';
	        		}else{
	        			return;
	        		}
				}	
	        }
	    ]],
	    onLoadSuccess:function(row, data){
	    }
	});  
};

//页面加载完动作
$(document).ready(function (){
	//加载列表
    gridLoad();
    $(window).resize(function(){
	 	$('#treegrid').datagrid('resize');
	});
});
</script>
</html>


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
			<div id="control" class="control">
				<a href="javascript:void(0);" onclick="add();"><img src="img/square/sys_but_add.png" class='dotimg' title="添加" /></a>&nbsp;
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			<table class="easyui-treegrid" data-options="fitColumns:true" id="treegrid"></table>
			<%-- <table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
<th scope="col">机构代码</th>
<th scope="col">机构名称</th>
<th scope="col">级别</th>

					<th scope="col" width="160">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
<td>${data.orgId}</td>
<td>${data.orgName}</td>
<td>${data.level}</td>

						<td>
							<a href="javascript:void(0);" onclick="javascript:updateData('${ data.id}');">更新</a>&nbsp;
							<a href="javascript:void(0);" onclick="javascript:deleteData('${ data.id}');">删除</a>&nbsp;
						</td>
					</tr>
				</c:forEach>
			</table>
	
			<div id="pageStyle">
			${ pm.pageNavigation}
			</div> --%>
		</form>
	</div>
</body>

<script language="javascript">
//更新
function updateData(data_id) {
	window.location = "<%=basePath%>" + "sys/org/update.do?id=" + data_id;
	
	return;
}

//删除
function deleteData(data_id) {
	
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
				url : "<%=basePath%>" + "sys/org/delete.do",
				// 防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
				data : encodeURI("id=" + data_id + "&timestamp=" + (new Date()).getTime()),
				async : false,// 同步提交
				success : function(data) {
					if ("true"==data.success) {
						// form刷新防提示
						
						$.messager.confirm('消息', data.message, function(ok){
			                if (ok){
			                   	window.location = window.location + "&timestamp=" + (new Date()).getTime();
								window.location.reload();
			                }
		            	});
					} else {
						
						$.messager.confirm('消息', data.message);
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
function add(){
	window.location = "<%=basePath%>" + "sys/org/update.do";
	
	return;
}

//加载列表方法
function gridLoad(){
	var params = $('#queryForm').serialize();
	$('#treegrid').treegrid({    
	    url:'sys/org/selectList.do?'+params,
	    singleSelect:true,
	    selectOnCheck:false,
	    checkOnSelect:false,
	    idField:'orgId',    
	    treeField:'orgName', 
	    columns:[[    
	        {title:'ID', field:'id', hidden:true, width:50},    
	        {title:'机构名称', field:'orgName', width:$(this).width() * 0.25}, 
	        {title:'机构代码', field:'orgId', width:$(this).width() * 0.25},
	        {title:'级别', field:'level', width:$(this).width() * 0.25},
	        {title:'操作', field:'操作',width:$(this).width() * 0.25, 
	        	formatter: function(value,row,index){
	        		if(row.level != 0){
						return '<a href="javascript:;" onclick="updateData('+ row.id +');">修改</a>&nbsp; &nbsp;' + 
						'<a href="javascript:;" onclick="deleteData('+ row.id +');">删除</a>';
	        		}else{
	        			return;
	        		}
				}	
	        }
	    ]],
	    /* onBeforeLoad:function(row, param){
	    	if(row == null){
	    		if($('#orgName').val().trim() == '')
	    			param.parentId = -1;	    		
	    	}
	    	else{
	    		param.parentId = row.id;	    		
	    	}
	    	param.orgName = $('#orgName').val().trim();
	    }, */
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


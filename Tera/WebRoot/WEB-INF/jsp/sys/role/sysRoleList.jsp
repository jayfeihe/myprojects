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
<title>角色管理</title>
<link href="css/global.css" type="text/css" rel="stylesheet"/>
<link href="css/dtree.css" rel="stylesheet" type="text/css"></link>
</head>
	<body>
		
		
		<div class="content">
			<form name="queryList" id="queryList" method="post" action="${pm.url}">
			<div id="control" class="control">
<%--				<a href="javascript:void(0);" onclick="javascript:showQueryContent('but_updown_id');"><img id="but_updown_id" src="img/square/but_up.png" class='dotimg' title="打开/关闭查询模板" /></a>&nbsp;--%>
				<a href="javascript:void(0);" onclick="javascript:artOpenPage('添加角色','sys/role/add.do');"><img src="img/square/sys_but_add.png" class='dotimg' title="添加角色" /></a>&nbsp;
<%--				<a href="javascript:void(0);" onclick="javascript:sysRoleExcel();"><img src="img/square/but_excel.png" class='dotimg' title="导出EXCEL文件" /></a>&nbsp;--%>
				<a href="javascript:void(0);" onclick="javascript:window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col" width="30">序号</th>
					<th scope="col">名称</th>
					<th scope="col">描述</th>
					<th scope="col">角色等级</th>
					<th scope="col" width="160">操作</th>
				</tr>
				<c:forEach items="${pm.data}" var="data" varStatus="status">
				<tr>
				<td style="text-align: center;  ">${status.index+pm.rowS+1}</td>
				<td>${data.name}</td>
				<td>${data.description}</td>
				<td>${data.leverName}</td>
				<td>
				<a href="javascript:void(0);" onclick="javascript:artOpenPage('修改角色','sys/role/update.do?id=${data.id}');" title="修改">修改</a>&nbsp;
				<a href="javascript:void(0);" onclick="javascript:artOpenPage('权限设置','sys/role/setmenu.do?id=${data.id}');">分配菜单</a>&nbsp;
				<a href="javascript:void(0);" onclick="javascript:delData('${data.id}');">删除</a>&nbsp;
				</td>
				</tr>
				</c:forEach>
				
			</table>
			
			<div id="pageStyle">
			${pm.pageNavigation}
			</div>
			
			</form>

		</div>

<script language="javascript">
function showQueryContent(obj){
	if($("#queryFormContent").css("display") == "block") {
		$("#queryFormContent").css("display","none");
		$("#"+obj).attr('src','img/square/but_down.png');
	}else{
		$("#queryFormContent").css("display","block");
		$("#"+obj).attr('src','img/square/but_up.png');
	}
}

//删除数据///////////////////////////////////
function delData(data_id){
	
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
	 			url:"sys/role/delRole.do",
	 			//防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
	 			data:encodeURI("ids=" + data_id  + "&timestamp=" + (new Date()).getTime()),
	 			//dataType:"json",
	 			async: false,//同步提交
	 			success:function(data){
	 				if("true"==data.success){
	 					//form刷新防提示
	 					
						$.messager.alert('消息', data.message,"info", function(){
			                   	window.location = window.location + "&timestamp=" + (new Date()).getTime();
								window.location.reload();
		            	});
	 				}else{
						
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

//打开页面
function artOpenPage(_title,_url) {
	if($("body").find("#dialogDiv").length==0){
		 $('body').append($("<div id='dialogDiv' style='top:150px;'></div>"));
	}
	
	$('#dialogDiv').dialog({
	    title: _title,
	    width: 450,
	    closed: false,
	    cache: false,
	    href: _url,
	    modal: true,
	    onLoad: function() {
			if(_title!='权限设置'){
		    	$("#orgRoleLever").combobox("clear");
				$('#orgRoleLever').combobox({ 
					valueField:'keyProp', 
					textField:'keyValue',
					data:dataDictJson.orgrolelever
				});
				$("#flag").combobox("clear");
				$('#flag').combobox({
					//url:tsurl, 
					valueField:'keyValue', 
					textField:'keyProp',
					data:[{keyProp:'请选择',keyValue:'0'},{keyProp:'组长',keyValue:'1'}]
				});
			}else{
				menuSet();
			}
	 	}
	});
}

//更新数据
function updateData(data_id){
	var url="sys/role/update.do?id="+data_id;
	//var winName = "更新角色";
	//openwindow(url,winName,600,400);
	window.location=url;
	
}


//分配菜单
function settleMenu(data_id){
	var url="sys/role/setmenu.do?id="+data_id;
	window.location=url;
	
}
//分配rma权限
function settleRmaAuth(data_id){
	var url="rmaRoleSetAuth.do?id="+data_id;
	window.location=url;
	
}
//导出
function sysRoleExcel(){
	var exportExcelAction = "sys/role/excel.do";
	
	var queryForm = $("#queryList");
	
	var oddAction = queryForm.attr("action");
	queryForm.attr("action",exportExcelAction);
	queryForm.get(0).submit();
	queryForm.attr("action",oddAction);
	
}
</script>

</body>
</html>


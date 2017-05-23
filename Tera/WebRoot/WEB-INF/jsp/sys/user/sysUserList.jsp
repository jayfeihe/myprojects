<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
		<div class="content">
			<form name="queryList" id="queryList" method="post" action="${pm.url}">

			<div id="control" class="control">

<%--				<a href="javascript:void(0);" onclick="javascript:showQueryContent('but_updown_id');"><img id="but_updown_id" src="img/square/but_up.png" class='dotimg' title="打开/关闭查询模板" /></a>&nbsp;--%>
				<a href="javascript:void(0);" onclick="javascript:artOpenPage('添加用户','sys/user/add.do');"><img src="img/square/sys_but_add.png" class='dotimg' title="添加用户" /></a>&nbsp;
<%--				<a href="javascript:void(0);" onclick="javascript:sysUserExcel();"><img src="img/square/but_excel.png" class='dotimg' title="导出EXCEL文件" /></a>&nbsp;--%>
				<a href="javascript:void(0);" onclick="javascript:window.location.reload();"><img src="img/square/but_renew.png"  class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col" width="30">序号</th>
					<th scope="col">登录ID</th>
					<th scope="col">姓名</th>
					<th scope="col">性别</th>
					<th scope="col">电邮</th>
					<th scope="col">电话</th>
					<th scope="col" width="250">操作</th>
				</tr>
				
				<c:forEach items="${pm.data}" var="data" varStatus="status">
				<tr>
				<td style="text-align: center;">${status.index+pm.rowS+1}</td>
				<td>${data.loginId}</td>
				<td>${data.name}</td>
				<td>
					<c:choose>
						<c:when test="${'M'==data.gender}">
						男
						</c:when>
						<c:when test="${'F'==data.gender}">
						女
						</c:when>
					</c:choose>
				</td>
				<td>${data.email}</td>
				<td>${data.phone}</td>
				<td>
				<%--
				<a href="javascript:void(0);" onclick="javascript:delData('${data.id}');" title="删除"><img src="img/deleteItem0.gif" class='dotimg'/></a>&nbsp;
				<a href="javascript:void(0);" onclick="javascript:artOpenPage('sys/user/update.do?id=${data.id}');" title="更新"><img src="img/updateItem.gif" class='dotimg'/></a>&nbsp;
				 --%>
				<a href="javascript:void(0);" onclick="javascript:delData('${data.id}');" title="删除">删除</a>&nbsp;
				<a href="javascript:void(0);" onclick="javascript:artOpenPage('修改用户','sys/user/update.do?id=${data.id}');" title="修改">修改</a>&nbsp;
<%-- 				<a href="javascript:void(0);" onclick="javascript:artOpenPage('角色分配','sys/user/setrole.do?id=${data.id}&roleType=1');">角色分配</a>&nbsp; --%>
<%-- 				<a href="javascript:void(0);" onclick="javascript:redWindow('<%=basePath%>sys/user/setOrg.do?id=${data.id}&roleType=1');">岗位权限设置</a>&nbsp; --%>
				<a href="javascript:void(0);" onclick="javascript:redWindow('<%=basePath%>sys/user/setOrg.do?id=${data.id}&loginId=${data.loginId}&name=${data.name }');">岗位权限设置</a>&nbsp;
				<a href="javascript:void(0);" onclick="javascript:redWindow('<%=basePath%>sys/user/setDepart.do?id=${data.id}');">组织机构设置</a>&nbsp;
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
//查询面板显示////////////////////////////////////////////////
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
	 			url:"sys/user/deluser.do",
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
	    width: 400,
	    closed: false,
	    cache: false,
	    href: _url,
	    modal: true
	    });
}

//查看用户菜单
function showUserMenu(data_id){
	
	var url="sys/user/usermenuspage.do?id="+data_id;
	window.location=url;
	
	return;
	
}

//查看用户菜单
function redWindow(url){
	window.location=url;
	
	return;
	
}

//导出
function sysUserExcel(){
	var exportExcelAction = "sys/user/excel.do";
	
	var queryForm = $("#queryList");
	
	var oddAction = queryForm.attr("action");
	queryForm.attr("action",exportExcelAction);
	queryForm.get(0).submit();
	queryForm.attr("action",oddAction);
	
}


</script>

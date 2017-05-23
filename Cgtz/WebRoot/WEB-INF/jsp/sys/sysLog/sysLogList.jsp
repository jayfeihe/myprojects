<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%--
<base href="<%=basePath%>"/>
 --%>
<title>用户管理</title>
<link href="css/global.css" type="text/css" rel="stylesheet"/>
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


//打开页面
function artOpenPage(_title,_url) {
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
}


//导出
function sysLogExcel(){
	var exportExcelAction = "sys/log/excel.do";
	
	var queryForm = $("#queryCondition");
	
	var oddAction = queryForm.attr("action");
	queryForm.attr("action",exportExcelAction);
	queryForm.get(0).submit();
	queryForm.attr("action",oddAction);
	
}


</script>
</head>
	<body>
		
		<div class="content">
			<form name="queryList" id="queryList" method="post" action="${pm.url}">
<%--
 			<div id="control" style="padding:0px;height:22px;background: #FFFFFF url(img/boxbak.png) no-repeat right;">
 --%>
			<div id="control" class="control">
			<%--
				<a href="javascript:void(0);" onclick="javascript:showQueryContent();"><img src="img/square/but_up.png" width="16px" height="16px" class='dotimg' title="打开/关闭查询模板" /></a>&nbsp;
				<a href="javascript:void(0);" onclick="javascript:sysUserExcel();"><img src="img/square/but_excel.png" class='dotimg' title="导出EXCEL文件" /></a>&nbsp;
				<a href="javascript:void(0);" onclick="javascript:artOpenPage('添加用户','sys/user/add.do');"><img src="img/addItem.gif" class='dotimg' title="添加用户" /></a>&nbsp;
				<a href="javascript:void(0);" onclick="javascript:window.location.reload();"><img src="img/reloadme.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			 --%>
<%--				<a href="javascript:void(0);" onclick="javascript:showQueryContent('but_updown_id');"><img id="but_updown_id" src="img/square/but_up.png" class='dotimg' title="打开/关闭查询模板" /></a>&nbsp;--%>
<%--				<a href="javascript:void(0);" onclick="javascript:sysLogExcel();"><img src="img/square/but_excel.png" class='dotimg' title="导出EXCEL文件" /></a>&nbsp;--%>
				<a href="javascript:void(0);" onclick="javascript:window.location.reload();"><img src="img/square/but_renew.png"  class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col" width="30">序号</th>
					<th scope="col">登录ID</th>
					<th scope="col">姓名</th>
					<th scope="col">IP</th>
					<th scope="col">事件</th>
					<th scope="col">时间</th>
					<th scope="col">描述</th>
				</tr>
				
				<c:forEach items="${pm.data}" var="data" varStatus="status">
				<tr>
				<td style="text-align: center;">${status.index+pm.rowS+1}</td>
				<td>${data.loginId}</td>
				<td>${data.name}</td>
				<td>${data.ipAddress}</td>
				<td>${data.event}</td>
				<td><fmt:formatDate value="${data.happendDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${data.remark }</td>
				</tr>
				
				</c:forEach>
				
			</table>
			
			<div id="pageStyle">
			${pm.pageNavigation}
			</div>
			
			</form>

		</div>



	</body>
</html>


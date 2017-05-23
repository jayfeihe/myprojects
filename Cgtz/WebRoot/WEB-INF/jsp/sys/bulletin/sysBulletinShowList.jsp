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
<title>公告管理</title>
<link href="css/global.css" type="text/css" rel="stylesheet"/>
<script src="js/jquery.min.js" type="text/javascript"></script>
<%--
<script src="js/popw.js" type="text/javascript"></script>
 --%>
	
<style type="text/css">
<!--
body{
	background-color:#ffffff;
	margin:0px; padding:0px;
}
#pageStyle{
	text-align: center;
	margin-top: 4px;
}
#control{
	margin:0px;
	padding:0Px;
	text-align: right;
}

-->
</style>
<script language="javascript">

//数据
function downData(data_id){
	$.ajax({
 			url:"sys/bulletin/download.do",
 			//防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
 			data:encodeURI("id=" + data_id + "&timestamp=" + (new Date()).getTime()),
 			dataType:"html",
 			success:function(data){
 				
 			}
 		});
}

</script>
</head>
	<body>
		
		
		<div class="content">
			<form name="queryList" id="queryList" method="post" action="${pm.url}">
			
			<div id="control">
				<%--
				<a href="javascript:void(0);" onclick="javascript:window.location.reload();"><img src="img/reloadme.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
				 --%>
				
				<a href="javascript:void(0);" onclick="javascript:window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col" width="30">序号</th>
					<th scope="col">标题</th>
					<th scope="col">作者</th>
					<th scope="col" width="100">发布时间</th>
					<th scope="col" width="60">操作</th>
				</tr>
				
				<c:forEach items="${pm.data}" var="data" varStatus="status">
				<tr>
				<td style="text-align: center;">${status.index+pm.rowS+1}</td>
				<td>${data.title}</td>
				<td>${data.writer}</td>
				<td><fmt:formatDate value="${data.publishTime}" pattern="yyyy/MM/dd:HH:mm:ss"/> </td>
				<td>
				<a href="sys/bulletin/download.do?id=${data.id}">下载</a>
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
     $("#table1").find("tr").hover(
	     function(){
	        $(this).addClass("altrow");    //鼠标经过添加hover样式
	     },
	     function(){
	        $(this).removeClass("altrow");   //鼠标离开移除hover样式
	     }
     );

</script>

	</body>
</html>


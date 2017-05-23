<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="<%=basePath%>resources/js/jquery.min.js"></script>
</head>
<body>
	<form id="downForm" action="">
		<table width="1000px">
			<tr>
				<td>appIds:</td>
				<td colspan="6">
					<input type="text" name="appIds"  style="width: 1000px"/>
				</td>
			</tr>
			<tr>
				<td>类别:</td>
				<td>
					<input type="text" id="category" name="category"/>
				</td>
			</tr>
			<tr>
				<td>
					<input type="button" value="提取" onclick="extract();"/>
				</td>
				<!-- <td>
					<input type="button" value="下载" onclick="download();"/>
				</td> -->
			</tr>
		</table>
	</form>
	
	<script type="text/javascript">
		function extract() {
			var params = $('#downForm').serialize();
			$.ajax({
				type : 'post',
				url : "<%=basePath%>img/extract",
				data: params,
				success: function(data) {
					data = $.parseJSON(data);
					alert(data.message);
				},
				error: function() {
					alert("系统出错,请联系相关人员");
				}
			});
		}
		
		function download() {
			var category = $("#category").val();
			window.location = "<%=basePath%>img/download?category=" + category ;
		}
	</script>
</body>
</html>
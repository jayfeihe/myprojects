<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=basePath%>css/jsoneditor.css" rel="stylesheet"
	type="text/css">
<script src="<%=basePath%>js/jquery-3.1.1.min.js"></script>
<script src="<%=basePath%>js/jsoneditor.js"></script>

<style type="text/css">
#jsoneditor {
	width: 100%;
}
</style>

<title>adxPc数据</title>
</head>
<body>
	<div id="jsoneditor"></div>
	<div style="margin-top: 20px;">
		<jsp:include page="/WEB-INF/jsp/pager.jsp">
			<jsp:param value="${pager.total }" name="totalRecord" />
			<jsp:param value="list" name="url" />
		</jsp:include>
	</div>

	<script>
		var container = document.getElementById('jsoneditor');

		var options = {
			mode : 'tree',
			//modes : [ 'code', 'form', 'text', 'tree', 'view' ], // allowed modes
			search : false,
			history : false,
			name : 'adxPc数据',
			onEditable : function(node) {
				return false;
			},
			onError : function(err) {
				alert(err.toString());
			}
		};
		
		var editor = new JSONEditor(container, options);

		var json = JSON.parse(${jsonDatas });
		
		editor.set(json);
		
	</script>
</body>
</html>
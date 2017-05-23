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
<title>菜单管理</title>
<link href="css/global.css" type="text/css" rel="stylesheet" />
<link href="css/default/easyui.css" type="text/css" rel="stylesheet" />
<script src="js/artDialog/artDialog.js?skin=opera"></script>
<script src="js/artDialog/plugins/iframeTools.source.js"></script>
<script src="js/jquery.min.js" type="text/javascript"></script>
<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>

<style type="text/css">
<!--
img{
	border: 0px;
}
.parentName{
	font-weight: bold;
}
-->
</style>
</head>
<body>
	<div id="main">

		<div id="part1" class="part">
			<p class="title">
				<a href="javascript:void(0);">菜单查询</a>
			</p>

			<div class="content">

				<form id="queryForm" method="post" action="sys/menu/list.do"
					target="queryContent">
					<table>
						<tr>
							<td>名称:</td>
							<td><input id='name' name='name' type='text' class="txt" /></td>
							<td>上级菜单:</td>
							<td><select id="parentId" name="parentId"
								class="sysSelectCss">
									<option></option>
									<c:forEach items="${pmenus}" var="menu">
										<option value="${menu.id}">${menu.name}</option>
									</c:forEach>
							</select></td>
							<td>
							<input type="button" value="查询" class="btn" onclick="queryForm('queryForm')" /> 
							<input type="reset" value="重置" class="btn" /></td>
						</tr>
						<%--
					<tr>
					</tr>
					<td>是否分层:</td><td><input id="isAllDataChkQuery" name="isAllDataChk" checked=true value="isAllDataChk" type="checkbox" onclick="javascript:isAllData(this);" /></td>
					--%>

					</table>
				</form>
			</div>
			<div id="queryContent">
				<%--
		查询列表
		 --%>
			</div>
		</div>
	</div>
	<!-- <div id="loading" class="easyui-window" title=""
		data-options="border:false,modal:true,closed:true,draggable:false,resizable:false">
		<img src="img/loading.gif" alt="加载中..." />
	</div> -->

</body>

<script type="text/javascript">
	function queryForm(fromId) {
		var formAction = $('#' + fromId).attr("action");
		var targetDiv = $('#' + fromId).attr("target");
		var params = $('#' + fromId).serialize();
		//弹出异步加载 遮罩
		openMask();
		$.ajax({
			type : "POST",
			url : formAction,
			data : encodeURI(params + "&targetDiv=" + targetDiv),
			dataType : "html",
			success : function(data) {
				closeMask();
				$('#' + targetDiv).html(data);
			},
			error : function() {
				closeMask();
				$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
			}
		});
	}

	/* //打开Loading遮罩并修改样式
	function queryOpenLoading() {
		$('#loading').window('open');
		$("#loading").attr("class", "");
		$("div[class='panel window']").css("position", "absolute");
		$("div[class='panel window']").attr("class", "");
		$("div[class='window-shadow']").attr("class", "");
	} */

	$(document).ready(function() {
		queryForm('queryForm');
	});
</script>
</html>


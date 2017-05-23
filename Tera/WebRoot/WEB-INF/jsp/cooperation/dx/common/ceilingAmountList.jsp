<%@page import="com.tera.cooperation.dinxuan.model.CeilingAmountQBean"%>
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
<title>鼎轩上限金额查看</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post">
			<div id="control" class="control">
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">鼎轩融资总金额</th>
					<th scope="col">本周审批通过金额</th>
					<th scope="col">本周审批剩余金额</th>
					<th scope="col">本周签约通过金额</th>
					<th scope="col">本周签约剩余金额</th>
					
					<th scope="col">操作</th>
				</tr>

				<tr>
					<td>
						<fmt:formatNumber value="${bean.ceilingAmount}" type="currency"/>万元
					</td>
					<td>
						<fmt:formatNumber value="${bean.passAmountOfWeek}" type="currency"/>万元
					</td>
					<td>
						<fmt:formatNumber value="${bean.leaveAmountOfWeek}" type="currency"/>万元
					</td>
					<td>
						<fmt:formatNumber value="${bean.signAmountOfWeek}" type="currency"/>万元
					</td>
					<td>
						<fmt:formatNumber value="${bean.leaveSignAmountOfWeek}" type="currency"/>万元
					</td>
					
					
					<td>
						<a href="javascript:void(0);" onclick="javascript:updateData();">更改上限金额</a>&nbsp;
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>

<script language="javascript">
//更新
function updateData() {
	if($("body").find("#dialogDiv").length==0){
		 $('body').append($("<div id='dialogDiv' style='top:150px;'></div>"));
	}
	var _url = "cooperation/dx/common/update.do?"
				+"paramId=${bean.paramId }&ceilingAmount=${bean.ceilingAmount }"
				+"&passAmountOfWeek=${bean.passAmountOfWeek }&leaveAmountOfWeek=${bean.leaveAmountOfWeek }"
				+"&signAmountOfWeek=${bean.signAmountOfWeek }&leaveSignAmountOfWeek=${bean.leaveSignAmountOfWeek }";
		$('#dialogDiv').dialog({
			title : "设置上限金额",
			width : 400,
			closed : false,
			cache : false,
			resizable : true,
			href : encodeURI(_url),
			modal : true,
		});
	}

	//页面加载完动作
	$(document).ready(function() {

	});
</script>
</html>


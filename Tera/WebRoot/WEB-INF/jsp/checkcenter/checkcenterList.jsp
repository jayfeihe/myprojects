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
<title>资金流转查看页面</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="">
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">进账总额</th>
					<th scope="col">撮合占用金额</th>
					<th scope="col">可支配金额</th>
					<th scope="col">出借人资金锁定金额</th>
					<th scope="col">未撮合金额</th>
				</tr>
				<tr>
					<td style="text-align:center;"><fmt:formatNumber value="${checkcenter.totalReceipts}" type="currency"/>元</td>
					<td style="text-align:center;"><fmt:formatNumber value="${checkcenter.matchOccupyAmount}" type="currency"/>元</td>
					<td style="text-align:center;"><fmt:formatNumber value="${checkcenter.disposableAmount}" type="currency"/>元</td>
					<td style="text-align:center;"><fmt:formatNumber value="${checkcenter.lendLockAmount}" type="currency"/>元</td>
					<td style="text-align:center;"><fmt:formatNumber value="${checkcenter.disMatchAmount}" type="currency"/>元</td>
				</tr>

				<%--	竖版排列	--%>
				<%--				<tr>--%>
				<%--					<th scope="col">进账总额</th>--%>
				<%--					<td><fmt:formatNumber value="${checkcenter.totalReceipts}" type="currency"/>元</td>--%>
				<%--				</tr>--%>
			</table>
		</form>
	</div>
</body>

<script language="javascript">

</script>
</html>


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
		<title>营业部审批列表</title>
		<link href="css/global.css" type="text/css" rel="stylesheet" />
	</head>
	<body>
		<div class="content">
			<form name="queryList" id="queryList" method="post"
				action="${ pm.url}">
				<table id="table" class="datatable"
					summary="list of members in EE Studay">
					<tr>
						<th scope="col">
							序号
						</th>
						<th scope="col">
							申请号
						</th>
						<th scope="col">
							客户姓名
						</th>
						<th scope="col">
							证件号码
						</th>
						<th scope="col">
							客户经理
						</th>
						<th scope="col">
							申请时间
						</th>
						<th scope="col">
							金额
						</th>
						<th scope="col">
							产品
						</th>
						<th scope="col">
							期限
						</th>
						<th scope="col">
							状态
						</th>
						<th scope="col" width="50">
							操作
						</th>
					</tr>
					<c:forEach items="${ pm.data}" var="data" varStatus="status">
						<tr>
							<td style="text-align: center;">
								${ status.index+pm.rowS+1}
							</td>
							<td>
								${data.appId}
							</td>
							<td>
								${data.name}
							</td>
							<td>
								${data.idNo}
							</td>
							<td>
								${data.customerNo}
							</td>
							<td>
								${data.createTimeStr}
							</td>
							<td>
<%--							<fmt:parseNumber value="${data.amount}" var="fmtAmount"/>--%>
<%--							${fmtAmount}--%>
							<fmt:formatNumber value="${data.amount}" type="currency"/>
							</td>
							<td>
								${data.product}
							</td>
							<td>
								${data.period}
							</td>
							<td>
								<c:choose>
									<c:when test="${data.state=='1'}">正常</c:when>
									<c:when test="${data.state=='2'}">拒件</c:when>
									<c:otherwise>复核退回</c:otherwise>
								</c:choose>
								
							</td>
							<td>
								&nbsp;&nbsp;&nbsp;
								<a href="javascript:void(0);"
									onclick="javascript:queryData('${data.id}');">审核</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div id="pageStyle">
					${ pm.pageNavigation}
				</div>
			</form>
		</div>
	</body>
	<script language="javascript">

//查看
function queryData(data_id) {
	window.location = "<%=basePath%>" + "loan/departverify/read.do?id="
			+ data_id;

	return;
}
</script>
</html>

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
		<title>落地催分单列表</title>
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
							<input id="selectAll" type="checkbox"
								data-options="required:true" />
						</th>
						<th scope="col">
							序号
						</th>
						<th scope="col">
							合同编号
						</th>
						<th scope="col">
							放款平台
						</th>
						<th scope="col">
							营业部
						</th>
						<th scope="col">
							客户姓名
						</th>
						<th scope="col">
							性别
						</th>
						<th scope="col">
							月还款总额
						</th>
						<th scope="col">
							产品
						</th>
						<th scope="col">
							还款日
						</th>
						<th scope="col">
							账龄
						</th>
						<th scope="col">
							逾期天数
						</th>
						<th scope="col">
							催收人
						</th>
						<th scope="col">
							当前状态
						</th>

					</tr>

					<c:forEach items="${ pm.data}" var="data" varStatus="status">
						<tr>
							<th scope="col">
								<c:if
									test="${loginOrgId==data.orgId&&(data.state=='3'||data.state=='4')}">
									<input id="item" name="appIds" value="${data.contractNo}"
										type="checkbox" />
								</c:if>

							</th>
							<td style="text-align: center;">
								${ status.index+pm.rowS+1}
							</td>
							<td>
								${data.contractNo}
							</td>
							<td>
								${data.channelName}
							</td>
							<td>
								${data.orgName}
							</td>
							<td>
								${data.customerName}
							</td>
							<td>

								<c:choose>
									<c:when
										test="${fn:length(data.idNo)==15&& fn:substring(data.idNo,14,15)% 2 == 0}">女</c:when>
									<c:when
										test="${fn:length(data.idNo)==18&& fn:substring(data.idNo,16,17)% 2 == 0}">女</c:when>
									<c:otherwise>男</c:otherwise>
								</c:choose>

							</td>
							<td>
								${data.monthAmountAll}
							</td>

							<td>
								${data.product}
							</td>


							<td>
								${data.repaymentDateStr}
							</td>

							<td>
								${data.lateAge}
							</td>

							<td>
								${data.lateDays}
							</td>


							<td>
								${data.collectionUidName}
							</td>
							<td>
								<c:if test="${data.state=='3'}">落地催收待分配</c:if>
								<c:if test="${data.state=='4'}">落地催处理中</c:if>
								<c:if test="${data.state=='7'}">催收完成</c:if>
								<c:if test="${data.state=='8'}">欺诈申请</c:if>
								<c:if test="${data.state=='9'}">欺诈复核处理中</c:if>
								<c:if test="${data.state=='10'}">欺诈审批处理中</c:if>
								<c:if test="${data.state=='12'}">司法申请</c:if>
								<c:if test="${data.state=='13'}">司法复核处理中</c:if>
								<c:if test="${data.state=='14'}">司法审批处理中</c:if>
								<c:if test="${data.state=='16'}">外包待审核</c:if>
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

//页面加载完动作
$(document).ready(function() {
	//复选框全选和全不选
		$("#selectAll").click(function() {
			if ($(this).attr("checked") == "checked") {
				$("input[type='checkbox']").attr("checked", true);
			} else {
				$("input[type='checkbox']").attr("checked", false);
			}
		});

	});
</script>
</html>


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
<title>司法复核列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">合同编号</th>
					<th scope="col">营业部</th>
					<th scope="col">客户姓名</th>
					<th scope="col">身份证号</th>
					<th scope="col">合同额</th>
					<th scope="col">还账日</th>
					<th scope="col">账龄</th>
					<th scope="col">逾期天数</th>
					<th scope="col">催收人员</th>
					<c:if test="${state=='0'}">
						<th scope="col">状态</th>
						<th scope="col" style="width: 50px">操作</th>
					</c:if>
					<c:if test="${state=='1'}">
						<th scope="col">复核结论</th>
						<th scope="col" style="width: 50px">复核意见</th>
					</c:if>
				</tr>
				<c:forEach items="${pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.contractNo}</td><!-- 合同编号 -->
						<td>${data.orgName}</td><!-- 营业部 -->
						<td>${data.customerName}</td><!-- 客户姓名 -->
						<td>${data.idNo}</td><!-- 身份证号 -->
						<td><fmt:formatNumber value="${data.contractAmount/10000}" type="currency"/>万元</td> <!-- 合同额 -->
						<td>${data.repaymentDateStr}</td><!-- 还款日 -->
						<td>${data.lateAge}</td><!-- 账龄 -->
						<td>${data.lateDays}</td><!-- 逾期天数 -->
						<td>${data.applyName}</td><!-- 催收人 -->
						<c:if test="${state=='0'}">
						<td>
							<c:choose>
								<c:when test="${data.state=='2' && data.approvalResult !='2'}">司法复核</c:when>
								<c:when test="${data.state=='2' && data.approvalResult =='2'}">司法审批退回</c:when>
								<c:when test="${data.state=='3'}">司法审批</c:when>
								<c:when test="${data.state=='4'}">司法生效</c:when>
								<c:otherwise>未知</c:otherwise>
							</c:choose>	
						</td>
						<c:if test="${data.state=='2'}">
						<td>
							<a href="javascript:void(0);" onclick="javascript:updateData('collection/judicial/review/update.do?id=${data.id}');">复核</a>&nbsp;
						</td>
						</c:if>
						</c:if>
						<c:if test="${state=='1'}">
						<td>${data.logContent1}</td><!-- 复核结论 -->
						<td>${data.logContent2}</td><!-- 复核意见 -->
						</c:if>
					</tr>
				</c:forEach>
			</table>
			<div id="pageStyle">${ pm.pageNavigation}</div>
		</form>
	</div>
</body>

<script language="javascript">
function updateData(_url) {
	if($("body").find("#dialogDiv").length==0){
		 $('body').append($("<div id='dialogDiv' style='top:150px;'></div>"));
	}
	$('#dialogDiv').dialog({
		title: "司法复核操作",
	    width: 745,
	    closed: false,
	    cache: false,
	    resizable:true,
	    href: encodeURI(_url),
	    modal: true,
	});
}
//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


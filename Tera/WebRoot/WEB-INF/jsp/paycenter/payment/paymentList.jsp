<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
<%--				<a href="javascript:void(0);" onclick="add();"><img src="img/square/but_add.png" class='dotimg' title="添加" /></a>&nbsp;--%>
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col" width="200px">合同号</th>
					<th scope="col">科目</th>
					<th scope="col">还款日</th>
					<th scope="col">金额</th>
					<th scope="col">期数</th>
					<th scope="col" width="160">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.contractNo}</td>
						<td>${data.subject}</td>
						<td>${data.repaymentDateStr}</td>
						<td>
						<fmt:formatNumber value="${data.actualAmount}" type="currency"/>
						</td>
						<td>${data.periodNum}</td>
						<td>
						  <c:choose>
						     <c:when test="${data.subject=='收利息'||data.subject=='收本息'}">
						     	<c:if test="${data.isRepayment}">
						     		<a href="javascript:void(0);" onclick="javascript:updateData('${ data.id}','${customerType}');">立即付款</a>
						     	</c:if>
						     </c:when>
						     <c:when test="${data.subject=='出借资金'}">
						           <a href="javascript:void(0);" onclick="javascript:updateData('${ data.id}','${customerType}');">立即付款</a>
						     </c:when>
						  </c:choose>
						  &nbsp;
						</td>
					</tr>
				</c:forEach>
			</table>
	
			<div id="pageStyle">
			${ pm.pageNavigation}
			</div>
		</form>
	</div>
<script language="javascript">
//更新
function updateData(data_id,customerType) {
	window.location = "<%=basePath%>paycenter/payment/read.htm?id=" + data_id+"&&customerType="+customerType;
	return;
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
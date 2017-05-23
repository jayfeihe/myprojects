<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />
		</style>
	</head>
	<body>
		<c:if test="${! empty pm}">
		<div id="main">
			<div id="part1" class="part">
				
				<div class="content">
					
					<from>
						<table id="table" class="datatable" summary="list of members in EE Studay">
							<tr>
								<th scope="col">序号</th>
								<th scope="col">产品</th>
								<th scope="col">期限</th>
								<th scope="col">机构名称</th>
								<th scope="col">利率</th>
								<th scope="col">起点金额</th>
								<th scope="col">用途</th>
								<th scope="col">操作</th>
							</tr>
							<c:forEach items="${ pm.data}" var="data" varStatus="status">
								<tr>
									<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
									<td>${data.trustProname}</td>
									<td>${data.period}个月</td>
									<td>${data.company}</td>
									<td>${data.interestRate}</td>
									<td align="center">
										<input size="15px" type="text" value="${data.startAmount}"/>
									</td>
									<td>${data.useage} </td>
									<td>
										<a href="javascript:void(0);" onclick="javascript:deleteSelectedTrustPro('${data.id}');">删除</a>&nbsp;
									</td>
								</tr>
							</c:forEach>
						</table>
				
						<!--<div id="pageStyle">
							${ pm.pageNavigation}
						</div>
					--></from>
				
				</c:if>
	</body>

<script type="text/javascript">

function deleteSelectedTrustPro(selectedTrustProId){
	//数组删除所点击的复选框的value值
	arr_selectedTrustProIds.splice($.inArray(selectedTrustProId,arr_selectedTrustProIds),1); 
	selectedTrustProduct('selectedTrustProduct');
}

$(document).ready(function() {

});
</script> 
</html>
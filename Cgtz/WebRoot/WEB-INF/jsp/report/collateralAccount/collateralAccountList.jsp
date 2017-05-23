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
<title>抵押物台账统计列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
			<!-- <a href="javascript:void(0);" onclick="exportExcel('queryCollateralAccountForm');"><img src="img/square/export.png" class='dotimg' title="导出" /></a>&nbsp; -->
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>		
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
<th scope="col">项目类型</th>
<!-- <th scope="col">申请编号</th> -->				
<th scope="col">线下合同编号</th>
<th scope="col">分公司</th>
<th scope="col">借款人</th>
<th scope="col">出借人</th>
<th scope="col">借款金额(元)</th>
<th scope="col">出借时间</th>
<th scope="col">还款时间</th>
<th scope="col">年化利率</th>
<th scope="col">车牌号</th>
<th scope="col">房产证号</th>
<th scope="col">房产/车库/仓库地址</th>
<th scope="col">评估价(元)</th>
<th scope="col">押品状态</th>
<th scope="col">备注</th>

				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>
						  <c:choose>
							<c:when test="${data.product eq '01'}">车贷</c:when>
							<c:when test="${data.product eq '02'}">车商贷</c:when>
							<c:when test="${data.product eq '03'}">房贷</c:when>
							<c:when test="${data.product eq '04'}">红木贷</c:when>
							<c:when test="${data.product eq '05'}">海鲜贷</c:when>
							<c:when test="${data.product eq '99'}">其他</c:when>
                          </c:choose>
						</td>
	<%-- <td>
							<a href="asset/manage/allRead.do?loanId=${data.loanId}" target="_blank" style="text-decoration: underline;">${data.loanId}</a> 
						</td>	 --%>				

<td>${data.contractId}</td>
<td>${data.orgName}</td>
<td>${data.loanName}</td>
<td>${data.lendName}</td>
<td><fmt:formatNumber value="${data.loanAmt}" type="currency"/></td>
<td>${data.startDateStr}</td>
<td>${data.endDateStr }</td>
<td>${data.rate}%</td>

<td>${data.license}</td>
<td>${data.housePropertyCode}</td>
<td>${data.assetAddr}</td>
<td><fmt:formatNumber value="${data.evalPrice}" type="currency"/></td>
<td>${data.colState }</td>
<td>${data.remark}</td>
		
					</tr>
				</c:forEach>
			</table>
	
			<div id="pageStyle">
			${ pm.pageNavigation}
			</div>
		</form>
	</div>
</body>
</html>


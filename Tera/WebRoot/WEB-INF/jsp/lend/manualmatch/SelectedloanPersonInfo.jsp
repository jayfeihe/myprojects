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
					
					<!--<form id="selectedLoanPersonForm" action="lend/manualmatch/save.do" method="post" >-->
					<!--<form id="selectedLoanPersonForm" action="lend/manualmatch/save.do" method="post" target="selectedLoanPersonContent">-->
					<form id="selectedLoanPersonForm" action="lend/manualmatch/save.do" method="post" >
					<input type="hidden" id="mylend2matchId" name="mylend2matchId" />
					<table id="table" class="datatable" summary="list of members in EE Studay">
							<tr>
									<th scope="col">序号</th>
									<th scope="col">借款申请号</th>
									<th scope="col">客户姓名</th>
									<th scope="col">用途</th>
									<th scope="col">产品</th>
									<th scope="col">金额</th>
									<th scope="col">服务费率</th>
									<th scope="col">利息率</th>
									<th scope="col">期限</th>
									<th scope="col">申请时间</th>
									<th scope="col">操作</th>
								</tr>
							<c:forEach items="${ pm.data}" var="data" varStatus="status">
								<tr>
									<input type="hidden" id="selectedLoan2MatchId" name="selectedLoan2MatchId" value="${data.id}"/>
									<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
									<td name="selectedLoanAppId">${data.loanAppId}</td>
									<td>${data.name}</td>
									<td>${data.useage}</td>
									<td>${data.loanProduct}</td>
									<td>
										<!--  <input id="tz_loanAmount" name="tz_loanAmount" size="5px" type="text" value="${data.loanAmount}"/> -->
										
										<fmt:parseNumber value="${data.loanAmount}" var="fmtAmount"/>
										<input id="tz_loanAmount" name="tz_loanAmount" size="5px" type="text" value="${fmtAmount}" />
									</td>
									<td>
										<input id="tz_loanServiceRate" name="tz_loanServiceRate" readonly="readonly" size="5px" type="text" value="${data.loanServiceRate}"/>
									</td>
									<td>
										<input id="tz_loanInterestRate" name="tz_loanInterestRate" readonly="readonly" size="5px" type="text" value="${data.loanInterestRate}"/>
									</td>
									<td>${data.loanPeriod}个月</td>
									<td>
										<fmt:formatDate value="${data.appTimeStr}" pattern="yyyy-MM-dd HH:mm:ss"/>
									</td>
									<td>
										<a href="javascript:void(0);" onclick="javascript:deleteSelectedLoanPerson('${data.id}');">删除</a>&nbsp;
									</td>
								</tr>
							</c:forEach>
						</table>
				
						<!--<div id="pageStyle">
							${ pm.pageNavigation}
						</div>
					-->
						<div>
							<!--<input type="button" value="提交" class="btn" onclick="selectedLoanPerson('selectedLoanPersonForm')"/> -->
							<input type="button" id="btn_submit" value="提交" class="btn" onclick="matchSelectedLoanPersonAndLend('selectedLoanPersonForm')"/>
							<input type="button" id="btn_back" value="返回" class="btn" onclick="javascript:back()"/>
						</div>
					</from>
				
				</c:if>
	</body>

<script type="text/javascript">

function deleteSelectedLoanPerson(selectedLoanPersonId){
	//数组删除所点击的复选框的value值
    arr_selectedLoan2MatchIds.splice($.inArray(selectedLoanPersonId,arr_selectedLoan2MatchIds),1); 
	$("table#table").find("input#selectInfo_"+selectedLoanPersonId).attr("checked",false);
    selectedLoanPerson('selectedLoanPerson');
}

$(document).ready(function() {

});
</script>
</html>
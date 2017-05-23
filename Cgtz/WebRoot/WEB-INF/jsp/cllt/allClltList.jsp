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
<title>催收分单</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content" id="clltlist">			
			<div id="control" class="control">
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			<form name="queryList" id="queryList" method="post" action="${ pm.url}">									    
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr><th scope="col"><input type="checkbox" id="SelectAll"  onclick="selectAll()"/></th>
					<th scope="col">序号</th>
					<th scope="col">催收人</th>
					<th scope="col">申请编号</th>
					<th scope="col">合同编号</th>
					<th scope="col">姓名/机构名称</th>
					<th scope="col">证件号码</th>
					<th scope="col">产品类型</th>
					<th scope="col">期数</th>
					<th scope="col">逾期还款日</th>
					<th scope="col">逾期天数</th>
					<th scope="col">借款金额</th>
					<th scope="col">合同开始时间</th>
					<th scope="col">合同结束时间</th>
					<th scope="col">是否续贷</th>
					<th scope="col" width="160">操作</th>
				</tr>
				
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr><th scope="col"><input type="checkbox" id="item" name="appIds" value="${data.contractId}" /></th>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.clltName}</td>
						<td><a href="cllt/allRead.do?loanId=${data.loanId}&contractId=${data.contractId}" target="_blank" style="text-decoration: underline;">${data.loanId}</a></td>
						<td>
						${data.contractId}
						</td>
						<td>${data.loanName}</td>
						<td>${data.idNo}</td>						
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
<%-- <td><a href="javascript:void(0);" onclick="addTab('【合同号-${data.contractId}】还款计划','<%=basePath%>contract/repayPlanList.do?contractId=${data.contractId}')" style="text-decoration: underline;">还款计划</a></td> --%>
<td>${data.lateNum }</td>
<td>${data.retDateStr}</td>
<td>${data.lateDays}</td>
<td><fmt:formatNumber value="${data.loanAmt}" type="currency"/>元</td>
<td>${data.startDateStr}</td>
<td>${data.endDateStr}</td>
<td>
<c:choose>
<c:when test="${data.isRenew eq '0'}">否</c:when>
<c:when test="${data.isRenew eq '1'}">是</c:when>
<c:when test="${data.isRenew eq '2'}">被续贷</c:when>
</c:choose>
</td>
						<td><a href="javascript:void(0);" onclick="readCllt('${ data.loanId}','${data.contractId}')">催收情况</a>&nbsp;
												    
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
//查看催收情况
function readCllt(loanId,contractId) {
	title="【合同号-"+contractId+"】催收日志";
    var url='<%=basePath%>' + 'cllt/clltlog/queryAll.do?loanId=' + loanId+'&contractId='+contractId;
	addTab(title,url);
	return;
}
//全选、取消全选的事件  
function selectAll(){  
    if ($("#SelectAll").attr("checked")) {  
        $("input[id='item']").attr("checked", true); 
    } else {
        $("input[id='item']").attr("checked", false); 
    }  
}
//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


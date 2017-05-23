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
<title>欺诈分单列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col"><input type="checkbox" id="SelectAll"  onclick="selectAll();"/></th>
					<th scope="col">序号</th>
					<th scope="col">合同编号</th>
					<th scope="col">营业部</th>				
					<th scope="col">客户姓名</th>
					<th scope="col">身份证号</th>
					<th scope="col">合同额</th>
					<th scope="col">还款日</th>
					<th scope="col">账龄</th>
					<th scope="col">逾期天数</th>
					<th scope="col">催收人员</th>
					<th scope="col">状态</th>
				</tr>

				<c:forEach items="${pm.data}" var="data" varStatus="status">
					<tr>
						<th scope="col">
						<c:if test="${data.state!='4'&&data.state!='3'}">
							<input type="checkbox" id="item" name="contractNos" value="${data.contractNo}" />
						</c:if>
						</th>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td><!-- 序号 -->
						<td>${data.contractNo}</td><!-- 合同编号 -->
						<td>${data.orgName}</td><!-- 营业部 -->
						<td>${data.customerName}</td> <!-- 客户姓名 -->
						<td>${data.idNo}</td> <!-- 身份证号 -->
						<td><fmt:formatNumber value="${data.contractAmount/10000}" type="currency"/>万元</td> <!-- 合同额 -->
						<td>${data.repaymentDateStr}</td><!-- 还款日 -->
						<td>${data.lateAge}</td><!-- 账龄 -->
						<td>${data.lateDays}</td><!-- 逾期天数 -->
						<td>${data.applyName}</td><!-- 催收人员 -->
						<td><c:choose>
								<c:when test="${data.state=='1'}">欺诈申请</c:when>
								<c:when test="${data.state=='2' && data.approvalResult !='2'}">欺诈复核</c:when>
								<c:when test="${data.state=='3'}">欺诈审批</c:when>
								<c:when test="${data.state=='4'}">欺诈生效</c:when>
								<c:when test="${data.state=='2' && data.approvalResult =='2'}">欺诈审批退回</c:when>
								<c:otherwise>未知</c:otherwise>
							</c:choose>	
						</td><!-- 状态 -->
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


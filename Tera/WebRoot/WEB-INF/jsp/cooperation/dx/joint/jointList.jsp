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
<title>合作方订单列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">申请编号</th>
					<th scope="col">客户姓名</th>
					<th scope="col">合同编号</th>
					<c:if test="${partnerCode == 'RY'}">
						<th scope="col">身份证号</th>
						<th scope="col">签约城市</th>
						<th scope="col">开户银行</th>
						<th scope="col">银行账号</th>
						<th scope="col">合同签订日期</th>
						<th scope="col">提交日期</th>
						<th scope="col">合同金额</th>
						<th scope="col">服务费</th>
						<th scope="col">月还款额</th>
					</c:if>
					<th scope="col">放款金额</th>
					<th scope="col">产品</th>
					<th scope="col">期限</th>
					<th scope="col">状态</th>
					<c:if test="${partnerCode == 'RY'}">
						<th scope="col">渠道状态</th>
					</c:if>
					<th scope="col">操作</th>
				</tr>

				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.appId}</td>
						<td>${data.name}</td>
						<td>${data.contractNo}</td>
						<c:if test="${partnerCode == 'RY'}">
							<td>${data.idNo}</td>
							<td>${data.signProvince}${data.signCity}</td>
							<td>${data.bankName}</td>
							<td>${data.bankAccount}</td>
							<td>${data.signDate}</td>
							<td>${data.lendingDate}</td>
							<td><fmt:formatNumber value="${data.loanAmount/10000}" type="currency"/>万元</td>
							<td><fmt:formatNumber value="${data.loanAmount-data.decisionAmount}" type="currency"/>元</td>
							<td>${data.monthAmount}</td>
						</c:if>
						<td>
							<fmt:formatNumber value="${data.decisionAmount/10000}" type="currency"/>万元							
						</td>
						<td>${data.decisionProduct}</td>
						<td>${data.decisionPeriod}个月</td>
						<td>
							<c:choose>
								<c:when test="${data.state=='0'}">前端拒贷</c:when>
								<c:when test="${data.state=='1'}">录入申请</c:when>
								<c:when test="${data.state=='2'}">审核退回</c:when>
								<c:when test="${data.state=='3'}">审核</c:when>
								<c:when test="${data.state=='4'}">审批退回</c:when>
								<c:when test="${data.state=='6'}">审批</c:when>
								<c:when test="${data.state=='7'}">特殊审批退回</c:when>
								<c:when test="${data.state=='10'}">特殊审批</c:when>
								<c:when test="${data.state=='13'}">签约</c:when>
								<c:when test="${data.state=='14'}">撮合已完成</c:when>
								<c:when test="${data.state=='15'}">复核退回</c:when>
								<c:when test="${data.state=='17'}">复核</c:when>
								<c:when test="${data.state=='18'}">放款退回</c:when>
								<c:when test="${data.state=='19'}">放款</c:when>
								<c:when test="${data.state=='20'}">已确认放款</c:when>
								<c:when test="${data.state=='21'}">放款成功</c:when>
								<c:when test="${data.state=='22'}">放款失败</c:when>
								<c:when test="${data.state=='23'}">放款完成</c:when>
								<c:when test="${data.state=='24'}">拒贷</c:when>
								<c:otherwise>未知</c:otherwise>
							</c:choose>
						</td>
						<c:if test="${partnerCode == 'RY'}">
							<td>
								<c:choose>
									<c:when test="${data.channelState=='1'}">准备中</c:when>
									<c:when test="${data.channelState=='2'}">准备完毕</c:when>
									<c:when test="${data.channelState=='3'}">投标中</c:when>
									<c:when test="${data.channelState=='4'}">投标结束</c:when>
									<c:when test="${data.channelState=='5'}">还款中</c:when>
									<c:when test="${data.channelState=='6'}">还款完成</c:when>
									<c:when test="${data.channelState=='7'}">中止</c:when>
									<c:when test="${data.channelState=='8'}">结束</c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</td>
						</c:if>
						<td>
							<a href="javascript:void(0);" onclick="javascript:downloadImage('${data.appId}');">下载附件</a>&nbsp;
							<c:if test="${partnerCode == 'RY' && data.channelState == '1'}">
								<a href="javascript:void(0);" onclick="javascript:updateData('pass','${data.appId}');">确认放款</a>&nbsp;
								<a href="javascript:void(0);" onclick="javascript:updateData('refuse','${data.appId}');">拒绝</a>
							</c:if>
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
//下载影像
function downloadImage(appId) {
	window.location = "<%=basePath%>" + "cooperation/dx/joint/downloadImage.do?appId=" + appId;
	return;
}

//确认放款与拒绝
function updateData(buttonType,appId) {
	var msg=(buttonType=="pass"?"您确认要放款吗？":"您确认要拒绝吗？");
	$.messager.prompt(msg, '请填写备注', function(r){
		if(buttonType=="refuse" && (r == undefined || r == '')){
			$.messager.alert('消息', "请填写备注");
		}else if(r.length > 500){
	    	$.messager.alert('消息', "退回原因最多500字！");
	    }else{
	    	openMask();
			$.ajax({
				url: "cooperation/dx/joint/save.do",
				data : encodeURI("buttonType="+buttonType+"&appId="+appId+"&timestamp="+(new Date()).getTime() + "&msg=" + r),
				success : function(data) {
					closeMask();
					if ("true"==data.success) {
						$.messager.alert('消息', data.message,"info", function(){
							submitForm("queryForm");
							return true;
		            	});
					} else {
						$.messager.alert('消息', data.message);
					}
				},
				error : function() {
					closeMask();
					$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
				}
			});
	    }
	});
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


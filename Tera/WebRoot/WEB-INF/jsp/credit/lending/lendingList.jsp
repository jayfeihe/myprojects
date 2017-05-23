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
<title>放款列表</title>
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
					<th scope="col">进件时间</th>
					<th scope="col">金额</th>
					<th scope="col">渠道</th>
					<th scope="col">产品</th>
					<th scope="col">期限</th>
					<th scope="col">营业部</th>
					<th scope="col">复核人</th>
<%--					<th scope="col">审批人</th>--%>
<%--					<th scope="col">营销人员</th>--%>
					<th scope="col">状态</th>
<%--					<th scope="col">操作</th>--%>
					<c:if test="${stateType=='waitTask'}">
						<th scope="col">操作</th>
					</c:if>
					<c:if test="${stateType=='inTask'||stateType=='offTask'}">
						<th scope="col">当前处理状态</th>
					</c:if>
				</tr>

				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td><a href="credit/review/read.do?id=${data.id}" target="_blank">${data.appId}</a></td>
						<td>${data.name}</td>
						<td>${data.contractNo}</td>
						<td>${data.inputTimeStr}</td>
						<td>
							<fmt:formatNumber value="${data.decisionAmount/10000}" type="currency"/>万元							
						</td>
						<td>${data.decisionChannelName}</td>
						<td>${data.decisionProduct}</td>
						<td>${data.decisionPeriod}个月</td>
<%--						<td>--%>
<%--							<c:choose>--%>
<%--	                        <c:when test="${data.period=='1'}">12个月</c:when>--%>
<%--	                        <c:when test="${data.period=='2'}">18个月</c:when>--%>
<%--	                        <c:when test="${data.period=='3'}">24个月</c:when>--%>
<%--	                        <c:when test="${data.period=='4'}">36个月</c:when>--%>
<%--	                        <c:otherwise>未知</c:otherwise>--%>
<%--	                        </c:choose>--%>
<%--						</td>--%>
						<td>${data.orgName}</td>
<%--						<td>${data.taskProcesser}</td>--%>
<%--						<td>${data.staffNo}</td>--%>
						<td>
							${data.reviewOperator }
						</td>
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
						<c:if test="${stateType=='waitTask'}">
						<td>
							<c:if test="${'19'==data.state}">
								<a href="javascript:void(0);" onclick="upLending('pass','${data.id}');">确认放款</a>&nbsp;
								<a href="javascript:void(0);" onclick="upLending('back','${data.id}');">退回复核</a>
							</c:if>
						</td>
						</c:if>
						<c:if test="${stateType=='inTask'||stateType=='offTask'}">
						<td>
							${data.taskState}
						</td>
						</c:if>
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
//确认放款
function upLending(buttonType,id) {
	var msg=(buttonType=="pass"?"您确认要放款吗？":"您确认要退回吗？");
	//确认放款
	if(buttonType == "pass"){
		$.messager.confirm('消息',msg, function(ok){
			if (ok){
				openMask();
				$.ajax({
					url: "credit/lending/save.do",
					data : encodeURI("buttonType="+buttonType+"&id="+id+"&timestamp="+(new Date()).getTime()),
					success : function(data) {
						closeMask();
						if ("true"==data.success) {
							$.messager.alert('消息', data.state,"info", function(){
								submitForm("queryForm");
								return true;
			            	});
						} else {
							$.messager.alert('消息', data.state);
						}
					},
					error : function() {
						$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
					}
				});
	        }
	    });
	}else{
		//退回放款
		$.messager.prompt('你确定要退回吗？', '请填写退回原因', function(r){
		    if(r == undefined || r == ''){
		    	$.messager.alert('消息', "请填写退回原因！");
		    }else if(r.length > 500){
		    	$.messager.alert('消息', "退回原因最多500字！");
		    }else{
				$.ajax({
					url: "credit/lending/save.do",
					data : encodeURI("buttonType="+buttonType+"&id="+id+"&timestamp="+(new Date()).getTime() + "&msg=" + r),
					success : function(data) {
						closeMask();
						if ("true"==data.success) {
							$.messager.alert('消息', data.state,"info", function(){
								submitForm("queryForm");
								return true;
			            	});
						} else {
							$.messager.alert('消息', data.state);
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
}



//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


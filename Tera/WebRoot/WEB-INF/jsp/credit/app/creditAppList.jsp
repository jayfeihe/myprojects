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
<title>信用贷款申请表列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
				<a href="javascript:void(0);" onclick="add();"><img src="img/square/but_add.png" class='dotimg' title="添加" /></a>&nbsp;
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
				<c:if test="${isHead}">
					<th scope="col"><input id="selectAll"  type="checkbox" data-options="required:true"/></th>
				</c:if>
					<th scope="col">序号</th>
					<th scope="col">申请编号</th>
					<th scope="col">姓名</th>
					<th scope="col">进件时间</th>
					<th scope="col">借款金额</th>
					<th scope="col">渠道</th>
					<th scope="col">产品</th>
					<th scope="col">期限</th>
					<c:if test="${isSaleRefuse}">
						<th scope="col">前端拒贷码1</th>
						<th scope="col">前端拒贷码2</th>
					</c:if>
					<th scope="col">状态</th>
					<th scope="col">所属机构</th>
					<th scope="col">营销人员</th>
					<th scope="col">营销团队</th>
					<th scope="col">团队经理</th>
					<c:if test="${stateType=='waitTask'}">
						<th scope="col">操作</th>
					</c:if>
					<c:if test="${stateType=='inTask'||stateType=='offTask'}">
						<th scope="col">当前处理状态</th>
					</c:if>
				</tr>

				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
					<c:if test="${isHead}">
						<th scope="col"><input id="item" name="ids" value="${data.id}" type="checkbox"/></th>
					</c:if>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td><a href="credit/app/read.do?id=${data.id}" target="_blank">${data.appId}</a></td>
						<td>${data.name}</td>
						<td>${data.inputTimeStr}</td>
						<td>
							<fmt:formatNumber value="${data.amount/10000}" type="currency"/>万元
						</td>
						<td>${data.belongChannelName}</td>
						<td>${data.product}</td>
						<td>${data.period}个月</td>
						<c:if test="${isSaleRefuse}">
							<td>${data.saleRefuseCode1}</td>
							<td>${data.saleRefuseCode2}</td>
						</c:if>
						<td>
							<c:choose>
								<c:when test="${data.state=='0'}">前端拒贷</c:when>
								<c:when test="${data.state=='1'}">录入申请</c:when>
								<c:when test="${data.state=='2'}">审核退回</c:when>
								<c:when test="${data.state=='3'}">审核</c:when>
								<c:when test="${data.state=='4'}">审核</c:when>
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
						<td>${data.orgName}</td>
						<td>${data.staffName}</td>
						<td>${data.staffTeam}</td>
						<td>${data.teamManager}</td>
						<c:if test="${stateType=='waitTask'}">
						<td>
						<c:if test="${data.taskProcesserId==login_user.loginId || login_user.isAdmin == 1}">
							<a href="javascript:void(0);" onclick="javascript:updateData('${data.id}');">更新</a>&nbsp;
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
//更新
function updateData(data_id) {
	window.location = "<%=basePath%>" + "credit/app/update.do?id=" + data_id;
	return;
}

//添加
function add(){
	window.location = "<%=basePath%>" + "credit/app/update.do";
	return;
}

//页面加载完动作
$(document).ready(function (){
		//复选框全选和全不选
	<c:if test="${isHead}">
		$("#selectAll").click(function (){
		 	if ($(this).attr("checked")=="checked") {  
				$("input[type='checkbox']").attr("checked", true); 
			}else{
				$("input[type='checkbox']").attr("checked", false);
			}
		});
	</c:if>
});
</script>
</html>


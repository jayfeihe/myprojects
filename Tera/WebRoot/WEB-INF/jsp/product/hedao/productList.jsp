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
<title>产品表列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
				<a href="javascript:void(0);" onclick="add();"><img src="img/square/sys_but_add.png" class='dotimg' title="添加" /></a>&nbsp;
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">产品名称</th>
					<th scope="col">产品类型</th>
					<th scope="col">期限</th>
					<th scope="col">总服务费率</th>
					<th scope="col">账户管理费率</th>
					<th scope="col">融资服务费率</th>
					<th scope="col">利率</th>
					<th scope="col">还款/付息方式</th>
					<th scope="col">罚息</th>
					<th scope="col">滞纳金</th>
					<th scope="col">提前5天赎回违约金比例</th>
					<th scope="col">提前30天赎回违约金比例</th>
					<th scope="col">提前60天赎回违约金比例</th>
					<th scope="col">提前90天赎回违约金比例</th>
					<th scope="col">备注</th>
					<th scope="col">所属渠道</th>
					<th scope="col">状态</th>
					<th scope="col" width="60">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.name}</td>
						<td>${data.typeName}</td>
						<td>${data.period}个月</td>
						<td>${data.sreviceFeeRate}%</td>
						<td>${data.sreviceFeeRate2}%</td>
						<td>${data.sreviceFeeRate3}%</td>
						<td>${data.interestRate}%</td>
						<td>${data.repayMethodName}</td>
						<td>${data.penaltyRate}%</td>
						<td>${data.delayRate}%</td>
						<td>${data.defaultRate1}%</td>
						<td>${data.defaultRate2}%</td>
						<td>${data.defaultRate3}%</td>
						<td>${data.defaultRate4}%</td>
						<td>${data.remark}</td>
						<td>${data.belongChannelName}</td>
						<td>
							<c:choose>
								<c:when test="${data.state=='1'}">正常</c:when>
								<c:when test="${data.state=='2'}">挂起</c:when>
								<c:when test="${data.state=='0'}">停用</c:when>
								<c:otherwise>其他</c:otherwise>
							</c:choose>
						</td>
						<c:if test="${data.state=='0'}">
							<td style="text-align: center;">
								已停用
							</td>
						</c:if>
						<c:if test="${data.state=='1'}">
							<td style="text-align: center;">
								<a href="javascript:void(0);" onclick="javascript:updateData('${ data.id}');">更新</a>&nbsp;
								<a href="javascript:void(0);" onclick="javascript:deleteData('${ data.id}');">删除</a>&nbsp;
								<a href="javascript:void(0);" onclick="javascript:upordown('${ data.id}');">点击挂起</a>
							</td>
						</c:if>
						<c:if test="${data.state=='2'}">
							<td style="text-align: center;">
								<a href="javascript:void(0);" onclick="javascript:updateData('${ data.id}');">更新</a>&nbsp;
								<a href="javascript:void(0);" onclick="javascript:deleteData('${ data.id}');">删除</a>&nbsp;
								<a href="javascript:void(0);" onclick="javascript:upordown('${ data.id}');">解除挂起</a>
							</td>
						</c:if>
<!-- 						<td> -->
<%-- 							<a href="javascript:void(0);" onclick="javascript:updateData('${ data.id}');">更新</a>&nbsp; --%>
<%-- 							<a href="javascript:void(0);" onclick="javascript:deleteData('${ data.id}');">删除</a>&nbsp; --%>
<!-- 						</td> -->
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
//挂起与解除
function upordown(data_id) {
	window.location = "<%=basePath%>product/hedao/upordown.do?id=" + data_id;
	//window.location.reload();
}

//更新
function updateData(data_id) {
	window.location = "<%=basePath%>" + "product/hedao/update.do?id=" + data_id;
	return;
}

//删除
function deleteData(data_id) {
	
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
				url : "<%=basePath%>" + "product/hedao/delete.do",
				// 防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
				data : encodeURI("id=" + data_id + "&timestamp=" + (new Date()).getTime()),
				async : false,// 同步提交
				success : function(data) {
					if ("true"==data.success) {
						// form刷新防提示
						
						$.messager.alert('消息', data.message,"info", function(){
			                   	window.location = window.location + "&timestamp=" + (new Date()).getTime();
								window.location.reload();
		            	});
					} else {
						
						$.messager.alert('消息', data.message);
					}
				},
				error : function() {
					
					$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
				}
			});
        }
    });
}


//添加
function add(){
	window.location = "<%=basePath%>" + "product/hedao/update.do";
	return;
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


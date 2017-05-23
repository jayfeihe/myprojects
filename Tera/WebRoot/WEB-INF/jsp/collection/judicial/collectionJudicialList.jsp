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
<title>司法客户列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">合同编号</th>
					<th scope="col">营业部</th>
					<th scope="col">客户姓名</th>
					<th scope="col">身份证号</th>
					<th scope="col">合同额</th>
					<th scope="col">还账日</th>
					<th scope="col">账龄</th>
					<th scope="col">逾期天数</th>
					<th scope="col">申请原因</th>
					<th scope="col">操作</th>
				</tr>
				<c:forEach items="${pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.contractNo}</td><!-- 合同编号 -->
						<td>${data.orgName}</td><!-- 营业部 -->
						<td>${data.customerName}</td><!-- 客户姓名 -->
						<td>${data.idNo}</td><!-- 身份证号 -->
						<td><fmt:formatNumber value="${data.contractAmount/10000}" type="currency"/>万元</td> <!-- 合同额 -->
						<td>${data.repaymentDateStr}</td><!-- 还款日 -->
						<td>${data.lateAge}</td><!-- 账龄 -->
						<td>${data.lateDays}</td><!-- 逾期天数 -->
						<td>${data.applyText}</td><!-- 申请原因 -->
						<td>
						<a href="javascript:void(0);" onclick="javascript:updateData('${data.id}');">司法解除</a>
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
function updateData(id) {
	$.messager.confirm('消息',"是否确认解除？", function(ok){
		if(ok){
			openMask();
			$.ajax({
				url: "<%=basePath%>collection/judicial/save.do",
				data : encodeURI("id="+id),
				success : function(data) {
					//关闭遮罩，弹出消息框
					closeMask();
					if ("true"==data.success) {
						$.messager.alert('消息', data.message,"info", function(){
							submitForm("queryForm");
							return true;
		            	});
					} else {
						$.messager.alert('消息', data.message);
						//按钮生效
						$(".btn").removeAttr("disabled");
					}
				},
				error : function() {
					//关闭遮罩，弹出消息框
					closeMask();
					$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
					//按钮生效
					$(".btn").removeAttr("disabled");
				}
			});
		}
	})

}
//页面加载完动作
$(document).ready(function() {
});

</script>
</html>


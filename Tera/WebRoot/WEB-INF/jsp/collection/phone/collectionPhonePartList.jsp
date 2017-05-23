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
<title>催收分单列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<!-- <div id="control" class="control">
				<a href="javascript:void(0);" onclick="add();"><img src="img/square/but_add.png" class='dotimg' title="添加" /></a>&nbsp;
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			 -->
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
				<td><input id="checkbutton" name="checkbutton" type="checkbox"   onclick="selectAll();" /></td>
					<th scope="col">序号</th>
					<th scope="col">合同编号</th>
					<th scope="col">放款平台</th>
					<th scope="col">营业部</th>
					<th scope="col">客户姓名</th>
					<th scope="col">性别</th>
					<th scope="col">合同额</th>
					<th scope="col">还款日</th>
					<th scope="col">账龄</th>
					<th scope="col">逾期天数</th>
					<th scope="col">催收人员</th>
					<th scope="col">催收组别</th>
					<th scope="col">当前状态</th>

				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<input id="listId" name="listId" type="hidden" value="${data.id}"/>
						<td><c:if test="${data.state=='1'||data.state=='2'}"><input name="checkresult" type="checkbox"/></c:if>
							<c:if test="${data.state!='1'&&data.state!='2'}"><input name="checkresult" type="checkbox" style="display: none"/></c:if>
						</td>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.contractNo}</td>
						<td>${data.channelName}</td>
						<td>${data.orgName}</td>
						<td>${data.customerName}</td>
						<td><c:choose>
							 	<c:when test="${fn:length(data.idNo)==15&& fn:substring(data.idNo,14,15)% 2 == 0}">女</c:when>
							 	<c:when test="${fn:length(data.idNo)==18&& fn:substring(data.idNo,16,17)% 2 == 0}">女</c:when>
								<c:otherwise>男</c:otherwise>  
							</c:choose></td>
						<td>${data.contractAmount}</td>
						<td>${data.repaymentDateStr}</td>
						<td>${data.lateAge}</td>
						<td>${data.lateDays}</td>
						<td>${data.name}</td>
						<td>${data.departName}</td>
						<td>
							<c:if test="${data.state=='1'}">待催收</c:if>
							<c:if test="${data.state=='2'}">电催处理中</c:if>
							<c:if test="${data.state=='3'}">落地催收待分配</c:if>
							<c:if test="${data.state=='4'}">落地催处理中</c:if>
							<c:if test="${data.state=='5'}">协催待分配</c:if>
							<c:if test="${data.state=='6'}">协催处理中</c:if>
							<c:if test="${data.state=='7'}">催收完成</c:if>
							<c:if test="${data.state=='8'}">欺诈申请</c:if>		
							<c:if test="${data.state=='9'}">欺诈复核处理中</c:if>
							<c:if test="${data.state=='10'}">欺诈审批处理中</c:if>
							<c:if test="${data.state=='11'}">欺诈认定生效</c:if>
							<c:if test="${data.state=='12'}">司法申请</c:if>
							<c:if test="${data.state=='13'}">司法复核处理中</c:if>
							<c:if test="${data.state=='14'}">司法审批处理中</c:if>
							<c:if test="${data.state=='15'}">司法认定生效</c:if>
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
//更新
function updateData(data_id) {
	window.location = "<%=basePath%>" + "collection/phone/update.do?id=" + data_id;
	return;
}

//删除
function deleteData(data_id) {
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
				url : "<%=basePath%>" + "collection/phone/delete.do",
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
					$.messager.confirm('消息', '数据加载失败,请联系系统管理员！');
				}
			});
        }
    });
}


//添加
function add(){
	window.location = "<%=basePath%>" + "collection/phone/update.do";
	return;
}

//页面加载完动作
$(document).ready(function (){
			
});
function selectAll(){
	var checkitems=$('[name=checkresult]');
	if($('#checkbutton').attr('checked')=='checked'){
		for(var i=0;i<checkitems.length;i++){
			checkitems.eq(i).attr("checked",true);
		}
	}else{
		for(var i=0;i<checkitems.length;i++){
			checkitems.eq(i).attr("checked",false);
		}
	}
	
}
</script>
</html>


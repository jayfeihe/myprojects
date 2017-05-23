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
<title>申请列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
				<c:if test="${opt ne 'read' }">
					<%-- <a href="javascript:void(0);" onclick="addCommon('${loanId}');"><img src="img/square/but_add.png" class='dotimg' title="添加" /></a>&nbsp; --%>
					<input type="button" value="添加共同借款人" class="btn" onclick="addCommon('${loanId}');"/>
					<a href="javascript:void(0);" onclick="refresh();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
				</c:if>
			</div>
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">共同借款人</th>
					<th scope="col">证件号码</th>
					<th scope="col">手机号</th>

					<th scope="col" width="160">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.name}</td>
						<td>${data.idNo}</td>
						<td>${data.tel}</td>
						<td>
							<c:choose>
								<c:when test="${opt eq 'read' }">
									<a href="javascript:void(0);" onclick="javascript:viewCommonData('${ data.id}','${data.name }','${loanId }');">查看</a>&nbsp;
								</c:when>
								<c:otherwise>
									<a href="javascript:void(0);" onclick="javascript:updateCommonData('${ data.id}','${data.name }','${loanId }');">更新</a>&nbsp;
									<a href="javascript:void(0);" onclick="javascript:deleteData('${ data.id}');">删除</a>&nbsp;
								</c:otherwise>
							</c:choose>
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
function updateCommonData(data_id,name,loan_id) {
	url = "<%=basePath%>loan/common/update.do?id=" + data_id + "&loanId=" + loan_id;
	addCommonTab(name,url);
	return;
}

// 查看
function viewCommonData(data_id,name,loan_id) {
	url = "<%=basePath%>loan/common/read.do?id=" + data_id + "&loanId=" + loan_id;
	addCommonTab(name,url);
	return;
}

//添加
function addCommon(loan_id){
	if(null == loan_id || '' == loan_id) {
		$.messager.alert("消息","请先保存申请","warnning");
		return;
	}
	var url = "<%=basePath%>loan/common/update.do?loanId=" + loan_id;
	addCommonTab("添加共同借款人",url);
	return;
}

// 删除
function deleteData(data_id) {
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
				url : "<%=basePath%>" + "loan/common/delete.do",
				// 防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
				data : encodeURI("id=" + data_id + "&timestamp=" + (new Date()).getTime()),
				async : false,// 同步提交
				success : function(data) {
					if ("true"==data.success) {
						// form刷新防提示
						$.messager.alert('消息', data.message,"info", function(){
							refresh();
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


function refresh() {
	refreshTab("appUpdateTabs");
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


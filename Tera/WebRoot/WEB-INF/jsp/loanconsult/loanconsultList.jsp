<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />
		<title>借款咨询列表</title>
		<link href="css/global.css" type="text/css" rel="stylesheet" />
	</head>
	<body>
		<div class="content">
			<form name="queryList" id="queryList" method="post"
				action="${ pm.url}">
				<div id="control" class="control">
					<a href="javascript:void(0);" onclick="add('person');"><img
							src="img/square/but_add.png" class='dotimg' title="添加个人" />
					</a>&nbsp;
					<a href="javascript:void(0);" onclick="add('group');"><img
							src="img/square/but_addd.png" class='dotimg' title="添加机构" />
					</a>&nbsp;
					<a href="javascript:void(0);" onclick="window.location.reload();"><img
							src="img/square/but_renew.png" class='dotimg' title="刷新" />
					</a>&nbsp;&nbsp;
				</div>

				<table id="table" class="datatable"
					summary="list of members in EE Studay">
					<tr>
						<th scope="col">
							序号
						</th>
						<th scope="col" style="display: none">
							ID
						</th>
						<th scope="col">
							客户姓名
						</th>
						<th scope="col">
							客户类型
						</th>
						<th scope="col">
							客户经理
						</th>
						<th scope="col">
							咨询时间
						</th>
						<th scope="col">
							金额
						</th>
						<th scope="col">
							期限
						</th>

						<th scope="col" width="160">
							操作
						</th>
					</tr>
					<c:forEach items="${ pm.data}" var="data" varStatus="status">
						<tr>
							<td style="text-align: center;">
								${ status.index+pm.rowS+1}
							</td>
							<td style="display: none">
								${data.id}
							</td>
							<td>
								${data.name}
							</td>
							<td>
								${data.type}
							</td>
							<td>
								${data.customerManager}
							</td>
							<td>
								${data.createTimeStr}
							</td>
							<td>
<%--								<fmt:parseNumber value="${data.amount}" var="fmtAmount"/>--%>
<%--								${fmtAmount}--%>
								<fmt:formatNumber value="${data.amount}" type="currency"/>
							</td>
							<td>
								${data.days}
							</td>
							<td style = "text-align: center;">
								<a href="javascript:void(0);"
									onclick="javascript:updateData('${ data.id}');">更新</a>&nbsp;
								<a href="javascript:void(0);"
									onclick="javascript:deleteData('${ data.id}');">删除</a>&nbsp;
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
	window.location = "<%=basePath%>" + "loanConsult/update.do?id=" + data_id +"&paramVal="+"param";
	
	return;
}

function deleteData(data_id) {
	
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
				url: "<%=basePath%>" + "loanConsult/delete.do",
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
function add(param) {
	window.location = "<%=basePath%>" + "loanConsult/update.do?paramVal="+param;
	
	return;
}
</script>
</html>


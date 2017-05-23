<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/sys/include.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <base href="<%=basePath%>"/><title>财富客户表列表</title>
    </head>
    <body>
        <div class="content">
            <form name="queryList" id="queryList" method="post" action="${ pm.url}">
                <div id="control" class="control">
                    <a href="javascript:void(0);" onclick="addpercustomer();"><img src="img/square/but_add.png" class='dotimg' title="添加个人客户" /></a>&nbsp;
                    <a href="javascript:void(0);" onclick="addorgcustomer();"><img src="img/square/but_addd.png" class='dotimg' title="添加机构客户" /></a>&nbsp;
					<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
                </div>
                <table id="table" class="datatable" summary="list of members in EE Studay">
                    <tr>
                        <th scope="col">序号</th>
                        <th scope="col">姓名/机构全称</th>
                        <th scope="col">客户类型</th>
                        <th scope="col">证件类型</th>
                        <th scope="col">证件号码</th>
<%--                        <th scope="col">所属机构</th>--%>
<%--						<th scope="col">客户经理</th>--%>
                        <th scope="col">状态</th>
                        <th scope="col">操作</th>
                    </tr>
                    <c:forEach items="${ pm.data}" var="data" varStatus="status">
                        <tr>
                            <td style="text-align: center;">${ status.index+pm.rowS+1}</td>
                            <td>${data.name}</td>
                            <td>${data.customerTypeName}</td>
                            <td>${data.idTypeName}</td>
                            <td>${data.idNo}</td>
<%--                            <td>${data.orgId}</td>--%>
<%--							<td>${data.customerManager}</td>--%>
                            <td>
                                <c:if test="${data.state =='1'}">有效</c:if>
								<c:if test="${data.state !='1'}">无效</c:if>
                            </td>
                            <td><a href="javascript:void(0);" onclick="javascript:updateData('${ data.id}','${data.customerType}');">更新</a>&nbsp;&nbsp;&nbsp;
                            	<a href="javascript:void(0);" onclick="javascript:deleteData('${ data.id}');">删除</a>&nbsp;</td>
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
function updateData(data_id,type){
    window.location = "<%=basePath%>" + "customer/update.do?id=" + data_id +"&customerType=" + type;
	return;
}

//删除
function deleteData(data_id) {
	
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
				url: "<%=basePath%>" + "customer/delete.do",
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

//添加个人客户
function addpercustomer(){
    window.location = "<%=basePath%>" + "customer/update.do?customerType=01";
    return;
}
//添加机构客户
function addorgcustomer(){
    window.location = "<%=basePath%>" + "customer/update.do?customerType=02";
    return;
}
</script>
</html>

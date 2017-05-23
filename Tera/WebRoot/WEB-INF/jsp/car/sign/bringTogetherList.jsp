<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<tr>
	<th scope="col">序号</th>
	<th scope="col">证件号码</th>
	<th scope="col">姓名</th>
	<th scope="col">金额</th>
	<!-- <th scope="col">撮合类型</th> -->
	<th scope="col">撮合时间</th>
</tr>

<c:forEach items="${ pm1.data}" var="data" varStatus="status">
	<tr>
		<td style="text-align: center;">${ status.index+pm1.rowS+1}</td>
		<td>${data.idNo}</td>
		<td>${data.name}</td>
		<td>
<%--			${data.lendAmount/10000}--%>
			<fmt:formatNumber value="${data.lendAmount}" type="currency"/>元	
		</td>
		<%-- <td>${data.lendType}</td> --%>
		<td>${data.matchTimeStr}</td>
	</tr>
</c:forEach>
<tr>
	<td colspan="5">
		<span style="float: right; padding-right: 20px;">
			<%-- <input type="button" value="下载合同" class="btn" onclick="downloadPDF()" <c:if test="${'0' eq contract.state}">hidden=true</c:if>/>&nbsp;&nbsp; --%>
			<c:if test="${'1' eq contract.state && state eq 'noDisplay'}"><a href="<%=basePath%>car/sign/download.do?appId=${appId}" target="_Blank">下载合同</a>&nbsp;&nbsp;</c:if>
		</span>
	</td>
</tr>

<script type="text/javascript">
//更新保存
function downloadPDF() {
	<%-- $.ajax({
		type : "POST",
		url : "<%=basePath%>" + "car/app/save.do",
		data : params+"&buttonType="+buttonType,
		success : function(data) {
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					// 保存的时候 当前页面刷新， 解决第一次保存APPID 与ID 为空的问题
					if(buttonType=="submit" || buttonType=="fail") {
						//跳转页面
						window.history.go(-1);
						//location.replace("<%=basePath%>car/app/update.do?id="+data.id);
					}else{
						//重新加载撮合列表
						
					}
            	});
            } else {				
    			$.messager.alert('消息', data.message);
            }
		},
		error : function() {
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
		}
	}); --%>
}
</script>
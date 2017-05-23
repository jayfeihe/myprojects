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
<title>对账列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
				<!-- <a href="javascript:void(0);" onclick="add();"><img src="img/square/but_add.png" class='dotimg' title="添加" /></a>&nbsp; -->
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">合同号</th>
					<th scope="col">收付</th>
					<th scope="col">账户</th>
					<th scope="col">科目</th>
					<th scope="col">实际金额</th>
					<th scope="col">期数</th>
					<th scope="col">日期</th>

					<th scope="col">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.contractNo}</td>
						<td>
							<c:choose>
								<c:when test="${data.inOut eq '1'}">收</c:when>
								<c:when test="${data.inOut eq '2'}">付</c:when>
							</c:choose>
						</td>
						<td>${data.account}</td>
						<td>${data.subject}</td>
						<td>
							<fmt:formatNumber value="${data.actualAmount}" type="currency"/> 元
						</td>
						<td>${data.periodNum}</td>
						<td>${data.createTimeStr}</td>

						<td>
							<a href="javascript:void(0);" onclick="javascript:showData('${ data.id}');">查看</a>&nbsp;
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
function showData(data_id) {
	if($("body").find("#dialogDiv").length==0){
		 $('body').append($("<div id='dialogDiv' style='top:120px;'></div>"));
	}
	
	var _url = "<%=basePath%>" + "accountting/update.do?id=" + data_id;
	
	$('#dialogDiv').dialog({
		title: '对账详情',
	    width: 520,
	    closed: false,
	    cache: false,
	    resizable:true,
	    href: encodeURI(_url),
	    modal: true,
	    onLoad: function(){
	    
	    }
	});
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


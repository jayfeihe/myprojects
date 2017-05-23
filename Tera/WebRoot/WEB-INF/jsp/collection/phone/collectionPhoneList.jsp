<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Date date =new Date();
 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date sixAfDate=new Date();sixAfDate.setHours(sixAfDate.getHours()-6);
Date dateAfDate=new Date();dateAfDate.setDate(dateAfDate.getDate()-1);
String nowTime =format.format(date);
String sixAfTime =format.format(sixAfDate);
String dateAfTime =format.format(dateAfDate);

request.setAttribute("nowTime",nowTime);
request.setAttribute("sixAfTime",sixAfTime);
request.setAttribute("dateAfTime",dateAfTime);


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>"/>
<title>电催列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<!-- <div id="control" class="control">
				<a href="javascript:void(0);" onclick="add();">'<img src="img/square/but_add.png" class='dotimg' title="添加" /></a>&nbsp;
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			 -->
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">合同编号</th>
					<th scope="col">客户姓名</th>
					<th scope="col">月还款总额</th>
					<th scope="col">产品</th>
					<th scope="col">账龄</th>
					<th scope="col" width="4%">逾期天数</th>
					<th scope="col">营业部</th>
					<th scope="col">接听状态</th>
					<th scope="col">电催摘要</th>
					<th scope="col">处理状态</th>
					<th scope="col" width="8%">跟进时间</th>
					<th scope="col" width="10%">预约状态</th>
					<th scope="col">状态</th>

					<th scope="col">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.contractNo}</td>
						<td>${data.customerName}</td>
						<td>${data.monthAmountAll}</td>
						<td>${data.product}</td>
						<td>${data.lateAge}</td>
						<td>${data.lateDays}</td>
						<td>${data.orgName}</td>
						<td><c:if test="${data.answerState=='Y'}">已接听</c:if><c:if test="${data.answerState=='N'}">未接听</c:if></td>
						<td><input id="phoneSummary${status.index }"  type="text" style="border-width:0;border:0;bobackground-color: white;width: 100%;" readonly="readonly" class="easyui-validatebox" value="${data.keyValue}"/></td>
						<td><c:if test="${data.handleState=='N'}">未处理</c:if><c:if test="${data.handleState=='Y'}">已处理</c:if></td>
						<td>${data.followTime}</td>
						<td><c:if test="${data.orderTimeStr>nowTime}" ><input style="color:black;font-size:small; border: 0;width: 100%" readonly="readonly" value="${data.orderTimeStr}"/></c:if>
							<c:if test="${data.orderTimeStr>=sixAfTime&&data.orderTimeStr<nowTime}" ><input style="color: yellow;font-size:small; border: 0" readonly="readonly" value="${data.orderTimeStr}"/></c:if>
							<c:if test="${data.orderTimeStr>=dateAfTime&&data.orderTimeStr<sixAfTime}" ><input style="color: red;font-size:small; border: 0" readonly="readonly" value="${data.orderTimeStr}"/></c:if>
							<c:if test="${data.orderTimeStr<dateAfTime}" ><input style="color: black;font-size:small; border: 0" readonly="readonly" value="${data.orderTimeStr}"/></c:if>
						</td> 
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

						<td>
							<a href="collectionBase/phone/read.do?id=${data.id}" onclick="">催收</a>&nbsp;
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
	/**$("[name=phoneSummary]").combobox({
		url:"sys/datadictionary/listjason.do?keyName=collectionsummary",
		valueField:'keyProp',    
   	 	textField:'keyValue',
		method:'get',
		required:false

	});**/
});
	

</script>
</html>


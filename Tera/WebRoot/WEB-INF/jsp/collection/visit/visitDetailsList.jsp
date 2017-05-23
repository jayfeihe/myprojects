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
<title>落地催列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					 
					<th scope="col">序号</th>
					<th scope="col">合同编号</th>
					<th scope="col">客户姓名</th>
					
					<th scope="col">月还款总额</th>
					<th scope="col">产品</th>
					<th scope="col">放款平台</th>
					
					<th scope="col">账龄</th>
					<th scope="col">逾期天数</th>
					<th scope="col">营业部</th>
					
					<th scope="col">接听状态</th>
					<th scope="col">落地催摘要</th> 
					<th scope="col">处理状态</th>
					
					<th scope="col">跟进时间</th>
					<th scope="col">预约时间</th> 
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
						 <td>${data.channelName}</td>
						 <td>${data.lateAge}</td>
						 <td>${data.lateDays}</td>
						 <td>${data.orgName}</td>
						 <td>
						 	<c:if test="${data.answerState=='Y'}">
						 		已接听
						 	</c:if>
						 	<c:if test="${data.answerState!='Y'}">
						 		未接听
						 	</c:if>
						 
						</td>
						 
						<td>
							 ${data.keyValue}
						</td>
						 
					    <td>
						 	<c:if test="${data.handleState=='Y'}">
						 		已处理
						 	</c:if>
						 	<c:if test="${data.handleState!='Y'}">
						 		未处理
						 	</c:if>
						</td>
						<td>${data.followTimeStr}</td>
						<td><c:if test="${data.orderTimeStr>nowTime}" ><input style="color:black;font-size:small; border: 0;width: 100%" readonly="readonly" value="${data.orderTimeStr}"/></c:if>
							<c:if test="${data.orderTimeStr>=sixAfTime&&data.orderTimeStr<nowTime}" ><input style="color: yellow;font-size:small; border: 0" readonly="readonly" value="${data.orderTimeStr}"/></c:if>
							<c:if test="${data.orderTimeStr>=dateAfTime&&data.orderTimeStr<sixAfTime}" ><input style="color: red;font-size:small; border: 0" readonly="readonly" value="${data.orderTimeStr}"/></c:if>
							<c:if test="${data.orderTimeStr<dateAfTime}" ><input style="color: black;font-size:small; border: 0" readonly="readonly" value="${data.orderTimeStr}"/></c:if>
						</td>
						<td>
							 
							<c:if test="${data.state=='3'}">落地催收待分配</c:if>
							<c:if test="${data.state=='4'}">落地催处理中</c:if>
						 
							<c:if test="${data.state=='7'}">催收完成</c:if>
							<c:if test="${data.state=='8'}">欺诈申请</c:if>
							<c:if test="${data.state=='9'}">欺诈复核处理中</c:if>		
							<c:if test="${data.state=='10'}">欺诈审批处理中</c:if>
							 
							<c:if test="${data.state=='12'}">司法申请</c:if>
							<c:if test="${data.state=='13'}">司法复核处理中</c:if>
							<c:if test="${data.state=='14'}">司法审批处理中</c:if>
							<c:if test="${data.state=='16'}">外包待审核</c:if> 
						</td>
						 <td>  
						 		<c:if test="${data.state>=4}">
								<a href="javascript:void(0);" onclick="javascript:updateCollection('${data.contractNo}');"><font color="blue">催收</font></a>&nbsp;
								</c:if>
								<c:if test="${data.state<4}">
									催收
								</c:if>
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
function updateCollection(data_id) {
 
	window.location = "<%=basePath%>" + "collection/visit/record/read.do?id="+data_id;
	return;
}

//页面加载完动作
$(document).ready(function (){
	$("[name=phoneSummary]").combobox({
		url:"sys/datadictionary/listjason.do?keyName=collectionsummary",
		valueField:'keyProp',    
   	 	textField:'keyValue',
		method:'get',
		required:false

	});
	
});
</script>
</html>


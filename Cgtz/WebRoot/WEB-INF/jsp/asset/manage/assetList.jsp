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
<title>资产管理列表</title>
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
<th scope="col">申请编号</th>
<th scope="col">合同编号</th>
<th scope="col">押品编号</th>
<th scope="col">所在仓库</th>
<th scope="col">类别</th>
<th scope="col">房产证号</th>
<th scope="col">车牌号</th>
<th scope="col">初始估值</th>
<th scope="col">最新金额</th>
<th scope="col">最近估值时间</th>
<th scope="col">资产检查</th>
<th scope="col">最近检查时间</th>
<th scope="col">状态</th>

					<th scope="col" width="160">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
	<td>
							<a href="asset/manage/allRead.do?loanId=${data.loanId}" target="_blank" style="text-decoration: underline;">${data.loanId}</a> 
						</td>					

<td>
${data.contractId}
</td>
<td>${data.id}</td>
<td>${data.assetAddr}</td>
<td>
<c:choose>
	<c:when test="${data.type eq '01'}">车</c:when>
	<c:when test="${data.type eq '02'}">车商</c:when>	
	<c:when test="${data.type eq '03'}">房</c:when>
	<c:when test="${data.type eq '04'}">红木</c:when>
	<c:when test="${data.type eq '05'}">海鲜</c:when>
	<c:when test="${data.type eq '06'}">其他</c:when>						
</c:choose>
</td>
<td>${data.housePropertyCode}</td>
<td>${data.license}</td>
<td><fmt:formatNumber value="${data.evalPrice}" type="currency"/>元</td>
<td>
	<c:choose>
	<c:when test="${data.isValueChange eq '0'}">无</c:when>
		<c:when test="${data.isValueChange eq '1'}">
			 <c:choose>
				<c:when test="${data.latestPrice gt data.evalPrice}"><span style="color:green;"><fmt:formatNumber value="${data.latestPrice}" type="currency"/>元</span></c:when>
				<c:when test="${data.latestPrice lt data.evalPrice}"><span style="color:red;"><fmt:formatNumber value="${data.latestPrice}" type="currency"/>元</span></c:when>
				<c:when test="${data.latestPrice eq data.evalPrice}"><fmt:formatNumber value="${data.latestPrice}" type="currency"/>元</c:when>
			</c:choose>
			
		</c:when>
	</c:choose>
		
</td>
<td>${data.changeTimeStr}</td>
<td>
<c:choose>
	<c:when test="${data.latestCheck eq '0'}">未检查</c:when>
	<c:when test="${data.latestCheck eq '1'}">正常</c:when>	
	<c:when test="${data.latestCheck eq '2'}">不正常</c:when>						
</c:choose>
</td>
<td>${data.checkTimeStr}</td>
<td>
<c:choose>
	<c:when test="${data.state eq '1'}">库存中</c:when>
	<c:when test="${data.state eq '2'}">正常出库</c:when>	
	<c:when test="${data.state eq '3'}">资产处置</c:when>					
</c:choose>
</td>
						<td>
							<a href="javascript:void(0);" onclick="assetCheck('${data.type}','${ data.id}','${data.isValueChange}','${data.state}');">检查</a>&nbsp;
							<a href="javascript:void(0);" onclick="valueChange('${data.type}','${ data.id}','${data.isValueChange}','${data.state}');">价值变动</a>&nbsp;
							<a href="javascript:void(0);" onclick="assetDeal('${data.type}','${ data.id}');">资产处置</a>&nbsp;
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
//查看申请

//弹出检查tab
function assetCheck(type,id,isValueChange,state){
   var title;
   var url='<%=basePath%>' + 'asset/check/query.do?collateralId='+id+'&isValueChange='+isValueChange+'&state='+state;

   if(type=="01"){
      title="车辆资产检查";
   }else if(type=="02"){
      title="车商资产检查";
   }else if(type=="03"){
      title="房产检查";
   }else if(type=="04"){
      title="红木资产检查";
   }else if(type=="05"){
      title="海鲜资产检查";
   }else{
      title="其他资产类别检查";
   }
   title="【资产编号-"+id+"】"+title;
   addTab(title,url);
}
//弹出价值变动tab
function valueChange(type,id,isValueChange,state){
    var title;
   var url='<%=basePath%>' + 'asset/change/query.do?collateralId='+id+'&isValueChange='+isValueChange+'&state='+state;
   if(type=="01"){
      title="车辆资产价值变动";
   }else if(type=="02"){
      title="车商资产价值变动";
   }else if(type=="03"){
      title="房产价值变动";
   }else if(type=="04"){
      title="红木资产价值变动";
   }else if(type=="05"){
      title="海鲜资产价值变动";
   }else{
      title="其他类别资产价值变动";
   }
   title="【资产编号-"+id+"】"+title;
   addTab(title,url);
}
//资产处置tab
//弹出检查tab
function assetDeal(type,id){
   var title;
   var url='<%=basePath%>' + 'asset/deal/update.do?collateralId='+id;
   if(type=="01"){
      title="车辆资产处置";
   }else if(type=="02"){
      title="车商资产处置";
   }else if(type=="03"){
      title="房产处置";
   }else if(type=="04"){
      title="红木资产处置";
   }else if(type=="05"){
      title="海鲜资产处置";
   }else{
      title="其他类别资产处置";
   }
   title="【资产编号-"+id+"】"+title;
   addTab(title,url);
}
//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


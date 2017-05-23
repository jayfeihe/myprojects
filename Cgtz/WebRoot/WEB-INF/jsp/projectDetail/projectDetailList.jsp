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
<title>项目明细表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
			  	<%-- <a href="javascript:void(0);" onclick="addTab('添加项目信息','<%=basePath%>projectInfoDetail/update.do')"><img src="img/square/sys_but_add.png" class='dotimg' title="添加仓库" /></a>&nbsp; --%>
			   <c:if test="${login_org.orgId eq '86'}">
			    <input type="button" value="添加项目信息" class="btn" onclick="addTab('添加项目信息','<%=basePath%>projectInfoDetail/update.do')"/>
			   </c:if>
			</div>		
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">债权线上编号</th>          
					<th scope="col">债权线下编号</th>          
					<th scope="col">项目名称</th>          
					<th scope="col">合同开始时间</th>  
					<th scope="col">合同结束时间</th>  
					<th scope="col">上线时间</th>    
					<th scope="col">合同实际结束时间</th>  
					<th scope="col">合同天数</th>            
					<th scope="col">借款金额</th>             
					<th scope="col">原始债权人</th>
					<th scope="col">债权人证件号码</th>          
					<th scope="col">借款人</th>
					<th scope="col">借款人证件号码</th>          
					<th scope="col">线下利率</th>          
					<th scope="col">线上利率</th>          
					<th scope="col">还款方式</th>          
					<th scope="col">贷款类别</th>          
					<th scope="col">所属分公司</th>          
					<th scope="col">财务说明</th>
					<th scope="col">分公司说明</th>
					<!-- <th scope="col">是否结束</th> -->
					<th scope="col">操作</th>  
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.projectId }</td>    
						<td>${data.contractNo }</td>      
						<td>${data.projectName }</td>    
						<td>${data.startDateStr }</td>      
						<td>${data.endDateStr }</td>        
						<td>${data.onlineDateStr }</td>
						<td>${data.realEndDateStr }</td>    
						<td>${data.days}</td>   
						<td><fmt:formatNumber value="${data.loanAmt }" type="currency"/>元</td>        
						<td>${data.lendName }</td>
						<td>${data.lendNo }</td>        
						<td>${data.loanName }</td>
						<td>${data.loanNo }</td>       
						<td>${data.loanRate }%</td>       
						<td>${data.onlineRate }%</td>     
						<td>${data.retWay }</td>
						<td>${data.type }</td>   
						<td>${data.orgName }</td>
						<td>${data.acctRemark }</td>
						<td>${data.branchRemark }</td>
						<%-- <td>
						<c:choose>
						<c:when test="${data.isEnd eq '0' }">否</c:when>
						<c:when test="${data.isEnd eq '1' }">是</c:when>
						<c:otherwise></c:otherwise>
						</c:choose>
						</td> --%>
						<td>
						<a href="javascript:void(0);" onclick="javascript:addTab('【编号-'+${data.id}+'】修改项目信息','<%=basePath%>projectInfoDetail/update.do?id= ${ data.id}')">修改</a>&nbsp;
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
</html>


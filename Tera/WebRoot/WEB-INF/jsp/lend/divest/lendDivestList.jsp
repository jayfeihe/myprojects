<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
		<div class="content">
			<form name="queryList" id="queryList" method="post"
				action="${ pm.url}">
				<table id="table" class="datatable"
					summary="list of members in EE Studay">
					<tr>
						<th scope="col">
							序号
						</th>
						<th scope="col">
							申请号
						</th>
						<th scope="col">
							类型
						</th>
						<th scope="col">
							姓名
						</th>
						<th scope="col">
							证件号码
						</th>
						<th scope="col">
							操作员
						</th>
						<th scope="col">
							出借金额
						</th>
						<th scope="col">
							出借产品
						</th>
						<th scope="col">
							服务截止日期
						</th>
						<th scope="col">
							所属机构
						</th>
						<th scope="col">
							操作
						</th>
					</tr>
					<c:forEach items="${ pm.data}" var="data" varStatus="status">
						<tr>
							<td style="text-align: center;">
								${ status.index+pm.rowS+1}
							</td>
							<td>
								${data.appId}
							</td>
							<td>
							<c:choose>
									<c:when test="${data.customerType=='01'}">个人</c:when>
									<c:when test="${data.customerType=='02'}">机构</c:when>
								</c:choose>
							
							</td>
							<td>
								${data.name}
							</td>
							<td>
								${data.idNo}
							</td>
							<td>
								${data.operator}
							</td>
							<td>
							<fmt:formatNumber value="${data.amount}" type="currency"/>
							</td>
							<td>
								${data.product}
							</td>
							<td>
								${data.serviceEndDateStr}
							</td>
							
							<td>
								${data.orgId}
							</td>
							<td>
								&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0);" onclick="javascript:openPage('撤资','lend/divest/showDivest.do?id=${data.id}');">撤资</a>&nbsp;
							</td>
						</tr>
					</c:forEach>
					
				</table>
				<div id="pageStyle">
					${ pm.pageNavigation}
				</div>
			</form>
		</div>
<script language="javascript">
//打开页面
function openPage(_title,_url) {
	if($("body").find("#dialogDiv").length==0){
		 $('body').append($("<div id='dialogDiv' style='top:150px;'></div>"));
	}
	$('#dialogDiv').dialog({
	    title: _title,
	    width: 400,
	    closed: false,
	    cache: false,
	    href: _url,
	    modal: true,
	    onLoad: function() {
		    	$("#divestDays").combobox("clear");
				$('#divestDays').combobox({ 
					valueField:'keyProp', 
					textField:'keyValue',
					data:[{"keyProp":"1","keyValue":"5"},{"keyProp":"2","keyValue":"30"},{"keyProp":"3","keyValue":"60"},{"keyProp":"4","keyValue":"90"}]
				});
	 	}
	});
}
</script>

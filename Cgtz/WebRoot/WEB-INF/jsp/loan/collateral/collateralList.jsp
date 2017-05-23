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
<title>质押、抵押物信息列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<c:if test="${opt ne 'read' }">
				<div id="control" class="control">
					<!-- <a href="javascript:void(0);" onclick="add();"><img src="img/square/but_add.png" class='dotimg' title="添加" /></a>&nbsp;
					<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp; -->
					<input type="button" value="添加车" class="btn" onclick="addCollateral('${loanId}','01','添加车');"/>
					<input type="button" value="添加车商" class="btn" onclick="addCollateral('${loanId}','02','添加车商');"/>
					<input type="button" value="添加房" class="btn" onclick="addCollateral('${loanId}','03','添加房');"/>
					<input type="button" value="添加红木" class="btn" onclick="addCollateral('${loanId}','04','添加红木');"/>
					<input type="button" value="添加海鲜" class="btn" onclick="addCollateral('${loanId}','05','添加海鲜');"/>
					<input type="button" value="添加其他" class="btn" onclick="addCollateral('${loanId}','99','添加其他');"/>
					<a href="javascript:void(0);" onclick="refresh();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>
				</div>
			</c:if>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">押品编号</th>
					<th scope="col">类型</th>
					<th scope="col">品种</th>
					<th scope="col">车牌号</th>
					<th scope="col">房产证号</th>
					<th scope="col">录入时间</th>
					<th scope="col">评估金额</th>
					<th scope="col">变动金额</th>
					<th scope="col">核价结果</th>
					<th scope="col">担保物权设定</th>
					<th scope="col">资产检查</th>
					
					
					<th scope="col">状态</th>
					<th scope="col" width="160">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.id }</td>
						<td>
							<c:choose>
								<c:when test="${data.type eq '01'}">车</c:when>
								<c:when test="${data.type eq '02'}">车商</c:when>
								<c:when test="${data.type eq '03'}">房</c:when>
								<c:when test="${data.type eq '04'}">红木</c:when>
								<c:when test="${data.type eq '05'}">海鲜</c:when>
								<c:when test="${data.type eq '99'}">其他</c:when>
							</c:choose>
						</td>
						<td>${data.var}</td>
						<td>${data.license}</td>
						<td>${data.housePropertyCode}</td>
						<td>${data.createTimeStr}</td>
						<td>
							<fmt:formatNumber value="${data.evalPrice}" type="currency"/>元
						</td>
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
					
						<td>
							<c:choose>
								<c:when test="${data.auditPriceState eq '0'}">未处理</c:when>
								<c:when test="${data.auditPriceState eq '1'}">相符</c:when>
								<c:when test="${data.auditPriceState eq '2'}">不相符</c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${data.isSet eq '0'}">否</c:when>
								<c:when test="${data.isSet eq '1'}">是</c:when>
							</c:choose>
						</td>
							<td>
							<c:choose>
								<c:when test="${data.latestCheck eq '0'}">未检查</c:when>
								<c:when test="${data.latestCheck eq '1'}">正常</c:when>
								<c:when test="${data.latestCheck eq '2'}">不正常</c:when>
							</c:choose>
							
							</td>
						
						<td>
							<c:choose>
								<c:when test="${data.state eq '1'}">库存中</c:when>
								<c:when test="${data.state eq '2'}">正常出库</c:when>
								<c:when test="${data.state eq '3'}">已处置</c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${opt eq 'read' or '1' eq data.isOrig}">
									<a href="javascript:void(0);" onclick="javascript:viewData('${ data.id}','${loanId }','${data.type }');">查看</a>&nbsp;
								</c:when>
								<c:otherwise>
									<c:if test="${'0' eq data.isOrig }">
										<a href="javascript:void(0);" onclick="javascript:updateData('${ data.id}','${loanId }','${data.type }');">更新</a>&nbsp;
										<a href="javascript:void(0);" onclick="javascript:deleteData('${ data.id}');">删除</a>&nbsp;
									</c:if>
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
function updateData(data_id,loan_id,collateral_type) {
	var url = "<%=basePath%>" + "loan/collateral/update.do?id=" + data_id +"&loanId="+loan_id+"&type="+collateral_type;
	var title = "";
	if("01" == collateral_type) {
		title = "车";
	}
	if("02" == collateral_type) {
		title = "车商";
	}
	if("03" == collateral_type) {
		title = "房";
	}
	if("04" == collateral_type) {
		title = "红木";
	}
	if("05" == collateral_type) {
		title = "海鲜";
	}
	if("99" == collateral_type) {
		title = "其他";
	}
	addCollateralTab(title+"-"+data_id,url);
	return;
}

//查看
function viewData(data_id,loan_id,collateral_type) {
	var url = "<%=basePath%>" + "loan/collateral/read.do?id=" + data_id +"&loanId="+loan_id+"&type="+collateral_type;;
	var title = "";
	if("01" == collateral_type) {
		title = "车";
	}
	if("02" == collateral_type) {
		title = "车商";
	}
	if("03" == collateral_type) {
		title = "房";
	}
	if("04" == collateral_type) {
		title = "红木";
	}
	if("05" == collateral_type) {
		title = "海鲜";
	}
	if("99" == collateral_type) {
		title = "其他";
	}
	addCollateralTab(title+"-"+data_id,url);
	return;
}

//删除
function deleteData(data_id) {
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
				url : "<%=basePath%>" + "loan/collateral/delete.do",
				// 防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
				data : encodeURI("id=" + data_id + "&timestamp=" + (new Date()).getTime()),
				async : false,// 同步提交
				success : function(data) {
					if ("true"==data.success) {
						// form刷新防提示
						$.messager.alert('消息', data.message,"info", function(){
							refreshTab("appUpdateTabs");
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
function addCollateral(loan_id,collateral_type,title){
	if(null == loan_id || '' == loan_id) {
		$.messager.alert("消息","请先保存申请","warnning");
		return;
	}
	var url = "<%=basePath%>loan/collateral/update.do?loanId="+loan_id+"&type="+collateral_type;
	addCollateralTab(title,url)
	return;
}

function refresh() {
	refreshTab('appUpdateTabs');
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


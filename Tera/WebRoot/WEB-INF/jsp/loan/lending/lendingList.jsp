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
		<title>会计明细账列表</title>
		<link href="css/global.css" type="text/css" rel="stylesheet" />
	</head>
	<body>
		<div class="content">
			<form name="queryList" id="queryList" method="post"
				action="${ pm.url}">
				<div id="control" class="control">
					<!--<a href="javascript:void(0);" onclick="add();"><img
							src="img/square/but_add.png" class='dotimg' title="添加" />
					</a>&nbsp;
					--><a href="javascript:void(0);" onclick="window.location.reload();"><img
							src="img/square/but_renew.png" class='dotimg' title="刷新" />
					</a>&nbsp;&nbsp;
				</div>

				<table id="table" class="datatable"
					summary="list of members in EE Studay">
					<tr>
						<th scope="col">
							序号
						</th>
						<th scope="col">
							合同号
						</th>
						<th scope="col">
							客户姓名
						</th>
						<th scope="col">
							证件号码
						</th>
						<th scope="col">
							签约时间
						</th>
						<th scope="col">
							合同金额
						</th>
						<th scope="col">
							放款金额
						</th>
						<th scope="col">
							产品
						</th>
						<th scope="col">
							期限
						</th>
						<th scope="col">
							状态
						</th>
						<th scope="col">
							原因
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
								${data.contractNo}
							</td>
							<td>
								${data.loanName}
							</td>
							<td>
								${data.loanIdNo}
							</td>
							<td>
								${data.signDateStr}
							</td>
							<td>
<%--								${data.loanAmount}--%>
								<fmt:formatNumber value="${data.loanAmount}" type="currency"/>
							</td>
							<td>
								<fmt:formatNumber value="${data.loanAmount*0.98}" type="currency"/>
							</td>
							<td>
								${data.loanProduct}
							</td>
							<td>
								${data.loanPeriod}
							</td>
							<td>
							<c:choose>
								<c:when test="${'1'==data.state}">
									待支付
								</c:when>
								<c:when test="${'2'==data.state}">
									已发盘
								</c:when>
								<c:when test="${'3'==data.state}">
									发盘失败
								</c:when>
								<c:when test="${'4'==data.state}">
									发盘成功
								</c:when>
								<c:when test="${'5'==data.state}">
									支付成功
								</c:when>
								<c:when test="${'6'==data.state}">
									支付失败
								</c:when>
								<c:when test="${'9'==data.state}">
									未确认
								</c:when>
								<c:when test="${'10'==data.state}">
									人工确认问题
								</c:when>
							</c:choose>
							</td>
							<td>
								${data.reason}
							</td>
							
							
							<td>
							<c:if test="${empty data.state}">
								<a href="javascript:void(0);"
									onclick="javascript:confirmLending('${ data.contractNo}');">
									
									确认放款
									
									</a>&nbsp;
								<a href="javascript:void(0);"
									onclick="javascript:returnCheck('${ data.contractNo}');">退回复核</a>&nbsp;
									<input name="subbpm" id="subbpm" type="hidden" value="" />
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
function updateData(data_id) {
	window.location = "<%=basePath%>" + "loan/lengding/update.do?id=" + data_id;
	
	return;
}
//确认放款
function confirmLending(contractno) {
	
	$.messager.confirm('消息', '您确认要放款吗？', function(ok){
	//点击确定做删除
		if (ok){
			$.ajax({
				url: "loan/lending/ConfirmLending.do",
				// 防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
				data : encodeURI("contractNo=" + contractno+ "&timestamp=" + (new Date()).getTime()),
				async : false,// 同步提交
				success : function(data) {
					if ("true"==data.success) {
						// form刷新防提示
						
						$.messager.alert('消息', data.message,"info", function(){
			                   	window.location = window.location + "&timestamp=" + (new Date()).getTime();
								window.location.reload();
								return true;
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
//保存
function returnCheck(contractno) {
	//弹出异步加载 遮罩
	openMask();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$('#subbpm').val('trueSubbpm');
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "loan/lending/ReturnCheck.do",
		data : encodeURI("contractNo=" + contractno+ "&subbpm=trueSubbpm&timestamp=" + (new Date()).getTime()),
		success : function(data) {
			if ("true"==data.success) {
				//关闭遮罩，弹出消息框
				closeMask();
				
				$.messager.alert('消息', data.message,"info", function(){
	                  	window.location=window.location + "&timestamp=" + (new Date()).getTime();
						window.location.reload();
						return true;
            	});
            } else {
            	closeMask();
				
				$.messager.alert('消息', data.message);
				//按钮生效
				$(".btn").removeAttr("disabled");
            }
		},
		error : function() {
			closeMask();
			
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
	});
}
//添加
function add() {
	window.location = "<%=basePath%>" + "loan/lengding/update.do";
	
	return;
}
</script>
</html>


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
<title>信用贷款人行报告信息概要列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
				<a href="javascript:void(0);" onclick="add();"><img src="img/square/but_add.png" class='dotimg' title="添加" /></a>&nbsp;
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">ID</th>
<th scope="col">申请ID</th>
<th scope="col">住房贷款笔数</th>
<th scope="col">其他贷款笔数</th>
<th scope="col">首笔贷款发放月份</th>
<th scope="col">贷记卡账户数</th>
<th scope="col">首张贷记卡发卡月份</th>
<th scope="col">准贷记卡账户数</th>
<th scope="col">首张准贷记卡发卡月份</th>
<th scope="col">本人声明数目</th>
<th scope="col">异议标注数目</th>
<th scope="col">贷款逾期笔数</th>
<th scope="col">贷款逾期月份数</th>
<th scope="col">贷款单月最高逾期总额</th>
<th scope="col">贷款最长逾期月数</th>
<th scope="col">贷记卡逾期账户数</th>
<th scope="col">贷记卡逾期月份数</th>
<th scope="col">贷记卡单月最高逾期总额</th>
<th scope="col">贷记卡最长逾期月数</th>
<th scope="col">准贷记卡逾期账户数</th>
<th scope="col">准贷记卡逾期月份数</th>
<th scope="col">准贷记卡单月最高透支总额</th>
<th scope="col">准贷记卡最长透支月数</th>
<th scope="col">未结清贷款法人机构数</th>
<th scope="col">未结清贷款机构数</th>
<th scope="col">未结清贷款笔数</th>
<th scope="col">未结清贷款合同总额</th>
<th scope="col">未结清贷款余额</th>
<th scope="col">未结清贷款6月均还款</th>
<th scope="col">未销户贷记卡法人机构数</th>
<th scope="col">未销户贷记卡机构数</th>
<th scope="col">未销户贷记卡账户数</th>
<th scope="col">未销户贷记卡授信总额</th>
<th scope="col">未销户贷记卡单家最高额度</th>
<th scope="col">未销户贷记卡单家最低额度</th>
<th scope="col">未销户贷记卡已用额度</th>
<th scope="col">未销户贷记卡6月均用额度</th>
<th scope="col">未销户准贷记卡法人机构数</th>
<th scope="col">未销户准贷记卡机构数</th>
<th scope="col">未销户准贷记卡账户数</th>
<th scope="col">未销户准贷记卡授信总额</th>
<th scope="col">未销户准贷记卡单家最高额度</th>
<th scope="col">未销户准贷记卡单家最低额度</th>
<th scope="col">未销户准贷记卡透支额度</th>
<th scope="col">未销户准贷记卡6月均透支额度</th>
<th scope="col">担保笔数</th>
<th scope="col">担保金额</th>
<th scope="col">担保本金余额</th>
<th scope="col">备注</th>
<th scope="col">状态</th>
<th scope="col">操作员</th>
<th scope="col">所属机构</th>
<th scope="col">创建日期</th>
<th scope="col">修改日期</th>

					<th scope="col" width="160">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.id}</td>
<td>${data.appId}</td>
<td>${data.houseLoanNum}</td>
<td>${data.otherLoanNum}</td>
<td>${data.firstLoanDate}</td>
<td>${data.creditNum}</td>
<td>${data.firstCreditDate}</td>
<td>${data.semiCreditNum}</td>
<td>${data.semiCreditDate}</td>
<td>${data.declareNum}</td>
<td>${data.objectionNum}</td>
<td>${data.loanDefaultNum}</td>
<td>${data.loanDefaultMonthNum}</td>
<td>${data.loanMaxDefaultAmount}</td>
<td>${data.loanMaxDefaultMonth}</td>
<td>${data.creditDefaultNum}</td>
<td>${data.creditDefaultMonthNum}</td>
<td>${data.creditMaxDefaultAmount}</td>
<td>${data.creditMaxDefaultMonth}</td>
<td>${data.semiCreditDefaultNum}</td>
<td>${data.semiCreditDefaultMonthNum}</td>
<td>${data.semiCreditMaxDefaultAmount}</td>
<td>${data.semiCreditMaxDefaultMonth}</td>
<td>${data.loanLegalNum}</td>
<td>${data.loanComNum}</td>
<td>${data.loanNum}</td>
<td>${data.loanAmount}</td>
<td>${data.loanRestAmount}</td>
<td>${data.loanAvg6mAmount}</td>
<td>${data.creditLegalNum}</td>
<td>${data.creditComNum}</td>
<td>${data.creditAccountNum}</td>
<td>${data.creditTotalAmount}</td>
<td>${data.creditMaxAmount}</td>
<td>${data.creditMinAmount}</td>
<td>${data.creditUseAmount}</td>
<td>${data.creditAvg6mAmount}</td>
<td>${data.semiCreditLegalNum}</td>
<td>${data.semiCreditComNum}</td>
<td>${data.semiCreditAccountNum}</td>
<td>${data.semiCreditTotalAmount}</td>
<td>${data.semiCreditMaxAmount}</td>
<td>${data.semiCreditMinAmount}</td>
<td>${data.semiCreditUseAmount}</td>
<td>${data.semiCreditAvg6mAmount}</td>
<td>${data.secureNum}</td>
<td>${data.secureAmount}</td>
<td>${data.secureRestAmount}</td>
<td>${data.remarks}</td>
<td>${data.state}</td>
<td>${data.operator}</td>
<td>${data.orgId}</td>
<td>${data.createTime}</td>
<td>${data.updateTime}</td>

						<td>
							<a href="javascript:void(0);" onclick="javascript:updateData('${ data.id}');">更新</a>&nbsp;
							<a href="javascript:void(0);" onclick="javascript:deleteData('${ data.id}');">删除</a>&nbsp;
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
	window.location = "<%=basePath%>" + "renhang/summary/update.do?id=" + data_id;
	return;
}

//删除
function deleteData(data_id) {
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
				url : "<%=basePath%>" + "renhang/summary/delete.do",
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


//添加
function add(){
	window.location = "<%=basePath%>" + "renhang/summary/update.do";
	return;
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


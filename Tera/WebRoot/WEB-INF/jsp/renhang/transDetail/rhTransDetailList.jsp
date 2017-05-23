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
<title>信用贷款人行报告交易明细列表</title>
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
<th scope="col">类型</th>
<th scope="col">发放时间</th>
<th scope="col">发放机构</th>
<th scope="col">发放金额</th>
<th scope="col">发放类型</th>
<th scope="col">业务号码</th>
<th scope="col">业务类型</th>
<th scope="col">期数</th>
<th scope="col">还款方式</th>
<th scope="col">到期日</th>
<th scope="col">截至日期</th>
<th scope="col">结清日期</th>
<th scope="col">交易状态</th>
<th scope="col">贷款五级分类</th>
<th scope="col">贷款本金余额</th>
<th scope="col">贷款剩余还款期数</th>
<th scope="col">贷款本月应还款</th>
<th scope="col">贷款应还款日期</th>
<th scope="col">贷款本月实还款</th>
<th scope="col">贷款最近一次还款日期</th>
<th scope="col">贷款当前逾期期数</th>
<th scope="col">贷款当前逾期金额</th>
<th scope="col">贷款逾期31-60未还本金</th>
<th scope="col">贷款逾期61-90未还本金</th>
<th scope="col">贷款逾期91-180未还本金</th>
<th scope="col">贷款逾期181以上未还本金</th>
<th scope="col">贷记卡共享额度</th>
<th scope="col">贷记卡已用额度</th>
<th scope="col">贷记卡6月均用额度</th>
<th scope="col">贷记卡最大使用额度</th>
<th scope="col">贷记卡本月还款</th>
<th scope="col">贷记卡账单日</th>
<th scope="col">贷记卡本月实还</th>
<th scope="col">贷记卡最近还款日</th>
<th scope="col">贷记卡当前逾期期数</th>
<th scope="col">贷记卡当前逾期金额</th>
<th scope="col">准贷记卡共享额度</th>
<th scope="col">准贷记卡透支余额</th>
<th scope="col">准贷记卡6月均透支额度</th>
<th scope="col">准贷记卡最大透支额度</th>
<th scope="col">准贷记卡账单日</th>
<th scope="col">准贷记卡本月实还款额</th>
<th scope="col">准贷记卡最近一次还款日</th>
<th scope="col">准贷记卡透支180天未付余额</th>
<th scope="col">担保贷款发放机构</th>
<th scope="col">担保贷款合同金额</th>
<th scope="col">担保贷款发放日期</th>
<th scope="col">担保贷款到期日期</th>
<th scope="col">担保金额</th>
<th scope="col">担保贷款本金余额</th>
<th scope="col">担保贷款五级分类</th>
<th scope="col">担保贷款结算日期</th>
<th scope="col">还款记录开始日期</th>
<th scope="col">还款记录结束日期</th>
<th scope="col">N1</th>
<th scope="col">N2</th>
<th scope="col">N3</th>
<th scope="col">N4</th>
<th scope="col">N5</th>
<th scope="col">N6</th>
<th scope="col">N7</th>
<th scope="col">N8</th>
<th scope="col">N9</th>
<th scope="col">N10</th>
<th scope="col">N11</th>
<th scope="col">N12</th>
<th scope="col">N13</th>
<th scope="col">N14</th>
<th scope="col">N15</th>
<th scope="col">N16</th>
<th scope="col">N17</th>
<th scope="col">N18</th>
<th scope="col">N19</th>
<th scope="col">N20</th>
<th scope="col">N21</th>
<th scope="col">N22</th>
<th scope="col">N23</th>
<th scope="col">N24</th>
<th scope="col">特殊交易类型</th>
<th scope="col">交易发生日期</th>
<th scope="col">变更月数</th>
<th scope="col">发生金额</th>
<th scope="col">明细记录</th>
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
<td>${data.type}</td>
<td>${data.startDate}</td>
<td>${data.company}</td>
<td>${data.amount}</td>
<td>${data.loanType}</td>
<td>${data.bizNo}</td>
<td>${data.bizType}</td>
<td>${data.period}</td>
<td>${data.payMethod}</td>
<td>${data.endDate}</td>
<td>${data.toDate}</td>
<td>${data.clearDate}</td>
<td>${data.transState}</td>
<td>${data.loanClass}</td>
<td>${data.loanRestAmount}</td>
<td>${data.loanRestPeriod}</td>
<td>${data.loanPayAmount}</td>
<td>${data.loanPayDate}</td>
<td>${data.loanPayReceived}</td>
<td>${data.loanLastPayDate}</td>
<td>${data.loanDefaultNum}</td>
<td>${data.loanDefaultAount}</td>
<td>${data.loanDefault12mAount}</td>
<td>${data.loanDefault23mAount}</td>
<td>${data.loanDefault36mAount}</td>
<td>${data.loanDefault6mAount}</td>
<td>${data.creditTotalAmount}</td>
<td>${data.creditUseAmount}</td>
<td>${data.creditAvg6mAmount}</td>
<td>${data.creditMaxAmount}</td>
<td>${data.creditPayAmount}</td>
<td>${data.creditBillDate}</td>
<td>${data.creditPayReceived}</td>
<td>${data.creditLastPayDate}</td>
<td>${data.creditDefaultNum}</td>
<td>${data.creditDefaultAount}</td>
<td>${data.semiCreditTotalAmount}</td>
<td>${data.semiCreditUseAmount}</td>
<td>${data.semiCreditAvg6mAmount}</td>
<td>${data.semiCreditMaxAmount}</td>
<td>${data.semiCreditBillDate}</td>
<td>${data.semiCreditPayReceived}</td>
<td>${data.semiCreditLastPayDate}</td>
<td>${data.semiCreditDefault6mAount}</td>
<td>${data.secureCompany}</td>
<td>${data.secureContractAmount}</td>
<td>${data.secureStartDate}</td>
<td>${data.secureEndDate}</td>
<td>${data.secureAmount}</td>
<td>${data.secureRestAmount}</td>
<td>${data.secureClass}</td>
<td>${data.secureBalanceDate}</td>
<td>${data.payStartDate}</td>
<td>${data.payEndDate}</td>
<td>${data.n1}</td>
<td>${data.n2}</td>
<td>${data.n3}</td>
<td>${data.n4}</td>
<td>${data.n5}</td>
<td>${data.n6}</td>
<td>${data.n7}</td>
<td>${data.n8}</td>
<td>${data.n9}</td>
<td>${data.n10}</td>
<td>${data.n11}</td>
<td>${data.n12}</td>
<td>${data.n13}</td>
<td>${data.n14}</td>
<td>${data.n15}</td>
<td>${data.n16}</td>
<td>${data.n17}</td>
<td>${data.n18}</td>
<td>${data.n19}</td>
<td>${data.n20}</td>
<td>${data.n21}</td>
<td>${data.n22}</td>
<td>${data.n23}</td>
<td>${data.n24}</td>
<td>${data.specialTransClass}</td>
<td>${data.transDate}</td>
<td>${data.changeMonth}</td>
<td>${data.transAmount}</td>
<td>${data.transDetail}</td>
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
	window.location = "<%=basePath%>" + "renhang/transDetail/update.do?id=" + data_id;
	return;
}

//删除
function deleteData(data_id) {
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
				url : "<%=basePath%>" + "renhang/transDetail/delete.do",
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
	window.location = "<%=basePath%>" + "renhang/transDetail/update.do";
	return;
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


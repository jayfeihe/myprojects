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
<title>信用贷款人行报告交易明细更新</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
	
<style type="text/css">
</style>
</head>
<body>
<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">更新</a></p>
		<div class="content">
			<form id="updateForm" >
				<table>
					<tbody>
					<input id="id" name="id" type="hidden" size="35" value="${ bean.id}" />
					<tr>
<td>申请ID:</td>
<td><input id="appId" name="appId" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.appId}"/></td>
</tr>
<tr>
<td>类型:</td>
<td><input id="type" name="type" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.type}"/></td>
</tr>
<tr>
<td>发放时间:</td>
<td><input id="startDate" name="startDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.startDateStr}"/></td>
</tr>
<tr>
<td>发放机构:</td>
<td><input id="company" name="company" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.company}"/></td>
</tr>
<tr>
<td>发放金额:</td>
<td><input id="amount" name="amount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.amount}"/></td>
</tr>
<tr>
<td>发放类型:</td>
<td><input id="loanType" name="loanType" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.loanType}"/></td>
</tr>
<tr>
<td>业务号码:</td>
<td><input id="bizNo" name="bizNo" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.bizNo}"/></td>
</tr>
<tr>
<td>业务类型:</td>
<td><input id="bizType" name="bizType" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.bizType}"/></td>
</tr>
<tr>
<td>期数:</td>
<td><input id="period" name="period" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.period}"/></td>
</tr>
<tr>
<td>还款方式:</td>
<td><input id="payMethod" name="payMethod" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.payMethod}"/></td>
</tr>
<tr>
<td>到期日:</td>
<td><input id="endDate" name="endDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.endDateStr}"/></td>
</tr>
<tr>
<td>截至日期:</td>
<td><input id="toDate" name="toDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.toDateStr}"/></td>
</tr>
<tr>
<td>结清日期:</td>
<td><input id="clearDate" name="clearDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.clearDateStr}"/></td>
</tr>
<tr>
<td>交易状态:</td>
<td><input id="transState" name="transState" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.transState}"/></td>
</tr>
<tr>
<td>贷款五级分类:</td>
<td><input id="loanClass" name="loanClass" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.loanClass}"/></td>
</tr>
<tr>
<td>贷款本金余额:</td>
<td><input id="loanRestAmount" name="loanRestAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.loanRestAmount}"/></td>
</tr>
<tr>
<td>贷款剩余还款期数:</td>
<td><input id="loanRestPeriod" name="loanRestPeriod" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.loanRestPeriod}"/></td>
</tr>
<tr>
<td>贷款本月应还款:</td>
<td><input id="loanPayAmount" name="loanPayAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.loanPayAmount}"/></td>
</tr>
<tr>
<td>贷款应还款日期:</td>
<td><input id="loanPayDate" name="loanPayDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.loanPayDateStr}"/></td>
</tr>
<tr>
<td>贷款本月实还款:</td>
<td><input id="loanPayReceived" name="loanPayReceived" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.loanPayReceived}"/></td>
</tr>
<tr>
<td>贷款最近一次还款日期:</td>
<td><input id="loanLastPayDate" name="loanLastPayDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.loanLastPayDateStr}"/></td>
</tr>
<tr>
<td>贷款当前逾期期数:</td>
<td><input id="loanDefaultNum" name="loanDefaultNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.loanDefaultNum}"/></td>
</tr>
<tr>
<td>贷款当前逾期金额:</td>
<td><input id="loanDefaultAount" name="loanDefaultAount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.loanDefaultAount}"/></td>
</tr>
<tr>
<td>贷款逾期31-60未还本金:</td>
<td><input id="loanDefault12mAount" name="loanDefault12mAount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.loanDefault12mAount}"/></td>
</tr>
<tr>
<td>贷款逾期61-90未还本金:</td>
<td><input id="loanDefault23mAount" name="loanDefault23mAount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.loanDefault23mAount}"/></td>
</tr>
<tr>
<td>贷款逾期91-180未还本金:</td>
<td><input id="loanDefault36mAount" name="loanDefault36mAount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.loanDefault36mAount}"/></td>
</tr>
<tr>
<td>贷款逾期181以上未还本金:</td>
<td><input id="loanDefault6mAount" name="loanDefault6mAount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.loanDefault6mAount}"/></td>
</tr>
<tr>
<td>贷记卡共享额度:</td>
<td><input id="creditTotalAmount" name="creditTotalAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.creditTotalAmount}"/></td>
</tr>
<tr>
<td>贷记卡已用额度:</td>
<td><input id="creditUseAmount" name="creditUseAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.creditUseAmount}"/></td>
</tr>
<tr>
<td>贷记卡6月均用额度:</td>
<td><input id="creditAvg6mAmount" name="creditAvg6mAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.creditAvg6mAmount}"/></td>
</tr>
<tr>
<td>贷记卡最大使用额度:</td>
<td><input id="creditMaxAmount" name="creditMaxAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.creditMaxAmount}"/></td>
</tr>
<tr>
<td>贷记卡本月还款:</td>
<td><input id="creditPayAmount" name="creditPayAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.creditPayAmount}"/></td>
</tr>
<tr>
<td>贷记卡账单日:</td>
<td><input id="creditBillDate" name="creditBillDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.creditBillDateStr}"/></td>
</tr>
<tr>
<td>贷记卡本月实还:</td>
<td><input id="creditPayReceived" name="creditPayReceived" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.creditPayReceived}"/></td>
</tr>
<tr>
<td>贷记卡最近还款日:</td>
<td><input id="creditLastPayDate" name="creditLastPayDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.creditLastPayDateStr}"/></td>
</tr>
<tr>
<td>贷记卡当前逾期期数:</td>
<td><input id="creditDefaultNum" name="creditDefaultNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.creditDefaultNum}"/></td>
</tr>
<tr>
<td>贷记卡当前逾期金额:</td>
<td><input id="creditDefaultAount" name="creditDefaultAount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.creditDefaultAount}"/></td>
</tr>
<tr>
<td>准贷记卡共享额度:</td>
<td><input id="semiCreditTotalAmount" name="semiCreditTotalAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.semiCreditTotalAmount}"/></td>
</tr>
<tr>
<td>准贷记卡透支余额:</td>
<td><input id="semiCreditUseAmount" name="semiCreditUseAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.semiCreditUseAmount}"/></td>
</tr>
<tr>
<td>准贷记卡6月均透支额度:</td>
<td><input id="semiCreditAvg6mAmount" name="semiCreditAvg6mAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.semiCreditAvg6mAmount}"/></td>
</tr>
<tr>
<td>准贷记卡最大透支额度:</td>
<td><input id="semiCreditMaxAmount" name="semiCreditMaxAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.semiCreditMaxAmount}"/></td>
</tr>
<tr>
<td>准贷记卡账单日:</td>
<td><input id="semiCreditBillDate" name="semiCreditBillDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.semiCreditBillDateStr}"/></td>
</tr>
<tr>
<td>准贷记卡本月实还款额:</td>
<td><input id="semiCreditPayReceived" name="semiCreditPayReceived" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.semiCreditPayReceived}"/></td>
</tr>
<tr>
<td>准贷记卡最近一次还款日:</td>
<td><input id="semiCreditLastPayDate" name="semiCreditLastPayDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.semiCreditLastPayDateStr}"/></td>
</tr>
<tr>
<td>准贷记卡透支180天未付余额:</td>
<td><input id="semiCreditDefault6mAount" name="semiCreditDefault6mAount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.semiCreditDefault6mAount}"/></td>
</tr>
<tr>
<td>担保贷款发放机构:</td>
<td><input id="secureCompany" name="secureCompany" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.secureCompany}"/></td>
</tr>
<tr>
<td>担保贷款合同金额:</td>
<td><input id="secureContractAmount" name="secureContractAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.secureContractAmount}"/></td>
</tr>
<tr>
<td>担保贷款发放日期:</td>
<td><input id="secureStartDate" name="secureStartDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.secureStartDateStr}"/></td>
</tr>
<tr>
<td>担保贷款到期日期:</td>
<td><input id="secureEndDate" name="secureEndDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.secureEndDateStr}"/></td>
</tr>
<tr>
<td>担保金额:</td>
<td><input id="secureAmount" name="secureAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.secureAmount}"/></td>
</tr>
<tr>
<td>担保贷款本金余额:</td>
<td><input id="secureRestAmount" name="secureRestAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.secureRestAmount}"/></td>
</tr>
<tr>
<td>担保贷款五级分类:</td>
<td><input id="secureClass" name="secureClass" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.secureClass}"/></td>
</tr>
<tr>
<td>担保贷款结算日期:</td>
<td><input id="secureBalanceDate" name="secureBalanceDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.secureBalanceDateStr}"/></td>
</tr>
<tr>
<td>还款记录开始日期:</td>
<td><input id="payStartDate" name="payStartDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.payStartDateStr}"/></td>
</tr>
<tr>
<td>还款记录结束日期:</td>
<td><input id="payEndDate" name="payEndDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.payEndDateStr}"/></td>
</tr>
<tr>
<td>N1:</td>
<td><input id="n1" name="n1" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n1}"/></td>
</tr>
<tr>
<td>N2:</td>
<td><input id="n2" name="n2" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n2}"/></td>
</tr>
<tr>
<td>N3:</td>
<td><input id="n3" name="n3" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n3}"/></td>
</tr>
<tr>
<td>N4:</td>
<td><input id="n4" name="n4" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n4}"/></td>
</tr>
<tr>
<td>N5:</td>
<td><input id="n5" name="n5" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n5}"/></td>
</tr>
<tr>
<td>N6:</td>
<td><input id="n6" name="n6" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n6}"/></td>
</tr>
<tr>
<td>N7:</td>
<td><input id="n7" name="n7" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n7}"/></td>
</tr>
<tr>
<td>N8:</td>
<td><input id="n8" name="n8" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n8}"/></td>
</tr>
<tr>
<td>N9:</td>
<td><input id="n9" name="n9" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n9}"/></td>
</tr>
<tr>
<td>N10:</td>
<td><input id="n10" name="n10" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n10}"/></td>
</tr>
<tr>
<td>N11:</td>
<td><input id="n11" name="n11" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n11}"/></td>
</tr>
<tr>
<td>N12:</td>
<td><input id="n12" name="n12" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n12}"/></td>
</tr>
<tr>
<td>N13:</td>
<td><input id="n13" name="n13" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n13}"/></td>
</tr>
<tr>
<td>N14:</td>
<td><input id="n14" name="n14" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n14}"/></td>
</tr>
<tr>
<td>N15:</td>
<td><input id="n15" name="n15" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n15}"/></td>
</tr>
<tr>
<td>N16:</td>
<td><input id="n16" name="n16" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n16}"/></td>
</tr>
<tr>
<td>N17:</td>
<td><input id="n17" name="n17" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n17}"/></td>
</tr>
<tr>
<td>N18:</td>
<td><input id="n18" name="n18" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n18}"/></td>
</tr>
<tr>
<td>N19:</td>
<td><input id="n19" name="n19" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n19}"/></td>
</tr>
<tr>
<td>N20:</td>
<td><input id="n20" name="n20" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n20}"/></td>
</tr>
<tr>
<td>N21:</td>
<td><input id="n21" name="n21" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n21}"/></td>
</tr>
<tr>
<td>N22:</td>
<td><input id="n22" name="n22" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n22}"/></td>
</tr>
<tr>
<td>N23:</td>
<td><input id="n23" name="n23" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n23}"/></td>
</tr>
<tr>
<td>N24:</td>
<td><input id="n24" name="n24" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.n24}"/></td>
</tr>
<tr>
<td>特殊交易类型:</td>
<td><input id="specialTransClass" name="specialTransClass" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.specialTransClass}"/></td>
</tr>
<tr>
<td>交易发生日期:</td>
<td><input id="transDate" name="transDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.transDateStr}"/></td>
</tr>
<tr>
<td>变更月数:</td>
<td><input id="changeMonth" name="changeMonth" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.changeMonth}"/></td>
</tr>
<tr>
<td>发生金额:</td>
<td><input id="transAmount" name="transAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.transAmount}"/></td>
</tr>
<tr>
<td>明细记录:</td>
<td><input id="transDetail" name="transDetail" type="text" data-options="validType:['length[0,200]']" class="textbox easyui-validatebox" value="${bean.transDetail}"/></td>
</tr>
<tr>
<td>备注:</td>
<td><input id="remarks" name="remarks" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.remarks}"/></td>
</tr>
<tr>
<td>状态:</td>
<td><input id="state" name="state" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.state}"/></td>
</tr>
<tr>
<td>操作员:</td>
<td><input id="operator" name="operator" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.operator}"/></td>
</tr>
<tr>
<td>所属机构:</td>
<td><input id="orgId" name="orgId" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.orgId}"/></td>
</tr>
<tr>
<td>创建日期:</td>
<td><input id="createTime" name="createTime" type="text" editable="false" class="textbox easyui-datetimebox" value="${bean.createTimeStr}"/></td>
</tr>
<tr>
<td>修改日期:</td>
<td><input id="updateTime" name="updateTime" type="text" editable="false" class="textbox easyui-datetimebox" value="${bean.updateTimeStr}"/></td>
</tr>

					<tr>
						<td>
							<input type="button" value="保存" class="btn" onclick="updateFunction()"/>
							<input type="button" value="返回" class="btn" onclick="javascript:back()"/>
						</td>
						<td></td>
					</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>

</body>
<script type="text/javascript">
//更新保存
function updateFunction() {
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	//验证表单验证是否通过
	if(false == $('#updateForm').form('validate') ){
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}
	//弹出异步加载 遮罩
	openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "renhang/transDetail/save.do",
		data : encodeURI(params),
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
//	                var url= "<%=basePath%>" + "renhang/transDetail/query.do";
//					window.location=url;
					window.history.go(-1);
            	});
            } else {				
    			$.messager.alert('消息', data.message);
				//按钮生效
				$(".btn").removeAttr("disabled");
            }
		},
		error : function() {
			//关闭遮罩，弹出消息框
			closeMask();
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
	});
}

//返回
function back(){
	window.history.go(-1);
}

//页面加载完动作
$(document).ready(function (){
	//填充select数据样例
	/*<%--
	var tsurl="sys/datadictionary/listjason.do?keyName=repaymethod";
	$("#repayMethod").combobox("clear");
	$('#repayMethod').combobox({url:tsurl, valueField:'keyProp', textField:'keyValue'});
	--%>*/
});

</script>
</html>


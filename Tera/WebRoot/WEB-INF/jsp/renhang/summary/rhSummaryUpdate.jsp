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
<title>信用贷款人行报告信息概要更新</title>
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
<td>住房贷款笔数:</td>
<td><input id="houseLoanNum" name="houseLoanNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.houseLoanNum}"/></td>
</tr>
<tr>
<td>其他贷款笔数:</td>
<td><input id="otherLoanNum" name="otherLoanNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.otherLoanNum}"/></td>
</tr>
<tr>
<td>首笔贷款发放月份:</td>
<td><input id="firstLoanDate" name="firstLoanDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.firstLoanDateStr}"/></td>
</tr>
<tr>
<td>贷记卡账户数:</td>
<td><input id="creditNum" name="creditNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.creditNum}"/></td>
</tr>
<tr>
<td>首张贷记卡发卡月份:</td>
<td><input id="firstCreditDate" name="firstCreditDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.firstCreditDateStr}"/></td>
</tr>
<tr>
<td>准贷记卡账户数:</td>
<td><input id="semiCreditNum" name="semiCreditNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.semiCreditNum}"/></td>
</tr>
<tr>
<td>首张准贷记卡发卡月份:</td>
<td><input id="semiCreditDate" name="semiCreditDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.semiCreditDateStr}"/></td>
</tr>
<tr>
<td>本人声明数目:</td>
<td><input id="declareNum" name="declareNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.declareNum}"/></td>
</tr>
<tr>
<td>异议标注数目:</td>
<td><input id="objectionNum" name="objectionNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.objectionNum}"/></td>
</tr>
<tr>
<td>贷款逾期笔数:</td>
<td><input id="loanDefaultNum" name="loanDefaultNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.loanDefaultNum}"/></td>
</tr>
<tr>
<td>贷款逾期月份数:</td>
<td><input id="loanDefaultMonthNum" name="loanDefaultMonthNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.loanDefaultMonthNum}"/></td>
</tr>
<tr>
<td>贷款单月最高逾期总额:</td>
<td><input id="loanMaxDefaultAmount" name="loanMaxDefaultAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.loanMaxDefaultAmount}"/></td>
</tr>
<tr>
<td>贷款最长逾期月数:</td>
<td><input id="loanMaxDefaultMonth" name="loanMaxDefaultMonth" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.loanMaxDefaultMonth}"/></td>
</tr>
<tr>
<td>贷记卡逾期账户数:</td>
<td><input id="creditDefaultNum" name="creditDefaultNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.creditDefaultNum}"/></td>
</tr>
<tr>
<td>贷记卡逾期月份数:</td>
<td><input id="creditDefaultMonthNum" name="creditDefaultMonthNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.creditDefaultMonthNum}"/></td>
</tr>
<tr>
<td>贷记卡单月最高逾期总额:</td>
<td><input id="creditMaxDefaultAmount" name="creditMaxDefaultAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.creditMaxDefaultAmount}"/></td>
</tr>
<tr>
<td>贷记卡最长逾期月数:</td>
<td><input id="creditMaxDefaultMonth" name="creditMaxDefaultMonth" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.creditMaxDefaultMonth}"/></td>
</tr>
<tr>
<td>准贷记卡逾期账户数:</td>
<td><input id="semiCreditDefaultNum" name="semiCreditDefaultNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.semiCreditDefaultNum}"/></td>
</tr>
<tr>
<td>准贷记卡逾期月份数:</td>
<td><input id="semiCreditDefaultMonthNum" name="semiCreditDefaultMonthNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.semiCreditDefaultMonthNum}"/></td>
</tr>
<tr>
<td>准贷记卡单月最高透支总额:</td>
<td><input id="semiCreditMaxDefaultAmount" name="semiCreditMaxDefaultAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.semiCreditMaxDefaultAmount}"/></td>
</tr>
<tr>
<td>准贷记卡最长透支月数:</td>
<td><input id="semiCreditMaxDefaultMonth" name="semiCreditMaxDefaultMonth" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.semiCreditMaxDefaultMonth}"/></td>
</tr>
<tr>
<td>未结清贷款法人机构数:</td>
<td><input id="loanLegalNum" name="loanLegalNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.loanLegalNum}"/></td>
</tr>
<tr>
<td>未结清贷款机构数:</td>
<td><input id="loanComNum" name="loanComNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.loanComNum}"/></td>
</tr>
<tr>
<td>未结清贷款笔数:</td>
<td><input id="loanNum" name="loanNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.loanNum}"/></td>
</tr>
<tr>
<td>未结清贷款合同总额:</td>
<td><input id="loanAmount" name="loanAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.loanAmount}"/></td>
</tr>
<tr>
<td>未结清贷款余额:</td>
<td><input id="loanRestAmount" name="loanRestAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.loanRestAmount}"/></td>
</tr>
<tr>
<td>未结清贷款6月均还款:</td>
<td><input id="loanAvg6mAmount" name="loanAvg6mAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.loanAvg6mAmount}"/></td>
</tr>
<tr>
<td>未销户贷记卡法人机构数:</td>
<td><input id="creditLegalNum" name="creditLegalNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.creditLegalNum}"/></td>
</tr>
<tr>
<td>未销户贷记卡机构数:</td>
<td><input id="creditComNum" name="creditComNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.creditComNum}"/></td>
</tr>
<tr>
<td>未销户贷记卡账户数:</td>
<td><input id="creditAccountNum" name="creditAccountNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.creditAccountNum}"/></td>
</tr>
<tr>
<td>未销户贷记卡授信总额:</td>
<td><input id="creditTotalAmount" name="creditTotalAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.creditTotalAmount}"/></td>
</tr>
<tr>
<td>未销户贷记卡单家最高额度:</td>
<td><input id="creditMaxAmount" name="creditMaxAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.creditMaxAmount}"/></td>
</tr>
<tr>
<td>未销户贷记卡单家最低额度:</td>
<td><input id="creditMinAmount" name="creditMinAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.creditMinAmount}"/></td>
</tr>
<tr>
<td>未销户贷记卡已用额度:</td>
<td><input id="creditUseAmount" name="creditUseAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.creditUseAmount}"/></td>
</tr>
<tr>
<td>未销户贷记卡6月均用额度:</td>
<td><input id="creditAvg6mAmount" name="creditAvg6mAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.creditAvg6mAmount}"/></td>
</tr>
<tr>
<td>未销户准贷记卡法人机构数:</td>
<td><input id="semiCreditLegalNum" name="semiCreditLegalNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.semiCreditLegalNum}"/></td>
</tr>
<tr>
<td>未销户准贷记卡机构数:</td>
<td><input id="semiCreditComNum" name="semiCreditComNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.semiCreditComNum}"/></td>
</tr>
<tr>
<td>未销户准贷记卡账户数:</td>
<td><input id="semiCreditAccountNum" name="semiCreditAccountNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.semiCreditAccountNum}"/></td>
</tr>
<tr>
<td>未销户准贷记卡授信总额:</td>
<td><input id="semiCreditTotalAmount" name="semiCreditTotalAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.semiCreditTotalAmount}"/></td>
</tr>
<tr>
<td>未销户准贷记卡单家最高额度:</td>
<td><input id="semiCreditMaxAmount" name="semiCreditMaxAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.semiCreditMaxAmount}"/></td>
</tr>
<tr>
<td>未销户准贷记卡单家最低额度:</td>
<td><input id="semiCreditMinAmount" name="semiCreditMinAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.semiCreditMinAmount}"/></td>
</tr>
<tr>
<td>未销户准贷记卡透支额度:</td>
<td><input id="semiCreditUseAmount" name="semiCreditUseAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.semiCreditUseAmount}"/></td>
</tr>
<tr>
<td>未销户准贷记卡6月均透支额度:</td>
<td><input id="semiCreditAvg6mAmount" name="semiCreditAvg6mAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.semiCreditAvg6mAmount}"/></td>
</tr>
<tr>
<td>担保笔数:</td>
<td><input id="secureNum" name="secureNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.secureNum}"/></td>
</tr>
<tr>
<td>担保金额:</td>
<td><input id="secureAmount" name="secureAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.secureAmount}"/></td>
</tr>
<tr>
<td>担保本金余额:</td>
<td><input id="secureRestAmount" name="secureRestAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.secureRestAmount}"/></td>
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
		url : "<%=basePath%>" + "renhang/summary/save.do",
		data : encodeURI(params),
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
//	                var url= "<%=basePath%>" + "renhang/summary/query.do";
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


<%@page import="com.tera.audit.common.constant.CommonConstant"%>
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
<title>贷后核帐</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
</head>
<body>
	<div class="easyui-tabs" id="afterLoanUpdateTabs" style="width: 100%;" data-options="fit:true">
		<div title="出纳贷后收款" style="width: 100%;padding:2px">
			<form id="afterLoanUpdateForm">
				<input type="hidden" name="id" value="${loanBase.id}"/>
				<input type="hidden" name="loanId" value="${loanBase.loanId}"/>
				<input type="hidden" name="contractId" value="${contract.contractId}"/>
				<input type="hidden" name="getLoanWay" value="${contract.getLoanWay}"/>
				<input type="hidden" name="lendUserId" value="${contract.lendUserId}"/>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>贷后核账</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>收款类型:</td>
						<td>
							<input id="subject" type="text" class="textbox easyui-combobox" name="subject" 
										data-options="required:true,editable:false,panelHeight:'auto',
													valueField:'keyProp',
													textField:'keyValue',
													data:dataDictJson.subject,
													onChange:function(nVal,oVal){
														if('2' == nVal) {
															$('.period').hide();
															$('.storFeeArea').hide();
															$('#feeText').text('本金金额:')
															$('#amt').numberbox('setValue','');
															$('#amt').numberbox({validate:true});
															$('#curPeriod').numberbox('setValue','');
															$('#curPeriod').numberbox({novalidate:true});
														} else {
															$('.period').show();
															$('.storFeeArea').show();
															$('#feeText').text('利息金额:');
															$('#amt').numberbox('setValue','');
															$('#amt').numberbox({validate:true});
															$('#curPeriod').numberbox({validate:true});
														}
													}" value="1"/>
						</td>
						<td>收款日期:</td>
						<td>
							<input id="payDate" name="payDate" type="text" class="textbox easyui-datetimebox" data-options="required:true,editable:false"/>
						</td>
						<td class="period">本次收款期数:</td>
						<td class="period">
							<input id="curPeriod" type="text" class="textbox easyui-numberbox" name="curPeriod" style="width: 90px"
										data-options="required:true,min:0,precision:0" value=""/>期(共${contract.num }期)
						</td>
					</tr>
					<tr>
						<td id="feeText">利息金额:</td>
						<td>
							<input id="amt" type="text" class="textbox easyui-numberbox" name="amt"
										data-options="required:true,min:0,precision:2" value=""/>元&nbsp;&nbsp;
						</td>
						<td>凭证号:</td>
						<td colspan="3">
							<input id="proof" type="text" class="textbox easyui-validatebox" name="proof" style="width: 200px;"
										data-options="required:true,validType:['length[0,100]']" value=""/><span style="color: red;margin-left: 10px">格式：账号+凭证号</span></td>
					</tr>
					<tr>
						<td>罚息金额:</td>
						<td>
							<input id="defaultFee" type="text" class="textbox easyui-numberbox" name="defaultFee"
										data-options="min:0,precision:2" value=""/>元&nbsp;&nbsp;
						</td>
						<td>滞纳金:</td>
						<td>
							<input id="penaltyFee" type="text" class="textbox easyui-numberbox" name="penaltyFee"
										data-options="min:0,precision:2" value=""/>元
						</td>
					</tr>
					<tr>
						<td>其他费用:</td>
						<td>
							<input id="otherFee" type="text" class="textbox easyui-numberbox" name="otherFee"
										data-options="min:0,precision:2" value=""/>元&nbsp;&nbsp;
						</td>
						<td class="storFeeArea">仓储费:</td>
						<td class="storFeeArea">
							<input id="storFee" type="text" class="textbox easyui-numberbox" name="storFee"
										data-options="min:0,precision:2" value=""/>元
						</td>
					</tr>
					<tr>
						<td>说明:</td>
						<td colspan="6">
							<textarea name="remark" class="textbox easyui-validatebox"
								data-options="required:true,validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;">${bean.remark }</textarea>
						</td>
					</tr>
					<tr>
						<td>
							<input type="button" value="收款" class="btn" onclick="updateAfterLoanFunction()"/>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div title="交易记录" data-options="href:'${basePath}account/afterloan/acctQuery.do?loanId=${loanBase.loanId }&contractId=${contract.contractId}'" style="width: 100%;padding:2px"></div>
		<c:if test="${loanBase.isRenew eq '1' }">
			<div title="续贷历史" data-options="href:'${basePath}loan/renew/readQuery.do?origLoanId=${loanBase.origLoanId}'" style="width: 100%;padding:10px"></div>
		</c:if>
		<div title="申请信息" data-options="href:'${basePath}loan/read.do?id=${loanBase.id}'" style="width: 100%;padding:10px"></div>
		<c:if test="${loanBase.isRenew eq '1' }">
			<div title="原申请信息" data-options="href:'${basePath}loan/origRead.do?id=${origLoanBase.id}'" style="width: 100%"></div>
		</c:if>
		<div title="申请影像资料" data-options="href:'${basePath}files/read2.do?loanId=${loanBase.loanId}&sec=${sec }&bizKey=${loanBase.loanId}'" style="padding:10px"></div>
		<c:if test="${isTgth eq '1' }">
			<div title="共同借款信息" data-options="href:'${basePath}loan/common/query.do?loanId=${origLoanBase.loanId}&opt=read'" style="width: 100%;padding:2px""></div>
		</c:if>
		<div title="质/抵押物" data-options="href:'${basePath}loan/collateral/query.do?loanId=${loanBase.loanId}&origLoanId=${origLoanBase.loanId }&opt=read'" style="width: 100%;padding:2px""></div>
		<div title="担保情况" data-options="href:'${basePath}loanguar/query.do?loanId=${loanBase.loanId}&origLoanId=${origLoanBase.loanId }&opt=read'" style="width: 100%;padding:2px""></div>
		<div title="诉讼情况" data-options="href:'${basePath}loan/law/query.do?loanId=${loanBase.loanId}&origLoanId=${origLoanBase.loanId }&opt=read'" style="width: 100%;padding:2px""></div>
		<div title="合同信息" data-options="href:'${basePath}contract/read.do?contractId=${contract.contractId}'" style="width: 100%;padding:2px""></div>
		<div title="还款计划" data-options="href:'${basePath}contract/repayPlanList.do?contractId=${contract.contractId}'" style="width: 100%;padding:2px""></div>
		<div title="流程报告" data-options="href:'${basePath}bpm/getBpmLogs.do?bizKey=${loanBase.loanId}'" style="width: 100%;padding:10px"></div>
	</div>

<script type="text/javascript">
//更新保存
function updateAfterLoanFunction() {
	//验证表单验证是否通过
	if(false == $('#afterLoanUpdateForm').form('validate')){
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}
	
	var sub = $("#subject").combobox('getValue');
	if ('1' == sub) {
		var num = $("#curPeriod").numberbox('getValue');
		var tNum = '${contract.num }';
		if(parseInt(num) > parseInt(tNum)) {
			$.messager.alert('消息', "期数不能大于总期数"+tNum+"！");
			return;
		}
	}
	
	//去掉 input 输入的 前后空格
	$('#afterLoanUpdateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	
	//弹出异步加载 遮罩
	openMask();
	
	var params = $('#afterLoanUpdateForm').serialize();
	
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "account/afterloan/save.do",
		data : params,
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					//window.parent.removeTab();
					var url = "<%=basePath%>" + "account/afterloan/update.do?id=" + data.id + "&contractId=" + data.contractId;
					location.replace(url);
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
			$.messager.confirm('消息', '数据加载失败,请联系系统管理员！');
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
	});
}

//页面加载完动作
$(document).ready(function (){
	
}); 
</script>

<script type="text/javascript" >

window.parent.openMask();
$(window).load(function (){
	window.parent.closeMask();
});

function refreshTab(tabs) {
	var currTab =  self.$('#'+tabs).tabs('getSelected'); //获得当前tab
    var url = $(currTab.panel('options').content).attr('src');
    var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
    self.$('#'+tabs).tabs('update', {
      tab : currTab,
      options : {
       content : content,
       //closable:true,  
       fit:true,  
       selected:true 
      }
     });
}
</script>
</body>
</html>


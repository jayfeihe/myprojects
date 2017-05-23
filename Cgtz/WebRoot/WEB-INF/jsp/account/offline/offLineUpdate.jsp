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
<title>线下放款</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
</head>
<body>
	<div class="easyui-tabs" id="offlineUpdateTabs" style="width: 100%;" data-options="fit:true">
		<div title="线下放款" style="width: 100%;padding:2px">
			<div style="margin-left: 10px;margin-top: 10px;">
				打印链接：
				<a href="${basePath }print/businessAuditPrint.do?loanId=${loanBase.loanId }" target="_blank"><strong>业务审批单打印</strong></a>
				&nbsp;&nbsp;&nbsp;
				<a href="${basePath }print/contractPrint.do?contractId=${contract.contractId }" target="_blank"><strong>用款申请单打印</strong></a>
			</div>
			<form id="offlineUpdateForm">
				<input type="hidden" name="loanId" value="${loanBase.loanId}"/>
				<input type="hidden" name="contractId" value="${contract.contractId}"/>
				<input type="hidden" name="amt" value="${loanBase.loanAmt}"/>
				<input type="hidden" name="getLoanWay" value="${contract.getLoanWay }"/>
				<input type="hidden" name="lendUserId" value="${contract.lendUserId }"/>
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>合同信息</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>借款人:</td>
						<td>
							<input type="text" class="textbox" value="${loanBase.name}" readonly="readonly"/>
						</td>
						<td>借款金额:</td>
						<td>
							<input type="text" class="textbox" value="<fmt:formatNumber value="${loanBase.loanAmt }" type="currency"/>" readonly="readonly"/>元
						</td>
					</tr>
					<tr>
						<td>开户名:</td>
						<td>
							<input id="acctName" name="loanBase.acctName" type="text" class="textbox easyui-validatebox" readonly="readonly"
								data-options="validType:['length[0,20]']" value="${loanBase.acctName}"/>
						</td>
						<td>账号:</td>
						<td><input id="acctCode" name="loanBase.acctCode" type="text" class="textbox" style="width:150px;" readonly="readonly"
								value="${loanBase.acctCode}"/>
						</td>
					</tr>
					<tr>
						<td>开户行:</td>
						<td colspan="6">
							<input id="acctPrvn" type="text" class="textbox easyui-combobox" readonly="readonly"
								data-options="editable:false" value="${loanBase.acctPrvn}"/>省
							<input id="acctCity" type="text" class="textbox easyui-combobox" readonly="readonly"
								data-options="editable:false" value="${loanBase.acctCity}"/>市
							<%-- <input id="acctCtry" type="text" class="textbox easyui-combobox" readonly="readonly"
								data-options="editable:false" value="${loanBase.acctCtry}"/>区/县 --%>
							<input id="acctBank" type="text" class="textbox easyui-combobox" readonly="readonly"
								data-options="editable:false" value="${loanBase.acctBank}"/>开户行
							<input id="acctBranch" type="text" class="textbox easyui-combobox" readonly="readonly"
								data-options="editable:true" value="${loanBase.acctBranch}" style="width: 250px;"/>支行
						</td>
					</tr>
					<tr>
						<td>放款金额:</td>
						<td>
							<input type="text" class="textbox" value="<fmt:formatNumber value="${loanBase.loanAmt }" type="currency"/>" readonly="readonly"/>元
						</td>
						<td>融资方式:</td>
						<td><input id="getLoanWay" type="text" value="${contract.getLoanWay }" readonly="readonly"
								data-options="textField:'keyValue',
											valueField:'keyProp',
											data:dataDictJson.getLoanWay" 
								class="textbox easyui-combobox" /></td>
						<c:if test="${contract.getLoanWay eq '2' or contract.getLoanWay eq '3' }">		
							<td>出借人:</td>
							<td><input id="lendUserId" type="text" class="textbox easyui-combobox" readonly="readonly"
								data-options="url:'lenduser/listLendUser.do',
												textField:'name',
												valueField:'id'" value="${contract.lendUserId }"/></td>
						</c:if>
					</tr>
				</table>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>放款决策</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>凭证号:</td>
						<td>
							<input id="proof" type="text" class="textbox easyui-validatebox" name="proof" style="width: 200px;"
										data-options="required:true,validType:['length[0,100]']" value=""/><span style="color: red;margin-left: 10px">格式：账号+凭证号</span></td>
					</tr>
					<tr>
						<td>说明:</td>
						<td colspan="6">
							<textarea name="remark" class="textbox easyui-validatebox" 
								data-options="required:true,validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;"></textarea>
						</td>
					</tr>
					<tr>
						<td>
							<input type="button" value="确认放款" class="btn" onclick="updateFunction()"/>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</form>
			<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>影像</strong></div><hr color="#D3D3D3"/>
			<jsp:include page="/files/load.do">
				<jsp:param value="${loanBase.loanId}" name="loId"/>
				<jsp:param value="filesce12" name="sec"/>
				<jsp:param value="${contract.contractId}" name="bizKey"/>
				<jsp:param value="0" name="opt"/>
			</jsp:include>
		</div>
		
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
		<div title="流程报告" data-options="href:'${basePath}bpm/getBpmLogs.do?bizKey=${loanBase.loanId}'" style="width: 100%;padding:10px"></div>
	</div>

<script type="text/javascript">
//更新保存
function updateFunction() {
	//验证表单验证是否通过
	if(false == $('#offlineUpdateForm').form('validate')){
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}
	
	//去掉 input 输入的 前后空格
	$('#offlineUpdateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	
	//弹出异步加载 遮罩
	openMask();
	
	var params = $('#offlineUpdateForm').serialize();
	
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "account/offline/save.do",
		data : params,
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					window.parent.removeTab();
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
	 initAcct();
}); 
</script>

<script type="text/javascript">
	function initAcct(){
		// 开户行省
		var bankPrv = "sys/datadictionary/listjason.do?keyName=province";
		$("#acctPrvn").combobox('clear');
		$("#acctPrvn").combobox({
			url: bankPrv,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
		
		// 开户行市
		var bankPrvVal = $('#acctPrvn').combobox('getValue');
		var bankCity = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(bankPrvVal);
		$("#acctCity").combobox("clear");
		$('#acctCity').combobox({
			url: bankCity,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
		
		
		// 开户行区县
		var bankCityVal = $('#acctCity').combobox('getValue');
		/* var bankCtry = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(bankCityVal);
		$("#acctCtry").combobox("clear");
		$('#acctCtry').combobox({
			url: bankCtry, 
			valueField: 'keyProp',
			textField: 'keyValue'
		});	 */
		
		// 银行
		var bank = "sys/datadictionary/listjason.do?keyName=bank";
		$("#acctBank").combobox("clear");
		$('#acctBank').combobox({
			url: bank, 
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
	            var bankProvince = $('#acctPrvn').combobox('getValue');
	            var bankCity = $('#acctCity').combobox('getValue');
				$('#acctBranch').combobox('clear');
	            var cUrl = "common/listBranchBank.do?province="+encodeURI(bankProvince)+"&city="+encodeURI(bankCity)+"&bank_name="+ encodeURI(newValue);
	            $('#acctBranch').combobox({
					url: cUrl, 
					valueField: 'keyProp',
					textField: 'keyValue'
				});
			}
		});	
		
		if(bankPrvVal != '' && bankCityVal != '' && bankVal != '') {
			// 支行
			var bankVal = $('#acctBank').combobox('getValue');
			var branch = "common/listBranchBank.do?province="+encodeURI(bankPrvVal)+"&city="+encodeURI(bankCityVal)+"&bank_name="+encodeURI(bankVal);
			$("#acctBranch").combobox("clear");
			$('#acctBranch').combobox({
				url: branch, 
				valueField: 'keyProp',
				textField: 'keyValue'
			});
		}
	}
</script>

<script type="text/javascript" >

window.parent.openMask();
$.parser.onComplete = function(){
	window.parent.closeMask();
}
</script>
</body>
</html>


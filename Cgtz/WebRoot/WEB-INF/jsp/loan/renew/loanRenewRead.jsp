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
<title></title>
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
		<div class="content" id="loanBaseRead">
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>续贷信息</strong></div><hr color="#D3D3D3"/>
				<table id="loanInfo">
					<tr>
						<td>未还金额:</td>
						<td>
							<input id="loanAmt" name="loanBase.loanAmt" type="text"  data-options="min:0,precision:2" readonly="readonly"
									class="textbox easyui-numberbox" value="${loanBase.loanAmt}"/>元
						</td>
						<td>延长日期:</td>
						<td>
							<input id="endDate" name="loanBase.endDate" type="text" class="textbox easyui-datebox"  readonly="readonly"
								data-options="editable:false" value="${loanBase.endDateStr}"/>
						</td>
					</tr>
				</table>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>还款方式</strong></div><hr color="#D3D3D3"/>
				<table id="repayInfo">
					<tr>
						<td>还款方式:</td>
						<td>
							<!-- <input type="hidden" name="loanBase.rate"/>
							<input type="hidden" name="loanBase.inteDays"/>
							<input type="hidden" name="loanBase.memFee" />
							<input type="hidden" name="loanBase.lawFee" />
							<input type="hidden" name="loanBase.otherFee"/> -->
							
							<input id="retWay" name="loanBase.retWay" type="text"  readonly="readonly"
								data-options="editable:false,panelHeight:'auto'" 
								class="textbox easyui-combobox" value="${loanBase.retWay}"/>
						</td>
					</tr>
					
					<tr>
						<td>年化利率:</td>
						<td>
							<input id="rate" type="text" class="textbox easyui-numberbox"  readonly="readonly"
								data-options="min:0,precision:2" value="${loanBase.rate}"/>%
						</td>
						<c:if test="${loanBase.retWay eq '02'}">
								<td>等额利率:</td>
								<td>
									<input id="deRate" name="loanBase.deRate" type="text" class="textbox easyui-numberbox"  readonly="readonly"
										data-options="min:0,precision:2" value="${loanBase.deRate}"/>%
								</td>
						</c:if>
						<td>计息天数:</td>
						<td><input id="inteDays" type="text" class="textbox easyui-combobox"  readonly="readonly"
								data-options="editable:false,
											panelHeight:'auto',
											textField:'keyValue',
											valueField:'keyProp',
											data:dataDictJson.inteDays" value="${loanBase.inteDays}"/>
						</td>
						<td>会员服务费:</td>
						<td><input id="memFee" type="text" class="textbox easyui-numberbox"  readonly="readonly"
							 	data-options="min:0,precision:2" value="${loanBase.memFee}"/>元
						</td>
					</tr>
					<tr>
						<td>法律服务费:</td>
						<td><input id="lawFee" type="text" class="textbox easyui-numberbox"  readonly="readonly"
								 data-options="min:0,precision:2" value="${loanBase.lawFee}"/>元
						</td>
						<td>保证金:</td>
						<td><input id="magin" type="text" class="textbox easyui-numberbox"  readonly="readonly"
								 data-options="min:0,precision:2" value="${loanBase.magin}"/>元
						</td>
						<td>转贷费:</td>
									<td><input id="tranFee" type="text" class="textbox easyui-numberbox"  readonly="readonly"
											 data-options="min:0,precision:2" value="${loanBase.tranFee}"/>元
									</td>
						<td>其他费用:</td>
						<td><input id="otherFee" type="text" class="textbox easyui-numberbox"  readonly="readonly"
								 data-options="min:0,precision:2" value="${loanBase.otherFee}"/>元
						</td>
					</tr>
					<tr>
						<td>管理服务费:</td>
						<td><input id="mgrFee" name="loanBase.mgrFee" type="text" class="textbox easyui-numberbox" readonly="readonly"
								 data-options="min:0,precision:2" value="${loanBase.mgrFee}"/>元
						</td>
						<td>他项权证费:</td>
						<td><input id="certFee" name="loanBase.certFee" type="text" class="textbox easyui-numberbox" readonly="readonly"
								 data-options="min:0,precision:2" value="${loanBase.certFee}"/>元
						</td>
						<td>评估费:</td>
						<td><input id="evalFee" name="loanBase.evalFee" type="text" class="textbox easyui-numberbox" readonly="readonly"
								 data-options="min:0,precision:2" value="${loanBase.evalFee}"/>元
						</td>
						<td>中介费:</td>
						<td><input id="agentFee" name="loanBase.agentFee" type="text" class="textbox easyui-numberbox" readonly="readonly"
								 data-options="min:0,precision:2" value="${loanBase.agentFee}"/>元
						</td>
					</tr>
					<tr>
						<td>周期方案:</td>
						<td>
							<input name="loanBase.retLoanSol" type="radio" value="01"  readonly="readonly" <c:if test="${loanBase.retLoanSol eq '01' }">checked='checked'</c:if> />月付
							<input name="loanBase.retLoanSol" type="radio" value="02"  readonly="readonly" <c:if test="${loanBase.retLoanSol eq '02' }">checked='checked'</c:if> />季付
							<input name="loanBase.retLoanSol" type="radio" value="03" readonly="readonly" <c:if test="${loanBase.retLoanSol eq '03' }">checked='checked'</c:if> />首付
							<input name="loanBase.retLoanSol" type="radio" value="04" readonly="readonly" <c:if test="${loanBase.retLoanSol eq '04' }">checked='checked'</c:if> />末付
						</td>
					</tr>
					
				</table>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>业务员备注</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>说明：</td>
						<td>
							<textarea name="ext1" class="textbox easyui-validatebox"  readonly="readonly" 
								data-options="validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;">${loanBase.ext1 }</textarea>
						</td>
					</tr>
				</table>
		</div>
	</div>
</body>
<script type="text/javascript">

//页面加载完动作
$(document).ready(function (){
	
	var tsurl="sys/datadictionary/listjason.do?keyName=repayMethod";
	$('#retWay').combobox('clear');
	$('#retWay').combobox({
		url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue'
	});
}); 

</script>
<script type="text/javascript" >

window.parent.openMask();
$.parser.onComplete = function(){
	window.parent.closeMask();
}
</script>
</html>
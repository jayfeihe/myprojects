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
<title>还款管理详情</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<style type="text/css">
	</style>
<script type="text/javascript" >
function zhezhao(){
		$("<div class=\"datagrid-mask\" id='chushiZhezhao'></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
		$("<div class=\"datagrid-mask-msg\" id='chushiZhezhaoMsg'></div>").html("正在加载，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
}
zhezhao();
function rmZhezhao(){
		$("#chushiZhezhao").remove();
		$("#chushiZhezhaoMsg").remove();
}

$(window).load(function (){
	rmZhezhao();
});
</script>
</head>
<body>
<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">贷款客户还款计划管理：${contract.contractNo}</a></p>
		<div class="content">
<table>
	<tr><td>
		<form id="updateForm" action="paymentManage/save.do">
			<input type="hidden" name="contractNo" value="${contract.contractNo}" />
			<input type="hidden" name="contractId" value="${contract.id}"/>
			<table class="datatable">
				<tr>
					<td>姓名：</td>
					<td><input class="textbox easyui-validatebox" disabled="disabled"
							   value="${contract.loanName}" /></td>
					<td>证件号码：</td>
					<td><input class="textbox easyui-validatebox" disabled="disabled"
							   value="${contract.loanIdNo}" /></td>
					<td>合同版本：</td>
					<td><input class="textbox easyui-validatebox" disabled="disabled"
							   value="V1.0" /></td>
				</tr>
				<tr>
					<td>合同金额：</td>
					<td><input data-options="required:true,min:0,precision:2" 
							   class="textbox easyui-numberbox" disabled="disabled"
							   value="${contract.loanAmount}" /></td>
					<td>贷款期数：</td>
					<td><input class="textbox easyui-validatebox" disabled="disabled"
							   value="${contract.loanPeriod}" /></td>
					<td>月还款额：</td>
					<td><input data-options="required:true,min:0,precision:2" 
							   class="textbox easyui-numberbox" disabled="disabled"
							   value="${yhkje}" /></td>
				</tr>
				<tr>
					<td>还款起始日期：</td>
					<td><input class="textbox easyui-datebox" disabled="disabled"
							   value="${hkqsrq}" /></td>
					<td>还款终止日期：</td>
					<td><input class="textbox easyui-datebox" disabled="disabled"
							   value="${contract.endDateStr}" /></td>
					<td>已还款期数：</td>
					<td><input class="textbox easyui-validatebox" disabled="disabled"
							   value="${yhkqs }" /></td>
				</tr>
				<tr>
					<td>违约起始日期：</td>
					<td><input class="textbox easyui-datebox" disabled="disabled"
							   value="${wyqsrq}" /></td>
<!-- 					<td>逾期天数：</td> -->
<!-- 					<td><input class="textbox easyui-validatebox" disabled="disabled" -->
<%-- 							   value="${yqts }" /></td> --%>
					<td>累计违约次数：</td>
					<td><input class="textbox easyui-validatebox" disabled="disabled"
							   value="${ljwycs }" /></td>
					<td colspan="2">
						<a href="<%=basePath%>paymentManage/predictPayment.do?id=${contract.id}&loanDate=" target="_blank" style='color:red'>还款计算</a>
					</td>
				</tr>
				<c:if test="${login_user.isAdmin == 1 || roleRepay == '还款管理专员'}">
					<tr>
						<td>一次性还款金额：</td>
						<td>
							<input data-options="min:0,precision:2" 
								   class="textbox easyui-numberbox" disabled="disabled"
								   value="${ycxhkje}" /></td>
						<td colspan="4"><input type="button" class="btn" onclick="javascript:updateFunction('advancePayment');" value="提前一次性还清" /></td>
					</tr>
				</c:if>
				
				<c:if test="${login_user.isAdmin == 1 || rolePublic == '对公平账员'}">
					<tr>
						<td>对公一次性还款金额：</td>
						<td>
							<input name="publicAdvanceAmount" id="publicAdvanceAmount"; data-options="min:0,precision:2" class="textbox easyui-numberbox" 
							value="${ycxhkje}" />
						</td>
						<td colspan="4">
							<input type="button" class="btn" onclick="javascript:updateFunction('publicAdvancePayment');" value="对公一次性还清" />
						</td>
					</tr>
				</c:if>
				
				<c:if test="${login_user.isAdmin == 1 || rolePublic == '对公平账员'}">
					<tr>
						<td>最高减免金额：</td>
						<td><input data-options="min:0,precision:2" 
							   class="textbox easyui-numberbox" disabled="disabled"
							   value="${maxjmje}" /></td>
						<td align="right">减免金额：</td>
						<td><input id="abatementAmount" name="abatementAmount" data-options="min:0,precision:2"
								   class="textbox easyui-numberbox"/></td>
						<td colspan="2"><input type="button" class="btn" value="减免" onclick="javascript:updateFunction('abatement');" /></td>
					</tr>
				</c:if>
				<c:if test="${login_user.isAdmin == 1 || roleRepay == '还款管理专员'}">
					<tr>
						<td>实时还款金额：</td>
						<td><input id="realTimeAmount" name="realTimeAmount" data-options="min:0,precision:2" 
								   class="textbox easyui-numberbox" /></td>
						<td colspan="4"><input type="button" value="实时还款" class="btn" onclick="javascript:updateFunction('realTime');" /></td>
					</tr>
				</c:if>
				<c:if test="${login_user.isAdmin == 1 || rolePublic == '对公平账员' }">
					<tr>
						<td>对公还款金额：</td>
						<td>
							<input id="dghkAmount" name="dghkAmount" data-options="min:0,precision:2" class="textbox easyui-numberbox" />
						</td>
						<td colspan="3">
							<input type="button" value="对公还款" class="btn" onclick="javascript:updateFunction('dghk');" />
						</td>
					</tr>
				</c:if>
			</table>
		</form>
	</td></tr>
	<tr><td>
		<table class="datatable">
			<tr>
				<th scope="col" rowspan="2">序号</th>
				<th scope="col" rowspan="2">还款日</th>
				<th scope="col" colspan="6">应收</th>
				<th scope="col" colspan="6">实收</th>
				<th scope="col" rowspan="2">罚息减免</th>
				<th scope="col" rowspan="2">滞纳金减免</th>
				<th scope="col" rowspan="2">本期应还款合计</th>
				<th scope="col" rowspan="2">实际还款日</th>
				<th scope="col" rowspan="2">逾期天数</th>
				<th scope="col" rowspan="2">还款状态</th>
				<th scope="col" rowspan="2">划扣状态</th>
			</tr>
			<tr>
				<th style="background-color:#ddd;" scope="col">当月应收服务费</th>
				<th style="background-color:#ddd;" scope="col">当月应收本金</th>
				<th style="background-color:#ddd;" scope="col">当月应收利息</th>
				<th style="background-color:#ddd;" scope="col">当月应收罚息</th>
				<th style="background-color:#ddd;" scope="col">当月应收滞纳金</th>
				<th style="background-color:#ddd;" scope="col">当月应收合计</th>
				<th style="background-color:#ddd;" scope="col">当月实收服务费</th>
				<th style="background-color:#ddd;" scope="col">当月实收本金</th>
				<th style="background-color:#ddd;" scope="col">当月实收利息</th>
				<th style="background-color:#ddd;" scope="col">当月实收罚息</th>
				<th style="background-color:#ddd;" scope="col">当月实收滞纳金</th>
				<th style="background-color:#ddd;" scope="col">当月实收合计</th>
			</tr>
		  <c:forEach items="${repayPlanList}" var="data" varStatus="status">
			<tr>
				<td>${status.index+1}</td>
				<td>${data.repaymentDateStr}</td>
				<td>${data.sreviceFeeReceivable}</td>
				<td>${data.principalReceivable}</td>
				<td>${data.interestReceivable}</td>
				<td>${data.penaltyReceivable}</td>
				<td>${data.delayReceivable}</td>
				<td>${data.dyyshj}</td>
				<td>${data.sreviceFeeReceived}</td>
				<td>${data.principalReceived}</td>
				<td>${data.interestReceived}</td>
				<td>${data.penaltyReceived}</td>
				<td>${data.delayReceived}</td>
				<td>${data.dyshhj}</td>
				<td>${data.penaltyReduce}</td>
				<td>${data.delayReduce}</td>
				<td>${data.bqyhkhj}</td>
				<td>${data.payDateStr}</td> 
				<td>${data.yqts}</td> 
				<td>${data.hkzt}</td> 
				<td>${data.fyLoanState}</td> 
			</tr>
		  </c:forEach>
		</table>
	</td></tr>
</table>		
		
	</div>
</div>

<<!-- div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
	<img src="img/loading.gif" alt="加载中..." />
</div> -->
</body>
<script type="text/javascript">
// function predictPaymentFn(data_id) {
<%-- 	window.location.href = "<%=basePath%>paymentManage/predictPayment.do?id=" + data_id + "&loanDate="; --%>
// 	return;
// }
//更新保存
function updateFunction(buttonType) {
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
	if(buttonType=="abatement") {
		if($('#abatementAmount').numberbox('getValue') == ''){
			$.messager.alert('消息', '请输入减免金额！');
			return;
		}
	}else if(buttonType=="realTime"){
		if($('#realTimeAmount').numberbox('getValue') == ''){
			$.messager.alert('消息', '请输入实时还款金额！');
			return;
		}
	}else if(buttonType=="dghk"){
		if($('#dghkAmount').numberbox('getValue') == ''){
			$.messager.alert('消息', '请输入对公还款金额！');
			return;
		}
	}else if(buttonType=="publicAdvancePayment"){
		var dgje=$('#publicAdvanceAmount').numberbox('getValue')
		if( dgje== ''){
			$.messager.alert('消息', '请输入  对公一次性还款金额！');
			return;
		}
	}
	//弹出异步加载 遮罩
	window.parent.openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	var str;
	if('advancePayment' == buttonType){
		str = "提前一次性还款";
	}else if('abatement' == buttonType){
		str = "减免";
	}else if('realTime' == buttonType){
		str = "实时还款";
	}else if('dghk' == buttonType){
		str = "对公还款";
	}else if(buttonType=="publicAdvancePayment"){
		str = "对公一次性还款";
	}
	
	$.messager.confirm('消息', "是否确认" + str + "?", function(ok){
		if (ok){
			$.ajax({
				type : "POST",
				url : "<%=basePath%>paymentManage/save.do",
				data : params+"&buttonType="+buttonType,
				success : function(data) {
					try{  
						//关闭遮罩，弹出消息框
						window.parent.closeMask();
					}catch(e){
						$(".btn").removeAttr("disabled");
					}
					if ("true"==data.success) {
						$.messager.alert('消息', data.message, 'info', function(){
							
							if(buttonType=="abatement" || buttonType=="realTime" || buttonType=="dghk") {
								location=location;
							}else{
								window.history.go(-1);
							}
		            	});
		            } else {
		    			$.messager.alert('消息', data.message);
		            }
				},
				error : function() {
					//关闭遮罩，弹出消息框
					try{  
						window.parent.closeMask();
					}catch(e){
						$(".btn").removeAttr("disabled");
					}
					$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
				}
			});
		}
	});
	try{  
		window.parent.closeMask();
	}catch(e){
		$(".btn").removeAttr("disabled");
	}
}


//返回
function back(){
	window.history.go(-1);
}

//打开Loading遮罩并修改样式
/*function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
}*/


//页面加载完动作
$(document).ready(function (){
	
	//填充select数据 借款用途1
    var useage1url = "sys/datadictionary/listjason.do?keyName=creditusage1";
	$("#useage1").combobox("clear");
	$('#useage1').combobox({
		url: useage1url,
		valueField: 'keyProp',
		textField: 'keyValue',
		onChange: function(newValue, oldValue){
            $('#useage2').combobox('clear');
            var useage2url = "sys/datadictionary/listjason.do?keyName=creditusage2&parentKeyProp=" + encodeURI(newValue);
            $('#useage2').combobox('reload',useage2url); 
      	}
	});
	
});
</script>

</html>

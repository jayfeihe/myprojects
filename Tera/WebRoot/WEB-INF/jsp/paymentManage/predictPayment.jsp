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
<title>预计还款</title>
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
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td>一次性还款金额：</td>
					<td><input type="hidden" name="contractNo" value="${contract.contractNo}" />
							<input type="hidden" name="contractId" value="${contract.id}"/>
						<input data-options="min:0,precision:2" 
							   class="textbox easyui-numberbox" disabled="disabled"
							   value="${ycxhkje}" /></td>
					<td>最高减免金额：</td>
					<td><input data-options="min:0,precision:2" 
						   class="textbox easyui-numberbox" disabled="disabled"
						   value="${maxjmje}" /></td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td>预计还款时间：</td>
					<td><input id="yjhkrq" class="textbox easyui-datebox" value="${yjhkrq }" /></td>
					<td colspan="4">
						&nbsp;
<%-- 						<input type="button" value="返回" onclick="updateData(${contract.id})"/> --%>
					</td>
				</tr>
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

<div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
	<img src="img/loading.gif" alt="加载中..." />
</div>
</body>
<script type="text/javascript">

//返回
function back(){
	window.history.go(-1);
}

//打开Loading遮罩并修改样式
function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
}

//调到操作界面
function updateData(data_id) {
	window.location = "<%=basePath%>paymentManage/update.do?id="+data_id;
	return;
}

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
	
	$('#yjhkrq').datebox({
        onSelect: function (date) {
        	var y = date.getFullYear();
 		   	var m = date.getMonth()+1;
 		   	var d = date.getDate();
//  		   var h = date.getHours();
//  		   var M = date.getMinutes();
//  		   var s = date.getSeconds();
			date = y+'-'+m+'-'+d;
        	window.location = "<%=basePath%>paymentManage/predictPayment.do?id=${contract.id}&loanDate=" + date ;
        	return;
        }
    });
	
});
</script>




</html>


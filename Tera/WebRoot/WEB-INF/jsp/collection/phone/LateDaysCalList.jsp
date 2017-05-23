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
<title>逾期还款计划</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" >
			
			<table class="datatable">
			<tr>
				<th scope="col" rowspan="2">序号</th>
				<th scope="col" rowspan="2">还款日</th>
				<th scope="col" colspan="6">应收</th>
				<th scope="col" colspan="6">实收</th>
				<th scope="col" rowspan="2">罚息减免</th>
				<th scope="col" rowspan="2">滞纳金减免</th>
				<th scope="col" rowspan="2">本期应还款合计</th>
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
			</tr>
		  </c:forEach>
		  <input id="bqyhkhjAllTemp" name="bqyhkhjAll" type="hidden" data-options="min:0,precision:2"  class="textbox easyui-numberbox" value=""/>
		  <input id="znAllAmountTemp" natme="znAllAmount" type="hidden" data-options="min:0,precision:2"  class="textbox easyui-numberbox" value=""/>
		  <input id="dbAllamountTemp" name="dbAllamount" type="hidden" data-options="min:0,precision:2"  class="textbox easyui-numberbox" value=""/>
		</table>
	
			<div id="pageStyle">
			<!-- ${ pm.pageNavigation} -->
			</div>
		</form>
	</div>
</body>

<script language="javascript">
//更新
function updateData(data_id) {
	window.location = "<%=basePath%>" + "collection/phone/update.do?id=" + data_id;
	return;
}

//删除
function deleteData(data_id) {
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
				url : "<%=basePath%>" + "collection/phone/delete.do",
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
					$.messager.confirm('消息', '数据加载失败,请联系系统管理员！');
				}
			});
        }
    });
}


//添加
function add(){
	window.location = "<%=basePath%>" + "collection/phone/update.do";
	return;
}

//页面加载完动作
$(document).ready(function (){
	$("#dbAllamount").val(${dbAllamount });	
	$("#znAllAmount").val(${znAllAmount });	
	$("#bqyhkhjAll").val(${bqyhkhjAll });		
});
function selectAll(){
	var checkitems=$('[name=checkresult]');
	if($('#checkbutton').attr('checked')=='checked'){
		for(var i=0;i<checkitems.length;i++){
			checkitems.eq(i).attr("checked",true);
		}
	}else{
		for(var i=0;i<checkitems.length;i++){
			checkitems.eq(i).attr("checked",false);
		}
	}
	
}
</script>
</html>


<%@page import="com.tera.cooperation.dinxuan.model.CeilingAmountQBean"%>
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
<title>渠道确认放款管理</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">申请编号</th>
					<th scope="col">进件日期</th>
					<th scope="col">客户姓名</th>
					<th scope="col">身份证号</th>
					<th scope="col">营业部</th>
					<th scope="col">决策产品</th>
					<th scope="col">合同金额</th>
					<th scope="col">月还款额</th>
					<th scope="col">借款用途</th>
					
					<th scope="col">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td><a href="credit/review/read.do?id=${data.appId}" target="_blank">${data.appNo }</a></td>
						<td>
							<fmt:formatDate value="${data.incomeTime }" type="date" />
						</td>
						<td>${data.name }</td>
						<td>${data.idNo }</td>
						<td>${data.orgName }</td>
						<td>${data.product }</td>
						<td>
							<fmt:formatNumber value="${data.contractAmount }" type="currency"/>元
						</td>
						<td>
							<fmt:formatNumber value="${data.monthRepayAmount }" type="currency"/>元
						</td>
						<td>${data.loanApplication }</td>
						
						<td>
							<a href="javascript:void(0);" onclick="javascript:updateData('Y','${data.appNo}','${data.channelType }');">确认放款</a>&nbsp;
							<a href="javascript:void(0);" onclick="javascript:updateData('N','${data.appNo}','${data.channelType }');">拒贷</a>&nbsp;
							<a href="javascript:void(0);" onclick="javascript:updateData('U','${data.appNo}','${data.channelType }');">上传图片</a>&nbsp;
						</td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</div>
</body>

<script language="javascript">
//更新
function updateData(_flag,app_no,channel_type) {
	if($("body").find("#dialogDiv").length==0){
		 $('body').append($("<div id='dialogDiv' style='top:150px;'></div>"));
	}
	var _url = "cooperation/common/loanManage/update.do?flag="+_flag+"&appNo="+app_no+"&channelType="+channel_type;
	var _title = "";
	if ('Y' == _flag) {
		_title = "确认放款操作";
	} else if('N' == _flag) {
		_title = "拒贷操作";
	} else {
		_title = "附件上传";
	}

	$('#dialogDiv').dialog({
		title : _title,
		width : 500,
		closed : false,
		cache : false,
		resizable : true,
		href : encodeURI(_url),
		modal : true,
		
		onLoad : function() {
			//填充select数据 拒件码01
			var refusedCode01url = "sys/datadictionary/listjason.do?keyName=refusedCode01";
			$("#decisionCode1").combobox("clear");
			$('#decisionCode1').combobox({
					url : refusedCode01url,
					valueField : 'keyProp',
					textField : 'keyValue',
					onChange : function(
							newValue, oldValue) {
						$('#decisionCode2').combobox('clear');
						var refusedCode02url = "sys/datadictionary/listjason.do?keyName=refusedCode02&parentKeyProp="
								+ encodeURI(newValue);
						$('#decisionCode2').combobox('reload',refusedCode02url);
					}
					});
			//填充select数据 拒件码02
			var decisionCode1 = $('#decisionCode1').combobox('getValue');
			var refusedCode02url = "sys/datadictionary/listjason.do?keyName=refusedCode02&parentKeyProp="
					+ encodeURI(decisionCode1);
			$("#decisionCode2").combobox("clear");
			$('#decisionCode2').combobox({
				url : refusedCode02url,
				valueField : 'keyProp',
				textField : 'keyValue'
			});	
		}
	});
}

	//页面加载完动作
	$(document).ready(function() {

	});
</script>
</html>


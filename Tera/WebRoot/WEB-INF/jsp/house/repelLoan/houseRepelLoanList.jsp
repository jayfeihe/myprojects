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
<title>外部接口拒贷查询列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
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
					<th scope="col">姓名</th>
					<th scope="col">进件时间</th>
					<th scope="col">借款金额</th>
					<th scope="col">申请渠道</th>
					<th scope="col">申请产品</th>
					<th scope="col">申请期限</th>
					
					
					<th scope="col">决策时间</th>
					<th scope="col">决策渠道</th>
					<th scope="col">决策产品</th>
					<th scope="col">决策期限</th>
					<th scope="col">到手金额</th>
					<th scope="col">月还款额</th>
					<th scope="col">合同金额</th>
					
					
					<th scope="col">状态</th>
					<th scope="col">营业部</th>
					<th scope="col">操作</th>
				</tr>

				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td><a href="house/repelLoan/read.do?id=${data.appId}" target="_blank">${data.loanAppId}</a></td>
						<td>${data.appName}</td>
						<td>${data.inputTimeStr}</td>
						<td>
							<fmt:formatNumber value="${data.appAmount/10000}" type="currency"/>万元	
						</td>
						<td>${date.appChannelName}</td>
						<td>${data.appProduct}</td>
						<td>${data.appPeriod}期</td>
						<td>${data.decisionDateStr}</td>
						<td>${data.decisionChannelName}</td>
						<td>${data.loanProduct}</td>
						<td>
							<c:if test="${data.loanPeriod != 0}">${data.loanPeriod }期</c:if>
						</td>
						<td>
							<c:if test="${null != data.decisionAmount && data.decisionAmount != 0.0  }"><fmt:formatNumber value="${data.decisionAmount}" type="currency"/>元</c:if>
						</td>
						<td>
							<c:if test="${null != data.methodAmount && data.methodAmount != 0.0  }"><fmt:formatNumber value="${data.methodAmount}" type="currency"/>元</c:if>
						</td>
						<td>
							<c:if test="${null != data.loanAmount && data.loanAmount != 0.0  }"><fmt:formatNumber value="${data.loanAmount}" type="currency"/>元</c:if>
						</td>
						<td>
							<c:choose>
								<c:when test="${data.appState=='0'}">前端拒贷</c:when>
								<c:when test="${data.appState=='1'}">录入申请</c:when>
								<c:when test="${data.appState=='2'}">审核退回</c:when>
								<c:when test="${data.appState=='5'}">核价退回</c:when>
								<c:when test="${data.appState=='8'}">核价</c:when>
								<c:when test="${data.appState=='3'}">审核</c:when>
								<c:when test="${data.appState=='4'}">审批退回</c:when>
								<c:when test="${data.appState=='6'}">审批</c:when>
								<c:when test="${data.appState=='7'}">特殊审批退回</c:when>
								<c:when test="${data.appState=='10'}">特殊审批</c:when>
								<c:when test="${data.appState=='13'}">签约</c:when>
								<c:when test="${data.appState=='14'}">撮合已完成</c:when>
								<c:when test="${data.appState=='15'}">复核退回</c:when>
								<c:when test="${data.appState=='17'}">复核</c:when>
								<c:when test="${data.appState=='18'}">放款退回</c:when>
								<c:when test="${data.appState=='19'}">放款</c:when>
								<c:when test="${data.appState=='20'}">已确认放款</c:when>
								<c:when test="${data.appState=='21'}">放款成功</c:when>
								<c:when test="${data.appState=='22'}">放款失败</c:when>
								<c:when test="${data.appState=='23'}">放款完成</c:when>
								<c:when test="${data.appState=='24'}">拒贷</c:when>
								<c:otherwise>未知</c:otherwise>
							</c:choose>	
						</td>
						<td>${data.orgName}</td>
						<td>
							<a href="javascript:void(0);" onclick="javascript:artOpenPage('拒贷详情','house/repelLoan/repelLoanPopup.do?id=${data.appId}')">拒贷</a>
<%-- 							<a href="javascript:void(0);" onclick="repelLoanFn('${data.loanAppId }');">拒贷</a> --%>
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


//原页面弹出框
function artOpenPage(_title,_url) {
	if($("body").find("#dialogDiv").length==0){
		 $('body').append($("<div id='dialogDiv' style='top:150px;'></div>"));
	}
	$('#dialogDiv').dialog({
	    title: _title,
	    height: 350,
	    width: 500,
	    closed: false,
	    cache: false,
	    href: encodeURI(_url),
	    modal: true,
	    resizable: true,
	    onLoad: function() {
			//填充select数据 拒件码01
		    var refusedCode01url = "sys/datadictionary/listjason.do?keyName=refusedCode01";
		    $("#decisionCode3").combobox("clear");
		    $('#decisionCode3').combobox({
		        url: refusedCode01url,
		        valueField: 'keyProp',
		        textField: 'keyValue',
		        onChange: function(newValue, oldValue){
		            $('#decisionCode4').combobox('clear');
		            var refusedCode02url = "sys/datadictionary/listjason.do?keyName=refusedCode02&parentKeyProp=" + encodeURI(newValue);
		            $('#decisionCode4').combobox('reload',refusedCode02url); 
		              }
		    });
		    //填充select数据 拒件码02
		    var decisionCode3 = $('#decisionCode3').combobox('getValue');
		    var refusedCode02url = "sys/datadictionary/listjason.do?keyName=refusedCode02&parentKeyProp=" + encodeURI(decisionCode3);
		    $("#decisionCode4").combobox("clear");
		    $('#decisionCode4').combobox({
		        url: refusedCode02url,
		        valueField: 'keyProp',
		        textField: 'keyValue'
		    }); 
	 	}
	});
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


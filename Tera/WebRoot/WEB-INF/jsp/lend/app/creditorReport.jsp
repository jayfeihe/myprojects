<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>债权报告</title>

<style type="text/css">
p {
	line-height:29px;		
}
</style>

</head>

<body>
<p style="text-align:center;font-size:24px;"><strong>${companyName}客户债权报告 </strong></p>
<br/>
<p>    尊敬的<u>      ${lendApp.name}       </u>先生/女士您好： </p>
<p>    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 您于 ${lendDate } 至 ${lendEndDate } 的资金出借情况如下：</p>
<br/>
<p style="text-align: right">货币单位：人民币（元）                </p>
<div style="position: relative; height:170px; top: 80px; float: right;" >
	<img src='<%=basePath%>/seal/getSealImage.do?contractNo=${lendApp.appId}'/>
</div>
<div style="position: relative; float: left;">
	<table border="1" cellspacing="0" cellpadding="0" align="center" width="660">
	  <tr align="center">
	    <td width="140" align="center"><p align="center">  <strong>出借编号</strong></p></td>
	    <td width="100" align="center"><p align="center"><strong>出借方式 </strong></p></td>
	    <td width="150" align="center"><p align="center"><strong>初始出借日期 </strong></p></td>
	    <td width="160" align="center"><p align="center"><strong>初始出借金额 </strong></p></td>
	    <td width="150" align="center"><p align="center"><strong>账户管理费</strong></p></td>
	    <td width="150" align="center"><p align="center"><strong>本期本息总额</strong></p></td>
	  </tr>
	  <tr align="center">
	  	<td width="140" valign="middle" align="center"><p>${lendApp.appId }</p></td>
	    <td width="100" valign="middle" align="center"><p>${lendApp.product }</p></td>
	    <td width="150" valign="middle" align="center"><p>${lendDate }</p></td>
	    <td width="160" valign="middle" align="center"><p><fmt:formatNumber value="${lendApp.amount }" type="currency"/></p></td>
	    <td width="150" valign="middle" align="center"><p><fmt:formatNumber value="0.0" type="currency"/></p></td>
	    <td width="150" valign="middle" align="center"><p><fmt:formatNumber value="${bxze }" type="currency"/></p></td>
	  </tr>
	</table>
</div>
<div style="clear:both"></div>
<div>
	<table border="1" cellspacing="0" cellpadding="0" align="center" width="660">
		<tr>
			<td colspan="9" align="center" height="50"><p align="center"><strong>债权列表</strong></p></td>
		</tr>
		<tr align="center">
		  <td width="59" align="center"><p align="center"><strong>序号</strong></p></td>
		  <td width="150" align="center"><p align="center"><strong>借款人姓名</strong></p></td>
		  <td width="249" align="center"><p align="center"><strong>借款人身份证号</strong></p></td>
		  <td width="140" align="center"><p align="center"><strong>借款人<br/>职业情况</strong></p></td>
		  <td width="100" align="center"><p align="center"><strong>借款<br/>用途</strong></p></td>
		  <td width="140" align="center"><p align="center"><strong>还款起始<br/>日期</strong></p></td>
		  <td width="70" align="center"><p align="center"><strong>还款<br/>期限</strong></p></td>
		  <td width="90" align="center"><p align="center"><strong>剩余还<br/>款时间<br/>(月)</strong></p></td>
		  <td width="170" align="center"><p align="center"><strong>借款<br/>金额</strong></p></td>
		</tr>
		<c:forEach items="${matchResultList}" var="data" varStatus="status">
		 <tr align="center">
			<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
		   <td width="150" valign="middle" align="center"><p>${data.name}</p></td>
		   <td width="249" valign="middle" align="center"><p>${data.idNo}</p></td>
		   <td width="140" valign="middle" align="center"><p>${data.comDuty}</p></td>
		   <td width="100" valign="middle" align="center"><p>${data.useage}</p></td>
		   <td width="140" valign="middle" align="center"><p>${data.loanStartDate}</p></td>
		   <td width="70" valign="middle" align="center"><p>${data.loanPeriod}</p></td>
		   <td width="90" valign="middle" align="center"><p>${data.surplusLoanPeriod}</p></td>
		   <td width="170" valign="middle" align="center"><p><fmt:formatNumber value="${data.loanAmount}" type="currency"/></p></td>
		 </tr>
		</c:forEach>
		<tr align="center">
		   <td width="350" valign="middle" colspan="8" align="center"><p>合计</p></td>
		   <td width="150" valign="middle" align="center"><p><fmt:formatNumber value="${jkjehj }" type="currency"/></p></td>
		 </tr>
	</table>
</div>
</body>
</html>
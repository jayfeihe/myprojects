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
<title>还款事项提醒函JMV1.1</title>

<style type="text/css">
p {
	line-height:29px;		
}
</style>

</head>

<body>
<p>借款声明书附件：</p>
<p style="text-align:center;font-size:24px;"><strong>还款计划 </strong></p>
<p style="text-align:right;"><br />
  编号：<u>    ${contract.contractNo}-02      </u></p>
<p>
本还款计划由以下借款人于<u> ${year }  </u>年<u> ${month }  </u>月<u> ${date }  </u>日签署:<br/>
借款人：<u>  ${bean.name}  </u> <br/>
身份证号码：<u> ${bean.idNo} </u> <br/>
住址：<u>  ${bean.addProvince} ${bean.addCity} ${bean.addCounty} ${bean.address}  </u><br/><br/>
	&nbsp; 借款人签署有编号为 <u>  ${contract.contractNo}  </u>的《借款声明书》，借款人确认该《借款声明书》项下的还款计划内容如下：<br />
借款金额：<u> ￥<fmt:formatNumber value="${zkbjse/10000} " pattern="#,#00.00"/> </u>万元<br />
借款期限：<u> ${product.period }  </u>个月<br />
</p>
<div style="position: relative;float: left;" >
	<table border="1" cellspacing="0" cellpadding="0" align="left" width="527">
	  <tr>
	    <td width="59"><p align="center">  <strong>期数 </strong></p></td>
	    <td width="99"><p align="center"><strong>还款日期 </strong></p></td>
	    <td width="104"><p align="center"><strong>还款金额 </strong></p></td>
	    <td width="113"><p align="center"><strong>本金还款 </strong></p></td>
	    <td width="113"><p align="center"><strong>息费 </strong></p></td>
	    <td width="151"><p align="center"><strong>剩余本金 </strong></p></td>
	  </tr>
  	<c:forEach items="${loanRepayPlanList}" var="data" varStatus="status">
  <c:if test="${status.index == 10}">
  	</table>
</div>
<div style="position: relative; height:170px; top:50px;left:-66px; float: right;" >
	<img src='<%=basePath%>seal/getSealImage.do?contractNo=${contract.contractNo}'/>
</div>
	<table cellspacing="0" cellpadding="0" align="left" width="527" style="border-width:0 1 1 1;">
  </c:if>
		  <tr>
		  	<td width="59" valign="top" style="border-width:1 1 1 1;"><p>${data.periodNum}</p></td>
		    <td width="99" valign="top" style="border-width:1 1 1 1;"><p>${data.repaymentDateStr}</p></td>
		    <td width="104" valign="top" style="border-width:1 1 1 1;"><p>￥<fmt:formatNumber value="${data.monthAmount}" pattern="#,#00.00"/></p></td>
		    <td width="113" valign="top" style="border-width:1 1 1 1;"><p>￥<fmt:formatNumber value="${data.principalReceivable}" pattern="#,#00.00"/></p></td>
		    <td width="113" valign="top" style="border-width:1 1 1 1;"><p>￥<fmt:formatNumber value="${data.interestReceivable}" pattern="#,#00.00"/></p></td>
		    <td width="151" valign="top" style="border-width:1 1 1 1;"><p>￥<fmt:formatNumber value="${data.restPrincipal}" pattern="#,#00.00"/></p></td>
		  </tr>
	  </c:forEach>
	</table>
<!-- </div> -->

<div style="clear:both"></div>
	<div style="height:200px;">	
		&nbsp; 利息自出借人支付的借款划转至借款人指定账户之日起起算。利息起算日与上表载明不一致的，以利息起算日为准；利息起算日迟于上表载明日期的，还款日依次顺延。<br/><br/><br/>
		借款人确认同意并依照上述表单履行还款义务<br/><br/>	
		<p style="text-align:left;">
		  借款人签字：<u>       &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;     </u>         <br />
		  日期：<u> &nbsp;&nbsp;&nbsp; &nbsp; &nbsp; </u> 年  <u>&nbsp; &nbsp; &nbsp;</u>月  <u>&nbsp; &nbsp; &nbsp;</u> 日 </p>
	</div>
</body>
</html>
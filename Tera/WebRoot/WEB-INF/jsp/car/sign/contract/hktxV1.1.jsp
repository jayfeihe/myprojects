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
<title>还款事项提醒函V1.1</title>

<style type="text/css">
p {
	line-height:29px;		
}
</style>

</head>

<body>
<p style="text-align:center;font-size:24px;"><strong>还款事项提醒函 </strong></p>
<p style="text-align:right;"><br />
  编号：<u>    ${contract.contractNo}-02      </u></p>
<p><u>      ${bean.name}       </u>先生/女士您好： </p>
<p><br />
  为了您更加方便、快捷地进行还款，同时在我公司积累良好的信用记录，在此，请您特别注意如下事项： <br />
  1．<strong>还款账户：</strong>在借款协议里列示的专用账号是您每次用于还款的账户，请您妥善保管好该银行卡。如发生此银行卡丢失、损坏的情况，请您第一时间联系我们。我们将根据具体情况，协助您安排按时还款事宜，以避免不必要的逾期违约金的产生。如您需要变更还款账户，请向我们提出申请（还款日当天不受理），我们的工作人员将协助您，进行变更借款协议中专用账号的事项办理。 </p>
<p><br />
  2．<strong>还款账户最低余额为15 元：</strong>为了保证您的还款成功划扣，请确保您的还款账户余额在划扣当月还款额以后，还有至少15 元的余额存在您的还款账户里，此金额有可能用于支付还款银行的&ldquo;年费&rdquo;和其他相关费用。关于此&ldquo;年费&rdquo;和其他费用收费标准，请致电您的还款银行咨询。同时为了保证扣款日能够成功划扣，请您保持专用账号中有充足的资金，避免因其他未知或遗忘的代扣等业务造成违约。 </p>
<p><br />
  3．<strong>还款金额</strong>：您的每月还款额为人民币（大写）<u>   ${MAmountUpperCase}   </u>元（<u>  ￥<fmt:formatNumber value="${MAmountLowerCase}" pattern="#,#00.00"/>   </u>）,请您务必牢记此数字。</p>
<p><br />
  4．<strong>还款时间</strong>：请您在每月<u>     ${day}       </u>日18点之前，将每月还款额足额缴存，如逾期将产生逾期违约金，并会影响您的个人信用记录。请您务必牢记此还款日，此还款日期不因节假日顺延。 <br />
  &nbsp; &nbsp; 若您采用跨行转账方式还款，请提前一周进行划转。我们将以成功划入账户的还款金额时间为准，记录还款时间。 </p>
<p><br />
  5．如果您因特殊原因暂时无法按时足额还款时，请在第一时间（当月还款日期之前）与我们联系。（电话：<u>      010-57484428      </u>）。我们将协助您安排预约还款并准确告知您还款金额（本息、逾期违约金、协议约定的其他费用等）。特别需要您值得注意的是：针对违约时长超过15天，根据借款协议，出借人有权提前终止借款协议，您须在出借人提出终止借款协议，要求的三日内一次性支付余下的所有本金、利息和逾期违约金。 </p>
<p><br />
  6．以下列举如果您在还款第一期即出现不能按时还款，违约天数、逾期违约金分别为人民币： </p>
  <table border="1" cellspacing="0" cellpadding="0">
    <tr>
      <td width="89" valign="top"><p align="center"><strong>违约天数 </strong></p></td>
      <td width="457" valign="top"><p align="center"><strong>逾期违约金 </strong></p></td>
    </tr>
    <tr>
      <td width="89" valign="top"><p align="center">1</p></td>
      <td width="457" valign="top"><p align="center"> ￥<fmt:formatNumber value="${yqwyj1}" pattern="#,#00.00"/> </p></td>
    </tr>
    <tr>
      <td width="89" valign="top"><p align="center">15</p></td>
      <td width="457" valign="top"><p align="center"> ￥<fmt:formatNumber value="${yqwyj15}" pattern="#,#00.00"/> </p></td>
    </tr>
  </table>
<p>7．<strong>联系方式变更</strong>：为了方便我们更好地服务于您，如果您的联系方式、工作单位、居住或工作地址等与借款有关的信息发生变化，请在第一时间拨打电话<u>     010-57484428      </u>通知我们。 </p>
<p><br />
  8．<strong>提前还款（即一次性还款）</strong>：为了方便我们更好地服务于您，如果您决定一次性将全部款项结清，请您务必提前三个工作日与我们进行书面确认（还款日及节假日期间不办理）。我们将帮助您安排预约一次性还款的相关事宜。 <br />
  <strong>提前还款电话</strong>：<u>      010-57484428      </u>。 </p>
<p><br />
  9．如您无法通过电话联系我们，您还可通过电子邮件方式将您的姓名、联系方式、需办理事项发送至：  kf_service@herongdai.cn ，我们的客服人员将在8个工作小时内跟您联系。 <br />
  您的还款分期表如下： </p>
<div style="position: relative;float: left;">
	<table border="1" cellspacing="0" cellpadding="0" align="left" width="527">
	  <tr>
	    <td width="59"><p align="center">  <strong>期数 </strong></p></td>
	    <td width="99"><p align="center"><strong>还款日期 </strong></p></td>
	    <td width="104"><p align="center"><strong>应还款额 </strong></p></td>
	    <td width="113"><p align="center"><strong>剩余本金 </strong></p></td>
	    <td width="151"><p align="center"><strong>一次性还款金额 </strong></p></td>
	  </tr>
	  <c:forEach items="${loanRepayPlanList}" var="data" varStatus="status">
 	<c:if test="${status.index == 10}">
 	</table>
</div>
<div style="position: relative; height:170px; top:50px;left:-66px; float: right;" >
	<img src='<%=basePath%>/seal/getSealImage.do?contractNo=${contract.contractNo}'/>
</div>
	<table cellspacing="0" cellpadding="0" align="left" width="527" style="border-width:0 1 1 1;">
		</c:if>
		  <tr>
		  	<td width="59" valign="top" style="border-width:1 1 1 1;"><p>${data.periodNum}</p></td>
		    <td width="99" valign="top" style="border-width:1 1 1 1;"><p>${data.repaymentDateStr}</p></td>
		    <td width="104" valign="top" style="border-width:1 1 1 1;"><p>￥<fmt:formatNumber value="${data.monthAmount}" pattern="#,#00.00"/></p></td>
		    <td width="113" valign="top" style="border-width:1 1 1 1;"><p>￥<fmt:formatNumber value="${data.restPrincipal}" pattern="#,#00.00"/></p></td>
		    <td width="151" valign="top" style="border-width:1 1 1 1;"><p>￥<fmt:formatNumber value="${data.repaySum}" pattern="#,#00.00"/></p></td>
		  </tr>
	  </c:forEach>
	</table>
<!-- </div> -->

<div style="clear:both"></div>
<p style="text-align:right; margin-right: 150px;">    
  借款人签字：<u>       &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;     </u>         <br />
  日期：<u> &nbsp;&nbsp;&nbsp; &nbsp; &nbsp; </u> 年  <u> &nbsp;&nbsp; </u>月  <u> &nbsp;&nbsp; </u> 日 </p>
</body>
</html>
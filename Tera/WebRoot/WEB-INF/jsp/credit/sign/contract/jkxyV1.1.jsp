<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>借款协议V1.1</title>

<style type="text/css">
p {
	line-height:29px;		
}
</style>
</head>
<body>
<p style="text-align:center;font-size:24px;"><strong>借款协议 </strong><br />
  </p>
<p style="text-align:right;">编号：<u>     ${contract.contractNo}     </u></p>
<p style="text-align:left;"><br />
  本协议由以下双方于<u>   ${year}   </u>年<u> ${month}  </u>月<u> ${date}  </u>日签署： </p>
<p style="text-align:left;"><br />
  甲方（出借人）： </p>
<table border="1" cellspacing="0" cellpadding="0">
  <tr>
    <td width="104" ><p>名称 </p></td>
    <td width="123" ><p>证件类型 </p></td>
    <td width="189" ><p>证件号码 </p></td>
    <td width="136" ><p>出借金额 </p></td>
  </tr>
  <c:forEach items="${ matchResultList}" var="data" varStatus="status">
	  <tr>
	    <td width="104" ><p>${data.name}</p></td>
	    <td width="123" ><p>身份证</p></td>
	    <td width="189" ><p>${data.idNo}</p></td>
	    <td width="136" ><p>￥<fmt:formatNumber value="${data.lendAmount/10000}" pattern="#,#00.00"/>万元</p></td>
	  </tr>
  </c:forEach>
</table>
<p>乙方（借款人）： <br />
  姓名：<u>               ${bean.name}                </u><br />
  身份证号码： <u>                ${bean.idNo}                            </u><br />
  住址： <u>               ${bean.addProvince} ${bean.addCity} ${bean.addCounty} ${bean.address}                                  </u><br /><br />
  <strong>第一条 借款 </strong><br />
  1.1 借款信息 </p>
<div align="center" >
 <table width="692" height="215" border="1" cellpadding="0" cellspacing="0" style="width:692; table-layout:fixed; word-wrap:break-word;">
  <tr>
    <td width="106" ><p align="center">借款月利率 </p></td>
    <td width="255" colspan="2" ><p align="center">${product.interestRate}%</p></td>
    <td width="33" ><p align="center">百 </p></td>
    <td colspan="2" ><p align="center">十 </p></td>
    <td width="30" ><p align="center">万 </p></td>
    <td width="27" ><p align="center">千 </p></td>
    <td width="30" ><p align="center">百 </p></td>
    <td width="28" ><p align="center">十 </p></td>
    <td width="29" ><p align="center">元 </p></td>
    <td width="29" ><p align="center">角 </p></td>
    <td width="24" ><p align="center">分 </p></td>
  </tr>
  <tr>
    <td width="106" ><p align="center">借款本金数额 </p></td>
    <td width="255" colspan="2" ><p align="left">人民币（大写）<br />${zkbjse} </p></td>
    <td width="33" ><p align="center">${zkbjse9}</p></td>
    <td colspan="2" ><p align="center">${zkbjse8}</p></td>
    <td width="30" ><p align="center">${zkbjse7}</p></td>
    <td width="27" ><p align="center">${zkbjse6}</p></td>
    <td width="30" ><p align="center">${zkbjse5}</p></td>
    <td width="28" ><p align="center">${zkbjse4}</p></td>
    <td width="29" ><p align="center">${zkbjse3}</p></td>
    <td width="29" ><p align="center">${zkbjse2}</p></td>
    <td width="24" ><p align="center">${zkbjse1}</p></td>
  </tr>
  <tr>
    <td width="106" ><p align="center">借款详细用途 </p></td>
    <td colspan="12" ><p>${bean.useage1}  ${bean.useage2}</p></td>
  </tr>
  <tr>
    <td width="106" rowspan="2"><p align="center">借款人名下收款账户 </p></td>
    <td width="85" ><p align="center">户名 </p></td>
    <td width="170" ><p align="center">${contract.bankAccountName}</p></td>
    <td colspan="2" ><p align="center">账号 </p></td>
    <td colspan="8" ><p align="center">${contract.bankAccount}</p></td>
  </tr>
  <tr>
    <td width="85" ><p align="center">开户行 </p></td>
    <td colspan="11" ><p align="left">${contract.bankProvince} ${contract.bankCity} ${contract.bankCounty} ${contract.bankName} <br />${contract.bankBranch}</p></td>
  </tr>
</table>
</div>
<p align="left">1.2 甲方委托支付机构自<u>   ${year}   </u>年<u>  ${month}  </u>月<u>  ${date}  </u>日起三个工作日内将出借资金扣除本协议第三条约定的费用后划扣至上述表格中乙方指定的收款账户。 <br /><br />
  <strong>第二条 还款 </strong><br />
  2.1  还款信息 </p>
<div align="center">
 <table width="692" height="215" border="1" cellpadding="0" cellspacing="0" style="width:692; table-layout:fixed; word-wrap:break-word;">
  <tr>
    <td width="106" ><p align="center">还款分期月数 </p></td>
    <td colspan="2" ><p align="center">${contract.loanPeriod}</p></td>
    <td width="33" ><p align="center">百 </p></td>
    <td colspan="2" ><p align="center">十 </p></td>
    <td width="30" ><p align="center">万 </p></td>
    <td width="27" ><p align="center">千 </p></td>
    <td width="30" ><p align="center">百 </p></td>
    <td width="28" ><p align="center">十 </p></td>
    <td width="29" ><p align="center">元 </p></td>
    <td width="29" ><p align="center">角 </p></td>
    <td width="24" ><p align="center">分 </p></td>
  </tr>
  <tr>
    <td width="106" ><p align="center">月偿还本息数额 </p></td>
    <td width="255" colspan="2" ><p align="left">人民币（大写）<br />${MAmount}</p></td>
    <td width="33" ><p align="center">${MAmount9}</p></td>
    <td colspan="2" ><p align="center">${MAmount8}</p></td>
    <td width="30" ><p align="center">${MAmount7}</p></td>
    <td width="27" ><p align="center">${MAmount6}</p></td>
    <td width="30" ><p align="center">${MAmount5}</p></td>
    <td width="28" ><p align="center">${MAmount4}</p></td>
    <td width="29" ><p align="center">${MAmount3}</p></td>
    <td width="29" ><p align="center">${MAmount2}</p></td>
    <td width="24" ><p align="center">${MAmount1}</p></td>
  </tr>
  <tr>
    <td width="106" ><p align="center">还款日 </p></td>
    <td colspan="12" ><p>每月<u>    ${day}    </u> 日（18：00前，节假日不顺延） </p></td>
  </tr>
  <tr>
    <td width="106" ><p align="center">还款起止日期 </p></td>
    <td colspan="12" ><p align="left"><u>   ${startYear}     </u>年<u> ${startMonth}  </u>月<u> ${startDay}  </u>日起，<u>    ${endYear}    </u>年<u> ${endMonth}  </u>月<u> ${endDay}  </u>日止 </p></td>
  </tr>
  <tr>
    <td width="106" rowspan="2"><p align="center">借款人还款账户 </p></td>
    <td width="85" ><p align="center">户名 </p></td>
    <td width="170" ><p align="center">${contract.bankAccountName}</p></td>
    <td colspan="2" ><p align="center">账号 </p></td>
    <td colspan="8" ><p align="center">${contract.bankAccount}</p></td>
  </tr>
  <tr>
    <td width="85" ><p align="center">开户行 </p></td>
    <td colspan="11" ><p align="left">${contract.bankProvince} ${contract.bankCity} ${contract.bankCounty} ${contract.bankName} <br />${contract.bankBranch}</p></td>
  </tr>
</table>
</div>
<p align="left">2.2 乙方承诺按月足额向甲方偿还本金和利息。 <br />
  2.3 乙方应在每月还款日当日（不得迟于18:00）或之前将本条约定的偿还月本息数额。 <br />
  2.4 如还款日为法定假日或公休日，还款日不顺延。 <br /><br />
  <strong>第三条 费用的委托支付 </strong><br />
  依据《融资服务协议》（编号：<u>    ${contract.contractNo}-01      </u> ），乙方应向<u>  ${companyName}    </u>支付服务费人民币（大写<u>     ${djfwfjeUpperCase}     </u>） 元（<u>   ￥<fmt:formatNumber value="${djfwfjeLowerCase}" pattern="#,#00.00"/>   </u>）。现乙方委托甲方从本协议第一条约定的出借资金中代为支付上述费用，甲方同意代为支付。 <br /><br />
  <strong>第四条 提前还款 </strong><br />
  4.1 如乙方拟提前还款，需至少提前三个工作日以书面方式提出。乙方应在约定的日期当日或之前将当月应还款项及剩余本金存入还款账户，并委托支付机构划扣给甲方。 <br />
  4.2 如乙方按照本条第1款约定提前还款，相应款项足额支付给甲方后，本协议终止。 <br /><br />
  <strong>第五条 违约责任 </strong><br />
  5.1 如乙方未于本协议第二条约定的还款日足额还款，则应向甲方支付逾期违约金。 <br />
  5.2 逾期违约金由以下两部分组成： <br />
  5.2.1 逾期当月应还本息的10%，但不低于人民币100元； <br />
  5.2.2 从还款日次日起，逾期当月直至借款期结束的应还本息全额的0.05%/日。 <br />
  5.3 逾期违约金每月单独计算。 <br />
  5.4 如因乙方原因导致支付机构未能及时、足额收到当月应还本息，乙方仍应按本条第一、二项支付逾期违约金。 <br />
  5.5 若乙方偿还金额不足，偿还先后顺序为逾期违约金、应还利息、应还本金。 <br />
  5.6 乙方发生如下违约行为时，甲方有权解除本协议，乙方须在甲方提出解除协议之日起三日内一次性支付余下的所有本金、利息和逾期违约金。 <br />
  5.6.1 乙方擅自改变本协议第一条规定的借款用途； <br />
  5.6.2 严重违反还款义务（逾期达到15天及以上）； <br />
  5.6.3 借款人提供虚假资料或者故意隐瞒重要事实。 <br />
  5.7 乙方同意，甲方可将乙方违约的相关信息予以公开披露。如乙方存在涉嫌欺诈等犯罪行为，甲方有权向相关国家机关报案，以追究其刑事责任。 <br />
  5.8 因乙方违约而导致甲方产生的损失，包括本息损失、仲裁费用、调查费用、律师费用及委托其他公民参加仲裁所产生的费用等由乙方承担。 <br /><br />
  <strong>第六条 乙方联系方式变更通知 </strong><br />
  6.1 乙方签署本协议之日起至借款全部清偿之日止，乙方有义务在下列信息变更之日起三日内提供更新后的信息给甲方： <br />
  6.1.1 乙方工作单位、居住地址、住所电话、手机号码、电子邮箱等； <br />
  6.1.2 乙方家庭联系人或常用联系人工作单位、居住地址、住所电话、手机号码等。 <br />
  6.2 因乙方不提供或不及时提供上述变更信息而导致甲方产生的损失，包括调查费用、仲裁费用及律师费用及委托其他公民参加仲裁所产生的费用等将由乙方承担。 <br /><br />
  <strong>第七条 债权转让通知 </strong><br />
  如甲方将本协议项下的债权向他人转让，甲方应将《债权转让通知》以邮件的方式发送至乙方下列邮箱  ${bean.email} 。该通知到达乙方指定邮箱时生效。乙方应向债权受让人继续履行本协议的后续还款义务。 <br /><br />
  <strong>第八条 争议管辖 </strong><br />
  8.1 双方一致同意，本协议签订地为<u>   ${contract.signProvince} ${contract.signCity} ${contract.signCounty}   </u>。 <br />
  8.2 各方一致同意，如发生争议，不论争议金额大小，均提交北京仲裁委员会适用北京仲裁委员会仲裁规则项下的简易程序进行仲裁。仲裁裁决为终局的，对各方均有拘束力。 <br /><br />
  <strong>第九条 其他 </strong><br />
  9.1 本协议自甲乙双方签署时成立，自甲方将出借款项支付到乙方指定收款账户时生效。 <br />
  9.2 本协议的任何修改、补充均须以书面形式作出，补充条款与本协议具有同等的法律效力。 <br />
  9.3 本协议的传真件、复印件和扫描件等有效复本的效力与本协议原件具有同等法律效力。 <br />
  9.4 本协议部分条款因违反法律法规被视为无效时不影响本协议其他条款的效力。 <br />
  （以下无正文） </p>
  <br />
<p align="left">
  甲方（出借人）：                      乙方（借款人）：   </p>
</body>
</html>
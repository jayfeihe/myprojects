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
<title>借款声明书v1.1</title>

<style type="text/css">
p {
	line-height:29px;
	
}
</style>

</head>

<body>
 
<div align="left" style="position: relative; left:-260px; float: right;">	
	<p style="text-align:center;font-size:24px;"><strong>借款声明书 </strong></p>
</div>
 
<div style="clear:both"></div>
<p style="text-align:right;">编号：<u>     ${contract.contractNo}     </u></p>

<p>本文件由以下借款人于<u> ${year} </u>年<u> ${month} </u>月<u> ${date} </u>日在<u> ${contract.signProvince}   ${contract.signCity}   ${contract.signCounty}  </u>   签署： <br /></p>
   
   <p>姓名：  <u> ${bean.name }  </u> <br />
	 身份证号码： <u> ${bean.idNo} </u><br />
	住址： <u>  ${bean.addProvince} ${bean.addCity} ${bean.addCounty} ${bean.address}  </u><br/>
	 联系方式： <u> ${contract.bankMobile} </u></p>
   
  
     <p style="text-indent: 2em;">
	 经由${companyName}资信评估及推荐，本人自愿通过<u> 中海微银资产管理有限公司  </u>向其合作方客户（以下称出借人）借款并同意签署本文件。 <br/>
本声明书内容为借款人向出借人借款（以下简称<strong>“借款事项”</strong>）的具体合同条款，声明内容如下：

	 </p>

	 <p align="left"><strong>第一条 借款 </strong><br />
	 1.1 借款基本信息:  </p>
			<div align="center" >
 <table width="692" height="215" border="1" cellpadding="0" cellspacing="0" style="width:692; table-layout:fixed; word-wrap:break-word;">
  <tr>
  	<td width="106" rowspan="2" colspan="1"><p align="center">借款本金数额 </p></td>
    <td width="255" colspan="3" rowspan="2"><p align="left">人民币（大写）<br />${zkbjse} </p></td>
    <td width="33" ><p align="center">百 </p></td>
    <td width="30" ><p align="center">十 </p></td>
    <td width="30" ><p align="center">万 </p></td>
    <td width="27" ><p align="center">千 </p></td>
    <td width="30" ><p align="center">百 </p></td>
    <td width="28" ><p align="center">十 </p></td>
    <td width="29" ><p align="center">元 </p></td>
    <td width="29" ><p align="center">角 </p></td>
    <td width="24" ><p align="center">分 </p></td>
  </tr>
  <tr>
    <td width="33" ><p align="center">${zkbjse9}</p></td>
    <td width="30" ><p align="center">${zkbjse8}</p></td>
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
	 <p align="left">1.2 借款人委托<u> ${companyName}    </u>将上述借款经由支付机构划扣至上述表格中借款人指定的收款账户。
	 </p>

 <p align="left"><strong>第二条 还款 </strong><br />
	2.1 还款信息: </p>

				<div align="center">
 <table width="692" height="215" border="1" cellpadding="0" cellspacing="0" style="width:692; table-layout:fixed; word-wrap:break-word;">
  <tr>
    <td width="106" ><p align="center">还款分期月数 </p></td>
    <td colspan="3" ><p align="center">${contract.loanPeriod}</p></td>
    <td width="33" ><p align="center">百 </p></td>
    <td width="30" ><p align="center">十 </p></td>
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
    <td width="255" colspan="3" ><p align="left">人民币（大写）<br />${MAmount}</p></td>
    <td width="33" ><p align="center">${MAmount9}</p></td>
    <td width="30" ><p align="center">${MAmount8}</p></td>
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
<p align="left">
2.2 借款人承诺按月足额向出借人偿还应还本金和利息（详见附件之还款计划）。<br/>
2.3 借款人应在每月还款日当日（不得迟于18:00）或之前将本条约定的月偿还本息数额存入上表载明的还款账户。<br/>
2.4 如还款日为法定假日或公休日，还款日不顺延；当月没有对应日期的，还款日为当月最后一个自然日。<br/>
</p>

<p align="left"><strong>第三条  费用的委托支付 </strong><br />

	依据《融资服务协议》（编号： <u> ${contract.contractNo}-01  </u>），借款人应向服务提供方各方（包括${companyName}、<u> 中海微银资产管理有限公司  </u>）支付相关服务费用，并委托<u> ${companyName} </u>通过支付机构在其取得的借款中直接划转并代为支付上述约定的服务费用。</p>

<p align="left"><strong>第四条 提前还款</strong><br />
4.1 借款人有权申请提前还清剩余借款。 <br />
4.2 借款人承诺：如提前还款，将提前至少3个工作日向${companyName}以书面方式提出，并在约定的日期当日18:00或之前将当月应还款项及剩余本金存入还款账户，并委托支付机构划扣上述金额，同时委托${companyName}将上述提前还款资金交付出借人。<br />
4.3 借款人按照4.1款约定进行提前还款的，相应款项足额支付给出借人后，本协议自行终止，无需另行确认。<br /></p>

<p align="left"><strong>第五条 诚实守法义务</strong><br />
5.1 借款人承诺向出借人、${companyName}及<u> 中海微银资产管理有限公司  </u>如实陈述及披露相关信息，保证其提供的信息和资料的真实性、准确性、合法性，且无事实的隐瞒。<br />
5.2 借款人承诺不会将所借款项用于任何违法活动，包括但不限于赌博、吸毒、贩毒、卖淫嫖娼等。<br /></p>

<p align="left"><strong>第六条 违约责任</strong><br />
6.1 借款人未按照协议约定于还款日当日18:00前将应还款金额足额存入指定还款账户，致使账户中的余额不足以支付当期应付本金及利息的，将被视为还款逾期，应按照融资服务协议中的约定支付逾期违约金及罚息。<br />
6.2 借款人承诺如出现以下情形，出借人或${companyName}有权宣告合同解除，借款人有义务将借款文件项下的全部债务进行一次性清偿：<br />
（1）借款人擅自改变借款用途；<br />
（2）严重违反还款义务，累计发生三次逾期的；<br />
（3）违背本文件第五条约定的诚实守法义务的；<br />
（4）未经出借人书面同意将债务转移给第三人的；<br />
（5）借款人发生亏损、财务状况恶化等改变，涉及或可能涉及重大经济纠纷、诉讼、仲裁或财产被依法查封、扣押或监管等，导致出借人或${companyName}依据自主判断认为可能危及本声明书项下债权实现的；<br />
（6）借款人怠于管理和追索其到期债权，或以无偿或其他不适当方式处分现有主要财产等转移财产或其他逃避债务行为的；<br />
（7）借款人违反本声明书的约定，出借人或${companyName}依据自主判断认为足以危及本协议项下债权实现的。<br />
上述情形下借款人应于被宣告合同解除之日起3日内，依据出借人或${companyName}的要求一次性支付本金、利息、罚息和逾期违约金等，否则视为逾期还款，依上述6.1款所述支付罚息及违约金。<br />
6.3 借款人在借款义务履行过程中，给出借人造成其他损失的，应另行赔偿出借人或${companyName}的损失。<br />
6.4 借款人清偿金额不足的，偿还先后顺序为催收费用、罚息、逾期违约金、应还利息、应还本金。<br />
6.5 借款人同意：出借人及${companyName}和<u> 中海微银资产管理有限公司  </u>有权将借款人的违约相关信息予以公开披露；如借款人存在涉嫌欺诈及上述5.2款载明的违法犯罪行为的，出借人及${companyName}和<u> 中海微银资产管理有限公司  </u>   亦有权向相关国家司法机关报案，以追究其刑事责任。<br /></p>

<p align="left"><strong>第七条 借款人联系方式变更通知</strong><br />
7.1 借款人签署本声明书之日起至借款全部清偿之日止，有义务在下列信息变更之日起3日内提供更新后的信息给出借人和${companyName}：<br />
7.1.1 借款人工作单位、居住地址、住所电话、手机号码、电子邮箱等；<br />
7.1.2 借款人家庭联系人或紧急联系人工作单位、居住地址、住所电话、手机号码等。<br />
7.2 因借款人不提供或不及时提供上述变更信息而导致出借人损失的，包括但不限于调查费用、仲裁费用、律师费用及差旅费用等，概由借款人承担。<br /></p>

<p align="left"><strong>第八条 债权转让通知</strong><br />
出借人有权将借款文件项下的债权向他人转让，此等转让一经发生即时生效，借款人确认无条件认可出借人的转让行为，且同意向债权受让人继续履行本协议的后续还款义务。<br /></p>

<p align="left"><strong>第九条 法律适用和争议解决</strong><br />
本声明书及其附件的签订、履行、终止、解释和争议解决均适用中国法律。<br />
如发生争议和纠纷，各方应友好协商解决，协商不成的任一一方有权向${companyName}住所地人民法院提起诉讼。<br /></p>

<p align="left"><strong>第十条 征信信息说明</strong><br />
借款人同意授权${companyName}与<u> 中海微银资产管理有限公司  </u>   依据《征信业管理条例》及相关法律法规，自行或者委托第三方征信机构，合法调查了解借款人信用信息，并向第三方征信机构提交借款人在借款业务中产生的相关信息，包括借款人基本信息、借款申请信息、借款合同信息以及还款行为信息等，并记录在该征信机构的信用信息数据库中。<br /></p>

<p align="left"><strong>第十一条 其他条款</strong><br />
11.1 本文件自借款人签署时成立，自出借人的出借款项支付到借款人账户时生效。<br />
11.2 借款人确认已知悉${companyName}和<u> 中海微银资产管理有限公司  </u>   并承诺遵守。<br />
11.3 出借人未支付借款金额的，借款人签署的借款声明书自动失效。<br />
11.4 如果本声明书及其附件（如有）有一条或多条规定根据任何法律或法规在任何方面被裁定为无效、不合法或不可强制执行，则本声明书及其附件其余规定的有效性、合法性或可强制执行性不应在任何方面受到影响或损害。出借人和借款人应通过诚意磋商，争取以法律许可及其期望的最大限度内有效的规定取代该等无效、不合法或不可强制执行的规定。而该等有效的规定所产生的经济效果应尽可能与该些无效、不合法或不可强制执行的规定所产生的经济效果相似。<br />
11.5 借款人应当保守与本声明书及其附件有关的商业秘密，不得私自泄露或使用。如给其他方造成损失，应当承担赔偿责任。<br />
11.6 本借款声明书为编号为<u>  ${contract.contractNo}-01  </u>   的融资服务协议之附件，与其具有同等法律效力。<br />
11.7 借款人按照本声明书的约定圆满履行其所有义务,本声明书及其附件（如有）自动终止。<br /></p>


   <div style="clear:both"></div>
   <br/>
   

<p style="text-align:right;">
声明人（借款人）签字：<u>       &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;     </u>     <br/>
日期：<u> &nbsp;&nbsp;&nbsp; &nbsp; &nbsp; </u> 年  <u> &nbsp;&nbsp; </u>月  <u> &nbsp;&nbsp; </u> 日  </p>


<div style="clear:both"></div>
</body>
</html>
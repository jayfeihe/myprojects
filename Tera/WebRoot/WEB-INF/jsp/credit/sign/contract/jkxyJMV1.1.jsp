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
<title>借款协议JMV1.1</title>

<style type="text/css">
p {
	line-height:29px;		
}
</style>
</head>
<body>
<p style="text-align:center;font-size:24px;"><strong>借款声明书 </strong><br />
  </p>
<p style="text-align:right;">编号：<u>     ${contract.contractNo}     </u></p>
<p style="text-align:left;"><br />
  本文件由以下借款人于<u>   ${year}   </u>年<u> ${month}  </u>月<u> ${date}  </u>日签署： </p>
<p style="text-align:left;"><br />
  姓名： <u> ${bean.name }  </u><br/>
  身份证号码： <u> ${bean.idNo} </u><br/>
 住址： <u>  ${bean.addProvince} ${bean.addCity} ${bean.addCounty} ${bean.address}  </u><br/>
<strong>鉴于：</strong><br/>
&nbsp; &nbsp; &nbsp; 经由${companyName}资信评估及推荐，本人自愿同意通过北京乐融多源信息技术有限公司系融资服务平台积木盒子（www.jimubox.com）向其注册用户（以下称出借人）借款并签署本文件。<br/>
&nbsp; &nbsp; &nbsp; 本声明书内容为借款人向出借人借款（<strong>“借款事项”</strong>）的具体合同条款，声明内容如下：<br/>
</p>
<p> <br />
  <strong>第一条 借款 </strong><br />
  1.1 借款基本信息 </p>
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
<p align="left">1.2 借款人委托北京乐融多源信息技术有限公司将上述借款经由支付机构划扣至上述表格中借款人指定的收款账户。 <br />
  <strong>第二条 还款 </strong><br />
  2.1 还款信息 </p>
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
  2.2 借款人承诺按月足额向出借人偿还本金和利息（详见附件之还款计划）。 <br />
  2.3 借款人应在每月还款日当日（不得迟于18:00）或之前将本条约定的月偿还本息数额存入上表载明的还款账户。 <br />
  2.4 如还款日为法定假日或公休日，还款日不顺延；当月没有对应日期的，还款日为当月最后一个自然日。 <br />
  <strong>第三条 借款文件 </strong><br />
&nbsp; &nbsp; &nbsp; 依据积木盒子交易规则，借款人同意以电子协议方式与出借人达成《借款协议》，该《借款协议》与本文件共同作为有效的法律文件，借款人确认已明确知悉并理解上述《借款协议》的内容，同意无条件接受其条款的约束，并委托北京乐融多源信息技术有限公司代为完成积木盒子平台的注册及通过积木盒子平台账户完成上述《借款协议》的签署，该签署行为由本人负责，由本人享有上述文件约定的合同权利并承担相应合同义务。<br />
  <strong>第四条 费用的委托支付</strong><br />
&nbsp; &nbsp; &nbsp; 依据《融资服务协议》（编号： <u> ${contract.contractNo}-01  </u>），借款人应向服务提供方各方（包括${companyName}、北京乐融多源信息技术有限公司、乐融多源（北京）科技有限公司）支付相关服务费用，特委托北京乐融多源信息技术有限公司通过支付机构在其取得的借款中代为支付上述约定服务费用。<br />
  <strong>第五条 提前还款</strong><br />
  5.1 借款人有权提前清偿借款。 <br />
  5.2 借款人承诺：如拟提前还款，将至少提前三个工作日以书面方式提出，并在约定的日期当日或之前将当月应还款项及剩余本金存入还款账户，并委托支付机构划扣上述金额，同时委托${companyName}将上述提前还款资金交付出借人。 <br />
  5.3 借款人按照本条第2款约定进行提前还款的，相应款项足额支付给出借人后，本协议自行终止，无需另行确认。 <br />
  <strong>第六条	诚实守法义务 </strong><br />
  6.1 借款人承诺向出借人及${companyName}与北京乐融多源信息技术有限公司如实陈述及披露相关信息，保证其提供的信息和资料的真实性、准确性、合法性，且无事实的隐瞒。 <br />
  6.2 借款人承诺不会将所借款项用于任何违法活动，包括但不限于赌博、吸毒、贩毒、卖淫嫖娼等。 <br />
  <strong>第七条	违约责任</strong><br />
  7.1 借款人承诺按照与出借人等主体达成的有关协议中约定的还款日18:00前在指定银行账户中的余额足以支付当期应付利息和本金，否则视为逾期还款，应自还款日之次日起以逾期当日至合同到期应还本息全额的0.1%每天的标准向出借人支付违约金。上述逾期违约金每月单独计算。<br />
  7.2 借款人承诺以下情形出借人或${companyName}和北京乐融多源信息技术有限公司有权宣告合同解除，借款人有义务将借款文件项下的全部债务进行一次性清偿：<br />
&nbsp; （1）借款人擅自改变借款用途；<br />
&nbsp; （2）严重违反还款义务，连续或累计发生六次逾期的；<br />
&nbsp; （3）违背本文件第六条约定的诚实守法义务的；<br />
&nbsp; （4）未经出借人书面同意将债务转移给第三人的；<br />
&nbsp; （5）借款人发生亏损、财务状况恶化等改变，涉及或可能涉及重大经济纠纷、诉讼、仲裁或财产被依法查封、扣押或监管等，导致北京乐融多源信息技术有限公司或出借人依据自主判断认为可能危及本声明书项下债权实现的；<br />
&nbsp; （6）借款人怠于管理和追索其到期债权，或以无偿或其他不适当方式处分现有主要财产等转移财产或其他逃避债务行为的；<br />
&nbsp; （7）借款人违反本声明书的约定，北京乐融多源信息技术有限公司或出借人依据自主判断认为足以危及本协议项下债权实现的。<br />
&nbsp; &nbsp; &nbsp; 上述情形下借款人应当于宣告合同解除之日起三日内，依据出借人或${companyName}和北京乐融多源信息技术有限公司的要求一次性支付本金、利息和违约金（借款金额*10%），否则视为逾期还款，依上述7.1条的标准支付违约金。<br />
  7.3 借款人在借款文件履行过程中，给出借人造成其他损失的，应另行赔偿出借人或北京乐融多源信息技术有限公司的损失。<br />
  7.4 借款人清偿金额不足的，偿还先后顺序为违约金、应还利息、应还本金。<br />
  7.5 借款人同意：出借人及${companyName}和北京乐融多源信息技术有限公司有权将借款人的违约的相关信息予以公开披露；如借款人存在涉嫌欺诈及6.2载明的违法犯罪行为的，出借人及${companyName}和北京乐融多源信息技术有限公司亦有权向相关国家机关报案，以追究其刑事责任。<br />
  
  <strong>第八条	借款人联系方式变更通知 </strong><br />
  8.1 借款人签署本协议之日起至借款全部清偿之日止，有义务在下列信息变更之日起三日内提供更新后的信息给出借人、${companyName}和北京乐融多源信息技术有限公司：<br />
  8.1.1 工作单位、居住地址、住所电话、手机号码、电子邮箱等；<br />
  8.1.2 家庭联系人或常用联系人工作单位、居住地址、住所电话、手机号码等。<br />
  8.2 因借款人不提供或不及时提供上述变更信息而导致出借人产生的损失，包括调查费用、仲裁费用及律师费用及委托其他公民参加仲裁所产生的费用等将由出借人承担。 <br />
  <strong>第九条	债权转让通知</strong><br />
&nbsp; &nbsp; &nbsp; 如出借人有权将借款文件项下的债权向他人转让，此等转让一经发生即可生效，借款人确认无条件认可出借人的转让行为，且同意向债权受让人继续履行本协议的后续还款义务。 <br />
  <strong>第十条	法律适用和争议解决</strong><br />
&nbsp; &nbsp; &nbsp; 本声明书及其附件的签订、履行、终止、解释和争议解决均适用中国法律。 <br />
&nbsp; &nbsp; &nbsp; 与本声明有关的争议和纠纷，任何一方可将争议提交北京仲裁委员会按照提交争议时现行的仲裁规则进行仲裁，仲裁在北京进行，仲裁费、律师代理费由败诉方承担。 <br />
  <strong>第十一条	征信信息说明</strong><br />
&nbsp; &nbsp; &nbsp; 借款人同意授权${companyName}与北京乐融多源信息技术有限公司依据《征信业管理条例》及相关法律法规，自行或者委托第三方征信机构，合法调查了解借款人信用信息，并向第三方征信机构提交借款人在积木盒子平台借贷业务中产生的相关信息，包括借款人基本信息、借款申请信息、借款合同信息以及还款行为信息，并记录在征信机构的信用信息数据库中。 <br />
  <strong>第十二条	其他条款</strong><br />
12.1 本文件自借款人签署时成立，自出借人的出借款项支付到借款人积木盒子平台账户时生效。<br />
12.2 借款人确认已知悉“积木盒子”平台运营规则并承诺遵守。<br />
12.3 出借人未支付借款金额的，借款人签署的借款声明书自动失效。<br />
12.4 如果本声明书及其附件（如有）有一条或多条规定根据任何法律或法规在任何方面被裁定为无效、不合法或不可强制执行，则本声明书及其附件其余规定的有效性、合法性或可强制执行性不应在任何方面受到影响或损害。出借人和借款人应通过诚意磋商，争取以法律许可及其期望的最大限度内有效的规定取代该等无效、不合法或不可强制执行的规定，而该等有效的规定所产生的经济效果应尽可能与该些无效、不合法或不可强制执行的规定所产生的经济效果相似。<br />
12.5 借款人应当保守与本声明书及其附件有关的商业秘密，不得私自泄露或使用。如给其他方、北京乐融多源信息技术有限公司造成损失，应当承担赔偿责任。<br />
借款人按照本声明书的约定圆满履行其所有义务,本声明书及其附件（如有）自动终止。</p>
  <br />
<p align="right">
  声明人（借款人）签字：<u>       &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;     </u>(签字)&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </p>
	<br/>
  附：还款计划
</body>
</html>
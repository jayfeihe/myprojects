<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
<meta http-equiv="expires" content="0">
<link rel="icon" href="images/favicon.ico" type="image/x-icon">
 
<title>信用付款</title>
<style type="text/css">
p {
	line-height:29px;
	text-indent: 2em; /*em是相对单位，2em即现在一个字大小的两倍*/
	/*长空格格式 15行即是*/
	/*
	                                                          
	                                                          
	*/
	
	/*短空格格式*/
	/*
                   
	*/
}
table p{
	line-height:29px;
	text-indent:0em;
}
table td{
	text-align:center; 
}
</style>
</head>
<body>
<!--秒趣分期协议弹框start-->
<div >
	 
		 
			<p style="text-align:center;font-size:24px;"><strong>1信用付款(秒趣分期)服务协议 </strong></p> 
			<br /> 
<p style="text-align:right;"><br />

编号：<u>${userProtocol.protocolId}</u></p>
<p>
本协议由以下甲乙双方于【<u><fmt:formatDate value="${userProtocol.enableTime}" pattern="yyyy" /></u>】年【 <u><fmt:formatDate value="${userProtocol.enableTime}" pattern="MM" /></u>】月【<u><fmt:formatDate value="${userProtocol.enableTime}" pattern="dd" /></u>】日签署：<br/>

<strong>甲方（借款人）：</strong><u>${userProtocol.userName}</u> <br/>
身份证号码：<u>${userProtocol.idCard}</u> <br/>
家庭住所：<u>${userProtocol.homeAddress}</u> <br/>
工作单位：<u>${userInfo.companyName}</u> <br/>
单位地址：<u>${userInfo.companyAddress}</u> <br/>
联系方式：<u>${userProtocol.userPhone}</u> <br/>
<strong>乙方：<u>合稞同创（北京）科技有限公司</u> </strong><br/>
地址：<u>北京市海淀区东升科技软件园C1</u> <br/>
联系方式：<u>010-58407644</u> </p>

<p>本协议是指甲方与乙方及其与乙方达成合作协议的电子商务网站的所有者及网站运营者(以下简称“合作平台”)以及与乙方达成合作协议的网络借贷信息服务机构(以下简称“网络借贷服务机构”)就用户购买合作平台的商品或服务，并可以依据本协议就该商品或服务进行分期付款相关事宜所签订的契约。请甲方仔细阅读本服务协议，用户勾选“同意签署”点击“确认分期购买”同等效果按钮后，本协议即构成对双方有约束力的法律文件；</p>
<p>甲方在接受本协议前应明确对于合作平台、网络借贷服务机构及乙方进行如下授权：</p>
<p>一、授权乙方、合作平台、网络借贷服务机构或由乙方委托的第三方通过合法渠道了解、咨询、审查甲方的资信状况、财务状况和其他与评定用户信用付款额度及付款能力有关的信息，并可以在秒趣分期信用付款服务整个过程中及在追偿违约欠款时索取、留存和使用该等信息以及用户在合作平台提供的全部资料；授权乙方向协议的相关方（包括为信用付款服务提供支付服务的第三方支付公司、为信用付款服务提供查询服务的第三方公司等）披露与用户相关的任何信息；授权乙方、网络借贷服务机构在甲方违反本协议项下相关义务的情形下，有权将甲方的有关信息（包括但不限于甲方名称、地址、联系方式等）及违约行为通过任意媒介（包括但不限于乙方或网络借贷服务机构网站、其他媒体、电话、传真等方式）向不特定的公众（包括但不限于金融机构、媒体受众及甲方合作伙伴等）披露或告知，甲方已充分知悉并同意承担此等披露或告知行为可能带来的负面结果，乙方、网络借贷服务机构及其他信息发布方均不因此等披露或告知行为承担任何法律责任；乙方有权将甲方资料、信息、履约情况提供给第三方征信机构，以供有关单位、部门或个人依法查询和使用；乙方有权以通讯手段（包括但不限于：电话、短信、微信等各种通讯手段）告知甲方的亲属、利害关系人、朋友关于甲方的欠款信息，并在乙方或网络借贷服务机构网站或其他媒体、网站上发布甲方的欠款信息，信息内容包括但不限于甲方的姓名、身份证号码、住址、工作单位、照片、欠款金额、逾期时间及违约责任等；将甲方违约失信相关信息向包括但不限于公安机关、检察机关等司法机关、媒体、用人单位及有关逾期款项催收服务机构等披露而无需承担任何责任；甲方同意并授权乙方和网络借贷服务机构向中国人民银行个人信用信息基础数据库以及其他经政府有权部门批准合法设立的信用信息库查询甲方信用报告、财产、资信等其他相关情况以及报送甲方信用记录。<br/>
甲方接受本协议即表明确认知悉乙方制定的关于使用信用付款服务的所有规定，接受乙方制定的全部收费项目和条件，以及乙方公布的任何与分期付款有关的条款约束并遵守本协议及其后之修订版本。
</p>

 <strong>第一条 &nbsp; 定义与释义</strong><br/>
<p>在本协议中，下列词语具有以下含义：</p>
<p>借款人/用户：指在乙方合作平台购买商品或服务并选择信用付款（秒趣分期）分期支付服务，并通过乙方及乙方指定第三方和网络借贷服务机构的评估且具有完全民事权利/行为能力的中华人民共和国（以下简称“中国”，为避免疑义，不包括中国香港、澳门及台湾地区）境内自然人、法人或其它组织。</p>
<p>平台规则：指标示在乙方、网络借贷服务机构或合作平台之上的，与用户使用秒趣分期所提供的平台服务有关的任何规范性文件，包括但不限于购物流程、售后政策、退款说明等，同时包括乙方就信用付款服务指定的特别规则，以下统称“平台规则”。</p>
<p>信用付款服务：指乙方及合作平台为符合条件的用户提供的“分期付款”的信用消费方式的平台服务。</p>
<p>秒趣分期：是信用付款服务的营销推广名称，乙方可根据法规和营销推广的实际情况对该名称进行调整。</p>
<p>用户基本信息：指用户提供的姓名、身份证件号码、身份证件地址、联系方式、家庭地址、单位名称、单位地址，其范围应以乙方平台规则为准。</p>
<p>用户信用信息：指乙方自行收集以及通过第三方合作机构获得的借款人的除借款人基本信息之外的其他信息，如借款人的工作情况、收入情况、家庭情况、信用报告、历史偿债情况等。</p>
<p>用户信息：指用户基本信息、用户信用信息以及乙方和网络借贷服务机构根据该等信息对用户做出的信用评级的合称。</p>
<p>用户信用评级：指乙方和网络借贷服务机构根据用户信息及内部评级规则对甲方进行的信用评级。</p>
<p>消费：指用户在合作平台购买商品或服务并支付款项的行为。</p>
<p>消费本金：指用户在合作平台每一笔消费(根据合作平台系统生成的订单确定) 需要支付的该消费对应价款的金额。用户通过签署本协议同意使用信用付款服务，即认定已向网络借贷服务机构发出借款需求，申请通过网络借贷服务机构平台募集相当于消费本金金额的借款（“借款”）。为避免疑义，用户申请需经网络借贷服务机构审核通过后方可在其平台发起借款募集。</p>
<p>消费记账日：指用户选择使用信用付款服务对当笔消费进行分期付款的起始日期。记账日以合作平台支付结算系统确定的日期为准，用户可以在秒趣分期网站中查阅。</p>
<p>到期付款日：指用户应付款的最晚日期。在分期付款方式下，到期付款日为每一期款项应支付的最晚日期，用户可以在秒趣分期网站中查阅。</p>
<p>借款合同：指对于网络借贷服务机构已审核通过并在网络借贷服务机构平台发起募集的借款，经该网络借贷服务机构撮合，由网络借贷服务机构平台投资人与借款人签订的所有借款合同。为明确之义，借款人可以委托乙方以其代理人的名义代其签署相关借款合同。</p>
<p>借款起算日：指用户借款需求在网络借贷服务机构平台发布后募集期满且符合借款合同约定的日期。</p>
<p>借款还款日：指用户在网络借贷服务机构平台偿还本息的最晚日期，以借款合同约定为准。用户授权其代理人于借款还款日在网络借贷服务机构平台支付借款本息。</p>
<p>分期付款：指用户确定将消费本金分为若干期付款，乙方及其他相关方按照规定收取费用的一种付款方式。</p>
<p>分期付款期数：指用户选择分期付款分摊支付的月数，最长不超过12个月。</p>
<p>分期手续费：指用户选择分期付款时须承担的费用，其中包括：（1）用户在网络借贷服务机构平台借款按照相关借款合同而需向网络借贷服务机构平台投资人支付的利息，（2）用户按照本协议应当向乙方支付的服务费，以及（3）用户按照其与网络借贷服务机构之间的服务协议应当向网络借贷服务机构支付的服务费。每期手续费与当期本金一同收取。</p>
<p>到期应付款：指用户在到期付款日前应支付的全部金额，包括但不限于消费本金、分期手续费、违约金等。</p>
<p>违约金：指用户在到期付款日前未能支付全部到期应付款而导致信用违约时，用户应付但尚未支付的金额为违约金额，乙方有权利向用户按日收取违约服务费用。</p>
<p>提前还款：指协议中约定了甲方的分期付款计划，甲方可能在协议规定的周期结束前，在某一期将剩余应付款全部提前支付。</p>

 <strong>第二条&nbsp; 甲方信用付款事项</strong><br/>
<p>2.1&nbsp; 甲方分期付款本金、期限、手续费：</p>
<p>甲方消费本金金额：【<u><fmt:formatNumber value="${userProtocol.partnerOrderAmount}" pattern="###0.00#" /></u>】元（大写：【 <u>${userProtocol.partnerOrderAmountZh}</u>】）；</p>
<p>分期付款期限：【<u>${userProtocol.instalmentCount}</u>】个月；分期服务费率：【<u><fmt:formatNumber value="${userProtocol.perServiceRate}"   pattern="0.00%" /></u>】（月）；</p>
<p>每期分期服务费金额=消费本金×分期服务费率（月）</p>
<p>每期分期服务费金额：【 <u><fmt:formatNumber value="${userProtocol.perServiceAmount}" pattern="###0.00#" /></u>】元（大写：【 <u>${userProtocol.perServiceAmountZh}</u>】），具体以附件还款计划为准。<p>
<p>2.2&nbsp; 甲方购买商品名称：【 <u>${userProtocol.productName}</u>】。</p>
<p>2.3&nbsp; 甲方消费记账日【<u>  <fmt:formatDate value="${userProtocol.enableTime}" pattern="yyyy" />  </u>】年【 <u>  <fmt:formatDate value="${userProtocol.enableTime}" pattern="MM" /> </u> 】月【 <u>  <fmt:formatDate value="${userProtocol.enableTime}" pattern="dd" />  </u> 】日；首期到期付款日【<u>  <fmt:formatDate value="${userProtocol.firstRepayTime}" pattern="yyyy" />  </u>   】年【<u>   <fmt:formatDate value="${userProtocol.firstRepayTime}" pattern="MM" />   </u>   】月【<u>   <fmt:formatDate value="${userProtocol.firstRepayTime}" pattern="dd" />   </u>    】日。</p>
<p>2.4&nbsp;付款方式：若用户选择分期付款，则视为用户已向乙方及网络借贷服务机构发出借款需求，委托乙方及网络借贷服务机构为其支付消费本金提供借贷撮合服务。<strong>用户签署本协议，即不可撤销地授权乙方以用户的名义全权协助完成其在网络借贷服务机构平台的借款事宜，授权范围包括但不限于：（1）授权乙方以用户名义向网络借贷服务机构平台投资人借款；（2）授权乙方以用户名义在网络借贷服务机构平台开立并管理有关用户账户；（3）授权乙方及网络借贷服务机构公开用户必要的个人信息；（4）授权乙方代替用户签署并保管相关借款合同，并代为确认借款合同约定的借款信息，包括但不限于具体的借款利率、借款还款日等信息；（5）授权乙方代为接收网络借贷服务机构向用户发送的相关信息（包括但不限于相关借款合同电子文本、债权转让通知等）；（6）授权乙方代理用户在网络借贷服务机构平台处理借款相关事宜，包括但不限于代理用户收取借款以及在借款还款日前偿付借款本息。用户应按照本协议附件约定于到期付款日当日18:00前将到期应付款金额足额存入指定付款账户，并且授权乙方作为其代理人按照借款合同约定于借款还款日当日20:00之前将借款本息及应付给网络借贷服务机构的服务费金额足额存至其在网络借贷服务机构平台最新绑定的银行卡账户，该等金额将以从用户存入的到期应付款金额中划扣。</strong></p>
<p>2.5&nbsp;结算凭证：用户选择分期付款时，用户与秒趣结算的依据是秒趣查询系统的查询结果以及银行对账凭证，后者没有记录而前者有记录的，以前者的查询结果为准；两者存在差异的，以银行对账凭证为准。</p>
<p>2.6&nbsp; 甲方分期付款计划：<strong>详见附件《还款计划表》</strong></p>
 <strong>第三条&nbsp; 甲方的权利与义务 </strong><br/>
<p>3.1&nbsp; 甲方在信用付款服务过程中，必须按乙方要求如实提供真实有效的个人信息和记载该信息的证件文本。</p>
<p>3.2 &nbsp;甲方同意乙方或乙方的合作机构在甲方未按时履行付款义务时，使用甲方提供的信息提醒或督促甲方履行付款义务。</p>
<p>3.3 &nbsp;甲方应履行付款文件约定的付款义务，如甲方接到乙方指定的第三方发出的代替乙方履行收款义务的通知，甲方应继续向乙方指定的第三方履行付款义务。</p>
<p>3.4&nbsp; 甲方应按照本协议的规定向乙方支付服务费。</p>
<p>3.5 &nbsp;依据本协议约定，<strong>甲方同意乙方委托的支付机构从甲方指定账户中划扣应支付的款项</strong>。如果实际划扣款项超过本协议约定的金额，甲方有权要求乙方将超过部分退还给甲方。</p>
<p>3.6&nbsp; 甲方同意，如甲方在履行本协议的过程中存在违约情形，乙方可将甲方不良信息予以公开披露或向征信机构提供。</p>
 <strong>第四条&nbsp; 乙方的权利与义务</strong><br/>
<p>4.1&nbsp; 乙方要对甲方提供的有关信息予以保密，但法律法规另有规定、用户予以授权、或本协议另有约定的除外。</p>
<p>4.2&nbsp; 乙方依据本协议约定向甲方收取分期手续费。</p>
<p>4.3&nbsp; 乙方对信用付款服务所承诺的各项服务，需要保证服务质量，并对不符合质量的服务进行整改。</p>
<p>4.4&nbsp; 乙方需明示信用付款服务的功能、使用方法，收费项目、收费标准、适用费率及相关的计算规则。</p>
<p>4.5乙方应督促甲方按照还款计划表及时、足额支付到期应付款，并作为甲方代理人，及时代理甲方在借款还款日前完成还款。若甲方发生逾期付款，乙方应当承担连带责任保证担保义务，及时向网络借贷服务机构及其平台投资人垫付相关款项。</p>
 <strong>第五条&nbsp; 甲方指定账户的变更</strong><br/>
<p>5.1&nbsp;甲方须确保指定划款账户为甲方本人名下合法有效的银行账户。如甲方需要变更该账户，需向乙方平台（www.mqcash.com）进行账户变更，否则甲方需承担因此而产生的未及时付款的违约责任。</p>

 <strong>第六条 违约责任</strong><br/>
<p>6.1&nbsp; 甲方未按照约定于到期付款日当日<strong>18:00</strong>前将应付款金额足额存入指定付款账户的视为逾期，甲方逾期应向乙方支付逾期违约金，违约金计算规则如下：</p>
<p>6.1.1&nbsp; 违约金：每日按照消费本金的【 <u><fmt:formatNumber value="${userProtocol.overdueRate}"   pattern="0.0#%" /></u>】收取违约金；多期还款逾期情形下，针对每期逾期应独立计算违约金，甲方应付违约金为各期独立计算的违约金金额之和；</p>
<p>6.1.2&nbsp; 如因甲方原因导致到期付款日前未能结清当期全部应付款，则按照以上条款执行；</p>
<p>6.1.3&nbsp;  若甲方应付款金额不足，付款先后顺序为催收费用、违约金、应还服务费、应还消费本金。</p>
<p>6.2&nbsp; 无论是否因甲方原因造成，若乙方未按照本协议及借款合同约定代理甲方于借款还款日当日20:00前将应付金额足额存入其在网络借贷服务机构平台开立的账户，则视为甲方逾期，甲方应按借款合同约定向网络借贷服务机构平台投资人支付逾期罚息，并应按其与网络借贷服务机构之间服务协议约定就应付未付服务费支付逾期违约金，乙方应对该等还款义务承担连带责任保证担保义务。</p>
<p>6.3&nbsp; 甲方在信用付款过程中提供的个人身份信息、联系方式、地址、单位信息等保证真实、合法、有效，如有隐瞒欺诈、虚假不实，乙方有权解除本协议，追回应付款，并向司法机关报案。</p>
<p>6.4 &nbsp;因甲方未按本协议约定及时付款而导致乙方发生的催收费用，包括但不限于调查费用、诉讼仲裁费用、律师费用及差旅费用等，概由甲方承担。甲方仍应按本协议约定支付逾期违约金、承担违约责任。</p>
<p>6.5&nbsp; 甲方累计逾期达15天以上的，或累计发生三次逾期的情况下，甲方同意：乙方有权宣布乙方信用付款服务即时提前到期，并要求甲方一次性支付全部剩余服务费和消费本金并按本协议第六条约定承担相应违约责任。</p>
 <strong>第七条&nbsp; 变更通知</strong><br/>
<p>7.1 &nbsp;本协议签订之日起至甲方分期付款全部清偿之日止，甲方有义务在下列信息变更之日起3日内提供更新后的信息给乙方：</p>
<p>7.1.1 &nbsp;甲方工作单位、居住地址、住所电话、手机号码、电子邮箱等；</p>
<p>7.2&nbsp; 因甲方不提供或不及时提供上述变更信息而导致乙方损失的，包括但不限于调查费用、仲裁费用、律师费用及差旅费用等，概由甲方承担。</p>
 <strong>第八条&nbsp; 法律适用与管辖</strong><br/>
<p>8.1 &nbsp;本协议的签订、履行、终止、解释和争议解决均适用中国法律。</p>
<p>8.2&nbsp; 甲、乙双方一致同意，如发生争议，双方应友好协商解决，协商不成的一方均有权向乙方住所地人民法院提起诉讼。</p>
 <strong>第九条&nbsp; 协议效力</strong><br/>
<p>9.1&nbsp; 本协议自甲方点击确认后生效。 </p>
<p>9.2&nbsp; 本协议的任何修改、补充均可以在乙方运营网站（秒趣分期）查询和下载，补充协议与本协议具有同等法律效力。</p>
<p>9.3&nbsp; 本协议部分条款无效或不能履行，不影响其他条款的效力与履行。</p>
<p>（以下无正文）</p>





<strong>附件：&nbsp; 还款计划表</strong><br/>

<p>本还款计划由以下用户于【<u>   <fmt:formatDate value="${userProtocol.enableTime}" pattern="yyyy" />   </u>    】年【<u> <fmt:formatDate value="${userProtocol.enableTime}" pattern="MM" />  </u>  】月【<u>  <fmt:formatDate value="${userProtocol.enableTime}" pattern="dd" />   </u>  】日签署：</p>
	
<p>用户：<u>${userProtocol.userName}</u></p>
<p>身份证号码：<u>${userProtocol.idCard}</u></p>
<p>住所：<u>${userProtocol.homeAddress}</u></p>
<p>联系方式：<u>${userProtocol.userPhone}</u></p>
	
<p>客户签署有编号为【<u>${userProtocol.protocolId}</u>】的《信用付款（秒趣分期）服务协议》，确认该协议下的还款计划内容如下：</p>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;消费本金：【<u><fmt:formatNumber value="${userProtocol.partnerOrderAmount}" pattern="###0.00#" /> </u>】元 &nbsp;&nbsp;&nbsp;&nbsp; 分期期限【<u>${userProtocol.instalmentCount}</u>】个月
 				
   <div>
	<table border="1" cellspacing="0" cellpadding="0" align="left" width="527" style="margin-left:30px;">
	  <tr>
	    <td width="59"><p align="center" >  <strong>期数 </strong></p></td>
	    <td width="99"><p align="center"><strong>到期还款日 </strong></p></td>
	    <td width="104"><p align="center"><strong>还款金额 </strong></p></td>
	    <td width="113"><p align="center"><strong>还款本金 </strong></p></td>
	    <td width="151"><p align="center"><strong>服务费 </strong></p></td>
	   </tr>
		<c:forEach items="${planList}" var="plan" >
		<tr>
	    <td width="59"><p align="center" >  <strong>${plan.instalmentNumber } </strong></p></td>
	    <td width="99"><fmt:formatDate value="${plan.planRepaymentTime}" pattern="yyyy-MM-dd"/> </td>
	    <td width="104"><fmt:formatNumber value="${plan.receivableAmount}"   pattern="###0.00#" /> </td>
	    <td width="113"><fmt:formatNumber value="${plan.receivablePrincipal}"   pattern="###0.00#" /> </td>
	    <td width="151"><fmt:formatNumber value="${plan.receivableService }"   pattern="###0.00#" /> </td>
	   </tr>
	   
	   </c:forEach>

	</table> 
   </div>				


</div>	 

 
</body>
</html>

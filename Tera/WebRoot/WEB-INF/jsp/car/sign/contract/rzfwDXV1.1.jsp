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
<title>融资服务协议JMV1.1</title>

<style type="text/css">
p {
	line-height:29px;		
}
</style>

</head>

<body>

<div align="left" style="position: relative; left:-260px; float: right;">	
	<p style="text-align:center;font-size:24px;"><strong>融资服务协议 </strong></p>
</div>
<div style="position:relative; height:170px; width:170px; float: left ; top:700px; left:500px; " >
	<img src='<%=basePath%>/seal/getSealImage.do?contractNo=${contract.contractNo}'/>
</div>
<div style="clear:both"></div>

<p style="text-align:right;">
  编号：<u>    ${contract.contractNo}-01      </u></p>
<p>本协议由以下三方于<u> ${year}   </u>年<u> ${month}  </u>月<u> ${date}  </u>日在<u>   ${contract.signProvince}    ${contract.signCity}   ${contract.signCounty}  </u>   签署： <br />
<strong>甲方（借款人）：</strong><u>               ${bean.name}                 </u> <br />
  性别：<u>               ${bean.sex}                 </u><br />
  出生年月：<u>               ${appBirthYear}年${appBirthMonth}月${appBirthDay}日                 </u><br />
  身份证号码：<u>               ${bean.idNo}                 </u><br />
  户籍地：<u>               ${bean.kosekiProvince}${bean.kosekiCity}                 </u><br />
  经常居住地：<u>               ${bean.addProvince}${bean.addCity}${bean.addCounty}${bean.address}                 </u><br />
  工作单位：<u>               ${bean.comName}                 </u><br />
  单位担任职务：<u>               ${bean.comDuty}                 </u><br />
  联系方式：<u>               ${bean.mobile}                 </u><br /> 
<c:if test="${bean.email!=''}">  电子邮箱：<u>               ${bean.email}                 </u><br /></c:if>
<c:if test="${bean.marriage=='02'}">
<strong>家庭联系人（或紧急联系人）：</strong><u>               ${bean.spouseName}                 </u> <br />
  性别：<u>               ${spouseSex}                 </u><br />
  出生年月：<u>               ${spouseBirthYear}年${spouseBirthMonth}月${spouseBirthDay}日                 </u><br />
  与甲方关系：<u>               是第2类                 </u>（1、父母，2、配偶，3、子女，4、亲属，5、朋友，6、同事，7、同学，8、邻居，9、其他）<br />
  身份证号码：<u>               ${bean.spouseIdNo}                 </u><br />
  工作单位：<u>               ${bean.spouseComName}                 </u><br />
  职业：<u>               ${bean.spouseJob}                 </u><br />
  联系方式：<u>               ${bean.spouseMobile}                 </u><br />
</c:if>
<c:if test="${bean.marriage!='02'}">
<strong>家庭联系人（或紧急联系人）：</strong><u>               ${carContact.name}                 </u> <br />
  与甲方关系：<u>               是第${carContact.relation}类                 </u>（1、父母，2、配偶，3、子女，4、亲属，5、朋友，6、同事，7、同学，8、邻居，9、其他）<br />
  年龄：<u>               ${carContact.age}                 </u><br />
  工作单位：<u>               ${carContact.comName}                 </u><br />
  联系方式：<u>               ${carContact.mobile}                 </u><br />
</c:if>
<strong>乙方：</strong><u>                ${companyName}                      </u><br />
  法定代表人：<u>                高华勇                          </u><br />
  地址：<u>                北京市朝阳区永安里东路16号CBD国际大厦611室                          </u><br />
  邮编：<u>           100022            </u><br />
<strong>丙方：</strong><u>               鼎轩资产管理（上海）有限公司                    </u><br />
  法定代表人：<u>                谢晓东                          </u><br />
  地址：<u>                上海市长宁区中山西路1065号SOHO中山广场A座1004                          </u><br />
  邮编：<u>           200051            </u><br />
</p>
<p align="left"><strong>鉴于： </strong><br />
  1. 甲方有借款需求； <br />
  2. 乙方为甲方提供借款信息咨询，对甲方的资信状况进行综合评估并推荐甲方在丙方平台向出借人发起借款请求，满足资金需求，并提供还款提醒、管理等居间服务；<br />
  3. 丙方为一家资产管理公司，提供金融信息咨询及撮合交易等相关居间服务。<br />
  甲方同意接受乙方、丙方提供的融资相关服务并与乙方、丙方达成如下一致意见：<br />
  <strong>第一条	甲方融资事项 </strong><br />
  1.1 甲方有意愿经由乙方的推荐在丙方发起借款，拟借款金额为人民币：<u>     ￥<fmt:formatNumber value="${contract.loanAmount}" pattern="#,#00.00"/>     </u>元（大写：<u>     ${loanAmount}     </u>元整）。即甲方同意丙方将本条约定的甲方拟借款金额作为一个甲方借款需求（“借款标”）发布在丙方平台上进行展示并从出借人（出借人信息以丙方提供的出借人名单为准）处获得借款。 <br />
</p>
<div style="position: relative; height:170px;width:170px; top:100px; left:0px; float: right;" >
    <img src='<%=basePath%>/seal/getSealImage.do?contractNo=${contract.contractNo}'/>
</div>
<div align="left" style="position: relative; top: -35px; left:0px; float: left;">    
    <p align="left">
  1.2 甲方借款期限：<br />
  借款合同期限：<u> ${decision.period } </u>   个月，具体以借款文件约定为准。<br />
  1.3 甲方借款用途：<u> ${bean.useage1}  ${bean.useage2}   </u> <br />
  甲方利率及偿还本金安排：具体以借款文件约定为准。<br />
  甲方借款其他事项：具体以借款文件约定为准。<br />
  <strong>第二条 甲方的权利与义务</strong><br />
    </p>
</div>
<div style="clear:both"></div> 
<p>
  2.1 甲方在借款过程中，必须如实向乙方提供必要的真实有效的个人信息和记载该信息的证件文本。<br />
  2.2 甲方同意乙方或乙方的合作机构在甲方未按时履行还款义务时，使用甲方提供的信息提醒或督促甲方履行还款义务。<br />
  2.3 甲方应履行“借款文件”约定的还款义务；如甲方接到出借人或其代理人发出的债权转让通知，甲方应继续向债权受让人履行“借款文件”的还款义务。<br />
  2.4 如甲方需提前还款，应提前三个工作日向乙方提出书面申请（还款日当日及节假日期间不受理），通过乙方与出借人协商提前还款时间，甲方应在约定的时间前将相应款项存入指定还款账户。<br />
  2.5 甲方应按照本协议的规定向乙方、丙方支付服务费。<br />
  2.6 依据“借款文件”及本协议，甲方同意乙方委托的支付机构从甲方指定账户中划扣应偿还或支付的款项；如果实际划扣款项超过“借款文件”或本协议约定的金额，甲方有权要求乙方将超过部分退还给甲方。<br />
  2.7 甲方同意，如甲方在履行“借款文件”的过程中存在违约情形，乙方可将甲方不良信息予以公开披露或向征信机构提供。<br />
  2.8 甲方理解并接受乙方促成交易过程中可能将其信息提供给多个出借人，而获取甲方个人信息的出借人可能拒绝出借。<br />
  2.9 甲方须确保“借款文件”指定收款账户为甲方名下合法有效的银行账户。<br />
  <strong>第三条 乙方的权利与义务</strong><br />
  3.1 乙方须为甲方提供借款方案和全程信息咨询服务，并在甲方申请借款服务过程中协助其办理各项手续。<br />
  3.2 对于甲方在接受服务时向乙方提供的个人资料及其他各类信息，乙方应依法为甲方保密。<br />
  3.3 乙方应根据甲方的信用状况提出借款方案，决定是否将甲方的全部或部分借款需求向出借人推荐，以协助达成交易。<br />
</P>
<div style="position: relative; height:170px;width:170px; top:150px; left:0px; float: right;" >
    <img src='<%=basePath%>/seal/getSealImage.do?contractNo=${contract.contractNo}'/>
</div>
<div align="left" style="position: relative; top: -35px; left:0px; float: left;">    
    <p align="left">
  3.4 乙方依据本协议向甲方收取服务费。<br />
  3.5 乙方应妥善保管甲方在借款交易过程中签署的法律文件。<br />
  <strong>第四条 丙方的权利与义务 </strong><br />
  4.1 在丙方平台上发布经乙方推荐的甲方借款需求。<br />
      </p>
</div>
<div style="clear:both"></div> 
<p>
  4.2 丙方依据融资协议的约定向甲方、乙方、出借人等提供居间服务。<br />
  4.3 丙方依据本协议向甲方收取服务费。<br />
  <strong>第五条 服务费及支付方式</strong><br />
  5.1 甲方应付服务费金额如下：<br />
  5.1.1 甲方应向乙方支付服务费人民币（大写） <u>     ${serviceSumAmountUpperCase}     </u>元，（小写） <u>     ￥<fmt:formatNumber value="${serviceSumAmountLowerCase}" pattern="#,#00.00"/>     </u>；甲方应向丙方支付服务费人民币（大写）<u>     ${jiaToBingjeUpperCase }     </u>元，（小写） <u>     ￥<fmt:formatNumber value="${jiaToBingjeLowerCase}" pattern="#,#00.00"/>     </u>。<br />
  5.2 甲方同意，所有服务费在甲方取得借款前一次性付清，由丙方从出借人提供给甲方的借款中直接划转相当于甲方应支付给乙方、丙方的服务费的金额至乙方、丙方有关账户。<br />
  5.3 无论何种原因，乙方、丙方已收取的服务费不予退还。<br />
  <strong>第六条 甲方指定账户的变更</strong><br />
  6.1 甲方须确保“借款文件”指定收款账户和还款账户为甲方名下合法有效的银行账户。如甲方需要变更指定还款账户，须在还款日前至少5个工作日前向乙方提出申请，并签署《借款人客户信息变更书》。否则甲方须承担因此而产生的未及时还款的违约责任。<br />
  6.2 在还款过程中，甲方有义务配合乙方为保证还款而进行的包括但不限于账户验证、账户变更、身份证验证等事项，因甲方不配合而造成的未能正常还款的违约责任，概由甲方承担。<br />

  <strong>第七条 委托条款</strong><br />
&nbsp; &nbsp; 为妥善完成服务事项，顺利取得资金支持，甲方特此委托：<br />
&nbsp; &nbsp; 委托并授权乙方向丙方披露甲方的与借款行为相关的全部信息，包括但不限于姓名、身份证明、通讯地址、征信情况、资产状况、涉诉情况等，并同意丙方将该等信息展示、告知出借人。<br />
</p>
<div style="position: relative; height:170px;width:170px; top:100px; left:0px; float: right;" >
    <img src='<%=basePath%>/seal/getSealImage.do?contractNo=${contract.contractNo}'/>
</div>
<div align="left" style="position: relative; top: -35px; left:0px; float: left;">    
    <p align="left">
&nbsp; &nbsp; 委托并授权丙方代理其办理与借款相关的在丙方平台展示及签约、开户、借款、提现、还款、解约等事项。<br />
&nbsp; &nbsp; 委托并授权丙方自出借人提供的借款中收取本协议第五条约定的相关费用，并代为将上述费用交付相应的收费方。<br />
    </p>
</div>
<div style="clear:both"></div> 
<p>
&nbsp; &nbsp; 委托并授权乙方自甲方指定银行账户划转还款资金至出借人账户，乙方的划转交付行为即视为甲方对出借人履行相应的还款义务，直至甲方的债务全部清偿完毕。<br />
  <strong>第八条 违约责任</strong><br />
&nbsp; &nbsp; 8.1 任何一方违反本协议的约定，导致本协议的全部或部分不能履行，均应承担违约责任，并赔偿守约方因此遭受的损失；如甲、乙、丙三方均违约，根据实际情况各自承担相应的责任。<br />
&nbsp; &nbsp; 8.2 甲方在借款过程中提供的个人身份信息、联系方式、单据、介绍信、印章或者其他证明文件应保证真实、合法、有效，如有隐瞒欺诈、虚假不实，应予补正；情节严重的，乙方有权解除本协议，追回欠款，并向司法机关报案。<br />
&nbsp; &nbsp; 8.3 因甲方未按“借款文件”约定及时还款而导致乙方发生的催收费用，包括但不限于调查费用、仲裁费用、律师费用及委托其他公民仲裁所产生的差旅费用等，概由甲方承担，甲方仍应按“借款文件”约定支付逾期利息、承担违约责任。<br />
&nbsp; &nbsp; 8.4 甲方单次或累计逾期达15天以上的、连续或累计发生三次逾期的的情形下，甲方同意：乙方有权宣布甲方债权即时提前到期，并要求甲方一次性清偿全部剩余本息。<br />
  <strong>第九条 变更通知</strong><br />
&nbsp; &nbsp; 9.1 本协议签订之日起至借款全部清偿之日止，甲方有义务在下列信息变更三日内提供更新后的信息给乙方：<br />
&nbsp; &nbsp; 9.1.1 甲方工作单位、居住地址、住所电话、手机号码、电子邮箱等；<br />
&nbsp; &nbsp; 9.1.2 甲方家庭联系人或紧急联系人工作单位、居住地址、住所电话、手机号码等。<br />
&nbsp; &nbsp; 9.2 因甲方不提供或不及时提供上述变更信息而导致乙方产生的损失，包括但不限于调查费用、仲裁费用、律师费用及委托其他公民仲裁所产生的差旅费用等，概由甲方承担。<br />
</p>
<div style="position: relative; height:170px;width:170px; top:100px; left:0px; float: right;" >
    <img src='<%=basePath%>/seal/getSealImage.do?contractNo=${contract.contractNo}'/>
</div>
<div align="left" style="position: relative; top: -35px; left:0px; float: left;">    
    <p align="left">
  <strong>第十条 法律适用与管辖</strong><br />
&nbsp; &nbsp; 10.1 本协议适用中国法律。<br />
&nbsp; &nbsp; 10.2 甲、乙、丙三方一致同意，如发生争议，不论争议金额大小，均提交北京仲裁委员会适用北京仲裁委员会仲裁规则项下的简易程序进行仲裁。仲裁裁决为终局的，对各方均有拘束力。<br />
    </p>
</div>
<div style="clear:both"></div> 
<p>
  <strong>第十一条 其他</strong><br />
&nbsp; &nbsp; 甲方确认：就乙方、丙方为甲方和出借人之间的借款事项提供的居间服务，甲方在此知悉并确认乙方、丙方并非甲方和出借人之间借款法律关系的一方，乙方、丙方对于甲方和出借人之间的借款事项不承担任何法律责任。<br />
  <strong>第十二条 协议效力</strong><br />
&nbsp; &nbsp; 12.1 本协议自甲、乙、丙三方签字或盖章后成立并生效。<br />
&nbsp; &nbsp; 12.2 本协议及其附件的任何修改、补充均须以书面形式作出，补充协议与本协议具有同等法律效力。<br />
&nbsp; &nbsp; 12.3 本协议的传真件、复印件和扫描件等有效复本的效力与本协议原件效力一致。<br />
&nbsp; &nbsp; 12.4 本协议部分条款无效或不能履行，不影响其他条款的效力与履行。<br />
&nbsp; &nbsp; 12.5 本协议一式三份，三方各执壹份。<br />
  （以下无正文） </p>
<div style="position: relative; height:170px; top: 50px; left:-450px; float: right;" >
	<img src='<%=basePath%>/seal/getSealImage.do?contractNo=${contract.contractNo}'/>
</div>
<div align="left" style="position: relative; top: -35px; left:0px; float: left;">    
    <p align="left">
甲方（签字）：<br /><br /><br />
乙方（盖章）：<br /><br /><br />
    </p>
</div>
<div style="clear:both">
	丙方（盖章）：<br /><br /><br />
</div>
</body>
</html>
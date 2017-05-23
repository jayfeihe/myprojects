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
<title>融资服务协议V1.1</title>

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
<p>本协议由以下双方于<u> ${year}   </u>年<u> ${month}  </u>月<u> ${date}  </u>日在<u>   ${contract.signProvince}    ${contract.signCity}   ${contract.signCounty}  </u>   签署： <br />
  甲方（借款人）：<u>               ${bean.name}                 </u> <br />
  身份证号码：<u>               ${bean.idNo}                 </u><br />
  乙方：<u>                 ${companyName}                         </u><br />
  地址：<u>                北京市朝阳区永安里东路16号CBD国际大厦611                          </u><br />
  邮编：<u>           100022            </u></p>
<p align="left"><strong>鉴于： </strong><br />
  1.甲方有借款需求； <br />
  2.乙方为甲方提供借款信息咨询，并在甲方申请借款过程中协助其办理各项手续； <br />
  3.乙方为甲方提供推荐出借人、促成交易，以及还款提醒管理等服务。 <br />
  甲方同意接受乙方的服务并与乙方达成如下一致意见： <br />
  <strong>第一条 释义 </strong><br />
  1.1 服务费：指乙方为甲方提供出借人推荐、还款提醒等服务，并在甲方成功获得借款后而由甲方支付给乙方的报酬。 <br />
  1.2 《借款协议》：指甲方于<u>   ffcx${year}   </u>年<u> ${month}  </u>月<u> ${date}  </u>日与乙方推荐的出借人签署的全部《借款协议》（借款金额合计为人民币（大写）<u>     ${loanAmount}     </u>元，（小写）<u>     ￥<fmt:formatNumber value="${contract.loanAmount}" pattern="#,#00.00"/>     </u>）。 <br />
  1.3 出借人：指与甲方签署《借款协议》的出借人。 <br />
  1.4 支付机构：指在本协议双方之间作为中介机构提供资金转移服务的银行或非金融机构。 <br />
  <strong>第二条 甲方的权利与义务 </strong><br />
  2.1 甲方在借款过程中，必须如实向乙方提供必要的个人信息。 <br />
  2.2 甲方同意乙方或乙方合作机构在甲方未按时履行还款义务时，使用甲方提供的信息提醒或督促甲方履行还款义务。 <br />
  2.3 《借款协议》生效后，甲方应履行《借款协议》约定的还款义务；如甲方接到出借人或其代理人发出的债权转让通知，甲方应继续向债权受让人履行《借款协议》的还款义务。 <br />
  2.4 如甲方需提前还款，应提前三个工作日向乙方提出书面申请（还款日当日及节假日期间不受理），通过乙方与出借人协商提前还款时间，甲方应在约定的时间前将相应款项存入指定还款账户。 <br />
  2.5 甲方应按照本协议的规定向乙方支付咨询费及服务费。 <br />
  2.6 依据《借款协议》及本协议，甲方同意乙方委托的支付机构从甲方指定账户中划扣应偿还或支付的款项；如果实际划扣款项超过《借款协议》或本协议约定的金额，甲方有权要求乙方将超过部分退还给甲方。 <br />
  2.7 甲方同意，如甲方在履行《借款协议》的过程中存在违约情形，乙方可将甲方不良信息予以公开披露或向征信机构提供。 <br />
  2.8 甲方理解并接受乙方促成交易过程中可能将其信息提供给多个出借人，而获取甲方个人信息的出借人可能拒绝出借。 <br />
  2.9 甲方须确保《借款协议》指定收款账户为甲方名下合法有效的银行账户。 <br />
  <strong>第三条 乙方的权利与义务 </strong><br />
  3.1 乙方须为甲方提供借款方案和全程信息咨询服务，并在甲方申请借款服务过程中协助其办理各项手续。 <br />
  3.2 对于甲方在接受服务时向乙方提供的个人资料及其他各类信息，乙方应依法为甲方保密。 <br />
  3.3 乙方应根据甲方的信用状况提出借款方案，决定是否将甲方的全部或部分借款需求向出借人推荐，以协助达成交易。 <br />
</p>
<div align="left" style="position: relative; top: -35px; left:0px; float: left;">    
	<p align="left">
  3.4 乙方依据本协议向甲方收取咨询费、融资服务费及账户管理费。 <br />
  3.5 乙方应妥善保管甲方在借款交易过程中签署的法律文件。 <br />
  <strong>第四条 服务费及支付方式 </strong><br />
  4.1 甲方应付服务费金额如下： <br />
  4.1.1 应向乙方支付服务费 <br />
	</p>
</div>
<div style="position: relative; height:170px; top:0px; left:0px; float: right;" >
    <img src='<%=basePath%>/seal/getSealImage.do?contractNo=${contract.contractNo}'/>
</div>
<div style="clear:both"></div> 
<p>
  人民币（大写）<u>     ${djfwfjeUpperCase}     </u>元（（小写）<u>     ￥<fmt:formatNumber value="${djfwfjeLowerCase}" pattern="#,#00.00"/>     </u>）。 <br />
  4.2 服务费由甲方委托出借人从出借资金中代为支付。 <br />
  4.3 如甲方与出借人对于提前款款事项达成一致协议，乙方应根据甲方提前还款日期的不同向甲方退还部分服务费，具体如下： <br />
  4.3.1 如甲方于《借款协议》约定的第四个还款日（含第四个还款日）还款，乙方应退还费用总计人民币（大写）<u>     ${fhfwfUpperCase}     </u>元（（小写）<u>     ￥<fmt:formatNumber value="${fhfwfLowerCase}" pattern="#,#00.00"/>     </u>）； <br />
  4.3.2 如甲方于《借款协议》约定的第四个还款日后还款，乙方退还费用应以本协议中4.3.1约定的金额为基础，每月递减人民币（大写）<u>     ${fwfdjUpperCase}     </u>元（（小写）<u>     ￥<fmt:formatNumber value="${fwfdjLowerCase}" pattern="#,#00.00"/>     </u>），直至退还费用总计金额为零。 <br />
  <strong>第五条 甲方指定账户的变更 </strong><br />
  5.1  甲方须确保《借款协议》指定收款账户和还款账户为甲方名下合法有效的银行账户。如甲方需要变更指定还款账户，须在还款日前至少5个工作日前向乙方提出申请，并签署《借款人客户信息变更书》。否则甲方须承担因此而产生的未及时还款的违约责任。 <br />
  5.2 在还款过程中，甲方有义务配合乙方为保证还款而进行的包括但不限于账户验证、账户变更、身份验证等事项，因甲方不配合而造成的未能正常还款的违约责任，概由甲方承担。 <br />
  <strong>第五条 违约责任 </strong><br />
  6.1 任何一方违反本协议的约定，导致本协议的全部或部分不能履行，均应承担违约责任，并赔偿守约方因此遭受的损失；如甲、乙双方均违约，根据实际情况各自承担相应的责任。 <br />
  6.2 因甲方未按《借款协议》约定及时还款而导致乙方发生的催收费用，包括但不限于调查费用、仲裁费用、律师费用及委托其他公民仲裁所产生的差旅费用等，概由甲方承担。 <br />
  <strong>第七条 变更通知 </strong><br />
  7.1 本协议签订之日起至借款全部清偿之日止，甲方有义务在下列信息变更三日内提供更新后的信息给乙方： <br />
</p>
<div style="position: relative; height:170px;width:170px; top:0px; left:0px; float: right;" >
    <img src='<%=basePath%>/seal/getSealImage.do?contractNo=${contract.contractNo}'/>
</div>
<div align="left" style="position: relative; top: -35px; left:0px; float: left;">    
	<p align="left">
  7.1.1 甲方工作单位、居住地址、住所电话、手机号码、电子邮箱等； <br />
  7.1.2 甲方家庭联系人或紧急联系人工作单位、居住地址、住所电话、手机号码等。 <br />
	</p>
</div>
<div style="clear:both"></div> 
<p>  
  7.2 因甲方不提供或不及时提供上述变更信息而导致乙方产生的损失，包括但不限于调查费用、仲裁费用、律师费用及委托其他公民仲裁所产生的差旅费用等，概由甲方承担。 <br /><br />
  <strong>第八条 法律适用与管辖 </strong><br />
  8.1 本协议适用中国法律。 <br />
  8.2 甲、乙双方一致同意，如发生争议，不论争议金额大小，均提交北京仲裁委员会适用北京仲裁委员会仲裁规则项下的简易程序进行仲裁。仲裁裁决为终局的，对各方均有拘束力。 <br />
  <strong>第九条 其他 </strong><br />
  9.1 本协议自甲、乙双方签字或盖章后成立并生效。 <br />
  9.2 本协议及其附件的任何修改、补充均须以书面形式作出，补充协议与本协议具有同等法律效力。 <br />
  9.3 本协议的传真件、复印件和扫描件等有效复本的效力与本协议原件效力一致。 <br />
  9.4 本协议部分条款无效或不能履行，不影响其他条款的效力与履行。 <br />
  9.5 本协议一式三份，甲、乙双方各执壹份。 <br />
  （以下无正文） </p>
<div style="position: relative; height:170px; top: -50px; left:500px; float: left;" >
	<img src='<%=basePath%>/seal/getSealImage.do?contractNo=${contract.contractNo}'/>
</div>
<div align="left" style="position: relative; left:-50px; float: right;">	
	甲方（签字）：
		&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
		&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
		&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
		&nbsp; &nbsp; &nbsp;
		乙方（盖章）：
</div>
<div style="clear:both"></div>
</body>
</html>
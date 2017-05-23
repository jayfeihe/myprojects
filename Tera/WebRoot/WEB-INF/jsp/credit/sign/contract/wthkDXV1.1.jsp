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
<title>委托划扣授权书JMV1.1</title>

<style type="text/css">
p {
	line-height:29px;		
}
</style>

</head>

<body>
<p style="text-align:left;">合同编号：<u>    ${contract.contractNo}-03     </u></p>
<p style="text-align:center;font-size:24px;"><strong>委托划扣授权书 </strong><br />
  </p>
<p><strong>委托人：</strong><u>                 ${bean.name}                      </u><br />
 <strong> 身份证号：</strong><u>             ${bean.idNo}                      </u><br />
  <strong>联系电话：</strong><u>             ${bean.mobile}                      </u><br />
  <strong>受托人：</strong><u>                  鼎轩资产管理（上海）有限公司                     </u><br />
  <strong>工商局注册号码：</strong><u>                  310115001852457                       </u><br />
  &nbsp; &nbsp; <strong>委托事项：</strong>
根据委托人与受托人于<u>  ${year}   </u>年<u> ${month} </u>月<u> ${date} </u>日签订的编号为<u>     ${contract.contractNo}-01     </u>的《融资服务协议》以及编号为<u>     ${contract.contractNo}     </u>的《借款声明书》的约定，委托人委托并授权受托人通过支付机构（包括但不限于银行及其他第三方支付机构）从委托人以下账户划扣上述协议中约定的所有相关款项的金额。此次授权自本授权书签署之日起至委托人签署的《融资服务协议》、《借款声明》项下全部义务履行完毕之日止。<br />
   &nbsp;  委托人扣款账户为:<br />
  户名：<u>              ${contract.bankAccountName}                  </u><br />
  开户行：<u>             ${contract.bankProvince} ${contract.bankCity} ${contract.bankCounty} ${contract.bankName} ${contract.bankBranch}                 </u><br />
账号：<u>              ${contract.bankAccount}                  </u></p>
<br/>
<table width="661px" border="0">
  <tr>
    <td width="295px" align="right" valign="bottom">
    	<p align="left">借款人签字： <u>       &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;     </u></p>
    	<p align="left">签署日期：<u> &nbsp;&nbsp;&nbsp; &nbsp; &nbsp; </u>年<u>&nbsp; &nbsp; &nbsp;</u> 月 <u>&nbsp; &nbsp; &nbsp;</u>日 </p>
    </td>
  </tr>
</table>
<p>附件1：委托人身份证复印件(正反面)<br />
附件2：支付授权单(委托人签字确认)</p>

</body>
</html>
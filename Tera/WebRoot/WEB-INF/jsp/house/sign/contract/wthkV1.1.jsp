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
<title>委托划扣授权书V1.1</title>

<style type="text/css">
p {
	line-height:29px;		
}
</style>

</head>

<body>
<p style="text-align:center;font-size:24px;"><strong>委托划扣授权书 </strong><br />
  </p>
<p style="text-align:right;">编号：<u>    ${contract.contractNo}-03     </u></p>
<p>委托人：<u>                 ${bean.name}                      </u><br />
  身份证号码：<u>             ${bean.idNo}                      </u><br />
  受托人：<u>                  ${companyName}                     </u><br />
  地址：<u>                  北京市朝阳区永安里东路16号CBD国际大厦611                       </u><br />
  委托人于<u>  ${year}   </u>年<u> ${month} </u>月<u> ${date} </u>日签署《借款咨询与服务协议》（编号：<u>     ${contract.contractNo}-01     </u>）、《借款协议》（编号：<u>     ${contract.contractNo}     </u>）。为履行上述协议的付款义务，委托人现授权受托人通过支付机构从委托人所属以下账户中扣划上述协议约定的款项。 <br />
  户名：<u>              ${contract.bankAccountName}                  </u><br />
  开户行：<u>             ${contract.bankProvince} ${contract.bankCity} ${contract.bankCounty} ${contract.bankName} ${contract.bankBranch}                 </u><br />
账号：<u>              ${contract.bankAccount}                  </u></p>
<table width="661px" border="0">
  <tr>
    <td width="356px">
	    <table width="309px" height="347px" style="border-style:dashed; border-width:1px; border-color:#000000;">
	      <tr>
	        <td><p style="text-align:center;">银行划扣回单 </p>
	          <p>&nbsp;</p>
	          <p style="text-align:center;">粘</p> 
	          <p style="text-align:center;">贴</p> 
	          <p style="text-align:center;">处 </p>
	           <p>&nbsp;</p>
	            <p>&nbsp;</p>
	             <p>&nbsp;</p>
	             <p>&nbsp;</p>
	             <p>&nbsp;</p>
	          </td>
	      </tr>
	    </table>
    </td>
    <td width="295px" align="center" valign="bottom">
    	<p align="left">委托人（签字） ： <u>       &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;     </u></p>
    	<p align="left">签署日期：<u> &nbsp;&nbsp;&nbsp; &nbsp; &nbsp; </u>年<u> &nbsp;&nbsp; </u> 月 <u> &nbsp;&nbsp; </u>日 </p>
    </td>
  </tr>
</table>
<p>&nbsp;</p>

</body>
</html>
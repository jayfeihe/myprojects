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
<title>委托提现授权书V1.1</title>

<style type="text/css">
p {
	margin: 15px 0;		
}
</style>

</head>

<body>
<p><strong>合同编号：${contract.contractNo}-04 </strong></p>
<p style="text-align:center;font-size:24px;"><strong>委托提现授权书 </strong><br />
  </p>
<p><strong>委托人：</strong><u>                 ${bean.name}                      </u></p><br />
<p><strong>身份证号码：</strong><u>             ${bean.idNo}                      </u></p><br />
<p><strong>联系电话：</strong><u>             ${bean.mobile}                      </u></p><br />
<p><strong>受托人：</strong><u>                  鼎轩资产管理（上海）有限公司                    </u></p><br />
<p><strong>工商局注册号码：</strong><u>                   310115001852457                       </u></p><br />
<p>
<strong>委托事项：</strong><br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;根据委托人与受托人于<u>  ${year}   </u>年<u> ${month} </u>月<u> ${date} </u>日签定的编号为：<u>     ${contract.contractNo}-01     </u>的《融资服务协议》的约定，委托人授权受托人通过支付机构（包括但不限于银行及其他第三方支付机构）从委托人以下账户进行上述协议约定的所有相关款项的金额提现操作。
</p><br />
<p><strong>委托人提现账户信息：</strong></p><br />
<p>户名：<u>              ${contract.bankAccountName}                  </u></p><br />
<p>开户行：<u>             ${contract.bankProvince} ${contract.bankCity} ${contract.bankCounty} ${contract.bankName} ${contract.bankBranch}                 </u></p><br />
<p>账号：<u>              ${contract.bankAccount}                  </u></p>

<p>&nbsp;&nbsp;&nbsp;&nbsp;</p>
<p style="margin: 0">&nbsp;&nbsp;&nbsp;&nbsp;</p>

<p align="left">委托人（签字） ： <u>       &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;     </u></p>
<div style="float: right;"><u> &nbsp;&nbsp;&nbsp; &nbsp; &nbsp; </u>年<u> &nbsp;&nbsp; </u> 月 <u> &nbsp;&nbsp; </u>日 </div>
<div style="clear: both;"></div>

<p>&nbsp;&nbsp;&nbsp;&nbsp;</p>

<p>附件1：委托人身份证复印件(正反面)</p>

</body>
</html>
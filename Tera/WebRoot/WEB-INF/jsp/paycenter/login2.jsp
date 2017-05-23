<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%@ page import="java.net.URLDecoder" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>"/>
<title>P2P综合业务系统</title>
<link href="css/global.css" type="text/css" rel="stylesheet"/>
<link href="css/icon.css" type="text/css" rel="stylesheet"/>
<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
<script src="js/jquery.min.js" type="text/javascript"></script>
<script src="js/jquery.form.js" type="text/javascript"></script>
<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script language="JavaScript"> 
if (window != top) 
top.location.href = location.href; 
</script> 
<style type="text/css">
<!--
html{height:100%;}
body{font-family:Arial, Helvetica, sans-serif;background: url('img/logoinbg3.jpg') center no-repeat;height: 100%;}

.wrapper{position:relative; display:block;margin:0px auto;width:974px; height:450px;top:50%;left:50%;margin-left:-487px;margin-top:-195px;}
.main-container{float:left; width:974px; height:auto;}
.main-container .t-t{float:left;/*background:url(img/t-bg.gif) repeat-x;*/ width:974px; height:50px;}
.main-container .t-t span.tt-l-corner{ float:left;/* background:url(img/t-l-corner.gif) no-repeat left; */width:15px; height:50px;}
.main-container .t-t span.tt-r-corner{float:right;/* background:url(img/t-r-corner.gif) no-repeat right;*/ width:15px; height:50px;}
.main-container .t-b{float:left;/*background:url(img/b-bg.gif) repeat-x; width:974px; height:15px;}
.main-container .t-b span.tb-l-corner{ float:left; background:url(img/b-l-corner.gif) no-repeat left; */width:15px; height:15px;}
.main-container .t-b span.tb-r-corner{float:right; /*background:url(img/b-r-corner.gif) no-repeat right; */width:15px; height:15px;}

.main-container .login-mainarea{ float:left;width:972px; height:auto;/* background: #FFF;border-left:1px solid #d2d2d2;border-right:1px solid #d2d2d2; */ padding:0px 0px 5px 0px;}

.graybox-wrapper{position:relative;margin:0px auto; width:716px; top:10px;}
.graybox-container{float:right; width:316px; height:auto;}
.graybox-container .gb-t{float:left;width:316px; text-align: center;
	font-size: 26px; 
	font-weight:bold; 
	font-style:微软雅黑;padding:20px 0px;/* background:url(img/gray-t-line.gif) repeat-x;*/}
.graybox-container .gb-t span.gb-t-l{float:left; width:20px; height:20px;/* background:url(img/gray-b-l-t.gif) no-repeat left;*/}
.graybox-container .gb-t span.gb-t-r{float:right; width:20px; height:20px;/* background:url(img/gray-b-r-t.gif) no-repeat right;*/}

.graybox-container .gb-m{float:left;width:314px; height:auto; /*background:#f3f3f5;border-left:1px solid #d2d2d2;border-right:1px solid #d2d2d2;*/}
.graybox-container .gb-m .loginform{float:left; padding:0px 20px 5px 20px; width:276px; height:auto; font-size:13px;}

.graybox-container .gb-b{float:left;width:316px; height:20px; /*background:url(img/gray-b-line.gif) repeat-x; */padding-bottom:5px;}
.graybox-container .gb-b span.gb-b-l{float:left; width:20px; height:20px; /*background:url(img/gray-b-l-b.gif) no-repeat left;*/}
.graybox-container .gb-b span.gb-b-r{float:right; width:20px; height:20px;/* background:url(img/gray-b-r-b.gif) no-repeat right;*/}

.graybox-info{ float:left;padding:10px 20px 5px 20px; width:275px; height:auto; margin-top:5px;} 

.footer-info{float:left;padding:30px 15px 0px 15px; width:100%;position:relative;top:-40px; height:auto;font-family: Arial, Helvetica, sans-serif; font-size: 10px; color: #666;}
.footer-info div{border-top:1px solid #E4E4E4; padding-top:10px;}

.inputtext{
	width: 180px;
}
.labeltext{
	font-weight: bold;
}
.footer{
	text-align: center;
}
.footer .btntxt{margin-top:5px;width:65px;height:26px;border:none;cursor:pointer;background:url(img/201407070019.png) center no-repeat;color:#333;}
.footer .btntxt:hover{color:#333;font-weight:bold;}
.systemTitle{
	text-align: center;
	padding-left:30px;
	font-size: 30px; 
	font-weight:bold; 
	font-style:微软雅黑;
}
.logopic{position:relative;float:left;left:0px;top:95px;width:339px;height:83px;}
.xian{width:2px;height:430px;float:left;background:url(img/201407081514.png) center no-repeat;position:relative;left:28px;top:-30px;}
.tabs-header{border-bottom:1px #FDC973 solid !important;background:none;}
.tabs-panels{margin-top:20px;border:none;}
.loginform table tr td{padding:10px 0px;}
.tabs{border:none;padding-left:21px;}
.tabs-inner{border:none;margin-top:1px !important;}
.tabs-selected a{background:#EADD7F !important;border-color:#EADD7F !important;}
-->
</style>
</head>
<body style="">
<div class="wrapper">
	<div class="main-container">
		<div class="t-m">
			<div class="login-mainarea">
			<div class="systemTitle" style="display:none;">
			P2P综合业务客户系统
			</div>
			
				<div class="graybox-wrapper">
					<div class="logopic"><img src="img/logopic.gif" /></div>
					
					<div class="xian"></div>
					<div class="graybox-container">

						<div class="gb-t">客户支付系统</div>

						<div class="gb-m">
							<div class="easyui-tabs">
								<div class="loginform" title="个人客户" name="myselfinfo"  >
									<form method="post" action="<%=basePath%>paycenter/login.htm" name="LoginFormPer" id="LoginFormPer">
										<input type="hidden" name="userType" value="01" />
										<table>
										<tr>
										<td><label class="labeltext" for="name">姓　　名:</label></td>
										<td><input class="easyui-validatebox textbox"  type="text" 
										id="userName" name="userName" title="请输入用户名" data-options="required:true"/></td>
										</tr>
										<tr>
										<td><label class="labeltext" for="idType">证件类型:</label></td>
										<td>
										<input class="easyui-combobox" name="idType" 
											id="idType" 
											data-options="required:true" 
											style="width:152px;" editable="false"  value="01" />
										</td>
										</tr>
										<tr>
										<td><label class="labeltext" for="idNo">证件号码:</label></td>
										<td id="ort">
											<input id="idNo" name="idNo" type="text" 
											data-options="required:true,validType:['length[0,18]','idNo']" 
											class="textbox easyui-validatebox" 
											/>
										</td>
										</tr>
										</table>
										<div class="footer">
											<span><input id="btnSubmit" class="btntxt" type="submit" name="btnSubmit" value="登录" title="请单击此处登录"/></span>
											<span><input id="btnReset" class="btntxt"  type="button" onclick="$('#LoginFormPer').form('clear');" name="btnReset" value="重置" title="请单击重新输入"/></span>	
										</div>
										<div class="">
										</div>
									</form>
							</div>
							<div class="loginform" title="机构客户" name="myselfinfo" >
									<form method="post" action="<%=basePath%>paycenter/login.htm" name="LoginFormOrg" id="LoginFormOrg">
										<input type="hidden" name="userType" value="02" />
										<table>
										<tr>
										<td><label class="labeltext" for="name">机构全称:</label></td>
										<td><input class="easyui-validatebox textbox" 
										data-options="required:true" type="text" 
										id="userName" name="userName" title="请输入用户名" maxlength="80" value="" class="inputtext" tabindex=2/></td>
										</tr>
										<tr>
										<td><label class="labeltext" for="orgIdType">证件类型:</label></td>
										<td>
										<input class="easyui-combobox" name="idType"
											 value="01" id="orgIdType" 
											 data-options="required:true"
											 style="width:152px;" editable="false"/>
										</td>
										</tr>
										<tr>
										<td><label class="labeltext" for="password">证件号码:</label></td>
										<td id="ort">
										<input id="idNo" name="idNo" type="text" 
											data-options="required:true,validType:['length[0,18]','idNo']" 
											class="textbox easyui-validatebox" 
											/>
										</td>
										</tr>
										</table>
										
										<div class="footer">
											<span><input id="btnSubmit" class="btntxt" type="submit" value="登录" title="请单击此处登录"/></span>
											<span><input id="btnReset" class="btntxt" type="button" onclick="$('#LoginFormOrg').form('clear');" value="重置" title="请单击重新输入"/></span>	
										</div>
										<div class="">
										</div>
									</form>
							</div>
						</div>
						<div class="graybox-info" style="border:none;">
							<span>
								<font color="red">
									<c:if test="${param.errLoginMsg!=null}">
										<c:out value="${param.errLoginMsg}"></c:out>
									</c:if>
								</font>
							</span>
						</div>
					</div>
				</div>

			</div>
			
			<div class="footer-info" style="">
				<div> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;建议使用IE9.0以上浏览器，本软件仅供北京天瑞兴隆科技发展有限公司授权用户使用。</div>
			</div>
		</div>
	</div>
	<div class="t-b"><span class="tb-l-corner"></span><span class="tb-r-corner"></span></div>
</div>
<div align="right" >

</div>
</div>

<script type="text/javascript">
$(document).ready(function (){
// 	$("#loginid").focus();
	//填充 个人 类型
	$("#idType").combobox("clear");
	$("#idType").combobox({
		valueField:'keyProp',
		textField:'keyValue',
		data:dataDictJson.personidtype
	});
	//填充 机构 类型
	$("#orgIdType").combobox("clear");
	$("#orgIdType").combobox({
		valueField:'keyProp',
		textField:'keyValue',
		data:dataDictJson.companyidtype
	});
});

</script>
</body>
</html>

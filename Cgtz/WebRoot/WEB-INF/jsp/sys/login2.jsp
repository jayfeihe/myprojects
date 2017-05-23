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
<style type="text/css">
<!--
html{height:100%;}
body{font-family:Arial, Helvetica, sans-serif;background: url('img/logoinbg3.jpg') center no-repeat;height: 100%;}
.wrapper{ display:block;margin:0px auto;width:974px; }
.main-container{float:left; width:974px; height:auto;position:absolute;left:50%;top:50%;margin-left:-487px;margin-top:-160px;}
.main-container:after{content:"";height:0;display:block;clear:both;}
.main-container .t-t{float:left;/*background:url(img/t-bg.gif) repeat-x; */width:974px; height:50px;}
.main-container .t-t span.tt-l-corner{ float:left;/* background:url(img/t-l-corner.gif) no-repeat left; */width:15px; height:50px;}
.main-container .t-t span.tt-r-corner{float:right;/* background:url(img/t-r-corner.gif) no-repeat right;*/ width:15px; height:50px;}
.main-container .t-b{float:left;/*background:url(img/b-bg.gif) repeat-x; width:974px; height:15px;}
.main-container .t-b span.tb-l-corner{ float:left;/* background:url(img/b-l-corner.gif) no-repeat left;*/ width:15px; height:15px;}
.main-container .t-b span.tb-r-corner{float:right;/* background:url(img/b-r-corner.gif) no-repeat right;*/ width:15px; height:15px;}

.main-container .login-mainarea{ float:left;width:972px; height:auto;  padding:0px 0px 5px 0px;}

.graybox-wrapper{position:relative;margin:0px auto; width:716px;}
.graybox-container{float:right; width:316px; height:auto;}
.graybox-wrapper:after{content:"";height:0;display:block;clear:both;}
.graybox-container .gb-t{float:left;width:316px; text-align:center;height:50px;line-height:50px;margin-bottom:20px;font-size:26px;font-family:"微软雅黑";/* background:url(img/gray-t-line.gif) repeat-x;*/ }
.graybox-container .gb-t span.gb-t-l{float:left; width:20px; height:20px; /*background:url(img/gray-b-l-t.gif) no-repeat left;*/ }
.graybox-container .gb-t span.gb-t-r{float:right; width:20px; height:20px;/* background:url(img/gray-b-r-t.gif) no-repeat right;*/ }

.graybox-container .gb-m{float:left;width:314px; height:auto;/* background:#f3f3f5;border-left:1px solid #d2d2d2;border-right:1px solid #d2d2d2;*/ }
.graybox-container .gb-m .loginform{float:left; padding:0px 20px 5px 20px; width:276px; height:auto; font-size:13px;}
.loginform table td{padding:5px;line-height:24px;}
.graybox-container .gb-b{float:left;width:316px; height:20px;/* background:url(img/gray-b-line.gif) repeat-x;*/  padding-bottom:5px;}
.graybox-container .gb-b span.gb-b-l{float:left; width:20px; height:20px;/* background:url(img/gray-b-l-b.gif) no-repeat left;*/ }
.graybox-container .gb-b span.gb-b-r{float:right; width:20px; height:20px; /*background:url(img/gray-b-r-b.gif) no-repeat right;*/ }

.graybox-info{ float:left;padding:10px 20px 5px 20px; width:275px; height:auto;/* border-top:1px solid #CCC;*/margin-top:5px;position:relative;left:82px;} 

.footer-info{padding:30px 15px 0px 15px; width:auto; height:auto;font-family: Arial, Helvetica, sans-serif; font-size: 10px; color: #666;}
.footer-info div{border-top:1px solid #E4E4E4; padding-top:10px;}
td#ort span.combo{height:22px !important;}
.combo-arrow{height:22px !important;}
.inputtext{
	width: 180px;
}
.labeltext{
	font-size:14px;
}
.footer{
	text-align: left;
}
.footer .btntxt{width:65px;height:26px;border:none;cursor:pointer;background:url(img/201407070019.png) center no-repeat;color:#333;}
.footer .btntxt:hover{color:#333;font-weight:bold;}
.systemTitle{
	text-align: center;
	padding-left:30px;
	font-size: 30px; 
	font-weight:bold; 
	font-style:微软雅黑;
}
.logopic{position:relative;float:left;left:0px;top:95px;width:339px;height:83px;}
-->
</style>
<script language="JavaScript"> 
if (window != top) 
top.location.href = location.href; 
</script> 
</head>
<body style="">
<div class="wrapper">
	<div class="main-container">
		<div class="t-m">

			<div class="login-mainarea">
			
				<div class="graybox-wrapper">
					<div class="logopic"><img src="img/logopic.gif" /></div>
					<div class="graybox-container">

						<div class="gb-t">P2P综合业务系统</div>

						<div class="gb-m">
							
							<div class="loginform">
								<form method="post" action="login.do" name="LoginForm">
									<table>
									<tr>
										<td nowrap="nowrap" align="right"><label class="labeltext" for="loginid">用户名:</label></td>
										<td><input class="easyui-validatebox textbox" data-options="required:true" style="height:22px;padding:0px 5px;" type="text" id="loginid" name="loginid" title="请输入用户名" value="" tabindex=2/></td>
									</tr>
									<tr>
										<td nowrap="nowrap" align="right"><label class="labeltext" for="password">密&nbsp;&nbsp;&nbsp;码:</label></td>
										<td><input class="easyui-validatebox textbox" data-options="required:true" style="height:22px;padding:0px 5px;" type="password" id="password" name="password" title="请输入密码" value="" tabindex=3/></td>
									</tr>
									<tr>
										<td nowrap="nowrap" align="right"><label class="labeltext" for="password">机&nbsp;&nbsp;&nbsp;构:</label></td>
										<td id="ort" style="padding-left:7px;">
											<input class="easyui-combobox textbox" name="orgId" id="orgId" style="width:162px;height:22px;padding:0px 5px;float:left;" />
										</td>
									</tr>
									<tr>
										<td valign="top" nowrap="nowrap" align="right"><label class="labeltext" for="password">验&nbsp;证&nbsp;码:</label></td>
										<td valign="top">
											<input type="text" name="rand" class="easyui-validatebox textbox" data-options="required:true"  style="height:22px;padding:0px 5px;"/>										
											<img alt="code..." name="randImage" id="randImage" src="randomImg.do" style="float:left;margin-top:2px;float:left;margin-left:3px;" width="60" height="20" border="1" align="middle">
											<a href="javascript:loadimage();" style="padding-top:3px;margin-left:2px;float:left;">看不清点我</a>
										</td>
									</tr>
									</table>
									<div class="footer">
										<span style="padding-left:72px;"><input id="btnSubmit" class="btntxt" type="submit" name="btnSubmit" value="登录" title="请单击此处登录"/></span>
										<span><input id="btnReset" class="btntxt" type="reset" name="btnReset" value="重置" title="请单击重新输入"/></span>	
									</div>
									<div class="">
									</div>
								</form>
						</div>
						<div class="graybox-info">
							<span>
								<font color="red">
									<c:if test="${param.errLoginMsg!=null}">
										<c:out value="${param.errLoginMsg}"></c:out>
									</c:if>
								</font>
							</span>
						</div>

					</div>

					<div class="gb-b"><span class="gb-b-l"></span><span class="gb-b-r"></span></div>
				</div>

			</div>
			<div class="footer-info">
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
var logid="";
$(document).ready(function (){
	$("#loginid").focus();
	//绑定input的blur事件
	function orgList() {
	 	var valueStr=$("#loginid").val();
		if(valueStr!=null&&valueStr!=""&&logid!=valueStr){
			logid=valueStr;
			var tsurl="<%=basePath%>sys/org/userOrg.do?loginid="+valueStr+"&timestamp="+(new Date()).getTime();
			$("#orgId").combobox("clear");
			$('#orgId').combobox({url:tsurl,valueField:'orgId',textField:'orgName'});
		}
	}
	
	$("#loginid").blur(orgList);
	$("span[class='combo-arrow']").click(orgList);
	$("input[class='combo-text validatebox-text']").click(orgList);
	orgList();
});

//重新加载图片
function loadimage(){ 
	$("#randImage").attr("src", "randomImg.do?"+Math.random()); 
} 

</script>
</body>
</html>

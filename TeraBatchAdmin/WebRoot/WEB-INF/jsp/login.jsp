<%@ page contentType="text/html; charset=UTF-8"%>
<%-- <%@ include file="/WEB-INF/jsp/sys/include.jsp"%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.net.URLDecoder" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>"/>
<title>P2P批处理系统登录</title>
<script src="js/jquery.min.js" type="text/javascript"></script>
<script src="js/jquery.form.js" type="text/javascript"></script>
<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
<link href="css/global.css" type="text/css" rel="stylesheet"/>
<link href="css/icon.css" type="text/css" rel="stylesheet"/>
<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
<style type="text/css">

html{height:100%;}
body{font-family:Arial, Helvetica, sans-serif;height: 100%;}
.wrapper{ display:block;margin:0px auto;width:100%;background:url(img/201509130104.jpg) top center no-repeat;}
.main-container{width:974px;height:400px;margin:0px auto;}
.main-container:after{content:"";height:0;display:block;clear:both;}
.main-container .t-t{float:left;/*background:url(img/t-bg.gif) repeat-x; */width:974px; height:50px;}
.main-container .t-t span.tt-l-corner{ float:left;/* background:url(img/t-l-corner.gif) no-repeat left; */width:15px; height:50px;}
.main-container .t-t span.tt-r-corner{float:right;/* background:url(img/t-r-corner.gif) no-repeat right;*/ width:15px; height:50px;}
.main-container .t-b{float:left;/*background:url(img/b-bg.gif) repeat-x; width:974px; height:15px;}
.main-container .t-b span.tb-l-corner{ float:left;/* background:url(img/b-l-corner.gif) no-repeat left;*/ width:15px; height:15px;}
.main-container .t-b span.tb-r-corner{float:right;/* background:url(img/b-r-corner.gif) no-repeat right;*/ width:15px; height:15px;}

.main-container .login-mainarea{ float:left;width:972px; height:auto;  padding:0px 0px 5px 0px;}

.graybox-wrapper{position:relative;margin:0px auto; width:716px;}
.graybox-container{float:right; width:316px; height:auto;border:1px #ddd solid;background:#fff;border-radius:10px; box-shadow:0px 0px 10px rgba(0,0,0,0.4);}
.graybox-wrapper:after{content:"";height:0;display:block;clear:both;}
.graybox-container .gb-t{float:left;width:316px; text-align:left;text-indent:20px;background:#f1f1f1;border-radius:10px 10px 0px 0px;height:50px;line-height:50px;margin-bottom:20px;font-size:20px;font-family:"微软雅黑";/* background:url(img/gray-t-line.gif) repeat-x;*/ }
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
td#ort span.combo{height:28px !important;margin-left:-3px;}
.combo-arrow{height:28px !important;}
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
.main-top{height:65px;width:100%;margin:0px auto;min-width:974px;background:#fff;}
.main-top .cont{width:974px;margin:0px auto;height:65px;}
.main-top .cont img{height:85px;margin-top:8px;float:left;}
.validatebox-invalid{background:none;}
.combo-text{height:28px !important;line-height:28px !Important;background:url(img/list03.png) 4px 5px no-repeat;padding-left:25px !important;}
</style>
<script language="JavaScript"> 
if (window != top) 
top.location.href = location.href; 
</script> 
</head>
<body style="">
<div class="wrapper">
	<div class="main-top">
       <div class="cont"><img src="img/logopic.png" /></div> 
    </div>
	<div class="main-container">
    	
		<div class="t-m">

			<div class="login-mainarea">
			
				<div class="graybox-wrapper">
					<div class="logopic"></div>
					<div class="graybox-container">

						<div class="gb-t">P2P综合业务系统</div>

						<div class="gb-m">
							
							<div class="loginform">
								<form action="login.do" method="post" name="LoginForm">
									<table>
									<tr>
										<!--td nowrap="nowrap" align="right"><label class="labeltext" for="loginid">用户名:</label></td-->
										<td><input class="easyui-validatebox textbox" data-options="required:true" style="height:28px !important;padding:0px 5px 0px 25px;width:220px;background:url(img/list01.png) 4px 5px no-repeat;" type="text" id="loginId" name="loginId" title="请输入用户名" value="" tabindex=2 placeholder="请输入用户名"/></td>
									</tr>
									<tr>
										<!--td nowrap="nowrap" align="right"><label class="labeltext" for="password">密&nbsp;&nbsp;&nbsp;码:</label></td-->
										<td><input class="easyui-validatebox textbox" data-options="required:true" style="height:28px !important;padding:0px 5px 0px 25px;width:220px;background:url(img/list02.png) 8px 6px no-repeat;" type="password" id="password" name="password" title="请输入密码" value="" tabindex=3 placeholder="请输入密码"/></td>
									</tr>
									<tr>
										<td>
											<input type="text" name="rand" class="easyui-validatebox textbox" data-options="required:true"  
											style="height:28px !important;padding:0px 5px 0px 25px;width:220px;background:url(img/yanjing.png) 5px 6px no-repeat;"  
											placeholder="验证码"/>										
										</td>
									</tr>
									<tr>
										<td valign="top">
											<img alt="code..." name="randImage"  id="randImage"  src="randomImg.do" onclick="" style="float:left;margin-top:2px;float:left;margin-left:3px;" width="60" height="20" border="1" align="middle">
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
									<c:if test="${errLoginMsg!=null}">
										<c:out value="${errLoginMsg}"></c:out>
									</c:if>
								</font>
							</span>
						</div>

					</div>

					<div class="gb-b"><span class="gb-b-l"></span><span class="gb-b-r"></span></div>
				</div>

			</div>
            
		</div>
	</div>
	<div class="t-b"><span class="tb-l-corner"></span><span class="tb-r-corner"></span></div>
    
</div>
			<style>
			.footer-info{width:100%;margin:0px auto !important;padding:0px;background:#fff;}
			.footer-info a{color:#666;padding:0px 5px;}
			.footer-info div{text-align:center;}
			.footer-info .div1{padding-top:20px;}
			</style>
			<div class="footer-info">
            	<div class="div1"><a href="http://teratech-soft.com/">首页</a>/<a href="http://teratech-soft.com/about32.html">关于我们</a>/<a href="http://teratech-soft.com/services.html">软件服务</a>/<a href="http://teratech-soft.com/solution15.html">解决方案</a>/<a href="http://teratech-soft.com/panters.html">我们的客户</a>/<a href="http://teratech-soft.com/contact.html">联系我们</a></div>
                <div style="border:none;">版权所有：北京天瑞兴隆科技发展有限公司 京ICP备：201104217</div>
				<div style="border:none;">建议使用IE9.0以上浏览器，本软件仅供授权用户使用。</div>
			</div>
</div>
					
<script type="text/javascript">
var logid="";
$(document).ready(function (){
	$("#loginId").focus();
// 	$("#loginid").blur(orgList);
// 	$("span[class='combo-arrow']").click(orgList);
// 	$("input[class='combo-text validatebox-text']").click(orgList);
});

//重新加载图片
function loadimage(){ 
	$("#randImage").attr("src", "randomImg.do?"+Math.random()); 
} 

</script>
</body>
</html>

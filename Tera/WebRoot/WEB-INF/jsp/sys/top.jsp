<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String serverAddress = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>"/>
<title>综合业务系统</title>
<link href="css/global.css" type="text/css" rel="stylesheet"/>
<script src="js/jquery.min.js" type="text/javascript"></script>
<style type="text/css">
<!--
body{
	background-color:#f8ffd3;
	margin:0px; padding:0px;
}
.topbar{
	background: url("img/carousel_bg.gif") repeat scroll 0 0 transparent;
	font-weight: bold;
}
#topFrame{height:130px;}

.manAndLogout{
	width:355px;
	height:35px;line-height:35px;
	margin-right: 0px;
	padding-right: 0px;
	float:right;
	text-align: right;
	position:relative;top:-38px;
}

img{
	border: 0px;
}
-->
</style>
</head>
<body>

<div id="top" >
	<div style="float:left;padding-top:10px ;padding-left:30px;font-size: 30px; font-weight:bold; font-style:微软雅黑;color: white">
	<a href="main.do" target="mainFrame"><img src="img/logo.png" style="float:left;" /></a>
	</div>
	<div style="float:right;padding-right:10px;">
	</div>
</div>

<div id="navigation">			
	<span id ="nowTime" style="margin-left: 0px;margin-right: 7px;color: #2E2E2A;float:left;display:none;font-size: 16px;padding-left:20px;"></span>
	<span class="hideleft" style="display:none;float:left;position:relative;left:10px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
	<span style="display:none;"><strong>| <a href="main.do" target="mainFrame">主页</a>：</strong></span>
	<span id="menuPathId"></span>
	<span class="manAndLogout" style="width:650px">			
		<table style="margin: 0px;padding: 0px;"  width="100%">
			<tr>
				<td style="vertical-align: middle">
					<div style="vertical-align: top;height:22px;position:relative;top:-4px;">
					用户信息:<a href="sys/user/updatemyself.do" target="mainFrame" title="${login_user.name}">【${login_user.name}/${login_user.loginId}】</a>
					机构信息:【${login_org.orgName}/${login_org.orgId}】
					</div>
				</td>
				<td width="20">
					<div style="height:22px;vertical-align:middle;">
						<a href="sys/user/updatemyself.do" target="mainFrame" title="${login_user.name}"><img src="img/config.png" class="dotimg" alt="${login_user.name}" /></a>
					</div>
				</td>
				<td width="20">
					<div style="">			
						<a href="javascript:void(0);" onclick="javascript:allExit();" style="" target="_parent" title="退出登录"><img style="vertical-align:middle;" src="img/square/but_exit.png" class="dotimg" alt="退出登录"/></a>
					</div>
				</td>
				<td width="5"></td>
			</tr>
		</table>
	</span>
			
</div>
<script language="javascript">
<%--点击动作事件方法--%>
var hideleft=false;
window.sleek = function(flag){
	if(flag=="open"){
		hideleft = false;
	} else if(flag=="close"){
		hideleft = true;
	}
	if(hideleft==false) {
			parent.document.getElementById("workframeset").cols="0,*";
			$(".hideleft").attr("title","显示菜单");
			$(".hideleft").css({background: "url('img/show_menu.gif') no-repeat left"});
			hideleft=true;
	} else {
			parent.document.getElementById("workframeset").cols="210,*";
			$(".hideleft").attr("title","隐藏菜单");
			$(".hideleft").css({background: "url('img/collpase_menu.gif') no-repeat left"});
			hideleft=false;
	}
	
}
<%--当前时间--%>
	function showtime(){
		now = new Date();
		month = now.getMonth();
		date = now.getDate();
		year = now.getYear();
		if (year< 1900) year = 1900 + year;
		hours = now.getHours();
		minutes = now.getMinutes();
		seconds = now.getSeconds();
		
		timeValue = year + "-";
		timeValue += (((month+1) <10)?"0":"") + (month+1) + "-";
		timeValue += ((date <10)?"0":"") + date + " "
		timeValue += hours + ":";
		timeValue += ((minutes <10)?"0":"") + minutes+":";
		timeValue += ((seconds <10)?"0":"") + seconds+"";
		document.getElementById("nowTime").innerHTML = timeValue;
		setTimeout("showtime()",1000);
	}
	showtime();

<%--为Tab添加点击动作事件--%>
$(document).ready(function(){
        //为Tab添加点击动作事件
        $(".hideleft").click(sleek);
    });

function allExit(){
	//alert("testExit");
	$.ajax({ 
		url: "<%out.write(serverAddress); %>/teamserver/faces/logout.jsp", 
		type: "POST",
		success: function(data){
		}
		});
	//cerus exit
	var url="logout.do";
	window.location=url;
	
}

</script>

</body>
</html>

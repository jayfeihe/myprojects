<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>	
<!--header-->
<div class="header">
  <div class="header-wrap">
    <div class="logo"> <a href="javascript:void(0)" onclick="goLocationHistory('instalmentManage')"><img  src="images/logo.png" alt="秒趣"  /></a> </div>
     <div class="logo-text"> ${logoMsg} </div>
	<div class="user-info">
		欢迎您，${userInfo.name} <a class="user-logout" href="<%=basePath%>passwdport/logout.jhtml">退出</a>
	</div>
  </div>
</div>
<!--/header-->

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>"/>
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
	<!--
	body{
		background-color:#ffffff;
		margin:0px; 
		padding:0px;
	}
	-->
	</style>
	<script type="text/javascript"></script>
  </head>
  
  <body>
    This is my JSP page. <br/>
   <input style=""/>
   <input type="button" value="test" onclick="javascript:ff()"/>
  </body>
</html>

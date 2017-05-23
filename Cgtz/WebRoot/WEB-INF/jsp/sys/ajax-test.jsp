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
<title>P2P综合业务系统</title>
<link href="css/global.css" type="text/css" rel="stylesheet"/>
<script src="js/jquery.min.js" type="text/javascript"></script>
<style type="text/css">
<!--
body{
	background-color: #FFFFFF;
	margin:0px; padding:0px;
}
.tar {
	background-color: #E4E4E4;
	border:1px solid #0058a3;
}
-->
</style>
</head>
<body>
<div id="main">

		<div class="part" id="part1" >
			<p class="title"><a href="#">Ajax_调用示例</a></p>
			<div class="content">
				<p>
				<label class="" for="loginid">用户名</label>
				<input type="text" id="loginid" name="loginid" title="请输入用户名" maxlength="80" />
				</p>
				<p>
				<label class="" for="password">密码</label>
				<input type="password" id="password" name="password" title="请输入密码" value="" maxlength="255" />
				</p>
				<input class="btntxt" type="button" value="点击" id="load"/>
				<div id="tar"></div>
			</div>
		</div>

</div>
<script type="text/javascript">
$(document).ready(function(){
 	$("#load").click(function(){
 		var loginid = $("#loginid").val();
 		var password = $("#password").val();
 		$("#tar").toggleClass("tar");
 		$.ajax({
 			url:"ajaxTest.do",
 			//防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
 			data:encodeURI("loginid=" + loginid + "&password=" + password + "&timestamp=" + (new Date()).getTime()),
 			dataType:"json",
 			success:function(data){
 				$("#tar").html("");
 				$("#tar").append("loginId：" + data.loginId + "<br/>");
 				$("#tar").append("password：" + data.password + "<br/>");
 				$("#tar").append("admin：" + data.admin + "<br/>");
 				var roles = data.assignRoles;
 				for(var i = 0; i<roles.length; i++) {
 					var role = roles[i];
 					$("#tar").append("id：" + role.id + "|");
 					$("#tar").append("name：" + role.name + "|");
 					$("#tar").append("description：" + role.description);
 					$("#tar").append("<br/>");
 				};
 			}
 		});
 	});
 });
</script>
</body>
</html>

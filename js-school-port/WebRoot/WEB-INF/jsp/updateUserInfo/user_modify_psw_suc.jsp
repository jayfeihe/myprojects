<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>	
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
<meta http-equiv="expires" content="0">
<link rel="icon" href="images/favicon.ico" type="image/x-icon">
<link rel="stylesheet" href="css/base.css">
<link rel="stylesheet" href="css/input.css">
<link rel="stylesheet" href="css/validator.css">
<link rel="stylesheet" href="css/user.css">
<title>秒趣分期-修改密码</title>
</head>
<body class="body-no-bg">
<!--header-->
<jsp:include page="${basePath}/jsp/frame/header.jsp"></jsp:include>
<!--/header-->

<!--content-->
<div class="user-main">
	<div class="user-result clearfix" style="padding-bottom: 130px;">
		<div class="user-remind clearfix">
			<div class="clearfix" style="text-align:center;"><i class="user-icon user-success-icon" style="vertical-align:middle;"></i><span class="user-success-remind">恭喜您，已成功修改密码！</span></div>
		</div>
		<div class="clearfix mt10" style="text-align:center;">请牢记您的新密码，作为下次登录时使用，谢谢！</div>
		<div class="user-remind clearfix mt20" style="text-align:center;"><button type="button" class="user-btn user-btn-red" id="close_tabs" onclick="backToHost()">关闭并返回</button></div>
	</div>

</div>
<!-- footer start-->
<jsp:include page="${basePath}/jsp/frame/footer.jsp"></jsp:include>
<!-- footer end-->
<script src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
	function backToHost(){
		var url="<%=basePath%>updateUserInfo/viewInfo.jhtml";
		window.location.href=url;
	}
</script>
</body>
</html>

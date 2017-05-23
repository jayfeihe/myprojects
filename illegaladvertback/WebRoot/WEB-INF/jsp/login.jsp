<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">

<title>违法广告预警后台管理系统</title>

<!-- Bootstrap core CSS -->
<link href="<%=basePath %>css/bootstrap/bootstrap.min.css" rel="stylesheet">
<link href="<%=basePath %>css/bootstrap/bootstrap-theme.min.css" rel="stylesheet">
<link href="<%=basePath %>css/signin.css" rel="stylesheet">
<script src="<%=basePath %>js/md5.js" type="text/javascript"></script>

<script type="text/javascript">
	// 兼容FF 
	document.onkeydown=keyListener; 
		function keyListener(e){ 
			e = e ? e : event; 
			if(e.keyCode == 13){ 
				//调用登录按钮的登录事件
				  $("#btnLogin").click();
			} 
		} 
	</script>
</head>

<body>

	<div class="container">
		<form class="form-signin" method="post" id="loginForm">
			<div class="form-group">
				<h3 class="form-signin-heading" align="center">广告后台管理系统</h3>
			</div>
			
			<div class="form-group">
				<label for="inputEmail" class="sr-only">账号</label> 
				<input type="text" id="account" name="account" class="form-control" placeholder="账号" required autofocus> 
			</div>
			
			<div class="form-group">
				<label for="inputPassword" class="sr-only">密码</label> 
				<input type="password" id="password"
					name="pwd" class="form-control" placeholder="密码" required>
			</div>

			<div class="form-group">
				<input type="text" id="code" name="code" class="form-control"
					style="width: 160px; float: left; margin-right: 20px;" placeholder="验证码" required> 
				<div style="width: 80px;height: 40px; margin-right: 5px; float: left; cursor: pointer;">
					<img alt="loading..." src="<%=basePath %>randomImg" id="randImage" onclick="loadImage();"> 
				</div>
				<div style="clear: both;"></div>
			</div>
			
			<div class="form-group">
				<input class="btn btn-lg btn-primary btn-block" type="button" id="btnLogin" value="登录" onclick="ajaxLogin();">
			</div>
			
			<div class="form-group">
				<span id="errorMsg" style="color: red;"></span>
			</div>
		</form>
	</div>
	
</body>

<script src="<%=basePath %>js/jquery/jquery-1.10.2.min.js"></script>
<script src="<%=basePath %>js/bootstrap/bootstrap.min.js"></script>

<script type="text/javascript">
	//重新加载验证图片
	function loadImage(){ 
		$("#randImage").attr("src", "<%=basePath %>randomImg?"+Math.random()); 
	} 
	
	function ajaxLogin(){
		$("#errorMsg").text("");
		var account = $("#account").val();
		var pwd = $("#password").val();
		var code = $("#code").val();
		
		if(account == "" || pwd == ""){
			$("#errorMsg").text("账号、密码不能为空");
			return;
		}
		if(code==""){
			$("#errorMsg").text("验证码不能为空");
			return;
		}
		
		$.post('<%=basePath %>loginAuth',{"account":window.btoa(account),"pwd":hex_md5(pwd),"code":code},function(data) {
			if(data.success == true) {
				window.location = '<%=basePath %>main';
			} else {
				$("#errorMsg").text(data.message);
			}
			
			$("#code").val("");
			loadImage();
		});
	}
</script>

</html>
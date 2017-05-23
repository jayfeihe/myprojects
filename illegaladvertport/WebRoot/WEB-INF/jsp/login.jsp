<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
	<base href="<%=basePath%>"/>
	<meta charset="UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<link rel="icon" href="<%=basePath%>images/favicon.ico" type="image/x-icon"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/bootstrap/bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/bootstrap/bootstrap-theme.min.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/login.css" />
	<script type='text/javascript' src='<%=basePath%>js/jquery/jquery.min.js'></script>
	<script type='text/javascript' src='<%=basePath%>js/jquery/jquery.cookie.js'></script>
	<script type='text/javascript' src='<%=basePath%>js/jquery/jquery.particleground.min.js'></script>
	<script type='text/javascript' src='<%=basePath%>js/md5.js'></script>
	<title>违法预警系统</title>
	<script type="text/javascript">
		$(document).ready(function() {
		
			setTimeout(function(){
				$('#intro').css({
					'margin-top': -($('#intro').height() / 1.8)
				});
				$('#intro').fadeIn();
			});
		
		});
	</script>
</head>
<style>
	.checkbox label{
		margin-bottom:20px !important;
	}
	input[type=checkbox]{
		margin-top:2px !important;
	}

</style>

<body>
	<div id="particles">
	    <div id="intro">
			<form class="form-signin login" id="loginForm" >
	        	<div class="top" id="top">
	        		<h1>违法预警系统</h1>
	        		
	        	</div>
				<h2 class="form-signin-heading">登录帐号</h2>
				<label for="inputAccount" class="sr-only">account</label>
				<input type="text" class="form-control" id="account" name="account" placeholder="登 录 名" required="" autofocus="">
				<label for="inputPassword" class="sr-only">Password</label>
				 <input type="password" class="form-control" id="passwd" name="pwd" placeholder="密&nbsp;&nbsp;&nbsp;码" required="">
				
				<div class="checkbox">
					<label>
						<input type="checkbox" class="loginc" name="remember-me" value="remember-me"> 记住帐户
					</label>
					<span class="pull-right forget" id="errorMsg"></span>
				</div>
				<p class="sub-box">
				 	<img src="images/tb11.png" alt="">
					<input type="button" class="btn btn-login btn-block" onclick="loginAuth();" value="登录">
				 </p>
			</form>
	    </div>
	</div>
	
	<script type="text/javascript">
		function loginAuth() {
			$("#errorMsg").html("");
			
			var account = $("#account").val().trim();
			var pwd = $("#passwd").val();
			//var decode_account = window.btoa(account);
			var md5_pwd = hex_md5(pwd);
			$.ajax({
				url: "<%=basePath%>loginAuth",
				method: "POST",
				async: false, 
				dataType: 'json',
				data: {'account':account,'pwd':md5_pwd},
				success: function(data) {
					var isSuccess = data.success;
					if(isSuccess == true) {
						// 记住账户
						var rem = $('input:checkbox[name="remember-me"]:checked').val();
						if(rem == null || rem == '') {
							// 进行Cookie操作
							$.removeCookie('account'); 
							$.removeCookie('passwd'); 
						} else {
							// 进行Cookie操作
							$.cookie('account', account, { expires: 7 });
							$.cookie('passwd', pwd, { expires: 7 });
						}
						window.location = "<%=basePath%>index";
					} else {
						$("#errorMsg").html(data.message);
						return false;
					}
				},
				error: function() {
					$("#errorMsg").html("<div class='errorTip'>抱歉，系统出现错误！</div>");
					return false;
				}
			});
		}
	</script>
	
	<script type="text/javascript">
		$(function(){
			var account = $.cookie('account');
			var passwd = $.cookie('passwd');
			if(account != null) {
				$("#account").val(account);
				$('input:checkbox[name="remember-me"]').attr("checked",true);
			}
			if(passwd != null) $("#passwd").val(passwd);
		})
	</script>
	
</body>
</html>

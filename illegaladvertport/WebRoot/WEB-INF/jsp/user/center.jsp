<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>"/>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<link rel="icon" href="<%=basePath%>images/favicon.ico" type="image/x-icon"/>
	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/bootstrap/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/bootstrap/bootstrap-theme.min.css">
	<link href="http://cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">
	<link href="http://cdn.bootcss.com/jqueryui/1.12.0/jquery-ui.min.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/tab_page.css">

	<script type="text/javascript" src="<%=basePath%>js/jquery/jquery.min.js"></script>
	<script type='text/javascript' src='<%=basePath%>js/jquery/jquery.cookie.js'></script>
	<script type="text/javascript" src="<%=basePath%>js/bootstrap/bootstrap.min.js"></script>
	<script src="http://cdn.bootcss.com/jqueryui/1.12.0/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/script.js"></script>
	<title>违法广告预警系统</title>
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" id="navbar-fixed-top">
    	<div class="container-fluid">
        	<div class="navbar-header">
	            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar"> 
	            	<span class="sr-only">Toggle navigation</span>
	 				<span class="icon-bar"></span>
	 				<span class="icon-bar"></span>
	 				<span class="icon-bar"></span>
	            </button>
	             <a class="navbar-brand set-brand" href="<%=basePath%>index">
	            	<img class="logo" src="<%=basePath%>images/logo_2.png">
	            </a>
	        </div>
	        <div id="navbar" class="navbar-collapse collapse">
	            <ul class="nav navbar-nav navbar-center">
	                <li>
	                	<a href="<%=basePath%>index">首 页</a>
	                </li>
	                <li >
	                	<a href="<%=basePath%>confirmIllegal/list">违法确认列表</a>
	                </li>
	                <li class="active">
	                	<a href="<%=basePath%>userAccount/center">个人中心</a>
	                </li>
	            </ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown ssp-dropdown"> 
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
							<h2>用户  ${login_user.account }</h2>
							<img src="<%=basePath%>images/xlcd.png" alt="" class="jiaobtn">
						</a>
					    <ul class="down-menu" id="down-menu">
					         <!-- <li>
					        	<a href="javascript:void(0);"><img src="images/tb2.png"> 公 告</a>
					        </li>
					        <li>
					        	<a href="javascript:void(0);"><img src="images/tb3.png"> 帮 助</a>
					        </li> -->
					        <li>
					        	<a href="/logout"><img src="<%=basePath%>images/tb4.png"> 退出</a>
					        </li>
					    </ul>
					</li>
	            </ul>
	        </div>
		</div>
	</nav>
	<div class="container-fluid dash index">
		<div class="row" id="row">
			<div class="col-md-12" id="message">
				<h1 style="font-size:16px;margin-top: 50px;">帐户信息</h1>
			</div>
			<div class="col-md-12">
				<form class="form-horizontal" style="font-size:12px;" role="form" id="accountForm">
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">帐户名：</label>
						<div class="col-sm-10">
							<p class="form-control-static">${login_user.account }</p>
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label"><b style="color: red;">*</b>密码：</label>
						<div class="col-sm-10">
							<p class="form-control-static " data-toggle="modal">
								<span class="nup" id="updatePwd">修改密码</span>
							</p>
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label"><b style="color: red;">*</b>邮箱地址：</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="email" name="email" value="${login_user.email }" placeholder="">
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label"><b style="color: red;">*</b>联系人：</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="userName" name="userName" value="${login_user.userName }"  placeholder="联系人姓名(2-12位汉字)">
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label"><b style="color: red;">*</b>手机：</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="mobile" name="mobile" value="${login_user.mobile }"  placeholder="">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">固定电话：</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="telephone" name="telephone" value="${login_user.telephone }"  placeholder="区号-数字">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">传真：</label>
						<div class="col-sm-10">	
							<input type="text" class="form-control" id="fax" name="fax" value="${login_user.fax }"  placeholder="">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">QQ：</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="qq" name="qq" value="${login_user.qq }"  placeholder="5-15位数字">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label"></label>
						<div class="col-sm-10">
							<input id="saveUserAccount" type="button" class="btn btn-yellow btn-ww" onclick="updateAccount();" value="保存"/>
							<span style="margin: 0 5px;"></span>
							<input type="button" class="btn btn-ssp btn-ww" onclick="javascript:window.location='<%=basePath%>index'" value="返回"/>
							<label id="errorMsg" class="kong"></label>
						</div>
					</div>
					
				</form>
			</div>
		</div>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="updatePwdModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
				<!-- <button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button> -->
				<h4 style="font-size: 16px;" class="modal-title" id="myModalLabel">修改密码</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" id="pwdForm">
					    <div class="form-group">
					        <label class="col-sm-3 control-label">原  密  码：</label>
					        <div class="col-sm-5">
					        	<input type="password" id="password"  name="password" class="form-control" placeholder="原密码">
					        </div>
					        <div class="col-sm-4">
					        	<span id="helpBlock1" class="help-block ">您的当前密码不正确</span>
					        </div>

					    </div>
					    <div class="form-group">
					        <label class="col-sm-3 control-label">新  密  码： </label>
					        <div class="col-sm-5">
					        	<input type="password" id="newpassword" name="newpassword" class="form-control" placeholder="密码为6-12位大小写字母，数字">
					        </div>
					        <div class="col-sm-4">
					        	<span id="helpBlock2" class="help-block"></span>
					        </div>

					    </div>
					    <div class="form-group">
					        <label class="col-sm-3 control-label">确认密码：</label>
					        <div class="col-sm-5">
					        	<input type="password" id="confirmpassword" name="confirmpassword" class="form-control" placeholder="确认密码">
					        </div>
					        <div class="col-sm-4">
					        	<span id="helpBlock3" class="help-block"></span>
					        </div>
					    </div>
					    <!-- <div class="form-group">
					    	<label class="col-sm-3 control-label"></label>
					        <div class="col-sm-9">
					        	密码规则：<br>
					    		1.可包含大写字母、小写字母、数字和特殊字符
					        </div>
					    	
					    </div> -->
					</form>
				</div>
				<div class="modal-footer illegal">
					<input id="confirmUpdatePwd" type="button" class="btn btn-yellow btn-ww" onclick="updatePwd();" value="确定"/>
					<!-- <button type="button" class="btn btn-primary">确定</button> -->
					<button type="button" class="btn btn-ssp btn-ww" data-dismiss="modal">取消</button>
					
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>
<script type="text/javascript" src="<%=basePath%>js/user-center.js"></script>
<script type="text/javascript">
	$(function(){
		user.openPwdModel();
		//user.pwdAuth();
		user.resetPwdForm();
	});
	
	function updateAccount() {
		$("#errorMsg").html("");
		var a = $("#accountForm").serializeArray();
		var obj = {};
		$.each(a, function(k, v) {
			obj[v.name] = v.value;
		});
		
		if(obj.email == '') {
			$("#errorMsg").html("").html("<span>邮箱不能为空</span>");
			return;
		}
		var emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9-])+((\.[a-zA-Z0-9-]{2,3}){1,2})$/; 
		if(obj.email != '') {
			if(!emailReg.test(obj.email)) {
				$("#errorMsg").html("<span>邮箱格式不正确</span>");
				return;
			}
		}
		if(obj.userName == '') {
			$("#errorMsg").html("<span>联系人不能为空</span>");
			return;
		}
		var chinese =  /^[\u4e00-\u9fa5]{2,12}$/;
		if(obj.userName != '') {
			if(!chinese.test(obj.userName)) {
				$("#errorMsg").html('<span>联系人必须为2-12位汉字</span>');
				return;
			}
		}
		if(obj.mobile == '') {
			$("#errorMsg").html("").html("<span>手机不能为空</span>");
			return;
		}
		var mobileReg = /^\d{11}$/;
		if(!mobileReg.test(obj.mobile)) {
			$("#errorMsg").html("<span>手机号必须为11位数字</span>");
			return;
		}
		
		if(obj.qq != '') {
			var qqReg = /^\d{5,15}$/;
			if(!qqReg.test(obj.qq)) {
				$("#errorMsg").html("").html("<span>qq号为5-15位数字</span>");
				return;
			}
		}
		
		$("#saveUserAccount").addClass("disabled");
		var params = $("#accountForm").serialize();
		$.ajax({
			url:'<%=basePath%>userAccount/update',
			method:"post",
			data: params,
			success: function(data) {
				$("#errorMsg").html("").html(data.message);
			}, 
			error: function() {
				$("#errorMsg").html("").html("<div class='errorTip'>抱歉，系统出现错误！</div>");
			}
		});
		$("#saveUserAccount").removeClass("disabled");
	}
	
	function updatePwd() {
		$("#helpBlock1").html('');
		$("#helpBlock2").html('');
		$("#helpBlock3").html('');
		var a = $("#pwdForm").serializeArray();
		var obj = {};
		$.each(a, function(k, v) {
			obj[v.name] = v.value;
		});
		if (obj.password == '') {
			$('#helpBlock1').html("当前密码不能为空").show();
			return;
		}
		
		if(obj.newpassword == '') {
			$('#helpBlock2').html('新密码不能为空').show();
			return;
		}
		
		var pwdReg = /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9]{6,12}$/;
		if(!pwdReg.test(obj.newpassword)) {
			$('#helpBlock2').html('密码为6-12位大小写字母，数字').show();
			return;
		}
		
		if (obj.confirmpassword == '') {
			$('#helpBlock3').html('确认密码不能为空').show();
			return;
		}
		
		if (obj.newpassword !== obj.confirmpassword) {
			$('#helpBlock3').html('两次密码输入不一致').show();
			return;
		}
		
		$("#confirmUpdatePwd").addClass("disabled");
		
		var params = $("#pwdForm").serialize();
		$.ajax({
			url:'<%=basePath%>userAccount/updatePwd',
			method:"post",
			data: params,
			success: function(data) {
				if(data.success == true) {
					window.location = '<%=basePath%>login';
				} else {
					$('#helpBlock1').html("当前密码不正确").show();
				}
			}, 
			error: function() {
				
			}
		});
		$("#confirmUpdatePwd").removeClass("disabled");
	}
</script>

</html>
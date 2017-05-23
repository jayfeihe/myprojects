$(function(){
	$("#login_code1").show();
	$("#login_code1_msg_box").show();
	getValidatetionCode();
	
	//tab
	$("#login_way_tab li").click(function(){
		var $this = $(this);
		var index = $this.index();
		$this.addClass("current");
		$this.siblings("li").removeClass("current");
		$(".login-form form").hide();
		$("#login_form"+index).show();
		
		//每次切换都重新获取验证码
		getValidatetionCode();
	});
	
	//手机登录表单验证
	$("#login_form0").validator({ 
		focusCleanup: false,
		stopOnError:false,
		//debug: true,
		timely: 2,
		//自定义规则
		rules: {
			 
			mobile:function(element){
				var re = /^1\d{10}$/;
				return re.test(element.value);
			},
			validationCode:function(element){
				var re = /^\w{4}$/;
				return re.test(element.value);
			},
			password:function(element){
				var re = /^\w{6}$/;
				return re.test(element.value);
			}
		},
		fields: {
			"mobile": {
				rule: "required;mobile",
				msg: {
					required: "请输入手机号码！",
					mobile:"手机号码错误！"
				}
			},
			"validationCode0": {
				rule: "required;validationCode",
				msg: {
					required: "请输入图形验证码！",
					validationCode: "图形验证码错误！"
				}
			},
			"password": {
				rule: "required;password",
				msg: {
					required: "请输入动态验证码！",
					password: "动态验证码错误！"
				}
			}
		}
	});
	//普通登录表单验证
	$("#login_form1").validator({ 
	
		focusCleanup: false,
		stopOnError:false,
		//debug: true,
		timely: 2,
		//自定义规则
		rules: {
			 
			mobile:function(element){
				var re = /^1\d{10}$/;
				return re.test(element.value);
			},
			validationCode:function(element){
				var re = /^\w{4}$/;
				return re.test(element.value);
			}
		},
		fields: {
			"mobile": {
				rule: "required;mobile",
				msg: {
					required: "请输入手机号码！",
					mobile:"手机号码错误！"
				}
			},
			"password": {
				rule: "required;",
				msg: {
					required: "请输入密码！"
				}
			},
			"validationCode": {
				rule: "required;validationCode",
				msg: {
					required: "请输入图形验证码！",
					validationCode: "图形验证码错误！"
				}
			}
		}
	});
	/*手机登录*/
	$("#submit_form0").unbind("click").click(function(){
		var $this = $(this);
		/*
		if($("#login_code0").is(":visible")){
			$("#login_form0").validator('setField', 'password', 'required;password;');
			$('#login_form0').validator('setMsg', {
				required: '请填写动态短信验证码！',
				password: '请输入正确的短信验证码(6位数字)！'
			});
		}else{
			$("#login_form0").validator('setField', {
				password: null
			});
		}
		*/
		var mobileNumber = Trim($("#mobile_0").val());
		var password = $("#password_0").val();
		var validationCode = Trim($("#validationCode0").val());
		var redirectUrl = $("#redirectUrl").val();
		
		$("#login_form0").trigger("validate");//表单验证
		$('#login_form0').isValid(function(v){
			//表单验证通过
			if(v){
				$this.prop("disabled",true).text("登录中...");//发送请求前禁用按钮,待返回结果后，禁用打开
				
				hideLoginForm0ErrorMsg();
				$.ajax({
					async : true,
					timeout : 10000,
					type : "post",
					url : contextPath + "/passwdport/loginBySM.jhtml",
					data : "mobileNumber=" + mobileNumber + "&validationCode=" + validationCode + "&password=" + password + "&redirectUrl=" + redirectUrl,
					//	dataType : 'json',
					error : function (XMLHttpRequest, textStatus, errorThrown) {
						getValidatetionCode();
						// 通常 textStatus 和 errorThrown 之中
						// 只有一个会包含信息
						/*if(textStatus == 'timeout'){
							showLoginForm0ErrorMsg('请求超时，请稍后尝试。');
						}else{
							showLoginForm0ErrorMsg('未知错误，请稍后尝试请联系客服。');
						}*/
						showLoginForm0ErrorMsg('出错了，请稍后重试！');
					},
					success : function(data, textStatus){
						getValidatetionCode();
						if(data.code == "0"){
							//send_code_time($this);//发送成功后开始倒计时
							window.location.href = data.redirectUrl+"?rnd="+Math.random();
						}else{
							showLoginForm0ErrorMsg(data.desc);
						}
					}
				});
				
				$this.prop("disabled",false).text("登录");
				/*
				$.ajax({ 
					url: "xxxxxxxxx", //请完善正确的ajax发送路径
					type:"post",
					success: function(){
						$this.prop("disabled",false).text("登录");
					}
				});
				*/
			}
		});
	});
	
	/*普通登录*/
	$("#submit_form1").unbind("click").click(function(){
		var $this = $(this);
		
		var mobileNumber = $("#mobile_1").val();
		var password = $("#password_1").val();
		var validationCode = $("#validationCode").val();
		var redirectUrl = $("#redirectUrl").val();
		$("#mobile_1").val(mobileNumber);
		$("#validationCode").val(validationCode);
		
		if($("#login_code1").is(":visible")){
			$("#login_form1").validator('setField', 'validationCode', 'required;validationCode;');
			$('#login_form1').validator('setMsg', {
				required: '请输入图形验证码！',
				validationCode: '图形验证码错误！'
			});
		}else{
			$("#login_form1").validator('setField', {
				validationCode: null
			});
		}
		$("#login_form1").trigger("validate");//表单验证
		$('#login_form1').isValid(function(v){
			//表单验证通过
			if(v){
				$this.prop("disabled",true).text("登录中...");//发送请求前禁用按钮,待返回结果后，禁用打开

				hideLoginForm1ErrorMsg();
				$.ajax({
					async : true,
					timeout : 5000,
					type : "post",
					url : contextPath + "/passwdport/loginByPwd.jhtml",
					data : "mobileNumber=" + window.btoa(mobileNumber) + "&validationCode=" + validationCode + "&password=" + hex_md5(password) + "&redirectUrl=" + redirectUrl,
					error : function (XMLHttpRequest, textStatus, errorThrown) {
						getValidatetionCode();
						// 通常 textStatus 和 errorThrown 之中
						// 只有一个会包含信息
						if(textStatus == 'timeout'){
							showLoginForm1ErrorMsg('请求超时，请稍后尝试。');
						}else{
							showLoginForm1ErrorMsg('未知错误，请稍后尝试请联系客服。');
						}
						showLoginForm0ErrorMsg('出错了，请稍后重试！');
					},
					success : function(data, textStatus){
						getValidatetionCode();
						if(data.code == "0"){
							//send_code_time($this);//发送成功后开始倒计时
							window.location.href = data.redirectUrl+"?rnd="+Math.random();
						}else{
							if(data.times >= 3){
								$("#login_code1").show();
								$("#login_code1_msg_box").show();
							}else{
								showLoginForm1ErrorMsg(data.desc);
							}
							
						}
					}
				});
				
				$this.prop("disabled",false).text("登录");
				/*
				$.ajax({ 
					url: "xxxxxxxxx", //请完善正确的ajax发送路径
					type:"post",
					success: function(){
						$this.prop("disabled",false).text("登录");
					}
				});
				*/
			}
		});
	});
	

	//点击发送验证码
	$("#login_get_code").unbind("click").click(function(){
		var $this = $(this);
		var flag=true;
		$("#mobile_0").trigger("validate");//表单验证
		$("#validationCode0").trigger("validate");//表单验证
		$('#mobile_0').isValid(function(v){
			if (v) {
				$('#validationCode0').isValid(function(vv){
					if (vv) {
					 }else{
						 flag=false;
					 }
					});
				if(!flag){return;}
				$this.prop("disabled",true);//发送请求前禁用按钮，避免快速点击发生多次发送短信事情
				send_code_time($this);//发送成功后开始倒计时
				hideLoginForm0ErrorMsg();
				var mobileNumber = $("#mobile_0").val();
				$.ajax({
//					async : true,
					timeout : 5000,
					type : "post",
					url : contextPath + "/passwdport/sendLoginSM.jhtml",
					data : "mobileNumber=" + mobileNumber,
				//	dataType : 'json',
					error : function (XMLHttpRequest, textStatus, errorThrown) {
					    // 通常 textStatus 和 errorThrown 之中
					    // 只有一个会包含信息
						if(textStatus == 'timeout'){
							//showLoginForm0ErrorMsg('请求超时，请稍后尝试。');
						}else{
							resetSendSmBut($this);
							showLoginForm0ErrorMsg('出错了，请稍后重试！');
						}
					},
					success : function(data, textStatus){
						if(data.code == "0"){
							
						}else{
							resetSendSmBut($this);
							showLoginForm0ErrorMsg(data.desc);
						}
					},
				});
				
				/*
				$.ajax({ 
					url: "xxxxxxxxx", //请完善正确的ajax发送路径
					type:"post",
					success: function(){
						send_code_time($this);//发送成功后开始倒计时
					}
				});
				send_code_time($this);//此行代码为重复代码，请在ajax请求调通后，删除此行代码
				*/
			}
		});	
	});
	
	/*
	*发送验证码倒计时
	*params:
	*$btn当前点击的按钮
	*/
	var timer ;
	function send_code_time($btn){
		var count = 60;
		$btn.addClass("disabled").prop("disabled",true).text("已发送（"+count+"s）");
		
		 timer = setInterval(function(){
			count--;
			if(count<=1){
				clearInterval(timer);//清除倒计时
				$btn.removeClass("disabled").prop("disabled",false).text("重新获取验证码");
			}else{
				$btn.text("已发送（"+count+"s）");
			}
			
		},1000);
	}
	/*
	*重置发送按钮状态
	*params:
	*$btn当前点击的按钮
	*/
	function resetSendSmBut($btn){
		clearInterval(timer);//清除倒计时
		$btn.removeClass("disabled").prop("disabled",false).text("重新获取验证码");
	}
	
	//忘记密码
	$("#forget_psw_btn").click(function(){
		$("#forget_psw_dialog").show();
	});
	//关闭忘记密码
	$("#close_forget_dialog_r").click(function(){
		$("#forget_psw_dialog").hide();
	});
	
	//显示表单0--动态密码登陆错误信息
	function showLoginForm0ErrorMsg(desc){
		$("#dev_login_msg_error_0").show();
		$("#label_login_msg_error_0").text(desc);
	//	$("#login_get_code").prop("disabled",false);
	}
	//隐藏表单0--动态密码登陆错误信息
	function hideLoginForm0ErrorMsg(){
		$("#dev_login_msg_error_0").hide();
		$("#label_login_msg_error_0").text('');
	}
	
	//显示表单1--动态密码登陆错误信息
	function showLoginForm1ErrorMsg(desc){
		$("#dev_login_msg_error_1").show();
		$("#label_login_msg_error_1").text(desc);
	}
	//隐藏表单1--动态密码登陆错误信息
	function hideLoginForm1ErrorMsg(){
		$("#dev_login_msg_error_1").hide();
		$("#label_login_msg_error_1").text('');
	}
	
	//获取验证码
	$("#a_validationCode").unbind("click").click(function(){
		getValidatetionCode();
	});
	$("#a_validationCode0").unbind("click").click(function(){
		getValidatetionCode();
	});
	
	function getValidatetionCode(){
//		$("#img_validationCode").attr('src', contextPath + '/passwdport/generateValidationCode.jhtml?type=loginByPwd&it='+Math.random());
		var id  = $(".current").attr("id");
		if(id == 'loginpwd'){
			$("#img_validationCode").attr('src', contextPath + '/passwdport/generateValidationCode.jhtml?type=loginByPwd&it='+Math.random());
		}else{
			$("#img_validationCode0").attr('src', contextPath + '/passwdport/generateValidationCode.jhtml?type=loginBySM&it='+Math.random());
		}
	}
	
});

$(function(){
	loadValidationCode();
	$("#get_code").prop("disabled",true);
	$("#check_way_mobile").prop("checked",true);//默认选择手机验证
	$("input[name='checkWay']").change(function(){
		//切换修改方式需刷新验证码
		loadValidationCode();
		$("#user_psw_form").validator( "cleanUp" );
		$(".error-info").html("");
		var check_mobile_area = $("#check_mobile_area");
		var check_psw_area = $("#check_psw_area");
		if($("#check_way_mobile").is(":checked")){
			check_mobile_area.show();
			check_psw_area.hide();
			$("#divSendSmcode").show();
			$("#smCodeId").val("");
			$("#newPwd1").val("");
			$("#newPwd2").val("");
			$("#oldpsw").val("");
			$("#user_psw_form").attr("action",contextPath+"/updateUserInfo/updatePwdByMobile.jhtml");
		}else{
			$("#smCodeId").val("");
			$("#newPwd1").val("");
			$("#newPwd2").val("");
			$("#oldpsw").val("");
			check_mobile_area.hide();
			$("#divSendSmcode").hide();
			check_psw_area.show();
			$("#user_psw_form").attr("action",contextPath+"/updateUserInfo/updatePwdByPwd.jhtml");
		}
	});
	
	//表单验证
	$("#user_psw_form").validator({ 
		focusCleanup: false,
		stopOnError:false,
		//debug: true,
		timely: 2,
		//自定义规则
		rules: {
			smCode:function(element){
				var re = /^\d{6}$/;
				return re.test(element.value);
			},
			newpswrule:function(element){
				var password = element.value;
				if(password.length<8||password.length>16){
					return "密码需要8-16个字符";
				}
				var hasaz = (password.search(/[a-z]/)!=-1);//是否有小写字母
				var hasAZ = (password.search(/[A-Z]/)!=-1);//是否有大写字母
				var has09 = (password.search(/[0-9]/)!=-1);//是否有数字
				var hasSign = (password.search(/[`!@#$%^&*()_+\-\=;',.<>?:"\\\|\[\]\{\}~]/)!=-1);//是否有符号
				
				if(!(hasaz&&hasAZ&&has09&&hasSign)){
					//if(!(hasAZ&&has09||hasAZ&&hasSign||has09&&hasSign)){
				  return  "密码必须包含大、小写字母、数字、特殊字符";
				}
				
			},
			againNewSame:function(element){
				return element.value==$('input[name="newpsw"]').val();
			},
			newPwdNotSameOldPwd:function(element){
				var newPwd = $('input[name="newpsw"]').val();
				var oldPwd =$('input[name="oldpsw"]').val();
				if (newPwd == oldPwd) {
					  return  "密码不能与旧密码相同";
				}
			},
			code:function(element){
				var re = /^\w{4}$/;
				var flag=false;
				if(re.test(element.value)){
					flag=validateCodeId();
				} 
				if(flag==null||flag=="undefined"){
					return false;
				}
				if($("#check_way_mobile").is(":checked")){
					$("#get_code").prop("disabled",!flag);
				}
				return flag;
			}
		},
		fields: {
			"oldpsw": {
				rule: "required;",
				msg: {
					required: "请输入密码！"
				},
				tip: "请输入当前登录密码"
				
			},
			"newpsw": {
				rule: "required;newpswrule;newPwdNotSameOldPwd;",
				msg: {
					required: "请输入新密码！"
				},
				tip: "密码由8~16位字符组成，必须包含大、小写字母、数字、特殊字符！"
			},
			"againNewpsw": {
				rule: "required;againNewSame",
				msg: {
					required: "请再次输入新密码！",
					againNewSame:"两次密码需保持一致！"
				},
				tip: "再次输入密码，两次密码需保持一致"
			},
			"smCode": {
				rule: "required;smCode",
				msg: {
					required: "请输入动态验证码！",
					smCode: "请输入正确的短信验证码(6位数字)！"
				}
			},
			"code": {
				rule: "required;code",
				msg: {
					required: "请输入图像验证码！",
					code:"请输入正确的图片验证码(4位字符)"
				},
				tip: "请输入图片中的字符，不区分大小写"
			}
		}
	});
	
	//提交密码修改
	$("#submit-psw").unbind("click").click(function(){
		var $this = $(this);
		if($("#check_way_mobile").is(":checked")){//手机验证
			$("#user_psw_form").validator('setField', {
				oldpsw: null
			});
			$("#user_psw_form").validator('setField', 'smCode', 'required;smCode;');
			$('#user_psw_form').validator('setMsg', {
				required: '请输入动态验证码！',
				smCode: '请输入正确的短信验证码(6位数字)！'
			});
			$("#user_psw_form").trigger("validate");//表单验证
			$('#user_psw_form').isValid(function(v){
				//表单验证通过
				if(v){
					$this.prop("disabled",true);
					submitForm();
				}
			});
		}else{
			$("#user_psw_form").validator('setField', {
				smCode: null
			});
			$("#user_psw_form").validator('setField', 'oldpsw', 'required;');
			$('#user_psw_form').validator('setMsg', {
				required: '请输入旧密码！'
			});
			$("#user_psw_form").trigger("validate");//表单验证
			$('#user_psw_form').isValid(function(v){
				//表单验证通过
				if(v){
					$this.prop("disabled",true);
					submitForm();
				}
			});
		}
		$this.prop("disabled",false);
	});
	
	//点击发送验证码
	$("#get_code").unbind("click").click(function(){
		var flag=true;
		$(".error-info").html("");
		//设置忽略短信验证码 后验证表单
		$("#user_psw_form").validator('setField', {
			smCode: null,
			oldpsw: null
		} );
		$("#user_psw_form").trigger("validate");//表单验证
		//设置忽略短信验证码 后验证表单
		$('#user_psw_form').isValid(function(v){
			//表单验证通过
			if(v){
			}else{
				flag=false;
			}
		});
		if(!flag){
			return;
		}
		var $this = $(this);
		$this.prop("disabled",true);//发送请求前禁用按钮，避免快速点击发生多次发送短信事情
		send_code_time($("#get_code"));
		$.ajax({
			url :contextPath+"/updateUserInfo/generateSmsCodeUpdate"+$("#currPage").val()+".jhtml",
	        type: "POST",
	        timeout:5000,
	        data:{'mobileNumber':$("#mobileNumberSend").val()},
	        success:function(result){
	        	if(result.code==-1){
					window.location.reload(true);
				}
	        	if(result.code!=0){
	        		resetSendSmBut($("#get_code"));
	        		$(".error-info").html(result.desc);
	        		//$("#get_code").prop("disabled",false);
				}else{
					//send_code_time($("#get_code"));
				}
	        },
	        error:function(){
	        	resetSendSmBut($("#get_code"));
	        	$(".error-info").html("验证码发送失败，请重试！");
	        	//$("#get_code").prop("disabled",false);
	        }
		});
	});
	
	/*
	*发送验证码倒计时
	*params:
	*$btn当前点击的按钮
	*/
	var timer;
	function send_code_time($btn){
		var count = 60;
		$btn.addClass("disabled").prop("disabled",true).text("已发送（"+count+"s）");
		
		 timer = setInterval(function(){
			count--;
			if(count<=0){
				clearInterval(timer);//清除倒计时
				$btn.removeClass("disabled").prop("disabled",false).text("重新获取验证码");
			}else{
				$btn.text("已发送（"+count+"s）");
			}
			
		},1000);
	}
});

/*
*重置发送按钮状态
*params:
*$btn当前点击的按钮
*/
function resetSendSmBut($btn){
	clearInterval(timer);//清除倒计时
	$btn.removeClass("disabled").prop("disabled",false).text("重新获取验证码");
}

function loadValidationCode(){
	var srcUrl =contextPath+"/updateUserInfo/generateValidationCodeForUpdate.jhtml?type="+"updatePwd"+"&rand="+Math.random();
	$("#validationCode").attr("src",srcUrl);
}

function submitForm(){
	  var urlPost=$("#user_psw_form").attr("action");
	  $.ajax({
			url :urlPost,
          type: "POST",
          async:false,
          data:{'mobileNumber':$("#mobileNumberSend").val(),'validationCode':$("#codeId").val(),'smCode':$("#smCodeId").val(),'oldPwd':hex_md5($("#oldpsw").val(), ''),'newPwd':strEnc($("#newPwd1").val(), ''),'newPwd2':strEnc($("#newPwd2").val(), '')},
			success:function(result){
				if(result.code==-1){
					window.location.reload(true);
				}
				if(result.code!=0){
					$(".error-info").html(result.desc);
					$("#get_code").prop("disabled",false);
					//验证失败情况下需刷新验证码
					loadValidationCode();
				}else{
					$("#user_psw_form").attr("action",contextPath+"/"+result.redirectUrl);
					$("#user_psw_form").submit();
				}
			}
		});
}

//后台校验图片验证码
function validateCodeId(){
	var ret=false;
	$.ajax({
		url:contextPath+"/updateUserInfo/validateCode.jhtml",
		type:"POST",
		async:false,
		data:{'validationCode':$("#codeId").val()},
		success:function(result){
			if(result.code==-1){
				window.location.reload(true);
			}
			if(result.code!=0){
				ret= false;
			}else{
				ret= true;
			}
		},
		error:function(){
			ret= false;
		}
	});
	return ret;
}

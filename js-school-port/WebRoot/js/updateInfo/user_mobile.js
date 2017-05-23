﻿$(function(){
	//获取图片验证码
	loadValidationCode();
	$("#get_code").prop("disabled",true);
	//表单验证
	$("#mobile_first_form").validator({ 
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
			
			code:function(element){
				var re = /^\w{4}$/;
				var flag=false;
				if(re.test(element.value)){
					flag=validateCodeId();
				} 
				if(flag==null||flag=="undefined"){
					return false;
				}
				$("#get_code").prop("disabled",!flag);
				return flag;
			}
		},
		
		fields: {
			"smCode": {
				rule: "required;smCode",
				msg: {
					required: "请输入动态验证码!",
					smCode: "请输入正确的短信验证码(6位数字)！"
				}
			},
			"code": {
				rule: "required;code",
				msg: {
					required: "请输入校验码!",
					code:"请输入正确的图片验证码(4位字符)"
				},
				tip: "请输入图片中的字符，不区分大小写"
			}
		
		}
	});
	//表单验证
	$("#mobile_second_form").validator({ 
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
			mobile:function(element){
				var re = /^1\d{10}$/;
				var flag=re.test(element.value);
				
				return flag;
			},
			mobileSame:function(element){
				var flag=$("#mobileNumberHide").val()!=$('#newmobile').val();
				$("#get_code").prop("disabled",!flag);
				return flag;
			}
		},
		fields: {
			"smCode": {
				rule: "required;smCode",
				msg: {
					required: "请输入动态验证码！",
					smCode: "请输入正确的短信验证码(6位数字)！"
				}
			},
			"newmobile": {
				rule: "required;mobile;mobileSame",
				msg: {
					required: "绑定新手机必填！",
					mobile:"请输入正确的手机号！",
					mobileSame:"新手机号不能与原手机号相同！"
				}
			}
		}
	});
	
	//第一步提交修改
	$("#submit_first").unbind("click").click(function(){
		var $this = $(this);
		$("#mobile_first_form").trigger("validate");//表单验证
		$('#mobile_first_form').isValid(function(v){
			//表单验证通过
			if(v){
				$this.prop("disabled",true);
				var urlPost=$("#mobile_first_form").attr("action");
				  $.ajax({
					  url:urlPost,
			          type: "POST",
			          async:false,
			          data:{'mobileNumber':$("#mobileNumberHide").val(),'validationCode':$("#codeId").val(),'smCode':$("#smCodeId").val()},
						success:function(result){
							if(result.code==-1){
								window.location.reload(true);
							}
							if(result.code!=0){
								//验证失败情况下需刷新验证码
								loadValidationCode();
								 $this.prop("disabled",false);
								$(".error-info").html(result.desc);
							}else{
								$(".error-info").html("");
								$("#tokenId").val(result.tokenId);
								$("#mobile_first_form").attr("action",contextPath+"/"+result.redirectUrl);
								$("#mobile_first_form").submit();
							}
						}
						
					});
			}
		});
		
	});
	//第二步提交修改
	$("#submit_second").unbind("click").click(function(){
		var $this = $(this);
		$("#mobile_second_form").trigger("validate");//表单验证
		$('#mobile_second_form').isValid(function(v){
			//表单验证通过
			if(v){
				$this.prop("disabled",true);
				 var urlPost=$("#mobile_second_form").attr("action");
				  $.ajax({
						url :urlPost,
			            type: "POST",
			            async:false,
			            data:{'mobileNumberOld':$("#mobileNumberHide").val(),'mobileNumberNew':$('#newmobile').val(),'smCode':$("#smCodeId").val(),'tokenId':$("#tokenId").val()},
						success:function(result){
							if(result.code==-1){
								window.location.reload(true);
							}
							if(result.code!=0){
								 $this.prop("disabled",false);
								$(".error-info").html(result.desc);
							}else{
								$(".error-info").html("");
								$("#mobile_second_form").attr("action",contextPath+"/"+result.redirectUrl);
								$("#mobile_second_form").submit();
							}
						}
					});
			}
		});
		
	});
	
	//点击发送验证码
	$("#get_code").unbind("click").click(function(){
		var $this = $(this);
		$this.prop("disabled",true);//发送请求前禁用按钮，避免快速点击发生多次发送短信事情
		send_code_time($("#get_code"));
		sendSmsCode();
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

function sendSmsCode(){
	$(".error-info").html("");
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
        	$(".error-info").html("发送失败，请重试！");
        	//$("#get_code").prop("disabled",false);
        }
	});
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
function loadValidationCode(){
		var srcUrl =contextPath+"/updateUserInfo/generateValidationCodeForUpdate.jhtml?type="+"updateMobile"+"&rand="+Math.random();
		$("#validationCode").attr("src",srcUrl);
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


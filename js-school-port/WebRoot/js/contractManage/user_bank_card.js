﻿var timer;
$(function(){
	$("#selected_bank").find("img").attr("src", $("div .hover").find("input[name='bankLogoUrlHide']").val());
	$("#cardNumShow").html( $("div .hover").find("div .bankShow").html());
	
	//表单验证
	$("#bank_card_form").validator({ 
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
			bankCard:function(element){
//				var re = /^\d{16}$/;
//				return re.test(element.value);
				return luhmCheck(element.value);
			},
			mobile:function(element){
				var re = /^1\d{10}$/;
				return re.test(element.value);
			}
		},
		fields: {
			"cardNumber": {
				rule: "required;bankCard",
				msg: {
					required: "储蓄卡号必填!",
					bankCard: "请输入正确的银行卡号！"
				}
			},
			"smCode": {
				rule: "required;smCode",
				msg: {
					required: "短信验证码必填!",
					smCode: "请输入正确的短信验证码(6位数字)！"
				}
			},
			"reserveMobile": {
				rule: "required;mobile",
				msg: {
					required: "银行预留手机必填!",
					mobile:"请输入正确的手机号！"
				},
				tip: "请填写办卡时银行预留的手机号码"
			}
		}
	});
	//银行卡
	$(".user-bank-card").click(function(){
		var $this = $(this);
		$this.siblings(".user-bank-card").removeClass("hover");
		$this.addClass("hover");
		$("#bank_card_form").stop().slideUp();
		$("#submit-psw").prop("disabled",false);
		$("#selected_bank").find("img").attr("src",$this.find("input[name='bankLogoUrlHide']").val());
		$("#cardNumShow").html($this.find("div .bankShow").html());
		//todo
	});
	//点击添加银行
	$("#add_bank").unbind("click").click(function(){
		$("#pay_select_bank").show();
	});
	//点击选择银行弹框里的关闭
	$("#close_dialog,#dialog_close_r").unbind("click").click(function(){
		$("#pay_select_bank").hide();
	});
	/*
	*向左箭头
	*/
	$("#prev-btn").unbind("click").click(function(){
		var current = 1;
		var total = $("#bank-list-ul").find(".bank-list").length;
		$("#bank-list-ul").find(".bank-list").each(function(index){
			if($(this).is(":visible")){
				current = (index+1);
			}
		});
		if(current>1){
			$(".pay-bank-num").text((current-1)+"/"+total);
			$("#bank-list-ul").find(".bank-list").hide();
			$("#bank-list-ul").find(".bank-list").eq(current-2).show();
		}
		
	});
	/*
	*向右箭头
	*/
	$("#next-btn").unbind("click").click(function(){
		var current = 1;
		var total = $("#bank-list-ul").find(".bank-list").length;
		$("#bank-list-ul").find(".bank-list").each(function(index){
			if($(this).is(":visible")){
				current = (index+1);
			}
		});
		if(current<total){
			$(".pay-bank-num").text((current+1)+"/"+total);
			$("#bank-list-ul").find(".bank-list").hide();
			$("#bank-list-ul").find(".bank-list").eq(current).show();
		}
	});
	
	//选择银行
	$(".bank-list li").click(function(){
		var $this = $(this);
		$("#pay_select_bank").hide();
		$("#bank_card_num").hide();
		$("#bank_card_form").stop().slideDown();
		$("#submit-psw").prop("disabled",true);
		$("#selected_bank").html($this.html());
		$("#relationBankId").val($this.find("input[name='relationBankId']").val());
		$(".user-bank-card").removeClass("hover");
	});
	


	//点击获取验证码
	$("#get_code").unbind("click").click(function(){
		$("#pay-step-last-error-info").text("");
		var $this = $(this);
		$("#bank_card_form").validator('setField', {
			smCode: null
		});
		$("#bank_card_form").trigger("validate");//表单验证
		$('#bank_card_form').isValid(function(v){
			//表单验证通过
			if(v){
		
				$this.prop("disabled",true);//发送请求前禁用按钮，避免快速点击发生多次发送短信事情
				send_code_time($this)
				var relationBankId = $("#relationBankId").val();
				var cardNumber = $("#cardNumber").val();
				var reserveMobile = $("#reserveMobile").val();
				var instalmentId=$("#instalmentId").val();
				$.ajax({
				//	async : true,
					timeout : 5000,
					type : "post",
					url : contextPath + "/bankCard/validateBindMobileNumber.jhtml",
					data : "relationBankId=" + relationBankId + "&cardNumber=" + cardNumber + "&reserveMobile=" + reserveMobile + "&instalmentId=" + instalmentId,
				//	dataType : 'json',
					error : function (XMLHttpRequest, textStatus, errorThrown) {
					    // 通常 textStatus 和 errorThrown 之中
					    // 只有一个会包含信息
						resetSendSmBut($this);
						$("#pay-step-last-error-info").text("发送失败,请重试"); // 调用本次AJAX请求时传递的options参数
						//$this.prop("disabled",false);//按钮禁用打开
					},
					success : function(data, textStatus){
						if(data.code==-1){
							window.location.reload(true);
						}
						if(data.code == "0"){
							//alert("成功");
							//send_code_time($this);//发送成功后开始倒计时
						}else{
							resetSendSmBut($this);
							$("#pay-step-last-error-info").text(data.desc);
							//$this.prop("disabled",false);//按钮禁用打开
						}
					},
				});
				
				//send_code_time($this);//此行代码为重复代码，请在ajax请求调通后，删除此行代码
			}
		});
	});
	//绑定银行卡
	$("#bind_card").click(function(){
		var $this = $(this);
		$("#pay-step-last-error-info").text("");
		$("#bank_card_form").validator('setField', 'smCode', 'required;smCode;');
		$('#bank_card_form').validator('setMsg', {
			required: '短信验证码必填!',
			smCode: '请输入正确的短信验证码(6位数字)！'
		});

		$("#bank_card_form").trigger("validate");//表单验证
		var instalmentId = $("#instalmentId").val();
		var relationBankId = $("#relationBankId").val();
		var cardNumber = $("#cardNumber").val();
		var reserveMobile = $("#reserveMobile").val();
		var smCode=$("#smCode").val();
		var bankCardId=$("#currentbankCardId").val();
		$('#bank_card_form').isValid(function(v){
			//表单验证通过
			if(v){ 
				$this.prop("disabled",true);//发送请求前禁用按钮,待返回结果后，禁用打开
				$.ajax({
				//	async : true,
					timeout : 5000,
					type : "post",
					url : contextPath + "/bankCard/bindBankCard.jhtml",
					data : "relationBankId=" + relationBankId  +"&instalmentId="+instalmentId + "&cardNumber=" + cardNumber + "&reserveMobile=" + reserveMobile + "&smCode=" + smCode,
				//	dataType : 'json',
					error : function (XMLHttpRequest, textStatus, errorThrown) {
					    // 通常 textStatus 和 errorThrown 之中
					    // 只有一个会包含信息
						$("#pay-step-last-error-info").text("绑定失败,请重试");; // 调用本次AJAX请求时传递的options参数
						$this.prop("disabled",false);//按钮禁用打开
					},
					success : function(data, textStatus){
						if(data.code==-1){
							window.location.reload(true);
						}
						if(data.code == "0"){
							//alert("成功");
							 window.location.href=contextPath+"/contractManage/modifyBankCard.jhtml?instalmentId="+instalmentId+"&bankCardId="+data.bankCardId+"&rnd="+Math.random();
						}else{
							//alert(data.desc);
							$("#pay-step-last-error-info").text(data.desc);
							$this.prop("disabled",false);//按钮禁用打开
						}
					},
				});
				
			}
		});
	});
	//绑定成功后
	function bind_success(){
		$(".user-bank-card").removeClass("hover");
		var html = '';
		html += '<div class="user-bank-card hover">';
			html += '<div class="user-bank-cardin">';
				html += '<div class="user-bank">';
					html += '<span class="bank-logo"><img src="images/banklogo/bank_logo_002.jpg"></span><span class="lh34">储蓄卡</span>';
				html += '</div>';
				html += '<div class="user-bank-info">622******89789</div>';
				html += '<div class="user-bank-info">小丽</div>';
				html += '<i class="user-icon icon-selected"></i>';
			html += '</div>';
		html += '</div>';
		$(".user-bank-card").last().after(html);
		$("#bind_card,#submit-psw").prop("disabled",false);
		$("#bank_card_num").show();
		$("#bank_card_form").stop().slideUp();
	}
	/*
	*发送验证码倒计时
	*params:
	*$btn当前点击的按钮
	*/
	
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

function modifyBankCardOfInstalment(){
	var bindId=$("div .hover").find("input[name='bankCardId']").val();
	if(bindId==null||bindId==""){
		$("#pay-step-submit-error-info").text("请选择银行卡");
		return;
	}
	var instalmentId=$("#instalmentId").val();
	$.ajax({
		url:contextPath+"/contractManage/modifyBankCardSub.jhtml",
		type:"POST",
		data:{"bankCardId":bindId,"instalmentId":instalmentId},
		success:function(data){
			if(data.code==-1){
				window.location.reload(true);
			}
			if(data.code == "0"){
				//alert("成功");
				 window.location.href=contextPath+"/"+data.redirectUrl+"?rnd="+Math.random();
			}else{
				//alert(data.desc);
				$("pay-step-submit-error-info").text(data.desc);
			}
		}
	});
}
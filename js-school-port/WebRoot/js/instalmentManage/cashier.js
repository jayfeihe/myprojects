$(function(){
	$(".bank-list li").click(function(){
		$(this).siblings("li").removeClass("hover");
		$(this).addClass("hover");
		// 选中网银支付
		$(this).parents('#cpcn').find("input[name='payType']").prop('checked',true);
	});
	var paymentOrderId="";
	$("#btnSub").click(function(){
		$(".error-info").html("");
		var $this=$(this);
		$this.prop("disabled",true);
		// 支付类型-网银支付（cpcn）、微信支付(wx)
		var payType = $("input[name='payType']:checked").val();
		// 请求地址
		var url = contextPath+"/"+"instalmentManage/surePayBank.jhtml";
		if('wx' == payType) {
			url = contextPath+"/"+"instalmentManage/sureWxPay.jhtml";
		}
		// 网银支付
		if('cpcn' == payType) {
			var strBankId;
			$("div .hover").each(function(){
				var objTemp=$(this).find("input[name='relationBankId']");
				strBankId=objTemp.val();
			});
			if(strBankId==null||strBankId==""){
				$(".error-info").html("请选择银行卡！");
				$this.prop("disabled",false);
				return;
			}
		}
		
		$.ajax({
			url: url,
			type:"POST",
			async: false,
			data:{"relationBankId":strBankId,"paymentInfoToken":$("#paymentInfoToken").val()},
			success:function(data){
				if(data.code==-1){
					window.location.reload(true);
				}
				$this.prop("disabled",false);
				if(data.code!=0){
					$(".error-info").html(data.desc);
				}else{
					paymentOrderId=data.paymentOrderId;
					if('cpcn' == payType) {
						$("#message").val(data.message);
						$("#signature").val(data.signature);
						$("#mainForm").attr("target","_blank");
						$("#mainForm").attr("action", data.redirectUrl);
						$("#mainForm").submit();
						$("#dialog-pay").show();
					}
					if('wx' == payType) {
						$("#paymentOrderId").val(data.paymentOrderId);
						$("#mainForm").attr("action", data.redirectUrl);
						$("#mainForm").submit();
					}
				}
			},
			error:function(){
				$this.prop("disabled",false);
			}
		
		});
	});
	
	//弹框关闭和支付遇到问题
	$("#dialog_close_r").click(function(){
		$("#dialog-pay").hide();
		refreshPayNo();
	});
	
	//已完成支付
	$("#finish_pay").click(function(){
 
		$.ajax({
			url:contextPath+"/"+"instalmentManage/validataPaymentResult.jhtml",
			type:"POST",
			data:{"paymentOrderId":paymentOrderId},
			success:function(data){
				if(data.code==-1){
					window.location.reload(true);
				}
				if(data.code==0){
					$("#dialog-pay").hide();//关闭dialog-pay弹框
					 if(data.payFlag==true){
						 $("#dialog-pay-success").show();//打开支付成功弹框
					 }else{
						 $("#dialog-pay-fail").show();//打开支付失败弹框 
						 $(".error-info").html(data.desc);
					 }
				}else{
					$(".error-info").html(data.desc);
				}
			},
			error:function(){
			}
		
		});
	});
	
	$("#error_pay").click(function(){
		 window.location.href=contextPath+"/jsp/frame/help_center.html";
		 return false;
	});
	
	//支付失败弹框中的确定按钮
	$("#pay-fail-close").click(function(){
		$("#dialog-pay-fail").hide();//关闭支付失败弹框
		refreshPayNo();
	});
	
	
	function refreshPayNo(){
		$(".error-info").html("");
		$.ajax({
			url:contextPath+"/"+"instalmentManage/refreshPaymentOrderNo.jhtml",
			type:"POST",
			data:{"paymentInfoToken":$("#paymentInfoToken").val()},
			success:function(data){
				if(data.code==-1){
					window.location.reload(true);
				}
				if(data.code!=0){
					$(".error-info").html(data.desc);
				}else{
					$("#_paymentOrderNo").html(data.paymentOrderNo);
				}
			},
			error:function(){
				
			}
		
		});
	}
	
	// 点击选择付款渠道
	$("input[name='payType']").on("click",function(){
		var payTypeVal = $(this).val();
		if(payTypeVal == "wx") {
			$("#wxPayAmount").show();
			$("#cpcnPayAmount").hide();
			// 去除银行选中
			$("#cpcn").find('ul li.hover').each(function(){
				$(this).removeClass('hover');
			})
		} 
		if(payTypeVal == "cpcn"){
			$("#wxPayAmount").hide();
			$("#cpcnPayAmount").show();
		}
	});
	
	// 显示更多银行
	$(".bank-list").find("#moreBank").on("click", function(){
		$(".bank-list").find(".is_show_2").remove();
		$(".bank-list").find(".is_show_3").show();
	});
});


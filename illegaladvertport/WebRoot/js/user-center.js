var user = {
	// 打开修改密码弹框
	openPwdModel : function() {
		$("#updatePwd").on('click',function(){
			$("#updatePwdModal").modal({
				show:true,
				backdrop:'static',
				keyboard:false
			});
		});
	},
	
	pwdAuth : function() {
		$('#password').on('keyup', function() {
			if ($(this).val() == '') {
				$('#helpBlock1').html("当前密码不能为空").show();
			} else {
				$('#helpBlock1').html("");
			}
		});
		
		$('#newpassword').on('keyup', function() {
			if ($(this).val().length < 6) {
				$('#helpBlock2').show();
			} else {
				$('#helpBlock2').hide();
			}
		});

		$('#confirmpassword').on('keyup', function() {
			if ($(this).val() != $('#newpassword').val()) {
				$('#helpBlock3').show();
			} else {
				$('#helpBlock3').hide();
			}
		});
	},
	
	// 重置修改密码表单
	resetPwdForm　: function() {
		$('#updatePwdModal').on('hidden.bs.modal', function (e) {
			$("input[name='password']").val('');
			$("input[name='newpassword']").val('');
			$("input[name='confirmpassword']").val('');
			$('#helpBlock1').hide();
			$('#helpBlock2').hide();
			$('#helpBlock3').hide();
		});
	}
}
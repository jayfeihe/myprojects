/**
 * 广告主管理
*/
var sysUser = {
		URL: {
			updatePwd_url:'/sys/user/updatePwd',
			queryOldPwd_url:'/sys/user/queryOldPwd'
		},
		
		// 列表查询
		submitForm : function(params) {
			var curPage = params['curPage'];
			var url = params['url'];
			var params = $("#queryForm").serialize();
			$.ajax({
				method : 'get',
				url : url +'?curPage=' + curPage,
				dataType : 'html',
				data : params,
				success : function(data) {
					$("#queryList").html(data);
				}
			})
		},
		
		detail : {
			validateForm : function(params) {
				$(".err_tip").each(function(){
					$(this).text('');
				});
				var formName = params['form'];
				var $form = $("#"+formName);
				var a = $form.serializeArray();
				var obj = {};
				$.each(a, function(k, v) {
					obj[v.name] = v.value;
				});
				
				
				if(obj['oldPwd'] == '') {
					$form.find("#oldPwdTip").text('原密码不能为空');
					return false;
				}
				
				if(obj['pwd'] == '') {
					$form.find("#pwdTip").text('密码不能为空');
					return false;
				}
				
				var pwdReg = /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9]{6,12}$/;
				if(!pwdReg.test(obj['pwd'])) {
					$form.find("#pwdTip").text('密码为6-12位大小写字母，数字');
					return false;
				}
				
				if(obj.confirmPwd == '') {
					$form.find("#confirmPwdTip").text('确认密码不能为空');
					return false;
				}
				
				if(obj['confirmPwd'] != obj['pwd']) {
					$form.find("#confirmPwdTip").text('两次密码输入不一致');
					return false;
				}
				
				return true;
			},
			
			updatePwd :function(params) {
				var redirect_url = params['redirect_url'];
				$("#confirmUpdate").on('click',function(){
					// 验证表单
					var pass = sysUser.detail.validateForm({'form':'updateForm'});
					if (pass) {
						var userId = $("#updateForm").find("#userId").val();
						var oldPwd = $("#updateForm").find("#oldPwd").val();
						$.get(sysUser.URL.queryOldPwd_url,{'userId':userId,'pwd':hex_md5(oldPwd)},function(result){
							if(result.success == true) {
								var formParams = $("#updateForm").serialize();
								$.post(sysUser.URL.updatePwd_url,formParams,function(data){
									if(data.success == true) {
										alert(data.message);
										jumpToMenu(redirect_url);
										$('#updatModal').modal("hide");
									 } else {
										 alert(data.message);
									 }
								});
							} else {
								$("#updateForm").find("#oldPwdTip").text(result.message);
							}
						});
					}
				})
			}
		},
		
		openUpdateModal : function() {
			$(".updateBtn").each(function(){
				$(this).on('click',function(){
					var userId = $(this).attr('userId');
					$("#updateForm").find("#userId").val(userId);
					// 弹出添加对话框
					$("#updateModal").modal({
						backdrop:'static',
						keyboard:false,
						show:true
					});
				});
			})
		},
		
		// 关闭modal事件
		onCloseModal : function() {
		   	// 更新框关闭清除表单数据
		    $('#updateModal').on('hidden.bs.modal', function (e) {
		    	$('#updateForm')[0].reset();
		    	// 清除提示
		    	$(".err_tip").each(function(){
					$(this).text('');
				});
		   	})
		}
}
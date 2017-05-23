/**
 * 广告主管理
*/
var userAccount = {
		URL: {
			add_url:'/account/add',
			update_url:'/account/update',
			queryById_url:'/account/queryById',
			queryByAccount_url:'/account/queryByAccount'
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
		
		queryForm : function(params) {
			var curPage = params['curPage'];
			var url = params['url'];
			$("#queryBtn").on('click',function(){
				userAccount.submitForm({
					'url':url,
					'curPage':curPage
				});
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
				
				if(obj['userCompany.companyName'] == '') {
					$form.find("#companyNameTip").text('公司名称不能为空');
					return false;
				}
				
				if(obj['userCompany.companyUrl'] == '') {
					$form.find("#companyUrlTip").text('公司Url地址不能为空');
					return false;
				}
				
				if(obj['userAccount.account'] == '') {
					$form.find("#accountTip").text('账号不能为空');
					return false;
				}
				
				var acc= /^[A-Za-z]+$/;
				if(!acc.test(obj['userAccount.account'])) {
					$form.find("#accountTip").text('账号必须为英文字母');
					return false;
				}
				
				if(obj['userAccount.pwd'] == '') {
					$form.find("#pwdTip").text('密码不能为空');
					return false;
				}
				
				var pwdReg = /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9]{6,12}$/;
				if(!pwdReg.test(obj['userAccount.pwd'])) {
					$form.find("#pwdTip").text('密码为6-12位大小写字母，数字');
					return false;
				}
				
				if(obj.confirmPwd == '') {
					$form.find("#confirmPwdTip").text('确认密码不能为空');
					return false;
				}
				
				if(obj.confirmPwd != obj['userAccount.pwd']) {
					$form.find("#confirmPwdTip").text('两次密码输入不一致');
					return false;
				}
				
				if(obj['userAccount.userName'] == '') {
					$form.find("#userNameTip").text('联系人不能为空');
					return false;
				}
				
				var chinese =  /^[\u4e00-\u9fa5]{2,12}$/;
				if(!chinese.test(obj['userAccount.userName'])) {
					$form.find("#userNameTip").text('联系人必须为2-12位汉字');
					return false;
				}
				
				if(obj['userAccount.mobile'] == '') {
					$form.find("#mobileTip").text('手机号不能为空');
					return false;
				}
				
				var mobileReg = /^\d{11}$/;
				if(!mobileReg.test(obj['userAccount.mobile'])) {
					$form.find("#mobileTip").text('手机号必须为11位数字');
					return false;
				}
				
				var emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9-])+((\.[a-zA-Z0-9-]{2,3}){1,2})$/; 
				if(obj['userAccount.email'] != '') {
					if(!emailReg.test(obj['userAccount.email'])) {
						$form.find("#emailTip").text('邮箱格式不正确');
						return false;
					}
				}
				
				return true;
			},
			
			addAccount :function(params) {
				var redirect_url = params['redirect_url'];
				$("#confirmAdd").on('click',function(){
					// 验证表单
					var pass = userAccount.detail.validateForm({'form':'addForm'});
					if (pass) {
						// 校验url是否存在
						var account = $("#addForm").find("#account").val();
						$.get(userAccount.URL.queryByAccount_url,{'account':account},function(result){
							if(result.success == true) {
								// 不存在进行保存
								var formParams = $("#addForm").serialize();
								$.post(userAccount.URL.add_url,formParams,function(data){
									if(data.success == true) {
										alert(data.message);
										jumpToMenu(redirect_url);
										$('#addModal').modal("hide");
									 } else {
										 alert(data.message);
									 }
								});
								
							} else {
								$("#addForm").find("#accountTip").text(result.message);
							}
						});
					}
				})
			},
			
			updateAccount :function(params) {
				var redirect_url = params['redirect_url'];
				$("#confirmUpdate").on('click',function(){
					// 验证表单
					var pass = userAccount.detail.validateForm({'form':'updateForm'});
					if (pass) {
						var formParams = $("#updateForm").serialize();
						$.post(userAccount.URL.update_url,formParams,function(data){
							if(data.success == true) {
								alert(data.message);
								jumpToMenu(redirect_url);
								$('#updateModal').modal("hide");
							 } else {
								 alert(data.message);
							 }
						});
					}
				})
			}
		},
		
		openAddModal : function() {
			$("#addAccountBtn").on('click',function(){
				// 弹出添加对话框
				$("#addModal").modal({
					backdrop:'static',
					keyboard:false,
					show:true
				});
			});
		},
		
		openUpdateModal : function() {
			$(".updateAccountBtn").each(function(){
				$(this).on('click',function(){
					var id = $(this).attr('accountId');
					// 初始化数据
					$.get(userAccount.URL.queryById_url,{'id':id},function(data){
						$("#updateForm").find("#accountId").val(data.id);
						$("#updateForm").find('#compayId').val(data.compayId);
						$("#updateForm").find("#account").val(data.account);
						//$("#updateForm").find("#pwd").val(data.pwd);
						//$("#updateForm").find("#confirmPwd").val(data.pwd);
						$("#updateForm").find("#userName").val(data.userName);
						$("#updateForm").find("#email").val(data.email);
						$("#updateForm").find("#fax").val(data.fax);
						$("#updateForm").find("#mobile").val(data.mobile);
						$("#updateForm").find("#telephone").val(data.telephone);
						$("#updateForm").find("#qq").val(data.qq);
						$("#updateForm").find("#status").val(data.status);
						
						$("#updateForm").find('#companyUrl').selectpicker('val', data.compayId);
						$("#updateForm").find('#companyName').selectpicker('val', data.compayId);
					});
					
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
			// 添加框关闭清除表单数据
		    $('#addModal').on('hidden.bs.modal', function (e) {
		    	$('#addForm')[0].reset();
		    	$('#addForm').find('.selectpicker').each(function(){
		    		$(this).selectpicker('val', '');
		    	}); 
		    	// 清除提示
		    	$(".err_tip").each(function(){
					$(this).text('');
				});
		   	})
		   	
		   	// 更新框关闭清除表单数据
		    $('#updateModal').on('hidden.bs.modal', function (e) {
		    	$('#updateForm')[0].reset();
		    	$('#updateForm').find('.selectpicker').each(function(){
		    		$(this).selectpicker('val', '');
		    	});
		    	// 清除提示
		    	$(".err_tip").each(function(){
					$(this).text('');
				});
		   	})
		},
		
		// 选择广告主或媒体时名称和url赋值操作
		companyChange : function(params) {
			var form =  params['form'];
			var $form = $("#"+form);
			
			// 名称改变自动带出url
			$form.find('#companyName').on('hidden.bs.select', function (e) {
				var companyId = $(this).val();
				$form.find('#companyUrl').selectpicker('val', companyId);
				$form.find("#compayId").val(companyId);
			});
			
			// url改变自动带出名称
			$form.find('#companyUrl').on('hidden.bs.select', function (e) {
				var companyId = $(this).val();
				$form.find('#companyName').selectpicker('val', companyId);
				$form.find("#compayId").val(companyId);
			})
		}
}
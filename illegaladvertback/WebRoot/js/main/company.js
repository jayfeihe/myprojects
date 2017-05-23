/**
 * 广告主管理
*/
var company = {
		URL: {
			add_url:'/company/add',
			update_url:'/company/update',
			updateStatus_url:'/company/updateStatus',
			queryByCompanyId_url:'/company/queryByCompanyId',
			queryByCompanyUrl_url:'/company/queryByCompanyUrl'
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
				company.submitForm({
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
				
				if(obj.companyName == '') {
					$form.find("#companyNameTip").text('公司全称不能为空');
					return false;
				}
				
				if(obj.companyUrl == '') {
					$form.find("#companyUrlTip").text('Url地址不能为空');
					return false;
				}
				
				if(obj.industryId == '') {
					$form.find("#industryIdTip").text('行业不能为空');
					return false;
				}
				
				if(obj.linkName == '') {
					$form.find("#linkNameTip").text('联系人不能为空');
					return false;
				}
				
				var chinese =  /^[\u4e00-\u9fa5]{2,12}$/;
				if(!chinese.test(obj.linkName)) {
					$form.find("#linkNameTip").text('联系人必须为2-12位汉字');
					return false;
				}
				
				if(obj.linkPhone == '') {
					$form.find("#linkPhoneTip").text('联系人电话不能为空');
					return false;
				}
				
				return true;
			},
			
			addCompany :function(params) {
				var redirect_url = params['redirect_url'];
				$("#confirmAdd").on('click',function(){
					// 验证表单
					var pass = company.detail.validateForm({'form':'addForm'});
					if (pass) {
						// 校验url是否存在
						var company_url = $("#addForm").find("#companyUrl").val().trim();
						var type = $("#addForm").find("#type").val();
						$.get(company.URL.queryByCompanyUrl_url,{'companyUrl':company_url,'type':type},function(result){
							if(result.success == true) {
								// 不存在进行保存
								var formParams = $("#addForm").serialize();
								$.post(company.URL.add_url,formParams,function(data){
									if(data.success == true) {
										alert(data.message);
										jumpToMenu(redirect_url);
										$('#addModal').modal("hide");
									 } else {
										 alert(data.message);
									 }
								});
								
							} else {
								$("#addForm").find("#companyUrlTip").text(result.message);
							}
						});
					}
				})
			},
			
			updateCompany :function(params) {
				var redirect_url = params['redirect_url'];
				$("#confirmUpdate").on('click',function(){
					// 验证表单
					var pass = company.detail.validateForm({'form':'updateForm'});
					if (pass) {
						var formParams = $("#updateForm").serialize();
						$.post(company.URL.update_url,formParams,function(data){
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
			},
			
			updateStatus :function(params) {
				var redirect_url = params['redirect_url'];
				$(".updateStatus").each(function(){
					$(this).on('click',function(){
						var curStatus = $(this).attr("curStatus");
						var companyId = $(this).attr('companyId');
						$.post(company.URL.updateStatus_url,{'status':curStatus,'companyId':companyId},function(data){
							if(data.success == true) {
								jumpToMenu(redirect_url);
							} else {
								alert(data.message);
							}
						});
					})
				});
			},
		},
		
		openAddModal : function() {
			$("#addCompanyBtn").on('click',function(){
				// 弹出添加对话框
				$("#addModal").modal({
					backdrop:'static',
					keyboard:false,
					show:true
				});
			});
		},
		
		openUpdateModal : function() {
			$(".updateCompanyBtn").each(function(){
				$(this).on('click',function(){
					var id = $(this).attr('companyId');
					// 初始化数据
					$.get(company.URL.queryByCompanyId_url,{companyId:id},function(data){
						$("#updateForm").find("#companyId").val(data.companyId);
						$("#updateForm").find("#companyName").val(data.companyName);
						$("#updateForm").find("#companyUrl").val(data.companyUrl);
						$("#updateForm").find("#industryId").val(data.industryId);
						$("#updateForm").find("#linkName").val(data.linkName);
						$("#updateForm").find("#linkPhone").val(data.linkPhone);
						$("#updateForm").find("#linkEmail").val(data.linkEmail);
						$("#updateForm").find("#companyAddr").val(data.companyAddr);
						$("#updateForm").find("#companyPhone").val(data.companyPhone);
						$("#updateForm").find("#postCode").val(data.postCode);
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
		    	// 清除提示
		    	$(".err_tip").each(function(){
					$(this).text('');
				});
		   	})
		   	
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
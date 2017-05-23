var feedback = {
		// 初始化
		init:function(form){
			$(".BangZhu").hover(function(){
				$(".wen_top").show();
			},function(){
				$(".wen_top").hide();
			})
			
			var $form = $("#"+form);
			
			$form.find(".changeTime>.time").on("click",function(){
				$form.find(".changeTime>.time_box").show();
			})
			
			$form.find("#confirmDate").on("click",function(){
				var date = $.trim($form.find("#date").val());
				var hour = $.trim($form.find("#hour").val());
				var minu = $.trim($form.find("#minute").val());
				
				// 日期为空校验
				if(date == '' || hour == '' || minu == '') {
					alert("请填写整改时间");
					return;
				}
				
				// 日期格式校验
				var corDateStr = date+' '+hour+':'+minu;
				var dateReg = /^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2})$/;
				if(!dateReg.test(corDateStr)) {
					alert('整改时间格式不正确');
					return;
				}
				
				// 日期范围校验
				var now = new Date();
				var corDate = new Date(date);
				corDate.setHours(hour);
				corDate.setMinutes(minu);
				if(corDate.getTime() >= now.getTime()) {
					alert("整改时间不能大于当前时间");
					return;
				}
				
				var corDate = new Date(date);
				corDate.setHours(hour);
				corDate.setMinutes(minu);
				$form.find("#correctionTime").val(corDate.Format('yyyy-MM-dd hh:mm'));
				
				$form.find(".changeTime>.time_box").hide();
			})
			
			$form.find("#cancelDate").on("click",function(){
				$form.find("#date").val('');
				$form.find("#hour").val('');
				$form.find("#minute").val('');
				$form.find("#correctionTime").val("");
				$form.find(".changeTime>.time_box").hide();
			})
		},
		
		//创建表格中其他主体方整改信息
		createOtherInfo:function($form,info_id){
			$form.find("#infoId").val(info_id);
			$.get('/feedback/getOtherInfosByInfoId',{'infoId':info_id},function(result){
				var $table = $form.find('table>tbody');
				for(var i=0;i<result.length;i++) {
					var ele = '<tr>';
					var roleType = result[i].roleType;
					if (roleType == '1') ele+='<td>广告主</td>';
					if (roleType == '2') ele+='<td>媒体</td>';
					
					if (result[i].correctionTime == null) 
						ele+='<td>未知</td>';
					else 
						ele+='<td>'+new Date(result[i].correctionTime).Format('yyyy-MM-dd hh:mm')+'</td>';
					
					if (result[i].correctionTime == null) 
						ele+='<td>未知</td>';
					else
						ele+='<td>'+result[i].remark+'</td>';
					
					ele+='</tr>';
					
					$table.append(ele);
				}
			});
		},
		
		// 获取我方真该信息会填页面显示
		getSelfInfo:function($form,info_id){
			$.get('/feedback/getSelfInfosByInfoId',{'infoId':info_id},function(result){
				var correctionTime = result.correctionTime;
				if (correctionTime != null && correctionTime != '') {
					var corDate = new Date(correctionTime);
					$form.find("#correctionTime").val(corDate.Format('yyyy-MM-dd hh:mm'));
					$form.find("#date").val(corDate.Format('yyyy-MM-dd'));
					$form.find("#hour").val(corDate.Format('hh'));
					$form.find("#minute").val(corDate.Format('mm'));
				}
				var remark = result.remark;
				$form.find("#wenben").val(remark);
			});
		},
		
		// 打开整改信息
		openModel:function(parentDiv){
			// 确认整改
			$("#"+parentDiv).on('click','.correctConfirmBtn',function(){
				var info_id = $(this).attr('info_id');
				// 创建表格其他主体信息
				feedback.createOtherInfo($("#correctConfirmForm"),info_id);
				$("#correctConfirmModal").modal({
					show:true,
					backdrop:'static',
					keyboard:false
				});
			});
			
			// 已整改
			$("#"+parentDiv).on('click','.correctedBtn',function(){
				var info_id = $(this).attr('info_id');
				// 创建表格其他主体信息
				feedback.createOtherInfo($("#correctedForm"),info_id);
				// 获取我方整改信息，会填页面
				feedback.getSelfInfo($("#correctedForm"),info_id);
				$("#correctedModal").modal({
					show:true,
					backdrop:'static',
					keyboard:false
				});
			});
		},
		
		// 重置整改信息表单
		resetForm:function($form){
			$form.find("#infoId").val('');
			$form.find('table>tbody').html('');
			$form.find("#correctionTime").val('');
			$form.find("#date").val('');
			$form.find("#hour").val('');
			$form.find("#minute").val('');
			$form.find("#wenben").val('');
			
			$form.find(".changeTime>.time_box").hide();
		},
		
		// 关闭整改信息弹出框
		closeModel:function(){
			
			// 确认整改model关闭
			$('#correctConfirmModal').on('hidden.bs.modal', function (e) {
				feedback.resetForm($("#correctConfirmForm"));
			});
			// 已整改model关闭
			$('#correctedModal').on('hidden.bs.modal', function (e) {
				feedback.resetForm($("#correctedForm"));
			})
		},
		
		// 表单校验
		validateForm:function($form) {
			var date = $.trim($form.find("#date").val());
			var hour = $.trim($form.find("#hour").val());
			var minu = $.trim($form.find("#minute").val());
			var remark = $.trim($form.find("#wenben").val());
			
			// 日期为空校验
			if(date == '' || hour == '' || minu == '') {
				alert("请填写整改时间");
				return false;
			}
			
			// 日期格式校验
			var corDateStr = date+' '+hour+':'+minu;
			var dateReg = /^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2})$/;
			if(!dateReg.test(corDateStr)) {
				alert('整改时间格式不正确');
				return false;
			}
			
			// 日期范围校验
			var now = new Date();
			var corDate = new Date(date);
			corDate.setHours(hour);
			corDate.setMinutes(minu);
			if(corDate.getTime() >= now.getTime()) {
				alert("整改时间不能大于当前时间");
				return false;
			}
			
			$form.find("#correctionTime").val(corDate.Format('yyyy-MM-dd hh:mm'));
			
			// 备注信息为空校验
			if(remark == '') {
				alert("请填写备注信息");
				return false;
			}
			
			return true;
		},
		
		// 提交整改
		submitCorrect :function(params){
			var pt = params['pageType']; // 1:确认违法列表，2：广告主详情页，3：媒体详情页
			var page = params['page'];
			
			$("#submitCorrect").on('click',function(){
				if(feedback.validateForm($("#correctConfirmForm"))){
					var params = $("#correctConfirmForm").serialize();
					$.ajax({
						url:'/feedback/correct',
						method:'post',
						data:params,
						success:function(data){
							if(data.success == 'true') {
								alert("提交整改成功");
								
								$("#correctConfirmModal").modal('hide');
								if(pt == '1') {
									loadConfirmAdvertIllegal(page);
									loadConfirmCreativeIllegal(page);
								} else if(pt == '2') {
									loadAdverterIllegal(page)
								} else if(pt == '3') {
									loadMediaIllegal(page);
								}
							} else {
								alert("抱歉，提交整改失败！");
								return;
							}
							
						},
						error:function(){
							alert("抱歉，系统出现错误！");
							return;
						}
					})
				}
			})
		},
		
		// 确认修改
		confirmUpdate :function(){
			$("#confirmUpdate").on('click',function(){
				if(feedback.validateForm($("#correctedForm"))){
					var params = $("#correctedForm").serialize();
					$.ajax({
						url:'/feedback/correct',
						method:'post',
						data:params,
						success:function(data){
							if(data.success == 'true') {
								alert("整改修改成功");
								
								$("#correctedModal").modal('hide');
							} else {
								alert("抱歉，整改修改失败！");
								return;
							}
							
						},
						error:function(){
							alert("抱歉，系统出现错误！");
							return;
						}
					})
				}
			})
		}
}
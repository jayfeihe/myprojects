
	var pageSize = 6;//账单分期数每页最多6条

$(function(){
	//从还款详情列表返回时，不直接加载“全部”标签内容，加载完该js后，模拟点击“已还款”
	if(currentLiParam!=null&&currentLiParam=='2'){
		
	}else if(currentLiParam!=null&&currentLiParam=='1'){
		
	}else {
		loadMain(contextPath+"/instalmentManage/queryInstalmentInfo.jhtml?currentLi=0");
	}
	
	//tab
	$("#user-loan-tabs li").click(function(){
		var $this = $(this);
		var index = $this.index();
		$this.addClass("current");
		$this.siblings("li").removeClass("current");
		
		if(index==0){
			loadMain(contextPath+"/instalmentManage/queryInstalmentInfo.jhtml?currentLi=0");
			$("#user-loan-tabcontainer").children(".user-loan-list").hide();
			$("#allOrwait").show();
		}else if(index==1){
			loadMain(contextPath+"/instalmentManage/queryInstalmentInfo.jhtml?instalmentStatus=0&currentLi=1");
			$("#user-loan-tabcontainer").children(".user-loan-list").hide();
			$("#allOrwait").show();
		}else if(index==2){ 
			 $.ajax({
					url :contextPath +"/instalmentManage/queryPaymentOrderAjax.jhtml",
			        type: "POST",
			        timeout:5000,
			        data:{'start':1},
			        success:function(result){
			        	if(result.code==-1){
							window.location.reload(true);
						}
			        	var dateList = result.list;
			        	 
			        		 createPage($("#ended_list_page"),{
				        		 pageCount:result.totalPage,
				                current:1,
				                backFn:function(page){
				                    //console.log(page);//打印页码 
				                    get_ajax(page,result.pageSize);
				                }
				            });
			        
			        	$("#user_ended_list tbody").html(get_list_html(dateList));
	        			$("#ended_list_page").find(".total-num").text(result.totalNum);
	        			if(result.totalNum==0){
	        				$("#ended_list_page").find(".total-page").text(0);
	        			}else{
	        				$("#ended_list_page").find(".total-page").text(result.totalPage);
	        			}
			        }
			 });
			 
			$("#user-loan-tabcontainer").children(".user-loan-list").hide();
			$("#payEndDiv").show();
		} 
	
	});
	
	//关闭弹框
	$("#dialog_close_r").unbind("click").click(function(){
		repayDialogHide();
	});
	//全部付清
	$("#repay_all").change(function(){
		var $this = $(this);
		if($this.is(":checked")){
			$("#dialog-loan-list").find("input[name='loanItem']").each(function(){
				$item=this;
				if(!$item.disabled){
					$item.checked=true;
				}
			});
				
			
		}else{
			$("#dialog-loan-list").find("input[name='loanItem']").prop("checked",false);
		}
		calAmount();
	});
	
	//至顶
	$("#go_top").click(function(){
		$(window).scrollTop(0);
	});
	
/*	//复杂分页
	var data = {
			list:[
					{id:"111",num:"150730567891633200",way:"自动扣款",bank:"广东发展银行",time:"2015-07-30 16:28:34",money:"443.70",state:"已还款",count:"1"},
					{id:"111",num:"150730567891633201",way:"自动扣款",bank:"广东发展银行",time:"2015-07-30 16:28:34",money:"443.70",state:"已还款",count:"1"},
					{id:"111",num:"150730567891633202",way:"自动扣款",bank:"广东发展银行",time:"2015-07-30 16:28:34",money:"443.70",state:"已还款",count:"1"},
					{id:"111",num:"150730567891633203",way:"自动扣款",bank:"广东发展银行",time:"2015-07-30 16:28:34",money:"443.70",state:"已还款",count:"1"},
					{id:"111",num:"150730567891633204",way:"自动扣款",bank:"广东发展银行",time:"2015-07-30 16:28:34",money:"443.70",state:"已还款",count:"1"},
					{id:"111",num:"150730567891633205",way:"自动扣款",bank:"广东发展银行",time:"2015-07-30 16:28:34",money:"443.70",state:"已还款",count:"1"},
					{id:"111",num:"150730567891633206",way:"自动扣款",bank:"广东发展银行",time:"2015-07-30 16:28:34",money:"443.70",state:"已还款",count:"1"},
					{id:"111",num:"150730567891633207",way:"自动扣款",bank:"广东发展银行",time:"2015-07-30 16:28:34",money:"443.70",state:"已还款",count:"1"},
					{id:"111",num:"150730567891633208",way:"自动扣款",bank:"广东发展银行",time:"2015-07-30 16:28:34",money:"443.70",state:"已还款",count:"1"},
					{id:"111",num:"150730567891633209",way:"自动扣款",bank:"广东发展银行",time:"2015-07-30 16:28:34",money:"443.70",state:"已还款",count:"1"}
				],
			totalNum: 55,
			totalPage: 6
		};//此处需用ajax获取每页数据
	//get_ajax(1,10);//第一页，一页10条
	
	var createpage = $("#ended_list_page").createPage({
        pageCount:data.totalPage,
        current:1,
        backFn:function(page){
            console.log(page);//页码
			//get_ajax(page,10);
			$("#user_ended_list tbody").html(get_list_html(data.list));
			$("#ended_list_page").find(".total-num").text(data.totalNum);
			$("#ended_list_page").find(".total-page").text(data.totalPage);
        }
    });
		function get_ajax(page,pageSize){
	$.ajax({ 
		url: "xxxxxxxxx", //请完善正确的ajax发送路径
		type:"post",
		async: false,
		success: function(result){
			data = result;
		}
	});
}
*/
	

	function get_ajax(page,pageSize){
		$.ajax({ 
			url :contextPath +"/instalmentManage/queryPaymentOrderAjax.jhtml",
			type:"post",
			async: false,
			data:{'start':page},
			success: function(result){
				if(result.code==-1){
					window.location.reload(true);
				}
				var dateList = result.list;
				$("#user_ended_list tbody").html(get_list_html(dateList));
    			$("#ended_list_page").find(".total-num").text(result.totalNum);
    			$("#ended_list_page").find(".total-page").text(result.totalPage);
			}
		});
	}
	function get_list_html(data){
		var html = '';
		if(data.length==0){
			html +='<tr>';
			html +='<td colspan="8">';
			html +='<div class="user-norecord">';
				html +='<div class="user-norecord-box"><i class="user-icon user-icon-norecord"></i></div>';
				html +='<div class="grey-color">暂无记录</div>';
			html +='</div>';
			html +='</td>';
			html +='</tr>';
		}else{
			$("#user_ended_list thead").show();
			$(data).each(function(index,record){
				var type =  record.paymentType == 0 ? '自动划扣' : '主动付款';
				html +='<tr>';
					html +='<td>'+record.userPaymentOrderId+'</td>';
					html +='<td>'+ type +'</td>';
					html +='<td>'+record.paymentChannelId+'</td>';
					html +='<td>'+record.bankName+'</td>';
					html +='<td>'+record.paymentTimeStr+'</td>';
					html +='<td>'+record.paymentAmount+'</span>元</span></td>';
					html +='<td>'+record.paymentStatusC+'</td>';
					html +='<td>'+record.paymentCount+'</span>期</span></td>';
					html +='<td><a href=' + contextPath+'/instalmentManage/queryPaymentOrderDetail.jhtml?userPaymentOrderId=' + record.userPaymentOrderId +"&rnd="+Math.random()+ '>详情</a></td>';
				html +='</tr>';
			});
		}
		return html;
	}

});

//立即付款弹出画面
function payNow(instalmentId){
	$("#mainShow").html('');
	$.ajax({
			url:contextPath+"/instalmentManage/payNow.jhtml",
			type:"POST",
			data:{"instalmentId":instalmentId},
			success:function(result){
				if(result.code==-1){
					window.location.reload(true);
				}
				$("#payNowBottom").show();
				if(result.payList==null||result.payList.length==0){
					var str="";
					str +='<div class="user-norecord">';
					str +='<div class="user-norecord-box"><i class="user-icon user-icon-norecord"></i></div>';
					str +='<div class="grey-color">暂无记录</div>';
					str +='</div>';
					$("#mainShow").html(str);
					$("#payNowBottom").hide();
					return;
				}else{
					var dataList=result.payList;
					var dtoInfo=result.dtoInfo;
					var strHtml="";
					strHtml+='<div class="user-loan-top clearfix" style="background:#fff;padding:0 20px 20px 20px;">'
						+'<div class="user-loan-top-l" style="width: 400px;">'
						+'<h3 class="user-loan-title"><a href="javascript:void(0)" target="_blank" id="productNameA">'+dtoInfo.productName+'<input type="hidden" value="'+dtoInfo.partnerOrderId+'" id="orderId"/>'+'<input type="hidden" value="'+dtoInfo.partnerName+'" id="partnerName"/>'+'</a></h3>'
						+'<p class="user-loan-time" >借款日期：<span id="orderDateTime">'+dtoInfo.loanDate+'</span> </p>'
						+'</div>'
						+'<div class="user-loan-top-r">'
						+'	<div class="user-loan-stage">分'+dtoInfo.periodMax+'期</div>'
						+'	<div class="user-loan-total">订单金额：<span class="money-color f18">'+reserve2Position(dtoInfo.loanPrincipalAmount)+'</span> 元</div>'
						+'</div>'
						+'</div>';
					strHtml+='<div class="dialog-loan-list"><input type="hidden" value="'+dtoInfo.instalmentId+'" name="currInstalmentId"/>'
							
							+'<table id="dialog-loan-list">'
							+'<tbody>';
					var strMain="";
					for (var i = 0; i < dataList.length; i++) {
						var _plan=dataList[i];
						//偶数行
						if(i%2==0){
							strMain+='<tr class="';
						}else{
							strMain+='<tr class="greybg';
						}
						//若逾期 加上class
						if(_plan.plansStatus==50){
							strMain+=" passed";
						}
						
						strMain+='" >'
						strMain+='<td width="10%"><input type="checkbox" class="ml20 " '+(_plan.plansStatus==30?' disabled  title="正在还款中，请稍后在试！"':'')+' name="loanItem" id="ck'+i+'" onclick="calAmount();"/>'
								+'<input type="hidden" value="'+_plan.repaymentPlansId+'" name="currId"/><input type="hidden" value="'+reserve2Position(_plan.receivableAmount)+'" name="currAmount"/>'
								+'<input type="hidden" value="'+_plan.instalmentNumber+'" name="currNum"/>'  
								+'</td>'
							+'<td width="20%">'+_plan.instalmentNumber+'期</td>'
							+'<td width="25%">'+formateDate(new Date(_plan.planRepaymentTime), "yyyy-MM-dd") +'</td>'
							+'<td width="20%">'+reserve2Position(_plan.receivableAmount)+'</td>';
						strMain+='<td width="25%">';
						//若逾期 加上逾期信息
						
							if(_plan.plansStatus==30){
								strMain+='<span style="color:#d84a4a;font-size:12px;font-family:tahoma,arial,'+"'Hiragino Sans GB'"+',\5b8b\4f53,sans-serif">还款中</span>';
							}else{
								if(_plan.plansStatus==20){
									strMain+='<span style="color:#d84a4a;font-size:12px;font-family:tahoma,arial,'+"'Hiragino Sans GB'"+',\5b8b\4f53,sans-serif">未还款</span>';
								}else if(_plan.plansStatus==50){
									strMain+='<i class="user-icon loan-haspassed-icon"></i><span class="f12">已逾期'+_plan.overDueDays+'天</span></td>';
								}
							}
						
						strMain+=	'</tr>';
					}
					
					strHtml+=strMain;
					strHtml+='</tbody>'
						+'</table>'
						+'</div>';
					$("#mainShow").html(strHtml);
					$("#ck0").click();
					$("#payNowSubBtn").unbind("click").click(function(){
						payNowSub(dtoInfo.instalmentId);
					}); 
				}
			},
			error:function(){
				return;
			}
	});
	
	$("#repay_dialog").show();
	$("#payAmountShow").html("0.00");
//	$("#payNowSubBtn").prop("disabled",true);
	$("#repay_all").prop("checked",false);
}

/**
* 计算显示金额
* 
**/
var paymentId=[];
var parymentAmounts=[];
function calAmount(){
	//table下寻找
	var curAmount=0.00;
	paymentId=[];
	parymentAmounts=[];
	var str="";
	var flag=false;
	$("#dialog-loan-list").find("input[name='loanItem']:checked").each(function(){
		var curId= $(this).parent().find("input[name='currId']").val();
		 var curAmountTemp= $(this).parent().find("input[name='currAmount']").val();
		 curAmount=accAdd(reserve2Position(curAmount), reserve2Position(curAmountTemp));
		 paymentId.push(curId);
		 parymentAmounts.push(curAmountTemp);
		 flag=true;
	});
	$("#payAmountShow").html(reserve2Position(curAmount));
//	if(flag){
//		$("#payNowSubBtn").prop("disabled",false);
//	}else{
//		$("#payNowSubBtn").prop("disabled",true);
//	}
}

	function payNowSub(instalmentId){
		var list =$("#dialog-loan-list").find("input[name='loanItem']:checked");
		var rows=[];
		if(list.length==0){
			$(".pay-now-area .user-hasloan-pop").css("visibility","visible");
			$(".pay-now-area .user-hasloan-popcon p").text("您还未勾选任何还款项哦");
			return;
		}
		for (var i = 0; i < list.length; i++) {
			var obj=list[i];
			var rowIndex=obj.parentNode.parentNode.rowIndex;
			rows.push(rowIndex);
		}
		var max=getMaxFromArray(rows);
		var min=getMinFromArray(rows);
		var _flag=$("#ck0").prop("checked");
		if((max-min+1)==rows.length&&_flag){
			$(".pay-now-area .user-hasloan-pop").css("visibility","hidden");
			$(".pay-now-area .user-hasloan-popcon p").text("您需要按还款时间顺序勾选哦");
		}else{
			$(".pay-now-area .user-hasloan-pop").css("visibility","visible");
			$(".pay-now-area .user-hasloan-popcon p").text("您需要按还款时间顺序勾选哦");
			return;
		}
		$.ajax({
			url:contextPath+"/instalmentManage/payNowSub.jhtml",
			async: false,
			type:"POST",
			data:{"totalAmount":$("#payAmountShow").html(),"payPlanIds":paymentId.join(","),"instalmentId":instalmentId,"parymentAmounts":parymentAmounts.join(",")},
			success:function(result){
				if(result.code==-1){
					window.location.reload(true);
				}
				if(result!=null&&result.code=="0xF1"){
					repayDialogHide();
				}else if(result.code!=0){
					$(".pay-now-area .user-hasloan-pop").css("visibility","visible");
					$(".pay-now-area .user-hasloan-popcon p").text(result.desc);
				}else{
					 $("#orderNameInfo").val($("#productNameA").html());
					 $("#paymentInfoToken").val(result.paymentInfoToken);
					 
					 $("#payNowForm").attr("action", result.redirectUrl);
					 $("#payNowForm").attr("target","_blank");
					 $("#payNowForm").submit();
				}
			},
			error:function(){
			}
		});
	}


	function repayDialogHide(){
		$("#repay_dialog").hide();
		goLocationHistory('instalmentManage');
	}
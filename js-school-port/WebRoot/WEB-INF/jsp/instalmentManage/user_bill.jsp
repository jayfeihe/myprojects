<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
<meta http-equiv="expires" content="0">
<link rel="icon" href="images/favicon.ico" type="image/x-icon">
<link rel="stylesheet" href="css/base.css">
<link rel="stylesheet" href="css/user.css">
<link rel="stylesheet" href="css/input.css">
<link rel="stylesheet" href="css/validator.css">
<title>秒趣分期-我的账单</title>
<script type="text/javascript">
	var contextPath = '${pageContext.request.contextPath}';
	var currentLiParam='${currentLi}';
</script>
</head>
<body>
	<!--header-->
	<jsp:include page="${basePath}/jsp/frame/header.jsp"></jsp:include>
	<!--/header-->

	<!--content-->
	<div class="content">
		<div class="content-top clearfix">
			<div class="content-left">
				<div class="user-pic">
					<c:if test="${userInfo.sex==1}">
				<img src="images/user-man.png"/> 
			</c:if>
			<c:if test="${userInfo.sex==0}">
				<img src="images/user-woman.png"/> 
			</c:if>
			<c:if test="${userInfo.sex==2}">
				<img src="images/user-default.png"/>
			</c:if>
				</div>
				<p class="user-name">${userInfo.name}</p>
			</div>
			<div class="content-right">
				<div class="user-date">
					<h3 class="user-date-title f16">近30日待付款</h3>
					<div class="user-pay-loan">
						<span class="one-pay-loan money-color">${topView.currentPayment }</span>
						<span class="all-pay-loan ml20">全部待付款：${topView.allLastPayment }</span>
					</div>
					<div class="user-finance">
						<div class="user-month clearfix">
							<span class="fl"> </span>
						<span class="fr"> </span>
						</div>
						<div class="user-day-line"></div>
					</div>
				</div>
			</div>

		</div>

		<div class="content-bottom clearfix">
			<div class="content-left">
				<ul class="user-nav">
					<li class="current"><a
						href="<%=basePath%>instalmentManage/instalmentBill.jhtml">分期账单</a></li>
					<li><a href="<%=basePath%>contractManage/userContract.jhtml">合同管理</a></li>
					<li><a href="<%=basePath%>updateUserInfo/viewInfo.jhtml">账户设置</a></li>
				</ul>
			</div>

			<div class="content-right">
				<div class="user-loan">
					<div class="user-loan-tabs clearfix">
						<ul id="user-loan-tabs">

							<li class="current">全部</li>
							<li  >待付款</li>
							<li  id="testLi">已还款</li>
						</ul>
					</div>
					<div id="user-loan-tabcontainer">

						<div class="user-loan-list clearfix " id="allOrwait"></div>

				<!--已付款列表start-->
				<div class="user-loan-list clearfix hide" style="border-left: 0;"
					id="payEndDiv">
					<!--已结清 start-->
					<table class="user-ended-list" id="user_ended_list">
						<thead class="hide">
							<tr>
								<td width="20%">还款单号</td>
								<td width="10%">还款方式</td>
								<td width="10%">还款渠道</td>
								<td width="15%">还款银行</td>
								<td width="20%">还款时间</td>
								<td width="10%">还款金额</td>
								<td width="10%">还款状态</td>
								<td width="9%">还款期数</td>
								<td width="6%">操作</td>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
					<!--已结清 end-->

					<!--分页代码 start -->
					<div class="tcdPageCode" id="ended_list_page"></div>
					<!--分页代码 end -->
				</div>
				<!--已付款列表end-->

			</div>

		</div>
	</div>
	</div>

	<!--至顶 start-->
	<div class="float-icon">
		<div class="go-top" id="go_top">
			<i class="user-icon user-icon-gotop"></i>
		</div>
	</div>
	<!--至顶 end-->
	</div>
	<!--/content-->

	<!--弹框 start-->
	<div id="repay_dialog" class="hide">
		<div class="pay-select-mask"></div>
		<div class="pay-select-bank">
			<div class="dialog-title">
				<span class="dialog-title-text">立即还款</span>
				<button type="button" class="dialog-close" id="dialog_close_r"></button>
			</div>
			<div id="mainShow"></div>
			
			<div class="dialog-loan-oper" id="payNowBottom">
			
			<form action="" method="post" id="payNowForm">
				<input type="hidden" id="orderNameInfo" name="orderNameInfo">
				<input type="hidden" id="paymentInfoToken" name="paymentInfoToken">
				
			</form>
				<label class="ml20 fl mt10"><input type="checkbox"
					name="allLoanItem" id="repay_all" /><span class="ml10">全部付清</span></label>
							<div class="pay-now-area fr  ml20">
				<div class="user-hasloan-pop">
					<i class="user-icon user-hasloan-popico"></i>
					<div class="user-hasloan-popcon">
						<p>您需要按还款时间顺序勾选哦</p>
					</div>
				</div>
				<button type="button" class="user-btn user-btn-red" id="payNowSubBtn">立即付款</button>
			</div>
				<span class="fr mt10">应付总额： <span id="payAmountShow">
						0.00</span> 元
				</span>
			</div>
		</div>
	</div>
	<!--弹框 end-->
	<!-- footer start-->
	<jsp:include page="${basePath}/jsp/frame/footer.jsp"></jsp:include>
	<!-- footer end-->

	<script src="js/jquery-1.10.2.min.js"></script>
	<script src="js/common.js"></script>
	<script type="text/javascript">
$(function(){
	   if('${topView.eveMapStr }'=="{}") {
		   waitMsg("","","",'${topView.nowDate}',"");
		   return;
	   }
	   try{
		   var jsonObj= eval("(" + '${topView.eveMapStr }' + ")");
		    for(var i in jsonObj){
				waitMsg(jsonObj[i].payMon,jsonObj[i].payDay,jsonObj[i].dayToPayment,'${topView.nowDate}',jsonObj[i].countToPay,jsonObj[i].betweenDays);
			}
	   }catch (e) {
	   }    
});
</script>
	<script src="${pageContext.request.contextPath}/js/instalmentManage/user_bill.js"></script>
	<script type="text/javascript">
	$(function(){
		//从还款详情列表返回时，不直接加载“全部”标签内容，加载完上面js后，模拟点击“已还款”
		if(currentLiParam!=null&&currentLiParam=='2'){
			$("#user-loan-tabs li").eq(2).click();
		}else if(currentLiParam!=null&&currentLiParam=='1'){
			$("#user-loan-tabs li").eq(1).click();
		}
	});
	function getPlanInfo(obj){
		var instalmentId=obj.parent().find("input[name='dataIdSend']").val();
		obj.parent().find(".showlist").load("<%=basePath%>jsp/frame/loading.html");
		$.ajax({
			url :"<%=basePath%>instalmentManage/queryPlanInfo.jhtml",
	        type: "POST",
	        timeout:5000,
	        data:{'instalmentId':instalmentId},
	        success:function(result){
	        	if(result.code==-1){
					window.location.reload(true);
				}
	        	if(result.list==null){
	        		obj.parent().find(".showlist").html("<p align='center'><span >出错了，请稍后重试！</span></p>");
				}else{
					var str="";
					var strBottom="";
					str+='<table>';
					for (var i = 0; i < result.list.length; i++) {
						 if(i%2==0){
							 str+='<tr class="waiting ';
						 }else{
							 str+='<tr class=" ';
						 }
						 if((i+1)>pageSize){
							 str+=" hide ";
						 }
						 if(result.list[i].receivableOverdue>0){
						 	str+='" style="color:#d84a4a;';
						 }
						 str+='">';
						 if(result.planCur!=null&&(i+1)==result.planCur.instalmentNumber){
							 str+='<td width="10%"><i class="user-icon user-table-icon"></i></td>';
						 } else{
							 str+='<td width="10%"></td>';
						 }
						 str+='<td width="20%">'+result.list[i].instalmentNumber+'期</td>';
						 var d = new Date();
						 d.setTime( result.list[i].planRepaymentTime);
						 var amount=0;
						 amount=accAdd(result.list[i].receivablePrincipal,result.list[i].receivableService);
						 amount=accAdd(amount,result.list[i].receivableOverdue);
						 amount=accSub(result.list[i].reduceOverdue,amount);
						 amount=reserve2Position(amount);
						 str+='<td width="20%">'+getDateFormat(d)+'</td>';
						 str+='<td width="20%"><div class="user-loan-num"><div class="user-hasloan-in">' 
						 	+amount
							 +'<div class="user-hasloan-pop"><em class="user-icon user-hasloan-popico"></em>'
							 +		'<div class="user-hasloan-popcon"> <p>本金:'+ reserve2Position(result.list[i].receivablePrincipal)+'</p> <p>服务费:'+reserve2Position(result.list[i].receivableService);
						/*罚息*/
						if(result.list[i].receivableOverdue>0){
							str+='</p> <p>罚息:'+reserve2Position(result.list[i].receivableOverdue);
						}	
						/*减免*/
						if(result.list[i].reduceOverdue>0){
							str+='</p> <p>减免:'+reserve2Position(result.list[i].reduceOverdue);
						}
						
						str+=  '</p> </div> </div> </div> </div></td>';
						
					 	if(result.list[i].plansStatus==10||result.list[i].plansStatus==20){
					 		str+='<td width="10%">待付款</td>';
					 	}else if(result.list[i].plansStatus==30){
					 		str+='<td width="10%">还款中</td>';
					 	}else if(result.list[i].plansStatus==40){
					 		str+='<td width="10%">已还款</td>';
					 	}else if(result.list[i].plansStatus==50){
					 		str+='<td width="10%">已逾期</td>';
					 	}else if(result.list[i].plansStatus==60){
					 		str+='<td width="10%">已取消</td>';
					 	}else if(result.list[i].plansStatus==70){
					 		str+='<td width="10%">已中止</td>';
					 	}else if(result.list[i].plansStatus==80){
					 		str+='<td width="10%">待退款</td>';
					 	}else {
					 		
					 	}
						 str+='<td width="20%"></td>';
						 str+='</tr>';
					}
					str+='</table>';
					str+='<div class="item-loan-oper clearfix">'
							+'<div class="item-loan-page">'
							+'</div> <div class="item-loan-total">'
					      +'剩余待还  '+result.bottomInfo.latPeriodNum+'  期 ，剩余待付款总额：'+reserve2Position(result.bottomInfo.lastAmount)+'元   <a href="javascript:void(0)" class="hide">全部付清</a>'
						  +	' </div></div>';
						 obj.parent().find(".showlist").html(str);
						  
						  
				}
	        },
	        error:function(){
	        	obj.parent().find(".showlist").html("<p align='center'><span >出错了，请稍后重试！</span></p>");
	        }
		});
	}
	
	function getDateFormat(myDate) {
		var y = myDate.getFullYear(); //获取完整的年份(4位,1970-????)
		var mo = myDate.getMonth() + 1; //获取当前月份(0-11,0代表1月)
		var d = myDate.getDate(); //获取当前日(1-31)
		if (mo < 10) {
			mo = "0" + "" + mo;
		}
		if (d < 10) {
			d = "0" + "" + d;
		}
		 var timeParam = y + "-" + mo + "-" + d;
		return timeParam;
	}
	</script>
	<script type="text/javascript">
	function loadMain(urlParam){
		$("#allOrwait").load("<%=basePath%>jsp/frame/loading.html");
		$.ajax({
			url:urlParam,
			type:"POST",
			success:function(result){
				if(result.code==-1){
					window.location.reload(true);
				}
				var str="";
				if(result.code!=0){
					str +='<div class="user-norecord">';
					str +='<div class="user-norecord-box"><i class="user-icon user-icon-norecord"></i></div>';
					str +='<div class="grey-color">暂无记录</div>';
					str +='</div>';
					$(".user-loan-list").css("border-left","none");
					$("#allOrwait").html(str);
					return;
				}
				var instalmentlist=result.instalmentlist;
				
				if(instalmentlist==null||instalmentlist.length<1){
					str +='<div class="user-norecord">';
					str +='<div class="user-norecord-box"><i class="user-icon user-icon-norecord"></i></div>';
					str +='<div class="grey-color">暂无记录</div>';
					str +='</div>';
					$(".user-loan-list").css("border-left","none");
					$("#allOrwait").html(str);
					return;
				}
				 for (var i = 0; i < instalmentlist.length; i++) {
					//$(".user-loan-list").css("border-left","2px solid #ced4e0");
						var instalmentInfo=instalmentlist[i];
						//剩余xx天 
						if (instalmentInfo.planState == 20) {
							str += '<div class="user-loan-item">'
									+ '<div class="item-first-box">'
									+ '<div class="user-icon user-loan-icon blue">'
									+ '	<div class="user-icon-text">剩'
									+ instalmentInfo.lastDay
									+ '天</div>'
									+ '</div>'
									+ '	<i class="user-icon user-cal-icon"></i>';
						} else if (instalmentInfo.planState == 40) { // 已结清 start 
							str += ' <div class="user-loan-item">'
									+ '<div class="item-first-box">'
									+ '<div class="user-icon user-loan-icon grey">'
									+ '	<div class="user-icon-text">已结清</div>'
									+ '	</div>'
									+ '<i class="user-icon user-cal-icon"></i>';
						}else if (instalmentInfo.planState == 50) { // 已取消 start 
							str += ' <div class="user-loan-item">'
								+ '<div class="item-first-box">'
								+ '<div class="user-icon user-loan-icon grey">'
								+ '	<div class="user-icon-text">已取消</div>'
								+ '	</div>'
								+ '<i class="user-icon user-cal-icon"></i>';
						}else if (instalmentInfo.planState == 60) { // 已中止 start 
							str += ' <div class="user-loan-item">'
								+ '<div class="item-first-box">'
								+ '<div class="user-icon user-loan-icon grey">'
								+ '	<div class="user-icon-text">已中止</div>'
								+ '	</div>'
								+ '<i class="user-icon user-cal-icon"></i>';
						}else if (instalmentInfo.planState == 70) { // 待退款 start 
							str += ' <div class="user-loan-item">'
								+ '<div class="item-first-box">'
								+ '<div class="user-icon user-loan-icon red">'
								+ '	<div class="user-icon-text">待退款</div>'
								+ '	</div>'
								+ '<i class="user-icon user-cal-icon"></i>';
						}  else if (instalmentInfo.planState == 30) { //已逾期 start
							str += '<div class="user-loan-item user-strong-item">'
									+ '<div class="item-first-box">'
									+ '<div class="user-icon user-loan-icon red">'
									+ '	<div class="user-icon-text">已逾期</div>'
									+ '</div>'
									+ '<i class="user-icon user-cal-icon"></i>';
						}
						
							var aurl="'"+result.partnerOrderListUrl+"'";
						str+='<div class="user-loan-top clearfix">'
							+'	<div class="user-loan-top-l">'
							+'		<h3 class="user-loan-title"><a href="javascript:void(0)" onclick="window.open('+aurl+');" >'+instalmentInfo.productName+'</a></h3>'
							+'		<p class="user-loan-time">借款日期：'+instalmentInfo.loanDate +' </p>'
							+'	</div>	 	'
							+'	<div class="user-loan-top-r">'
							+'		<div class="user-loan-stage">分'+instalmentInfo.periodMax+'期</div>'
							+'		<div class="user-loan-total">消费金额：<span class="money-color f18">'+reserve2Position(instalmentInfo.loanPrincipalAmount)+'</span> 元</div>'
							+'	</div>'
							+'</div>'
							+'<div class="user-item-bot">'
							+'	<p>本期应付款：<span class="money-color f18">'+reserve2Position(instalmentInfo.repaymentAmount)+'</span> <span class="user-strong-color">元  <span class="f12">('+instalmentInfo.instalmentNum+'/'+instalmentInfo.periodMax+'期)</span></span></p>'
							+'	<p>应付款明细：<span class="user-strong-color">'+reserve2Position(instalmentInfo.repaymentPrincipal)+'+'+reserve2Position(instalmentInfo.repaymentService) +'（分期手续费）';
							
						if(instalmentInfo.planState==30){
							str+='+'+reserve2Position(instalmentInfo.allOverDueAmount) +'（逾期违约金）';
						}
						if(instalmentInfo.repaymentReduce>0){
							str+='-'+reserve2Position(instalmentInfo.repaymentReduce) +'（罚息减免）';
						}
						str+='	</span></p>';	
						//账单未还清时显示(排除初始化状态)
						if(instalmentInfo.instalmentIdStatus!=10&&(instalmentInfo.planState==20 ||instalmentInfo.planState==30)){
							str+='	<button class="user-btn user-btn-default now_repay" onclick="payNow( ';
							str+="'"+instalmentInfo.instalmentId+"'";
							 str+=')">立即付款</button>';
						}    
						//账单未还清时显示
						if(instalmentInfo.planState==20){		
							str+='	<p>到期付款日：<span class="user-strong-color">'+instalmentInfo.repayDay
						 }	
						if(instalmentInfo.planState==30){
							str+=' <p><span class="money-color">本单已违约，将持续产生违约金，请尽快付款!!!</span>';
						}
						
						
						str+='</span></p>'		;	
						 str+='</div>'
								+'</div>'
								+'<div class="item-second-box">'
								+'<i class="user-icon user-btn-open" >'
								+'<input type="hidden" value="'+instalmentInfo.instalmentId+'" name="dataIdSend" />'
								+'</i>'
								+'<div class="item-loan-list showlist"  >'
								+'</div>'
								+'</div>'
								+'<div class="item-third-box"></div>'
								+'</div>';
				
				}
				$("#allOrwait").html(str);
				//每条记录的展开按钮
				$(".item-second-box").on("click",".user-btn-open",function(){
				
					var $this = $(this);
					$this.prop("disabled",true);
					getPlanInfo($this);
					$this.parents(".item-second-box").find(".item-loan-list").stop(true,true).slideDown(300);
					$this.removeClass("user-btn-open").addClass("user-btn-close");
					$this.parents(".user-loan-item").find(".item-third-box").hide();
					setTimeout(function(){page($this.parents(".user-loan-item").find("div .item-loan-page"),$this.parents(".user-loan-item").find("tbody"));$this.prop("disabled",false)},500);
//					page($this.parents(".user-loan-item").find("div .item-loan-page"),$this.parents(".user-loan-item").find("tbody"));
					
				});
				//每条记录的展收缩按钮
				$(".item-second-box").on("click",".user-btn-close",function(){
					var $this = $(this);
					$this.prop("disabled",true);
					$this.parents(".item-second-box").find(".item-loan-list").stop(true,true).slideUp(300);
					$this.removeClass("user-btn-close").addClass("user-btn-open").prop("disabled",false);
					$this.parents(".user-loan-item").find(".item-third-box").show();
				});
				//简单分页
				function page($pageContainer,$listContainer){
					$listContainer.find("tr").hide();
					
					var totalNum = $listContainer.find("tr").length;//总条数
					//var pageTotal = (totalNum%pageSize==0)?(totalNum/pageSize):(totalNum/pageSize+1);//总页码数
					var pageTotal = parseInt((totalNum+pageSize-1)/pageSize);
					
					for(var i = 0;i<pageSize;i++){
						$listContainer.find("tr").eq(i).show();
					}
					if(pageTotal>0){	
						var pageHtml = '';
						pageHtml += '<button class="user-page-btn user-prev-page" disabled type="button" title="上一页"><i class="user-icon prev-page-icon"></i></button>';
						pageHtml += '<span class="user-page-num"><span class="user-page-curr">1</span>/<span class="user-page-total">'+pageTotal+'</span></span>';
						pageHtml += '<button class="user-page-btn user-next-page" type="button" title="下一页"><i class="user-icon next-page-icon"></i></button>';
						$pageContainer.html(pageHtml);
						if(pageTotal==1){
							$pageContainer.find(".user-next-page").prop("disabled",true);
						}
						
						$pageContainer.on("click",".user-prev-page",function(){
							var pageCurr = parseInt($pageContainer.find(".user-page-curr").text());
							if(pageCurr>1){
								$pageContainer.find(".user-page-curr").text(pageCurr-1);
								$pageContainer.find(".user-next-page").prop("disabled",false);
								if(pageCurr==2){
									$pageContainer.find(".user-prev-page").prop("disabled",true);
								}
								pageCurr = parseInt($pageContainer.find(".user-page-curr").text());
								$listContainer.find("tr").hide();
								var start = (pageCurr - 1) * pageSize+1;
								var end = pageCurr * pageSize;
								$listContainer.find("tr").hide();
								for(var i = start;i<=end;i++){
									$listContainer.find("tr").eq(i-1).show();
								}
							}
							else{
								$pageContainer.find(".user-next-page").prop("disabled",false);
							}
							
						});
						
						$pageContainer.on("click",".user-next-page",function(){
							var pageCurr = parseInt($pageContainer.find(".user-page-curr").text());
							if(pageCurr<pageTotal){
								$pageContainer.find(".user-page-curr").text(pageCurr+1);
								$pageContainer.find(".user-prev-page").prop("disabled",false);
								if(pageCurr==pageTotal-1){
									$pageContainer.find(".user-next-page").prop("disabled",true);
								}
								pageCurr = parseInt($pageContainer.find(".user-page-curr").text());
								var start = (pageCurr - 1) * pageSize+1;
								var end = pageCurr * pageSize;
								$listContainer.find("tr").hide();
								for(var i = start;i<= end;i++){
									$listContainer.find("tr").eq(i-1).show();
								}
							}
							
						});
					}
				}
			}
		});
	}
</script>
</body>
</html>

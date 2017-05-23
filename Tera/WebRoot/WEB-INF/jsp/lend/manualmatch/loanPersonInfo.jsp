<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />
		</style>
	</head>
	<body>
		
		
		<c:if test="${! empty pm}">
		<div id="main">
			<div id="part1" class="part">
				
				<div class="content">
					
					<form id="selectedLoanPerson" action="lend/manualmatch/selectedLoanPersonList.do" method="post" target="selectedLoanPersonContent">
						
						<table id="table" class="datatable" summary="list of members in EE Studay">
							<tr>
								<th scope="col">选择</th>
								<th scope="col">序号</th>
								<th scope="col">借款申请号</th>
								<th scope="col">客户姓名</th>
								<th scope="col">申请时间</th>
								<th scope="col">用途</th>
								<th scope="col">产品</th>
								<th scope="col">金额</th>
								<th scope="col">服务费率</th>
								<th scope="col">利息率</th>
								<th scope="col">期限</th>
								<th scope="col" width="160">详情</th>
							</tr>
							<c:forEach items="${ pm.data}" var="data" varStatus="status">
								<tr>
									<td style="text-align: center;"><input name="chk_loanIds" type="checkbox" id="selectInfo_${data.id}" value="${data.id}"/></td>
									<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
									<td>${data.loanAppId}</td>
									<td>${data.name}</td>
									<td>
										<fmt:formatDate value="${data.appTimeStr}" pattern="yyyy-MM-dd HH:mm:ss"/>
									</td>
									<td>${data.useage}</td>
									<td>${data.loanProduct}</td>
									<!--<td>${data.loanAmount}</td>-->
									<td>
<%--										<fmt:parseNumber value="${data.loanAmount}" var="fmtAmount"/>--%>
<%--										${fmtAmount}--%>
										<fmt:formatNumber value="${data.loanAmount}" type="currency"/>
									</td>
									<td>${data.loanServiceRate}</td>
									<td>${data.loanInterestRate} </td>
									
									<td>${data.loanPeriod}个月</td>
									<td>
									<a href="javascript:void(0);" onclick="javascript:showDetail('${data.loan_id}');">详情</a>&nbsp;
<!--										<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#w').window('open')">详情</a>-->
									</td>
								</tr>
							</c:forEach>
						</table>
				
						<div id="pageStyle">
							${ pm.pageNavigation}
						</div>
					</from>
					
				
				</c:if>
				
				<div id="control" class="control">
				
						
				</div>
				
				
	</body>

<script type="text/javascript">

//给每个复选框定义点击函数
jQuery(function() {  
     checkboxOnClick();  
}); 

//给每个复选框的单击事件所定义的函数
function checkboxOnClick() {  
            $("input[type=checkbox]").click(function() {  
                    var checkbox_value = $(this).attr('value');  
                    if ($("#selectInfo_" + checkbox_value).is(":checked")) {                    	
                    	for(var i=0;i<arr_selectedLoan2MatchIds.length;i++){
                    		if(checkbox_value == arr_selectedLoan2MatchIds[i]){
	                    		
								$.messager.alert('消息', "该申请已经添加。");
								return;
                    			//alert("该申请已经添加");
                    			//$(this).attr('checked',false)
                    			//return;
                    		}
                    	}
                        //数组增加所点击的复选框的value值
                       	arr_selectedLoan2MatchIds.push(checkbox_value); 
                        selectedLoanPerson('selectedLoanPerson');
 
                    } else {  
                    	
                    }  
            });  
        }  

//这个方法用于选择复选框
function selectedLoanPerson(fromId) {
	
	//定义数组中各个元素组成的字符串
	var mm = "";
	for(var i=0;i < arr_selectedLoan2MatchIds.length;i++){
		mm += arr_selectedLoan2MatchIds[i];
		if(i != arr_selectedLoan2MatchIds.length-1){
			mm +=",";
		}
	}
	
	//取得所提交的form的action
	var formAction = $('#' + fromId).attr("action");	
	var targetDiv = $('#' + fromId).attr("target");
	var params = $('#' + fromId).serialize();
	openMask();
 $.ajax({
		type : "POST",
		url : formAction + "?loan2matchIdsStr=" + mm,
		data : encodeURI(params + "&targetDiv=" + targetDiv),
		success : function(data) {
			
			if("true" == data.success){
				if("0" == data.message){
					var btn_submit = document.getElementById("btn_submit");
					var btn_back = document.getElementById("btn_back");
					
					btn_submit.style.display = "none";
					btn_back.style.display = "none";
				}
				
			}
			$('#' + targetDiv).html(data);
			closeMask();
		},
		error : function() {
			closeMask();
			
			$.messager.confirm('消息', '加载失败。', function(ok){
	            if (ok){
//	 				window.history.go(-1);
	            }
	    	});
		}
	}); 
}


//点击”提交按钮“
function matchSelectedLoanPersonAndLend(fromId) {
	
	//取得调整后的金额
	var tz_loanAmounts = document.getElementsByName("tz_loanAmount");
	
	for(var s=0;s<tz_loanAmounts.length;s++){
		//如果调整后的金额中有非数字类型则给出提示
		if(isNaN(tz_loanAmounts[s].value)){
			
			$.messager.confirm('消息', "调整后的金额中有非数字类型，请改正！");
			return;
		}
		//如果调整后的金额中有空格则给出提示
		if($.trim(tz_loanAmounts[s].value) == "" || $.trim(tz_loanAmounts[s].value) == null){
			
			$.messager.confirm('消息', "调整后的金额不能为空，请改正！");
			return;
		}
	}


	//记住出借人的Id,用在执行lend2match与loan2match匹配的时候
	var lend2matchId = document.getElementById("id").value;
	
	//取得所提交的form的action
	var formAction = $('#' + fromId).attr("action");
	
	var mylend2matchId = document.getElementById("mylend2matchId");
	//将提交的对应form的mylend2matchId隐含域的值设置为lend2match的id
	mylend2matchId.value=lend2matchId;
	
	var targetDiv = $('#' + fromId).attr("target");
	var params = $('#' + fromId).serialize();
	openMask();
 $.ajax({
		type : "POST",
		url : formAction,
		data : encodeURI(params + "&targetDiv=" + targetDiv),
		success : function(data) {
			closeMask();
			if ("true"==data.success) {
				//alert(data.message);
				//if("提交成功！" == data.message){
					$.messager.confirm('消息', data.message, function(ok){
			            if (ok){
			 				window.history.go(-1);
			 				return;
			            }
		    		});
					//window.history.go(-1);
					//return;
				//}
			}
		},
		error : function() {
			closeMask();
			
			$.messager.confirm('消息', '提交失败。', function(ok){
	            if (ok){
//	 				window.history.go(-1);
	            }
	    	});
		}
	}); 
}

//这个方法用于显示借款人详细信息
function showDetail(loan_id) {

	window.open("<%=basePath%>lend/manualmatch/loanPersonSpecifyInfo.do?id=" + loan_id,'_blank');  
	return;
}

$(document).ready(function() {

});
</script>
</html>
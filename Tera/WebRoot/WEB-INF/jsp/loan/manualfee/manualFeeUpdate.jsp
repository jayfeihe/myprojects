<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>"/>
<title>财富端申请表查询</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
	
<style type="text/css">

</style>
</head>
<body>
		<div id="main">
		<form name="updateForm" id="updateForm" method="post" action="loan/manualverify/passManualMatchVerify.do">
		<input type="hidden" id="loan2matchId" name="manualVerifyloan2matchId" value="${loan2matchId}"/>
			<div id="part1" class="part">
				<div class="content">
					
						<input id="id" name="id" type="hidden" size="35"
							value="${id}" />
							
						<input name="subbpm" id="subbpm" type="hidden" value="" />
						<input name="auditResult" id="auditResult" type="hidden" value="" />
						<input name="loanApprovalid" id="loanApprovalid" type="hidden" value="${loanApproval.appId}" />
						 <input id="appId" name="appId" type="hidden" value="${loanApproval.appId}" />
						<p class="title">
							人工撮合收费
						</p>
						<%-- 重要！--%>
						<div class="easyui-tabs" data-options="tools:'#tab-tools'">
							<jsp:include page="/loan/app/read.do?id=${id}" ></jsp:include>
						</div>
							
						
								 <strong>审批通过信息</strong>
  							 <hr />	
  							 <table>
  							 	<tr>
  							 		<td>审批金额</td>
  							 		<td>
  							 		<fmt:parseNumber value="${loanApproval.approvalAmount}" var="fmtAmount"/>
  							 		<input id="loanAmount" name="loanAmount" type="text" data-options="required:true,validType:['length[0,100]']" class="textbox easyui-validatebox" value="${fmtAmount}" disabled="disabled"/>
  							 		</td>
  							 		<td>期限</td>
  							 		<td><input id="loanPeriod" name="loanPeriod" type="text" data-options="required:true,validType:['length[0,100]']" class="textbox easyui-validatebox" value="${loanApproval.approvalPeriod}" disabled="disabled"/></td>
  							 		<td>费率</td>
  							 		<td><input id="loanServiceRate" name="loanServiceRate" type="text" data-options="required:true,validType:['length[0,100]']" class="textbox easyui-validatebox" value="${loanApproval.approvalServiceRate}" disabled="disabled"/></td>
  							 		<input type="radio" name="radio" id="radio" value="${loanApproval.thirdPartyContract}" <c:if test="${loanApproval.thirdPartyContract == '1'}"> checked</c:if> disabled="disabled" />需要第三方购销合同
  				
  							 	</tr>
  							 	<tr>
  							 	<td>担保条件</td>
  							 	<td><input id="loanAmount" name="loanAmount" type="text" data-options="required:true,validType:['length[0,100]']" class="textbox easyui-validatebox" value="${loanApproval.guaranteeCondition}" disabled="disabled"/></td>
  							 	</tr>
  							 	
  							 	<tr>
  							 		<td colspan="8"> <strong>出借人清单</strong><hr /></td></tr>
  							 	<tr>
  							 		<td colspan="8">
	  							 		<table id="table" class="datatable" summary="list of members in EE Studay">
											<tr>
												<th scope="col">序号</th>
												<th scope="col">申请号</th>
												<th scope="col">地区</th>
												<th scope="col">申请时间</th>
												<th scope="col">金额</th>
												<th scope="col">产品</th>
												<th scope="col">期限</th>
												<th scope="col">撮合类型</th>
												<th scope="col">撮合时间</th>
											</tr>
											<c:forEach items="${pm.data}" var="data" varStatus="status">
											<tr>
													<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
													<td>${data.lendAppId}</td>
													<td>${data.orgId}</td>
													<td>
													${data.createTimeStr}
													
													</td>
													<td>
<%--													<fmt:formatNumber type="number" value="${data.lendAmount } " maxFractionDigits="2"/>--%>
													<fmt:formatNumber value="${data.lendAmount}" type="currency"/>
													</td>
													<td>${data.lendProduct}</td>
													<td>${data.lendPeriod}</td>
													<td>
														<c:choose>
															<c:when test="${data.matchType=='0'}">自动撮合</c:when>
															<c:when test="${data.matchType=='1'}">人工撮合</c:when>
														</c:choose>
													</td>
													<td>
													${data.matchTimeStr}
													</td>
											
											</tr>
											</c:forEach>
										</table>
								</td>
							</tr>
						</table>
			</div>
			</table>
						<br/>
				<input type="button" value="提交" clss="btn" onclick="javascript:servicefee()"/>
				<label>收取服务费金额：</label>
				<input type="text" id="manualfee" name="manualfee"/>
					
			</div>
			</form>
		</div>
		
		<form id="fileSmt" action="file/upload/zipupload.do"  enctype="multipart/form-data" style="display: none;" >
			<input type="hidden" id="fileappId" name="appId" <c:if test="${bean!=null}">value="${bean.appId}"</c:if>/>
		</form>
		<!-- <div id="loading" class="easyui-window" title=""
			data-options="border:false,modal:true,closed:true,draggable:false,resizable:false">
			<img src="img/loading.gif" alt="加载中..." />
		</div> -->
	</body>

	
<script type="text/javascript">

function submitForm(fromId) {
	var formAction = $('#' + fromId).attr("action");
	var targetDiv = $('#' + fromId).attr("target");
	var params = $('#' + fromId).serialize();
	openMask();
	
 $.ajax({
		type : "POST",
		url : formAction,
		data : encodeURI(params + "&targetDiv=" + targetDiv),
		success : function(data) {
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

//审批通过执行的方法
function passManualMatchVerify(fromId) {

	var loan2matchId_matchresult = document.getElementById("loan2matchId").value;
	var formAction = $('#' + fromId).attr("action");
	var params = $('#' + fromId).serialize();
	openMask();
	
 $.ajax({
		type : "POST",
		url : formAction + "?loan2matchId_matchresult=" + loan2matchId_matchresult,
		//data : encodeURI(params + "&targetDiv=" + targetDiv),
		success : function(data) {
			
			if ("true" == data.success) {
				//关闭遮罩，弹出消息框
				closeMask();
				$.messager.confirm('消息', data.message, function(ok) {
					if (ok) {
						var url = "<%=basePath%>" + "loan/manualverify/loan2matchList.do";
						window.location = url;
						window.history.go(-1);
					}
			});
			} else {
				closeMask();
				$.messager.confirm('消息', data.message);
				//按钮生效
				$(".btn").removeAttr("disabled");
			}
	
		},
		error : function() {
			closeMask();
			
			$.messager.confirm('消息', '加载失败。', function(ok){
	            if (ok){
	 				window.history.go(-1);
	            }
	    	});
		}
	}); 
}

//审批通过执行的方法
function servicefee() {
	var loan2matchId_matchresult = document.getElementById("loan2matchId").value;
	var servicefee = document.getElementById("manualfee").value;
	//判断是否为空
	if(servicefee == "" || servicefee == null){
		$.messager.confirm('消息', "请填写收取费用金额", function(ok) {
			if (ok) {
				servicefee.value="";
				servicefee.focus();
			}
		});
		return;
	}
	//判断是否为数字
	if(isNaN(servicefee)){
		$.messager.confirm('消息', "金额应为数字", function(ok) {
			if (ok) {
				servicefee.value="";
				servicefee.focus();
			}
		});
		return;
	}
	
	var formAction = "loan/manualfee/manualfee.do";
	openMask();
	
 $.ajax({
		type : "POST",
		url : formAction + "?loan2matchId_matchresult=" + loan2matchId_matchresult.trim() + "&&servicefee=" + servicefee.trim(),
		success : function(data) {
			
			if ("true" == data.success) {
				//关闭遮罩，弹出消息框
				closeMask();
				$.messager.confirm('消息', data.message, function(ok) {
					if (ok) {
						var url = "<%=basePath%>" + "loan/manualverify/loan2matchList.do";
						window.location = url;
						window.history.go(-1);
					}
			});
			} else {
				closeMask();
				$.messager.confirm('消息', data.message);
				//按钮生效
				$(".btn").removeAttr("disabled");
			}
	
		},
		error : function() {
			closeMask();
			
			$.messager.confirm('消息', '加载失败。', function(ok){
	            if (ok){
	 				window.history.go(-1);
	            }
	    	});
		}
	}); 
}

/* function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
} */

$(document).ready(function(){
	submitForm("queryForm");
	var tsurl="<%=basePath%>sys/org/allOrgList.do?loginid=all&timestamp="+(new Date()).getTime();
	$("#orgId2").combobox("clear");
	$("#orgId2").combobox({url:tsurl,valueField:'orgId',textField:'orgName'});
});

</script>
</html>


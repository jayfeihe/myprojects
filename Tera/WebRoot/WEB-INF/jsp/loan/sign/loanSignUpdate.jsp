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
		<title>借款申请审批表更新</title>
		<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
		<link href="css/global.css" type="text/css" rel="stylesheet" />
		<link href="css/icon.css" type="text/css" rel="stylesheet" />
		<link href="css/default/easyui.css" type="text/css" rel="stylesheet" />
		<script src="js/jquery.min.js" type="text/javascript"></script>
		<script src="js/jquery.form.js" type="text/javascript"></script>
		<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
		<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
		
		<style type="text/css">
</style>
	</head>
	<body>
		<div id="main">
		<form id="updateForm">
			<div id="part1" class="part">
				<div class="content">
					
						<input id="id" name="id" type="hidden" size="35" value="${id}" />
							
						<input name="subbpm" id="subbpm" type="hidden" value="" />
						<input name="auditResult" id="auditResult" type="hidden" value="" />
						<input name="loanApprovalid" id="loanApprovalid" type="hidden" value="${loanApproval.appId}" />
						 <input id="appId" name="appId" type="hidden" value="${loanApproval.appId}" />
						<p class="title">
							<a href="javascript:void(0);">个人申请签约</a>
						</p>
						<%-- 重要！--%>
						<div class="easyui-tabs" >
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
  							 	<td><input id="guaranteeCondition" name="guaranteeCondition" type="text" data-options="required:true,validType:['length[0,100]']" class="textbox easyui-validatebox" value="${loanApproval.guaranteeCondition}" disabled="disabled"/></td>
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
							<tr>
								<td colspan="8">
									<table width="100%">
										<tr>
											<td>
												<div id="toolbar" style="margin-left: 10px;margin-top: 10px; font-size: 14px;" >
													<strong>影像资料</strong>
													<hr width="100%" size=2 color=#E0ECFF align=center noshade>
												</div>
											</td>
										</tr>
										<tr>
											<td>
												必备资料： a b c									
											</td>
										</tr>
										<tr>
											<td>
												<span style="float:left: ;" id="yingxiangUP">
												上传附件：&nbsp; &nbsp;
													<input onchange="fileForm();" id="file" name="file" type="file" accept="application/x-zip-compressed" />
												</span>
											</td>
										</tr>
									</table>
									<table width="100%">
										<tr>
											<td id="imgDiv">
											<jsp:include page="/img/imgSlidePath.do?appId=${loanApproval.appId}" />
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					
						<input id="contractno" name="contractno" type="hidden" size="35" value="${contractno}" />	
						<input type="button" value="生成线上合同" class="btn" onclick="saveContract(${id});" />
						签约时间：<input id="signTime" type="text" class="easyui-datebox" required="required" name="signTime">
						<br/>
			
						<table>
						<c:forEach items="${pm2.data}" var="data" varStatus="status">
							<tr>
								<td>
								担保物${ status.index+pm2.rowS+1}：<input type="button" value="生成担保物合同" class="btn"
									onclick="createCollateralContract(${data.id});" />
									<%-- <input id="diyaId" name="diyaId" type="hidden" size="35" value="${data.id}" /> --%></td>
							</tr>
						</c:forEach>
						</table>
						<br/>
						
						<input type="button" value="提交" class="btn" onclick="bmmupdateFunction(1)" />
						<input type="button" value="返回" class="btn" onclick="javascript:back()" />
						<input type="button" value="放弃" class="btn" onclick="bmmupdateFunction(4)" />
					</div>
				</div>
			</form>
		<form id="fileSmt" action="file/upload/zipupload.do"  enctype="multipart/form-data" style="display: none;" >
			<input type="hidden" id="fileappId" name="appId" <c:if test="${bean!=null}">value="${bean.appId}"</c:if>/>
		</form>
		<!-- <div id="loading" class="easyui-window" title=""
			data-options="border:false,modal:true,closed:true,draggable:false,resizable:false">
			<img src="img/loading.gif" alt="加载中..." />
		</div> -->
	</div>
	</body>
<script type="text/javascript">
//文件上传
function fileForm(){
	var appid=$('#appId').val();
	if(appid!=null&&appid!=''){
		var thisfile=$('#yingxiangUP').find("#file");
		var fileValue=$('#file').val();
		if((fileValue.substring(fileValue.lastIndexOf("."))).toUpperCase()!=".ZIP"){
			
			$.messager.confirm('消息', "文件类型必须为 ZIP格式");
			return;
		}
		
		$.messager.confirm('消息', "是否确定上传？", function(ok){
            if (ok){
            	var $file=$("#fileSmt input[name='file']");
        		if($file!=null){
        			$file.remove();
        		}
        		var fcolne=thisfile.clone();
        		$('#fileSmt').append(thisfile);
        		$('#yingxiangUP').append(fcolne);
        		$('#fileSmt').submit();
            }
    	});
	}else{
		
		$.messager.confirm('消息', "请先保存，然后才能上传附件。");
	}
}
$(document).ready(function(){
	$('#fileSmt').submit(function() {
		openMask();
	    $(this).ajaxSubmit({
	    		type : "POST",
	    		contentType:"multipart/form-data",
	    		url : "file/upload/zipupload.do",
	    		beforeSubmit:function(type) {
	//     			alert(type);
	    		},
	    	    success:function(data) {;
	    	    	data=jQuery.parseJSON(data);
	    	    	if ("true"==data.success) {
	    				
	    				$.messager.alert('消息', data.message,"info", function(){
	    						imgpartLoad($('#appId').val());
	    	        	});
	                } else {
	    				
	    				$.messager.alert('消息', data.message);
	                }
	    	    	closeMask();
	    		}
	    });
	    return false;
	});
});
</script>

<script type="text/javascript">
function saveContract(data_id){
// 	//去掉 input 输入的 前后空格
// 	$('#updateForm').find('input').each(function(){
// 		$(this).val($.trim($(this).val()));
// 	});
		//验证表单验证是否通过
	if(false == $('#updateForm').form('validate') ){
		
		$.messager.confirm('消息', "请先输入签约时间！");
		return;
	}
	openMask();
	var params = $('#updateForm').serialize();
	$.ajax({
		type : "POST",
		url : "loan/sign/saveContract.do?id="+data_id,
		data : encodeURI(params),
		success : function(data) {
			closeMask();
			if ("true"==data.success) {
				$('#contractno').val(data.message);
				
				$.messager.confirm('消息', "生存成功是是否立即下载", function(ok){
	                if (ok){
	                	var url="loan/sign/downloadContract.do";
	        			var htfrom=$("<form></form>");
	        			htfrom.attr("target","_blank");
	        			htfrom.attr("action",url);
	        			htfrom.css('display', 'none');
	        			htfrom.append("<input name='id' value='"+data_id+"' />");
	        			htfrom.appendTo('body');
	        			htfrom.submit();
	        			htfrom.remove();
	                }
	        	});
            } else {
				
				$.messager.alert('消息', data.message);
            }
		},
		error : function() {
			closeMask();
			
			$.messager.alert('消息', '生成合同失败，请联系管理员！');
		}
	});
	
	
	
}

function createCollateralContract(diya_id){
	var contno=$('#contractno').val();
	if(contno!=null &&contno!=''){
		var url="loan/sign/createCollateralContract.do";
		var htfrom=$("<form></form>");
		htfrom.attr("target","_blank");
		htfrom.attr("action",url);
		htfrom.css('display', 'none');
		htfrom.append($("<input value='"+diya_id+"' name='diyaId'>"));
		htfrom.append($('#updateForm').find("#id").clone());
		htfrom.appendTo('body');
		htfrom.submit();
		htfrom.remove();
	}else{
		
		$.messager.alert('消息', "生成抵押合同前，请先生成合同。");
	}
}
function bmmupdateFunction(val) {
	
	if(val==1){
		$.messager.confirm('消息', "是否确认提交？",function(ok){
			if(ok){
				$('#subbpm').val('trueSubbpm');
				$('#auditResult').val(val);
				var contno=$('#contractno').val();
				if(contno!=null &&contno!=''){
				updateFunction();
				}else{
					
					$.messager.alert('消息', "提交前 ，请先生成合同。");
				}
				$('#subbpm').val('');
			}
		});
	}else if(val==4){
		$.messager.confirm('消息', "是否确认放弃？",function(ok){
			if(ok){
				$('#subbpm').val('trueSubbpm');
				$('#auditResult').val(val);
				var contno=$('#contractno').val();
				if(contno!=null &&contno!=''){
				updateFunction();
				}else{
					
					$.messager.alert('消息', "提交前 ，请先生成合同。");
				}
				$('#subbpm').val('');
			}
		});
	}
}


//返回
function back() {
	window.history.go(-1);
}
//更新保存
function updateFunction() {

	//弹出异步加载 遮罩
	openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$.ajax( {
		type : "POST",
		url : "<%=basePath%>" + "loan/sign/save.do",
		data : encodeURI(params),
		success : function(data) {
			$('#subbpm').val('');
			closeMask();closeMask();
			if ("true" == data.success) {
					//关闭遮罩，弹出消息框
				$.messager.alert('消息', data.message,"info", function(){
						window.history.go(-1);
				});
			} else {
				$.messager.alert('消息', data.message);
				//按钮生效
				$(".btn").removeAttr("disabled");
			}
},
error : function() {
	closeMask();
	$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
	//按钮生效
		$(".btn").removeAttr("disabled");
	}
	});
}

//返回
function back() {
	window.history.go(-1);
}

//打开Loading遮罩并修改样式
/* function openLoading() {
	$('#loading').window('open');
	$("#loading").attr("class", "");
	$("div[class='panel window']").css("position", "absolute");
	$("div[class='panel window']").attr("class", "");
	$("div[class='window-shadow']").attr("class", "");
}
 */
//页面加载完动作
$(document).ready(function() {
//	var tsurl = "sys/datadictionary/listjason.do?keyName=contracttype";
//	$("#hetong").combobox("clear");
//	$('#hetong').combobox( {
//		url : tsurl,
//		valueField : 'keyProp',
//		textField : 'keyValue',
//		select:'01'
//	});

});

</script>
</html>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />
		<title>营业部审批查看</title>
		<link href="css/global.css" type="text/css" rel="stylesheet" />
		<link href="css/icon.css" type="text/css" rel="stylesheet" />
		<link href="css/default/easyui.css" type="text/css" rel="stylesheet" />
		<script src="js/jquery.min.js" type="text/javascript"></script>
		<script src="js/jquery.form.js" type="text/javascript"></script>
		<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
		<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
		
		<style type="text/css"></style>
	</head>
	<body>
		<div id="main">
			<div id="part11" class="part">
				<p class="title">
					<a href="javascript:void(0);">个人借款申请营业部审批</a>
				</p>
				<div class="content">
				
					<form id="readForm" name="readForm"
						action="loan/loanDepartVerify/read.do">
						<%-- 重要！--%>
						<input id="id" name="id" type="hidden" size="35" value="${id}" />
						<div class="easyui-tabs" data-options="tools:'#tab-tools'">
							<jsp:include page="/loan/app/read.do?id=${id}" ></jsp:include>
						</div>
						 <br/>
						
						 <%--查看图片--%>
						<table width="100%">
							<tr>
								<td id="imgDiv">
								<jsp:include page="/img/imgSlidePath.do?appId=${bean.appId}" />
								</td>
							</tr>
						</table>
						<br />
						<br />
						<input name="subbpm" id="subbpm" type="hidden" value="" />
						<input name="auditResult" id="auditResult" type="hidden" value="" />
						<br/>
						
						<c:choose>
							<c:when test="${state == '1'}">
							<input type="button" value="通过" class="btn" onclick="application(1)" />
      							 <input type="button" value="不通过" class="btn" onclick="application(0)" />
								不通过原因：
								<input id="auditText" name="auditText" type="text" value=""  class="textbox easyui-validatebox" data-options="validType:['length[0,500]']"/>
     						 </c:when>
							<c:otherwise> 
							  	<label style="font-size: 15px;color: blue;">复议原因：${logContent}。</label>
							  	<br/>
							<br/>
							<input type="button" value="复议通过" class="btn" onclick="application(1)" />
      							 <input type="button" value="复议不通过" class="btn" onclick="application(2)" />
								复议不通过原因：
								<input id="auditText" name="auditText" type="text" value=""  class="textbox easyui-validatebox" data-options="validType:['length[0,500]']"/>
      						</c:otherwise>
						</c:choose>
					<br/>
				</div>
<label ></label>

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


//提交
function application(val) {
	$('#subbpm').val('trueSubbpm');
	$('#auditResult').val(val);
	if(val==0){
		var btgyy=$('#auditText').val();
		if(btgyy==null||btgyy==""){
			
			$.messager.confirm('消息', "请填写不通过信息。");
			$('#subbpm').val('');
			return;
		}
		$('#auditText').val(btgyy);
	}
	readForm();
	$('#subbpm').val('');
	
	//window.location = "<%=basePath%>" + "loan/departverify/application.do"
			//+ data_id + "&appType=" + getRadioValue('app');
}
//保存
function readForm() {
	//弹出异步加载 遮罩
	openMask();
	var params = $('#readForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "loan/departverify/save.do",
		data : encodeURI(params),
		success : function(data) {
			if ("true"==data.success) {
				//关闭遮罩，弹出消息框
				closeMask();
				
				$.messager.alert('消息', data.message,"info", function(){
//	                   	var url= "<%=basePath%>" + "loan/departverify/query.do";
//						window.location=url;
						window.history.go(-1);
            	});
            } else {
            	closeMask();
				
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
function back(){
	window.history.go(-1);
}

//打开Loading遮罩并修改样式
/* function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
} */


//页面加载完动作
$(document).ready(function (){
});
$("div[name='redInfo']").find("input").attr("disabled", "disabled");
$("div[name='redInfo']").find("select").attr("disabled", "disabled");
</script>
</html>


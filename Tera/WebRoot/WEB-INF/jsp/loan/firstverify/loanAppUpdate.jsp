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
		<title>借款端申请表更新</title>
		<link href="css/global.css" type="text/css" rel="stylesheet" />
		<link href="css/icon.css" type="text/css" rel="stylesheet" />
		<link href="css/default/easyui.css" type="text/css" rel="stylesheet" />
		<script src="js/jquery.min.js" type="text/javascript"></script>
		<script src="js/jquery.form.js" type="text/javascript"></script>
		<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
		<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
		
		<style type="text/css"></style>

<script type="text/javascript">
$(document).ready(function(){ 
		if(${bean.state} == "1" || ${bean.state} == "3"){
			var btn_fy = document.getElementById("btn_fy");
			var txt_fyReason = document.getElementById("txt_fyReason");
			var lbl_fy = document.getElementById("lbl_fy");
			btn_fy.style.display = "none";
			lbl_fy.style.display = "none"; 
			txt_fyReason.style.display = "none";
		}   
		
		if(${bean.state} == "2"){
			var btn_tg = document.getElementById("btn_tg");
			var txt_btgReason = document.getElementById("denyReason");
			var btn_btg = document.getElementById("btn_btg");
			var lbl_btg = document.getElementById("lbl_btg");
			btn_tg.style.display = "none"; 
			btn_btg.style.display = "none"; 
			txt_btgReason.style.display = "none";
			lbl_btg.style.display = "none"; 
		}    
	});
</script>
	</head>
	<body>
		<div id="main">
			<div id="part1" class="part">
				<p class="title">
					&nbsp;
					<a href="javascript:void(0);">个人借款初审</a>
				</p>
				<div class="easyui-tabs" data-options="tools:'#tab-tools'">
					<%--					<jsp:include page="/loan/app/read.do?id=${bean.id}" />--%>
					<jsp:include page="/loan/app/read.do" />
				</div>


				<div class="content">
					<form id="updateForm">
						<table>
							<tbody>
								<input id="id" name="id" type="hidden" size="35"
									value="${ bean.id}" />

								<!--<tr>
									<td>
										<input type="button" value="提交" class="btn"
											onclick="updateFunction()" />
										<input type="button" value="返回" class="btn"
											onclick="javascript:back()" />
									</td>
									<td></td>
								</tr>
							-->
							</tbody>
						</table>


						<!--<input type="radio" value="通过" checked="checked" name="checkResult"/>
						<input type="radio" value="不通过"  name="checkResult"/>
						<input type="radio" value="复议"  name="checkResult"/>
						<input type="radio" value="放弃"  name="checkResult"/>
						<input type="text" name="reason" style="direction: none"/>
						
						<input type="button" value="提交" onclick="javascript:checkApplication()"/>
						
						-->
						<!-- 影像查看开始 -->
						<table width="100%">
							<tr>
								<td id="imgDiv">
								<jsp:include page="/img/imgSlidePath.do?appId=${bean.appId}" />
								</td>
							</tr>
						</table>
						<!-- 影像查看结束 -->
						<table>
							<tbody>
								<tr>
									<td>
										<c:choose>  
					                        <c:when test="${bean.state eq '1' && !empty denyReason }">  
					                            	退回原因：
					                    		<textarea id="noPassReason"style="width: 100%;height:100px !important;"
						 						class="textbox easyui-validatebox" readonly="readonly">${denyReason}</textarea>
					                    	</c:when> 
					                        <c:when test="${bean.state eq '2' }">
					                        		拒件原因：
					                            <textarea id="noPassReason" style="width: 100%;height:100px !important;"
						 						class="textbox easyui-validatebox"readonly="readonly">${denyReason}</textarea>
					                        </c:when>  
					                         <c:when test="${bean.state eq '3' }">  
					                            	复核退回原因：
					                            <textarea id="noPassReason" style="width: 100%;height:100px !important;"
						 						class="textbox easyui-validatebox" readonly="readonly">${denyReason}</textarea>
					                        </c:when>   
			                    		</c:choose> 
		                    		</td>
								</tr>
								<tr>
									<td>
										<input id="btn_tg" type="button" value="通过" class="btn"
											onclick="javascript:ajax_dealApplication('loan/firstverify/passApplication.do')" />
									</td>
								</tr>
								<tr>
									<td>
										<input id="btn_btg" type="button" value="不通过" class="btn"
											onclick="javascript:ajax_dealApplication('loan/firstverify/denyApplication.do')" />
										<label id="lbl_btg">
											不通过原因：
										</label>
										
										<input type="text" id="denyReason" name="denyReason" />
										<br />
									</td>
								</tr>
								<tr>
									<td>
										<input type="button" value="放弃" class="btn"
											onclick="javascript:ajax_dealApplication('loan/firstverify/quitApplication.do')" />
									</td>
								</tr>
								<tr>
									<td>
										<input id="btn_fy" type="button" value="复议" class="btn"
											onclick="javascript:ajax_dealApplication('loan/firstverify/talkAgainApplication.do')" />
										<label id="lbl_fy">
											复议原因：
										</label>
										<%--<input id="txt_fyReason" type="text" name="talkAgainReason" />--%>
									<textarea id="txt_fyReason" name="talkAgainReason" 
										style="width: 100%;height:100px !important;"
										data-options="validType:['length[0,200]']"
						 				class="textbox easyui-validatebox" ></textarea>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
		</div>
	</body>

	<script type="text/javascript">

//更新保存
function updateFunction() {
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		$(this).val($.trim($(this).val()));
	});
	//验证表单验证是否通过
	if (false == $('#updateForm').form('validate')) {
		return;
	}
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", "disabled");
	$.ajax( {
		type : "POST",
		url : "<%=basePath%>" + "sys/firstverify/save.do",
		data : encodeURI(params),
		success : function(data) {
			//TODO 更新类的操作 
		alert("操作成功！");
		//按钮生效
		$(".btn").removeAttr("disabled");
		window.history.go(-1);
	},
	error : function() {
		alert("数据加载失败！");
		//按钮生效
		$(".btn").removeAttr("disabled");
	}
	});
}
//返回
function back() {
	window.history.go(-1);
}

//杨长收添加，处理申请
function ajax_dealApplication(action_url) {
	
	//如果是不通过一定要填写不通过原因
	if(action_url == "loan/firstverify/denyApplication.do"){
		var  denyReason = $.trim($('#denyReason').val());
		if(denyReason == null || denyReason=="" ){
			
			$.messager.alert('消息', "请填写不通过原因。");
			return;
		}
	}
	
	//如果是复议一定要填写复议原因
	if(action_url == "loan/firstverify/talkAgainApplication.do"){
		var  txt_fyReason = $.trim($('#txt_fyReason').val());
		if(txt_fyReason == null || txt_fyReason=="" ){
			
			$.messager.alert('消息', "请填写复议原因。");
			return;
		}
	}

	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		$(this).val($.trim($(this).val()));
	});
	//验证表单验证是否通过
	if (false == $('#updateForm').form('validate')) {
		return;
	}
	//弹出异步加载 遮罩
	openMask();

	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", "disabled");
	$.ajax( {
		type : "POST",
		url : "<%=basePath%>" + action_url,
		data : encodeURI(params),
		success : function(data) {
			if ("true" == data.success) {
				//关闭遮罩，弹出消息框
				closeMask();
				//复议成功,放弃成功
				$.messager.alert('消息', data.message,"info", function() {
						var url = "<%=basePath%>" + "loan/firstverify/query.do";
						window.location = url;
						//window.history.go(-1);
			});
			} else {
				//复议失败
				closeMask();
				$.messager.alert('消息', data.message);
				//按钮生效
				$(".btn").removeAttr("disabled");
			}
	
			//alert(data);
			//按钮生效
			//$(".btn").removeAttr("disabled");
			//window.history.go(-1);
		},
		error : function() {
			closeMask();
			alert("申请复议失败！");
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
		});
}

//打开Loading遮罩并修改样式
/* function openLoading() {
	$('#loading').window('open');
	$("#loading").attr("class", "");
	$("div[class='panel window']").css("position", "absolute");
	$("div[class='panel window']").attr("class", "");
	$("div[class='window-shadow']").attr("class", "");
} */

</script>


</html>


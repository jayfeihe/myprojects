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
		<title>借款咨询更新</title>
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
			<div id="part1" class="part">
				<p class="title">
					<a href="javascript:void(0);">个人客户</a>
				</p>
				<div class="content">
					<form id="updateForm">
						<table>
							<tbody>
								<input id="id" name="id" type="hidden" size="35"
									value="${ bean.id}" />
								<input id="type" name="type" type="hidden" value="01" />
								<tr>
									<td>
										姓名
									</td>
									<td>
										<input id="name" name="name" type="text"
											data-options="validType:['length[0,50]']"
											class="textbox easyui-validatebox" value="${bean.name}" />
									</td>
									<td>
										电话:
									</td>
									<td>
										<input id="phone" name="phone" type="text"
											data-options="validType:['length[0,100]']"
											class="textbox easyui-validatebox" value="${bean.phone}" />
									</td>
									<td>
										金额:
									</td>
									<td>
										<input id="amount" name="amount" type="text" editable="false"
											data-options="min:0,precision:2"
											class="textbox easyui-numberbox" value="${bean.amount}" />
									</td>
								</tr>
								<tr>
									<td>
										开始时间:
									</td>
									<td>
										<input id="startTime" name="startTime" type="text" data-options="required:true"
											editable="false" class="textbox easyui-datebox"
											value="${bean.startTimeStr}" />
									</td>
									<td>
										结束时间:
									</td>
									<td>
										<input id="endTime" name="endTime" type="text" data-options="required:true"
											editable="false" class="textbox easyui-datebox"
											value="${bean.endTimeStr}" />
									</td>
									<td>
										用途:
									</td>
									<td>
										<select id="loanPurpose" name="loanPurpose"
											data-options="required:true,validType:['length[0,10]']"
											class="textbox easyui-validatebox" style="width: 150px;">
											<c:forEach items="${loanusageList}" var="datas"
												varStatus="status">
												<c:if test="${datas.keyName eq 'loanusage'}">
													<option value="${datas.keyProp}"
														<c:if test="${bean.loanPurpose== datas.keyProp}">selected</c:if>>
														${datas.keyValue}
													</option>
												</c:if>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td>
										还款来源:
									</td>
									<td>
										<input id="repaymentSource" name="repaymentSource" type="text"
											class="textbox easyui-validatebox"
											value="${bean.repaymentSource}" />
									</td>
									<td>
										是否抵押:
									</td>
									<td>
										<select id="mortgage" name="mortgage"
											class="textbox easyui-validatebox" data-options="required:true,validType:['length[0,10]']"
											style="width: 150px;">
											<option value="Y"
												<c:if test="${bean.mortgage == 'Y'}">selected</c:if>>
												是
											</option>
											<option value="N"
												<c:if test="${bean.mortgage == 'N'}">selected</c:if>>
												否
											</option>
										</select>
									</td>
								</tr>
								<tr>
									<td>
										证件类型:
									</td>
									<td>
										<select id="idType" name="idType"
											class="textbox easyui-validatebox" data-options="required:true,validType:['length[0,10]']"
											style="width: 150px;">
											<c:forEach items="${loanusageList}" var="datas"
												varStatus="status">
												<c:if test="${datas.keyName eq 'personidtype'}">
													<option value="${datas.keyProp}"
														<c:if test="${bean.loanPurpose== datas.keyProp}">selected</c:if>>
														${datas.keyValue}
													</option>
												</c:if>
											</c:forEach>
										</select>
									</td>
									<td>
										证件号码:
									</td>
									<td>
										<input id="idNo" name="idNo" type="text"
											data-options="validType:['length[0,18]','idNo']" 
											class="textbox easyui-validatebox" value="${bean.idNo}" />
									</td>
								</tr>
								
								<tr>
									<td colspan="4">
										<input type="button" value="提交" class="btn"
											onclick="updateFunction()" />
										<input type="button" value="返回" class="btn"
											onclick="javascript:back()" />
											
											<input type="button" value="转申请" class="btn"
											onclick="readLoanApp()" />
									</td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
		</div>
		
<!-- <div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
	<img src="img/loading.gif" alt="加载中..." />
</div> -->

	</body>

<script type="text/javascript">
function readLoanApp(){
	window.location = "<%=basePath%>" + "loan/app/update.do?paramVal="+"per";
	
	return;
}
//更新保存
function updateFunction() {
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		$(this).val($.trim($(this).val()));
	});
	//验证表单验证是否通过
	if(false == $('#updateForm').form('validate') ){
		
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}
	//弹出异步加载 遮罩
	openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "loanConsult/save.do",
		data : encodeURI(params),
		success : function(data) {
			if ("true"==data.success) {
				//关闭遮罩，弹出消息框
				closeMask();
				
				$.messager.alert('消息', data.message,"info", function(){
//	                   	var url= "<%=basePath%>" + "loanConsult/query.do";
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

//打开Loading遮罩并修改样式
/* function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
} */

//返回
function back() {
	window.history.go(-1);
}
</script>
</html>


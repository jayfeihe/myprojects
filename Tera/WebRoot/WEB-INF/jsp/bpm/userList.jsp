<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
		<div class="content" style="width: 100%; height: 100%">
			<form id="upBpmUserForm"  method="post">
			<input type="hidden" name="bizKey" value="${bizKey }" />
				<table>
					<tr>
						<td>待处理人:</td>
						<td></td>
					</tr>
					
							<c:forEach items="${users}" var="user" varStatus="status">
					<tr>
					<td></td>
						<td>
								 <input <c:if test="${user.loginId==processer}">checked="checked"</c:if> 
								 type="radio" name="userLoginId" value="${user.loginId}" />${user.name}&nbsp; &nbsp; &nbsp; 
						</td>	
					</tr>
							</c:forEach>
				</table>	
			</form>
			<br>
			<table >
				<tr>
						<td>
							<input type="button" value="提交" class="btn"
								onclick="submitbpmUserform()" />
							<input type="button" value="返回"
							class="btn" onclick="javascript:back()" />
						</td>
					</tr>
			</table>
		</div>

<script type="text/javascript">
function submitbpmUserform() {
	var fromdata=$('#upBpmUserForm').serialize();
	openMask();
	$.ajax({
		type : "POST",
		data : fromdata,
		url  : "bpm/reAssignTask.do",
		success : function(data) {
			closeMask();
			if ("true"==data.success) {
				
				$.messager.alert('消息', data.message,"info", function(){
                	$('#ProcesserDiv').dialog('close');
                	//flushAfterSubmit();
					window.location.reload();
	        	});
	
            } else {
				
				$.messager.alert('消息', data.message);
            }
			
		},
		error : function() {
			closeMask();
			$.messager.alert('消息',"操作失败，请联系管理员！");
		}
	});
}
	/*function flushAfterSubmit(){
		window.location.reload();
	}*/
//返回
function back() {
	$('#ProcesserDiv').dialog('close');
}
</script>
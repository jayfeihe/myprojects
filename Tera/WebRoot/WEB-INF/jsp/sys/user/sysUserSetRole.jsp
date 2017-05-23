<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	<div id="main">
		<div>
				<form id="setRoleForm">
					<table>
						<c:forEach items="${allRoles }" var="role" varStatus="status">
							<c:if test="${(status.count%4) eq 1 }"><tr></c:if>
								<td><input type="checkbox" name="roleId" value="${role.id}"><span id="${role.id}">${role.name}</span></td>
							<c:if test="${(status.count%4) eq 0 }"></tr></c:if>	
						</c:forEach>
					</table>
				</form>
		</div>
<hr/>
<div align="right">
	<input value="确定" type="button" id="submitId" onclick="submit()" class="btn"/> 
	<input value="重置" type="button" id="resetId" onclick="$('#setRoleForm').form('clear');" class="btn"/>
</div>
</div>
	
<script type="text/javascript">

function submit(){
	$("#dialogDiv").dialog('close');
}

$(document).ready(function (){
	var roleIdStr = '${roleIds}';
	var roleIds = roleIdStr.split(',');
	$("input[name='roleId']").each(function(){
		for(var i=0;i<roleIds.length;i++) {
			var roleId = $(this).val();
			if(roleId == roleIds[i]) {
				$(this).attr("checked","checked");
			}
		}
	})
});
</script>
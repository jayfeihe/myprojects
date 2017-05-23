<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	<div id="main">
		<div style="width: 360px;height: 205px" name="inputInfo">
			<form id="addDataId">
				<input type="hidden" id="staffNo_departset" value="${bean.staffNo }" />
				<input type="hidden" id="orgId_departset" value="${bean.orgId }" />
				<input type="hidden" id="level_departset" value="${bean.level }" />
				<ta:formToken/>
				<c:if test="${bean == null}">
					<table>
						<tr>
							<td>组织:</td>
							<td>
								<input id="id" name="id" value="${bean.id }" type="hidden"/>
								<input id="departId" name="departId" class="easyui-combotree" data-options="url:'sys/depart/listDataUseForSales.do?orgId=${login_org.orgId}',method:'get',required:true" style="width: 250px;"/>
							</td>
						</tr>
					</table>
				</c:if>
				<c:if test="${bean != null}">
					<table>
						<tr>
							<td>组织:</td>
							<td>
								<input id="id" name="id" value="${bean.id }" type="hidden"/>
								<input id="departId" name="departId" class="easyui-combotree" data-options="url:'sys/depart/listDataUseForSales.do?orgId=${login_org.orgId}',method:'get',required:true" value="${bean.departId }" style="width: 250px;"/>
							</td>
						</tr>
					</table>
				</c:if>	
				<hr/>
	<div align="right">
		<input type="button" value="提交" class="btn" onclick="submitDepartForm()" /> 
		<input value="重置" type="button" id="resetId" class="btn" onclick="$('#addDataId').form('clear');"/>

	</div>
			</form>
		</div>
	</div>
	<div id="loading" class="easyui-window" title=""
		data-options="border:false,modal:true,closed:true,draggable:false,resizable:false">
		<img src="img/loading.gif" alt="加载中..." />
	</div>
	<script type="text/javascript">
function submitDepartForm(){
	//去掉 input 输入的 前后空格
	$("#addDataId").find('input').each(function(){
		$(this).val($.trim($(this).val()));
	});
	//验证表单验证是否通过
	if(false == $('#addDataId').form('validate')){
		$.messager.alert('消息', "页面有不符合规范内容，请正确填写！");
		return;
	}
	
	// 如果是主管，判断该机构下是否存在
	if($("#level_departset").val() == '3'){
		var staff_no = $("#staffNo_departset").val();
		var org_id = $('#orgId_departset').val();
		var depart_id = $("#addDataId").find("input[name='departId']").val();
		if(isExistTeamManagerByOrgId(staff_no,org_id,depart_id)) {
			$.messager.alert("消息","该组已存在团队经理","warning");
			return;
		}
	}
	
	var fromdata=$('#addDataId').serialize();
	$.ajax({
		type : "POST",
		data : fromdata,
		url  : "<%=basePath%>sales/departSet.do",
		success : function(data) {
			if ("true"==data.success) {
				$.messager.alert('消息', data.message,"info", function(){
                	$('#dialogDiv').dialog('close');
	        	});
            } else {
            	$.messager.alert('消息', data.message);
            }
		},
		error : function() {
			$.messager.alert('消息',"操作失败，请联系管理员！");
		}
	});
}

//判断此营业部是否已经有主管存在
function isExistTeamManagerByOrgId(staff_no,org_id,depart_id) {
	var isExist = false;
	$.ajax({
		type: 'get',
		async:false,
		url: "<%=basePath%>" + "sales/isExistTeamManagerByOrgId.do",
		data: ({'staffNo':staff_no,'orgId':org_id,'departId':depart_id}),
		success: function(data){
			if(data.success == 'true'){
				isExist = true;
			}
		},
		error: function(){
			
		}
	});
	return isExist;
}

</script>	


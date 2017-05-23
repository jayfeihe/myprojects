<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div id="main">
<%--	<div style="width: 350px;height: 200px" name="inputInfo">--%>
	<form id="addDataId" >
    <table>
		<tr>
			<td>
				申请号：<br/>
				<input value="${bean.appId}" type="text" style="width:223px;" readonly="readonly"
			    data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" />
			</td> 
		</tr>
		<tr> 
			<td>
				客户姓名：<br/>
				<input id="name" name="name" value="${bean.name}" type="text" readonly="readonly"
			    data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" />
			</td>
		</tr>
		<tr> 
			<td>
			 	前端拒件码:<br/>
			    <input id="saleRefuseCode1" name="saleRefuseCode1" value="" type="text" editable="false"
			    data-options="required:true,validType:['length[0,50]']" class="easyui-combobox" />
			    <input id="saleRefuseCode2" name="saleRefuseCode2" value="" type="text" editable="false"
			    data-options="required:true,validType:['length[0,50]']" class="easyui-combobox" />
			</td>
		</tr>
		<tr> 
			<td>
			 	 反馈销售描述:
			    <textarea id="saleRefuseDescribe" name="saleRefuseDescribe" data-options="required:true" style="resize: none;width:100%;height:50px !important;" class="textbox easyui-validatebox"></textarea>
			</td> 
		</tr>
		<tr> 
			<td>
				&nbsp;
			</td> 
		</tr>
	</table>
	</form>  
<div>
	<input value="拒贷" type="button" onclick="repelLoanFn('${bean.appId }')" class="btn"/> 
	<input value="取消" type="button" id="resetId" class="btn" onclick="goback()"/>
</div>
</div>

<script language="javascript">
function goback(){
	window.location.reload();
}

//确认拒贷
function repelLoanFn(id) {
	//去掉 input 输入的 前后空格
	$('#addDataId').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	//验证表单验证是否通过
	if(false == $('#addDataId').form('validate') ){
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}
	var params = $('#addDataId').serialize();
	$.messager.confirm('消息', "您确认要拒贷吗？", function(ok){
		if (ok){
			openMask();
			$.ajax({
				url: "house/app/saleRefuse.do",
				data : params + '&appId=' + id,
				success : function(data) {
					closeMask();
					if ("true"==data.success) {
						$.messager.alert('消息', data.message,"info", function(){
							window.history.go(-1);
// 							window.location.reload();
							return true;
		            	});
					} else {
						$.messager.alert('消息', data.message);
					}
				},
				error : function() {
					$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
				}
			});
        }
    });
}

//页面加载完动作
$(document).ready(function (){
	
});
</script>
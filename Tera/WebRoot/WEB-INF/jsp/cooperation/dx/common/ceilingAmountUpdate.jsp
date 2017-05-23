<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
	<div id="main">
		<div style="width: auto;height: auto" name="inputInfo">
				<form id="updateForm">
					<table >
						<input id="id" name="parameter.id" type="hidden" value="${paramId}" />
						<input name="parameter.paramValue" type="hidden" value="" />
						<tr>
							<td>鼎轩融资总额：</td>
							<td><input id="ceilingAmount" type="text" data-options="min:0,precision:2"
									class="textbox easyui-numberbox" value="${ceilingAmount}" style="width: auto;" /> 万元</td>
						</tr>
						<tr>
							<td >本周审批通过金额：</td>
							<td><input id="passAmountOfWeek" name="passAmountOfWeek" type="text" disabled="disabled" 
									data-options="min:0,precision:2"
									class="textbox easyui-numberbox" value="${passAmountOfWeek}" /> 万元</td>
						</tr>
						<tr>
							<td>本周审批剩余金额：</td>
							<td><input id="leaveAmountOfWeek" name="leaveAmountOfWeek" type="type" disabled="disabled" 
									data-options="min:0,precision:2"
									class="textbox easyui-numberbox" value="${leaveAmountOfWeek}" /> 万元</td>
						</tr>
						<tr>
							<td>本周签约通过金额：</td>
							<td><input id="signAmountOfWeek" name="signAmountOfWeek" type="type" disabled="disabled" 
									data-options="min:0,precision:2"
									class="textbox easyui-numberbox" value="${signAmountOfWeek}" /> 万元</td>
						</tr>
						<tr>
							<td>本周签约剩余金额：</td>
							<td><input id="leaveSignAmountOfWeek" name="leaveSignAmountOfWeek" type="type" disabled="disabled" 
									data-options="min:0,precision:2"
									class="textbox easyui-numberbox" value="${leaveSignAmountOfWeek}" /> 万元</td>
						</tr>	
					</table>
				</form>
		</div>
	</div>
	<hr/>
<div align="right">
	<input type="button" value="提交" class="btn" onclick="updateFunction()" /> 
	<input type="button" value="取消" id="resetId" class="btn" onclick="$('#dialogDiv').dialog('close');"/>	
</div>

<script type="text/javascript">
//更新保存
function updateFunction() {
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	
	
	//弹出异步加载 遮罩
	openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "cooperation/dx/common/save.do",
		data : encodeURI(params),
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
						window.location="<%=basePath%>cooperation/dx/common/query.do";
					});
				} else {
					$.messager.alert('消息', data.message);
					//按钮生效
					$(".btn").removeAttr("disabled");
				}
			},
			error : function() {
				//关闭遮罩，弹出消息框
				closeMask();
				$.messager.confirm('消息', '数据加载失败,请联系系统管理员！');
				//按钮生效
				$(".btn").removeAttr("disabled");
			}
		});
	}

	//页面加载完动作
	$(document).ready(function() {
		var txtValue = $("#ceilingAmount").val();
		$("#ceilingAmount").bind("keyup", function() {
             var currentValue = $(this).val();
             if (currentValue != txtValue) {
                 TxtChange(currentValue);
                 txtValue = currentValue;
             }
         });
	});
	
	function TxtChange(currentValue) {
		var passAmount = $('#passAmountOfWeek').val();
		var signAmount = $('#signAmountOfWeek').val();
		var leaveAmount = currentValue - passAmount;
		var leaveSignAmount = currentValue - signAmount;
		$('#leaveAmountOfWeek').val(leaveAmount.toFixed(2));
		$('#leaveSignAmountOfWeek').val(leaveSignAmount.toFixed(2));
		$("input[name='parameter.paramValue']").val(currentValue*10000);
    }
</script>


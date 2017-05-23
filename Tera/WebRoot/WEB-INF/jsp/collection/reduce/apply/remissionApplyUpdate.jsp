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
				<form id="remissionApplyForm">
					<input id="contractNo" name="contractNo" type="hidden" value="${bean.contractNo}"/>
					<input id="applyUid" name="applyUid" type="hidden" value="${login_user.loginId}"/>
					<table>
							<td>合同编号：</td>
							<td><input id="contractNo" name="contractNo" type="text" disabled="disabled"
									class="textbox" value="${bean.contractNo}" style="width: auto;" /></td>
							<td >申请人：</td>
							<td><input id="applyUid" name="applyUid" type="text" disabled="disabled"
									class="textbox" value="${login_user.name}" /></td>
						</tr>
						<tr>
							<td>客户姓名：</td>
							<td><input id="name" name="name" type="text" disabled="disabled"
									class="textbox" value="${bean.customerName}" /></td>
							<td>身份证号：</td>
							<td><input id="idNo" name="idNo" type="text" disabled="disabled"
									class="textbox" value="${bean.idNo}" /></td>
						</tr>	
					</table>
					<table>
						<tbody>
							<tr>
								<td>还款金额:</td>
								<td>
									<input type="text" id="reducedAmount" name="reducedAmount" data-options="required:true,min:0,precision:2" 
									class="textbox easyui-validatebox easyui-numberbox" value="" /> 元
								</td>
							</tr>
							<tr>
								<td>减免原因:</td>
								<td colspan="4"><textarea id="applyText" name="applyText" type="text" editable="false" class="textbox easyui-validatebox"
									data-options="required:true,min:0,precision:2" 
									style="resize: none;width:390px;height:50px!important; background: #F2F2F2;"></textarea>
								</td>
							</tr>
						</tbody>
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
	$('#remissionApplyForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	var reducedAmount = $("input[name='reducedAmount']").val();
	var applyText = $("#applyText").val();
	if (null == reducedAmount || '' == reducedAmount) {
		$.messager.alert('消息', "请填写还款金额", 'warning');
		return;
	}
	if (null == applyText || '' == applyText) {
		$.messager.alert('消息', "请填写减免原因", 'warning');
		return;
	}
	
	//弹出异步加载 遮罩
	openMask();
	var params = $('#remissionApplyForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "collection/reduce/apply/save.do",
		data : encodeURI(params),
		dataType : "json",
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			$.messager.alert('消息', data.message, 'info', function(){
				if(data.success == 'true') {
					$('#dialogDiv').dialog('close');
				}
			});
			//按钮生效
			$(".btn").removeAttr("disabled");
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
	});
</script>


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
						<input id="id" name="id" type="hidden" value="${bean.id}" />
						<input id="contractNo" name="contractNo" type="hidden" value="${bean.contractNo}" />
						<input id="state" name="state" type="hidden" value="${bean.state}" />
						<input id="checkUid" name="checkUid" type="hidden" value="${login_user.name}"/>
						<input id="approvalUid" name="approvalUid" type="hidden" value="${bean.approvalUid}"/>
						<tr>
							<td>合同编号：</td>
							<td><input id="contractNo" name="contractNo" type="text" disabled="disabled"
									class="textbox" value="${bean.contractNo}" style="width: auto;" /></td>
							<td>客户姓名：</td>
							<td><input id="customerName" name="customerName" type="text" disabled="disabled"
									class="textbox" value="${bean.customerName}" /></td>
							<td>身份证号：</td>
							<td><input id="idNo" name="idNo" type="text" disabled="disabled"
									class="textbox" value="${bean.idNo}" /></td>
						</tr>
						<tr>
							<td >申请时间：</td>
							<td><input id="applyTime" name="applyTime" type="text" disabled="disabled"
									class="textbox" value="${bean.applyTimeStr}" /></td>
							<td >申请人：</td>
							<td><input id="applyName" name="applyName" type="text" disabled="disabled"
									class="textbox" value="${bean.applyName}" /></td>
						</tr>	
						<tr>
								<td>司法原因:</td>
								<td colspan="4"><textarea id="applyText" name="applyText" type="text" disabled="disabled" editable="false" class="textbox"
									style="resize: none;width:400px;height:50px!important; background: #F2F2F2;">${bean.applyText}</textarea>
								</td>
						</tr>
						<c:if test="${bean.approvalResult=='2'}">
						<tr>
							<td >审批时间：</td>
							<td><input id="approvalTime" name="approvalTime" type="text" disabled="disabled"
									class="textbox" value="${bean.approvalTimeStr}" /></td>
							<td >审批人：</td>
							<td><input id="approvalName" name="approvalName" type="text" disabled="disabled"
									class="textbox" value="${bean.approvalName}" /></td>
						</tr>
						<tr>
								<td>审批退回原因:</td>
								<td colspan="4"><textarea id="approvalText" name="approvalText" type="text" disabled="disabled" editable="false" class="textbox"
									style="resize: none;width:400px;height:50px!important; background: #F2F2F2;">${bean.approvalText}</textarea>
								</td>
						</tr>
						</c:if>
						<tr>
								<td>复核结果:</td>
								<td colspan="4"><select id="checkResult" name="checkResult">
								<option value="1" selected="selected">通过</option>
								<option value="2">退回</option>
								</select>	
								</td>
							</tr>
							<tr>
								<td>复核意见:</td>
								<td colspan="4"><textarea id="checkText" name="checkText"
									data-options="required:true " style="resize: none;width:400px;height:50px!important;"
									class="textbox easyui-validatebox"></textarea></td>
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
	var checkResult = $("#checkResult").val();
	var checkText = $("#checkText").val();
	if (null == checkResult || '' == checkResult) {
		$.messager.alert('消息', "请选择复核结果", 'warning');
		return;
	}
	if (null == checkText || '' == checkText) {
		$.messager.alert('消息', "请填写复核意见", 'warning');
		return;
	}
	
	//弹出异步加载 遮罩
	openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>collection/judicial/review/save.do",
		data : params,
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
						window.location="<%=basePath%>collection/judicial/review/query.do";
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
	});
</script>


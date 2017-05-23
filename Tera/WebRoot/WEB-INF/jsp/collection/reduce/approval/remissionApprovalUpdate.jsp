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
						<!-- scope用于区分审批还是高级审批 -->
						<input id="scope" name="scope" type="hidden" value="${scope}" />
						<input id="id" name="collectionRemission.id" type="hidden" value="${bean.id}" />
						<input id="contractNo" name="collectionRemission.contractNo" type="hidden" value="${bean.contractNo}" />
						<input id="reducedAmount" name="collectionRemission.reducedAmount" type="hidden" value="${bean.collectionRemission.reducedAmount}" />
						<input id="approvalUid" name="collectionRemission.approvalUid" type="hidden" value="${login_user.loginId}"/>
						<tr>
							<td>合同编号：</td>
							<td><input id="contractNo" name="contractNo" type="text" disabled="disabled"
									class="textbox" value="${bean.contractNo}" style="width: auto;" /></td>
							<td >申请人：</td>
							<td><input id="applyUid" name="applyUid" type="text" disabled="disabled"
									class="textbox" value="${bean.collectionRemission.applyUid}" /></td>
						</tr>
						<tr>
							<td>客户姓名：</td>
							<td><input id="name" name="name" type="text" disabled="disabled"
									class="textbox" value="${bean.name}" /></td>
							<td>身份证号：</td>
							<td><input id="idNo" name="idNo" type="text" disabled="disabled"
									class="textbox" value="${bean.idNo}" /></td>
						</tr>
						<tr>
							<td class="title">还款金额：</td>
							<td><input id="name" name="name" type="text" disabled="disabled"
									class="textbox" value='￥${bean.collectionRemission.reducedAmount}元' /></td>
							<td class="title">减免金额：</td>
							<td><input id="idNo" name="idNo" type="text" disabled="disabled"
									class="textbox" value="￥${bean.remissionMount}元" /></td>
						</tr>	
					</table>
					<table>
						<tbody>
							<tr>
								<td>减免原因:</td>
								<td colspan="4"><textarea id="applyText" name="applyText" type="text" disabled="disabled" editable="false" class="textbox"
									style="resize: none;width:400px;height:50px!important; background: #F2F2F2;">${bean.collectionRemission.applyText}</textarea>
								</td>
							</tr>
							<tr>
								<td>复核人:</td>
								<td><input id="checkUid" name="collectionRemission.checkUid" type="text" disabled="disabled"
									data-options="validType:['length[0,20]']"
									class="textbox" value="${bean.collectionRemission.checkUid}" /></td>
								<td>复核结果:</td>
								<td><input id="checkResult" name="collectionRemission.checkResult" type="text" editable="false"
									class="textbox easyui-validatebox " disabled="disabled"
									value="<c:choose><c:when test="${bean.collectionRemission.checkResult eq 'Y'}">通过</c:when><c:when test="${bean.collectionRemission.checkResult eq 'N'}">不通过</c:when></c:choose>"/>
								</td>
								
							</tr>
							<tr>
								<td>复核意见:</td>
								<td colspan="4"><textarea id="checkText" name="collectionRemission.checkText" disabled="disabled"
									data-options="required:true " style="resize: none;width:400px;height:50px!important; background: #F2F2F2;"
									class="textbox easyui-validatebox">${bean.collectionRemission.checkText}</textarea></td>
							</tr>
							
							<tr>
								<td>审批人:</td>
								<td><input id="approvalUid" name="collectionRemission.approvalUid" type="text" disabled="disabled"
									data-options="validType:['length[0,20]']"
									class="textbox" value="${login_user.loginId}" /></td>
								<td>审批结果:</td>
								<td><input id="approvalResult" name="collectionRemission.approvalResult" type="text" editable="false"
									class="textbox easyui-validatebox easyui-combobox"
									data-options="required:true"/>
								</td>
							</tr>
							<tr>
								<td>审批意见:</td>
								<td colspan="4"><textarea id="approvalText" name="collectionRemission.approvalText"
									data-options="required:true " style="resize: none;width:400px;height:50px!important;"
									class="textbox easyui-validatebox"></textarea></td>
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
	$('#updateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	var approvalResult = $("input[name='collectionRemission.approvalResult']").val();
	var approvalText = $("#approvalText").val();
	if (null == approvalResult || '' == approvalResult) {
		$.messager.alert('消息', "请选择审批结果", 'warning');
		return;
	}
	if (null == approvalText || '' == approvalText) {
		$.messager.alert('消息', "请填写审批意见", 'warning');
		return;
	}
	
	//弹出异步加载 遮罩
	openMask();
	var scope = $("#scope").val();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "collection/reduce/approval/save.do",
		data : encodeURI(params),
		success : function(data) {
				//关闭遮罩，弹出消息框
				closeMask();
				$.messager.alert('消息', data.message, 'info', function(){
						window.location="<%=basePath%>collection/reduce/approval/query.do?scope="+scope;
				});
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


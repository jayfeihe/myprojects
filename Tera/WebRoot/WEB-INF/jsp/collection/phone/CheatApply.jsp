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
				<form id="applyUpdateForm">
					<input id="applyType" name="applyType" type="hidden" value="<%=request.getAttribute("applyType") %>"/>
					<input id="collectionWay" name="collectionWay" type="hidden" value="${bean.collectionWayCur}"/>
					<table>
							<td>合同编号：</td>
							<td><input id="contractNo" name="contractNo" type="text" readonly="readonly"
									class="textbox" value="${bean.contractNo}" style="width: auto;background: #F2F2F2;" /></td>
							<td >申请人：</td>
							<td><input  type="text" readonly="readonly" style="background: #F2F2F2;"
									class="textbox" value="${login_user.name}" /></td>
						</tr>
						<tr>
							<td>客户姓名：</td>
							<td><input id="name" name="name" type="text" readonly="readonly" style="background: #F2F2F2;"
									class="textbox" value="${bean.customerName}" /></td>
							<td>身份证号：</td>
							<td><input id="idNo" name="idNo" type="text" readonly="readonly" style="background: #F2F2F2;"
									class="textbox" value="${bean.idNo}" /></td>
						</tr>	
					</table>
					<table>
						<tbody>
							<tr>
								<td>申请原因:</td>
								<td colspan="4"><textarea id="applyText" name="applyText" type="text"  editable="false" class="textbox easyui-validatebox" 
									data-options="required:true,min:0,precision:2"
									style="resize: none;width:390px;!important;background: #F2F2F2;min-height: 50px;"></textarea>
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
	$('#applyUpdateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	var applyText = $("#applyText").val();
	if (null == applyText || '' == applyText) {
		$.messager.alert('消息', "请填写申请原因", 'warning');
		return;
	}
	
	//弹出异步加载 遮罩
	openMask();
	var params = $('#applyUpdateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "collectionBase/phone/cheatAplyUpdate.do",
		data : params,
		dataType : "json",
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if(data.success=="true"){
				if(${applyType}==2){
					$.messager.alert('消息', "欺诈申请成功", 'info', function(){
						<c:if test="${bean.collectionWayCur=='1'}"> window.location.href="<%=basePath%>" + "collectionBase/phone/actionquery.do";</c:if>
						<c:if test="${bean.collectionWayCur=='2'}"> window.location.href="<%=basePath%>" + "collection/visit/detail/query.do";</c:if>	
						//window.location.reload();
						$('#dialogDiv').dialog('close');
					});
				}
				else if(${applyType}==3){
					$.messager.alert('消息', "外包申请成功", 'info', function(){
						<c:if test="${bean.collectionWayCur=='1'}"> window.location.href="<%=basePath%>" + "collectionBase/phone/actionquery.do";</c:if>
						<c:if test="${bean.collectionWayCur=='2'}"> window.location.href="<%=basePath%>" + "collection/visit/detail/query.do";</c:if>	
						//window.location.reload();
						$('#dialogDiv').dialog('close');
					});
				}	
				else{
					$.messager.alert('消息', "司法申请 成功", 'info', function(){
						<c:if test="${bean.collectionWayCur=='1'}"> window.location.href="<%=basePath%>" + "collectionBase/phone/actionquery.do";</c:if>
						<c:if test="${bean.collectionWayCur=='2'}"> window.location.href="<%=basePath%>" + "collection/visit/detail/query.do";</c:if>
						//window.location.reload();
						$('#dialogDiv').dialog('close');
					});
					}
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


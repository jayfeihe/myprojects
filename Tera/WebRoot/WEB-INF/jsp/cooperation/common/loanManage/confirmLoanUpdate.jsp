<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
	<div id="main">
		<div style="width: auto;height: auto">
			<c:if test="${flag ne 'U' }">
				<form id="updateForm">
					<input type="hidden" name="confirmLoanQBean.appNo" value="${bean.appId}"/>
					<input type="hidden" name="confirmLoanQBean.name" value="${bean.name}"/>
					<!-- 确认放款 -->
					<c:if test="${flag eq 'Y' }">
						<table id="confirmLoan">
							<tr>
								<td>申请编号：</td>
								<td><input id="appNo" type="text" disabled="disabled"
										class="textbox" value="${bean.appId}" style="width: auto;" /></td>
								<td >客户姓名：</td>
								<td><input id="name" type="text" disabled="disabled"
										editable="false" class="textbox" value="${bean.name }" /></td>
							</tr>
							<tr>
								<td>放款日期：</td>
								<td colspan="3"><input id="confirmLoanDate" name="confirmLoanQBean.confirmLoanDate" type="text" 
										editable="false" class="textbox easyui-datebox" value="" /></td>
							</tr>
							<tr>
								<td style="text-align: right;">备注：</td>
								<td colspan="3"><textarea id="remark" name="confirmLoanQBean.remark" type="text"
										style="resize: none;width:300px;height:50px!important;" 
										class="textbox" ></textarea></td>
							</tr>
						</table>
					</c:if>
					
					<!-- 拒贷 -->
					<c:if test="${flag eq 'N' }">
						<table id="denyLoan">
							<tr>
								<td>申请编号：</td>
								<td><input id=""appNo"" type="text" disabled="disabled"
										class="textbox" value="${bean.appId }" /></td>
								<td >客户姓名：</td>
								<td><input id="name" type="text" disabled="disabled" 
										class="textbox" value="${bean.name }" /></td>
							</tr>
							<tr>
								<td>拒件码：</td>
								<td colspan="3">
									<input id="decisionCode1" name="confirmLoanQBean.decisionCode1" value="" type="text" editable="false"
								    	data-options="required:true,validType:['length[0,50]']" class="easyui-combobox" />
								    <input id="decisionCode2" name="confirmLoanQBean.decisionCode2" value="" type="text" editable="false"
								   	 	data-options="required:true,validType:['length[0,50]']" class="easyui-combobox" />
								</td>
							</tr>
							<tr>
								<td>备注：</td>
								<td colspan="3"><textarea id="remark" name="confirmLoanQBean.remark" type="text"
										style="resize: none;width:300px;height:50px!important;" 
										class="textbox" ></textarea></td>
							</tr>
						</table>
					</c:if>
				</form>
			</c:if>
			<!-- 附件上传 -->
			<c:if test="${flag eq 'U' }">
				<form id="fileUpload" action="<%=basePath%>cooperation/common/loanManage/fileUpload.do"  enctype="multipart/form-data" >
					<input type="hidden" id="appId" name="appId" value="${bean.appId}" />
					<table style="height: 100px;">
					<tr>
						<td>附件上传：</td>
						<td><input id="file" name="file" type="file" accept="application/x-zip-compressed" /></td>
						<td><input onclick="fileUpload();"  value="上传" type="button" class="btn" /></td>
					</tr>
				</table>
				</form>
			</c:if>
			
		</div>
	</div>
<c:if test="${flag ne 'U' }">	
	<hr/>
	<div align="right">
		<input type="button" value="提交" class="btn" onclick="updateFunction('${flag}','${channelType }')" /> 
		<input type="button" value="取消" id="resetId" class="btn" onclick="$('#dialogDiv').dialog('close');"/>	
	</div>
</c:if>

<script type="text/javascript">
//更新保存
function updateFunction(_flag,channel_type) {
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
		url : "<%=basePath%>" + "cooperation/common/loanManage/save.do?flag="+_flag+"&channelType="+channel_type,
		data : encodeURI(params),
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
						window.location="<%=basePath%>cooperation/common/loanManage/query.do";
				});
			} else {
				$.messager.alert('消息', data.message,'warnning');
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
	
	function fileUpload() {
		$.messager.confirm('消息', "是否确定上传？", function(ok){
            if (ok){
        		$('#fileUpload').submit();
            }
    	});
	}
	
	
	$('#fileUpload').submit(function() {
		openMask();
		// 上传文件 必须用 这个 异步提交
	    $(this).ajaxSubmit({
	    		type : "POST",
	    		contentType:"multipart/form-data",
	    		url : "<%=basePath%>cooperation/common/loanManage/fileUpload.do",
	    	    success:function(data) {
	    	    	data=jQuery.parseJSON(data);
	    			if ("true"==data.success) {
	    				$.messager.alert('消息', data.message,"info",function(){
	    					window.location="<%=basePath%>cooperation/common/loanManage/query.do";
	    				});
	                } else {
	    				$.messager.alert('消息', data.message);
	                }
	    			closeMask();
	    		}
	    });
	    return false;
	});

	//页面加载完动作
	$(document).ready(function() {
		
	});
	
</script>


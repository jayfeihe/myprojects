<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 
 <div id="main">
		<div style="width: auto;height: auto" name="inputInfo">
				<form id="addDataId" name="addDataId" >
					<input id="id" name="id" value="${bean.id}"  type="hidden" 	   class="textbox easyui-validatebox" />
					<input id="appId" name="contractNo" value="${bean.contractNo}" type="hidden"    class="textbox easyui-validatebox" />
					 <table style="text-align: center;">
						<tr>
							<td align="left">接听人关系：</td>
							<td align="left">
								 <input id="relation1" name="relation" type="text" readonly="readonly" class="easyui-combobox"  value="${bean.relation}"   /> 
							</td>
						</tr>
						<tr>
							<td align="left">接听人姓名：</td>
							<td align="left">
								<input id="name" name="name" value="${bean.name}" type="text"  readonly="readonly"  class="textbox easyui-validatebox"  />
							</td>
						</tr>
						<tr>
							<td align="left">接听人号码：</td>
							<td align="left"><input id="mobile" name="mobile"  value="${bean.mobile}" type="text"   readonly="readonly" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"  />
			    			</td>
						</tr>
							
						 
						<tr>
							<td align="left">落地催摘要：</td>
							<td align="left"><input id="phoneSummary1" name="phoneSummary" value="" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"  />
			    			</td>
						</tr>	
						<tr>
								<td align="left">备注：</td>
								<td align="left"><textarea id="remark" name="remark" value="" class="textbox easyui-validatebox" 
								data-options="required:true,validType:['length[0,500]']" style="resize: none;height:50px!important;"  cols="60" ></textarea></td>
						</tr>
					  	<tr>
					  		<td style="width: 100px" colspan="2"><input type="button" class="btn" value="提交"  onclick="saveRecord()"/>
					  		&nbsp;<input type="button" class="btn" value="取消" onclick="goback();"/> 
					  		</td>
    					</tr>
					</table>
				</form>
		</div>
	</div>
	
    
 
<script language="javascript">
function goback(){
	//window.location.reload();
	$('#dialogDiv').dialog('close');
}
function saveRecord(){
	var params = $('#addDataId').serialize();
		$.ajax({
		type : "POST",
		url : "collection/visit/record/saveRecord.do",
		data : params,
		success : function(data) {
			if ("true"==data.success) {
				$.messager.alert('消息', data.message);
				$('#dialogDiv').dialog('close');
				scanRecords();

            } else {				
    			$.messager.alert('消息', data.message);
            }
		}
	}); 
}


//页面加载完动作
 
</script>

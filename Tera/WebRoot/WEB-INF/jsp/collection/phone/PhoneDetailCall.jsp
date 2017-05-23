<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div id="main" >
<div style="width: auto;height: auto" name="inputInfo">
	<form id="addDataId" name="addDataId" >
		 <input id="lateDays_str" name="lateDays_str" type="hidden"    data-options="min:0,precision:0"  class="textbox easyui-numberbox" style="width: 10px" value="${lateDays}"/>
		<input id="contractNo" name="contractNo" type="hidden"   data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${contractNo}"/>
		<input id="index" name="index" type="hidden"    data-options="min:0,precision:0"  class="textbox easyui-numberbox" value="${index}"/>
		<table style="text-align: center;width: 100%" >
			<tr>
				<td align="left">接听人关系:</td>
				
				<td align="left"><input id="answerRelation" name="answerRelation"  type="text" readonly="readonly"  class="textbox easyui-combobox"  value=""/></td>
			</tr>
			<tr>
				<td align="left">接听人姓名:</td>
				<td align="left"> <input id="answerName" name="answerName"  type="text" readonly="readonly"   class="textbox easyui-validatebox"  value=""></input></td>
				
			</tr>
			<tr>
				<td align="left">接听人号码:</td>
				<td align="left"><input id="tel" name="tel"  type="text" readonly="readonly"  class="textbox easyui-validatebox"  value=""></input></td>
				
			</tr>
			<tr>
				<td align="left">电催摘要:</td>
				<td align="left"><input id="phoneSummary" name="phoneSummary" type="text"  data-options="required:true" class="textbox easyui-combobox"   value=""></input></td>
				
			</tr>
			<tr>
				<td align="left">备注:</td>
				<td align="left"><textarea id="remark"  name="remark"　rows="2" data-options="required:true,validType:['length[0,500]']" style="resize: none;height:50px!important;font-size: small" class="textbox easyui-validatebox" cols="52"></textarea></td>
			
			</tr>
			 
			<tr><td colspan="2" ><input type="button" class="btn" style="right: 50px" onclick="updaeRecode()" value="提交"/>
			&nbsp;
			<input type="button" class="btn" onclick="goback()" value="取消"/></td></tr>
		</table>
				
	</form>  
</div>
</div>

<script language="javascript">
function goback(){
	$('#dialogDiv').dialog('close');//window.location.reload();
}
function updaeRecode(){
	openMask();
	var params = $('#addDataId').serialize();
	var	url="collectionRecord/phone/update.do";
	$.ajax( {
				type : "POST",
				url  : url,
				data : params+"&lateDays_str="+$('#lateDays_str').val(),
				dataType : "json",
				success : function(data) {
					closeMask();
					//$('#' + targetDiv).html(data);
					if(data.success=="true"){
					$.messager.alert('消息', '提交成功！','info',function (){$('#dialogDiv').dialog('close');scanRecords();});
					}else{
					$.messager.alert('消息', '提交失败！');
					}
				},
				error : function() {
					closeMask();
					$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
				}
			});
	
}

//页面加载完动作
 
</script>

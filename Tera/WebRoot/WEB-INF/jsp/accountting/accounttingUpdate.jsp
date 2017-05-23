<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div id="main">
	<div class="content">
		<form id="updateForm" >
				<input id="id" name="id" type="hidden" size="35"
					value="${ bean.id}" />
			<table>
				<tbody>
					<tr>
						<td>合同号:</td>
						<td><input id="contractNo" name="contractNo" type="text" disabled="disabled"
							data-options="required:true,validType:['length[0,30]']"
							class="textbox easyui-validatebox" value="${bean.contractNo}" /></td>
						<td>收付:</td>
						<td>
							<input id="inOut" name="inOut" type="text" disabled="disabled"
							 	class="textbox easyui-combobox" value="${bean.inOut }"
								data-options="textField:'text',
											  valueField:'value',
											  data:[{'text':'请选择','value':''},
											        {'text':'收','value':'1'},
											        {'text':'付','value':'2'}],
											  panelHeight:'auto',
											  editable:false" />
						</td>
					</tr>
					<tr>
						<td>账户:</td>
						<td><input id="account" name="account" type="text" disabled="disabled"
							data-options="validType:['length[0,30]']"
							class="textbox easyui-validatebox" value="${bean.account}" /></td>
						<td>科目:</td>
						<td><input id="subject" name="subject" type="text" disabled="disabled"
							data-options="validType:['length[0,20]']"
							class="textbox easyui-validatebox" value="${bean.subject}" /></td>
					</tr>
					<tr>
						<td>实际金额:</td>
						<td><input id="actualAmount" name="actualAmount" type="text" disabled="disabled"
							editable="false" data-options="min:0,precision:2"
							class="textbox easyui-numberbox" value="${bean.actualAmount}" />元</td>
					</tr>
					<tr>
						<td>期数:</td>
						<td><input id="periodNum" name="periodNum" type="text" disabled="disabled"
							editable="false" data-options="min:0,precision:0"
							class="textbox easyui-numberbox" value="${bean.periodNum}" /></td>
						<td>日期:</td>
						<td><input id="createTime" name="createTime" type="text" disabled="disabled"
							editable="false" class="textbox"
							value="${bean.createTimeStr}" /></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</div>
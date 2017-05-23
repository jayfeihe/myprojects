<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="tab_show_processReport" style="display: none;" title="流程报告" ></div>
<div id="processReportDiv" class="easyui-accordion" data-options="multiple:false"  data-options="fit:true">
<table width="100%" id="processReportTable">
	<tr>
		<td colspan="2">进件时间:
			<input id="inputTime" name="inputTime" type="text" data-options="validType:['length[0,50]']" 
			class="textbox easyui-datetimebox" value="${bean.inputTime}" 
			readonly="readonly" style="background-color: #F0F0F0;" disabled="disabled"/>
		</td>
	</tr>
	<c:forEach items="${bpmLogList }" var="data" varStatus="status">
		<tr>
			<td colspan="6">
				<div><hr width="100%" size=2 color="#D3D3D3" noshade></div>
			</td>
		</tr>
		<tr>
			<td><strong>第${status.index + 1}环节:</strong></td>
			<td><input id="state" name="state" type="text" data-options="validType:['length[0,50]']"
			class="textbox easyui-validatebox" value="${data.state}" 
			readonly="readonly" style="background-color: #F0F0F0;"/></td>
			<c:if test="${data.operatorName != '' && not empty data.operatorName}">
				<td>决策人:</td>
				<td><input id="operatorName" name="operatorName" type="text" data-options="validType:['length[0,50]']"
				class="textbox easyui-validatebox" value="${data.operatorName}" 
				readonly="readonly" style="background-color: #F0F0F0;"/></td>
			</c:if>
			<td>决策时间:</td>
			<td><input id="outtime" name="outtime" type="text" data-options="validType:['length[0,50]']"
			class="textbox easyui-datetimebox" value="${data.outtime}" 
			readonly="readonly" style="background-color: #F0F0F0;" disabled="disabled"/></td>
		</tr>
		<c:if test="${data.logContent1 != '' && not empty data.logContent1}">
			<tr>
				<td>决策结果:</td>
				<td><input id="logContent1" name="logContent1" type="text" data-options="validType:['length[0,50]']"
				class="textbox easyui-validatebox" value="${data.logContent1}" 
				readonly="readonly" style="background-color: #F0F0F0;"/></td>
				<c:if test="${data.state eq '拒件' }">
					<td>拒贷码:</td>
					<td>
						<input type="text" class="textbox" value="${data.logContent3}"
							readonly="readonly" style="background-color: #F0F0F0;" />
					</td>
				</c:if>
				<!-- 退回 -->
				<c:if test="${data.logContent3 == '退回码'}">
					<td>退回码：</td>
					<td colspan="3">
						<input id="returnCode01" name="returnCode01[${status.index}]" type="text" 
						data-options="valueField:'keyProp',textField:'keyValue',
						url:'sys/datadictionary/listjason.do?keyName=returnCode01'"
						class="easyui-combobox" value="${code1list[status.index]}" 
						readonly="readonly" style="width:170px;background-color: #F0F0F0;" disabled="disabled"/>
						<input id="returnCode02" name="returnCode02[${status.index}]" type="text" 
						data-options="valueField:'keyProp',textField:'keyValue',
						url:'sys/datadictionary/listjason.do?keyName=returnCode02'"
						class="easyui-combobox" value="${code2list[status.index]}" 
						readonly="readonly" style="width:230px;background-color: #F0F0F0;" disabled="disabled"/>
					</td>
				</c:if>
				<!-- 审批拒贷 -->
				<c:if test="${data.logContent3 == '拒贷码' && data.logContent1 == '拒贷'}">
					<td>拒贷码：</td>
					<td colspan="3">
						<input id="refusedCode01" name="refusedCode01" type="text" 
						data-options="valueField:'keyProp',textField:'keyValue',
						url:'sys/datadictionary/listjason.do?keyName=refusedCode01'"
						class="easyui-combobox" value="${code1list[status.index]}" 
						readonly="readonly" style="width:170px;background-color: #F0F0F0;" disabled="disabled"/>
						<input id="refusedCode02" name="refusedCode02" type="text" 
						data-options="valueField:'keyProp',textField:'keyValue',
						url:'sys/datadictionary/listjason.do?keyName=refusedCode02'"
						class="easyui-combobox" value="${code2list[status.index]}" 
						readonly="readonly" style="width:230px;background-color: #F0F0F0;" disabled="disabled"/>
					</td>
				</c:if>
				<!-- 前端拒贷 -->
				<c:if test="${data.logContent3 == '拒贷码' && data.logContent1 == '客户放弃'}">
					<td>前端拒贷码：</td>
					<td colspan="3">
						<input id="saleRefuseCode01" name="saleRefuseCode01" type="text" 
						data-options="valueField:'keyProp',textField:'keyValue',
						url:'sys/datadictionary/listjason.do?keyName=saleRefuseCode01'"
						class="easyui-combobox" value="${code1list[status.index]}" 
						readonly="readonly" style="width:170px;background-color: #F0F0F0;" disabled="disabled"/>
						<input id="saleRefuseCode02" name="saleRefuseCode02" type="text" 
						data-options="valueField:'keyProp',textField:'keyValue',
						url:'sys/datadictionary/listjason.do?keyName=saleRefuseCode02'"
						class="easyui-combobox" value="${code2list[status.index]}" 
						readonly="readonly" style="width:230px;background-color: #F0F0F0;" disabled="disabled"/>
					</td>
				</c:if>
			</tr>
		</c:if>
		<c:if test="${data.logContent2 != '' && not empty data.logContent2}">
			<tr>
				<td>决策原因:</td>
				<td colspan="5">
					<textarea id="logContent2" name="logContent2"  value="" class="textbox easyui-validatebox" data-options="validType:['length[0,500]']" 
					readonly="readonly" style="resize: none;width:625px;height:50px!important;background-color: #F0F0F0;">${data.logContent2}</textarea>
				</td>
			</tr>
		</c:if>
		<c:if test="${data.logContent5 != '' && not empty data.logContent5}">
			<tr>
				<td>备注:</td>
				<td colspan="5">
					<textarea id="logContent5" name="logContent5"  value="" class="textbox easyui-validatebox" data-options="validType:['length[0,500]']" 
					readonly="readonly" style="resize: none;width:625px;height:50px!important;background-color: #F0F0F0;">${data.logContent5}</textarea>
				</td>
			</tr>
		</c:if>
	</c:forEach>
</table>
</div>
<script type="text/javascript">
$.parser.parse($("#processReportDiv"));

$(document).ready(function(){

});
 </script>
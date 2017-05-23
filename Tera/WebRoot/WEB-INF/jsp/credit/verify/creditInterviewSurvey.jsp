<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="tab_show_interview" style="display: none;" title="面审调查" ></div>

	<div class="easyui-accordion" id="intervewDiv" data-options="multiple:false,width:'100%',fit:true" style="">
		<div title="添加" style="moverflow:auto;padding:10px;" data-options="collapsed:false,collapsible:false">
		<input type="hidden" id="type01Index" value="${fn:length(type01InterviewList)}"/>
		<input type="hidden" id="type02Index" value="${fn:length(type02InterviewList)}"/>
		<input type="hidden" id="type03Index" value="${fn:length(type03InterviewList)}"/>
		<input type="hidden" id="type04Index" value="${fn:length(type04InterviewList)}"/>
		<input type="hidden" id="type05Index" value="${fn:length(type05InterviewList)}"/>
			<form id="interviewAddForm">
			<table width="100%" id="interviewAddDiv">
				<tr>
					<td>类型:</td>
					<td>
						<input id="type" class="textbox easyui-combobox easyui-validatebox" editable="false"/>
					</td>
					<td>关系:</td>
					<td>
						<input id="relation" class="textbox easyui-combobox easyui-validatebox" editable="false" />
					</td>
					<td>来源:</td>
					<td>
						<input id="source" type="text" editable="false"
							class="textbox easyui-combobox easyui-validatebox"/>
					</td>
				</tr>
				<tr>
					<td>名字:</td>
					<td>
						<input id="name" type="text" data-options="validType:['length[0,30]']" 
							class="textbox easyui-validatebox"/>
					</td>
					<td>号码:</td>
					<td>
						<input id="phone" type="text" data-options="validType:['length[0,50]']" 
							class="textbox easyui-validatebox"/>
					</td>
					<td colspan="6">
						<input type="button" value="添加" class="btn" onclick="addInterview()"/>
					</td>
				</tr>
			</table>
			</form>
<script type="text/javascript">
//添加面审调查
function addInterview(){
	//添加 选项页 ID
	var interviewAddDiv=$("#intervewDiv").find("#interviewAddDiv");

	var type = interviewAddDiv.find("#type").combobox("getValue");	//类型
	var relation = interviewAddDiv.find("#relation").combobox("getValue"); // 关系
	var source = interviewAddDiv.find("#source").combobox("getValue"); // 来源
	var name=interviewAddDiv.find("#name").val();	//名字
	var phone=interviewAddDiv.find("#phone").val();	//号码
	
	if(type==null||type==""){
		$.messager.alert('消息', "请选择类型","info");
		return;
	}
	if(relation==null||relation==""){
		$.messager.alert('消息', "请选择关系","info");
		return;
	}
	if(source==null||source==""){
		$.messager.alert('消息', "请选择来源","info");
		return;
	}
	if(name==null||name==""){
		$.messager.alert('消息', "请输入名字","info");
		return;
	}
	if(phone==null||phone==""){
		$.messager.alert('消息', "请输入号码","info");
		return;
	}
	var typetext = interviewAddDiv.find("#type").combobox("getText");
	var title=typetext+' '+name+':'+phone+'(添)——未调查(正常)';
	var html=$("#type"+type+"_textarea").val();		//
	var indexKey="type"+type+"Index";
	var index=$("#"+indexKey).val();		//
	
	html=html.replace(eval('/type_what_index/gm'),index);
	html=html.replace(eval('/interview_relation/gm'),relation);
	html=html.replace(eval('/interview_source/gm'),source);
	html=html.replace(eval('/interview_name/gm'),name);
	html=html.replace(eval('/interview_phone/gm'),phone);
	
	$('#intervewDiv').accordion('add', {
		title: title,
		content: html,
		selected: true,
		closable: true
	});
	$("#"+indexKey).val(++index);
	
	var typevalue = "";
	var href = phone;
	var hreftext = phone;
	if("01" == type || "04" == type || "05" == type){
		typevalue = name;
	}
	var wckey = ""
	if("01" == type){
		wckey = "7";
	}
	if("02" == type){
		wckey = "9";
	}
	if("03" == type){
		wckey = "2";
	}
	if("04" == type){
		wckey = "8";
	}
	if("05" == type){
		wckey = "10";
	}
	
	
	addWcInfo("type5Exts",typevalue,typetext+"：",href,hreftext,wckey);
	$('#interviewAddForm').form('clear');
}

//添加网查对象
function addWcInfo(key,typevalue,typetext,href,hreftext,wckey){
	var html=$("#"+key+"_textarea").val();		
	var keyIndex=key+"Index";
	var index=$("#"+keyIndex).val();
	
	html=html.replace(eval('/'+keyIndex+'/gm'),index);
	var newDriver=$(html);
	newDriver.find("#typeText").text(typetext);
	newDriver.find("span").text(typevalue);
	newDriver.find("a").attr("href",href);
	newDriver.find("a").text(hreftext);
	newDriver.find("#remark").append("<textarea id=\"remarks\" name=\"type5Exts["+index+"].remarks\" value=\"${contact.remarks}\" class=\"textbox easyui-validatebox\" data-options=\"validType:['length[0,500]']\" style=\"resize: none;width:200px;height:50px!important;\"></textarea>");
	newDriver.appendTo("#wc");				
	$.parser.parse(newDriver);
	$("#wc").find("input[name='"+key+"["+index+"].name']").val(typevalue+":"+href);
	$("#wc").find("input[name='"+key+"["+index+"].key']").val(wckey);
	$("#"+keyIndex).val(++index);
}

//删除已添加的面审
function rmInterview(thisObj){
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){
			var obj=$(thisObj).closest('.panel');
			var state=true;
			obj.find('.easyui-validatebox').validatebox({novalidate:state});
			obj.find('.easyui-combobox').combobox({novalidate:state});
			obj.find('.easyui-numberbox').validatebox({novalidate:state});
			obj.find('.easyui-datebox').datebox({novalidate:state});
			obj.find('#state').val("0");
			obj.hide();
		}
	});
}
</script>
		</div>
		
<c:forEach items="${type03InterviewList}" var="interview" varStatus="status">
	<div title="${interview.pateTitle}" style="overflow:auto;padding:10px;" 
	<c:if test="${interview.isAdd==1}">
	data-options="tools:[{iconCls:'icon-cancel',handler:function(){rmInterview(this);}}]"</c:if>>
		<table style="" width="100%" id="type03Interview_${status.index}" >
			<tr style="display: none;">
				<td colspan="6">
					<input name="type03InterviewList[${status.index}].id" type="hidden"  value="${interview.id}"/>
					<input id="state" name="type03InterviewList[${status.index}].state" type="hidden"  value="1"/>
 					<input name="type03InterviewList[${status.index}].appId" type="hidden" value="${bean.appId}"/> 
 					<input title="是否新增" type="hidden" name="type03InterviewList[${status.index}].isAdd" value="${interview.isAdd}"/> 
 					<input title="类型" name="type03InterviewList[${status.index}].type" type="hidden" value="${interview.type}"/> 
 					<input title="关系" name="type03InterviewList[${status.index}].relation" type="hidden" value="${interview.relation}"/> 
 					<input title="来源" name="type03InterviewList[${status.index}].source" type="hidden" value="${interview.source}"/> 
 					<input title="名字" name="type03InterviewList[${status.index}].name" type="hidden"  value="${interview.name}"/> 
 					<input title="号码" name="type03InterviewList[${status.index}].phone" type="hidden" value="${interview.phone}"/> 
				</td>
			</tr>
			<tr>
				<td>调查状态:</td>
				<td><input id="surveyState" name="type03InterviewList[${status.index}].surveyState" type="text" editable="false"
				data-options="required:true,valueField:'keyProp', 
								textField:'textField',
								onHidePanel:upInterviewTitile,
								data:[{keyProp:'01',textField:'未调查'}
								,{keyProp:'02',textField:'调查中'}
								,{keyProp:'03',textField:'调查成功'}
								,{keyProp:'04',textField:'调查失败'}]" 
				class="textbox easyui-combobox" value="${interview.surveyState}"/></td>
				<td>调查标志:</td>
				<td><input id="surveyFlag" name="type03InterviewList[${status.index}].surveyFlag" type="text" editable="false"
				data-options="required:true,valueField:'keyProp', 
								textField:'textField',
								onHidePanel:upInterviewTitile,
								data:[{keyProp:'01',textField:'正常'}
								,{keyProp:'02',textField:'异常'}]" 
				class="textbox easyui-combobox" value="${interview.surveyFlag}"/></td>
			</tr>
			<tr>
				<td>单位名称是否一致:</td>
				<td colspan="3"><input id="comName" name="type03InterviewList[${status.index}].comName" type="text" 
				data-options="required:true,validType:['length[0,500]']" class="textbox easyui-validatebox" 
				value="${interview.comName}"/>
<%--				</td><td>--%>
					<input id="comNameFlag" name="type03InterviewList[${status.index}].comNameFlag" type="text" editable="false" 
						data-options="required:true,valueField:'keyProp', 
								textField:'textField',
								data:[{keyProp:'0',textField:'不一致'}
								,{keyProp:'1',textField:'一致'}]" 
						class="textbox easyui-combobox" value="${interview.comNameFlag}"/>
				</td>
			</tr>
			<tr>
				<td>单位地址是否一致:</td>
				<td colspan="4"><input id="comAddress" name="type03InterviewList[${status.index}].comAddress" type="text" 
				data-options="required:true,validType:['length[0,500]']" class="textbox easyui-validatebox" style="width:304px;"
				value="${interview.comAddress}"/>
<%--				</td><td>--%>
					<input id="comAddressFlag" name="type03InterviewList[${status.index}].comAddressFlag" type="text" editable="false"
						data-options="required:true,valueField:'keyProp', 
								textField:'textField',
								data:[{keyProp:'0',textField:'不一致'}
								,{keyProp:'1',textField:'一致'}]" 
						class="textbox easyui-combobox" value="${interview.comAddressFlag}"/>
				</td>
			</tr>
			<tr>
				<td>客户是否在职:</td>
				<td>
					<input id="isJob" name="type03InterviewList[${status.index}].isJob" type="text" editable="false" 
						data-options="required:true,valueField:'keyProp', 
								textField:'textField',
								data:[{keyProp:'0',textField:'否'}
								,{keyProp:'1',textField:'是'}]" 
						class="textbox easyui-combobox" value="${interview.isJob}"/>
				</td>
			</tr>
			<tr>
				<td colspan="6">
					<div style="font-size: 14px;" >
						<strong>拨打记录</strong>
						<a href="javascript:addcallLog('type03','${status.index}')" >
						拨打</a>
						<input id="callLongIndex" type="hidden"
						value="${fn:length(interview.callLogList)}" />
						<hr width="100%" size=2 color="#D3D3D3" noshade>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="6">
					<table class="datatable" id="callLongList">
						<tr>
							<th scope="col" width="125px">拨打时间</th>
							<th scope="col" width="70px">接听关系</th>
							<th scope="col" width="70px">接听状态</th>
							<th scope="col" width="400px">备注</th>
						</tr>
<c:forEach items="${interview.callLogList}" var="callLog" varStatus="callLogStatus">
	<tr id="callLong_${callLogStatus.index}">
		<td>
			<input id="id" name="type03InterviewList[${status.index}].callLogList[${callLogStatus.index}].id" type="hidden"  value="${callLog.id}"/>
			<input id="state" name="type03InterviewList[${status.index}].callLogList[${callLogStatus.index}].state" type="hidden"  value="${callLog.state}"/>
			${callLog.callDateStr}
		</td>
		<td>
			<input id="onRelation" name="type03InterviewList[${status.index}].callLogList[${callLogStatus.index}].onRelation" 
			class="textbox easyui-combobox" editable="false" value="${callLog.onRelation}" style="width: 70px;"
			data-options="required:true,valueField:'keyProp', 
						textField:'textField',
						onHidePanel:upInterviewTitile,
						data:[
						 {keyProp:'01',textField:'本人'}
						,{keyProp:'02',textField:'父亲'} 
						,{keyProp:'03',textField:'母亲'}
						,{keyProp:'04',textField:'配偶'}
						,{keyProp:'05',textField:'子女'}
						,{keyProp:'06',textField:'亲属'}
						,{keyProp:'07',textField:'朋友'}
						,{keyProp:'08',textField:'同事'}
						,{keyProp:'09',textField:'同学'}
						,{keyProp:'99',textField:'其他'}]"/>
		</td>
		<td>
			<input id="onState" name="type03InterviewList[${status.index}].callLogList[${callLogStatus.index}].onState" 
			class="textbox easyui-combobox" editable="false" value="${callLog.onState}" style="width: 70px;"
			data-options="required:true,valueField:'keyProp', 
						textField:'textField',
						onHidePanel:upInterviewTitile,
						data:[
						 {keyProp:'01',textField:'已接听'}
						,{keyProp:'02',textField:'未接听'}
						,{keyProp:'03',textField:'无法接通'}]"/>
		</td>
		<td>
<%--			<input id="remarks" name="type03InterviewList[${status.index}].callLogList[${callLogStatus.index}].remarks" --%>
<%--				class="textbox easyui-validatebox" data-options="validType:['length[0,500]']" style="width: 400px;"--%>
<%--				value="${callLog.remarks}"/>--%>
			<textarea id="remarks" name="type03InterviewList[${status.index}].callLogList[${callLogStatus.index}].remarks"  
				class="textbox easyui-validatebox" style="resize: none;width:400px;height:50px !important;" data-options="validType:['length[0,500]']"
				value="${callLog.remarks}" >${callLog.remarks}</textarea>
		</td>
	</tr>
</c:forEach>
					</table>
				</td>
			</tr>
		</table>
	</div>
</c:forEach>
<%--结束--%>
<%--常用联系人开始--%>
<%--		<div title="常用联系人" style="overflow:auto;padding:10px;" data-options="">--%>

<c:forEach items="${type04InterviewList}" var="interview" varStatus="status">
	<div title="${interview.pateTitle}" id="type04Interview_${status.index}" style="overflow:auto;padding:10px;" 
	<c:if test="${interview.isAdd==1}">data-options="tools:[{iconCls:'icon-cancel',handler:function(){rmInterview(this);}}]"</c:if> >
<table style="" width="100%" >
			<tr style="display: none;">
				<td colspan="6">
					<input name="type04InterviewList[${status.index}].id" type="hidden" value="${interview.id}"/>
					<input id="state" name="type04InterviewList[${status.index}].state" type="hidden"  value="1"/>
					<input name="type04InterviewList[${status.index}].appId" type="hidden" value="${bean.appId}"/>
					<input title="是否新增" type="hidden" name="type04InterviewList[${status.index}].isAdd" value="${interview.isAdd}"/>
					<input title="类型" name="type04InterviewList[${status.index}].type" type="hidden" value="${interview.type}"/>
					<input title="关系" name="type04InterviewList[${status.index}].relation" type="hidden" value="${interview.relation}"/>
					<input title="来源" name="type04InterviewList[${status.index}].source" type="hidden" value="${interview.source}"/>
					<input title="名字" name="type04InterviewList[${status.index}].name" type="hidden"  value="${interview.name}"/>
					<input title="号码" name="type04InterviewList[${status.index}].phone" type="hidden" value="${interview.phone}"/>
				</td>
			</tr>
			<tr>
				<td>调查状态:</td>
				<td><input id="surveyState" name="type04InterviewList[${status.index}].surveyState" type="text" editable="false"
				data-options="required:true,valueField:'keyProp', 
								textField:'textField',
								onHidePanel:upInterviewTitile,
								data:[{keyProp:'01',textField:'未调查'}
								,{keyProp:'02',textField:'调查中'}
								,{keyProp:'03',textField:'调查成功'}
								,{keyProp:'04',textField:'调查失败'}]" 
				class="textbox easyui-combobox" value="${interview.surveyState}"/></td>
				<td>调查标志:</td>
				<td><input id="surveyFlag" name="type04InterviewList[${status.index}].surveyFlag" type="text" editable="false" 
				data-options="required:true,valueField:'keyProp', 
								textField:'textField',
								onHidePanel:upInterviewTitile,
								data:[{keyProp:'01',textField:'正常'}
								,{keyProp:'02',textField:'异常'}]" 
				class="textbox easyui-combobox" value="${interview.surveyFlag}"/></td>
			</tr>
			<tr>
				<td>居住地址是否一致:</td>
				<td colspan="4"><input id="address" name="type04InterviewList[${status.index}].address" type="text" 
				data-options="required:true,validType:['length[0,500]']" class="textbox easyui-validatebox" style="width:304px;" 
				value="${interview.address}"/>
<%--				</td><td>--%>
					<input id="addressFlag" name="type04InterviewList[${status.index}].addressFlag" type="text" editable="false" 
						data-options="required:true,valueField:'keyProp', 
								textField:'textField',
								data:[{keyProp:'0',textField:'不一致'}
								,{keyProp:'1',textField:'一致'}]" 
						class="textbox easyui-combobox" value="${interview.addressFlag}"/>
				</td>
			</tr>
			<tr>
				<td colspan="6">
					<div style="font-size: 14px;" >
						<strong>拨打记录</strong>
						<a href="javascript:addcallLog('type04','${status.index}')" >
						拨打</a>
						<input id="callLongIndex" type="hidden"  
						value="${fn:length(interview.callLogList)}" />
						<hr width="100%" size=2 color="#D3D3D3" noshade>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="6">
					<table class="datatable" id="callLongList">
						<tr>
							<th scope="col" width="125px">拨打时间</th>
							<th scope="col" width="70px">接听关系</th>
							<th scope="col" width="70px">接听状态</th>
							<th scope="col" width="400px">备注</th>
						</tr>
<c:forEach items="${interview.callLogList}" var="callLog" varStatus="callLogStatus">
	<tr id="callLong_${callLogStatus.index}">
		<td>
			<input id="id" name="type04InterviewList[${status.index}].callLogList[${callLogStatus.index}].id" type="hidden"  value="${callLog.id}"/>
			<input id="state" name="type04InterviewList[${status.index}].callLogList[${callLogStatus.index}].state" type="hidden"  value="${callLog.state}"/>
			${callLog.callDateStr}
		</td>
		<td>
			<input id="onRelation" name="type04InterviewList[${status.index}].callLogList[${callLogStatus.index}].onRelation" 
			class="textbox easyui-combobox" value="${callLog.onRelation}" editable="false" style="width: 70px;"
			data-options="required:true,valueField:'keyProp', 
						textField:'textField',
						onHidePanel:upInterviewTitile,
						data:[
						 {keyProp:'01',textField:'本人'}
						,{keyProp:'02',textField:'父亲'}
						,{keyProp:'03',textField:'母亲'}
						,{keyProp:'04',textField:'配偶'}
						,{keyProp:'05',textField:'子女'}
						,{keyProp:'06',textField:'亲属'}
						,{keyProp:'07',textField:'朋友'}
						,{keyProp:'08',textField:'同事'}
						,{keyProp:'09',textField:'同学'}
						,{keyProp:'99',textField:'其他'}]"/>
		</td>
		<td>
			<input id="onState" name="type04InterviewList[${status.index}].callLogList[${callLogStatus.index}].onState" 
			class="textbox easyui-combobox" value="${callLog.onState}" editable="false" style="width: 70px;"
			data-options="required:true,valueField:'keyProp', 
						textField:'textField',
						onHidePanel:upInterviewTitile,
						data:[
						 {keyProp:'01',textField:'已接听'}
						,{keyProp:'02',textField:'未接听'}
						,{keyProp:'03',textField:'无法接通'}]"/>
		</td>
		<td>
<%--			<input id="remarks" name="type04InterviewList[${status.index}].callLogList[${callLogStatus.index}].remarks" --%>
<%--				class="textbox easyui-validatebox" data-options="validType:['length[0,500]']"--%>
<%--				value="${callLog.remarks}"/>--%>
			<textarea id="remarks" name="type04InterviewList[${status.index}].callLogList[${callLogStatus.index}].remarks"  
				class="textbox easyui-validatebox" style="resize: none;width:400px;height:50px !important;" data-options="validType:['length[0,500]']"
				value="${callLog.remarks}" >${callLog.remarks}</textarea>
		</td>
	</tr>
</c:forEach>
					</table>
				</td>
			</tr>
		</table>
	</div>
</c:forEach>	
<%--		</div>--%>
<%--常用联系人结束--%>
<%--家庭固话开始--%>
<%--		<div title="家庭固话" style="overflow:auto;padding:10px;" data-options="">--%>
<c:forEach items="${type02InterviewList}" var="interview" varStatus="status">
	<div title="${interview.pateTitle}" id="type02Interview_${status.index}" style="overflow:auto;padding:10px;" <c:if test="${interview.isAdd==1}">data-options="tools:[{iconCls:'icon-cancel',handler:function(){rmInterview(this);}}]"</c:if>>
		<table style="" width="100%" >
			<tr style="display: none;">
				<td colspan="6">
					<input name="type02InterviewList[${status.index}].id" type="hidden" value="${interview.id}"/>
					<input id="state" name="type02InterviewList[${status.index}].state" type="hidden"  value="1"/>
					<input name="type02InterviewList[${status.index}].appId" type="hidden" value="${bean.appId}"/>
					<input title="是否新增" type="hidden" name="type02InterviewList[${status.index}].isAdd" value="${interview.isAdd}"/>
					<input title="类型" name="type02InterviewList[${status.index}].type" type="hidden" value="${interview.type}"/>
					<input title="关系" name="type02InterviewList[${status.index}].relation" type="hidden" value="${interview.relation}"/>
					<input title="来源" name="type02InterviewList[${status.index}].source" type="hidden" value="${interview.source}"/>
					<input title="名字" name="type02InterviewList[${status.index}].name" type="hidden"  value="${interview.name}"/>
					<input title="号码" name="type02InterviewList[${status.index}].phone" type="hidden" value="${interview.phone}"/>
				</td>
			</tr>
			<tr>
				<td>调查状态:</td>
				<td><input id="surveyState" name="type02InterviewList[${status.index}].surveyState" type="text" editable="false" 
				data-options="required:true,valueField:'keyProp', 
								textField:'textField',
								onHidePanel:upInterviewTitile,
								data:[{keyProp:'01',textField:'未调查'}
								,{keyProp:'02',textField:'调查中'}
								,{keyProp:'03',textField:'调查成功'}
								,{keyProp:'04',textField:'调查失败'}]" 
				class="textbox easyui-combobox" value="${interview.surveyState}"/></td>
				<td>调查标志:</td>
				<td><input id="surveyFlag" name="type02InterviewList[${status.index}].surveyFlag" type="text" editable="false" 
				data-options="required:true,valueField:'keyProp', 
								textField:'textField',
								onHidePanel:upInterviewTitile,
								data:[{keyProp:'01',textField:'正常'}
								,{keyProp:'02',textField:'异常'}]" 
				class="textbox easyui-combobox" value="${interview.surveyFlag}"/></td>
			</tr>
			<tr>
				<td>居住地址是否一致:</td>
				<td colspan="4"><input id="address" name="type02InterviewList[${status.index}].address" type="text" 
				data-options="required:true,validType:['length[0,500]']" class="textbox easyui-validatebox" style="width:304px;"
				value="${interview.address}"/>
<%--				</td><td>--%>
					<input id="addressFlag" name="type02InterviewList[${status.index}].addressFlag" type="text" editable="false" 
						data-options="valueField:'keyProp', 
								textField:'textField',
								data:[{keyProp:'0',textField:'不一致'}
								,{keyProp:'1',textField:'一致'}]" 
						class="textbox easyui-combobox" value="${interview.addressFlag}"/>
				</td>
			</tr>
			<tr>
				<td colspan="6">
					<div style="font-size: 14px;" >
						<strong>拨打记录</strong>
						<a href="javascript:addcallLog('type02','${status.index}')" >
						拨打</a>
						<input id="callLongIndex" type="hidden"  
						value="${fn:length(interview.callLogList)}" />
						<hr width="100%" size=2 color="#D3D3D3" noshade>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="6">
					<table class="datatable" id="callLongList">
						<tr>
							<th scope="col" width="125px">拨打时间</th>
							<th scope="col" width="70px">接听关系</th>
							<th scope="col" width="70px">接听状态</th>
							<th scope="col" width="400px">备注</th>
						</tr>
<c:forEach items="${interview.callLogList}" var="callLog" varStatus="callLogStatus">
	<tr id="callLong_${callLogStatus.index}">
		<td>
			<input id="id" name="type02InterviewList[${status.index}].callLogList[${callLogStatus.index}].id" type="hidden"  value="${callLog.id}"/>
			<input id="state" name="type02InterviewList[${status.index}].callLogList[${callLogStatus.index}].state" type="hidden"  value="${callLog.state}"/>
			${callLog.callDateStr}
		</td>
		<td>
			<input id="onRelation" name="type02InterviewList[${status.index}].callLogList[${callLogStatus.index}].onRelation" 
			class="textbox easyui-combobox" editable="false" value="${callLog.onRelation}"  style="width: 70px;"
			data-options="required:true,valueField:'keyProp', 
						textField:'textField',
						onHidePanel:upInterviewTitile,
						data:[
						 {keyProp:'01',textField:'本人'}
						,{keyProp:'02',textField:'父亲'}
						,{keyProp:'03',textField:'母亲'}
						,{keyProp:'04',textField:'配偶'}
						,{keyProp:'05',textField:'子女'}
						,{keyProp:'06',textField:'亲属'}
						,{keyProp:'07',textField:'朋友'}
						,{keyProp:'08',textField:'同事'}
						,{keyProp:'09',textField:'同学'}
						,{keyProp:'99',textField:'其他'}]"/>
		</td>
		<td>
			<input id="onState" name="type02InterviewList[${status.index}].callLogList[${callLogStatus.index}].onState" 
			class="textbox easyui-combobox" editable="false" value="${callLog.onState}" style="width: 70px;"
			data-options="required:true,valueField:'keyProp', 
						textField:'textField',
						onHidePanel:upInterviewTitile,
						data:[
						 {keyProp:'01',textField:'已接听'}
						,{keyProp:'02',textField:'未接听'}
						,{keyProp:'03',textField:'无法接通'}]"/>
		</td>
		<td>
<%--			<input id="remarks" name="type02InterviewList[${status.index}].callLogList[${callLogStatus.index}].remarks" --%>
<%--				class="textbox easyui-validatebox" data-options="validType:['length[0,500]']"--%>
<%--				value="${callLog.remarks}"/>--%>
			<textarea id="remarks" name="type02InterviewList[${status.index}].callLogList[${callLogStatus.index}].remarks"  
				class="textbox easyui-validatebox" style="resize: none;width:400px;height:50px !important;" data-options="validType:['length[0,500]']"
				value="${callLog.remarks}" >${callLog.remarks}</textarea>
		</td>
	</tr>
</c:forEach>
					</table>
				</td>
			</tr>
		</table>
	</div>
</c:forEach>
<%--		</div>--%>
<%--家庭固话结束--%>
<%--本人手机开始--%>
<%--		<div title="本人手机" style="overflow:auto;padding:10px;" data-options="">--%>
<c:forEach items="${type01InterviewList}" var="interview" varStatus="status">
	<div title="${interview.pateTitle}" id="type01Interview_${status.index}" style="overflow:auto;padding:10px;"
		<c:if test="${interview.isAdd==1}">data-options="tools:[{iconCls:'icon-cancel',handler:function(){rmInterview(this);}}]"</c:if>>
		<table style="" width="100%" >
			<tr style="display: none;">
				<td colspan="6">
					<input name="type01InterviewList[${status.index}].id" type="hidden" value="${interview.id}"/>
					<input id="state" name="type01InterviewList[${status.index}].state" type="hidden"  value="1"/>
					<input name="type01InterviewList[${status.index}].appId" type="hidden" value="${bean.appId}"/>
					<input title="是否新增" type="hidden" name="type01InterviewList[${status.index}].isAdd" value="${interview.isAdd}"/>
					<input title="类型" name="type01InterviewList[${status.index}].type" type="hidden" value="${interview.type}"/>
					<input title="关系" name="type01InterviewList[${status.index}].relation" type="hidden" value="${interview.relation}"/>
					<input title="来源" name="type01InterviewList[${status.index}].source" type="hidden" value="${interview.source}"/>
					<input title="名字" name="type01InterviewList[${status.index}].name" type="hidden"  value="${interview.name}"/>
					<input title="号码" name="type01InterviewList[${status.index}].phone" type="hidden" value="${interview.phone}"/>
				</td>
			</tr>
			<tr>
				<td>调查状态:</td>
				<td><input id="surveyState" name="type01InterviewList[${status.index}].surveyState" type="text" editable="false" 
				data-options="required:true,valueField:'keyProp', 
								textField:'textField',
								onHidePanel:upInterviewTitile,
								data:[{keyProp:'01',textField:'未调查'}
								,{keyProp:'02',textField:'调查中'}
								,{keyProp:'03',textField:'调查成功'}
								,{keyProp:'04',textField:'调查失败'}]" 
				class="textbox easyui-combobox" value="${interview.surveyState}"/></td>
				<td colspan="3">调查标志:
				<input id="surveyFlag" name="type01InterviewList[${status.index}].surveyFlag" type="text" editable="false" 
				data-options="required:true,valueField:'keyProp', 
								textField:'textField',
								onHidePanel:upInterviewTitile,
								data:[{keyProp:'01',textField:'正常'}
								,{keyProp:'02',textField:'异常'}]" 
				class="textbox easyui-combobox" value="${interview.surveyFlag}"/></td>
			</tr>
			<tr>
<%--				<td>期限:</td>--%>
<%--				<td><input id="period" name="type01InterviewList[${status.index}].period" type="text" editable="false" --%>
<%--				data-options="required:true,valueField:'keyProp', --%>
<%--								textField:'textField',--%>
<%--								data:[{keyProp:'1',textField:'12'}--%>
<%--								,{keyProp:'2',textField:'18'}--%>
<%--								,{keyProp:'3',textField:'24'}--%>
<%--								,{keyProp:'4',textField:'36'}]" --%>
<%--				class="textbox easyui-combobox" value="${interview.period}"/></td>--%>
				<td>期限:</td>
				<td><input id="period" name="type01InterviewList[${status.index}].period" type="text" editable="false" 
				data-options="" class="textbox easyui-numberbox" value="${interview.period}" style="width:128px;"/>个月</td>
				<td colspan="2">月可还款额:
				<input id="monthlyPayments" name="type01InterviewList[${status.index}].monthlyPayments" type="text" 
				data-options="required:true,validType:['length[0,10]'],min:0,precision:2" 
				class="textbox easyui-numberbox" value="${interview.monthlyPayments}" style="width:130px;"/>元</td>
			</tr>
			<tr>
				<td>借款金额:</td>
				<td><input id="amount" name="type01InterviewList[${status.index}].amount" type="text" 
				data-options="required:true,validType:['length[0,10]'],min:0,precision:2" 
				class="textbox easyui-numberbox" value="${interview.amount/10000}" style="width:128px;"/>万元</td>
				<td colspan="3">借款用途:
				<input id="useage1" name="type01InterviewList[${status.index}].useage1" type="text" 
				data-options="required:true,validType:['length[0,10]']" editable="false" 
				class="easyui-combobox" value="${interview.useage1}"/>
				<input id="useage2" name="type01InterviewList[${status.index}].useage2" type="text" editable="false" 
				data-options="required:true,validType:['length[0,10]']" 
				class="easyui-combobox" value="${interview.useage2}"/></td>
			</tr>
			<tr>
				<td>家庭固话是否一致:</td>
				<td><input id="tel" name="type01InterviewList[${status.index}].tel" type="text" 
				data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" 
				value="${interview.tel}"/></td>
				<td>
					<input id="telFlag" name="type01InterviewList[${status.index}].telFlag" type="text" editable="false"
						data-options="required:true,valueField:'keyProp', 
								textField:'textField',
								data:[{keyProp:'0',textField:'不一致'}
								,{keyProp:'1',textField:'一致'}]" 
						class="textbox easyui-combobox" value="${interview.telFlag}"/>
				</td>
			</tr>
			<tr>
				<td>居住地址是否一致:</td>
				<td colspan="4"><input id="address" name="type01InterviewList[${status.index}].address" type="text" 
				data-options="required:true,validType:['length[0,500]']" class="textbox easyui-validatebox" style="width:304px;"
				value="${interview.address}"/>
<%--				</td><td>--%>
					<input id="addressFlag" name="type01InterviewList[${status.index}].addressFlag" type="text" editable="false" 
						data-options="required:true,valueField:'keyProp', 
								textField:'textField',
								data:[{keyProp:'0',textField:'不一致'}
								,{keyProp:'1',textField:'一致'}]" 
						class="textbox easyui-combobox" value="${interview.addressFlag}"/>
				</td>
			</tr>
			<tr>
				<td>单位地址是否一致:</td>
				<td colspan="4"><input id="comAddress" name="type01InterviewList[${status.index}].comAddress" type="text" 
				data-options="required:true,validType:['length[0,500]']" class="textbox easyui-validatebox" style="width:304px;"
				value="${interview.comAddress}"/>
<%--				</td><td>--%>
					<input id="comAddressFlag" name="type01InterviewList[${status.index}].comAddressFlag" type="text" editable="false" 
						data-options="required:true,valueField:'keyProp', 
								textField:'textField',
								data:[{keyProp:'0',textField:'不一致'}
								,{keyProp:'1',textField:'一致'}]" 
						class="textbox easyui-combobox" value="${interview.comAddressFlag}"/>
				</td>
			</tr>
<%--			<tr>--%>
<%--				<td>行业:</td>--%>
<%--				<td>--%>
<%--					--%>
<%--				</td>--%>
<%--			</tr>--%>
			<tr>
				<td colspan="6">
					<div style="font-size: 14px;" >
						<strong>拨打记录</strong>
						<a href="javascript:addcallLog('type01','${status.index}')" >
						拨打</a>
						<input id="callLongIndex" type="hidden"  
						value="${fn:length(interview.callLogList)}" />
						<hr width="100%" size=2 color="#D3D3D3" noshade>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="6">
					<table class="datatable" id="callLongList">
						<tr>
							<th scope="col" width="125px">拨打时间</th>
							<th scope="col" width="70px">接听关系</th>
							<th scope="col" width="70px">接听状态</th>
							<th scope="col" width="400px">备注</th>
						</tr>
<c:forEach items="${interview.callLogList}" var="callLog" varStatus="callLogStatus">
	<tr id="callLong_${callLogStatus.index}">
		<td>
			<input id="id" name="type01InterviewList[${status.index}].callLogList[${callLogStatus.index}].id" type="hidden"  value="${callLog.id}"/>
			<input id="state" name="type01InterviewList[${status.index}].callLogList[${callLogStatus.index}].state" type="hidden"  value="${callLog.state}"/>
			${callLog.callDateStr}
		</td>
		<td>
			<input id="onRelation" name="type01InterviewList[${status.index}].callLogList[${callLogStatus.index}].onRelation" 
			class="textbox easyui-combobox" value="${callLog.onRelation}" editable="false" style="width: 70px;"
			data-options="required:true,valueField:'keyProp', 
						textField:'textField',
						onHidePanel:upInterviewTitile,
						data:[
						 {keyProp:'01',textField:'本人'}
						,{keyProp:'02',textField:'父亲'}
						,{keyProp:'03',textField:'母亲'}
						,{keyProp:'04',textField:'配偶'}
						,{keyProp:'05',textField:'子女'}
						,{keyProp:'06',textField:'亲属'}
						,{keyProp:'07',textField:'朋友'}
						,{keyProp:'08',textField:'同事'}
						,{keyProp:'09',textField:'同学'}
						,{keyProp:'99',textField:'其他'}]"/>
		</td>
		<td>
			<input id="onState" name="type01InterviewList[${status.index}].callLogList[${callLogStatus.index}].onState" 
			class="textbox easyui-combobox" editable="false" value="${callLog.onState}" style="width: 70px;"
			data-options="required:true,valueField:'keyProp', 
						textField:'textField',
						onHidePanel:upInterviewTitile,
						data:[
						 {keyProp:'01',textField:'已接听'}
						,{keyProp:'02',textField:'未接听'}
						,{keyProp:'03',textField:'无法接通'}]" />
		</td>
		<td>
<%--			<input id="remarks" name="type01InterviewList[${status.index}].callLogList[${callLogStatus.index}].remarks" --%>
<%--				class="textbox easyui-validatebox" data-options="validType:['length[0,500]']"--%>
<%--				value="${callLog.remarks}"/>--%>
			<textarea id="remarks" name="type01InterviewList[${status.index}].callLogList[${callLogStatus.index}].remarks"  
				class="textbox easyui-validatebox" style="resize: none;width:400px;height:50px !important;" data-options="validType:['length[0,500]']"
				value="${callLog.remarks}" >${callLog.remarks}</textarea>
		</td>
	</tr>
</c:forEach>
					</table>
				</td>
			</tr>
		</table>
	</div>
</c:forEach>
<%--		</div>--%>
<%--本人手机结束--%>		
<%--开始--%>
<%--配偶手机开始--%>
<%--		<div title="配偶手机" style="overflow:auto;padding:10px;" data-options="">--%>
<c:forEach items="${type05InterviewList}" var="interview" varStatus="status">
	<div title="${interview.pateTitle}" id="type05Interview_${status.index}" style="overflow:auto;padding:10px;" <c:if test="${interview.isAdd==1}">data-options="tools:[{iconCls:'icon-cancel',handler:function(){rmInterview(this);}}]"</c:if>>
		<table style="" width="100%" >
			<tr style="display: none;">
				<td colspan="6">
					<input name="type05InterviewList[${status.index}].id" type="hidden" value="${interview.id}"/>
					<input id="state" name="type05InterviewList[${status.index}].state" type="hidden"  value="1"/>
					<input name="type05InterviewList[${status.index}].appId" type="hidden" value="${bean.appId}"/>
					<input title="是否新增" type="hidden" name="type05InterviewList[${status.index}].isAdd" value="${interview.isAdd}"/>
					<input title="类型" name="type05InterviewList[${status.index}].type" type="hidden" value="${interview.type}"/>
					<input title="关系" name="type05InterviewList[${status.index}].relation" type="hidden" value="${interview.relation}"/>
					<input title="来源" name="type05InterviewList[${status.index}].source" type="hidden" value="${interview.source}"/>
					<input title="名字" name="type05InterviewList[${status.index}].name" type="hidden"  value="${interview.name}"/>
					<input title="号码" name="type05InterviewList[${status.index}].phone" type="hidden" value="${interview.phone}"/>
				</td>
			</tr>
			<tr>
				<td>调查状态:</td>
				<td><input id="surveyState" name="type05InterviewList[${status.index}].surveyState" type="text" editable="false" 
				data-options="required:true,valueField:'keyProp', 
								textField:'textField',
								onHidePanel:upInterviewTitile,
								data:[{keyProp:'01',textField:'未调查'}
								,{keyProp:'02',textField:'调查中'}
								,{keyProp:'03',textField:'调查成功'}
								,{keyProp:'04',textField:'调查失败'}]" 
				class="textbox easyui-combobox" value="${interview.surveyState}"/></td>
				<td>调查标志:</td>
				<td><input id="surveyFlag" name="type05InterviewList[${status.index}].surveyFlag" type="text" editable="false" 
				data-options="required:true,valueField:'keyProp', 
								textField:'textField',
								onHidePanel:upInterviewTitile,
								data:[{keyProp:'01',textField:'正常'}
								,{keyProp:'02',textField:'异常'}]" 
				class="textbox easyui-combobox" value="${interview.surveyFlag}"/></td>
			</tr>
			<tr>
				<td>居住地址是否一致:</td>
				<td colspan="4"><input id="address" name="type05InterviewList[${status.index}].address" type="text" 
				data-options="required:true,validType:['length[0,500]']" class="textbox easyui-validatebox" style="width:304px;"
				value="${interview.address}"/>
<%--				</td><td>--%>
					<input id="addressFlag" name="type05InterviewList[${status.index}].addressFlag" type="text" editable="false" 
						data-options="valueField:'keyProp', 
								textField:'textField',
								data:[{keyProp:'0',textField:'不一致'}
								,{keyProp:'1',textField:'一致'}]" 
						class="textbox easyui-combobox" value="${interview.addressFlag}"/>
				</td>
			</tr>
			<tr>
				<td colspan="6">
					<div style="font-size: 14px;" >
						<strong>拨打记录</strong>
						<a href="javascript:addcallLog('type05','${status.index}')" >
						拨打</a>
						<input id="callLongIndex" type="hidden"  
						value="${fn:length(interview.callLogList)}" />
						<hr width="100%" size=2 color="#D3D3D3" noshade>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="6">
					<table class="datatable" id="callLongList">
						<tr>
							<th scope="col" width="125px">拨打时间</th>
							<th scope="col" width="70px">接听关系</th>
							<th scope="col" width="70px">接听状态</th>
							<th scope="col" width="400px">备注</th>
						</tr>
<c:forEach items="${interview.callLogList}" var="callLog" varStatus="callLogStatus">
	<tr id="callLong_${callLogStatus.index}">
		<td>
			<input id="id" name="type05InterviewList[${status.index}].callLogList[${callLogStatus.index}].id" type="hidden"  value="${callLog.id}"/>
			<input id="state" name="type05InterviewList[${status.index}].callLogList[${callLogStatus.index}].state" type="hidden"  value="${callLog.state}"/>
			${callLog.callDateStr}
		</td>
		<td>
			<input id="onRelation" name="type05InterviewList[${status.index}].callLogList[${callLogStatus.index}].onRelation" 
			class="textbox easyui-combobox" editable="false" value="${callLog.onRelation}"  style="width: 70px;"
			data-options="required:true,valueField:'keyProp', 
						textField:'textField',
						onHidePanel:upInterviewTitile,
						data:[
						 {keyProp:'01',textField:'本人'}
						,{keyProp:'02',textField:'父亲'}
						,{keyProp:'03',textField:'母亲'}
						,{keyProp:'04',textField:'配偶'}
						,{keyProp:'05',textField:'子女'}
						,{keyProp:'06',textField:'亲属'}
						,{keyProp:'07',textField:'朋友'}
						,{keyProp:'08',textField:'同事'}
						,{keyProp:'09',textField:'同学'}
						,{keyProp:'99',textField:'其他'}]"/>
		</td>
		<td>
			<input id="onState" name="type05InterviewList[${status.index}].callLogList[${callLogStatus.index}].onState" 
			class="textbox easyui-combobox" editable="false" value="${callLog.onState}" style="width: 70px;"
			data-options="required:true,valueField:'keyProp', 
						textField:'textField',
						onHidePanel:upInterviewTitile,
						data:[
						 {keyProp:'01',textField:'已接听'}
						,{keyProp:'02',textField:'未接听'}
						,{keyProp:'03',textField:'无法接通'}]"/>
		</td>
		<td>
<%--			<input id="remarks" name="type02InterviewList[${status.index}].callLogList[${callLogStatus.index}].remarks" --%>
<%--				class="textbox easyui-validatebox" data-options="validType:['length[0,500]']"--%>
<%--				value="${callLog.remarks}"/>--%>
			<textarea id="remarks" name="type05InterviewList[${status.index}].callLogList[${callLogStatus.index}].remarks"  
				class="textbox easyui-validatebox" style="resize: none;width:400px;height:50px !important;" data-options="validType:['length[0,500]']"
				value="${callLog.remarks}" >${callLog.remarks}</textarea>
		</td>
	</tr>
</c:forEach>
					</table>
				</td>
			</tr>
		</table>
	</div>
</c:forEach>
<%--		</div>--%>
<%--配偶手机结束--%>

	</div>
<div style="display: none;">
	<textarea id="type01_textarea" disabled="disabled">
	<table style="" width="100%" id="type01Interview_type_what_index" >
		<tr style="display: none;">
			<td colspan="6">
				<input name="type01InterviewList[type_what_index].id" type="hidden" value="0"/>
				<input id="state" name="type01InterviewList[type_what_index].state" type="hidden"  value="1"/>
				<input name="type01InterviewList[type_what_index].appId" type="hidden" value="${bean.appId}"/>
				<input title="是否新增" type="hidden" name="type01InterviewList[type_what_index].isAdd" value="1"/>
				<input title="类型" name="type01InterviewList[type_what_index].type" type="hidden" value="01"/>
				<input title="关系" name="type01InterviewList[type_what_index].relation" type="hidden" value="interview_relation"/>
				<input title="来源" name="type01InterviewList[type_what_index].source" type="hidden" value="interview_source"/>
				<input title="名字" name="type01InterviewList[type_what_index].name" type="hidden"  value="interview_name"/>
				<input title="号码" name="type01InterviewList[type_what_index].phone" type="hidden" value="interview_phone"/>
			</td>
		</tr>
		<tr>
			<td>调查状态:</td>
			<td><input id="surveyState" name="type01InterviewList[type_what_index].surveyState" type="text" editable="false"  
			data-options="required:true,valueField:'keyProp', 
							textField:'textField',
							onHidePanel:upInterviewTitile,
							data:[{keyProp:'01',textField:'未调查'}
							,{keyProp:'02',textField:'调查中'}
							,{keyProp:'03',textField:'调查成功'}
							,{keyProp:'04',textField:'调查失败'}]" 
			class="textbox easyui-combobox" value="01"/></td>
			<td colspan="3">调查标志:
			<input id="surveyFlag" name="type01InterviewList[type_what_index].surveyFlag" type="text" editable="false"   
			data-options="required:true,valueField:'keyProp', 
							textField:'textField',
							onHidePanel:upInterviewTitile,
							data:[{keyProp:'01',textField:'正常'}
							,{keyProp:'02',textField:'异常'}]" 
			class="textbox easyui-combobox" value="01"/></td>
		</tr>
		<tr>
<%--			<td>期限:</td>--%>
<%--			<td><input id="period" name="type01InterviewList[type_what_index].period" type="text" editable="false" --%>
<%--			data-options="required:true,valueField:'keyProp', --%>
<%--							textField:'textField',--%>
<%--							data:[{keyProp:'1',textField:'12'}--%>
<%--							,{keyProp:'2',textField:'18'}--%>
<%--							,{keyProp:'3',textField:'24'}--%>
<%--							,{keyProp:'4',textField:'36'}]" --%>
<%--			class="textbox easyui-combobox" value="${bean.period}"/></td>--%>
			<td>期限:</td>
			<td><input id="period" name="type01InterviewList[type_what_index].period" type="text" editable="false" 
			data-options="" class="textbox easyui-numberbox" value="${bean.period}" style="width:128px;"/>个月</td>
			<td colspan="2">月可还款额:
			<input id="monthlyPayments" name="type01InterviewList[type_what_index].monthlyPayments" type="text" 
			data-options="required:true,validType:['length[0,10]'],min:0,precision:2" 
			class="textbox easyui-numberbox" value="${bean.monthlyPayments}" style="width:130px;"/>元</td>
		</tr>
		<tr>
			<td>借款金额:</td>
			<td><input id="amount" name="type01InterviewList[type_what_index].amount" type="text" 
			data-options="required:true,validType:['length[0,10]'],min:0,precision:2" 
			class="textbox easyui-numberbox" value="${bean.amount/10000}" style="width:128px;"/>万元</td>
			<td colspan="3">借款用途:
			<input id="useage1" name="type01InterviewList[type_what_index].useage1" type="text" editable="false"  
			data-options="required:true,validType:['length[0,10]'],onShowPanel:function(){
				initUseage('type01Interview_type_what_index');
				$('#type01Interview_type_what_index').find('#useage1').combobox({
					onShowPanel: function(){}});}" editable="false" 
			class="easyui-combobox" value="${bean.useage1}"/>
			<input id="useage2" name="type01InterviewList[type_what_index].useage2" type="text" editable="false" 
			data-options="required:true,validType:['length[0,10]']" 
			class="easyui-combobox" value="${bean.useage2}"/></td>
		</tr>
		<tr>
			<td>家庭固话是否一致:</td>
			<td><input id="tel" name="type01InterviewList[type_what_index].tel" type="text" 
			data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" 
			value="${bean.phone}"/></td>
			<td>
				<input id="telFlag" name="type01InterviewList[type_what_index].telFlag" type="text" editable="false"
					data-options="required:true,valueField:'keyProp', 
							textField:'textField',
							data:[{keyProp:'0',textField:'不一致'}
							,{keyProp:'1',textField:'一致'}]" 
					class="textbox easyui-combobox" value="1"/>
			</td>
		</tr>
		<tr>
			<td>居住地址是否一致:</td>
			<td colspan="4"><input id="address" name="type01InterviewList[type_what_index].address" type="text" editable="false"   
			data-options="required:true,validType:['length[0,500]']" class="textbox easyui-validatebox" style="width:304px;"
			value="${bean.addProvince}${bean.addCity}${bean.addCounty}${bean.address}"/>
	<%--		</td><td>--%>
				<input id="addressFlag" name="type01InterviewList[type_what_index].addressFlag" type="text" editable="false" 
					data-options="required:true,valueField:'keyProp', 
							textField:'textField',
							data:[{keyProp:'0',textField:'不一致'}
							,{keyProp:'1',textField:'一致'}]" 
					class="textbox easyui-combobox" value="1"/>
			</td>
		</tr>
		<tr>
			<td>单位地址是否一致:</td>
			<td colspan="4"><input id="comAddress" name="type01InterviewList[type_what_index].comAddress" type="text" 
			data-options="required:true,validType:['length[0,500]']" class="textbox easyui-validatebox" style="width:304px;"
			value="${bean.comProvince}${bean.comCity}${bean.comCounty}${bean.comAddress}"/>
	<%--		</td><td>--%>
				<input id="comAddressFlag" name="type01InterviewList[type_what_index].comAddressFlag" type="text" editable="false" 
					data-options="required:true,valueField:'keyProp', 
							textField:'textField',
							data:[{keyProp:'0',textField:'不一致'}
							,{keyProp:'1',textField:'一致'}]" 
					class="textbox easyui-combobox" value="1"/>
			</td>
		</tr>
	<%--	<tr>--%>
	<%--		<td>行业:</td>--%>
	<%--		<td>--%>
	<%--			--%>
	<%--		</td>--%>
	<%--	</tr>--%>
		<tr>
			<td colspan="6">
				<div style="font-size: 14px;" >
					<strong>拨打记录</strong>
					<a href="javascript:addcallLog('type01','type_what_index')" >
					拨打</a>
					<input id="callLongIndex" type="hidden"  
					value="0" />
					<hr width="100%" size=2 color="#D3D3D3" noshade>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="6">
				<table class="datatable" id="callLongList">
					<tr>
						<th scope="col" width="125px">拨打时间</th>
						<th scope="col" width="70px">接听关系</th>
						<th scope="col" width="70px">接听状态</th>
						<th scope="col" width="400px">备注</th>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</textarea>
	<textarea id="type02_textarea" disabled="disabled">
		<table style="" width="100%" id="type02Interview_type_what_index" >
		<tr style="display: none;">
			<td colspan="6">
				<input name="type02InterviewList[type_what_index].id" type="hidden" value="0"/>
				<input id="state" name="type02InterviewList[type_what_index].state" type="hidden"  value="1"/>
				<input name="type02InterviewList[type_what_index].appId" type="hidden" value="${bean.appId}"/>
				<input title="是否新增" type="hidden" name="type02InterviewList[type_what_index].isAdd" value="1"/>
				<input title="类型" name="type02InterviewList[type_what_index].type" type="hidden" value="02"/>
				<input title="关系" name="type02InterviewList[type_what_index].relation" type="hidden" value="interview_relation"/>
				<input title="来源" name="type02InterviewList[type_what_index].source" type="hidden" value="interview_source"/>
				<input title="名字" name="type02InterviewList[type_what_index].name" type="hidden"  value="interview_name"/>
				<input title="号码" name="type02InterviewList[type_what_index].phone" type="hidden" value="interview_phone"/>
			</td>
		</tr>
		<tr>
			<td>调查状态:</td>
			<td><input id="surveyState" name="type02InterviewList[type_what_index].surveyState" type="text" editable="false"  
			data-options="required:true,valueField:'keyProp', 
							textField:'textField',
							onHidePanel:upInterviewTitile,
							data:[{keyProp:'01',textField:'未调查'}
							,{keyProp:'02',textField:'调查中'}
							,{keyProp:'03',textField:'调查成功'}
							,{keyProp:'04',textField:'调查失败'}]" 
			class="textbox easyui-combobox" value="01"/></td>
			<td>调查标志:</td>
			<td><input id="surveyFlag" name="type02InterviewList[type_what_index].surveyFlag" type="text" editable="false"  
			data-options="required:true,valueField:'keyProp', 
							textField:'textField',
							onHidePanel:upInterviewTitile,
							data:[{keyProp:'01',textField:'正常'}
							,{keyProp:'02',textField:'异常'}]" 
			class="textbox easyui-combobox" value="01"/></td>
		</tr>
		<tr>
			<td>居住地址是否一致:</td>
			<td colspan="4"><input id="address" name="type02InterviewList[type_what_index].address" type="text" 
			data-options="required:true,validType:['length[0,500]']" class="textbox easyui-validatebox"  style="width:304px;"
			value="${bean.addProvince}${bean.addCity}${bean.addCounty}${bean.address}"/>
<%--			</td><td>--%>
				<input id="addressFlag" name="type02InterviewList[type_what_index].addressFlag" type="text" editable="false" 
					data-options="required:true,valueField:'keyProp', 
							textField:'textField',
							data:[{keyProp:'0',textField:'不一致'}
							,{keyProp:'1',textField:'一致'}]" 
					class="textbox easyui-combobox" value="1"/>
			</td>
		</tr>
		<tr>
			<td colspan="6">
				<div style="font-size: 14px;" >
					<strong>拨打记录</strong>
					<a href="javascript:addcallLog('type02','type_what_index')" >
					拨打</a>
					<input id="callLongIndex" type="hidden"  
					value="0" />
					<hr width="100%" size=2 color="#D3D3D3" noshade>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="6">
				<table class="datatable" id="callLongList">
					<tr>
						<th scope="col" width="125px">拨打时间</th>
						<th scope="col" width="70px">接听关系</th>
						<th scope="col" width="70px">接听状态</th>
						<th scope="col" width="400px">备注</th>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</textarea>
<textarea id="type03_textarea" disabled="disabled">
	<table style="" width="100%" id="type03Interview_type_what_index" >
		<tr style="display: none;">
			<td colspan="6">
				<input name="type03InterviewList[type_what_index].id" type="hidden" value="0"/>
				<input id="state" name="type03InterviewList[type_what_index].state" type="hidden"  value="1"/>
				<input name="type03InterviewList[type_what_index].appId" type="hidden" value="${bean.appId}"/>
				<input title="是否新增" type="hidden" name="type03InterviewList[type_what_index].isAdd" value="1"/>
				<input title="类型" name="type03InterviewList[type_what_index].type" type="hidden" value="03"/>
				<input title="关系" name="type03InterviewList[type_what_index].relation" type="hidden" value="interview_relation"/>
				<input title="来源" name="type03InterviewList[type_what_index].source" type="hidden" value="interview_source"/>
				<input title="名字" name="type03InterviewList[type_what_index].name" type="hidden"  value="interview_name"/>
				<input title="号码" name="type03InterviewList[type_what_index].phone" type="hidden" value="interview_phone"/>
			</td>
		</tr>
		<tr>
			<td>调查状态:</td>
			<td><input id="surveyState" name="type03InterviewList[type_what_index].surveyState" type="text" editable="false"  
			data-options="required:true,valueField:'keyProp', 
							textField:'textField',
							onHidePanel:upInterviewTitile,
							data:[{keyProp:'01',textField:'未调查'}
							,{keyProp:'02',textField:'调查中'}
							,{keyProp:'03',textField:'调查成功'}
							,{keyProp:'04',textField:'调查失败'}]" 
			class="textbox easyui-combobox" value="01"/></td>
			<td>调查标志:</td>
			<td><input id="surveyFlag" name="type03InterviewList[type_what_index].surveyFlag" type="text" editable="false"  
			data-options="required:true,valueField:'keyProp', 
							textField:'textField',
							onHidePanel:upInterviewTitile,
							data:[{keyProp:'01',textField:'正常'}
							,{keyProp:'02',textField:'异常'}]" 
			class="textbox easyui-combobox" value="01"/></td>
		</tr>
		<tr>
			<td>单位名称是否一致:</td>
			<td colspan="3"><input id="comName" name="type03InterviewList[type_what_index].comName" type="text" 
			data-options="required:true,validType:['length[0,500]']" class="textbox easyui-validatebox" 
			value="${bean.comName}"/>
				<input id="comNameFlag" name="type03InterviewList[type_what_index].comNameFlag" type="text" editable="false"  
					data-options="required:true,valueField:'keyProp', 
							textField:'textField',
							data:[{keyProp:'0',textField:'不一致'}
							,{keyProp:'1',textField:'一致'}]" 
					class="textbox easyui-combobox" value="1"/>
			</td>
		</tr>
		<tr>
			<td>单位地址是否一致:</td>
			<td colspan="4"><input id="comAddress" name="type03InterviewList[type_what_index].comAddress" type="text" 
			data-options="required:true,validType:['length[0,500]']" class="textbox easyui-validatebox" style="width:304px;"
			value="${bean.comProvince}${bean.comCity}${bean.comCounty}${bean.comAddress}"/>
<%--			</td><td>--%>
				<input id="comAddressFlag" name="type03InterviewList[type_what_index].comAddressFlag" type="text" editable="false"  
					data-options="required:true,valueField:'keyProp', 
							textField:'textField',
							data:[{keyProp:'0',textField:'不一致'}
							,{keyProp:'1',textField:'一致'}]" 
					class="textbox easyui-combobox" value="1"/>
			</td>
		</tr>
		<tr>
			<td>客户是否在职:</td>
			<td>
				<input id="isJob" name="type03InterviewList[type_what_index].isJob" type="text" editable="false"  
					data-options="required:true,valueField:'keyProp', 
							textField:'textField',
							data:[{keyProp:'0',textField:'否'}
							,{keyProp:'1',textField:'是'}]" 
					class="textbox easyui-combobox" value="1"/>
			</td>
		</tr>
		<tr>
			<td colspan="6">
				<div style="font-size: 14px;" >
					<strong>拨打记录</strong>
					<a href="javascript:addcallLog('type03','type_what_index')" >
					拨打</a>
					<input id="callLongIndex" type="hidden"  
					value="0" />
					<hr width="100%" size=2 color="#D3D3D3" noshade>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="6">
				<table class="datatable" id="callLongList">
					<tr>
						<th scope="col" width="125px">拨打时间</th>
						<th scope="col" width="70px">接听关系</th>
						<th scope="col" width="70px">接听状态</th>
						<th scope="col" width="400px">备注</th>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</textarea>
	<textarea id="type04_textarea" disabled="disabled">
		<table style="" width="100%" id="type04Interview_type_what_index" >
		<tr style="display: none;">
			<td colspan="6">
				<input name="type04InterviewList[type_what_index].id" type="hidden" value="0"/>
				<input id="state" name="type04InterviewList[type_what_index].state" type="hidden"  value="1"/>
				<input name="type04InterviewList[type_what_index].appId" type="hidden" value="${bean.appId}"/>
				<input title="是否新增" type="hidden" name="type04InterviewList[type_what_index].isAdd" value="1"/>
				<input title="类型" name="type04InterviewList[type_what_index].type" type="hidden" value="04"/>
				<input title="关系" name="type04InterviewList[type_what_index].relation" type="hidden" value="interview_relation"/>
				<input title="来源" name="type04InterviewList[type_what_index].source" type="hidden" value="interview_source"/>
				<input title="名字" name="type04InterviewList[type_what_index].name" type="hidden"  value="interview_name"/>
				<input title="号码" name="type04InterviewList[type_what_index].phone" type="hidden" value="interview_phone"/>
			</td>
		</tr>
		<tr>
			<td>调查状态:</td>
			<td><input id="surveyState" name="type04InterviewList[type_what_index].surveyState" type="text" editable="false"  
			data-options="required:true,valueField:'keyProp', 
							textField:'textField',
							onHidePanel:upInterviewTitile,
							data:[{keyProp:'01',textField:'未调查'}
							,{keyProp:'02',textField:'调查中'}
							,{keyProp:'03',textField:'调查成功'}
							,{keyProp:'04',textField:'调查失败'}]" 
			class="textbox easyui-combobox" value="01"/></td>
			<td>调查标志:</td>
			<td><input id="surveyFlag" name="type04InterviewList[type_what_index].surveyFlag" type="text" editable="false"  
			data-options="required:true,valueField:'keyProp', 
							textField:'textField',
							onHidePanel:upInterviewTitile,
							data:[{keyProp:'01',textField:'正常'}
							,{keyProp:'02',textField:'异常'}]" 
			class="textbox easyui-combobox" value="01"/></td>
		</tr>
		<tr>
			<td>居住地址是否一致:</td>
			<td colspan="4"><input id="address" name="type04InterviewList[type_what_index].address" type="text" 
			data-options="required:true,validType:['length[0,500]']" class="textbox easyui-validatebox" style="width:304px;"
			value="${bean.addProvince}${bean.addCity}${bean.addCounty}${bean.address}"/>
	<%--		</td><td>--%>
				<input id="addressFlag" name="type04InterviewList[type_what_index].addressFlag" type="text" editable="false" 
					data-options="required:true,valueField:'keyProp', 
							textField:'textField',
							data:[{keyProp:'0',textField:'不一致'}
							,{keyProp:'1',textField:'一致'}]" 
					class="textbox easyui-combobox" value="1"/>
			</td>
		</tr>
		<tr>
			<td colspan="6">
				<div style="font-size: 14px;" >
					<strong>拨打记录</strong>
					<a href="javascript:addcallLog('type04','type_what_index')" >
					拨打</a>
					<input id="callLongIndex" type="hidden"  
					value="0" />
					<hr width="100%" size=2 color="#D3D3D3" noshade>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="6">
				<table class="datatable" id="callLongList">
					<tr>
						<th scope="col" width="125px">拨打时间</th>
						<th scope="col" width="70px">接听关系</th>
						<th scope="col" width="70px">接听状态</th>
						<th scope="col" width="400px">备注</th>
					</tr>
				</table>
			</td>
		</tr>
	</table>
		</textarea>
<textarea id="type05_textarea" disabled="disabled">
		<table style="" width="100%" id="type05Interview_type_what_index" >
		<tr style="display: none;">
			<td colspan="6">
				<input name="type05InterviewList[type_what_index].id" type="hidden" value="0"/>
				<input id="state" name="type05InterviewList[type_what_index].state" type="hidden"  value="1"/>
				<input name="type05InterviewList[type_what_index].appId" type="hidden" value="${bean.appId}"/>
				<input title="是否新增" type="hidden" name="type05InterviewList[type_what_index].isAdd" value="1"/>
				<input title="类型" name="type05InterviewList[type_what_index].type" type="hidden" value="05"/>
				<input title="关系" name="type05InterviewList[type_what_index].relation" type="hidden" value="interview_relation"/>
				<input title="来源" name="type05InterviewList[type_what_index].source" type="hidden" value="interview_source"/>
				<input title="名字" name="type05InterviewList[type_what_index].name" type="hidden"  value="interview_name"/>
				<input title="号码" name="type05InterviewList[type_what_index].phone" type="hidden" value="interview_phone"/>
			</td>
		</tr>
		<tr>
			<td>调查状态:</td>
			<td><input id="surveyState" name="type05InterviewList[type_what_index].surveyState" type="text" editable="false"  
			data-options="required:true,valueField:'keyProp', 
							textField:'textField',
							onHidePanel:upInterviewTitile,
							data:[{keyProp:'01',textField:'未调查'}
							,{keyProp:'02',textField:'调查中'}
							,{keyProp:'03',textField:'调查成功'}
							,{keyProp:'04',textField:'调查失败'}]" 
			class="textbox easyui-combobox" value="01"/></td>
			<td>调查标志:</td>
			<td><input id="surveyFlag" name="type05InterviewList[type_what_index].surveyFlag" type="text" editable="false"  
			data-options="required:true,valueField:'keyProp', 
							textField:'textField',
							onHidePanel:upInterviewTitile,
							data:[{keyProp:'01',textField:'正常'}
							,{keyProp:'02',textField:'异常'}]" 
			class="textbox easyui-combobox" value="01"/></td>
		</tr>
		<tr>
			<td>居住地址是否一致:</td>
			<td colspan="4"><input id="address" name="type05InterviewList[type_what_index].address" type="text" 
			data-options="required:true,validType:['length[0,500]']" class="textbox easyui-validatebox" style="width:304px;"
			value="${bean.addProvince}${bean.addCity}${bean.addCounty}${bean.address}"/>
	<%--		</td><td>--%>
				<input id="addressFlag" name="type05InterviewList[type_what_index].addressFlag" type="text" editable="false" 
					data-options="required:true,valueField:'keyProp', 
							textField:'textField',
							data:[{keyProp:'0',textField:'不一致'}
							,{keyProp:'1',textField:'一致'}]" 
					class="textbox easyui-combobox" value="1"/>
			</td>
		</tr>
		<tr>
			<td colspan="6">
				<div style="font-size: 14px;" >
					<strong>拨打记录</strong>
					<a href="javascript:addcallLog('type05','type_what_index')" >
					拨打</a>
					<input id="callLongIndex" type="hidden"  
					value="0" />
					<hr width="100%" size=2 color="#D3D3D3" noshade>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="6">
				<table class="datatable" id="callLongList">
					<tr>
						<th scope="col" width="125px">拨打时间</th>
						<th scope="col" width="70px">接听关系</th>
						<th scope="col" width="70px">接听状态</th>
						<th scope="col" width="400px">备注</th>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</textarea>
	
<textarea id="callLog_textarea" disabled="disabled">
<tr id="callLong_callLongIndex">
	<td>
		<input id="id" name="type_whatInterviewList[type_what_index].callLogList[callLongIndex].id" type="hidden"  value="0"/>
		<input id="state" name="type_whatInterviewList[type_what_index].callLogList[callLongIndex].state" type="hidden"  value="1"/>
		<input id="callDate" name="type_whatInterviewList[type_what_index].callLogList[callLongIndex].callDate" type="hidden" value="JS_callDateStr"/>
		JS_callDateStr
	</td>
	<td>
		<input id="onRelation" name="type_whatInterviewList[type_what_index].callLogList[callLongIndex].onRelation" 
		class="textbox easyui-combobox" value="" editable="false" style="width: 70px;"
		data-options="required:true,valueField:'keyProp', 
					textField:'textField',
					onHidePanel:upInterviewTitile,
					data:[
					 {keyProp:'01',textField:'本人'}
					,{keyProp:'02',textField:'父亲'}
					,{keyProp:'03',textField:'母亲'}
					,{keyProp:'04',textField:'配偶'}
					,{keyProp:'05',textField:'子女'}
					,{keyProp:'06',textField:'亲属'}
					,{keyProp:'07',textField:'朋友'}
					,{keyProp:'08',textField:'同事'}
					,{keyProp:'09',textField:'同学'}
					,{keyProp:'99',textField:'其他'}]"/>
	</td>
	<td>
		<input id="onState" name="type_whatInterviewList[type_what_index].callLogList[callLongIndex].onState" 
		class="textbox easyui-combobox" value="" editable="false" style="width: 70px;"
		data-options="required:true,valueField:'keyProp', 
					textField:'textField',
					onHidePanel:upInterviewTitile,
					data:[
					 {keyProp:'01',textField:'已接听'}
					,{keyProp:'02',textField:'未接听'}
					,{keyProp:'03',textField:'无法接通'}]"/>
	</td>
	<td>
<%--		<input id="remarks" name="type_whatInterviewList[type_what_index].callLogList[callLongIndex].remarks" --%>
<%--			class="textbox easyui-validatebox" data-options="validType:['length[0,500]']" style="resize: none;width:400px;height:50px !important;"--%>
<%--			value=""/>--%>
		
		
		<textarea_textarea id="remarks" name="type_whatInterviewList[type_what_index].callLogList[callLongIndex].remarks"  
			class="textbox easyui-validatebox" style="resize: none;width:400px;height:50px !important;" 
			 data-options="validType:['length[0,500]']"
			></textarea_textarea>
<%--		<textarea id="remarks" name="type_whatInterviewList[type_what_index].callLogList[callLongIndex].remarks"  --%>
<%--			class="textbox easyui-validatebox" style="resize: none;width:400px;height:50px !important;"  data-options="validType:['length[0,500]']"--%>
<%--			value="" ></textarea>--%>
	</td>
</tr>
</textarea>
</div>

<!-- 面审记录的 操作方法 -->
<script type="text/javascript">
	//面审添加拨打记录
	function addcallLog(interviewType,interviewIndex){
		var thisInterview=$("#"+interviewType+"Interview_"+interviewIndex);
		var callLongIndex=thisInterview.find("#callLongIndex").val();
		var callLogHTML=$("#callLog_textarea").val();
		var JS_callDateStr=date();
		callLogHTML=callLogHTML.replace(eval('/textarea_textarea/gm'),"textarea");
		callLogHTML=callLogHTML.replace(eval('/type_what_index/gm'),interviewIndex);
		callLogHTML=callLogHTML.replace(eval('/type_what/gm'),interviewType);
		callLogHTML=callLogHTML.replace(eval('/callLongIndex/gm'),callLongIndex);
		callLogHTML=callLogHTML.replace(eval('/JS_callDateStr/gm'),JS_callDateStr);
		var newDriver=$(callLogHTML);					//转换成 JQuery 对象
		newDriver.appendTo(thisInterview.find("#callLongList"));				//添加到 对应地点
		$.parser.parse(newDriver);			
		thisInterview.find("#callLongIndex").val(++callLongIndex);
	}
	//得到当前时间
	function date(type,myDate){//获取三个参数，依次为：时间格式，显示的ID,是否同步
		if(myDate==null){
			myDate = new Date();
		}
		if(type==null){
			type = "Y-M-D h:m:s";
		}
	        Y = myDate.getFullYear();//年份：如2014
	        M = myDate.getMonth()+1<10?"0"+(myDate.getMonth()+1):myDate.getMonth()+1;//月份：如06
	  	D = myDate.getDate()<10?"0"+myDate.getDate():myDate.getDate();//日：如15
	        h = myDate.getHours()<10?"0"+myDate.getHours():myDate.getHours();
	        m = myDate.getMinutes()<10?"0"+myDate.getMinutes():myDate.getMinutes();
	        s = myDate.getSeconds()<10?"0"+myDate.getSeconds():myDate.getSeconds();
		  //获取当前时间，并将变量格式且赋值，如M代码月，并肯于默认的时间月份比实际月份小1，月份小于10也不会补0。
	    var time = type.replace("Y",Y);
	        time = time.replace("M",M);
	        time = time.replace("D",D);
	        time = time.replace("h",h);
	        time = time.replace("m",m);
	        time = time.replace("s",s);
	 	return time;
	};
	//改变title
	function upInterviewTitile(newValue,oldValue){
		var panel = $('#intervewDiv').accordion('getSelections')[1];
		var title = panel.panel('options').title;
		var t = title.split("——");
		if(t.length > 1){
			panel.panel('setTitle', t[0] + "——" + 
					panel.find("#surveyState").combobox('getText') +
					"(" + panel.find("#surveyFlag").combobox('getText') + ")");
		}
	}
	//填充借款用途
	function initUseage(divId){
		var obj = $('#'+divId);
		//填充select数据 借款用途1
	   	var useage1url = "sys/datadictionary/listjason.do?keyName=creditusage1";
		obj.find("#useage1").combobox("clear");
		obj.find('#useage1').combobox({
			url: useage1url,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
	            obj.find('#useage2').combobox('clear');
	            var useage2url = "sys/datadictionary/listjason.do?keyName=creditusage2&parentKeyProp=" + encodeURI(newValue);
	            obj.find('#useage2').combobox('reload',useage2url); 
	          }
		});
		//填充select数据 借款用途2
		var useage1 = obj.find('#useage1').combobox('getValue');
		var useage2url = "sys/datadictionary/listjason.do?keyName=creditusage2&parentKeyProp=" + encodeURI(useage1);
		obj.find("#useage2").combobox("clear");
		obj.find('#useage2').combobox({
			url: useage2url,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
	}
</script>


<!-- 初始化面审调查 -->
<script type="text/javascript">
$.parser.parse($("#intervewDiv"));
$(document).ready(function(){
	//当前页面总 ID
	var intervewDiv=$("#intervewDiv");
	//添加 选项页 ID
	var interviewAddDiv=intervewDiv.find("#interviewAddDiv");
	
	//填充”添加页“里的”来源“
	var source=interviewAddDiv.find("#source");
	source.combobox("clear");
	source.combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.interviewSource
	});
	//填充”添加页“里的”关系“
	var relation = interviewAddDiv.find("#relation");
	relation.combobox("clear");
	relation.combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.relation
	});
	
	var type = interviewAddDiv.find("#type");
	type.combobox("clear");
	type.combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:[{keyProp:"01",keyValue:"本人手机"},
		      {keyProp:"02",keyValue:"家庭固话"},
		      {keyProp:"03",keyValue:"单位电话"},
		      {keyProp:"04",keyValue:"常用联系人"},
		      {keyProp:"05",keyValue:"配偶手机"}]
	});
	<c:forEach items="${type01InterviewList}" var="type01Interview" varStatus="status">
			initUseage('type01Interview_${status.index}');
	</c:forEach>
	
});
 </script>
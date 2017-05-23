<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>"/>
<title>信用贷款审批决策表更新</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
<style type="text/css">
</style>
</head>
<body>
<div id="main">
<div id="part1" class="part">
	<p class="title">
		<a href="javascript:void(0);">审批决策</a>
<%--		<span style="float: right; padding-right: 20px;">--%>
<%--			<input type="button" value="保存" class="btn" onclick="updateFunction('save')"/>--%>
<%--			<input type="button" value="返回" class="btn" onclick="javascript:back()"/>--%>
<%--		</span>--%>
		<span style="padding-left: 20px; color: red;">
			预审批评分等级：${bean.ruleGrade}
			<c:if test="${fn:contains(creditVerify.product,'精英贷') and not empty bean.gradeRemind}">&nbsp;&nbsp;|&nbsp;&nbsp;${bean.gradeRemind}</c:if>
		</span>
		<span style="padding-left: 20px; color: red;">
			授信额度：5万元
		</span>
	</p>
<div class="content">
<div class="easyui-layout" id="easyui_layout" style="width:100%;height:730px;" data-options="fit:true">
<div data-options="region:'center',split:true,border:false" >
	<div class="easyui-tabs" data-options="fit:true,onLoad:tabsReadOnly">
		<div title="影像资料" data-options="href:'${basePath}img/imgSlidePath.do?appId=${bean.appId}'" style="width: 100%;padding:10px"></div>
		<div title="申请信息" data-options="href:'${basePath}credit/app/read1.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		<div title="综合信息" data-options="href:'${basePath}credit/verify/complexInfo.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
	</div>
</div>

<div data-options="region:'east',split:true,border:false" style="width:800px;">
	<div class="easyui-tabs" id="verify_tabs" style="height:730px;" data-options="fit:true,onLoad:tabsReadOnly">
		<div title="风险提示" data-options="href:'${basePath}credit/rule/read.do'" style="width: 100%;padding:10px"></div>
		<div title="信息核查" data-options="href:'${basePath}credit/verify/infoCheck.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		<div title="影像摘要" data-options="href:'${basePath}credit/verify/imageSummary.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		<div title="信用报告" data-options="href:'${basePath}credit/verify/creditReport.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		<div title="面审调查" data-options="href:'${basePath}credit/verify/interviewSurvey.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		<div title="审核决策"  style="width: 100%;padding:10px">
		  <form id="updateForm" >
		  <input type="hidden" name="appId" value="${bean.appId}" />
			 <table width="100%">
				<tr>
					<td colspan="9">
						<div style="font-size: 14px;" >
							<strong>申请信息</strong>
							<hr width="100%" size=2 color="#D3D3D3" noshade>
						</div>
					</td>
				</tr>
			 	<tr><td>
			 		<table id="verifylAppInfo">
						<tr>
							<td align="right">借款金额：</td>
							<td><input type="text" id="amount" value="${bean.amount/10000}" class="textbox easyui-numberbox" 
								style="width:152px;" disabled="disabled" data-options="required:true,min:0,precision:2"/>万元
							</td>
<%--							<td align="right">借款期限：</td>--%>
<%--							<td><input id="period" type="text" data-options="required:true,validType:['length[0,10]']" --%>
<%--								class="easyui-combobox"  value="${bean.period}" editable="false" style="width:152px;" disabled="disabled"/></td>--%>
							<td align="right">渠道：</td>
							<td>
								<input id="belongChannel" type="text" data-options="required:true,validType:['length[0,50]']" 
									class="easyui-combobox"  value="${bean.belongChannel}" editable="false" style="width:152px;" disabled="disabled"/>
							</td>
							<td align="right">产品：</td>
							<td>
								<input id="product" type="text" data-options="required:true,validType:['length[0,50]']" 
									class="easyui-combobox"  value="${bean.product}" editable="false" style="width:152px;" disabled="disabled"/>
							</td>
						</tr>
					</table>
				</td></tr>
			 	<tr>
					<td colspan="9">
						<div style="font-size: 14px;" >
							<strong>审核</strong>
							<hr width="100%" size=2 color="#D3D3D3" noshade>
						</div>
					</td>
				</tr>
			 	<tr><td>
			 		<table id="verifyInfo">
			 			<tr>
							<td align="right">借款金额：</td>
							<td><input id="amount" value="${creditVerify.amount/10000}" type="text"
									data-options="required:true,min:0,precision:2" class="textbox easyui-numberbox" style="width:152px;" disabled="disabled"/>万元
							</td>
<%--							<td align="light">借款期限:</td>--%>
<%--							<td ><input id="period" value="${creditVerify.period}" type="text" data-options="validType:['length[0,10]']" --%>
<%--								class="easyui-combobox" editable="false" style="width:152px;" disabled="disabled"/>--%>
<%--							</td>--%>
							<td align="right">渠道：</td>
							<td>
								<input id="belongChannel" type="text" class="easyui-combobox" 
								data-options="validType:['length[0,50]']" editable="false" value="${creditVerify.belongChannel}" disabled="disabled"/>
							</td>
							<td align="right">产品：</td>
							<td>
								<input id="product" value="${creditVerify.product}" type="text"  data-options="validType:['length[0,50]']" 
								class="easyui-combobox" editable="false" style="width:152px;" disabled="disabled"/>
							</td>
						</tr>
						<tr>
							<td>月还款额：</td>
							<td colspan="5">
								 <input id="yhkje1" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${yhkje1}" style="width:152px;" disabled="disabled"/>
							</td>
						</tr>
						<tr>
							<td align="right">审核人：</td>
							<td ><input value="${creditVerify.operator}" type="text"
										data-options="validType:['length[0,30]']"
										class="textbox easyui-validatebox" style="width:148px;" disabled="disabled"/>
								<input type="hidden" name="creditVerifyOperator" value="${creditVerify.operator}" /></td>
							<td colspan="4">&nbsp;</td>
						</tr>
						<tr>
							<td align="right">建议：</td>
							<td><select id="decision" value="${creditVerify.decision}"
									data-options="validType:['length[0,50]']" 
									class="easyui-combobox" editable="false" style="width:152px;" disabled="disabled">
									<option value="">请选择</option>
									<option value="02" <c:if test="${'02' eq creditVerify.decision}">selected</c:if>>拟通过</option>
									<option value="03" <c:if test="${'03' eq creditVerify.decision}">selected</c:if>>拟拒贷</option>
								</select>
							</td>
							<c:if test="${'03' eq creditVerify.decision}">
								<td align="right" colspan="4">
									违例码：
									<input id="decisionCode1" 
										value="${creditVerify.decisionCode1}" type="text" 
										data-options="validType:['length[0,50]']" 
										class="easyui-combobox" editable="false" disabled="disabled" style="width:152px;"/>
									<input id="decisionCode2" 
										value="${creditVerify.decisionCode2}" type="text" 
										data-options="validType:['length[0,50]']" 
										class="easyui-combobox" editable="false" disabled="disabled" style="width:152px;"/>
								</td>
							</c:if>
						</tr>
						<tr>
							<td align="right">备注：</td>
							<td colspan="5"><textarea value="${creditVerify.remarks}" class="textbox easyui-validatebox" data-options="validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;background-color: #F0F0F0;" disabled="disabled">${creditVerify.remarks}</textarea></td>
						</tr>
					</table>
				</td></tr>
				
				<c:if test="${not empty creditFraud }">
					<tr>
						<td colspan="9">
							<div style="font-size: 14px;" >
								<strong>欺诈审核信息</strong>
								<hr width="100%" size=2 color="#D3D3D3" noshade>
							</div>
						</td>
					</tr>
					<tr><td>
				 		<table id="fraudInfo">
							<tr>
								<td align="right">欺诈结果：</td>
								<td><input type="text" id="fraudResult"  class="textbox easyui-combobox" 
									disabled="disabled" 
									data-options="valueField:'keyProp', 
												textField:'keyValue',
												panelHeight:'auto',
												value:'${creditFraud.result }',
												data:[{'keyProp':'1','keyValue':'无问题'},
													  {'keyProp':'2','keyValue':'有问题'}]"/>
								</td>
								<td align="right">处理人：</td>
								<td>
									<input type="text" value="${creditFraud.operatorName }" class="textbox" disabled="disabled"/>
								</td>
							</tr>
							<tr>
								<td align="right">欺诈备注：</td>
								<td colspan="4">
									<textarea id="resultInfo" name="resultInfo" class="textbox" disabled="disabled"
									style="resize: none;width:304px;height:60px!important;">${creditFraud.resultInfo }</textarea>
								</td>
							</tr>
						</table>
					</td></tr>
				</c:if>
				
				<tr>
					<td colspan="9">
						<div style="font-size: 14px;" >
							<strong>审批决策</strong>
								<input type="hidden" name="id" value="${bean.id}" />
								<input type="hidden" name="decision.id" value="${creditApproval.id}" />
								<input type="hidden" name="decision.appId" value="${bean.appId}" />
								<input type="hidden" name="decision.type" value="2" />
								<input type="hidden" id="state" name="decision.state" value="${creditApproval.state}" />
							<hr width="100%" size=2 color="#D3D3D3" noshade>
						</div>
					</td>
				</tr>
				<tr><td>
			 		<table id="approvalInfo">
			 			<tr>
							<td align="right">借款金额：</td>
							<td><div id ="amountDiv">
								<input id="amount" name="decision.amount" value="${creditApproval.amount/10000}" type="text"
									data-options="required:true,min:0,precision:2" class="textbox easyui-numberbox" style="width:152px;" onchange="updateYhkjeHtje();"/>万元
								</div>
							</td>
<%--							<td align="light">借款期限:</td>--%>
<%--							<td ><input id="period" name="decision.period" <c:if test="${0 != creditApproval.period}">value='${creditApproval.period}'</c:if> type="text" data-options="required:true,validType:['length[0,10]']" --%>
<%--									class="easyui-combobox" editable="false" style="width:152px;"/>--%>
<%--							</td>--%>
							<td align="right">渠道：</td>
							<td>
								<input id="belongChannel" name="decision.belongChannel" type="text" class="easyui-combobox" 
								data-options="required:true,validType:['length[0,50]']" editable="false" value="${creditApproval.belongChannel}"/>
							</td>
							<td align="right">产品：</td>
							<td>
								<input id="product" name="decision.product" value="${creditApproval.product }"
								 type="text"  data-options="required:true,validType:['length[0,50]']" class="easyui-combobox" editable="false" style="width:152px;"/>
							</td>
<%--							测试码--%>
							<td align="right"  class="JYDTestScheme">精英贷测试方案：</td>
							<td style="display: none;" id="selectCode" class="JYDTestScheme">
								<input id="secondTestCode" name="decision.secondTestCode" value="${creditApproval.secondTestCode}"
									data-options="" class="easyui-combobox" editable="false" style="width:120px;"/>
								<input type="hidden" id="firstTestCode" name="decision.firstTestCode" value="${creditApproval.firstTestCode}"/>
								<input type="hidden" id="secondTestValue" name="decision.secondTestValue" value="${creditApproval.secondTestValue}"/>
							</td>
						</tr>
						<tr>
							<td align="right">月还款额：</td>
							<td>
								 <input id="yhkje2" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${yhkje2}" style="width:152px;" onchange="updateJkjeHtje();"/> 元
							</td>
							<td align="right">
								合同金额：
							</td>
							<td>
								<input id="htje" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${creditApproval.contractAmount}" style="width:152px;" onchange="updateJkjeYhkje();"/> 元
							</td>
						</tr>
						
						<tr id="dxShow" style="display: none;">
							<%-- <td align="right">鼎轩融资总额:</td>
							<td>
								 <input id="ceilingAmount" type="text" data-options="min:0,precision:2" disabled="disabled"
								 	class="textbox easyui-numberbox" value="${dxBean.ceilingAmount}" style="width:152px;" /> 元
							</td> --%>
							<td align="right">
								审批剩余额度：
							</td>
							<td>
								<input id="leaveAmountOfWeek" type="text" data-options="min:0,precision:2" disabled="disabled"
									class="textbox easyui-numberbox" value="${dxBean.leaveAmountOfWeek}" style="width:152px;" /> 万元
							</td>
							<td align="right">
								签约剩余额度：
							</td>
							<td>
								<input id="leaveAmountOfWeek" type="text" data-options="min:0,precision:2" disabled="disabled"
									class="textbox easyui-numberbox" value="${dxBean.leaveSignAmountOfWeek}" style="width:152px;" /> 万元
							</td>
						</tr>
						
						<tr>
							<td align="right">决策：</td>
							<td><select id="decision" name="decision.decision" value="${creditApproval.decision}"
									data-options="required:true,validType:['length[0,50]']
									,onChange: function(decisionVal){
										if(decisionVal == '00'){
											$('#noPassReasonDiv').show();//显示div   
											toggleValidate('#noPassReasonDiv',true);
											$('#operatorDiv').hide();//隐藏div  
											toggleValidate('#operatorDiv',false);
											$('#decisionCodeDiv').hide();//隐藏div 
											toggleValidate('#decisionCodeDiv',false);
											$('#decisionCodeDiv1').hide();//隐藏div 
											toggleValidate('#decisionCodeDiv1',false);
											toggleValidate('amountDiv',false);
											$('#feedbackSellDescribeDiv1').hide();//隐藏div  
											toggleValidate('#feedbackSellDescribeDiv1',false);
											$('#feedbackSellDescribeDiv2').hide();//隐藏div  
											toggleValidate('#feedbackSellDescribeDiv2',false);
										}else if(decisionVal == '01'){
											$('#noPassReasonDiv').hide();//隐藏div    
											toggleValidate('#noPassReasonDiv',false);
											$('#operatorDiv').hide();//隐藏div  
											toggleValidate('#operatorDiv',false);
											$('#decisionCodeDiv').hide();//隐藏div 
											toggleValidate('#decisionCodeDiv',false);
											$('#decisionCodeDiv1').hide();//隐藏div  
											toggleValidate('#decisionCodeDiv1',false);
											toggleValidate('amountDiv',true);
											$('#feedbackSellDescribeDiv1').hide();//隐藏div  
											toggleValidate('#feedbackSellDescribeDiv1',false);
											$('#feedbackSellDescribeDiv2').hide();//隐藏div  
											toggleValidate('#feedbackSellDescribeDiv2',false);
										}else if(decisionVal == '05'){
											$('#noPassReasonDiv').hide();//隐藏div    
											toggleValidate('#noPassReasonDiv',false);
											$('#operatorDiv').hide();//隐藏div  
											toggleValidate('#operatorDiv',false);
											$('#decisionCodeDiv').hide();//隐藏div 
											toggleValidate('#decisionCodeDiv',false);
											$('#decisionCodeDiv1').show();//显示div  
											toggleValidate('#decisionCodeDiv1',true);
											toggleValidate('amountDiv',true);
											$('#feedbackSellDescribeDiv1').show();//反馈信息div  
											toggleValidate('#feedbackSellDescribeDiv1',true);
											$('#feedbackSellDescribeDiv2').show();//反馈信息div  
											toggleValidate('#feedbackSellDescribeDiv2',true);
										}else if(decisionVal == '04'){
											$('#noPassReasonDiv').hide();//隐藏div  退回原因  
											toggleValidate('#noPassReasonDiv',false);
											$('#operatorDiv').show();//显示div  高级处理人
											toggleValidate('#operatorDiv',true);
											$('#decisionCodeDiv').show();//显示div 违例码
											toggleValidate('#decisionCodeDiv',true);
											$('#decisionCodeDiv1').hide();//隐藏div  拒件码
											toggleValidate('#decisionCodeDiv1',false);
											toggleValidate('amountDiv',true);
											$('#feedbackSellDescribeDiv1').hide();//隐藏div  
											toggleValidate('#feedbackSellDescribeDiv1',false);
											$('#feedbackSellDescribeDiv2').hide();//隐藏div  
											toggleValidate('#feedbackSellDescribeDiv2',false);
										}else{
											$('#noPassReasonDiv').hide();//隐藏div    
											toggleValidate('#noPassReasonDiv',false);
											$('#operatorDiv').hide();//隐藏div  
											toggleValidate('#operatorDiv',false);
											$('#decisionCodeDiv').hide();//隐藏div 
											toggleValidate('#decisionCodeDiv',false);
											$('#decisionCodeDiv1').hide();//隐藏div  
											toggleValidate('#decisionCodeDiv1',false);
											toggleValidate('amountDiv',true);
											$('#feedbackSellDescribeDiv1').hide();//隐藏div  
											toggleValidate('#feedbackSellDescribeDiv1',false);
											$('#feedbackSellDescribeDiv2').hide();//隐藏div  
											toggleValidate('#feedbackSellDescribeDiv2',false);
										}
							    	}" 
									class="easyui-combobox" editable="false" style="width:152px;">
									<option value="">请选择</option>
									<option value="00" <c:if test="${'00' eq creditApproval.decision}">selected</c:if>>退回</option>
									<option value="01" <c:if test="${'01' eq creditApproval.decision}">selected</c:if>>通过</option>
									<option value="04" <c:if test="${'04' eq creditApproval.decision}">selected</c:if>>特殊审批</option>
									<option value="05" <c:if test="${'05' eq creditApproval.decision}">selected</c:if>>拒贷</option>
									<option value="06" <c:if test="${'06' eq creditApproval.decision}">selected</c:if>>推送线上</option>
								</select>
							</td>
							<td align="right" colspan="4">
								<div id ="noPassReasonDiv">
									退回原因：
									<textarea id="returnMsg" name="decision.returnMsg" value="${creditApproval.returnMsg}" class="textbox easyui-validatebox"
									data-options="required:true,validType:['length[0,500]']" 
									style="resize: none;width:325px;height:50px!important;vertical-align:middle;">${creditApproval.returnMsg}</textarea>
								</div>
								<div id ="operatorDiv">
									高级处理人：
									<input id="operator" name="higtManagerPeople" type="text"
										data-options="validType:['length[0,30]']" editable="false"
										class="easyui-combobox" style="width:304px;"/>
								</div>
								<div id ="decisionCodeDiv">
									违例码：
									<input id="decisionCode1" name="decision.decisionCode1" 
										value="${creditApproval.decisionCode1}" 
										type="text" data-options="validType:['length[0,50]']" 
										class="easyui-combobox" editable="false" style="width:152px;"/>
									<input id="decisionCode2" name="decision.decisionCode2"  
										value="${creditApproval.decisionCode2}" 
										type="text" data-options="validType:['length[0,50]']" 
										class="easyui-combobox" editable="false" style="width:152px;"/>
								</div>
								<div id ="decisionCodeDiv1">
									拒件码：
									<input id="decisionCode3" name="decision.decisionCode3" 
										value="${creditApproval.decisionCode3}" 
										type="text" data-options="validType:['length[0,50]']" 
										class="easyui-combobox" editable="false" style="width:152px;"/>
									<input id="decisionCode4" name="decision.decisionCode4" 
										value="${creditApproval.decisionCode4}" 
										type="text" data-options="validType:['length[0,50]']" 
										class="easyui-combobox" editable="false" style="width:152px;"/>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div id ="feedbackSellDescribeDiv1">
									反馈销售描述：
								</div>
							</td>
							<td colspan="5">
								<div id="feedbackSellDescribeDiv2">
									<textarea id="feedbackDescribe" name="feedbackDescribe" value="${feedbackDescribe}" class="textbox easyui-validatebox"
									style="resize: none;width:625px;height:50px!important;vertical-align:middle;">${feedbackDescribe}</textarea>
								</div>
							</td>
						</tr>
						
						<c:if test="${null != returnReasons && '' != returnReasons}">
							<tr>
								<td>
									特殊审批退回原因:
								</td>
								<td colspan="5">
									<textarea value="${returnReasons}" class="textbox easyui-validatebox"
										style="resize: none;width:625px;height:50px!important;vertical-align:middle;background-color: #F0F0F0;" disabled="disabled">${returnReasons}</textarea>
								</td>
							</tr>
						</c:if>
						<tr>
							<td align="right">备注：</td>
								<td colspan="5">
								<textarea id="remarks" name="decision.remarks"
								value="${creditApproval.remarks}" class="textbox easyui-validatebox" data-options="validType:['length[0,100]']"
								style="resize: none;width:625px;height:50px!important;">${creditApproval.remarks}</textarea></td>
						</tr>
						<tr>
							<td>
								<td colspan="6">
									<span style="float: right; padding-right: 20px;">
										<input type="button" value="保存" class="btn" onclick="updateFunction('save')"/>
										<input type="button" value="提交" class="btn" onclick="updateFunction('submit')"/>
										<input type="button" value="返回" class="btn" onclick="javascript:back()"/>
									</span>
								</td>
							</td>	
						</tr>
					</table>
				</td></tr>
				<tr>
					<td colspan="9">
						<div style="font-size: 14px;" >
							<strong>反欺诈处理</strong>
							<hr width="100%" size=2 color="#D3D3D3" noshade>
						</div>
					</td>
				</tr>
			 	<tr><td>
			 		<table id="creditAntifraud">
			 			<tr>
							<td>
							<input type="button" value="提交反欺诈" class="btn" onclick="submitAntifraud('antifraud')"/></td>
							<td>
<%--								<input id=submitInfo name="submitInfo" type="text"--%>
<%--									data-options="validType:['length[0,500]']"--%>
<%--									class="textbox easyui-validatebox" style="width:304px;"/>--%>
								<textarea id="submitInfo" name="submitInfo"  value="" class="textbox easyui-validatebox" data-options="validType:['length[0,500]']" 
										style="resize: none;width:600px;height:50px!important;"></textarea>
							</td>
						</tr>
<%--						<tr>--%>
<%--							<td><input type="button" name="relieveAntifraud" value="解除反欺诈"/></td>--%>
<%--							<td><input id="relieveAntifraud" name="relieveAntifraud" type="text" data-options="validType:['length[0,50]']" --%>
<%--								class="easyui-combobox" editable="false" />--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--						<tr>--%>
<%--							<td></td>--%>
<%--							<td ><textarea id="creditAntifraud" name="creditAntifraud.remarks" style="resize: none;width:304px;height:60px;"></textarea></td>--%>
<%--						</tr>--%>
					</table>
				</td></tr>
			</table>
		  </form>
		</div>
		<div title="流程报告" data-options="href:'${basePath}bpm/getBpmLogs.do?bizKey=${bean.appId}'" style="width: 100%;padding:10px"></div>
	</div>

</div>
</div>
</div>
</div>
</div>

</body>
<script type="text/javascript" >

window.parent.openMask();
$(window).load(function (){
	window.parent.closeMask();
});
</script>

<script type="text/javascript">
//只读页 设置为只读
function tabsReadOnly(redinfo){

	redinfo.find("input[type='radio']").attr('disabled',true);
	redinfo.find("input[type='hidden']").attr('disabled',true);
	redinfo.find("input[type='checkbox']").attr('disabled',true);
	redinfo.find("textarea").attr('disabled',true);
	redinfo.find("input[value='添加']").attr('disabled',true);
	redinfo.find("input[value='算']").attr('disabled',true);
	redinfo.find("input[value='全']").attr('disabled',true);

	redinfo.find('.easyui-validatebox').attr('disabled', 'disabled');
	redinfo.find('.easyui-validatebox').validatebox('disableValidation');
	redinfo.find('.easyui-combobox').combo('disableValidation');
	redinfo.find('.easyui-numberbox').numberbox('disableValidation');
	redinfo.find('.easyui-datebox').datebox('disableValidation');
	redinfo.find('.easyui-combobox').combo('disable');
	redinfo.find('.easyui-numberbox').numberbox('disable');
	redinfo.find('.easyui-datebox').datebox('disable');
	redinfo.find('div.panel div.panel-tool>a.icon-cancel').hide();
	redinfo.find('div.panel div.panel-tool>a.icon-add').hide();
	redinfo.find('div.panel div[class="panel-body accordion-body"]>table img[src$="deleteItem2.png"]').hide();
	redinfo.find('div.panel div[class="panel-body accordion-body"]>table img[src$="img/addItem.gif"]').hide();
// 	redinfo.find('div.panel div[class="panel-body accordion-body"]>table a').hide();
}

//切换 是否验证
function toggleValidate(objId,isValidete){
	var state=!isValidete;
	var obj=$(objId);
	obj.find('.easyui-validatebox').validatebox({novalidate:state});
	//obj.find('.easyui-combobox').combobox({novalidate:state});
	obj.find('.easyui-numberbox').validatebox({novalidate:state});
	obj.find('.easyui-datebox').datebox({novalidate:state});
}

//更新保存
function updateFunction(buttonType) {
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	//验证表单验证是否通过
	if(false == $('#updateForm').form('validate') ){
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}

	if('submit' == buttonType){
		if('' == $("#approvalInfo").find("#decision").combobox('getValue')){
			$.messager.confirm('消息', "请选择决策！");
			return;
		}
		if('04'==$("#approvalInfo").find("#decision").combobox('getValue')){
			if($("#approvalInfo").find("#operator").combobox('getValue') == ''){
				$.messager.confirm('消息', "请选择高级处理人！");
				return;
			}
		}
		if('05' == $("#approvalInfo").find("#decision").combobox('getValue')){
			if('' == $("#approvalInfo").find("#decisionCode3").combobox('getValue') || '' == $("#approvalInfo").find("#decisionCode4").combobox('getValue')){
				$.messager.confirm('消息', "请选择拒贷码！");
				return;
			}
			if('' == $("#approvalInfo").find("#feedbackDescribe").val()){
				$.messager.confirm('消息', "请填写反馈销售描述！");
				return;
			}
		}
	}
	
	// 验证精英贷测试方案
	var v = $("#approvalInfo").find("#secondTestCode").attr("class");
	if(v.indexOf('validate') != -1) {
		if($("#approvalInfo").find("input[name='decision.secondTestCode']").val() == "" || 
				$("#approvalInfo").find("input[name='decision.secondTestCode']").val()==null) {
			$.messager.alert('消息', "请选择测试方案！","warnning");
			return;
		}
	}
	
	// 鼎轩剩余资金校验
	var pVal = $("#approvalInfo").find("input[name='decision.product']").val();
	if(pVal.indexOf('DX')!=-1) {
		var appAmount = $("#approvalInfo").find('#htje').val();
		var appLeaveAmount = $('#leaveAmountOfWeek').val();
		var subAmount = appLeaveAmount*10000 - appAmount;
		if(subAmount < 0) {
			$.messager.alert('消息', "审批合同金额已超出鼎轩剩余融资金额！","warnning");
			return;
		}
	}
	
	//弹出异步加载 遮罩
 	try{  
 		window.parent.openMask();
	}catch(e){
		//按钮失效防点击
		$(".btn").attr("disabled", true);
	}
	var params = $('#updateForm').serialize();
	
	$.ajax({
		type : "POST",
		url : "<%=basePath%>credit/approval/save.do",
		data : params+"&buttonType="+buttonType,
		success : function(data) {
			//关闭遮罩，弹出消息框
			try{  
				window.parent.closeMask();
			}catch(e){
				$(".btn").removeAttr("disabled");
			}
			if ("true"==data.success) {
				$.messager.alert('消息', "操作成功", 'info', function(){
					$("input[name='decision.id']").val(data.message);
//	                var url= "<%=basePath%>" + "credit/decision/query.do";
//					window.location=url;
					//window.history.go(-1);
					if('submit' == buttonType){
						window.parent.removeTab();
					}
            	});
            } else {				
    			$.messager.alert('消息', data.message);
				//按钮生效
            }
		},
		error : function() {
			//关闭遮罩，弹出消息框
			try{  
				window.parent.closeMask();
			}catch(e){
				$(".btn").removeAttr("disabled");
			}
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
		}
	}); 
}

function submitAntifraud(buttonType){
//弹出异步加载 遮罩
	window.parent.openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "credit/antifraud/save.do",
		data : params+"&buttonType="+buttonType,
		success : function(data) {
			//关闭遮罩，弹出消息框
			window.parent.closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					//window.history.go(-1);
					//location.replace(location.href);
					//return;
					window.parent.removeTab();
				});
            } else {				
    			$.messager.alert('消息', data.message);
				//按钮生效
				$(".btn").removeAttr("disabled");
            }
		},
		error : function() {
			//关闭遮罩，弹出消息框
			window.parent.closeMask();
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
	});
}

/**
 * 改变 月还款额和合同金额（根据产品类型和借款金额）、借款金额和合同金额（根据产品类型和月还款额）、借款金额和月还款额（根据产品类型和合同金额）
 * url		访问地址
 * params	出入参数
 * type 	yhkje fkje
 */
function updateJe(url, params, type){
	$.ajax({
		type : "POST",
		url : url,
		data : params,
		success : function(data) {
			//关闭遮罩，弹出消息框
			if ("true"==data.success) {
					var je = data.message.split(",");
				if('jkje' == type){
					$("#approvalInfo").find("#yhkje2").numberbox('setValue', je[0]);
					$("#approvalInfo").find("#htje").numberbox('setValue', je[1]);					
				}else if('yhkje' == type){
					$("#approvalInfo").find("#amount").numberbox('setValue', je[0]/10000);
					$("#approvalInfo").find("#htje").numberbox('setValue', je[1]);		
				}else if('htje' == type){
					$("#approvalInfo").find("#amount").numberbox('setValue', je[0]/10000);
					$("#approvalInfo").find("#yhkje2").numberbox('setValue', je[1]);
				}
            }
		},
		error : function() {
		}
	});
}

/**
 * 根据 产品类型和借款金额 计算 月还款额和合同金额
 */
function updateYhkjeHtje(){
	if($('#approvalInfo').find('#amount').val() != '' && $('#approvalInfo').find('#product').combobox('getValue') != ''){
		updateJe("<%=basePath%>" + "credit/verify/getJe.do", "yhkje=0.00&htje=0.00&jkje=" + $('#approvalInfo').find('#amount').val() + "&productName=" + $('#approvalInfo').find('#product').combobox('getValue'), "jkje");
	}
}

/**
 * 根据 产品类型和月还款额 计算 借款金额和合同金额
 */
function updateJkjeHtje(){
	if($('#approvalInfo').find('#yhkje2').val() != '' && $('#approvalInfo').find('#product').combobox('getValue') != ''){
		updateJe("<%=basePath%>" + "credit/verify/getJe.do", "jkje=0.00&htje=0.00&yhkje=" + $('#approvalInfo').find('#yhkje2').val() + "&productName=" + $('#approvalInfo').find('#product').combobox('getValue'), "yhkje");
	}
}

/**
 * 根据 产品类型和合同金额 计算 借款金额和月还款额
 */
function updateJkjeYhkje(){
	if($('#approvalInfo').find('#htje').val() != '' && $('#approvalInfo').find('#product').combobox('getValue') != ''){
		updateJe("<%=basePath%>" + "credit/verify/getJe.do", "jkje=0.00&yhkje=0.00&htje=" + $('#approvalInfo').find('#htje').val() + "&productName=" + $('#approvalInfo').find('#product').combobox('getValue'), "htje");
	}
}

//返回
function back(){
	//window.history.go(-1);
	window.parent.removeTab();
}
//页面加载完动作
$(document).ready(function (){
	
	if('${creditApproval.decision}' == '00'){
		$('#noPassReasonDiv').show();//显示div   
		toggleValidate('#noPassReasonDiv',true);
		$('#operatorDiv').hide();//隐藏div  
		toggleValidate('#operatorDiv',false);
		$('#decisionCodeDiv').hide();//隐藏div 
		toggleValidate('#decisionCodeDiv',false);
		$('#decisionCodeDiv1').hide();//隐藏div 
		toggleValidate('#decisionCodeDiv1',false);
		toggleValidate('amountDiv',false);
		$('#feedbackSellDescribeDiv1').hide();//隐藏div  
		toggleValidate('#feedbackSellDescribeDiv1',false);
		$('#feedbackSellDescribeDiv2').hide();//隐藏div  
		toggleValidate('#feedbackSellDescribeDiv2',false);
	}else if('${creditApproval.decision}' == '01'){
		$('#noPassReasonDiv').hide();//隐藏div    
		toggleValidate('#noPassReasonDiv',false);
		$('#operatorDiv').hide();//隐藏div  
		toggleValidate('#operatorDiv',false);
		$('#decisionCodeDiv').hide();//隐藏div 
		toggleValidate('#decisionCodeDiv',false);
		$('#decisionCodeDiv1').hide();//隐藏div  
		toggleValidate('#decisionCodeDiv1',false);
		toggleValidate('amountDiv',true);
		$('#feedbackSellDescribeDiv1').hide();//隐藏div  
		toggleValidate('#feedbackSellDescribeDiv1',false);
		$('#feedbackSellDescribeDiv2').hide();//隐藏div  
		toggleValidate('#feedbackSellDescribeDiv2',false);
	}else if('${creditApproval.decision}' == '05'){
		$('#noPassReasonDiv').hide();//隐藏div    
		toggleValidate('#noPassReasonDiv',false);
		$('#operatorDiv').hide();//隐藏div  
		toggleValidate('#operatorDiv',false);
		$('#decisionCodeDiv').hide();//隐藏div 
		toggleValidate('#decisionCodeDiv',false);
		$('#decisionCodeDiv1').show();//显示div  
		toggleValidate('#decisionCodeDiv1',true);
		toggleValidate('amountDiv',true);
		$('#feedbackSellDescribeDiv1').show();//反馈信息div  
		toggleValidate('#feedbackSellDescribeDiv1',true);
		$('#feedbackSellDescribeDiv2').show();//反馈信息div  
		toggleValidate('#feedbackSellDescribeDiv2',true);
	}else if('${creditApproval.decision}' == '04'){
		$('#noPassReasonDiv').hide();//隐藏div  退回原因  
		toggleValidate('#noPassReasonDiv',false);
		$('#operatorDiv').show();//显示div  高级处理人
		toggleValidate('#operatorDiv',true);
		$('#decisionCodeDiv').show();//显示div 违例码
		toggleValidate('#decisionCodeDiv',true);
		$('#decisionCodeDiv1').hide();//隐藏div  拒件码
		toggleValidate('#decisionCodeDiv1',false);
		toggleValidate('amountDiv',true);
		$('#feedbackSellDescribeDiv1').hide();//隐藏div  
		toggleValidate('#feedbackSellDescribeDiv1',false);
		$('#feedbackSellDescribeDiv2').hide();//隐藏div  
		toggleValidate('#feedbackSellDescribeDiv2',false);
	}else{
		$('#noPassReasonDiv').hide();//隐藏div    
		toggleValidate('#noPassReasonDiv',false);
		$('#operatorDiv').hide();//隐藏div  
		toggleValidate('#operatorDiv',false);
		$('#decisionCodeDiv').hide();//隐藏div 
		toggleValidate('#decisionCodeDiv',false);
		$('#decisionCodeDiv1').hide();//隐藏div  
		toggleValidate('#decisionCodeDiv1',false);
		toggleValidate('amountDiv',true);
		$('#feedbackSellDescribeDiv1').hide();//隐藏div  
		toggleValidate('#feedbackSellDescribeDiv1',false);
		$('#feedbackSellDescribeDiv2').hide();//隐藏div  
		toggleValidate('#feedbackSellDescribeDiv2',false);
	}
	
	var userurl=encodeURI("sys/user/listUserByOrgAndRole.do?orgId=${login_org.orgId}&roleNames=信用高级审批员");
	$("#operatorDiv").find("#operator").combobox("clear");
	$("#operatorDiv").find("#operator").combobox({
		url:userurl, valueField:'loginId', textField:'name',
		//添加空白行
		loadFilter:function(data){
			var opts = $(this).combobox('options');
	   		var emptyRow = {};
			emptyRow[opts.valueField] = '';
			emptyRow[opts.textField] = '请选择';
			data.unshift(emptyRow);
			return data;
		}
	});
			
	
	//拖动时 调节 下拉框 宽度
	$('#easyui_layout').layout('panel', 'east').panel({
		onResize:function(w,h){
			$("#verify_tabs").tabs('getSelected').find(".easyui-accordion").accordion("resize");
			return true;
		  }
		});
/*	
	//填充select数据 借款期限
	$("#verifylAppInfo").find("#period").combobox("clear");
	$("#verifylAppInfo").find("#period").combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:[{"keyProp":"1","keyValue":"12"},{"keyProp":"2","keyValue":"18"},{"keyProp":"3","keyValue":"24"},{"keyProp":"4","keyValue":"36"}]
	});
	
	$("#verifyInfo").find("#period").combobox("clear");
	$("#verifyInfo").find("#period").combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:[{"keyProp":"1","keyValue":"12"},{"keyProp":"2","keyValue":"18"},{"keyProp":"3","keyValue":"24"},{"keyProp":"4","keyValue":"36"}]
	});
	
	$("#approvalInfo").find("#period").combobox("clear");
	$("#approvalInfo").find("#period").combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:[{"keyProp":"1","keyValue":"12"},{"keyProp":"2","keyValue":"18"},{"keyProp":"3","keyValue":"24"},{"keyProp":"4","keyValue":"36"}]
	});
*/

	//填充select数据 渠道
	var channelurl="channeltotal/listjason.do";
	$("#verifylAppInfo").find("#belongChannel").combobox("clear");
	$("#verifylAppInfo").find('#belongChannel').combobox({
		url:channelurl,
		valueField:'channelCode', 
		textField:'channelName',
		onChange: function(newValue, oldValue){             
			$("#verifylAppInfo").find('#product').combobox('clear');
			var producturl = "product/hedao/listjason.do?type=3&belongChannel=" + encodeURI(newValue);
			$("#verifylAppInfo").find('#product').combobox('reload',producturl); 
		}
	});
	//填充select数据 产品
	var belongChannel = $("#verifylAppInfo").find('#belongChannel').combobox('getValue');
	var producturl = "product/hedao/listjason.do?type=3&belongChannel=" + encodeURI(belongChannel);
	$("#verifylAppInfo").find("#product").combobox("clear");
	$("#verifylAppInfo").find('#product').combobox({
		url:producturl,
		valueField:'name', 
		textField:'name'
	});
		
	//填充select数据 渠道
	var channelurl1="channeltotal/listjason.do";
	$("#verifyInfo").find("#belongChannel").combobox("clear");
	$("#verifyInfo").find('#belongChannel').combobox({
		url:channelurl1,
		valueField:'channelCode', 
		textField:'channelName',
		onChange: function(newValue, oldValue){             
			$("#verifyInfo").find('#product').combobox('clear');
			var producturl1 = "product/hedao/listjason.do?type=3&flag=1&belongChannel=" + encodeURI(newValue);
			$("#verifyInfo").find('#product').combobox('reload',producturl1); 
		}
	});
	//填充select数据 产品
	var belongChannel1 = $("#verifyInfo").find('#belongChannel').combobox('getValue');
	var producturl1 = "product/hedao/listjason.do?type=3&flag=1&belongChannel=" + encodeURI(belongChannel1);
	$("#verifyInfo").find("#product").combobox("clear");
	$("#verifyInfo").find('#product').combobox({
		url:producturl1,
		valueField:'name', 
		textField:'name',
		onChange: function(productVal){
			if($('#verifyInfo').find('#amount').val() != '')
				updateJe("<%=basePath%>" + "credit/verify/getYhkje.do", "fkje=" + $('#verifyInfo').find('#amount').val() + "&productName=" + productVal, "yhkje");
		}	
	});
	
	//填充select数据 渠道
	var channelurl2="channeltotal/listjason.do?state=1";
	$("#approvalInfo").find("#belongChannel").combobox("clear");
	$("#approvalInfo").find('#belongChannel').combobox({
		url:channelurl2,
		valueField:'channelCode', 
		textField:'channelName',
		onChange: function(newValue, oldValue){             
			$("#approvalInfo").find('#product').combobox('clear');
			var producturl2 = "product/hedao/listjason.do?type=3&state=1&flag=1&belongChannel=" + encodeURI(newValue);
			$("#approvalInfo").find('#product').combobox('reload',producturl2); 
		}
	});
	//填充select数据 产品
	var belongChannel2 = $("#approvalInfo").find('#belongChannel').combobox('getValue');
	var producturl2 = "product/hedao/listjason.do?type=3&state=1&flag=1&belongChannel=" + encodeURI(belongChannel2);
	$("#approvalInfo").find("#product").combobox("clear");
	$("#approvalInfo").find('#product').combobox({
		url:producturl2,
		valueField:'name', 
		textField:'name',
		onChange: function(productVal){
			if($('#approvalInfo').find('#amount').val() != ''){
				updateYhkjeHtje();
			}else if($('#approvalInfo').find('#yhkje2').val() != ''){
				updateJkjeHtje();
			}else if($('#approvalInfo').find('#htje').val() != ''){
				updateJkjeYhkje();
			}	
		}	
	});
	
	//填充select数据 拒件码01
    var refusedCode01url = "sys/datadictionary/listjason.do?keyName=refusedCode01";
		$("#decisionCode3").combobox("clear");
		$('#decisionCode3').combobox({
			url: refusedCode01url,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
	            $('#decisionCode4').combobox('clear');
	            var refusedCode02url = "sys/datadictionary/listjason.do?keyName=refusedCode02&parentKeyProp=" + encodeURI(newValue);
	            $('#decisionCode4').combobox('reload',refusedCode02url); 
       		}
		});
		//填充select数据 拒件码02
		var decisionCode3 = $('#decisionCode3').combobox('getValue');
		var refusedCode02url = "sys/datadictionary/listjason.do?keyName=refusedCode02&parentKeyProp=" + encodeURI(decisionCode3);
		   $("#decisionCode4").combobox("clear");
		$('#decisionCode4').combobox({
			url: refusedCode02url,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
	
});

$(function(){
	$("input[comboname='decision.product']").combobox({
		onSelect: function(){	
			var newVal = $(this).combobox("getValue");
			//决策产品更改为精英JM时进行精英贷测试方案显示
			if((newVal.indexOf('精英贷')!=-1) && (newVal.indexOf('JM')!=-1)) {
				$("#approvalInfo").find(".JYDTestScheme").css("display","");
				$("#approvalInfo").find("#firstTestCode").val("A01");
				$("#approvalInfo").find("#secondTestCode").combobox("clear");
				
				$("#approvalInfo").find("#secondTestCode").combobox({ 
					url:"sys/datadictionary/listjason.do?keyName=second_test_code&parentKeyProp=A01&random=Math.random()",
					valueField:'keyProp', 
					textField:'keyValue',
					data: [{'keyProp':"",'keyValue':'请选择'}],
					onSelect: function(){
						var secondText = $(this).combobox("getText");
						$("#approvalInfo").find("#secondTestValue").val(secondText);
					}
				});
				$("#approvalInfo").find("#secondTestCode").addClass("validate");
			} else {
				$("#approvalInfo").find(".JYDTestScheme").css("display","none");
				$("#approvalInfo").find("#secondTestCode").removeClass("validate");
				$("#approvalInfo").find("input[name='decision.firstTestCode']").val("");
				$("#approvalInfo").find("input[name='decision.secondTestCode']").val("");
				$("#approvalInfo").find("input[name='decision.secondTestValue']").val("");
			}
			
			//决策产品更改为鼎轩产品时进行鼎轩融资信息显示
			if((newVal.indexOf('DX')!=-1)) {
				$("#approvalInfo").find("#dxShow").css("display","");
			} else {
				$("#approvalInfo").find("#dxShow").css("display","none");
			}
		}
	});
	
	// 积木精英贷测试方案显示
	var pVal = $("#approvalInfo").find("#product").val();
	if((pVal.indexOf('精英贷')!=-1) && (pVal.indexOf('JM')!=-1)){
		$("#approvalInfo").find(".JYDTestScheme").css("display","");
		$("#approvalInfo").find("#secondTestCode").addClass("validate");
		$("#approvalInfo").find("#secondTestCode").combobox({ 
				url:"sys/datadictionary/listjason.do?keyName=second_test_code&parentKeyProp=A01&random=Math.random()",
				valueField:'keyProp', 
				textField:'keyValue',
				onSelect: function(){
					var secondText = $(this).combobox("getText");
					$("#approvalInfo").find("#secondTestValue").val(secondText);
				}
		});
	}else {
		$("#approvalInfo").find(".JYDTestScheme").css("display","none");
		$("#approvalInfo").find("#secondTestCode").removeClass("validate");
	}
	
	// 鼎轩融资信息显示
	if((pVal.indexOf('DX')!=-1)) {
		$("#approvalInfo").find("#dxShow").css("display","");
	} else {
		$("#approvalInfo").find("#dxShow").css("display","none");
	}
});
</script>
</html>


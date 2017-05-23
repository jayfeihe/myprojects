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
<title>房贷特殊决策表更新</title>
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
		<a href="javascript:void(0);">特殊审批</a>
<%--		<span style="float: right; padding-right: 20px;">--%>
<%--			<input type="button" value="返回" class="btn" onclick="javascript:back()"/>--%>
<%--		</span>--%>
		<span style="padding-left: 20px; color: red;">
		预审批评分等级：${bean.ruleGrade}
		</span>
		<span style="padding-left: 20px; color: red;">
			授信额度：15万元
		</span>
	</p>
<div class="content">
<div class="easyui-layout" id="easyui_layout" style="width:100%;height:730px;" data-options="fit:true">
<div data-options="region:'center',split:true,border:false" >
	<div class="easyui-tabs" data-options="fit:true,onLoad:tabsReadOnly">
		<div title="影像资料" data-options="href:'${basePath}img/imgSlidePath.do?appId=${bean.appId}'" style="width: 100%;padding:10px"></div>
		<div title="申请信息" data-options="href:'${basePath}house/app/read1.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		<div title="综合信息" data-options="href:'${basePath}house/verify/complexInfo.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
	</div>
</div>

<div data-options="region:'east',split:true,border:false" style="width:800px;">
<form id="updateForm" >
	<input type="hidden" name="appId" value="${bean.appId}" />
	
	<div class="easyui-tabs" id="verify_tabs" style="height:730px;" data-options="fit:true,onLoad:tabsReadOnly">
		<div title="信息核查" data-options="href:'${basePath}house/verify/infoCheck.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		<div title="影像摘要" data-options="href:'${basePath}house/verify/imageSummary.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		<div title="信用报告" data-options="href:'${basePath}house/verify/creditReport.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		<div title="面审调查" data-options="href:'${basePath}house/verify/interviewSurvey.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		<div title="审核决策"  style="width: 100%;padding:10px">
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
							<td><input type="text" value="${bean.amount/10000}" class="textbox easyui-numberbox"
								style="width:128px;" disabled="disabled" data-options="min:0,precision:2"/>万元</td>
<%--							<td align="right">借款期限：</td>--%>
<%--							<td><input id="period" type="text" data-options="required:true,validType:['length[0,10]']" --%>
<%--								class="easyui-combobox"  value="${bean.period}" editable="false" style="width:152px;" disabled="disabled"/></td>--%>
							<td align="right">渠道：</td>
							<td><input id="belongChannel" type="text" data-options="required:true,validType:['length[0,50]']" 
								class="easyui-combobox"  value="${bean.belongChannel}" editable="false" style="width:152px;" disabled="disabled"/></td>
							<td align="right">产品：</td>
							<td><input id="product" type="text" data-options="required:true,validType:['length[0,50]']" 
								class="easyui-combobox"  value="${bean.product}" editable="false" style="width:152px;" disabled="disabled"/></td>
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
			 				<td>
								<td align="right">借款金额:</td>
								<td><input value="${houseDecision1.amount/10000}" type="text"
										data-options="required:true,min:0,precision:2" class="textbox easyui-numberbox" style="width:128px;" disabled="disabled"/>万元
								</td>
<%--								<td align="light">借款期限:</td>--%>
<%--								<td ><input id="period" value="${houseDecision1.period}" type="text" data-options="validType:['length[0,10]']" --%>
<%--									class="easyui-combobox" editable="false" style="width:152px;" disabled="disabled"/>--%>
<%--								</td>--%>
								<td align="right">渠道:
									<input id="belongChannel" type="text" class="easyui-combobox" 
									data-options="validType:['length[0,50]']" editable="false" value="${houseDecision1.belongChannel}" disabled="disabled"/>
								</td>
								<td align="right">产品：
									<input id="product" value="${houseDecision1.product}" type="text"  data-options="validType:['length[0,50]']" 
									class="easyui-combobox" editable="false" style="width:152px;" disabled="disabled"/>
								</td>
							</td>
						</tr>
						<tr>
							<td>
								<td>月还款额:</td>
								<td>
									<input id="yhkje1" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${yhkje1}" style="width:152px;" disabled="disabled"/>
								</td>
								<td colspan="4">
									 &nbsp;
								</td>
							</td>
						</tr>
						<tr>
							<td>
								<td>审核人：</td>
								<td><input value="${houseDecision1.operator}" type="text"
											data-options="validType:['length[0,30]']"
											class="textbox easyui-validatebox" style="width:148px;" disabled="disabled"/></td>
								<td colspan="4">&nbsp;</td>
							</td>
						</tr>
						<tr>
							<td id="houseDecision_1">
								<td align="right">建议:</td>
								<td>
									<select disabled="disabled" id="decision" value="${houseDecision1.decision}"
									data-options="validType:['length[0,50]']
									,onChange: function(decisionVal){
										if(decisionVal == '02'){
											$('#noPassReasonDiv1').hide();//隐藏div   
											$('#decisionCodeDiv1').show();//显示div  
											$('#returnCodeDiv1').hide();//隐藏div 
										}else if(decisionVal == '03'){
											$('#noPassReasonDiv1').hide();//隐藏div   
											$('#decisionCodeDiv1').show();//显示div 
											$('#returnCodeDiv1').hide();//隐藏div  
										}else if(decisionVal == '00'){
											$('#noPassReasonDiv1').show();//显示div   
											$('#decisionCodeDiv1').hide();//隐藏div  
											$('#returnCodeDiv1').hide();//隐藏div 
										}
							    	}" 
									class="easyui-combobox" editable="false" style="width:152px;">
									<option value="">请选择</option>
									<option value="02">拟通过</option>
									<option value="03">拟拒贷</option>
									<option value="00">退回</option>
								</select>
								</td>
								<td align="right" colspan="4">
								<c:if test="${houseDecision1.decision eq '00'}">
									<div id ="noPassReasonDiv1">
										退回原因:<textarea value="${houseDecision1.returnMsg}" class="textbox easyui-validatebox"
											data-options="validType:['length[0,500]']" 
											style="resize: none;width:325px;height:50px!important;vertical-align:middle;background-color: #F0F0F0;" disabled="disabled">${houseDecision1.returnMsg}</textarea>
									</div>
								</c:if>
								<c:if test="${houseDecision1.decision eq '03'}">
									<div id ="decisionCodeDiv1">
										违例码:
										<input value="${houseDecision1.decisionCode1}" type="text" data-options="validType:['length[0,50]']" 
											class="easyui-combobox" editable="false" style="width:152px;" disabled="disabled"/>
										<input value="${houseDecision1.decisionCode2}" type="text" data-options="validType:['length[0,50]']" 
											class="easyui-combobox" editable="false" style="width:152px;" disabled="disabled"/>
									</div>
								</c:if>
									<div id ="returnCodeDiv1">
										拒件码：
										<input id="decisionCode3" value="${houseDecision1.decisionCode3}" type="text" data-options="validType:['length[0,50]']" 
											class="easyui-combobox" editable="false" style="width:152px;" disabled="disabled"/>
										<input id="decisionCode4" value="${houseDecision1.decisionCode4}" type="text" data-options="validType:['length[0,50]']" 
											class="easyui-combobox" editable="false" style="width:152px;" disabled="disabled"/>
									</div>
								</td>
							</td>
						</tr>
						<tr>
							<td>
								<td>备注：</td>
								<td colspan="5">
									<textarea class="textbox easyui-validatebox" data-options="validType:['length[0,500]']" 
									style="font-size:12px;resize: none;width:625px;height:50px!important;background-color: #F0F0F0;" disabled="disabled">${houseDecision1.remarks}</textarea>
								</td>
							</td>							
						</tr>
					</table>
				</td></tr>
				<tr>
					<td colspan="9">
						<div style="font-size: 14px;" >
							<strong>审批决策</strong>
							<hr width="100%" size=2 color="#D3D3D3" noshade>
						</div>
					</td>
				</tr>
				<tr><td>
			 		<table id="approvalInfo">
			 			<tr>
			 				<td>
								<td align="right">借款金额:</td>
								<td><input value="${houseDecision2.amount/10000}" type="text"
										data-options="required:true,min:0,precision:2" class="textbox easyui-numberbox" style="width:128px;" disabled="disabled"/>万元
								</td>
<%--								<td align="light">借款期限:</td>--%>
<%--								<td ><input id="period" value="${houseDecision2.period}" type="text" data-options="validType:['length[0,10]']" --%>
<%--									class="easyui-combobox" editable="false" style="width:152px;" disabled="disabled"/>--%>
<%--								</td>--%>
								<td align="right">渠道:
									<input id="belongChannel" type="text" class="easyui-combobox" 
									data-options="validType:['length[0,50]']" editable="false" value="${houseDecision2.belongChannel}" disabled="disabled"/>
								</td>
								<td align="right">产品：
									<input id="product" value="${houseDecision2.product}" type="text"  data-options="validType:['length[0,50]']" 
									class="easyui-combobox" editable="false" style="width:152px;" disabled="disabled"/>
								</td>
							</td>
						</tr>
						<tr>
							<td>
								<td>月还款额:</td>
								<td>
									<input id="yhkje2" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${yhkje2}" style="width:152px;" disabled="disabled"/>
								</td>
								<td colspan="4">
									 &nbsp;
								</td>
							</td>
						</tr>
						<tr>
							<td>
								<td>审批人：</td>
								<td><input value="${houseDecision2.operator}" type="text"
											data-options="validType:['length[0,30]']"
											class="textbox easyui-validatebox" style="width:148px;" disabled="disabled"/>
									<input type="hidden" name="houseApprovalOperator" value="${houseDecision2.operator}" /></td>
								<td colspan="4">&nbsp;</td>
							</td>
						</tr>
						<tr>
							<td>
								<td align="right">决策：</td>
								<td><select disabled="disabled" id="decision1" value="${houseDecision2.decision}"
										data-options="validType:['length[0,50]']
										,onChange: function(decisionVal){
											if(decisionVal == '00'){
												$('#noPassReasonDiv2').show();//显示div   
												$('#decisionCodeDiv2').hide();//隐藏div 
												$('#returnCodeDiv2').hide();//隐藏div 
											}else if(decisionVal == '01'){
												$('#noPassReasonDiv2').hide();//隐藏div    
												$('#decisionCodeDiv2').hide();//隐藏div 
												$('#returnCodeDiv2').hide();//隐藏div  
											}else if(decisionVal == '05'){
												$('#noPassReasonDiv2').hide();//隐藏div    
												$('#decisionCodeDiv2').hide();//隐藏div 
												$('#returnCodeDiv2').show();//显示div  
											}else if(decisionVal == '04'){
												$('#noPassReasonDiv2').hide();//隐藏div    
												$('#decisionCodeDiv2').show();//显示div 
												$('#returnCodeDiv2').hide();//隐藏div   
											}else{
												$('#noPassReasonDiv2').hide();//隐藏div    
												$('#decisionCodeDiv2').hide();//隐藏div 
												$('#returnCodeDiv2').hide();//隐藏div  
											}
								    	}" 
										class="easyui-combobox" editable="false" style="width:152px;">
										<option value="">请选择</option>
										<option value="00">退回</option>
										<option value="01">通过</option>
										<option value="04">特殊审批</option>
										<option value="05">拒贷</option>
									</select>
								</td>
								<td align="right" colspan="4">
									<c:if test="${houseDecision2.decision eq '00'}">
										<div id ="noPassReasonDiv2">
											退回原因:<textarea value="${houseDecision2.returnMsg}" class="textbox easyui-validatebox"
												data-options="validType:['length[0,500]']" 
												style="resize: none;width:325px;height:50px~important;vertical-align:middle;background-color: #F0F0F0;" disabled="disabled">${houseDecision2.returnMsg}</textarea>
										</div>
									</c:if>
									<c:if test="${houseDecision2.decision eq '04'}">
										<div id ="decisionCodeDiv2">
											违例码:
											<input value="${houseDecision2.decisionCode1}" 
												type="text" data-options="validType:['length[0,50]']" class="easyui-combobox" editable="false" style="width:152px;" disabled="disabled"/>
											<input value="${houseDecision2.decisionCode2}" 
												type="text" data-options="validType:['length[0,50]']" class="easyui-combobox" editable="false" style="width:152px;" disabled="disabled"/>
										</div>
									</c:if>
									<c:if test="${houseDecision2.decision eq '05'}">
										<div id ="returnCodeDiv2">
											拒件码:
											<input id="decisionCode3" value="${houseDecision2.decisionCode3}" 
												type="text" data-options="validType:['length[0,50]']" class="easyui-combobox" editable="false" style="width:152px;" disabled="disabled"/>
											<input id="decisionCode4" value="${houseDecision2.decisionCode4}" 
												type="text" data-options="validType:['length[0,50]']" class="easyui-combobox" editable="false" style="width:152px;" disabled="disabled"/>
										</div>
									</c:if>
								</td>
							</td>
						</tr>
						<tr>
							<td>
								<td>备注：</td>
								<td colspan="5">
									<textarea class="textbox easyui-validatebox" data-options="validType:['length[0,500]']" 
									style="resize: none;width:625px;height:50px!important;background-color: #F0F0F0;" disabled="disabled">${houseDecision2.remarks}</textarea>
								</td>
							</td>								
						</tr>
					</table>
				</td></tr>
				
				<c:if test="${not empty houseFraud }">
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
												value:'${houseFraud.result }',
												data:[{'keyProp':'1','keyValue':'无问题'},
													  {'keyProp':'2','keyValue':'有问题'}]"/>
								</td>
								<td align="right">处理人：</td>
								<td>
									<input type="text" value="${houseFraud.operatorName }" class="textbox" disabled="disabled"/>
								</td>
							</tr>
							<tr>
								<td align="right">欺诈备注：</td>
								<td colspan="4">
									<textarea id="resultInfo" name="resultInfo" class="textbox" disabled="disabled"
									style="resize: none;width:304px;height:60px!important;">${houseFraud.resultInfo }</textarea>
								</td>
							</tr>
						</table>
					</td></tr>
				</c:if>
				
				<tr>
					<td colspan="9">
						<div style="font-size: 14px;" >
							<strong>特殊审批</strong>
							<hr width="100%" size=2 color="#D3D3D3" noshade>
						</div>
					</td>
				</tr>
				<tr><td>
			 		<table id="specialApprovalInfo">
			 			<tr>
			 				<td id="houseDecision_0">
			 					<input type="hidden" name="id" value="${bean.id}" />
					 			<input type="hidden" name="decision.id" value="${houseDecision3.id}" />
								<input type="hidden" name="decision.appId" value="${bean.appId}" />
								<input type="hidden" name="decision.type" value="3" />
								<input type="hidden" id="state" name="decision.state" value="${houseDecision3.state}" />
								<td align="right">借款金额:</td>
								<td><div id="amountDiv">
									<input id="amount" name="decision.amount" value="${houseDecision3.amount/10000}" type="text"
										data-options="required:true,min:0,precision:2" class="textbox easyui-numberbox" style="width:128px;" onchange="updateYhkjeHtje();"/>万元
									</div>
								</td>
<%--								<td align="light">借款期限:</td>--%>
<%--								<td ><input id="period" name="decision.period" <c:if test="${0 != houseDecision3.period}">value='${houseDecision3.period}'</c:if> type="text" --%>
<%--									data-options="required:true,validType:['length[0,10]']" class="easyui-combobox" editable="false" style="width:152px;"/>--%>
<%--								</td>--%>
								<td align="right">渠道:
									<input id="belongChannel" name="decision.belongChannel" type="text" class="easyui-combobox" 
									data-options="required:true,validType:['length[0,50]']" editable="false" value="${houseDecision3.belongChannel}"/>
								</td>
								<td align="right">产品：
									<input id="product" name="decision.product" type="text" value="${houseDecision3.product }"
									data-options="required:true,validType:['length[0,50]']" class="easyui-combobox" editable="false" style="width:152px;"/>
								</td>
							</td>
						</tr>
						<tr>
							<td>
								<td align="right">月还款额:</td>
								<td>
									<input id="yhkje3" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${yhkje3}" style="width:152px;" onchange="updateJkjeHtje();"/>
								</td>
								<td align="right">
									 合同金额：
								</td>
								<td>
									<input id="htje" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${htje}" style="width:152px;" onchange="updateJkjeYhkje();"/>
								</td>
							</td>
						</tr>
						<tr>
							<td id="houseDecision_1">
								<td align="right">决策:</td>
								
								<td>
									<select id="decision1" name="decision.decision" value="${houseDecision3.decision}"
										data-options="validType:['length[0,50]']
										,onChange: function(decisionVal){
											if(decisionVal == '' || decisionVal == '01' || decisionVal == '06'){
												$('#noPassReasonDiv').hide();//隐藏div 
												toggleValidate('#noPassReasonDiv',false);
												$('#returnCodeDiv').hide();//隐藏div 
												toggleValidate('#returnCodeDiv',false);
												toggleValidate('amountDiv',true);
												$('#feedbackSellDescribeDiv1').hide();//隐藏div  
												toggleValidate('#feedbackSellDescribeDiv1',false);
												$('#feedbackSellDescribeDiv2').hide();//隐藏div  
												toggleValidate('#feedbackSellDescribeDiv2',false);
											}else if(decisionVal == '00'){
												$('#noPassReasonDiv').show();//显示div 
												toggleValidate('#noPassReasonDiv',true);
												$('#returnCodeDiv').hide();//隐藏div 
												toggleValidate('#returnCodeDiv',false);
												toggleValidate('amountDiv',false);
												$('#feedbackSellDescribeDiv1').hide();//隐藏div  
												toggleValidate('#feedbackSellDescribeDiv1',false);
												$('#feedbackSellDescribeDiv2').hide();//隐藏div  
												toggleValidate('#feedbackSellDescribeDiv2',false);
											}else{
												$('#noPassReasonDiv').hide();//隐藏div 
												toggleValidate('#noPassReasonDiv',false);
												$('#returnCodeDiv').show();//显示div  
												toggleValidate('#returnCodeDiv',true);
												toggleValidate('amountDiv',true);
												$('#feedbackSellDescribeDiv1').show();//反馈销售描述div  
												toggleValidate('#feedbackSellDescribeDiv1',true);
												$('#feedbackSellDescribeDiv2').show();//反馈销售描述div  
												toggleValidate('#feedbackSellDescribeDiv2',true);
											}
								    	}"
										class="easyui-combobox" editable="false" style="width:152px;">
										<option value="">请选择</option>
										<option value="00">退回</option>
										<option value="01">通过</option>
										<option value="05">拒贷</option>
										<option value="06">推送线上</option>
									</select>
								</td>
								<td align="right" colspan="4">
									<div id ="noPassReasonDiv">
										退回原因:<textarea id="returnMsg" name="decision.returnMsg" value="${houseDecision3.returnMsg}" class="textbox easyui-validatebox"
											data-options="required:true,validType:['length[0,500]']" 
											style="resize: none;width:325px;height:50px!important;vertical-align:middle;">${houseDecision3.returnMsg}</textarea>
									</div>
									<div id ="returnCodeDiv">
										拒件码:
										<input id="decisionCode3" name="decision.decisionCode3" value="${houseDecision3.decisionCode3}" 
											type="text" data-options="validType:['length[0,50]']" class="easyui-combobox" editable="false" style="width:152px;" />
										<input id="decisionCode4" name="decision.decisionCode4" value="${houseDecision3.decisionCode4}" 
											type="text" data-options="validType:['length[0,50]']" class="easyui-combobox" editable="false" style="width:152px;" />
									</div>
								</td>
							</td>
						</tr>
						<tr>
							<td>
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
							</td>
						</tr>
						<tr>
							<td id="houseDecision_2">
								<td>备注：</td>
								<td colspan="5">
									<textarea id="remarks" name="decision.remarks" class="textbox easyui-validatebox" data-options="validType:['length[0,100]']"
									style="resize: none;width:625px;height:50px!important;">${houseDecision3.remarks}</textarea>
								</td>
							</td>								
						</tr>
						<tr>
							<td>
								<td colspan="6">
									<span style="float: right; padding-right: 20px;">
										<input type="button" value="保存" class="btn" onclick="saveOrSubForm('save')"/>
										<input type="button" value="提交" class="btn" onclick="saveOrSubForm('submit')"/>
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
			 		<table id="houseAntifraud">
			 			<tr>
							<td>
								<input type="button" value="提交反欺诈" class="btn" onclick="submitAntifraud('antifraud')"/>
							</td>
							<td>
<%--								<input id="submitInfo" name="submitInfo" type="text"--%>
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
<%--							<td ><textarea id="houseAntifraud" name="houseAntifraud.remarks" style="resize: none;width:304px;height:60px;"></textarea></td>--%>
<%--						</tr>--%>
					</table>
				</td></tr>
			</table>
		</div>
		<div title="流程报告" data-options="href:'${basePath}bpm/getBpmLogs.do?bizKey=${bean.appId}'" style="width: 100%;padding:10px"></div>
	</div>
</form>
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
	//redinfo.find("input[type='button']").attr("disabled",true);
	redinfo.find("input[type='checkbox']").attr('disabled',true);
	redinfo.find("textarea").attr('disabled',true);
	redinfo.find("input[value='算']").attr('disabled',true);
	redinfo.find("input[value='全']").attr('disabled',true);

	redinfo.find('.easyui-validatebox').attr('disabled', 'disabled');
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
	redinfo.find('div.panel div[class="panel-body accordion-body"]>table a').hide();
}

//切换 是否验证
function toggleValidate(objId,isValidete){
	var state=!isValidete;
	var obj=$(objId);
	obj.find('.easyui-validatebox').validatebox({novalidate:state});
// 	var val = $("#specialApprovalInfo").find('#product').combobox('getValue');
// 	obj.find('#product').combobox({novalidate:state});
// 	$("#specialApprovalInfo").find('#product').combobox('setValue', val);
	//obj.find('.easyui-combobox').combobox({novalidate:state});
	obj.find('.easyui-numberbox').validatebox({novalidate:state});
	obj.find('.easyui-datebox').datebox({novalidate:state});
}

function submitAntifraud(buttonType){
//弹出异步加载 遮罩
	window.parent.openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "house/antifraud/save.do",
		data : params+"&buttonType="+buttonType,
		success : function(data) {
			//关闭遮罩，弹出消息框
			window.parent.closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					//window.history.go(-1);
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
//更新保存
function updateFunction() {
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
	//弹出异步加载 遮罩
	window.parent.openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "house/decision/save.do",
		data : encodeURI(params),
		success : function(data) {
			//关闭遮罩，弹出消息框
			window.parent.closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
//	                var url= "<%=basePath%>" + "house/special/query.do";
//					window.location=url;
					//window.history.go(-1);
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

//保存或提交
function saveOrSubForm(buttonType){
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
		if('' == $("#specialApprovalInfo").find("#decision1").combobox('getValue')){
			$.messager.confirm('消息', "请选择决策！");
			return;
		}
		if('05' == $("#specialApprovalInfo").find("#decision1").combobox('getValue')){
			if('' == $("#specialApprovalInfo").find("#decisionCode3").combobox('getValue') || '' == $("#specialApprovalInfo").find("#decisionCode4").combobox('getValue')){
				$.messager.confirm('消息', "请选择拒贷码！");
				return;
			}
			if('' == $("#specialApprovalInfo").find("#feedbackDescribe").val()){
				$.messager.confirm('消息', "请填写反馈销售描述！");
				return;
			}
		}
	}
	//弹出异步加载 遮罩
	window.parent.openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "house/special/save.do",
		data : params+"&buttonType="+buttonType,
		success : function(data) {
			//关闭遮罩，弹出消息框
			window.parent.closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
//	                var url= "<%=basePath%>" + "house/special/query.do";
//					window.location=url;
					//window.history.go(-1);
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
					$("#specialApprovalInfo").find("#yhkje3").numberbox('setValue', je[0]);
					$("#specialApprovalInfo").find("#htje").numberbox('setValue', je[1]);					
				}else if('yhkje' == type){
					$("#specialApprovalInfo").find("#amount").numberbox('setValue', je[0]/10000);
					$("#specialApprovalInfo").find("#htje").numberbox('setValue', je[1]);		
				}else if('htje' == type){
					$("#specialApprovalInfo").find("#amount").numberbox('setValue', je[0]/10000);
					$("#specialApprovalInfo").find("#yhkje3").numberbox('setValue', je[1]);
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
	if($('#specialApprovalInfo').find('#amount').val() != '' && $('#specialApprovalInfo').find('#product').combobox('getValue') != ''){
		updateJe("<%=basePath%>" + "house/verify/getJe.do", "yhkje=0.00&htje=0.00&jkje=" + $('#specialApprovalInfo').find('#amount').val() + "&productName=" + $('#specialApprovalInfo').find('#product').combobox('getValue'), "jkje");
	}
}

/**
 * 根据 产品类型和月还款额 计算 借款金额和合同金额
 */
function updateJkjeHtje(){
	if($('#specialApprovalInfo').find('#yhkje3').val() != '' && $('#specialApprovalInfo').find('#product').combobox('getValue') != ''){
		updateJe("<%=basePath%>" + "house/verify/getJe.do", "jkje=0.00&htje=0.00&yhkje=" + $('#specialApprovalInfo').find('#yhkje3').val() + "&productName=" + $('#specialApprovalInfo').find('#product').combobox('getValue'), "yhkje");
	}
}

/**
 * 根据 产品类型和合同金额 计算 借款金额和月还款额
 */
function updateJkjeYhkje(){
	if($('#specialApprovalInfo').find('#htje').val() != '' && $('#specialApprovalInfo').find('#product').combobox('getValue') != ''){
		updateJe("<%=basePath%>" + "house/verify/getJe.do", "jkje=0.00&yhkje=0.00&htje=" + $('#specialApprovalInfo').find('#htje').val() + "&productName=" + $('#specialApprovalInfo').find('#product').combobox('getValue'), "htje");
	}
}

//返回
function back(){
	//window.history.go(-1);
	window.parent.removeTab();
}
//页面加载完动作
$(document).ready(function (){
	
	if('${houseDecision1.decision}' == '02'){
		$('#noPassReasonDiv1').show();//显示div   
		$('#decisionCodeDiv1').hide();//隐藏div  
		$('#returnCodeDiv1').hide();//隐藏div 
	}else if('${houseDecision1.decision}' == '03'){
		$('#noPassReasonDiv1').show();//显示div   
		$('#decisionCodeDiv1').hide();//隐藏div  
		$('#returnCodeDiv1').hide();//隐藏div  
	}else if('${houseDecision1.decision}' == '00'){
		$('#noPassReasonDiv1').show();//显示div   
		$('#decisionCodeDiv1').hide();//隐藏div  
		$('#returnCodeDiv1').hide();//隐藏div 
	}
	
	if('${houseDecision2.decision}' == '00'){
		$('#noPassReasonDiv2').show();//显示div   
		$('#decisionCodeDiv2').hide();//隐藏div 
		$('returnCodeDiv2').hide();//隐藏div 
	}else if('${houseDecision2.decision}' == '01'){
		$('#noPassReasonDiv2').hide();//隐藏div    
		$('#decisionCodeDiv2').hide();//隐藏div 
		$('returnCodeDiv2').hide();//隐藏div  
	}else if('${houseDecision2.decision}' == '05'){
		$('#noPassReasonDiv2').hide();//隐藏div    
		$('#decisionCodeDiv2').hide();//隐藏div 
		$('returnCodeDiv2').show();//显示div  
	}else if('${houseDecision2.decision}' == '04'){
		$('#noPassReasonDiv2').hide();//隐藏div    
		$('#decisionCodeDiv2').hide();//隐藏div 
		$('returnCodeDiv2').hide();//隐藏div   
	}else{
		$('#noPassReasonDiv2').hide();//隐藏div    
		$('#decisionCodeDiv2').hide();//隐藏div 
		$('returnCodeDiv2').hide();//隐藏div  
	}
	
	if('${houseDecision3.decision}' == '00'){
		toggleValidate('#specialApprovalInfo',false);
	}else{
		toggleValidate('#specialApprovalInfo',true);			
	}
	
	if('${houseDecision3.decision}' == '' || '${houseDecision3.decision}' == '01' || '${houseDecision3.decision}' == '06'){
		$('#noPassReasonDiv').hide();//隐藏div 
		toggleValidate('#noPassReasonDiv',false);
		$('#returnCodeDiv').hide();//隐藏div 
		toggleValidate('#returnCodeDiv',false);
		toggleValidate('amountDiv',true);
		$('#feedbackSellDescribeDiv1').hide();//隐藏div  
		toggleValidate('#feedbackSellDescribeDiv1',false);
		$('#feedbackSellDescribeDiv2').hide();//隐藏div  
		toggleValidate('#feedbackSellDescribeDiv2',false);
	}else if('${houseDecision3.decision}' == '00'){
		$('#noPassReasonDiv').show();//显示div 
		toggleValidate('#noPassReasonDiv',true);
		$('#returnCodeDiv').hide();//隐藏div 
		toggleValidate('#returnCodeDiv',false);
		toggleValidate('amountDiv',false);
		$('#feedbackSellDescribeDiv1').hide();//隐藏div  
		toggleValidate('#feedbackSellDescribeDiv1',false);
		$('#feedbackSellDescribeDiv2').hide();//隐藏div  
		toggleValidate('#feedbackSellDescribeDiv2',false);
	}else{
		$('#noPassReasonDiv').hide();//隐藏div 
		toggleValidate('#noPassReasonDiv',false);
		$('#returnCodeDiv').show();//显示div  
		toggleValidate('#returnCodeDiv',true);
		toggleValidate('amountDiv',true);
		$('#feedbackSellDescribeDiv1').show();//反馈销售描述div  
		toggleValidate('#feedbackSellDescribeDiv1',true);
		$('#feedbackSellDescribeDiv2').show();//反馈销售描述div  
		toggleValidate('#feedbackSellDescribeDiv2',true);
	}
	
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
*/

/*
	//填充select数据 借款期限
	$("#verifyInfo").find("#period").combobox("clear");
	$("#verifyInfo").find("#period").combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:[{"keyProp":"1","keyValue":"12"},{"keyProp":"2","keyValue":"18"},{"keyProp":"3","keyValue":"24"},{"keyProp":"4","keyValue":"36"}]
	});
*/
/*
//填充select数据 借款期限
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
			var producturl = "product/hedao/listjason.do?type=5&belongChannel=" + encodeURI(newValue);
			$("#verifylAppInfo").find('#product').combobox('reload',producturl); 
		}
	});
	//填充select数据 产品
	var belongChannel = $("#verifylAppInfo").find('#belongChannel').combobox('getValue');
	var producturl = "product/hedao/listjason.do?type=5&belongChannel=" + encodeURI(belongChannel);
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
			var producturl1 = "product/hedao/listjason.do?type=5&belongChannel=" + encodeURI(newValue);
			$("#verifyInfo").find('#product').combobox('reload',producturl1); 
		}
	});
	//填充select数据 产品
	var belongChannel1 = $("#verifyInfo").find('#belongChannel').combobox('getValue');
	var producturl1 = "product/hedao/listjason.do?type=5&belongChannel=" + encodeURI(belongChannel1);
	$("#verifyInfo").find("#product").combobox("clear");
	$("#verifyInfo").find('#product').combobox({
		url:producturl1,
		valueField:'name', 
		textField:'name'
	});
	
	//填充select数据 渠道
	var channelurl2="channeltotal/listjason.do";
	$("#approvalInfo").find("#belongChannel").combobox("clear");
	$("#approvalInfo").find('#belongChannel').combobox({
		url:channelurl2,
		valueField:'channelCode', 
		textField:'channelName',
		onChange: function(newValue, oldValue){             
			$("#approvalInfo").find('#product').combobox('clear');
			var producturl2 = "product/hedao/listjason.do?type=5&belongChannel=" + encodeURI(newValue);
			$("#approvalInfo").find('#product').combobox('reload',producturl2); 
		}
	});
	//填充select数据 产品
	var belongChannel2 = $("#approvalInfo").find('#belongChannel').combobox('getValue');
	var producturl2 = "product/hedao/listjason.do?type=5&belongChannel=" + encodeURI(belongChannel2);
	$("#approvalInfo").find("#product").combobox("clear");
	$("#approvalInfo").find('#product').combobox({
		url:producturl2,
		valueField:'name', 
		textField:'name'
	});

	//填充select数据 渠道
	var channelurl3="channeltotal/listjason.do?state=1";
	$("#specialApprovalInfo").find("#belongChannel").combobox("clear");
	$("#specialApprovalInfo").find('#belongChannel').combobox({
		url:channelurl3,
		valueField:'channelCode', 
		textField:'channelName',
		onChange: function(newValue, oldValue){             
			$("#specialApprovalInfo").find('#product').combobox('clear');
			var producturl3 = "product/hedao/listjason.do?type=5&state=1&belongChannel=" + encodeURI(newValue);
			$("#specialApprovalInfo").find('#product').combobox('reload',producturl3); 
		}
	});
	//填充select数据 产品
	var belongChannel3 = $("#specialApprovalInfo").find('#belongChannel').combobox('getValue');
	var producturl3 = "product/hedao/listjason.do?type=5&state=1&belongChannel=" + encodeURI(belongChannel3);
	$("#specialApprovalInfo").find("#product").combobox("clear");
	$("#specialApprovalInfo").find('#product').combobox({
		url:producturl3,
		valueField:'name', 
		textField:'name',
		onChange: function(productVal){
			if($('#specialspecialApprovalInfo').find('#amount').val() != ''){
				updateYhkjeHtje();
			}else if($('#specialspecialApprovalInfo').find('#yhkje3').val() != ''){
				updateJkjeHtje();
			}else if($('#specialspecialApprovalInfo').find('#htje').val() != ''){
				updateJkjeYhkje();
			}
		}	
	});
	
	$('#verifyInfo').find('#decision').combobox('setValue', '${houseDecision1.decision}');
	$('#approvalInfo').find('#decision1').combobox('setValue', '${houseDecision2.decision}');
	$('#specialApprovalInfo').find('#decision1').combobox('setValue', '${houseDecision3.decision}'); 
	
/* 	$("#noPassReasonDiv").hide();//隐藏div 
	$("#decisionCodeDiv").hide();//隐藏div 
	$('#noPassReasonDiv1').hide();//隐藏div   
	$('#decisionCodeDiv1').hide();//隐藏div 
	$('#returnCodeDiv1').hide();//隐藏div 
	$('#noPassReasonDiv2').hide();//隐藏div   
	$('#decisionCodeDiv2').hide();//隐藏div 
	$('#returnCodeDiv2').hide();//隐藏div  */
	
/* 	$("#specialApprovalInfo").find('#decision').combobox("clear");
	$("#specialApprovalInfo").find('#decision').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:[{"keyProp":"","keyValue":"请选择"},{"keyProp":"01","keyValue":"通过"},{"keyProp":"00","keyValue":"不通过"},{'keyProp':'05','keyValue':'拒贷'}],
		onChange: function(decisionVal){
			if(decisionVal == '' || decisionVal == '01'){
				$("#noPassReasonDiv").hide();//div 
				$("#decisionCodeDiv").hide();//div
			}else if(decisionVal == '00'){
				$("#noPassReasonDiv").show();//显示div   
				$("#decisionCodeDiv").hide();//隐藏div  
			}else{
				$("#noPassReasonDiv").hide();//div 
				$("#decisionCodeDiv").show();//div  
			}
    	}
	}); */
	
	//填充select数据 决策信息
/* 	$("#specialApprovalInfo").find('#decision1').combobox("clear");
	$("#specialApprovalInfo").find('#decision1').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:[{"keyProp":"","keyValue":"请选择"},{"keyProp":"01","keyValue":"通过"},{"keyProp":"00","keyValue":"不通过"},{'keyProp':'05','keyValue':'拒贷'}],
		onSelect: function(decisionVal){
			alert('aa');
			if(decisionVal == '' || decisionVal == '01'){
				$("#noPassReasonDiv").hide();//div 
				$("#decisionCodeDiv").hide();//div
			}else if(decisionVal == '00'){
				$("#noPassReasonDiv").show();//显示div   
				$("#decisionCodeDiv").hide();//隐藏div  
			}else{
				$("#noPassReasonDiv").hide();//div 
				$("#decisionCodeDiv").show();//div  
			}
    	}
	}); */

/*
	//填充select数据 借款期限
	$("#specialApprovalInfo").find("#period").combobox("clear");
	$("#specialApprovalInfo").find('#period').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:[{"keyProp":"1","keyValue":"12"},{"keyProp":"2","keyValue":"18"},{"keyProp":"3","keyValue":"24"},{"keyProp":"4","keyValue":"36"}]
	});
*/
	
	//填充select数据 拒件码01
    var refusedCode01url = "sys/datadictionary/listjason.do?keyName=refusedCode01";
		$('#verifyInfo').find("#decisionCode3").combobox("clear");
		$('#verifyInfo').find('#decisionCode3').combobox({
			url: refusedCode01url,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
	            $('#verifyInfo').find('#decisionCode4').combobox('clear');
	            var refusedCode02url = "sys/datadictionary/listjason.do?keyName=refusedCode02&parentKeyProp=" + encodeURI(newValue);
	            $('#verifyInfo').find('#decisionCode4').combobox('reload',refusedCode02url); 
       		}
		});
		//填充select数据 拒件码02
		var decisionCode3 = $('#verifyInfo').find('#decisionCode3').combobox('getValue');
		var refusedCode02url = "sys/datadictionary/listjason.do?keyName=refusedCode02&parentKeyProp=" + encodeURI(decisionCode3);
		    $('#verifyInfo').find("#decisionCode4").combobox("clear");
			$('#verifyInfo').find('#decisionCode4').combobox({
			url: refusedCode02url,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
	//填充select数据 拒件码01
    var refusedCode01url = "sys/datadictionary/listjason.do?keyName=refusedCode01";
		$('#approvalInfo').find("#decisionCode3").combobox("clear");
		$('#approvalInfo').find('#decisionCode3').combobox({
			url: refusedCode01url,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
	            $('#approvalInfo').find('#decisionCode4').combobox('clear');
	            var refusedCode02url = "sys/datadictionary/listjason.do?keyName=refusedCode02&parentKeyProp=" + encodeURI(newValue);
	            $('#approvalInfo').find('#decisionCode4').combobox('reload',refusedCode02url); 
       		}
		});
		//填充select数据 拒件码02
		var decisionCode3 = $('#approvalInfo').find('#decisionCode3').combobox('getValue');
		var refusedCode02url = "sys/datadictionary/listjason.do?keyName=refusedCode02&parentKeyProp=" + encodeURI(decisionCode3);
		    $('#approvalInfo').find("#decisionCode4").combobox("clear");
			$('#approvalInfo').find('#decisionCode4').combobox({
			url: refusedCode02url,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
	//填充select数据 拒件码01
    var refusedCode01url = "sys/datadictionary/listjason.do?keyName=refusedCode01";
		$('#specialApprovalInfo').find("#decisionCode3").combobox("clear");
		$('#specialApprovalInfo').find('#decisionCode3').combobox({
			url: refusedCode01url,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
	            $('#specialApprovalInfo').find('#decisionCode4').combobox('clear');
	            var refusedCode02url = "sys/datadictionary/listjason.do?keyName=refusedCode02&parentKeyProp=" + encodeURI(newValue);
	            $('#specialApprovalInfo').find('#decisionCode4').combobox('reload',refusedCode02url); 
       		}
		});
		//填充select数据 拒件码02
		var decisionCode3 = $('#specialApprovalInfo').find('#decisionCode3').combobox('getValue');
		var refusedCode02url = "sys/datadictionary/listjason.do?keyName=refusedCode02&parentKeyProp=" + encodeURI(decisionCode3);
		    $('#specialApprovalInfo').find("#decisionCode4").combobox("clear");
			$('#specialApprovalInfo').find('#decisionCode4').combobox({
			url: refusedCode02url,
			valueField: 'keyProp',
			textField: 'keyValue'
		});
	
});
</script>
</html>


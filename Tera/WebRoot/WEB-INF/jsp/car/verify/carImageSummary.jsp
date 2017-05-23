<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="tab_show_summary" style="display: none;" title="影像摘要" ></div>

  <div id="faceVerify" class="easyui-accordion" data-options="multiple:false" style="" data-options="fit:true">
        <div title="材料完整有效性" style="overflow:auto;padding:10px;">
			<table>
				<tr>
					<td align="right">身份证:</td>
					<td>
					<input id="id" name="summary.id" type="hidden"  value="${summary.id}"/>
					<input id="state" name="summary.state" type="hidden"  value="${summary.state}"/>
					<input id="appId" name="summary.appId" type="hidden"  value="${bean.appId}"/>
					
					<input id="validteIdnoFlag" name="summary.validteIdnoFlag" 
					type="radio" value="1" <c:if test="${summary.validteIdnoFlag==1}">checked="checked"</c:if> />有效
					<input id="validteIdnoFlag" name="summary.validteIdnoFlag" 
					type="radio" value="0" <c:if test="${summary.validteIdnoFlag==0}">checked="checked"</c:if>/>无效
					<input id="validteIdnoRemark" name="summary.validteIdnoRemark" type="text" data-options="validType:['length[0,30]']" 
					class="textbox easyui-validatebox" value="${summary.validteIdnoRemark}"/></td>
				</tr>
				<tr>
					<td align="right">房产证:</td>
					<td><input id="validteHouseFlag" name="summary.validteHouseFlag" 
					type="radio" value="1" <c:if test="${summary.validteHouseFlag==1}">checked="checked"</c:if> />有效
					<input id="validteHouseFlag" name="summary.validteHouseFlag" 
					type="radio" value="0" <c:if test="${summary.validteHouseFlag==0}">checked="checked"</c:if>/>无效
					<input id="validteHouseRemark" name="summary.validteHouseRemark" type="text" data-options="validType:['length[0,30]']" 
					class="textbox easyui-validatebox" value="${summary.validteHouseRemark}"/></td>
				</tr>
				<tr>
					<td align="right">离婚证:</td>
					<td><input id="validteDivorceFlag" name="summary.validteDivorceFlag" 
					type="radio" value="1" <c:if test="${summary.validteDivorceFlag==1}">checked="checked"</c:if> />有效
					<input id="validteDivorceFlag" name="summary.validteDivorceFlag" 
					type="radio" value="0" <c:if test="${summary.validteDivorceFlag==0}">checked="checked"</c:if>/>无效
					<input id="validteDivorceRemark" name="summary.validteDivorceRemark" type="text" data-options="validType:['length[0,30]']" 
					class="textbox easyui-validatebox" value="${summary.validteDivorceRemark}"/></td>
				</tr>
				<tr>
					<td align="right">工资卡流水:</td>
					<td><input id="validteWageFlag" name="summary.validteWageFlag" 
					type="radio" value="1" <c:if test="${summary.validteWageFlag==1}">checked="checked"</c:if> />有效
					<input id="validteWageFlag" name="summary.validteWageFlag" 
					type="radio" value="0" <c:if test="${summary.validteWageFlag==0}">checked="checked"</c:if>/>无效
					<input id="validteWageRemark" name="summary.validteWageRemark" type="text" data-options="validType:['length[0,30]']" 
					class="textbox easyui-validatebox" value="${summary.validteWageRemark}"/></td>
				</tr>
				<tr>
					<td>常用借记卡流水:</td>
					<td><input id="validteDebitFlag" name="summary.validteDebitFlag" 
					type="radio" value="1" <c:if test="${summary.validteDebitFlag==1}">checked="checked"</c:if> />有效
					<input id="validteDebitFlag" name="summary.validteDebitFlag" 
					type="radio" value="0" <c:if test="${summary.validteDebitFlag==0}">checked="checked"</c:if>/>无效
					<input id="validteDebitRemark" name="summary.validteDebitRemark" type="text" data-options="validType:['length[0,30]']" 
					class="textbox easyui-validatebox" value="${summary.validteDebitRemark}"/></td>
				</tr>
			</table>
		</div>
		<div title="流水" style="overflow:auto;padding:10px;" id="wageFlowAdd"
			data-options="tools:[{iconCls:'icon-add', handler:function(){addObj('wageFlows');}}]" >
			<table class="datatable" id="wageFlows">
				<tr>
					<th style="display:none">
					<input type="hidden"  id="wageFlowsIndex" 
					value="${wageFlows==null?0:(fn:length(wageFlows)>0?fn:length(wageFlows):0)}"/>
					</th>
					<th scope="col" >资料类型</th>
					<th scope="col" >最近一个月</th>
					<th scope="col" >N-1个月</th>
					<th scope="col" >N-2个月</th>
					<th scope="col" >N-3个月</th>
					<th scope="col" >N-4个月</th>
					<th scope="col" >N-5个月</th>
					<th scope="col" >账号余额</th>
					<th scope="col" >平均值</th>
					<th scope="col" >操作</th>
				</tr>
				<c:forEach items="${wageFlows}" var="wageFlow" varStatus="status">
						<tr id="wageFlow_${status.index}">
							<td>
								<input type="hidden" name="wageFlowList[${status.index}].id" value="${wageFlow.id}" />
								<input type="hidden" name="wageFlowList[${status.index}].appId" value="${bean.appId}" />
								<input type="hidden" name="wageFlowList[${status.index}].state" id="state" value="${wageFlow.state}"/>
								<select id="type" name="wageFlowList[${status.index}].type" value="${wageFlow.type}"
									data-options="required:true,validType:['length[0,50]']
									,onChange: function(decisionVal){}" 
									class="easyui-combobox" editable="false" style="width:100px;">
									<option value="01" <c:if test="${'01' eq wageFlow.type}">selected</c:if>>常储卡流水</option>
									<option value="02" <c:if test="${'02' eq wageFlow.type}">selected</c:if>>工资卡流水</option>
									<option value="03" <c:if test="${'03' eq wageFlow.type}">selected</c:if>>对公账户流水</option>
								</select>
							</td>
							<td>
								<input id="n0Check" name="wageFlowList[${status.index}].n0Check" type="checkbox" <c:if test="${wageFlow.n0Check=='1'}">checked="checked"</c:if>
								 value="${wageFlow.n0Check}" onchange="checkChange('wageFlow_${status.index}', 'n0Check')"/>
								<input id="n0Amount" name="wageFlowList[${status.index}].n0Amount" type="text" editable="false"  data-options="min:0,precision:2" 
									class="textbox easyui-numberbox" value="${wageFlow.n0Amount}" style="width:70px;"/>
							</td>
							<td>
								<input id="n1Check" name="wageFlowList[${status.index}].n1Check" type="checkbox" <c:if test="${wageFlow.n1Check=='1'}">checked="checked"</c:if> 
								value="${wageFlow.n1Check}" onchange="checkChange('wageFlow_${status.index}', 'n1Check')"/>
								<input id="n1Amount" name="wageFlowList[${status.index}].n1Amount" type="text" editable="false"  data-options="min:0,precision:2" 
									class="textbox easyui-numberbox" value="${wageFlow.n1Amount}" style="width:70px;"/>
							</td>
							<td>
								<input id="n2Check" name="wageFlowList[${status.index}].n2Check" type="checkbox" <c:if test="${wageFlow.n2Check=='1'}">checked="checked"</c:if> 
								value="${wageFlow.n2Check}" onchange="checkChange('wageFlow_${status.index}', 'n2Check')"/>
								<input id="n2Amount" name="wageFlowList[${status.index}].n2Amount" type="text" editable="false"  data-options="min:0,precision:2" 
									class="textbox easyui-numberbox" value="${wageFlow.n2Amount}" style="width:70px;"/>
							</td>
							<td>
								<input id="n3Check" name="wageFlowList[${status.index}].n3Check" type="checkbox" <c:if test="${wageFlow.n3Check=='1'}">checked="checked"</c:if> 
								value="${wageFlow.n3Check}" onchange="checkChange('wageFlow_${status.index}', 'n3Check')"/>
								<input id="n3Amount" name="wageFlowList[${status.index}].n3Amount" type="text" editable="false"  data-options="min:0,precision:2" 
									class="textbox easyui-numberbox" value="${wageFlow.n3Amount}" style="width:70px;"/>
							</td>
							<td>
								<input id="n4Check" name="wageFlowList[${status.index}].n4Check" type="checkbox" <c:if test="${wageFlow.n4Check=='1'}">checked="checked"</c:if> 
								value="${wageFlow.n4Check}" onchange="checkChange('wageFlow_${status.index}', 'n4Check')"/>
								<input id="n4Amount" name="wageFlowList[${status.index}].n4Amount" type="text" editable="false"  data-options="min:0,precision:2" 
									class="textbox easyui-numberbox" value="${wageFlow.n4Amount}" style="width:70px;"/>
							</td>
							<td>
								<input id="n5Check" name="wageFlowList[${status.index}].n5Check" type="checkbox" <c:if test="${wageFlow.n5Check=='1'}">checked="checked"</c:if> 
								value="${wageFlow.n5Check}" onchange="checkChange('wageFlow_${status.index}', 'n5Check')"/>
								<input id="n5Amount" name="wageFlowList[${status.index}].n5Amount" type="text" editable="false"  data-options="min:0,precision:2" 
									class="textbox easyui-numberbox" value="${wageFlow.n5Amount}" style="width:70px;"/>
							</td>
							<td>
								<input id="restAmount" name="wageFlowList[${status.index}].restAmount" type="text" editable="false"  data-options="min:0,precision:2" 
									class="textbox easyui-numberbox" value="${wageFlow.restAmount}" style="width:70px;"/>
							</td>
							<td>
								<input id="avgAmount" name="wageFlowList[${status.index}].avgAmount" type="text" editable="false"  data-options="min:0,precision:2" 
									class="textbox easyui-numberbox" value="${wageFlow.avgAmount}" style="width:70px;" readonly="readonly"/>
							</td>
							<td>
								<input type="button" value="算" class="btn" style="width:30px;" onclick="calculateFn(${status.index});"/>
								<input type="button" value="全" class="btn" style="width:30px;" onclick="checkAll(${status.index});"/>
								<img id="img1" src="img/deleteItem2.png"  onclick="rmObj('wageFlow_${status.index}');" />
							</td>
						</tr>
				</c:forEach>
				<tr style="display:none">
					<td colspan="5">
						<textarea id="wageFlows_textarea" disabled="disabled" style="display:none">
							<tr id="wageFlow_wageFlowsIndex">
								<td>
									<input type="hidden" name="wageFlowList[wageFlowsIndex].id" value="0" />
									<input type="hidden" name="wageFlowList[wageFlowsIndex].appId" value="${bean.appId}" />
									<input type="hidden" name="wageFlowList[wageFlowsIndex].state" id="state" value="1"/>
									<select id="type" name="wageFlowList[wageFlowsIndex].type" value=""
										data-options="required:true,validType:['length[0,50]']
										,onChange: function(decisionVal){}" 
										class="easyui-combobox" editable="false" style="width:100px;">
										<option value="01" <c:if test="${'01' eq wageFlow.type}">selected</c:if>>常储卡流水</option>
										<option value="02" <c:if test="${'02' eq wageFlow.type}">selected</c:if>>工资卡流水</option>
										<option value="03" <c:if test="${'03' eq wageFlow.type}">selected</c:if>>对公账户流水</option>
									</select>
								</td>
								<td>
									<input id="n0Check" name="wageFlowList[wageFlowsIndex].n0Check" type="checkbox" value="1" 
									 onchange="checkChange('wageFlow_${status.index}', 'n0Check')"/>
									<input id="n0Amount" name="wageFlowList[wageFlowsIndex].n0Amount" type="text" editable="false"  data-options="min:0,precision:2" 
										class="textbox easyui-numberbox" value="" style="width:70px;"/>
								</td>
								<td>
									<input id="n1Check" name="wageFlowList[wageFlowsIndex].n1Check" type="checkbox" value="1" 
									 onchange="checkChange('wageFlow_${status.index}', 'n1Check')"/>
									<input id="n1Amount" name="wageFlowList[wageFlowsIndex].n1Amount" type="text" editable="false"  data-options="min:0,precision:2" 
										class="textbox easyui-numberbox" value="" style="width:70px;"/>
								</td>
								<td>
									<input id="n2Check" name="wageFlowList[wageFlowsIndex].n2Check" type="checkbox" value="1" 
									 onchange="checkChange('wageFlow_${status.index}', 'n2Check')"/>
									<input id="n2Amount" name="wageFlowList[wageFlowsIndex].n2Amount" type="text" editable="false"  data-options="min:0,precision:2" 
										class="textbox easyui-numberbox" value="" style="width:70px;"/>
								</td>
								<td>
									<input id="n3Check" name="wageFlowList[wageFlowsIndex].n3Check" type="checkbox" value="1" 
									 onchange="checkChange('wageFlow_${status.index}', 'n3Check')"/>
									<input id="n3Amount" name="wageFlowList[wageFlowsIndex].n3Amount" type="text" editable="false"  data-options="min:0,precision:2" 
										class="textbox easyui-numberbox" value="" style="width:70px;"/>
								</td>
								<td>
									<input id="n4Check" name="wageFlowList[wageFlowsIndex].n4Check" type="checkbox" value="1" 
									 onchange="checkChange('wageFlow_${status.index}', 'n4Check')"/>
									<input id="n4Amount" name="wageFlowList[wageFlowsIndex].n4Amount" type="text" editable="false"  data-options="min:0,precision:2" 
										class="textbox easyui-numberbox" value="" style="width:70px;"/>
								</td>
								<td>
									<input id="n5Check" name="wageFlowList[wageFlowsIndex].n5Check" type="checkbox" value="1" 
									 onchange="checkChange('wageFlow_${status.index}', 'n5Check')"/>
									<input id="n5Amount" name="wageFlowList[wageFlowsIndex].n5Amount" type="text" editable="false"  data-options="min:0,precision:2" 
										class="textbox easyui-numberbox" value="" style="width:70px;"/>
								</td>
								<td>
									<input id="restAmount" name="wageFlowList[wageFlowsIndex].restAmount" type="text" editable="false"  data-options="min:0,precision:2" 
										class="textbox easyui-numberbox" value="" style="width:70px;"/>
								</td>
								<td>
									<input id="avgAmount" name="wageFlowList[wageFlowsIndex].avgAmount" type="text" editable="false"  data-options="min:0,precision:2" 
										class="textbox easyui-numberbox" value="" style="width:70px;" readonly="readonly"/>
								</td>
								<td>
									<input type="button" value="算" class="btn" style="width:30px;" onclick="calculateFn(wageFlowsIndex);"/>
									<input type="button" value="全" class="btn" style="width:30px;" onclick="checkAll(wageFlowsIndex);"/>
									<img id="img2" src="img/deleteItem2.png"  onclick="rmObj('wageFlow_wageFlowsIndex');" />
								</td>
							</tr>
						</textarea>
					</td>
				</tr>
			</table>
			<table style="width: 94% !important;height:50px;">
				<tr>
					<td align="right" style="width: 50px;">备注：</td>
					<td style="width: 94% !important;height:50px;">
						<textarea name="summary.wageFlowRemarks" class="tera_textarea">${summary.wageFlowRemarks}</textarea>
					</td>
				</tr>
			</table>
		</div>
		<div title="人行信息" style="overflow:auto;padding:10px;">
			<table>
				<tr>
					<td>是否有信用卡:</td>
					<td>
						<input id="creditCard" name="summary.creditCard" type="text" data-options="validType:['length[0,10]']" 
						class="easyui-combobox" value="${summary.creditCard}" editable="false"/>
					</td>
					<td>是否有贷款:</td>
					<td>
						<input id="isLoan" name="summary.isLoan" type="text" data-options="validType:['length[0,10]']" 
						class="easyui-combobox" value="${summary.isLoan}" editable="false"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">近12个月贷后管理查询次数:</td>
					<td colspan="2"><input id="queryCount" name="summary.queryCount" type="text" editable="false"  data-options="min:0,precision:0" 
					class="textbox easyui-numberbox" value="${summary.queryCount}"/></td>
				</tr>
			</table>
			<div id="creditReportDiv" <c:if test="${summary.creditCard!='1'}">style="display: none;"</c:if>>
				<table>
					<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>信用卡</strong></div>
					<hr width="100%" size="2" noshade="" color="#D3D3D3">
					<tr>
						<td align="right">近12个月是否异常：</td>
						<td>
	 					<input id="id" name="creditReport.id" type="hidden"  value="${creditReport.id}"/>
						<input id="state" name="creditReport.state" type="hidden"  value="${creditReport.state}"/>
						<input id="appId" name="creditReport.appId" type="hidden"  value="${bean.appId}"/>
						
					
						<input id="creditCardException" name="creditReport.creditCardException" 
						type="radio" value="1" <c:if test="${creditReport.creditCardException==1}">checked="checked"</c:if> />是
						<input id="creditCardException" name="creditReport.creditCardException" 
						type="radio" value="0" <c:if test="${creditReport.creditCardException==0||creditReport.creditCardException==null}">checked="checked"</c:if>/>否</td>
						<td align="right">近6个月查询次数:</td>
						<td><input id="querySixCount" name="creditReport.querySixCount" type="text" editable="false"  data-options="required:true,min:0,precision:0" 
						class="textbox easyui-numberbox" value="${creditReport.querySixCount}"/></td>
					</tr>
					<tr>
						<td align="right">最大账龄（月）:</td>
						<td><input id="maxDefault" name="creditReport.maxDefault" type="text" editable="false"  data-options="required:true,min:0,precision:0" 
						class="textbox easyui-numberbox" value="${creditReport.maxDefault}"/></td>
						<td align="right">最高额度/总额:</td>
						<td><input id="amount" name="creditReport.amount" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
						class="textbox easyui-numberbox" value="${creditReport.amount}"/></td>
					</tr>
					<tr>
						<td align="right">逾期总额:</td>
						<td><input id="defaultAmount" name="creditReport.defaultAmount" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
						class="textbox easyui-numberbox" value="${creditReport.defaultAmount}"/></td>
						<td align="right">逾期的笔数/数量:</td>
						<td><input id="defaultCount" name="creditReport.defaultCount" type="text" editable="false"  data-options="required:true,min:0,precision:0" 
						class="textbox easyui-numberbox" value="${creditReport.defaultCount}"/></td>
					</tr>
					<tr>
						<td align="right">最大逾期次数:</td>
						<td><input id="defaultMaxCount" name="creditReport.defaultMaxCount" type="text" editable="false"  data-options="required:true,min:0,precision:0" 
						class="textbox easyui-numberbox" value="${creditReport.defaultMaxCount}"/></td>
						<td>90天以上逾期次数:</td>
						<td><input id="defaultNinetyCount" name="creditReport.defaultNinetyCount" type="text" editable="false"  data-options="required:true,min:0,precision:0" 
						class="textbox easyui-numberbox" value="${creditReport.defaultNinetyCount}"/></td>
					</tr>
					<tr>
						<td align="right">已用额度:</td>
						<td><input id="usedAmount" name="creditReport.usedAmount" type="text" data-options="required:true,validType:['length[0,50]']" 
						class="textbox easyui-numberbox" value="${creditReport.usedAmount}"/></td>
					</tr>
				</table>
			</div>
			<div id="loanReportDiv" <c:if test="${summary.isLoan!='1'}">style="display: none;"</c:if>>
				<table>
					<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>贷款</strong></div>
					<hr width="100%" size="2" noshade="" color="#D3D3D3">
					<tr>
						<td align="right">最大账龄（月）:</td>
						<td>
	 					<input id="id" name="loanReport.id" type="hidden"  value="${loanReport.id}"/>
						<input id="state" name="loanReport.state" type="hidden"  value="${loanReport.state}"/>
						<input id="appId" name="loanReport.appId" type="hidden"  value="${bean.appId}"/>
					
 					
						<input id="maxDefault" name="loanReport.maxDefault" type="text" editable="false"  data-options="required:true,min:0,precision:0" 
						class="textbox easyui-numberbox" value="${loanReport.maxDefault}"/></td>
						<td align="right">最高额度/总额:</td>
						<td><input id="amount" name="loanReport.amount" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
						class="textbox easyui-numberbox" value="${loanReport.amount}"/></td>
					</tr>
					<tr>
						<td align="right">逾期总额:</td>
						<td><input id="defaultAmount" name="loanReport.defaultAmount" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
						class="textbox easyui-numberbox" value="${loanReport.defaultAmount}"/></td>
						<td align="right">逾期的笔数/数量:</td>
						<td><input id="defaultCount" name="loanReport.defaultCount" type="text" editable="false"  data-options="required:true,min:0,precision:0" 
						class="textbox easyui-numberbox" value="${loanReport.defaultCount}"/></td>
					</tr>
					<tr>
						<td align="right">最大逾期次数:</td>
						<td><input id="defaultMaxCount" name="loanReport.defaultMaxCount" type="text" editable="false"  data-options="required:true,min:0,precision:0" 
						class="textbox easyui-numberbox" value="${loanReport.defaultMaxCount}"/></td>
						<td>90天以上逾期次数:</td>
						<td><input id="defaultNinetyCount" name="loanReport.defaultNinetyCount" type="text" editable="false"  data-options="required:true,min:0,precision:0" 
						class="textbox easyui-numberbox" value="${loanReport.defaultNinetyCount}"/></td>
					</tr>
					<tr>
						<td>一个月内到期的贷款笔数:</td>
						<td><input id="expireLoan" name="loanReport.expireLoan" type="text" editable="false"  data-options="required:true,min:0,precision:0" 
						class="textbox easyui-numberbox" value="${loanReport.expireLoan}"/></td>
						<td>个人住房贷款笔数:</td>
						<td><input id="expireHousing" name="loanReport.expireHousing" type="text" editable="false"  data-options="required:true,min:0,precision:0" 
						class="textbox easyui-numberbox" value="${loanReport.expireHousing}"/></td>
					</tr>
				</table>
			</div>
		</div>
		<div title="社保信息" style="overflow:auto;padding:10px;">
			<table>
				<tr>
					<td align="right">社保单位名称:</td>
					<td>
					<input id="socialComName" name="summary.socialComName" type="text" data-options="validType:['length[0,100]']" 
					class="textbox easyui-validatebox" value="${summary.socialComName}"/></td>
					<td align="right">社保卡号:</td>
					<td><input id="socialCode" name="summary.socialCode" type="text" data-options="validType:['NumLetters','length[0,100]']" 
					class="textbox easyui-validatebox" value="${summary.socialCode}"/></td>
				</tr>
				<tr>
					<td align="right">社保月基纳金额:</td>
					<td><input id="socialAmount" name="summary.socialAmount" type="text" data-options="validType:['length[0,100]']" 
					class="textbox easyui-numberbox" value="${summary.socialAmount}" style="width:140px;"/>元</td>
				</tr>
				<tr>
					<td>社保最近基纳时间:</td>
					<td><input id="socialDate" name="summary.socialDate" type="text" editable="false" 
					class="textbox easyui-datebox" value="${summary.socialDateStr}"/></td>
					<td>社保已基纳时间:</td>
					<td><input id="socialMonths" name="summary.socialMonths" type="text" editable="false"  data-options="min:0,precision:0" 
					class="textbox easyui-numberbox" value="${summary.socialMonths}" style="width:140px;"/>月</td>
				</tr>
			</table>
		</div>
		<div title="公积金信息" style="overflow:auto;padding:10px;">
			<table>
				<tr>
					<td align="right">公积金单位名称:</td>
					<td>
					<input id="publicComName" name="summary.publicComName" type="text" data-options="validType:['length[0,100]']" 
					class="textbox easyui-validatebox" value="${summary.publicComName}"/></td>
					<td>公积金月基纳金额:</td>
					<td><input id="publicAmount" name="summary.publicAmount" type="text" data-options="validType:['length[0,100]']" 
					class="textbox easyui-numberbox" value="${summary.publicAmount}" style="width:140px;"/>元</td>
				</tr>
				<tr>
					<td>公积金最近基纳时间:</td>
					<td><input id="publicDate" name="summary.publicDate" type="text" editable="false" 
					class="textbox easyui-datebox" value="${summary.publicDateStr}"/></td>
					<td>公积金已基纳时间:</td>
					<td><input id="publicMonths" name="summary.publicMonths" type="text" editable="false"  data-options="min:0,precision:0" 
					class="textbox easyui-numberbox" value="${summary.publicMonths}" style="width:140px;"/>月</td>
				</tr>
			</table>
		</div>
		
		<div title="房产信息" style="overflow:auto;padding:10px;" id="houseAdd"
			data-options="tools:[{iconCls:'icon-add', handler:function(){addObj('houseInfos');}}]" >
			<table class="datatable" id="houseInfos">
				<tr>
					<th style="display:none">
					<input type="hidden"  id="houseInfosIndex" 
					value="${houseInfos==null?0:(fn:length(houseInfos)>0?fn:length(houseInfos):0)}"/>
					</th>
					<th scope="col" >房产地址</th>
					<th scope="col" >面积</th>
					<th scope="col" >是否抵押</th>
					<th scope="col" >产权获取时间</th>
				</tr>
				<c:forEach items="${houseInfos}" var="houseInfo" varStatus="status">
						<tr id="houseInfo_${status.index}">
							<td>
							<input type="hidden" name="housingList[${status.index}].id" value="${houseInfo.id}" />
							<input type="hidden" name="housingList[${status.index}].appId" value="${bean.appId}" />
							<input type="hidden" name="housingList[${status.index}].state" id="state" value="${houseInfo.state}" />
								<input id="address" name="housingList[${status.index}].address"  
								type="text" data-options="validType:['length[0,50]']" 
								class="textbox easyui-validatebox" 
								value="${houseInfo.address}"/>
							</td>
							<td>
								<input id="area" name="housingList[${status.index}].area" 
								type="text" data-options="validType:['length[0,50]']" 
								class="textbox easyui-validatebox" value="${houseInfo.area}" />
							</td>
							<td>
								<input id="isMortgage" name="housingList[${status.index}].isMortgage"
								type="radio" value="1" <c:if test="${houseInfo.isMortgage==1}">checked="checked"</c:if> />是
								<input id="isMortgage" name="housingList[${status.index}].isMortgage" 
								type="radio" value="0" <c:if test="${houseInfo.isMortgage==0}">checked="checked"</c:if>/>否
							</td>
							<td>
								<input id="acquisitionDateStr" name="housingList[${status.index}].acquisitionDate" type="text" editable="false" data-options="validType:['length[0,50]']" 
								class="textbox easyui-datebox" value="${houseInfo.acquisitionDateStr}"/>
<%--								<img src="img/deleteItem2.png"  onclick="rmObj('houseInfo_${status.index}');" />--%>
							</td>
						</tr>
				</c:forEach>
				<tr style="display:none">
					<td colspan="5">
						<textarea id="houseInfos_textarea" disabled="disabled" style="display:none">
							<tr id="houseInfo_houseInfosIndex">
								<td>
								<input type="hidden" name="housingList[houseInfosIndex].id" value="0" />
								<input type="hidden" name="housingList[houseInfosIndex].appId" value="${bean.appId}" />	
								<input type="hidden" id="state" name="housingList[houseInfosIndex].state" value="1" />
									<input id="address" name="housingList[houseInfosIndex].address" 
									type="text" data-options="validType:['length[0,50]']" 
									class="textbox easyui-validatebox" value=""/>
								</td>
								<td>
									<input id="area" name="housingList[houseInfosIndex].area" 
									type="text" editable="false"  data-options="min:0,precision:0" 
									class="textbox easyui-numberbox" value=""/>
								</td>
								<td>
									<input id="isMortgage" name="housingList[houseInfosIndex].isMortgage" 
									type="radio" value="1"  />是
									<input id="isMortgage" name="housingList[houseInfosIndex].isMortgage" 
									type="radio" value="0" checked="checked" />否
								</td>
								<td>
									<input id="acquisitionDateStr" name="housingList[houseInfosIndex].acquisitionDate" type="text" data-options="validType:['length[0,50]']" 
									class="textbox easyui-datebox"  editable="false" value=""/>
									<img id="img3" src="img/deleteItem2.png"  onclick="rmObj('houseInfo_houseInfosIndex');" />
								</td>
							</tr>
						</textarea>
					</td>
				</tr>
			</table>
		</div>
		<div title="备注" style="overflow:auto;padding:10px;" data-options="collapsed:false,collapsible:false">
			<table style="width: 100% !important;height:200px;">
				<tr>
					<td style="width: 100% !important;height:200px;">
						<textarea name="summary.remarks" class="tera_textarea">${summary.remarks}</textarea>
					</td>
				</tr>
			</table>
		</div>
  </div>
 
<script type="text/javascript">
$.parser.parse($("#houseAdd"));
$.parser.parse($("#wageFlowAdd"));
	//添加对象
	function addObj(key){
		var html=$("#"+key+"_textarea").val();		//得到 特约驾驶员 HTML
		var keyIndex=key+"Index";
		var index=$("#"+keyIndex).val();					//得到索引值
		html=html.replace(eval('/'+keyIndex+'/gm'),index);	//把索引占位符 替换
		var newDriver=$(html);					//转换成 JQuery 对象
		newDriver.appendTo("#"+key);				//添加到 对应地点
		$.parser.parse(newDriver);						//初始化 EasyUI
		$("#"+keyIndex).val(++index);	//索引递增
	}
	//删除元素
	function rmObj(objId){
		var obj = $("#"+objId);
		obj.find("#state").val("0");
		obj.hide();
	}
	
	//复选框选择改变值
	function checkChange(trName, checkName){
		if($("#" + trName).find("#" + checkName).attr("checked") == "checked"){
			$("#" + trName).find("#" + checkName).val("1");
		}else{
			$("#" + trName).find("#" + checkName).val("0");			
		}
	}
	
	//算
	function calculateFn(_index){
		var sum = 0;
		var count = 0;
		if($("#wageFlow_" + _index).find("#n0Check").attr("checked") == "checked"){
			sum = parseFloat(sum) + parseFloat($("#wageFlow_" + _index).find("#n0Amount").val());
			count++;
		}
		if($("#wageFlow_" + _index).find("#n1Check").attr("checked") == "checked"){
			sum = parseFloat(sum) + parseFloat($("#wageFlow_" + _index).find("#n1Amount").val());
			count++;
		}
		if($("#wageFlow_" + _index).find("#n2Check").attr("checked") == "checked"){
			sum = parseFloat(sum) + parseFloat($("#wageFlow_" + _index).find("#n2Amount").val());
			count++;
		}
		if($("#wageFlow_" + _index).find("#n3Check").attr("checked") == "checked"){
			sum = parseFloat(sum) + parseFloat($("#wageFlow_" + _index).find("#n3Amount").val());
			count++;
		}
		if($("#wageFlow_" + _index).find("#n4Check").attr("checked") == "checked"){
			sum = parseFloat(sum) + parseFloat($("#wageFlow_" + _index).find("#n4Amount").val());
			count++;
		}
		if($("#wageFlow_" + _index).find("#n5Check").attr("checked") == "checked"){
			sum = parseFloat(sum) + parseFloat($("#wageFlow_" + _index).find("#n5Amount").val());
			count++;
		}
		if(count != 0){
			$("#wageFlow_" + _index).find("#avgAmount").numberbox('setValue', sum/count);
		}
	}
	
	//全选
	function checkAll(_index){
		$("#wageFlow_" + _index).find("#n0Check").attr("checked", "checked");
		$("#wageFlow_" + _index).find("#n1Check").attr("checked", "checked");
		$("#wageFlow_" + _index).find("#n2Check").attr("checked", "checked");
		$("#wageFlow_" + _index).find("#n3Check").attr("checked", "checked");
		$("#wageFlow_" + _index).find("#n4Check").attr("checked", "checked");
		$("#wageFlow_" + _index).find("#n5Check").attr("checked", "checked");
	}
	
	//切换 是否验证
	function toggleValidate(objId,isValidete){
		var state=!isValidete;
		var obj=$(objId);
		obj.find('.easyui-validatebox').validatebox({novalidate:state});
		obj.find('.easyui-combobox').combobox({novalidate:state});
		obj.find('.easyui-numberbox').validatebox({novalidate:state});
		obj.find('.easyui-datebox').datebox({novalidate:state});
	}
$(document).ready(function(){
	$.parser.parse($("#faceVerify"));
	//信用卡与贷款
	$('#faceVerify').find("#creditCard").combobox("clear");
	$('#faceVerify').find('#creditCard').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:[{"keyProp":"1","keyValue":"是"},{"keyProp":"0","keyValue":"否"}],
		onChange: function(creditCardVal){
            if(creditCardVal=='1'){
                $("#creditReportDiv").show();//显示div
                toggleValidate("#creditReportDiv",true);//加验证必填
            }else{
		        $("#creditReportDiv").hide();
		        toggleValidate("#creditReportDiv",false);//取消验证
            }
        }
	});
	$('#faceVerify').find("#isLoan").combobox("clear");
	$('#faceVerify').find('#isLoan').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:[{"keyProp":"1","keyValue":"是"},{"keyProp":"0","keyValue":"否"}],
		onChange: function(isLoan){
            if(isLoan=='1'){
                $("#loanReportDiv").show();  
                toggleValidate("#loanReportDiv",true);//加验证必填
            }else{
		        $("#loanReportDiv").hide();
		        toggleValidate("#loanReportDiv",false);//加验证必填
            }
        }
	});

});
 </script>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<div>
	<c:if test="${paramType=='per'}">
		<%@include file="loanUpdatePer.jsp"%>
		<c:if test="${mainFlag == 0}">
			<table id="personVal_${mainFlag}">
				<div style="margin-left: 10px; margin-top: 10px; font-size: 14px;">
					<strong>借款信息</strong>
				</div>
				<hr />
				<tbody>
					<tr>
						<td>
							<SPAN style="color: red">*</SPAN>借款金额:
						</td>
						<td>
							<input class="easyui-numberspinner" id="amount" name="amount" 
						 	data-options="min:500000,increment:50000,precision:2,groupSeparator:','
						 , onChange: function(value){
							if(value!=null&&value!=''){
								var cz=value%50000;
								if(cz>0){
									$(this).numberspinner('setValue',value-cz);
								}
								if(value >2000000){
									$.messager.alert('提示','输入的金额为'+value+',确认要大于2000000.00!!!','info');
								}
							}else{
								$(this).numberspinner('setValue',0);
							}
						 }"
						 style="width:150px;" 
						 <c:if test="${bean!=null}">value="${bean.amount}"</c:if>
						 <c:if test="${bean==null}">value="0"</c:if>
						 />元
						</td>
						<td>
							<SPAN style="color: red">*</SPAN>用途:
						</td>
						<td>
							<input class="easyui-combobox" name="useage" id="useage"
								data-options="required:true" style="width: 152px;"
								value="${bean.useage}" editable="false" />
						</td>
						<td>
							&nbsp;详细用途:
						</td>
						<td>
							<input id="detailUseage" name="detailUseage" type="text"
								data-options="required:true,validType:['length[0,100]']"
								class="textbox easyui-validatebox" value="${bean.detailUseage}" />
						</td>
						<td>
							<SPAN style="color: red;">*</SPAN>申请类型:
						</td>
						<td>
							<input value="${bean.appChannel}" id="appChannel"
								name="appChannel" style="width: 150px;"
								data-options="required:true,validType:['length[0,18]']"
								class="easyui-combobox" editable="false" style="width:148px;" />
						</td>
					</tr>
					<tr>
						<td>
							<SPAN style="color: red">*</SPAN> 产品:
						</td>
						<td>
							<input class="easyui-combobox" name="product" id="product"
								data-options="required:true" style="width: 150px;"
								value="${bean.product}" editable="false" />
						</td>
						<td>
							<SPAN style="color: red">*</SPAN>期限:
						</td>
						<td>
							<input id="period" name="period" type="text"
								data-options="required:true,validType:['length[0,100]']"
								style="width: 126px" class="textbox easyui-validatebox"
								value="${bean.period}" readonly />
							&nbsp;月
						</td>
						<td>
							<SPAN style="color: red">*</SPAN>服务费率:
						</td>
						<td>
							<input id="sreviceFeeRate" name="sreviceFeeRate" type="text"
								data-options="required:true,validType:['length[0,100]']"
								class="textbox easyui-validatebox"
								value="${bean.sreviceFeeRate}" />%
						</td>
						<td>
							<SPAN style="color: red">*</SPAN>借款利率:
						</td>
						<td>
							<input id="interestRate" name="interestRate" type="text"
								data-options="required:true,validType:['length[0,100]']"
								class="textbox easyui-validatebox" value="${bean.interestRate}"
								readonly />%
						</td>
					</tr>
					<tr>
						<td>
							<SPAN style="color: red">*</SPAN>收款账户名:
						</td>
						<td>
							<input id="lendAccName" name="lendAccName" type="text"
								data-options="required:true,validType:['length[0,100]']"
								class="textbox easyui-validatebox" value="${bean.lendAccName}" />
						</td>
						<td>
							<SPAN style="color: red">*</SPAN>收款开户银行:
						</td>
						<td>
							<input id="lendAccBank" name="lendAccBank" type="text"
								data-options="required:true,validType:['length[0,100]']"
								class="textbox easyui-validatebox" value="${bean.lendAccBank}" />
						</td>
						<td>
							<SPAN style="color: red">*</SPAN>收款银行账号:
						</td>
						<td>
							<input id="lendAccount" name="lendAccount" type="text"
								data-options="required:true,validType:['length[0,100]','NumLetters']"
								class="textbox easyui-validatebox" value="${bean.lendAccount}" />
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;还款账户名:
						</td>
						<td>
							<input id="repayAccName" name="repayAccName" type="text"
								class="textbox easyui-validatebox" value="${bean.repayAccName}" />
						</td>
						<td>
							&nbsp;还款开户银行:
						</td>
						<td>
							<input id="repayAccBank" name="repayAccBank" type="text"
								class="textbox easyui-validatebox" value="${bean.repayAccBank}" />
						</td>
						<td>
							&nbsp;还款银行账号:
						</td>
						<td>
							<input id="repayAccount" name="repayAccount" type="text"
								data-options="validType:['length[0,20]','NumLetters']"
								class="textbox easyui-validatebox" value="${bean.repayAccount}" />
						</td>
					</tr>
				</tbody>
			</table>
		</c:if>
		<%@include file="loanUpdateOrg.jsp"%>
	</c:if>
	<c:if test="${paramType=='org'}">
		<%@include file="loanUpdateOrg.jsp"%>
		<hr />
		<c:if test="${mainFlag == 0}">
			<table id="personVal_${mainFlag}">
				<div style="margin-left: 10px; margin-top: 10px; font-size: 14px;">
					<strong>借款信息</strong>
				</div>
				<tbody>
					<tr>
						<td>
							<SPAN style="color: red">*</SPAN>借款金额:
						</td>
						<td>
							<input class="easyui-numberspinner" id="amount" name="amount" 
						 	data-options="min:500000,increment:50000,precision:2,groupSeparator:','
						 , onChange: function(value){
							if(value!=null&&value!=''){
								var cz=value%50000;
								if(cz>0){
									$(this).numberspinner('setValue',value-cz);
								}
								if(value >2000000){
									$.messager.alert('提示','输入的金额为'+value+',确认要大于2000000.00!!!','info');
								}
							}else{
								$(this).numberspinner('setValue',0);
							}
						 }"
						 style="width:150px;" 
						 <c:if test="${bean!=null}">value="${bean.amount}"</c:if>
						 <c:if test="${bean==null}">value="0"</c:if>
						 />
						</td>
						<td>
							<SPAN style="color: red">*</SPAN>用途:
						</td>
						<td>
							<input class="easyui-combobox" name="useage" id="useage"
								data-options="required:true" style="width: 152px;"
								value="${bean.useage}" editable="false" />
						</td>
						<td>
							&nbsp;详细用途:
						</td>
						<td>
							<input id="detailUseage" name="detailUseage" type="text"
								data-options="required:true,validType:['length[0,100]']"
								class="textbox easyui-validatebox" value="${bean.detailUseage}" />
						</td>
						<td>
							<SPAN style="color: red;">*</SPAN>申请类型:
						</td>
						<td>
							<input value="${loanBean.appChannel}" id="appChannel"
								name="appChannel" style="width: 150px;"
								data-options="required:true,validType:['length[0,18]']"
								class="easyui-combobox" editable="false" style="width:148px;" />
						</td>
					</tr>
					<tr>
						<td>
							<SPAN style="color: red">*</SPAN> 产品:
						</td>
						<td>
							<input value="${bean.product}" id="product" name="product"
								style="width: 150px;"
								data-options="required:true,validType:['length[0,18]']"
								class="easyui-combobox" editable="false" style="width:148px;" />
						</td>
						<td>
							<SPAN style="color: red">*</SPAN>期限:
						</td>
						<td>
							<input id="period" name="period" type="text"
								data-options="required:true,validType:['length[0,100]']"
								style="width: 126px" class="textbox easyui-validatebox"
								value="${bean.period}" readonly />
							&nbsp;月
						</td>
						<td>
							<SPAN style="color: red">*</SPAN>服务费率:
						</td>
						<td>
							<input id="sreviceFeeRate" name="sreviceFeeRate" type="text"
								data-options="required:true,validType:['length[0,100]']"
								class="textbox easyui-validatebox"
								value="${bean.sreviceFeeRate}" />
						</td>
						<td>
							<SPAN style="color: red">*</SPAN>借款利率:
						</td>
						<td>
							<input id="interestRate" name="interestRate" type="text"
								data-options="required:true,validType:['length[0,100]']"
								class="textbox easyui-validatebox" value="${bean.interestRate}"
								readonly />
						</td>
					</tr>
					<tr>
						<td>
							<SPAN style="color: red">*</SPAN>收款账户名:
						</td>
						<td>
							<input id="lendAccName" name="lendAccName" type="text"
								data-options="required:true,validType:['length[0,100]']"
								class="textbox easyui-validatebox" value="${bean.lendAccName}" />
						</td>
						<td>
							<SPAN style="color: red">*</SPAN>收款开户银行:
						</td>
						<td>
							<input id="lendAccBank" name="lendAccBank" type="text"
								data-options="required:true,validType:['length[0,100]']"
								class="textbox easyui-validatebox" value="${bean.lendAccBank}" />
						</td>
						<td>
							<SPAN style="color: red">*</SPAN>收款银行账号:
						</td>
						<td>
							<input id="lendAccount" name="lendAccount" type="text"
								data-options="required:true,validType:['length[0,100]']"
								class="textbox easyui-validatebox" value="${bean.lendAccount}" />
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;还款账户名:
						</td>
						<td>
							<input id="repayAccName" name="repayAccName" type="text"
								class="textbox easyui-validatebox" value="${bean.repayAccName}" />
						</td>
						<td>
							&nbsp;还款开户银行:
						</td>
						<td>
							<input id="repayAccBank" name="repayAccBank" type="text"
								class="textbox easyui-validatebox" value="${bean.repayAccBank}" />
						</td>
						<td>
							&nbsp;还款银行账号:
						</td>
						<td>
							<input id="repayAccount" name="repayAccount" type="text"
								class="textbox easyui-validatebox" value="${bean.repayAccount}" />
						</td>
					</tr>
				</tbody>
			</table>
		</c:if>
		<%@include file="loanUpdatePer.jsp"%>
	</c:if>
</div>

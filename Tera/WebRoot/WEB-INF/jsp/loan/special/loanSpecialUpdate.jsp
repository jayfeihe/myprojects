<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>" />
<title>借款申请审批表更新</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
<link href="css/global.css" type="text/css" rel="stylesheet" />
<link href="css/icon.css" type="text/css" rel="stylesheet" />
<link href="css/default/easyui.css" type="text/css" rel="stylesheet" />
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
				<a href="javascript:void(0);">特殊件审核</a>
			</p>
			<div class="easyui-tabs" data-options="tools:'#tab-tools'">
				<div title="审核信息" name="inputInfo" style="padding: 10px">
					<form id="updateForm">
						<table>
							<tbody>
								<input id="appId" name="appId" type="hidden" size="35"
									value="${appId}" />
								<tr>
									<td width="150px">借款人信用报告:</td>
									<td>贷记卡违约值:</td>
									<td><select id="selfCreditCardDvalue"
										name="selfCreditCardDvalue" class="easyui-combobox">
											<option value="0"
												<c:if test="${bean.selfCreditCardDvalue=='0'}">selected="selected"</c:if>>未预期</option>
											<option value="1"
												<c:if test="${bean.selfCreditCardDvalue=='1'}">selected="selected"</c:if>>30天以下</option>
											<option value="2"
												<c:if test="${bean.selfCreditCardDvalue=='2'}">selected="selected"</c:if>>30~60天</option>
											<option value="3"
												<c:if test="${bean.selfCreditCardDvalue=='3'}">selected="selected"</c:if>>60~90天</option>
											<option value="4"
												<c:if test="${bean.selfCreditCardDvalue=='4'}">selected="selected"</c:if>>90~120天</option>
											<option value="5"
												<c:if test="${bean.selfCreditCardDvalue=='5'}">selected="selected"</c:if>>120~150天</option>
											<option value="6"
												<c:if test="${bean.selfCreditCardDvalue=='6'}">selected="selected"</c:if>>150~180天</option>
											<option value="7"
												<c:if test="${bean.selfCreditCardDvalue=='7'}">selected="selected"</c:if>>180天以上</option>
									</select></td>
									<td>借款人贷记卡金额:</td>
									<td><input id="selfCreditCardDamount"
										name="selfCreditCardDamount" style="width: 80px;" type="text"
										editable="false" data-options="min:0,precision:2"
										class="textbox easyui-numberbox"
										value="${bean.selfCreditCardDamount}" /></td>
									<td>借款人贷记卡备注:</td>
									<td><input id="selfCreditCardRemark"
										name="selfCreditCardRemark" type="text"
										data-options="validType:['length[0,100]']"
										class="textbox easyui-validatebox"
										value="${bean.selfCreditCardRemark}" /></td>
								</tr>
								<tr>
									<td><input id="selfCreditMark" name="selfCreditMark"
										type="radio" value='0'
										<c:if test="${bean.selfCreditMark=='0'}">checked='checked'</c:if> />正常
										<!-- 							<br/> --> <input id="selfCreditMark"
										name="selfCreditMark" type="radio" value="1"
										<c:if test="${bean.selfCreditMark=='1'}">checked='checked'</c:if> />异常
									</td>
									<td>借款人贷款违约值:</td>
									<td><select id="selfLoanDvalue" name="selfLoanDvalue"
										class="easyui-combobox">
											<option value="0"
												<c:if test="${bean.selfLoanDvalue=='0'}">selected="selected"</c:if>>未预期</option>
											<option value="1"
												<c:if test="${bean.selfLoanDvalue=='1'}">selected="selected"</c:if>>30天以下</option>
											<option value="2"
												<c:if test="${bean.selfLoanDvalue=='2'}">selected="selected"</c:if>>30~60天</option>
											<option value="3"
												<c:if test="${bean.selfLoanDvalue=='3'}">selected="selected"</c:if>>60~90天</option>
											<option value="4"
												<c:if test="${bean.selfLoanDvalue=='4'}">selected="selected"</c:if>>90~120天</option>
											<option value="5"
												<c:if test="${bean.selfLoanDvalue=='5'}">selected="selected"</c:if>>120~150天</option>
											<option value="6"
												<c:if test="${bean.selfLoanDvalue=='6'}">selected="selected"</c:if>>150~180天</option>
											<option value="7"
												<c:if test="${bean.selfLoanDvalue=='7'}">selected="selected"</c:if>>180天以上</option>
									</select></td>
									<td>借款人贷款金额:</td>
									<td><input id="selfLoanDamount" name="selfLoanDamount"
										style="width: 80px;" type="text" editable="false"
										data-options="min:0,precision:2"
										class="textbox easyui-numberbox"
										value="${bean.selfLoanDamount}" /></td>
									<td>借款人贷款备注:</td>
									<td><input id="selfLoanRemark" name="selfLoanRemark"
										type="text" data-options="validType:['length[0,100]']"
										class="textbox easyui-validatebox"
										value="${bean.selfLoanRemark}" /></td>
								</tr>
								<tr>
									<td>配偶信用正常标志:</td>
									<td>配偶贷记卡违约值:</td>
									<td><select id="spouseCreditCardDvalue"
										name="spouseCreditCardDvalue" class="easyui-combobox">
											<option value="0"
												<c:if test="${bean.spouseCreditCardDvalue=='0'}">selected="selected"</c:if>>未预期</option>
											<option value="1"
												<c:if test="${bean.spouseCreditCardDvalue=='1'}">selected="selected"</c:if>>30天以下</option>
											<option value="2"
												<c:if test="${bean.spouseCreditCardDvalue=='2'}">selected="selected"</c:if>>30~60天</option>
											<option value="3"
												<c:if test="${bean.spouseCreditCardDvalue=='3'}">selected="selected"</c:if>>60~90天</option>
											<option value="4"
												<c:if test="${bean.spouseCreditCardDvalue=='4'}">selected="selected"</c:if>>90~120天</option>
											<option value="5"
												<c:if test="${bean.spouseCreditCardDvalue=='5'}">selected="selected"</c:if>>120~150天</option>
											<option value="6"
												<c:if test="${bean.spouseCreditCardDvalue=='6'}">selected="selected"</c:if>>150~180天</option>
											<option value="7"
												<c:if test="${bean.spouseCreditCardDvalue=='7'}">selected="selected"</c:if>>180天以上</option>
									</select></td>
									<td>配偶贷记卡金额:</td>
									<td><input id="spouseCreditCardDamount"
										name="spouseCreditCardDamount" style="width: 80px;"
										type="text" editable="false" data-options="min:0,precision:2"
										class="textbox easyui-numberbox"
										value="${bean.spouseCreditCardDamount}" /></td>
									<td>配偶贷记卡备注:</td>
									<td><input id="spouseCreditCardRemark"
										name="spouseCreditCardRemark" type="text"
										data-options="validType:['length[0,100]']"
										class="textbox easyui-validatebox"
										value="${bean.spouseCreditCardRemark}" /></td>
								</tr>
								<tr>
									<td><input id="spouseCreditMark" name="spouseCreditMark"
										type="radio" value='0'
										<c:if test="${bean.spouseCreditMark=='0'}">checked='checked'</c:if> />正常
										<!-- 							<br/> --> <input id="spouseCreditMark"
										name="spouseCreditMark" type="radio" value="1"
										<c:if test="${bean.spouseCreditMark=='1'}">checked='checked'</c:if> />异常
									</td>
									<td>配偶贷款违约值:</td>
									<td><select id="spouseLoanDvalue" name="spouseLoanDvalue"
										class="easyui-combobox">
											<option value="0"
												<c:if test="${bean.spouseLoanDvalue=='0'}">selected="selected"</c:if>>未预期</option>
											<option value="1"
												<c:if test="${bean.spouseLoanDvalue=='1'}">selected="selected"</c:if>>30天以下</option>
											<option value="2"
												<c:if test="${bean.spouseLoanDvalue=='2'}">selected="selected"</c:if>>30~60天</option>
											<option value="3"
												<c:if test="${bean.spouseLoanDvalue=='3'}">selected="selected"</c:if>>60~90天</option>
											<option value="4"
												<c:if test="${bean.spouseLoanDvalue=='4'}">selected="selected"</c:if>>90~120天</option>
											<option value="5"
												<c:if test="${bean.spouseLoanDvalue=='5'}">selected="selected"</c:if>>120~150天</option>
											<option value="6"
												<c:if test="${bean.spouseLoanDvalue=='6'}">selected="selected"</c:if>>150~180天</option>
											<option value="7"
												<c:if test="${bean.spouseLoanDvalue=='7'}">selected="selected"</c:if>>180天以上</option>
									</select></td>
									<td>配偶贷款金额:</td>
									<td><input id="spouseLoanDamount" name="spouseLoanDamount"
										style="width: 80px;" type="text" editable="false"
										data-options="min:0,precision:2"
										class="textbox easyui-numberbox"
										value="${bean.spouseLoanDamount}" /></td>
									<td>配偶贷款备注:</td>
									<td><input id="spouseLoanRemark" name="spouseLoanRemark"
										type="text" data-options="validType:['length[0,100]']"
										class="textbox easyui-validatebox"
										value="${bean.spouseLoanRemark}" /></td>
								</tr>
								<tr>
									<td>企业信用正常标志:</td>
									<td>企业贷款违约值:</td>
									<td><select id="selfCreditCardDvalue" name="entLoanDvalue"
										class="easyui-combobox">
											<option value="0"
												<c:if test="${bean.entLoanDvalue=='0'}">selected="selected"</c:if>>未预期</option>
											<option value="1"
												<c:if test="${bean.entLoanDvalue=='1'}">selected="selected"</c:if>>30天以下</option>
											<option value="2"
												<c:if test="${bean.entLoanDvalue=='2'}">selected="selected"</c:if>>30~60天</option>
											<option value="3"
												<c:if test="${bean.entLoanDvalue=='3'}">selected="selected"</c:if>>60~90天</option>
											<option value="4"
												<c:if test="${bean.entLoanDvalue=='4'}">selected="selected"</c:if>>90~120天</option>
											<option value="5"
												<c:if test="${bean.entLoanDvalue=='5'}">selected="selected"</c:if>>120~150天</option>
											<option value="6"
												<c:if test="${bean.entLoanDvalue=='6'}">selected="selected"</c:if>>150~180天</option>
											<option value="7"
												<c:if test="${bean.entLoanDvalue=='7'}">selected="selected"</c:if>>180天以上</option>
									</select></td>
									<td>企业贷款余额:</td>
									<td><input id="entLoanDamount" name="entLoanDamount"
										type="text" style="width: 80px;" editable="false"
										data-options="min:0,precision:2"
										class="textbox easyui-numberbox"
										value="${bean.entLoanDamount}" /></td>
									<td>企业贷款备注:</td>
									<td><input id="entLoanRemark" name="entLoanRemark"
										type="text" data-options="validType:['length[0,100]']"
										class="textbox easyui-validatebox"
										value="${bean.entLoanRemark}" /></td>
								</tr>
								<tr>
									<td><input id="entLoanMark" name="entLoanMark"
										type="radio" value='0'
										<c:if test="${bean.entLoanMark=='0'}">checked='checked'</c:if> />正常
										<!-- 							<br/> --> <input id="entLoanMark"
										name="entLoanMark" type="radio" value="1"
										<c:if test="${bean.entLoanMark=='1'}">checked='checked'</c:if> />异常
									</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td>企业贷款到期日:</td>
									<td><input id="entLoanExpiryDate" name="entLoanExpiryDate"
										type="text" editable="false" class="textbox easyui-datebox"
										value="${bean.entLoanExpiryDateStr}" /></td>
								</tr>
								<tr>
									<td colspan="7" align="center"><hr width="100%" size=1
											color=#E0ECFF align=center noshade></td>
								</tr>
								<tr>
									<td colspan="7" style="font-weight: bold; font-style: normal;">法院执行信息查询情况</td>
								</tr>
								<tr>
									<td>&nbsp; &nbsp; &nbsp;1) 公司查询结果:</td>
									<td><input id="courtCompany" name="courtCompany"
										type="radio" value='0'
										<c:if test="${bean.courtCompany=='0'}">checked='checked'</c:if> />无
										&nbsp; &nbsp; <input id="courtCompany" name="courtCompany"
										type="radio" value="1"
										<c:if test="${bean.courtCompany=='1'}">checked='checked'</c:if> />有
									</td>
									<td>备注:</td>
									<td colspan="4"><input id="courtCompanyRemark"
										style="width: 80%;" name="courtCompanyRemark" type="text"
										data-options="validType:['length[0,100]']"
										class="textbox easyui-validatebox"
										value="${bean.courtCompanyRemark}" /></td>
								</tr>
								<tr>
									<td>&nbsp; &nbsp; &nbsp;2) 公司法人或实际控制人查询结果:</td>
									<td><input id="courtLegal" name="courtLegal" type="radio"
										value='0'
										<c:if test="${bean.courtLegal=='0'}">checked='checked'</c:if> />无
										&nbsp; &nbsp; <input id="courtLegal" name="courtLegal"
										type="radio" value="1"
										<c:if test="${bean.courtLegal=='1'}">checked='checked'</c:if> />有
									</td>
									<td>备注:</td>
									<td colspan="4"><input style="width: 80%;"
										id="courtLegalRemark" name="courtLegalRemark" type="text"
										data-options="validType:['length[0,100]']"
										class="textbox easyui-validatebox"
										value="${bean.courtLegalRemark}" /></td>
								</tr>
								<tr>
									<td>&nbsp; &nbsp; &nbsp;3) 抵押担保人查询结果:</td>
									<td><input id="courtGuarantee" name="courtGuarantee"
										type="radio" value='0'
										<c:if test="${bean.courtGuarantee=='0'}">checked='checked'</c:if> />无
										&nbsp; &nbsp; <input id="courtGuarantee" name="courtGuarantee"
										type="radio" value="1"
										<c:if test="${bean.courtGuarantee=='1'}">checked='checked'</c:if> />有
									</td>
									<td>备注:</td>
									<td colspan="4"><input style="width: 80%;"
										id="courtGuaranteeRemark" name="courtGuaranteeRemark"
										type="text" data-options="validType:['length[0,100]']"
										class="textbox easyui-validatebox"
										value="${bean.courtGuaranteeRemark}" /></td>
								</tr>
								<tr>
									<td>&nbsp; &nbsp; &nbsp;工商网:</td>
									<td><input id="industryNetwork" name="industryNetwork"
										type="radio" value="1"
										<c:if test="${bean.industryNetwork=='1'}">checked='checked'</c:if> />一致
										&nbsp; &nbsp; <input id="industryNetwork"
										name="industryNetwork" type="radio" value='0'
										<c:if test="${bean.industryNetwork=='0'}">checked='checked'</c:if> />不一致
									</td>
									<td>工商网备注:</td>
									<td><input id="industryNetworkRemark"
										name="industryNetworkRemark" type="text"
										data-options="validType:['length[0,100]']"
										class="textbox easyui-validatebox"
										value="${bean.industryNetworkRemark}" /></td>
									<td>114查询备注:</td>
									<td><input id="network114" name="network114" type="text"
										data-options="validType:['length[0,100]']"
										class="textbox easyui-validatebox" value="${bean.network114}" /></td>
								</tr>
								<tr>
									<td>&nbsp; &nbsp; &nbsp;组织结构网:</td>
									<td><input id="orgNetwork" name="orgNetwork" type="radio"
										value="1"
										<c:if test="${bean.orgNetwork=='1'}">checked='checked'</c:if> />一致
										&nbsp; &nbsp; <input id="orgNetwork" name="orgNetwork"
										type="radio" value='0'
										<c:if test="${bean.orgNetwork=='0'}">checked='checked'</c:if> />不一致
									</td>
									<td>组织结构网备注:</td>
									<td><input id="orgNetworkRemark" name="orgNetworkRemark"
										type="text" data-options="validType:['length[0,100]']"
										class="textbox easyui-validatebox"
										value="${bean.orgNetworkRemark}" /></td>
									<td>网查备注:</td>
									<td><input id="network114Remark" name="network114Remark"
										type="text" data-options="validType:['length[0,100]']"
										class="textbox easyui-validatebox"
										value="${bean.network114Remark}" /></td>
								</tr>
								<tr>
									<td colspan="7" align="center"><hr width="100%" size=2
											color=#E0ECFF align=center noshade></td>
								</tr>
								<tr>
									<td colspan="7" style="font-weight: bold; font-style: normal;">电话调查
									</td>
								</tr>
								<tr>
									<td>&nbsp; &nbsp; &nbsp;法人:</td>
									<td><input id="telLegal" name="telLegal" type="radio"
										value="1"
										<c:if test="${bean.telLegal=='1'}">checked='checked'</c:if> />已打
										&nbsp; &nbsp; <input id="telLegal" name="telLegal"
										type="radio" value='0'
										<c:if test="${bean.telLegal=='0'}">checked='checked'</c:if> />未打
									</td>
									<td>备注:</td>
									<td colspan="4"><input style="width: 80%;"
										id="telLegalRemark" name="telLegalRemark" type="text"
										data-options="validType:['length[0,100]']"
										class="textbox easyui-validatebox"
										value="${bean.telLegalRemark}" /></td>
								</tr>
								<tr>
									<td>&nbsp; &nbsp; &nbsp;代理人:</td>
									<td><input id="telAgent" name="telAgent" type="radio"
										value="1"
										<c:if test="${bean.telAgent=='1'}">checked='checked'</c:if> />已打
										&nbsp; &nbsp; <input id="telAgent" name="telAgent"
										type="radio" value='0'
										<c:if test="${bean.telAgent=='0'}">checked='checked'</c:if> />未打

									</td>
									<td>备注:</td>
									<td colspan="4"><input style="width: 80%;"
										id="telAgentRemark" name="telAgentRemark" type="text"
										data-options="validType:['length[0,100]']"
										class="textbox easyui-validatebox"
										value="${bean.telAgentRemark}" /></td>
								</tr>
								<tr>
									<td>&nbsp; &nbsp; &nbsp;财务主管:</td>
									<td><input id="telCfo" name="telCfo" type="radio"
										value="1"
										<c:if test="${bean.telCfo=='1'}">checked='checked'</c:if> />已打
										&nbsp; &nbsp; <input id="telCfo" name="telCfo" type="radio"
										value='0'
										<c:if test="${bean.telCfo=='0'}">checked='checked'</c:if> />未打
									</td>
									<td>备注:</td>
									<td colspan="4"><input style="width: 80%;"
										id="telCfoRemark" name="telCfoRemark" type="text"
										data-options="validType:['length[0,100]']"
										class="textbox easyui-validatebox"
										value="${bean.telCfoRemark}" /></td>
								</tr>
								<tr>
									<td>&nbsp; &nbsp; &nbsp;购销合同:</td>
									<td><input id="telContract" name="telContract"
										type="radio" value="1"
										<c:if test="${bean.telContract=='1'}">checked='checked'</c:if> />已打
										&nbsp; &nbsp; <input id="telContract" name="telContract"
										type="radio" value='0'
										<c:if test="${bean.telContract=='0'}">checked='checked'</c:if> />未打
									</td>
									<td>备注:</td>
									<td colspan="4"><input style="width: 80%;"
										id="telContractRemark" name="telContractRemark" type="text"
										data-options="validType:['length[0,100]']"
										class="textbox easyui-validatebox"
										value="${bean.telContractRemark}" /></td>
								</tr>

								<tr>
									<td colspan="7" align="center"><hr width="100%" size=2
											color=#E0ECFF align=center noshade></td>
								</tr>
								</tbody>
								</table>
								<table width="100%">
									<tr>
										<td id="imgDiv">
										<jsp:include page="/img/imgSlidePath.do?appId=${bean.appId}" />
										</td>
									</tr>
								</table>
								<table>
								<tbody>
								<tr>
									<td colspan="7" style="font-weight: bold; font-style: normal;">审批结论
									</td>
								</tr>

								<tr>
									<td width="160px">&nbsp; &nbsp; &nbsp;审批金额:</td>
									<td><input id="approvalAmount" 
										name="approvalAmount" type="text" editable="false"
										class="easyui-numberbox" 
										data-options="required:true,groupSeparator:','"
										<c:if test="${bean!=null}">value="${bean.approvalAmount}"</c:if>
										<c:if test="${bean==null}">value="${appBean.amount}"</c:if>
										/>元
									</td>
									<td>审批期限:</td>
									<td><input id="approvalPeriod" style="width: 80px;"
										name="approvalPeriod" type="text" editable="false"
										data-options="required:true,min:0,precision:0"
										class="textbox easyui-numberbox"
										<c:if test="${bean!=null}">value="${bean.approvalPeriod}"</c:if>
										<c:if test="${bean==null}">value="${appBean.period}"</c:if>
										
										/>月</td>
									<td>审批服务费率:</td>
									<td><input id="approvalServiceRate" style="width: 80px;"
										name="approvalServiceRate" type="text" editable="false"
										data-options="required:true,min:0,max:100,precision:2"
										class="textbox easyui-numberbox"
										
										<c:if test="${bean!=null}">value="${bean.approvalServiceRate}" </c:if>
										<c:if test="${bean==null}">value="${appBean.sreviceFeeRate}"</c:if>
										
										/>%</td>
								</tr>
								<tr>
									<td>&nbsp; &nbsp; &nbsp;需要第三方购销合同:</td>
									<td><input id="thirdPartyContract"
										name="thirdPartyContract" type="radio" value="1"
										<c:if test="${bean.thirdPartyContract=='1'}">checked='checked'</c:if> />是
										&nbsp; &nbsp; <input id="thirdPartyContract"
										name="thirdPartyContract" type="radio" value='0'
										<c:if test="${bean.thirdPartyContract=='0'}">checked='checked'</c:if> />否
									</td>
									<td>担保条件:</td>
									<td colspan="4"><textarea id="guaranteeCondition"
											name="guaranteeCondition"
											data-options="validType:['length[0,500]']"
											style="width: 100%;height:100px !important;"
											class="textbox easyui-validatebox">${bean.guaranteeCondition}</textarea>
									</td>
								</tr>
								<tr>
									<input name="subbpm" id="subbpm" type="hidden" value="" />
									<input name="auditResult" id="auditResult" type="hidden"
										value="" />
									<input id="auditText" name="auditText" type="hidden" value="" />
									<td>撮合类型:<input id="matchType" name="matchType"
										style="width: 80px;" value="${bean.matchType}"
										class="easyui-combobox" editable="false" />
									</td>
									<td>
										<input type="button" value="通过" class="btn" onclick="bmmupdateFunction(1)"/>
							
									</td>
								</tr>
								<tr>
									<td></td>
									<td>
										<input type="button" value="退回" class="btn" onclick="bmmupdateFunction(0)"/>
									</td>
									<td>
										<select id="denyToPerson" name="denyToRole"  class="easyui-combobox">
											<option value="0" <c:if test="${bean.entLoanDvalue=='0'}">selected="selected"</c:if> >风险专员</option>
											 <option value="1" <c:if test="${bean.entLoanDvalue=='1'}">selected="selected"</c:if> >营业部经理</option>
										</select>
									</td>
									<td>
										不通过原因：<input id="auditText_btg" type="text" class="textbox easyui-validatebox"/>
									</td>
								</tr>
								<tr>
									<td></td>
									<td>
										<input type="button" value="拒件" class="btn" onclick="bmmupdateFunction(2)"/>
									</td>
									<td>
										拒件原因：<input id="auditText_jujian" type="text" class="textbox easyui-validatebox"/>
									</td>
								</tr>
								<tr>
									<td>
										<input type="button" value="保存" class="btn" onclick="updateFunction()"/>
										<input type="button" value="返回" class="btn" onclick="javascript:back()"/>
									</td>
								</tr>
								<tr>
									<td>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
				<jsp:include page="/loan/app/read.do?id=${id}" ></jsp:include>
			</div>
			
		</div>
	</div>

	<!-- <div id="loading" class="easyui-window" title=""
		data-options="border:false,modal:true,closed:true,draggable:false,resizable:false">
		<img src="img/loading.gif" alt="加载中..." />
	</div> -->

</body>

<script type="text/javascript">

function bmmupdateFunction(val) {
	$('#subbpm').val('trueSubbpm');
	$('#auditResult').val(val);
	if(val==0){
		var btgyy=$('#auditText_btg').val();
		if(btgyy==null||btgyy==""){
			
			$.messager.alert('消息', "请填写不通过信息。");
			$('#subbpm').val('');
			return;
		}
		$('#auditText').val(btgyy);
	}else if(val==1){
		var btgyy=$('#matchType').combobox('getValue');
		if(btgyy==null||btgyy==""){
			
			$.messager.alert('消息', "撮合类型为必选项。");
			$('#subbpm').val('');
			return;
		}else if(btgyy=="1"){
			var approvalAmount = $('#approvalAmount').numberbox('getValue');
			if(approvalAmount<5000000){
				
				$.messager.alert('消息', "审核金额超过 (5,000,000) 才可以使用人工撮合。");
				$('#subbpm').val('');
				return
			}
		}
	}else if(val==2){
		var btgyy=$('#auditText_jujian').val();
		if(btgyy==null||btgyy==""){
			
			$.messager.alert('消息', "请填写拒件信息。");
			$('#subbpm').val('');
			return;
		}
		$('#auditText').val(btgyy);
	}
	updateFunction();
	$('#subbpm').val('');
}

//更新保存
function updateFunction() {
	//去掉 input 输入的 前后空格
	//$('#updateForm').find('input').each(function(){
	$("div[name='inputInfo']").find('input').each(function(){
		$(this).val($.trim($(this).val()));
	});
	//验证表单验证是否通过
	if(false == $('#updateForm').form('validate') ){
		
		$.messager.alert('消息', "页面有不符合规范内容，请正确填写！");
		return;
	}
	//弹出异步加载 遮罩
	openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "loan/special/save.do",
		data : encodeURI(params),
		success : function(data) {
			if ("true"==data.success) {
				//关闭遮罩，弹出消息框
				closeMask();
				
				$.messager.alert('消息', data.message,"info", function(){
//	                   	var url= "<%=basePath%>" + "loan/special/query.do";
//						window.location=url;
						window.history.go(-1);
            	});
            } else {
            	closeMask();
				
				$.messager.alert('消息', data.message);
				//按钮生效
				$(".btn").removeAttr("disabled");
            }
		},
		error : function() {
			closeMask();
			
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
	});
}

//返回
function back(){
	window.history.go(-1);
}

//打开Loading遮罩并修改样式
/* function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
} */
//页面加载完动作
	$(document).ready(function() {
		//var tsurl = "sys/datadictionary/listjason.do?keyName=matchtype";
		$("#matchType").combobox("clear");
		$('#matchType').combobox({
			//url : tsurl,
			valueField :'keyProp',
			textField : 'keyValue',
			data:dataDictJson.matchtype
		});
		//嵌套的回显信息。 设置为disabled 只用于查看， 删除 EasyUI 表单验证
		$("div[name='redInfo']").find("input").attr("disabled", "disabled");
		$("div[name='redInfo']").find("input").removeAttr("data-options");
		$("div[name='redInfo']").find("select").attr("disabled", "disabled");
		$("div[name='redInfo']").find("select").removeAttr("data-options");
		//删除  担保物动态添加 操作
		$("div[name='redInfo']").find("img[id='onclosecoll']").remove();
		$("div[name='redInfo']").find("img[id='onAppContactsPer']").remove();
		$("div[name='redInfo']").find("img[id='onAppContactsOrg']").remove();
		$("div[name='redInfo']").find("img[id='onclose']").remove();
	});
</script>
</html>


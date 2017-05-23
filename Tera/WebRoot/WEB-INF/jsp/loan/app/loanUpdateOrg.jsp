<%@ page contentType="text/html; charset=UTF-8"%>
    <table id="groupInfo_${mainFlag}">
    <div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"> <strong>机构信息</strong></div>
    	<hr />
    <input id="orgLoanAppId" name="orgLoanAppId" type="hidden" size="35" value="${loanBean.orgLoanAppId}" />
        <tbody>
            <tr>
                <td><SPAN style="color:red">*</SPAN>机构全称:</td>
                <td><input id="orgName" name="orgName" 
                type="text" data-options="required:true,validType:['length[0,100]']" 
                class="textbox easyui-validatebox"value="${loanBean.orgName}"
                <c:if test="${mainFlag == 0&&paramType=='org'&&loanBean!=null}">
                readonly='readonly'
				</c:if>
                /></td>
					<td><SPAN style="color:red">*</SPAN>
						注册时间:
					</td>
					<td>
						<input id="orgRegDate" name="orgRegDate" type="text" editable="false" data-options="required:true,validType:['length[0,100]']"
							class="textbox easyui-datebox" editable="false" value="${loanBean.orgRegDate}" />
					</td>
					<td><SPAN style="color:red">*</SPAN>注册资本:</td>
				<td>
					<input value="${loanBean.orgRegAmount}" class="easyui-numberspinner" data-options="groupSeparator:','" name="orgRegAmount" id="orgRegAmount"  />	
				</td>
			</tr>
			<tr>
				<td><SPAN style="color:red">*</SPAN>证件类型:</td>
				<td>
					<input class="easyui-combobox" name="orgIdType"
					 value="${loanBean.orgIdType}" id="orgIdType" 
					 data-options="<c:if test="${mainFlag == 0&&paramType=='org'}">
					 	  onSelect:getCustomer,
					 </c:if>required:true"
					 style="width:152px;" editable="false"/>
				</td>
				
				<td><SPAN style="color:red">*</SPAN>证件编码:</td>
				<td><input id="orgIdNo" name="orgIdNo" type="text" 
				data-options="required:true,validType:['length[0,18]','idNo']" 
				class="textbox easyui-validatebox" value="${loanBean.orgIdNo}"
				<c:if test="${mainFlag == 0&&paramType=='org'}">
					 onblur="getCustomer();"
				</c:if>
				/></td>
            	<td><SPAN style="color:red">*</SPAN>实缴资本:</td>
				<td>
					<input type="text" class="easyui-numberspinner" data-options="groupSeparator:','" name="orgAcctualAmount" value="${loanBean.orgAcctualAmount}" id="orgAcctualAmount" />
				</td>
            </tr>
            <tr>
           		 <input id="fdOrgId" name="fdOrgId" type="hidden" value="${loanBean.fdOrgId}" />
				<td><SPAN style="color:red">*</SPAN>法定代表人姓名:</td>
				<td><input id="fdOrgName" name="fdOrgName" type="text" data-options="required:true,validType:['length[0,16]']" class="textbox easyui-validatebox"value="${loanBean.fdOrgName}"/></td>
				<td><SPAN style="color:red">*</SPAN>手机号:</td>
				<td><input id="fdOrgMobile" name="fdOrgMobile" type="text" data-options="required:true,validType:['mobile']" class="textbox easyui-validatebox"value="${loanBean.fdOrgMobile}"/></td>
				<td><SPAN style="color:red">*</SPAN>证件类型:</td>
				<td>
					<input value="${loanBean.fdorgIdType}" class="easyui-combobox" name="fdorgIdType" id="fdorgIdType" data-options="required:true" style="width:152px;" editable="false" />
				</td>
				
				<td><SPAN style="color:red">*</SPAN>证件号码:</td>
				<td><input id="fdOrgIdNo" name="fdOrgIdNo" type="text" data-options="required:true,validType:['length[0,18]','idNo']" class="textbox easyui-validatebox"value="${loanBean.fdOrgIdNo}"/></td>
			</tr>
			<tr>
			 	<input id="sqOrgId" name="sqOrgId" type="hidden" value="${loanBean.sqOrgId}" />
				<td>&nbsp;授权代理人姓名:</td>
				<td><input id="sqOrgName" name="sqOrgName" type="text" data-options="required:false,validType:['length[0,16]']" class="textbox easyui-validatebox"value="${loanBean.sqOrgName}"/></td>
				<td>&nbsp;手机号:</td>
				<td><input id="sqOrgMobile" name="sqOrgMobile" type="text" data-options="validType:['mobile']" class="textbox easyui-validatebox"value="${loanBean.sqOrgMobile}"/></td>
				<td>&nbsp;证件类型:</td>
				<td>
					<input value="${loanBean.sqOrgIdType}" class="easyui-combobox" name="sqOrgIdType" id="sqOrgIdType" data-options="required:false" style="width:152px;" editable="false" />
				</td>
				
				<td>&nbsp;证件号码:</td>
				<td><input id="sqOrgIdNo" name="sqOrgIdNo" type="text" data-options="required:false,validType:['length[0,18]','idNo']" class="textbox easyui-validatebox"value="${loanBean.sqOrgIdNo}"/></td>
			</tr>
			<tr>
				<input id="cwOrgId" name="cwOrgId" type="hidden" value="${loanBean.cwOrgId}" />
				<td>&nbsp;财务主管姓名:</td>
				<td><input id="cwOrgName" name="cwOrgName" type="text" data-options="required:false,validType:['length[0,16]']" class="textbox easyui-validatebox"value="${loanBean.cwOrgName}"/></td>
				<td>&nbsp;手机号:</td>
				<td><input id="cwOrgMobile" name="cwOrgMobile" type="text" data-options="validType:['mobile']" class="textbox easyui-validatebox"value="${loanBean.cwOrgMobile}"/></td>
				<td>&nbsp;证件类型:</td>
				<td>
					<input value="${loanBean.cwOrgIdType}" class="easyui-combobox" name="cwOrgIdType" id="cwOrgIdType" data-options="required:false" style="width:152px;" editable="false" />
				</td>
				
				<td>&nbsp;证件号码:</td>
				<td><input id="cwOrgIdNo" name="cwOrgIdNo" type="text" data-options="required:false,validType:['length[0,18]','idNo']" class="textbox easyui-validatebox"value="${loanBean.cwOrgIdNo}"/></td>
            </tr>
            <tr>
            	<td>&nbsp;组织机构代码证:</td>
				<td colspan="2"><input id="orgOrgCodeNo" name="orgOrgCodeNo" type="text" style = "width: 200px;"data-options="required:false,validType:['length[0,16]']" class="textbox easyui-validatebox" value="${loanBean.orgOrgCodeNo}"/></td>
            	<td><span style = "margin-bottom: 30px;">有效期:&nbsp;</span>
            	<input id="orgOrgCodeExpiryDate" name="orgOrgCodeExpiryDate"  style = "width: 145px;"data-options="required:false,validType:['length[0,16]']" class="textbox easyui-datebox" editable="false" value="${loanBean.orgOrgCodeExpiryDateStr}"/>
            	</td>
            </tr>
             <tr>
				<td>
					&nbsp;税务登记证(地税)：
				</td>
				<td  colspan="2">
					<input id="orgTaxRegNo" name="orgTaxRegNo" type="text"
						style="width: 200px;"
						data-options="required:false,validType:['length[0,18]']"
						class="textbox easyui-validatebox" value="${loanBean.orgTaxRegNo}" />
				</td>
			</tr>
			<tr>
            	<td>&nbsp;基本账户开户银行:</td>
				<td colspan="2"><input id="orgBaiscAccountBank" name="orgBaiscAccountBank" type="text" style = "width: 200px;"data-options="validType:['length[0,16]']" class="textbox easyui-validatebox"value="${loanBean.orgBaiscAccountBank}"/></td>
            	<td ><span style = "margin-bottom: 30px;">&nbsp;&nbsp;帐号:&nbsp;&nbsp;</span>
            	<input id="orgBaiscAccount" name="orgBaiscAccount" type="text" style = "width: 145px;"data-options="validType:['length[0,19]']" class="textbox easyui-validatebox"value="${loanBean.orgBaiscAccount}"/>
            	</td>
            </tr>
            <table id = "orgInfo_${mainFlag }">
            <tr>
            	<td colspan="5"> &nbsp;行业代码1:
				<input id="orgIndustry1" name="orgIndustry1" type="text" data-options="validType:['length[0,100]']" class="easyui-combobox" editable="false" value="${loanBean.orgIndustry1}"/>
           		行业代码2:
				<input id="orgIndustry2" name="orgIndustry2" type="text" data-options="validType:['length[0,100]']" class="easyui-combobox" editable="false" value="${loanBean.orgIndustry2}"/>
            	行业代码3:
				<input id="orgIndustry3" name="orgIndustry3" type="text" data-options="validType:['length[0,100]']" class="easyui-combobox" editable="false" value="${loanBean.orgIndustry3}"/>
            	</td>
            </tr>
            <tr>
            	<td>&nbsp;经营范围:&nbsp;&nbsp;
            	<input id="orgBizzScope" name="orgBizzScope" type="text" style = "width: 200px;"data-options="required:false,validType:['length[0,16]']" class="textbox easyui-validatebox"value="${loanBean.orgBizzScope}"/></td>
            	<td ><span style = "margin-bottom: 30px;">&nbsp;经营实体属性:&nbsp;</span>
            	<input class= "easyui-combobox"id="orgCompanyType" name="orgCompanyType" type="text" style = "width: 145px;" editable="false" data-options="required:false" value="${loanBean.orgCompanyType}"/>
            	</td>
            </tr>
            <tr>
				<td>&nbsp;主要销售产品:
				<input id="orgMainProduct" name="orgMainProduct" type="text" data-options="required:false,validType:['length[0,16]']" class="textbox easyui-validatebox"value="${loanBean.orgMainProduct}"/></td>
				<td >&nbsp;上一年度营业额:&nbsp;<input id="orgLastYearTurnover" name="orgLastYearTurnover" type="text"  class="easyui-numberspinner" data-options="groupSeparator:','" value="${loanBean.orgLastYearTurnover}"/>
				&nbsp;&nbsp;&nbsp;近三个月营业额:&nbsp;<input id="orgLast3mTurnover" name="orgLast3mTurnover" type="text" class="easyui-numberspinner" data-options="groupSeparator:','" value="${loanBean.orgLast3mTurnover}"/></td>
            </tr>
            <tr>
				<td >&nbsp;以往主要合作银行或公司：&nbsp;
				<input id="orgCooperateBankCompany" name="orgCooperateBankCompany" type="text" data-options="required:false,validType:['length[0,16]']" class="textbox easyui-validatebox"value="${loanBean.orgCooperateBankCompany}"/></td>
				<td >&nbsp;日常结算银行:&nbsp;&nbsp;<input id="orgDailyClearBank" name="orgDailyClearBank" type="text" data-options="required:false,validType:['length[0,16]']" class="textbox easyui-validatebox"value="${loanBean.orgDailyClearBank}"/></td>
            </tr>
            <tr>
            	<td>&nbsp;贷款融资余额:
				<input id="orgLoanBalance" name="orgLoanBalance" type="text" class="easyui-numberspinner" data-options="groupSeparator:','" value="${loanBean.orgLoanBalance}"/></td>
				<td >&nbsp;融资公司名称:&nbsp;<input id="orgFinanceCompany" name="orgFinanceCompany" type="text" data-options="required:false,validType:['length[0,16]']" class="textbox easyui-validatebox"value="${loanBean.orgFinanceCompany}"/>
				&nbsp;&nbsp;&nbsp;融资银行名称:&nbsp;<input id="orgFinanceBank" name="orgFinanceBank" type="text" data-options="required:false,validType:['length[0,16]']" class="textbox easyui-validatebox"value="${loanBean.orgFinanceBank}"/></td>
            </tr>
            <tr>
            	<td>&nbsp;近三个月电费单:&nbsp;&nbsp;&nbsp;第一个月
				<input id="orgNearly3mEleBill1" name="orgNearly3mEleBill1" type="text" class="easyui-numberspinner" data-options="groupSeparator:','" value="${loanBean.orgNearly3mEleBill1}"/></td>
				<td >&nbsp;第二个月:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="orgNearly3mEleBill2" name="orgNearly3mEleBill2" type="text" class="easyui-numberspinner" data-options="groupSeparator:','" value="${loanBean.orgNearly3mEleBill2}"/>
				&nbsp;&nbsp;&nbsp;第三个月:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="orgNearly3mEleBill3" name="orgNearly3mEleBill3" type="text" class="easyui-numberspinner" data-options="groupSeparator:','" value="${loanBean.orgNearly3mEleBill3}"/></td>
            </tr>
<%--            <tr>--%>
<%--            	<td>&nbsp;收款账户名:&nbsp;&nbsp;&nbsp;--%>
<%--				<input id="orgLendAccName" name="orgLendAccName" type="text" data-options="required:false,validType:['length[0,16]']" class="textbox easyui-validatebox"value="${loanBean.orgLendAccName}"/></td>--%>
<%--				<td >&nbsp;收款开户银行:--%>
<%--				<input id="orgLendAccBank" name="orgLendAccBank" type="text" data-options="required:false,validType:['length[0,16]']" class="textbox easyui-validatebox"value="${loanBean.orgLendAccBank}"/>--%>
<%--				&nbsp;&nbsp;&nbsp;收款银行账号:&nbsp;<input id="orgLendAccount" name="orgLendAccount" type="text" data-options="required:false,validType:['length[0,16]']" class="textbox easyui-validatebox"value="${loanBean.orgLendAccBank}"/></td>--%>
<%--            </tr>--%>
            </table>
        </tbody>
    </table>
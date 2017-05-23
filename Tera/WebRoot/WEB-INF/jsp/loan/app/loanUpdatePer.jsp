<%@ page contentType="text/html; charset=UTF-8"%>
	<table id="person_${mainFlag}">
	 <div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"> <strong>个人信息</strong></div>
	 <hr/>
        <tbody>
            <input id="id" name="id" type="hidden" size="35" value="${loanBean.id}" />
            <input id="mainFlag" name="mainFlag" type="hidden" value="${mainFlag}" />
            <tr>
                <td><SPAN style="color:red">*</SPAN>姓名(中文):</td>
                <td><input id="name" name="name" type="text" 
                data-options="required:true,validType:['length[0,100]']" 
                class="textbox easyui-validatebox" value="${loanBean.name}"
                <c:if test="${mainFlag == 0&&paramType=='per' && loanBean!=null}">
                readonly='readonly'
				</c:if>
                /></td>
					<td><SPAN style="color:red">*</SPAN>
						手机号:
					</td>
					<td>
						<input id="mobile" name="mobile" type="text" editable="false" data-options="required:true,validType:['mobile']"
							class="textbox easyui-validatebox" value="${loanBean.mobile}" />
					</td>
			</tr>
			<tr>
				<td><SPAN style="color:red">*</SPAN>证件类型:</td>
				<td>
					<input class="easyui-combobox" name="idType" 
					id="idType" 
					data-options="
					<c:if test="${mainFlag == 0&&paramType=='per'}">
					  onSelect:getCustomer,
					</c:if>required:true" 
					style="width:152px;" editable="false"  value="${loanBean.idType}" />
				</td>
				<td><SPAN style="color:red">*</SPAN>证件号码:</td>
				<td><input id="idNo" name="idNo" type="text" 
				data-options="required:true,validType:['length[0,18]','idNo']" 
				class="textbox easyui-validatebox" 
				<c:if test="${mainFlag == 0&&paramType=='per'}">
					 onblur="getCustomer();"
				</c:if>
				value="${loanBean.idNo}"/></td>
            	<td><SPAN style="color:red">*</SPAN>婚姻状况:</td>
				<td>
					<input class="easyui-combobox" data-options="required:true"  name="marriage" id="marriage" style="width:152px;"  value="${loanBean.marriage}" editable="false"  />
				</td>
            </tr>
            <tr>
				<td><SPAN style="color:red">*</SPAN>通讯地址-省:</td>
				<td>
				<input  class="easyui-combobox" name="addProvince" id="addProvince" data-options="required:true" style="width:152px;" editable="false" value="${loanBean.addProvince}" />
				</td>
				<td align="right">市:&nbsp;</td>
				<td>
				<input class="easyui-combobox" name="addCity" id="addCity" data-options="required:true" style="width:152px;" editable="false" value="${loanBean.addCity}"/> </td>
				<td align="right">区县:&nbsp;</td>
				<td>
				<input class="easyui-combobox" name="addCounty" id="addCounty" data-options="required:true" style="width:152px;" editable="false" value="${loanBean.addCounty}"/></td>
				<td>通讯地址:</td>
				<td>
				<input class="textbox easyui-validatebox" name="address" id="address" data-options="required:true" style="width:152px;" editable="false" value="${loanBean.address}">
				</td>
            </tr>
            <tr style="border-bottom: 2px;">
				<td > &nbsp;行业代码1:</td>
				<td><input id="industry1" name="industry1" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-combobox" editable="false" value="${loanBean.industry1}"/></td>
           		<td>行业代码2:</td>
				<td><input id="industry2" name="industry2" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-combobox" editable="false" value="${loanBean.industry2}"/></td>
            	<td>行业代码3:</td>
				<td><input id="industry3" name="industry3" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-combobox" editable="false" value="${loanBean.industry3}"/></td>
            </tr>
        </tbody>
    </table>
     <div id = "marriageDiv${mainFlag}" <c:if test="${loanBean.marriage!='02'}">style="display: none;"</c:if>>
     
  <div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"> <strong>配偶信息</strong></div>
   <hr />
   <table id="personSpouseInfo_${mainFlag}">
   		<tbody>
   		 	 <tr>
   		 	 <input id="contactId" name="contactId" type="hidden" value="${loanBean.contactId}" />
                <td><SPAN style="color:red">*</SPAN>配偶姓名:</td>
                <td><input id="contactName" name="contactName" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${loanBean.contactName}"/></td>
					<td><SPAN style="color:red">*</SPAN>
						手机号:
					</td>
					<td>
						<input id="contactMobile" name="contactMobile" type="text" editable="false" data-options="validType:['mobile']"
							class="textbox easyui-validatebox" value="${loanBean.contactMobile}" />
					</td>
			</tr>
			<tr>
                <td><SPAN style="color:red">*</SPAN>证件类型:</td>
                <td>
                <input class="easyui-combobox" editable="false" name="contactIdType" id="contactIdType" style="width:152px;" editable="false" value="${loanBean.contactIdType}" />
                </td>
					<td><SPAN style="color:red">*</SPAN>
						证件号码:
					</td>
					<td>
						<input id="contactIdNo" name="contactIdNo" type="text" editable="false" data-options="validType:['length[0,18]','idNo']"
							class="textbox easyui-validatebox" value="${loanBean.contactIdNo}" />
					</td>
			</tr>
			<tr>
				<td><SPAN style="color:red">*</SPAN>通讯地址-省:</td>
				<td>
				<input class="easyui-combobox"  name="contactAddProvice" id="contactAddProvice"  style="width:152px;" editable="false" value="${loanBean.contactAddProvice}" />
				</td>
				<td align="right">市:&nbsp;</td>
				<td>
					<input class="easyui-combobox" name="contactAddCity" id="contactAddCity"  style="width:152px;" editable="false" value="${loanBean.contactAddCity}" />
				
				</td>
				<td align="right">区县:&nbsp;</td>
				<td>
				<input class="easyui-combobox" name="contactAddCounty" id="contactAddCounty"  style="width:152px;" editable="false" value="${loanBean.contactAddCounty}" />
				
				</td>
				<td>通讯地址:</td>
				<td>
				<input class="textbox easyui-validatebox" name="contactAddress" id="contactAddress"  style="width:152px;" value="${loanBean.contactAddress}"/>
				</td>
            </tr>
   		</tbody>
   	</table>
   	</div>
<script>

</script>
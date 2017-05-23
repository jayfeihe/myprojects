<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>

<table id="collateralTool">
  <tr>
	<td><SPAN style="color: red">*</SPAN>抵押物类型:</td>
	<td><input id="collateralType" value="01" name="collateralType" type="text" class="easyui-combobox" editable="false"/></td>
	<td><input type="button" value="添加抵押物" onclick="addCollateral();" /></td>
	<td><input id="collateralIndex" name="collateralIndex" value="${collNextIndex}" type="hidden" /></td>
</tr>
</table>

<c:set value="" var="initCollJs" /> 
<c:set value="" var="initContJs" /> 

<div class="guarantyBox" id="guarantyBox" style="padding-left: 30px;">
<!-- 抵押物body -->
 <c:forEach items="${collList}" var="coll" varStatus="status">
	<table id="guaranty_${coll.seqFlag}">
		<tr style="display:none;">
			<td colspan="8">
				<input id="id" name="guarantyList.id" type="hidden" value="${coll.id}"/>
				<input id="appId" name="guarantyList.appId" type="hidden" value="${coll.appId}"/>
				<input id="mainFlag" name="guarantyList.mainFlag" type="hidden" value="${coll.mainFlag}"/>
				<input id="type" name="guarantyList.type" type="hidden"  value="${coll.type}"/>
				<input id="seqFlag" name="guarantyList.seqFlag" type="hidden" value="${coll.seqFlag}"/>
			</td>
		</tr>
		<tr id="tr_fc1"<c:if test="${coll.type=='03'}"> style="display:none;"</c:if>>
			<td>房产证号/车辆号牌:</td>
			<td><input id="certificate1" name="guarantyList.certificate1" type="text"
				data-options="validType:['length[0,50]']" value="${coll.certificate1}"
				class="textbox easyui-validatebox"  /></td>
			<td>土地证号/登记证号:</td>
			<td><input id="certificate2" name="guarantyList.certificate2" type="text"
				data-options="validType:['length[0,50]']" value="${coll.certificate2}"
				class="textbox easyui-validatebox"  /></td>
			<td>评估公司:</td>
			<td><input id="appraisalCompany" name="guarantyList.appraisalCompany"
				type="text" data-options="validType:['length[0,50]']" value="${coll.appraisalCompany}"
				class="textbox easyui-validatebox"  /></td>
			<td>评估总价:</td>
			<td><input id="appraisalAmount" name="guarantyList.appraisalAmount"
				type="text" editable="false" data-options="min:0,precision:2" value="${coll.appraisalAmount}"
				class="easyui-numberbox"  /></td>
			<td>
				<img src="img/deleteItem2.png" id="onclosecoll" onclick="rmCollateral('${coll.seqFlag}');" />
			</td>
	
		</tr>
		<tr id="tr_fc2"<c:if test="${coll.type=='03'}"> style="display:none;"</c:if>>
			<td>面积:</td>
			<td><input id="houseSize" name="guarantyList.houseSize" type="text"
				editable="false" value="${coll.houseSize}"
				data-options="min:0,precision:2"
				class="easyui-numberbox"  /></td>
			<td>朝向:</td>
			<td><input id="houseToward" name="guarantyList.houseToward" type="text"
				data-options="validType:['length[0,50]']" value="${coll.houseToward}"
				class="textbox easyui-validatebox"  /></td>
			<td>使用年限:</td>
			<td><input id="useYears" name="guarantyList.useYears" type="text"
				editable="false" value="${coll.useYears}"
				class="easyui-numberbox"/></td>
			<td>邮编:</td>
			<td><input id="postcode" name="guarantyList.postcode" type="text"
				data-options="validType:['ZIP']" value="${coll.postcode}"
				class="textbox easyui-validatebox"  /></td>
		</tr>
		<tr id="tr_fc3"<c:if test="${coll.type=='03'}"> style="display:none;"</c:if>>
			<td>省:</td>
			<td><input id="addProvice" name="guarantyList.addProvice" type="text"
				data-options="validType:['length[0,10]']" editable="false"  value="${coll.addProvice}"/></td>
			<td>市:</td>
			<td><input id="addCity" name="guarantyList.addCity" type="text"
				data-options="validType:['length[0,10]']"  editable="false" value="${coll.addCity}" /></td>
			<td>区县:</td>
			<td><input id="addCounty" name="guarantyList.addCounty" type="text"
				data-options="validType:['length[0,10]']"   editable="false" value="${coll.addCounty}" /></td>
			<td>地址:</td>
			<td><input id="address" name="guarantyList.address" type="text"
				data-options="validType:['length[0,100]']" value="${coll.address}"
				class="textbox easyui-validatebox"  /></td>
		</tr>
		<tr id="tr_guquan" <c:if test="${coll.type!='03'}"> style="display:none;"</c:if> >
			<td>股权公司全称:</td>
			<td><input id="holdingCompany" name="guarantyList.holdingCompany" type="text"
				data-options="validType:['length[0,50]']" value="${coll.holdingCompany}"
				class="textbox easyui-validatebox"  /></td>
			<td>股权类型:</td>
			<td><input id="holdingType" name="guarantyList.holdingType" type="text" editable="false" value="${coll.holdingType}" /></td>
			<td>股权证件类型:</td>
			<td><input id="idType" name="guarantyList.idType" type="text" editable="false" value="${coll.idType}" /></td>
			<td>股权证件编号:</td>
			<td><input id="idNo" name="guarantyList.idNo" type="text"
				data-options="validType:['length[0,18]','idNo']" value="${coll.idNo}"
				class="textbox easyui-validatebox"  /></td>
			<td>
				<img src="img/deleteItem2.png" id="onclosecoll" onclick="rmCollateral('${coll.seqFlag}');" />
			</td>
		</tr>
		<tr>
			<td colspan="8" style="padding-left:20px;">
				<table width="100%">
					<tr>
						<td colspan="9">
							<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;" >
								<strong>个人产权人列表</strong>
								<span>
									<img src="img/addItem.gif" id="onAppContactsPer" onclick="appContacts('per','${coll.seqFlag}');" />
								</span>
								<hr width="100%" size=2 color="#E0ECFF" noshade>
							</div>
						</td>
					</tr>
					<tr>
						<td class="perContactsBox" id="perContactsBox" style="padding-left: 30px;">
							<c:set value="0" var="collPerIndex" /> 
							<c:forEach items="${collPerConList}" var="cont" varStatus="statusper">
								<c:if test="${cont.collateralId==coll.seqFlag}">
								<table width="100%" id="perContacts_${collPerIndex}" style="left: 20px;">
									<tr style="display:none;"> 
										<td>
											<input id="id" name="perContactsList.id" type="hidden" value="${cont.id}" />
											<input id="appId" name="perContactsList.appId" type="hidden" value="${cont.appId}" />
											<input id="type" name="perContactsList.type" type="hidden" value="${cont.type}" />
											<input id="mainFlag" name="perContactsList.mainFlag" type="hidden" value="${cont.mainFlag}"/>
											<input id="contactType" name="perContactsList.contactType" type="hidden" value="${cont.contactType}"/>
											<input id="collateralId" name="perContactsList.collateralId" type="hidden"  value="${cont.collateralId}" />
										</td>
									</tr>
									<tr>
										<td>姓名:</td>
										<td><input id="name" name="perContactsList.name" type="text"
											data-options="validType:['length[0,50]']" value="${cont.name}"
											class="textbox easyui-validatebox"  /></td>
										<td>移动电话:</td>
										<td><input id="mobile" name="perContactsList.mobile" type="text"
											data-options="validType:['length[0,18]']" value="${cont.mobile}"
											class="textbox easyui-validatebox"  /></td>
										<td>证件类型:</td>
										<td><input id="idType" name="perContactsList.idType" type="text"  editable="false" 
										 value="${cont.idType}" /></td>
										<td>证件号码:</td>
										<td><input id="idNo" name="perContactsList.idNo" type="text"
											data-options="validType:['length[0,18]','idNo']" value="${cont.idNo}"
											class="textbox easyui-validatebox"  /></td>
										<td>婚姻:<input id="marriage" name="perContactsList.marriage" 
										type="text" value="${cont.marriage}" editable="false" 
										data-options="onSelect:function(param){ var thiscont=$('#guaranty_${coll.seqFlag}').find('#perContactsBox').find('#perContacts_${collPerIndex}');}"
										/></td>
									</tr>
									<tr id="collcontPerPeiou">
										<td style="padding-left:10px;">配偶姓名:</td>
										<td><input id="name2" name="perContactsList.name2" type="text"
											data-options="validType:['length[0,50]']" value="${cont.name2}"
											class="textbox easyui-validatebox"  /></td>
										<td>手机号:</td>
										<td><input id="phone2" name="perContactsList.phone2" type="text"
											data-options="validType:['length[0,18]']" value="${cont.phone2}"
											class="textbox easyui-validatebox"  /></td>
										<td>证件类型:</td>
										<td><input id="idType2" name="perContactsList.idType2" type="text" editable="false" 
										 value="${cont.idType2}" /></td>
										<td>证件号码:</td>
										<td><input id="idNo2" name="perContactsList.idNo2" type="text"
											data-options="validType:['length[0,18]','idNo']" value="${cont.idNo2}"
											class="textbox easyui-validatebox"  /></td>
										<td align="right">
											<img src="img/deleteItem2.png" 
											onclick="rmContacts('per','${cont.collateralId}','${collPerIndex}');" />
										</td>
									</tr>
									<tr>
										<td colspan="10"><hr width="100%" size=2 color=#E0ECFF align=center noshade></td>
									</tr>
								</table>
								<c:set value="${initContJs}perContactsBox('${cont.collateralId}','${collPerIndex}');||" var="initContJs" /> 
								<c:set value="${collPerIndex+1}" var="collPerIndex" /> 
							</c:if>
							</c:forEach>
							
						</td>
					</tr>
					<tr><td><input id="perIndex" type="hidden" value="${collPerIndex}"/></td></tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="8" style="padding-left:20px;">
				<table width="100%">
					<tr>
						<td>
							<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;" >
								<strong>企业产权人列表</strong>
								<span>
									<img src="img/addItem.gif" id="onAppContactsOrg" onclick="appContacts('org','${coll.seqFlag}');" />
								</span>
								<hr width="100%" size=2 color=#E0ECFF align=center noshade>
							</div>
						</td>
					</tr>
					<tr>
						<td class="orgContactsBox" id="orgContactsBox" style="padding-left: 30px;">
							<c:set value="0" var="collOrgIndex" /> 
							<c:forEach items="${collOrgConList}" var="cont" varStatus="statusper">
								<c:if test="${cont.collateralId==coll.seqFlag}">
									<table width="100%" id="orgContacts_${collOrgIndex}">
										<tr style="display:none;">
											<td>
												<input id="id" name="orgContactsList.id" type="hidden" value="${cont.id}" />
												<input id="appId" name="orgContactsList.appId" type="hidden" value="${cont.appId}" />
												<input id="type" name="orgContactsList.type" type="hidden" value="${cont.type}" />
												<input id="mainFlag" name="orgContactsList.mainFlag" type="hidden" value="${cont.mainFlag}"/>
												<input id="contactType" name="orgContactsList.contactType" type="hidden" value="${cont.contactType}"/>
												<input id="collateralId" name="orgContactsList.collateralId" type="hidden" value="${cont.collateralId}"/>
											</td>
										</tr>
										<tr>
											<td>姓名:</td>
											<td><input id="name" name="orgContactsList.name" type="text"
												data-options="validType:['length[0,50]']" value="${cont.name}"
												class="textbox easyui-validatebox"  /></td>
											<td>法人代表:</td>
											<td><input id="name2" name="orgContactsList.name2" type="text"
												data-options="validType:['length[0,50]']" value="${cont.name2}"
												class="textbox easyui-validatebox"  /></td>
											<td>电话:</td>
											<td><input id="mobile" name="orgContactsList.mobile" type="text"
												data-options="validType:['length[0,18]']" value="${cont.mobile}"
												class="textbox easyui-validatebox"  /></td>
											<td align="right" colspan="2">
												<img src="img/deleteItem2.png" 
											onclick="javascript:rmContacts('org','${cont.collateralId}','${collOrgIndex}');" />
											</td>
										</tr>
										<tr>
											<td colspan="10"><hr width="100%" size=2 color=#E0ECFF align=center noshade></td>
										</tr>
									</table>
								<c:set value="${collOrgIndex+1}" var="collOrgIndex" /> 
								</c:if>
							</c:forEach>
							
						</td>
					</tr>
					<tr><td><input id="orgIndex" type="hidden" value="${collOrgIndex}"/></td></tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="8"><hr width="100%" size=2 color=#95B8E7 align=center noshade></td>
		</tr>
	</table>
	<c:set value="${initCollJs}initProvice('${coll.seqFlag}','${coll.type}');||" var="initCollJs" />
 </c:forEach>
</div>

<div style="display:none;" id="collButijiao">
<!-- 	机构用户 -->
	<table width="100%" id="orgContacts">
		<tr style="display:none;">
			<td>
				<input id="id" name="orgContactsList.id" type="hidden" value="" />
				<input id="appId" name="orgContactsList.appId" type="hidden" value="${appId}" />
				<input id="type" name="orgContactsList.type" type="hidden" value="${appType}" />
				<input id="mainFlag" name="orgContactsList.mainFlag" type="hidden" value="0"/>
				<input id="contactType" name="orgContactsList.contactType" type="hidden" value="02"/>
				<input id="collateralId" name="orgContactsList.collateralId" type="hidden" value=""/>
			</td>
		</tr>
		<tr>
			<td>姓名:</td>
			<td><input id="name" name="orgContactsList.name" type="text"
				data-options="validType:['length[0,50]']" value=""
				class="textbox easyui-validatebox"  /></td>
			<td>法人代表:</td>
			<td><input id="name2" name="orgContactsList.name2" type="text"
				data-options="validType:['length[0,50]']" value=""
				class="textbox easyui-validatebox"  /></td>
			<td>电话:</td>
			<td><input id="mobile" name="orgContactsList.mobile" type="text"
				data-options="validType:['length[0,18]']" value=""
				class="textbox easyui-validatebox"  /></td>
			<td align="right" colspan="2">
				<img src="img/deleteItem2.png" id="onclose" />
			</td>
		</tr>
		<tr>
			<td colspan="10"><hr width="100%" size=2 color=#E0ECFF align=center noshade></td>
		</tr>
	</table>
<!-- 	个人用户 -->
	<table width="100%" id="perContacts" style="left: 20px;">
		<tr style="display:none;"> 
			<td>
				<input id="id" name="perContactsList.id" type="hidden" value="" />
				<input id="appId" name="perContactsList.appId" type="hidden" value="${appId}" />
				<input id="type" name="perContactsList.type" type="hidden" value="${appType}" />
				<input id="mainFlag" name="perContactsList.mainFlag" type="hidden" value="0"/>
				<input id="contactType" name="perContactsList.contactType" type="hidden" value="01"/>
				<input id="collateralId" name="perContactsList.collateralId" type="hidden"  value="" />
			</td>
		</tr>
		<tr>
			<td>姓名:</td>
			<td><input id="name" name="perContactsList.name" type="text"
				data-options="validType:['length[0,50]']" value=""
				class="textbox easyui-validatebox"  /></td>
			<td>移动电话:</td>
			<td><input id="mobile" name="perContactsList.mobile" type="text"
				data-options="validType:['length[0,18]']" value=""
				class="textbox easyui-validatebox"  /></td>
			<td>证件类型:</td>
			<td><input id="idType" name="perContactsList.idType" type="text"  editable="false"   value="" /></td>
			<td>证件号码:</td>
			<td><input id="idNo" name="perContactsList.idNo" type="text"
				data-options="validType:['length[0,18]','idNo']" value=""
				class="textbox easyui-validatebox"  /></td>
			<td>婚姻:<input id="marriage" name="perContactsList.marriage" type="text" value="" editable="false"  /></td>
			<td align="left">
				<img src="img/deleteItem2.png" id="onclose" />
			</td>
		</tr>
		<tr id="collcontPerPeiou">
			<td style="padding-left:10px;">配偶姓名:</td>
			<td><input id="name2" name="perContactsList.name2" type="text"
				data-options="validType:['length[0,50]']" value=""
				class="textbox easyui-validatebox"  /></td>
			<td>手机号:</td>
			<td><input id="phone2" name="perContactsList.phone2" type="text"
				data-options="validType:['length[0,18]']" value=""
				class="textbox easyui-validatebox"  /></td>
			<td>证件类型:</td>
			<td><input id="idType2" name="perContactsList.idType2" type="text" value=""
			 editable="false"  /></td>
			<td>证件号码:</td>
			<td><input id="idNo2" name="perContactsList.idNo2" type="text"
				data-options="validType:['length[0,18]','idNo']" value=""
				class="textbox easyui-validatebox"  /></td>
			
		</tr>
		<tr>
			<td colspan="10"><hr width="100%" size=2 color=#E0ECFF align=center noshade></td>
		</tr>
	</table>
	
<!-- 	抵押物 -->
	<table id="guaranty">
		<tr style="display:none;">
			<td colspan="8">
				<input id="id" name="guarantyList.id" type="hidden" value=""/>
				<input id="appId" name="guarantyList.appId" type="hidden" value="${appId}"/>
				<input id="mainFlag" name="guarantyList.mainFlag" type="hidden" value="0"/>
				<input id="type" name="guarantyList.type" type="hidden"  value=""/>
				<input id="seqFlag" name="guarantyList.seqFlag" type="hidden" value=""/>
				<input id="perIndex" type="hidden" value="0"/>
				<input id="orgIndex" type="hidden" value="0"/>
			</td>
		</tr>
		<tr id="tr_fc1">
			<td>房产证号/车辆号牌:</td>
			<td><input id="certificate1" name="guarantyList.certificate1" type="text"
				data-options="validType:['length[0,50]']" value=""
				class="textbox easyui-validatebox"  /></td>
			<td>土地证号/登记证号:</td>
			<td><input id="certificate2" name="guarantyList.certificate2" type="text"
				data-options="validType:['length[0,50]']" value=""
				class="textbox easyui-validatebox"  /></td>
			<td>评估公司:</td>
			<td><input id="appraisalCompany" name="guarantyList.appraisalCompany"
				type="text" data-options="validType:['length[0,50]']" value=""
				class="textbox easyui-validatebox"  /></td>
			<td>评估总价:</td>
			<td><input id="appraisalAmount" name="guarantyList.appraisalAmount"
				type="text" editable="false" value=""
				data-options="min:0,precision:2,groupSeparator:','"/></td>
			<td>
				<img src="img/deleteItem2.png" id="onclosecoll" />
			</td>
	
		</tr>
		<tr id="tr_fc2">
			<td>面积:</td>
			<td><input id="houseSize" name="guarantyList.houseSize" type="text"
				editable="false" value=""
				data-options="min:0,precision:2"
				/></td>
			<td>朝向:</td>
			<td><input id="houseToward" name="guarantyList.houseToward" type="text"
				data-options="validType:['length[0,50]']" value=""
				class="textbox easyui-validatebox"  /></td>
			<td>使用年限:</td>
			<td><input id="useYears" name="guarantyList.useYears" type="text"
				editable="false" value="" /></td>
			<td>邮编:</td>
			<td><input id="postcode" name="guarantyList.postcode" type="text"
				data-options="validType:['ZIP']" value="" class="textbox easyui-validatebox"  /></td>
		</tr>
		<tr id="tr_fc3">
			<td>省:</td>
			<td><input id="addProvice" name="guarantyList.addProvice" type="text"
				data-options="validType:['length[0,10]']" editable="false"  value=""/></td>
			<td>市:</td>
			<td><input id="addCity" name="guarantyList.addCity" type="text"
				data-options="validType:['length[0,10]']"  editable="false" value="" /></td>
			<td>区县:</td>
			<td><input id="addCounty" name="guarantyList.addCounty" type="text"
				data-options="validType:['length[0,10]']"   editable="false" value="" /></td>
			<td>地址:</td>
			<td><input id="address" name="guarantyList.address" type="text"
				data-options="validType:['length[0,100]']" value=""
				class="textbox easyui-validatebox"  /></td>
		</tr>
		<tr id="tr_guquan">
			<td>股权公司全称:</td>
			<td><input id="holdingCompany" name="guarantyList.holdingCompany" type="text"
				data-options="validType:['length[0,50]']" value=""
				class="textbox easyui-validatebox"  /></td>
			<td>股权类型:</td>
			<td><input id="holdingType" name="guarantyList.holdingType" type="text" editable="false" value="" /></td>
			<td>股权证件类型:</td>
			<td><input id="idType" name="guarantyList.idType" type="text" editable="false" value="" /></td>
			<td>股权证件编号:</td>
			<td><input id="idNo" name="guarantyList.idNo" type="text"
				data-options="validType:['length[0,18]','idNo']" value=""
				class="textbox easyui-validatebox"  /></td>
			<td>
				<img src="img/deleteItem2.png" id="onclosecoll" />
			</td>
		</tr>
		<tr>
			<td colspan="8" style="padding-left:20px;">
				<table width="100%">
					<tr>
						<td colspan="9">
							<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;" >
								<strong>个人产权人列表</strong>
								<span>
									<img src="img/addItem.gif" id="onAppContactsPer" />
								</span>
								<hr width="100%" size=2 color="#E0ECFF" noshade>
							</div>
						</td>
					</tr>
					<tr>
						<td class="perContactsBox" id="perContactsBox" style="padding-left: 30px;">
							
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="8" style="padding-left:20px;">
				<table width="100%">
					<tr>
						<td>
							<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;" >
								<strong>企业产权人列表</strong>
								<span>
									<img src="img/addItem.gif" id="onAppContactsOrg"  />
								</span>
								<hr width="100%" size=2 color=#E0ECFF align=center noshade>
							</div>
						</td>
					</tr>
					<tr>
						<td class="orgContactsBox" id="orgContactsBox" style="padding-left: 30px;">
							
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="8"><hr width="100%" size=2 color=#95B8E7 align=center noshade></td>
		</tr>
	</table>
	
</div>


<script type="text/javascript">
	//添加联系人 type 必须 为      per | org
	function appContacts(type,collateralId){
		var contactsBox,contacts,contactsIndex;
		contactsBox=$("#guaranty_"+collateralId).find("#"+type+"ContactsBox");
		contactsIndex=$("#guaranty_"+collateralId).find("#"+type+"Index").val();
		contacts=$("#"+type+"Contacts").clone();
		// 设置抵押物序号
		contacts.find("#collateralId").val(collateralId);
		contacts.attr("id",contacts.attr("id")+"_"+contactsIndex);
		contacts.find("input").removeAttr("disabled");
		contactsBox.append(contacts);
		if(type=="per"){
			perContactsBox(collateralId, contactsIndex);
		}else{
			$.parser.parse(contacts);
		}
		contacts.find("#onclose").attr("onclick","rmContacts('"+type+"','"+collateralId+"','"+contactsIndex+"')");
		
		//标识+1
		$("#guaranty_"+collateralId).find("#"+type+"Index").val(++contactsIndex);
	}
	//删除 联系人  type 必须 为      per | org
	function rmContacts(type,collateralId,contactsIndex){
		var contTable = $("#guaranty_"+collateralId).find("#"+type+"Contacts_"+contactsIndex);
		var contId=contTable.find("#id").val();
		if(contId!=null&&contId!=""){
			
			$.messager.confirm('消息', "是否确定删除此产权人？", function(ok){
	            if (ok){
	            	openMask();
	            	$.ajax({
	            		url : "loan/app/delAppCollCont.do?contId="+contId,
	            		success : function(data) {
	            			closeMask();
	            			if ("true"==data.success) {
	            				
	            				$.messager.alert('消息', data.message,"info", function(){
	            	                	contTable.remove();;
	            	        			var tables=$("#guaranty_"+collateralId).find("#"+type+"ContactsBox").find("table").children();
	            	        			if(tables.length==0){
	            	        				$("#guaranty_"+collateralId).find("#"+type+"Index").val(0);
	            	        				appContacts(type, collateralId);
	            	        			}
	                        	});
	                        } else {
	            				
	            				$.messager.alert('消息', data.message);
	                        }
	            		},
	            		error : function() {
	            			closeMask();
	            			
	            			$.messager.alert('消息', '操作失败,请联系系统管理员！');
	            		}
	            	});
	            	
	            }
	    	});
		}else{
			contTable.remove();;
			var tables=$("#guaranty_"+collateralId).find("#"+type+"ContactsBox").find("table").children();
			if(tables.length==0){
				$("#guaranty_"+collateralId).find("#"+type+"Index").val(0);
				appContacts(type, collateralId);
			}
		}
		
	}
	//初始化 联系人的 下拉框
	function perContactsBox(collateralId,contactsIndex){
		var cont=$("#guaranty_"+collateralId).find("#perContactsBox").find("#perContacts_"+contactsIndex);
		cont.find("#idType").attr("class","easyui-combobox");
		cont.find("#idType2").attr("class","easyui-combobox");
		cont.find("#marriage").attr("class","easyui-combobox");
		cont.find("#marriage").attr("data-options","onSelect:function(param){ var thiscont=$('#guaranty_"+collateralId+"').find('#perContactsBox').find('#perContacts_"+contactsIndex+"'); if(param.keyValue !='已婚'){ thiscont.find('#collcontPerPeiou').hide(); }else{ thiscont.find('#collcontPerPeiou').show();}}");
		$.parser.parse(cont);
		
		//var provinceurl = "sys/datadictionary/listjason.do?keyName=personidtype";
		cont.find("#idType").combobox("clear");
		cont.find('#idType').combobox({
			//url: provinceurl,
			valueField: 'keyProp',
			textField: 'keyValue',
			data:dataDictJson.personidtype
		});
		
		cont.find("#idType2").combobox("clear");
		cont.find('#idType2').combobox({
			//url: provinceurl,
			valueField: 'keyProp',
			textField: 'keyValue',
			data:dataDictJson.personidtype
		});
		
		//provinceurl = "sys/datadictionary/listjason.do?keyName=marriage";
		cont.find("#marriage").combobox("clear");
		cont.find('#marriage').combobox({
			//url: provinceurl,
			valueField: 'keyProp',
			textField: 'keyValue',
			data:dataDictJson.marriage
		});
	}
</script>
<!-- 	抵押物的 js -->
<script type="text/javascript">
// 	删除抵押物
	function rmCollateral(collateralId){
		var collTable=$("#guarantyBox").find("#guaranty_"+collateralId);
		var collId=collTable.find("input[name='guarantyList.id']").val();
		if(collId!=null&&collId!=""){
			
			$.messager.confirm('消息', "是否确定删除此抵押物？", function(ok){
	            if (ok){
	            	openMask();
	            	$.ajax({
	            		url : "loan/app/delAppColl.do?collId="+collId,
	            		success : function(data) {
	            			closeMask();
	            			if ("true"==data.success) {
	            				
	            				$.messager.alert('消息', data.message,"info", function(){
	            	                	collTable.remove();;
	            	            		var tables=$("#guarantyBox").find("table").children();
	            	            		if(tables.length==0){
	            	            			$("#collateralTool").find("#collateralIndex").val(1);
	            	            			addCollateral();
	            	            		}
	                        	});
	                        } else {
	            				
	            				$.messager.alert('消息', data.message);
	                        }
	            		},
	            		error : function() {
	            			closeMask();
	            			
	            			$.messager.alert('消息', '操作失败,请联系系统管理员！');
	            		}
	            	});
	            	
	            }
	    	});
		}else{
			collTable.remove();;
    		var tables=$("#guarantyBox").find("table").children();
    		if(tables.length==0){
    			$("#collateralTool").find("#collateralIndex").val(1);
    			addCollateral();
    		}
		}
		
		
	}
// 	添加抵押物
	function addCollateral(){
		var ctype=$("#collateralTool").find("input[name='collateralType']").val();
		if(ctype==null|| ctype==''){
			alert("请选择抵押物类型。");
			return;
		}
		var collateralIndex=$("#collateralTool").find("#collateralIndex").val();
		var coll=$("#guaranty").clone();
		coll.find("#type").val(ctype);
		coll.find("#seqFlag").val(collateralIndex);
		coll.find("#onAppContactsOrg").attr("onclick","appContacts('org','"+collateralIndex+"')");
		coll.find("#onAppContactsPer").attr("onclick","appContacts('per','"+collateralIndex+"')");
		coll.attr("id",coll.attr("id")+"_"+collateralIndex);
		coll.find("#onclosecoll").attr("onclick","rmCollateral('"+collateralIndex+"')");
		coll.find("input").removeAttr("disabled");
		var guarantyBox=$("#guarantyBox");
		guarantyBox.append(coll);
		if(ctype=="03"){
			coll.find("#tr_fc1").css("display","none");
			coll.find("#tr_fc2").css("display","none");
			coll.find("#tr_fc3").css("display","none");
		}else{
			coll.find("#tr_guquan").css("display","none");
		}
		initProvice(collateralIndex,ctype);				//省市级连  加载数据
		appContacts("per",collateralIndex);
		appContacts("org",collateralIndex);
		$("#collateralTool").find("#collateralIndex").val(++collateralIndex);
	}
	
	//初始化省市级连
	function initProvice(collateralIndex,type){
		var coll=$("#guaranty_"+collateralIndex);
		//得到 抵押物 
		if(type=="03"){
			coll.find("#tr_guquan").find("#holdingType").attr("class","easyui-combobox");
			coll.find("#tr_guquan").find("#idType").attr("class","easyui-combobox");
			$.parser.parse(coll);		//初始化
			
			//var ziUrl="sys/datadictionary/listjason.do?keyName=companyidtype";
			coll.find("#tr_guquan").find("#idType").combobox("clear");
			coll.find("#tr_guquan").find('#idType').combobox({
				//url: ziUrl,
				valueField: 'keyProp',
				textField: 'keyValue',
				data:dataDictJson.companyidtype
			});
			
			//ziUrl="sys/datadictionary/listjason.do?keyName=holdingtype";
			coll.find("#tr_guquan").find("#holdingType").combobox("clear");
			coll.find("#tr_guquan").find('#holdingType').combobox({
				//url: ziUrl,
				valueField: 'keyProp',
				textField: 'keyValue',
				data:dataDictJson.holdingtype
			});
		}else{
			
			//设置 class 用于 UI插件识别  numberbox combobox 不能在隐藏曾里面直接写入，写入后 动态添加会有问题
			coll.find("#addProvice").attr("class","easyui-combobox");
			coll.find("#addCity").attr("class","easyui-combobox");
			coll.find("#addCounty").attr("class","easyui-combobox");

			coll.find("#houseSize").attr("class","easyui-numberbox");
			coll.find("#useYears").attr("class","easyui-numberbox");
// 			coll.find("#postcode").attr("class","easyui-numberbox");
			coll.find("#appraisalAmount").attr("class","easyui-numberbox");
			
			$.parser.parse(coll);		//初始化省市级连 UI
			var provinceurl = "sys/datadictionary/listjason.do?keyName=province";
			coll.find("#addProvice").combobox("clear");
			coll.find('#addProvice').combobox({url: provinceurl,valueField: 'keyProp',textField: 'keyValue'});
			//填充select数据 市
			var province = coll.find("#addProvice").combobox('getValue');
			province=(province==null||province=="")?"non":province;
			var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(province);
			coll.find("#addCity").combobox("clear");
			coll.find('#addCity').combobox({url: cityurl,valueField: 'keyProp',textField: 'keyValue'});
			//填充select数据 区县
			var city= coll.find("#addCity").combobox('getValue');
			citys=(city==null||city=="")?"non":city;
			var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(city);
			coll.find("#addCounty").combobox("clear");
			coll.find('#addCounty').combobox({url: countyurl,valueField: 'keyProp',textField: 'keyValue'});
			
			// 连动
			coll.find("#addProvice").combobox({
			    onChange: function(newValue, oldValue){
			    	coll.find("#addCity").combobox('clear');
			    	coll.find("#addCounty").combobox('clear');
			    	newValue=(newValue==null||newValue=="")?"non":newValue;
					var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
					coll.find('#addCity').combobox('reload',cityurl); 
			    }
			});
			coll.find("#addCity").combobox({
			    onChange: function(newValue, oldValue){
			    	coll.find("#addCounty").combobox('clear');
			    	newValue=(newValue==null||newValue=="")?"non":newValue;
					var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
					coll.find("#addCounty").combobox('reload',countyurl); 
			    }
			});
		}
		
	}
</script>

<script type="text/javascript">
	$(document).ready(function(){
		//var	collateralTypeurl="sys/datadictionary/listjason.do?keyName=collateraltype";
		$("#collateralTool").find("#collateralType").combobox("clear");
		$("#collateralTool").find('#collateralType').combobox({
			//url:collateralTypeurl, 
			valueField:'keyProp', 
			textField:'keyValue',
			data:dataDictJson.collateraltype
		});
		$("#collButijiao input").attr("disabled","disabled");

		<c:forEach items="${fn:split(initCollJs, '||')}" var="colljs" varStatus="status">
			${colljs}
		</c:forEach>
		<c:forEach items="${fn:split(initContJs, '||')}" var="colljs" varStatus="status">
			${colljs}
		</c:forEach>	

		<c:if test="${collList==null}">addCollateral();</c:if>
	});
// 	 $.parser.parse("tr") //对新添加的html初始化easyui
</script>

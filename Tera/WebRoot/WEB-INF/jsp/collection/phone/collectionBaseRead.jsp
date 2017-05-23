<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%> 
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int relationIndex=1;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>"/>
<title>电催详情查看</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
	
<style type="text/css">
.hiddenborder {border:0;background-color: white;color:blue;}
</style>
</head>
<body>

	<div id="phoneCollection" class="easyui-tabs" data-options="fit:true" >
	<div id="part1" class="part" title="电催催收">
	 <!-- <p class="title"><a href="javascript:void(0);">电催详细信息</a></p>  -->
	 
	<div class="content" style="overflow-x:scroll ">
	
	<div style="margin-left: 10px;margin-top: 10px; width:80%;">
				
				<div style="float: left; ">
				
					<input type="button" name="show" class="btn"   value="逾期计算器"  onclick="lateDaysCal('${bean.id}')"/>
				 	<input type="button" name="show" class="btn"   value="还款计划 "   onclick="paymentPlan('<%=basePath%>collection/reduce/apply/repayPlanDetail.do?contractNo=${bean.contractNo}','还款计划')"/>
				 	<input type="button" name="show" class="btn"   value="档案资料"  onclick="paymentPlan('<%=basePath%>collection/visit/credit/read.do?id=${creditApp.id}','档案资料')"/>
				 	<input type="button" name="hand" class="btn"    value="减免申请 "   onclick="remissionApply('${bean.contractNo}')"/>
				 	<input type="button" name="hand" class="btn"    value="欺诈申请"  onclick="cheatApply('${bean.contractNo}',2,'欺诈申请')"/>
				 	<input type="button" name="hand" class="btn"     value="司法申请 "   onclick="cheatApply('${bean.contractNo}',1,'司法申请')"/>
				</div>
				<br />
				<div style="float: right;">
					<input type="button"   name="show" value="返回上一级" class="btn" onclick="javascript:back()"/>
				 	 &nbsp;
				 	<input type="button"  name="hand" class="btn"  value="保存"  onclick="submitForm('updateForm')"/>
					</div>
			</div>
		<br/>
	<form id="updateForm" action="collectionBase/phone/save.do" method="post" target="contacts">
		  <table style="width: 100%">
					<tbody>
						<tr>
							<td>
							<div id="targetDiv" style="border-style:solid;border-width:thin;border-color: silver;">
							<table>
							<input id="ishandler" name="ishandler" type="hidden" size="35" value="${ishandler}" />
							<input id="id" name="id" type="hidden" size="35" value="${bean.id}" />
							<input id="appId" name="appId" type="hidden" size="35" value="${bean.appId}" />
							<input id="contractId" name="contractId" type="hidden" size="35" value="${bean.contractId}" />
							<input id="readState" name="readState" type="hidden"  value="${bean.state}" />
								<tr>
									<td><!-- <SPAN style="color:red">*</SPAN> -->合同编号:</td>
									<td><input id="contractNo" name="contractNo" type="text"   disabled="disabled" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.contractNo}"/></td>
								
									<td>客户姓名:</td>
									<td><input id="customerName" name="customerName" type="text"  disabled="disabled" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.customerName}"/></td>
									<td>性别:</td>
									
									<td>
									<input id="gender" name="gender" type="text"  disabled="disabled" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="<c:if test="${bean.gender=='F'}">男</c:if><c:if test="${bean.gender=='M'}">女</c:if>"/></td>
									<td>合同额:</td>
									<td><input id="contractAmount" name="contractAmount" type="text"   disabled="disabled"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.contractAmount}"/></td>
									
								</tr>
								<tr>
									<td>产品:</td>
									<td><input id="product" name="" type="text" disabled="disabled" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.product}"/></td>
								
									<td>历史逾期期数:</td>
									<td><input id="periodLateHis" name="periodLateHis" type="text"  disabled="disabled"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.periodLateHis}"/></td>
									<td>逾期天数:</td>
									<td><input id="lateDays" name="lateDays" type="text"  disabled="disabled"  data-options="min:0,precision:0"  class="textbox easyui-numberbox" value="${bean.lateDays}"/></td>
									<td>营业部:</td>
									<td><input id="orgId" name="orgId" type="text"  disabled="disabled" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.orgName}"/></td>
								</tr>
								<tr>
									<td>月还款额:</td>
									<td><input id="monthAmountAll" name="monthAmountAll" type="text"  disabled="disabled" data-options="min:0,precision:2"  class="textbox easyui-numberbox" value="${bean.monthAmountAll}"/></td>
									<td>应还总额:</td>
									<td><input id="amountAll" name="" type="text"  disabled="disabled" data-options="min:0,precision:2"  class="textbox easyui-numberbox" value="${bean.amountAll}"/></td>
									<td>滞纳金:</td>
									<td><input id="delay" name="delay" type="text"  disabled="disabled"  data-options="min:0,precision:2"  class="textbox easyui-numberbox" value="${bean.delay}"/></td>
									<td>罚息:</td>
									<td><input id="penalty" name="penalty" type="text"  disabled="disabled"  data-options="min:0,precision:2" class="textbox easyui-numberbox"  value="${bean.penalty}"/></td>
								</tr>
								<tr>	
									<td>还款日:</td>
									<td><input id="repaymentDate" name="repaymentDate" type="text"  disabled="disabled" data-options="validType:['length[0,20]']"  class="textbox easyui-validatebox"  value="${bean.repaymentDateStr}"/></td>
									<td>还款银行:</td>
									<td><input id="bankName" name="bankName" type="text"  disabled="disabled" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.bankName}"/></td>
									<td>银行卡号:</td>
									<td><input id="bankAccount" name="bankAccount" type="text"  disabled="disabled" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.bankAccount}"/></td>
								</tr>
								</table>
								</div>
							</td> 
						</tr>
						<tr>
							<td>
								 <div id="mainBusinessInfoDiv" <c:if test="${fn:contains(bean.product,'业主贷')==false}">style="display: none;"</c:if>>
									<table>	
										<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>主营业务信息（业主贷必填）</strong></div><hr/>
										<tr>
											<td>商号名称:</td>
											<td><input id="" name="" type="text" data-options="required:true,validType:['length[0,100]']" 
											class="textbox easyui-validatebox" value="${creditApp.firmName}"/></td>
											<td>主营业务:</td>
											<td><input id="" name="" type="text" data-options="required:true,validType:['length[0,100]']" 
											class="textbox easyui-validatebox" value="${creditApp.firmMainBusiness}"/></td>
											<td>年营业额:</td>
											<td><input id="" name="" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
											class="textbox easyui-numberbox" value="${creditApp.firmIncome/10000}" style="width:128px;"/>万元</td>
											<td>占股比例:</td>
											<td><input id="" name="" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
											class="textbox easyui-numberbox" value="${creditApp.firmShare}" style="width:140px;"/>%</td>				
										</tr>
										<tr>
											<td>商号地址:</td>
											<td colspan="5" ><input id="firmProvince" name="" type="text" data-options="required:true,validType:['length[0,10]']" 
											class="easyui-combobox" value="${creditApp.firmProvince}" editable="false" style="width:140px;" />省
											<input id="firmCity" name="" type="text" data-options="required:true,validType:['length[0,10]']" 
											class="easyui-combobox" value="${creditApp.firmCity}" editable="false" style="width:140px;" />市
											<input id="firmCounty" name="" type="text" data-options="required:true,validType:['length[0,10]']" 
											class="easyui-combobox" value="${creditApp.firmCounty}" editable="false" style="width:128px;" />区县
											<input id="firmAddress" name="" type="text" data-options="required:true,validType:['length[0,100]']" 
											class="textbox easyui-validatebox" value="${creditApp.firmAddress}" editable="false"/></td>
										</tr>
										<tr>
											<td>本地经营时间:</td>
											<td><input id="firmManageDate" name="" type="text" editable="false" data-options="required:true"
											class="textbox easyui-datebox" value="${creditApp.firmManageDateStr}"/></td>					
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<strong>联系人<span>
								<input type="button" class="btn" name="hand" style="display: none;" value="添加" onclick="addObj('relationTable','<%=relationIndex%>')"/>
								</span></strong><hr/>
								<div id="contacts" style="border-style: solid;border-width:thin;border-color:silver;" >
									
									<table id="relationTable">
										<c:forEach var="creditContact" items="${creditContactList}" varStatus="status">
										
											<tr>
												<input type="hidden" id="creditContacttype${status.index+1}" name="creditContacttype" value="${creditContact.type}" />
												<input type="hidden" id="creditContactId${status.index+1}" name="creditContactId" value="${creditContact.id}" />
												<td>姓名:</td>
												<td><input id="contactname${status.index+1}" name="contactname" type="text" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" value="${creditContact.name}"/></td>
												<td>接听人关系:</td>
												<td><input id="contactrelation${status.index+1}" name="contactrelation" type="text" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-combobox" value="${creditContact.relation}"/></td>
												<td>电话:</td>
												<td><input id="contactmobile${status.index+1}" name="contactmobile" type="text" data-options="required:true,validType:['length[0,11]']" class="textbox easyui-numberbox" value="${creditContact.type=='2'?creditContact.phone:creditContact.mobile}"/></td>
												<td>联系人类型:</td>
												<td>
													<c:if test="${creditContact.type!=1&&creditContact.type!=2&&creditContact.type!=3}"><input type="text" disabled="disabled" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" value="本人"/></c:if>
													<c:if test="${creditContact.type==1}"><input type="text" disabled="disabled" ddata-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" value="常用联系人"/></c:if>
													<c:if test="${creditContact.type==2}"><input type="text" disabled="disabled" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" value="业主贷联系人"/></c:if>
													<c:if test="${creditContact.type==3}"><input type="text" disabled="disabled" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" value="催收联系人"/></c:if>
												</td>
												<td><input type="button" name="hand" class="btn"  style="display: none;" value="拨打" onclick="artOpenPage('电催拨打','collectionBase/phone/callRelation.do?contractNo=${bean.contractNo}&creditContactId=${creditContact.id}&name=${creditContact.name}&lateDays=${bean.lateDays}&relation=${creditContact.relation}','${status.index+1}');"></input>
													<input id="deleteBtn${status.index+1}" name="hand"   style="display: none;" type="button" class="btn" value="删除" onclick="removetr(this,'${status.index+1}','old');"> </input>
												<!-- <input type="button" value="保存" onclick="updatecredit(${status.index+1},'creditContact');"/> -->
												<!-- 	<span id="relation${status.index+1}" style="border-style: solid;border-width:thin;border-color:silver;display:none;position: absolute;left: 10px;margin-top: 25px" >
														<table style="border-color:black;background-color: white;width: 100%">
															<tr style="background-color: gray;">
															<td width="10%" align="center">催收人</td>
															<td width="10%" align="center">拨打时间</td>
															<td width="10%" align="center">接听人关系</td>
															<td width="10%" align="center">接听人姓名</td>
															<td width="10%" align="center">接听人号码</td>
															<td width="10%" align="center">电催摘要</td>
															<td width="20%" align="center">备注</td>
															</tr>
															<tr>
																<td><input id="collectionUid${status.index+1}" name="collectionUid"  type="hidden" data-options="required:true,validType:['length[0,50]']" value="${login_user.id}"/>${login_user.name}</td>
																<td><input id="callTime${status.index+1}" name="callTime"　type="text"  data-options="required:true,validType:['length[0,50]']" style="border: 0" value="<%=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())%>"/></td>
																<td><input id="answerRelation${status.index+1}" name="answerRelation"  type="text" data-options="required:true" class="textbox easyui-combobox" value="${creditContact.relation}"/></td>
																<td><input id="answerName${status.index+1}" name="answerName"  type="text" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox"  value="${creditContact.name}"></input></td>
																<td><input id="tel${status.index+1}" name="tel"  type="text" data-options="required:true,validType:['length[0,11]']" class="textbox easyui-numberbox" value="${creditContact.mobile}"></input></td>
																<td><input id="phoneSummary${status.index+1}" name="phoneSummary" type="text"  data-options="required:true" class="textbox easyui-combobox" value="${creditContact.phoneSummary}"></input></td>
																<td><textarea id="remark${status.index+1}"  name="remark"　rows="2" style="border-color: silver;">${creditContact.remark} </textarea></td>
															</tr>
															<tr><td colspan="7" align="right"><input type="button" onclick="updatecredit(${status.index+1},'record')" value="提交"/></td></tr>
														</table>
													</span>
													 -->
												</td>
												
											</tr>
											<%relationIndex++;%>
										</c:forEach>
										
										</table>
										<!-- <table width="63%">
											<tr>
												<td align="right"><input align="right" type="button" name="hand" value="添加联系人" onclick="addObj('relationTable','<%=relationIndex%>')"></input></td>
											</tr>
										 </table>-->
											
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<strong>催收记录</strong><hr/>
								<div id="history" style="border-style: solid;border-width:thin;border-color:silver; " >
									
									<table>
										<tr> 
											<td >电话催收</td>
										</tr>
										<tr>
											<td>催收预约:</td>
											<td><input id="orderTime" name="orderTime" type="text" class="textbox easyui-datetimebox"  value="${bean.orderTime }" /></td>
										</tr>
										<tr>
											<td>接听状态:</td>
											<td>
												<select id="answerState" name="answerState">
													<option value="">- - - -</option>
													<option value="N"  <c:if test="${ bean.answerState=='N'}">selected</c:if>>未接听</option>
													<option value="Y" <c:if test="${ bean.answerState=='Y'}">selected</c:if>>已接听</option>
												</select>
											</td>
										</tr>
										<tr >
											<td >催收摘要:</td>
											<td>
											<div> 
												<c:forEach var="dictionaryBean" items="${dictionarylist}"  varStatus="status">
													<input style="border-width: 0;" id="" name="dictionaryphoneSummary" type="radio" value="${dictionaryBean.keyProp}" <c:if test="${dictionaryBean.keyProp==bean.phoneSummary}">checked</c:if>/><input style="width:100px;border-width: 0" value="${dictionaryBean.keyValue}"/>
													<c:if test="${(status.index+1)%7==0}"><br></c:if>
												</c:forEach>
											</div> 
											</td>
										</tr>
										<tr>
											<td colspan="2" align="right"><input type="button" name="hand"   style="display: none;" class="btn" style="margin-left:  800px;" value="提交"  onclick="submitForm('updateForm','submit')"/></td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
						<tr>
							<td>
								<div id="collectionHistory"  style="height:410px;"> 
								</div>
							</td>
						</tr>	
								
								<!-- 
								<td>
								<strong>催收历史</strong>
								<div id="history" style="border-style: solid;border-width:thin;border-color:silver;overflow-y:scroll;height: 200px" >
								<table width="90%" class="datatable">
									<tr>
										<th align="center" width="5%">序号</th>
										<th width="10%">催收员</th>
										<th width="10%">逾期天数</th>
										<th width="10%">联系电话</th>
										<th width="10%">跟进日期</th>
										<th width="10%">联系人</th>
										<th width="10%">催收摘要</th>
										<th align="center"  width="10%">备注</th>
										
									</tr>
									<c:forEach var="historyList" items="${collectionHistoryList}" varStatus="status">
										<tr>
											<td style="text-align: center;">${status.index+1}</td>
											<td><input  type="text"   disabled="disabled"  value="${historyList.collectionUid}"/></td>
											<td><input  type="text"   disabled="disabled" value="${historyList.lateDays}"/></td>
											<td><input  type="text"   disabled="disabled" value="${historyList.tel}"/></td>
											<td><input  type="text"   disabled="disabled" value="${historyList.updateTimeStr}"/></td>
											<td><input  type="text"   disabled="disabled" value="${historyList.answerName}"/></td>
											<td><input  type="text"   disabled="disabled" value="${historyList.phoneSummary}"/></td>
											<td><input  type="text"   disabled="disabled" value="${historyList.remark}"/></td>
											
										</tr>
									</c:forEach>
								</table>
								</div>
								</td>
								 -->
							
						
						<!-- <tr>
							<td>
								<input type="button" value="返回" class="btn" onclick="javascript:back()"/>
							</td>
							<td></td>
						</tr>
						 -->
					</tbody>
				</table>
			</form>
		</div>
	
		<div style="display: none;" >
		<textarea id="creditContact_textarea" disabled="disabled">
								<tr>
									<input type="hidden"  name="creditContacttype" value="3" />
									<input type="hidden" name="creditContactIdrelationIndex" value="" />
									<td>姓名:</td>
									<td><input id="contactnamerelationIndex" name="contactname" type="text" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" value=""/></td>
									<td>接听人关系:</td>
									<td><input id="contactrelationrelationIndex" name="contactrelation" type="text" data-options="required:true" class="easyui-combobox"  value=""/></td>
									<td>电话:</td>
									<td><input id="contactmobilerelationIndex" name="contactmobile" type="text" data-options="required:true,validType:['length[0,11]']" class="textbox easyui-numberbox" value=""/></td>
									<td><input type="button" class="btn" value="删除" onclick="removetr(this,relationIndex,'new');"> </input><!-- <input type="button" value="保存" onclick="updatecredit(relationIndex,'creditContact');"/> -->
									<!-- 	<span id="relationrelationIndex" style="border-style: solid;border-width:thin;border-color:silver;display:none;position: absolute;left: 10px;margin-top: 25px" >
											<table style="border-color:black;background-color: white;width: 100%">
												<tr style="background-color: gray;">
												<td width="10%" align="center">催收人</td>
												<td width="10%" align="center">拨打时间</td>
												<td width="10%" align="center">接听人关系</td>	
												<td width="10%" align="center">接听人姓名</td>
												<td width="10%" align="center">接听人号码</td>
												<td width="10%" align="center">电催摘要</td>
												<td width="20%" align="center">备注</td>
												</tr>
												<tr>
													<td><input id="collectionUidrelationIndex" name="collectionUid"  type="hidden" data-options="required:true,validType:['length[0,50]']" value="${login_user.id}"/>${login_user.name}</td>
													<td><input id="callTimerelationIndex" name="callTime"　type="text" data-options="required:true,validType:['length[0,50]']" style="border: 0" value="<%=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())%>"/></td>
													<td><input id="answerRelationrelationIndex" name="answerRelation"  type="text" data-options="required:true" class="easyui-combobox" ></input></td>
													<td><input id="answerNamerelationIndex" name="answerName"  type="text" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" ></input></td>
													<td><input id="telrelationIndex" name="tel"  type="text" data-options="required:true,validType:['length[0,11]']" class="textbox easyui-numberbox" ></input></td>
													<td><input id="phoneSummaryrelationIndex" name="phoneSummary" type="text" data-options="required:true" class="easyui-combobox" value=""></input></td>
													<td><textarea id="remarkrelationIndex"  name="remark"　rows="2" data-options="required:true,validType:['length[0,50]']" style="border-color: silver;"/></td>
												</tr>
												<tr><td colspan="7" align="right"><input type="button" onclick="updatecredit(relationIndex,'record')" value="提交"/></td></tr>
											</table>
										</span>
										 -->
									</td>
									
								</tr>
								</textarea>
</div>


<div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
	<img src="img/loading.gif" alt="加载中..." />
</div>

</div>
</div>
</body>

<script type="text/javascript">

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
function submitForm(fromId,type) {
	//去掉 input 输入的 前后空格
	$('#queryForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	var formAction = $('#' + fromId).attr("action");
	var targetDiv = $('#' + fromId).attr("target");
	var params = $('#' + fromId).serialize();
	 
	//弹出异步加载 遮罩
	openMask();
	var sumitState="";
	if(type!='submit'){
		sumitState='确认保存';
	}else{
		sumitState='确认提交';
	}
	$.messager.confirm('操作',sumitState,
	function(result){
	if(result){
	$.ajax( {
		type : "POST",
		url  : formAction,
		data : params+"&submitType="+type+"&targetDiv=" + targetDiv,
		dataType : "json",
		success : function(data) {
			closeMask();
			//$('#' + targetDiv).html(data);
			if(data.success=="true"&&type!='submit'){
				$.messager.alert('消息', '保存成功！','info',function () {window.location.href="<%=basePath%>"+"collectionBase/phone/read.do?id="+$('#id').val();})
			}
			else if(data.success=="true"&&type=='submit'){
				$.messager.alert('消息',  '提交成功！','info',function(){window.location.href="<%=basePath%>" + "collectionBase/phone/actionquery.do";})
			}
			else {
				$.messager.alert('消息', '保存失败！')
			}
			},
		error : function() {
			closeMask();
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
		}
	})
	}},
		closeMask()
	);
}
//逾期计算器
function lateDaysCal(id) {
		window.open("<%=basePath%>collectionBase/phone/lateDaysCalQuery.do?id=${bean.contractId}&contractNo=${bean.contractNo}");
}
//添加选项卡
	function addTab(title, url) {
		if ($('#phoneCollection').tabs('exists', title)) {
			$('#phoneCollection').tabs('select', title);
		} else {
			var content = '<iframe name="sss" scrolling="auto" frameborder="0"  src="'
					+ url + '" style="width:100%;height:100%;"></iframe>';
			$('#phoneCollection').tabs('add', {
				title : title,
				content : content,
				closable : true
			});
		}
	}
 //还款计划
function paymentPlan(_url,_name){
	addTab(_name,_url);
    return null;
}
//页面加载完动作
$(document).ready(function (){
	/*if($("#ishandler").val()!="Y"){*/
		$("#updateForm").find("[name=show]").css("display", "");
	 
		$("#updateForm").find("[type=button]").css("display", "");
	 
	<c:choose>
		<c:when test="${fn:length(data.idNo)==15&& fn:substring(data.idNo,14,15)% 2 == 0}">$("#gender").val("女");</c:when>
		<c:when test="${fn:length(data.idNo)==18&& fn:substring(data.idNo,16,17)% 2 == 0}">$("#gender").val("女");</c:when>
		<c:otherwise>$("#gender").val("男");</c:otherwise>  
	</c:choose>
	//将Form元素禁用
	/**$("#updateForm").find("input").attr("disabled", "disabled");
	$("#updateForm").find("select").attr("disabled", "disabled");
	//将easyui控件禁用
	$("#updateForm").find(".easyui-combobox").combo('disable');
	$("#updateForm").find(".easyui-numberbox").numberbox('disable');
	$("#updateForm").find(".easyui-numberspinner").numberspinner('disable');
	$("#updateForm").find(".easyui-datebox").datebox('disable');
	$("#updateForm").find(".easyui-datetimebox").datetimebox('disable');
	**/
	//填充select数据样例
	/*
	var tsurl="sys/datadictionary/listjason.do?keyName=repaymethod";
	$("#repayMethod").combobox("clear");
	$('#repayMethod').combobox({url:tsurl, valueField:'keyProp', textField:'keyValue'});
	*/
	
	
	/**var obj = $("[name=relation]");
		obj.combobox("clear");
		obj.combobox({
			valueField:'keyProp', 
			textField:'keyValue',
			data:dataDictJson.relation
		});	**/
		
	var typelength=$('[name=creditContacttype]').length;
		for(var i=0;i<typelength;i++){
			
				  
			if($('[name=creditContacttype]').eq(i).val()=="3"){
				
			}
			else{
				/*$('#contactmobile'+(i+1)+'').attr("disabled", "disabled"); 
				$('#contactname'+(i+1)+'').attr("disabled", "disabled");
				$('#contactrelation'+(i+1)+'').combo('disable');*/
			 	$('#deleteBtn'+(i+1)+'').css("display","none");
				
			}
		}
	scanRecords();
	//填充select数据 商号地址省份
	var provinceurl = "sys/datadictionary/listjason.do?keyName=province";
		$("#firmProvince").combobox("clear");
		$('#firmProvince').combobox({
			url: provinceurl,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
	        $('#firmCity').combobox('clear');
	        $('#firmCounty').combobox('clear');
	        var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
	        $('#firmCity').combobox('reload',cityurl); 
	    }
		});
	//填充select数据 商号地址市
	var province = $('#firmProvince').combobox('getValue');
		var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(province);
		$("#firmCity").combobox("clear");
		$('#firmCity').combobox({
			url: cityurl,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
	        $('#firmCounty').combobox('clear');
	        var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
	        $('#firmCounty').combobox('reload',countyurl); 
	    }
		});
	//填充select数据 商号地址区县
	var city = $('#firmCity').combobox('getValue');
		var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(city);
		$("#firmCounty").combobox("clear");
		$('#firmCounty').combobox({url: countyurl, valueField: 'keyProp',textField: 'keyValue'});
		
	});
	//联系人关系
	$("[name=contactrelation]").combobox({
				valueField:'keyProp',    
		   	 	textField:'keyValue',
		   	 	data:dataDictJson.relation,
				method:'get',
				required:false
	
		});
	/**$("[name=answerRelation]").combobox({
				valueField:'keyProp',    
		   	 	textField:'keyValue',
		   	 	data:dataDictJson.relation,
				method:'get',
				required:false
	
		});**/
	//摘要信息
	/**$("[name=phoneSummary]").combobox({
				url:"sys/datadictionary/listjason.do?keyName=collectionsummary",
				valueField:'keyProp',    
		   	 	textField:'keyValue',
				method:'get',
				required:false
	
		});
**/
		
	/**function updatecredit(index,type){
		openMask();
		var targetDiv = $('#updateForm').attr("target");
		var url="";
		if(type=="record"){
			url="collectionRecord/phone/update.do";
		}else if(type=="creditContact"){
			url="collectionRecord/phone/creditContactUpdate.do";
		}
		$.ajax( {
					type : "POST",
					url  : url,
					data : "callTime_str="+$("#callTime"+index+'').val()+"&answerRelation="+$('#answerRelation'+index+'').combobox("getValue")+"&answerName="+$('#answerName'+index+'').val()+"&tel="+$('#tel'+index+'').val()+
						   "&phoneSummary="+$('#phoneSummary'+index+'').combobox("getValue")+"&remark=" + $('#remark'+index+'').val()+"&contractNo="+$('#contractNo').val()+"&lateDays="+$('#lateDays').val()+
						   "&contactname="+$("#contactname"+index+'').val()+"&contactrelation="+$("#contactrelation"+index+'').combobox("getValue")+"&contactmobile="+$("#contactmobile"+index+'').val()+"&appId="+$("#appId").val(),
					dataType : "json",
					success : function(data) {
						closeMask();
						//$('#' + targetDiv).html(data);
						if(data.success=="true"){
						$.messager.alert('消息', '提交成功！');
						}else{
						$.messager.alert('消息', '提交失败！');
						}
					},
					error : function() {
						closeMask();
						$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
					}
				});
				if(type=="record"){
				callMessage(index);
				}
		
	}**/
	var relationIndexAll=<%=relationIndex%>;
	function addObj(key){
		var html=$("#creditContact_textarea").val();		//得到 特约驾驶员 HTML
		//var keyIndex=key+"Index";
		//var index=$("#"+keyIndex).val();		//得到索引值
		html=html.replace(eval('/relationIndex/gm'),relationIndexAll);	//把索引占位符 替换
		
		var newDriver=$(html);					//转换成 JQuery 对象
		newDriver.appendTo("#"+key);				//添加到 对应地点
		$.parser.parse(newDriver);						//初始化 EasyUI
		//if(key=="commonContacts"){
			//initRelation("commonContact_"+index);
		//}
		
		//$("#"+keyIndex).val(++index);	//索引递增
		/**$("#phoneSummary"+relationIndexAll+"").combobox({
			url:"sys/datadictionary/listjason.do?keyName=collectionsummary",
			valueField:'keyProp',    
	   	 	textField:'keyValue',
			method:'get',
			required:false
	
		});**/
		/**$("#answerRelation"+relationIndexAll+"").combobox({
				valueField:'keyProp',    
		   	 	textField:'keyValue',
		   	 	data:dataDictJson.relation,
				method:'get',
				required:false
	
		});**/
		$("#contactrelation"+relationIndexAll+"").combobox({
				valueField:'keyProp',    
		   	 	textField:'keyValue',
		   	 	data:dataDictJson.relation,
				method:'get',
				required:false
	
		});
		
		relationIndexAll++;
		}
	function removetr(del,index,status){
		if(status=='new'){
			$(del).parent().parent().remove();
			}else{
			$.ajax( {
					type : "POST",
					url  : "collectionBase/phone/delete.do",
					data : "contactname="+$("#contactname"+index+'').val()+"&contactrelation="+$("#contactrelation"+index+'').combobox("getValue")+"&contactmobile="+$("#contactmobile"+index+'').val()+"&appId="+$("#appId").val(),
					dataType : "json",
					success : function(data) {
						closeMask();
						//$('#' + targetDiv).html(data);
						if(data.success=="true"){
						$.messager.alert('消息', '删除成功！');
						}else{
						$.messager.alert('消息', '删除失败！');
						}
					},
					error : function() {
						closeMask();
						$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
					}
				});
				$(del).parent().parent().remove();
			}	
		}
	//减免申请	
	function remissionApply(_contractNo) {
	if($("body").find("#dialogDiv").length==0){
		 $('body').append($("<div id='dialogDiv' style='top:120px;'></div>"));
	}
	$('#dialogDiv').dialog({
		title: "减免申请",
	    width: 500,
	    closed: false,
	    cache: false,
	    resizable:true,
	    href: encodeURI("<%=basePath%>collection/reduce/apply/update.do?contractNo="+_contractNo),
	    modal: true
	});
}
//欺诈申请
function cheatApply(_contractNo,applyType,title) {
	if($("#readState").val()=="1"||$("#readState").val()=="2"){
		if($("body").find("#dialogDiv").length==0){
			 $('body').append($("<div id='dialogDiv' style='top:120px;'></div>"));
		}
		$('#dialogDiv').dialog({
			title: title,
		    width: 500,
		    closed: false,
		    cache: false,
		    resizable:true,
		    href: encodeURI("<%=basePath%>collectionBase/phone/cheatAply.do?contractNo="+_contractNo+"&applyType="+applyType),
		    modal: true
		});
	}else{
		if($('#readState').val()=="6"){
			$.messager.alert('消息', '协催处理中！');
			}
		if($('#readState').val()=="7"){
			$.messager.alert('消息', '催收完成！');
			}
		if($('#readState').val()=="8"){
			$.messager.alert('消息', '欺诈申请！');
			}
		if($('#readState').val()=="9"){
			$.messager.alert('消息', '欺诈待复核处理中！');
			}
		if($('#readState').val()=="10"){
			$.messager.alert('消息', '欺诈审批处理中！');
			}
		if($('#readState').val()=="11"){
			$.messager.alert('消息', '欺诈认定生效！');
			}
		if($('#readState').val()=="12"){
			$.messager.alert('消息', '司法申请！');
			}
		if($('#readState').val()=="13"){
			$.messager.alert('消息', '司法待复核处理中！');
			}
		if($('#readState').val()=="14"){
			$.messager.alert('消息', '司法审批处理中！');
			}
		if($('#readState').val()=="15"){
			$.messager.alert('消息', '司法认定生效！');
			}
	}
	
	
}
//拨打联系人电话，操作记录
function artOpenPage(_title,_url,index) {
	if($("body").find("#dialogDiv").length==0){
		 $('body').append($("<div id='dialogDiv' style='top:150px;'></div>"));
	}
	  
	$('#dialogDiv').dialog({
	    title: _title,
	    width: 600,
	    closed: false,
	    cache: false,

	    href: encodeURI(_url),
	    modal: true,
	    resizable: true,
	    onLoad: function() {
		var obj11 = $("#answerRelation");
		 
		obj11.combobox("clear");
		obj11.combobox({
			valueField:'keyProp', 
			textField:'keyValue',
			data:dataDictJson.relation
		});
		 
		$("#phoneSummary").combobox({
		url:"sys/datadictionary/listjason.do?keyName=collectionsummary",
		valueField:'keyProp',    
   	 	textField:'keyValue',
		method:'get',
		required:false
		});	
		callMessage(index);	
	 	}
	});
	$('#dialogDiv').window('center');
}
function callMessage(index){
		$('#answerRelation').combobox("setValue",$('#contactrelation'+index+'').combobox("getValue"));
		$('#answerName').val($('#contactname'+index+'').val());
		$('#tel').val($('#contactmobile'+index+'').val());
}
//页面加载完动作
function scanRecords(){
	 
	  var divtext="<iframe id='id' frameborder='0' width='100%'height='100%'  scrolling='no' src='<%=basePath%>collection/visit/record/getRecordHistory.do?contractNo=${bean.contractNo}'></iframe>";
	  $("#collectionHistory").html(divtext);
	  
}
	
</script>
</html>


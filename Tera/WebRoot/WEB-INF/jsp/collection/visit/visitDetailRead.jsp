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
<title>落地催详情</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
 
  
</head>
<body>
<div id="remissionApply" class="easyui-tabs" data-options="fit:true">
	<div title="落地催催收">
	<div id="main">
	<div id="part1" class="part">
		 
		<input id="appId" name="appId" type="hidden" value="${bean.appId}"/>
		<div class="content">
			<div style="margin-left: 10px;margin-top: 10px; width:80%;">
				
				<div style="float: left; ">
				
					<input type="button" class="btn" style="display: none;" id="" name="" onclick="lateDaysCal('${bean.id}')" value="逾期计算器"/>&nbsp;
					<input type="button" class="btn" style="display: none;" id="" name="" onclick="paymentPlan('<%=basePath%>collection/reduce/apply/repayPlanDetail.do?contractNo=${bean.contractNo}','还款计划')" value="还款计划"/>&nbsp;
					<input type="button" class="btn" style="display: none;" id="" name="" onclick="paymentPlan('<%=basePath%>collection/visit/credit/read.do?id=${creditApp.id}','档案资料')" value="档案资料"/>&nbsp;
					<input type="button" class="btn" style="display: none;" id="" name="" onclick="remissionApply('${bean.contractNo}')" value="减免申请"/>&nbsp;
					<input type="button" value="欺诈申请" class="btn" style="display: none;" onclick="cheatApply('${bean.contractNo}','${bean.state}',2,'欺诈申请')"/>
					<input type="button" value="司法申请 "  class="btn" style="display: none;" onclick="cheatApply('${bean.contractNo}','${bean.state}',1,'司法申请')"/>
					<input type="button" value="外包申请 "  class="btn" style="display: none;" onclick="cheatApply('${bean.contractNo}','${bean.state}',3,'外包申请')"/>
				</div>
				<br />
				<div style="float: right;">
					<input type="button" class="btn" style="display: none;" id="" name="" onclick="javascript:window.history.go(-1)" value="返回上一级"/>&nbsp;
					<input type="button" class="btn" style="display: none;" id="" name="" onclick="saveObj('save');" value="保存"/>
				</div>
			</div>
		<br/>
			<form id="updateForm" name="updateForm" action="" AUTOCOMPLETE="off">
				 
				<table id="appInfo">
					<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>借款信息</strong></div><hr/>
					<input id="id" name="id" type="hidden" size="35" value="${bean.id}" />
					<input  name="contractNo" type="hidden" size="35" value="${bean.contractNo}" />
								<tr>
									<td><!-- <SPAN style="color:red">*</SPAN> -->合同编号:</td>
									<td><input id="contractNo" name="" type="text" disabled="disabled" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.contractNo}"/></td>
								
									<td>客户姓名:</td>
									<td><input id="customerName" name="" type="text" disabled="disabled" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.customerName}"/></td>
									<td>性别:</td>
									<td><input id="userGender" name="" type="text" disabled="disabled" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value=""/>
									</td>
									<td>合同额:</td>
									<td><input id="contractAmount" name="" type="text"  disabled="disabled""  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.contractAmount}"/></td>
								</tr>
								<tr>
									<td>产品:</td>
									<td><input id="product" name="" type="text" disabled="disabled" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.product}"/></td>
								
									<td>历史逾期期数:</td>
									<td><input id="periodLateHis" name="" type="text" disabled="disabled"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.periodLateHis}"/></td>
									<td>逾期天数:</td>
									<td><input id="lateDays" name="" type="text" disabled="disabled"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.lateDays}"/></td>
									<td>营业部:</td>
									<td><input id="orgId" name="" type="text" disabled="disabled" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.orgName}"/></td>
								</tr>
								<tr>
									<td>月还款额:</td>
									<td><input id="monthAmountAll" name="" type="text" disabled="disabled" data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.monthAmountAll}"/></td>
									<td>应还总额:</td>
									<td><input id="amountAll" name="" type="text"  disabled="disabled" data-options="min:0,precision:2"  class="textbox easyui-numberbox" value="${bean.amountAll}"/></td>
									<td>滞纳金:</td>
									<td><input id="delay" name="" type="text" disabled="disabled"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.delay}"/></td>
									<td>罚息:</td>
									<td><input id="penalty" name="" type="text" disabled="disabled"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.penalty}"/></td>
									</tr>
								<tr>	
									<td>还款日:</td>
									<td><input id="repaymentDate" name="" type="text" disabled="disabled" class="textbox easyui-datebox" value="${bean.repaymentDateStr}"/></td>
									<td>还款银行:</td>
									<td><input id="bankName" name="" type="text" disabled="disabled" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.bankName}"/></td>								 
									<td>银行卡号:</td>
									<td><input id="bankAccount" name="" type="text" disabled="disabled" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.bankAccount}"/></td>
									<td></td><td></td>
								</tr>
								</table>
				
 <div id="mainBusinessInfoDiv" <c:if test="${fn:contains(bean.product,'业主贷')==false}">style="display: none;"</c:if>>
	<table>	
		<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>主营业务信息</strong></div><hr/>
		<tr>
			<td>商号名称:</td>
			<td><input id="" name="" disabled="disabled" type="text" data-options="required:true,validType:['length[0,100]']" 
			class="textbox easyui-validatebox" value="${creditApp.firmName}"/></td>
			<td>主营业务:</td>
			<td><input id="" name="" disabled="disabled" type="text" data-options="required:true,validType:['length[0,100]']" 
			class="textbox easyui-validatebox" value="${creditApp.firmMainBusiness}"/></td>
			<td>年营业额:</td>
			<td><input id="" name="" disabled="disabled" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
			class="textbox easyui-numberbox" value="${creditApp.firmIncome/10000}" style="width:128px;"/>万元</td>
			<td>占股比例:</td>
			<td><input id="" name="" disabled="disabled" type="text" editable="false"  data-options="required:true,min:0,precision:2" 
			class="textbox easyui-numberbox" value="${creditApp.firmShare}" style="width:140px;"/>%</td>				
		</tr>
		<tr>
			<td>商号地址:</td>
			<td colspan="5" ><input id="firmProvince" name="" disabled="disabled" type="text" data-options="required:true,validType:['length[0,10]']" 
			class="easyui-combobox" value="${creditApp.firmProvince}" editable="false" style="width:140px;" />省
			<input id="firmCity" name="" disabled="disabled" type="text" data-options="required:true,validType:['length[0,10]']" 
			class="easyui-combobox" value="${creditApp.firmCity}" editable="false" style="width:140px;" />市
			<input id="firmCounty" name="" disabled="disabled" type="text" data-options="required:true,validType:['length[0,10]']" 
			class="easyui-combobox" value="${creditApp.firmCounty}" editable="false" style="width:128px;" />区县
			<input id="firmAddress" name="" disabled="disabled"  type="text" data-options="required:true,validType:['length[0,100]']" 
			class="textbox easyui-validatebox" value="${creditApp.firmAddress}" editable="false"/></td>
		</tr>
		<tr>
			<td>本地经营时间:</td>
			<td><input id="firmManageDate" name="" disabled="disabled" type="text" editable="false" data-options="required:true"
			class="textbox easyui-datebox" value="${creditApp.firmManageDateStr}"/></td>					
		</tr>
	</table>
</div>
				<hr/>
					 
				<table  id="commonContacts" style="width: auto;" >
					<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;">
					<strong>联系人信息
						<span>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn" style="display: none;" value="添加" onclick="addObj('commonContacts');"/>
							&nbsp;
							<!--<input type="button" class="btn" value="保存" onclick="saveObj();" />  
						--></span>
					</strong></div><hr/>
				<input type="hidden"  id="commonContactsIndex" value="${fn:length(commonContacts)}" />
				
				
				<c:forEach items="${commonContacts}" var="creditContact" varStatus="status">
				<c:if test="${status.index>=0}">
					<tr id="commonContact_${status.index}">
						<td>
						<input type="hidden" id="creditContactId_${status.index}" name="commonContacts[${status.index}].id" value="${creditContact.id}" />
						<input type="hidden" name="commonContacts[${status.index}].appId" value="${creditContact.appId}" />
						<input type="hidden" name="commonContacts[${status.index}].type" value="${creditContact.type}" />
						<input type="hidden" id="state" name="commonContacts[${status.index}].state" value="1" />
						姓名:</td>
						<td><input id="name" name="commonContacts[${status.index}].name" type="text"  readonly="readonly"  data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" value="${creditContact.name}"/></td>
						<td>接听人关系:</td>
						<td><input id="relation" name="commonContacts[${status.index}].relation" type="text" data-options="required:true,validType:['length[0,10]']" 
							class="easyui-combobox" value="${creditContact.relation}" readonly="readonly" editable="false" /> 
							 
						</td>
						<td>电话:</td>
						<td><input id="mobile" name="commonContacts[${status.index}].mobile" type="text"   readonly="readonly" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" value="${creditContact.type=='2'?creditContact.phone:creditContact.mobile}"/></td>
						<td>联系人类型:</td>
						<td><input id="" name="" type="text"  readonly="readonly"  data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" value="${creditContact.type!='1'?creditContact.type!='2'?creditContact.type!='3'?creditContact.type!='4'?creditContact.type!='5'?'':'本人':'配偶':'催收添加联系人':'业主贷联系人':'常用联系人'}"/></td>
						
						<td><input type="button" class="btn" style="display: none;" value="拨打" onclick="javascript:artOpenPage('拨打','collection/visit/record/callRelation.do?contractNo=${bean.contractNo}&creditContactId=${creditContact.id}&relation=${creditContact.relation}&name=${creditContact.name}&mobile=${creditContact.type=='2'?creditContact.phone:creditContact.mobile}');" ></input>
						<c:if test="${creditContact.type=='3'}">
						 
							&nbsp;&nbsp;<input type="button" class="btn" style="display: none;" value="删除" onclick="removetr(this,${status.index},'old')"/>
						  </c:if>
						
						</td>
					</tr>
				
				</c:if>
		</c:forEach>
		<tr style="display: none;"><td>
<textarea id="commonContacts_textarea" disabled="disabled">
	<tr id="commonContact_commonContactsIndex">
		<td>
		<input type="hidden" name="commonContacts[commonContactsIndex].id" value="" />
		<input type="hidden" name="commonContacts[commonContactsIndex].appId" value="${bean.appId}" />
		<input type="hidden" name="commonContacts[commonContactsIndex].type" value="3" />
		<input type="hidden" id="state" name="commonContacts[commonContactsIndex].state" value="1" />
		姓名:</td>
		<td>
		 
			<input id="name" name="commonContacts[commonContactsIndex].name" 
			type="text" data-options="required:true,validType:['length[0,50]']" 
			class="textbox easyui-validatebox" 
			value=""/>
		</td>
		<td>联系人关系:</td>
		<td>
			
			<input id="relation" name="commonContacts[commonContactsIndex].relation" type="text" data-options="required:true,validType:['length[0,10]']" 
			class="easyui-combobox" value="" editable="false" /> 
		</td>
		 
		<td>联系方式:</td>
		 
		<td>
			<input id="mobile" name="commonContacts[commonContactsIndex].mobile" 
			type="text" data-options="required:true,validType:['length[0,50]']" 
			class="textbox easyui-validatebox" 
			value=""/>	
		</td>
		<td>
		<input type="button" class="btn" value="删除" onclick="rmObj('commonContact_commonContactsIndex');"/>
		
		 
		</td>
	</tr>
</textarea>
</td></tr>
		</table>

	<hr/>
		<table  id="" style="width: auto;" >
					<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;">
					<strong>催收记录</strong>
					</div>
					<hr/>
						 
							<tr>
								<td>催收预约:</td>
								<td><input id="orderTime" name="orderTime" type="text" class="textbox easyui-datetimebox"  value="${bean.orderTime}" /></td>
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
													<input style="border-width: 0;" id="phoneSummary" name="phoneSummary" type="radio" value="${dictionaryBean.keyProp}" <c:if test="${dictionaryBean.keyProp==bean.phoneSummary}">checked</c:if>/>
													<input style="width:100px;border-width: 0" value="${dictionaryBean.keyValue}"/>
													<c:if test="${(status.index+1)%7==0}"><br></c:if>
										</c:forEach>
									</div> 
								</td>
							</tr>
							<tr >
								<td colspan="2">
									<input type="button" class="btn" style="display: none;" id="" name="" onclick="saveObj('submit');" value="提交"/>
								</td>
								 
							</tr>
		</table>
		<hr/>
		<table>	
					<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>申请资料上传</strong></div><hr/>
					<tr>
						<td>
							<span style="float:left ;"  id="yingxiangUP">
								附件：&nbsp; &nbsp;
 								 
								<input id="file" name="file" type="file" accept="application/x-zip-compressed" />&nbsp; &nbsp;
							</span>
								<input onclick="fileForm();"  value="上传" type="button" class="btn" style="display: none;" />
						</td>
					</tr>
					<tr>
						<td><SPAN style="color:red">上传提示：压缩文件不要大于10M 并且压缩包内单个图片不能大于1M</SPAN></td>
					</tr>
		</table>
		 
		<hr/>
	<!-- 影像查看开始 -->
			<table width="80%;">
				<tr>
					<td id="imgDiv" >
						<jsp:include page="/img/imgSlidePath.do?appId=${bean.appId}" />
					</td>
				</tr>
			</table>
	<!-- 影像查看结束 -->		
		
		<hr>
		<table width="85%;">
				<tr>
					<td >
						<div id="collectionHistory"  style="height:450px;"> 
					</td>
				</tr>
			</table>	
		
		
		  
		
		</div>
		
				</div>	
				</form>
			</div>
		</div>
	</div>
 
	<form id="fileSmt" action="file/upload/zipupload.do"  enctype="multipart/form-data" style="display: none;" >
		<input type="hidden" id="fileappId" name="appId" <c:if test="${bean!=null}">value="${bean.appId}"</c:if>/>
	</form>
	<div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
		<img src="img/loading.gif" alt="加载中..." />
	</div>
</body>	
<script type="text/javascript" >
function zhezhao(){
		$("<div class=\"datagrid-mask\" id='chushiZhezhao'></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
		$("<div class=\"datagrid-mask-msg\" id='chushiZhezhaoMsg'></div>").html("正在加载，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
}
zhezhao();
function rmZhezhao(){
		$("#chushiZhezhao").remove();
		$("#chushiZhezhaoMsg").remove();
}

$(window).load(function (){
	rmZhezhao();
});
</script>
<script type="text/javascript" >

	
	//添加选项卡
	function addTab(title, url) {
		if ($('#remissionApply').tabs('exists', title)) {
			$('#remissionApply').tabs('select', title);
		} else {
			var content = '<iframe name="sss" scrolling="auto" frameborder="0"  src="'
					+ url + '" style="width:100%;height:100%;"></iframe>';
			$('#remissionApply').tabs('add', {
				title : title,
				content : content,
				closable : true
			});
		}
	}
 
	//添加对象
	function addObj(key){
		var html=$("#"+key+"_textarea").val();		//得到 特约驾驶员 HTML
		var keyIndex=key+"Index";
		var index=$("#"+keyIndex).val();				//得到索引值
		html=html.replace(eval('/'+keyIndex+'/gm'),index);	//把索引占位符 替换
		var newDriver=$(html);					//转换成 JQuery 对象
		newDriver.appendTo("#"+key);				//添加到 对应地点
		$.parser.parse(newDriver);						//初始化 EasyUI
		if(key=="commonContacts"){
			initRelation("commonContact_"+index);
		}
		
		$("#"+keyIndex).val(++index);	//索引递增
		
	}
	//删除元素 实际上是隐藏元素
	function rmObj(objId,sm){
		$.messager.confirm('消息', "是否确认删除", function(ok){
            if (ok){
            	var obj = $("#"+objId);
        		obj.find("#state").val("0");
        		obj.find('.easyui-combobox').combo('disableValidation');
        		obj.find('.easyui-numberbox').numberbox('disableValidation');
        		obj.find('.easyui-datebox').datebox('disableValidation');
        		obj.find('.easyui-validatebox').validatebox('disableValidation');
        		obj.hide();
            }
		});
	}
	function initRelation(objId){
		var obj = $("#"+objId).find("#relation");
		 
		obj.combobox("clear");
		obj.combobox({
			valueField:'keyProp', 
			textField:'keyValue',
			data:dataDictJson.relation
		});	
	}
	//保存  添加的联系人   和催收预约信息
	function saveObj(submitType){
		var params = $('#updateForm').serialize(); 
		 $.ajax( {
			type : "POST",
			url : "collection/visit/record/addRelation.do?submitType=" + submitType,
			data : params,
			dataType : "json",
			success : function(data) {
		
		 	 
			if ("true"==data.success) {
				  
				$.messager.alert('消息', data.message,'',function(){
					if(submitType=='submit'){
						window.location.href="<%=basePath%>collection/visit/detail/query.do";
					}
					else{
						 window.location.reload();
					}
				});
			} else {				
    			$.messager.alert('消息', data.message);
            }
		}
	}); 
}	
function removetr(del,index,status){
		if(status=='new'){
			$(del).parent().parent().remove();
		}else{
			 $.messager.confirm('消息', "是否确认删除", function(ok){
	            if (ok){
	            	 $.ajax( {
						type : "POST",
						url  : "collection/visit/record/delete.do",
						data : "creditContactId="+$("#creditContactId_"+index).val(),
						dataType : "json",
						success : function(data) {
							 
							//$('#' + targetDiv).html(data);
							if(data.success=="true"){
								$.messager.alert('消息', '删除成功！');
								$(del).parent().parent().remove();
							}else{
							$.messager.alert('消息', '删除失败！');
							}
						},
						error : function() {
							closeMask();
							$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
						}
					}); 
	            }
			});
	   	}	
}
//拨打联系人电话，操作记录
function artOpenPage(_title,_url) {

	if($("body").find("#dialogDiv").length==0){
		 $('body').append($("<div id='dialogDiv' style='top:150px;width:600px;'></div>"));
	}
	  
	$('#dialogDiv').dialog({
	    title: _title,
	    cache: false,
	    href: encodeURI(_url),
	    modal: true,
	    resizable: true,
	    onLoad: function() {
		var obj11 = $("#relation1");
		 
		obj11.combobox("clear");
		obj11.combobox({
			valueField:'keyProp', 
			textField:'keyValue',
			data:dataDictJson.relation
		});
		 
		$("#phoneSummary1").combobox({
		url:"sys/datadictionary/listjason.do?keyName=collectionsummary",
		valueField:'keyProp',    
   	 	textField:'keyValue',
		method:'get',
		required:false
		});	
		
	 	},
	  
	});
	$('#dialogDiv').window('center');
}

//逾期计算器
function lateDaysCal(id) {
		/*window.open("<%=basePath%>collectionBase/phone/lateDaysCalQuery.do?id="+id);*/
		window.open("<%=basePath%>collectionBase/phone/lateDaysCalQuery.do?id=${bean.contractId}&contractNo=${bean.contractNo}");
}
//还款计划
function paymentPlan(_url,_name){
	//var url="<%=basePath%>collection/reduce/apply/repayPlanDetail.do?contractNo=G通化市201504160016";
	addTab(_name,_url);
    return null;
}
//减免申请
function remissionApply(_contractNo) {
	if($("body").find("#dialogDiv").length==0){
		 $('body').append($("<div id='dialogDiv' style='top:120px;'></div>"));
	}
	$('#dialogDiv').dialog({
		title: "test",
	    width: 500,
	    closed: false,
	    cache: false,
	    resizable:true,
	    href: encodeURI("<%=basePath%>collection/reduce/apply/update.do?contractNo="+_contractNo),
	    modal: true
	});
}

//司法、欺诈申请
function cheatApply(_contractNo,state,applyType,title) {
	
	if(state>=8&&state<=11)
	{
		$.messager.alert('消息',"已进入欺诈审核流程，不能重复申请!");
		return;
	}
	else if(state>=12&&state<=15)
	{
		$.messager.alert('消息',"已进入司法审核流程，不能重复申请!");
		return;
	}
	if(state>=16&&state<=17)
	{
		$.messager.alert('消息',"已进入外包审核流程，不能重复申请!");
		return;
	}
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
}

//文件上传
function fileForm(){
	$('#isStart').val('');
	var appid=$('#appId').val();
	if(appid!=null&&appid!=''){
		var thisfile=$('#yingxiangUP').find("#file");
		var fileValue=$('#file').val();
		if((fileValue.substring(fileValue.lastIndexOf("."))).toUpperCase()!=".ZIP"){
			$.messager.alert('消息', "文件类型必须为 ZIP格式");
			return;
		}
		
		$.messager.confirm('消息', "是否确定上传？", function(ok){
            if (ok){
            	var $file=$("#fileSmt input[name='file']");
        		if($file!=null){
        			$file.remove();
        		}
        		var fcolne=thisfile.clone();
        		$('#fileSmt').append(thisfile);
        		$('#yingxiangUP').append(fcolne);
        		$('#fileSmt').submit();
            }
    	});
	}else{
		$.messager.alert('消息', "请先保存，然后才能上传附件。");
	}
}
$('#fileSmt').submit(function() {
	openMask();
	// 上传文件 必须用 这个 异步提交
    $(this).ajaxSubmit({
    		type : "POST",
    		contentType:"multipart/form-data",
    		url : "file/upload/zipupload.do",
    		//beforeSubmit:function(type) {
//     			alert(type);
    		//},
    	    success:function(data) {
    	    	data=jQuery.parseJSON(data);
    			if ("true"==data.success) {
    				
    				$.messager.alert('消息', data.message,"info", function(){
    	                	imgpartLoad($('#appId').val());
    	        	});
                } else {
    				
    				$.messager.alert('消息', data.message);
                }
    			closeMask();
    		}
    });
    return false;
});
//打开Loading遮罩并修改样式
function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
}
//页面加载完动作
   function scanRecords(){
	 
	  var divtext="<iframe id='id' frameborder='0' width='100%'height='100%'  scrolling='no' src='<%=basePath%>collection/visit/record/getRecordHistory.do?contractNo=${bean.contractNo}'></iframe>";
	  $("#collectionHistory").html(divtext);
	  
}
$(document).ready(function (){
	$('#remissionApply').find('input').each(function(){
		if($(this).attr("type")=="button"){
			
				$(this).show();
		
			/* <c:if test="${roleName!='落地催收专员'}"> 
				if($(this).val()=='返回上一级'||$(this).val()=='逾期计算器'||$(this).val()=='档案资料'||$(this).val()=='还款计划'){
				}else{$(this).hide();
				}
			</c:if> */
		}
	});
	<c:forEach items="${commonContacts}" var="commonContact" varStatus="status">
			initRelation("commonContact_${status.index}");
	</c:forEach>
	 
	 scanRecords();
	 //客户性别
	<c:choose>
		<c:when test="${fn:length(data.idNo)==15&& fn:substring(data.idNo,14,15)% 2 == 0}">$("#userGender").val("女");</c:when>
		<c:when test="${fn:length(data.idNo)==18&& fn:substring(data.idNo,16,17)% 2 == 0}">$("#userGender").val("女");</c:when>
		<c:otherwise>$("#userGender").val("男");</c:otherwise>  
	</c:choose>
	 	
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

</script>
</html>


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
<title>财富端个人客户申请表</title>
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
		<p class="title"><a href="javascript:void(0);">个人客户录入申请</a></p>
		<div class="content">
			<form id="updateForm" >
			<input type="hidden" name="customerType" value="01" <c:if test="${bean!=null}">disabled="disabled"</c:if> />
			<input type="hidden" id="id" name="id" <c:if test="${bean!=null}">value="${bean.id}"</c:if> />
			<input type="hidden" id="customerNo" name="customerNo" <c:if test="${bean!=null}">value="${bean.customerNo}" disabled="disabled"</c:if>/>
			<input type="hidden" id="customerLever" name="customerLever" <c:if test="${bean!=null}">value="${bean.customerLever}" disabled="disabled"</c:if>/>
			<input type="hidden" id="isStart" name="isStart" value="false" />
			<input type="hidden" id="appId" name="appId" value="${bean.appId}" />
			<table>
				<tbody>
				<tr>
					<td>合同编号:</td>
                    <td><input <c:if test="${bean!=null}">value="${bean.contractNo}" </c:if> class="textbox easyui-validatebox" name="contractNo" id="contractNo" data-options="validType:['length[0,50]']"/></td>
					<td>证件类型:</td>
					<td><input <c:if test="${bean!=null}">value="${bean.idType}" </c:if> class="easyui-combobox" name="idType" id="idType" style="width:148px;" editable="false" data-options="required:true,validType:['length[0,18]']"/></td>
					<td>证件号码:</td>
					<td><input <c:if test="${bean!=null}">value="${bean.idNo}" </c:if> id="idNo" onChange="getCustomer();" name="idNo" type="text" data-options="required:true,validType:['length[0,18]','idNo']" class="textbox easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>姓名:</td>
					<td><input <c:if test="${bean!=null}">value="${bean.name}" </c:if> readonly="readonly" id="name" name="name" type="text" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" /></td>
                    <td>手机号:</td>
					<td><input <c:if test="${bean!=null}">value="${bean.mobile}"</c:if> id="mobile" name="mobile" type="text" data-options="required:true,validType:['mobile']" class="textbox easyui-validatebox" /></td>
					<td>电话:</td>
					<td><input <c:if test="${bean!=null}">value="${bean.phone}"</c:if> id="phone" name="phone" type="text" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>申请类型:</td>
					<td><input <c:if test="${bean!=null}">value="${bean.appType}"</c:if> id="appType" name="appType"  data-options="required:true,validType:['length[0,18]']" class="easyui-combobox"  style="width:148px;" editable="false" /></td>
					<td>出借金额:</td>
					<td>
						<input class="easyui-numberspinner" id="amount" name="amount"
						 data-options="min:10000,increment:10000,precision:2,groupSeparator:','
						 , onChange: function(value){
							if(value!=null&&value!=''){
								var cz=value%10000;
								if(cz>0){
									$(this).numberspinner('setValue',value-cz);
								}
							}else{
								$(this).numberspinner('setValue',0);
							}
						 }"
						 style="width:145px;" 
						 <c:if test="${bean!=null}">value="${bean.amount}"</c:if>
						 <c:if test="${bean==null}">value="0"</c:if>
						 />
						
					</td>
					<td>产品:</td>
					<td><input <c:if test="${bean!=null}">value="${bean.product}"</c:if> id="product" name="product" data-options="required:true,validType:['length[0,50]']" class="easyui-combobox" style="width:148px;" editable="false" /></td>
				</tr>
				<tr>
					<td>服务截至日期:</td>
					<td><input <c:if test="${bean!=null}">value="${bean.serviceEndDate}"</c:if> id="serviceEndDate"
					 name="serviceEndDate" type="text" data-options="required:true" editable="false" class="textbox easyui-datebox" /></td>
					<td>营销人员:</td>
                    <td><input <c:if test="${bean!=null}">value="${bean.staffNo}" </c:if> class="easyui-combobox" name="staffNo" id="staffNo" editable="false" data-options="validType:['length[0,18]']"/></td>
               		<td>所属机构:</td>
                    <td><input 
                    	<c:if test="${bean!=null}">value="${bean.orgId}" </c:if> 
                    	<c:if test="${bean==null}">value="${login_org.orgId}" </c:if> 
                    	class="easyui-combobox" name="orgId" id="orgId" readonly="readonly" data-options="validType:['length[0,18]']"/></td>
                </tr>
				<tr>
					<td>开户行省:</td>
					<td><input id="bankProvince" name="bankProvince" type="text" class="textbox easyui-combobox" data-options="required:true" value="${bean.bankProvince}" editable="false"/></td>
					<td>开户行市:</td>
					<td><input id="bankCity" name="bankCity" type="text" class="textbox easyui-combobox" data-options="required:true" value="${bean.bankCity}" editable="false"/></td>
					<td>POS关联手机号:</td>
					<td><input <c:if test="${bean!=null}">value="${bean.bankMobile}"</c:if> id="bankMobile" name="bankMobile" type="text" data-options="required:true,validType:['mobile']" class="textbox easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>开户行名称:</td>
					<td>
						<input id="bankCode" name="bankCode" type="text" class="textbox easyui-combobox" data-options="required:true" value="${bean.bankCode}" editable="false"/>
						<input id="bankCodeName" name="bankName" type="hidden" value="${bean.bankName}"/>
					</td>
					<td>支行名称:</td>
					<td>
						<input <c:if test="${bean!=null}">value="${bean.repayAccBank}"</c:if> id="repayAccBank" name="repayAccBank" type="text" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" />
					</td>
					<td>银行卡号:</td>
					<td><input <c:if test="${bean!=null}">value="${bean.repayAccount}"</c:if> id="repayAccount" name="repayAccount" type="text" data-options="required:true,validType:['length[0,50]','NumLetters']" class="textbox easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>撮合类型:</td>
					<td><input <c:if test="${bean!=null}">value="${bean.matchType}"</c:if> id="matchType" name="matchType"  data-options="required:true,validType:['length[0,50]']" class="easyui-combobox" style="width:148px;" editable="false" /></td>
				</tr>
				<tr>
					<td colspan="8" align="center"><hr width="100%" size=2 color=#E0ECFF align=center noshade></td>
				</tr>
				<tr>
				<td>
					<input type="button" value="保存" class="btn" onclick="updateFunction()"/>
				</td>
				</tr>
				<!--影像上传 -->
				<tr>
					<td colspan="8">
						<table width="100%">
							<tr>
								<td>
									<div id="toolbar" style="margin-left: 10px;margin-top: 10px; font-size: 14px;" >
										<strong>影像资料</strong>
										<hr width="100%" size=2 color=#E0ECFF align=center noshade>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									必备资料： a b c									
								</td>
							</tr>
							<tr><td>
								<span style="float:left ;"  id="yingxiangUP">
								上传附件：&nbsp; &nbsp;
									<input onchange="fileForm();" id="file" name="file" type="file" accept="application/x-zip-compressed" />
								</span>
							</td></tr>
						</table>
					</td>
				</tr>
				<!--影响查看 -->
				<tr>
					<td colspan="8" id="imgDiv">
						<jsp:include page="/img/imgSlidePath.do?appId=${bean.appId}" />
					</td>
				</tr>
				<tr>
					<td>
						<input type="button" value="提交" class="btn" onclick="$('#isStart').val('start');updateFunction()"/>
						<input type="button" value="返回" class="btn" onclick="javascript:back()"/>
					</td>
				</tr>
				</tbody>
			</table>
			</form>
		</div>
	</div>
</div>
<form id="fileSmt" action="file/upload/zipupload.do"  enctype="multipart/form-data" style="display: none;" >
	<input type="hidden" id="fileappId" name="appId" <c:if test="${bean!=null}">value="${bean.appId}"</c:if>/>
</form>
<!-- <div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
	<img src="img/loading.gif" alt="加载中..." />
</div> -->
</body>
<script type="text/javascript">
//文件上传
function fileForm(){
	$('#isStart').val('');
	var appid=$('#appId').val();
	if(appid!=null&&appid!=''){
		var thisfile=$('#yingxiangUP').find("#file");
		var fileValue=$('#file').val();
		if((fileValue.substring(fileValue.lastIndexOf("."))).toUpperCase()!=".ZIP"){
			
			$.messager.confirm('消息', "文件类型必须为 ZIP格式");
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
		
		$.messager.confirm('消息', "请先保存，然后才能上传附件。");
	}
}
$('#fileSmt').submit(function() {
	openMask();
	// 上传文件 必须用 这个 异步提交
    $(this).ajaxSubmit({
    		type : "POST",
    		contentType:"multipart/form-data",
    		url : "file/upload/zipupload.do",
    		beforeSubmit:function(type) {
//     			alert(type);
    		},
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
		$('#isStart').val('');
		return;
	}
	openMask();
	var cusNo=$("#customerNo").val();
	if(!cusNo || cusNo==""){
		$('#isStart').val('');
		
		$.messager.alert('消息', '客户不存在，请先添加客户',"info" ,function(){
           
    	});
		closeMask();
		return;
	}
	$('#bankCodeName').val($('#bankCode').combobox('getText'));
// 	$('#file').attl("disabled","disabled");
	var params = $('#updateForm').serialize();
	$.ajax({
		type : "POST",
		url : "lendApp/save.do",
		data : encodeURI(params),
		success : function(data) {
			$('#isStart').val('');
			closeMask();
			if(data.type.indexOf("add")!=-1){
				$('#id').val(data.bean.id);
				$('#appId').val(data.bean.appId);
				$("#fileSmt").find("#fileappId").val(data.bean.appId);
			} 
			if ("true"==data.success) {
				if(data.type.indexOf("submit")!=-1){
					$.messager.alert('消息', data.message, "info",function(){
						window.history.go(-1);
		        	});
				}else{
					$.messager.alert('消息', data.message);
				}
			}else{
				$.messager.alert('消息', data.message);
			}
		}
	});
}
//返回
function back(){
	window.history.go(-1);
}
$(document).ready(function(){
	//初始化 营销人员
    var sales="sales/listSelect.do?orgId=${login_org.orgId}";
    $('#staffNo').combobox({
        url:sales, 
        valueField:'staffNo', 
        textField:'name'
    });
    
    var tsurl1="sys/org/allOrgList.do";
	$("#orgId").combobox("clear");
	$('#orgId').combobox({
		url:tsurl1,
		valueField:'orgId',
		textField:'orgName'
	});
    
	//填充select数据 个人证件类型
	var tsurl="sys/datadictionary/listjason.do?keyName=personidtype";
	$("#idType").combobox("clear");
	$('#idType').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		onSelect:getCustomer,
		data:dataDictJson.personidtype
	});
	
	//tsurl="sys/datadictionary/listjason.do?keyName=lendapptype";
	$("#appType").combobox("clear");
	$('#appType').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.lendapptype
	});

	//tsurl="sys/datadictionary/listjason.do?keyName=matchtype";
	$("#matchType").combobox("clear");
	$('#matchType').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.matchtype
	});
	
	tsurl="product/hedao/listjason.do?type=1&state=1";
	$("#product").combobox("clear");
	$('#product').combobox({
		url:tsurl, 
		valueField:'name', 
		textField:'name'		
	});
	
	//填充select数据 开户行信息省份
    var provinceurl = "sys/datadictionary/listjason.do?keyName=bankProv";
		$("#bankProvince").combobox("clear");
		$('#bankProvince').combobox({
			url: provinceurl,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
	            $('#bankCity').combobox('clear');
	            var cityurl = "sys/datadictionary/listjason.do?keyName=bankCity&parentKeyProp=" + encodeURI(newValue);
	            $('#bankCity').combobox('reload',cityurl); 
        }
	});
    //填充select数据 开户行信息市
    var province = $('#bankProvince').combobox('getValue');
		var cityurl = "sys/datadictionary/listjason.do?keyName=bankCity&parentKeyProp=" + encodeURI(province);
		$("#bankCity").combobox("clear");
		$('#bankCity').combobox({
			url: cityurl,
			valueField: 'keyProp',
			textField: 'keyValue'
	});
	
	//填充select数据 银行机构
	$("#bankCode").combobox("clear");
	$('#bankCode').combobox({
		url:"sys/datadictionary/listjason.do?keyName=bankcode",
		valueField:'keyProp', 
		textField:'keyValue'
	});
		
});

var lsZjNo,lsNoType;
function getCustomer(){
	var zjNo=$("#idNo").val();
	var zjNoType=$("input[name='idType']").val();
	if(zjNo&& zjNo!="" && zjNoType &&zjNoType!=""&&(zjNo!=lsZjNo||zjNoType!=lsNoType)){
		lsZjNo=zjNo;
		lsNoType=zjNoType;
		$.ajax({
			type : "POST",
			url : "customer/readJson.do",
			data : encodeURI("customerType=01&zjNumber=" + zjNo+"&idType="+zjNoType),
			dataType : "json",
			success : function(data) {
				if(data.bean){
					$("#name").val(data.bean.name);
					$("#mobile").val(data.bean.mobile);
					$("#phone").val(data.bean.phone);
					$("#customerNo").val(data.bean.id);
					$("#customerLever").val(data.bean.customerLever);
				}else{
					$("#customerNo").val('');
					$.messager.alert('消息', '没有检索到客户，请先添加。',"info", function(){
// 						window.history.go(-1);
		        	});
				}
			}
		});
	}
}
/* function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
} */
</script>
</html>
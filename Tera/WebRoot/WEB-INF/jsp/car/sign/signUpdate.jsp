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
<title>签约处理</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
	
<style type="text/css">
.upbox{ margin:0 auto; overflow:hidden;}
.upbox ul{ float:left; list-style-type:none; margin:0; padding:0; font:12px Arial, Helvetica, sans-serif; line-height:24px; border-left:1px solid white; border-top:1px solid white;}
.upbox ul li{ float:left;  width:100px; text-align:center; background:#eaeaea; white-space:nowrap; border-right:1px solid white; border-bottom:1px solid white; cursor:pointer;}
.upbox ul li:hover{ float:left; width:100px; text-align:center; background:#ccc; white-space:nowrap; border-right:1px solid white; border-bottom:1px solid white; cursor:pointer;}
</style>
</head>
<body>
<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">签约</a></p>
		<div class="content">
			<form id="updateForm" name="carApp.updateForm" action="carApp/update.do" AUTOCOMPLETE="off">
				<input type="hidden" name="appId" value="${bean.appId}" />
				<table>
					<tr><td><div style="font-size: 14px;" ><strong>借款信息</strong><hr width="100%" size=2 color="#D3D3D3" noshade></div></td></tr>
					<tr><td>
						<table>
							<tr>
								<td>客户姓名:</td>
								<td><input type="text" class="textbox easyui-validatebox" value="${bean.name}" disabled="disabled"/></td>
								<td>身份证号:</td>
								<td><input type="text" class="textbox easyui-validatebox" value="${bean.idNo}" disabled="disabled"/></td>
								<td>借款用途:</td>
								<td><input id="useage1" type="text" class="easyui-combobox"  value="${bean.useage1}" disabled="disabled"/>
									<input id="useage2" type="text" class="easyui-combobox"  value="${bean.useage2}" disabled="disabled"/></td>
							</tr>
							<tr>
								<td>借款金额:</td>
								<td><input type="text" editable="false"  data-options="required:true,min:0,precision:2" 
								class="textbox easyui-numberbox" value="${actualAmount}" style="width:140px;" disabled="disabled"/>元</td>
								<td>分期数:</td>
								<td><input id="period" type="text" class="easyui-combobox"  value="${decision.period}" disabled="disabled"/></td>
								<td>渠道产品:</td>
								<td><input id="belongChannel" type="text" class="textbox easyui-combobox" value="${decision.belongChannel}" disabled="disabled"/>
									<input id="product" type="text" class="textbox easyui-combobox" value="${decision.product}" disabled="disabled"/></td>
							</tr>
							<tr>
								<td>客户实际获得金额:</td>
								<td><input type="text" data-options="required:true,min:0,precision:2" 
								class="textbox easyui-numberbox" value="${decision.amount}" style="width:140px;" disabled="disabled"/>元</td>
								<td>月还款金额:</td>
								<td><input type="text" data-options="required:true,min:0,precision:2" 
								class="textbox easyui-numberbox" value="${MAmount}" style="width:140px;" disabled="disabled"/>元</td>
								<td colspan="2">&nbsp;</td>
							</tr>
						</table>
					</td></tr>
					<tr>
						<td>
							<div style="font-size: 14px;" >
								<strong>合同信息</strong>
								<hr width="100%" size=2 color="#D3D3D3" noshade>
							</div>
						</td>
					</tr>
					<tr><td>
						<table>
							<tr>
								<td>开户行信息:</td>
								<td colspan="5">
									<input name="contract.id" type="hidden" value="${contract.id}"/>
									<input name="contract.state" type="hidden" value="${contract.state}"/>
									<input name="contract.contractNo" type="hidden" value="${contract.contractNo}"/>
									<input id="appId" name="contract.loanAppId" type="hidden" value="${bean.appId}"/>
									<input id="bankProvince" name="contract.bankProvince" type="text" class="textbox easyui-combobox" data-options="required:true" value="${contract.bankProvince}" editable="false"/>省 &nbsp; &nbsp;
									<input id="bankCity" name="contract.bankCity" type="text" class="textbox easyui-combobox" data-options="required:true" value="${contract.bankCity}" editable="false"/>市 &nbsp; &nbsp;
									<input id="bankCode" name="contract.bankCode" type="text" class="textbox easyui-combobox" data-options="required:true" value="${contract.bankCode}" editable="false"/>银行 &nbsp; &nbsp;
									<input id="bankCodeName" name="contract.bankName" type="hidden" value="${contract.bankName}"/>
									<input name="contract.bankBranch" type="text" class="textbox easyui-validatebox" data-options="required:true,validType:['length[0,50]']" value="${contract.bankBranch}" />（支行全称）
								</td>
							</tr>	
							<tr>	
								<td>银行账户姓名:</td>
								<td><input name="contract.bankAccountName" type="text" class="textbox easyui-validatebox" data-options="required:true,validType:['length[0,50]']"
								readonly="readonly" value="${contract.id!=0?contract.bankAccountName:bean.name}" /></td>
								<td>银行卡号:</td>
								<td><input name="contract.bankAccount" type="text" class="textbox easyui-validatebox" data-options="required:true,validType:['length[0,100]']" value="${contract.id!=0?contract.bankAccount:bean.account}" style="  width: 180px;" /></td>
								<td>POS关联手机号:</td>
								<td><input name="contract.bankMobile" type="text" class="textbox easyui-validatebox" data-options="required:true,validType:['mobile']" value="${contract.bankMobile}" /></td>
							</tr>
							<tr>
								<td>签约地点:</td>
								<td colspan="3">
									<input id="signProvince" name="contract.signProvince" type="text" class="textbox easyui-combobox" data-options="required:true" value="${contract.signProvince}" editable="false"/>省 &nbsp; &nbsp; 
									<input id="signCity" name="contract.signCity" type="text" class="textbox easyui-combobox" data-options="required:true" value="${contract.signCity}" editable="false"/>市 &nbsp; &nbsp;
									<input id="signCounty" name="contract.signCounty" type="text" class="textbox easyui-combobox" data-options="required:true" value="${contract.signCounty}" editable="false"/>区(县) &nbsp; &nbsp;
								</td>
								<td>要求放款时间:</td>
								<td>
									<input name="contract.lendingDate" type="text" editable="false" data-options="required:true" class="textbox easyui-datebox" value="${contract.lendingDateStr}"/>
								</td>
							</tr>
							
							<c:if test="${!empty returnReasons }"> 
								<tr>
									<td>复核退回原因:</td>
									<td colspan=3">
										<textarea value="${returnReasons}" class="textbox easyui-validatebox" data-options="validType:['length[0,500]']" 
											style="resize: none;width:600px;height:50px !important;vertical-align:middle;background-color: #F0F0F0;" disabled="disabled">${returnReasons}</textarea>
									</td>
								</tr>
							</c:if> 
							
							<tr>
								<td colspan="5" align="right">
									<span style="float: right; padding-right: 20px;">
<%-- 										<c:if test="${loginId==toBeProcessed}"> --%>
											<input type="button" value="生成合同" class="btn" onclick="updateFunction('save')"/>
<%-- 										</c:if> --%>
									</span>
								</td>
								<td>&nbsp;</td>
							</tr>
						</table>
					</td></tr>
					<tr>
						<td>
							<div style="font-size: 14px;" >
								<strong>出借人清单</strong>
								<hr width="100%" size=2 color="#D3D3D3" noshade>
							</div>
						</td>
					</tr>
					<tr><td>
						<table id="spouse" class="datatable" width="100%">	
							<jsp:include page="/car/sign/bringTogetherList.do?carAppId=${bean.appId}&state=noDisplay" />
						</table>
					</td></tr>
					<tr><td><div style="font-size: 14px;" ><strong>上传合同</strong><hr width="100%" size=2 color="#D3D3D3" noshade></div></td></tr>
					
					<tr>
						<td class="upbox">
							<span style="float: left;clear:both;padding: 3px;">M类资料：</span>
							<ul>
								<li>M（借款协议）</li>
							</ul>
						</td>
					</tr>
					
					<tr><td>
<%-- 						<c:if test="${loginId==toBeProcessed}"> --%>
								<span style="float:left ;"  id="yingxiangUP">附件：&nbsp; &nbsp;<input id="file" name="file" type="file" accept="application/x-zip-compressed" /></span>&nbsp; &nbsp;
								<input onclick="fileForm();" class="btn" value="上传" type="button" />
<%-- 						</c:if> --%>
					</td></tr>
					<tr><td style="padding-left: 35px;"  colspan="6"><SPAN style="color:red">上传提示：压缩文件不要大于10M 并且压缩包内单个图片不能大于1M</SPAN></td></tr>
					<tr>
						<td>
							<span style="float: right; padding-right: 20px;">
<%-- 								<c:if test="${loginId==toBeProcessed}"> --%>
									<input type="button" value="前端拒贷" class="btn" onclick="javascript:artOpenPage('前端拒贷','car/app/frontRefusePopup.do?id=${bean.id}');" title="拒贷"/>&nbsp;&nbsp;
	<!-- 								<input type="button" value="签约失败" class="btn" onclick="$.messager.confirm('消息','是否确认,签约失败！', function(ok){if (ok){updateFunction('fail')}});"/>&nbsp;&nbsp; -->
									<input type="button" value="提交" class="btn" onclick="updateFunction('submit')"/>&nbsp;&nbsp;
<%-- 								</c:if>	 --%>
								<input type="button" value="返回" class="btn" onclick="javascript:back()"/>&nbsp;&nbsp;
							</span>
						</td>
					</tr>
					<tr>
						<td id="imgDiv">
						<jsp:include page="/img/imgSlidePath.do?appId=${bean.appId}" />
						</td>
					</tr>
					<tr>
						<td>
							<div style="font-size: 14px;" >
								<strong>反欺诈处理</strong>
								<hr width="100%" size=2 color="#D3D3D3" noshade>
							</div>
						</td>
					</tr>
				 	<tr><td>
				 		<table id="carAntifraud">
							<tr>
								<td>
<%-- 									<c:if test="${loginId==toBeProcessed}"> --%>
										<input type="button" value="提交反欺诈" class="btn" onclick="submitAntifraud('antifraud')"/>
<%-- 									</c:if> --%>
								</td>
								<td>
<%--									<input id=submitInfo name="submitInfo" type="text"--%>
<%--										data-options="validType:['length[0,500]']"--%>
<%--										class="textbox easyui-validatebox" style="width:304px;"/>--%>
									<textarea id="submitInfo" name="submitInfo"  value="" class="textbox easyui-validatebox" data-options="validType:['length[0,500]']" 
										style="resize: none;width:600px;height:50px!important;"></textarea>
								</td>
							</tr>
<%--							<tr>--%>
<%--								<td><input type="button" name="relieveAntifraud" value="解除反欺诈"/></td>--%>
<%--								<td><input id="relieveAntifraud" name="relieveAntifraud" type="text" data-options="validType:['length[0,50]']" --%>
<%--									class="easyui-combobox" editable="false" />--%>
<%--								</td>--%>
<%--							</tr>--%>
<%--							<tr>--%>
<%--								<td></td>--%>
<%--								<td ><textarea id="carAntifraud" name="carAntifraud.remarks" style="resize: none;width:304px;height:60px;"></textarea></td>--%>
<%--							</tr>--%>
						</table>
					</td></tr>
				</table>	
			</form>
		</div>
	</div>
</div>
<form id="fileSmt" action="file/upload/zipupload.do"  enctype="multipart/form-data" style="display: none;" >
	<input type="hidden" id="fileappId" name="appId" value="${bean.appId}" />
</form>
<!-- <div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
	<img src="img/loading.gif" alt="加载中..." />
</div> -->
</body>
<script type="text/javascript" >
function zhezhao(){
		$("<div class=\"datagrid-mask\" id='chushiZhezhao'></div>").css({display:"block",width:"100%",height:$(window).height(),position:'fixed'}).appendTo("body");
		$("<div class=\"datagrid-mask-msg\" id='chushiZhezhaoMsg'></div>").html("正在加载，请稍候。。。").appendTo("body").css({display:"block",position:'fixed',left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
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
<script type="text/javascript">
function submitAntifraud(buttonType){
//弹出异步加载 遮罩
	openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "car/antifraud/save.do",
		data : params+"&buttonType="+buttonType,
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					window.history.go(-1);
					//location.replace(location.href);
					//return;
				});
            } else {				
    			$.messager.alert('消息', data.message);
				//按钮生效
				$(".btn").removeAttr("disabled");
            }
		},
		error : function() {
			//关闭遮罩，弹出消息框
			closeMask();
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
	});
}
//更新保存
function updateFunction(buttonType) {
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
	openMask();
	$('#bankCodeName').val($('#bankCode').combobox('getText'));
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "car/sign/save.do",
		data : params+"&buttonType="+buttonType,
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					// 保存的时候 当前页面刷新， 解决第一次保存APPID 与ID 为空的问题
					if(buttonType=="submit" || buttonType=="fail") {
						//跳转页面
						window.history.go(-1);
						//location.replace("<%=basePath%>car/app/update.do?id="+data.id);
					}else{
						//重新加载撮合列表
						window.location.reload();
					}
            	});
            } else {				
    			$.messager.alert('消息', data.message);
            }
		},
		error : function() {
			//关闭遮罩，弹出消息框
			closeMask();
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
		}
	});
}
//文件上传
function fileForm(){
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
		$.messager.alert('消息', "页面参数丢失，请刷新页面后从新操作或联系系统管理员。");
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

//返回
function back(){
	window.history.go(-1);
}

/* //打开Loading遮罩并修改样式
function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
} */

//原页面弹出框前端拒贷
function artOpenPage(_title,_url) {
	if($("body").find("#dialogDiv").length==0){
		 $('body').append($("<div id='dialogDiv' style='top:150px;'></div>"));
	}
	$('#dialogDiv').dialog({
	    title: _title,
	    height: 350,
	    width: 500,
	    closed: false,
	    cache: false,
	    href: encodeURI(_url),
	    modal: true,
	    resizable: true,
	    onLoad: function() {
			//填充select数据 拒件码01
		    var saleRefuseCode01url = "sys/datadictionary/listjason.do?keyName=saleRefuseCode01";
		    $("#saleRefuseCode1").combobox("clear");
		    $('#saleRefuseCode1').combobox({
		        url: saleRefuseCode01url,
		        valueField: 'keyProp',
		        textField: 'keyValue',
		        onChange: function(newValue, oldValue){
		            $('#saleRefuseCode2').combobox('clear');
		            var saleRefuseCode02url = "sys/datadictionary/listjason.do?keyName=saleRefuseCode02&parentKeyProp=" + encodeURI(newValue);
		            $('#saleRefuseCode2').combobox('reload',saleRefuseCode02url); 
		        }
		    });
		    //填充select数据 拒件码02
		    var saleRefuseCode1 = $('#saleRefuseCode1').combobox('getValue');
		    var saleRefuseCode02url = "sys/datadictionary/listjason.do?keyName=saleRefuseCode02&parentKeyProp=" + encodeURI(saleRefuseCode1);
		    $("#saleRefuseCode2").combobox("clear");
		    $('#saleRefuseCode2').combobox({
		        url: saleRefuseCode02url,
		        valueField: 'keyProp',
		        textField: 'keyValue'
		    }); 
	 	}
	});
	$('#dialogDiv').window('center');
}

//页面加载完动作
$(document).ready(function (){
	
	//填充select数据 借款用途1
    var useage1url = "sys/datadictionary/listjason.do?keyName=creditusage1";
	$("#useage1").combobox("clear");
	$('#useage1').combobox({
		url: useage1url,
		valueField: 'keyProp',
		textField: 'keyValue',
		onChange: function(newValue, oldValue){
            $('#useage2').combobox('clear');
            var useage2url = "sys/datadictionary/listjason.do?keyName=creditusage2&parentKeyProp=" + encodeURI(newValue);
            $('#useage2').combobox('reload',useage2url); 
      	}
	});
	//填充select数据 借款用途2
	var useage1 = $('#useage1').combobox('getValue');
	var useage2url = "sys/datadictionary/listjason.do?keyName=creditusage2&parentKeyProp=" + encodeURI(useage1);
		$("#useage2").combobox("clear");
		$('#useage2').combobox({
			url: useage2url,
			valueField: 'keyProp',
			textField: 'keyValue'
		});

	//填充select数据 借款期限
	$("#period").combobox("clear");
	$('#period').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:[{"keyProp":"1","keyValue":"12"},{"keyProp":"2","keyValue":"18"},{"keyProp":"3","keyValue":"24"},{"keyProp":"4","keyValue":"36"}]
	});

	//填充select数据 渠道
	var channelurl="channeltotal/listjason.do";
	$("#belongChannel").combobox("clear");
	$('#belongChannel').combobox({
		url:channelurl,
		valueField:'channelCode', 
		textField:'channelName',
		onChange: function(newValue, oldValue){             
			$('#product').combobox('clear');
			var producturl = "product/hedao/listjason.do?type=4&belongChannel=" + encodeURI(newValue);
			$('#product').combobox('reload',producturl); 
		}
	});
	//填充select数据 产品
	var belongChannel = $('#belongChannel').combobox('getValue');
	var producturl = "product/hedao/listjason.do?type=4&belongChannel=" + encodeURI(belongChannel);
	$("#product").combobox("clear");
	$('#product').combobox({
		url:producturl,
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
		
	//填充select数据 签约地点省份
    var provinceurl = "sys/datadictionary/listjason.do?keyName=province";
		$("#signProvince").combobox("clear");
		$('#signProvince').combobox({
			url: provinceurl,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
            $('#signCity').combobox('clear');
            $('#signCounty').combobox('clear');
            var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
            $('#signCity').combobox('reload',cityurl); 
        }
	});
    //填充select数据 签约地点地市
    var province = $('#signProvince').combobox('getValue');
		var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(province);
		$("#signCity").combobox("clear");
		$('#signCity').combobox({
			url: cityurl,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
            $('#signCounty').combobox('clear');
            var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
            $('#signCounty').combobox('reload',countyurl); 
        }
	});
    //填充select数据 签约地点区县
    var city = $('#signCity').combobox('getValue');
		var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(city);
		$("#signCounty").combobox("clear");
		$('#signCounty').combobox({url: countyurl, valueField: 'keyProp',textField: 'keyValue'});
	
});
</script>
</html>


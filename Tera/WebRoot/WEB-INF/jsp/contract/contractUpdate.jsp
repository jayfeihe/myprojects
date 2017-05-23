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
<title>合同修改</title>
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
		<p class="title"><a href="javascript:void(0);">合同修改</a></p>
		<div class="content">
			<form id="updateForm" name="updateForm" action="contract/save.do" AUTOCOMPLETE="off">
				<input type="hidden" name="id" value="${contract.id}" />
				<table>
					<tr>
						<td>
							<div style="font-size: 14px;" >
								<strong>申请信息</strong>
								<hr width="100%" size=2 color="#D3D3D3" noshade>
							</div>
						</td>
					</tr>
					<tr><td>
						<table>
							<tr>
								<td>姓名：</td>
								<td>
									<input type="text" class="textbox easyui-validatebox"
									disabled="disabled" value="${creditApp.name}" />
								</td>
								<td>身份证：</td>
								<td>
									<input type="text" class="textbox easyui-validatebox"
									disabled="disabled" value="${creditApp.idNo}" />
								</td>
							</tr>
							<tr>
								<td>申请金额：</td>
								<td>
									<input type="text" editable="false" disabled="disabled" class="textbox easyui-numberbox" data-options="required:true,min:0,precision:2"
									 value="${creditApp.amount}" />
								</td>
								<td>申请产品：</td>
								<td>
									<input type="text" class="textbox easyui-validatebox"
									disabled="disabled" value="${creditApp.product}" />
								</td>
							</tr>
						</table>
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
								<td>合同金额:</td>
								<td>
									<input type="text" editable="false" disabled="disabled" class="textbox easyui-numberbox" data-options="required:true,min:0,precision:2"
									 value="${contract.loanAmount}" />
								</td>
								<td>客户到手金额:</td>
								<td>
									<input type="text" editable="false" disabled="disabled" class="textbox easyui-numberbox" data-options="required:true,min:0,precision:2"
									 value="${decision.amount}" />
								</td>
								<td>实际产品:</td>
								<td>
									<input type="text" class="textbox easyui-validatebox"
									disabled="disabled" value="${contract.loanProduct}" />
								</td>
							</tr>
							<tr>
								<td>开户行信息:</td>
								<td colspan="5">
									<input id="bankProvince" name="bankProvince" type="text" class="textbox easyui-combobox" data-options="required:true" value="${contract.bankProvince}" editable="false"/>省 &nbsp; &nbsp;
									<input id="bankCity" name="bankCity" type="text" class="textbox easyui-combobox" data-options="required:true" value="${contract.bankCity}" editable="false"/>市 &nbsp; &nbsp;
									<input id="bankCode" name="bankCode" type="text" class="textbox easyui-combobox" data-options="required:true" value="${contract.bankCode}" editable="false"/>银行 &nbsp; &nbsp;
									<input id="bankCodeName" name="bankName" type="hidden" value="${contract.bankName}"/>
									<input name="bankBranch" type="text" class="textbox easyui-validatebox" data-options="required:true,validType:['length[0,50]']" value="${contract.bankBranch}" />（支行全称）
								</td>
							</tr>	
							<tr>	
								<td>银行账户姓名:</td>
								<td>
									<input type="text" class="textbox easyui-validatebox"
									disabled="disabled" value="${contract.bankAccountName}" />
								</td>
								<td>银行卡号:</td>
								<td><input name="bankAccount" type="text" class="textbox easyui-validatebox" data-options="required:true,validType:['length[0,100]']" value="${contract.id!=0?contract.bankAccount:bean.account}" style="  width: 180px;" /></td>
								<td>POS关联手机号:</td>
								<td><input name="bankMobile" type="text" class="textbox easyui-validatebox" data-options="required:true,validType:['mobile']" value="${contract.bankMobile}" /></td>
							</tr>
						</table>
					</td></tr>
					<%-- <tr>
						<td>
							<div style="font-size: 14px;" >
								<strong>出借人清单</strong>
								<hr width="100%" size=2 color="#D3D3D3" noshade>
							</div>
						</td>
					</tr>
					<tr><td>
						<table id="spouse" class="datatable" width="100%">	
							<jsp:include page="/credit/sign/bringTogetherList.do?creditAppId=${bean.appId}&state=noDisplay" />
						</table>
					</td></tr> --%>
					<tr>
						<td>
							<span style="float: right; padding-right: 20px;">
								<input type="button" value="保存" class="btn" onclick="updateFunction('submit')"/>&nbsp;&nbsp;
								<input type="button" value="返回" class="btn" onclick="javascript:back()"/>&nbsp;&nbsp;
							</span>
						</td>
					</tr>
					<tr>
						<td id="imgDiv">
						<jsp:include page="/img/imgSlidePath.do?appId=${bean.appId}" />
						</td>
					</tr>
				</table>	
			</form>
		</div>
	</div>
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
<script type="text/javascript">

//更新保存
function updateFunction() {
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	//弹出异步加载 遮罩
	zhezhao();
	$('#bankCodeName').val($('#bankCode').combobox('getText'));
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "contract/save.do",
		data : params,
		success : function(data) {
			//关闭遮罩，弹出消息框
			rmZhezhao();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					window.history.go(-1);
            	});
            } else {				
    			$.messager.alert('消息', data.message);
            }
		},
		error : function() {
			//关闭遮罩，弹出消息框
			rmZhezhao();
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
		}
	});
}

//返回
function back(){
	window.history.go(-1);
}

//页面加载完动作
$(document).ready(function (){

/* 	//填充select数据 产品
	var tsurl="product/hedao/listjason.do?type=3";
	$("#product").combobox("clear");
	$('#product').combobox({url:tsurl,
		valueField:'name', 
		textField:'name',
		onChange: function(product){
   	}}); */
	
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
</script>




</html>


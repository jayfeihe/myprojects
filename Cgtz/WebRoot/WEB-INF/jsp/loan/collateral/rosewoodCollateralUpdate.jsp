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
<title>质押、抵押物信息更新</title>
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
		<p class="title"><a href="javascript:void(0);">抵押物更新</a></p>
		<div class="content">
			<form id="updateRosewoodForm" >
				<input id="id" name="id" type="hidden" size="35" value="${ bean.id}" />
				<input id="loanId" name="loanId" type="hidden" size="35" value="${loanId}" />
				<input id="type" name="type" type="hidden" size="35" value="${type}" />
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>红木基本信息</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>品种:</td>
						<td><input id="var" name="var" type="text" data-options="required:true,validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.var}"/></td>
						<td>规格:</td>
						<td><input id="size" name="size" type="text" data-options="required:true,validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.size}"/></td>
					</tr>
					<tr>
						<td>说明:</td>
						<td colspan="6">
						<textarea name="remark" class="textbox easyui-validatebox" 
								data-options="validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;">${bean.remark}</textarea></td>
					</tr>
				</table>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>红木评估</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>评估金额:</td>
						<td><input id="evalPrice" name="evalPrice" type="text" data-options="required:true,min:0,precision:2" class="textbox easyui-numberbox" value="${bean.evalPrice}"/>元</td>
						<td>评估者:</td>
						<td><input id="evalName" name="evalName" type="text" data-options="required:true,validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.evalName}"/></td>
					</tr>
					<tr>
						<td>评估说明:</td>
						<td colspan="6"><textarea name="evalRemark" class="textbox easyui-validatebox" 
								data-options="validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;">${bean.evalRemark}</textarea></td>
					</tr>
				</table>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>资产管理</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>担保物权是否设定:</td>
						<td>
							<input name="isSet" type="radio" value="0" <c:if test="${bean.isSet eq '0' }">checked='checked'</c:if> checked='checked'/>否
							<input name="isSet" type="radio" value="1" <c:if test="${bean.isSet eq '1' }">checked='checked'</c:if>/>是
						</td>
					</tr>
					<tr>
						<td>所在仓库:</td>
						<td>
							<%-- <input id="warehousePrvn" name="warehousePrvn" type="text" 
								data-options="required:true,editable:false" class="textbox easyui-combobox" value="${bean.warehousePrvn}"/>省
							<input id="warehouseCity" name="warehouseCity" type="text" 
								data-options="required:true,editable:false" class="textbox easyui-combobox" value="${bean.warehousePrvn}"/>市 --%>
							<input id="warehouseId" name="warehouseId" type="text" 
									data-options="required:true,editable:false,panelHeight:'auto',
												url:'warehouse/listWarehouse.do',
												textField:'name',
												valueField:'id'," 
									class="textbox easyui-combobox" value="${bean.warehouseId}"/>仓库
							<input id="warehouseName" name="warehouseName" type="hidden"/></td>
					</tr>
					<tr>
						<td>资产说明:</td>
						<td colspan="6"><textarea name="assetRemark" class="textbox easyui-validatebox" 
								data-options="validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;">${bean.assetRemark}</textarea></td>
					</tr>
					<tr>
						<td>保管物品说明:</td>
						<td colspan="6"><textarea name="reserveDes" class="textbox easyui-validatebox" 
								data-options="validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;">${bean.reserveDes}</textarea></td>
					</tr>
				</table>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>操作</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>
							<input type="button" value="保存" class="btn" onclick="updateFunction()"/>
							<!-- <input type="button" value="返回" class="btn" onclick="javascript:back()"/> -->
						</td>
						<td></td>
					</tr>
				</table>
			</form>
			<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>影像</strong></div><hr color="#D3D3D3"/>
			<jsp:include page="/files/load.do">
				<jsp:param value="${loanId }" name="loId"/>
				<jsp:param value="filesce4" name="sec"/>
				<jsp:param value="${ bean.id}" name="bizKey"/>
				<jsp:param value="0" name="opt"/>
			</jsp:include>
		</div>
	</div>
</div>

</body>
<script type="text/javascript">
//开启遮罩
function openMask(){
	$("<div class=\"datagrid-mask\" id='mask_id'></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
	$("<div class=\"datagrid-mask-msg\" id='mask_msg_id'></div>").html("请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
}
//关闭遮罩
function closeMask(){
	$("#mask_id").remove();
	$("#mask_msg_id").remove();
}
</script>

<script type="text/javascript">
//更新保存
function updateFunction() {
	//去掉 input 输入的 前后空格
	$('#updateRosewoodForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	//验证表单验证是否通过
	if(false == $('#updateRosewoodForm').form('validate') ){
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}
	//弹出异步加载 遮罩
	openMask();
	
	$("#warehouseName").val($("#warehouseId").combobox('getText'));
	
	var params = $('#updateRosewoodForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "loan/collateral/save.do",
		data : encodeURI(params),
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					//window.parent.refreshTab("appUpdateTabs");
					var url = "loan/collateral/update.do?id=" + data.id +"&loanId="+data.loanId+"&type="+data.type;
					window.parent.refreshCollateralTab('collQueryTabs',url);
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
			$.messager.confirm('消息', '数据加载失败,请联系系统管理员！');
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
	});
}

//返回
function back(){
	window.history.go(-1);
}

//页面加载完动作
$(document).ready(function (){
	/* // 仓库所在地-省
	var provinceUrl = "sys/datadictionary/listjason.do?keyName=province";
	$("#warehousePrvn").combobox("clear");
	$('#warehousePrvn').combobox({
		url: provinceUrl,
		valueField: 'keyProp',
		textField: 'keyValue',
		onChange: function(newValue, oldValue){
	        $('#warehouseCity').combobox('clear');
	        var cityUrl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
	        $('#warehouseCity').combobox('reload',cityUrl); 
    	},
    	loadFilter:function(data){
			var comVal = $(this).combobox("getValue");
			if(comVal == '' || comVal == null) {
		   		var opts = $(this).combobox('options');
		   		var emptyRow = {};
				emptyRow[opts.valueField] = '';
				emptyRow[opts.textField] = '请选择';
				data.unshift(emptyRow);
				$(this).combobox("setValue",'');
			}
			return data;
		}
	});
	
	// 仓库所在地-市
	var homePrvn = $('#warehousePrvn').combobox('getValue');
	var cityUrl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(homePrvn);
	$("#warehouseCity").combobox("clear");
	$('#warehouseCity').combobox({
		url: cityUrl,
		valueField: 'keyProp',
		textField: 'keyValue'
	}); */
});

</script>
</html>


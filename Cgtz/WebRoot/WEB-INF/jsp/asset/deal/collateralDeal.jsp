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
<title>资产处置</title>
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
		<p class="title"><a href="javascript:void(0);">资产处置</a></p>	
		<div class="content">
			<form id="assetDealForm" action="asset/deal/save.do" method="post">
				<input id="id" name="id" type="hidden" value="${collateralId}"/>
				<table>
<tr>
<td>处置金额:</td>
<td><input id="sellAmt" name="sellAmt" type="text" required="true" data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${collateral.sellAmt }" <c:if test="${collateral.state eq '3'}">readOnly="readOnly"</c:if>/>元</td>
</tr>
<tr>
<td>渠道:</td>
<td><input id="sellWay" name="sellWay" type="text" required="true"  class="textbox easyui-validatebox" value="${collateral.sellWay }" <c:if test="${collateral.state eq '3'}">readOnly="readOnly"</c:if> data-options="validType:['length[0,30]']"/></td>
</tr>
<tr>
<td>负责部门:</td>
<td><input id="sellOrg" name="sellOrg" type="text" required="true"  class="textbox easyui-validatebox" value="${collateral.sellOrg }" <c:if test="${collateral.state eq '3'}">readOnly="readOnly"</c:if> data-options="validType:['length[0,30]']"/></td>
</tr>
<tr>
<td>处置说明:</td>
<td><textarea id="sellRemark" name="sellRemark" type="text" required="true" data-options="validType:['length[0,500]']" class="textbox easyui-validatebox" style="resize:none;width:500px;height:50px!important;" <c:if test="${collateral.state eq '3'}">readOnly="readOnly"</c:if>>${collateral.sellRemark }</textarea></td>
</tr>
					<c:if test="${collateral.state ne '3'}"><tr><td ></td>
						<td align="right">
							<input type="button" value="提交处置" class="btn" onclick="updateFunction(${collateralId})"/>		
						</td>
						<td></td>
					</tr></c:if>
				</table>	
			</form>
			<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>影像</strong></div><hr color="#D3D3D3"/>
			<jsp:include page="/files/load.do">
				<jsp:param value="${loanId}" name="loId"/>
				<jsp:param value="filesce11" name="sec"/>
				<jsp:param value="${collateral.id}" name="bizKey"/>
				<jsp:param value="0" name="opt"/>
			</jsp:include>
	
		</div>
		
		<div id="queryValueChangeContent" >
		<%--
		查询列表
		 --%>
		</div>
	</div>
</div>
</body>
<script type="text/javascript">
//添加选项卡
function addTab(title, url){
    if ($('#verifyTable').tabs('exists', title)){
        $('#verifyTable').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#verifyTable').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeTab(){
	var tab = $('#verifyTable').tabs('getSelected');
	var tabIndex=$('#verifyTable').tabs('getTabIndex',tab);
	$('#verifyTable').tabs('close',tabIndex);
	submitForm("queryValueChangeForm");//解决Tab提交关闭列表页刷新的问题
}

//////////////       ////////////////
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
function submitForm(fromId) {
	//去掉 input 输入的 前后空格
	$('#queryValueChangeForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	var formAction = $('#' + fromId).attr("action");
	var targetDiv = $('#' + fromId).attr("target");
	var params = $('#' + fromId).serialize();
	//弹出异步加载 遮罩
	openMask();
	$.ajax( {
		type : "POST",
		url  : formAction,
		data : params + "&targetDiv=" + targetDiv,
		dataType : "html",
		success : function(data) {
			closeMask();
			$('#' + targetDiv).html(data);
		},
		error : function() {
			closeMask();
			$.messager.confirm('消息', '数据加载失败,请联系系统管理员！');
		}
	});
}
//更新保存
function updateFunction(collateralId) {
	//去掉 input 输入的 前后空格
	$('#assetDealForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	//验证表单验证是否通过
	if(false == $('#assetDealForm').form('validate') ){
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}
	//弹出异步加载 遮罩
	openMask();
	var params = $('#assetDealForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "asset/deal/save.do",
		data : encodeURI(params),
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
	                
					var url = '<%=basePath%>' + 'asset/deal/update.do?collateralId='+collateralId;
					//window.parent.refreshAssetManageTabs("assetManageTabs",url);
					location.replace(url);
					window.parent.submitForm('queryAssetForm');
					/* window.history.go(-1); */
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
//页面加载完动作
$(document).ready(function() {
//填充select数据样例
/*<%--
	var tsurl="sys/datadictionary/listjason.do?keyName=repaymethod";
	$("#repayMethod").combobox("clear");
	$('#repayMethod').combobox({
		url:tsurl,
		valueField:'keyProp',
		textField:'keyValue',
		//添加空白行
		loadFilter:function(data){
	   		var opts = $(this).combobox('options');
	   		var emptyRow = {};
			emptyRow[opts.valueField] = '&nbsp;';
			emptyRow[opts.textField] = '...';
			data.unshift(emptyRow);
			return data;
		}
	});
--%>*/

	/* submitForm("queryValueChangeForm"); */
});
</script>

</html>


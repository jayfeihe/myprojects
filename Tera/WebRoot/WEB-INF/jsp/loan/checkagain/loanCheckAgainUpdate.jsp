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
<title>借款申请审批表更新</title>
	<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
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
		<p class="title"><a href="javascript:void(0);">个人申请签约复核</a></p>
		<div class="content">
			<form id="updateForm" >
				<table>
					<tbody>
					<input id="appId" name="appId" type="hidden" size="35" value="${appId}" />
					<tr>
						<td colspan="7" style="font-weight:bold;font-style:normal;">审批通过信息 </td>
					</tr>
					
					<tr>
						<td> &nbsp; &nbsp; &nbsp;审批金额:</td>
						<td><input id="approvalAmount" readonly="readonly" style="width:80px;" name="approvalAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.approvalAmount}"/></td>
						<td>审批期限:</td>
						<td><input id="approvalPeriod" readonly="readonly" style="width:80px;" name="approvalPeriod" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.approvalPeriod}"/></td>
						<td>审批服务费率:</td>
						<td><input id="approvalServiceRate" readonly="readonly" style="width:80px;" name="approvalServiceRate" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.approvalServiceRate}"/></td>
					</tr>
					<tr>
						<td> &nbsp; &nbsp; &nbsp;需要第三方购销合同:</td>
						<td>
							<input id="thirdPartyContract" disabled="disabled" name="thirdPartyContract" type="radio" value="1" <c:if test="${bean.thirdPartyContract=='1'}">checked='checked'</c:if> />是 &nbsp; &nbsp;	
							<input id="thirdPartyContract" disabled="disabled"  name="thirdPartyContract" type="radio" value='0' <c:if test="${bean.thirdPartyContract=='0'}">checked='checked'</c:if> />否
						</td>
						<td>担保条件:</td>
						<td colspan="4"><textarea id="guaranteeCondition" readonly="readonly" name="guaranteeCondition" 
						data-options="validType:['length[0,500]']" style="width: 100%;height:100px !important;"
						 class="textbox easyui-validatebox">${bean.guaranteeCondition}</textarea>
						</td>
					</tr>
					</tbody>
					</table>
					<table width="100%">
					<tr>
						<td id="imgDiv">
						<jsp:include page="/img/imgSlidePath.do" />
						</td>
					</tr>
				</table>
					<table>
					<tbody>
					<tr>
					<input name="subbpm" id="subbpm" type="hidden" value=""/>
					<input name="auditResult" id="auditResult" type="hidden" value=""/>
					<input id="auditText" name="auditText" type="hidden" value=""/>
						<td>
							<input type="button" value="通过" class="btn" onclick="bmmupdateFunction(1)"/>
							
						</td>
					</tr>
					<tr>
						<td>
							<input type="button" value="退回" class="btn" onclick="bmmupdateFunction(0)"/>
						</td>
						<td>
							<select id="denyToPerson" name="denyToRole"  class="easyui-combobox">
								<option value="0" <c:if test="${bean.entLoanDvalue=='0'}">selected="selected"</c:if> >风险专员</option>
								<!--<option value="1" <c:if test="${bean.entLoanDvalue=='1'}">selected="selected"</c:if> >营业部经理</option>-->
							</select>
						</td>
						<td>
							不通过原因：<input id="auditText_btg" type="text" class="textbox easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<!--<td>
							<input type="button" value="拒件" class="btn" onclick="bmmupdateFunction(2)"/>
						</td>
					--></tr>
					<!--<tr>
						<td>
							<input type="button" value="保存" class="btn" onclick="updateFunction()"/>
							<input type="button" value="返回" class="btn" onclick="javascript:back()"/>
						</td>
					</tr>
					--><tr>
						<td>
						</td>
					</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>

<!-- <div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
	<img src="img/loading.gif" alt="加载中..." />
</div> -->

</body>

<script type="text/javascript">

function bmmupdateFunction(val) {
	$('#subbpm').val('trueSubbpm');
	$('#auditResult').val(val);
	if(val==0){
		var btgyy=$('#auditText_btg').val();
		if(btgyy==null||btgyy==""){
			
			$.messager.confirm('消息', "请填写不通过信息。");
			$('#subbpm').val('');
			return;
		}
		$('#auditText').val(btgyy);
	}else if(val==1){
		/*var btgyy=$('#matchType').val();
		if(btgyy==null||btgyy==""){
			
			$.messager.confirm('消息', "撮合类型为必选项。");
			$('#subbpm').val('');
			return;
		}*/
	}else if(val==2){
		var btgyy=$('#auditText_jujian').val();
		if(btgyy==null||btgyy==""){
			
			$.messager.confirm('消息', "请填写退回信息。");
			$('#subbpm').val('');
			return;
		}
		$('#auditText').val(btgyy);
	}
	updateFunction();
	$('#subbpm').val('');
}

//更新保存
function updateFunction() {
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		$(this).val($.trim($(this).val()));
	});
	//验证表单验证是否通过
	if(false == $('#updateForm').form('validate') ){
		return;
	}
	//弹出异步加载 遮罩
	openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "loan/checkagain/save.do",
		data : encodeURI(params),
		success : function(data) {
			if ("true"==data.success) {
				//关闭遮罩，弹出消息框
				closeMask();
				
				$.messager.alert('消息', data.message,"info", function(){
//	                   	var url= "<%=basePath%>" + "loan/special/query.do";
//						window.location=url;
						window.history.go(-1);
            	});
            } else {
            	closeMask();
				
				$.messager.alert('消息', data.message);
				//按钮生效
				$(".btn").removeAttr("disabled");
            }
		},
		error : function() {
			closeMask();
			
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
	});
}

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


//页面加载完动作
$(document).ready(function (){
	//var tsurl="sys/datadictionary/listjason.do?keyName=matchtype";
	$("#matchType").combobox("clear");
	$('#matchType').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.matchtype
	});
});

</script>
</html>


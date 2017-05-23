<%@page import="com.tera.audit.common.constant.CommonConstant"%>
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
<title>法务初审</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
</head>
<body>
	<div class="easyui-tabs" id="lawFirstUpdateTabs" style="width: 100%;" data-options="fit:true">
		<div title="审核" style="width: 100%;padding:2px">
			<form id="lawUpdateForm">
				<input type="hidden" name="contract.loanId" value="${loanBase.loanId}"/>
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>决策</strong></div><hr color="#D3D3D3"/>
				<input type="hidden" name="loanId" value="${loanBase.loanId}"/>
				<table>
					<tr>
						<td>决策:</td>
						<td>
							<input type="text" id="decision" name="decision" class="textbox easyui-combobox" 
								data-options="required:true,editable:false,panelHeight:'auto'" />
							<span id="insideArea" style="display: none;" >
								法务内勤人员：<input type="text" id="lawInsideUser" name="lawInsideUser" class="textbox easyui-combobox"
									data-options="editable:false,panelHeight:'auto'"/>
							</span>
							<span id="nodeArea" style="display: none;" >
								<input type="text" id="node" name="node" class="textbox easyui-combobox" value="<%=CommonConstant.PROCESS_A %>"
									data-options="editable:false,panelHeight:'auto'"/>
							</span>
						</td>
					</tr>
					<tr>
						<td>说明:</td>
						<td colspan="6">
							<textarea name="remark" class="textbox easyui-validatebox" 
								data-options="required:true,validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;"></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<!-- <input type="button" value="保存" class="btn" onclick="updateFunction('save')"/>&nbsp;&nbsp; -->
							<input type="button" value="提交" class="btn" onclick="updateFunction('submit')"/>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<c:if test="${loanBase.isRenew eq '0' }">
			<div title="诉讼审核" data-options="href:'${basePath}law/loanlaw/query.do?loanId=${loanBase.loanId}'" style="width: 100%;padding:2px"></div>
		</c:if>
		<c:if test="${loanBase.isRenew eq '1' }">
			<div title="续贷历史" data-options="href:'${basePath}loan/renew/readQuery.do?origLoanId=${loanBase.origLoanId}'" style="width: 100%;padding:10px"></div>
		</c:if>
		<div title="申请信息" data-options="href:'${basePath}loan/read.do?id=${loanBase.id}'" style="width: 100%;padding:10px"></div>
		<c:if test="${loanBase.isRenew eq '1' }">
			<div title="原申请信息" data-options="href:'${basePath}loan/origRead.do?id=${origLoanBase.id}'" style="width: 100%"></div>
		</c:if>
		<div title="申请影像资料" data-options="href:'${basePath}files/read2.do?loanId=${loanBase.loanId}&sec=${sec }&bizKey=${loanBase.loanId}'" style="padding:10px"></div>
		<c:if test="${isTgth eq '1' }">
			<div title="共同借款信息" data-options="href:'${basePath}loan/common/query.do?loanId=${origLoanBase.loanId}&opt=read'" style="width: 100%;padding:2px""></div>
		</c:if>
		<div title="质/抵押物" data-options="href:'${basePath}loan/collateral/query.do?loanId=${loanBase.loanId}&origLoanId=${origLoanBase.loanId }&opt=read'" style="width: 100%;padding:2px""></div>
		<div title="担保情况" data-options="href:'${basePath}loanguar/query.do?loanId=${loanBase.loanId}&origLoanId=${origLoanBase.loanId }&opt=read'" style="width: 100%;padding:2px""></div>
		<!--  <div title="诉讼情况" data-options="href:'${basePath}loan/law/query.do?loanId=${loanBase.loanId}&origLoanId=${origLoanBase.loanId }&opt=read'" style="width: 100%;padding:2px""></div>-->
		<div title="流程报告" data-options="href:'${basePath}bpm/getBpmLogs.do?bizKey=${loanBase.loanId}'" style="width: 100%;padding:10px"></div>
	</div>

<script type="text/javascript">
//更新保存
function updateFunction(buttonType) {
	/* <c:if test="${loanBase.isRenew eq '0' }">
		var dec = $("#decision").combobox('getValue');
		if('1' == dec) {
			if($(".tab_show_loanlaw").length == 0){
				$.messager.confirm('消息', "请进行诉讼复核！");
				return;
			}
		}
	</c:if> */
	
	if('submit' == buttonType){
		//验证表单验证是否通过
		if(false == $('#lawUpdateForm').form('validate')){
			$.messager.confirm('消息', "页面有必输字段，但未填值！");
			return;
		}
	}
	
	//去掉 input 输入的 前后空格
	$('#lawUpdateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	
	//弹出异步加载 遮罩
	openMask();
	
	var params = $('#lawUpdateForm').serialize();
	
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "law/first/save.do",
		data : params + "&buttonType="+buttonType,
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					if ('save' == buttonType) {
						var url = '<%=basePath%>law/first/update.do?id='+data.id;
						window.parent.refreshTab('lawQueryTabs',url);
					}else {
						window.parent.removeTab();
					}
            	});
            } else {				
    			$.messager.alert('消息', data.message);
            }
			//按钮生效
			$(".btn").removeAttr("disabled");
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
$(document).ready(function (){
	var type = '${nodeType}';
	
	$("#decision").combobox({
		textField:'keyValue',
		valueField:'keyProp',
		data:dataDictJson.decision,
		onChange:function(nVal,oVal){
			if("0" == nVal) {
				$("#nodeArea").show();
				var url = "common/nodeorder/listNode.do?name=" + "<%=CommonConstant.PROCESS_G1%>" + "&type="+type;
				$('#node').combobox('clear');
				$("#node").combobox('reload',url);
				var node = "<%=CommonConstant.PROCESS_A %>";
				$("#node").combobox("setValue",node);
				
				$("#insideArea").hide();
				$("#lawInsideUser").combobox("setValue","");
			} else if("1" == nVal){
				$("#insideArea").show();
				var url=encodeURI("sys/user/listUserByOrgAndRole.do?orgId=${loanBase.org}&roleNames=<%=CommonConstant.ROLE_LAW_INSIDE %>");
				$("#lawInsideUser").combobox("clear");
				$("#node").combobox('reload',url);
				
				$("#nodeArea").hide();
				$("#node").combobox("setValue","");
			}
		}
	});
	
	var tsurl=encodeURI("sys/user/listUserByOrgAndRole.do?orgId=${loanBase.org}&roleNames=<%=CommonConstant.ROLE_LAW_INSIDE %>");
	$("#lawInsideUser").combobox("clear");
	$('#lawInsideUser').combobox({
		url:tsurl,
		valueField:'loginId',
		textField:'name'
	});
	
	$("#node").combobox({
		url:'common/nodeorder/listNode.do?name=<%=CommonConstant.PROCESS_G1%>&type='+type,
		textField:'keyValue',
		valueField:'keyProp'
	});
}); 
</script>

<script type="text/javascript" >

window.parent.openMask();
$(window).load(function (){
	window.parent.closeMask();
});

function refreshTab(tabs) {
	var currTab =  self.$('#'+tabs).tabs('getSelected'); //获得当前tab
    var url = $(currTab.panel('options').content).attr('src');
    var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
    self.$('#'+tabs).tabs('update', {
      tab : currTab,
      options : {
       content : content,
       //closable:true,  
       fit:true,  
       selected:true 
      }
     });
}
</script>
</body>
</html>


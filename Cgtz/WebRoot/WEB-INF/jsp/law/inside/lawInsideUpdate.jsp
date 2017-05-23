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
				<input type="hidden" name="contract.id" value="${bean.id}"/>
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>合同管理</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>合同号:</td>
						<td><input id="contractId" name="contract.contractId"  value="${bean.contractId }"
							type="text" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" /></td>
						<td colspan="3"><input type="button" value="保存合同" class="btn" onclick="updateFunction('save')"/>&nbsp;&nbsp;</td>
					</tr>
				</table>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>决策</strong></div><hr color="#D3D3D3"/>
				<input type="hidden" name="formBean.loanId" value="${loanBase.loanId}"/>
				<table>
					<tr>
						<td>决策:</td>
						<td>
							<input type="text" id="decision" name="formBean.decision" class="textbox easyui-combobox" 
								data-options="required:true,editable:false,panelHeight:'auto'" />
							<span id="nodeArea" style="visibility: hidden;" >
								<input type="text" id="node" name="formBean.node" class="textbox easyui-combobox" value="<%=CommonConstant.PROCESS_A %>"
									data-options="editable:false,panelHeight:'auto'"/>
							</span>
						</td>
					</tr>
					<tr>
						<td>说明:</td>
						<td colspan="6">
							<textarea name="formBean.remark" class="textbox easyui-validatebox" 
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
			<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>影像</strong></div><hr color="#D3D3D3"/>
			<jsp:include page="/files/load.do"  flush="true">
				<jsp:param value="${loanBase.loanId}" name="loId"/>
				<jsp:param value="filesce9" name="sec"/>
				<jsp:param value="${bean.contractId}" name="bizKey"/>
				<jsp:param value="0" name="opt"/>
			</jsp:include>
		</div>
		
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
		<div title="诉讼情况" data-options="href:'${basePath}loan/law/query.do?loanId=${loanBase.loanId}&origLoanId=${origLoanBase.loanId }&opt=read'" style="width: 100%;padding:2px""></div>
		<div title="流程报告" data-options="href:'${basePath}bpm/getBpmLogs.do?bizKey=${loanBase.loanId}'" style="width: 100%;padding:10px"></div>
	</div>

<script type="text/javascript">
//更新保存
function updateFunction(buttonType) {
	if('submit' == buttonType) {
		// 决策通过-校验合同是否填写
		var dec = $("#decision").combobox('getValue');
		if(dec == '1') {
			var contId = $("input[name='contract.id']").val();
			if(null==contId || ''==contId) {
				$.messager.alert('消息','请先保存合同！');
				return;
			}
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
		url : "<%=basePath%>" + "law/inside/save.do",
		data : params + "&buttonType="+buttonType,
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					if ('save' == buttonType) {
						var url = '<%=basePath%>law/inside/update.do?id='+data.id;
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
				$("#nodeArea").css("visibility","visible");
				var url = "common/nodeorder/listNode.do?name=" + "<%=CommonConstant.PROCESS_G2%>" + "&type="+type;
				$('#node').combobox('clear');
				$("#node").combobox('reload',url);
				var node = "<%=CommonConstant.PROCESS_A %>";
				$("#node").combobox("setValue",node);
			} else {
				$("#nodeArea").css("visibility","hidden");
				$("#node").combobox("setValue","");
			}
		}
	});
	
	$("#node").combobox({
		url:'common/nodeorder/listNode.do?name=<%=CommonConstant.PROCESS_G2%>&type='+type,
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


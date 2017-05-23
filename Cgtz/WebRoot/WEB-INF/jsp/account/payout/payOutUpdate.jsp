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
<title>贷后垫付</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
</head>
<body>
	<div class="easyui-tabs" id="preLoanUpdateTabs" style="width: 100%;" data-options="fit:true">
		<div title="贷后垫付处理" style="width: 100%;padding:2px">
			<form id="payOutUpdateForm">
				<input type="hidden" name="loanId" value="${loanBase.loanId}"/>
				<input type="hidden" name="contractId" value="${contract.contractId}"/>
				<input type="hidden" name="getLoanWay" value="${contract.getLoanWay}"/>
				<input type="hidden" name="lendUserId" value="${contract.lendUserId}"/>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>贷前收息</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>垫付期数:</td>
						<td><input type="text" class="textbox easyui-numberbox" id="payOutNum" name="payOutNum" data-options="required:true,min:0,precision:0"/>期/${contract.num}</td>
						<td>垫付金额:</td>
						<td>
							<input id="amt" type="text" class="textbox easyui-numberbox" name="amt"
										data-options="required:true,min:0,precision:2" value=""/>元
						</td>
						<td>凭证号:</td>
						<td colspan="3">
							<input id="proof" type="text" class="textbox easyui-validatebox" name="proof" style="width: 200px;"
										data-options="required:true,validType:['length[0,100]']" value=""/><span style="color: red;margin-left: 10px">格式：账号+凭证号</span></td>
					</tr>
					<tr>
						<td>说明:</td>
						<td colspan="6">
							<textarea name="remark" class="textbox easyui-validatebox"
								data-options="required:true,validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;">${bean.remark }</textarea>
						</td>
					</tr>
					<tr>
						<td>
							<input type="button" value="垫付" class="btn" onclick="updatePayOutFunction()"/>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div title="申请信息" data-options="href:'${basePath}loan/read.do?id=${loanBase.id}'" style="width: 100%;padding:10px"></div>
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
function updatePayOutFunction() {
	//验证表单验证是否通过
	if(false == $('#payOutUpdateForm').form('validate')){
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}
	
	//去掉 input 输入的 前后空格
	$('#payOutUpdateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	
	//弹出异步加载 遮罩
	openMask();
	
	var params = $('#payOutUpdateForm').serialize();
	
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "account/payout/save.do",
		data : params,
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					window.parent.removeTab();
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
$(document).ready(function (){
	
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


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
<title>申请查询</title>
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
<div class="easyui-tabs" id="appQueryTabs" data-options="fit:true">
	<div title="综合查询列表">
		<div id="main">
			<div id="part1" class="part">
				<p class="title"><a href="javascript:void(0);">查询</a></p>
				
				<div class="content">
					<form id="queryForm" action="integrated/list.do" method="post" target="queryContent">
						<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>基本条件信息</strong></div><hr color="#D3D3D3"/>
						<table>
							<tr>
								<td>申请编号:</td>
								<td><input id="loanId" name="loanId" type="text" class="textbox"/></td>
								<td>姓名/机构名称:</td>
								<td><input id="name" name="name" type="text" class="textbox"/></td>
								<td>证件号码:</td>
								<td><input id="idNo" name="idNo" type="text" class="textbox"/></td>
								<td>产品:</td>
								<td><input id="product" name="product" type="text" class="textbox easyui-combobox"
										data-options="url:'sys/datadictionary/listjason.do?keyName=productType',
													textField:'keyValue',
													valueField:'keyProp',
													value:'',
													editable:false,
													panelHeight:'auto',
													loadFilter:function(data){
												   		var opts = $(this).combobox('options');
												   		var emptyRow = {};
														emptyRow[opts.valueField] = '';
														emptyRow[opts.textField] = '请选择';
														data.unshift(emptyRow);
														return data;
													}"/>
								</td>
							</tr>
						</table>
			<div><a id="cont" href="javascript:void(0)"><strong>更多条件</strong></a></div>			
			<div id="more" style="display: none;">	
						<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>人员条件信息</strong></div><hr color="#D3D3D3"/>
						<table>
							<tr>
								<td>业务人员:</td>
								<td><input id="salesmanName" name="salesmanName" type="text" class="textbox" /></td>
								<td>分公司审批人员:</td>
								<td><input id="branchAuditUser" name="branchAuditUser" type="text" class="textbox" /></td>
								<td>风控初审人员:</td>
								<td><input id="riskFirstAuditUser" name="riskFirstAuditUser" type="text" class="textbox" /></td>
								<td>风控复核人员:</td>
								<td><input id="riskCheckUser" name="riskCheckUser" type="text" class="textbox" /></td>
							</tr>
							<tr>
								<td>评审会初审人员:</td>
								<td><input id="meetFirstAuditUser" name="meetFirstAuditUser" type="text" class="textbox" /></td>
								<td>评审会复核人员:</td>
								<td><input id="meetCheckUser" name="meetCheckUser" type="text" class="textbox" /></td>
								<td>法务初审人员:</td>
								<td><input id="lawFirstUser" name="lawFirstUser" type="text" class="textbox" /></td>
								<td>法务内勤人员:</td>
								<td><input id="lawInsideUser" name="lawInsideUser" type="text" class="textbox" /></td>
							</tr>
							<tr>
								<td>法务复核人员:</td>
								<td><input id="lawReviewUser" name="lawReviewUser" type="text" class="textbox" /></td>
								<td>贷前核帐人员:</td>
								<td><input id="cashUser" name="cashUser" type="text" class="textbox" /></td>
								<td>财务核帐人员:</td>
								<td><input id="acctUser" name="acctUser" type="text" class="textbox" /></td>
								<td>放款人员:</td>
								<td><input id="loanUser" name="loanUser" type="text" class="textbox" /></td>
							</tr>
						</table>
						
						<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>时间条件信息</strong></div><hr color="#D3D3D3"/>
						<table>
							<tr>	
								<td>申请录入时间:</td>
								<td colspan="2">
									<input id="inputTimeMin" name="inputTimeMin" type="text" class="textbox easyui-datebox" 
										data-options="editable:false" style="width: 90px;"/>
									&nbsp;-&nbsp;
									<input id="inputTimeMax" name="inputTimeMax" type="text" class="textbox easyui-datebox" 
										data-options="editable:false" style="width: 90px;"/>
								</td>	
								<td>分公司审批时间:</td>
								<td colspan="2">
									<input id="branchAuditTimeMin" name="branchAuditTimeMin" type="text" class="textbox easyui-datebox" 
										data-options="editable:false" style="width: 90px;"/>
									&nbsp;-&nbsp;
									<input id="branchAuditTimeMax" name="branchAuditTimeMax" type="text" class="textbox easyui-datebox" 
										data-options="editable:false" style="width: 90px;"/>
								</td>	
								<td>风控初审时间:</td>
								<td>
									<input id="riskFirstTimeMin" name="riskFirstTimeMin" type="text" class="textbox easyui-datebox" 
										data-options="editable:false" style="width: 90px;"/>
									&nbsp;-&nbsp;
									<input id="riskFirstTimeMax" name="riskFirstTimeMax" type="text" class="textbox easyui-datebox" 
										data-options="editable:false" style="width: 90px;"/>
								</td>			
							</tr>
							<tr>	
								<td>风控复核时间:</td>
								<td colspan="2">
									<input id="riskCheckTimeMin" name="riskCheckTimeMin" type="text" class="textbox easyui-datebox" 
										data-options="editable:false" style="width: 90px;"/>
									&nbsp;-&nbsp;
									<input id="riskCheckTimeMax" name="riskCheckTimeMax" type="text" class="textbox easyui-datebox" 
										data-options="editable:false" style="width: 90px;"/>
								</td>	
								<td>评审会初审时间:</td>
								<td colspan="2">
									<input id="meetFirstTimeMin" name="meetFirstTimeMin" type="text" class="textbox easyui-datebox" 
										data-options="editable:false" style="width: 90px;"/>
									&nbsp;-&nbsp;
									<input id="meetFirstTimeMax" name="meetFirstTimeMax" type="text" class="textbox easyui-datebox" 
										data-options="editable:false" style="width: 90px;"/>
								</td>	
								<td>评审会复核时间:</td>
								<td>
									<input id="meetCheckTimeMin" name="meetCheckTimeMin" type="text" class="textbox easyui-datebox" 
										data-options="editable:false" style="width: 90px;"/>
									&nbsp;-&nbsp;
									<input id="meetCheckTimeMax" name="meetCheckTimeMax" type="text" class="textbox easyui-datebox" 
										data-options="editable:false" style="width: 90px;"/>
								</td>			
							</tr>
							<tr>	
								<td>法务处理时间:</td>
								<td colspan="2">
									<input id="lawTimeMin" name="lawTimeMin" type="text" class="textbox easyui-datebox" 
										data-options="editable:false" style="width: 90px;"/>
									&nbsp;-&nbsp;
									<input id="lawTimeMax" name="lawTimeMax" type="text" class="textbox easyui-datebox" 
										data-options="editable:false" style="width: 90px;"/>
								</td>	
								<td>贷前核帐时间:</td>
								<td colspan="2">
									<input id="cashTimeMin" name="cashTimeMin" type="text" class="textbox easyui-datebox" 
										data-options="editable:false" style="width: 90px;"/>
									&nbsp;-&nbsp;
									<input id="cashTimeMax" name="cashTimeMax" type="text" class="textbox easyui-datebox" 
										data-options="editable:false" style="width: 90px;"/>
								</td>	
								<td>财务核帐时间:</td>
								<td>
									<input id="acctTimeMin" name="acctTimeMin" type="text" class="textbox easyui-datebox" 
										data-options="editable:false" style="width: 90px;"/>
									&nbsp;-&nbsp;
									<input id="acctTimeMax" name="acctTimeMax" type="text" class="textbox easyui-datebox" 
										data-options="editable:false" style="width: 90px;"/>
								</td>			
							</tr>
							<tr>
								<td>放款时间:</td>
								<td>
									<input id="loanTimeMin" name="loanTimeMin" type="text" class="textbox easyui-datebox" 
										data-options="editable:false" style="width: 90px;"/>
									&nbsp;-&nbsp;
									<input id="loanTimeMax" name="loanTimeMax" type="text" class="textbox easyui-datebox" 
										data-options="editable:false" style="width: 90px;"/>
								</td>
							</tr>
							
							
						</table>
						
						<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>状态条件信息</strong></div><hr color="#D3D3D3"/>
						<table>
							<tr>
								<%-- <td>流程节点状态:</td>
								<td>
									<select id="bpmState" name="bpmState">
										<option selected="selected" value="">请选择</option>
										<option value="<%=CommonConstant.PROCESS_A%>"><%=CommonConstant.PROCESS_A%></option>
										<option value="<%=CommonConstant.PROCESS_B%>"><%=CommonConstant.PROCESS_B%></option>
										<option value="<%=CommonConstant.PROCESS_C%>"><%=CommonConstant.PROCESS_C%></option>
										<option value="<%=CommonConstant.PROCESS_D%>"><%=CommonConstant.PROCESS_D%></option>
										<option value="<%=CommonConstant.PROCESS_E%>"><%=CommonConstant.PROCESS_E%></option>
										<option value="<%=CommonConstant.PROCESS_F%>"><%=CommonConstant.PROCESS_F%></option>
										<option value="<%=CommonConstant.PROCESS_G%>"><%=CommonConstant.PROCESS_G%></option>
										<option value="<%=CommonConstant.PROCESS_H%>"><%=CommonConstant.PROCESS_H%></option>
										<option value="<%=CommonConstant.PROCESS_I%>"><%=CommonConstant.PROCESS_I%></option>
										<option value="<%=CommonConstant.PROCESS_J%>"><%=CommonConstant.PROCESS_J%></option>
										<option value="<%=CommonConstant.PROCESS_K%>"><%=CommonConstant.PROCESS_K%></option>
										<option value="<%=CommonConstant.PROCESS_N%>"><%=CommonConstant.PROCESS_N%></option>
									</select>
								</td> --%>
								
								<td>申请状态:</td>
								<td>
									<select id="state1st" name="state1st">
										<option selected="selected" value="">请选择</option>
										<option value="A">录入申请</option>
										<option value="B">分公司审批中</option>
										<option value="C">风控初审中</option>
										<option value="D">评审会初审中</option>
										<option value="E">评审会复核中</option>
										<option value="F">风控复核中</option>
										<option value="G">法务处理中</option>
										<option value="H">贷前核帐中</option>
										<option value="H">财务审批中</option>
										<option value="J">出纳放款确认中</option>
										<option value="L">确认放款</option>
										<option value="K">客户放弃</option>
										<option value="N">推送线上</option>
									</select>
								</td>
							</tr>
						</table>
				</div>
						<div style="margin-left: 10px;margin-top: 10px;"></div>
						<table>
							<tr>
								<td colspan="3">
									<input type="button" value="查询" class="btn" onclick="submitForm('queryForm')"/>
									<input type="button" value="重置" class="btn" onclick="$('#queryForm').form('clear');"/>
								</td>
							</tr>
						</table>	
					</form>
				</div>
				
				<div id="queryContent" >
				<%--
				查询列表
				 --%>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function submitForm(fromId) {
	//去掉 input 输入的 前后空格
	$('#queryForm').find('input').each(function(){
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
//页面加载完动作
$(document).ready(function() {
	//submitForm("queryForm");
	
	$("#cont").on('click',function(){
		if($("#more").is(':visible')) {
			$("#more").slideUp();
			$(this).find('strong').text("更多条件");
		} else if($("#more").is(':hidden')) {
			$("#more").slideDown();
			$(this).find('strong').text("收起");
		}
	});
});
</script>
</body>
</html>


<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />
		<title>财富端申请表更新</title>
		<link href="css/global.css" type="text/css" rel="stylesheet" />
		<link href="css/icon.css" type="text/css" rel="stylesheet" />
		<link href="css/default/easyui.css" type="text/css" rel="stylesheet" />
		<script src="js/jquery.min.js" type="text/javascript"></script>
		<script src="js/jquery.form.js" type="text/javascript"></script>
		<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
		<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
		
		<style type="text/css"></style>
	</head>
	<body>
		<div id="main">
			<div id="part1" class="part">
				<p class="title">
					<a href="javascript:void(0);">人工撮合-机构申请</a>
				</p>
				<div class="content">
					<form id="updateForm">
						<table>
							<tbody>
								<input id="id" name="id" type="hidden" size="35"
									value="${ bean.id}" />
								<tr>
									<td>
										<SPAN style="color: red">*</SPAN>申请号:
									</td>
									<td>
										<input id="appId" name="appId" type="text" readonly="readonly"
											data-options="required:true,validType:['length[0,30]']"
											class="textbox easyui-validatebox" value="${bean.appId}" />
									</td>
									<td>
										<SPAN style="color: red">*</SPAN>类型-个人/机构:
									</td>
									<td>
										<input id="customerType" name="customerType" type="text" readonly="readonly"
											data-options="required:true,validType:['length[0,2]']"
											class="textbox easyui-validatebox"
											value="${bean.customerType}" />
									</td>
									<td>
										客户等级:
									</td>
									<td>
										<input id="customerLever" name="customerLever" type="text" readonly="readonly"
											data-options="validType:['length[0,2]']"
											class="textbox easyui-validatebox"
											value="${bean.customerLever}" />
									</td>
								</tr>
									
								<tr>
									<td>
										产品:
									</td>
									<td>
										<input id="product" name="product" type="text" readonly="readonly"
											data-options="validType:['length[0,50]']"
											class="textbox easyui-validatebox" value="${bean.product}" />
									</td>
									<td>
										出借金额:
									</td>
									<td>
										<input id="amount" name="amount" type="text" editable="false" 
											data-options="min:0,precision:2"
											class="textbox easyui-numberbox" value="${bean.amount}" />
									</td>
									<td>
										申请类型:
									</td>
									<td>
										<input id="appType" name="appType" type="text" readonly="readonly"
											data-options="validType:['length[0,2]']"
											class="textbox easyui-validatebox" value="${bean.appType}" />
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
				<p class="title">
					<h3><a href="javascript:void(0);">撮合选择列表</a></h3>
				</p>
				<div class="content">
						<div id="alltabs" class="easyui-tabs" data-options="tools:'#tab-tools'">
	                        <div title="借款人" name="myselfinfo" style="padding:10px">
	                        	<form id="loanPerson" action="lend/manualmatch/loan2matchList.do" method="post" target="loanPersonContent">
	                        		<table>
										<tr>
											<td>
												借款申请号:
											</td>
											<td>
												<input id="loanAppId" name="loanAppId" type="text"
													data-options="validType:['length[0,50]']"
													class="textbox easyui-validatebox" />
											</td>
											<td>
												姓名:
											</td>
											<td>
												<input id="name" name="name" type="text"
													data-options="validType:['length[0,50]']"
													class="textbox easyui-validatebox" />
											</td>
											<td>所属机构:</td>
											<td>
												<input id="orgId" name="orgId" type="text" editable="false" class="easyui-combobox" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"/>
											</td>
										</tr>
										<tr>
											
											<!--<td>
												证件类型:
											</td>
											<td>
												<input id="bborgId" name="bborgId" type="text" class="easyui-combobox"
													data-options="validType:['length[0,50]']"
													class="textbox easyui-validatebox" value="${bean.orgId}" />
											</td>
											--><td>
												证件号码:
											</td>
											<td>
												<input id="idNo" name="idNo" type="text"
													data-options="validType:['length[0,18]','idNo']" 
													class="textbox easyui-validatebox" />
											</td>
											<td>
												期限:
											</td>
											<td>
													<input id="loanPeriod" name="loanPeriod" type="text"
													data-options="validType:['length[0,50]']"
													class="textbox easyui-validatebox" />个月
													
											</td>
										</tr>
						
										<tr>
						
											
											<td>
												金额:
											</td>
						
											<td>
												<input id="contractAmountMin" name="contractAmountMin"
													style="width: 63px;" type="text" editable="false"
													data-options="min:0,precision:2" class="textbox easyui-numberbox" />
											</td>
											<td>
												至:
											</td>
						
											<td>
												<input id="contractAmountMax" name="contractAmountMax"
													style="width: 63px;" type="text" editable="false"
													data-options="min:0,precision:2" class="textbox easyui-numberbox" />
											</td>
										</tr>
						
										<tr>
											<td>
												<input type="button" value="查询借款人" class="btn" onclick="submitForm('loanPerson')"/>
												<input type="reset" value="重置" class="btn" onclick="$('#trustProduct').form('clear');"/>
											</td>
											<td></td>
										</tr>
									</table>
								</form>
									<div id="loanPersonContent">
										
									</div>
									<div id="selectedLoanPersonContent">
	                            	
	            					</div>
	                        	
	                        </div>
	                        <div title="信托产品" style="padding:10px">
	                            <form id="trustProduct" action="lend/manualmatch/trustProductList.do" method="post" target="trustProductContent">
	                            	<table>
										<tr>
											<td>
												产品:
											</td>
											<td>
												<input id="trustProname" name="trustProname" type="text"
													data-options="validType:['length[0,50]']"
													class="textbox easyui-validatebox" />
											</td>
											<td>
												期限:
											</td>
											<td>
												<input id="period" name="period" type="text"
													data-options="validType:['length[0,50]']"
													class="textbox easyui-validatebox" />
											</td>
											<td>
											个月
											</td>
										</tr>
										<tr>
											
											<td>
												机构名称:
											</td>
											<td>

												<input id="company" name="company" style="width: 63px;"
														type="text" data-options="validType:['length[0,50]']"
													class="textbox easyui-validatebox" />
											</td>
											<td>
												起点金额:
											</td>
							
											<td>
												<input id="prostartMoneyMin" name="prostartMoneyMin" style="width: 63px;"
													type="text" editable="false" data-options="min:0,precision:2"
													class="textbox easyui-numberbox" />
											</td>
											<td>
												至:
											</td>
							
											<td>
												<input id="prostartMoneyMax" name="prostartMoneyMax" style="width: 63px;"
													type="text" editable="false" data-options="min:0,precision:2"
													class="textbox easyui-numberbox" />
											</td>
										</tr>
							
										
							
										<tr>
											<td>
												<input type="button" value="信托产品查询" class="btn" onclick="submitForm('trustProduct')" />
												<input type="reset" value="重置" class="btn" onclick="$('#trustProduct').form('clear');"/>
											</td>
											<td></td>
										</tr>
									</table>
	                            </form>
	                            
	                            <div id="trustProductContent">
	                            	
	                            </div>
	                            
	                            <div id="selectedTrustContent">
	                            	
	            				</div>
							</div>
                    	</div>

				</div>
				

			<div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
				<img src="img/loading.gif" alt="加载中..." />
			</div>
			
			<div id="selectedLoanPersonOrProduct">
			
			</div>
		</div>
	</body>

<script type="text/javascript">

//记录所选中的loan_2match的id
var arr_selectedLoan2MatchIds=[];

//记录所选中的trustProduct的id
var arr_selectedTrustProIds=[];

function submitForm(fromId) {

	var formAction = $('#' + fromId).attr("action");
	var targetDiv = $('#' + fromId).attr("target");
	var params = $('#' + fromId).serialize();
	openMask();
 $.ajax({
		type : "POST",
		url : formAction,
		data : encodeURI(params + "&targetDiv=" + targetDiv),
		dataType : "html",
		success : function(data) {
			$('#' + targetDiv).html(data);
			closeMask();
		},
		error : function() {
			closeMask();
			
			$.messager.confirm('消息', '加载失败。', function(ok){
	            if (ok){
//	 				window.history.go(-1);
	            }
	    	});
		}
	}); 
}
function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
}


$(document).ready(function(){
	
	//var tsurl="<%=basePath%>sys/org/allOrgList.do?loginid=all&timestamp="+(new Date()).getTime();
	//$("#orgId").combobox("clear");
	//$("#orgId").combobox({url:tsurl,valueField:'orgId',textField:'orgName'});
	//填充select数据样例

	var tsurl="sys/org/subOrg.do?orgId=${login_org.orgId}";
	$("#orgId").combobox("clear");
	$('#orgId').combobox({
		url:tsurl,
		valueField:'orgId',
		textField:'orgName',
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

});


//更新保存
function updateFunction() {
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		$(this).val($.trim($(this).val()));
	});
	//验证表单验证是否通过
	if (false == $('#updateForm').form('validate')) {
		return;
	}
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax( {
		type : "POST",
		url : "<%=basePath%>" + "lend/manualmatch/save.do",
		data : encodeURI(params),
		success : function(data) {
			//TODO 更新类的操作 
		if ("true" == data.success) {
			alert("操作成功！");
			var url = "<%=basePath%>" + "lend/manualmatch/query.do";
			window.location = url;
		} else {
			alert("操作失败，请联系系统管理员！");
		}
		//按钮生效
		$(".btn").removeAttr("disabled");
	},
	error : function() {
		alert("数据加载失败！");
		//按钮生效
		$(".btn").removeAttr("disabled");
	}
	});
}
//返回
function back() {
	window.history.go(-1);
}


</script>
</html>


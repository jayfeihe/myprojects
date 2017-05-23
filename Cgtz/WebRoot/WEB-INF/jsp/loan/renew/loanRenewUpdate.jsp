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
<title>个人借款业务申请</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
</head>
<body>
	<!-- <div class="easyui-tabs" id="appUpdateTabs" style="width: 100%;" data-options="fit:true">
		<div title="续贷申请信息"  style="width: 100%;"> -->
			<div id="main">
				<div class="content">
					<form id="renewUpdateForm" >
						<input type="hidden" name="loanBase.origLoanId" value="${loanBase.loanId }" />
						<input type="hidden" name="contractId" value="${contractId }"/>
						<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>续贷信息</strong></div><hr color="#D3D3D3"/>
						<table id="loanInfo">
							<tr>
								<td>未还金额:</td>
								<td>
									<input id="loanAmt" name="loanBase.loanAmt" type="text"  data-options="min:0,precision:2" 
											class="textbox easyui-numberbox" readonly="readonly" value="${renewAmt}"/>元
								</td>
								<td>延长日期:</td>
								<td>
									<input id="endDate" name="loanBase.endDate" type="text" class="textbox easyui-datebox" 
										data-options="required:true,editable:false"/>
								</td>
							</tr>
						</table>
						
						<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>还款方式</strong></div><hr color="#D3D3D3"/>
						<table id="repayInfo">
							<tr>
								<td>还款方式:</td>
								<td>
									<!-- <input type="hidden" name="loanBase.rate"/>
									<input type="hidden" name="loanBase.inteDays"/>
									<input type="hidden" name="loanBase.memFee" />
									<input type="hidden" name="loanBase.lawFee" />
									<input type="hidden" name="loanBase.magin" />
									<input type="hidden" name="loanBase.otherFee"/> -->
									
									<input id="retWay" name="loanBase.retWay" type="text" 
										data-options="required:true,editable:false,panelHeight:'auto'" 
										class="textbox easyui-combobox" value="01"/>
								</td>
							</tr>
							
							<tr>
								<td>年化利率:</td>
								<td>
									<input id="rate" type="text" name="loanBase.rate" class="textbox easyui-numberbox" 
										data-options="required:true,min:0,max:99,precision:2"/>%
								</td>
								<td class="deRate" style="display: none;">等额利率:</td>
								<td class="deRate" style="display: none;">
									<input id="deRate" name="loanBase.deRate" type="text" class="textbox easyui-numberbox" 
										data-options="required:true,min:0,max:99,precision:2"/>%
								</td>
								<td>计息天数:</td>
								<td><input id="inteDays" type="text" name="loanBase.inteDays" class="textbox easyui-combobox" 
										data-options="required:true,editable:false,
													panelHeight:'auto',
													textField:'keyValue',
													valueField:'keyProp',
													data:dataDictJson.inteDays"/>
								</td>
								<td>会员服务费:</td>
								<td><input id="memFee"  name="loanBase.memFee" type="text" class="textbox easyui-numberbox" 
									 	data-options="min:0,precision:2" />元
								</td>
							</tr>
							<tr>
								<td>法律服务费:</td>
								<td><input id="lawFee" name="loanBase.lawFee" type="text" class="textbox easyui-numberbox" 
										 data-options="min:0,precision:2" />元
								</td>
								<td>保证金:</td>
								<td><input id="magin" name="loanBase.magin" type="text" class="textbox easyui-numberbox" 
										 data-options="min:0,precision:2"/>元
								</td>
								<td>转贷费:</td>
									<td><input id="tranFee" name="loanBase.tranFee" type="text" class="textbox easyui-numberbox" 
											 data-options="min:0,precision:2"/>元
									</td>
								<td>其他费用:</td>
								<td><input id="otherFee" name="loanBase.otherFee" type="text" class="textbox easyui-numberbox" 
										 data-options="min:0,precision:2"/>元
								</td>
							</tr>
							<tr>
								<td>管理服务费:</td>
								<td><input id="mgrFee" name="loanBase.mgrFee" type="text" class="textbox easyui-numberbox" 
										 data-options="min:0,precision:2"/>元
								</td>
								<td>他项权证费:</td>
								<td><input id="certFee" name="loanBase.certFee" type="text" class="textbox easyui-numberbox" 
										 data-options="min:0,precision:2"/>元
								</td>
								<td>评估费:</td>
								<td><input id="evalFee" name="loanBase.evalFee" type="text" class="textbox easyui-numberbox" 
										 data-options="min:0,precision:2"/>元
								</td>
								<td>中介费:</td>
								<td><input id="agentFee" name="loanBase.agentFee" type="text" class="textbox easyui-numberbox" 
										 data-options="min:0,precision:2"/>元
								</td>
							</tr>
							<tr>
								<td>周期方案:</td>
								<td>
									<input name="loanBase.retLoanSol" type="radio" value="01" <c:if test="${loanBase.retLoanSol eq '01' }">checked='checked'</c:if> checked='checked'/>月付
									<input name="loanBase.retLoanSol" type="radio" value="02" <c:if test="${loanBase.retLoanSol eq '02' }">checked='checked'</c:if> />季付
									<input name="loanBase.retLoanSol" type="radio" value="03" <c:if test="${loanBase.retLoanSol eq '03' }">checked='checked'</c:if> />首付
									<input name="loanBase.retLoanSol" type="radio" value="04" <c:if test="${loanBase.retLoanSol eq '04' }">checked='checked'</c:if> />末付
								</td>
							</tr>
						</table>
						
						<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>业务员备注</strong></div><hr color="#D3D3D3"/>
						<table>
							<tr>
								<td>说明:</td>
								<td>
									<textarea name="loanBase.ext1" class="textbox easyui-validatebox" 
										data-options="validType:['length[0,500]']" 
										style="resize: none;width:625px;height:50px!important;"></textarea>
								</td>
							</tr>
						</table>
						
						<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>操作</strong></div><hr color="#D3D3D3"/>
						<table>
							<tr>
								<td colspan=3">
									<!-- <input type="button" value="保存" class="btn" onclick="javascript:updateFunction('save');"/>&nbsp;&nbsp; -->
									<input type="button" value="提交申请" class="btn" onclick="updateFunction('submit')"/>&nbsp;&nbsp;
									<!-- <input type="button" value="客户放弃" class="btn" onclick="updateFunction('giveUp')"/>&nbsp;&nbsp; -->
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
	<!-- </div>
	</div> -->

<script type="text/javascript">
//更新保存
function updateFunction(buttonType) {
	//去掉 input 输入的 前后空格
	$('#renewUpdateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	
	if("save"!=buttonType){
		//验证表单验证是否通过
		if(false == $('#renewUpdateForm').form('validate') ){
			$.messager.confirm('消息', "页面有必输字段，但未填值！");
			return;
		}
	}
	
	/* var retWay = $("#retWay").combobox('getValue');
	// 利息等额
	if('01' == retWay) {
		$("input[name='loanBase.rate']").val($(".lxde").find("#rate").numberbox('getValue'));  
		$("input[name='loanBase.inteDays']").val($(".lxde").find("#inteDays").combobox('getValue')); 
		$("input[name='loanBase.memFee']").val($(".lxde").find("#memFee").numberbox('getValue')); 
		$("input[name='loanBase.lawFee']").val($(".lxde").find("#lawFee").numberbox('getValue')); 
		$("input[name='loanBase.magin']").val($(".lxde").find("#magin").numberbox('getValue'));  
		$("input[name='loanBase.otherFee']").val($(".lxde").find("#otherFee").numberbox('getValue')); 
	}
	if('02' == retWay) {
		$("input[name='loanBase.rate']").val($(".xxhde").find("#rate").numberbox('getValue'));  
		$("input[name='loanBase.inteDays']").val($(".xxhde").find("#inteDays").combobox('getValue')); 
		$("input[name='loanBase.memFee']").val($(".xxhde").find("#memFee").numberbox('getValue')); 
		$("input[name='loanBase.lawFee']").val($(".xxhde").find("#lawFee").numberbox('getValue'));
		$("input[name='loanBase.magin']").val($(".xxhde").find("#magin").numberbox('getValue'));  
		$("input[name='loanBase.otherFee']").val($(".xxhde").find("#otherFee").numberbox('getValue')); 
	}
	 */
	//弹出异步加载 遮罩
	openMask();
	
	var params = $('#renewUpdateForm').serialize();
	
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "loan/renew/save.do",
		data : params+"&buttonType="+buttonType,
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					if(buttonType=="save") {
						location.replace("<%=basePath%>loan/renew/update.do?id="+data.id);
						window.parent.submitForm('queryForm');
					} else {
						window.parent.removeTab();
					}
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
	
	// 还款方式
	var retWay = $("#retWay").combobox('getValue');
	// 利息等额
	if('01' == retWay) {
		$(".deRate").hide();
		toggleValidate(".deRate",false);
	}
	if('02' == retWay) {
		$(".deRate").show();
		toggleValidate(".deRate",true);
	}
	
	var tsurl="sys/datadictionary/listjason.do?keyName=repayMethod";
	$("#retWay").combobox("clear");
	$('#retWay').combobox({
		url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		onChange: function(nVal,oVal) {
			// 利息等额
			if('01' == nVal) {
				$(".deRate").hide();
				toggleValidate(".deRate",false);
				resetElement("#repayInfo");
			    $('input:radio:first').attr('checked', 'checked');
			}
			
			// 利息先部分后等额
			if('02' == nVal) {
				$(".deRate").show();
				toggleValidate(".deRate",true);
				resetElement("#repayInfo");
				$('input:radio:first').attr('checked', 'checked');
			}
		}/* ,
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
		} */
	});
});
</script>

<script type="text/javascript" >
openMask();
$(window).load(function (){
	closeMask();
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


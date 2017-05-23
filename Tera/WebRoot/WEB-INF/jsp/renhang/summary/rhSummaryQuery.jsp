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
<title>信用贷款人行报告信息概要查询</title>
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
		<p class="title"><a href="javascript:void(0);">查询</a></p>
		
		<div class="content">
			<form id="queryForm" action="renhang/summary/list.do" method="post" target="queryContent">
				<table>
					<tr>
<td>申请ID:</td>
<td><input id="appId" name="appId" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox"/></td>
</tr>
<tr>
<td>住房贷款笔数:</td>
<td><input id="houseLoanNum" name="houseLoanNum" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>其他贷款笔数:</td>
<td><input id="otherLoanNum" name="otherLoanNum" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>首笔贷款发放月份:</td>
<td><input id="firstLoanDate" name="firstLoanDate" type="text" editable="false" class="textbox easyui-datebox"/></td>
</tr>
<tr>
<td>贷记卡账户数:</td>
<td><input id="creditNum" name="creditNum" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>首张贷记卡发卡月份:</td>
<td><input id="firstCreditDate" name="firstCreditDate" type="text" editable="false" class="textbox easyui-datebox"/></td>
</tr>
<tr>
<td>准贷记卡账户数:</td>
<td><input id="semiCreditNum" name="semiCreditNum" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>首张准贷记卡发卡月份:</td>
<td><input id="semiCreditDate" name="semiCreditDate" type="text" editable="false" class="textbox easyui-datebox"/></td>
</tr>
<tr>
<td>本人声明数目:</td>
<td><input id="declareNum" name="declareNum" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>异议标注数目:</td>
<td><input id="objectionNum" name="objectionNum" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>贷款逾期笔数:</td>
<td><input id="loanDefaultNum" name="loanDefaultNum" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>贷款逾期月份数:</td>
<td><input id="loanDefaultMonthNum" name="loanDefaultMonthNum" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>贷款单月最高逾期总额:</td>
<td><input id="loanMaxDefaultAmount" name="loanMaxDefaultAmount" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>贷款最长逾期月数:</td>
<td><input id="loanMaxDefaultMonth" name="loanMaxDefaultMonth" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>贷记卡逾期账户数:</td>
<td><input id="creditDefaultNum" name="creditDefaultNum" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>贷记卡逾期月份数:</td>
<td><input id="creditDefaultMonthNum" name="creditDefaultMonthNum" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>贷记卡单月最高逾期总额:</td>
<td><input id="creditMaxDefaultAmount" name="creditMaxDefaultAmount" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>贷记卡最长逾期月数:</td>
<td><input id="creditMaxDefaultMonth" name="creditMaxDefaultMonth" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>准贷记卡逾期账户数:</td>
<td><input id="semiCreditDefaultNum" name="semiCreditDefaultNum" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>准贷记卡逾期月份数:</td>
<td><input id="semiCreditDefaultMonthNum" name="semiCreditDefaultMonthNum" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>准贷记卡单月最高透支总额:</td>
<td><input id="semiCreditMaxDefaultAmount" name="semiCreditMaxDefaultAmount" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>准贷记卡最长透支月数:</td>
<td><input id="semiCreditMaxDefaultMonth" name="semiCreditMaxDefaultMonth" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>未结清贷款法人机构数:</td>
<td><input id="loanLegalNum" name="loanLegalNum" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>未结清贷款机构数:</td>
<td><input id="loanComNum" name="loanComNum" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>未结清贷款笔数:</td>
<td><input id="loanNum" name="loanNum" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>未结清贷款合同总额:</td>
<td><input id="loanAmount" name="loanAmount" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>未结清贷款余额:</td>
<td><input id="loanRestAmount" name="loanRestAmount" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>未结清贷款6月均还款:</td>
<td><input id="loanAvg6mAmount" name="loanAvg6mAmount" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>未销户贷记卡法人机构数:</td>
<td><input id="creditLegalNum" name="creditLegalNum" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>未销户贷记卡机构数:</td>
<td><input id="creditComNum" name="creditComNum" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>未销户贷记卡账户数:</td>
<td><input id="creditAccountNum" name="creditAccountNum" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>未销户贷记卡授信总额:</td>
<td><input id="creditTotalAmount" name="creditTotalAmount" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>未销户贷记卡单家最高额度:</td>
<td><input id="creditMaxAmount" name="creditMaxAmount" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>未销户贷记卡单家最低额度:</td>
<td><input id="creditMinAmount" name="creditMinAmount" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>未销户贷记卡已用额度:</td>
<td><input id="creditUseAmount" name="creditUseAmount" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>未销户贷记卡6月均用额度:</td>
<td><input id="creditAvg6mAmount" name="creditAvg6mAmount" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>未销户准贷记卡法人机构数:</td>
<td><input id="semiCreditLegalNum" name="semiCreditLegalNum" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>未销户准贷记卡机构数:</td>
<td><input id="semiCreditComNum" name="semiCreditComNum" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>未销户准贷记卡账户数:</td>
<td><input id="semiCreditAccountNum" name="semiCreditAccountNum" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>未销户准贷记卡授信总额:</td>
<td><input id="semiCreditTotalAmount" name="semiCreditTotalAmount" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>未销户准贷记卡单家最高额度:</td>
<td><input id="semiCreditMaxAmount" name="semiCreditMaxAmount" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>未销户准贷记卡单家最低额度:</td>
<td><input id="semiCreditMinAmount" name="semiCreditMinAmount" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>未销户准贷记卡透支额度:</td>
<td><input id="semiCreditUseAmount" name="semiCreditUseAmount" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>未销户准贷记卡6月均透支额度:</td>
<td><input id="semiCreditAvg6mAmount" name="semiCreditAvg6mAmount" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>担保笔数:</td>
<td><input id="secureNum" name="secureNum" type="text" data-options="min:0,precision:0" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>担保金额:</td>
<td><input id="secureAmount" name="secureAmount" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>担保本金余额:</td>
<td><input id="secureRestAmount" name="secureRestAmount" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox"/></td>
</tr>
<tr>
<td>备注:</td>
<td><input id="remarks" name="remarks" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox"/></td>
</tr>
<tr>
<td>状态:</td>
<td><input id="state" name="state" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox"/></td>
</tr>
<tr>
<td>操作员:</td>
<td><input id="operator" name="operator" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"/></td>
</tr>
<tr>
<td>所属机构:</td>
<td><input id="orgId" name="orgId" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"/></td>
</tr>
<tr>
<td>创建日期:</td>
<td><input id="createTime" name="createTime" type="text" editable="false" class="textbox easyui-datetimebox"/></td>
</tr>
<tr>
<td>修改日期:</td>
<td><input id="updateTime" name="updateTime" type="text" editable="false" class="textbox easyui-datetimebox"/></td>
</tr>

					<tr>
						<td>
							<input type="button" value="查询" class="btn" onclick="submitForm('queryForm')"/>
							<input type="button" value="重置" class="btn" onclick="$('#queryForm').form('clear');"/>
						</td>
						<td></td>
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
	submitForm("queryForm");//解决Tab提交关闭列表页刷新的问题
}
</script>

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
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
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

	submitForm("queryForm");
});
</script>

</html>


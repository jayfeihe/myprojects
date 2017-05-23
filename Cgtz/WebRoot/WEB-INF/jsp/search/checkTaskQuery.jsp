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
<title>稽查任务</title>
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
<div id="afterLoanTabs" class="easyui-tabs" data-options="fit:true">
<div title="稽查任务">
	<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">查询</a></p>
		
		<div class="content">
			<form id="queryCheckTaskForm" action="search/task/list.do" method="post" target="queryContent">
				<table>
<tr>
<td>合同状态:</td>
<td><input id="state" name="state" type="text" editable="false" class="textbox easyui-combobox"
data-options="panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'1','keyValue':'未生效'},{'keyProp':'2','keyValue':'合同中'},{'keyProp':'3','keyValue':'合同结清'},{'keyProp':'4','keyValue':'被拆分'}]"/></td>
<td>合同编号:</td>
<td><input id="contractId" name="contractId" type="text" class="textbox easyui-validatebox"/></td>
<td>申请编号:</td>
<td><input id="loanId" name="loanId" type="text" class="textbox easyui-validatebox"/></td>
</tr>
<tr> 
<td>稽查报告状态:</td>
<td><input id="checkReportState" name="checkReportState" type="text" class="textbox easyui-combobox" 
data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'0','keyValue':'未提交'},{'keyProp':'1','keyValue':'未审核'},{'keyProp':'2','keyValue':'未通过'},{'keyProp':'3','keyValue':'已通过'}]"/></td>
<!-- <td>稽查案件来源:</td>
<td><input id="checkSource" name="checkSource" type="text" class="textbox easyui-combobox" 
data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'1','keyValue':'新逾期'},{'keyProp':'2','keyValue':'业务员跟进'},{'keyProp':'3','keyValue':'还款变化'}]"/></td> -->
<td>借款人:</td><td><input id="loanName" name="loanName" type="text" class="textbox easyui-validatebox"/></td>
<td>业务经办人:</td><td><input id="saleName" name="saleName" type="text" class="textbox easyui-validatebox"/></td>        
</tr>
<tr>
<td>分公司:</td>
<td><input id="org" name="org" <c:if test="${login_org.orgId ne '86'  }">value='${login_org.orgId }' readonly='readonly'</c:if> class="textbox easyui-combotree"
data-options="editable:false,panelHeight:'auto',url:'<%=basePath%>sys/org/selectList.do?nodeLevel=2',method:'get'"/></td>

<td>产品类型:</td>
				<td><input id="product" name="product" type="text"  class="textbox easyui-combobox" style="width:100px;"
				data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
				url:'sys/datadictionary/listjason.do?keyName=productType'"/>
               </td>
<td>逾期类型:</td>
				<td><input id="overdueType" name="overdueType" type="text"  class="textbox easyui-combobox" style="width:100px;"
				data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
				data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'1','keyValue':'利息逾期'},{'keyProp':'2','keyValue':'本金逾期'}]"/>
               </td>	      
</tr>
<tr>
<td>处理情况:</td>
				<td><input id="checkState" name="checkState" type="text"  class="textbox easyui-combobox" style="width:100px;"
				data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
				data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'1','keyValue':'未处理'},{'keyProp':'2','keyValue':'已处理'},{'keyProp':'3','keyValue':'移交法务'},{'keyProp':'4','keyValue':'重点关注'}]"/>
				</td> 
<td>是否受理:</td>
				<td><input id="isAccept" name="isAccept" type="text"  class="textbox easyui-combobox" style="width:100px;"
				data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
				data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'0','keyValue':'未受理'},{'keyProp':'1','keyValue':'已受理'}]"/>
				</td>    				
<td>逾期天数:</td>
				<td><input id="startDays" name="startDays" type="text"  class="textbox easyui-numberbox" style="width:100px;"/>
				-<input id=endDays name="endDays" type="text"  class="textbox easyui-numberbox" style="width:100px;"/>天</td>         				

				</tr>
<tr>
<td>合同开始日期:</td>
<td colspan="2"><input id="minStartTime" name="minStartTime" type="text" editable="false" class="textbox easyui-datebox" style="width:100px;"/>
-<input id="maxStartTime" name="maxStartTime" type="text" editable="false" class="textbox easyui-datebox" style="width:100px;"/></td>

<td colspan="3">合同结束日期:
<input id="minEndTime" name="minEndTime" type="text" editable="false" class="textbox easyui-datebox" style="width:100px;"/>
-<input id="maxEndTime" name="maxEndTime" type="text" editable="false" class="textbox easyui-datebox" style="width:100px;"/></td>				
</tr>
<tr>
<td>应收日期:</td>
<td colspan="2"><input id="minRetDate" name="minRetDate" type="text" editable="false" class="textbox easyui-datebox" style="width:100px;"/>
-<input id="maxRetDate" name="maxRetDate" type="text" editable="false" class="textbox easyui-datebox" style="width:100px;"/></td>		
</tr>					

					<tr><td></td>
						<td>
							<input type="button" value="查询" class="btn" onclick="submitForm('queryCheckTaskForm')"/>
							<input type="button" value="导出" class="btn" onclick="exportExcel('queryCheckTaskForm')"/>
							<input type="button" value="重置" class="btn" onclick="$('#queryCheckTaskForm').form('clear');"/>
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
</div>
</div>
</body>
<script type="text/javascript">
//添加选项卡
function addTab(title, url){
    if ($('#afterLoanTabs').tabs('exists', title)){
        $('#afterLoanTabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#afterLoanTabs').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeTab(){
	var tab = $('#afterLoanTabs').tabs('getSelected');
	var tabIndex=$('#afterLoanTabs').tabs('getTabIndex',tab);
	$('#afterLoanTabs').tabs('close',tabIndex);
	submitForm("queryCheckTaskForm");//解决Tab提交关闭列表页刷新的问题
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
	$('#queryCheckTaskForm').find('input').each(function(){
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
//导出
function exportExcel(fromId) {
	$('#' + fromId).attr("action","<%=basePath%>search/afterloan/excel.do?type=task");
	$('#' + fromId).attr("method","post");
	$('#' + fromId).submit();
}
//页面加载完动作
$(document).ready(function() {
/* //填充select数据样例
        var coll=$("#tr_address");
		//填充select数据 省份
		var provinceurl = "sys/datadictionary/listjason.do?keyName=province";
		coll.find('#prvn').combobox({
		url: provinceurl,
		valueField: 'keyProp',
		textField: 'keyValue',
		onChange: function(newValue, oldValue){
				coll.find('#city').combobox('clear');
				coll.find('#ctry').combobox('clear');
				var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
				coll.find('#city').combobox('reload',cityurl);
		},
		loadFilter:function(data){
			var comVal = $(this).combobox('getValue');
			if(comVal == '' || comVal == null) {
		   		var opts = $(this).combobox('options');
		   		var emptyRow = {};
				emptyRow[opts.valueField] = '';
				emptyRow[opts.textField] = '请选择';
				data.unshift(emptyRow);
				$(this).combobox('setValue','');
			}
			return data;
		}
	});
		
		//填充select数据 市
		var province = coll.find('#prvn').combobox('getValue');
		var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" +encodeURI(province);
		coll.find('#city').combobox({
			url: cityurl,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
				coll.find('#ctry').combobox('clear');
				var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
				coll.find("#ctry").combobox('reload',countyurl); 
		    }
		});
		//填充select数据 区县
		var city = coll.find('#city').combobox('getValue');
		var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(city);
		coll.find('#ctry').combobox({url: countyurl, valueField: 'keyProp',textField: 'keyValue'}); */
	submitForm("queryCheckTaskForm");
});
</script>

</html>


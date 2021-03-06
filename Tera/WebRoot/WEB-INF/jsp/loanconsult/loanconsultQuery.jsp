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
<title>借款咨询查询</title>
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
		<p class="title"><a href="javascript:void(0);">借款咨询查询</a></p>
		
		<div class="content">
			<form id="queryForm" action="loanConsult/list.do" method="post" target="queryContent">
				<table>
							<tr>
								<td align="right">
									客户姓名:								</td>
								<td>
									<input id="name" name="name" type="text"
										data-options="validType:['length[0,50]']"
										class="textbox easyui-validatebox" value="${bean.name}" />								</td>
								<td align="right">
									客户类型:								</td>
								<td>
									<select id="type" name="type"  class="easyui-combobox" style="width:152px;" editable="false" value="${bean.type}" /></td>
									</tr><tr>
								<td colspan="4">
									<div style="padding:0px;float:left;">
									  <input type="button" value="查询" class="btn"
										onclick="submitForm('queryForm')" />
									  <input type="reset" value="重置" class="btn" onclick="$('#queryForm').form('clear');"/>								
							        </div></td>
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

<!-- <div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
	<img src="img/loading.gif" alt="加载中..." />
</div> -->

</body>
	
<script type="text/javascript">
$(document).ready(function(){
	submitForm('queryForm');
});
function submitForm(fromId) {
	//去掉 input 输入的 前后空格
	$('#queryForm').find('input').each(function(){
		$(this).val($.trim($(this).val()));
	});
	//验证表单验证是否通过
	if(false == $('#queryForm').form('validate') ){
		return;
	}
	var formAction = $('#' + fromId).attr("action");
	var targetDiv = $('#' + fromId).attr("target");
	var params = $('#' + fromId).serialize();
	//弹出异步加载 遮罩
	openMask();
	$.ajax({
		type : "POST",
		url : formAction,
		data : encodeURI(params + "&targetDiv=" + targetDiv),
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

//打开Loading遮罩并修改样式
/* function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
} */


$(document).ready(function(){
	//填充select数据 婚姻
	//var tsurl="sys/datadictionary/listjason.do?keyName=customertype";
	$("#type").combobox("clear");
	$('#type').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.customertype,
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

function loanConsultRead(param){
	var urlParam = "loanConsult/update.do?paramVal="+param;
	if(param == 'group'){
		urlParam ="loanConsult/update.do?paramVal="+param;
	}
	window.location = "<%=basePath%>" + urlParam;
	
	return;
}
</script>
</html>


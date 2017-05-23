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
	<title>审核统计</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
	
</head>
<body style="height: '100%'">
	<div class="easyui-tabs" data-options="fit:true">
		<div title="审核统计">
			<div id="main">
				<div id="part1" class="part">
					<p class="title"><a href="javascript:void(0);">审核统计查询</a></p>
		
					<div class="content">
						<form id="queryForm" action="report/verifyStatisticsList.do" method="get" target="queryContent">
							<table>
								<tr>
									<td>审核日期：</td>
									<td>
										<input id="verifyDateStart" name="verifyDateStart" style="width: 90px;" 
											data-options="formatter:myformatter,parser:myparser" 
											type="text" editable="false" class="textbox easyui-datebox" />
									
										<span style="font-size: 12px;">&nbsp;至&nbsp;</span>
									
										<input id="verifyDateEnd" name="verifyDateEnd" style="width: 90px;" 
											data-options="formatter:myformatter,parser:myparser" 
											type="text" editable="false" class="textbox easyui-datebox" />
									</td>
									<td>
										<input type="button" value="查询" class="btn" onclick="submitForm('queryForm')" />
										<input type="button" value="重置" class="btn" onclick="$('#queryForm').form('clear');" />
									</td>
								</tr>
							</table>
						</form>
					</div>
		
					<div id="queryContent">
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
function submitForm(fromId) {
	var formAction = $('#' + fromId).attr("action");
	var targetDiv = $('#' + fromId).attr("target");
	var params = $('#' + fromId).serialize();
	openMask();
	$.ajax({
		type : "POST",
		url : formAction,
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
	
	initRangeDate('verifyDateStart','verifyDateEnd');
	submitForm("queryForm");
});

function initRangeDate(startDate,endDate){
	$('#'+startDate).datebox().datebox('calendar').calendar({
		validator: function(date){
			var now = new Date();
			return now>=date;
		}
	});
	$('#'+endDate).datebox().datebox('calendar').calendar({
		validator: function(date){
			var now = new Date();
			var start = $('#'+startDate).datebox('getValue');
			return now>=date && start<=date;
		}
	});
	var start = new Date();
	var end = new Date();
	$('#'+startDate).datebox({
		onSelect: function(date) {
			start = date;
			$('#'+endDate).datebox().datebox('calendar').calendar({
				validator: function(date){
					var now = new Date();
					return now>=date && start<=date;
				}
			});
			$('#'+endDate).datebox('setValue',myformatter(end));
		}
	});
	$('#'+endDate).datebox({
		onSelect: function(date) {
			end = date;
			$('#verifyDateStart').datebox().datebox('calendar').calendar({
				validator: function(date){
					return end>=date;
				}
			});
			$('#'+startDate).datebox('setValue',myformatter(start));
		}
	});
	
	$('#'+startDate).datebox('setValue',myformatter(new Date()));
	$('#'+endDate).datebox('setValue',myformatter(new Date()));
}

function myformatter(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}

function myparser(s){
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
}
</script>
</html>


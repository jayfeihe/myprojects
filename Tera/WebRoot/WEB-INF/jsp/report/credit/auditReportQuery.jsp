<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<!DOCTYPE html>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="en">
<head>
	<base href="<%=basePath%>" />
    <meta charset="utf-8">
    <title>ECharts</title>
    <link href="css/global.css" type="text/css" rel="stylesheet"/>
   	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<script src="js/echarts/echarts.js"></script>
</head>

<body>
	<div id="main">
	   	<div id="part1" class="part">
			<p class="title"><a href="javascript:void(0);">信审量统计</a></p>
			<div class="content">
				<form id="queryForm" action="" method="get" target="queryContent">
					<table>
						<tr>
							<td>信审类型：</td>
							<td>
								<input id="auditType" name="auditType" type="text" 
									data-options="textField:'text',
										    	  valueField:'value',
										    	  value:'1',
										    	  data:[{'text':'审核统计','value':'1'},
										    		    {'text':'审批统计','value':'2'}],
										    		panelHeight:'auto',
										    		editable:false"
									class="textbox easyui-combobox" style="width: 90px;">
							</td>
							<td>审核日期：</td>
							<td>
								<input id="auditDateStart" name="auditDateStart" style="width: 90px;" 
									type="text" editable="false" class="textbox easyui-datebox" />
							
								<span style="font-size: 12px;">&nbsp;至&nbsp;</span>
							
								<input id="auditDateEnd" name="auditDateEnd" style="width: 90px;" 
									type="text" editable="false" class="textbox easyui-datebox" />
							</td>
							<td>
								<input type="button" id="queryBtn" value="查询" class="btn" onclick="submitForm('queryForm')" />
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
    
    <script type="text/javascript">
		function submitForm(fromId) {
			var formAction = "<%=basePath%>report/credit/auditReport/list.do";
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
			submitForm("queryForm");
		});
	</script>
</body>
</html>
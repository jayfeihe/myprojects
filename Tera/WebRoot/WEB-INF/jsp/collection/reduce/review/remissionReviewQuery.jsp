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
<title>减免复核查询</title>
<link href="css/global.css" type="text/css" rel="stylesheet" />
<link href="css/icon.css" type="text/css" rel="stylesheet" />
<link href="css/default/easyui.css" type="text/css" rel="stylesheet" />
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
			<p class="title">
				<a href="javascript:void(0);">减免复核查询</a>
			</p>

			<div class="content">
				<form id="queryForm" action="collection/reduce/review/list.do" method="post"
					target="queryContent">
					<table>
						<tr>
							<td>合同编号:</td>
							<td><input id="contractNo" name="contractNo" type="text"class="textbox easyui-validatebox" /></td>
							<td>客户姓名:</td>
							<td><input id="name" name="name" type="text" class="textbox easyui-validatebox" /></td>
							<td>身份证号:</td>
							<td><input id="idNo" name="idNo" type="text"class="textbox easyui-validatebox" /></td>
						</tr>
						<tr>
							<td>处理状态:</td>
							<td><input id="processStatus" name="processStatus" type="text"editable="false" 
									class="textbox easyui-combobox" /></td>
							<td>逾期天数:</td>
							<td>
								<input id="overdueDaysStart" name="overdueDaysStart" type="text" data-options="min:0,precision:0"
									class="textbox easyui-numberbox" style="width:65px;"/>
							&nbsp;-&nbsp;
								<input id="overdueDaysEnd" name="overdueDaysEnd" type="text" data-options="min:0,precision:0"
									class="textbox easyui-numberbox" style="width:65px;"/>
							</td>
							<td colspan="2"><input type="button" value="查询" class="btn"
								onclick="submitForm('queryForm')" /> <input type="button"
								value="重置" class="btn" onclick="$('#queryForm').form('clear');" />
							</td>
						</tr>
					</table>
				</form>
			</div>

			<div id="queryContent">
				<%--
					查询列表
				--%>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	//添加选项卡
	function addTab(title, url) {
		if ($('#verifyTable').tabs('exists', title)) {
			$('#verifyTable').tabs('select', title);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0"  src="'
					+ url + '" style="width:100%;height:100%;"></iframe>';
			$('#verifyTable').tabs('add', {
				title : title,
				content : content,
				closable : true
			});
		}
	}
	//提交后，删除当前选项卡
	function removeTab() {
		var tab = $('#verifyTable').tabs('getSelected');
		var tabIndex = $('#verifyTable').tabs('getTabIndex', tab);
		$('#verifyTable').tabs('close', tabIndex);
		submitForm("queryForm");//解决Tab提交关闭列表页刷新的问题
	}
</script>

<script type="text/javascript">
	function submitForm(fromId) {
		//去掉 input 输入的 前后空格
		$('#queryForm').find('input').each(function() {
			if ($(this).attr("type") != "file") {
				$(this).val($.trim($(this).val()));
			}
		});
		var formAction = $('#' + fromId).attr("action");
		var targetDiv = $('#' + fromId).attr("target");
		var params = $('#' + fromId).serialize();
		//弹出异步加载 遮罩
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
				$.messager.confirm('消息', '数据加载失败,请联系系统管理员！');
			}
		});
	}
	//页面加载完动作
	$(document).ready(function() {
		$("#processStatus").combobox("clear");
		$('#processStatus').combobox({
			valueField:'keyProp',
			textField:'keyValue',
			value:'0',
			panelHeight:'auto',
			data:[{'keyProp':'0','keyValue':'待处理'},{'keyProp':'1','keyValue':'已处理'}],
			/* loadFilter:function(data){
		   		var opts = $(this).combobox('options');
		   		var emptyRow = {};
				emptyRow[opts.valueField] = '&nbsp;';
				emptyRow[opts.textField] = '全部';
				data.unshift(emptyRow);
				return data;
			} */
		});
		submitForm("queryForm");
	});
</script>

</html>


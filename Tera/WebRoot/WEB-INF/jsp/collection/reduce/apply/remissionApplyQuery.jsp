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
<title>减免申请查询</title>
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
<div id="remissionApply" class="easyui-tabs" data-options="fit:true">
	<div title="减免申请列表">
		<div id="main">
			<div id="part1" class="part">
				<p class="title">
					<a href="javascript:void(0);">减免申请列表</a>
				</p>
	
				<div class="content">
					<form id="queryForm" action="collection/reduce/apply/list.do" method="post"
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
								<td>催收人员:</td>
								<td><input id="collectionUid" name="collectionUid" type="text" editable="false" 
										class="textbox easyui-combobox" /></td>
								<td>申请状态:</td>
								<td><input id="currentState" name="currentState" type="text" editable="false" class="textbox easyui-combobox" /></td>
								<td>逾期天数:</td>
								<td>
									<input id="overdueDaysStart" name="overdueDaysStart" type="text" data-options="min:0,precision:0"
										class="textbox easyui-numberbox" style="width:65px;"/>
								&nbsp;-&nbsp;
									<input id="overdueDaysEnd" name="overdueDaysEnd" type="text" data-options="min:0,precision:0"
										class="textbox easyui-numberbox" style="width:65px;"/>
								</td>
							</tr>
							<tr>
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
	</div>
</div>
</body>
<script type="text/javascript">
	//添加选项卡
	function addTab(title, url) {
		if ($('#remissionApply').tabs('exists', title)) {
			$('#remissionApply').tabs('select', title);
		} else {
			var content = '<iframe name="sss" scrolling="auto" frameborder="0"  src="'
					+ url + '" style="width:100%;height:100%;"></iframe>';
			$('#remissionApply').tabs('add', {
				title : title,
				content : content,
				closable : true
			});
		}
	}
	//提交后，删除当前选项卡
	function removeTab() {
		var tab = $('#remissionApply').tabs('getSelected');
		var tabIndex = $('#remissionApply').tabs('getTabIndex', tab);
		$('#remissionApply').tabs('close', tabIndex);
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
		var tsurl=encodeURI("sys/user/listUserByOrgAndRole.do?orgId=${login_org.orgId}&roleNames=电话催收专员");
		$("#collectionUid").combobox("clear");
		$('#collectionUid').combobox({
			url:tsurl,
			valueField:'loginId',
			textField:'name',
			value:'',
			panelHeight:'auto',
			loadFilter:function(data){
		   		var opts = $(this).combobox('options');
		   		var emptyRow = {};
				emptyRow[opts.valueField] = '';
				emptyRow[opts.textField] = '请选择';
				data.unshift(emptyRow);
				return data;
			} 
		});
		
		$('#currentState').combobox('clear');
		$('#currentState').combobox({
			valueField:'keyProp',
			textField:'keyValue',
			value: '',
			data:[{'keyProp':'','keyValue':'全部'},
			      {'keyProp':'1','keyValue':'减免复核'},
			      {'keyProp':'5','keyValue':'复核否决'},
			      {'keyProp':'2','keyValue':'减免审批'},
			      {'keyProp':'6','keyValue':'审批否决'},
			      {'keyProp':'3','keyValue':'高级审批'},
			      {'keyProp':'4','keyValue':'减免生效'},
			      {'keyProp':'0','keyValue':'减免无效'}],
			panelHeight:'auto'
		});
		submitForm("queryForm");
	});
</script>

</html>


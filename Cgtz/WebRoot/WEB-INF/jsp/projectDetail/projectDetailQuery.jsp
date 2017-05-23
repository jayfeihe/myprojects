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
<title>项目明细表查询</title>
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
<div class="easyui-tabs" id="queryTabs" data-options="fit:true">
<div title="项目明细表">
	<div id="main">
		<div id="part1" class="part">
			<p class="title"><a href="javascript:void(0);">查询</a></p>
			
			<div class="content">
				<form id="queryForm" action="" method="post" target="queryContent">
					<input type="hidden" id="orgName" name="orgName"/>
					<table>
						<tr>
							<%-- <td>分公司:</td>
							<td>
								<input type="text" 
									<c:if test="${login_org.orgId ne '86'  }">value='${login_org.orgId }' readonly='readonly'</c:if>
									class="textbox easyui-combotree" id="org" name="org" />
							</td> --%>
							<td>分公司:</td>
							<c:choose>
							<c:when test="${login_org.orgId ne '86'}">
							<td><input type="text" class="textbox easyui-validatebox" value="${loginOrgName}" readonly="readonly"/></td>
							</c:when>
							<c:otherwise>
							<td><input id="org" name="org"   class="textbox easyui-combobox" 
							    data-options="editable:false,panelHeight:'auto',valueField:'orgId',textField:'orgName',
							    url:'roleDataRelOrgs/listOrgs.do'"/></td>
							</c:otherwise>
							</c:choose>
							<td>贷款类别:</td>
							<td><input class="textbox" id="type" name="type" /></td>
						</tr>
						<tr>
							<td>合同开始日期:</td>
							<td>
								<input id="minStartDate" name="minStartDate" type="text" class="textbox easyui-datebox" 
									data-options="editable:false" style="width: 90px;"/>
								&nbsp;-&nbsp;
								<input id="maxStartDate" name="maxStartDate" type="text" class="textbox easyui-datebox" 
									data-options="editable:false" style="width: 90px;"/>
							</td>
							<!-- <td>是否结束:</td>
							<td><input id="isEnd" name="isEnd" type="text" class="textbox easyui-combobox" data-options="editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
                                 data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'0','keyValue':'否'},{'keyProp':'1','keyValue':'是'}]"/></td> -->
							<td colspan="3">
								<input type="button" value="查询" class="btn" onclick="submitForm('queryForm')"/>
								<input type="button" value="导出" class="btn" onclick="excelExport('queryForm')"/>
								<input type="button" value="重置" class="btn" onclick="$('#queryForm').form('clear');"/>
							</td>
						</tr>
					</table>	
				</form>
			</div>
			
			<div id="queryContent" ></div>
		</div>
	</div>
</div>
</div>
<script type="text/javascript">
//添加选项卡
function addTab(title, url){
    if ($('#queryTabs').tabs('exists', title)){
        $('#queryTabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#queryTabs').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeTab(){
	var tab = $('#queryTabs').tabs('getSelected');
	var tabIndex=$('#queryTabs').tabs('getTabIndex',tab);
	$('#queryTabs').tabs('close',tabIndex);
	submitForm("queryForm");//解决Tab提交关闭列表页刷新的问题
}

function submitForm(fromId) {
	//去掉 input 输入的 前后空格
	$('#queryForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	var formAction = "<%=basePath%>projectInfoDetail/list.do";
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

//导出
function excelExport(fromId) {
	$('#' + fromId).attr("action","<%=basePath%>projectInfoDetail/export.do");
	$('#' + fromId).attr("method","post");
	$('#' + fromId).submit();
}

//页面加载完动作
$(document).ready(function() {
	
	$("#org").combotree({
		url:"sys/org/selectList.do?nodeLevel=2",
		method:'get',
		required:false,
		panelHeight:'auto',
	    onSelect:function(node) {
	    	$("#org").val(node.text);
	    }
	});
	
	/* $('#loanType').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.loanType,
		editable:false,
		panelHeight:'auto',
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
		}
	}); */
	
	submitForm("queryForm");
});
</script>
</body>
</html>


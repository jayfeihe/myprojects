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
<title>黑名单表查询</title>
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
		<p class="title"><a href="javascript:void(0);">黑名单查询</a></p>
		
		<div class="content">
			<form id="queryForm" action="blacklist/list.do" method="post" target="queryContent">
				<table>
					<tr>
						<td>姓名:</td>
						<td><input id="name" name="name" type="text" data-options="validType:['length[0,200]']" class="textbox easyui-validatebox"/></td>
						<td>证件号码:</td>
						<td><input id="idNo" name="idNo" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox"/></td>
						<td>申请号:</td>
						<td><input id="appId" name="appId" type="text" data-options="validType:['length[0,200]']" class="textbox easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>手机:</td>
						<td><input id="mobile" name="mobile" type="text" data-options="validType:['length[0,200]']" class="textbox easyui-validatebox"/></td>
						<td>QQ:</td>
						<td><input id="qq" name="qq" type="text" data-options="validType:['length[0,200]']" class="textbox easyui-validatebox"/></td>
						<td>微信号:</td>
						<td><input id="wechat" name="wechat" type="text" data-options="validType:['length[0,200]']" class="textbox easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>单位名称:</td>
						<td><input id="company" name="company" type="text" data-options="validType:['length[0,200]']" class="textbox easyui-validatebox"/></td>
						<td>状态:</td>
						<td><input id="state" name="state" type="text" class="textbox easyui-combobox"
									data-options="textField:'text',
												  valueField:'value',
												  panelHeight:'auto',
												  value:'1',
												  data:[{'text':'有效','value':'1'},
												  	    {'text':'无效','value':'0'}],
												  editable:false"/></td>
						<td>创建时间：</td>
						<td>
							<input name="createTimeMin" class="easyui-datebox" editable="false"/>
							&nbsp;-&nbsp;
							<input name="createTimeMax" class="easyui-datebox" editable="false"/>
						</td>
					</tr>
					<tr>
						<td>地址:</td>
						<td colspan="5">
							<input id="addProvince" name="addProvince" type="text" class="easyui-combobox" editable="false" /> 省
							<input id="addCity" name="addCity" type="text" class="easyui-combobox" editable="false" /> 市
							<input id="addCounty" name="addCounty" type="text" class="easyui-combobox" editable="false" /> 区县
							<input id="address" name="address" type="text" class="textbox"/>
						</td>
					</tr>

					<tr>
						<td colspan="2">
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
			$.messager.confirm('消息', '数据加载失败,请联系系统管理员！');
		}
	});
}
//页面加载完动作
$(document).ready(function() {
	//地址省份
    var provinceurl = "sys/datadictionary/listjason.do?keyName=province";
		$("#addProvince").combobox("clear");
		$('#addProvince').combobox({
			url: provinceurl,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
            $('#addCity').combobox('clear');
            $('#addCounty').combobox('clear');
            var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
            $('#addCity').combobox('reload',cityurl); 
        }
		});
    //地址市
    var province = $('#addProvince').combobox('getValue');
		var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(province);
		$("#addCity").combobox("clear");
		$('#addCity').combobox({
			url: cityurl,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
            $('#addCounty').combobox('clear');
            var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
            $('#addCounty').combobox('reload',countyurl); 
        }
		});
    //地址区县
    var city = $('#addCity').combobox('getValue');
		var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(city);
		$("#addCounty").combobox("clear");
		$('#addCounty').combobox({url: countyurl, valueField: 'keyProp',textField: 'keyValue'});

	submitForm("queryForm");
});
</script>

</html>


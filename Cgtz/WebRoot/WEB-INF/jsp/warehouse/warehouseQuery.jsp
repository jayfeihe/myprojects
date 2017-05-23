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
<title>T_WAREHOUSE查询</title>
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
<div class="easyui-tabs" data-options="fit:true" id="queryWareHouseTabs">
<div title="仓库维护列表">
	<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">查询</a></p>
		
		<div class="content">
			<form id="queryWareHouseForm" action="warehouse/list.do" method="post" target="queryWareHouseContent">
				<table>
					<tr>
<td>仓库名称:</td>
<td><input id="id" name="id" type="text"  class="textbox easyui-combobox"
    data-options="editable:false,panelHeight:'auto',valueField:'id',textField:'name',
    url:'warehouse/listWarehouse.do'"/></td>
<td>分公司:</td>
<td><input id="org" name="org" <c:if test="${login_org.orgId ne '86'  }">value='${login_org.orgId }' readonly='readonly'</c:if> class="textbox easyui-combotree"
data-options="editable:false,panelHeight:'auto',url:'<%=basePath%>sys/org/selectList.do?nodeLevel=2',method:'get'"/></td>
</tr>
<tr id="tr_address">
<td>所在省:</td>
<td><input id="prvn" name="prvn" type="text"  data-options="editable:false,validType:['length[0,20]']" class="textbox easyui-validatebox"/></td>

<td>所在市:</td>
<td><input id="city" name="city" type="text" data-options="editable:false,validType:['length[0,30]']" class="textbox easyui-validatebox" /></td>

<td>所在区/县:</td>
<td><input id="ctry" name="ctry" type="text" data-options="editable:false,validType:['length[0,30]']" class="textbox easyui-validatebox" /></td>

<td>所在地址:</td>
<td><input id="addr" name="addr" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox"/></td>
</tr>

<tr>
					    <td></td>
						<td>
							<input type="button" value="查询" class="btn" onclick="submitForm('queryWareHouseForm')"/>
							<input type="button" value="重置" class="btn" onclick="$('#queryWareHouseForm').form('clear');"/>
						</td>
						<td></td>
					</tr>
				</table>	
			</form>
		</div>
		
		<div id="queryWareHouseContent" >
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
    if ($('#queryWareHouseTabs').tabs('exists', title)){
        $('#queryWareHouseTabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#queryWareHouseTabs').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeTab(){
	var tab = $('#queryWareHouseTabs').tabs('getSelected');
	var tabIndex=$('#queryWareHouseTabs').tabs('getTabIndex',tab);
	$('#queryWareHouseTabs').tabs('close',tabIndex);
	submitForm("queryWareHouseForm");//解决Tab提交关闭列表页刷新的问题
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
	$('#queryWareHouseForm').find('input').each(function(){
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
//加载省市县
function address(id){
	var coll=$(id);
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
		coll.find('#ctry').combobox({url: countyurl, valueField: 'keyProp',textField: 'keyValue'});
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
    address("#tr_address");
	submitForm("queryWareHouseForm");
	
});
</script>

</html>


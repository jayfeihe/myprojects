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
<title>黑名单表更新</title>
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
		<p class="title"><a href="javascript:void(0);">黑名单更新</a></p>
		<div class="content">
			<form id="updateForm" >
				<input id="id" name="id" type="hidden" size="35" value="${ bean.id}" />
				<input id="state" name="state" type="hidden" size="35" value="1" />
				<table>
					<tbody>
						<tr>
							<td>姓名:</td>
							<td><input id="name" name="name" type="text" data-options="validType:['length[0,200]']" class="textbox easyui-validatebox" value="${bean.name}"/></td>
							<td>证件号码:</td>
							<td><input id="idNo" name="idNo" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.idNo}"/></td>
							<td>申请号:</td>
							<td><input id="appId" name="appId" type="text" data-options="validType:['length[0,200]']" class="textbox easyui-validatebox" value="${bean.appId}"/></td>
						</tr>
						
						<tr>
							<td>手机:</td>
							<td><input id="mobile" name="mobile" type="text" data-options="validType:['length[0,200]']" class="textbox easyui-validatebox" value="${bean.mobile}"/></td>
							<td>QQ:</td>
							<td><input id="qq" name="qq" type="text" data-options="validType:['length[0,200]']" class="textbox easyui-validatebox" value="${bean.qq}"/></td>
							<td>微信号:</td>
							<td><input id="wechat" name="wechat" type="text" data-options="validType:['length[0,200]']" class="textbox easyui-validatebox" value="${bean.wechat}"/></td>
							<td>单位名称:</td>
							<td><input id="company" name="company" type="text" data-options="validType:['length[0,200]']" class="textbox easyui-validatebox" value="${bean.company}"/></td>
						</tr>
						<tr>
							<td>地址:</td>
							<td colspan="5">
								<input id="addProvince" name="addProvince" type="text" class="easyui-combobox" editable="false"  value="${bean.addProvince}" /> 省
								<input id="addCity" name="addCity" type="text" class="easyui-combobox" editable="false" value="${bean.addCity}"/> 市
								<input id="addCounty" name="addCounty" type="text" class="easyui-combobox" editable="false" value="${bean.addCounty}"/> 区县
								<input id="address" name="address" type="text" class="textbox" value="${bean.address}"/>
							</td>
						</tr>
						<tr>
							<td>逾期平台:</td>
							<td><input id="platform" name="platform" type="text" data-options="validType:['length[0,200]']" class="textbox easyui-validatebox" value="${bean.platform}"/></td>
							
							<td>逾期天数:</td>
							<td><input id="defaultDays" name="defaultDays" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.defaultDays}"/></td>
							
							<td>逾期笔数:</td>
							<td><input id="defaultNum" name="defaultNum" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.defaultNum}"/></td>
							<td>逾期金额:</td>
							<td><input id="defaultAmount" name="defaultAmount" type="text" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.defaultAmount}"/></td>
						</tr>
						<tr>
							<td>备注:</td>
							<td colspan="4"><textarea id="remark" name="remark" class="textbox"
									style="resize: none;width:400px;height:60px!important;">${bean.remark}</textarea></td>
						</tr>
						
						<tr>
							<td colspan="2">
								<input type="button" value="保存" class="btn" onclick="updateFunction()"/>
								<input type="button" value="返回" class="btn" onclick="javascript:back()"/>
							</td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>

</body>
<script type="text/javascript">
//更新保存
function updateFunction() {
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	//验证表单验证是否通过
	if(false == $('#updateForm').form('validate') ){
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}
	//弹出异步加载 遮罩
	openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "blacklist/save.do",
		data : encodeURI(params),
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
//	                var url= "<%=basePath%>" + "blacklist/query.do";
//					window.location=url;
					window.history.go(-1);
            	});
            } else {				
    			$.messager.alert('消息', data.message);
				//按钮生效
				$(".btn").removeAttr("disabled");
            }
		},
		error : function() {
			//关闭遮罩，弹出消息框
			closeMask();
			$.messager.confirm('消息', '数据加载失败,请联系系统管理员！');
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
	});
}

//返回
function back(){
	window.history.go(-1);
}

//页面加载完动作
$(document).ready(function (){
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
});

</script>
</html>


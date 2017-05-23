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
<title>T_WAREHOUSE更新</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
<style type="text/css">
.require{color:red;}
</style>
</head>
<body>
<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">
		<c:choose>
		<c:when test="${bean.name==null}">添加仓库</c:when>
		<c:otherwise>仓库更新</c:otherwise>
		</c:choose>
		</a></p>
		<div class="content">
			<form id="updateForm" >
				<table>
					<tbody>
					<input id="id" name="id" type="hidden" size="35" value="${ bean.id}" />
                    <input id="login_id" name="login_id" type="hidden" size="35" value="${loginid}" />
<tr>
<td><span class="require">*</span>仓库名称:</td>
<td><input id="name" name="name" <c:if test="${not empty bean.name}">readonly="readonly"</c:if>  type="text" required="true" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.name}"/></td>
<td><span class="require">*</span>所属机构:</td>
<td><input id="org" name="org"  type="text" class="textbox easyui-combotree" value="${bean.org}"
    data-options="url:'<%=basePath%>sys/org/selectList.do',
				 	method:'get',
				 	valueField:'orgId',
				 	textField:'orgName',
					required:false,
					panelHeight:'auto',
				 	editable:false"/></td>

<td><span class="require">*</span>负责人:</td>
<td><input id="owner" name="owner" type="text" required="true" data-options="validType:['length[2,20]','CHS']" class="textbox easyui-validatebox" value="${bean.owner}"/></td>
<td><span class="require">*</span>联系方式:</td>
<td><input id="tel" name="tel" type="text" required="true" data-options="validType:['length[5,20]']" class="textbox easyui-numberbox" value="${bean.tel}"/></td>
</tr>
<tr id="tr_address">
<td><span class="require">*</span>所在省:</td>
<td><input id="prvn" name="prvn" <c:if test="${not empty bean.name}">readonly="readonly"</c:if> type="text" required="true" editable="false" data-options="validType:['length[0,20]']" class="textbox easyui-combobox" value="${bean.prvn}"/></td>

<td><span class="require">*</span>所在市:</td>
<td><input id="city" name="city" <c:if test="${not empty bean.name}">readonly="readonly"</c:if> type="text" required="true" editable="false" data-options="validType:['length[0,30]']" class="textbox easyui-combobox" value="${bean.city}"/></td>

<td><span class="require">*</span>所在县/区:</td>
<td><input id="ctry" name="ctry" <c:if test="${not empty bean.name}">readonly="readonly"</c:if> type="text" required="true" editable="false" data-options="validType:['length[0,30]']" class="textbox easyui-combobox" value="${bean.ctry}"/></td>

<td><span class="require">*</span>所在地址:</td>
<td><input id="addr" name="addr" <c:if test="${not empty bean.name}">readonly="readonly"</c:if> type="text" required="true"  data-options="validType:['length[3,100]']" class="textbox easyui-validatebox" value="${bean.addr}"/></td>
</tr>

<tr>
<td>&nbsp;&nbsp;说明:</td>
<td colspan="8"><textarea id="remark" name="remark"  data-options="validType:['length[0,500]']" class="textbox easyui-validatebox" style="resize: none;width:625px;height:50px!important;" value="${bean.remark}">${bean.remark}</textarea></td>
</tr>

					<tr><td></td>
						<td>
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
		url : "<%=basePath%>" + "warehouse/save.do",
		data : encodeURI(params),
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
//	                var url= "<%=basePath%>" + "warehouse/query.do";
//					window.location=url;
					window.parent.removeTab();
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
//加载省市县地址
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
$(document).ready(function (){
	//填充select数据样例
	/*<%--
	var tsurl="sys/datadictionary/listjason.do?keyName=repaymethod";
	$("#repayMethod").combobox("clear");
	$('#repayMethod').combobox({url:tsurl, valueField:'keyProp', textField:'keyValue'});
	--%>*/
	 //$("#org").combobox({
	 	//url:'<%=basePath%>sys/org/userOrg.do?loginid=${loginid}',
	 	//valueField:'orgName',
	 	//textField:'orgName',
	 	//editable:false,
	 	//panelHeight:'auto'
	 //});*/
	address("#tr_address");
});

</script>
</html>


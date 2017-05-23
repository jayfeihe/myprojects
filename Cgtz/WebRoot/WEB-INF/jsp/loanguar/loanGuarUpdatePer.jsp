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
<title>T_LOAN_GUAR更新</title>
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
		<p class="title"><a href="javascript:void(0);">
		<c:choose>
		<c:when test="${bean.name==null}">添加担保人</c:when>
		<c:otherwise>担保人更新</c:otherwise>
		</c:choose>
		</a></p>
		<div class="content">
			<form id="updatePerForm" >
				<table>
					<tbody>
					<input id="id" name="id" type="hidden" size="35" value="${bean.id }"/>
					<input id="loanId" name="loanId" type="hidden" size="35" value="${loanId}" />
					<input id="type" name="type" type="hidden" size="35" value="1"/>
<tr>
<td>担保人姓名:</td>
<td><input id="name" name="name" type="text" required="true" data-options="validType:['length[2,50]']" class="textbox easyui-validatebox" value="${bean.name}"/></td>
<td>性别:</td>
<td><input id="sex" name="sex" type="text"  class="textbox easyui-combobox" value="${bean.sex}" 
data-options="required:true,editable:false,panelHeight:'auto',valueField:'keyProp',textField:'keyValue',
data:[{'keyProp':'','keyValue':'请选择'},{'keyProp':'M','keyValue':'男'},{'keyProp':'F','keyValue':'女'}]"/>
</td>
<td>学历:</td>
<td><input id="education" name="edu" type="text" required="true" editable="false" data-options="validType:['length[0,10]']" class="textbox easyui-combobox" value="${bean.edu}"/> 
</td>
</tr>
<tr>
<td>证件类型:</td>
<td><input id="idType" name="idType" required="true" type="text" editable="false"  class="textbox easyui-combobox" value="${bean.idType}"
            data-options="required:true,editable:false,panelHeight:'auto',
              valueField:'keyProp',
	          textField:'keyValue',
	          data:dataDictJson.applyIdType"/>
</td>

<td>证件号码:</td>
<td><input id="idNo" name="idNo" type="text" required="true" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.idNo}"/></td>

<td>证件有效期:</td>
<td><input id="validType" name="validType" type="text" editable="false" class="textbox easyui-datebox" value="${bean.validTypeStr}"/></td>

</tr>
<tr>
<td>手机号:</td>
<td><input id="tel" name="tel" type="text" required="true" data-options="validType:['mobile','length[11,12]']" class="textbox easyui-numberbox" value="${bean.tel}"/></td>

<td>备用手机号:</td>
<td><input id="tel2" name="tel2" type="text" data-options="validType:['mobile','length[11,12]','mobile']" class="textbox easyui-numberbox" value="${bean.tel2}"/></td>

<td>婚姻状况:</td>
<td><input id="marriage" name="marriage" type="text" required="true" editable="false"  class="textbox easyui-combobox" value="${bean.marriage}"/>
</td>

</tr>
<tr id="home">
<td>户籍省份:</td>
<td><input id="homePrvn" name="homePrvn" required="true" editable="false"  data-options="validType:['length[0,20]']" class="textbox easyui-combobox" value="${bean.homePrvn}"/></td>

<td>城市:</td>
<td><input id="homeCity" name="homeCity" required="true" editable="false"  data-options="validType:['length[0,30]']" class="textbox easyui-combobox" value="${bean.homeCity}"/></td>

<td>区/县:</td>
<td><input id="homeCtry" name="homeCtry" required="true" editable="false"  data-options="validType:['length[0,30]']" class="textbox easyui-combobox" value="${bean.homeCtry}"/></td>

<td>户籍地址:</td>
<td><input id="homeAddr" name="homeAddr" required="true" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.homeAddr}"/></td>
</tr>

<tr id="now">
<td>现居省份:</td>
<td><input id="nowPrvn" name="nowPrvn" required="true" editable="false" type="text"  data-options="validType:['length[0,20]']" class="textbox easyui-combobox" value="${bean.nowPrvn}"/></td>

<td>城市:</td>
<td><input id="nowCity" name="nowCity" required="true" editable="false" type="text"  data-options="validType:['length[0,30]']" class="textbox easyui-combobox" value="${bean.nowCity}"/></td>

<td>区/县:</td>
<td><input id="nowCtry" name="nowCtry" required="true" editable="false" type="text"  data-options="validType:['length[0,30]']" class="textbox easyui-combobox" value="${bean.nowCtry}"/></td>

<td>居住地址:</td>
<td><input id="nowAddr" name="nowAddr" required="true" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.nowAddr}"/></td>
</tr>



<tr>
<td>业务员备注:</td>
<td colspan="8"><textarea id="saleRemark" name="saleRemark" type="text" data-options="validType:['length[0,500]']" class="textbox easyui-validatebox" value="${bean.saleRemark}" style="resize:none;width:625px;height:50px!important;">${bean.saleRemark}</textarea></td>
</tr>
					<tr>
						<td colspan="2">
							<input type="button" value="保存" class="btn" onclick="updatePerFunction()"/>
							<!-- <input type="button" value="返回" class="btn" onclick="javascript:back()"/> -->
						</td>
						<td></td>
					</tr>
					</tbody>
				</table>
			</form>
			<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>影像</strong></div><hr color="#D3D3D3"/>
			<jsp:include page="/files/load.do">
				<jsp:param value="${loanId }" name="loId"/>
				<jsp:param value="filesce5" name="sec"/>
				<jsp:param value="${ bean.id}" name="bizKey"/>
				<jsp:param value="0" name="opt"/>
			</jsp:include>
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
function updatePerFunction() {
	//验证表单验证是否通过
	if(false == $('#updatePerForm').form('validate') ){
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}
	var now=new Date();
	//证件有效期不能早于当前日期	
	var validType=$("#validType").datebox('getValue');
	var myValidType=new Date(validType);
	if(myValidType<now){
		$.messager.confirm('消息', "证件有效期不能早于当前日期！");
		return;
	}
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	//弹出异步加载 遮罩
	openMask();
	var params = $('#updatePerForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "loanguar/save.do?id=${bean.id}",
		data : encodeURI(params),
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					//window.parent.refreshTab("appUpdateTabs");
					var url = '<%=basePath%>' + 'loanguar/update.do?id='+data.id+'&loanId='+data.loanId;
					window.parent.refreshGuarTab("guarQueryTabs",url);
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
//加载地址
function address(id){
	var coll=$("#"+id);//#home行
		//填充select数据 省份
		var provinceurl = "sys/datadictionary/listjason.do?keyName=province";
		coll.find("#"+id+"Prvn").combobox({
		url: provinceurl,
		valueField: 'keyProp',
		textField: 'keyValue',
		onChange: function(newValue, oldValue){
				coll.find("#"+id+"City").combobox('clear');
				coll.find("#"+id+"Ctry").combobox('clear');
				var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
				coll.find("#"+id+"City").combobox('reload',cityurl);		
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
		var province = coll.find("#"+id+"Prvn").combobox('getValue');
		var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" +encodeURI(province);
		coll.find("#"+id+"City").combobox({
			url: cityurl,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
				coll.find("#"+id+"Ctry").combobox('clear');
				var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
				coll.find("#"+id+"Ctry").combobox('reload',countyurl); 
		    }
		});
		//填充select数据 区县
		var city = coll.find("#"+id+"City").combobox('getValue');
		coll.find("#"+id+"City").combobox('setValue',city);
		var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(city);
		coll.find("#"+id+"Ctry").combobox({url: countyurl, valueField: 'keyProp',textField: 'keyValue'});
}
//读取数据字典数据
function listMsg(type){
   if(type=="edu"){
     type="education";
   }
   $("#"+type).combobox({
	          url:"sys/datadictionary/listjason.do?keyName="+type,
	          valueField:'keyProp',
	          textField:'keyValue',
	          panelHeight:'auto',
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
}
   

//页面加载完动作
$(document).ready(function (){
	//填充select数据样例
	address("home");
	address("now");
	listMsg("marriage");
	listMsg("education");
});

</script>
</html>


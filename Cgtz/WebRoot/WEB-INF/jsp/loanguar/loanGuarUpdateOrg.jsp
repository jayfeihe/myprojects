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
		<c:when test="${bean.name==null}">添加担保机构</c:when>
		<c:otherwise>担保机构更新</c:otherwise>
		</c:choose>
		</a></p>
		<div class="content">
			<form id="updateOrgForm" >
				<table>
					<tbody>
					<input id="id" name="id" type="hidden" size="35" value="${ bean.id}" />	
					<input id="loanId" name="loanId" type="hidden" size="35" value="${loanId}" />			
                    <input id="type" name="type" type="hidden" size="35" value="2"/>
<tr>
<td>机构名称:</td>
<td><input id="name" name="name" required="true" type="text" data-options="validType:['length[2,50]']" class="textbox easyui-validatebox" value="${bean.name}"/></td>

<td>注册时间:</td>
<td><input id="orgRegTime" name="orgRegTime" required="true" type="text" editable="false" class="textbox easyui-datebox" value="${bean.orgRegTimeStr}"/></td>

<td>注册资本:</td>
<td colspan="3"><input id="orgRegAmt" name="orgRegAmt" type="text" required="true" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.orgRegAmt}"/>元</td>
</tr>
<tr>
<td>证件类型:</td>
<td><input id="idType" name="idType" type="text"  data-options="required:true,editable:false,valueField:'keyProp',
															                                    textField:'keyValue',
															                                     data:dataDictJson.companyIdType,
															                                     panelHeight:'auto'" 
class="textbox easyui-combobox" value="${bean.idType}"/>
</td>

<td>证件号码:</td>
<td><input id="idNo" name="idNo" type="text" required="true" data-options="validType:['length[0,20]','idNo']" class="textbox easyui-validatebox" value="${bean.idNo}"/></td>

<td>证件有效期:</td>
<td><input id="validType" name="validType" type="text" editable="false" class="textbox easyui-datebox" value="${bean.validTypeStr}"/></td>

<td>机构经营范围:</td>
<td><input id="orgBus" name="orgBus" type="text" required="true" data-options="validType:['length[0,500]']" class="textbox easyui-validatebox" value="${bean.orgBus}"/></td>
</tr>
<tr>
<td>法人姓名:</td>
<td><input id="legalName" name="legalName" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.legalName}"/></td>

<td>法人手机号:</td>
<td><input id="legalTel" name="legalTel" type="text" data-options="validType:['length[11,12]','mobile']" class="textbox easyui-numberbox" value="${bean.legalTel}"/></td>

<td>法人证件类型:</td>
<td><input id="legalIdType" name="legalIdType" type="text" data-options="editable:false,valueField:'keyProp',
															                                    textField:'keyValue',
															                                     data:dataDictJson.applyIdType,
															                                     panelHeight:'auto'"
	  class="textbox easyui-combobox" value="${bean.legalIdType}"/>
</td>

<td>法人证件号码:</td>
<td><input id="legalIdNo" name="legalIdNo" type="text" data-options="validType:['length[0,20]','idNo']" class="textbox easyui-validatebox" value="${bean.legalIdNo}"/></td>
</tr>
<tr>
<td>股东姓名:</td>
<td><input id="shareName" name="shareName" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.shareName}"/></td>

<td>股东手机号:</td>
<td><input id="shareTel" name="shareTel" type="text" data-options="validType:['length[11,12]','mobile']" class="textbox easyui-numberbox" value="${bean.shareTel}"/></td>

<td>股东证件类型:</td>
<td><input id="shareIdType" name="shareIdType" type="text" data-options="editable:false,valueField:'keyProp',
															                                    textField:'keyValue',
															                                     data:dataDictJson.applyIdType,
															                                     panelHeight:'auto'" 
class="textbox easyui-combobox" value="${bean.shareIdType}"/>
</td>

<td>股东证件号码:</td>
<td><input id="shareIdNo" name="shareIdNo" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.shareIdNo}"/></td>
</tr>
<tr id="home">
<td>注册所在省份:</td>
<td><input id="homePrvn" name="homePrvn" editable="false"  data-options="validType:['length[0,20]']" class="textbox easyui-combobox" value="${bean.homePrvn}"/></td>

<td>城市:</td>
<td><input id="homeCity" name="homeCity" required="true" editable="false"  data-options="validType:['length[0,30]']" class="textbox easyui-combobox" value="${bean.homeCity}"/></td>

<td>区/县:</td>
<td><input id="homeCtry" name="homeCtry" required="true" editable="false"  data-options="validType:['length[0,30]']" class="textbox easyui-combobox" value="${bean.homeCtry}"/></td>

<td>注册地址:</td>
<td><input id="homeAddr" name="homeAddr" required="true" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.homeAddr}"/></td>
</tr>
<tr id="now">
<td>实际经营所在省份:</td>
<td><input id="nowPrvn" name="nowPrvn" required="true" editable="false" type="text"  data-options="validType:['length[0,20]']" class="textbox easyui-combobox" value="${bean.nowPrvn}"/></td>

<td>城市:</td>
<td><input id="nowCity" name="nowCity" required="true" editable="false" type="text"  data-options="validType:['length[0,30]']" class="textbox easyui-combobox" value="${bean.nowCity}"/></td>

<td>区/县:</td>
<td><input id="nowCtry" name="nowCtry" required="true" editable="false" type="text"  data-options="validType:['length[0,30]']" class="textbox easyui-combobox" value="${bean.nowCtry}"/></td>

<td>经营地址:</td>
<td><input id="nowAddr" name="nowAddr" required="true" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.nowAddr}"/></td>
</tr>

<tr>
<td>业务员备注:</td>
<td colspan="8"><textarea id="saleRemark" name="saleRemark" type="text" data-options="validType:['length[0,500]']" class="textbox easyui-validatebox" value="${bean.saleRemark}" style="resize:none;width:625px;height:50px!important;">${bean.saleRemark}</textarea></td>
</tr>
					<tr>
						<td colspan="2">
							<input type="button" value="保存" class="btn" onclick="updateOrgFunction()"/>
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
				<jsp:param value="filesce6" name="sec"/>
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
function updateOrgFunction() {
	//验证表单验证是否通过
	if(false == $('#updateOrgForm').form('validate') ){
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}
	var now=new Date();
	//注册日期不能晚于当前日期
	var orgRegTime=$("#orgRegTime").datebox('getValue'); 
	var myRegDate=new Date(orgRegTime);
	if(myRegDate>now){
		$.messager.confirm('消息', "注册日期不能晚于当前日期！");
		return;
	}
	//证件有效期不能早于当前日期
	var validType=$("#validType").datebox('getValue'); 
	var myValidType=new Date(validType);
	if(myValidType<now){
		$.messager.confirm('消息', "证件有效期不能早于当前日期！");
		return;
	}
	//去掉 input 输入的 前后空格
	$('#updateOrgForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	//弹出异步加载 遮罩
	openMask();
	var params = $('#updateOrgForm').serialize();
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
//	               //window.parent.refreshTab("appUpdateTabs");
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
function addressOrg(id){
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
//页面加载完动作
$(document).ready(function (){
	//填充select数据样例
	
	addressOrg("home");
	addressOrg("now");
});

</script>
</html>


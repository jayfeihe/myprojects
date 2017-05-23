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
<title>出借人基本信息维护查看</title>
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
		<p class="title"><a href="javascript:void(0);">查看</a></p>
		<div class="content">
			<form id="updateForm" >
				<table>
					<tbody>
					<input id="id" name="id" type="hidden" size="35" value="${ bean.id}" />
					<tr>
<td>标识:</td>
<td><input id="name" name="name" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.name}"/></td>
<td>真实姓名:</td>
<td><input id="realName" name="realName" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.realName}"/></td>

<td>性别:</td>
<td><input id="gender" name="gender" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" <c:if test="${bean.gender eq 'M' }">value="男"</c:if><c:if test="${bean.gender eq 'F' }">value="女"</c:if>/></td>

<td>身份证号:</td>
<td><input id="idNo" name="idNo" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.idNo}"/></td>
</tr>
<tr>
<td>EMAIL:</td>
<td><input id="email" name="email" type="text" data-options="validType:['email','length[0,50]']" class="textbox easyui-validatebox" value="${bean.email}"/></td>

<td>固定电话:</td>
<td><input id="phone" name="phone" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.phone}"/></td>

<td>手机:</td>
<td><input id="mobile" name="mobile" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.mobile}"/></td>
</tr>
<tr>
<td>户籍所在省:</td>
<td><input id="homePrvn" name="homePrvn" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.homePrvn}"/></td>

<td>户籍所在市:</td>
<td><input id="homeCity" name="homeCity" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.homeCity}"/></td>
<td>户籍所在县/区:</td>
<td><input id="homeCtry" name="homeCtry" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.homeCtry}"/></td>
<td>户籍地址:</td>
<td><input id="homeAddr" name="homeAddr" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.homeAddr}"/></td>
</tr>
<tr>
<td>现居地所在省:</td>
<td><input id="nowPrvn" name="nowPrvn" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.nowPrvn}"/></td>

<td>现居地所在市:</td>
<td><input id="nowCity" name="nowCity" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.nowCity}"/></td>

<td>现居地所在县/区:</td>
<td><input id="nowCtry" name="nowCtry" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.nowCtry}"/></td>

<td>现居地地址:</td>
<td><input id="nowAddr" name="nowAddr" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.nowAddr}"/></td>
</tr>

<%-- <tr>
<td>开户名:</td>
<td><input id="acctName" name="acctName" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.acctName}"/></td>

<td>开户行:</td>
<td><input id="acctBank" name="acctBank" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.acctBank}"/></td>

<td>收款账号:</td>
<td><input id="acctCode" name="acctCode" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.acctCode}"/></td>
</tr> --%>

<tr>
	<td>开户名:</td>
	<td>
		<input id="acctName" name="acctName" type="text" class="textbox easyui-validatebox" 
			data-options="required:true,validType:['length[0,20]']" value="${bean.acctName}"/>
	</td>
	<td>收款账号:</td>
	<td><input id="acctCode" name="acctCode" type="text" class="textbox easyui-numberbox"
			data-options="required:true" value="${bean.acctCode}"/>
	</td>
</tr>
<tr>
	<td>开户行:</td>
	<td colspan="8">
		<input id="acctPrvn" name="acctPrvn" type="text" class="textbox easyui-validatebox"
			data-options="required:true,editable:false" value="${bean.acctPrvn}"/>省
		<input id="acctCity" name="acctCity" type="text" class="textbox easyui-validatebox" 
			data-options="required:true,editable:false" value="${bean.acctCity}"/>市
		<%-- <input id="acctCtry" name="acctCtry" type="text" class="textbox easyui-combobox" 
			data-options="required:true,editable:false" value="${bean.acctCtry}"/>区/县 --%>
		<input id="acctBank" name="acctBank" type="text" class="textbox easyui-validatebox"
			data-options="required:true,editable:false" value="${bean.acctBank}"/>开户行
		<input id="acctBranch" name="acctBranch" type="text" class="textbox easyui-validatebox"
			data-options="required:true,editable:true" value="${bean.acctBranch}" style="width: 250px;"/>支行
	</td>
</tr>

<tr>
<td>余额:</td>
<td><input id="amt" name="amt" type="text" editable="false"  data-options="precision:2" class="textbox easyui-numberbox" value="${bean.amt}"/>元</td>
</tr>
<tr>
<td>说明:</td>
<td colspan="8"><textarea id="remark" name="remark" disabled="disabled"  data-options="validType:['length[0,200]']" class="textbox easyui-validatebox" value="${bean.remark}" style="resize:none;width:625px;height:50px!important;">${bean.remark}</textarea></td>
</tr>
					<tr>
						<td>
							<!-- <input type="button" value="返回" class="btn" onclick="javascript:back()"/> -->
						</td>
						<td></td>
					</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>

<div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
	<img src="img/loading.gif" alt="加载中..." />
</div>

</body>

<script type="text/javascript">

//返回
function back(){
	window.history.go(-1);
}

//打开Loading遮罩并修改样式
function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
}


//页面加载完动作
$(document).ready(function (){
	//将Form元素禁用
	$("#updateForm").find("input").attr("disabled", "disabled");
	$("#updateForm").find("select").attr("disabled", "disabled");
	//将easyui控件禁用
	$("#updateForm").find(".easyui-combobox").combo('disable');
	$("#updateForm").find(".easyui-numberbox").numberbox('disable');
	$("#updateForm").find(".easyui-numberspinner").numberspinner('disable');
	$("#updateForm").find(".easyui-datebox").datebox('disable');
	$("#updateForm").find(".easyui-datetimebox").datetimebox('disable');
	
	//填充select数据样例
	/*
	var tsurl="sys/datadictionary/listjason.do?keyName=repaymethod";
	$("#repayMethod").combobox("clear");
	$('#repayMethod').combobox({url:tsurl, valueField:'keyProp', textField:'keyValue'});
	*/
	
});

</script>
</html>


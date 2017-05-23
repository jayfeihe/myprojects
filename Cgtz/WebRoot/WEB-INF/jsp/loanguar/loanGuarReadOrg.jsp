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
<title>T_LOAN_GUAR查看</title>
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
		<p class="title"><a href="javascript:void(0);">查看担保机构</a></p>
		<div class="content">
			<form id="updateForm" >
				<table>
					<tbody>
					<input id="id" name="id" type="hidden" size="35" value="${ bean.id}" />
					<input id="loanId" name="loanId" type="hidden" size="35" value="${ bean.loanId}" />			
<tr>
<td>机构名称:</td>
<td><input id="name" name="name" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.name}"/></td>

<td>注册时间:</td>
<td><input id="orgRegTime" name="orgRegTime" type="text" editable="false" class="textbox easyui-datebox" value="${bean.orgRegTimeStr}"/></td>

<td>注册资本:</td>
<td><input id="orgRegAmt" name="orgRegAmt" type="text" required="true" editable="false"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.orgRegAmt}"/>元</td>
</tr>
<tr>
<td>证件类型:</td>
<td><input id="idType" name="idType" type="text" data-options="validType:['length[0,10]']" class="textbox easyui-validatebox" 
<c:choose>
<c:when test="${bean.idType eq '04' }">value="营业执照"</c:when>
<c:when test="${bean.idType eq '05' }">value="组织机构代码证"</c:when>
<c:when test="${bean.idType eq '06' }">value="税务登记证"</c:when>
</c:choose>/>
</td>

<td>证件号码:</td>
<td><input id="idNo" name="idNo" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.idNo}"/></td>

<td>证件有效期:</td>
<td><input id="validType" name="validType" type="text"  editable="false" class="textbox easyui-datebox" value="${bean.validTypeStr}"/></td>

<td>机构经营范围:</td>
<td><input id="orgBus" name="orgBus" type="text" data-options="validType:['length[0,500]']" class="textbox easyui-validatebox" value="${bean.orgBus}"/></td>

</tr>
<tr>
<td>法人姓名:</td>
<td><input id="legalName" name="legalName" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.legalName}"/></td>

<td>法人手机号:</td>
<td><input id="legalTel" name="legalTel" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.legalTel}"/></td>

<td>法人证件类型:</td>
<td><input id="legalIdType" name="legalIdType" type="text" data-options="validType:['length[0,10]']" class="textbox easyui-validatebox" 
<c:choose>
<c:when test="${bean.legalIdType eq '01' }">value="身份证"</c:when>
<c:when test="${bean.legalIdType eq '02' }">value="军官证"</c:when>
<c:when test="${bean.legalIdType eq '03' }">value="驾驶证"</c:when>
</c:choose>/>
</td>


<td>法人证件号码:</td>
<td><input id="legalIdNo" name="legalIdNo" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.legalIdNo}"/></td>
</tr>
<tr>
<td>股东姓名:</td>
<td><input id="shareName" name="shareName" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.shareName}"/></td>

<td>股东手机号:</td>
<td><input id="shareTel" name="shareTel" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.shareTel}"/></td>

<td>股东证件类型:</td>
<td><input id="shareIdType" name="shareIdType" type="text" data-options="validType:['length[0,10]']" class="textbox easyui-validatebox" 
<c:choose>
<c:when test="${bean.shareIdType eq '01' }">value="身份证"</c:when>
<c:when test="${bean.shareIdType eq '02' }">value="军官证"</c:when>
<c:when test="${bean.shareIdType eq '03' }">value="驾驶证"</c:when>
</c:choose>/>
</td>

<td>股东证件号码:</td>
<td><input id="shareIdNo" name="shareIdNo" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.shareIdNo}"/></td>
</tr>


<tr>
<td>注册所在省:</td>
<td><input id="homePrvn" name="homePrvn" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.homePrvn}"/></td>

<td>注册所在市:</td>
<td><input id="homeCity" name="homeCity" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.homeCity}"/></td>

<td>注册所在县:</td>
<td><input id="homeCtry" name="homeCtry" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.homeCtry}"/></td>

<td>注册地址:</td>
<td><input id="homeAddr" name="homeAddr" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.homeAddr}"/></td>
</tr>
<tr>
<td>实际经营所在省:</td>
<td><input id="nowPrvn" name="nowPrvn" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.nowPrvn}"/></td>

<td>实际经营所在市:</td>
<td><input id="nowCity" name="nowCity" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.nowCity}"/></td>

<td>实际经营所在县:</td>
<td><input id="nowCtry" name="nowCtry" type="text" data-options="validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.nowCtry}"/></td>

<td>实际经营地址:</td>
<td><input id="nowAddr" name="nowAddr" type="text" data-options="validType:['length[0,100]']" class="textbox easyui-validatebox" value="${bean.nowAddr}"/></td>
</tr>
<%-- <tr>
<td>诉讼情况:</td>
<td><input id="lawState" name="lawState" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" 
<c:choose>
<c:when test="${bean.lawState eq '0'}">value="无"</c:when>
<c:when test="${bean.lawState eq '1'}">value="有"</c:when>
</c:choose>/>
</td>

<td>诉讼说明:</td>
<td><input id="lawRemark" name="lawRemark" type="text" data-options="validType:['length[0,500]']" class="textbox easyui-validatebox" value="${bean.lawRemark}"/></td>

<td>诉讼复核:</td>
<td><input id="lawCheckState" name="lawCheckState" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" 
<c:choose>
<c:when test="${bean.lawCheckState eq '0'}">value="不通过"</c:when>
<c:when test="${bean.lawCheckState eq '1'}">value="通过"</c:when>
</c:choose>/>
</td>

<td>诉讼复核说明:</td>
<td><input id="lawCheckRemark" name="lawCheckRemark" type="text" data-options="validType:['length[0,500]']" class="textbox easyui-validatebox" value="${bean.lawCheckRemark}"/></td>
</tr>

<tr>
<td>创建人:</td>
<td><input id="createName" name="createName" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.createName}"/></td>

<td>创建时间:</td>
<td><input id="createTime" name="createTime" type="text" editable="false" class="textbox easyui-datetimebox" value="${bean.createTimeStr}"/></td>

<td>修改人:</td>
<td><input id="updateName" name="updateName" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.updateName}"/></td>

<td>修改时间:</td>
<td><input id="updateTime" name="updateTime" type="text" editable="false" class="textbox easyui-datetimebox" value="${bean.updateTimeStr}"/></td>
</tr> --%>
<tr>
<td>业务员备注:</td>
<td><input id="saleRemark" name="saleRemark" type="text" data-options="validType:['length[0,500]']" class="textbox easyui-validatebox" value="${bean.saleRemark}"/></td>
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
			<jsp:include page="/files/load.do">
				<jsp:param value="${bean.loanId }" name="loId"/>
				<jsp:param value="filesce6" name="sec"/>
				<jsp:param value="${ bean.id}" name="bizKey"/>
				<jsp:param value="1" name="opt"/>
			</jsp:include>
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


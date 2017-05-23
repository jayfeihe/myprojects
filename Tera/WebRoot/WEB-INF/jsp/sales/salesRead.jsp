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
<title>营销人员表查看</title>
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
			<table>
				<tbody>
				<input id="id" name="id" type="hidden" size="35" value="${ bean.id}" />
				<tr>
<td><SPAN style="color:red">*</SPAN>ID:</td>
<td><input id="id" name="id" type="text" data-options="required:true,min:0,precision:0" editable="false" class="textbox easyui-numberbox" value="${bean.id}" disabled="disabled" /></td>
</tr>
<tr>
<td><SPAN style="color:red">*</SPAN>员工卡号:</td>
<td><input id="staffNo" name="staffNo" type="text" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.staffNo}" disabled="disabled" /></td>
</tr>
<tr>
<td>姓名:</td>
<td><input id="name" name="name" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.name}" disabled="disabled" /></td>
</tr>
<tr>
<td>性别:</td>
<td><input id="gender" name="gender" type="text" data-options="validType:['length[0,1]']" class="textbox easyui-validatebox" value="${bean.gender}" disabled="disabled" /></td>
</tr>
<tr>
<td>证件类型:</td>
<td><input id="idType" name="idType" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.idType}" disabled="disabled" /></td>
</tr>
<tr>
<td>证件号码:</td>
<td><input id="idNo" name="idNo" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.idNo}" disabled="disabled" /></td>
</tr>
<tr>
<td>户口性质:</td>
<td><input id="hukou" name="hukou" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.hukou}" disabled="disabled" /></td>
</tr>
<tr>
<td>所属机构:</td>
<td><input id="orgId" name="orgId" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.orgId}" disabled="disabled" /></td>
</tr>
<tr>
<td>岗位级别:</td>
<td><input id="level" name="level" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.level}" disabled="disabled" /></td>
</tr>
<tr>
<td>入职时间:</td>
<td><input id="entryDate" name="entryDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.entryDateStr}" disabled="disabled" /></td>
</tr>
<tr>
<td>拟转正时间:</td>
<td><input id="permanentDate" name="permanentDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.permanentDateStr}" disabled="disabled" /></td>
</tr>
<tr>
<td>离职时间:</td>
<td><input id="leaveDate" name="leaveDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.leaveDateStr}" disabled="disabled" /></td>
</tr>
<tr>
<td>调岗时间:</td>
<td><input id="adjustDate" name="adjustDate" type="text" editable="false" class="textbox easyui-datebox" value="${bean.adjustDateStr}" disabled="disabled" /></td>
</tr>
<tr>
<td>劳动合同期限:</td>
<td><input id="contractTerm" name="contractTerm" type="text" editable="false"  data-options="min:0,precision:0" class="textbox easyui-numberbox" value="${bean.contractTerm}" disabled="disabled" /></td>
</tr>
<tr>
<td>电话:</td>
<td><input id="mobile" name="mobile" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.mobile}" disabled="disabled" /></td>
</tr>
<tr>
<td>邮箱:</td>
<td><input id="email" name="email" type="text" data-options="validType:['email','length[0,50]']" class="textbox easyui-validatebox" value="${bean.email}" disabled="disabled" /></td>
</tr>
<tr>
<td>婚姻:</td>
<td><input id="marriage" name="marriage" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.marriage}" disabled="disabled" /></td>
</tr>
<tr>
<td>婚育:</td>
<td><input id="birthFlag" name="birthFlag" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.birthFlag}" disabled="disabled" /></td>
</tr>
<tr>
<td>学历:</td>
<td><input id="education" name="education" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.education}" disabled="disabled" /></td>
</tr>
<tr>
<td>学位证:</td>
<td><input id="degreeCertifyNo" name="degreeCertifyNo" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.degreeCertifyNo}" disabled="disabled" /></td>
</tr>
<tr>
<td>毕业院校:</td>
<td><input id="university" name="university" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.university}" disabled="disabled" /></td>
</tr>
<tr>
<td>所学专业:</td>
<td><input id="major" name="major" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.major}" disabled="disabled" /></td>
</tr>
<tr>
<td>身份证地址:</td>
<td><input id="idAddress" name="idAddress" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.idAddress}" disabled="disabled" /></td>
</tr>
<tr>
<td>现住址:</td>
<td><input id="curAddress" name="curAddress" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.curAddress}" disabled="disabled" /></td>
</tr>
<tr>
<td>紧急联系人:</td>
<td><input id="contact" name="contact" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.contact}" disabled="disabled" /></td>
</tr>
<tr>
<td>紧急联系人电话:</td>
<td><input id="contactPhone" name="contactPhone" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.contactPhone}" disabled="disabled" /></td>
</tr>
<tr>
<td>工资卡卡号:</td>
<td><input id="wageCardNo" name="wageCardNo" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.wageCardNo}" disabled="disabled" /></td>
</tr>
<tr>
<td>工资卡开户行:</td>
<td><input id="wageCardBank" name="wageCardBank" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.wageCardBank}" disabled="disabled" /></td>
</tr>
<tr>
<td>社保缴纳情况:</td>
<td><input id="socialSecurity" name="socialSecurity" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.socialSecurity}" disabled="disabled" /></td>
</tr>
<tr>
<td>转正标志:</td>
<td><input id="fullFlag" name="fullFlag" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.fullFlag}" disabled="disabled" /></td>
</tr>
<tr>
<td>状态:</td>
<td><input id="state" name="state" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.state}" disabled="disabled" /></td>
</tr>
<tr>
<td>备注:</td>
<td><input id="remark" name="remark" type="text" data-options="validType:['length[0,200]']" class="textbox easyui-validatebox" value="${bean.remark}" disabled="disabled" /></td>
</tr>
<tr>
<td>操作员:</td>
<td><input id="operator" name="operator" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.operator}" disabled="disabled" /></td>
</tr>
<tr>
<td>创建日期:</td>
<td><input id="createTime" name="createTime" type="text" editable="false" class="textbox easyui-datetimebox" value="${bean.createTimeStr}" disabled="disabled" /></td>
</tr>
<tr>
<td>修改日期:</td>
<td><input id="updateTime" name="updateTime" type="text" editable="false" class="textbox easyui-datetimebox" value="${bean.updateTimeStr}" disabled="disabled" /></td>
</tr>

				<tr>
					<td>
						<input type="button" value="返回" class="btn" onclick="javascript:back()"/>
					</td>
					<td></td>
				</tr>
				</tbody>
			</table>			
		</div>
	</div>
</div>
</body>

<script type="text/javascript">
//返回
function back(){
	window.history.go(-1);
}

//页面加载完动作
$(document).ready(function (){
	//填充select数据样例
	/*
	var tsurl="sys/datadictionary/listjason.do?keyName=repaymethod";
	$("#repayMethod").combobox("clear");
	$('#repayMethod').combobox({url:tsurl, valueField:'keyProp', textField:'keyValue'});
	*/	
});
</script>
</html>


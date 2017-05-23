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
<title>借款咨询查看</title>
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
<td>ID:</td>
<td><input id="id" name="id" type="text" class="textbox"value="${bean.id}"  disabled="disabled"/></td>
</tr>
<tr>
<td>咨询人类型:</td>
<td><input id="type" name="type" type="text" class="textbox"value="${bean.type}"  disabled="disabled"/></td>
</tr>
<tr>
<td>姓名/机构全称:</td>
<td><input id="name" name="name" type="text" class="textbox"value="${bean.name}"  disabled="disabled"/></td>
</tr>
<tr>
<td>金额:</td>
<td><input id="amount" name="amount" type="text" class="textbox"value="${bean.amount}"  disabled="disabled"/></td>
</tr>
<tr>
<td>电话:</td>
<td><input id="phone" name="phone" type="text" class="textbox"value="${bean.phone}"  disabled="disabled"/></td>
</tr>
<tr>
<td>证件类型:</td>
<td><input id="idType" name="idType" type="text" class="textbox"value="${bean.idType}"  disabled="disabled"/></td>
</tr>
<tr>
<td>证件号码:</td>
<td><input id="idNo" name="idNo" type="text" class="textbox"value="${bean.idNo}"  disabled="disabled"/></td>
</tr>
<tr>
<td>还款来源:</td>
<td><input id="repaymentSource" name="repaymentSource" type="text" class="textbox"value="${bean.repaymentSource}"  disabled="disabled"/></td>
</tr>
<tr>
<td>是否抵押:</td>
<td><input id="mortgage" name="mortgage" type="text" class="textbox"value="${bean.mortgage}"  disabled="disabled"/></td>
</tr>
<tr>
<td>用途:</td>
<td><input id="loanPurpose" name="loanPurpose" type="text" class="textbox"value="${bean.loanPurpose}"  disabled="disabled"/></td>
</tr>
<tr>
<td>开始时间:</td>
<td><input id="startTime" name="startTime" type="text" class="textbox"value="${bean.startTime}"  disabled="disabled"/></td>
</tr>
<tr>
<td>结束时间:</td>
<td><input id="endTime" name="endTime" type="text" class="textbox"value="${bean.endTime}"  disabled="disabled"/></td>
</tr>
<tr>
<td>客户经理:</td>
<td><input id="customerManager" name="customerManager" type="text" class="textbox"value="${bean.customerManager}"  disabled="disabled"/></td>
</tr>
<tr>
<td>操作员:</td>
<td><input id="operator" name="operator" type="text" class="textbox"value="${bean.operator}"  disabled="disabled"/></td>
</tr>
<tr>
<td>所属机构:</td>
<td><input id="orgId" name="orgId" type="text" class="textbox"value="${bean.orgId}"  disabled="disabled"/></td>
</tr>
<tr>
<td>创建日期:</td>
<td><input id="createTime" name="createTime" type="text" class="textbox"value="${bean.createTime}"  disabled="disabled"/></td>
</tr>
<tr>
<td>修改日期:</td>
<td><input id="updateTime" name="updateTime" type="text" class="textbox"value="${bean.updateTime}"  disabled="disabled"/></td>
</tr>
<tr>
<td>状态:</td>
<td><input id="state" name="state" type="text" class="textbox"value="${bean.state}"  disabled="disabled"/></td>
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
</script>
</html>


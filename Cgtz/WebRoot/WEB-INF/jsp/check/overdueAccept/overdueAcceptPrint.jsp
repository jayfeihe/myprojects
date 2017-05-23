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
<title>逾期受理打印</title>
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
<div align="center"  style="margin:0 auto;width:600px;font-size: 16px;">逾期受理登记表</div><br/>
<div style="margin:0 auto;width:600px;"><span style="margin-left: 5px;">编号:</span><span style="margin-right: 5px;float: right;letter-spacing:20px;">年月日</span></div>	
<table align="center" border="1px" cellspacing="0" cellpadding="0" width="625px" style="border:1px solid black;line-height: 2;">
<tr>
<td align="center">部门</td><td>${bean.dept}</td><td align="center">业务经办人</td><td>${bean.busName}</td><td align="center">联系方式</td><td>${bean.tel}</td><td align="center">性质</td>
<td>
<c:choose>
<c:when test="${bean.property eq '1'}">利息逾期</c:when>
<c:when test="${bean.property eq '2'}">本金逾期</c:when>
</c:choose>
</td>
</tr>
<tr>
<td align="center">部门负责人</td><td>${bean.deptOwner}</td><td align="center">评审成员</td><td>${bean.auditMem}</td><td align="center">风控人员</td><td>${bean.riskName}</td><td align="center">法务人员</td><td>${bean.lawName}</td>
</tr>
<tr>
<td align="center">产品类型</td>
<td colspan="2">
<c:choose>
<c:when test="${bean.proType eq '01'}">车贷</c:when>
<c:when test="${bean.proType eq '02'}">车商贷</c:when>
<c:when test="${bean.proType eq '03'}">房贷</c:when>
<c:when test="${bean.proType eq '04'}">红木贷</c:when>
<c:when test="${bean.proType eq '05'}">海鲜贷</c:when>
<c:otherwise>其它</c:otherwise>
</c:choose>
</td>
<td align="center">逾期时间</td><td>${bean.lateDateStr}</td>
<td align="center">金额</td><td colspan="2"><fmt:formatNumber type="currency">${bean.amt}</fmt:formatNumber>元</td>
</tr>
<tr>
<td align="center">借款人的基本情况</td><td colspan="7" height="100px">${bean.loanInfo}</td>
</tr>
<tr>
<td align="center">逾期报告摘要</td><td colspan="7" height="100px">${bean.reportSummary}</td>
</tr>
<tr>
<td align="center">部门经理审核意见</td><td colspan="7" height="100px">${bean.deptAuditAdv}</td>
</tr>
<tr>
<td align="center">承办人审核意见</td><td colspan="7" height="100px">${bean.busAdv}</td>
</tr>
<tr>
<td align="center">稽查部门负责人意见</td><td colspan="7" height="100px">${bean.checkAdv}</td>
</tr>
<tr>
<td align="center">保全小组意见</td><td colspan="7" height="100px">${bean.keepAdv}</td>
</tr>
<tr>
<td align="center">领导意见</td><td colspan="7" height="100px">${bean.leaderAdv}</td>
</tr>
</table>
<br/>

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

//页面加载完动作
$(document).ready(function (){
});

</script>
</html>


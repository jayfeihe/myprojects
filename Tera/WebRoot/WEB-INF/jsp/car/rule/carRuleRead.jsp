<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>" />
<title>决策引擎信息查看</title>
	<link href="css/global.css" type="text/css" rel="stylesheet" />
	<link href="css/icon.css" type="text/css" rel="stylesheet" />
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet" />
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
	
	<style type="text/css">
		hr{
			color:#D3D3D3;
		}
	</style>
</head>

<body>
	<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>欺诈规则</strong></div>
	<hr/>
	<table id="fraudRule">
		<tr>
			<td class="ruleInfo">1、申请人单位电话在黑名单库中</td>
		</tr>
		<tr>
			<td class="ruleInfo">2、申请人手机号码与历史库中相同，对应姓名不同</td>
		</tr>
		<tr>
			<td class="ruleInfo">3、申请人单位地址与历史库中相同，单位电话不同</td>
		</tr>
	</table>
	
	<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>大纲规则</strong></div>
	<hr/>
	<table id="appRule">
		<tr>
			<td class="ruleInfo">1、申请人年龄不符</td>
		</tr>
		<tr>
			<td class="ruleInfo">2、工作地无我公司网点</td>
		</tr>
		<tr>
			<td class="ruleInfo">3、申请人在人法网执行中</td>
		</tr>
	</table>
	
	<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>查重规则</strong></div>
	<hr/>
	<table id="checkRule">
		<tr>
			<td class="ruleInfo">1、申请人住宅地址与历史库联系人住宅地址查重</td>
		</tr>
		<tr>
			<td class="ruleInfo">2、申请人单位电话与历史库查重</td>
		</tr>
	</table>
</body>
</html>
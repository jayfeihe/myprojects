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
<title>诉讼管理</title>
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
		<div class="content">
			<form id="updateLawForm" >
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>诉讼管理</strong></div><hr color="#D3D3D3"/>
				<input type="hidden" name="id" value="${bean.id }" />
				<input type="hidden" name="loanId" value="${loanId }" />
				<table id="lawInfo">
					<tr>
						<td>诉讼情况:</td>
						<td>
							<input id="lawState" name="lawState" type="text" readonly="readonly" class="textbox easyui-combobox" 
								data-options="required:true,editable:false,panelHeight:'auto',
											textField:'keyValue',
											valueField:'keyProp',
											data:[{'keyProp':'','keyValue':'请选择'},
												{'keyProp':'0','keyValue':'无'},
												{'keyProp':'1','keyValue':'有'},]" value="${bean.lawState}"/>
						</td>
					</tr>
					<tr>
						<td>说明:</td>
						<td colspan="6">
							<textarea name="lawRemark" class="textbox easyui-validatebox" readonly="readonly"
										data-options="validType:['length[0,500]']" 
										style="resize: none;width:625px;height:50px!important;">${bean.lawRemark }</textarea>
						</td>
					</tr>
				</table>
			</form>
			<jsp:include page="/files/load.do">
				<jsp:param value="${loanId }" name="loId"/>
				<jsp:param value="filesce7" name="sec"/>
				<jsp:param value="${ bean.id}" name="bizKey"/>
				<jsp:param value="1" name="opt"/>
			</jsp:include>
		</div>
	</div>

<script type="text/javascript">
//页面加载完动作
$(document).ready(function (){
	
});
</script>

<script type="text/javascript" >
openMask();
$(window).load(function (){
	closeMask();
});
</script>
</body>
</html>


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
<title>催收历史</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
 
  
</head>
<body>
	
 
	<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;">
	<strong>催收历史</strong></div>
	<hr/>
	<div style="height: 360px;width:100%;overflow-y: scroll;">
    <table class="datatable"  >
					
					<tr>
						<th scope="col">序号</td>
						<th scope="col">催收员</td>
						<th scope="col">逾期天数</td>
						<th scope="col">联系电话</td>
						<th scope="col">跟进日期</td>
						<th scope="col">联系人</td>
						<th scope="col">催收摘要</td>
						<th scope="col">备注</td>
						
					</tr>	
				<c:forEach var="historyList" items="${collectionHistoryList}" varStatus="status">
					<tr>
											<td style="text-align: center;">${status.index+1}</td>
											<td>${historyList.collectionName}</td>
											<td>${historyList.lateDays}</td>
											<td>${historyList.tel}</td>
											<td>${historyList.callTimeStr}</td>
											<td>${historyList.answerName}</td>
											<td>${historyList.phoneSummary}</td>
											<td>
											<textarea readonly="readonly" class="textbox easyui-validatebox" 
											data-options="required:true,validType:['length[0,500]']" 
											style="resize: none;height:50px!important;font-size: small;"  cols="60" >${historyList.remark}</textarea>
											 </td>
											
					</tr>
						</c:forEach>
				
						
					</table>
	</div> 
 
</body>
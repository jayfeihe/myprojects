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
</head>
<body>
<div id="main">
	<div class="content" id="contractRead">
		<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>合同信息</strong></div><hr color="#D3D3D3"/>
		<table>
			<tr>
				<td>合同编号:</td>
				<td><input class="textbox" type="text" value="${bean.contractId }" readonly="readonly"/></td>
				<td>合同金额:</td>
				<td><input class="textbox" type="text" value="<fmt:formatNumber value="${bean.loanAmt }" type="currency"/>" readonly="readonly"/>元</td>
			</tr>
			<tr>
				<td>借款人:</td>
				<td><input class="textbox" type="text" value="${loanUser }" readonly="readonly"/></td>
				<td>经办人:</td>
				<td><input class="textbox" type="text" value="${bean.lawName }" readonly="readonly"/></td>
			</tr>
			<tr>
				<td>融资方式:</td>
				<td>
					<input type="text" type="text" value="${bean.getLoanWay }" 
						data-options="textField:'keyValue',
									valueField:'keyProp',
									data:dataDictJson.getLoanWay" 
						class="textbox easyui-combobox" readonly="readonly"/>
				</td>
				<c:if test="${bean.getLoanWay eq '2' or bean.getLoanWay eq '3'}">
					<td>出借人:</td>
					<td><input class="textbox" type="text" value="${lendUser }" readonly="readonly"/></td>
				</c:if>
			</tr>
			
			<tr>
				<td>合同开始时间:</td>
				<td><input class="textbox" type="text" value="${bean.startDateStr }" readonly="readonly"/></td>
				<td>合同结束时间:</td>
				<td><input class="textbox" type="text" value="${bean.endDateStr }" readonly="readonly"/></td>
			</tr>
			<!--<tr>
				<td>合同实际结束时间:</td><td>${bean.realEndDateStr }</td>
			</tr>
			-->
			<tr>
				<td>合同天数:</td>
				<td><input class="textbox" type="text" value="${bean.days }" readonly="readonly"/>天&nbsp;&nbsp;</td>
				<td>期数:</td>
				<td><input class="textbox" type="text" value="${bean.num }" readonly="readonly"/>期</td>
			</tr>
			<tr>
				<td>说明:</td>
				<td colspan="5"><textarea readonly="readonly" class="textbox easyui-validatebox" style="resize: none;width:400px;height:50px!important;">${bean.remark }</textarea></td>
			</tr>
		</table>
	</div>
	<jsp:include page="/files/read2.do">
		<jsp:param value="${bean.loanId}" name="loanId"/>
		<jsp:param value="filesce9" name="sec"/>
		<jsp:param value="${bean.contractId}" name="bizKey"/>
	</jsp:include>
</div>
</body>
</html>
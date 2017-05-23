<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />
		<title>支付明细表查看</title>
		<link href="css/global.css" type="text/css" rel="stylesheet" />
		<link href="css/icon.css" type="text/css" rel="stylesheet" />
		<link href="css/default/easyui.css" type="text/css" rel="stylesheet" />
		<script src="js/jquery.min.js" type="text/javascript">
</script>
		<script src="js/jquery.form.js" type="text/javascript">
</script>
		<script src="js/jquery.easyui.min.js" type="text/javascript">
</script>
		<script src="js/easyui-lang-zh_CN.js" type="text/javascript">
</script>
		<style type="text/css">
</style>
	</head>
	<body>
		<!-- top -->
		<%@include file="../header.jsp"%>
		<div id="main">
			<div id="part1" class="part">
				<p class="title">
					<a href="javascript:void(0);">查看</a>
				</p>
				<div class="content">
					<table>
						<tbody>
							<input id="id" name="id" type="hidden" size="35"
								value="${ appBean.id}" />
							<input id="type" name="type" type="hidden" size="35"
								value="${type}" />
							<tr>
								<td>
									姓名(中文):
								</td>
								<td>
									<input id="name" name="name" type="text"
										data-options="required:true,validType:['length[0,100]']"
										class="textbox easyui-validatebox" value="${appBean.name}"
										readonly='readonly' />
								</td>
								<td>
									手机号:
								</td>
								<td>
									<input id="mobile" name="mobile" type="text" editable="false"
										data-options="required:true,validType:['length[0,100]']"
										class="textbox easyui-validatebox" value="${appBean.mobile}"
										readonly='readonly' />
								</td>
							</tr>
							<tr>
								<td>
									证件类型:
								</td>
								<td>
									<input class="easyui-combobox" name="idType" id="idType"
										data-options="
										required:true" style="width: 152px;"
										editable="false" value="${appBean.idType}" readonly />
								</td>
								<td>
									证件号码:
								</td>
								<td>
									<input id="idNo" name="idNo" type="text"
										data-options="required:true,validType:['length[0,18]','idNo']"
										class="textbox easyui-validatebox" value="${appBean.idNo}"
										readonly='readonly' />
								</td>
							</tr>
							<tr>
								<td>
									产品:
								</td>
								<td>
									<input class="easyui-combobox" name="product" id="product"
										data-options="required:true" style="width: 150px;"
										value="${appBean.product}" editable="false" readonly />
								</td>
								<td>
									期限:
								</td>
								<td>
									<input id="period" name="period" type="text"
										data-options="required:true,validType:['length[0,100]']"
										style="width: 126px" class="textbox easyui-validatebox"
										value="${appBean.period}" readonly />
									&nbsp;月
								</td>
								<td>
									利率:
								</td>
								<td>
									<input id="interestRate" name="interestRate" type="text"
										data-options="required:true,validType:['length[0,100]']"
										class="textbox easyui-validatebox"
										<c:choose><c:when test="${type=='C'}">value='${product.interestRate}'</c:when><c:otherwise>value='${appBean.interestRate}'</c:otherwise></c:choose>
										readonly />
									%
								</td>
							</tr>
							<tr>
							<c:if test="${type=='L'}">
							<td>
									出借金额:
								</td>
								<td>
									<input id="amountSum" name="amountSum" type="text"
										class="textbox"
										value="<fmt:formatNumber value="${appBean.amount}" type="currency"/>"
										readonly />
								</td>
							</c:if>
							<c:if test="${type=='B'}">
								<td>
									服务费率:
								</td>
								<td>
									<input id="sreviceFeeRate" name="sreviceFeeRate" type="text"
										data-options="required:true,validType:['length[0,100]']"
										class="textbox easyui-validatebox"
										value="${appBean.sreviceFeeRate}" readonly />
									%
								</td>
								
								<td>
									总金额:
								</td>
								<td>
									<input id="amountSum" name="amountSum" type="text"
										class="textbox"
										value="<fmt:formatNumber value="${appBean.amount}" type="currency"/>"
										readonly />
								</td>
								<td>
									本期应还金额:
								</td>
								<td>
									<input id="amount" name="amount" type="text" class="textbox"
										value="<fmt:formatNumber value="${actualAmount}" type="currency"/>"
										readonly />
								</td>
							</c:if>
							<c:if test="${type=='C'}">
								<td>
									服务费率:
								</td>
								<td>
									<input id="sreviceFeeRate" name="sreviceFeeRate" type="text"
										data-options="required:true,validType:['length[0,100]']"
										class="textbox easyui-validatebox"
										value="${product.sreviceFeeRate}" readonly />
									%
								</td>
								
								<td>
									总金额:
								</td>
								<td>
									<input id="amountSum" name="amountSum" type="text"
										class="textbox"
										value="<fmt:formatNumber value="${contract.loanAmount}" type="currency"/>"
										readonly />
								</td>
								<td>
									本期应还金额:
								</td>
								<td>
									<input id="amount" name="amount" type="text" class="textbox"
										value="<fmt:formatNumber value="${actualAmount}" type="currency"/>"
										readonly />
								</td>
							</c:if>
							</tr>
							<tr>
								<td>
									<input type="button" value="返回" class="btn"
										onclick="javascript:back()" />
									<input type="button" value="立即支付" class="btn"
										onclick="javascript:pay('${bean.id}')" />
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
function back() {
	window.history.go(-1);
}
function pay(id) {
	window.open("<%=basePath%>paycenter/payment/allinpay.htm?id=" + id);
}
//页面加载完动作
$(document).ready(function() {
	//填充 证件类型 类型
	$("#idType").combobox("clear");
	$("#idType").combobox({
		valueField:'keyProp',
		textField:'keyValue',
		data:
			<c:if test="${customerType=='01'}">
			dataDictJson.personidtype
			</c:if>	
			<c:if test="${customerType=='02'}">
			dataDictJson.companyidtype
			</c:if>
	});
	tsurl="product/hedao/listjason.do";
	$("#product").combobox("clear");
	$('#product').combobox({
		valueField:'name', 
		textField:'name',
		url:tsurl
	});
});
</script>
</html>

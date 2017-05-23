<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 <table width="100%" id="zongheInfo">
	<tr>
		<td colspan="9">
			<div id="basicInfo" style="font-size: 14px;" >
				<strong>基本信息</strong>
				<hr width="100%" size=2 color="#D3D3D3" noshade>
			</div>
		</td>
	</tr>
 	<tr>
	 	<td>
	 	<table>
		 	<tr>
				<td>姓名:</td>
				<td><input id="name" name="carApp.name" type="text" data-options="validType:['length[0,50]']" 
					class="textbox easyui-validatebox" value="${bean.name}"/></td>
				<td><%--证件类型:默认身份证<input id="idType" name="carApp.idType" type="hidden" value="01"/>--%>
				证件号码:</td>
				<td><input id="idNo" name="carApp.idNo" type="text" data-options="validType:['idcard']"
				class="textbox easyui-validatebox" value="${bean.idNo}"/></td>
			</tr>
			<tr>
				<td>有无子女:</td>
				<td>
					<input id="withoutChildren" name="carApp.withoutChildren" 
					type="radio" value="0" <c:if test="${bean.withoutChildren==0}">checked="checked"</c:if> />无
					<input id="withoutChildren" name="carApp.withoutChildren" 
					type="radio" value="1" <c:if test="${bean.withoutChildren==1}">checked="checked"</c:if>/>有
					<input id="childrenSize" name="carApp.childrenSize" type="text" editable="false"  data-options="min:0,precision:0" 
					class="textbox easyui-numberbox" value="${bean.childrenSize}" 
					style="width:52px;"/>个
				</td>
				<td>婚姻状况:</td>
				<td><input id="marriage" name="carApp.marriage" type="text" data-options="validType:['length[0,2]']" 
				class="easyui-combobox" value="${bean.marriage}" editable="false"/></td>
			</tr>
			<tr>
				<td>居住性质:</td>
				<td><input id="addNature" name="carApp.addNature" type="text" data-options="validType:['length[0,10]']" 
				class="easyui-combobox" value="${bean.addNature}" editable="false"/></td>
				<td>担任职务:</td>
				<td><input id="comDuty" name="carApp.comDuty" type="text" data-options="rvalidType:['length[0,50]']" 
				class="textbox easyui-validatebox" value="${bean.comDuty}"/></td>
			</tr>
		</table>
		</td>
	</tr>
 	<tr>
		<td colspan="9">
			<div id="creditReport" style="font-size: 14px;" >
				<strong>还款能力</strong>
				<hr width="100%" size=2 color="#D3D3D3" noshade>
			</div>
		</td>
	</tr>
 	<tr>
	 	<td>
		 	<table>
			 	<tr>
					<td>信用卡最高额:</td>
					<td><input id="amount" name="creditReport.amount" type="text" data-options="validType:['length[0,50]'],min:0,precision:2" 
					class="textbox easyui-numberbox" value="${creditReport.amount}"/></td>
<%--					<td>信用卡月负债:</td>--%>
<%--					<td><input id="defaultAmount" name="creditReport.defaultAmount" type="text" data-options="validType:['length[0,50]']" --%>
<%--					class="textbox easyui-validatebox" value="${creditReport.defaultAmount}"/></td>--%>
					<td>已用额度:</td>
					<td><input id="usedAmount" name="creditReport.usedAmount" type="text" data-options="validType:['length[0,50]'],min:0,precision:2" 
					class="textbox easyui-numberbox" value="${creditReport.usedAmount}"/></td>
				</tr>
				<tr>
					<td>月还款额:</td>
					<td><input id="monthlyPayments" name="carApp.monthlyPayments" type="text" editable="false"  data-options="min:0,precision:2" 
					class="textbox easyui-numberbox" value="${bean.monthlyPayments}" style="width:130px;"/>元</td>
				</tr>
			</table>
		</td>
	</tr>

	<tr <c:if test="${fn:length(contractList) < 1}">style="display:none;"</c:if>>
		<td colspan="9">
			<div style="font-size: 14px;" >
				<strong>本公司借款情况</strong>
				<hr width="100%" size=2 color="#D3D3D3" noshade>
			</div>
		</td>
	</tr>
	<tr <c:if test="${fn:length(contractList) < 1}">style="display:none;"</c:if>>
		<td>
			<table class="datatable">
				<tr>
					<td>合同金额</td>
					<td>剩余本息</td>
					<td>分期数</td>
					<td>已还期数</td>
					<td>逾期期数</td>
				</tr>
				<c:forEach items="${contractList }" var="data" varStatus="status">
					<tr>
						<td><fmt:formatNumber value="${data.contractMoney/10000}" type="currency"/>万元</td>
						<td><fmt:formatNumber value="${data.restPrincipalReceived}" type="currency"/></td>
						<td>${data.loanPeriod}</td>
						<td>${data.loanPeriod - data.notReturnPeriod}</td>
						<td>${data.exceedPeriod}</td>
					</tr>
				</c:forEach>
			</table>
		</td>
	</tr>

	<tr <c:if test="${fn:length(antifraudList) < 1}">style="display:none;"</c:if>>
		<td colspan="9">
			<div style="font-size: 14px;" >
				<strong>反欺诈信息</strong>
				<hr width="100%" size=2 color="#D3D3D3" noshade>
			</div>
		</td>
	</tr>
	<tr <c:if test="${fn:length(antifraudList) < 1}">style="display:none;"</c:if>>
		<td>
			<table class="datatable">
				<tr>
					<td>提交人</td>
					<td>解除人</td>
					<td width="65px";>反欺诈结果</td>
					<td>说明</td>
				</tr>
				<c:forEach items="${antifraudList}" var="antifraud" varStatus="status">
					<tr>
						<td>${antifraud.submitOperatorName}</td>
						<td>${antifraud.operatorName}</td>
						<td>
							<c:choose>
				<c:when test="${antifraud.result=='1'}">无问题</c:when>
				<c:when test="${antifraud.result=='2'}">有问题</c:when>
				<c:otherwise>未知</c:otherwise>
				</c:choose>
						</td>
						<td>${antifraud.resultInfo}</td>
					</tr>
				</c:forEach>
			</table>
		</td>
	</tr>
</table>
<script type="text/javascript">
$.parser.parse($("#zongheInfo"));

$(document).ready(function(){

	//填充select数据 婚姻状况
	$("#zongheInfo").find("#marriage").combobox("clear");
	$("#zongheInfo").find("#marriage").combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.marriage
	});
	
	//填充select数据 居住性质
	$("#zongheInfo").find("#addNature").combobox("clear");
	$("#zongheInfo").find('#addNature').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.houseNature
	});
});
 </script>
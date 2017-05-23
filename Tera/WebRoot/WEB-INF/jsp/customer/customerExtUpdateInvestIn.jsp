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
<table>
	<tbody>
		<tr>
			<td align="left">&nbsp;</td>
		</tr>

		<tr>
		<td align="left"><b>您目前的置业情况：</b><hr width="100%" size=1 color=#E0ECFF align=center noshade></td>
		</tr>
							
		<tr>
			<td>
				<input id="realEstate" name="realEstate" type="checkbox" value="1"/>无自住房 &nbsp;
				<input id="realEstate" name="realEstate" type="checkbox" value="2"/>房贷&gt;资产的50% &nbsp;
				<input id="realEstate" name="realEstate" type="checkbox" value="3"/>房贷&lt;资产的50% &nbsp;
				<input id="realEstate" name="realEstate" type="checkbox" value="4"/>自住无房贷 &nbsp;
				<input id="realEstate" name="realEstate" type="checkbox" value="5"/>无自住房且没有投资房产 &nbsp;
			</td>
		</tr>
		<tr>
			<td>
				<input id="realEstate" name="realEstate" type="checkbox" value="6"/>未购车 &nbsp;
				<input id="realEstate" name="realEstate" type="checkbox" value="7"/>已购车有车贷 &nbsp;
				<input id="realEstate" name="realEstate" type="checkbox" value="8"/>已购车无车贷 &nbsp;
			</td>
		</tr>
		<tr>
			<td align="left">&nbsp;</td>
		</tr>

		<tr>
		<td align="left"><b>您对投资的了解程度：</b><hr width="100%" size=1 color=#E0ECFF align=center noshade></td>
		</tr>
		<tr>
			<td>
				<input id="investmentLevel" name="investmentLevel" type="checkbox" value="8"/>不了解 &nbsp;
				<input id="investmentLevel" name="investmentLevel" type="checkbox" value="9"/>略懂&nbsp;
				<input id="investmentLevel" name="investmentLevel" type="checkbox" value="10"/>自修有心得 &nbsp;
				<input id="investmentLevel" name="investmentLevel" type="checkbox" value="11"/>金融从业人员 &nbsp; &nbsp;
				<input id="investmentLevel" name="investmentLevel" type="checkbox" value="12"/>有投资专业执照 &nbsp;
			</td>
		</tr>
		<tr>
			<td align="left">&nbsp;</td>
		</tr>
		<tr>
		<td align="left"><b>您的投资资金来源：</b><hr width="100%" size=1 color=#E0ECFF align=center noshade></td>
		</tr>
		<tr>
			<td>
				<input id="investmentSource" name="investmentSource" type="checkbox" value="1"/>经营收入 &nbsp;
				<input id="investmentSource" name="investmentSource" type="checkbox" value="2"/>工资收入&nbsp;
				<input id="investmentSource" name="investmentSource" type="checkbox" value="3"/>投资收入 &nbsp;
				<input id="investmentSource" name="investmentSource" type="checkbox" value="4"/>继承遗产 &nbsp;
				<input id="investmentSource" name="investmentSource" type="checkbox" value="5"/>个人储蓄 &nbsp;
				<input id="investmentSource" name="investmentSource" type="checkbox" value="9"/>其他 &nbsp;
			</td>
		</tr>
		
		<tr>
			<td align="left">&nbsp;</td>
		</tr>
		<tr>
		<td align="left"><b>您目前的投资经验：</b><hr width="100%" size=1 color=#E0ECFF align=center noshade></td>
		</tr>
		<tr>
			<td>
				<input id="investmentExp" name="investmentExp" type="checkbox" value="1"/>无 &nbsp;
				<input id="investmentExp" name="investmentExp" type="checkbox" value="2"/>1年以内 &nbsp;
				<input id="investmentExp" name="investmentExp" type="checkbox" value="3"/>2-5年&nbsp;
				<input id="investmentExp" name="investmentExp" type="checkbox" value="4"/>6-10年&nbsp;
				<input id="investmentExp" name="investmentExp" type="checkbox" value="5"/>10年以上&nbsp;
			</td>
		</tr>
		
		<tr>
			<td align="left">&nbsp;</td>
		</tr>
		<tr>
		<td align="left"><b>您目前的主要投资：</b><hr width="100%" size=1 color=#E0ECFF align=center noshade></td>
		</tr>
		<tr>
			<td>
				<input id="investmentProduct" name="investmentProduct" type="checkbox" value="1"/>储蓄 &nbsp;
				<input id="investmentProduct" name="investmentProduct" type="checkbox" value="2"/>基金&nbsp;
				<input id="investmentProduct" name="investmentProduct" type="checkbox" value="3"/>信托&nbsp;
				<input id="investmentProduct" name="investmentProduct" type="checkbox" value="4"/>股票&nbsp;
				<input id="investmentProduct" name="investmentProduct" type="checkbox" value="5"/>房产&nbsp;
				<input id="investmentProduct" name="investmentProduct" type="checkbox" value="6"/>黄金&nbsp;
				<input id="investmentProduct" name="investmentProduct" type="checkbox" value="7"/>外汇&nbsp;
				<input id="investmentProduct" name="investmentProduct" type="checkbox" value="8"/>衍生品&nbsp;
				<input id="investmentProduct" name="investmentProduct" type="checkbox" value="9"/>其他&nbsp;
			</td>
		</tr>
		
		<tr>
			<td align="left">&nbsp;</td>
		</tr>
		<tr>
		<td align="left"><b>您目前的投资收益：</b><hr width="100%" size=1 color=#E0ECFF align=center noshade></td>
		</tr>
		<tr>
			<td>
				<input id="investmentIncome" name="investmentIncome" type="checkbox" value="1"/>只赚不赔 &nbsp;
				<input id="investmentIncome" name="investmentIncome" type="checkbox" value="2"/>赚多赔少 &nbsp;
				<input id="investmentIncome" name="investmentIncome" type="checkbox" value="3"/>损益两平&nbsp;
				<input id="investmentIncome" name="investmentIncome" type="checkbox" value="4"/>赚少赔多&nbsp;
			</td>
		</tr>
	</tbody>
</table>
</body>

<script type="text/javascript">
//初始化被选中
$(document).ready(function(){
	var realEstateArray = "${customerExt.realEstate}".split(",");	
	for(var i=0; i<realEstateArray.length; i++){
	       $("#updateForm :checkbox[name=realEstate]").each(function(){
	           if ($(this).val() == realEstateArray[i]) {
	               $(this).attr('checked', 'checked');
	           }
	       });
	}
	
	var investmentLevelArray = "${customerExt.investmentLevel}".split(",");
	for(var i=0; i<investmentLevelArray.length; i++){
	       $("#updateForm :checkbox[name=investmentLevel]").each(function(){
	           if ($(this).val() == investmentLevelArray[i]) {
	               $(this).attr('checked', 'checked');
	           }
	       });
	}
	
	var investmentSourceArray = "${customerExt.investmentSource}".split(",");
	for(var i=0; i<investmentSourceArray.length; i++){
	       $("#updateForm :checkbox[name=investmentSource]").each(function(){
	           if ($(this).val() == investmentSourceArray[i]) {
	               $(this).attr('checked', 'checked');
	           }
	       });
	}
	
	var investmentExpArray = "${customerExt.investmentSource}".split(",");
	for(var i=0; i<investmentExpArray.length; i++){
	       $("#updateForm :checkbox[name=investmentExp]").each(function(){
	           if ($(this).val() == investmentExpArray[i]) {
	               $(this).attr('checked', 'checked');
	           }
	       });
	}
	
	var investmentProductArray = "${customerExt.investmentProduct}".split(",");
	for(var i=0; i<investmentProductArray.length; i++){
	       $("#updateForm :checkbox[name=investmentProduct]").each(function(){
	           if ($(this).val() == investmentProductArray[i]) {
	               $(this).attr('checked', 'checked');
	           }
	       });
	}
	
	var investmentIncomeArray = "${customerExt.investmentIncome}".split(",");
	for(var i=0; i<investmentIncomeArray.length; i++){
	       $("#updateForm :checkbox[name=investmentIncome]").each(function(){
	           if ($(this).val() == investmentIncomeArray[i]) {
	               $(this).attr('checked', 'checked');
	           }
	       });
	}

})

</script>
</html>


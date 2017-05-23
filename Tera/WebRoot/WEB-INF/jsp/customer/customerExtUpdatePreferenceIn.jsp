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
		<td align="left"><b>您理财的主要目标是：</b><hr width="100%" size=1 color=#E0ECFF align=center noshade></td>
		</tr>
							
		<tr>
			<td>
				<input id="preferenceGoal" name="preferenceGoal" type="checkbox" value="1"/>产期稳健增值 &nbsp;
				<input id="preferenceGoal" name="preferenceGoal" type="checkbox" value="2"/>提高短期收益 &nbsp;
				<input id="preferenceGoal" name="preferenceGoal" type="checkbox" value="3"/>跑赢通胀 &nbsp;
				<input id="preferenceGoal" name="preferenceGoal" type="checkbox" value="4"/>随时提供部分收益&nbsp;
				<input id="preferenceGoal" name="preferenceGoal" type="checkbox" value="5"/>养老 &nbsp;
				<input id="preferenceGoal" name="preferenceGoal" type="checkbox" value="6"/>保障子女教育 &nbsp;
				<input id="preferenceGoal" name="preferenceGoal" type="checkbox" value="9"/>其他&nbsp;
			</td>
		</tr>
		
		<tr>
			<td align="left">&nbsp;</td>
		</tr>

		<tr>
		<td align="left"><b>您更倾向于多久的投资期限？（最多选两项）</b><hr width="100%" size=1 color=#E0ECFF align=center noshade></td>
		</tr>
		<tr>
			<td>
				<input id="preferencePeriod" name="preferencePeriod" type="checkbox" value="1"/>1-3个月 &nbsp;
				<input id="preferencePeriod" name="preferencePeriod" type="checkbox" value="2"/>3-6个月 &nbsp;
				<input id="preferencePeriod" name="preferencePeriod" type="checkbox" value="3"/>6个月-1年 &nbsp;
				<input id="preferencePeriod" name="preferencePeriod" type="checkbox" value="4"/>1-3年 &nbsp;
				<input id="preferencePeriod" name="preferencePeriod" type="checkbox" value="5"/>3年以上 &nbsp;
			</td>
		</tr>
		
		<tr>
			<td align="left">&nbsp;</td>
		</tr>

		<tr>
		<td align="left"><b>您一般自行做投资决策吗？</b><hr width="100%" size=1 color=#E0ECFF align=center noshade></td>
		</tr>
		<tr>
			<td>
				<input id="preferenceDecision" name="preferenceDecision" type="checkbox" value="1"/>是的，我对自己的决心很有信心 &nbsp;
			</td>
		</tr>
		<tr>
			<td>
				<input id="preferenceDecision" name="preferenceDecision" type="checkbox" value="2"/>我会与他人商量，但决策靠自己 &nbsp;
				</td>
		</tr>
		<tr>
			<td>
				<input id="preferenceDecision" name="preferenceDecision" type="checkbox" value="3"/>有时是，我希望多听听理财顾问的专业建议 &nbsp;
				</td>
		</tr>
		<tr>
			<td>
				<input id="preferenceDecision" name="preferenceDecision" type="checkbox" value="4"/>我把决策权交给我的理财顾问 &nbsp;
				</td>
		</tr>

		<tr>
			<td align="left">&nbsp;</td>
		</tr>

		<tr>
		<td align="left"><b>您对理财产品的预期收益与存在风险的态度如何：</b><hr width="100%" size=1 color=#E0ECFF align=center noshade></td>
		</tr>
		<tr>
			<td>
				<input id="preferenceRisk" name="preferenceRisk" type="checkbox" value="1"/>希望收益尽可能高些，即使因此本金有可能受较高损失也可以承受&nbsp;				
			</td>
		</tr>
		<tr>
			<td>
				<input id="preferenceRisk" name="preferenceRisk" type="checkbox" value="2"/>希望收益高些，如果因此本金有可能受少量损失是可以承受的&nbsp;
				</td>
		</tr>
		<tr>
			<td>
				<input id="preferenceRisk" name="preferenceRisk" type="checkbox" value="3"/>希望理财产品的本金尽可能要保住，收益最好高于储蓄 &nbsp;
				</td>
		</tr>
		<tr>
			<td>
				<input id="preferenceRisk" name="preferenceRisk" type="checkbox" value="4"/>可以接受收益可能会少于储蓄，但本金不能受到损失 &nbsp;
				</td>
		</tr>
		
		<tr>
			<td align="left">&nbsp;</td>
		</tr>

		<tr>
		<td align="left"><b>未来，您避免的投资方向为：</b><hr width="100%" size=1 color=#E0ECFF align=center noshade></td>
		</tr>
		<tr>
			<td>
				<input id="preferenceProduct" name="preferenceProduct" type="checkbox" value="1"/>期货 &nbsp;
				<input id="preferenceProduct" name="preferenceProduct" type="checkbox" value="2"/>股票 &nbsp;
				<input id="preferenceProduct" name="preferenceProduct" type="checkbox" value="3"/>基金 &nbsp;
				<input id="preferenceProduct" name="preferenceProduct" type="checkbox" value="4"/>房产 &nbsp;
				<input id="preferenceProduct" name="preferenceProduct" type="checkbox" value="5"/>信托  &nbsp;                                                                信托
				<input id="preferenceProduct" name="preferenceProduct" type="checkbox" value="9"/>其他  &nbsp;  
			</td>
		</tr>
		
		<tr>
			<td align="left">&nbsp;</td>
		</tr>

		<tr>
		<td align="left"><b>如果有一种理财产品第一年帮您赚到了10%以上的收益，第二年您打算追加多少投资？</b><hr width="100%" size=1 color=#E0ECFF align=center noshade></td>
		</tr>
		<tr>
			<td>
				<input id="preferenceAmount" name="preferenceAmount" type="checkbox" value="1"/>10-20万&nbsp;
				<input id="preferenceAmount" name="preferenceAmount" type="checkbox" value="2"/>20-50万&nbsp;
				<input id="preferenceAmount" name="preferenceAmount" type="checkbox" value="3"/>50-100万 &nbsp;
				<input id="preferenceAmount" name="preferenceAmount" type="checkbox" value="4"/>100-300万 &nbsp;
				<input id="preferenceAmount" name="preferenceAmount" type="checkbox" value="5"/>300万以上 &nbsp;
			</td>
		</tr>
	</tbody>
</table>
</body>

<script type="text/javascript">
//初始化被选中的兴趣爱好, 参加活动等
$(document).ready(function(){
	var preferenceGoalArray = "${customerExt.preferenceGoal}".split(",");
	
	for(var i=0; i<preferenceGoalArray.length; i++){
	       $("#updateForm :checkbox[name=preferenceGoal]").each(function(){
	           if ($(this).val() == preferenceGoalArray[i]) {
	               $(this).attr('checked', 'checked');
	           }
	       });
	}
	
	var preferencePeriodArray = "${customerExt.preferencePeriod}".split(",");
	for(var i=0; i<preferencePeriodArray.length; i++){
	       $("#updateForm :checkbox[name=preferencePeriod]").each(function(){
	           if ($(this).val() == preferencePeriodArray[i]) {
	               $(this).attr('checked', 'checked');
	           }
	       });
	}
	
	var preferenceDecisionArray = "${customerExt.preferenceDecision}".split(",");
	for(var i=0; i<preferenceDecisionArray.length; i++){
	       $("#updateForm :checkbox[name=preferenceDecision]").each(function(){
	           if ($(this).val() == preferenceDecisionArray[i]) {
	               $(this).attr('checked', 'checked');
	           }
	       });
	}
	
	var preferenceRiskArray = "${customerExt.preferenceRisk}".split(",");
	for(var i=0; i<preferenceRiskArray.length; i++){
	       $("#updateForm :checkbox[name=preferenceRisk]").each(function(){
	           if ($(this).val() == preferenceRiskArray[i]) {
	               $(this).attr('checked', 'checked');
	           }
	       });
	}
	
	var preferenceProductArray = "${customerExt.preferenceProduct}".split(",");
	for(var i=0; i<preferenceProductArray.length; i++){
	       $("#updateForm :checkbox[name=preferenceProduct]").each(function(){
	           if ($(this).val() == preferenceProductArray[i]) {
	               $(this).attr('checked', 'checked');
	           }
	       });
	}
	
	var preferenceAmountArray = "${customerExt.preferenceAmount}".split(",");
	for(var i=0; i<preferenceAmountArray.length; i++){
	       $("#updateForm :checkbox[name=preferenceAmount]").each(function(){
	           if ($(this).val() == preferenceAmountArray[i]) {
	               $(this).attr('checked', 'checked');
	           }
	       });
	}

})

</script>
</html>


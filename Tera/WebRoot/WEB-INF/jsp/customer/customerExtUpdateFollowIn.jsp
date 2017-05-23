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
		<td align="left"><b>您最初接触到公司是通过何种方式：</b><hr width="100%" size=1 color=#E0ECFF align=center noshade></td>
		</tr>
							
		<tr>
			<td>
				<input id="followType" name="followType" type="checkbox" value="1"/>报纸、网络等媒体报道 &nbsp;
				<input id="followType" name="followType" type="checkbox" value="2"/>网络搜索 &nbsp;
				<input id="followType" name="followType" type="checkbox" value="3"/>亲友介绍 &nbsp;
				<input id="followType" name="followType" type="checkbox" value="4"/>${companyName}广告 &nbsp;
				<input id="followType" name="followType" type="checkbox" value="5"/>${companyName}工作人员介绍 &nbsp;
			</td>
		</tr>
		<tr>
			<td>
				<input id="followType" name="followType" type="checkbox" value="6"/>各类展会 &nbsp;
				<input id="followType" name="followType" type="checkbox" value="7"/>接到${companyName}的电话拜访 &nbsp;
				<input id="followType" name="followType" type="checkbox" value="8"/>${companyName}理财讲座 &nbsp;
				<input id="followType" name="followType" type="checkbox" value="9"/>市场活动 &nbsp;
				<input id="followType" name="followType" type="checkbox" value="10"/>其他 &nbsp;
			</td>
		</tr>
		<tr>
			<td align="left">&nbsp;</td>
		</tr>

		<tr>
		<td align="left"><b>您觉得${companyName}的哪些特点吸引了您的关注：</b><hr width="100%" size=1 color=#E0ECFF align=center noshade></td>
		</tr>
		<tr>
			<td>
				<input id="followPoint" name="followPoint" type="checkbox" value="1"/>经营模式创新 &nbsp;
				<input id="followPoint" name="followPoint" type="checkbox" value="2"/>风险低、收益高&nbsp;
				<input id="followPoint" name="followPoint" type="checkbox" value="3"/>能够帮助有需要的人 &nbsp;
				<input id="followPoint" name="followPoint" type="checkbox" value="4"/>公开透明的理财过程 &nbsp;
			</td>
		</tr>
		<tr>
			<td>
				<input id="followPoint" name="followPoint" type="checkbox" value="5"/>服务团队专业&nbsp;
				<input id="followPoint" name="followPoint" type="checkbox" value="6"/>资金流动性好 &nbsp;
				<input id="followPoint" name="followPoint" type="checkbox" value="7"/>灵活自主的投资方式 &nbsp;
				<input id="followPoint" name="followPoint" type="checkbox" value="8"/>专业完善的运作体系 &nbsp;
				<input id="followPoint" name="followPoint" type="checkbox" value="9"/>其他 &nbsp;
			</td>
		</tr>
	</tbody>
</table>
</body>

<script type="text/javascript">
//初始化被选中
$(document).ready(function(){
	var followTypeArray = "${customerExt.followType}".split(",");
	
	for(var i=0; i<followTypeArray.length; i++){
	       $("#updateForm :checkbox[name=followType]").each(function(){
	           if ($(this).val() == followTypeArray[i]) {
	               $(this).attr('checked', 'checked');
	           }
	       });
	}
	
	var followPointArray = "${customerExt.followPoint}".split(",");
	for(var i=0; i<followPointArray.length; i++){
	       $("#updateForm :checkbox[name=followPoint]").each(function(){
	           if ($(this).val() == followPointArray[i]) {
	               $(this).attr('checked', 'checked');
	           }
	       });
	}

})

</script>
</html>

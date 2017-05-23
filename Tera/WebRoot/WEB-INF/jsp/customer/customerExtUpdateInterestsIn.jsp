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
		<td align="left"><b>您的兴趣爱好（可多选择）：</b><hr width="100%" size=1 color=#E0ECFF align=center noshade></td>
		</tr>
							
		<tr>
			<td>
				<input id="interests" name="interests" type="checkbox" value="1"/>古董收藏 &nbsp;
				<input id="interests" name="interests" type="checkbox" value="2"/>戏曲歌剧 &nbsp;
				<input id="interests" name="interests" type="checkbox" value="3"/>体育赛事 &nbsp;
				<input id="interests" name="interests" type="checkbox" value="4"/>影视观摩 &nbsp;
				<input id="interests" name="interests" type="checkbox" value="5"/>旅游渡假 &nbsp;
				<input id="interests" name="interests" type="checkbox" value="6"/>博物展览 &nbsp;
				<input id="interests" name="interests" type="checkbox" value="7"/>饮食文化 &nbsp;
			</td>
		</tr>
		<tr>
			<td>
				<input id="interests" name="interests" type="checkbox" value="8"/>汽车游艇 &nbsp;
				<input id="interests" name="interests" type="checkbox" value="9"/>健康沙龙 &nbsp;
				<input id="interests" name="interests" type="checkbox" value="10"/>运动健身 &nbsp;
				<input id="interests" name="interests" type="checkbox" value="11"/>高尔夫 &nbsp; &nbsp;
				<input id="interests" name="interests" type="checkbox" value="12"/>钓鱼休闲 &nbsp;
				<input id="interests" name="interests" type="checkbox" value="13"/>宠物饲养 &nbsp;
				<input id="interests" name="interests" type="checkbox" value="99"/>其他 &nbsp;
			</td>
		</tr>
		<tr>
			<td align="left">&nbsp;</td>
		</tr>

		<tr>
		<td align="left"><b>您经常参加或未来有意向参加的活动（可多选择）：</b><hr width="100%" size=1 color=#E0ECFF align=center noshade></td>
		</tr>
		<tr>
			<td>
				<input id="activities" name="activities" type="checkbox" value="1"/>银行卡积分兑换 &nbsp;
				<input id="activities" name="activities" type="checkbox" value="2"/>移动电话积分兑换或话费优惠 &nbsp;
				<input id="activities" name="activities" type="checkbox" value="3"/>大型商场会员活动 &nbsp;
				<input id="activities" name="activities" type="checkbox" value="4"/>超市会员活动 &nbsp;
				<input id="activities" name="activities" type="checkbox" value="5"/>体育、健身、美容、汽车、餐饮、娱乐等俱乐部会员活动 &nbsp;
			</td>
		</tr>
		<tr>
			<td>
				<input id="activities" name="activities" type="checkbox" value="6"/>亲子互动活动 &nbsp;
				<input id="activities" name="activities" type="checkbox" value="7"/>子女教育讲座 &nbsp;
				<input id="activities" name="activities" type="checkbox" value="8"/>专家讲座活动 &nbsp;
				<input id="activities" name="activities" type="checkbox" value="9"/>行业动态分享活动 &nbsp;
				<input id="activities" name="activities" type="checkbox" value="10"/>社会公益活动 &nbsp;
			</td>
		</tr>
	</tbody>
</table>
</body>

<script type="text/javascript">
//初始化被选中的兴趣爱好, 参加活动等
$(document).ready(function(){
	var interestsarray = "${customerExt.interests}".split(",");
	
	for(var i=0; i<interestsarray.length; i++){
	       $("#updateForm :checkbox[name=interests]").each(function(){
	           if ($(this).val() == interestsarray[i]) {
	               $(this).attr('checked', 'checked');
	           }
	       });
	}
	
	var activitiesarray = "${customerExt.activities}".split(",");
	for(var i=0; i<activitiesarray.length; i++){
	       $("#updateForm :checkbox[name=activities]").each(function(){
	           if ($(this).val() == activitiesarray[i]) {
	               $(this).attr('checked', 'checked');
	           }
	       });
	}

})

</script>
</html>


<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<div id="top" >
	<div style="float:left;padding-top:18px ;padding-left:30px;font-size: 30px; font-weight:bold; font-style:微软雅黑;color: white"><a href="main.do" target="mainFrame"><img src="img/logo.gif" style="float:left;" /></a></div>
	
</div>
<div id="navigation" style="height:32px;" >
<span id ="nowTime" style="margin-left: 0px;margin-right: 7px;color: #2E2E2A;float:left;font-size: 16px;padding-left:20px;"></span>
<span style="position:relative;top:-5px;float:right;right:10px;">
		<table style="margin: 0px; padding: 0px;"  >
			<tr>
				<td style="vertical-align: middle" align="center" valign="middle">
						欢迎【<span style="font-weight:bold;">${loginCustomer.name}</span>】登陆
				</td>
				<td style="vertical-align: middle" align="center" valign="middle">
						<a title="退出登录" target="_parent" onclick="javascript:allExit();"
							style="vertical-align: middle;position:relative;">
							<img alt="退出登录" class="dotimg" src="img/square/but_exit.png" style="cursor:pointer;">
						</a>
				</td>
			</tr>
		</table>
	</span>
</div>

<script language="javascript">
function allExit(){
	$.ajax({ 
		url : "<%=basePath%>" + "paycenter/login.htm",
		type: "POST",
		success: function(data){
		}
		});
	var url="<%=basePath%>" + "paycenter/logout.htm";
	window.location=url;
	
}
<%--当前时间--%>
	function showtime(){
		now = new Date();
		month = now.getMonth();
		date = now.getDate();
		year = now.getYear();
		if (year< 1900) year = 1900 + year;
		hours = now.getHours();
		minutes = now.getMinutes();
		seconds = now.getSeconds();
		
		timeValue = year + "-";
		timeValue += (((month+1) <10)?"0":"") + (month+1) + "-";
		timeValue += ((date <10)?"0":"") + date + " "
		timeValue += hours + ":";
		timeValue += ((minutes <10)?"0":"") + minutes+":";
		timeValue += ((seconds <10)?"0":"") + seconds+"";
		document.getElementById("nowTime").innerHTML = timeValue;
		setTimeout("showtime()",1000);
	}
	showtime();

</script>
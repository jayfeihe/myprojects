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
<title>P2P综合业务系统</title>
<link href="css/global.css" type="text/css" rel="stylesheet"/>
<script src="js/jquery.min.js" type="text/javascript"></script>
<style type="text/css">
<!--
body{
	background-color: #FFFFFF;
	margin:0px; padding:0px;
}
-->
</style>
</head>
<body>
<div id="main">

	<div id="part1" class="part">
		<p class="title"><a href="#">数据表格</a></p>
		<div class="content">
			<table id="table1" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">Name</th>
					<th scope="col">Class</th>
					<th scope="col">Birthday</th>
					<th scope="col">Constellation</th>
					<th scope="col">Mobile</th>
				</tr>
				<tr>
					<td>isaac</td>
					<td>W13</td>
					<td>Jun 24th</td>
					<td>Cancer</td>
					<td>1118159</td>
				</tr>
				<tr>
					<td>girlwing</td>
					<td>W210</td>
					<td>Sep 16th</td>
					<td>Virgo</td>
					<td>1307994</td>
				</tr>
				<tr>
					<td>tastestory</td>
					<td>W15</td>
					<td>Nov 29th</td>
					<td>Sagittarius</td>
					<td>1095245</td>
				</tr>
				<tr>
					<td>lovehate</td>
					<td>W47</td>
					<td>Sep 5th</td>
					<td>Virgo</td>
					<td>6098017</td>
				</tr>
				<tr>
					<td>slepox</td>
					<td>W19</td>
					<td>Nov 18th</td>
					<td>Scorpio</td>
					<td>0658635</td>
				</tr>
				<tr>
					<td>smartlau</td>
					<td>W19</td>
					<td>Dec 30th</td>
					<td>Capricorn</td>
					<td>0006621</td>
				</tr>
				<tr>
					<td>whaler</td>
					<td>W19</td>
					<td>Jan 18th</td>
					<td>Capricorn</td>
					<td>1851918</td>
				</tr>
				<tr>
					<td>shenhuanyan</td>
					<td>W25</td>
					<td>Jan 31th</td>
					<td>Aquarius</td>
					<td>0621827</td>
				</tr>
				<tr>
					<td>tuonene</td>
					<td>W210</td>
					<td>Nov 26th</td>
					<td>Sagittarius</td>
					<td>0091704</td>
				</tr>
				<tr>
					<td>ArthurRivers</td>
					<td>W91</td>
					<td>Feb 26th</td>
					<td>Pisces</td>
					<td>0468357</td>
				</tr>
				<tr>
					<td>reconzansp</td>
					<td>W09</td>
					<td>Oct 13th</td>
					<td>Libra</td>
					<td>3643041</td>
				</tr>
				<tr>
					<td>linear</td>
					<td>W86</td>
					<td>Aug 18th</td>
					<td>Leo</td>
					<td>6398341</td>
				</tr>
				<tr>
					<td>laopiao</td>
					<td>W41</td>
					<td>May 17th</td>
					<td>Taurus</td>
					<td>1254004</td>
				</tr>
				<tr>
					<td>dovecho</td>
					<td>W19</td>
					<td>Dec 9th</td>
					<td>Sagittarius</td>
					<td>1892013</td>
				</tr>
			</table>

		</div>
	</div>



</div>

<script language="javascript">
$(document).ready(function(){
     $("#table1").find("tr").hover(
	     function(){
	        $(this).addClass("altrow");    //鼠标经过添加hover样式
	     },
	     function(){
	        $(this).removeClass("altrow");   //鼠标离开移除hover样式
	     }
     );
});
</script>

</body>
</html>

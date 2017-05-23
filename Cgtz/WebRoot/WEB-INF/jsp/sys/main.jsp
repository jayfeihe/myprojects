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
	background-color: #f6f6f6;
	margin:0px; padding:0px;
}
.btitle{
	text-align: center;
	font-weight: bold;
	font-size: 20px;
}
.bwirter{
	/* text-align: right; */
	font-size: 12px;
	margin: 5px 50px;
}
.bcontent{
	margin: 5px 70px;
}
.bfoot{
	text-align: right;
	font-size: 12px;
	font-weight: bold;
	margin: 5px 50px;
}

-->
</style>
</head>
<body>
<div id="main">

		<div class="part" id="part1" >
			<p class="title"><a href="javascript:void(0);" >系统公告</a></p>
			
			<div class="content">
				<br/>
				<div style="text-align: center;"><span class="btitle" >${bulletin.title }</span>
					<c:if test="${not empty bulletin.writer}">
						<span>&nbsp;&nbsp;&nbsp;【${bulletin.writer}】</span>
					</c:if>
				</div>
				<%-- <div class="bwirter">${bulletin.writer}</div> --%>
				<br />
				<div class="bcontent">${bulletin.content}</div>
				<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=2);margin-top: 20px" width="80%"  color=#d5d5d5 SIZE="3"></HR>
				<div class="bfoot"><a  href="javascript:void(0);" onclick="javascritp:window.location='sys/bulletin/showquery.do'" title="进入公告列表">&gt;&gt;更多<a/></div>
				
			</div>
		</div>
		
	</div>

</div>
</body>
</html>

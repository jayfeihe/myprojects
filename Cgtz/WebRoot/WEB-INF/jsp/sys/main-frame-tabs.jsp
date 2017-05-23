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

.tabs{width:auto; line-height:24px;}
.tabs .nav{height:30px;line-height:30px;}   
.tabs .nav span.active {position:relative;font-weight:bold;background-color: #85B6E2;}   
.tabs .nav span{padding:0 10px;float:left;border:1px solid #11a3ff;cursor:pointer;margin-bottom:-1px;margin-right:5px;}   
.tabs .doc{clear:both;height:auto;width:auto;padding:10px;border:1px solid #11a3ff;display:none;} 

-->
</style>
</head>
<body>

<div id="main">

	<div class="part" id="part1" >
		<p class="title"><a href="#">第一部分_tab标签</a></p>
		<div class="content">

			<div class="tabs">
				<div class="nav">
					<span id="tab1" iframSrc='top.do'>Tab标签1</span>
					<span id="tab2">Tab标签2</span>
					<span id="tab3">Tab标签3</span>
					<span id="tab4">Tab标签4</span>
				</div>
				<div class="doc" id="doc1">
				<iframe id="tabIframe" src="" frameborder="0" width="100%" height="100%"></iframe>		
				</div>
				
			</div>

		</div>
	</div>
		
		
</div>
<script type="text/javascript">
<!--
	var tabNo=0; //第1个tab开始
	$(document).ready(function(){
   		//默认第一个tab显示
        $('.nav span:first').addClass('active');   
   		document.getElementById("tabIframe").src=$('.nav span:first').attr("iframSrc");
        $('.doc:first').css('display','block');
        //为Tab添加点击动作事件
        $('.nav span').click( 
	    		function () {
	    			 tabNo = $(this).prevAll().length;
	    			 slide(tabNo);
	    			 document.getElementById("tabIframe").src=$(this).attr("iframSrc");
	    			 
	    		}
    		);   
    });
    //变换tab的样式
    function slide(tabNo){   
        $('.nav span').eq(tabNo).addClass('active').siblings().removeClass('active');   
       // $('.doc').eq(tabNo).css('display','block').siblings('.doc').css('display','none');   
    }
//-->
</script>
</body>
</html>


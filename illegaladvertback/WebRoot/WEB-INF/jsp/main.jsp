<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/include.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">

<title>违法广告预警后台管理系统</title>

<!-- Bootstrap core CSS -->
<link href="<%=basePath %>css/bootstrap/bootstrap.min.css"rel="stylesheet">
<%-- <link href="<%=basePath %>css/bootstrap/bootstrap-theme.min.css" rel="stylesheet"> --%>
<link href="<%=basePath %>css/bootstrap/bootstrap-select.min.css" rel="stylesheet" >
<link href="<%=basePath %>css/justified-nav.css" rel="stylesheet">
<link href="<%=basePath %>css/dashboard.css" rel="stylesheet">

<style type="text/css">
/*左侧菜单*/
.mqBack_sidebar-menu {
    border-left: 1px solid #c4c8cb;
    border-right: 1px solid #c4c8cb;
    border-top: 1px solid #c4c8cb;
    border-bottom: 1px solid #c4c8cb;
}
/*一级菜单*/
.mqBack_menu-first{
    height:45px;
    line-height:45px;
    background-color: #e9e9e9;
    border-top: 1px solid #efefef;
    border-bottom: 1px solid #e1e1e1;
    padding: 0;
    font-size: 14px;
    font-weight: normal;
    text-align: center;
}
/*一级菜单鼠标划过状态*/
.mqBack_menu-first:hover{
    text-decoration: none;
    background-color: #d6d4d5;
    border-top: 1px solid #b7b7b7;
    border-bottom: 1px solid #acacac;
}
/*二级菜单*/
.mqBack_menu-second li a{
    background-color: #f6f6f6;
    height:31px;
    line-height:31px;
    border-top: 1px solid #efefef;
    border-bottom: 1px solid #efefef;
    font-size: 12px;
    text-align:center;
}
/*二级菜单鼠标划过样式*/
.mqBack_menu-second li a:hover {
    text-decoration: none;
    background-color: #66c3ec;
    border-top: 1px solid #83ceed;
    border-bottom: 1px solid #83ceed;
    border-right: 3px solid #f8881c;
    border-left: 3px solid #66c3ec;
}
/*二级菜单选中状态*/
.mqBack_menu-second-selected {
    background-color: #66c3ec;
    height:31px;
    line-height:31px;
    border-top: 1px solid #83ceed;
    border-bottom: 1px solid #83ceed;
    border-right: 3px solid #f8881c;
    border-left: 3px solid #66c3ec;
    text-align:center;
}
/*覆盖bootstrap的样式*/
.mqBack_nav-list,.mqBack_nav-list li a{
    padding: 0px;
    margin: 0px;
}
</style>
</head>

<body id="mqBackMainBody">
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#" style="font-size: 20px;color:#fff;" id="hideMenus">违法广告后台管理系统</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right" style="margin-right: 2px;">
					<li style="margin-top: 12px;"><span
						style="font-size: 18px; color: #fff;">您好，${login_user.nickName}</span></li>
					<li style="margin-top: 10px;margin-left: 10px;">
						<input type="button" class="btn btn-default" value="注销" onclick='javascript:window.location = "/logout" '>
					</li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container-fluid" id="frame_content">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar" id="mqBackMenuDIv">
				<div class="row-fluid">
					<div class="span12">

						<div class="row-fluid">
							<div>
								<div class="mqBack_sidebar-menu">

									<c:forEach items="${sysMenu}" var="m" varStatus="idx">
										<a href="#articleMenu${idx.index}" ival="${m.menuId}"
											class="nav-header mqBack_menu-first collapsed"
											data-toggle="collapse">${m.menuName}</a>
										<ul id="articleMenu${idx.index}"
											class="nav mqBack_nav-list collapse mqBack_menu-second">
											<c:forEach items="${m.subMenuList}" var="subM">
												<li><a href="${subM.menuUrl}" ival="${subM.menuId}"
													target="right">${subM.menuName}</a></li>
											</c:forEach>
										</ul>
									</c:forEach>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 " id="mqBackContextDiv">
				<button type="button" class="close" aria-hidden="true"
					style="margin-top: 10px; margin-right: 25px; display: none;"
					id="mqBackCloseBtn">×</button>
				<div id="mqBackMainDiv"></div>
			</div>
		</div>
	</div>
</body>


<script src="<%=basePath %>js/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="<%=basePath %>js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
<script src="<%=basePath %>js/bootstrap/bootstrap-select.min.js"></script>
<script src="<%=basePath %>js/bootstrap/defaults-zh_CN.min.js"></script>
<script src="<%=basePath %>js/datepicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=basePath %>js/common.js" type="text/javascript"></script>
<script src="<%=basePath %>js/main/menuToggle.js" type="text/javascript"></script>
<script src="<%=basePath %>js/md5.js" type="text/javascript"></script>

<script type="text/javascript">
$.ajaxSetup({   
    contentType:"application/x-www-form-urlencoded;charset=utf-8",   
    cache:false ,   
    complete:function(XHR,TS){   
    	var resText=XHR.responseText; 
    	if(resText == 'session-invalidate') {
    		window.location = "/login";
    	}
    }    
});
</script>

<script type="text/javascript">
	$(function() {
		$(".mqBack_sidebar-menu a").click(function() {
			var url = $(this).attr("href");
			var menuId=$(this).attr("ival");
			if (url.indexOf("#articleMenu") == -1) {
				$("#mqBackCloseBtn").hide();
				$("#mqBackMainDiv").load(url + "?rnd=" + Math.random()+"&menuIdParam="+menuId);
				$("#mqBackCloseBtn").show();
				return false;
			}
		});

		$("#mqBackCloseBtn").click(function() {
			$("#mqBackMainDiv").html("");
			$("#mqBackCloseBtn").hide();
		});
		
		 $('#mqBackMenuDIv').menuToggle({
	            'ctrlBtn':'hideMenus',
	            'speed':400,
	            'width':$('#mqBackMenuDIv').outerWidth(),
	            'contextDiv':'mqBackMainDiv'
	        });
	});

	//去除遮罩
	function removeModal(){
		$(".modal-backdrop").remove();
		$("#mqBackMainBody").removeAttr("class").removeAttr("style");
	}
	
	function jumpToMenu(url) {
		$("#mqBackCloseBtn").hide();
		if (url.indexOf("?") > 0) {
			$("#mqBackMainDiv").load(url + "&rnd=" + Math.random()+"&menuIdParam="+ "");
		}else{
			$("#mqBackMainDiv").load(url + "?rnd=" + Math.random()+"&menuIdParam="+ "");
		}
		$("#mqBackCloseBtn").show();
		removeModal();
	}
</script>

</html>
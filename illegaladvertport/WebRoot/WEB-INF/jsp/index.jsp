<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>"/>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<link rel="icon" href="<%=basePath%>images/favicon.ico" type="image/x-icon"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/bootstrap/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/bootstrap/bootstrap-theme.min.css">
<!-- 	<link href="http://cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet"> -->
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/tab_page.css">
<%-- 	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/pinterest.css"> --%>
	<script type="text/javascript" src="<%=basePath%>js/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jquery/jquery.wookmark.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/bootstrap/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/lib/masonry.pkgd.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/lib/imagesloaded.pkgd.min.js"></script>
<%-- 	<script type="text/javascript" src="<%=basePath%>js/pinterest_grid.js"></script> --%>
	<script type="text/javascript" src="<%=basePath%>js/datepicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/echarts.min.js"></script>
	<title>违法广告预警系统</title>
	
	<style type="text/css">
		#latest-gallery-wrapper,#severe-gallery-wrapper  {
			position: relative;
			/* max-width: 75%;
			width: 75%;  */
			margin: 20px auto;
		}
	</style> 
	
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" id="navbar-fixed-top">
    	<div class="container-fluid">
        	<div class="navbar-header">
	            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar"> 
	            	<span class="sr-only">Toggle navigation</span>
	 				<span class="icon-bar"></span>
	 				<span class="icon-bar"></span>
	 				<span class="icon-bar"></span>
	            </button>
	            <a class="navbar-brand set-brand" href="<%=basePath%>index">
	            	<img class="logo" src="<%=basePath%>images/logo_2.png">
	            </a>
	        </div>
	        <div id="navbar" class="navbar-collapse collapse">
	            <ul class="nav navbar-nav navbar-center">
	                <li class="active" id="active">
	                	<a href="<%=basePath%>index">首 页</a>
	                </li>
	                <li >
	                	<a href="<%=basePath%>confirmIllegal/list">违法确认列表</a>
	                </li>
	                <li>
	                	<a href="<%=basePath%>userAccount/center">个人中心</a>
	                </li>
	            </ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown ssp-dropdown"> 
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
							<h2>用户  ${login_user.account }</h2>
							<img src="<%=basePath%>images/xlcd.png" alt="" class="jiaobtn">
						</a>
					    <ul class="down-menu" id="down-menu">
					         <!-- <li>
					        	<a href="javascript:void(0);"><img src="images/tb2.png"> 公 告</a>
					        </li>
					        <li>
					        	<a href="javascript:void(0);"><img src="images/tb3.png"> 帮 助</a>
					        </li> -->
					        <li>
					        	<a href="/logout"><img src="<%=basePath%>images/tb4.png"> 退出</a>
					        </li>
					    </ul>
					</li>
	            </ul>
	        </div>
		</div>
	</nav>
	<div class="container-fluid dash index" id="container-fluid">
		<div class="row margin-bottom-24" id="dataCount">
			<div class="col-md-12">
				<h1 class="h sh" id="h" style="margin-bottom: 10px;">数据概况</h1>
				<!-- 以前的日历 -->
				<div class="pull-right datePanForm">
		      		<img class="icon" src="<%=basePath%>images/rili.png">
		       		<input type="text" class="form-control dateInput" id="dateChange" style="width:250px;" value="">
		       		<img id="dateArrow" class="arrow" style="cursor: pointer;" src="<%=basePath%>images/xlcd.png">
		        	<div class="datePan" id="datePan">
	                  <p>请选择一个日期范围</p>
	                  <div class="input">
	                   	<input type="text" class="Wdate" 
	                   		id="confirmTimeMin" name="confirmTimeMin" style="height: 25px; line-height: 25px; width: 95px;" class="xinput"/>
						-
						<input type="text"  class="Wdate" 
							id="confirmTimeMax" name="confirmTimeMax" style="height: 25px; line-height: 25px; width: 95px;" class="xinput"/>
	                  </div>
	                  <p>快捷日期：</p>	
	                  <div class="select" style="height:115px;">
	                    <ul class="quick-list">
	                       <li class="active"><a>今日</a></li>
	                       <li><a>昨日</a></li>
	                       <li><a>过去7天</a></li>
	                       <li><a>过去15天</a></li>
	                       <li><a>过去30天</a></li>
	                       <li><a>上周</a></li>
	                       <li><a>上月</a></li>
	                       <li><a>本月</a></li>
	                    </ul>
	                   </div>
	                   <div>
	                   	<input type="button" class="btn btn-ssp" value="确定" onclick="bindDate('dataCount');"/>
	                   	<input type="button" class="btn btn-ssp" value="取消" onclick="cancelDate('dataCount');"/>
	                   </div>
               		</div>
      			</div>
				<!-- 数据概况统计 -->
				<div id="dataCountArea">
					
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-default">
				  	<div class="panel-heading">
				  		<div class="row">
				  			<div class="newadd">
								<label class="checkbox-inline activity1" >
									<input type="checkbox" name="eType" checked="checked" value="S1">
									<span></span> 
									严重违法广告活动
								</label>	
								<label class="checkbox-inline adboard1">
									<input type="checkbox"  name="eType" value="C1">
									<span></span> 
									 一般违法广告活动
								</label>
								<label class="checkbox-inline media1">
									<input type="checkbox"  name="eType" value="L1"> 
									<span></span> 
									轻微违法广告活动
								</label>
				  			</div>
							
							<div class="newadd">
								<label class="checkbox-inline activity5">
									<input type="checkbox" name="eType" checked="checked" value="S2">
									<span></span>  
									严重违法广告创意
								</label>
								<label class="checkbox-inline adboard5">
									<input type="checkbox"  name="eType" value="C2">
									<span></span> 
									一般违法广告创意
								</label>
								<label class="checkbox-inline media5">
									<input type="checkbox"  name="eType" value="L2"> 
									<span></span> 
									轻微违法广告创意
								</label>
				  			</div>
							
				  			<div class="newadd">
								<label class="checkbox-inline activity3" >
									<input type="checkbox" name="eType" value="S6">
									<span></span> 
									总体已整改严重违法广告创意
								</label>	
								<label class="checkbox-inline adboard3">
									<input type="checkbox"  name="eType" value="C6">
									<span></span> 
									总体已整改一般违法广告创意
								</label>
								<label class="checkbox-inline media3">
									<input type="checkbox"  name="eType" value="L6"> 
									<span></span> 
									总体已整改轻微违法广告创意
								</label>
				  			</div>

				  			<div class="newadd">
								<label class="checkbox-inline activity4">
									<input type="checkbox" name="eType" value="S7">
									<span></span> 
									我方已整改严重违法广告创意
								</label>
								<label class="checkbox-inline adboard4">
									<input type="checkbox"  name="eType" value="C7"> 
									<span></span> 
									我方已整改一般违法广告创意
								</label>
								<label class="checkbox-inline media4">
									<input type="checkbox"  name="eType"  value="L7"> 
									<span></span> 
									我方已整改轻微违法广告创意
								</label>
				  			</div>
				  			
				  			<c:if test="${login_company.type eq 1 }">
					  			<div class="newadd">
									<label class="checkbox-inline activity2" >
										<input type="checkbox" name="eType" value="S3">
										<span></span> 
										严重违法涉及媒体
									</label>	
									<label class="checkbox-inline adboard2">
										<input type="checkbox"  name="eType" value="C3">
										<span></span> 
										一般违法涉及媒体
									</label>
									<label class="checkbox-inline media2">
										<input type="checkbox"  name="eType" value="L3"> 
										<span></span> 
										轻微违法涉及媒体
									</label>
					  			</div>
				  			</c:if>
				  			
				  			<c:if test="${login_company.type eq 2 }">
					  			<div class="newadd">
									<label class="checkbox-inline activity2" >
										<input type="checkbox" name="eType" value="S4">
										<span></span> 
										严重违法广告主
									</label>	
									<label class="checkbox-inline adboard2">
										<input type="checkbox"  name="eType" value="C4">
										<span></span> 
										一般违法广告主
									</label>
									<label class="checkbox-inline media2">
										<input type="checkbox"  name="eType" value="L4"> 
										<span></span> 
										轻微违法广告主
									</label>
					  			</div>
				  			</c:if>
				  			
				  			<div class="newadd">
								<label class="checkbox-inline activity6">
									<input type="checkbox" name="eType" value="S5">
									<span></span> 
									严重违法广告活动占比
								</label>
								<label class="checkbox-inline adboard6">
									<input type="checkbox"  name="eType"  value="C5">
									<span></span> 
									一般违法广告活动占比
								</label>
								<label class="checkbox-inline media6">
									<input type="checkbox"  name="eType" value="L5">
									<span></span> 
									轻微违法广告活动占比
								</label>
				  			</div>
				  		</div>
				  	</div>
				  	
				  	<div class="panel-body" id="chartArea">
				    	<div id="main" style="width: 100%; height: 400px;"></div>
				  	</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-12">
				<h1 class="h sh" id="h" style="margin-bottom: 10px;">违法列表</h1>
				<ul class="nav nav-tabs isac" id="isac" role="tablist">
					<li role="presentation" class="active"><a href="#latestIllegal" role="tab" data-toggle="tab"  id="latestTab">最新违法广告创意</a></li>
					<li role="presentation"><a href="#severeIllegal" role="tab" data-toggle="tab" id="severeTab">严重违法广告创意</a></li>
				</ul>
				<div class="tab-content">
					<!-- 最新违法 -->
					<div role="tabpanel" class="tab-pane illegal active" id="latestIllegal">
						 
					</div>
					
					<!-- 严重违法 -->
					<div role="tabpanel" class="tab-pane illegal" id="severeIllegal">
						
					</div>
					
					<div id="bgBlock" class="bgBlock">
						图片正在加载，请稍等...
					</div>
				</div>
			</div>			
		</div>
	</div>

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	<%-- <%@ include file="/WEB-INF/jsp/img_mask.jsp"%> --%>
	
	<script type="text/javascript" src="<%=basePath%>js/script.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/index/index.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/index/advchart.js"></script>
</body>
</html>
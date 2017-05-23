<%@page import="com.greenkoo.record.model.DataRecord"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath %>"/>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<link rel="icon" href="<%=basePath%>images/favicon.ico" type="image/x-icon"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/table_sorter.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/bootstrap/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/bootstrap/bootstrap-theme.min.css">
<!-- 	<link href="http://cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet"> -->
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/tab_page.css">
<%-- 	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/pinterest.css"> --%>
	<script type="text/javascript" src="<%=basePath%>js/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jquery/jquery.wookmark.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jquery/jquery.tablesorter.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/lib/imagesloaded.pkgd.min.js"></script>
<%-- 	<script type="text/javascript" src="<%=basePath%>js/pinterest_grid.js"></script> --%>
	<script type="text/javascript" src="<%=basePath%>js/bootstrap/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/script.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/datepicker/WdatePicker.js"></script>
	<title>违法广告预警系统</title>
	<style type="text/css">
		#advertiser-gallery-wrapper {
			position: relative;
			/* max-width: 75%;
			width: 75%;  */
			margin: 20px auto;
		}
	</style> 
</head>
<body>
	<!-- 顶部的导航块 -->
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
	                <li>
	                	<a href="<%=basePath%>index">首 页</a>
	                </li>
	                <li>
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
	
	<div class="container-fluid dash index">
		<div class="row blank">
			<div class="col-md-12">
				<form class="form-inline" style="text-align: center;" role="form">
					<div class="form-group">
						<label class="sr-only" for="exampleInputEmail2"></label>
						<input class="form-control big-search"  placeholder="可输入广告名称/创意名称/媒体名称查询" id="keyWord">
					</div>
					<input type="button" class="btn btn-yellow btn-ww" value="GO" onclick="queryAdvertDetail($('#keyWord').val().trim());"/>
				</form>
			</div>
		</div>

		<div class="row" style="margin-bottom: 25px;">
			<div class="col-md-12 fsc">
				<p><span class="fsd fsb">营销活动</span>：<span class="fse">${record.adName } </span></p>
				<p><span class="fsf">广 告 主</span>：<a class="gold" href="${record.advertiserUrl }" target="_blank">${record.advertiserUrl } - ${record.advertiserName }</a></p>
				<p><span class="fsf">活动页面</span>：<a class="gold" href="${record.landingUrl }" target="_blank">${record.landingUrl }</a></p>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-12"  id="speciall">
				<ul class="nav nav-tabs fucb" role="tablist" style="padding: 0px 40px; text-align: center;">
					<li role="presentation" class="active">
						<a href="#home" class="fuca" role="tab" data-toggle="tab">
							<span class="title">违法广告创意</span>
							<span class="content">
								共计<span class="impor changeColor" id="creativeCount"></span>个
							</span>
						</a>
					</li>
					<li role="presentation" class="last">
						<a href="#profile" class="fuca" role="tab" data-toggle="tab">
							<span class="title">涉及媒体</span>
							<span class="content">
								共计<span class="impor">${mediaCount }</span>个
							</span>
						</a>
					</li>
				</ul>
				<!-- Tab panes -->
				<div class="tab-content illegal2">
					<div role="tabpanel" class="tab-pane active" id="home">
						<div class="row">
							<div class="col-md-12">
								<form class="form-inline search" role="form" id="adverterForm">
									<input type="hidden" value="${record.id } " name="id"/>
					        		<div class="pull-left datePanForm">
					        			<img class="icon" src="<%=basePath%>images/rili.png">
						        		<input type="text" class="form-control dateInput" id="dateChange" name="dateChange" style="width:250px;" value="">
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
					                               <li><a>今日</a></li>
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
						                        <input type="button" class="btn btn-ssp" value="确定" onclick="bindDate('adverterForm');"/>
						                      	<input type="button" class="btn btn-ssp" value="取消" onclick="cancelDate('adverterForm');"/>
					                        </div>
					                    </div>
					        		</div>
			
									<div class="form-group">
										<label class="sr-only" for="exampleInputEmail2"></label>
										<select class="form-control" name="terminalType">
											<option value="">全部终端</option>
											<option value="<%=DataRecord.TERMINAL_TYPE_PC %>">PC端</option>
											<option value="<%=DataRecord.TERMINAL_TYPE_APP %>">移动端</option>
										</select>
									</div>
									
									<div class="form-group">
										<div class="wenhao" id="wenhao" style="position:relative">
											<label class="sr-only" for="exampleInputEmail2"></label>
											<select class="form-control" name="status">
												<option value="">全部</option>
												<option value="0">未知</option>
												<option value="1">已整改</option>
											</select>
											<div class="BangZhu_box" style="width:15px;height:15px;position:absolute;right:0;top:0;">
												<img class="BangZhu" id="BangZhu" src="<%=basePath %>images/bangzhu.png" style="width:100%;height:auto;position:absolute;top:0;right:0;display:block;cursor:pointer">
											</div>
										</div>
									</div>
			
									<input type="button" class="btn btn-yellow" value="搜索" onclick="loadAdverterIllegal(1)"/>
								</form>
								<div id="adverterIllegal"></div>
							</div>
						</div>
					</div>
					
					<div role="tabpanel" class="tab-pane" id="profile">
						<div id="relatedArea" class="addisplay related-list" style="background: #FFF;">
							<table class="table tablesorter sa">
								<thead>
									<tr>
										<th class="left" id="firstOne">媒体</th>
											<th class="ri">违法广告活动数<!-- <i class="fa fa-caret-down" aria-hidden="true"></i> --></th>
											<th class="ri">违法广告创意数<!-- <i class="fa fa-caret-down" aria-hidden="true"></i> --></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${datas }" var="data" varStatus="status">
										<tr>
											<td class="left moren">${data.relatedName }</td>
											<td>${data.adCount }</td>
											<td>${data.creativeCount }</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				
					<div id="bgBlock" class="bgBlock">
						图片正在加载，请稍等...
					</div>
				</div>
				
				<!-- ***************************************对应问号的定位内容开始************************************ -->
				<div class="wen_top" style="position:absolute;background:#ccc;width:250px;height:60px;left:247px;top:70px;padding:5px 10px;display:none;z-index:9999999999999;border-radius:5px;">
					<p style="font-size:12px;line-height:25px;text-align:justify;">未知：我方未提交整改信息     <br/>已整改：我方已提交整改信息</p>
					<div class="jiao_top" style="position:absolute;right:15px;bottom:-10px;width:0;height:0;border-top:10px solid #ccc;border-right:10px solid transparent;border-left:10px solid transparent;"></div>
				</div>
				<!-- ***************************************对应问号的定位内容结束************************************ -->
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="correctConfirmModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<img src="<%=basePath%>images/guanbi.png" alt="" class="close_icon" data-dismiss="modal">
					<h4 style="font-size: 14px;" class="modal-title" id="myModalLabel">整改确认</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" id="correctConfirmForm">
						<input type="hidden" id="infoId" name="infoId" value="">
						
					   <div class="center">
					   		<p>其他主体方整改信息</p>
					   		<table class="table">
								<thead>
									<tr>
										<td><strong>主体方</strong></td>
										<td><strong>整改时间</strong></td>
										<td><strong>备注</strong></td>
									</tr>
								</thead>
								<tbody>
									
								</tbody>
							</table>
							<div class="changeTime">
								<p>整改信息提交</p>
								<div class="time">
									整改时间
									<img src="<%=basePath%>images/shijian.png" alt="" class="Left">
									<img src="<%=basePath%>images/timexiala.png" alt="" class="Right">
								</div>
								<div class="time_box">
									<p>日期范围:</p>
									<div id="round">
										<input type="text" id="date" placeholder="xxxx-xx-xx" class="input_one"> 日
									    <input type="text" id="hour" placeholder="xx" class="input_two"> 时 
									    <input type="text" id="minute" placeholder="xx" class="input_two"> 分
									</div>
									<div class="time_bottom">
										<input type="button" class="date_btn" id="confirmDate" value="确 认">
										<input type="button" class="date_btn" id="cancelDate" value="取消">
									</div>
									<input type="hidden" id="correctionTime" name="correctionTime" value="">
								</div>
								<div class="beizhu">
									<textarea style="resize: none; " id="wenben" name="remark" class="PUB_TXT fast_content fl" placeholder="请输入整改备注信息……"></textarea>
								</div>
							</div>
							<div class="bottom">
								<input type="button" class="btn btn-correct" id="submitCorrect" value="提交">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="correctedModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<img src="<%=basePath%>images/guanbi.png" alt="" class="close_icon" data-dismiss="modal">
					<h4 style="font-size: 14px;" class="modal-title" id="myModalLabel">整改确认</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" id="correctedForm">
					   <input type="hidden" id="infoId" name="infoId" value="">
						
					   <div class="center">
					   		<p>其他主体方整改信息</p>
					   		<table class="table">
								<thead>
									<tr>
										<td><strong>主体方</strong></td>
										<td><strong>整改时间</strong></td>
										<td><strong>备注</strong></td>
									</tr>
								</thead>
								<tbody>
								
								</tbody>
							</table>
							<div class="changeTime">
								<p>整改信息提交</p>
								<div class="time">
									整改时间
									<img src="<%=basePath%>images/shijian.png" alt="" class="Left">
									<img src="<%=basePath%>images/timexiala.png" alt="" class="Right">
								</div>
								<div class="time_box">
									<p>日期范围:</p>
									<div id="round">
										<input type="text" id="date" placeholder="xxxx-xx-xx" class="input_one"> 日
									    <input type="text" id="hour" placeholder="xx" class="input_two"> 时 
									    <input type="text" id="minute" placeholder="xx" class="input_two"> 分
									</div>
									<div class="time_bottom">
										<input type="button" class="date_btn" id="confirmDate" value="确 认">
										<input type="button" class="date_btn" id="cancelDate" value="取消">
									</div>
									<input type="hidden" id="correctionTime" name="correctionTime" value="">
								</div>
								<div class="beizhu">
									<textarea style="resize: none; " id="wenben" name="remark" class="PUB_TXT fast_content fl" placeholder="请输入整改备注信息……"></textarea>
								</div>
							</div>
							<div class="bottom">
								<input type="button" class="btn btn-correct" id="confirmUpdate" value="确认修改" onclick="confirmUpdate()" />
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	<%-- <%@ include file="/WEB-INF/jsp/img_mask.jsp"%> --%>
    <script type="text/javascript" src="<%=basePath%>js/detail/advertiser.js"></script>
    
    <script type="text/javascript" src="<%=basePath %>js/feedback.js"></script>
    
    <script type="text/javascript">
    	$(function(){
    		$('table').tablesorter();
    		
    		// 整改初始化
    		feedback.openModel('adverterIllegal');
    		feedback.closeModel();
    		feedback.init('correctConfirmForm');
    		feedback.init('correctedForm');
    		feedback.submitCorrect({'pageType':'2','page':'1'});
    		feedback.confirmUpdate();
    	});
    </script>
</body>
</html>
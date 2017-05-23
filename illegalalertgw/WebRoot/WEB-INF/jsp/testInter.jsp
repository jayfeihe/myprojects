<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>" />
<title>广告违法预警接口测试</title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>

<script src="http://cdn.bootcss.com/jquery.form/3.51/jquery.form.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>

<script type="text/javascript" src="<%=basePath %>js/datepicker/WdatePicker.js"></script>
</head>

<body>
	
	<div class="panel panel-primary">
		<div class="panel-heading">违法广告预警接口测试（网络图片地址）</div>
		<div class="panel-body" style="padding: 20px 10px;">
			<form class="form-horizontal" role="form" id="testUrlForm">
				<div class="form-group">
					<label for="inputText" class="col-sm-1 control-label">广告名</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="adName" name="adName" value="优衣库波点衣服">
					</div>
					<label for="inputText" class="col-sm-1 control-label">创意名称</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="adCreativeName"
							name="adCreativeName" value="新品上市">
					</div>
					<label for="inputText" class="col-sm-1 control-label">活动落地页</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="landingUrl"
							name="landingUrl" value="http://www.uniqlo.cn/">
					</div>
				</div>
				<div class="form-group">
					<label for="inputText" class="col-sm-1 control-label">广告主域名</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="advertiserUrl"
							name="advertiserUrl" value="http://www.taobao.com">
					</div>
					<label for="inputText" class="col-sm-1 control-label">广告主名称</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="advertiserName"
							name="advertiserName" value="淘宝">
					</div>
					<label for="inputText" class="col-sm-1 control-label">投放媒体域名</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="mediaUrl"
							name="mediaUrl" value="http://www.sina.com">
					</div>
				</div>
				<div class="form-group">
					<label for="inputText" class="col-sm-1 control-label">投放媒体名称</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="mediaName"
							name="mediaName" value="新浪">
					</div>
					<label for="inputText" class="col-sm-1 control-label">投放终端</label>
					<div class="col-sm-3">
						<select class="form-control" id="terminalType" name="terminalType">
							<option value="1" selected="selected">PC</option>
							<option value="2">APP</option>
							<option value="3">WAP</option>
						</select>
					</div>
					<label for="inputText" class="col-sm-1 control-label">Adx域名</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="adxUrl" name="adxUrl"  value="Adx域名">
					</div>
				</div>
				<div class="form-group">
					<label for="inputText" class="col-sm-1 control-label">Adx名称</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="adxName" name="adxName" value="Adx名称">
					</div>
					<label for="inputText" class="col-sm-1 control-label">数据类型</label>
					<div class="col-sm-3">
						<select class="form-control" id="type" name="type">
							<option value="1">坐席审核后的-疑似违法广告</option>
							<option value="2" selected="selected">领导审核后的-确认违法广告</option>
						</select>
					</div>
					<label for="inputText" class="col-sm-1 control-label">违法程度</label>
					<div class="col-sm-3">
						<select class="form-control" id="level" name="level">
							<option value="1" selected="selected">严重违法</option>
							<option value="2">一般违法</option>
							<option value="3">轻微违法</option>
						</select>
					</div>
				</div>
				
				<div class="form-group">
					<label for="inputText" class="col-sm-1 control-label">采集时间</label>
					<div class="col-sm-3">
						<input type="text" class="form-control Wdate" id="collectTime" name="collectTime"
							onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});"
							style="border: 1px solid #ccc; height: 35px;" value="2016-09-13 10:38:52">
					</div>
					<label for="inputText" class="col-sm-1 control-label">坐席审核时间</label>
					<div class="col-sm-3">
						<input type="text" class="form-control Wdate" id="checkTime" name="checkTime"
							onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});"
							style="border: 1px solid #ccc; height: 35px;" value="2016-09-15 10:39:29">
					</div>
					<label for="inputText" class="col-sm-1 control-label">领导确认时间</label>
					<div class="col-sm-3">
						<input type="text" class="form-control Wdate" id="confirmTime" name="confirmTime"
							onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});"
							style="border: 1px solid #ccc; height: 35px;" value="2016-09-20 10:39:42">
					</div>
				</div>
				<div class="form-group">
					<label for="inputText" class="col-sm-2 control-label">创意图片   网络地址</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" id="adPic" name="adPic"  value="http://d1.sina.com.cn/201608/01/1427196_caijing.jpg">
					</div>
					<label for="inputText" class="col-sm-2 control-label">信息ID</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" id="infoId" name="infoId"  value="1232342">
					</div>
				</div>
				<input type="button" class="btn btn-primary" value="测试" onclick="testInterUrl();">
			</form>
		</div>
	</div>
	
	<script type="text/javascript">
		
		function testInterUrl() {
			var params = $("#testUrlForm").serialize();
			$.ajax({
				method:'post',
				data:params,
				url:"<%=basePath%>url/testInter",
				success : function(data) {
					alert(data.message);
				}
			})
		}
	</script>
	
	<div class="panel panel-primary">
		<div class="panel-heading">违法广告预警接口测试（图片流）</div>
		<div class="panel-body" style="padding: 20px 10px;">
			<form class="form-horizontal" role="form" id="testStreamForm" enctype="multipart/form-data">
				<div class="form-group">
					<label for="inputText" class="col-sm-1 control-label">广告名</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="adName" name="adName" value="优衣库波点衣服">
					</div>
					<label for="inputText" class="col-sm-1 control-label">创意名称</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="adCreativeName"
							name="adCreativeName" value="新品上市">
					</div>
					<label for="inputText" class="col-sm-1 control-label">活动落地页</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="landingUrl"
							name="landingUrl" value="http://www.uniqlo.cn/">
					</div>
				</div>
				<div class="form-group">
					<label for="inputText" class="col-sm-1 control-label">广告主域名</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="advertiserUrl"
							name="advertiserUrl" value="http://www.taobao.com">
					</div>
					<label for="inputText" class="col-sm-1 control-label">广告主名称</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="advertiserName"
							name="advertiserName" value="淘宝">
					</div>
					<label for="inputText" class="col-sm-1 control-label">投放媒体域名</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="mediaUrl"
							name="mediaUrl" value="http://www.sina.com">
					</div>
				</div>
				<div class="form-group">
					<label for="inputText" class="col-sm-1 control-label">投放媒体名称</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="mediaName"
							name="mediaName" value="新浪">
					</div>
					<label for="inputText" class="col-sm-1 control-label">投放终端</label>
					<div class="col-sm-3">
						<select class="form-control" id="terminalType" name="terminalType">
							<option value="1" selected="selected">PC</option>
							<option value="2">APP</option>
							<option value="3">WAP</option>
						</select>
					</div>
					<label for="inputText" class="col-sm-1 control-label">Adx域名</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="adxUrl" name="adxUrl"  value="Adx域名">
					</div>
				</div>
				<div class="form-group">
					<label for="inputText" class="col-sm-1 control-label">Adx名称</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="adxName" name="adxName" value="Adx名称">
					</div>
					<label for="inputText" class="col-sm-1 control-label">数据类型</label>
					<div class="col-sm-3">
						<select class="form-control" id="type" name="type">
							<option value="1">坐席审核后的-疑似违法广告</option>
							<option value="2" selected="selected">领导审核后的-确认违法广告</option>
						</select>
					</div>
					<label for="inputText" class="col-sm-1 control-label">违法程度</label>
					<div class="col-sm-3">
						<select class="form-control" id="level" name="level">
							<option value="1" selected="selected">严重违法</option>
							<option value="2">一般违法</option>
							<option value="3">轻微违法</option>
						</select>
					</div>
				</div>
				
				<div class="form-group">
					<label for="inputText" class="col-sm-1 control-label">采集时间</label>
					<div class="col-sm-3">
						<input type="text" class="form-control Wdate" id="collectTime" name="collectTime"
							onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});"
							style="border: 1px solid #ccc; height: 35px;" value="2016-09-13 10:38:52">
					</div>
					<label for="inputText" class="col-sm-1 control-label">坐席审核时间</label>
					<div class="col-sm-3">
						<input type="text" class="form-control Wdate" id="checkTime" name="checkTime"
							onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});"
							style="border: 1px solid #ccc; height: 35px;" value="2016-09-15 10:39:29">
					</div>
					<label for="inputText" class="col-sm-1 control-label">领导确认时间</label>
					<div class="col-sm-3">
						<input type="text" class="form-control Wdate" id="confirmTime" name="confirmTime"
							onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});"
							style="border: 1px solid #ccc; height: 35px;" value="2016-09-20 10:39:42">
					</div>
				</div>
				<div class="form-group">
					<label for="inputText" class="col-sm-1 control-label">创意图片</label>
					<div class="col-sm-3">
						<input type="file" name="file">
					</div>
				</div>
				<input type="button" class="btn btn-primary" value="测试" onclick="testInterStream();">
			</form>
		</div>
	</div>

	<script type="text/javascript">
		
		function testInterStream() {
			var adPic = $("input[name='file']").val();
			if(adPic == '') {
				alert("请上传图片");
				return;
			}
			
			$("#testStreamForm").ajaxSubmit({
				method:'post',
				contentType:"multipart/form-data",
				url:"<%=basePath%>stream/testInter",
				success : function(data) {
					alert(data.message);
				}
			})
		}
	</script>
</body>
</html>
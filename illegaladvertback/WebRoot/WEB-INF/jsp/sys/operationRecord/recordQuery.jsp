<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>操作日志</title>
</head>
<body>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">操作日志</h3>
		</div>
		
		<div class="panel-body">
			<form role="form" id="queryForm">
				<div class="form-inline">
					<div class="form-group">
						<label for="inputText" class="control-label">操作日期：</label> 
						<input type="text" class="Wdate form-control" id="confirmTimeMin" name="createTimeMin"/>
						-
						<input type="text"  class="Wdate form-control" id="confirmTimeMax" name="createTimeMax"/>
                    </div>
					<div class="form-group">
						<input type="button" class="btn btn-primary btn-sm form-control" value="查&nbsp;&nbsp;&nbsp;&nbsp;询" id="queryBtn">
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="queryList"></div>
	
	<script type="text/javascript" src="<%=basePath%>js/main/operationRecord.js"></script>
	<script type="text/javascript">
		$(function() {
			operationRecord.submitForm({
				'url':'/sys/operationRecord/list',
				'curPage':1
			});
			
			// 点击查询
			operationRecord.queryForm({
				'url':'/sys/operationRecord/list',
				'curPage':1
			});
			
			
			$("#confirmTimeMin").on("click", function(){
				$("#confirmTimeMax").val("");
				WdatePicker({
					skin:'whyGreen',
					dateFmt:'yyyy-MM-dd', 
					maxDate:'%y-%M-%d' 
				});
			});
			
			$("#confirmTimeMax").on('click',function(){
				WdatePicker({
					skin:'whyGreen',
					dateFmt:'yyyy-MM-dd', 
					maxDate:'%y-%M-%d'
				});
			});
		});

	</script>
	
	<script type="text/javascript">
		function nextPage(number) {
			var curPage = $("#curPage").val();
			curPage = parseInt(curPage);
			var totalpage = $("#totalPage").val();
			totalpage = parseInt(totalpage);
			
			var nextCurPage = curPage + number;
			if (nextCurPage == 0){
				nextCurPage = 1;
			}else if(nextCurPage > totalpage){
				nextCurPage = totalpage;
			}
			
			operationRecord.submitForm({
				'url':'/sys/operationRecord/list',
				'curPage':nextCurPage
			});
		}
		function goPage(pNum) {
			operationRecord.submitForm({
				'url':'/sys/operationRecord/list',
				'curPage':pNum
			});
		}
	</script>
</body>
</html>
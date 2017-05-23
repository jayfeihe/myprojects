<%@page import="com.greenkoo.company.model.UserCompany"%>
<%@page import="com.greenkoo.sys.constants.CommonConstants"%>
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
<title>个人信息管理</title>

<style type="text/css">
	.err_tip {
		color: red;}
</style>

</head>
<body>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">个人信息管理</h3>
		</div>
	</div>
	<div id="queryList"></div>

	<!-- 重置密码-->
	<div class="modal fade" id="updatePwdModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 700px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">重置密码</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="updatePwdForm">
					<input type="hidden" class="form-control" id="userId" name="userId" value="">
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label">* 原密码：</label>
							<div class="col-sm-6">
								<input type="password" class="form-control" id="oldPwd" name="oldPwd" placeholder="原密码">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="oldPwdTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label">* 密码：</label>
							<div class="col-sm-6">
								<input type="password" class="form-control" id="pwd" name="pwd" placeholder="密码(6-12位大小写字母，数字)">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="pwdTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label">* 重复密码：</label>
							<div class="col-sm-6">
								<input type="password" class="form-control" id="confirmPwd"
									name="confirmPwd" placeholder="重复密码(6-12位大小写字母，数字)">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="confirmPwdTip"></span>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="confirmUpdate">确定</button>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="<%=basePath%>js/main/sysUser.js"></script>
	<script type="text/javascript">
		$(function() {
			sysUser.submitForm({
				'url':'/sys/user/list',
				'curPage':1
			});
			
			// 点击查询
			/* sysUser.queryForm({
				'url':'/account/media/list',
				'curPage':1
			}); */
			
			// 初始化模态框事件
			sysUser.onCloseModal();
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
			
			sysUser.submitForm({
				'url':'/sys/user/list',
				'curPage':nextCurPage
			});
		}
		function goPage(pNum) {
			sysUser.submitForm({
				'url':'/sys/user/list',
				'curPage':pNum
			});
		}
	</script>
</body>
</html>
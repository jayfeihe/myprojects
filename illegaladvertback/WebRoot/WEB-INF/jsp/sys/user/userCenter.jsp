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
	<div id="queryList">
		<table class="table table-bordered table-hover">
		 	<thead>
		 		<tr>
		 			<th style="text-align:center;">序号</th>
		 			<th style="text-align:center;">登录帐号</th>
		 			<th style="text-align:center;">创建时间</th>
		 			<th style="text-align:center;">修改时间</th>
		 			<th style="text-align:center;">操作</th>
		 		</tr>
		 	</thead>
		 	<tbody>
		 		<c:if test="${fn:length(datas) eq 0 }">
		 			<tr>
		 				<td colspan="7" align="center" style="color: red; font-weight: bold;">未查到数据</td>
		 			</tr>
		 		</c:if>
		 		<c:if test="${fn:length(datas) gt 0 }">
				 	<c:forEach items="${datas }" var="data" varStatus="status">
				 		<tr>
				 			<td>${status.count}</td>
				 			<td>${data.account }</td>
				 			<td> <fmt:formatDate value="${data.createTime }" type="both"/></td>
				 			<td> <fmt:formatDate value="${data.updateTime }" type="both"/></td>
				 			<td>
				 				<input type="button" class="btn btn-primary updateBtn" value="重置密码" userId="${data.userId }">
				 			</td>
				 		</tr>
				 	</c:forEach>
			 	</c:if>
		 	</tbody>
		</table>
	</div>

	<!-- 重置密码-->
	<div class="modal fade" id="updateModal" tabindex="-1"
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
					<form class="form-horizontal" role="form" id="updateForm">
					<input type="hidden" class="form-control" id="userId" name="userId" value="">
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> 原密码：</label>
							<div class="col-sm-6">
								<input type="password" class="form-control" id="oldPwd" name="oldPwd" placeholder="原密码">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="oldPwdTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> 密码：</label>
							<div class="col-sm-6">
								<input type="password" class="form-control" id="pwd" name="pwd" placeholder="密码(6-12位大小写字母，数字)">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="pwdTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> 重复密码：</label>
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
			// 初始化模态框事件
			sysUser.onCloseModal();
			
			sysUser.openUpdateModal();
			sysUser.detail.updatePwd({
				'redirect_url':'/sys/user/userCenter'
			});
		});

	</script>
</body>
</html>
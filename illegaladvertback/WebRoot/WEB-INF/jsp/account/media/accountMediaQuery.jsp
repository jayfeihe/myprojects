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
<title>媒体账号管理</title>

<style type="text/css">
	.err_tip {
		color: red;}
</style>

</head>
<body>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">媒体账号管理</h3>
		</div>
		<div class="panel-body">
			<form role="form" id="queryForm">
				<div class="form-inline">
					<div class="form-group">
						<label for="inputText" class="control-label">媒体账号名称：</label> 
						<input type="text" class="form-control" id="companyName"
							name="companyName" placeholder="媒体账号名称">
					</div>
					<div class="form-group">
						<label for="inputText" class="control-label">状态：</label> 
						<select id="status" name="status" class="form-control">
							<option value="">请选择</option>
							<option value="<%=CommonConstants.STATUS_ON%>">有效</option>
							<option value="<%=CommonConstants.STATUS_OFF%>">无效</option>
						</select>
					</div>
					<div class="form-group">
						<input type="button" class="btn btn-primary btn-sm form-control" value="查&nbsp;&nbsp;&nbsp;&nbsp;询" id="queryBtn">
					</div>
				</div>
			</form>
		</div>
		<div style="margin-left: 10px;margin-bottom: 10px;">
			<input type="button" id="addAccountBtn" class="btn btn-primary" value="新增媒体账号">
		</div>
	</div>
	<div id="queryList"></div>

	<!-- 新增媒体账号 -->
	<div class="modal fade" id="addModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content"  style="width: 700px;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">新增媒体账号</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="addForm">
						<input type="hidden" class="form-control" id="compayId" name="userAccount.compayId" value="">
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> 媒体全称：</label>
							<div class="col-sm-6">
								<select class="selectpicker" data-live-search="true" id="companyName" name="userCompany.companyName">
									<option value="">请选择</option>
									<c:forEach items="${medias }" var="ad">
										<option value="${ad.companyId }">${ad.companyName }</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="companyNameTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> 媒体URL：</label>
							<div class="col-sm-6">
								<select class="selectpicker" data-live-search="true"  id="companyUrl" name="userCompany.companyUrl">
									<option value="">请选择</option>
									<c:forEach items="${medias }" var="ad">
										<option value="${ad.companyId }">${ad.companyUrl }</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="companyUrlTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> 账号：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="account" name="userAccount.account"  placeholder="账号(英文字母)">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="accountTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> 密码：</label>
							<div class="col-sm-6">
								<input type="password" class="form-control" id="pwd" name="userAccount.pwd" placeholder="密码(6-12位大小写字母，数字)">
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
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> 联系人姓名：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="userName"
									name="userAccount.userName" placeholder="联系人姓名(2-12位汉字)">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="userNameTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> 手机：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="mobile"
									name="userAccount.mobile" placeholder="手机">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="mobileTip"></span>
							</div>
						</div>
						
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label">邮箱：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="email"
									name="userAccount.email" placeholder="邮箱">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="emailTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label">传真：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="fax"
									name="userAccount.fax" placeholder="传真">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="faxTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label">固话：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="telephone"
									name="userAccount.telephone" placeholder="固话">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="telephoneTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label">QQ：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="qq"
									name="userAccount.qq" placeholder="QQ">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="qqTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label">状态：</label>
							<div class="col-sm-6">
								<select id="status" name="userAccount.status" class="form-control">
									<option value="<%=CommonConstants.STATUS_ON %>" selected="selected">有效</option>
									<option value="<%=CommonConstants.STATUS_OFF %>">无效</option>
								</select>
							</div>
							<div class="col-sm-4">
								<span></span>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="confirmAdd">确定</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 编辑媒体账号 -->
	<div class="modal fade" id="updateModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 700px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">修改媒体账号</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="updateForm">
					<input type="hidden" class="form-control" id="accountId" name="userAccount.id" value="">
						<input type="hidden" class="form-control" id="compayId" name="userAccount.compayId" value="">
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> 媒体全称：</label>
							<div class="col-sm-6">
								<select class="selectpicker" data-live-search="true" id="companyName" name="userCompany.companyName">
									<option value="">请选择</option>
									<c:forEach items="${medias }" var="ad">
										<option value="${ad.companyId }">${ad.companyName }</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="companyNameTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> 媒体URL：</label>
							<div class="col-sm-6">
								<select class="selectpicker" data-live-search="true"  id="companyUrl" name="userCompany.companyUrl">
									<option value="">请选择</option>
									<c:forEach items="${medias }" var="ad">
										<option value="${ad.companyId }">${ad.companyUrl }</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="companyUrlTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> 账号：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" readonly="readonly" id="account" name="userAccount.account"  placeholder="账号(英文字母)">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="accountTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> 密码：</label>
							<div class="col-sm-6">
								<input type="password" class="form-control" id="pwd" name="userAccount.pwd" placeholder="密码(6-12位大小写字母，数字)">
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
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> 联系人姓名：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="userName"
									name="userAccount.userName" placeholder="联系人姓名(2-12位汉字)">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="userNameTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> 手机：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="mobile"
									name="userAccount.mobile" placeholder="手机">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="mobileTip"></span>
							</div>
						</div>
						
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label">邮箱：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="email"
									name="userAccount.email" placeholder="邮箱">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="emailTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label">传真：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="fax"
									name="userAccount.fax" placeholder="传真">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="faxTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label">固话：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="telephone"
									name="userAccount.telephone" placeholder="固话">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="telephoneTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label">QQ：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="qq"
									name="userAccount.qq" placeholder="QQ">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="qqTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label">状态：</label>
							<div class="col-sm-6">
								<select id="status" name="userAccount.status" class="form-control">
									<option value="<%=CommonConstants.STATUS_ON %>" selected="selected">有效</option>
									<option value="<%=CommonConstants.STATUS_OFF %>">无效</option>
								</select>
							</div>
							<div class="col-sm-4">
								<span></span>
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
	
	<script type="text/javascript" src="<%=basePath%>js/main/account.js"></script>
	<script type="text/javascript">
		$(function() {
			$('.selectpicker').selectpicker({
				'width':'100%'
			});
			
			userAccount.submitForm({
				'url':'/account/media/list',
				'curPage':1
			});
			
			// 点击查询
			userAccount.queryForm({
				'url':'/account/media/list',
				'curPage':1
			});
			// 初始化模态框事件
			userAccount.openAddModal();
			userAccount.onCloseModal();
			
			// 下拉框事件
			userAccount.companyChange({
				'form':'addForm'
			});
			userAccount.companyChange({
				'form':'updateForm'
			});
			
			// 数据操作
			userAccount.detail.addAccount({
				'redirect_url':'/account/media/query'
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
			
			userAccount.submitForm({
				'url':'/account/media/list',
				'curPage':nextCurPage
			});
		}
		function goPage(pNum) {
			userAccount.submitForm({
				'url':'/account/media/list',
				'curPage':pNum
			});
		}
	</script>
</body>
</html>
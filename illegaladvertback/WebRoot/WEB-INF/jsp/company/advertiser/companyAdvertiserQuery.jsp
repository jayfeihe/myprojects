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
<title>广告主管理</title>

<style type="text/css">
	.err_tip {
		color: red;}
</style>

</head>
<body>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">广告主管理</h3>
		</div>
		<div class="panel-body">
			<form role="form" id="queryForm">
				<div class="form-inline">
					<div class="form-group">
						<label for="inputText" class="control-label">广告主名称：</label> 
						<input type="text" class="form-control" id="companyName"
							name="companyName" placeholder="广告主名称">
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
			<input type="button" id="addCompanyBtn" class="btn btn-primary" value="新增广告主">
		</div>
	</div>
	<div id="queryList"></div>

	<!-- 新增广告主 -->
	<div class="modal fade" id="addModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content"  style="width: 700px;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">新增广告主</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="addForm">
						<input type="hidden" class="form-control" id="type" name="type" value="<%=UserCompany.TYPE_ADVERTER%>">
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> 公司全称：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="companyName"
									name="companyName" placeholder="公司全称">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="companyNameTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> URL地址：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="companyUrl" name="companyUrl"  placeholder="URL地址"> 
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="companyUrlTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> 所属行业：</label>
							<div class="col-sm-6">
								<select id="industryId" name="industryId" class="form-control">
									<option value="" selected="selected">请选择</option>
									<c:forEach items="${industries }" var="in">
										<option value="${in.industryId }">${in.industryName }</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="industryIdTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> 联系人姓名：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="linkName"
									name="linkName" placeholder="联系人姓名(2-12位汉字)">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="linkNameTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> 联系人电话：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="linkPhone"
									name="linkPhone" placeholder="联系人电话">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="linkPhoneTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label">联系人邮箱：</label>
							<div class="col-sm-6">
								<input type="email" class="form-control" id="linkEmail"
									name="linkEmail" placeholder="联系人邮箱">
							</div>
							<div class="col-sm-4">
								<span></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label">公司地址：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="companyAddr"
									name="companyAddr" placeholder="公司地址">
							</div>
							<div class="col-sm-4">
								<span></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label">公司电话：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="companyPhone"
									name="companyPhone" placeholder="公司电话">
							</div>
							<div class="col-sm-4">
								<span></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label">邮政编码：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="postCode"
									name="postCode" placeholder="邮政编码">
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
	
	<!-- 编辑广告主 -->
	<div class="modal fade" id="updateModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 700px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">修改广告主</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="updateForm">
						<input type="hidden" class="form-control" id="companyId" name="companyId" value="">
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> 公司全称：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="companyName"
									name="companyName" value="">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="companyNameTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> URL地址：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" readonly="readonly" id="companyUrl" name="companyUrl"  value=""> 
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="companyUrlTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> 所属行业：</label>
							<div class="col-sm-6">
								<select id="industryId" name="industryId" class="form-control">
									<option value="" selected="selected">请选择</option>
									<c:forEach items="${industries }" var="in">
										<option value="${in.industryId }">${in.industryName }</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="industryIdTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> 联系人姓名：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="linkName"
									name="linkName" value="">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="linkNameTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label"><i style="color: red;">*</i> 联系人电话：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="linkPhone"
									name="linkPhone" value="">
							</div>
							<div class="col-sm-4">
								<span class="err_tip" id="linkPhoneTip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label">联系人邮箱：</label>
							<div class="col-sm-6">
								<input type="email" class="form-control" id="linkEmail"
									name="linkEmail" value="">
							</div>
							<div class="col-sm-4">
								<span></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label">公司地址：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="companyAddr"
									name="companyAddr" value="">
							</div>
							<div class="col-sm-4">
								<span></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label">公司电话：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="companyPhone"
									name="companyPhone" value="">
							</div>
							<div class="col-sm-4">
								<span></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputText" class="col-sm-2 control-label">邮政编码：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="postCode"
									name="postCode" value="">
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
	
	<script type="text/javascript" src="<%=basePath%>js/main/company.js"></script>
	<script type="text/javascript">
		$(function() {
			company.submitForm({
				'url':'/company/advertiser/list',
				'curPage':1
			});
			
			// 点击查询
			company.queryForm({
				'url':'/company/advertiser/list',
				'curPage':1
			});
			// 初始化模态框事件
			company.openAddModal();
			company.onCloseModal();
			
			// 数据操作
			company.detail.addCompany({
				'redirect_url':'/company/advertiser/query'
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
			
			company.submitForm({
				'url':'/company/advertiser/list',
				'curPage':nextCurPage
			});
		}
		function goPage(pNum) {
			company.submitForm({
				'url':'/company/advertiser/list',
				'curPage':pNum
			});
		}
	</script>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
		<div id="main">
			<div id="part1" class="part">
				<p class="title"><a href="javascript:void(0);">还款计划试算</a></p>
				
				<div class="content">
					<form id="queryForm" action="retplancalc/list.do" method="post" target="queryContent">
						<input type="hidden" name="loanId" value="${loanId }">
						<table>
							<tr>
								<td>预计合同开始日期:</td>
								<td><input id="startDate" name="startDate" type="text" data-options="editable:false" class="textbox easyui-datebox"/></td>
								<td>
									<input type="button" value="试算" class="btn" onclick="submitForm('queryForm')"/>
								</td>
							</tr>
						</table>	
					</form>
				</div>
				
				<div id="queryContent" >
				<%--
				查询列表
				 --%>
				</div>
			</div>
		</div>
		
<script type="text/javascript">
function submitForm(fromId) {
	//去掉 input 输入的 前后空格
	$('#queryForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	var formAction = $('#' + fromId).attr("action");
	var targetDiv = $('#' + fromId).attr("target");
	var params = $('#' + fromId).serialize();
	//弹出异步加载 遮罩
	openMask();
	$.ajax( {
		type : "POST",
		url  : formAction,
		data : params + "&targetDiv=" + targetDiv,
		dataType : "html",
		success : function(data) {
			closeMask();
			$('#' + targetDiv).html(data);
		},
		error : function() {
			closeMask();
			$.messager.confirm('消息', '数据加载失败,请联系系统管理员！');
		}
	});
}

//页面加载完动作
$(document).ready(function() {
	
});
</script>
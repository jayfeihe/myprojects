<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>"/>
<title>T_LOAN_GUAR列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
			<c:if test="${opt ne 'read' }">
				<%-- <a href="javascript:void(0);" onclick="updateData('${loanId}','1')"><img src="img/square/but_add.png" class='dotimg' title="添加担保人" /></a>&nbsp;
				<a href="javascript:void(0);" onclick="updateData('${loanId}','2')"><img src="img/square/but_addd.png" class='dotimg' title="添加担保机构" /></a>&nbsp; --%>
				<input type="button" value="添加担保人" class="btn" onclick="updateData('${loanId}','1')"/>
				<input type="button" value="添加担保机构" class="btn" onclick="updateData('${loanId}','2')"/>
				<a href="javascript:void(0);" onclick="refresh();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</c:if>
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">类型</th>
					<th scope="col">名称</th>
					<th scope="col">证件类型</th>
					<th scope="col">证件号码</th>
					<th scope="col">手机号</th>
					<th scope="col" width="160">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<%-- 申请ID   <td>${data.id}</td> --%>

						<td>
						<c:choose>
						<c:when test="${data.type eq '1' }">个人</c:when>
						<c:when test="${data.type eq '2' }">企业</c:when>
						</c:choose>
						</td>
						<td>${data.name}</td>
						
						<td>
						<c:choose>
						<c:when test="${data.idType eq '01'}">身份证</c:when>
						<c:when test="${data.idType eq '02'}">军官证</c:when>
						<c:when test="${data.idType eq '03'}">驾驶证</c:when>
						<c:when test="${data.idType eq '04'}">营业执照</c:when>
						<c:when test="${data.idType eq '05'}">组织机构代码证</c:when>
						<c:when test="${data.idType eq '06'}">税务登记证</c:when>
						</c:choose>
						</td>			
						<td>${data.idNo}</td>
						<td>${data.tel}</td>
						<td>
							<c:choose>
								<c:when test="${opt eq 'read' or '1' eq data.isOrig}">
									<a href="javascript:void(0);" onclick="readData('${data.id}','${data.type}')">查看</a>&nbsp;
								</c:when>
								<c:otherwise>
									<c:if test="${'0' eq data.isOrig }">
										<a href="javascript:void(0);" onclick="updateLoanGuar('${data.type}','${data.id }','${loanId}')">更新</a>&nbsp;
										<a href="javascript:void(0);" onclick="deleteData('${ data.id}');">删除</a>&nbsp;
									</c:if>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</table>
	
			<div id="pageStyle">
			${ pm.pageNavigation}
			</div>
		</form>
	</div>
</body>

<script language="javascript">

//查看
function readData(data_id,type) {
	var title="";
	if(type=='1'){
      title="查看担保人信息";
    }else{
      title="查看担保机构信息";
    }
	title="【担保编号-"+data_id+"】"+title;
 	addGuarTab(title,'<%=basePath%> loanguar/read.do?id='+data_id);
    return;
}
//更新担保人、担保机构
function updateLoanGuar(type,id,loanId){
   if(type=='1'){
      title="更新担保人信息";
   }else{
      title="更新担保机构信息";
   }
   title="【担保编号-"+id+"】"+title;
    addGuarTab(title,'<%=basePath%>' + 'loanguar/update.do?id='+id+'&loanId='+loanId);
	return;
}
//添加
function updateData(data_id,type) {
   var url="";
   var title="";
   if(null == data_id || '' == data_id) {
		$.messager.alert("消息","请先保存申请","warnning");
		return;
   }
   if(type=='1'){
      url= "<%=basePath%>" + "loanguar/updatePer.do?loanId=" + data_id;
      title="添加担保人";
   }else{
      url = "<%=basePath%>" + "loanguar/updateOrg.do?loanId=" + data_id;
      title="添加担保机构";
   }
    addGuarTab(title,url);
	return;
}

//删除
function deleteData(data_id) {
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
				url : "<%=basePath%>" + "loanguar/delete.do",
				// 防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
				data : encodeURI("id=" + data_id + "&timestamp=" + (new Date()).getTime()),
				async : false,// 同步提交
				success : function(data) {
					if ("true"==data.success) {
						// form刷新防提示
						$.messager.alert('消息', data.message,"info", function(){
			              		window.location = window.location + "&timestamp=" + (new Date()).getTime();
								//window.location.reload();
								refresh();
								
		            	});
					} else {
						$.messager.alert('消息', data.message);
					}
				},
				error : function() {
					$.messager.confirm('消息', '数据加载失败,请联系系统管理员！');
				}
			});
        }
    });
}


//添加
function add(type){
	window.location = "<%=basePath%>" + "loanguar/update"+type+".do";
	return;
}
function refresh() {
	refreshTab("appUpdateTabs");
}
//页面加载完动作
$(document).ready(function() {
	
});

</script>
</html>


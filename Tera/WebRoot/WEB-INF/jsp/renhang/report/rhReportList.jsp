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
<title>信用贷款人行报告列表</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div class="content">
		<form name="queryList" id="queryList" method="post" action="${ pm.url}">
			<div id="control" class="control">
				<a href="javascript:void(0);" onclick="add();"><img src="img/square/but_add.png" class='dotimg' title="添加" /></a>&nbsp;
				<a href="javascript:void(0);" onclick="window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr>
					<th scope="col">序号</th>
					<th scope="col">ID</th>
<th scope="col">申请ID</th>
<th scope="col">有无标记</th>
<th scope="col">报告编号</th>
<th scope="col">请求时间</th>
<th scope="col">报告时间</th>
<th scope="col">姓名</th>
<th scope="col">证件类型</th>
<th scope="col">证件号码</th>
<th scope="col">查询操作员</th>
<th scope="col">查询原因</th>
<th scope="col">性别</th>
<th scope="col">生日</th>
<th scope="col">婚姻状况</th>
<th scope="col">手机号</th>
<th scope="col">单位电话</th>
<th scope="col">住宅电话</th>
<th scope="col">学历</th>
<th scope="col">学位</th>
<th scope="col">通讯地址</th>
<th scope="col">户籍地址</th>
<th scope="col">配偶姓名</th>
<th scope="col">配偶证件类型</th>
<th scope="col">配偶证件号码</th>
<th scope="col">配偶工作单位</th>
<th scope="col">配偶联系电话</th>
<th scope="col">备注</th>
<th scope="col">状态</th>
<th scope="col">操作员</th>
<th scope="col">所属机构</th>
<th scope="col">创建日期</th>
<th scope="col">修改日期</th>

					<th scope="col" width="160">操作</th>
				</tr>
				<c:forEach items="${ pm.data}" var="data" varStatus="status">
					<tr>
						<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
						<td>${data.id}</td>
<td>${data.appId}</td>
<td>${data.flag}</td>
<td>${data.reportNo}</td>
<td>${data.requestTime}</td>
<td>${data.reportTime}</td>
<td>${data.name}</td>
<td>${data.idType}</td>
<td>${data.idNo}</td>
<td>${data.queryName}</td>
<td>${data.queryReason}</td>
<td>${data.gender}</td>
<td>${data.birthday}</td>
<td>${data.marriage}</td>
<td>${data.mobile}</td>
<td>${data.comPhone}</td>
<td>${data.homePhone}</td>
<td>${data.education}</td>
<td>${data.degree}</td>
<td>${data.address}</td>
<td>${data.hkAddress}</td>
<td>${data.mateName}</td>
<td>${data.mateIdType}</td>
<td>${data.mateIdNo}</td>
<td>${data.mateCom}</td>
<td>${data.matePhone}</td>
<td>${data.remarks}</td>
<td>${data.state}</td>
<td>${data.operator}</td>
<td>${data.orgId}</td>
<td>${data.createTime}</td>
<td>${data.updateTime}</td>

						<td>
							<a href="javascript:void(0);" onclick="javascript:updateData('${ data.id}');">更新</a>&nbsp;
							<a href="javascript:void(0);" onclick="javascript:deleteData('${ data.id}');">删除</a>&nbsp;
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
//更新
function updateData(data_id) {
	window.location = "<%=basePath%>" + "renhang/report/update.do?id=" + data_id;
	return;
}

//删除
function deleteData(data_id) {
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
				url : "<%=basePath%>" + "renhang/report/delete.do",
				// 防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
				data : encodeURI("id=" + data_id + "&timestamp=" + (new Date()).getTime()),
				async : false,// 同步提交
				success : function(data) {
					if ("true"==data.success) {
						// form刷新防提示
						$.messager.alert('消息', data.message,"info", function(){
			              		window.location = window.location + "&timestamp=" + (new Date()).getTime();
								window.location.reload();
		            	});
					} else {
						$.messager.alert('消息', data.message);
					}
				},
				error : function() {
					$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
				}
			});
        }
    });
}


//添加
function add(){
	window.location = "<%=basePath%>" + "renhang/report/update.do";
	return;
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


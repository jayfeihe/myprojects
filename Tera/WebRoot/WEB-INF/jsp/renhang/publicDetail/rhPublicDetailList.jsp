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
<title>信用贷款人行报告公共信息明细列表</title>
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
<th scope="col">公积金参缴地</th>
<th scope="col">公积金参缴日期</th>
<th scope="col">公积金初缴月份</th>
<th scope="col">公积金缴至月份</th>
<th scope="col">公积金缴费状态</th>
<th scope="col">公积金月缴存额</th>
<th scope="col">公积金个人缴存比例</th>
<th scope="col">公积金单位缴存比例</th>
<th scope="col">公积金缴费单位</th>
<th scope="col">公积金信息更新日期</th>
<th scope="col">养老保险参保地</th>
<th scope="col">养老保险参保日期</th>
<th scope="col">养老保险缴费月数</th>
<th scope="col">养老保险参加工作月份</th>
<th scope="col">养老保险缴费状态</th>
<th scope="col">养老保险个人缴费基数</th>
<th scope="col">养老保险本月缴费金额</th>
<th scope="col">养老保险信息更新日期</th>
<th scope="col">养老保险缴费单位</th>
<th scope="col">养老保险中断终止原因</th>
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
<td>${data.fundPlace}</td>
<td>${data.fundStartDate}</td>
<td>${data.fundFirstDate}</td>
<td>${data.fundPaidDate}</td>
<td>${data.fundState}</td>
<td>${data.fundAmount}</td>
<td>${data.fundPersonPercent}</td>
<td>${data.fundComPercent}</td>
<td>${data.fundCompany}</td>
<td>${data.fundUpdateDate}</td>
<td>${data.pensionPlace}</td>
<td>${data.pensionStartDate}</td>
<td>${data.pensionPayMonth}</td>
<td>${data.pensionWorkDate}</td>
<td>${data.pensionState}</td>
<td>${data.pensionBaseAmount}</td>
<td>${data.pensionPayAmount}</td>
<td>${data.pensionUpdateDate}</td>
<td>${data.pensionCompany}</td>
<td>${data.pensionInterruptReason}</td>
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
	window.location = "<%=basePath%>" + "renhang/publicDetail/update.do?id=" + data_id;
	return;
}

//删除
function deleteData(data_id) {
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
				url : "<%=basePath%>" + "renhang/publicDetail/delete.do",
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
	window.location = "<%=basePath%>" + "renhang/publicDetail/update.do";
	return;
}

//页面加载完动作
$(document).ready(function (){
			
});
</script>
</html>


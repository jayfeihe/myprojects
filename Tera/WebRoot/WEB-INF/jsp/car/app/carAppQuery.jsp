<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />
		<title>车贷申请表查询</title>
		<link href="css/global.css" type="text/css" rel="stylesheet" />
		<link href="css/icon.css" type="text/css" rel="stylesheet" />
		<link href="css/default/easyui.css" type="text/css" rel="stylesheet" />
		<script src="js/jquery.min.js" type="text/javascript"></script>
		<script src="js/jquery.form.js" type="text/javascript"></script>
		<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
		<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
		
		<style type="text/css"></style>
	</head>
	<body>
		<div id="main">
			<div id="part1" class="part">
				<p class="title"><a href="javascript:void(0);">车贷申请查询</a></p>

				<div class="content">
					<form id="queryForm" action="car/app/list.do" method="post" target="queryContent">
						<table>
							<tr>
								<td>申请编号:</td>
								<td><input id="appId" name="appId" type="text"
										data-options="validType:['length[0,30]']"
										class="textbox easyui-validatebox" /></td>
										
								<td>姓名:</td>
								<td><input id="name" name="name" type="text"
										data-options="validType:['length[0,50]']"
										class="textbox easyui-validatebox" /></td>
										
								<td>身份证:</td>
								<td><input id="idNo" name="idNo" type="text"
										data-options="validType:['idcard']"
										class="textbox easyui-validatebox" /></td>
								<td>申请状态:</td>
								<td>
									<select class="easyui-combobox" name="appState" editable="false" style="width:152px;">
										<option value="" selected="selected">全部</option>
										<option value="0">前端拒贷</option>
										<option value="1">录入申请</option>
										<option value="2">审核退回</option>
										<option value="3,4">审核</option>
<!-- 										<option value="4">审批退回</option> -->
										<option value="6,7">审批</option>
<!-- 										<option value="7">特殊审批退回</option> -->
										<option value="10">特殊审批</option>
										<option value="13">签约</option>
										<option value="14">撮合已完成</option>
										<option value="15">复核退回</option>
										<option value="17">复核</option>
										<option value="18">放款退回</option>
										<option value="19">放款</option>
										<option value="20">已确认放款</option>
										<option value="21">放款成功</option>
										<option value="22">放款失败</option>
										<option value="23">放款完成</option>
										<option value="24">拒贷</option>
									</select>
								</td>
							</tr>
							<tr>
								<td>进件时间:</td>
								<td><input id="inputTimeMin" name="inputTimeMin" style="width: 90px;"  
										type="text" editable="false" class="textbox easyui-datebox" />
									至
								<input id="inputTimeMax" name="inputTimeMax" style="width: 90px;"  
										type="text" editable="false" class="textbox easyui-datebox" /></td>
								<td>渠道:</td>
								<td><input id="belongChannel" name="belongChannel" type="text" class="easyui-combobox" editable="false"/></td>
								<td>产品:</td>
								<td><input id="product" name="product" type="text"
										data-options="validType:['length[0,50]']"
										editable="false" class="easyui-combobox" /></td>
							</tr>
							<tr>
								<td>处理状态:</td>
								<td>
									<select class="easyui-combobox" name="stateTask" editable="false" style="width:152px;">
										<option value="waitTask" selected="selected">待处理</option>
										<option value="inTask">处理中</option>
										<option value="offTask">已完成</option>
									</select>
								</td>
								<td>营业部:</td>
								<td>
									<input id="departId" name="departId" class="easyui-combotree" data-options="url:'sys/depart/listDataUseForSales.do?orgId=${login_org.orgId}',method:'get',required:false"/>
								</td>
								<td>营销人员:</td>
								<td><input id="staffNo" name="staffNo" type="text" data-options="validType:['length[0,50]']"
										editable="false" class="easyui-combobox" /></td>
							</tr>
							<tr>
								<td colspan="2">
									<input type="button" value="查询" class="btn" onclick="submitForm('queryForm')" />
									<input type="button" value="重置" class="btn" onclick="$('#queryForm').form('clear');" />
								</td>
							</tr>
						</table>
					</form>
				</div>

				<div id="queryContent">
					<%--
		查询列表
		 --%>
				</div>
			<c:if test="${isHead}">
				<div class="content">
					<table>
						<tr>
							<td><span>&nbsp;&nbsp;&nbsp;&nbsp;营销人员：</span><input id="changeStaffNo" name="changeStaffNo" type="text" data-options="validType:['length[0,50]']" 
									class="easyui-combobox"  editable="false"/>
									&nbsp;&nbsp;<span><input class="btn" type="button" value="批量更改" onclick="assignStaff()"/></span>
							</td>
						</tr>
					</table>
				</div>
			</c:if>
			</div>
		</div>

		<!-- <div id="loading" class="easyui-window" title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false">
			<img src="img/loading.gif" alt="加载中..." />
		</div> -->
		
	</body>

<script type="text/javascript">
<c:if test="${isHead}">
function assignStaff(){
		var params = $('#queryList').serialize();
		var ur=$("#changeStaffNo").combobox('getValue');
		var size = $("input[id='item']:checked").length;
		if(size==0) {
			$.messager.confirm('消息', "请选择待处理申请单！");
			return;
		}
		if(ur=='') {
			$.messager.confirm('消息', "请选择营销人！");
			return;
		}
		
		openMask();
		$.ajax( {
			type : "POST",
			url : "car/app/updateStaffNo.do",
			data : params+"&changeStaffNo="+ur,
			success : function(data) {
				closeMask();
				if(data.success){
					$.messager.alert('成功信息',data.message,"info",function(){
						submitForm("queryForm");
					});
				}else{
					$.messager.alert('失败信息',data.message);
				}
			},
			error : function() {
				closeMask();
				$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
			}
		});
}
</c:if>

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
		url : formAction,
		data : params + "&targetDiv=" + targetDiv,
		dataType : "html",
		success : function(data) {
			closeMask();
			$('#' + targetDiv).html(data);
		},
		error : function() {
			closeMask();
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
		}
	});
}

/* //打开Loading遮罩并修改样式
function openLoading() {
	$('#loading').window('open');
	$("#loading").attr("class", "");
	$("div[class='panel window']").css("position", "absolute");
	$("div[class='panel window']").attr("class", "");
	$("div[class='window-shadow']").attr("class", "");
} */

//页面加载完动作
$(document).ready(function() {
	//初始化 营销人员,调单
	var sales="sales/listjason.do?state=1&orgId=${login_org.orgId}&departId=" + $("#departId").combobox('getValue');
	$('#changeStaffNo').combobox({url:sales, valueField:'staffNo', textField:'name'});
	//初始化 营销人员,query
	$('#staffNo').combobox({url:sales, valueField:'staffNo', textField:'name'});
	
	
	//填充select数据 销售人员
	$("#changeStaffNo").combobox("clear");
	$('#changeStaffNo').combobox({
		/*
		 * valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.saleslevel,
		*/
	
		//添加空白行
		loadFilter:function(data){
	   		var opts = $(this).combobox('options');
	   		var emptyRow = {};
			emptyRow[opts.valueField] = '';
			emptyRow[opts.textField] = '请选择';
			data.unshift(emptyRow);
			return data;
		}
	});
	
	$("#departId").combotree({
	    //选择树节点触发事件  
	    onSelect : function(node) {  
	        //返回树对象  
	        var tree = $(this).tree;  
	        //选中的节点是否为叶子节点,如果不是叶子节点,清除选中  
	        var isLeaf = tree('isLeaf', node.target);  
	        if (!isLeaf) {  
	            //清除选中  
	             $('#departId').treegrid("unselect");
	        }
	        sales = "sales/listjason.do?state=1&orgId=${login_org.orgId}&departId=" + node.id;
			$("#staffNo").combobox("clear");
			$('#staffNo').combobox({url:sales, valueField:'staffNo', textField:'name'});
	    }  
	}); 
	//填充select数据样例
		/*
		var tsurl="sys/datadictionary/listjason.do?keyName=repaymethod";
		$("#repayMethod").combobox("clear");
		$('#repayMethod').combobox({
			url:tsurl,
			valueField:'keyProp',
			textField:'keyValue',
			//添加空白行
			loadFilter:function(data){
		   		var opts = $(this).combobox('options');
		   		var emptyRow = {};
				emptyRow[opts.valueField] = '&nbsp;';
				emptyRow[opts.textField] = '...';
				data.unshift(emptyRow);
				return data;
			}
		});
		 */
		 
	//填充select数据 渠道
	var channelurl="channeltotal/listjason.do";
	$("#belongChannel").combobox("clear");
	$('#belongChannel').combobox({
		url:channelurl,
		valueField:'channelCode', 
		textField:'channelName',
		onChange: function(newValue, oldValue){             
			$('#product').combobox('clear');
			var producturl = "product/hedao/listjason.do?type=4&belongChannel=" + encodeURI(newValue);
			$('#product').combobox('reload',producturl); 
		},
		//添加空白行
		loadFilter:function(data){
			var opts = $(this).combobox('options');
			var emptyRow = {};
			emptyRow[opts.valueField] = '';
			emptyRow[opts.textField] = '请选择';
			data.unshift(emptyRow);
			return data;
		}
	});
	//填充select数据 产品
	var belongChannel = $('#belongChannel').combobox('getValue');
	var producturl = "product/hedao/listjason.do?type=4&belongChannel=" + encodeURI(belongChannel);
	$("#product").combobox("clear");
	$('#product').combobox({
		url:producturl,
		valueField:'name', 
		textField:'productName',
		//添加空白行
		loadFilter:function(data){
       		var opts = $(this).combobox('options');
       		var emptyRow = {};
			emptyRow[opts.valueField] = '';
			emptyRow[opts.textField] = '全部';
			data.unshift(emptyRow);
 			return data;
		}
	});

	submitForm("queryForm");
});
</script>
</html>


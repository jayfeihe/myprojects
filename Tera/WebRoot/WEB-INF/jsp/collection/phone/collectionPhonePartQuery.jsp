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
<title>催收分单信息基本表查询</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
	
<style type="text/css">

</style>
</head>
<body>
	<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">电催分配查询</a></p>
		
		<div class="content">
			<form id="queryForm" action="collectionBase/phone/list.do" method="post" target="queryContent">
				<table>
				
					<tr>
						<td>客户姓名:</td>
						<td><input id="customerName" name="customerName" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox"/></td>
						<td>证件号码:</td>
						<td><input id="idNo" name="idNo" type="text" data-options="validType:['length[0,20]']" class="textbox easyui-validatebox"/></td>
						<td>合同编号:</td>
						<td><input id="contractNo" name="contractNo" type="text" data-options="validType:['length[0,50]']" class="textbox easyui-validatebox"/></td>
						<td>当前催收人:</td>
						<td><input id="collectionUidCur" name="collectionUidCur" type="text" data-options="validType:['length[0,20]']" class="easyui-combobox"/></td>
						
					</tr>		
					<tr>
						<td>营业部:</td>
						<td><input id="orgName" name="orgName" type="text"  class="easyui-combotree" vale=""/></td>
						
						<!-- <td>分配状态:</td>
						<td><select class="easyui-combobox" id="distributionState" name="distributionState" editable="false" style="width:152px;">
										<option value="N" selected="selected">未分配</option>
										<option value="Y">已分配</option>
										
									</select>
						
						</td>
						 -->
						<td>状态标识:</td>
						<td><select class="easyui-combobox" id="state" name="state" editable="false" style="width:152px;">
										<!-- 	<option value="1">待催收</option> -->
										<option value="2">电催处理中</option>
									<!--	<option value="3">落地催收待分配</option> -->
									<!--	<option value="4">落地催处理中</option> -->
									<!--	<option value="5">协催待分配</option> -->
									<!--	<option value="6">协催处理中</option> -->
										<option value="7">催收完成</option>
										<option value="8">欺诈申请</option>
										<option value="9">欺诈复核处理中</option>		
										<option value="10">欺诈审批处理中</option>
									<!--	<option value="11">欺诈认定生效</option>-->
										<option value="12">司法申请</option>
										<option value="13">司法复核处理中</option>
										<option value="14">司法审批处理中</option>
									<!--	<option value="15">司法认定生效</option>-->
									</select>
						</td>
						<td>逾期天数:</td>
						<td>
							<input id="minLateDays" name="minLateDays" type="text" data-options="min:0,precision:0" style="width: 70px" class="textbox easyui-numberbox" onblur="timeCompare()"/>&nbsp;-
							<input id="maxLateDays" name="maxLateDays" type="text" data-options="min:0,precision:0" style="width: 70px" class="textbox easyui-numberbox" onblur="timeCompare()"/>
						</td>
					</tr>		
					<tr>
						
						
					</tr>		

					<tr>
						<td>
							<input type="button" value="查询" class="btn" onclick="submitForm('queryForm')"/>
							<input type="button" value="重置" class="btn" onclick="$('#queryForm').form('clear');"/>
						</td>
						<td></td>
					</tr>
				</table>	
			</form>
		</div>
		
		<div id="queryContent" >
		<%--
		查询列表
		 --%>
		</div>
		<div id="bottom">
			<table>
				<tr>
					<td>分单：</td>
					<td><input id="collectionTeam" name="collectionTeam" type="text"  class="easyui-combotree" data-options="url:'sys/org/listDataByLevelAndOrgId.do?level=9',method:'get',required:false" value="86"/></td>
					<td><input id="collectionPeople" name="collectionPeople" type="text"  class="easyui-combobox"/></td>
					<td><input type="button" align="bottom" value="分配" onclick="partUpdate()"/></td>
				</tr>
			</table>
		</div>
	</div>
</div>
</body>
<script type="text/javascript">
//添加选项卡
function addTab(title, url){
    if ($('#verifyTable').tabs('exists', title)){
        $('#verifyTable').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#verifyTable').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
//提交后，删除当前选项卡
function removeTab(){
	var tab = $('#verifyTable').tabs('getSelected');
	var tabIndex=$('#verifyTable').tabs('getTabIndex',tab);
	$('#verifyTable').tabs('close',tabIndex);
	submitForm("queryForm");//解决Tab提交关闭列表页刷新的问题
}
</script>

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
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
		}
	});
}
//页面加载完动作
$(document).ready(function() {
//填充select数据样例
<%--
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
--%>
	getOrg();
	
	submitForm("queryForm");
});
//树形式显示营销团队
function getOrg() {
	$("#orgName").combotree({
		url:"sys/org/listDataByLevelAndOrgId.do?level=4",
		method:'get',
		required:false

	});
	$("#collectionTeam").combotree({
		/**url:"sys/depart/listDataByLevel.do?level=4",
		method:'get',
		required:false**/
		onSelect : function(node) {  
			var phonepeople = "sys/user/listUserByOrgAndRole.do?roleNames="+encodeURI('电话催收专员')+"&orgId="+node.id;
			$("#collectionPeople").combobox("clear");
			$('#collectionPeople').combobox({url:phonepeople, valueField:'loginId', textField:'name'});
		}
		

	});
	$("#collectionPeople").combobox({
		url:"sys/user/listUserByOrgAndRole.do?roleNames="+encodeURI('电话催收专员')+"&orgId=86",
		valueField:'loginId',    
   	 	textField:'name',
		method:'get',
		required:false

	});
	$("#collectionUidCur").combobox({
		url:"sys/user/listUserByOrgAndRole.do?roleNames="+encodeURI('电话催收专员'),
		valueField:'loginId',    
   	 	textField:'name',
		method:'get',
		required:false

	});
	
	
} 
	function partUpdate(){
		openMask();
		var targetDiv = $('#queryForm').attr("target");
		var checkresult=$('[name=checkresult]');
		var listId=$('[name=listId]');
		var checkStatus=false;
		var arr=new Array(); 
		for(var i=0;i<checkresult.length;i++){
			if(checkresult.eq(i).attr('checked')=="checked"){
				arr.push(listId.eq(i).val());
				//checkresult.eq(i).attr('checked',false);
				checkStatus=true;
			}	
		}	
				
				$.ajax( {
					type : "POST",
					url  : "collectionBase/phone/updateData.do",
					data : "ids="+arr+"&org_id="+$("#collectionTeam").combotree("getValue")+"&collection_uid_cur="+$('#collectionPeople').combobox("getValue")+"&targetDiv=" + targetDiv,
					dataType : "json",
					success : function(data) {
						closeMask();
						//$('#' + targetDiv).html(data);
						if(data.success=="true"){
							$.messager.alert('消息', '电催分配成功！','info',function(){submitForm("queryForm");})
						}else{
							$.messager.alert('消息', '电催分配失败！')
						}
					},
					error : function() {
						closeMask();
						$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
					}
					
				});
				//checkStatus=true;
			
			
		
		if(!checkStatus){
			$.messager.alert('消息', '请选择电催催收分配项目！');
		}
			closeMask();
	}
	function timeCompare(){
		if(($('#minLateDays').val()!="")&&($('#maxLateDays').val()!="")&&($('#minLateDays').val()>$('#maxLateDays').val())){
			$.messager.alert('消息', '最小逾期天数不能大于最大逾期天数！');
			}
		}

</script>

</html>


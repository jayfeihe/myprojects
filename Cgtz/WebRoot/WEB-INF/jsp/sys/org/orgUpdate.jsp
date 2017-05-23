<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div id="main">
	<div class="content">
		<form id="updateForm" >
				<input id="id" name="id" type="hidden" size="35" value="${ bean.id}" />
				<input id="orgId" name="orgId" type="hidden" size="35" value="${ bean.orgId}" />
			<table>
				<tbody>
				<%-- <tr>
					<td>数据权限代码:</td>
					<td><input id="orgId" name="orgId" type="text" data-options="required:true,validType:['length[0,18]']" class="textbox easyui-validatebox"value="${bean.orgId}" data-options="required:true,validType:['length[2,20]']"/></td>
				</tr> --%>
				<tr>
					<td>机构简称:</td>
					<td><input id="orgName" name="orgName" type="text" class="textbox easyui-validatebox" value="${bean.orgName}" data-options="required:true,validType:['length[2,20]']"/></td>
				</tr>
				<tr>
					<td>机构全称:</td>
					<td><input id="orgFullName" name="orgFullName" type="text" data-options="validType:['length[0,60]']" class="textbox easyui-validatebox" value="${bean.orgName}"/></td>
				</tr>
				<tr>
					<td>机构代码:</td>
					<td><input id="code" name="code" type="text" class="textbox easyui-validatebox"value="${bean.code}" data-options="required:true,validType:['length[2,20]']"/></td>
				</tr>
				<%-- <tr>
					<td>级别:</td>
					<td><input id="level" name="level" type="text" data-options="validType:['length[0,2]']" class="textbox easyui-validatebox"value="${bean.level}"/></td>
				</tr> --%>
				<tr>
					<td>所属机构:</td>
					<td><input id="parentOrgId" name="parentOrgId" type="text" class="textbox easyui-combotree" <c:if test="${not empty bean.orgId }">readonly='readonly'</c:if> value="${bean.parentOrgId }" /></td>
				</tr>
				</tbody>
			</table>
		</form>
		
		<hr color="#D3D3D3"/>
		<div align="right">
			<input type="button" value="提交" class="btn" onclick="updateFunction()"/>
			<input type="button" value="取消" class="btn" onclick="$('#dialogDiv').dialog('close');"/> 
		</div>
	</div>
</div>
<!-- 
<div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
	<img src="img/loading.gif" alt="加载中..." />
</div> -->

<script type="text/javascript">

//更新保存
function updateFunction() {
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		$(this).val($.trim($(this).val()));
	});
	//验证表单验证是否通过
	if(false == $('#updateForm').form('validate') ){
		return;
	}
	
	if($("#parentOrgId").combotree("getValue")==""){
		$.messager.alert('消息',"请选择所属机构");
		return;
	}
	//弹出异步加载 遮罩
	openMask();
	var params = $('#updateForm').serialize();
	
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "sys/org/save.do",
		data : encodeURI(params),
		success : function(data) {
			if ("true"==data.success) {
				//关闭遮罩，弹出消息框
				closeMask();
				
				$.messager.confirm('消息', data.message, function(ok){
	                if (ok){
	                	var url= "<%=basePath%>" + "sys/org/query.do";
						 window.location=url;
						//window.history.go(-1);
	                }
            	});
            } else {
            	closeMask();
				
				$.messager.confirm('消息', data.message);
				//按钮生效
				$(".btn").removeAttr("disabled");
            }
		},
		error : function() {
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
			closeMask();
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
	});
}

//返回
function back(){
	window.history.go(-1);
}
</script>

<script type="text/javascript">
//页面加载完动作
$(document).ready(function() {
	$("#parentOrgId").combotree({
		url:"sys/org/selectList.do?nodeLevel=${bean.level}",
		method:'get',
		required:false,
		panelHeight:'auto'
		//选择树节点触发事件
		/*
	    onBeforeSelect : function(node) {  
	        //返回树对象  
	        var tree = $(this).tree;  
	        //选中的节点是否为叶子节点,如果不是叶子节点,清除选中  
	        var isLeaf = tree('isLeaf', node.target);  
	        if (!isLeaf) {  
	            //清除选中  
	            return false;
	        }  
	    } */
	});
});
</script>
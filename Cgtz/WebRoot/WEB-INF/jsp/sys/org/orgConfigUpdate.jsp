<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>
<div id="main">
	<div class="content">
		<form id="updateForm" >
			<input id="id" name="id" type="hidden" size="35" value="${ bean.id}" />
			<input id="orgId" name="orgId" type="hidden" size="35" value="${ bean.orgId}" />
			<table>
				<tbody>
				<%-- <tr>
					<td>数据权限代码:</td>
					<td><input id="orgId" name="orgId" type="text"  class="textbox" value="${bean.orgId}" readonly='readonly'/></td>
				</tr> --%>
				<tr>
					<td>机构简称:</td>
					<td><input id="orgName" name="orgName" type="text" class="textbox" value="${bean.orgName}"  readonly='readonly'/></td>
				</tr>
				<tr>
					<td>机构全称:</td>
					<td><input id="orgFullName" name="orgFullName" type="text" class="textbox" value="${bean.orgName}" readonly='readonly'/></td>
				</tr>
				<tr>
					<td>机构代码:</td>
					<td><input id="code" name="code" type="text" class="textbox" value="${bean.code}" readonly='readonly'/></td>
				</tr>
				<tr>
					<td>所属机构:</td>
					<td><input id="parentOrgId" name="parentOrgId" type="text" class="textbox easyui-combotree" readonly='readonly' value="${bean.parentOrgId }"/></td>
				</tr>
				<tr>
					<td>审批额度:</td>
					<td><input id="aduitAmt" name="aduitAmt" type="text" class="textbox easyui-numberbox" data-options="min:0,precision:2" value="${bean.aduitAmt}" />元</td>
				</tr>
				<tr>
					<td>月债权:</td>
					<td><input id="loanAmt" name="loanAmt" type="text" class="textbox easyui-numberbox" data-options="min:0,precision:2" value="${bean.loanAmt}" />元</td>
				</tr>
				<tr>
					<td>利息差比:</td>
					<td><input id="intRate" name="intRate" type="text" class="textbox easyui-numberbox" data-options="min:0,precision:4" value="${bean.intRate}" />%</td>
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


<div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
	<img src="img/loading.gif" alt="加载中..." />
</div>

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
	//弹出异步加载 遮罩
	openLoading();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "sys/orgconfig/save.do",
		data : encodeURI(params),
		success : function(data) {
			if ("true"==data.success) {
				//关闭遮罩，弹出消息框
				$('#loading').window('close');
				
				$.messager.confirm('消息', data.message, function(ok){
	                if (ok){
	                   	var url= "<%=basePath%>" + "sys/orgconfig/query.do";
						window.location=url;
	                }
            	});
            } else {
                $('#loading').window('close');
				
				$.messager.confirm('消息', data.message);
				//按钮生效
				$(".btn").removeAttr("disabled");
            }
		},
		error : function() {
			$('#loading').window('close');
			
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
	});
}

//打开Loading遮罩并修改样式
function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
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
	});
});
</script>
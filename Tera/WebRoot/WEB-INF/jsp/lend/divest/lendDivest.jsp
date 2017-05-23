<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	<div id="main">
		<div style="width: 350px;height: 200px">
				<form id="frmDivest">
				<table>
				<tr>
				<td>提前天数:</td>
				<td>
				<input class="easyui-combobox" name="divestDays" id="divestDays" style="width:148px;" editable="false" onchange="showRate()"/>天
				<input type="hidden" value="${lendId}" name="lendId" id="lendId"/>
				</td>
				</tr>
				<tr>
				<td>违约金:</td>
				<td>
				<select id="defaultRate" name="defaultRate" class="sysSelectCss" style="width:148px;" disabled>
					<option value="0">-请先选择提前天数-</option>
					<c:forEach items="${rateList}" var="rate" varStatus="status">
						<option value="${status.index+1}">${rate}</option>
				    </c:forEach>
				</select>%
				</td>
				</tr>
				</table>	
				</form>
				
		</div>
		<hr/>
		<div align="right">
			<input value="提交" type="button" id="submitId" class="btn" onclick="submit()"/> 
			<input value="重置" type="button" id="resetId" class="btn" onclick="$('#frmDivest').form('clear');"/>
		</div>
	</div>

<script type="text/javascript">
$('#divestDays').combobox({
    onChange:function(){
	var days=$('#divestDays').combobox('getValue');
	//$('#divestDays').combobox('getText')
	
	 if(days== 1){
		  $("#defaultRate").val("1");
	  } else if(days== 2){
		   $("#defaultRate").val("2");
	  } else if(days== 3){
		   $("#defaultRate").val("3");
	  } else if(days== 4){
		   $("#defaultRate").val("4");
	  }
	}
 });
function submit() {
	//alert($("#defaultRate").find("option:selected").text());
	var defaultRate=$("#defaultRate").find("option:selected").text();
	var lendId=$("#lendId").attr("value");
	var divestDays=$('#divestDays').combobox('getText');
	//验证表单验证是否通过
	if(false == $('#frmDivest').form('validate')){
		
		$.messager.alert('消息', "页面有不符合规范内容，请正确填写！");
		return;
	}
	
	openMask();
	var fromdata=$('#frmDivest').serialize();
	//按钮失效防点击encodeURI("defaultRate=" + defaultRate  + "&timestamp=" + (new Date()).getTime()),
	$.ajax({
		type : "POST",
		data : encodeURI("defaultRate=" + defaultRate +"&divestDays="+divestDays +"&lendId="+lendId+ "&timestamp=" + (new Date()).getTime()),
		url:'lend/divest/divest.do',
		success : function(data) {
			closeMask();
			if ("true"==data.success) {
				
				$.messager.alert('消息', data.message,"info", function(){
                	$('#dialogDiv').dialog('close');
                	//flushAfterSubmit();
					window.location.reload();
	        	});
	
            } else {
				
				$.messager.alert('消息', data.message);
            }
			
		},
		error : function() {
			closeMask();
			$.messager.alert('消息',"操作失败，请联系管理员！");
		}
		});
	}
</script>


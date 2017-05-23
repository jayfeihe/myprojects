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
<title>公告管理</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/artDialog/artDialog.js?skin=opera"></script>
	<script src="js/artDialog/plugins/iframeTools.source.js"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
	

</head>
	<body>
	<div id="main">

	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">公告查询</a></p>
		
		<div id="queryForm" class="content">
			<form id="queryCondition" action="sys/bulletin/list.do" method="post" target="queryContent">
				<table>
					<tr>
					<td>名称:</td><td><input id='title' name='title' type='text' class="txt"/></td>
					<td>时间:</td>
					<td><input type="text" name="spublishTime" id="spublishTime" style="width: 90px;" editable="false" data-options="" class="textbox easyui-datebox"/>
						至<input type="text" name="epublishTime"  id="epublishTime" style="width: 90px;" editable="false" data-options="" class="textbox easyui-datebox"/></td>
					</tr>
					<tr>
						<td colspan="6">
					<input type="button" value="提交" class="btn" onclick="submitForm('queryCondition')"/>
					<input type="button" value="重置" class="btn" 
					onclick="$('#queryCondition').form('clear');"  />
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
 	
 	 function submitForm(fromId){  
 		 	var formAction = $('#'+fromId).attr("action");
 		 	var targetDiv = $('#'+fromId).attr("target");
            var params=$('#'+fromId).serialize();   
            openMask();
            $.ajax({   
                type:"POST",   
                url:formAction,   
                data:encodeURI(params+"&targetDiv="+targetDiv),   
                dataType:"html",   
                success:function(data){
                    $('#'+targetDiv).html(data); 
                    closeMask();
                },   
                error:function() {
					closeMask();
					$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
                }   
            });
               
        }   
 	 $(document).ready(function(){
 		 submitForm('queryCondition');
 	 });
 	 
</script>
	</body>
</html>


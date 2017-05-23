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
<title>日志管理</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.validate.js" type="text/javascript"></script>
	<script src="js/jquery.validate.ms.js" type="text/javascript"></script>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>	
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
	
</head>
	<body>
	<div id="main">

	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">日志查询</a></p>
		
		<div id="queryForm" class="content">
			<form id="queryCondition" method="post" action="sys/log/list.do" target="queryContent">
				<table>
					<tr>
						<td>登录ID:</td><td><input id='loginId' name='loginId' type='text' class="txt" value=''/></td>
						
						<td>姓名:</td><td><input id='name' name='name' type='text' class="txt" value=''/></td>
						
						<td>时间:</td>
						<td><input type="text" name="startTime" id="startTime" style="width: 90px;" editable="false" data-options="" class="textbox easyui-datebox"/>
							至<input type="text" name="endTime" id="endTime" style="width: 90px;" editable="false" data-options="" class="textbox easyui-datebox" /></td>
					</tr>
					<tr>
						<td colspan="2">
						<input type="button" value="查询" class="btn" onclick="submitForm('queryCondition')"/>
						<input type="reset" value="重置" class="btn" onclick="$('#queryCondition').form('clear');"/>
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
 		 	var targetDiv = $('#'+fromId).attr("target");
 		 	var formAction = $('#'+fromId).attr("action");
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
                error:function(){   
                	closeMask();
                   alert("数据加载失败！");   
                }   
            });   
               
        }   
 	 $(document).ready(function(){
 		 submitForm('queryCondition');
 	 });
 	 
</script>
	</body>
</html>


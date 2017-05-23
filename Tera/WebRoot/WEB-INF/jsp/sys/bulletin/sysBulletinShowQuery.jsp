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
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
<style type="text/css">
<!--
-->
</style>
<script language="javascript">

</script>
</head>
	<body>
	<div id="main">

	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">已发布公告查询</a></p>
		
		<div id="queryForm" class="content">
		
			<form id="queryCondition" action="sys/bulletin/showlist.do" method="post" target="queryContent">
				<input type="hidden" name="bulState" value="publishing" />
				<table>
					<tr>
					<td>名称:</td><td><input id='title' name='title' type='text' class="txt"/></td><td></td></td><td></td><td></tr>
					<tr>
					<td>
					<input type="button" value="提交" class="btn" onclick="submitForm('queryCondition')"/>
					<input type="reset" value="重置" class="btn"/>
					</td><td></td></td><td></td><td>
					</tr>
				</table>
					
			</form>
			</div>
		<div id="queryContent" >
		<%--
		<iframe id="queryContent" name="queryContent" src="sys/role/list.do" height="300" width="100%" frameborder="no" border="0" marginwidth="0" marginheight="0"  allowTransparency="true" />
		 --%>
		</div>
	</div>



</div>
<script type="text/javascript">
 	
 	 function submitForm(fromId){  
 		 	var formAction = $('#'+fromId).attr("action");
 		 	var targetDiv = $('#'+fromId).attr("target");
            var params=$('#'+fromId).serialize();   
            $.ajax({   
                type:"POST",   
                url:formAction,   
                data:encodeURI(params+"&targetDiv="+targetDiv),   
                dataType:"html",   
                success:function(data){    
                    $('#'+targetDiv).html(data);   
                },   
                error:function(){   
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


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
<title>角色管理</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>	
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
		<p class="title"><a href="javascript:void(0);">角色查询</a></p>
		
		<div id="queryForm" class="content">
			<form id="queryCondition" action="sys/role/list.do" method="post" target="queryContent">
				<table>
					<tr>
					<td>名称:</td><td><input id='name' name='name' type='text' class="txt" value='${pm.model.name}'/></td>
					
					<%-- <tr><td>类型:</td><td>
					<select id="roleType" name="type" class="sysSelectCss">
						<option value=""></option>
						<c:forEach items="${roleTypes}" var="data" varStatus="status">
							<option value="${data.id}"}>${data.description}</option>
						</c:forEach>
					</select>
					</td></tr> --%>
					</tr>
					<tr><td colspan="2">
					<input type="button" value="提交" class="btn" onclick="submitForm('queryCondition')"/>
					<input type="text" style="display:none;"/>
					<input type="reset" value="重置" class="btn"/>
					</td></tr>
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
                error:function(){   
                   alert("数据加载失败！");
                   closeMask();
                }   
            });   
               
        }   
 	 $(document).ready(function(){
 	 	submitForm('queryCondition');
 	 });
 	 
</script>
	</body>
</html>


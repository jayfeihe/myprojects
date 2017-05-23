<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript" src="<%=basePath %>js/dtree.js"></script>
<link href="<%=basePath %>css/dtree.css" rel="stylesheet" type="text/css"></link>
	<div id="main">
		<p class="title">当前设置角色:${role.name}</p>
		<div style="width: 425px;height:250px;overflow:auto;overflow-x:hidden;">
			<div id="menutree">
				<div id="systree"></div>
			</div>
		</div>
		<hr />
		<div id="menucontrl" align="right">
			<input type="button" class="btn" value="打开"
				onclick="javascript: d.openAll();" /> <input type="button"
				class="btn" value="关闭" onclick="javascript: d.closeAll();" /> <input
				type="button" class="btn" value="提交" onclick="submit()" /> <input
				type="button" class="btn" value="取消" onclick="goback()" />

		</div>
	</div>


	<!-- javascript -->
<script type="text/javascript">
	function sel(){
		var selids=d.getCheckedNodes();
		var str="";
		for(var n=0; n<selids.length; n++){
			str+=selids[n]+";";
		}
		//alert(str);
	}
	//提交
	function submit(){
		var valueStr='';
		$("input[type='checkbox']").each(function() {
            if (!!$(this).attr('checked')) {
//                 alert($(this).val());
                valueStr=valueStr+$(this).val()+",";
            }
        });
// 		alert(valueStr);
	 	$.ajax({
 			url:"sys/role/setmenuaction.do",
 			//防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
 			data:encodeURI("id=${id}&menuIds=" + valueStr + "&timestamp=" + (new Date()).getTime()),
 			dataType:"json",
 			success:function(data){
 				if("true" == data.success){
	        		
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
				$.messager.alert('消息',"操作失败，请联系管理员！");
			}
			});
	}
	function goback(){
	window.location.reload();
}
</script>
<script type="text/javascript" >
var d;
function menuSet(){
	d = new dTree('d','img/system/menu/');
	d.config.folderLinks=true;
	d.config.useCookies=false;
	d.config.check=true;
		//d.add(1,-1,'设置菜单',"javascript:;",'提示');
	<c:forEach items="${allMenus}" var="menu">
		d.add(${menu.id},${menu.parentId},'${menu.name}',"javascript:void(0);",'${menu.description}');
	</c:forEach>
		
	document.getElementById("systree").innerHTML=d;
	
	var funcs = eval("("+"{funcs:[${roleMenus}]}"+")");
	try{
		for(var n=0; n<funcs.funcs.length;n++){
			d.co(funcs.funcs[n].menudm).checked=true;
		}
	}catch(err){
		
	}
	
	d.openAll();
}

	
</script>


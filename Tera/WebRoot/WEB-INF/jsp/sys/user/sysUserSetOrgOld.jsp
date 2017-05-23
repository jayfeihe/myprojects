<%@page import="com.tera.sys.model.Org"%>
<%@page import="java.util.List"%>
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
<title>用户管理</title>
<link href="css/global.css" type="text/css" rel="stylesheet" />
<script src="js/jquery.min.js" type="text/javascript"></script>
<script src="js/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript" src="js/dtree.js"></script>
<link href="css/dtree.css" rel="stylesheet" type="text/css"></link>
<script src="js/artDialog/artDialog.js?skin=opera"></script>
<script src="js/artDialog/plugins/iframeTools.source.js"></script>
<script src="js/jquery.validate.js" type="text/javascript"></script>
<script src="js/jquery.validate.ms.js" type="text/javascript"></script>
<link href="css/global.css" type="text/css" rel="stylesheet"/>
<link href="css/icon.css" type="text/css" rel="stylesheet" />
<link href="css/default/easyui.css" type="text/css" rel="stylesheet" />
<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
<style type="text/css">
<!--
body {
	background-color: #ffffff;
	margin: 0px;
	padding: 0px;
}

#roletree {
	border: 1px solid #85B6E2;
	margin: 5px;
	padding: 5px;
}

#rolecontrl {
	margin: 5px;
	padding: 5px;
}

.cx {
	
}
-->
</style>
<script type="text/javascript">
	function cc(id){
		alert(id);
	}
</script>
</head>
<body>
<div id="main">

	<div class="part" id="part1" >
		<p class="title">当前设置用户:${loginId}</p>
		<div class="content">
			<form action="">
				<div id="roletree" style="height: 90%; overflow-x: scroll;">
					<div id="systree" style="float:left; "></div>
				</div>
				<div id="rolecontrl" align="right">
					<input type="button" class="btn" value="提交" onclick="submitForm()" />
					<input type="button" class="btn" value="返回"
						onclick="javascript:back();" />
				</div>
			</form>
		</div>
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
	var d = new dTree('d','img/system/dept/');
	d.config.folderLinks=true;
	d.config.useCookies=false;
	d.config.check=true;
// 		d.add(0,-1,'选择机构',"javascript:;",'提示');
	<% 
List<Org> orgs=(List<Org>)request.getAttribute("allOrgs");
for(Org org:orgs){
	String fu="-1";
	if("1".equals(org.getLevel()))
		fu="86";
	else if ("2".equals(org.getLevel()))
		fu=org.getOrgId().substring(0,4);
	else if("3".equals(org.getLevel())){
		fu=org.getOrgId().substring(0,8);
	}
%>
	d.add('<%=org.getOrgId()%>',<%=fu%>,'<%=org.getOrgName()%>',"javascript:;",'<%=org.getId()%>');
<%}%>		
document.getElementById('systree').innerHTML = d;

$(document).ready(function(){
	$("input[type='checkbox']").removeAttr("onclick");
	var funcs = eval("("+"{funcs:[${userOrgs}]}"+")");
	for(var n=0; n<funcs.funcs.length;n++){
		d.co(funcs.funcs[n].menudm).checked=true;
		chushiXuanze(d.co(funcs.funcs[n].menudm));
	};
	//选中加载
	roleXuanze();
	$("input[name='dtreeCheck']").click(function() {
		var orgid=$(this).next().attr("title");
		if($(this).attr('checked')){
			var xzys='${zhiwei}';
			$(this).next().after(xzys);
		}else{
			$("#"+orgid).remove();
		}
	});
	d.openAll();
});
//初始化  显示
function chushiXuanze(This) {
	var orgid=$(This).next().attr("title");
	var xzys='${zhiwei}';
	$(This).next().after(xzys);
}
//初始化选择
function roleXuanze(){
	var orgRoleids = "${orgAndRoles}".split(",");
	$("input[name='orgAndRole']").each(function() {
		for(var i=0; i<orgRoleids.length; i++){
			if ($(this).val() == orgRoleids[i]) {
                $(this).attr('checked', 'checked');
            }
		}
       });
}
//提交
function submitForm(){
	var valueStr='';
	$("input[name='orgAndRole']").each(function() {
           if ($(this).attr('checked')) {
               valueStr=valueStr+$(this).val()+",";
           }
       });
	$.ajax({
			url:"<%=basePath%>sys/user/setOrgAction.do",
			//防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
			data:encodeURI("id=${id}&loginId=${loginId}&orgIds="+valueStr+"&timestamp="+(new Date()).getTime()),
			dataType:"json",
			success:function(data){
				if("true" == data.success){
					$.messager.alert('消息', data.message,"info", function(){
						window.history.go(-1);
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
//返回
function back(){
	window.history.go(-1);
}
</script>

</body>
</html>


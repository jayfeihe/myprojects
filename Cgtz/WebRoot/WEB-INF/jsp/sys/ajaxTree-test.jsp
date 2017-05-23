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
<title>P2P综合业务系统</title>
<link href="css/global.css" type="text/css" rel="stylesheet"/>
<script src="js/jquery.min.js" type="text/javascript"></script>

<script src="./dynatree/jquery/jquery-ui.custom.js" type="text/javascript"></script>
	<script src="./dynatree/jquery/jquery.cookie.js" type="text/javascript"></script>
	

	<link href="./dynatree/skin/ui.dynatree.css" rel="stylesheet" type="text/css" id="skinSheet"/>
	<script src="./dynatree/jquery.dynatree.js" type="text/javascript"></script>
	
	
	
<style type="text/css">
<!--
body{
	background-color: #FFFFFF;
	margin:10px; padding:10px;
}

-->
</style>

<script type="text/javascript">

//这是为了在打开页面时就调用这个方法
	$(document).ready(function(){   
        initTree();
         });

function initTree(){
         //初始化树状结构
        $("#test").dynatree({
            checkbox: true,
            imagePath:'',
            //classNames: {nodeIcon: ""},//图标开关
            // Select mode 3: multi-hier
            selectMode: 3,
            //初始化树
			initAjax: {url: "ajaxTreeTest1.do", 
               data: {key: "root", // Optional arguments to append to the url 
                      mode: "all" 
                      } 
            }, 
            onSelect:function(node){
				alert(11);
			},
			onClick:function(node){
				//alert(node.data.title);
				//alert(node.data.key);
			},
			onActivate: function(node){
				alert(node.data.key);
				//alert(node.data.title);
			},
			onSelect: function(flag, node) { 
				alert(flag);
			},
			//lazy加载数据
			onLazyRead: function(node){ 
				 node.appendAjax({
					url:"ajaxTreeTest1.do",
					//url:'sample-data1.json',
					data:{"key":node.data.key},
					cache:false,
					success: function(data, textStatus){ 
	                },
	                error: function(node, XMLHttpRequest, textStatus, errorThrown) { 
	                }, 
				 });
			},
			classNames: { 
		        container: "dynatree-container", 
		        node: "dynatree-node", 
		        folder: "dynatree-folder", 
		 
		        empty: "dynatree-empty", 
		        vline: ""
			}
        });
         //设置树的样式
         $("#skinCombo")
			.val(0) // set state to prevent caching
			.change(function(){
				
				var href = "./dynatree/"
					+ $(this).val()
					+ "/ui.dynatree.css"
					+ "?reload=" + new Date().getTime();

				$("#skinSheet").attr("href", href);
			});
         

}    
</script>

</head>
<body>
<div>
<h1>Dynatree Test</h1>
		Skin:
		<select id="skinCombo" size="1">
			<option value="skin">Standard ('/skin/')</option>
			<option value="skin-vista">Vista ('/skin-vista/')</option>
		</select>
	</div>
	<hr/>
 <div id="test" name=test  style="width:270px;height:300px;overflow: auto ;background-color: white; border: #83BCF5 1px solid;" value="1">
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tera.sys.model.*"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>"/>
<title>left</title>
  <style>
	body {
	background-color:#fff;
	margin:0; padding:0;
	text-align: center;
	}
	div, p, table, th, td {
		list-style:none;
		margin:0; padding:0;
		color:#333; font-size:12px;
		font-family:dotum, Verdana, Arial, Helvetica, AppleGothic, sans-serif;
	}
	#mainFrame {margin-left: 10px;}
  </style>
<script src="js/jquery.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="js/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="js/zTree_v3/js/jquery.ztree.core-3.5.js"></script>


<style type="text/css">
<!--
/*.ztree li a.level0 {width:180px;height: 20px; text-align: center; background: url("../img/201407061345.gif") repeat-x scroll 0 0 rgba(0, 0, 0, 0); border:1px silver solid;text-decoration: none; }
.ztree li a.level0.cur {background: url("../img/201407061532.gif") repeat-x scroll left center rgba(0, 0, 0, 0);  text-decoration: none; }
.ztree li a.level0 span { color: #5B5B5B; padding-top:3px; font-size:13px; font-weight: bold; font-family: 微软雅黑; word-spacing: 2px;}
.ztree li a.level0 span.button {	float:right; margin-left: 10px; visibility: visible;display:none;}
.ztree li span.button.switch.level0 {display:none;}

.ztree li span.button.switch.level0 {visibility:hidden; width:1px;}
.ztree li ul.level0 {padding:0; background:none;} */
-->
</style>
  <SCRIPT type="text/javascript" >
  <!--
  	var curMenu = null, zTree_Menu = null,demoIframe;
	var setting = {
		view: {
			showLine: true,
			selectedMulti: false,
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: function(treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("tree");
				if (treeNode.isParent) {
					zTree.expandNode(treeNode);
					return false;
				} else {
					demoIframe.attr("src",treeNode.file);
					return true;
				}
			}
		}
	};
	
	var zNodes =${treeString};
	$(document).ready(function(){
		demoIframe = $('#mainFrame', window.parent.document);
		demoIframe.bind("load", loadReady);
		var t =$.fn.zTree.init($("#tree"), setting, zNodes);
	});
	function dblClickExpand(treeId, treeNode) {
		return treeNode.level > 0;
	};
	
	function loadReady() {
		
	};

  //-->
  
  </SCRIPT>
</head>
<body >
<TABLE border=0 align=left width="100%">
	<TR>
		<TD align=left valign=top >
			<ul id="tree" class="ztree" style="overflow:auto;padding:5px 0px !important;overflow-x:hidden;">
			</ul>
		</TD>
	</TR>
</TABLE>

</body>

</html>


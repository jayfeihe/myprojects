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
</head>
<body>

<c:if test="${not empty fileCount and fileCount gt 0 }">
 	<div id="toolbar" style="font-size: 14px;" ><strong>文件资料</strong><hr width="100%" size=2 color=#D3D3D3 align=center noshade></div>
 		<table id="table" class="datatable" summary="list of members in EE Studay">
			<tr>
				<th scope="col">序号</th>
				<th scope="col">申请编号</th>
				<th scope="col">分类</th>
				<th scope="col">上传时间</th>
				<th scope="col">文件名</th>
				<th scope="col">操作</th>
			</tr>
			<c:forEach items="${ pm.data}" var="data" varStatus="status">
				<tr>
					<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
					<td>${data.loanId}</td>
					<td>${data.category}</td>
					<td>${data.createTimeStr}</td>
					<td>${data.fileName}</td>
					<td>
						<a href="<%=basePath%>files/download.do?filePath=${data.filePath}&fileName=${data.fileName}">下载</a> &nbsp;&nbsp;
					</td>
				</tr>
			</c:forEach>
		</table>
		<div id="pageStyle">
			${ pm.pageNavigation}
		</div>
 </c:if>

 <c:if test="${not empty imgCount and imgCount gt 0 }">
	<div id="toolbar" style="font-size: 14px;" ><strong>图片资料</strong><hr width="100%" size="2" color="#D3D3D3" align="center" noshade/></div>
	 <table width="100%">
		 <tr>
		 	<td colspan="3">
		 		<a class="lawAdvice_category_a" href="javascript:void(0)" onclick="javascript:showLawAdviceImage('${loanId }','${bizKey}','${sec }','');">全部[${imgCount }张]</a> &nbsp; &nbsp;
		 		<c:forEach items="${imgCategories}" var="img" varStatus="status" >
					<a class="lawAdvice_category_a" href="javascript:showLawAdviceImage('${loanId }','${bizKey}','${sec }','${img.category}');">
						${img.category}[${img.categoryCount }张]
					</a> &nbsp;&nbsp;
				</c:forEach>
		 	</td>
		 </tr>
	 	
		<tr>
			<td align="center">
				<div id= "showLawAdviceImg" style="width: 80%;margin-top: 10px;">
					
				</div>
			</td>
		</tr>
	</table>
	
	<script type="text/javascript">
		function loadLawAdviceImage(loanId,bizKey,sec,category){
			var imgurl="<%=basePath%>files/getImgs.do?timestamp=" + (new Date()).getTime();
			if(loanId!=null&& loanId!="") {
				imgurl=imgurl+"&loanId="+loanId;
				if(bizKey!=null&& bizKey!=""){
					imgurl=imgurl+"&bizKey="+bizKey;
				}
				if(sec != null && sec != '') {
					imgurl=imgurl+"&sec="+sec;
				}
				if(category!=null && category!=""){
					imgurl=imgurl+"&category="+category;
				}
				$.ajax({
					url: imgurl,
					async : false,// 同步提交
					success : function(data) {
						if(data.length>0) {
							var html = [];
							 var id = data[0].id;//附件ID
							 var type = "fileId";
	                		 var fileName = data[0].fileName; // 文件名称
	                		 var filePath = data[0].filePath;// 文件路径
	                		 var imgSrc = "files/imgRead.do?imgurl="+filePath;
	                		 html.push('<div style="margin:10px;width:100%"><a href="${basePath }'+imgSrc+'" target="_blank"><img width="100%" src="'+imgSrc+'" alt="'+fileName+'" title="'+fileName+'"/></a></div>');
	                		$("#showLawAdviceImg").html(html.join(""));
						}
					},
					error : function() {
						$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
					}
				});
			}
		}
		
		function showLawAdviceImage(loanId,bizKey,sec,category){
			var imgurl="<%=basePath%>files/getImgs.do?timestamp=" + (new Date()).getTime();
			if(loanId!=null&& loanId!="") {
				imgurl=imgurl+"&loanId="+loanId;
				if(bizKey!=null&& bizKey!=""){
					imgurl=imgurl+"&bizKey="+bizKey;
				}
				if(sec != null && sec != '') {
					imgurl=imgurl+"&sec="+sec;
				}
				if(category!=null && category!=""){
					imgurl=imgurl+"&category="+category;
				}
				$.ajax({
					url: imgurl,
					async : false,// 同步提交
					success : function(data) {
						if(data.length>0){
							 var html = [];
							for(var i=0;i<data.length;i++){
								 var id = data[i].id;//附件ID
								 var type = "fileId";
		                		 var fileName = data[i].fileName; // 文件名称
		                		 var filePath = data[i].filePath;// 文件路径
		                		 var imgSrc = "files/imgRead.do?imgurl="+filePath;
		                		 html.push('<div style="margin:10px;width:100%"><a href="${basePath }'+imgSrc+'" target="_blank"><img width="100%" src="'+imgSrc+'" alt="'+fileName+'" title="'+fileName+'"/></a></div>');
							}
	                		$("#showLawAdviceImg").html(html.join(""));
						}
					},
					error : function() {
						
						$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
					}
				});
			}
		}
		
		loadLawAdviceImage("${loanId}","${bizKey}","${sec}","");
	
		$(".lawAdvice_category_a").click(function(){
			$(".lawAdvice_category_a").css({"color":"#000","font-weight":"normal"});
			$(this).css({"color":"#F00","font-weight":"bold"});
		 });
	 </script>
  </c:if>
</body>
</html>
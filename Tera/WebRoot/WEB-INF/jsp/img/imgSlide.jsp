<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<link href="css/img.css" rel="stylesheet">
<script src="js/cropping/cropper.min.js"></script>

 <table width="100%">
 <tr>
	<td colspan="3">
		<div id="toolbar" style="font-size: 14px;" >
			<strong>查看影像</strong>
			<hr width="100%" size=2 color=#D3D3D3 align=center noshade>
		</div>
	</td>
</tr>
 <tr>
 	<td colspan="3">
 	<a href="javascript:void(0)" onclick="javascript:showImage('${appId}','');">全部[<c:if test="${empty totalCount }">0</c:if>${totalCount }张]</a> &nbsp; &nbsp;
 	<c:forEach items="${imgCategoryList}" var="img" varStatus="status" >
			<a href="javascript:showImage('${appId}','${img.category}');">
				分类${img.categoryName}[${img.categoryCount }张]
			</a> &nbsp; &nbsp;
<%--		<a onclick="javascript:showImage('${appId}','${img.category}');">分类${img.categoryName}</a> &nbsp; &nbsp;--%>
	</c:forEach>
 	</td>
 </tr>
	 <tr>
		 <td> </td>
		 <td align="center">
			 <input type="button"  value="左旋" onclick="$('.container > img').cropper('rotate', -45);" />
			 &nbsp; &nbsp; &nbsp;
			 <c:if test="${not empty totalCount }">
				 当前第<input id="imgPageOffset" style="width:20px;text-align: center; border: 0; " readonly="readonly" type="text" value="1"/>张,
				 共<input id="imgTotal" style="width:20px;text-align: center; border: 0; " readonly="readonly" type="text" value=""/>张
			 </c:if>
			 &nbsp; &nbsp; &nbsp;
			 <input type="button"  value="右旋" onclick="$('.container > img').cropper('rotate', 45);" />
		 </td>
		 <td> </td>
 	</tr>
		<tr>
			<td width="72px"><input type="button" value="上一张" class="prev" /></td>
			<td align="center">
				<div id= "showImg" class="container" style="width: 100%">
					<img width="100%" src="" id="imgshow">
				</div>
			</td>
			<td width="72px"><input type="button"  value="下一张"  class="next"  /></td>
		</tr>
</table>
<script type="text/javascript">
var ph=null;
var i =0;		//当前 查看的图片索引
function showImage(appId,category){
	var imgurl="<%=basePath%>img/imgSlide.do?timestamp=" + (new Date()).getTime();
	if(appId!=null&& appId!=""){
		imgurl=imgurl+"&appId="+appId;
		if(category!=null && category!=""){
			imgurl=imgurl+"&category="+category;
		}
		$.ajax({
			url: imgurl,
			async : false,// 同步提交
			success : function(data) {
				if(data.length>0){
					ph=new Array();
<%-- 					$("#imgshow").attr("src","<%=basePath%>img/imgReadYun.do?imgurl="+data[0].imgPath); --%>
					$(".container > img").cropper("replace", "<%=basePath%>img/imgReadYun.do?imgurl="+data[0].imgPath);
					$("#imgshow").attr("alt",data[0].category+"_"+data[0].fileName);
					i=0;
					$("#imgPageOffset").val(i+1);
					$("#imgTotal").val(data.length);
					for(var j=0;j<data.length;j++){
						ph[j] = data[j];
					}
				}
			},
			error : function() {
				
				$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
			}
			
		});
	}
}

function imgpartLoad(appId){
	 $.ajax({
			type : "POST",
			url : "<%=basePath%>img/imgSlidePath.do",
			data : "appId=" + appId,
			dataType : "html",
			success : function(data) {
				$("#imgDiv").html(data);
			},
			error : function() {
				$.messager.confirm('消息', '加载失败。', function(ok){
		            if (ok){
//		 				window.history.go(-1);
		            }
		    	});
			}
		}); 
}
$("input.next").click(function(){
	 if(i< (ph.length-1)){
		 i++;
<%-- 			$("#imgshow").attr("src","<%=basePath%>img/imgReadYun.do?imgurl="+ph[i].imgPath); --%>
			$(".container > img").cropper("replace", "<%=basePath%>img/imgReadYun.do?imgurl="+ph[i].imgPath);
			$("#imgshow").attr("alt",ph[i].category+"_"+ph[0].fileName);
			$("#imgPageOffset").val(i+1);
	 }else{
//		 alert("到头了!");
	 }
});
 $("input.prev").click(function(){
	  if(i> 0 && i < ph.length){
		    i--;
<%-- 			$("#imgshow").attr("src","<%=basePath%>img/imgReadYun.do?imgurl="+ph[i].imgPath); --%>
			$(".container > img").cropper("replace", "<%=basePath%>img/imgReadYun.do?imgurl="+ph[i].imgPath);
			
			$("#imgshow").attr("alt",ph[i].category+"_"+ph[0].fileName);
			$("#imgPageOffset").val(i+1);
	  }else{
//		  alert("到顶了!!!!!");
	  }
 });
  
//   $(document).ready(function(){
	
	$('.container > img').cropper({
		aspectRatio: 16 / 9,
		crop: function(data) {},
		built: function () {
			$('.container > img').cropper("clear")
			$('.container > img').cropper("setDragMode", "move");
		}
	});
	
	showImage("${appId}","");
// });

 </script>
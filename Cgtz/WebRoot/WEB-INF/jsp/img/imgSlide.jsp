<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script src="js/jquery.rotate.min.js"></script>

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
 		<a onclick="javascript:showImage('${userId}','');">全部</a> &nbsp; &nbsp;
	 	<c:forEach items="${imgCategoryList}" var="img" varStatus="status" >
			<a href="javascript:showImage('${userId}','${img.category}');">分类${img.categoryName}</a> &nbsp; &nbsp;
		</c:forEach>
 	</td>
 </tr>
		<tr>
			<td align="center" colspan="5">
				<div id="showImg" align="center" style="margin: 20px 0; height: auto; overflow: hidden;">
					<!-- <span id="imgName"></span> -->
					<a id="imgA" data-lightbox="example-set"><img width="100%" height="100%" id="imgshow"></a>
				</div>
				<c:if test="${not empty imgCategoryList }">
					<div align="center">
						<input type="button" value="上一张" class="prev" />
						<input type="button" value="向右旋转" id="rightRotate" />
						<input type="button" value="向左旋转" id="leftRotate"/>
						<input type="button"  value="下一张"  class="next"  />
					</div>
				</c:if>
			</td>
		</tr>
</table>

<script type="text/javascript">
	var angle = 0;
	$('#rightRotate').on("click",function(){
		angle = angle+90;
		$('#imgshow').rotate({angle:angle});
	});
	
	$('#leftRotate').on("click",function(){
		angle = angle-90;
		$('#imgshow').rotate({angle:angle});
	});

</script>
<script type="text/javascript">
var ph=null;
var i =0;		//当前 查看的图片索引
function showImage(userId,category){
	var imgurl="<%=basePath%>img/imgSlide.do?timestamp=" + (new Date()).getTime();
	if(userId!=null&& userId!=""){
		imgurl=imgurl+"&userId="+userId;
		if(category!=null && category!=""){
			imgurl=imgurl+"&category="+category;
		}
		$.ajax({
			url: imgurl,
			async : false,// 同步提交
			success : function(data) {
				if(data.length>0){
					ph=new Array();
					$("#imgshow").attr("src","<%=basePath%>img/imgReadYun.do?imgurl="+data[0].imgPath);
					$("#imgshow").attr("alt",data[0].category+"_"+data[0].fileName);
					$("#imgA").attr("href","<%=basePath%>img/imgReadYun.do?imgurl="+data[0].imgPath);
					$("#imgA").attr("title",data[0].category+"_"+data[0].fileName);
					$("#imgName").html("名称:\t"+data[0].fileName);
					i=0;
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

function imgpartLoad(userId){
	 $.ajax({
			type : "POST",
			url : "<%=basePath%>img/imgSlidePath.do",
			data : "userId=" + userId,
			dataType : "html",
			success : function(data) {
				$("#imgDiv").html(data);
			},
			error : function() {
				$('#loading').window('close');
				
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
			$("#imgshow").attr("src","<%=basePath%>img/imgReadYun.do?imgurl="+ph[i].imgPath);
			$("#imgshow").attr("alt",ph[i].category+"_"+ph[i].fileName);
			$("#imgA").attr("href","<%=basePath%>img/imgReadYun.do?imgurl="+ph[i].imgPath);
			$("#imgA").attr("title",ph[i].category+"_"+ph[i].fileName);
			$("#imgName").html("名称:\t"+ph[i].fileName);
	 }else{
// 		 alert("到头了!");
	 }
 });
  $("input.prev").click(function(){
	  if(i> 0 && i < ph.length){
		    i--;
			$("#imgshow").attr("src","<%=basePath%>img/imgReadYun.do?imgurl="+ph[i].imgPath);
			$("#imgshow").attr("alt",ph[i].category+"_"+ph[i].fileName);
			$("#imgA").attr("href","<%=basePath%>img/imgReadYun.do?imgurl="+ph[i].imgPath);
			$("#imgA").attr("title",ph[i].category+"_"+ph[i].fileName);
			$("#imgName").html("名称:\t"+ph[i].fileName);
	  }else{
// 		  alert("到顶了!!!!!");
	  }
  });
  
  $(document).ready(function(){
	showImage("${userId}","");
});
  
 
 </script>
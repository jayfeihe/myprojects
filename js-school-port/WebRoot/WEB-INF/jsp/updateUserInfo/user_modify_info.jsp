<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>	
<!DOCTYPE html>
<html>

<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
<meta http-equiv="expires" content="0">
<link rel="icon" href="images/favicon.ico" type="image/x-icon">
<link rel="stylesheet" href="css/base.css">
<link rel="stylesheet" href="css/input.css">
<link rel="stylesheet" href="css/validator.css">
<link rel="stylesheet" href="css/user.css">
<link rel="stylesheet" href="css/uploadify.css"/>
<link rel="stylesheet" href="css/upload_pic.css"/>
<link rel="stylesheet" href="css/jquery.Jcrop.css"/>
<title>秒趣分期-修改个人信息</title>
<script type="text/javascript">
	var contextPath = '${pageContext.request.contextPath}';
</script>
</head>
<body class="body-no-bg">
<!--header-->
<jsp:include page="${basePath}/jsp/frame/header.jsp"></jsp:include>
<!--/header-->

<!--content-->
<div class="user-main">
	<h3 class="user-title user-common-con">修改个人信息</h3>
	<div class="pay-input-item clearfix mt40">
	  <div class="input-item-left">真实姓名<em>*</em></div>
	  <div class="input-item-right">
		<span class="input-item-text">${userInfo.name}</span>
	  </div>
	</div>
	<div class="pay-input-item clearfix">
	  <div class="input-item-left">头像</div>
	  <div class="input-item-right">
	  <c:if test="${userInfo.sex==1}">
				<img src="images/user-man.png" class="fl" id="user_pic"/> 
			</c:if>
			<c:if test="${userInfo.sex==0}">
				<img src="images/user-woman.png" class="fl" id="user_pic"/> 
			</c:if>
			<c:if test="${userInfo.sex==2}">
				<img src="images/user-default.png" class="fl" id="user_pic"/>
			</c:if>
		<a href="javascript:void(0)" class="red-link lh100 ml20 hide" id="modify_pic_btn">修改头像</a>
	  </div>
	</div>
	<div class="pay-input-item clearfix">
	  <div class="input-item-left">家庭地址</div>
	  <div class="input-item-right">
		<select class="select-default fl" name="address-province" onchange="loadArea('0','proId','cityId','areaId')" id="proId">
		<c:forEach items="${listPro}" var="list" varStatus="idx">
					<option value="${list.areaNo}"  <c:if test="${userInfo.provinceId ==list.areaNo}">selected</c:if>>
						${list.areaName}
					</option>
		</c:forEach>
			
		</select>
		<select class="select-default ml10 fl"  name="address-city" onchange="loadArea('1','proId','cityId','areaId')" id="cityId">
			<c:forEach items="${listCity}" var="list" varStatus="idx">
					<option value="${list.areaNo}"  <c:if test="${userInfo.cityId ==list.areaNo}">selected</c:if>>
						${list.areaName}
					</option>
		   </c:forEach>
		</select>
		<select class="select-default ml10 fl"  name="address-area" id="areaId">
			<c:forEach items="${listArea}" var="list" varStatus="idx">
					<option value="${list.areaNo}"  <c:if test="${userInfo.areaId ==list.areaNo}">selected</c:if>>
						${list.areaName}
					</option>
		   </c:forEach>
		</select>
		<input type="text" autocomplete="off" class="input-default  ml10 fl"   placeholder="详细地址"  name="address-detail" id="address-detail" value="${userInfo.homeAddress}"/>
	  </div>
	</div>
		<div class="pay-input-item clearfix">
	<div class="input-item-left">单位名称</div>
	<div class="input-item-right">
			<input type="text" autocomplete="off" class="input-default"   placeholder="单位名称"  name="comName" id="comName" value="${userInfo.companyName}"/>
	</div>
	</div>
	<div class="pay-input-item clearfix">
	  <div class="input-item-left">单位地址</div>
	  <div class="input-item-right">
		<select class="select-default fl" name="address-province"  onchange="loadArea('0','comProId','comCityId','comAreaId')" id="comProId">
		<c:forEach items="${listPro}" var="list" varStatus="idx">
					<option value="${list.areaNo}"  <c:if test="${userInfo.companyProvinceId ==list.areaNo}">selected</c:if>>
						${list.areaName}
					</option>
		</c:forEach>
			
		</select>
		<select class="select-default ml10 fl"  name="address-city" onchange="loadArea('1','comProId','comCityId','comAreaId')" id="comCityId">
			<c:forEach items="${listCityCom}" var="list" varStatus="idx">
					<option value="${list.areaNo}"  <c:if test="${userInfo.companyCityId ==list.areaNo}">selected</c:if>>
						${list.areaName}
					</option>
		   </c:forEach>
		</select>
		<select class="select-default ml10 fl"  name="address-area" id="comAreaId">
			<c:forEach items="${listAreaCom}" var="list" varStatus="idx">
					<option value="${list.areaNo}"  <c:if test="${userInfo.companyAreaId ==list.areaNo}">selected</c:if>>
						${list.areaName}
					</option>
		   </c:forEach>
		</select>
		<input type="text" autocomplete="off" class="input-default  ml10 fl"   placeholder="详细地址"  name="address-detail" id="comAddress-detail" value="${userInfo.companyAddress}"/>
	  </div>
	</div>

 
	<div class="pay-input-item clearfix">
	  <div class="input-item-left">兴趣爱好</div>
	  <div class="input-item-right">
		<div class="input-item-text">请选择你感兴趣的分类，给您最精准的推荐</div>
		<div class="pay-stages user-likes">
			<ul class="pay-stages-list" id="pay-stages-list">
				<li <c:if test="${fn:contains(userInfo.interesting, '计算机、手机、消费类电子产品') }">class="hover"</c:if>>
					<div class="pay-stages-li">
						计算机、手机、消费类电子产品
						<i class="user-icon icon-selected"></i>
					</div>
				</li>
				<li <c:if test="${fn:contains(userInfo.interesting, '奢侈品') }">class="hover"</c:if>>
					<div class="pay-stages-li">
						奢侈品
						<i class="user-icon icon-selected"></i>
					</div>
				</li>
				<li <c:if test="${fn:contains(userInfo.interesting, '家具家电') }">class="hover"</c:if>>
					<div class="pay-stages-li">
						家具家电
						<i class="user-icon icon-selected"></i>
					</div>
				</li>
				<li <c:if test="${fn:contains(userInfo.interesting, '装修') }">class="hover"</c:if>>
					<div class="pay-stages-li">
						装修
						<i class="user-icon icon-selected"></i>
					</div>
				</li>
				<li <c:if test="${fn:contains(userInfo.interesting, '租房') }">class="hover"</c:if>>
					<div class="pay-stages-li">
						租房
						<i class="user-icon icon-selected"></i>
					</div>
				</li>
				<li <c:if test="${fn:contains(userInfo.interesting, '旅游') }">class="hover"</c:if>>
					<div class="pay-stages-li">
						旅游
						<i class="user-icon icon-selected"></i>
					</div>
				</li>
				<li <c:if test="${fn:contains(userInfo.interesting, '教育') }">class="hover"</c:if>>
					<div class="pay-stages-li">
						教育
						<i class="user-icon icon-selected"></i>
					</div>
				</li>
				 

			  </ul>
		</div>
		
	  </div>
	</div>
	<div class="pay-input-item clearfix">
	  <div class="input-item-left">&nbsp;</div>
	  <div class="input-item-right">
		<button type="button" class="user-btn user-btn-red" id="submit-info" onclick="submitForm()">提交</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="user-btn user-btn-red" id="back-btn" onclick="goLocationHistory('account')">返回</button>
	  </div>
	</div>
</div>
<!--/content-->
<!--修改头像弹框start-->
<div class="hide"  id="user_pic_dialog">
	 <div class="pay-select-mask"></div>
	 <div class="pay-select-bank" style="width:900px;height:520px;margin-top:-260px;margin-left: -450px;">
		<div class="dialog-title">
			<span class="dialog-title-text">修改头像</span>
			<button type="button" class="dialog-close" id="close_user_pic_dialog_r"></button>
		</div>
		<div class="clearfix ml40">
			<button type="button" class="user-btn user-btn-default" id="uploadify">请选择上传的头像</button>
			<span style="margin-left:380px;font-size: 16px;">头像预览</span>
		</div>
		<div class="mt10 grey-color ml40">
			仅支持JPG、GIF、 PNG格式，文件小于4M 
		</div>
		<div class="wrap clearfix" id="jcropdiv">
			<div class="wl">
				<div class="jc-demo-box" data="0">
					<div id="target" class="jcrop_w" >
						<img src="images/user-default.png"/>
					</div>
				</div>
				<div class="jy-up-ch">
					<span id="idLeft" class="bch bch1"> </span>
					<span id="idSmall" class="bch bch2"> </span>
					<span id="idBig" class="bch bch3"> </span>
					<span id="idRight" class="bch bch4"> </span>
				</div>
			</div>
			<div class="wr" id="preview-pane">
				<div class="preview-container">
					<div class="pre-1">
						<img src="images/user-default.png" class="jcrop-preview jcrop_preview_s" alt="" />
					</div>
					<div class="pre-2">
						<img src="images/user-default.png" class="jcrop-preview jcrop_preview_s" alt="" />
					</div>
					<div class="pre-3">
						<img src="images/user-default.png" class="jcrop-preview jcrop_preview_s" alt="" />
					</div>
				</div>
			</div>
		</div>
		<div class="ml40 mt10 clearfix">
			<button type="button" class="user-btn user-btn-red" id="close_contract_dialog">保存</button>
			<span class="error-info"></span>
		</div>
	  </div>
</div>
<!--修改头像弹框end-->
<!-- footer start-->
<jsp:include page="${basePath}/jsp/frame/footer.jsp"></jsp:include>
<!-- footer end-->

<script src="js/jquery-1.10.2.min.js"></script>
<!--  <script type="text/javascript" src="js/jquery.Jcrop.js"> </script>
 --><!--script type="text/javascript" src="js/upload_pic.js"> </script-->
<script src="js/common.js"></script>
<script src="js/updateInfo/user_modify_info.js"></script>
<script type="text/javascript">
    $(function(){
    	  var year,mon,day;
    	var btemp='${userInfo.idCard}';
    	year=btemp.substring(6, 10);
    	mon=btemp.substring(10, 12);
    	day=btemp.substring(12, 14);
    	if(mon.substring(0, 1)=="0"){
    		mon=mon.substring(1, 2);
    	}
    	if(day.substring(0, 1)=="0"){
    		day=day.substring(1, 2);
    	}
       $("#birdthday_year").val(year);
       $("#birdthday_month").val(mon);
       $("#birdthday_day").val(day);
       
       $("#birdthday_year").attr("disabled","disabled");
       $("#birdthday_month").attr("disabled","disabled");
        $("#birdthday_day").attr("disabled","disabled");
        
    });
    
    
    function submitForm(){
    	var strHabby="";
    	$(".hover").find("div").each(function(){
    		var tempStr= $(this).text();
    		tempStr=tempStr.replace(/\ +/g,""); //去掉空格
    		tempStr=tempStr.replace(/[ ]/g,"");    //去掉空格
    		tempStr=tempStr.replace(/[\r\n]/g,""); //去掉回车换行
    		tempStr=tempStr.replace(/[\t]/g,"");//去掉\t制表符
    		strHabby+=tempStr+",";
    	});
    	strHabby=strHabby.substring(0, strHabby.length-1);
    	
    	$.ajax({
    		url:"<%=basePath%>updateUserInfo/modifyInfoSub.jhtml",
    		type:"POST",
    		data:{"homeAddr":$("#address-detail").val(),"comName":$("#comName").val(),"habby":strHabby,
    			"proId":$("#proId").val(),"cityId":$("#cityId").val(),"areaId":$("#areaId").val(),
    			"comProId":$("#comProId").val(),"comCityId":$("#comCityId").val(),"comAreaId":$("#comAreaId").val(), 
    			"comAddr":$("#comAddress-detail").val()
    			},
    		success:function(result){
    			if(result.code==-1){
					window.location.reload(true);
				}
				if(result.code!=0){
					$(".error-info").html(result.desc);
				}else{
					$(".error-info").html("");
					window.location.href="<%=basePath%>"+result.redirectUrl;
				}
			}
    	});
    }
    
    function loadArea(flag,proId,citiId,areaId){
    	var paramProCode="";
    	var paramCityCode="";
    	if(flag=='0'){
    		paramProCode=$("#"+proId).val();
    	}else{
    		paramCityCode=$("#"+citiId).val();
    	}
    	$.ajax({
    		url:"<%=basePath%>updateUserInfo/getAreaInfo.jhtml",
    		type:"POST",
    		data:{"paramProCode":paramProCode,"paramCityCode":paramCityCode},
    		success:function(result){
    			if(result.code==-1){
					window.location.reload(true);
				}
    				var cityStr="";
    				var areaStr="";
    			if(flag=='0'){
    				for (var i = 0; i < result.listCityInfo.length; i++) {
    					cityStr+='<option value="'+result.listCityInfo[i].areaNo+'" ';
    					cityStr+='>'+result.listCityInfo[i].areaName+' </option>';
					}
    				$("#"+citiId).html(cityStr);
    			}
    				for (var i = 0; i < result.listAreaInfo.length; i++) {
    					areaStr+='<option value="'+result.listAreaInfo[i].areaNo+'" ';
    					areaStr+='>'+result.listAreaInfo[i].areaName+' </option>';
					}
    				$("#"+areaId).html(areaStr);
			}
    	});
    }
</script>
</body>
</html>

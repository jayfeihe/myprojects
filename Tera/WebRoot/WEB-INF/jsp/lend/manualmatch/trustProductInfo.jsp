<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />
		</style>
	</head>
	<body>
		<c:if test="${! empty pm}">
		<div id="main">
			<div id="part1" class="part">
				
					
				
				<div class="content">
					
					<form id="selectedTrustProduct" action="lend/manualmatch/selectedTrustProductList.do" method="post" target="selectedTrustContent">
						<table id="table" class="datatable" summary="list of members in EE Studay">
							<tr>
								<th scope="col">选择</th>
								<th scope="col">序号</th>
								<th scope="col">产品</th>
								<th scope="col">期限</th>
								<th scope="col">机构名称</th>
								<th scope="col">利率</th>
								<th scope="col">起点金额</th>
								<th scope="col">用途</th>
								<th scope="col">操作</th>
							</tr>
							<c:forEach items="${ pm.data}" var="data" varStatus="status">
								<tr>
									<td style="text-align: center;"><input name="chk_productIds" type="checkbox" id="trustProselectInfo_${data.id}" value="${data.id}"/></td>
									<td style="text-align: center;">${ status.index+pm.rowS+1}</td>
									<td>${data.trustProname}</td>
									<td>${data.period}</td>
									<td>${data.company}</td>
									<td>${data.interestRate}</td>
									<td>${data.startAmount}</td>
									<td>${data.useage} </td>
									<td>
										<a href="javascript:void(0);" onclick="javascript:updateData('${data.id}');">详情</a>&nbsp;
									</td>
								</tr>
							</c:forEach>
						</table>
				
						<div id="pageStyle">
							${ pm.pageNavigation}
						</div>
					</from>
				</c:if>
				
				<div id="control" class="control">
				
						<!--<input  type="button" onclick="selectedTrustProduct('selectedTrustProduct')" value="确认选择"/>	
				--></div>
				
				
	</body>

<script type="text/javascript">

//给每个复选框定义点击函数
jQuery(function() {  
     trustProcheckboxOnClick();  
}); 

//给每个复选框的单击事件所定义的函数
function trustProcheckboxOnClick() {  
            $("input[type=checkbox]").click(function() {  
                    var checkbox_value = $(this).attr('value');  
                    if ($("#trustProselectInfo_" + checkbox_value).is(":checked")) {                    	
                    	for(var i=0;i<arr_selectedTrustProIds.length;i++){
                    		if(checkbox_value == arr_selectedTrustProIds[i]){
                    			alert("该申请已经添加");
                    			$(this).attr('checked',false)
                    			return;
                    		}
                    	}
                        //数组增加所点击的复选框的value值
                       	arr_selectedTrustProIds.push(checkbox_value); 
                        selectedTrustProduct('selectedTrustProduct');
 
                    } else {  
                    	
                    }  
            });  
        }  

function selectedTrustProduct(fromId) {
	
	//定义数组中各个元素组成的字符串
	var mm = "";
	
	for(var i=0;i < arr_selectedTrustProIds.length;i++){
		if(i != arr_selectedTrustProIds.length-1){
			mm += arr_selectedTrustProIds[i] + ",";
		}
		
		if(i == arr_selectedTrustProIds.length-1){
			mm += arr_selectedTrustProIds[i]
		}
	}
	
	
	var formAction = $('#' + fromId).attr("action");
	var targetDiv = $('#' + fromId).attr("target");
	var params = $('#' + fromId).serialize();
	openMask();
 $.ajax({
		type : "POST",
		url : formAction+ "?trustProIdsStr=" + mm,
		data : encodeURI(params + "&targetDiv=" + targetDiv),
		dataType : "html",
		success : function(data) {
			$('#' + targetDiv).html(data);
			closeMask();
		},
		error : function() {
			closeMask();
			
			$.messager.confirm('消息', '加载失败。', function(ok){
	            if (ok){
//	 				window.history.go(-1);
	            }
	    	});
		}
	}); 
}

$(document).ready(function() {

});
</script>
</html>
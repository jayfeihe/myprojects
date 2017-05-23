<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

		<div class="content">
			<form name="queryList" id="queryList" method="post" action="${pm.url}">
			
			<div id="control"  class="control">
				<a href="javascript:void(0);" onclick="javascript:artOpenPage('添加菜单','sys/menu/add.do');"><img src="img/square/sys_but_add.png" class='dotimg' title="添加菜单" /></a>&nbsp;
				<a href="javascript:void(0);" onclick="javascript:window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;		
			</div>
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr id="tr_00">
					<th scope="col">名称</th>
					<th scope="col">地址</th>
					<th scope="col">描述</th>
					<th scope="col" width="180">操作</th>
				</tr>
				
				<c:forEach items="${pm.data}" var="data">
				<tr id="tr_${data.id}">
					<td <c:if test="${data.type==0}">class="parentName"</c:if>  >${data.name}
						<c:if test="${data.id!=1&&data.type!=2}">
							<span style="width: 10px;">
								<span style="margin-bottom: 2px;"><a href="javascript:void(0);" onclick="javascript:up('${data.id}');"><img src="img/table-add-row-before.gif"/></a></span>
								<span style="margin-top: 1px;"><a href="javascript:void(0);" onclick="javascript:down('${data.id}');"><img src="img/table-add-row-after.gif"/></a></span>
							</span>
						</c:if>
					</td>
					<td>${data.url}</td>
					<td>${data.description}</td>
					<td>
						<c:if test="${data.id!=1}">
						<c:if test="${data.state=='1'}">
						<a href="javascript:void(0);" onclick="javascript:delData('${data.id}','${data.state}');" title="删除">禁用</a>&nbsp;
						</c:if>
						<c:if test="${data.state=='0'}">
							<a href="javascript:void(0);" onclick="javascript:addData('${data.id}','${data.state}');" title="删除">开启</a>&nbsp;
						</c:if>
						<a href="javascript:void(0);" onclick="javascript:artOpenPage('修改菜单','sys/menu/update.do?id=${data.id}');" title="修改">修改</a>&nbsp;
						</c:if>
						<c:if test="${data.type==1}">
						<a href="javascript:void(0);" onclick="javascript:artOpenPage('添加按钮事件','sys/menu/add.do?parentId=${data.id}');" title="添加按钮事件">添加按钮事件</a>&nbsp;
						</c:if>
					</td>
				</tr>
				</c:forEach>
				
			</table>
			
			<div id="pageStyle">
			</div>
			
			</form>

		</div>
<script language="javascript" type="text/javascript">

function showQueryContent(obj){
	if($("#queryFormContent").css("display") == "block") {
		$("#queryFormContent").css("display","none");
		$("#"+obj).attr('src','img/square/but_down.png');
	}else{
		$("#queryFormContent").css("display","block");
		$("#"+obj).attr('src','img/square/but_up.png');
	}
}
//打开页面
function artOpenPage(_title,_url) {
	if($("body").find("#dialogDiv").length==0){
		 $('body').append($("<div id='dialogDiv'></div>"));
	}
	$('#dialogDiv').dialog({
	    title: _title,
	    width: 400,
	    closed: false,
	    cache: false,
	    href: _url,
	    modal: true
	    });
}
//删除数据

function delData(data_id,state){
	
	$.messager.confirm('消息', '您确认要禁用？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
	 			url:"sys/menu/delect.do",
	 			//防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
	 			data:encodeURI("ids=" + data_id +"&state="+state + "&timestamp=" + (new Date()).getTime()),
	 			//dataType:"json",
	 			async: false,//同步提交
	 			success:function(data){
	 				if("true"==data.success){
	 					//form刷新防提示
						$.messager.alert('消息', data.message,"info", function(){
			                   	window.location = window.location + "&timestamp=" + (new Date()).getTime();
								window.location.reload();
		            	});
	 				}else{
						
						$.messager.alert('消息', data.message);
	 				}
	 			},
	 			error : function() {
					
					$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
				}
	 		});
	    }
	});
}
//启用数据

function addData(data_id,state){
	
	$.messager.confirm('消息', '您确认要启用？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
	 			url:"sys/menu/delect.do",
	 			//防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
	 			data:encodeURI("ids=" + data_id +"&state="+state + "&timestamp=" + (new Date()).getTime()),
	 			//dataType:"json",
	 			async: false,//同步提交
	 			success:function(data){
	 				if("true"==data.success){
	 					//form刷新防提示
						$.messager.alert('消息', data.message,"info", function(){
			                   	window.location = window.location + "&timestamp=" + (new Date()).getTime();
								window.location.reload();
		            	});
	 				}else{
						
						$.messager.alert('消息', data.message);
	 				}
	 			},
	 			error : function() {
					
					$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
				}
	 		});
	    }
	});
}
//更新数据
function updateData(data_id){
	var url="sys/menu/update.do?id="+data_id;
	//var winName = "更新用户";
	//openwindow(url,winName,600,400);
	window.location=url;
	
}


//导出
function sysMenuExcel(){
	var exportExcelAction = "sys/menu/excel.do";
	
	var queryForm = $("#queryList");
	
	var oddAction = queryForm.attr("action");
	queryForm.attr("action",exportExcelAction);
	queryForm.get(0).submit();
	queryForm.attr("action",oddAction);
	
}

function up(data_id){
	changeParentOrderNum(data_id,"up",$("#isAllDataChk").val());
	return;
}
function down(data_id){
	changeParentOrderNum(data_id,"down",$("#isAllDataChk").val());
	return;
}

//在树显时用
function changeParentOrderNum(meId,option){
	//alert("targetDiv=queryContent&meId="+meId+"&option="+option);
	//alert($("#parentId").val());
	//return;
	var parentId=$("#parentId").val();
	 $.ajax({   
                type:"POST",   
                url:"sys/menu/list.do",  
                async: false,//同步
                data:encodeURI("targetDiv=queryContent&meId="+meId+"&option="+option),   
                dataType:"html",   
                success:function(data){    
                    $('#queryContent').html(data);   
                },   
                error:function(){   
                   alert("数据加载失败！");   
                }   
            });   
}

</script>

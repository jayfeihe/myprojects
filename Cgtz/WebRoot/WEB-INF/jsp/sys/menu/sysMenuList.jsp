<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
		<div class="content" >
			<form name="queryList" id="queryList" method="post" action="${pm.url}">
			
			<div id="control" class="control" >
				<a href="javascript:void(0);" onclick="javascript:openPage('添加菜单','sys/menu/add.do');"><img src="img/square/sys_but_add.png" class='dotimg' title="添加菜单" /></a>&nbsp;
				<a href="javascript:void(0);" onclick="javascript:window.location.reload();"><img src="img/square/but_renew.png" class='dotimg' title="刷新" /></a>&nbsp;&nbsp;
			</div>
			
			<table id="table" class="datatable" summary="list of members in EE Studay">
				<tr id="tr_00">
					<th scope="col" >名称</th>
					<th scope="col">地址</th>
					<th scope="col">描述</th>
					<%--
					<th scope="col">序号</th>
					 --%>
					
					<th scope="col" width="180">操作</th>
				</tr>
				
				<c:forEach items="${pm.data}" var="data">
				<tr id="tr_${data.id}">
				<td <c:if test="${data.url==null}">class="parentName"</c:if>  >${data.name}
					<%-- <span style="width: 10px;">
						<span style="margin-bottom: 2px;"><a href="javascript:void(0);" onclick="javascript:up('${data.id}');"><img src="img/table-add-row-before.gif"/></a></span>
						<span style="margin-top: 1px;"><a href="javascript:void(0);" onclick="javascript:down('${data.id}');"><img src="img/table-add-row-after.gif"/></a></span>
					</span>
				 --%>
				</td>
				<td>${data.url}</td>
				<td>${data.description}</td>
				<%--
				<td>${data.orderNum}</td>
				<a href="javascript:void(0);" onclick="javascript:delData('${data.id}');" title="删除"><img src="img/deleteItem0.gif" class='dotimg'/></a>&nbsp;
				<a href="javascript:void(0);" onclick="javascript:artOpenPage('sysMenuUpdate.do?id=${data.id}');" title="更新"><img src="img/updateItem.gif" class='dotimg'/></a>&nbsp;
				 --%>
				<td>
				<c:if test="${data.id!=1}">
				<a href="javascript:void(0);" onclick="javascript:delData('${data.id}');" title="删除">删除</a>&nbsp;
				<a href="javascript:void(0);" onclick="javascript:openPage('修改菜单','sys/menu/update.do?id=${data.id}');" title="修改">修改</a>&nbsp;
				</c:if>
				<%-- <c:if test="${data.type==1}">
				<a href="javascript:void(0);" onclick="" title="添加按钮事件">添加按钮事件</a>&nbsp;
				</c:if>
				<c:if test="${data.type==0}">
				<a href="javascript:void(0);" onclick="" title="添加模块">添加模块</a>&nbsp;
				<a href="javascript:void(0);" onclick="" title="添加页面">添加页面</a>&nbsp;
				</c:if> --%>
				</td>
				</tr>
				
				</c:forEach>
				
			</table>
			
			<div id="pageStyle">
			<%--
			${pm.pageNavigation}
			 --%>
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
//删除数据

function delData(data_id){
	
	
	$.messager.confirm('消息', '您确认要删除？', function(ok){
		//点击确定做删除
		if (ok){
			$.ajax({
	 			url:"sys/menu/delect.do",
	 			//防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
	 			data:encodeURI("ids=" + data_id  + "&timestamp=" + (new Date()).getTime()),
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
//打开页面
function openPage(_title,_url) {
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

	var prevTr = $('#tr_'+data_id).prev();
	var meTr = $('#tr_'+data_id);
	
	if(prevTr.attr('parentId') != meTr.attr('parentId')){return;}
	
	var pcontent = prevTr.html();
	var pid = prevTr.attr('id');
	//如果顶层
	if(pid=="tr_00"){return;}
	
	var content = meTr.html();
	var id = meTr.attr('id');
	
	
	var meId=id.split("_")[1];
	var otherId = pid.split("_")[1];
	//alert(meId+"__"+otherId);
	var isChanged = changeOrderNum(meId, otherId);
	//如果后台没有调整成功
	if(!isChanged){return;}
	
	
	$('#tr_'+data_id).prev().html(content);
	$('#tr_'+data_id).html(pcontent);
	
	prevTr.attr('id',id);
	meTr.attr('id',pid);
	
	
	
}
function down(data_id){

	var nextTr = $('#tr_'+data_id).next();
	var meTr = $('#tr_'+data_id);
	
	if(nextTr.attr('parentId') != meTr.attr('parentId')){return;}

	var ncontent = nextTr.html();
	var nid = nextTr.attr('id');
	
	if(nid==undefined){return;	}
	
	var content = meTr.html();
	var id = meTr.attr('id');
	
	var meId=id.split("_")[1];
	var otherId = nid.split("_")[1];
	//alert(meId+"__"+otherId);
	var isChanged = changeOrderNum(meId, otherId);
	if(!isChanged){return;}
	
	$('#tr_'+data_id).next().html(content);
	$('#tr_'+data_id).html(ncontent);
	
	nextTr.attr('id',id);
	meTr.attr('id',nid);
	
}

function changeOrderNum(meId, otherId){
	var result = false;
	$.ajax({
 			url:"sys/menu/changeorder.do",
 			//防止浏览器缓存，在URL后加时间戳 "&timestamp=" + (new Date()).getTime()
 			async: false,//同步
 			data:encodeURI("meId=" + meId + "&otherId=" + otherId + "&timestamp=" + (new Date()).getTime()),
 			dataType:"text",
 			success:function(data){
 				if(data=="true")
 				result=true;
 			}
 		});
	return result;
}
</script>
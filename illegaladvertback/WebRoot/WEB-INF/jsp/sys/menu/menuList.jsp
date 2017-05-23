<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
	<link href="<%=basePath %>css/pay.css"rel="stylesheet">
<style>
	.tree {
		font-size:20px;
	    min-height:20px;
	    padding:19px;
	    margin-bottom:20px;
	    
	    background-color:#fbfbfb;
	    /* border:1px solid #999; */
	    -webkit-border-radius:4px;
	    -moz-border-radius:4px;
	    border-radius:4px;
	    -webkit-box-shadow:inset 0 1px 1px rgba(0, 0, 0, 0.05);
	    -moz-box-shadow:inset 0 1px 1px rgba(0, 0, 0, 0.05);
	    box-shadow:inset 0 1px 1px rgba(0, 0, 0, 0.05)
	}
	 
	.tree li {
	    list-style-type:none;
	    margin:0;
	    line-height:20px;
	    padding:10px 5px 0 5px;
	    position:relative
	}
	.tree li::before, .tree li::after {
	    content:'';
	    left:-20px;
	    position:absolute;
	    right:auto
	}
	.tree li::before {
	    border-left:1px solid #999;
	    bottom:50px;
	    height:100%;
	    top:0;
	    width:1px
	}
	.tree li::after {
	    border-top:1px solid #999;
	    height:20px;
	    top:25px;
	    width:25px
	}
	.tree li span {
	    -moz-border-radius:5px;
	    -webkit-border-radius:5px;
	    border:1px solid #999;
	    border-radius:5px;
	    display:inline-block;
	    padding:3px 8px;
	    text-decoration:none
	}
	.tree li.parent_li>span {
	    cursor:pointer
	}
	.tree>ul>li::before, .tree>ul>li::after {
	    border:0
	}
	.tree li:last-child::before {
	    height:30px
	}
	.tree li.parent_li>span:hover, .tree li.parent_li>span:hover+ul li span {
	    background:#eee;
	    border:1px solid #94a0b4;
	    color:#000
	}
</style>
</head>
<body>
<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">菜单管理</h3>
	</div>
</div>
		<div class="tree well">
	    	<ul>
	        	<li>
		            <span ilevel="1" iid="0" imaxOrderby="${fn:length(sysMenu)}" idesc="" class="_main" >
			            <i class="icon-minus-sign"></i>后台系统
		            </span>  
		            <button class="addItem">
		            	<i class="icon-plus"></i> 
		            </button> 
		            <button class="delItem"  >
		            	<i class="icon-minus"></i>
		            </button> 
		            <button class="setItem">
		            	<i class="icon-modify"></i>
		            </button>
		            <ul>
			           	<c:forEach items="${sysMenu}" var="m" varStatus="idx">
			           		  <li>
			                	<span ilevel="${m.menuLevel}" iid="${m.menuId}" imaxOrderby="${fn:length(m.subMenuList)}" idesc="${m.menuDesc}"  iOrderBy="${m.orderBy}" status="${m.status }" class="_main" >
			                		<i class="icon-minus-sign"></i>${m.menuName}
			                	</span>  
			                	<button class="addItem">
			                		<i class="icon-plus"></i> 
			                	</button>
			                	<button class="delItem" >
			                		<i class="icon-minus"></i>
			                	</button>
			                	<button class="setItem">
			                		<i class="icon-modify"></i>
			                	</button>
			                    <ul>
									<c:forEach items="${m.subMenuList}" var="subM">
									    <li>
			                                <span ilevel="${subM.menuLevel}" iid="${subM.menuId}"  imaxOrderby="${subM.orderBy}" idesc="${subM.menuDesc}"  iOrderBy="${subM.orderBy}" status="${subM.status }" class="_main" >
			                                	<i class="icon-leaf"></i>${subM.menuName}
			                                </span> 
			                                <label class="menuUrl" >${subM.menuUrl}</label>
			                                <button class="delItem">
			                                	<i class="icon-minus"></i>
			                                </button> 
			                                <button class="setItem">
			                                	<i class="icon-modify"></i>
			                                </button>
		                                </li>
									</c:forEach>
								</ul>			 
							  </li>
					 </c:forEach>
	            <!--  end  -->
	            </ul>
	        </li>
	    </ul>
	</div>

<!-- 添加 -->
<div class="modal fade" id="addMenuModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">添加菜单</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal" role="form" id="addMenuForm">
		  <div class="form-group">
		    <label for="inputText" class="col-sm-2 control-label">菜单名称：</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" id="menuName" name="menuName" placeholder="菜单名称">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="inputText" class="col-sm-2 control-label">菜单级别：</label>
		    <div class="col-sm-10">
		    	<input type="text" class="form-control" readonly="readonly" id="menuLevelName" name="menuLevelName" value="">
		    	<input type="hidden" class="form-control" readonly="readonly" id="menuLevel" name="menuLevel" value="">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="inputText" class="col-sm-2 control-label">菜单URL路径：</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" id="menuUrl" name="menuUrl" placeholder="菜单URL路径(注意起始部分加  /)">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="inputText" class="col-sm-2 control-label">菜单功能描述：</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" id="menuDesc" name="menuDesc" placeholder="菜单功能描述">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="inputText" class="col-sm-2 control-label">上级菜单：</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" id="parentMenuName" readonly="readonly" value="">
		      <input type="hidden" class="form-control" id="parentMenuId" name="parentMenuId" value="">
		    </div>
		  </div>
		  <div class="form-group">
	    	<label for="inputText" class="col-sm-2 control-label">是否有效：</label>
	    	<div class="col-sm-10">
		      	<select id="status" name="status" class="form-control">
		    		<option value="0">无效</option>
		    		<option value="1" selected="selected">有效</option>
		    	</select>
		    </div>
		  </div>
		  <input type="hidden" name="orderBy" id="orderBy" value="">
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="addMenu();">确定</button>
      </div>
    </div>
  </div>
</div>

<!-- 编辑 -->
<div class="modal fade" id="updateMenuModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">修改菜单</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal" role="form" id="updateMenuForm">
          <input type="hidden" class="form-control" id="menuId" name="menuId" value="">
		  <div class="form-group">
		    <label for="inputText" class="col-sm-2 control-label">菜单名称：</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" id="menuName" name="menuName" placeholder="菜单名称">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="inputText" class="col-sm-2 control-label">菜单级别：</label>
		    <div class="col-sm-10">
		    	<input type="text" class="form-control" readonly="readonly" id="menuLevelName" name="menuLevelName" value="">
		    	<input type="hidden" class="form-control" readonly="readonly" id="menuLevel" name="menuLevel" value="">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="inputText" class="col-sm-2 control-label">菜单URL路径：</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" id="menuUrl" name="menuUrl" placeholder="菜单URL路径(注意起始部分加  /)">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="inputText" class="col-sm-2 control-label">菜单功能描述：</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" id="menuDesc" name="menuDesc" placeholder="菜单功能描述">
		    </div>
		  </div>
		  <div class="form-group">
	    	<label for="inputText" class="col-sm-2 control-label">是否有效：</label>
	    	<div class="col-sm-10">
		      	<select id="status" name="status"  class="form-control">
		    		<option value="0">无效</option>
		    		<option value="1" selected="selected">有效</option>
		    	</select>
		    </div>
		  </div>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="updateMenu();">确定</button>
      </div>
    </div>
  </div>
</div>
</body>

<script type="text/javascript">
$(function() {
    $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', '点击折叠');
    $('.tree li.parent_li > span').on('click', function (e) {
        var children = $(this).parent('li.parent_li').find(' > ul > li');
        if (children.is(":visible")) {
            children.hide('fast');
            $(this).attr('title', '点击展开').find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
        } else {
            children.show('fast');
            $(this).attr('title', '点击折叠').find(' > i').addClass('icon-minus-sign').removeClass('icon-plus-sign');
        }
        e.stopPropagation();
    });
    
    $(".addItem").each(function(){
    	$(this).on("click",function(){
    		var obj=$(this).prevAll("._main");
    		$("#addMenuForm").find("#parentMenuId").val(obj.attr("iid"));
    		$("#addMenuForm").find("#parentMenuName").val(obj.text().trim());
    		$("#addMenuForm").find("#menuLevel").val(parseInt(obj.attr("ilevel"))+1);
    		$("#addMenuForm").find("#menuLevelName").val(parseInt(obj.attr("ilevel"))+1 == 2 ? '功能模块菜单' : '功能点菜单');
    		$("#addMenuForm").find("#orderBy").val(parseInt(obj.attr("imaxOrderby"))+1);
    		
    		// 显示弹出框
    		$("#addMenuModal").modal({
    			backdrop:'static',
    			keyboard:false,
    			show:true
    		});
    	});
    });
    
    $(".delItem").each(function(){
    	$(this).attr("title","del").hide();
    });
    
    $(".setItem").each(function(){
    	$(this).on("click",function(){
    		var obj=$(this).prevAll("._main");
    		$("#updateMenuForm").find("#menuId").val(obj.attr("iid"));
    		$("#updateMenuForm").find("#menuName").val(obj.text().trim());
    		$("#updateMenuForm").find("#menuLevel").val(parseInt(obj.attr("ilevel"))+1);
    		$("#updateMenuForm").find("#menuLevelName").val(parseInt(obj.attr("ilevel"))+1 == 2 ? '功能模块菜单' : '功能点菜单');
    		$("#updateMenuForm").find("#menuUrl").val($(this).prevAll(".menuUrl").text().trim());
    		$("#updateMenuForm").find("#menuDesc").val(obj.attr("idesc"));
    		$("#updateMenuForm").find("#status").val(obj.attr("status"));
    		
    		// 显示弹出框
    		$("#updateMenuModal").modal({
    			backdrop:'static',
    			keyboard:false,
    			show:true
    		});
    	});
    });
    
    // 添加框关闭清除表单数据
    $('#addMenuModal').on('hidden.bs.modal', function (e) {
    	$("#menuName").val("");
    	$("#menuUrl").val("");
    	$("#menuDesc").val("");
   	})
   	
   	// 更新框关闭清除表单数据
    $('#updateMenuModal').on('hidden.bs.modal', function (e) {
    	$("#menuName").val("");
    	$("#menuUrl").val("");
    	$("#menuDesc").val("");
   	})
    
});

// 添加菜单
function addMenu(){
	var params = $("#addMenuForm").serialize();
	
	 $.ajax({
		 method : 'POST',
		 url :'/sys/menu/add',
		 data:params,
		 success:function(data){
			 if(data.success == true) {
				 alert(data.message);
				 jumpToMenu('/sys/menu/list');
				 $('#addMenuModal').modal("hide");
			 } else {
				 alert(data.message);
			 }
		 }
	 });
}

//修改菜单
function updateMenu(){
	var params = $("#updateMenuForm").serialize();
	
	 $.ajax({
		 method : 'POST',
		 url :'/sys/menu/update',
		 data:params,
		 success:function(data){
			 if(data.success == true) {
				alert(data.message);
				jumpToMenu('/sys/menu/list');
				$('#updateMenuModal').modal("hide");
			 } else {
				 alert(data.message);
			 }
		 }
	 });
}
</script>
</html>
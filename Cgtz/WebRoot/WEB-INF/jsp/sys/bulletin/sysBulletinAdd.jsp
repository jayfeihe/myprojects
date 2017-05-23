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
<title>公告管理</title>
<link href="css/global.css" type="text/css" rel="stylesheet"/>
    <script src="js/jquery.min.js" type="text/javascript"></script>
    <script src="js/jquery.form.js" type="text/javascript"></script>
    <script src="js/jquery.validate.js" type="text/javascript"></script>
    <script src="js/jquery.validate.ms.js" type="text/javascript"></script>
    <script src="js/ckeditor/ckeditor.js" type="text/javascript" ></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	
	<style type="text/css">
	<!--
	body{
	    background-color:#ffffff;
	    margin:0px; padding:0px;
	}
	label.error{
	    font-weight: bold;
	    color: red;
	}
	-->
	</style>
</head>
    <body>
    <div id="main">
    <div id="part1" class="part">
        <p class="title"><a href="#">添加公告</a></p>
        <div class="content">
                <form id="addDataId">
                <ta:formToken/>
                <table>
                <tr><td>标题:<input id="title" name="title" style="width: 270px" 
                            data-options="required:true" class="textbox easyui-validatebox"/></td></tr>
                <tr><td>作者:<input id="writer" name="writer" style="width: 270px" 
                            data-options="required:true" class="textbox easyui-validatebox"/></td></tr>
                <tr><td>状态:<select id="bulState" name="bulState" style="width: 130px">
                        <option value="1" >编辑中</option>
                        <option value="2" >已发布</option>
                        <option value="0" >无效</option>
                    </select>
                    
                </td></tr>
                <tr>
                <td>
                
                <textarea cols="80" id="content" name="content" rows="10"></textarea>
                </td>
                </tr>
                <tr>
                <td>
                <%--
                <input value="提交" type="button" id="submitId" class="btn"/> 
                 --%>
                
                <input value="提交" type="button" id="sid" class="btn" onclick="submitForm()"/>
                <input type="reset" value="重置" class="btn"/>
<%--                    <input value="重置" type="button" id="resetId" class="btn"/>--%>
                    <input value="返回" type="button" class="btn" onclick="javascript:history.go(-1);"/>
                    
                    </td>
                </tr>
                </table>    
                
                </form>
        </div>
    </div>
    
    </div>
    </body>
    <script type="text/javascript">
    //
    CKEDITOR.replace('content');
function submitForm() {
    $("#content").val(CKEDITOR.instances.content.getData());
    //去掉 input 输入的 前后空格
    $('#addDataId').find('input').each(function(){
        $(this).val($.trim($(this).val()));
    });
    //验证表单验证是否通过
    if(false == $('#addDataId').form('validate') ){
        return;
    }
    openMask();
    var params = $('#addDataId').serialize();
    //按钮失效防点击
    $(".btn").attr("disabled", "disabled");
    $.ajax({
        type : "POST",
        url : "<%=basePath%>sys/bulletin/addaction.do",
        data : encodeURI(params),
        success : function(data) {
            if ("true"==data.success) {
//关闭遮罩，弹出消息框
                closeMask();
                
                $.messager.alert('消息', data.message, "info",function(){
//                           var url= "<%=basePath%>sys/bulletin/addaction.do";
//                        window.location=url;
                        window.history.go(-1);
                });
            } else {
            	closeMask();
                
                $.messager.confirm('消息', data.message);
                //按钮生效
                $(".btn").removeAttr("disabled");
            }
        },
        error : function() {
            alert("数据加载失败！");
            //按钮生效
            $(".btn").removeAttr("disabled");
        }
    });
}
</script>
</html>
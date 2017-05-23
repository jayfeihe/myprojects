<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>"/>
<title>文件上传</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/stream-v1.css" rel="stylesheet" type="text/css">
<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>

</head>
<body>
  
           <input id="biz" name="biz" type="hidden" value="${biz}" />
           <input id="secId" name="secId" type="hidden" value="${secId}"  />
           <input id="loanId" name="loanId" type="hidden" value="${loanId}"  />
          
           <!-- <input id="biz" name="biz" type="hidden" value="bitaoyang" />
           <input id="secId" name="secId" type="hidden" value="productType"  />
           <input id="loanId" name="loanId" type="hidden" value="A0012"  /> -->
 <c:if test="${opt eq '0' }">        
	<div id="i_select_files" style="display: block; width: 610px;">
	</div>

	<div id="i_stream_files_queue">
	</div>
	文件类别：
				     <input id="catId" name="catId" type="text" class="easyui-combobox" editable="false" />
	<button onclick="javascript:_t.upload();">开始上传</button>|<button onclick="javascript:_t.cancel();">取消</button>
	<button onclick="javascript:_t.destroy();_t=null;_t=new Stream(config);">清空</button>
	<!--  <button onclick="javascript:_t.stop();">停止上传</button>|
	|<button onclick="javascript:_t.disable();">禁用文件选择</button>|<button onclick="javascript:_t.enable();">启用文件选择</button>
	|<button onclick="javascript:_t.hideBrowseBlock();">隐藏文件选择按钮</button>|<button onclick="javascript:_t.showBrowseBlock();">显示文件选择按钮</button>
	|<button onclick="javascript:_t.destroy();_t=null;_t=new Stream(config);">销毁重新生成按钮</button>
	-->
	<br><!--
	 
	Messages:
	<div id="i_stream_message_container" class="stream-main-upload-box" style="overflow: auto;height:200px;">
	</div>
	
--><br>
</c:if> 
	<div id="imageArea" class="container" style="display: block; width: 100%;">
		
    </div>

<script type="text/javascript" src="js/stream-v1.js"></script>
<script type="text/javascript">
/**
 * 配置文件（如果没有默认字样，说明默认值就是注释下的值）
 * 但是，on*（onSelect， onMaxSizeExceed...）等函数的默认行为
 * 是在ID为i_stream_message_container的页面元素中写日志
 */
 var opt = '${opt}';
 if('0' == opt) {
	
	 var ext = [];
	 // 推送线上只能传zip文件
	 if('filesce10' == '${secId}') {
		 ext = ['.zip'];
	 }
	 
	var config = {
		browseFileId : "i_select_files", /** 选择文件的ID, 默认: i_select_files */
		browseFileBtn : "<div>请选择文件</div>", /** 显示选择文件的样式, 默认: `<div>请选择文件</div>` */
		dragAndDropArea: "i_select_files", /** 拖拽上传区域，Id（字符类型"i_select_files"）或者DOM对象, 默认: `i_select_files` */
		dragAndDropTips: "<span>把文件拖拽到这里</span>", /** 拖拽提示, 默认: `<span>把文件拖拽到这里</span>` */
		filesQueueId : "i_stream_files_queue", /** 文件上传容器的ID, 默认: i_stream_files_queue */
		filesQueueHeight : 200, /** 文件上传容器的高度（px）, 默认: 450 */
		messagerId : "i_stream_message_container", /** 消息显示容器的ID, 默认: i_stream_message_container */
		multipleFiles:true,  /** 多个文件一起上传, 默认: false */
		autoUploading: false, /** 选择文件后是否自动上传, 默认: true */
		autoRemoveCompleted : true, /** 是否自动删除容器中已上传完毕的文件, 默认: false */
		maxSize: 52428800,  //57600//, /** 单个文件的最大大小，默认:2G */
		retryCount : 5, /** HTML5上传失败的重试次数 */
//		postVarsPerFile : { /** 上传文件时传入的参数，默认: {} */
//			param1: "val1",
//			param2: "val2"
//		},
		swfURL : "swf/FlashUploader.swf", /** SWF文件的位置 */
		tokenURL : "files/tk.do", /** 根据文件名、大小等信息获取Token的URI（用于生成断点续传、跨域的令牌） */
		frmUploadURL : "files/fd.do", /** Flash上传的URI */
		uploadURL : "files/upload.do", /** HTML5上传的URI */
		simLimit: 50, /** 单次最大上传文件个数 */
//		extFilters: [".txt", ".rpm", ".rmvb", ".gz", ".rar", ".zip", ".avi", ".mkv", ".mp3"], /** 允许的文件扩展名, 默认: [] */
//		onSelect: function(list) {alert('onSelect')}, /** 选择文件后的响应事件 */
//		onMaxSizeExceed: function(size, limited, name) {alert('onMaxSizeExceed')}, /** 文件大小超出的响应事件 */
//		onFileCountExceed: function(selected, limit) {alert('onFileCountExceed')}, /** 文件数量超出的响应事件 */
//		onExtNameMismatch: function(name, filters) {alert('onExtNameMismatch')}, /** 文件的扩展名不匹配的响应事件 */
//		onCancel : function(file) {alert('Canceled:  ' + file.name)}, /** 取消上传文件的响应事件 */
//		onComplete: function(file) {alert('onComplete')}, /** 单个文件上传完毕的响应事件 */
//		onQueueComplete: function() {alert('onQueueComplete')}, /** 所以文件上传完毕的响应事件 */
//		onUploadError: function(status, msg) {alert('onUploadError')} /** 文件上传出错的响应事件 */
//		onDestroy: function() {alert('onDestroy')} /** 文件上传出错的响应事件 */
		extFilters: ext,
		onQueueComplete: function(file) {
			initImages('${loanId}','${biz}','${secId}','','${opt}');
		}
	};
	var _t = new Stream(config);
	$("#i_stream_dropzone").hide();

 }

	
 $(document).ready(function() {
	 var opt = '${opt}';
	 if('0' == opt) {
		//获取数据字典 根据类别
		var tsurl="sys/datadictionary/listjason.do?keyName="+$("#secId").val();
		//keyProp  keyValue
		
		$("#catId").combobox("clear");
		$('#catId').combobox({
			url:tsurl,
			valueField:'keyProp',
			textField:'keyValue',
			
			//添加空白行
			loadFilter:function(data){
				var comVal = $(this).combobox("getValue");
				if(comVal == '' || comVal == null) {
			   		var opts = $(this).combobox('options');
			   		var emptyRow = {};
					emptyRow[opts.valueField] = '';
					emptyRow[opts.textField] = '请选择';
					data.unshift(emptyRow);
					$(this).combobox("setValue",'');
				}
				return data;
			}
		});
	 }

	initImages('${loanId}','${biz}','${secId}','','${opt}');
});
	
</script>

<script type="text/javascript">
	function initImages(loan_id,biz_key,sec,category,opt) {
		/* var temp = [];
        $(".picShowArea").each(function(){
            temp.push($(this).attr("newvalue"));
         }); */
		$.ajax({
			 type : "POST",
             url : "files/read.do",
             data : {"loanId":loan_id,"bizKey":biz_key,"sec":sec,"category":category,"opt":opt,"pageSize":10},
             dataType: "html",  
             success : function(data) {
            	 
            	 $("#imageArea").html(data);
            	 
            	 /* var obj = eval(data);
            	 var num = obj.length;
            	 for(var j=0;j<temp.length;j++){                  	 
                     var html = [];
                     var pic =0;
                	for(var i=0;i<num;i++){
                		
                		 var ext = obj[i].ext;
                		 // 图片
                		 if('img' == ext) {
	                		 var id = obj[i].id;//附件ID
	                		 var type = "fileId";
	                		 var fileName = obj[i].fileName; // 文件名称
	                		 var filePath = obj[i].filePath;// 文件路径
	                		 var suffix = fileName.substring(fileName.indexOf(".")+1).toLowerCase();//文件后缀
	                		 var imgSrc = filePath.substring(1);
                			 var imgUrl = "files/imgRead.do?imgurl="+filePath;
                			 
	                		 if(temp[j]==obj[i].category){
	                			 pic++;
	                			 html.push("<input type='checkbox' id='"+fileName+"' name='"+type+"' value ='"+id+"' ><a href = '"+imgUrl+"' target='_blank'><img src='"+imgUrl+"' title='"+fileName+"' style = 'width:150px;height:150px;'/></a>");
	                			 if(pic%6==0){
                                    html.push("</br>");
                                }
	                		 } else {
	                			 continue;
	                		 }
                		 }
                	}
                	 var photoid= "#photo_"+temp[j]; 
                     $(photoid).html(html.join(""));
            	 } */
             },
             error: function(){
            	 
             }
		});
	}	
</script>
</body>
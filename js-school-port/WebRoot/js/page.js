function toPage(pageStart){
	$('#pageStart').val(pageStart);
	$('form').submit();
}

function checkpageNo(totalPage,pageSize){
	var numbers=/^[0-9]{1,20}$/; 
	var pageNum = $('#pageNum').val();
	if(numbers.test(pageNum)&&pageNum>0&&pageNum<=(totalPage)){
		$('#pageStart').val((pageNum - 1) * pageSize);
		$('form').submit();
	}else{
		alert("输入页码有误!!!");
		$('#pageNum').focus();
		return;
	}		
}

function toPage(pageStart,formId){
	$('#pageStart').val(pageStart);
	$('#' + formId).submit();
}

function checkpageNo(totalPage,pageSize,formId){
	var numbers=/^[0-9]{1,20}$/; 
	var pageNum = $('#pageNum').val();
	if(numbers.test(pageNum)&&pageNum>0&&pageNum<=(totalPage)){
		$('#pageStart').val((pageNum - 1) * pageSize);
		$('#' + formId).submit();
	}else{
		alert("输入页码有误!!!");
		$('#pageNum').focus();
		return;
	}		
}

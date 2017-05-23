<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.Map"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>"/>
<title>借款端申请</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
	
<style type="text/css">

</style>
</head>
<body>
<div id="main">
        <div id="part1" class="part">
            <p class="title">
                <a href="javascript:void(0);">
     <c:if test="${paramType=='per'}">个人</c:if><c:if test="${paramType=='org'}">机构</c:if>借款申请</a>
            </p>
        <div class="content">
                <form id="updateForm" name="updateForm" action="loanApp/update.do">
                <input id="appId" name="appId" type="hidden" value="${appId}" />
                <input type="hidden" id="isStart" name="isStart" value="false" />
                <input type="hidden" id="appType" name="appType" value="${appType}" />
                    <div id="alltabs" class="easyui-tabs" data-options="tools:'#tab-tools'">
                        <div title="申请信息" name="myselfinfo" style="padding:10px">
							<c:if test="${appId!=null && appId!=''}">
								<c:forEach items="${mainFlagList}" var="mainFlag" varStatus="status">
									<c:if test="${!status.first}">
										</div><div title="共同借款人(${mainFlag})" name="myselfinfo" data-options="closable:true" style="padding:10px">
									</c:if>
									<jsp:include page="/loan/app/loanUpdateBorr.do?appId=${appId}&mainFlag=${mainFlag}&paramType=${paramType}" flush="false" />
								</c:forEach>
							</c:if>
							<c:if test="${appId==null}" >
							<jsp:include page="/loan/app/loanUpdateBorr.do?mainFlag=0&paramType=${paramType}" flush="true" />
							</c:if>
                        </div>
                        <div title="担保物" style="padding:10px">
							<jsp:include page="/loan/app/collateraList.do?appId=${appId}&paramType=${paramType}" flush="false" />
						</div>
                    </div>
                    <div id="tab-tools">
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addPanel()"></a>
                    </div>
                    	<table id="acceptStaff">
							<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"> <strong>受理人员</strong></div>
							<hr />
							<tr>
									<td>&nbsp;营销人员:</td>
									<td>
										<input value="${bean.sales}" class="easyui-combobox" 
										name="sales" id="sales" data-options="required:true" 
										style="width:152px;" editable="false" />
									</td>
							</tr>
						</table>
                    <br/>
                    <tr>
					<td>
						<input id="updateFormBtn" type="button" value="保存" class="btn" onclick="submitupdateFunction()"/>
					</td>
					</tr>
                    	<table width="100%">
							<tr>
								<td>
									<div id="toolbar" style="margin-left: 10px;margin-top: 10px; font-size: 14px;" >
										<strong>影像资料</strong>
										<hr width="100%" size=2 color=#E0ECFF align=center noshade>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									必备资料： a b c									
								</td>
							</tr>
							<tr>
								<td>
									<span style="float:left: ;" id="yingxiangUP">
									上传附件：&nbsp; &nbsp;
										<input onchange="fileForm();" id="file" name="file" type="file" accept="application/x-zip-compressed" />
									</span>
								</td>
							</tr>
						</table>
						<table width="100%">
							<tr>
								<td id="imgDiv">
								<jsp:include page="/img/imgSlidePath.do?appId=${bean.appId}" />
								</td>
							</tr>
						</table>
					<br />
                     <input id="updateFormBtn" type="button" value="提交" class="btn" onclick="$('#isStart').val('start');submitupdateFunction()"/>
                    <input type="button" value="返回" class="btn" onclick="goback()"/>
                </form>
            </div>
        </div>
    </div>
    
<form id="fileSmt" action="file/upload/zipupload.do"  enctype="multipart/form-data" style="display: none;" >
	<input type="hidden" id="fileappId" name="appId" <c:if test="${bean!=null}">value="${bean.appId}"</c:if>/>
</form>
<!-- <div id="loading" class="easyui-window"  title="" data-options="border:false,modal:true,closed:true,draggable:false,resizable:false" >
	<img src="img/loading.gif" alt="加载中..." />
</div> -->
    
</body>


<script type="text/javascript">
function zhezhao(){
		$("<div class=\"datagrid-mask\" id='chushiZhezhao'></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
		$("<div class=\"datagrid-mask-msg\" id='chushiZhezhaoMsg'></div>").html("正在加载，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
}
zhezhao();
function rmZhezhao(){
		$("#chushiZhezhao").remove();
		$("#chushiZhezhaoMsg").remove();
}

$(window).load(function (){
	rmZhezhao();
});
//文件上传
function fileForm(){
	$('#isStart').val('');
	var appid=$('#appId').val();
	if(appid!=null&&appid!=''){
		var thisfile=$('#yingxiangUP').find("#file");
		var fileValue=$('#file').val();
		if((fileValue.substring(fileValue.lastIndexOf("."))).toUpperCase()!=".ZIP"){
			$.messager.alert('消息', "文件类型必须为 ZIP格式");
			return;
		}
		
		
		$.messager.confirm('消息', "是否确定上传？", function(ok){
            if (ok){
            	var $file=$("#fileSmt input[name='file']");
        		if($file!=null){
        			$file.remove();
        		}
        		var fcolne=thisfile.clone();
        		$('#fileSmt').append(thisfile);
        		$('#yingxiangUP').append(fcolne);
        		$('#fileSmt').submit();
            }
    	});
	}else{
		
		$.messager.alert('消息', "请先保存，然后才能上传附件。");
		
	}
}
$(document).ready(function(){
	$('#fileSmt').submit(function() {
		openMask();
	    $(this).ajaxSubmit({
	    		type : "POST",
	    		contentType:"multipart/form-data",
	    		url : "file/upload/zipupload.do", 
	    		beforeSubmit:function(type) {
	//     			alert(type);
	    		},
	    	    success:function(data) {
	    	    	data=jQuery.parseJSON(data);
	    	    	if ("true"==data.success) {
	    				
	    				$.messager.alert('消息', data.message,"info", function(){
	    						imgpartLoad($('#appId').val());
	    	        	});
	    				
	                } else {
	    				
	    				$.messager.alert('消息', data.message);
	                }
	    	    	 closeMask();
	    		}
	    });
	    return false;
	});
});
</script>

<script type="text/javascript">

var indexNum = ${mainFlag};	
//添加tab
function addPanel(){
	indexNum++;
	$.ajax({
		type : "POST",
		url  : "loan/app/loanUpdateBorr.do?appid=${appid}&paramType=${paramType}&mainFlag="+indexNum,
		async:false,		
		dataType : "html",
		success : function(data) {
			$('#alltabs').tabs('add',{title: '共同借款人',closable:true,content: data});
			xianshi(indexNum);
			$("div[class='panel-body panel-body-noheader panel-body-noborder']").css('padding','10px');
		},
		error : function() {
			$.messager.alert('消息',"操作失败，请联系管理员！");
		}
	});
}
//更新保存
function submitupdateFunction() {
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	//验证表单验证是否通过
	if(false == $('#updateForm').form('validate') ){
		
		$.messager.alert('消息', "页面有必输字段，但未填值！");
		$('#isStart').val('');
		return;
	}
	//弹出异步加载 遮罩
	openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "loan/app/save.do",
		data : encodeURI(params),
		success : function(data) {
			closeMask();
			//TODO 更新类的操作 
			if ("true"==data.success) {
				//关闭遮罩，弹出消息框				
				$.messager.alert('消息', data.message,"info", function(){
					//如果保存的话不跳转
					if($('#isStart').val()=="start") {
						window.history.go(-1);
					}
					
            	});
				// 保存的时候 当前页面刷新， 解决第一次保存APPID 与ID 为空的问题
				if($('#isStart').val()!="start") {
					location.replace("<%=basePath%>loan/app/update.do?id="+data.id);
				}
            } else {				
				$.messager.alert('消息', data.message);
				//按钮生效
            }
		},
		error : function() {
			closeMask();
			
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
			//按钮生效
		}
	});
}
//打开Loading遮罩并修改样式
/* function openLoading(){
	$('#loading').window('open');
	$("#loading").attr("class","");
	$("div[class='panel window']").css("position","absolute");
	$("div[class='panel window']").attr("class","");
	$("div[class='window-shadow']").attr("class","");
} */
//返回
function goback(){
	window.history.go(-1);
}

</script>

<script type="text/javascript">
	function xianshi(mainFlag){
		//var tsurl="sys/datadictionary/listjason.do?keyName=marriage";
		$("#person_"+mainFlag).find("#marriage").combobox("clear");
		$("#person_"+mainFlag).find("#marriage").combobox({
			//url:tsurl, 
			valueField:'keyProp', 
			textField:'keyValue',
			data:dataDictJson.marriage
		});
		//var tsurl="sys/datadictionary/listjason.do?keyName=personidtype";
		$("#person_"+mainFlag).find("#idType").combobox("clear");
		$("#person_"+mainFlag).find("#idType").combobox({
			//url:tsurl, 
			valueField:'keyProp', 
			textField:'keyValue',
			data:dataDictJson.personidtype
		});
		//var tsurl="sys/datadictionary/listjason.do?keyName=personidtype";
		$("#personSpouseInfo_"+mainFlag).find("#contactIdType").combobox("clear");
		$("#personSpouseInfo_"+mainFlag).find("#contactIdType").combobox({
			//url:tsurl, 
			valueField:'keyProp', 
			textField:'keyValue',
			data:dataDictJson.personidtype
		});
		//var tsurl="sys/datadictionary/listjason.do?keyName=loanusage";
		$("#personVal_"+mainFlag).find("#useage").combobox("clear");
		$("#personVal_"+mainFlag).find("#useage").combobox({
			//url:tsurl, 
			valueField:'keyProp', 
			textField:'keyValue',
			data:dataDictJson.loanusage
		});
		//个人信息填充
		//填充select数据 省份
		var provinceurl = "sys/datadictionary/listjason.do?keyName=province";
		$("#person_"+mainFlag).find("#addProvince").combobox("clear");
		$("#person_"+mainFlag).find('#addProvince').combobox({url: provinceurl,valueField: 'keyProp',textField: 'keyValue'});
		
		//填充select数据 市
		var province = $("#person_"+mainFlag).find('#addProvince').combobox('getValue');
		var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(province);
		$("#person_"+mainFlag).find("#addCity").combobox("clear");
		$("#person_"+mainFlag).find('#addCity').combobox({url: cityurl,valueField: 'keyProp',textField: 'keyValue'});
		//填充select数据 区县
		var city = $("#person_"+mainFlag).find('#addCity').combobox('getValue');
		var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(city);
		$("#person_"+mainFlag).find("#addCounty").combobox("clear");
		$("#person_"+mainFlag).find('#addCounty').combobox({url: countyurl, valueField: 'keyProp',textField: 'keyValue'});
		
		//var companyTypeurl = "sys/datadictionary/listjason.do?keyName=businessattr";
		$("#orgInfo_"+mainFlag).find("#orgCompanyType").combobox("clear");
		$("#orgInfo_"+mainFlag).find("#orgCompanyType").combobox({
			//url: companyTypeurl,
			valueField: 'keyProp',
			textField: 'keyValue',
			data:dataDictJson.businessattr
		});
		
		var industry1url = "sys/datadictionary/listjason.do?keyName=industry1";
		$("#orgInfo_"+mainFlag).find("#orgIndustry1").combobox("clear");
		$("#orgInfo_"+mainFlag).find("#orgIndustry1").combobox({url: industry1url,valueField: 'keyProp',textField: 'keyValue'});
		//填充select数据 二级行业
		var industry1 =$("#orgInfo_"+mainFlag).find('#orgIndustry1').combobox('getValue');
		var industry2url = "sys/datadictionary/listjason.do?keyName=industry2&parentKeyProp=" + industry1;
		$("#orgInfo_"+mainFlag).find("#orgIndustry2").combobox("clear");
		$("#orgInfo_"+mainFlag).find('#orgIndustry2').combobox({url: industry2url,valueField: 'keyProp',textField: 'keyValue'});
		
		//填充select数据 三级行业
		var industry2 = $("#orgInfo_"+mainFlag).find('#orgIndustry2').combobox('getValue');
		var industry3url = "sys/datadictionary/listjason.do?keyName=industry3&parentKeyProp=" + industry2;
		$("#orgInfo_"+mainFlag).find("#orgIndustry3").combobox("clear");
		$("#orgInfo_"+mainFlag).find('#orgIndustry3').combobox({url: industry3url, valueField: 'keyProp', textField: 'keyValue' });
		//填充 机构 
		//var tsurl="sys/datadictionary/listjason.do?keyName=companyidtype";
		$("#groupInfo_"+mainFlag).find("#orgIdType").combobox("clear");
		$("#groupInfo_"+mainFlag).find("#orgIdType").combobox({
			//url:tsurl, 
			valueField:'keyProp', 
			textField:'keyValue',
			data:dataDictJson.companyidtype
		});
		//tsurl="sys/datadictionary/listjason.do?keyName=personidtype";
		$("#groupInfo_"+mainFlag).find("#fdorgIdType").combobox("clear");
		$("#groupInfo_"+mainFlag).find("#fdorgIdType").combobox({
		    //url:tsurl, 
			valueField:'keyProp', 
			textField:'keyValue',
			data:dataDictJson.personidtype
		});
		$("#groupInfo_"+mainFlag).find("#sqOrgIdType").combobox("clear");
		$("#groupInfo_"+mainFlag).find("#sqOrgIdType").combobox({
			//url:tsurl, 
			valueField:'keyProp', 
			textField:'keyValue',
			data:dataDictJson.personidtype
		});
		$("#groupInfo_"+mainFlag).find("#cwOrgIdType").combobox("clear");
		$("#groupInfo_"+mainFlag).find("#cwOrgIdType").combobox({
			//url:tsurl, 
			valueField:'keyProp', 
			textField:'keyValue',
			data:dataDictJson.personidtype
		});
		
		
		$("#person_"+mainFlag).find('#addProvince').combobox({
		    onChange: function(newValue, oldValue){
				$("#person_"+mainFlag).find('#addCity').combobox('clear');
				$("#person_"+mainFlag).find('#addCounty').combobox('clear');
				var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
				$("#person_"+mainFlag).find('#addCity').combobox('reload',cityurl); 
				
		    }
		});
		$("#person_"+mainFlag).find('#addCity').combobox({
		    onChange: function(newValue, oldValue){
				$("#person_"+mainFlag).find('#addCounty').combobox('clear');
				var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
				$("#person_"+mainFlag).find('#addCounty').combobox('reload',countyurl); 
		    }
		});
		//填充select数据 一级行业
		var industry1url = "sys/datadictionary/listjason.do?keyName=industry1";
		$("#person_"+mainFlag).find("#industry1").combobox("clear");
		$("#person_"+mainFlag).find('#industry1').combobox({url: industry1url,valueField: 'keyProp',textField: 'keyValue'});
		
		//填充select数据 二级行业
		var industry1 =$("#person_"+mainFlag).find('#industry1').combobox('getValue');
		var industry2url = "sys/datadictionary/listjason.do?keyName=industry2&parentKeyProp=" + industry1;
		$("#person_"+mainFlag).find("#industry2").combobox("clear");
		$("#person_"+mainFlag).find('#industry2').combobox({url: industry2url,valueField: 'keyProp',textField: 'keyValue'});
		
		//填充select数据 三级行业
		var industry2 = $("#person_"+mainFlag).find('#industry2').combobox('getValue');
		var industry3url = "sys/datadictionary/listjason.do?keyName=industry3&parentKeyProp=" + industry2;
		$("#person_"+mainFlag).find("#industry3").combobox("clear");
		$("#person_"+mainFlag).find('#industry3').combobox({url: industry3url, valueField: 'keyProp', textField: 'keyValue' });
		
		//个人信息填充
		//填充select数据 省份
		var provinceurl = "sys/datadictionary/listjason.do?keyName=province";
		$("#personSpouseInfo_"+mainFlag).find("#contactAddProvice").combobox("clear");
		$("#personSpouseInfo_"+mainFlag).find('#contactAddProvice').combobox({url: provinceurl,valueField: 'keyProp',textField: 'keyValue'});
		
		//填充select数据 市
		var province = $("#personSpouseInfo_"+mainFlag).find('#contactAddProvice').combobox('getValue');
		var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(province);
		$("#personSpouseInfo_"+mainFlag).find("#contactAddCity").combobox("clear");
		$("#personSpouseInfo_"+mainFlag).find('#contactAddCity').combobox({url: cityurl,valueField: 'keyProp',textField: 'keyValue'});
		//填充select数据 区县
		var city = $("#personSpouseInfo_"+mainFlag).find('#contactAddCity').combobox('getValue');
		var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(city);
		$("#personSpouseInfo_"+mainFlag).find("#contactAddCounty").combobox("clear");
		$("#personSpouseInfo_"+mainFlag).find('#contactAddCounty').combobox({url: countyurl, valueField: 'keyProp',textField: 'keyValue'});
		
		$("#personSpouseInfo_"+mainFlag).find('#contactAddProvice').combobox({
		    onChange: function(newValue, oldValue){
				$("#personSpouseInfo_"+mainFlag).find('#contactAddCity').combobox('clear');
				$("#personSpouseInfo_"+mainFlag).find('#contactAddCounty').combobox('clear');
				var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
				$("#personSpouseInfo_"+mainFlag).find('#contactAddCity').combobox('reload',cityurl); 
				
		    }
		});
		$("#personSpouseInfo_"+mainFlag).find('#contactAddCity').combobox({
		    onChange: function(newValue, oldValue){
				$("#personSpouseInfo_"+mainFlag).find('#contactAddCounty').combobox('clear');
				var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
				$("#personSpouseInfo_"+mainFlag).find('#contactAddCounty').combobox('reload',countyurl); 
		    }
		});
		//人员信息行业代码
	 	$("#person_"+mainFlag).find('#industry1').combobox({
	    	onChange: function(newValue, oldValue){
				$("#person_"+mainFlag).find('#industry2').combobox('clear');
				$("#person_"+mainFlag).find('#industry3').combobox('clear');
				var industry2url = "sys/datadictionary/listjason.do?keyName=industry2&parentKeyProp=" + newValue;
				$("#person_"+mainFlag).find('#industry2').combobox('reload',industry2url);
	    	}	
		});
		$("#person_"+mainFlag).find('#industry2').combobox({
		    onChange: function(newValue, oldValue){
				$("#person_"+mainFlag).find('#industry3').combobox('clear');
				var industry3url = "sys/datadictionary/listjason.do?keyName=industry3&parentKeyProp=" + newValue;
				$("#person_"+mainFlag).find('#industry3').combobox('reload',industry3url);
		    }
		});
		//机构信息行业代码
	 	$("#orgInfo_"+mainFlag).find('#orgIndustry1').combobox({
	    	onChange: function(newValue, oldValue){
				$("#orgInfo_"+mainFlag).find('#orgIndustry2').combobox('clear');
				$("#orgInfo_"+mainFlag).find('#orgIndustry3').combobox('clear');
				var industry2url = "sys/datadictionary/listjason.do?keyName=industry2&parentKeyProp=" + newValue;
				$("#orgInfo_"+mainFlag).find('#orgIndustry2').combobox('reload',industry2url);
	    	}	
		});
		$("#orgInfo_"+mainFlag).find('#orgIndustry2').combobox({
		    onChange: function(newValue, oldValue){
				$("#orgInfo_"+mainFlag).find('#orgIndustry3').combobox('clear');
				var industry3url = "sys/datadictionary/listjason.do?keyName=industry3&parentKeyProp=" + newValue;
				$("#orgInfo_"+mainFlag).find('#orgIndustry3').combobox('reload',industry3url);
		    }
		});
		$("#person_"+mainFlag).find('#marriage').combobox({
			    onChange: function(marriageVal){
					if(marriageVal == '02'){
						$("#marriageDiv"+mainFlag).show();//显示div   
					}
					if(marriageVal == '01' || marriageVal == '99'){
						$("#marriageDiv"+mainFlag).hide();//隐藏div  
					}
		    	}
			});
		}
	$(document).ready(function(){
		<c:if test="${appId!=null && appId!=''}">
		<c:forEach items="${mainFlagList}" var="mainFlag" varStatus="status">
			xianshi('${mainFlag}');
		</c:forEach></c:if><c:if test="${appId==null}" >
			xianshi('0');
		</c:if>
		//var tsurl="sys/datadictionary/listjason.do?keyName=loanchannel";
	 	$("#appChannel").combobox("clear");
	 	$('#appChannel').combobox({
	 		//url:tsurl, 
	 		valueField:'keyProp', 
	 		textField:'keyValue',
	 		data:dataDictJson.loanchannel
	 	});
	 	
	 	tsurl="product/hedao/listjason.do?type=2&state=1";
		$("#product").combobox("clear");
		$('#product').combobox({url:tsurl, valueField:'name', textField:'name'});
		
		
		
		$('#product').combobox({
			    onChange: function(product){
					$.ajax({
					type : "POST",
					url : "product/hedao/listjason.do?state=1&name="+encodeURI(product),
					success : function(data) {
						$('#interestRate').val(data[0].interestRate);
						$('#period').val(data[0].period);
					}
				})
		    }
		});
		$('#alltabs').tabs({'onBeforeClose':function(title,index){
				var target = $(this);
				var rmTab=target.tabs('getTab',index);
				
				var appId=$("#appId").val(); //申请ID
            	var mainFlag=rmTab.find("#mainFlag").val(); //借款人标识
				var perId=rmTab.find("#id").val(); //LoanApp 人员 Id
				var orgId=rmTab.find("#orgLoanAppId").val(); //LoanApp 机构 Id
				var fdOrgId=rmTab.find("#fdOrgId").val(); //法定ID
            	var cwOrgId=rmTab.find("#cwOrgId").val(); // 财务主管ID
            	var contactId=rmTab.find("#contactId").val(); // 配偶ID
            	var sqOrgId = rmTab.find("#sqOrgId").val(); // 配偶ID
      
				if(appId==null||appId==""||perId==null||perId==""){
					return true;
				}
				var param = "appId="+appId+"&mainFlag="+mainFlag+"&id="+perId+"&orgId="+orgId+"&fdOrgId="+fdOrgId+"&cwOrgId="+cwOrgId+"&contactId="+contactId+"&sqOrgId="+sqOrgId;
				
				
    			
				
				
				$.messager.confirm('消息', "您确定删除共同借款人吗？",function(ok){
	                if (ok){
	                	var opts = $(target).tabs('options');
	        			var bc = opts.onBeforeClose;
	        			opts.onBeforeClose = function(){};  // allowed to close now
	                	$.ajax({
							type : "POST",
							url : "<%=basePath%>" + "loan/app/delPerAndOrg.do",
							data : encodeURI(param),
							success : function(data) {
								if ("true"==data.success) {
									//关闭遮罩，弹出消息框
									
									$.messager.alert('消息', data.message,"info", function(){
						                	target.tabs('close',index);
						        			opts.onBeforeClose = bc;  // restore the event function
					            	});
					            }
							}
						})
	                	
					}
				});
				return false;
			}
		});
		
		//初始化 营销人员
		var sales="sales/listjason.do?state=1&orgId=${login_org.orgId}";
		$('#sales').combobox({url:sales, valueField:'staffNo', textField:'name'});
		
	});
	
</script>

<script type="text/javascript">
var lsZjNo,lsNoType;
function getCustomer(){
	var zujkr=$('#alltabs').tabs('getTab',0);
	var sqType='${paramType}';
	var customerType='';
	
	var $zjNo,$zjNoType,$khName;
	if(sqType=='per'){
		customerType="01";
		$zjNo=zujkr.find("#idNo");
		$zjNoType=zujkr.find("input[name='idType']");
		$khName=zujkr.find("#name");
	}else{
		customerType="02";
		$zjNo=zujkr.find("#orgIdNo");
		$zjNoType=zujkr.find("input[name='orgIdType']");
		$khName=zujkr.find("#orgName");
	}
	var zjNo=$zjNo.val();
	var zjNoType=$zjNoType.val();
	if(zjNo&& zjNo!="" && zjNoType &&zjNoType!=""&&(zjNo!=lsZjNo||zjNoType!=lsNoType)){
		lsZjNo=zjNo;
		lsNoType=zjNoType;
		$.ajax({
			type : "POST",
			url : "customer/readJson.do",
			data : encodeURI("customerType="+customerType+"&zjNumber=" + zjNo+"&idType="+zjNoType),
			dataType : "json",
			success : function(data) {
				if(data.bean!=null&&data.bean!=''){
					$khName.val(data.bean.name);
					$khName.attr("readonly","readonly");
				}else{
					$khName.removeAttr("readonly");
				}
			}
		});
	}
}
</script>



</html>


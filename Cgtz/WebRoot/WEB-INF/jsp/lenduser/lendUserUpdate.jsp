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
<title>出借人基本信息维护更新</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
<style type="text/css">
.require{color:red;}
</style>
</head>
<body>
<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">
		<c:choose>
		<c:when test="${bean.name==null}">添加出借人</c:when>
		<c:otherwise>出借人更新</c:otherwise>
		</c:choose>
		</a></p>
		<div class="content">
			<form id="updateForm" >
				<table>
					<tbody>
					<input id="id" name="id" type="hidden" size="35" value="${ bean.id}" />
<tr>
<td colspan=""><span class="require">*</span>标识:</td>
<td><input id="name" name="name" type="text" data-options="validType:['length[2,5]']" required="true" class="textbox easyui-validatebox" value="${bean.name}"/></td>
<td colspan=""><span class="require">*</span>真实姓名:</td>
<td><input id="realName" name="realName" <c:if test="${not empty bean.realName}">readonly="readonly"</c:if> type="text" data-options="validType:['length[2,5]','CHS']" required="true" class="textbox easyui-validatebox" value="${bean.realName}"/></td>

<td><span class="require">*</span>性别:</td>
<td><input id="gender" required="true" type="text" <c:if test="${not empty bean.realName}">readonly="readonly"</c:if> editable="false" name="gender" class="textbox easyui-combobox"  value="${bean.gender}"/>
</td>

<td><span class="require">*</span>身份证号:</td>
<td><input id="idNo" name="idNo" <c:if test="${not empty bean.realName}">readonly="readonly"</c:if> type="text" data-options="validType:['idcard']" required="true" class="textbox easyui-validatebox" value="${bean.idNo}"/></td>
</tr>
<tr>
<td><span class="require">*</span>EMAIL:</td>
<td><input id="email" name="email" type="text" data-options="validType:['email','length[0,50]']" required="true" class="textbox easyui-validatebox" value="${bean.email}"/></td>
<td><span class="require">*</span>固定电话:</td>
<td><input id="phone" name="phone" type="text" data-options="validType:['number','length[7,20]']" required="true" class="textbox easyui-validatebox" value="${bean.phone}"/></td>

<td><span class="require">*</span>手机:</td>
<td><input id="mobile" name="mobile" type="text" data-options="validType:['mobile','length[11,12]']" required="true" class="textbox easyui-numberbox" value="${bean.mobile}"/></td>
</tr>
<tr id="tr_home">
			<td><span class="require">*</span>户籍所在省:</td>
			<td><input id="addProvice" name="homePrvn"  class="textbox easyui-combobox"  required="true" editable="false"   
				  value="${bean.homePrvn }"/></td>
			<td><span class="require">*</span>城市:</td>
			<td><input id="addCity" name="homeCity"  class="textbox easyui-combobox"   required="true" editable="false"  
				   value="${bean.homeCity}" /></td>
			<td><span class="require">*</span>区/县:</td>
			<td><input id="addCounty" name="homeCtry"  class="textbox easyui-combobox"  required="true" editable="false"  
				    value="${bean.homeCtry }" /></td>
			<td><span class="require">*</span>地址:</td>
			<td><input id="address"  name="homeAddr" type="text"
				data-options="validType:['length[0,100]']" required="true" value="${bean.homeAddr}"
				class="textbox easyui-validatebox"/></td>
		
</tr>
<tr id="tr_now">
			<td><span class="require">*</span>现居住省:</td>
			<td><input type="text" id="addProvice" name="nowPrvn" class="easyui-combobox" required="true"  type="text"
				data-options="validType:['length[0,10]']" editable="false"  value="${bean.nowPrvn }"/></td>
			<td><span class="require">*</span>城市:</td>
			<td><input id="addCity" name="nowCity"  required="true" class="easyui-combobox"  
				data-options="validType:['length[0,10]']"  editable="false" value="${bean.nowCity }" /></td>
			<td><span class="require">*</span>区/县:</td>
			<td><input id="addCounty" name="nowCtry" type="text" required="true" class="easyui-combobox" 
				data-options="validType:['length[0,10]']"   editable="false" value="${bean.nowCtry }" /></td>
			<td><span class="require">*</span>地址:</td>
			<td><input id="address" name="nowAddr" type="text"
				data-options="validType:['length[0,100]']" required="true" value="${bean.nowAddr}"
				class="textbox easyui-validatebox"  /></td>		
</tr>
<%-- <tr>
<td><span class="require">*</span>开户名:</td>
<td><input id="acctName" name="acctName" <c:if test="${not empty bean.realName}">readonly="readonly"</c:if> type="text" data-options="validType:['length[2,50]','CHS']" required="true" class="textbox easyui-validatebox" value="${bean.acctName}" /></td>

<td><span class="require">*</span>开户行:</td>
<td><input id="acctBank" name="acctBank" <c:if test="${not empty bean.realName}">readonly="readonly"</c:if> type="text" data-options="validType:['length[4,50]','CHS']" required="true" class="textbox easyui-validatebox" value="${bean.acctBank}"/></td>

<td><span class="require">*</span>收款账号:</td>
<td><input id="acctCode" name="acctCode" <c:if test="${not empty bean.realName}">readonly="readonly"</c:if> type="text" data-options="validType:['length[15,50]','number']" required="true" class="textbox easyui-validatebox" value="${bean.acctCode}"/></td>
</tr> --%>

<tr>
	<td>开户名:</td>
	<td>
		<input id="acctName" name="acctName" type="text" class="textbox easyui-validatebox" 
			data-options="required:true,validType:['length[0,20]']" value="${bean.acctName}"/>
	</td>
	<td>收款账号:</td>
	<td><input id="acctCode" name="acctCode" type="text" class="textbox easyui-numberbox"
			data-options="required:true" value="${bean.acctCode}"/>
	</td>
</tr>
<tr>
	<td>开户行:</td>
	<td colspan="8">
		<input id="acctPrvn" name="acctPrvn" type="text" class="textbox easyui-combobox"
			data-options="required:true,editable:false" value="${bean.acctPrvn}"/>省
		<input id="acctCity" name="acctCity" type="text" class="textbox easyui-combobox" 
			data-options="required:true,editable:false" value="${bean.acctCity}"/>市
		<%-- <input id="acctCtry" name="acctCtry" type="text" class="textbox easyui-combobox" 
			data-options="required:true,editable:false" value="${bean.acctCtry}"/>区/县 --%>
		<input id="acctBank" name="acctBank" type="text" class="textbox easyui-combobox"
			data-options="required:true,editable:true" value="${bean.acctBank}"/>开户行
		<input id="acctBranch" name="acctBranch" type="text" class="textbox easyui-combobox"
			data-options="required:true,editable:true" value="${bean.acctBranch}" style="width: 250px;"/>支行
	</td>
</tr>

<!--<tr>
<td>余额:</td>
<td><input id="amt" name="amt" type="text" editable="false" required="true"  data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${bean.amt}"/></td>


</tr>
--><tr>
<td>说明:</td>
<td colspan="8"><textarea name="remark" data-options="validType:['length[0,500]']"   class="textbox easyui-validatebox"  value="${bean.remark}" style="resize:none;width:625px;height:50px!important;">${bean.remark}</textarea></td>
</tr>
</tbody>
</table></form>
					<table><tr>
						<td>
							<input type="button" value="保存" class="btn" onclick="updateFunction()"/>
							<!-- <input type="button" value="返回" class="btn" onclick="javascript:back()"/> -->
						</td>
						<td></td>
					</tr>
					
				</table>
		</div>
	</div>
</div>

</body>
<script type="text/javascript">
//开启遮罩
function openMask(){
	$("<div class=\"datagrid-mask\" id='mask_id'></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
	$("<div class=\"datagrid-mask-msg\" id='mask_msg_id'></div>").html("请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
}
//关闭遮罩
function closeMask(){
	$("#mask_id").remove();
	$("#mask_msg_id").remove();
}
//更新保存
function updateFunction() {
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	//验证表单验证是否通过
	if(false == $('#updateForm').form('validate') ){
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}
	//弹出异步加载 遮罩
	openMask();
	$('#acctBank').combobox('setValue',$('#acctBank').combobox('getText') )
	$('#acctBranch').combobox('setValue',$('#acctBranch').combobox('getText') ) 
	var params = $('#updateForm').serialize();
	
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "lenduser/save.do",
		data : encodeURI(params),
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
	                var url= "<%=basePath%>" + "lenduser/query.do";
					window.location=url;
					window.parent.removeTab();
            	});
            } else {				
    			$.messager.alert('消息', data.message);
				//按钮生效
				$(".btn").removeAttr("disabled");
            }
		},
		error : function() {
			//关闭遮罩，弹出消息框
			closeMask();
			$.messager.confirm('消息', '数据加载失败,请联系系统管理员！');
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
	});
}

//返回
function back(){
	window.history.go(-1);
}
//加载地址
function address(id){
	var coll=$(id);
		//填充select数据 省份
		var provinceurl = "sys/datadictionary/listjason.do?keyName=province";
		coll.find('#addProvice').combobox({
		url: provinceurl,
		valueField: 'keyProp',
		textField: 'keyValue',
		onChange: function(newValue, oldValue){
				coll.find('#addCity').combobox('clear');
				coll.find('#addCounty').combobox('clear');
				var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
				coll.find('#addCity').combobox('reload',cityurl);
		}, 
		loadFilter:function(data){
			var comVal = $(this).combobox('getValue');
			if(comVal == '' || comVal == null) {
		   		var opts = $(this).combobox('options');
		   		var emptyRow = {};
				emptyRow[opts.valueField] = '';
				emptyRow[opts.textField] = '请选择';
				data.unshift(emptyRow);
				$(this).combobox('setValue','');
			}
			return data;
		}
		    
	});
		
		//填充select数据 市
		var province = coll.find('#addProvice').combobox('getValue');
		var cityurl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" +encodeURI(province);
		coll.find('#addCity').combobox({
			url: cityurl,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
				coll.find('#addCounty').combobox('clear');
				var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
				coll.find("#addCounty").combobox('reload',countyurl); 
		    }
		});
		//填充select数据 区县
		var city = coll.find('#addCity').combobox('getValue');
		var countyurl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(city);
		coll.find('#addCounty').combobox({url: countyurl, valueField: 'keyProp',textField: 'keyValue'});
} 
function refreshTab(tabs) {
	var currTab =  self.$('#'+tabs).tabs('getSelected'); //获得当前tab
    var url = $(currTab.panel('options').content).attr('src');
    var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
    self.$('#'+tabs).tabs('update', {
      tab : currTab,
      options : {
       content : content,
       //closable:true,  
       fit:true,  
       selected:true 
      }
     });
}
//页面加载完动作
$(document).ready(function(){
	//填充select数据样例
	<%--
	var tsurl="sys/datadictionary/listjason.do?keyName=repaymethod";
	$("#repayMethod").combobox("clear");
	$('#repayMethod').combobox({url:tsurl, valueField:'keyProp', textField:'keyValue'});
	--%>		
     address("#tr_home");
     address("#tr_now"); 
     
     initAcct();
     
     //读取数据字典数据
     $("#gender").combobox({
	          valueField:'keyProp',
	          textField:'keyValue',
	          data:dataDictJson.gender,
	          panelHeight:'auto',
	          loadFilter:function(data){
					var comVal = $(this).combobox('getValue');
					if(comVal == '' || comVal == null) {
				   		var opts = $(this).combobox('options');
				   		var emptyRow = {};
						emptyRow[opts.valueField] = '';
						emptyRow[opts.textField] = '请选择';
						data.unshift(emptyRow);
						$(this).combobox('setValue','');
					}
					return data;
				    }
	          });
 });   
    
</script>

<script type="text/javascript">
	function initAcct(){
		// 开户行省
		var bankPrv = "sys/datadictionary/listjason.do?keyName=province";
		$("#acctPrvn").combobox('clear');
		$("#acctPrvn").combobox({
			url: bankPrv,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
		        $('#acctCity').combobox('clear');
		        /* $('#acctCtry').combobox('clear'); */ 
		        var cUrl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(newValue);
		        $('#acctCity').combobox('reload',cUrl);
	    	},
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
		
		// 开户行市
		var bankPrvVal = $('#acctPrvn').combobox('getValue');
		var bankCity = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(bankPrvVal);
		$("#acctCity").combobox("clear");
		$('#acctCity').combobox({
			url: bankCity,
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
	            /* $('#acctCtry').combobox('clear');
	            var cUrl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(newValue);
	            $('#acctCtry').combobox('reload',cUrl);  */
			}
		});
		
		
		// 开户行区县
		var bankCityVal = $('#acctCity').combobox('getValue');
		/* var bankCtry = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(bankCityVal);
		$("#acctCtry").combobox("clear");
		$('#acctCtry').combobox({
			url: bankCtry, 
			valueField: 'keyProp',
			textField: 'keyValue'
		});	 */
		
		// 银行
		var bank = "sys/datadictionary/listjason.do?keyName=bank";
		$("#acctBank").combobox("clear");
		$('#acctBank').combobox({
			url: bank, 
			valueField: 'keyProp',
			textField: 'keyValue',
			onChange: function(newValue, oldValue){
	            var bankProvince = $('#acctPrvn').combobox('getValue');
	            var bankCity = $('#acctCity').combobox('getValue');
				$('#acctBranch').combobox('clear');
	            var cUrl = "common/listBranchBank.do?province="+encodeURI(bankProvince)+"&city="+encodeURI(bankCity)+"&bank_name="+ encodeURI(newValue);
	            $('#acctBranch').combobox({
					url: cUrl, 
					valueField: 'keyProp',
					textField: 'keyValue'
				});
			},
			filter: function (q, row) {
				var opts = $(this).combobox('options');
				return row[opts.textField].indexOf(q) == 0;
            }
		});	
		
		if(bankPrvVal != '' && bankCityVal != '' && bankVal != '') {
			// 支行
			var bankVal = $('#acctBank').combobox('getValue');
			var branch = "common/listBranchBank.do?province="+encodeURI(bankPrvVal)+"&city="+encodeURI(bankCityVal)+"&bank_name="+encodeURI(bankVal);
			$("#acctBranch").combobox("clear");
			$('#acctBranch').combobox({
				url: branch, 
				valueField: 'keyProp',
				textField: 'keyValue'
			});
		}
	}
</script>
</html>


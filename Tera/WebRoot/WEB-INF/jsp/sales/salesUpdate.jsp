<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />
		<title>营销人员表更新</title>
		<link href="css/global.css" type="text/css" rel="stylesheet" />
		<link href="css/icon.css" type="text/css" rel="stylesheet" />
		<link href="css/default/easyui.css" type="text/css" rel="stylesheet" />
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
					<a href="javascript:void(0);">更新</a>
				</p>
				<div class="content">
					<form id="updateForm">
						<table>
							<tbody>
								<input id="id" name="id" type="hidden" size="35"
									value="${ bean.id}" />
								<tr>
									<td>
										<SPAN style="color: red">*</SPAN>员工卡号:
									</td>
									<td>
										<input id="staffNo" name="staffNo" type="text"
											data-options="required:true,validType:['length[0,50]']"
											class="textbox easyui-validatebox" value="${bean.staffNo}" />
									</td>

									<td>
										<SPAN style="color: red">*</SPAN>姓名:
									</td>
									<td>
										<input id="name" name="name" type="text"
											data-options="required:true,validType:['length[0,50]']"
											class="textbox easyui-validatebox" value="${bean.name}" />
									</td>

									<td>
										性别:
									</td>
									<td>
									<input class="easyui-combobox" data-options=""  name="gender" id="gender" style="width:152px;"  value="${bean.gender}" editable="false"  />
									</td>
									<td>
										户口性质:
									</td>
									<td>
									<input class="easyui-combobox" data-options=""  name="hukou" id="hukou" style="width:152px;"  value="${bean.hukou}" editable="false"  />
									</td>
									</tr>
									<tr>
									<td>
										身份证号:
									</td>
									<td>
										<input id="idNo" name="idNo" type="text"
											data-options="validType:['idcard']"
											class="textbox easyui-validatebox" value="${bean.idNo}" />
									</td>
									<td>
<!-- 										所属机构: -->
										<SPAN style="color: red">*</SPAN>所属组织:
									</td>
									<td>
										<input id="orgId" name="orgId" type="hidden" value="${login_org.orgId}"/>
<!-- 										<input id="orgId" name="orgId" type="hidden" -->
<!-- 											data-options="validType:['length[0,20]']" -->
<%-- 											class="textbox easyui-combobox" style="" editable="false" value="${login_org.orgId}" readonly/> --%>
										<input id="departId" name="departId" class="easyui-combotree" data-options="url:'sys/depart/listDataUseForSales.do?orgId=${login_org.orgId}',method:'get',required:true" 
											style="width: 152px;" value="${bean.departId}"/>
									</td>
									<td>
										<SPAN style="color: red">*</SPAN>岗位级别:
									</td>
									<td>
									<input class="easyui-combobox" data-options="required:true"  name="level" id="level" style="width:152px;"  value="${bean.level}" editable="false"  />
									</td>
									<td>
										入职时间:
									</td>
									<td>
										<input id="entryDate" name="entryDate" type="text" data-options=""
											editable="false" class="textbox easyui-datebox"
											value="${bean.entryDateStr}" />
									</td>
								</tr>
								<tr>
									<td>
										拟转正时间:
									</td>
									<td>
										<input id="permanentDate" name="permanentDate" type="text" data-options=""
											editable="false" class="textbox easyui-datebox"
											value="${bean.permanentDateStr}" />
									</td>

									<td>
										离职时间:
									</td>
									<td>
										<input id="leaveDate" name="leaveDate" type="text"
											editable="false" class="textbox easyui-datebox"
											value="${bean.leaveDateStr}" />
									</td>

									<td>
										调岗时间:
									</td>
									<td>
										<input id="adjustDate" name="adjustDate" type="text"
											editable="false" class="textbox easyui-datebox"
											value="${bean.adjustDateStr}" />
									</td>

									<td>
										劳动合同期限:
									</td>
									<td>
										<input id="contractTerm" name="contractTerm" type="text"
											editable="false" data-options="min:0,max:100,precision:0"
											class="textbox easyui-numberbox" value="${bean.contractTerm}" />年
									</td>
								</tr>
								<tr>
									<td>
										联系电话:
									</td>
									<td>
										<input id="mobile" name="mobile" type="text"
											data-options="validType:['length[0,20]']"
											class="textbox easyui-validatebox" value="${bean.mobile}" />
									</td>

									<td>
										邮箱:
									</td>
									<td>
										<input id="email" name="email" type="text"
											data-options="validType:['email','length[0,50]']"
											class="textbox easyui-validatebox" value="${bean.email}" />
									</td>

									<td>
										婚姻状况:
									</td>
								    <td>
									<input class="easyui-combobox" data-options=""  name="marriage" id="marriage" style="width:152px;"  value="${bean.marriage}" editable="false"  />
									</td>
									<td>
										婚育状况:
									</td>
									<td>
									<input class="easyui-combobox" data-options=""  name="birthFlag" id="birthFlag" style="width:152px;"  value="${bean.birthFlag}" editable="false"  />
									</td>
									</tr>
									<tr>
									<td>
										最高学历:
									</td>
									<td>
									<input class="easyui-combobox" data-options=""  name="education" id="education" style="width:152px;"  value="${bean.education}" editable="false"  />
									</td>
									<td>
										学位证:
									</td>
									<td>
										<input id="degreeCertifyNo"  name="degreeCertifyNo" type="text"
											class="textbox easyui-validatebox" data-options="validType:['length[0,50]']"   
											style="width:152px;"  value="${bean.degreeCertifyNo}" />
									</td>
									<td>
										毕业院校:
									</td>
									<td>
										<input id="university" name="university" type="text"
											data-options="validType:['length[0,50]']"
											class="textbox easyui-validatebox" value="${bean.university}" />
									</td>

									<td>
										所学专业:
									</td>
									<td>
										<input id="major" name="major" type="text"
											data-options="validType:['length[0,50]']"
											class="textbox easyui-validatebox" value="${bean.major}" />
									</td>
								</tr>
								<tr>
									<td>
										身份证地址:
									</td>
									<td>
										<input id="idAddress" name="idAddress" type="text"
											data-options="validType:['length[0,50]']"
											class="textbox easyui-validatebox" value="${bean.idAddress}" />
									</td>

									<td>
										现住址:
									</td>
									<td>
										<input id="curAddress" name="curAddress" type="text"
											data-options="validType:['length[0,50]']"
											class="textbox easyui-validatebox" value="${bean.curAddress}" />
									</td>

									<td>
										紧急联系人:
									</td>
									<td>
										<input id="contact" name="contact" type="text"
											data-options="validType:['length[0,50]']"
											class="textbox easyui-validatebox" value="${bean.contact}" />
									</td>

									<td>
										紧急联系人电话:
									</td>
									<td>
										<input id="contactPhone" name="contactPhone" type="text"
											data-options="validType:['length[0,50]']"
											class="textbox easyui-validatebox"
											value="${bean.contactPhone}" />
									</td>
								</tr>
								<tr>
									<td>
										工资卡卡号:
									</td>
									<td>
										<input id="wageCardNo" name="wageCardNo" type="text"
											data-options="validType:['length[0,50]']"
											class="textbox easyui-validatebox" value="${bean.wageCardNo}" />
									</td>

									<td>
										开户行:
									</td>
									<td>
										<input id="wageCardBank" name="wageCardBank" type="text"
											data-options="validType:['length[0,50]']"
											class="textbox easyui-validatebox"
											value="${bean.wageCardBank}" />
									</td>

									<td>
										社保缴纳情况:
									</td>
									<td>
										<input id="socialSecurity" name="socialSecurity" type="text"
											data-options="validType:['length[0,50]']"
											class="textbox easyui-validatebox"
											value="${bean.socialSecurity}" />
									</td>

									<td>
										是否转正:
									</td>
									<td>
									<input class="easyui-combobox" data-options=""  name="fullFlag" id="fullFlag" style="width:152px;"  value="${bean.fullFlag}" editable="false"  />
									</td>
									</tr>
									<tr>
										<td>在职状态:</td>
										<td>
											<select class="easyui-combobox" name="state" editable="false" style="width:152px;">
												<option value="1">在职</option>
												<option value="2">挂起</option>
												<option value="0">离职</option>
											</select>
										</td>
									</tr>
									<tr>
									<td>
										备注:
									</td>
									<td colspan="9">
<!-- 										<input id="remark" name="remark" type="text" -->
<!-- 											data-options="validType:['length[0,200]']" -->
<%-- 											class="textbox easyui-validatebox" value="${bean.remark}" /> --%>
										<textarea id="remark" name="remark" value="${bean.remark}" class="textbox easyui-validatebox" data-options="validType:['length[0,200]']"
											style="resize: none;width:888px;height:50px!important;">${bean.remark}</textarea>
									</td>
								</tr>

								<tr>
									<td>
										<input type="button" value="保存" class="btn"
											onclick="updateFunction()" />
										<input type="button" value="返回" class="btn"
											onclick="javascript:back()" />
									</td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
		</div>

		<!-- <div id="loading" class="easyui-window" title=""
			data-options="border:false,modal:true,closed:true,draggable:false,resizable:false">
			<img src="img/loading.gif" alt="加载中..." />
		</div> -->

	</body>

	<script type="text/javascript">
//更新保存
function updateFunction() {
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	//验证表单验证是否通过
	if (false == $('#updateForm').form('validate')) {
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}
	
	// 如果是主管，判断该机构下是否存在
	if($("input[name='level']").val() == '3'){
		var staff_no = $("#staffNo").val();
		var org_id = $('#orgId').val();
		var depart_id = $("input[name='departId']").val();
		if(isExistTeamManagerByOrgId(staff_no,org_id,depart_id)) {
			$.messager.alert("消息","该组已存在团队经理","warning");
			return;
		}
	}
	
	//弹出异步加载 遮罩
	openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax( {
		type : "POST",
		url : "<%=basePath%>" + "sales/save.do",
		data : encodeURI(params),
		success : function(data) {
			//关闭遮罩，弹出消息框
		closeMask();
		if ("true" == data.success) {
			$.messager.alert('消息', data.message, 'info', function() {
				//	                var url= "<%=basePath%>" + "sales/query.do";
					//					window.location=url;
					window.history.go(-1);
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
		$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
		//按钮生效
		$(".btn").removeAttr("disabled");
	}
	});
}

//返回
function back() {
	window.history.go(-1);
}

/* //打开Loading遮罩并修改样式
function openLoading() {
	$('#loading').window('open');
	$("#loading").attr("class", "");
	$("div[class='panel window']").css("position", "absolute");
	$("div[class='panel window']").attr("class", "");
	$("div[class='window-shadow']").attr("class", "");
} */

// 判断此营业部是否已经有主管存在
function isExistTeamManagerByOrgId(staff_no,org_id,depart_id) {
	var isExist = false;
	$.ajax({
		type: 'get',
		async:false,
		url: "<%=basePath%>" + "sales/isExistTeamManagerByOrgId.do",
		data: ({'staffNo':staff_no,'orgId':org_id,'departId':depart_id}),
		success: function(data){
			if(data.success == 'true'){
				isExist = true;
			}
		},
		error: function(){
			
		}
	});
	return isExist;
}

//页面加载完动作
$(document).ready(function() {
	//填充select数据 性别
	//var tsurl="sys/datadictionary/listjason.do?keyName=marriage";
	$("#gender").combobox("clear");
	$('#gender').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.gender
	});
	$('#gender').combobox('select', '${bean.gender}');
	
	//填充select数据 户口性质
	//var tsurl="sys/datadictionary/listjason.do?keyName=marriage";
	$("#hukou").combobox("clear");
	$('#hukou').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.hukou
	});
	$('#hukou').combobox('select', '${bean.hukou}');
	
	//填充select数据 岗位级别
	//var tsurl="sys/datadictionary/listjason.do?keyName=marriage";
	$("#level").combobox("clear");
	$('#level').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.saleslevel
	});
	$('#level').combobox('select', '${bean.level}');
	
	//填充select数据 婚姻状况
	//var tsurl="sys/datadictionary/listjason.do?keyName=marriage";
	$("#marriage").combobox("clear");
	$('#marriage').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.marriage
	});
	$('#marriage').combobox('select', '${bean.marriage}');
	
	//填充select数据 婚育状况
	//var tsurl="sys/datadictionary/listjason.do?keyName=marriage";
	$("#birthFlag").combobox("clear");
	$('#birthFlag').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.birthFlag
	});
	$('#birthFlag').combobox('select', '${bean.birthFlag}');
	
	//填充select数据 最高学历
	//var tsurl="sys/datadictionary/listjason.do?keyName=marriage";
	$("#education").combobox("clear");
	$('#education').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.education
	});
	$('#education').combobox('select', '${bean.education}');
	
	//填充select数据 学位证
	//var tsurl="sys/datadictionary/listjason.do?keyName=marriage";
	$("#degreeCertifyNo").combobox("clear");
	$('#degreeCertifyNo').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.degreeCertifyNo
	});
	$('#degreeCertifyNo').combobox('select', '${bean.degreeCertifyNo}');
	
	//填充select数据 是否转正
	//var tsurl="sys/datadictionary/listjason.do?keyName=marriage";
	$("#fullFlag").combobox("clear");
	$('#fullFlag').combobox({
		//url:tsurl, 
		valueField:'keyProp', 
		textField:'keyValue',
		data:dataDictJson.booleanFlag
	});
	$('#fullFlag').combobox('select', '${bean.fullFlag}');
	
//	var tsurl="sys/org/subOrg.do?orgId=${login_org.orgId}";
// 	var tsurl="sys/org/subOrg.do?orgId=86";
// 	$("#orgId").combobox("clear");
// 	$('#orgId').combobox({
// 		url:tsurl,
// 		valueField:'orgId',
// 		textField:'orgName',
//		添加空白行
// 		loadFilter:function(data){
//        		var opts = $(this).combobox('options');
//        		var emptyRow = {};
// 			emptyRow[opts.valueField] = '&nbsp;';
// 			emptyRow[opts.textField] = '...';
// 			data.unshift(emptyRow);
//  			return data;
// 		}
// 	});
	
	//填充select数据样例
		/*
		var tsurl="sys/datadictionary/listjason.do?keyName=repaymethod";
		$("#repayMethod").combobox("clear");
		$('#repayMethod').combobox({url:tsurl, valueField:'keyProp', textField:'keyValue'});
		 */
	});
</script>
</html>


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
<title>信用贷款决策表更新</title>
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
		<a href="javascript:void(0);">审核</a>
		<span style="padding-left: 20px; color: red;">
			<c:if test="${not empty bean.gradeRemind}">${bean.gradeRemind}</c:if>
		</span> 
		<span style="float: right; padding-right: 20px;">
			<input type="button" value="保存" class="btn" onclick="updateFunction('save')"/>
			<input type="button" value="返回" class="btn" onclick="javascript:back()"/>
		</span>
	</p>
<div class="content">
<div class="easyui-layout" id="easyui_layout" style="width:100%;height:730px;" data-options="fit:true">
<div data-options="region:'center',split:true,border:false" >
	<div class="easyui-tabs" data-options="fit:true,onLoad:tabsReadOnly">
		<div title="影像资料" data-options="href:'${basePath}img/imgSlidePath.do?appId=${bean.appId}'" style="width: 100%;padding:10px"></div>
		<div title="申请信息" data-options="href:'${basePath}credit/app/read1.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		<div title="综合信息" data-options="href:'${basePath}credit/verify/complexInfo.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
	</div>
</div>

<div data-options="region:'east',split:true,border:false" style="width:800px;">
<form id="updateForm" >
	<input type="hidden" name="id" value="${bean.id}" />
	<input type="hidden" name="appId" value="${bean.appId}" />

	<div class="easyui-tabs" id="verify_tabs" style="height:730px;" data-options="fit:true">
		<div title="风险提示" data-options="href:'${basePath}credit/rule/read.do'" style="width: 100%;padding:10px"></div>
		<div title="信息核查" data-options="href:'${basePath}credit/verify/infoCheck.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		<div title="影像摘要" data-options="href:'${basePath}credit/verify/imageSummary.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		<div title="信用报告" data-options="href:'${basePath}credit/verify/creditReport.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		<div title="面审调查" data-options="href:'${basePath}credit/verify/interviewSurvey.do?id=${bean.id}'" style="width: 100%;padding:10px"></div>
		<div title="审核决策"  style="width: 100%;padding:10px">
			 <table width="100%">
				<tr>
					<td colspan="9">
						<div style="font-size: 14px;" >
							<strong>申请信息</strong>
							<hr width="100%" size=2 color="#D3D3D3" noshade>
						</div>
					</td>
				</tr>
			 	<tr><td>
			 		<table id="verifylAppInfo">
						<tr>
							<td align="right">借款金额：</td>
							<td><input type="text" id="amount" value="${bean.amount/10000}" class="textbox easyui-numberbox" 
								style="width:128px;" disabled="disabled" data-options="required:true,min:0,precision:2"/>万元</td>
<%--							<td align="right">借款期限：</td>--%>
<%--							<td><input id="period" type="text" data-options="required:true,validType:['length[0,10]']" --%>
<%--								class="easyui-combobox"  value="${bean.period}" editable="false" style="width:152px;" disabled="disabled"/></td>--%>
							<td align="right">渠道：</td>
							<td><input id="belongChannel" type="text" data-options="required:true,validType:['length[0,50]']" 
								class="easyui-combobox"  value="${bean.belongChannel}" editable="false" style="width:152px;" disabled="disabled"/></td>
							<td align="right">产品：</td>
							<td><input id="product" type="text" data-options="required:true,validType:['length[0,50]']" 
								class="easyui-combobox"  value="${bean.product}" editable="false" style="width:152px;" disabled="disabled"/></td>
						</tr>
					</table>
				</td></tr>
			
				<c:if test="${not empty creditFraud }">
					<tr>
						<td colspan="9">
							<div style="font-size: 14px;" >
								<strong>欺诈审核信息</strong>
								<hr width="100%" size=2 color="#D3D3D3" noshade>
							</div>
						</td>
					</tr>
					<tr><td>
				 		<table id="fraudInfo">
							<tr>
								<td align="right">欺诈结果：</td>
								<td><input type="text" id="fraudResult"  class="textbox easyui-combobox" 
									disabled="disabled" 
									data-options="valueField:'keyProp', 
												textField:'keyValue',
												panelHeight:'auto',
												value:'${creditFraud.result }',
												data:[{'keyProp':'1','keyValue':'无问题'},
													  {'keyProp':'2','keyValue':'有问题'}]"/>
								</td>
								<td align="right">处理人：</td>
								<td>
									<input type="text" value="${creditFraud.operatorName }" class="textbox" disabled="disabled"/>
								</td>
							</tr>
							<tr>
								<td align="right">欺诈备注：</td>
								<td colspan="4">
									<textarea id="resultInfo" name="resultInfo" class="textbox" disabled="disabled"
									style="resize: none;width:304px;height:60px!important;">${creditFraud.resultInfo }</textarea>
								</td>
							</tr>
						</table>
					</td></tr>
				</c:if>
				
			 	<tr>
					<td colspan="9">
						<div style="font-size: 14px;" >
							<strong>审核</strong>
								<input type="hidden" name="decision.id" value="${creditVerify.id}" />
								<input type="hidden" name="decision.appId" value="${bean.appId}" />
								<input type="hidden" name="decision.type" value="1" />
								<input type="hidden" id="state" name="decision.state" value="1" />
							<hr width="100%" size=2 color="#D3D3D3" noshade>
						</div>
					</td>
				</tr>
			 	<tr><td>
			 		<table id="verifyInfo">
			 			<tr>
							<td align="right">借款金额:</td>
							<td><div id ="amountDiv">
							<input id="amount" name="decision.amount" value="${creditVerify.amount/10000}" type="text"
									data-options="required:true,min:0,precision:2"
									class="textbox easyui-numberbox" style="width:128px;" onchange="updateYhkje();"/>万元
								</div>
							</td>
<%--							<td align="light">借款期限:</td>--%>
<%--							<td ><input id="period" name="decision.period" value="${creditVerify.period}" type="text" data-options="required:true,validType:['length[0,10]']" --%>
<%--								class="easyui-combobox" editable="false" style="width:152px;"/>--%>
<%--							</td>--%>
							<td align="right">渠道:
								<input id="belongChannel" name="decision.belongChannel" type="text" class="easyui-combobox" 
								data-options="required:true,validType:['length[0,50]']" editable="false" value="${creditVerify.belongChannel}"/>
							</td>
							<td align="right">产品：
								<input id="product" name="decision.product" value="${creditVerify.product }" type="text"  data-options="required:true,validType:['length[0,50]']" 
								class="easyui-combobox" editable="false" style="width:152px;"/>
							</td>
						</tr>
						<tr>
							<td align="right">月还款额:</td>
							<td colspan="5">
								 <input id="yhkje" type="text" data-options="min:0,precision:2" class="textbox easyui-numberbox" value="${yhkje}" style="width:280px;" onchange="updateFkje();"/>
							</td>
						</tr>
						<tr>
							<td align="right">决策:</td>
							<td>
								<input id="decision" name="decision.decision" value="${creditVerify.decision}" type="text"  data-options="required:true,validType:['length[0,50]']" 
								class="easyui-combobox" editable="false" style="width:152px;"/>
							</td>
							<td align="center" colspan="4" valign="top">
								<div id="noPassReasonDiv" >
									回退码：
									<input id="decisionCode5" name="decision.decisionCode5" 
										value="${creditVerify.decisionCode5}" type="text" 
										data-options="validType:['length[0,50]']" 
										class="easyui-combobox" editable="false" style="width:152px;"/>
									<input id="decisionCode6" name="decision.decisionCode6" 
										value="${creditVerify.decisionCode6}" type="text" 
										data-options="validType:['length[0,50]']" 
										class="easyui-combobox" editable="false" style="width:152px;"/>
									
<%--								<input type="button" class="btn" value="补充资料" onclick="addObj('commonContacts');"/>--%>
<%-- 									<input type="button" class="btn" value="补充资料" onclick="javascript:artOpenPage('影像资料','credit/verify/addImgDataPopup.do?appId=${bean.appId}')"> --%>
									<br>
									退回原因:
									<textarea id="returnMsg" name="decision.returnMsg" value="${creditVerify.returnMsg}" class="textbox "
									data-options="required:true,validType:['length[0,500]']" 
									style="resize: none;width:390px;height:50px!important;vertical-align:middle;">${creditVerify.returnMsg}</textarea>
								</div>
								<div id ="decisionCodeDiv">
									违例码:
									<input id="decisionCode1" name="decision.decisionCode1" 
										value="${creditVerify.decisionCode1}" type="text" 
										data-options="validType:['length[0,50]']" 
										class="easyui-combobox" editable="false" style="width:152px;"/>
									<input id="decisionCode2" name="decision.decisionCode2" 
										value="${creditVerify.decisionCode2}" type="text" 
										data-options="validType:['length[0,50]']" 
										class="easyui-combobox" editable="false" style="width:152px;"/>
								</div>
							</td>
						</tr>
<%--						<tr>--%>
<%--							<td>--%>
<%--								特殊审批退回原因:--%>
<%--							</td>--%>
<%--							<td colspan="5">--%>
<%--								<textarea value="${returnReasons}" class="textbox easyui-validatebox" data-options="validType:['length[0,500]']" --%>
<%--									style="resize: none;width:625px;height:50px!important;vertical-align:middle;background-color: #F0F0F0;" disabled="disabled">${returnReasons}</textarea>--%>
<%--							</td>--%>
<%--						</tr>--%>
						<tr>
							<td colspan="6">
								<div id="imageClassListDiv" style="overflow-x: hidden;border:1px solid #000000;height:150px;width:100%">
									<table>
										<tr>
											<td colspan="2">
												<span style="font-weight:bold;"><big>影像分类选择：</big></span>
											</td>
										</tr>
										<tr>
											<td width="40px;">
												<span style="font-weight:bold;">A类：</span>
											</td>
											<td>
												<c:forEach items="${imgList}" var="img" varStatus="status">
													<c:if test="${fn:startsWith(img.keyProp, 'A')}">
															<nobr><input id="checkbox_${img.keyProp}" type="checkbox" value="${img.keyProp}_${img.keyValue}" 
															onchange="checkImgClassChange('checkbox_${img.keyProp}')"/>
															${img.keyProp}
															${img.keyValue}</nobr>
													</c:if>
												</c:forEach>
											</td>
										</tr>
										<tr>
											<td>
												<span style="font-weight:bold;">B类：</span>
											</td>
											<td>
												<c:forEach items="${imgList}" var="img" varStatus="status">
													<c:if test="${fn:startsWith(img.keyProp, 'B')}">
														<nobr><input id="checkbox_${img.keyProp}" type="checkbox" value="${img.keyProp}_${img.keyValue}" 
															onchange="checkImgClassChange('checkbox_${img.keyProp}')"/>
															${img.keyProp}
															${img.keyValue}
														</nobr>
													</c:if>
												</c:forEach>
											</td>
										</tr>
										<tr>
											<td>
												<span style="font-weight:bold;">C类：</span>
											</td>
											<td>
												<c:forEach items="${imgList}" var="img" varStatus="status">
													<c:if test="${fn:startsWith(img.keyProp, 'C')}">
															<nobr><input id="checkbox_${img.keyProp}" type="checkbox" value="${img.keyProp}_${img.keyValue}" 
															onchange="checkImgClassChange('checkbox_${img.keyProp}')"/>
															${img.keyProp}
															${img.keyValue}</nobr>
													</c:if>
												</c:forEach>
											</td>
										</tr>
										<tr>
											<td>
												<span style="font-weight:bold;">D类：</span>
											</td>
											<td>
												<c:forEach items="${imgList}" var="img" varStatus="status">
													<c:if test="${fn:startsWith(img.keyProp, 'D')}">
															<nobr><input id="checkbox_${img.keyProp}" type="checkbox" value="${img.keyProp}_${img.keyValue}" 
															onchange="checkImgClassChange('checkbox_${img.keyProp}')"/>
															${img.keyProp}
															${img.keyValue}</nobr>
													</c:if>
												</c:forEach>
											</td>
										</tr>
										<tr>
											<td>
												<span style="font-weight:bold;">E类：</span>
											</td>
											<td>
												<c:forEach items="${imgList}" var="img" varStatus="status">
													<c:if test="${fn:startsWith(img.keyProp, 'E')}">
															<nobr><input id="checkbox_${img.keyProp}" type="checkbox" value="${img.keyProp}_${img.keyValue}" 
															onchange="checkImgClassChange('checkbox_${img.keyProp}')"/>
															${img.keyProp}
															${img.keyValue}</nobr>
													</c:if>
												</c:forEach>
											</td>
										</tr>
										<tr>
											<td>
												<span style="font-weight:bold;">F类：</span>
											</td>
											<td>
												<c:forEach items="${imgList}" var="img" varStatus="status">
													<c:if test="${fn:startsWith(img.keyProp, 'F')}">
															<nobr><input id="checkbox_${img.keyProp}" type="checkbox" value="${img.keyProp}_${img.keyValue}" 
															onchange="checkImgClassChange('checkbox_${img.keyProp}')"/>
															${img.keyProp}
															${img.keyValue}</nobr>
													</c:if>
												</c:forEach>
											</td>
										</tr>
										<tr>
											<td>
												<span style="font-weight:bold;">G类：</span>
											</td>
											<td>
												<c:forEach items="${imgList}" var="img" varStatus="status">
													<c:if test="${fn:startsWith(img.keyProp, 'G')}">
															<nobr><input id="checkbox_${img.keyProp}" type="checkbox" value="${img.keyProp}_${img.keyValue}" 
															onchange="checkImgClassChange('checkbox_${img.keyProp}')"/>
															${img.keyProp}
															${img.keyValue}</nobr>
													</c:if>
												</c:forEach>
											</td>
										</tr>
										<tr>
											<td>
												<span style="font-weight:bold;">H类：</span>
											</td>
											<td>
												<c:forEach items="${imgList}" var="img" varStatus="status">
													<c:if test="${fn:startsWith(img.keyProp, 'H')}">
															<nobr><input id="checkbox_${img.keyProp}" type="checkbox" value="${img.keyProp}_${img.keyValue}" 
															onchange="checkImgClassChange('checkbox_${img.keyProp}')"/>
															${img.keyProp}
															${img.keyValue}</nobr>
													</c:if>
												</c:forEach>
											</td>
										</tr>
										<tr>
											<td>
												<span style="font-weight:bold;">I类：</span>
											</td>
											<td>
												<c:forEach items="${imgList}" var="img" varStatus="status">
													<c:if test="${fn:startsWith(img.keyProp, 'I')}">
															<nobr><input id="checkbox_${img.keyProp}" type="checkbox" value="${img.keyProp}_${img.keyValue}" 
															onchange="checkImgClassChange('checkbox_${img.keyProp}')"/>
															${img.keyProp}
															${img.keyValue}</nobr>
													</c:if>
												</c:forEach>
											</td>
										</tr>
										<tr>
											<td>
												<span style="font-weight:bold;">J类：</span>
											</td>
											<td>
												<c:forEach items="${imgList}" var="img" varStatus="status">
													<c:if test="${fn:startsWith(img.keyProp, 'J')}">
															<nobr><input id="checkbox_${img.keyProp}" type="checkbox" value="${img.keyProp}_${img.keyValue}" 
															onchange="checkImgClassChange('checkbox_${img.keyProp}')"/>
															${img.keyProp}
															${img.keyValue}</nobr>
													</c:if>
												</c:forEach>
											</td>
										</tr>
										<tr>
											<td>
												<span style="font-weight:bold;">K类：</span>
											</td>
											<td>
												<c:forEach items="${imgList}" var="img" varStatus="status">
													<c:if test="${fn:startsWith(img.keyProp, 'K')}">
															<nobr><input id="checkbox_${img.keyProp}" type="checkbox" value="${img.keyProp}_${img.keyValue}" 
															onchange="checkImgClassChange('checkbox_${img.keyProp}')"/>
															${img.keyProp}
															${img.keyValue}</nobr>
													</c:if>
												</c:forEach>
											</td>
										</tr>
										<tr>
											<td>
												<span style="font-weight:bold;">L类：</span>
											</td>
											<td>
												<c:forEach items="${imgList}" var="img" varStatus="status">
													<c:if test="${fn:startsWith(img.keyProp, 'L')}">
															<nobr><input id="checkbox_${img.keyProp}" type="checkbox" value="${img.keyProp}_${img.keyValue}" 
															onchange="checkImgClassChange('checkbox_${img.keyProp}')"/>
															${img.keyProp}
															${img.keyValue}</nobr>
													</c:if>
												</c:forEach>
											</td>
										</tr>
										<tr>
											<td>
												<span style="font-weight:bold;">M类：</span>
											</td>
											<td>
												<c:forEach items="${imgList}" var="img" varStatus="status">
													<c:if test="${fn:startsWith(img.keyProp, 'M')}">
															<nobr><input id="checkbox_${img.keyProp}" type="checkbox" value="${img.keyProp}_${img.keyValue}" 
															onchange="checkImgClassChange('checkbox_${img.keyProp}')"/>
															${img.keyProp}
															${img.keyValue}</nobr>
													</c:if>
												</c:forEach>
											</td>
										</tr>
										<tr>
											<td>
												<span style="font-weight:bold;">N类：</span>
											</td>
											<td>
												<c:forEach items="${imgList}" var="img" varStatus="status">
													<c:if test="${fn:startsWith(img.keyProp, 'N')}">
															<nobr><input id="checkbox_${img.keyProp}" type="checkbox" value="${img.keyProp}_${img.keyValue}" 
															onchange="checkImgClassChange('checkbox_${img.keyProp}')"/>
															${img.keyProp}
															${img.keyValue}</nobr>
													</c:if>
												</c:forEach>
											</td>
										</tr>
										<tr>
											<td>
												<span style="font-weight:bold;">O类：</span>
											</td>
											<td>
												<c:forEach items="${imgList}" var="img" varStatus="status">
													<c:if test="${fn:startsWith(img.keyProp, 'O')}">
															<nobr><input id="checkbox_${img.keyProp}" type="checkbox" value="${img.keyProp}_${img.keyValue}" 
															onchange="checkImgClassChange('checkbox_${img.keyProp}')"/>
															${img.keyProp}
															${img.keyValue}</nobr>
													</c:if>
												</c:forEach>
											</td>
										</tr>
										<tr>
											<td>
												<span style="font-weight:bold;">P类：</span>
											</td>
											<td>
												<c:forEach items="${imgList}" var="img" varStatus="status">
													<c:if test="${fn:startsWith(img.keyProp, 'P')}">
															<nobr><input id="checkbox_${img.keyProp}" type="checkbox" value="${img.keyProp}_${img.keyValue}" 
															onchange="checkImgClassChange('checkbox_${img.keyProp}')"/>
															${img.keyProp}
															${img.keyValue}</nobr>
													</c:if>
												</c:forEach>
											</td>
										</tr>
										<tr>
											<td>
												<span style="font-weight:bold;">Q类：</span>
											</td>
											<td>
												<c:forEach items="${imgList}" var="img" varStatus="status">
													<c:if test="${fn:startsWith(img.keyProp, 'Q')}">
															<nobr><input id="checkbox_${img.keyProp}" type="checkbox" value="${img.keyProp}_${img.keyValue}" 
															onchange="checkImgClassChange('checkbox_${img.keyProp}')"/>
															${img.keyProp}
															${img.keyValue}</nobr>
													</c:if>
												</c:forEach>
											</td>
										</tr>
										<tr>
											<td>
												<span style="font-weight:bold;">R类：</span>
											</td>
											<td>
												<c:forEach items="${imgList}" var="img" varStatus="status">
													<c:if test="${fn:startsWith(img.keyProp, 'R')}">
															<nobr><input id="checkbox_${img.keyProp}" type="checkbox" value="${img.keyProp}_${img.keyValue}" 
															onchange="checkImgClassChange('checkbox_${img.keyProp}')"/>
															${img.keyProp}
															${img.keyValue}</nobr>
													</c:if>
												</c:forEach>
											</td>
										</tr>
										<tr>
											<td>
												<span style="font-weight:bold;">S类：</span>
											</td>
											<td>
												<c:forEach items="${imgList}" var="img" varStatus="status">
													<c:if test="${fn:startsWith(img.keyProp, 'S')}">
															<nobr><input id="checkbox_${img.keyProp}" type="checkbox" value="${img.keyProp}_${img.keyValue}" 
															onchange="checkImgClassChange('checkbox_${img.keyProp}')"/>
															${img.keyProp}
															${img.keyValue}</nobr>
													</c:if>
												</c:forEach>
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="6">
								<input id="imageSummarys" name="decision.imageSummarys" type="hidden" value="${key_props}"/>
								<input id="imageSummarysName" type="hidden" value="${key_values}"/>
								<div id="imageClassSelectDiv" style="overflow-x: hidden;border:1px solid #000000;height:50px;width:100%">
									 <span style='font-weight:bold;'>已选的影像分类：</span>
								</div>
							</td>
						</tr>
						<c:if test="${!empty returnReasons }"> 
							<tr>
								<td>审批退回原因:</td>
								<td colspan="5">
									<textarea value="${returnReasons}" class="textbox easyui-validatebox" data-options="validType:['length[0,500]']" 
										style="resize: none;width:625px;height:50px!important;background-color: #F0F0F0;" disabled="disabled">${returnReasons}</textarea>
								</td>
							</tr>
						</c:if>
						<tr>
							<td>备注：</td>
							<td colspan="5">
								<textarea id="remarks" name="decision.remarks" value="${creditVerify.remarks}"
								class="textbox easyui-validatebox" data-options="validType:['length[0,100]']"
								style="resize: none;width:625px;height:50px!important;">${creditVerify.remarks}</textarea>
							</td>
						</tr>
						<tr>
							<td colspan="6">
								<input type="button" value="提交" class="btn" onclick="updateFunction('submit')"/>
							</td>
						</tr>
						<%--<tr>
							<td><input type="button" name="decision" value="拒贷"/></td>
							<td align="right">拒件码：</td>
							<td colspan="3">
								<input id="decisionCode3" name="decision.decisionCode3" 
									value="${creditVerify.decisionCode3}"
									type="text" data-options="validType:['length[0,50]']" 
									class="easyui-combobox" editable="false" style="width:152px;"/>
								<input id="decisionCode4" name="decision.decisionCode4" 
									value="${creditVerify.decisionCode4}"
									type="text" data-options="validType:['length[0,50]']" 
									class="easyui-combobox" editable="false" style="width:152px;"/>
							</td>
						</tr>--%>
					</table>
				</td></tr>
				<tr>
					<td colspan="9">
						<div style="font-size: 14px;" >
							<strong>反欺诈处理</strong>
							<hr width="100%" size=2 color="#D3D3D3" noshade>
						</div>
					</td>
				</tr>
			 	<tr><td>
			 		<table id="creditAntifraud">
			 			<tr>
							<td>
								<input type="button" value="提交反欺诈" class="btn" onclick="submitAntifraud('antifraud')"/>
							</td>
							<td>
<%--								<input id="submitInfo" name="submitInfo" type="text"--%>
<%--									data-options="validType:['length[0,500]']"--%>
<%--									class="textbox easyui-validatebox" style="width:304px;"/>--%>
								<textarea id="submitInfo" name="submitInfo"  value="" class="textbox " data-options="validType:['length[0,500]']" 
										style="resize: none;width:600px;height:50px!important;"></textarea>
							</td>
						</tr>
<%--						<tr>--%>
<%--							<td>--%>
<%--								<input type="button" value="解除反欺诈" class="btn" onclick="submitRelieveAntifraud('relieveAntifraud')"/>--%>
<%--							</td>--%>
<%--							<td>--%>
<%--								<input id="result" name="result" type="text" data-options="validType:['length[0,50]']" --%>
<%--								class="easyui-combobox" editable="false" />--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--						<tr>--%>
<%--							<td></td>--%>
<%--							<td ><textarea id="resultInfo" name="resultInfo" style="resize: none;width:304px;height:60px;"></textarea></td>--%>
<%--						</tr>--%>
					</table>
				</td></tr>
			</table>
		</div>
		<div title="流程报告" data-options="href:'${basePath}bpm/getBpmLogs.do?bizKey=${bean.appId}'" style="width: 100%;padding:10px"></div>
	</div>
</form>
</div>
</div>
</div>
</div>
</div>

</body>
<script type="text/javascript" >

window.parent.openMask();
$(window).load(function (){
	window.parent.closeMask();
});
</script>

<script type="text/javascript">
function submitAntifraud(buttonType){
//弹出异步加载 遮罩
	window.parent.openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "credit/antifraud/save.do",
		data : params+"&buttonType="+buttonType,
		success : function(data) {
			//关闭遮罩，弹出消息框
			window.parent.closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					//window.history.go(-1);
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
			window.parent.closeMask();
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
	});
}

/**
 * 影像分类选择事件
 */
function checkImgClassChange(checkName){
	var imgs = $("#imageClassListDiv").find("input[type='checkbox']");
	$("#imageClassSelectDiv").empty();
	$("#imageClassSelectDiv").append("<span style='font-weight:bold;'>已选的影像分类：</span> ");
	var imageSummarys = "";
	for(var i=0;i<imgs.length;i++){
		if(imgs[i].checked){
			imageSummarys = imageSummarys + imgs[i].value.split("_")[0] + ",";
			$("#imageClassSelectDiv").append("<span style='color: green;'>" + imgs[i].value + "</span>");
			$("#imageClassSelectDiv").append("&nbsp; &nbsp;");
		}
	}
	if("" != imageSummarys){
		$("#imageSummarys").val(imageSummarys.substring(0, imageSummarys.length - 1));
	}else{
		$("#imageSummarys").val(imageSummarys);
	}
}

// /*
//   function submitRelieveAntifraud(buttonType){
// //弹出异步加载 遮罩
// 	window.parent.openMask();
// 	var params = $('#updateForm').serialize();
// 	//按钮失效防点击
// 	$(".btn").attr("disabled", true);
// 	$.ajax({
// 		type : "POST",
<%-- 		url : "<%=basePath%>credit/antifraud/update.do", --%>
// 		data : params+"&buttonType="+buttonType,
// 		success : function(data) {
// 			//关闭遮罩，弹出消息框
// 			window.parent.closeMask();
// 			if ("true"==data.success) {
// 				$.messager.alert('消息', data.message, 'info', function(){
// 					location.replace(location.href);
// 					return;
// 				});
//             } else {				
//     			$.messager.alert('消息', data.message);
// 				//按钮生效
// 				$(".btn").removeAttr("disabled");
//             }
// 		},
// 		error : function() {
// 			//关闭遮罩，弹出消息框
// 			window.parent.closeMask();
// 			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
// 			//按钮生效
// 			$(".btn").removeAttr("disabled");
// 		}
// 	});
// }
// */
//只读页 设置为只读
function tabsReadOnly(redinfo){

	redinfo.find("input[type='radio']").attr('disabled',true);
	redinfo.find("input[type='hidden']").attr('disabled',true);
// 	redinfo.find("input[type='button']").attr("disabled",true);

	redinfo.find('.easyui-validatebox').attr('disabled', 'disabled');
	redinfo.find('.easyui-validatebox').validatebox('disableValidation');
	redinfo.find('.easyui-combobox').combo('disableValidation');
	redinfo.find('.easyui-numberbox').numberbox('disableValidation');
	redinfo.find('.easyui-datebox').datebox('disableValidation');
	redinfo.find('.easyui-combobox').combo('disable');
	redinfo.find('.easyui-numberbox').numberbox('disable');
	redinfo.find('.easyui-datebox').datebox('disable');

	redinfo.find('div.panel div.panel-tool>a.icon-cancel').hide();
}

//信息核查tab页签校验
function infoCheckValidate(){
	//114查号台
	var is114Check = true;
	$("#114").find("tr").each(function(i,v){
			var name = "name="+"\'"+"type2Exts["+i+"].value"+"\'";
			var input = "input["+name+"]"+":checked";
			if($(this).find(input).val() == '' || $(this).find(input).val() == null){
				is114Check = false;
				return false;
			}
		});
	if(is114Check == false){
		$.messager.alert("提示消息","请完善114查号台核实情况","warning");
		return false;
	}
	
	//人法网
	var rfCheck = true;
	$("#rfw").find("tr").each(function(i,v){
			var name = "name="+"\'"+"type3Exts["+i+"].value"+"\'";
			var input = "input["+name+"]"+":checked";
			if($(this).find(input).val() == '' || $(this).find(input).val() == null){
				rfCheck = false;
				return false;
			}
		});
	if(rfCheck == false){
		$.messager.alert("提示消息","请完善人法网核实情况","warning");
		return false;
	}
	
	//工商网
	var gsCheck = true;
	$("#gsw").find("tr").each(function(i,v){
			var name = "name="+"\'"+"type4Exts["+i+"].value"+"\'";
			var input = "input["+name+"]"+":checked";
			if($(this).find(input).val() == '' || $(this).find(input).val() == null){
				gsCheck = false;
				return false;
			}
		});
	if(gsCheck == false){
		$.messager.alert("提示消息","请完善工商网核实情况","warning");
		return false;
	}
	
	//网查
	var wcCheck = true;
	$("#wc").find("tr").each(function(i,v){
			var name = "name="+"\'"+"type5Exts["+i+"].value"+"\'";
			var input = "input["+name+"]"+":checked";
			if($(this).find(input).val() == '' || $(this).find(input).val() == null){
				wcCheck = false;
				return false;
			}
		});
	if(wcCheck == false){
		$.messager.alert("提示消息","请完善网查核实情况","warning");
		return false;
	}
}

//更新保存
function updateFunction(buttonType) {
	//去掉 input 输入的 前后空格
	$('#updateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	
	if(buttonType!="save"){
		if(false == $('#updateForm').form('validate') ){
			$.messager.alert('消息', "页面有必输字段，但未填值！");
			return;
		}
		var message = "";
		if ($(".tab_show_infoChecky").length == 0) {
			message += "[信息核查]";
		}
		if ($(".tab_show_summary").length == 0) { 
			message += "[影像摘要]";
		}
		if ($(".tab_show_report").length == 0) { 
			message += "[信用报告]";
		}
		if ($(".tab_show_interview").length == 0) { 
			message += "[面审调查]";
		}
		if("" !=  message){
			$.messager.alert('消息', message+"没有加载。");
			return;
		}
	}
	
	if('submit' == buttonType){
		if('' == $("#verifyInfo").find("#decision").combobox('getValue')){
			$.messager.confirm('消息', "请选择决策！");
			return;
		}
		if('00' == $("#verifyInfo").find("#decision").combobox('getValue')){
			if('' == $("#verifyInfo").find("#decisionCode5").combobox('getValue') || '' == $("#verifyInfo").find("#decisionCode6").combobox('getValue')){
				$.messager.confirm('消息', "请选择回退码！");
				return;
			}
		}
		//信息核查校验(拟通过)
		if($("#verifyInfo").find("#decision").combobox('getValue') == '02'){
			if(infoCheckValidate()==false){
				return;
			}
		}
	}
	//弹出异步加载 遮罩
	window.parent.openMask();
	var params = $('#updateForm').serialize();
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>credit/verify/save.do",
		data : params+"&buttonType="+buttonType,
		success : function(data) {
			//关闭遮罩，弹出消息框
			window.parent.closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					if(buttonType=="save"){
						location.replace(location.href);
						return;
					}
// 					window.history.go(-1);
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
			window.parent.closeMask();
			$.messager.alert('消息', '数据加载失败,请联系系统管理员！');
			//按钮生效
			$(".btn").removeAttr("disabled");
		}
	});
}

/**
 * 改变月还款金额或反推放款金额
 * url		访问地址
 * params	出入参数
 * type 	yhkje fkje
 */
function updateJe(url, params, type){
	$.ajax({
		type : "POST",
		url : url,
		data : params,
		success : function(data) {
			//关闭遮罩，弹出消息框
			if ("true"==data.success) {
				if('yhkje' == type)
					$("#verifyInfo").find("#yhkje").numberbox('setValue', data.message);
				else
					$("#verifyInfo").find("#amount").numberbox('setValue', data.message/10000);
            }
		},
		error : function() {
		}
	});
}

/**
 * 改变放款金额计算出月还款额调用的方法
 */
function updateYhkje(){
	if($('#verifyInfo').find('#amount').val() != '' && $('#verifyInfo').find('#product').combobox('getValue') != ''){
		updateJe("<%=basePath%>" + "credit/verify/getYhkje.do", "fkje=" + $('#verifyInfo').find('#amount').val() + "&productName=" + $('#verifyInfo').find('#product').combobox('getValue'), "yhkje");
	}
}

/**
 * 改变月还款额计算出放款额调用的方法
 */
function updateFkje(){
	if($('#verifyInfo').find('#yhkje').val() != '' && $('#verifyInfo').find('#product').combobox('getValue') != ''){
		updateJe("<%=basePath%>" + "credit/verify/getFkje.do", "yhkje=" + $('#verifyInfo').find('#yhkje').val() + "&productName=" + $('#verifyInfo').find('#product').combobox('getValue'), "fkje");
	}
}

//返回
function back(){
	$.messager.confirm('消息',"请确定保存过信息，确定要返回吗？", function(ok){
		if (ok){
			window.parent.removeTab();
		}
	});
}

//切换 是否验证
function toggleValidate(objId,isValidete, type){
	var state=!isValidete;
	var obj=$(objId);
	obj.find('.easyui-validatebox').validatebox({novalidate:state});
	//obj.find('.easyui-combobox').combobox({novalidate:state});
	obj.find('.easyui-numberbox').validatebox({novalidate:state});
	obj.find('.easyui-datebox').datebox({novalidate:state});
}

//原页面弹出框影像的补充资料
function artOpenPage(_title,_url) {
	if($("body").find("#dialogDiv").length==0){
		 $('body').append($("<div id='dialogDiv' style='top:150px;'></div>"));
	}
	$('#dialogDiv').dialog({
	    title: _title,
	    height: 460,
	    width: 1000,
	    closed: false,
	    cache: false,
	    href: encodeURI(_url),
	    modal: true,
	    resizable: true
	});
}


//页面加载完动作
$(document).ready(function (){
	$("#imageClassSelectDiv").empty();
	$("#imageClassSelectDiv").append("<span style='font-weight:bold;'>已选的影像分类：</span> ");
	var imgs = $('#imageSummarys').val().split(',');
	var imgsName = $('#imageSummarysName').val().split(',');
	for(var i=0;i<imgs.length;i++){
		$("#imageClassListDiv").find("#checkbox_" + imgs[i]).attr("checked", "checked");
		$("#imageClassSelectDiv").append("<span style='color: green;'>" + imgs[i] + "_" + imgsName[i] + "</span>");
		$("#imageClassSelectDiv").append("&nbsp; &nbsp;");
	}
	
	if('${creditVerify.decision}' == '00'){
		$('#noPassReasonDiv').show();//显示div   
		toggleValidate('#noPassReasonDiv',true);
		$("#imageClassListDiv").show();
		$("#imageClassSelectDiv").show();
		$('#decisionCodeDiv').hide();//隐藏div  
		toggleValidate('#decisionCodeDiv',false);
		toggleValidate('#amountDiv',false);
	}else if('${creditVerify.decision}' == ''){
		$('#noPassReasonDiv').hide();//div 
		toggleValidate('#noPassReasonDiv',false);
		$("#imageClassListDiv").hide();
		$("#imageClassSelectDiv").hide();
		$('#decisionCodeDiv').hide();//div 
		toggleValidate('#decisionCodeDiv',false);
		toggleValidate('#amountDiv',true);
	}else if('${creditVerify.decision}' == '02'){
		$('#noPassReasonDiv').hide();//div 
		toggleValidate('#noPassReasonDiv',false);
		$("#imageClassListDiv").hide();
		$("#imageClassSelectDiv").hide();
		$('#decisionCodeDiv').hide();//div  
		toggleValidate('#decisionCodeDiv',false);
		toggleValidate('#amountDiv',true);
	}else{
		$('#noPassReasonDiv').hide();//div 
		toggleValidate('#noPassReasonDiv',false);
		$("#imageClassListDiv").hide();
		$("#imageClassSelectDiv").hide();
		$('#decisionCodeDiv').show();//div  
		toggleValidate('#decisionCodeDiv',true);
		toggleValidate('#amountDiv',true);
	}
	
	//拖动时 调节 下拉框 宽度
	$('#easyui_layout').layout('panel', 'east').panel({
		onResize:function(w,h){
			$("#verify_tabs").tabs('getSelected').find(".easyui-accordion").accordion("resize");
			return true;
		  }
		});
//填充select数据 解除反欺诈结果
	$("#creditAntifraud").find("#result").combobox("clear");
	$("#creditAntifraud").find('#result').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:[{"keyProp":"1","keyValue":"无问题"},{"keyProp":"2","keyValue":"有问题"}]
	});
	
//填充select数据 借款期限
	$("#verifylAppInfo").find("#period").combobox("clear");
	$("#verifylAppInfo").find("#period").combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:[{"keyProp":"1","keyValue":"12"},{"keyProp":"2","keyValue":"18"},{"keyProp":"3","keyValue":"24"},{"keyProp":"4","keyValue":"36"}]
	});
	
	//填充select数据 渠道
	var channelurl="channeltotal/listjason.do";
	$("#verifylAppInfo").find("#belongChannel").combobox("clear");
	$("#verifylAppInfo").find('#belongChannel').combobox({
		url:channelurl,
		valueField:'channelCode', 
		textField:'channelName',
		onChange: function(newValue, oldValue){             
			$("#verifylAppInfo").find('#product').combobox('clear');
			var producturl = "product/hedao/listjason.do?type=3&belongChannel=" + encodeURI(newValue);
			$("#verifylAppInfo").find('#product').combobox('reload',producturl); 
		}
	});
	//填充select数据 产品
	var belongChannel = $("#verifylAppInfo").find('#belongChannel').combobox('getValue');
	var producturl = "product/hedao/listjason.do?type=3&belongChannel=" + encodeURI(belongChannel);
	$("#verifylAppInfo").find("#product").combobox("clear");
	$("#verifylAppInfo").find('#product').combobox({
		url:producturl,
		valueField:'name', 
		textField:'name'
	});
	
	//填充select数据 决策信息
	$("#verifyInfo").find('#decision').combobox("clear");
	$("#verifyInfo").find('#decision').combobox({
		valueField:'keyProp',
		textField:'keyValue',
		data:[{"keyProp":"","keyValue":"请选择"},{"keyProp":"00","keyValue":"退回"},{"keyProp":"02","keyValue":"拟通过"},{"keyProp":"03","keyValue":"拟拒贷"}],
		onChange: function(decisionVal){
			if(decisionVal == '00'){
				$('#noPassReasonDiv').show();//显示div   
				toggleValidate('#noPassReasonDiv',true);
				$("#imageClassListDiv").show();
				$("#imageClassSelectDiv").show();
				$('#decisionCodeDiv').hide();//隐藏div  
				toggleValidate('#decisionCodeDiv',false);
				toggleValidate('#amountDiv',false);
			}else if(decisionVal == ''){
				$('#noPassReasonDiv').hide();//div 
				toggleValidate('#noPassReasonDiv',false);
				$("#imageClassListDiv").hide();
				$("#imageClassSelectDiv").hide();
				$('#decisionCodeDiv').hide();//div 
				toggleValidate('#decisionCodeDiv',false);
				toggleValidate('#amountDiv',true);
			}else if(decisionVal == '02'){
				$('#noPassReasonDiv').hide();//div 
				toggleValidate('#noPassReasonDiv',false);
				$("#imageClassListDiv").hide();
				$("#imageClassSelectDiv").hide();
				$('#decisionCodeDiv').hide();//div  
				toggleValidate('#decisionCodeDiv',false);
				toggleValidate('#amountDiv',true);
			}else{
				$('#noPassReasonDiv').hide();//div 
				toggleValidate('#noPassReasonDiv',false);
				$("#imageClassListDiv").hide();
				$("#imageClassSelectDiv").hide();
				$('#decisionCodeDiv').show();//div  
				toggleValidate('#decisionCodeDiv',true);
				toggleValidate('#amountDiv',true);
			}
   	}
	});
	$("#verifyInfo").find("#period").combobox("clear");
	$("#verifyInfo").find('#period').combobox({
		valueField:'keyProp', 
		textField:'keyValue',
		data:[{"keyProp":"1","keyValue":"12"},{"keyProp":"2","keyValue":"18"},{"keyProp":"3","keyValue":"24"},{"keyProp":"4","keyValue":"36"}]
	});
	
	//填充select数据 渠道
	var channelurl1="channeltotal/listjason.do?state=1";
	$("#verifyInfo").find("#belongChannel").combobox("clear");
	$("#verifyInfo").find('#belongChannel').combobox({
		url:channelurl1,
		valueField:'channelCode', 
		textField:'channelName',
		onChange: function(newValue, oldValue){             
			$("#verifyInfo").find('#product').combobox('clear');
			var producturl1 = "product/hedao/listjason.do?type=3&state=1&flag=1&belongChannel=" + encodeURI(newValue);
			$("#verifyInfo").find('#product').combobox('reload',producturl1); 
		}
	});
	//填充select数据 产品
	var belongChannel1 = $("#verifyInfo").find('#belongChannel').combobox('getValue');
	var producturl1 = "product/hedao/listjason.do?type=3&state=1&flag=1&belongChannel=" + encodeURI(belongChannel1);
	$("#verifyInfo").find("#product").combobox("clear");
	$("#verifyInfo").find('#product').combobox({
		url:producturl1,
		valueField:'name', 
		textField:'name',
		onChange: function(productVal){
			if($('#verifyInfo').find('#amount').val() != '')
				updateJe("<%=basePath%>" + "credit/verify/getYhkje.do", "fkje=" + $('#verifyInfo').find('#amount').val() + "&productName=" + productVal, "yhkje");
		}	
	});
	
	//填充select数据 回退码01
    var returnCode01url = "sys/datadictionary/listjason.do?keyName=returnCode01";
	$("#decisionCode5").combobox("clear");
	$('#decisionCode5').combobox({
		url: returnCode01url,
		valueField: 'keyProp',
		textField: 'keyValue',
		onChange: function(newValue, oldValue){
            $('#decisionCode6').combobox('clear');
            var returnCode02url = "sys/datadictionary/listjason.do?keyName=returnCode02&parentKeyProp=" + encodeURI(newValue);
            $('#decisionCode6').combobox('reload',returnCode02url); 
      	}
	});
	//填充select数据 回退码02
	var decisionCode5 = $('#decisionCode5').combobox('getValue');
	var returnCode02url = "sys/datadictionary/listjason.do?keyName=returnCode02&parentKeyProp=" + encodeURI(decisionCode5);
	$("#decisionCode6").combobox("clear");
	$('#decisionCode6').combobox({
		url: returnCode02url,
		valueField: 'keyProp',
		textField: 'keyValue'
	});
<%--	//判断 决策选择 ，取消隐藏域 必填验证--%>
<%--	VAR MARRIAGEVALUE= $("#DECISION").COMBOBOX("GETVALUE");--%>
<%--		IF(DECISIONVAL!='00'){// 00是不通过--%>
<%--			TOGGLEVALIDATE("#NOPASSREASONDIV",FALSE);--%>
<%--		}--%>
	
});
</script>
</html>


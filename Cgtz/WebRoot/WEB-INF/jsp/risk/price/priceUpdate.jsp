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
<title>核价更新</title>
	<link href="css/global.css" type="text/css" rel="stylesheet"/>
	<link href="css/icon.css" type="text/css" rel="stylesheet"/>
	<link href="css/default/easyui.css" type="text/css" rel="stylesheet"/>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/jquery.form.js" type="text/javascript"></script>
	<script src="js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="js/easyui-lang-zh_CN.js" type="text/javascript"></script>
</head>
<body>

<div id="main">
	<div id="part1" class="part">
		<p class="title"><a href="javascript:void(0);">核价更新</a></p>
		<div class="content">
		<form id="priceUpdateForm" >
			<!-- 车 -->
			<c:if test="${type eq '01' }">
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>车辆基本信息</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>车辆型号:</td>
						<td><input id="carType" name="carType" type="text" readonly="readonly" data-options="required:true,validType:['length[0,50]']" class="textbox easyui-validatebox" value="${bean.carType}"/></td>
						<td>车牌号:</td>
						<td><input id="license" name="license" type="text" readonly="readonly" data-options="required:true,validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.license}"/></td>
						<td>车辆年限:</td>
						<td><input id="carAge" name="carAge" type="text" readonly="readonly" data-options="required:true,validType:['length[0,3]']" class="textbox easyui-validatebox" value="${bean.carAge}"/>年</td>
					</tr>
					<tr>
						<td>发动机号:</td>
						<td><input id="engCode" name="engCode" type="text" readonly="readonly" data-options="required:true,validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.engCode}"/></td>
						<td>里程:</td>
						<td><input id="mile" name="mile" type="text" readonly="readonly" data-options="required:true,validType:['length[0,5]']" class="textbox easyui-validatebox" value="${bean.mile}"/>km</td>
						<td>车架号:</td>
						<td><input id="frameCode" name="frameCode" type="text" readonly="readonly" data-options="required:true,validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.frameCode}"/></td>
					</tr>
					<tr>
						<td>开票价格:</td>
						<td><input id="billPrice" name="billPrice" type="text" readonly="readonly" data-options="required:true,min:0,precision:2" class="textbox easyui-numberbox" value="${bean.billPrice}"/>元</td>
						<td>过户次数:</td>
						<td><input id="tranTimes" name="tranTimes" type="text" readonly="readonly" data-options="required:true,validType:['length[0,2]']" class="textbox easyui-validatebox" value="${bean.tranTimes}"/>次</td>
					</tr>
					<tr>
						<td>出厂日期:</td>
						<td><input id="proDate" name="proDate" type="text" readonly="readonly" data-options="required:true,editable:false" class="textbox easyui-datebox" value="${bean.proDateStr}"/></td>
						<td>购买日期:</td>
						<td><input id="buyDate" name="buyDate" type="text" readonly="readonly" data-options="required:true,editable:false" class="textbox easyui-datebox" value="${bean.buyDateStr}"/></td>
					</tr>
					<tr>
						<td>说明:</td>
						<td colspan="6">
						<textarea readonly="readonly" name="remark" class="textbox easyui-validatebox" 
								data-options="validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;">${bean.remark}</textarea></td>
					</tr>
				</table>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>车辆评估</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>评估金额:</td>
						<td><input id="evalPrice" name="evalPrice" type="text" readonly="readonly" data-options="required:true,min:0,precision:2" class="textbox easyui-numberbox" value="${bean.evalPrice}"/>元</td>
						<td>评估者:</td>
						<td><input id="evalName" name="evalName" type="text" readonly="readonly" data-options="required:true,validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.evalName}"/></td>
					</tr>
					<tr>
						<td>评估说明:</td>
						<td colspan="6"><textarea readonly="readonly" name="evalRemark" class="textbox easyui-validatebox" 
								data-options="validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;">${bean.evalRemark}</textarea></td>
					</tr>
				</table>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>资产管理</strong></div><hr color="#D3D3D3"/>
				<table id="car">
					<tr>
						<td>担保物权是否设定:</td>
						<td>
							<input name="isSet" type="radio" disabled="disabled" value="0" <c:if test="${bean.isSet eq '0' }">checked='checked'</c:if> checked='checked'/>否
							<input name="isSet" type="radio" disabled="disabled" value="1" <c:if test="${bean.isSet eq '1' }">checked='checked'</c:if>/>是
						</td>
					</tr>
					<tr>
						<td>所在仓库:</td>
						<td>
							<input id="warehousePrvn" name="warehousePrvn" type="text" readonly="readonly" 
							data-options="required:true,editable:false" class="textbox easyui-combobox" value="${bean.warehousePrvn}"/>省
						<input id="warehouseCity" name="warehouseCity" type="text" readonly="readonly" 
							data-options="required:true,editable:false" class="textbox easyui-combobox" value="${bean.warehouseCity}"/>市
							<input id="warehouseId" name="warehouseId" type="text" readonly="readonly" 
								data-options="required:true,editable:false,panelHeight:'auto',
											url:'warehouse/listWarehouse.do',
											textField:'name',
											valueField:'id'," 
								class="textbox easyui-combobox" value="${bean.warehouseId}"/>仓库
							<input id="warehouseName" name="warehouseName" type="hidden"/></td>
					</tr>
					<tr>
						<td>资产说明:</td>
						<td colspan="6"><textarea readonly="readonly" name="assetRemark" class="textbox easyui-validatebox" 
								data-options="validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;">${bean.assetRemark}</textarea></td>
					</tr>
				</table>
			</c:if>
			
			<!-- 车商 -->
			<c:if test="${type eq '02' }">
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>车商基本信息</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>车辆数量:</td>
						<td><input id="size" name="size" type="text" readonly="readonly" 
								data-options="required:true,validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.size}"/>辆</td>
					</tr>
					<tr>
						<td>所在地:</td>
						<td colspan="6">
							<input id="prvn" name="prvn" type="text" readonly="readonly" 
								data-options="required:true,editable:false" class="textbox easyui-combobox" value="${bean.prvn}"/>省
							<input id="city" name="city" type="text" readonly="readonly" 
								data-options="required:true,editable:false" class="textbox easyui-combobox" value="${bean.city}"/>市
							<input id="ctry" name="ctry" type="text" readonly="readonly" 
								data-options="required:true,editable:false" class="textbox easyui-combobox" value="${bean.ctry}"/>区/县
							<input id="addr" name="addr" type="text" readonly="readonly" style="width: 200px"
								data-options="required:true,editable:false" class="textbox" value="${bean.addr}"/>
						</td>
					</tr>
				</table>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>车辆评估</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>评估金额:</td>
						<td><input id="evalPrice" name="evalPrice" type="text" readonly="readonly" data-options="required:true,min:0,precision:2" class="textbox easyui-numberbox" value="${bean.evalPrice}"/>元</td>
						<td>评估者:</td>
						<td><input id="evalName" name="evalName" type="text" readonly="readonly" data-options="required:true,validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.evalName}"/></td>
					</tr>
					<tr>
						<td>评估说明:</td>
						<td colspan="6"><textarea readonly="readonly" name="evalRemark" class="textbox easyui-validatebox" 
								data-options="validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;">${bean.evalRemark}</textarea></td>
					</tr>
				</table>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>资产管理</strong></div><hr color="#D3D3D3"/>
				<table id="cardealer">
					<tr>
						<td>担保物权是否设定:</td>
						<td>
							<input name="isSet" type="radio" disabled="disabled" value="0" <c:if test="${bean.isSet eq '0' }">checked='checked'</c:if> checked='checked'/>否
							<input name="isSet" type="radio" disabled="disabled" value="1" <c:if test="${bean.isSet eq '1' }">checked='checked'</c:if>/>是
						</td>
					</tr>
					<tr>
						<td>所在仓库:</td>
						<td>
							<input id="warehousePrvn" name="warehousePrvn" type="text" readonly="readonly" 
								data-options="required:true,editable:false" class="textbox easyui-combobox" value="${bean.warehousePrvn}"/>省
							<input id="warehouseCity" name="warehouseCity" type="text" readonly="readonly" 
								data-options="required:true,editable:false" class="textbox easyui-combobox" value="${bean.warehousePrvn}"/>市
							<input id="warehouseId" name="warehouseId" type="text" readonly="readonly" 
									data-options="required:true,editable:false,panelHeight:'auto',
												url:'warehouse/listWarehouse.do',
												textField:'name',
												valueField:'id'," 
									class="textbox easyui-combobox" value="${bean.warehouseId}"/>仓库
							<input id="warehouseName" name="warehouseName" type="hidden"/></td>
					</tr>
					<tr>
						<td>资产说明:</td>
						<td colspan="6"><textarea readonly="readonly" name="assetRemark" class="textbox easyui-validatebox" 
								data-options="validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;">${bean.assetRemark}</textarea></td>
					</tr>
				</table>
			</c:if>
			
			<!-- 房 -->
			<c:if test="${type eq '03' }">
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>房屋基本信息</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>房产证号:</td>
						<td><input id="housePropertyCode" name="housePropertyCode" type="text" readonly="readonly" data-options="required:true,validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.housePropertyCode}"/></td>
						<td>土地证号:</td>
						<td><input id="landCode" name="landCode" type="text" readonly="readonly" data-options="required:true,validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.landCode}"/></td>
					</tr>
					<tr>
						<td>房屋面积:</td>
						<td><input id="area" name="area" type="text" readonly="readonly" data-options="required:true,validType:['length[0,10]']" class="textbox easyui-validatebox" value="${bean.area}"/></td>
						<td>房屋朝向:</td>
						<td><input id="direction" name="direction" type="text" readonly="readonly" data-options="validType:['length[0,10]']" class="textbox easyui-validatebox" value="${bean.direction}"/></td>
						<td>购买日期:</td>
						<td><input id="buyDate" name="buyDate" type="text" readonly="readonly" editable="false" class="textbox easyui-datebox" value="${bean.buyDateStr}"/></td>
					</tr>
					<tr>
						<td>所在地:</td>
						<td colspan="6">
							<input id="prvn" name="prvn" type="text" readonly="readonly" 
								data-options="required:true,editable:false" class="textbox easyui-combobox" value="${bean.prvn}"/>省
							<input id="city" name="city" type="text" readonly="readonly" 
								data-options="required:true,editable:false" class="textbox easyui-combobox" value="${bean.city}"/>市
							<input id="ctry" name="ctry" type="text" readonly="readonly" 
								data-options="required:true,editable:false" class="textbox easyui-combobox" value="${bean.ctry}"/>区/县
							<input id="addr" name="addr" type="text" readonly="readonly" style="width: 200px"
								data-options="required:true,editable:false" class="textbox" value="${bean.addr}"/>
						</td>
					</tr>
				</table>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>房屋评估</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>评估金额:</td>
						<td><input id="evalPrice" name="evalPrice" type="text" readonly="readonly" data-options="required:true,min:0,precision:2" class="textbox easyui-numberbox" value="${bean.evalPrice}"/>元</td>
						<td>评估者:</td>
						<td><input id="evalName" name="evalName" type="text" readonly="readonly" data-options="required:true,validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.evalName}"/></td>
					</tr>
					<tr>
						<td>评估说明:</td>
						<td colspan="6"><textarea readonly="readonly" name="evalRemark" class="textbox easyui-validatebox" 
								data-options="validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;">${bean.evalRemark}</textarea></td>
					</tr>
				</table>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>资产管理</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>担保物权是否设定:</td>
						<td>
							<input name="isSet" type="radio" disabled="disabled" value="0" <c:if test="${bean.isSet eq '0' }">checked='checked'</c:if> checked='checked'/>否
							<input name="isSet" type="radio" disabled="disabled" value="1" <c:if test="${bean.isSet eq '1' }">checked='checked'</c:if>/>是
						</td>
					</tr>
					<tr>
						<td>资产说明:</td>
						<td colspan="6"><textarea readonly="readonly" name="assetRemark" class="textbox easyui-validatebox" 
								data-options="validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;">${bean.assetRemark}</textarea></td>
					</tr>
				</table>
			</c:if>
			
			<!-- 红木 -->
			<c:if test="${type eq '04' }">
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>红木基本信息</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>品种:</td>
						<td><input id="var" name="var" type="text" readonly="readonly" data-options="required:true,validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.var}"/></td>
						<td>规格:</td>
						<td><input id="size" name="size" type="text" readonly="readonly" data-options="required:true,validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.size}"/></td>
					</tr>
				</table>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>红木评估</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>评估金额:</td>
						<td><input id="evalPrice" name="evalPrice" type="text" readonly="readonly" data-options="required:true,min:0,precision:2" class="textbox easyui-numberbox" value="${bean.evalPrice}"/>元</td>
						<td>评估者:</td>
						<td><input id="evalName" name="evalName" type="text" readonly="readonly" data-options="required:true,validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.evalName}"/></td>
					</tr>
					<tr>
						<td>评估说明:</td>
						<td colspan="6"><textarea readonly="readonly" name="evalRemark" class="textbox easyui-validatebox" 
								data-options="validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;">${bean.evalRemark}</textarea></td>
					</tr>
				</table>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>资产管理</strong></div><hr color="#D3D3D3"/>
				<table id="rosewood">
					<tr>
						<td>担保物权是否设定:</td>
						<td>
							<input name="isSet" type="radio" disabled="disabled" value="0" <c:if test="${bean.isSet eq '0' }">checked='checked'</c:if> checked='checked'/>否
							<input name="isSet" type="radio" disabled="disabled" value="1" <c:if test="${bean.isSet eq '1' }">checked='checked'</c:if>/>是
						</td>
					</tr>
					<tr>
						<td>所在仓库:</td>
						<td>
							<input id="warehousePrvn" name="warehousePrvn" type="text" readonly="readonly" 
								data-options="required:true,editable:false" class="textbox easyui-combobox" value="${bean.warehousePrvn}"/>省
							<input id="warehouseCity" name="warehouseCity" type="text" readonly="readonly" 
								data-options="required:true,editable:false" class="textbox easyui-combobox" value="${bean.warehousePrvn}"/>市
							<input id="warehouseId" name="warehouseId" type="text" readonly="readonly" 
									data-options="required:true,editable:false,panelHeight:'auto',
												url:'warehouse/listWarehouse.do',
												textField:'name',
												valueField:'id'," 
									class="textbox easyui-combobox" value="${bean.warehouseId}"/>仓库
							<input id="warehouseName" name="warehouseName" type="hidden"/></td>
					</tr>
					<tr>
						<td>资产说明:</td>
						<td colspan="6"><textarea readonly="readonly" name="assetRemark" class="textbox easyui-validatebox" 
								data-options="validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;">${bean.assetRemark}</textarea></td>
					</tr>
				</table>
			</c:if>
			
			<!-- 海鲜 -->
			<c:if test="${type eq '05' }">
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>海鲜基本信息</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>品种:</td>
						<td><input id="var" name="var" type="text" readonly="readonly" data-options="required:true,validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.var}"/></td>
						<td>规格:</td>
						<td><input id="size" name="size" type="text" readonly="readonly" data-options="required:true,validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.size}"/></td>
					</tr>
				</table>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>海鲜评估</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>评估金额:</td>
						<td><input id="evalPrice" name="evalPrice" type="text" readonly="readonly" data-options="required:true,min:0,precision:2" class="textbox easyui-numberbox" value="${bean.evalPrice}"/>元</td>
						<td>评估者:</td>
						<td><input id="evalName" name="evalName" type="text" readonly="readonly" data-options="required:true,validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.evalName}"/></td>
					</tr>
					<tr>
						<td>评估说明:</td>
						<td colspan="6"><textarea readonly="readonly" name="evalRemark" class="textbox easyui-validatebox" 
								data-options="validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;">${bean.evalRemark}</textarea></td>
					</tr>
				</table>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>资产管理</strong></div><hr color="#D3D3D3"/>
				<table id="seafood">
					<tr>
						<td>担保物权是否设定:</td>
						<td>
							<input name="isSet" type="radio" disabled="disabled" value="0" <c:if test="${bean.isSet eq '0' }">checked='checked'</c:if> checked='checked'/>否
							<input name="isSet" type="radio" disabled="disabled" value="1" <c:if test="${bean.isSet eq '1' }">checked='checked'</c:if>/>是
						</td>
					</tr>
					<tr>
						<td>所在仓库:</td>
						<td>
							<input id="warehousePrvn" name="warehousePrvn" type="text" readonly="readonly" 
								data-options="required:true,editable:false" class="textbox easyui-combobox" value="${bean.warehousePrvn}"/>省
							<input id="warehouseCity" name="warehouseCity" type="text" readonly="readonly" 
								data-options="required:true,editable:false" class="textbox easyui-combobox" value="${bean.warehousePrvn}"/>市
							<input id="warehouseId" name="warehouseId" type="text" readonly="readonly" 
									data-options="required:true,editable:false,panelHeight:'auto',
												url:'warehouse/listWarehouse.do',
												textField:'name',
												valueField:'id'," 
									class="textbox easyui-combobox" value="${bean.warehouseId}"/>仓库
							<input id="warehouseName" name="warehouseName" type="hidden"/></td>
					</tr>
					<tr>
						<td>资产说明:</td>
						<td colspan="6"><textarea readonly="readonly" name="assetRemark" class="textbox easyui-validatebox" 
								data-options="validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;">${bean.assetRemark}</textarea></td>
					</tr>
				</table>
			</c:if>
			
			<!-- 其他 -->
			<c:if test="${type eq '99' }">
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>基本信息</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>品种:</td>
						<td><input id="var" name="var" type="text" readonly="readonly" data-options="required:true,validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.var}"/></td>
						<td>规格:</td>
						<td><input id="size" name="size" type="text" readonly="readonly" data-options="required:true,validType:['length[0,30]']" class="textbox easyui-validatebox" value="${bean.size}"/></td>
					</tr>
				</table>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>评估</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>评估金额:</td>
						<td><input id="evalPrice" name="evalPrice" type="text" readonly="readonly" data-options="required:true,min:0,precision:2" class="textbox easyui-numberbox" value="${bean.evalPrice}"/>元</td>
						<td>评估者:</td>
						<td><input id="evalName" name="evalName" type="text" readonly="readonly" data-options="required:true,validType:['length[0,20]']" class="textbox easyui-validatebox" value="${bean.evalName}"/></td>
					</tr>
					<tr>
						<td>评估说明:</td>
						<td colspan="6"><textarea readonly="readonly" name="evalRemark" class="textbox easyui-validatebox" readonly="readonly" 
								data-options="validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;">${bean.evalRemark}</textarea></td>
					</tr>
				</table>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>资产管理</strong></div><hr color="#D3D3D3"/>
				<table id="other">
					<tr>
						<td>担保物权是否设定:</td>
						<td>
							<input name="isSet" type="radio" disabled="disabled" value="0" <c:if test="${bean.isSet eq '0' }">checked='checked'</c:if> checked='checked'/>否
							<input name="isSet" type="radio" disabled="disabled" value="1" <c:if test="${bean.isSet eq '1' }">checked='checked'</c:if>/>是
						</td>
					</tr>
					<tr>
						<td>所在仓库:</td>
						<td>
							<input id="warehousePrvn" name="warehousePrvn" type="text" readonly="readonly" 
								data-options="required:true,editable:false" class="textbox easyui-combobox" value="${bean.warehousePrvn}"/>省
							<input id="warehouseCity" name="warehouseCity" type="text" readonly="readonly" 
								data-options="required:true,editable:false" class="textbox easyui-combobox" value="${bean.warehousePrvn}"/>市
							<input id="warehouseId" name="warehouseId" type="text" readonly="readonly" 
									data-options="required:true,editable:false,panelHeight:'auto',
												url:'warehouse/listWarehouse.do',
												textField:'name',
												valueField:'id'," 
									class="textbox easyui-combobox" value="${bean.warehouseId}"/>仓库
							<input id="warehouseName" name="warehouseName" type="hidden"/></td>
					</tr>
					<tr>
						<td>资产说明:</td>
						<td colspan="6"><textarea readonly="readonly" name="assetRemark" class="textbox easyui-validatebox" 
								data-options="validType:['length[0,500]']"  readonly="readonly"
								style="resize: none;width:625px;height:50px!important;">${bean.assetRemark}</textarea></td>
					</tr>
				</table>
			</c:if>
			
				<input id="id" name="id" type="hidden" size="35" value="${ bean.id}" />
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>核价</strong></div><hr color="#D3D3D3"/>
				<table>
					<tr>
						<td>决策:</td>
						<td>
							<input id="auditPriceState" name="auditPriceState" type="text" value="${bean.auditPriceState }" 
							<c:if test="${bean.auditPriceState ne '0' }">readonly="readonly"</c:if>
							data-options="required:true,editable:false,panelHeight:'auto',
										textField:'keyValue',
										valueField:'keyProp',
										data:dataDictJson.auditPriceState,
										loadFilter:function(data){
											var comVal = $(this).combobox('getValue');
											if(comVal == '' || comVal == null) {
												$(this).combobox('setValue','0');
											}
											return data;
										}" 
							class="textbox easyui-combobox"/>
						</td>
						<td>核价金额:</td>
						<td>
							<input id="latestPrice" name="latestPrice" type="text" <c:if test="${bean.auditPriceState ne '0' }">readonly="readonly"</c:if>
								data-options="required:true,min:0,precision:2" value="${bean.latestPrice }"
								class="textbox easyui-numberbox"/>元
						</td>
					</tr>
					<tr>
						<td>说明:</td>
						<td colspan="6"><textarea name="auditPriceRemark" class="textbox easyui-validatebox" 
								<c:if test="${bean.auditPriceState ne '0' }">readonly="readonly"</c:if>
								data-options="validType:['length[0,500]']" 
								style="resize: none;width:625px;height:50px!important;">${bean.auditPriceRemark }</textarea></td>
					</tr>
				</table>
				
				<c:if test="${bean.auditPriceState eq '0' }">
					<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>操作</strong></div><hr color="#D3D3D3"/>
					<table>
						<tr>
							<td>
								<input type="button" value="保存" class="btn" onclick="javascript:updatePriceFunction('save');"/>
							</td>
						</tr>
					</table>
				</c:if>
			</form>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>核价历史</strong></div><hr color="#D3D3D3"/>
				<table id="table" class="datatable" summary="list of members in EE Studay">
					<tr>
						<th scope="col">序号</th>
						<th scope="col">操作人</th>
						<th scope="col">金额</th>
						<th scope="col">风控决策</th>
						<th scope="col">说明</th>
					</tr>
					<c:forEach items="${priceAuditList}" var="data" varStatus="status">
						<tr>
							<td style="text-align: center;">${ status.count}</td>
							<td>${data.operName}</td>
							<td>
								<fmt:formatNumber value="${data.amt}" type="currency"/>元
							</td>
							<td>
								<c:choose>
									<c:when test="${data.result eq '0'}">未处理</c:when>
									<c:when test="${data.result eq '1'}">相符</c:when>
									<c:when test="${data.result eq '2'}">不相符</c:when>
								</c:choose>
							</td>
							<td>${data.remark}</td>
						</tr>
					</c:forEach>
				</table>
				
				<div style="margin-left: 10px;margin-top: 10px; font-size: 14px;"><strong>影像</strong></div><hr color="#D3D3D3"/>
				<jsp:include page="/files/load.do">
					<jsp:param value="${loanId }" name="loId"/>
					<jsp:param value="filesce4" name="sec"/>
					<jsp:param value="${ bean.id}" name="bizKey"/>
					<jsp:param value="0" name="opt"/>
				</jsp:include>
		</div>
	</div>
</div>

<script type="text/javascript">

// 保存操作
function updatePriceFunction() {
	//去掉 input 输入的 前后空格
	$('#priceUpdateForm').find('input').each(function(){
		if($(this).attr("type")!="file"){
			$(this).val($.trim($(this).val()));
		}
	});
	
	//验证表单验证是否通过
	if(false == $('#priceUpdateForm').form('validate') ){
		$.messager.confirm('消息', "页面有必输字段，但未填值！");
		return;
	}
	
	//弹出异步加载 遮罩
	openMask();
	
	var params = $('#priceUpdateForm').serialize();
	
	//按钮失效防点击
	$(".btn").attr("disabled", true);
	$.ajax({
		type : "POST",
		url : "<%=basePath%>" + "risk/price/save.do",
		data : params,
		success : function(data) {
			//关闭遮罩，弹出消息框
			closeMask();
			if ("true"==data.success) {
				$.messager.alert('消息', data.message, 'info', function(){
					//window.parent.removePriceTab();
					var url = "<%=basePath%>risk/price/update.do?id=${bean.id}&loanId=${loanId}&type=${type}";
					window.parent.refreshPriceTab('priceQueryTabs',url);
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

//页面加载完动作
$(document).ready(function (){
	var type = '${type}';
	
	if(type == '02' || type == '03') {
		initAddr();
	}
	
	if(type == '01') {
		wareHouseAdd("#car");
	}
	if(type == '02') {
		wareHouseAdd("#cardealer");
	}
	if(type == '04') {
		wareHouseAdd("#rosewood");
	}
	if(type == '05') {
		wareHouseAdd("#seafood");
	}
	if(type == '99') {
		wareHouseAdd("#other");
	}
});

function initAddr(){
	// 所在地-省
	var provinceUrl = "sys/datadictionary/listjason.do?keyName=province";
	$("#prvn").combobox("clear");
	$('#prvn').combobox({
		url: provinceUrl,
		valueField: 'keyProp',
		textField: 'keyValue'
	});
	
	// 所在地-市
	var homePrvn = $('#prvn').combobox('getValue');
	var cityUrl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(homePrvn);
	$("#city").combobox("clear");
	$('#city').combobox({
		url: cityUrl,
		valueField: 'keyProp',
		textField: 'keyValue'
	});
	
	// 所在地-区/县
	var homeCity = $('#city').combobox('getValue');
	var countyUrl = "sys/datadictionary/listjason.do?keyName=county&parentKeyProp=" + encodeURI(homeCity);
	$("#ctry").combobox("clear");
	$('#ctry').combobox({
		url: countyUrl, 
		valueField: 'keyProp',
		textField: 'keyValue'
	});
}

function wareHouseAdd(type) {
	// 仓库所在地-省
	var provinceUrl = "sys/datadictionary/listjason.do?keyName=province";
	$(type).find("#warehousePrvn").combobox("clear");
	$(type).find('#warehousePrvn').combobox({
		url: provinceUrl,
		valueField: 'keyProp',
		textField: 'keyValue'
	});
	
	// 仓库所在地-市
	var homePrvn = $(type).find('#warehousePrvn').combobox('getValue');
	var cityUrl = "sys/datadictionary/listjason.do?keyName=city&parentKeyProp=" + encodeURI(homePrvn);
	$(type).find("#warehouseCity").combobox("clear");
	$(type).find('#warehouseCity').combobox({
		url: cityUrl,
		valueField: 'keyProp',
		textField: 'keyValue'
	});
}
</script>
</body>
</html>


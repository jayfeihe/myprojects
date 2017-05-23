<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="tab_show_infoChecky" style="display: none;" title="信息核查" ></div>

 <div class="easyui-accordion" id="infoCheckAccordion" data-options="multiple:false" style="" data-options="fit:true">
        <div title="信息查重" style="overflow:auto;padding:10px;" data-options="">
			<jsp:include page="/house/verify/repeatDetailList.do?appId=${bean.appId}"/>
		</div>
		<div title="114查号台" style="overflow:auto;padding:10px;" data-options="">
			<table id="114">
				<c:forEach items="${type2Exts}" var="contact" varStatus="status">
					<tr>
						<td align="right">
							<c:if test="${contact.key == '1'}">单位名称:</c:if>  
							<c:if test="${contact.key == '2'}">单位电话反查:</c:if>
							<c:if test="${contact.key == '3'}">单位地址反查:</c:if>
						</td>
						<td>
						<a href="http://www.baidu.com/baidu?wd=${contact.name}" target="_blank">${contact.name}</a>
							<input type="hidden" name="type2Exts[${status.index}].id" value="${contact.id}" />
							<input type="hidden" name="type2Exts[${status.index}].name" value="${contact.name}" />
							<input type="hidden" name="type2Exts[${status.index}].appId" value="${bean.appId}" />
							<input type="hidden" name="type2Exts[${status.index}].type" value="2" />
							<input type="hidden" name="type2Exts[${status.index}].key" value="${contact.key}" />
							<input type="hidden" id="state" name="type2Exts[${status.index}].state" value="1" />
						</td>
						<td>
							<input id="contact1" name="type2Exts[${status.index}].value" 
							type="radio" value="0" <c:if test="${contact.value=='0'}">checked="checked"</c:if> />无信息
							<input id="contact2" name="type2Exts[${status.index}].value" 
							type="radio" value="1" <c:if test="${contact.value=='1'}">checked="checked"</c:if>/>正常
							<input id="contact3" name="type2Exts[${status.index}].value" 
							type="radio" value="2" <c:if test="${contact.value=='2'}">checked="checked"</c:if>/>异常
<%--							<input id="remarks" name="type2Exts[${status.index}].remarks" type="text" data-options="min:0,precision:0" --%>
<%--								class="textbox easyui-validatebox" value="${contact.remarks}"/>--%>
							<textarea id="remarks" name="type2Exts[${status.index}].remarks" value="${contact.remarks}"
								class="textbox easyui-validatebox" data-options="validType:['length[0,500]']"
								style="resize: none;width:200px;height:50px!important;">${contact.remarks}</textarea>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div title="人法网" style="overflow:auto;padding:10px;" data-options="">
			<table id="rfw">
				<c:forEach items="${type3Exts}" var="contact" varStatus="status">
					<tr>
						<td align="right">
							<c:if test="${contact.key == '4'}">申请人身份证:</c:if>  
							<c:if test="${contact.key == '5'}">配偶身份证:</c:if>
							<c:if test="${contact.key == '1'}">单位名称:</c:if>
						</td>
						<td>
							<a href="http://zhixing.court.gov.cn/search/" target="_blank">${contact.name}</a>
							<input type="hidden" name="type3Exts[${status.index}].id" value="${contact.id}" />
							<input type="hidden" name="type3Exts[${status.index}].name" value="${contact.name}" />
							<input type="hidden" name="type3Exts[${status.index}].appId" value="${bean.appId}" />
							<input type="hidden" name="type3Exts[${status.index}].type" value="3" />
							<input type="hidden" name="type3Exts[${status.index}].key" value="${contact.key}" />
							<input type="hidden" id="state" name="type3Exts[${status.index}].state" value="1" />
						</td>
						<td>
							<input id="contact1" name="type3Exts[${status.index}].value" 
							type="radio" value="0" <c:if test="${contact.value=='0'}">checked="checked"</c:if> />无信息
							<input id="contact2" name="type3Exts[${status.index}].value" 
							type="radio" value="1" <c:if test="${contact.value=='1'}">checked="checked"</c:if>/>正常
							<input id="contact3" name="type3Exts[${status.index}].value" 
							type="radio" value="2" <c:if test="${contact.value=='2'}">checked="checked"</c:if>/>异常
<%--							<input id="remarks" name="type3Exts[${status.index}].remarks" type="text" data-options="min:0,precision:0" --%>
<%--								class="textbox easyui-validatebox" value="${contact.remarks}"/>--%>
							<textarea id="remarks" name="type3Exts[${status.index}].remarks" value="${contact.remarks}"
								class="textbox easyui-validatebox" data-options="validType:['length[0,500]']"
								style="resize: none;width:200px;height:50px!important;">${contact.remarks}</textarea>	
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div title="工商网" style="overflow:auto;padding:10px;" data-options="">
			<table id="gsw">
				<c:forEach items="${type4Exts}" var="contact" varStatus="status">
					<tr>
						<td align="right">
							<c:if test="${contact.key == '1'}">单位名称:</c:if>  
						</td>
						<td>${contact.name}
<%--						<a href="http://www.baidu.com/baidu?wd=${contact.name}" target="_blank">${contact.name}</a>--%>
							<input type="hidden" name="type4Exts[${status.index}].id" value="${contact.id}" />
							<input type="hidden" name="type4Exts[${status.index}].name" value="${contact.name}" />
							<input type="hidden" name="type4Exts[${status.index}].appId" value="${bean.appId}" />
							<input type="hidden" name="type4Exts[${status.index}].type" value="4" />
							<input type="hidden" name="type4Exts[${status.index}].key" value="${contact.key}" />
							<input type="hidden" id="state" name="type4Exts[${status.index}].state" value="1" />
						</td>
						<td>
							<input id="contact1" name="type4Exts[${status.index}].value" 
							type="radio" value="0" <c:if test="${contact.value=='0'}">checked="checked"</c:if> />无信息
							<input id="contact2" name="type4Exts[${status.index}].value" 
							type="radio" value="1" <c:if test="${contact.value=='1'}">checked="checked"</c:if>/>正常
							<input id="contact3" name="type4Exts[${status.index}].value" 
							type="radio" value="2" <c:if test="${contact.value=='2'}">checked="checked"</c:if>/>异常
<%--							<input id="remarks" name="type4Exts[${status.index}].remarks" type="text" data-options="min:0,precision:0" --%>
<%--								class="textbox easyui-validatebox" value="${contact.remarks}"/>--%>
							<textarea id="remarks" name="type4Exts[${status.index}].remarks" value="${contact.remarks}"
								class="textbox easyui-validatebox" data-options="validType:['length[0,500]']"
								style="resize: none;width:200px;height:50px!important;">${contact.remarks}</textarea>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div title="网查" style="overflow:auto;padding:10px;" data-options="">
			<table id="wc">
				<input type="hidden" id="type5ExtsIndex" value="${fn:length(type5Exts) }" />
				<c:forEach items="${type5Exts}" var="contact" varStatus="status">
					<tr>
						<td align="right">
							<c:if test="${contact.key == '4'}">申请人身份证:</c:if>
							<c:if test="${contact.key == '5'}">配偶身份证:</c:if>
							<c:if test="${contact.key == '10'}">配偶手机:</c:if>
							<c:if test="${contact.key == '6'}">居住地址:</c:if>  
							<c:if test="${contact.key == '1'}">单位名称:</c:if>  
							<c:if test="${contact.key == '3'}">单位地址:</c:if>  
							<c:if test="${contact.key == '7'}">本人手机:</c:if>  
							<c:if test="${contact.key == '9'}">家庭固话:</c:if>  
							<c:if test="${contact.key == '2'}">单位电话:</c:if>  
							<c:if test="${contact.key == '8'}">常用联系人:</c:if>  
						</td>
						<td>
						<c:if test="${contact.key eq '7' || contact.key eq '8' || contact.key eq '10'}">${contact.name1}</c:if>
						<a href="http://www.baidu.com/baidu?wd=<c:choose><c:when test="${contact.key == '8'}">${contact.phone }</c:when><c:otherwise>${contact.name }</c:otherwise></c:choose>" target="_blank"><c:choose><c:when test="${contact.key == '8'}">${contact.phone }</c:when><c:otherwise>${contact.name }</c:otherwise></c:choose></a>
							<input type="hidden" name="type5Exts[${status.index}].id" value="${contact.id}" />
							<input type="hidden" name="type5Exts[${status.index}].name" value="${contact.name}" />
							<input type="hidden" name="type5Exts[${status.index}].appId" value="${bean.appId}" />
							<input type="hidden" name="type5Exts[${status.index}].type" value="5" />
							<input type="hidden" name="type5Exts[${status.index}].key" value="${contact.key}" />
							<input type="hidden" id="state" name="type5Exts[${status.index}].state" value="1" />
						</td>
						<td>
							<input id="contact1" name="type5Exts[${status.index}].value" 
							type="radio" value="0" <c:if test="${contact.value=='0'}">checked="checked"</c:if> />无信息
							<input id="contact2" name="type5Exts[${status.index}].value" 
							type="radio" value="1" <c:if test="${contact.value=='1'}">checked="checked"</c:if>/>正常
							<input id="contact3" name="type5Exts[${status.index}].value" 
							type="radio" value="2" <c:if test="${contact.value=='2'}">checked="checked"</c:if>/>异常
<%--							<input id="remarks" name="type5Exts[${status.index}].remarks" type="text" data-options="min:0,precision:0" --%>
<%--								class="textbox easyui-validatebox" value="${contact.remarks}"/>--%>
							<textarea id="remarks" name="type5Exts[${status.index}].remarks" value="${contact.remarks}"
								class="textbox easyui-validatebox" data-options="validType:['length[0,500]']"
								style="resize: none;width:200px;height:50px!important;">${contact.remarks}</textarea>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
  </div>
  <table style="display: none;">
  <tr>
		<td>
			<textarea id="type5Exts_textarea" disabled="disabled">
				<tr id="type5Exts_type5ExtsIndex">
					<td align="right" id="typeText">
						
					</td>
					<td>
						<span></span>
						<a href="type5Exts_href" target="_blank"></a>
						<input type="hidden" name="type5Exts[type5ExtsIndex].id" value="${contact.id}" />
						<input type="hidden" name="type5Exts[type5ExtsIndex].name" value="${contact.name}" />
						<input type="hidden" name="type5Exts[type5ExtsIndex].appId" value="${bean.appId}" />
						<input type="hidden" name="type5Exts[type5ExtsIndex].type" value="5" />
						<input type="hidden" name="type5Exts[type5ExtsIndex].key" value="${contact.key}" />
						<input type="hidden" id="state" name="type5Exts[type5ExtsIndex].state" value="1" />
					</td>
					<td id="remark" class="22">
						<input id="contact1" name="type5Exts[type5ExtsIndex].value" 
						type="radio" value="0" <c:if test="${contact.value=='0'}">checked="checked"</c:if> />无信息
						<input id="contact2" name="type5Exts[type5ExtsIndex].value" 
						type="radio" value="1" <c:if test="${contact.value=='1'}">checked="checked"</c:if>/>正常
						<input id="contact3" name="type5Exts[type5ExtsIndex].value" 
						type="radio" value="2" <c:if test="${contact.value=='2'}">checked="checked"</c:if>/>异常
					</td>
				</tr>
			</textarea>
		</td>
	</tr>
</table>

<script type="text/javascript">

$(document).ready(function(){
	
});
 </script>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<base href="<%=basePath%>"/>
<script type="text/javascript" src="<%=basePath%>js/script.js"></script>
<input type="hidden" id="totalCount" value="${fn:length(advertiserDatas) }"/>
<%-- <section id="advertiser-gallery-wrapper">
	<c:forEach items="${advertiserDatas }" var="data">
		<div class="mask white-panel single-box" >
			<dl>
				<dt>
					<img class="thumb" alt="" src="<%=basePath%>dataRecord/imgRead?imgType=2&adpicUrl=${data.adpicUrl }" >
				</dt>
				<dd>${data.creativeName }</dd>
			</dl>
			<div class="somediv">
				<div class="tupianBox"><img alt="" src="<%=basePath%>dataRecord/imgRead?imgType=1&adpicUrl=${data.adpicUrl }" style="display: block;" id="adpic"></div>
				<div class="ziliaoBox">
					<ul class="paramlist clearfix">
						<li title="广告尺寸">
							<label for="">广告尺寸:</label>
							<span>${data.adpicWidth } * ${data.adpicHeight }</span>
						</li>
						<li title="落地页">
							<label for="">落地页:</label>
							<span><a href="${data.landingUrl }">${data.landingUrl }</a></span>
						</li>
						<li title="广告主">
							<label for="">广告主:</label>
							<span><a href="${data.advertiserUrl }">${data.advertiserUrl } - ${data.advertiserName }</a></span>
						</li>
						<li title="投放媒体">
							<label for="">投放媒体:</label>
							<span><a href="${data.mediaUrl }">${data.mediaUrl } - ${data.mediaName }</a></span>
						</li>
						<li title="投放平台">
							<label for="">投放平台:</label>
							<span>
								<c:choose>
									<c:when test="${data.terminalType eq '1' }">PC</c:when>
									<c:when test="${data.terminalType eq '2' }">APP</c:when>
									<c:when test="${data.terminalType eq '3' }">WAP</c:when>
								</c:choose>
							</span>
						</li>
						<li title="采集时间">
							<label for="">采集时间:</label>
							<span><fmt:formatDate value="${data.collectTime }" type="both"/></span>
						</li>
						<li title="确认结果">
							<label for="">确认结果:</label>
							<span>
								<c:choose>
									<c:when test="${data.level eq '1' }">严重违法<b style="color: red;">***</b></c:when>
									<c:when test="${data.level eq '2' }">一般违法<b style="color: red;">**</b></c:when>
									<c:when test="${data.level eq '3' }">轻微违法<b style="color: red;">*</b></c:when>
								</c:choose>
							</span>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</c:forEach>
</section> --%>

<div id="advertiser" class="wookmark">
 	<c:forEach items="${advertiserDatas }" var="data">
 		<div class="mask">
	       	<div class="waterfall">
	       		<div style="height: ${data.thumbHeight}px" class="image-box">
		       		<a href="<%=basePath%>advert/detail?id=${data.id }" target="_blank">
		       			<img src="<%=basePath%>dataRecord/imgRead?imgType=2&adpicUrl=${data.adpicUrl }">
		       		</a>
	       		</div>
	       		<p>${data.creativeName }</p>
	       		<p>违法程度&nbsp;&nbsp;&nbsp;
	       			<span class="star">
	       				<c:if test="${data.level eq '1' }">★★★</c:if>
		       			<c:if test="${data.level eq '2' }">★★</c:if> 
		       			<c:if test="${data.level eq '3' }">★</c:if> 
	       			</span>
	       		</p>
	       		
	       		<p>
	       			<c:if test="${data.status eq 0 }">
	       				<input type="button" class="dl_btn correctConfirmBtn" value="整改确认" info_id="${data.infoId }">
	       			</c:if>
	       			<c:if test="${data.status eq 1 }">
	       				<input type="button" class="dl_btn correctedBtn" value="已整改" info_id="${data.infoId }">
	       			</c:if>
	       		</p>
	       		
	       		<div class="somediv" >
					<div class="tupianBox"><img alt="" src="<%=basePath%>dataRecord/imgRead?imgType=1&adpicUrl=${data.adpicUrl }" style="display: block;" id="adpic"></div>
					<div class="ziliaoBox">
						<ul class="paramlist clearfix">
							<li title="广告尺寸">
								<label for="">广告尺寸:</label>
								<span>${data.adpicWidth } * ${data.adpicHeight }</span>
							</li>
							<li title="落地页">
								<label for="">落地页:</label>
								<span><a target="_blank" href="${data.landingUrl }">${data.landingUrl }</a></span>
							</li>
							<li title="广告主">
								<label for="">广告主:</label>
								<span><a target="_blank" href="${data.advertiserUrl }">${data.advertiserUrl } - ${data.advertiserName }</a></span>
							</li>
							<li title="投放媒体">
								<label for="">投放媒体:</label>
								<span><a target="_blank" href="${data.mediaUrl }">${data.mediaUrl } - ${data.mediaName }</a></span>
							</li>
							<li title="投放平台">
								<label for="">投放平台:</label>
								<span>
									<c:choose>
										<c:when test="${data.terminalType eq '1' }">PC</c:when>
										<c:when test="${data.terminalType eq '2' }">APP</c:when>
										<c:when test="${data.terminalType eq '3' }">WAP</c:when>
									</c:choose>
								</span>
							</li>
							<li title="采集时间">
								<label for="">采集时间:</label>
								<span><fmt:formatDate value="${data.collectTime }" type="both"/></span>
							</li>
							<li title="确认时间">
								<label for="">确认时间:</label>
								<span><fmt:formatDate value="${data.confirmTime }" type="both"/></span>
							</li>
							<li title="确认结果">
								<label for="">确认结果:</label>
								<span>
									<c:choose>
										<c:when test="${data.level eq '1' }">严重违法<b class="star">★★★</b></c:when>
										<c:when test="${data.level eq '2' }">一般违法<b class="star">★★</b></c:when>
										<c:when test="${data.level eq '3' }">轻微违法<b class="star">★</b></c:when>
									</c:choose>
								</span>
							</li>
						</ul>
					</div>
				</div>
	       	</div>
	    </div>
   	</c:forEach>
   	<div style="clear:both"></div>
 </div>
<script type="text/javascript" src="<%=basePath %>js/waterfall.js"></script>
<script type="text/javascript">
	waterfall.init({
		imageParent:'advertiser',
		container:'adverterIllegal',
		imageCount:$("#totalCount").val()
	});
</script>
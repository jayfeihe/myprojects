package com.hikootech.mqcash.filter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.po.UserInfo;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.UserUtils;

public class SessionFilter implements Filter {
	
	private static Logger log = LoggerFactory.getLogger(SessionFilter.class);
	
	private FilterConfig config;
	
	private String indexPageUrl;
	
	private Set<String> checks;
	
	private Set<String> ignores;
	
	String[] cs={"|","&",";","$","%","@","'","\"","\\\'","\\\"","<",">","(",")","+","CR","LF",",","\\"};
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		
		String uri = request.getRequestURI();
		String ajaxHeader = request.getHeader("X-Requested-With");
		String myhosts = request.getHeader("host"); 
		log.info("uri : " + uri+",host="+myhosts);

		//登录页过滤非法参数
		if(uri.contains("/passwdport/toLogin.jhtml") && filter(request)){
			response.sendRedirect(indexPageUrl);
			return;
		}
			
				
		HttpSession session = request.getSession();
		UserInfo userInfo = UserUtils.getUserInfoFromCache(session);
		
//		response.setHeader("Cache-Control", "no-cache");
//		response.setHeader("Cache-Control", "no-store");
//		response.setHeader("Pragrma","no-cache");
//		response.setDateHeader("Expires",0);
		
		if(check(uri)){
			log.debug("无需登录就可以访问的页面");
			arg2.doFilter(arg0, arg1);
			return;
		}else{
			if(userInfo != null){
				//如果用户未修改初始密码，则强制修改初始密码
				if (EntityUtils.isNotEmpty(userInfo.getPwdModSts()) && userInfo.getPwdModSts() == 0
						&& !uri.contains("/updateUserInfo/updateInitPwd.jhtml") 
						&& !uri.contains("/updateUserInfo/updateInitPwdByPwd.jhtml")
						&& !uri.contains("/updateUserInfo/validateCode.jhtml")
						&& !uri.contains("/updateUserInfo/updateInitPwdSuccess.jhtml")
						&& !uri.contains("/instalmentManage/instalmentInitBill.jhtml")) {
						
					log.info("用户未修改初始密码，则跳转修改初始密码页面");
					response.sendRedirect("/updateUserInfo/updateInitPwd.jhtml");
					return;
				}
				arg2.doFilter(arg0, arg1);
				return;
			}
			if(EntityUtils.isNotEmpty(ajaxHeader) && "XMLHttpRequest".equalsIgnoreCase(ajaxHeader)){
				response.setCharacterEncoding("UTF-8");  
			    response.setContentType("application/json; charset=utf-8");
			    Map<String, Object> retMap=new HashMap<String, Object>();
				retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.UNVALID);
				retMap.put(ResponseConstants.RETURN_DESC, "登录超时，请重新登录！");
				System.out.println(JSONObject.fromObject(retMap).toString());
				response.getWriter().write(JSONObject.fromObject(retMap).toString());
				response.getWriter().close();
				return;
			}else{
				log.info("没有登录，跳转到首页");
				String redirectUrl = getRedirectUrl(indexPageUrl, 
						"redirectUrl=" + URLEncoder.encode(uri, "utf-8"));
				
				response.sendRedirect(redirectUrl);
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		config = arg0;
		indexPageUrl = config.getInitParameter("indexPageUrl");
		if(indexPageUrl == null || "".equals(indexPageUrl)){
			throw new ServletException("SessionFilter initialization is failed, because parameter of indexPageUrl is null");
		}
		
		checks = getFilterSet("sessionfilter.xml","checks");
		ignores = getFilterSet("sessionfilter.xml","ignores");
	}
	
	/**检查uri是否需要登陆 （默认返回false，即使uri在配置文件中没有配置，最终也会返回false）
	 * ignores匹配返回true
	 * checks匹配返回false
	 * @param uri
	 * @return
	 */
	public boolean check(String uri){
		for(String str :checks){
			if (uri.equals(str.toString())) {
				return false;
			}
		}
		
		for(String str :ignores){
			if (uri.equals(str.toString())) {
				return true;
			}
		}
		
		return false;
	}
	
	public static Set<String> getFilterSet(String xmlName, String filtType) {

		Set<String> set = new HashSet<String>();

		InputStream in = SessionFilter.class.getClassLoader().getResourceAsStream(xmlName);
		SAXReader saxReader = new SAXReader();
		Document document;
		try {
			document = saxReader.read(in);
			// 获取根元素
			Element rootElement = document.getRootElement();
			// 根元素下的子节点
			List<Element> list = rootElement.elements();
			for (Element node : list) {
				// 得到没一个二级节点下的子节点
				List<Element> list2 = node.elements();
				for (Element node2 : list2) {
					if (filtType.equals(node.getName())) {
						set.add(node2.getText());
					}
				}
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return set;
	}
	
	private String getRedirectUrl(String mainPage, String param){
		if(mainPage.contains("?")){
			mainPage += param;
		}else{
			mainPage += "?" + param;
		}
		return mainPage;
	}
	
    private boolean filter(HttpServletRequest request) {
    	Map parameters = request.getParameterMap();
		if (parameters != null && parameters.size() > 0) {
			for (Iterator iter = parameters.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				String[] values = (String[]) parameters.get(key);
				for (int i = 0; i < values.length; i++) {
					if(has(values[i])){
						return true;
					}
				}
			}
		}
		return false;
    }
    private boolean has(String str){
    	for(int i=0;i<cs.length;i++){
    		if(str.indexOf(cs[i])!=-1){
    			return true;
    		}
    	}
    	return false;
    }
}

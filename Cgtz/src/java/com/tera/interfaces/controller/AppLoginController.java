package com.tera.interfaces.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.interfaces.model.AppLoginBean;
import com.tera.interfaces.util.GsonUtils;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Role;
import com.tera.sys.model.SysLog;
import com.tera.sys.model.User;
import com.tera.sys.model.UserExt;
import com.tera.sys.service.LogInOutService;
import com.tera.sys.service.RoleService;
import com.tera.sys.service.SysLogService;
import com.tera.sys.service.UserExtService;
import com.tera.util.NetUtils;

/** App登录Controller
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/inter/app")
public class AppLoginController {

	private static final Logger log = Logger.getLogger(AppLoginController.class);
	@Autowired
	private LogInOutService logInOutService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private UserExtService userExtService;
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/login.do")
	public void loginApp(String loginId,String password,HttpServletRequest request,HttpServletResponse response) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		log.info("请求参数：[loginId<--------------->"+loginId+"],[password<--------------->"+password+"]");
		
		PrintWriter writer = null;
		try {
			response.setContentType("application/json;charset=UTF-8");
			writer = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		User user = logInOutService.checkUser(loginId, password);
		if (user != null) {
			if (user.getIsAdmin()==1) {
				writer.print(GsonUtils.getInstance().toJson(new JsonMsg(false,"管理员不能登录")));
				return;
			}
			try {
				String orgId="";
				String roleName="";
				
				UserExt ext = userExtService.queryByKey(loginId);
				if (ext != null) {
					Role role = roleService.getRoleById(ext.getRoleId());
					orgId = ext.getOrgId();
					roleName = role.getName();
				}
				
				// json Bean
				AppLoginBean bean = new AppLoginBean();
				bean.setLoginId(loginId);
				bean.setRole(roleName);
				
				String jsonStr = GsonUtils.getInstance().toJson(bean);
				log.info("== 响应报文："+ jsonStr +" ==");
				
				writer.print(jsonStr);
				log.info(loginId+",APP客户端登录成功!");
				
				request.getSession().setAttribute(SysConstants.LOGIN_ID, loginId);
				
				String ipAddress = NetUtils.getIpAddr(request);
				this.sysLogService.addSysLog(new SysLog(ipAddress, user, orgId, "App客户端登录"));
				
			} catch (Exception e) {
				writer.print(GsonUtils.getInstance().toJson(new JsonMsg(false,"接口服务调用失败")));
				e.printStackTrace();
			}
		} else {
			writer.print(GsonUtils.getInstance().toJson(new JsonMsg(false,"用户名密码错误")));
		}
		
		log.info(thisMethodName+":end");
	}
}

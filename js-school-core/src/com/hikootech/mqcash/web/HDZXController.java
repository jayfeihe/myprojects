package com.hikootech.mqcash.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hikootech.mqcash.service.ConfigCreditKvService;
import com.hikootech.mqcash.service.HaoDaiService;
import com.hikootech.mqcash.util.UUIDTools;
import com.hikootech.mqcash.vo.HaoDaiVo;
import com.hikootech.mqcash.vo.UserHdsxbzxRecord;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RequestMapping("/hdzx")
@Controller
public class HDZXController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(HDZXController.class);
	@Autowired
	private HaoDaiService haoDaiService;
	@Autowired
	private ConfigCreditKvService configCreditKvService;

	/**
	 * @Title getRiskData @Description TODO(征信接口调用好贷接口) @param 参数列表 @param
	 *        request @param response @param UserHdsxbzxRecord @param haoDaiVo
	 *        设定文件 @return void 返回类型 @throws
	 */
	@RequestMapping("/queryHdInfo.do")
	public void getRiskDataByCredit(HttpServletRequest request, HttpServletResponse response,
			UserHdsxbzxRecord UserHdsxbzxRecord, HaoDaiVo haoDaiVo) {

		Map<String, String> map = new HashMap<String, String>();
		// 从memcached中取出数据
		Map<String, String> meMap = null;
		try {
			meMap = configCreditKvService.getAllCreditKv();
		} catch (Exception e3) {
			throw new RuntimeException("调用好贷征信接口，获取征信配置数据异常");
		}

		if (meMap == null) {
			throw new RuntimeException("调用好贷征信接口，获取征信配置数据为空");
		}

		String hdrequestId = UUIDTools.getFormatUUID();
		String userName = request.getParameter("userName");
		String userIdCard = request.getParameter("userIdCard");
		String mobileNumber = request.getParameter("mobileNumber");
		String totalId = request.getParameter("totalInfoId");
		String queryType = "700";

		log.info("MQ征信调用(HAODAI)-》(HAODAI)接收到征信请求：userName-》" + userName + ",userIdCard->" + userIdCard
				+ ",mobileNumber->" + mobileNumber + ",totalId-》" + totalId);

		map.put("hdrequestId", hdrequestId);
		map.put("userName", userName);
		map.put("userIdCard", userIdCard);
		map.put("totalId", totalId);
		map.put("mobileNumber", mobileNumber);
		map.put("queryType", queryType);

		try {
			response.getWriter().write(hdrequestId);
			response.getWriter().close();
		} catch (Exception e) {
			log.error("将本次调用id返回调用者时发生错误", e);
		}

		boolean creditStatus = false;
		String result = null;
		try {

			result = haoDaiService.requestHaoDai(map, meMap);
		} catch (Exception e) {
			log.error("请求好贷网时，发生错误", e);
			return;
		}

		try {
			// 第二步 =============》处理接受数据
			creditStatus = haoDaiService.queryIsHdCredit(result, totalId, hdrequestId, meMap);
			// haoDaiService.modifyThirdPartCreditStatus(totalId, creditStatus);
		} catch (Exception e) {
			log.error("根据好贷网返回的结果数据,判断征信状态且入库时，发生错误", e);
			return;
		}

		try {
			// 第三步 =============》修改第三方征信结果
			haoDaiService.modifyCreditRecord(totalId, creditStatus);
		} catch (Exception e) {
			log.error("根据好贷网最终征信结果：" + creditStatus + ",修改totalId：" + totalId + "的数据状态时，发生错误", e);
			return;
		}

		log.info("MQ征信调用(HAODAI)-》征信请求成功完成");

	}

	/**
	 * @Title getRiskData @Description TODO(供外部调用) @param 参数列表 @param
	 *        request @param response @param UserHdsxbzxRecord @param haoDaiVo
	 *        设定文件 @return void 返回类型 @throws
	 */
	@RequestMapping("/queryInfo.do")
	public void getRiskData(HttpServletRequest request, HttpServletResponse response,
			UserHdsxbzxRecord UserHdsxbzxRecord, HaoDaiVo haoDaiVo) {

		Map<String, String> map = new HashMap<String, String>();

		// 从memcached中取出数据
		Map<String, String> meMap = null;
		try {
			meMap = configCreditKvService.getAllCreditKv();
		} catch (Exception e3) {
			throw new RuntimeException("调用好贷征信接口，获取征信配置数据异常");
		}

		if (meMap == null) {
			throw new RuntimeException("调用好贷征信接口，获取征信配置数据为空");
		}

		String hdrequestId = UUIDTools.getFormatUUID();
		String userName = request.getParameter("userName");
		String userIdCard = request.getParameter("userIdCard");
		String mobileNumber = request.getParameter("mobileNumber");
		String totalId = request.getParameter("totalInfoId");
		String queryType = "700";

		log.info("外部其他应用调用(HAODAI)-》(HAODAI)接收到征信请求：userName-》" + userName + ",userIdCard->" + userIdCard
				+ ",mobileNumber->" + mobileNumber + ",totalId-》" + totalId);
		map.put("hdrequestId", hdrequestId);
		map.put("userName", userName);
		map.put("userIdCard", userIdCard);
		map.put("totalId", totalId);
		map.put("mobileNumber", mobileNumber);
		map.put("queryType", queryType);

		// 反给调用者本次调用分配的id
		try {
			response.getWriter().write(hdrequestId);
			response.getWriter().flush();
			response.getWriter().close();

		} catch (Exception e) {
			log.error("外部其他应用调用(HAODAI)-》将本次生成id返回调用者时发生错误", e);
		}
		try {
			haoDaiService.requestHaoDai(map, meMap);

		} catch (Exception e) {
			log.error("外部其他应用调用(HAODAI)-》调用hd征信时，发生错误", e);
			return;
		}

		log.info("外部其他应用调用(HAODAI)-》征信请求成功完成");

	}

	public static void main(String[] args) {
		String[] ss = new String[] { "0", "2", "3" };
		JSONObject jo = new JSONObject();
		jo.put("ss", JSONArray.fromObject(ss));
		System.out.println(jo.toString());
	}
}

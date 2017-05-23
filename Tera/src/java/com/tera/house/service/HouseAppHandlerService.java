package com.tera.house.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tera.bpm.model.BpmLog;
import com.tera.bpm.service.BpmLogService;
import com.tera.house.model.HouseApp;
import com.tera.img.model.Img;
import com.tera.img.service.ImgService;
import com.tera.sys.model.User;
import com.tera.sys.service.UserService;

@Service("houseAppHandlerService")
public class HouseAppHandlerService {

	@Autowired(required=false)
	private ImgService imgService;
	@Autowired(required=false)
	private UserService userService;
	@Autowired(required=false)
	private BpmLogService bpmLogService;
	
	/**
	 * 精英贷测试生成评级提醒
	 * @param houseApp
	 * @return
	 * @throws Exception
	 */
	public String getGradeRemind(HouseApp houseApp) throws Exception {
		// 判断性别
		String idNo = houseApp.getIdNo();
		int genderNo = Integer.parseInt(idNo.substring(idNo.length() - 2,
				idNo.length() - 1));
		String gender = "";
		if (genderNo % 2 == 0) {
			gender = "女性";
		}
		StringBuilder gradeRemind = new StringBuilder(gender);

		// 判断是否有学历证明
		Map<String, Object> imgMap = new HashMap<String, Object>();
		imgMap.put("appId", houseApp.getAppId());
		imgMap.put("category", "H");
		List<Img> educationList = imgService.queryList(imgMap);
		if (educationList != null && educationList.size() > 0) {
			if (gender.equals("女性")) {
				gradeRemind.append("、提供学历证明");
			} else {
				gradeRemind.append("提供学历证明");
			}
		}
		return gradeRemind.toString();
	}
	
	/**
	 * 根据bizKey获取上一节点处理人
	 * @author QYANZE
	 * 
	 * @param bizKey
	 * @return
	 */
	public String getPrevProcesser(String bizKey) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("bizKey", bizKey);
		List<BpmLog> logs = bpmLogService.getBpmLogNoDesc(map);
		if(logs != null && logs.size() > 0) 
			return logs.get(logs.size()-2).getProcesser();
		return "";
	}
	
	/**
	 * 随机生成处理人
	 * 
	 * @author QYANZE
	 * 
	 * @param roleName
	 * @param orgId
	 * @return
	 */
	public String generateRandProcesser(String roleName,String orgId) {
		String processer = "";
		List<User> users = userService.getUserByOrgAndRoleAndDepart(orgId,new String[]{roleName},null);
		Collections.shuffle(users);
		if (users != null && users.size() > 0) {
			User user = users.get(new Random().nextInt(users.size()));
			processer = user.getLoginId();
		}
		return processer;
	}
}

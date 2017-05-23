package com.tera.message.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.message.constant.MessageConstant;
import com.tera.message.dao.MsglogDao;
import com.tera.message.model.Msglog;
import com.tera.message.model.MsgTemplate;
import com.tera.util.DateUtils;

/**
 * 
 * 短信日志表服务类 <b>功能：</b>MsglogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-30 15:29:17<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("msglogService")
public class MsglogService {

	private final static Logger log = Logger.getLogger(MsglogService.class);

	@Autowired(required = false)
	private MsglogDao dao;

	@Autowired(required = false)// 自动注入
	private MsgTemplateService templateService;

	@Transactional
	public void add(Msglog... objs) throws Exception {
		if (objs == null || objs.length < 1) {
			return;
		}
		for (Msglog obj : objs) {
			dao.add(obj);
		}
	}

	@Transactional
	public void update(Msglog obj) throws Exception {
		dao.update(obj);
	}

	@Transactional
	public void updateOnlyChanged(Msglog obj) throws Exception {
		dao.updateOnlyChanged(obj);
	}

	@Transactional
	public void delete(Object... ids) throws Exception {
		if (ids == null || ids.length < 1) {
			return;
		}
		for (Object id : ids) {
			dao.delete(id);
		}
	}

	public int queryCount(Map<String, Object> map) throws Exception {
		return dao.queryCount(map);
	}

	public List<Msglog> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public Msglog queryByKey(Object id) throws Exception {
		return (Msglog) dao.queryByKey(id);
	}

	/**
	 * 查询并发送短信，参数为空则查询所有待发送短信并发送
	 * @param map 
	 * @throws Exception
	 */
	public void queryAndSendMessage(Map<String, Object> map) throws Exception {
		if (map == null || map.isEmpty()) {
			map = new HashMap<String, Object>();
			map.put("sendState", MessageConstant.SENDSTATE_WAIT);
		}
		List<Msglog> list = queryList(map);
		sendMessageOneByOne(list);
	}

	/**
	 * 逐条发送短信
	 * @param list
	 *            待发送短信对象集合
	 * @throws Exception
	 */
	@Transactional
	public void sendMessageOneByOne(List<Msglog> list) throws Exception {
		if (list != null && !list.isEmpty()) {
			// 模板集合
			Map<Integer, MsgTemplate> map = templateService.getTemplateMap();
			String resultStr = "";
			for (Msglog msglog : list) {
				resultStr = sendMessage(msglog.getMsgContent(), msglog
						.getMobileTel(), map.get(msglog.getTemplateId())
						.getRemindTime().getTime() / 1000);
				if(resultStr.indexOf("success")!=-1){
					//更新发送状态
					msglog.setMsgId(resultStr.split(":")[1]);
					msglog.setSendState(MessageConstant.SENDSTATE_COMPLETE);
					msglog.setSendTime(new Timestamp(System.currentTimeMillis()));
					updateOnlyChanged(msglog);
				} else {
					log.info("短信(ID："+msglog.getId()+")推送短信平台失败！");
				}
			}
		}
	}

	/**
	 * 从短信平台获取状态报告
	 * @throws Exception
	 */
	@Transactional
	public void renewMessageReceiveState() throws Exception {
		// 查询结果字符串
		String resultStr = "";
		Map<String, Object> map = new HashMap<String, Object>();
		// 状态报告数据 集合
		String[] resultStrs = null;
		// 状态报告数据    msgid,手机号,状态,时间
		String[] result = null;
		// 查询短信对象集合
		List<Msglog> list = null;
		Msglog msglog = null;
		while(true){
			//调用短信平台接口 查询状态报告
			resultStr = sendAction(MessageConstant.SEND_TYPE_RECV, "");
//			resultStr = "14355711130538,15588889999,DELIVRD,2015-07-01 14:30:36;14357320348536,15588889999,DELIVRD,2015-07-01 14:30:36;";
			// 查询到的状态报告是否是记录数据
			if(!MessageConstant.SEND_TYPE_RECV_RESULT.equals(resultStr)){
				resultStrs = resultStr.split(";");
				for (String string : resultStrs) {
					result = string.split(",");
					map.clear();
					map.put("msgId", result[0]);
					map.put("mobileTel", result[1]);
					list = queryList(map);
					if(list!=null&&!list.isEmpty()){
						msglog = list.get(0);
						msglog.setReceiveState(result[2]);
						msglog.setReceiveTime(new Timestamp(DateUtils.getDate(result[3], DateUtils.DEFAULT_TIME_PATTERN).getTime()));
						// 更新 接收状态和接收时间
						updateOnlyChanged(msglog);
					}
				}
				// 短信平台每次返回数据条数最大是固定的，
				//实际条数小于最大值，则说明目前所有状态报告都获取到了
				if(resultStrs.length<MessageConstant.RECV_COUNT_MAX){
					break;
				}
			}else{//没有记录，则跳出
				break;
			}
		}
	}

	/**
	 * 发送短信
	 * @param content 短信内容
	 * @param mobileList 手机号集合
	 * @param startTime 指定发送时间
	 * @return
	 * @throws IOException
	 */
	private String sendMessage(String content, List<String> mobileList, long startTime) throws IOException {

		// 创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer();

		// 向StringBuffer追加手机号码
		String mobiles = "";
		for (int i = 0; i < mobileList.size(); i++) {
			if (i < mobileList.size() - 1) {
				mobiles += mobileList.get(i) + ",";
			} else {
				mobiles += mobileList.get(i);
			}
		}
		sb.append("&mobile=" + mobiles);

		// 向StringBuffer追加消息内容转URL标准码
		sb.append("&content=" + URLEncoder.encode(content, "GBK"));

		// 指定发送时间
		if (startTime > 0) {
			sb.append("&startTime=" + startTime);
		}

		// 返回发送结果
		String inputline = sendAction(MessageConstant.SEND_TYPE_SEND, sb.toString());

		return inputline;
	}
	/**
	 * 发送短信
	 * @param content 短信内容
	 * @param mobileNO 手机号
	 * @param startTime 指定发送时间
	 * @return
	 * @throws IOException
	 */
	private String sendMessage(String content, String mobileNO, long startTime) throws IOException {

		// 创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer();

		// 向StringBuffer追加手机号码
		sb.append("&mobile=" + mobileNO);

		// 向StringBuffer追加消息内容转URL标准码
		sb.append("&content=" + URLEncoder.encode(content, "GBK"));

		// 指定发送时间
		if (startTime > 0) {
			sb.append("&startTime=" + startTime);
		}

		// 返回发送结果
		String inputline = sendAction(MessageConstant.SEND_TYPE_SEND, sb
				.toString());

		return inputline;
	}

	/**
	 * @param type
	 *            调用类型 send:发送信息
	 *            参数：mobile(手机号，多个以,分隔)、content(短信内容)、startTime(指定发送时间)；
	 *            query：查询发送记录 参数：action(固定值record)、from(开始时间 9位时间戳)、to(结束时间
	 *            9位时间戳)； recv：发送状态报告 参数 无
	 * @param parameterStr
	 *            参数字符串 以&开始，如 &name=ss&startTime=12334312
	 * @return
	 * @throws IOException
	 */
	private String sendAction(String type, String parameterStr)
			throws IOException {
		
		// 创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer("http://m.5c.com.cn/api/" + type
				+ "/index.php?");

		// APIKEY
		sb.append("apikey=");
		sb.append(MessageConstant.APIKEY);

		// 用户名
		sb.append("&username=");
		sb.append(MessageConstant.USERNAME);

		// 向StringBuffer追加密码
		sb.append("&password=");
		sb.append(MessageConstant.PASSWORD);

		// 参数串
		sb.append(parameterStr);
		
		// 创建url对象
		URL url = new URL(sb.toString());

		// 打开url连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// 设置url请求方式 ‘get’ 或者 ‘post’
		connection.setRequestMethod("POST");

		// 发送
		BufferedReader in = new BufferedReader(new InputStreamReader(url
				.openStream()));

		// 返回发送结果
		String inputline = in.readLine();

		return inputline;
	}
}

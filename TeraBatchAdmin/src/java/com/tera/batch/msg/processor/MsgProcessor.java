package com.tera.batch.msg.processor;

import org.springframework.batch.item.ItemProcessor;

import com.tera.message.service.MsglogService;

public class MsgProcessor implements ItemProcessor<Integer, String> {
	private MsglogService msglogService;
	
	 
	@Override
    public String process(Integer lm) throws Exception {
		msglogService.renewMessageReceiveState();
		return null;
	}


	public MsglogService getMsglogService() {
		return msglogService;
	}


	public void setMsglogService(MsglogService msglogService) {
		this.msglogService = msglogService;
	}
	
	
}

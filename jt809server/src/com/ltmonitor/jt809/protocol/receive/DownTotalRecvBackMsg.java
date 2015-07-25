package com.ltmonitor.jt809.protocol.receive;

import java.util.Date;

import org.apache.log4j.Logger;

import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.protocol.IReceiveProtocol;


/**
 * ���ճ�����λ��Ϣ����֪ͨ 9101������ҪӦ��,�������
 * 
 * @author Administrator
 * 
 */
public class DownTotalRecvBackMsg implements IReceiveProtocol {
	private Logger logger = Logger.getLogger(DownTotalRecvBackMsg.class);

	public String handle(JT809Message message) {

		this.logger.info("ResolveHandle start");

		String body = message.getMessageBody();

		MessageParser mp = new MessageParser(body);
		int dynamicInfoTotal = mp.getInt(4);
		Date start = mp.getUtcDate();
		Date end = mp.getUtcDate();

		message.setMsgDescr("�յ���λ��Ϣ����:" + dynamicInfoTotal
				+ ",ʱ���:" + start + " �� " + end);
		return null;
	}
}

package com.ltmonitor.jt809.protocol.receive;

import org.apache.log4j.Logger;

import com.ltmonitor.jt809.app.T809Manager;
import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.protocol.IReceiveProtocol;

/**
 * ����·�������� 0x9001
 * @author tianfei
 *
 */
public class DownConnectReq implements IReceiveProtocol {
	private Logger logger = Logger.getLogger(DownConnectReq.class);

	public String handle(JT809Message message) {
		String strResult = message.getMessageBody();
		MessageParser mp = new MessageParser(strResult);
		int result = mp.getInt(4);
		// int result = Integer.parseInt(strResult.substring(0, 8));
		message.setMsgDescr("����·���ӳɹ�,У����:" + result);
		this.logger.warn("����·���ӳɹ�,У����:" + result);
		try {
			Thread.sleep(500);
			T809Manager.setSubLinkState(true, "���ӳɹ�");
			
			// ����·����Ӧ����Ϣ
			T809Manager.DownConnectRsp();
		} catch (InterruptedException e) {
		}
		
		return null;
	}
}

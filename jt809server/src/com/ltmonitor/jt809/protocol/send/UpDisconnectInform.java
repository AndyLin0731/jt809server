package com.ltmonitor.jt809.protocol.send;

import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.protocol.ISendProtocol;
/**
 * ����·�Ͽ�֪ͨ��Ϣ
 * @author DELL
 *
 */
public class UpDisconnectInform implements ISendProtocol {
	private int msgType = 0x1007;
	
	public UpDisconnectInform()
	{
		
	}

	public JT809Message wrapper() {
		String dataBody = null;

		String error_code = "00";
		String mess = "";
		dataBody = error_code;

		return new JT809Message(msgType, dataBody);
	}
}

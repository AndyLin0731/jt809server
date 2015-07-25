package com.ltmonitor.jt809.protocol.receive;

import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.protocol.IReceiveProtocol;


/**
 * ������λ��Ϣ��Ӧ��9209
 * @author Administrator
 *
 */
public class DownExgMsgApplyHisGnssDataAck implements IReceiveProtocol {
	public String handle(JT809Message message) {

		String dataBody = message.getMessageBody();
		// ������ͷ������28.
		int result = Integer.parseInt(dataBody.substring(56, 58));

		String descr = "";
		if (result == 0)
			descr = "�ɹ�,�ϼ�ƽ̨��������";
		else if (result == 1)
			descr = "�ɹ�,�ϼ�ƽ̨�������";
		else if (result == 2)
			descr = "ʧ��,�ϼ�ƽ̨�޶�Ӧ����Ķ�λ����";
		else if (result == 3)
			descr = "ʧ�ܣ��������ݲ���ȷ";
		else if (result == 4)
			descr = "ʧ�ܣ�����ԭ��";

		message.setMsgDescr(descr);
		return null;
	}
}

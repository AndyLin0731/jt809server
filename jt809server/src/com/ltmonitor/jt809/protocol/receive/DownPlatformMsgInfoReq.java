package com.ltmonitor.jt809.protocol.receive;

import org.apache.log4j.Logger;
import com.ltmonitor.jt809.app.T809Manager;
import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.protocol.IReceiveProtocol;

/**
 * ƽ̨�䱨��������Ϣ
 * 
 * @author DELL
 * 
 */
public class DownPlatformMsgInfoReq implements IReceiveProtocol {
	Logger logger = Logger.getLogger(DownPlatformMsgInfoReq.class);

	public String handle(JT809Message message) {
		String msgBody = message.getMessageBody();

		MessageParser mp = new MessageParser(msgBody);

		int dataType = mp.getInt(2);
		int dataLength = mp.getInt(4);
		int objType = mp.getInt(1); // ��ڶ�������
		String objId = mp.getString(12); // ��ڶ���ID
		int infoId = mp.getInt(4); // ��ϢID
		int infoLength = mp.getInt(4); // ��Ϣ���ݳ���

		String content = mp.getString(infoLength);

		StringBuilder sb = new StringBuilder();
		sb.append(objType).append(';').append(objId).append(';').append(infoId)
				.append(';').append(content);
		message.setMsgDescr(sb.toString());
			String objTypeName = "";
		switch (objType) {
		case 0:
			objTypeName = " �¼�ƽ̨������һƽ̨";
			break;
		case 1:
			objTypeName = " ��ǰ���ӵ��¼�ƽ̨";
			break;
		case 2:
			objTypeName = " �¼�ƽ̨������һҵ��";
			break;
		case 3:
			objTypeName = " �¼�ƽ̨��������ҵ��";
			break;
		case 4:
			objTypeName = "�¼�ƽ̨��������ƽ̨";
			break;
		case 5:
			objTypeName = "�¼�ƽ̨��������ƽ̨��ҵ��";
			break;
		case 6:
			objTypeName = " �¼�ƽ̨���������������ƽ̨������ضˣ�";
			break;
		case 7:
			objTypeName = "�¼�ƽ̨����������ҵ���ƽ̨ ";
			break;
		case 8:
			objTypeName = "�¼�ƽ̨�������о�Ӫ����ҵ���ƽ̨ ";
			break;
		case 9:
			objTypeName = "�¼�ƽ̨�������зǾ�Ӫ����ҵ���ƽ̨ ";
		}

		T809Manager.UpPlatFormMsgInfoAck(infoId);
		message.setMsgDescr("��ϢID��" + infoId + "," + objTypeName
				+ "�·�ƽ̨�䱨��Ӧ����Ϣ");

		/**
		 * if (updateInfoState(content)) { this.logger.info("���±���������Ϣ״̬"); }
		 */
		//this.logger.warn(objTypeName + "�·�ƽ̨�䱨��Ӧ����Ϣ");

		return "";
	}

}

package com.ltmonitor.jt809.protocol.receive;

import org.apache.log4j.Logger;
import com.ltmonitor.entity.TerminalCommand;
import com.ltmonitor.jt809.app.ServiceLauncher;
import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.protocol.IReceiveProtocol;
import com.ltmonitor.service.JT808Constants;

/**
 * �����������������Ϣ 9501
 * 
 * @author DELL
 * 
 */
public class DownCtrlMsgMonitorVehicleReq implements IReceiveProtocol {
	private static Logger logger = Logger
			.getLogger(DownCtrlMsgMonitorVehicleReq.class);

	public String handle(JT809Message message) {
		String dataBody = message.getMessageBody();
		MessageParser mp = new MessageParser(dataBody, 28);

		// �����绰����
		String monitorPhone = mp.getString(message.getContentLength());
		message.setMsgDescr("�����绰����:" + monitorPhone);

		TerminalCommand tc = new TerminalCommand();
		tc.setCmdType(JT808Constants.CMD_DIAL_BACK);
		tc.setCmdData(0 + ";" + monitorPhone);
		tc.setCmd("0");
		tc.setPlateNo(message.getPlateNo());
		tc.setOwner(TerminalCommand.FROM_GOV);
		try {
			ServiceLauncher.getBaseDao().save(tc);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		// 1501Ӧ��
		//byte result = 0; // �����ɹ�
		//T809Manager.UpCtrlMsgMonitorVehicleAck(message.getPlateNo(),
				//message.getPlateColor(), result);

		this.logger.info("���뽻��ָ��������λ��ϢӦ������ɹ�" + dataBody);

		return "";

	}

}

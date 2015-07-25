package com.ltmonitor.jt809.protocol.receive;

import org.apache.log4j.Logger;

import com.ltmonitor.entity.EWayBill;
import com.ltmonitor.jt809.app.ServiceLauncher;
import com.ltmonitor.jt809.app.T809Manager;
import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.protocol.IReceiveProtocol;

/**
 * �����˵�����
 * 
 * @author DELL
 * 
 */
public class DownExgMsgTakeEwayBillReq implements IReceiveProtocol {
	Logger logger = Logger.getLogger(DownExgMsgTakeEwayBillReq.class);

	public String handle(JT809Message message) {
		String receBody = message.getMessageBody();
		// �����˵�����
		String eBill = queryBillInfo(message.getPlateNo());
		T809Manager.UpExgMsgTakeEWayBillAck(message.getPlateNo(),
				message.getPlateColor(), eBill);
		return "";
	}

	private String queryBillInfo(String plateNo) {
		try {
			String hql = "from EWayBill where plateNo = ? order by createDate desc";

			EWayBill eBill = (EWayBill) ServiceLauncher.getBaseDao().find(hql,
					plateNo);

			if (eBill != null)
				return eBill.geteContent();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		String ewayBillInfo = "�ϸ��Ϳ⣬��Ʒ��91#��20�֣�����ʱ��12��00"; // queryBillInfo(message.getPlateNo());

		return ewayBillInfo;
	}
}

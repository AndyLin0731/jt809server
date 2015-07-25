package com.ltmonitor.jt809.protocol.receive;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.ltmonitor.entity.TakePhotoModel;
import com.ltmonitor.entity.TerminalCommand;
import com.ltmonitor.jt809.app.ServiceLauncher;
import com.ltmonitor.jt809.app.T809Manager;
import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.protocol.IReceiveProtocol;
import com.ltmonitor.jt809.protocol.send.UpCtrlMsgTakePhotoAck;
import com.ltmonitor.service.JT808Constants;


public class DownCtrlMsgTakePhotoReq implements IReceiveProtocol {
	private Logger logger = Logger.getLogger(DownWarnMsgTodoReq.class);

	public String handle(JT809Message message) {
		String dataBody = message.getMessageBody();
		MessageParser mp = new MessageParser(dataBody, 28);
		
		TakePhotoModel ph = new TakePhotoModel();
		ph.setLensId(mp.getInt(1));
		ph.setPhotoSize(mp.getInt(1));
		message.setMsgDescr("��ͷId��" + ph.getLensId() + ", ��С:" + ph.getPhotoSize());
		
		StringBuilder sb = new StringBuilder();
		/**
		sb.append(1).append(";").append(1).append(";")
				.append(1).append(";").append(1).append(";")
				.append(ph.getPhotoSize()).append(";").append(120).append(";")
				.append(125).append(";").append(120).append(";")
				.append(120).append(";").append(120);
		*/
		byte channel = 1;
		byte action = 1;//0��ʾֹͣ���㣻0xFFFF��ʾ¼��������ʾ��������  
		byte interval = 1;//������
		byte saveType = 0;//1�����棻0��ʵʱ�ϴ� 
		byte picSize = (byte)ph.getPhotoSize();//��Ƭ�ֱ��ʴ�С
		byte quality = 1;//1��10��1����������ʧ��С��10��ʾѹ�������
		byte light = 125;
		byte compare = 120;
		byte stature = 120;
		byte grade = 120;
		sb.append(channel).append(";").append(action).append(";")
		.append(interval).append(";").append(saveType).append(";")
		.append(picSize).append(";").append(quality).append(";")
		.append(light).append(";").append(compare).append(";")
		.append(stature).append(";").append(grade);
		TerminalCommand tc = new TerminalCommand();
		tc.setCmdType(JT808Constants.CMD_TAKE_PHOTO);
		tc.setCmdData(sb.toString());

		tc.setPlateNo(message.getPlateNo());
		tc.setOwner(TerminalCommand.FROM_GOV);
		try {
			ServiceLauncher.getBaseDao().save(tc);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}


		//UpCtrlMsgTakePhotoAck ack = new UpCtrlMsgTakePhotoAck();
		TakePhotoModel tm = new TakePhotoModel();
		tm.setPlateNo(message.getPlateNo());
		tm.setPlateColor(message.getPlateColor());
		tm.setLensId(ph.getLensId());
		tm.setPhotoSize(ph.getPhotoSize());
		tm.setPhotoFormat(1);
		byte[] temp = new byte[36];
		tm.setPhotoData(temp);
		T809Manager.UpCtrlMsgTakePhotoAck(tm);
		
		return "";
	}

}

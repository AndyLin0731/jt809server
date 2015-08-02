package com.ltmonitor.jt809.protocol.receive;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.protocol.IReceiveProtocol;
import com.ltmonitor.jt809.tool.Tools;

/**
 * ƽ̨���������Ϣ
 * @author tianfei
 *
 */
public class DownPlatformMsgPostQueryReq implements IReceiveProtocol {
	private static Logger logger = Logger
			.getLogger(DownPlatformMsgPostQueryReq.class);

	public String handle(JT809Message message) {

		String mess = message.getMessageBody();

		//int dataType = mp.getInt(2);
		//int dataLength = mp.getInt(4);
		
		MessageParser mp = new MessageParser(mess,6);
		int objType = mp.getInt(1); //��ڶ�������
		String objId = mp.getString(12); //��ڶ���ID
		int infoId = mp.getInt(4); //��ϢID
		int infoLength = mp.getInt(4);  //��Ϣ���ݳ���
		String content = mp.getString(infoLength);
		/**
		CheckRecord cm = new CheckRecord();
		cm.setInfoId(infoId);
		cm.setObjId(objId);
		cm.setObjType(objType);
		cm.setMessage(content);
		*/
		
		StringBuilder sb = new StringBuilder();
		sb.append(infoId).append(';')
		.append(objId).append(';')
		.append(objType).append(';')
		.append(content);
		message.setMsgDescr(sb.toString());
		/**
		GovPlatformMsg gf = new GovPlatformMsg();
		gf.setMsg(sb.toString());
		gf.setMsgType(GovPlatformMsg.TYPE_POST_QUERY);
		try {
			//ServiceLauncher.getBaseDao().save(gf);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		*/
		
		if(objType==2){
				logger.warn("���������Ϣ�Ѵ������ݿ�!");
		}
		

		//PlatformChaGangModel pm = new PlatformChaGangModel(cm);
		String backContent = "";
		/**
		if (objType == 1) {
			logger.warn("�Զ���ڲ��:objID:"+objId+"|infoid:"+infoId+"|content:" + content);
			backContent = "��ǰ����ҵ���б�:=" + GlobalConfig.parModel.getLicenseNo() + "|"
					+ GlobalConfig.parModel.getUsername();
			String autoContent = Tools.TurnISN(backContent);
			//pm.setMessage(autoContent);
			cm.setAnswer("2");
			T809Manager.UpPlatFormMsgPostQueryAck(cm);
			
		} else {
			//logger.warn("�˹����:" + content);
			//pm.setMessage("�˹����:" + content);
			//T809Manager.UpPlatFormMsgPostQueryAck(cm);
		}*/

		logger.warn("ƽ̨�����Ϣ��" + infoId + ":" + content);
		/**
		if (updatePlatState(infoId, backContent))
			logger.info("���²��״̬�ɹ�");
		else {
			logger.info("���²��״̬ʧ��");
		}
		*/
		return "";
	}


	private static Timestamp getTimestamp() {
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String s = format2.format(new Date());
		Timestamp insTime = Tools.convertToTimestamp(s);
		System.out.println(insTime);
		return insTime;
	}

}

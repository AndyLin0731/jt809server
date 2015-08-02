package com.ltmonitor.jt809.server;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.ltmonitor.jt809.app.GlobalConfig;
import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.model.ProtocolModel;
import com.ltmonitor.jt809.protocol.IReceiveProtocol;
import com.ltmonitor.jt809.tool.Tools;
import com.ltmonitor.jt809.tool.xml.ClassUtils;

/**
 * ������·��Ϣ����
 * @author tianfei
 *
 */
public class MessageAction {
	private Logger logger = Logger.getLogger(MessageAction.class);

	public boolean ResolveHandler(IoSession session, JT809Message message) {
		String data = "";
		int protocolId = message.getMsgType();

		if (GlobalConfig.protocolMap.get(Integer.valueOf(protocolId)) != null) {
			ProtocolModel pm = (ProtocolModel) GlobalConfig.protocolMap.get(Integer
					.valueOf(protocolId));
			//TODO:��Ҫ�Ż�
			IReceiveProtocol protocolClass = getBean(pm.getName());

			try {
				data = protocolClass.handle(message);
			} catch (Exception e) {
				logger.error("����Э�����:"+ message.ToHexString());
				logger.error(e.getMessage(), e);
			}
			GlobalConfig.putMsg(message);
			//���յ���������ҪӦ�𣬾ͷ���Ӧ�����ݣ���socket���ӷ���Ӧ��
			if (data != null && data.length() > 0) {
				SendData(data, session);
			}
		} else {
			this.logger.error("UpDataArriveû�д���Э�����͵��ࣺ"
					+ Tools.ToHexString(protocolId, 2));
			return false;
		}
		return true;
	}

	private IReceiveProtocol getBean(String className) {
		return (IReceiveProtocol) ClassUtils.getBean(className);
	}

	public void SendData(String StrsendData, IoSession session) {
		if (session.isConnected())
			session.write(StrsendData);
	}
}

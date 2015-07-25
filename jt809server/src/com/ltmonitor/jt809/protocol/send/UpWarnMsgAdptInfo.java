package com.ltmonitor.jt809.protocol.send;

import org.apache.log4j.Logger;

import com.ltmonitor.entity.WarnData;
import com.ltmonitor.jt809.app.GlobalConfig;
import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.model.ParameterModel;
import com.ltmonitor.jt809.protocol.ISendProtocol;
import com.ltmonitor.jt809.server.PlatformClient;
import com.ltmonitor.jt809.tool.Tools;

/**
 * �ϱ�������Ϣ 1402
 * @author DELL
 *
 */
public class UpWarnMsgAdptInfo implements ISendProtocol {
	private static Logger logger = Logger.getLogger(UpWarnMsgAdptInfo.class);
	private WarnData wd;

	private int msgType = 0x1400;
	private int subType = 0x1402;
	public UpWarnMsgAdptInfo(WarnData wd) {
		this.wd = wd;
	}
	

	public JT809Message wrapper() {
		String content = Tools.ToHexString(wd.getContent());
		int infoLength = content.length() / 2;
		int dataLength = 1 + 2 + 8 + 4 + 4 + infoLength; // ���������峤��

		StringBuilder sb = new StringBuilder();
		sb.append(Tools.ToHexString(wd.getPlateNo(), 21))
				.append(Tools.ToHexString(wd.getPlateColor(), 1))
				.append(Tools.ToHexString(subType, 2))
				.append(Tools.ToHexString(dataLength, 4))
				.append(Tools.ToHexString(wd.getSrc(), 1))
				.append(Tools.ToHexString(wd.getType(), 2))
				.append(Tools.getUTC(wd.getWarnTime()))
				.append(Tools.ToHexString(wd.getInfoId(), 4))
				.append(Tools.ToHexString(infoLength, 4))
				.append(content)
				;

		ParameterModel pm = GlobalConfig.parModel;
		String body = sb.toString();
		JT809Message mm = new JT809Message(msgType,  subType,body);
		mm.setPlateNo(wd.getPlateNo());
		mm.setPlateColor(wd.getPlateColor());
		return mm;
	}

	/**
	 * ��������
	 * @return
	 */
	public String ResolveHandle2() {
		String mess = "";

		ParameterModel parModel = GlobalConfig.parModel;
		try {
			String vehicleNo = Tools
					.turnStrLength(Tools.TurnISN("��B12345"), 42);

			String vehicleColor = "02";

			String dataType = "1402";

			String alermInfoContent = Tools.TurnISN("������Ϣ");

			String warnSrc = "01";

			String warnType = "0001";

			String alermTime = "0000000050B5D5FD";

			String infoId = Tools.turnDataLength(
					Long.toHexString(GlobalConfig.getSN()), 8);

			int len = alermInfoContent.length() / 2;

			String lenStr = Tools.turnDataLength(Integer.toHexString(len), 8);

			String lastLenth = Tools.turnDataLength(
					Integer.toHexString(len + 19), 8);

			String childDataBody = vehicleNo + vehicleColor + dataType
					+ lastLenth + warnSrc + warnType + alermTime + infoId
					+ lenStr + alermInfoContent;

			//mess = Tools.getHeaderAndFlag(GNSSImpl.getSN(), childDataBody,
					//1400, parModel.getPlatformCenterId());
			logger.warn("ʵʱ�ϴ��ı�����Ϣ��" + mess);
			if ((mess != null) && (mess.length() > 0)) {
				PlatformClient.session.write(mess);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mess;
	}
}

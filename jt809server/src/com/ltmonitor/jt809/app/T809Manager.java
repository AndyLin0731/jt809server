package com.ltmonitor.jt809.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.ltmonitor.dao.IBaseDao;
import com.ltmonitor.entity.GPSRealData;
import com.ltmonitor.entity.GnssData;
import com.ltmonitor.entity.PlatformState;
import com.ltmonitor.entity.TakePhotoModel;
import com.ltmonitor.entity.VehicleData;
import com.ltmonitor.entity.WarnData;
import com.ltmonitor.jt809.entity.DriverModel;
import com.ltmonitor.jt809.entity.CheckRecord;
import com.ltmonitor.jt809.entity.VehicleRegisterInfo;
import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.model.VehicleModel;
import com.ltmonitor.jt809.protocol.send.DownConnectRsp;
import com.ltmonitor.jt809.protocol.send.DownDisconnectRsp;
import com.ltmonitor.jt809.protocol.send.DownLinkTestRsp;
import com.ltmonitor.jt809.protocol.send.UpBaseMsgVehicleAddedAck;
import com.ltmonitor.jt809.protocol.send.UpCloseLinkInform;
import com.ltmonitor.jt809.protocol.send.UpConnectReq;
import com.ltmonitor.jt809.protocol.send.UpCtrlMsgEmergencyMonitoringAck;
import com.ltmonitor.jt809.protocol.send.UpCtrlMsgMonitorVehicleAck;
import com.ltmonitor.jt809.protocol.send.UpCtrlMsgTakePhotoAck;
import com.ltmonitor.jt809.protocol.send.UpCtrlMsgTakeTravelAck;
import com.ltmonitor.jt809.protocol.send.UpCtrlMsgTextInfoAck;
import com.ltmonitor.jt809.protocol.send.UpDisconnectInform;
import com.ltmonitor.jt809.protocol.send.UpDisconnectReq;
import com.ltmonitor.jt809.protocol.send.UpExgMsgApplyForMonitorEnd;
import com.ltmonitor.jt809.protocol.send.UpExgMsgApplyForMonitorStartup;
import com.ltmonitor.jt809.protocol.send.UpExgMsgApplyHisGnssDataReq;
import com.ltmonitor.jt809.protocol.send.UpExgMsgHistoryLocation;
import com.ltmonitor.jt809.protocol.send.UpExgMsgRealLocation;
import com.ltmonitor.jt809.protocol.send.UpExgMsgRegister;
import com.ltmonitor.jt809.protocol.send.UpExgMsgReportDriverAck;
import com.ltmonitor.jt809.protocol.send.UpExgMsgReportDriverInfo;
import com.ltmonitor.jt809.protocol.send.UpExgMsgReportTakeEWayBill;
import com.ltmonitor.jt809.protocol.send.UpExgMsgReturnEndAck;
import com.ltmonitor.jt809.protocol.send.UpExgMsgReturnStartUpAck;
import com.ltmonitor.jt809.protocol.send.UpExgMsgTakeEWayBillAck;
import com.ltmonitor.jt809.protocol.send.UpLinkTestReq;
import com.ltmonitor.jt809.protocol.send.UpPlatFormMsgInfoAck;
import com.ltmonitor.jt809.protocol.send.UpPlatFormMsgPostQueryAck;
import com.ltmonitor.jt809.protocol.send.UpWarnMsgAdptInfo;
import com.ltmonitor.jt809.protocol.send.UpWarnMsgAdptToDoInfo;
import com.ltmonitor.jt809.protocol.send.UpWarnMsgUrgeToDoAck;
import com.ltmonitor.jt809.server.LocalServer;
import com.ltmonitor.jt809.server.PlatformClient;
import com.ltmonitor.jt809.tool.Tools;
import com.ltmonitor.util.DateUtil;

/**
 * �ṩ�ⲿ����809ת��ƽ̨�Ľӿ�
 * 
 * @author DELL
 * 
 */
public class T809Manager {

	private static Logger logger = Logger.getLogger(T809Manager.class);
	public static boolean mainLinkConnected = false;

	public static boolean subLinkConnected = false;

	public static boolean encrypt = false;

	/**
	 * ʵʱ��λ��Ϣ�����������·���ϵ�������£���Ҫѹ����У�����·�ָ�������ͨ������Э����в���
	 */
	private static ConcurrentLinkedQueue<GnssData> gnssDataQueueWaitForSend = new ConcurrentLinkedQueue<GnssData>();

	public static void setMainLinkState(boolean connect, String errorMsg) {
		try {
			mainLinkConnected = connect;
			PlatformState ps = ServiceLauncher.getPlateformState();
			if (connect == false) {
				ps.setMainLinkState("���Ӵ���:" + errorMsg);
			} else {
				ps.setMainLinkState("���ӳɹ�");
				ps.setMainLinkDate(new Date());
			}
			ServiceLauncher.updatePlateformState(ps);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void setSubLinkState(boolean connect, String errorMsg) {
		try {
			subLinkConnected = connect;
			PlatformState ps = ServiceLauncher.getPlateformState();
			if (connect == false) {
				ps.setSubLinkState(errorMsg);
			} else {
				ps.setSubLinkState("���ӳɹ�");
				ps.setSubLinkDate(new Date());
			}
			ServiceLauncher.updatePlateformState(ps);
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
		}

		if (connect && gnssDataQueueWaitForSend.size() > 0) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			List<GnssData> gnssDataList = new ArrayList<GnssData>();
			while (gnssDataQueueWaitForSend.size() > 0) {
				GnssData g = gnssDataQueueWaitForSend.poll();
				if (g != null) {
					gnssDataList.add(g);
					if (gnssDataList.size() == 1) {
						UpExgMsgHistoryLocations(g.getPlateNo(),
								g.getPlateColor(), gnssDataList);
						gnssDataList.clear();
					}
				}
			}
			if (gnssDataList.size() > 0) {
				GnssData g = gnssDataList.get(0);
				UpExgMsgHistoryLocations(g.getPlateNo(), g.getPlateColor(),
						gnssDataList);
				gnssDataList.clear();
			}
		}
	}

	/**
	 * ���뽻��ָ��������λ��Ϣ
	 * 
	 * @param PlateNo
	 *            ���ƺ�
	 * @param color
	 *            ��ɫ
	 * @param start
	 *            ��ʼʱ��
	 * @param end
	 *            ����ʱ��
	 */
	public static boolean UpExgMsgApplyForMonitorStartup(String PlateNo,
			byte color, Date start, Date end) {
		UpExgMsgApplyForMonitorStartup up1207 = new UpExgMsgApplyForMonitorStartup(
				PlateNo, color, start, end);
		JT809Message msg = up1207.wrapper();
		return Send(msg);
	}

	/**
	 * ����·��������1001
	 * 
	 * @return
	 */
	public static boolean UpConnectReq() {
		UpConnectReq up1001 = new UpConnectReq();

		JT809Message message = up1001.wrapper();
		return Send(message);
	}

	/**
	 * �����ر�������·֪ͨ1008
	 * 
	 * @return
	 */
	public static boolean UpCloseLinkInform() {
		UpCloseLinkInform up1008 = new UpCloseLinkInform();
		JT809Message msg = up1008.wrapper();
		return Send(msg);
	}
	/**
	 * ����·�Ͽ�֪ͨ1007
	 * 
	 * @return
	 */
	public static boolean UpDisconnectMainLinkInform() {
		UpDisconnectInform up1007 = new UpDisconnectInform();
		JT809Message msg = up1007.wrapper();
		return Send(msg);
	}
	
	/**
	 * ����·�Ͽ�֪ͨ
	 * 
	 * @return
	 */
	public static boolean UpDisconnectReq() {
		UpDisconnectReq up1003 = new UpDisconnectReq();
		JT809Message msg = up1003.wrapper();
		return Send(msg);
	}

	

	/**
	 * ����·��������1005
	 * 
	 * @return
	 */
	public static boolean UpLinkTestReq() {
		UpLinkTestReq up1005 = new UpLinkTestReq();
		JT809Message mess = up1005.wrapper();
		return Send(mess);
	}

	/**
	 * ������������ָ��������λ��Ϣ 1209
	 * 
	 * @param PlateNo
	 * @param color
	 * @param start
	 *            ��ʼʱ��
	 * @param end
	 *            ����ʱ��
	 */
	public static boolean UpExgMsgApplyHisGnssDataReq(String PlateNo,
			byte color, Date start, Date end) {
		UpExgMsgApplyHisGnssDataReq up1209 = new UpExgMsgApplyHisGnssDataReq(
				PlateNo, color, start, end);
		JT809Message msg = up1209.wrapper();
		return Send(msg);
	}

	/**
	 * ȡ�����뽻��ָ����������1208
	 * 
	 * @param PlateNo
	 * @param color
	 * @return
	 */
	public static boolean UpExgMsgApplyForMonitorEnd(String PlateNo, byte color) {
		UpExgMsgApplyForMonitorEnd up1208 = new UpExgMsgApplyForMonitorEnd(
				PlateNo, color);
		JT809Message mess = up1208.wrapper();
		return Send(mess);
	}

	/**
	 * ��̬����ע��1201
	 * 
	 * @param vm
	 * @return
	 */
	public static boolean UpExgMsgRegister(VehicleRegisterInfo vm) {
		UpExgMsgRegister um1201 = new UpExgMsgRegister(vm);
		JT809Message mess = um1201.wrapper();
		return Send(mess);
	}

	/**
	 * ʵʱ�ϴ�������λ��Ϣ 1202
	 * 
	 * @param gnssData
	 * @return
	 */
	public static boolean UpExgMsgRealLocation(GnssData gnssData) {
		// �����·�Ͽ�������Ҫѹ�뵽�����У��ȴ�����
		if (mainLinkConnected == false && subLinkConnected == false) {
			if(gnssDataQueueWaitForSend.size() > 3000)
				gnssDataQueueWaitForSend.clear();
			gnssDataQueueWaitForSend.add(gnssData);
			return false;
		}
		UpExgMsgRealLocation real1202 = new UpExgMsgRealLocation(gnssData);
		JT809Message mess = real1202.wrapper();
		return Send(mess);
	}

	/**
	 * ��λ��Ϣ�Զ�����1203
	 * 
	 * @param gnssDatas
	 * @return
	 */
	public static boolean UpExgMsgHistoryLocations(String plateNo,
			int plateColor, List<GnssData> gnssDatas) {
		UpExgMsgHistoryLocation real1203 = new UpExgMsgHistoryLocation(plateNo,
				plateColor, gnssDatas);
		JT809Message mess = real1203.wrapper();
		return Send(mess);
	}

	/**
	 * �ϱ�������Ϣ 1402
	 * 
	 * @param wd
	 * @return
	 */
	public static boolean UpWarnMsgAdptInfo(WarnData wd) {
		UpWarnMsgAdptInfo real1402 = new UpWarnMsgAdptInfo(wd);
		JT809Message mess = real1402.wrapper();
		return Send(mess);
	}

	/**
	 * �ϱ�����̎��Y����Ϣ 1403
	 * 
	 * @param wd
	 * @return
	 */
	public static boolean UpWarnMsgAdptToDoInfo(String plateNo, int plateColor,
			long infoId, int result) {
		UpWarnMsgAdptToDoInfo real1403 = new UpWarnMsgAdptToDoInfo(plateNo,
				plateColor, infoId, result);
		JT809Message mess = real1403.wrapper();
		return Send(mess);
	}

	/**
	 * �����ϱ������˵���Ϣ 120D
	 * 
	 * @param dm
	 * @return
	 */
	public static boolean UpExgMsgReportTakeEWayBill(String plateNo,
			int plateColor, String eContent) {
		UpExgMsgReportTakeEWayBill up120D = new UpExgMsgReportTakeEWayBill(
				plateNo, plateColor, eContent);
		JT809Message mess = up120D.wrapper();
		return Send(mess);
	}

	// ���ϼ�ƽ̨��������
	private static boolean Send(String msg) {
		PlatformClient pc = PlatformClient.getInstance();
		// ����ʹ������·��������
		boolean res = true;
		if (PlatformClient.Send(msg) == false) {
			res = LocalServer.Send(msg);
		}
		return res;
	}
	

	// ���ϼ�ƽ̨��������
	public static boolean Send(JT809Message msg) {
		String strMsg = Tools.getHeaderAndFlag(GlobalConfig.getSN(),
				msg.getMessageBody(), msg.getMsgType(),
				msg.getMsgGNSSCenterID(), T809Manager.encrypt);
		msg.setPacketDescr(strMsg);
		GlobalConfig.putMsg(msg);
		return Send(strMsg);
	}

	/**
	 * ��������·���Ӻʹ���·����
	 */
	public static boolean StartServer() {
		// ����·����
		PlatformClient pc = PlatformClient.getInstance();

		boolean res = pc.start();
		if (res) {
			LocalServer ls = LocalServer.getInstance();
			// ����·����
			res = ls.start();
			ServiceLauncher.getCommandService().Start();

			// ������ʱ��
			DNSSTimer.getInstance().start();
		}
		return res;
	}
	


	/**
	 * ֹͣ����
	 */
	public static void StopServer() {
		DNSSTimer.getInstance().Stop();

		if (ServiceLauncher.getCommandService() != null)
			ServiceLauncher.getCommandService().Stop();

		PlatformClient pc = PlatformClient.getInstance();
		pc.Stop();
		LocalServer ls = LocalServer.getInstance();
		ls.Stop();

		T809Manager.setSubLinkState(false, "����·�����Ͽ�");
		T809Manager.setMainLinkState(false, "����·�����Ͽ�");

	}

	/**
	 * �رմ���·
	 */
	public static void CloseSubLink() {
		LocalServer ls = LocalServer.getInstance();
		ls.Stop();

		T809Manager.setSubLinkState(false, "����·�����Ͽ�");
	}

	/**
	 * �ر�������
	 */
	public static void CloseMainLink() {

		PlatformClient pc = PlatformClient.getInstance();
		pc.Stop();
		GlobalConfig.isOpenPlat = false;
		GlobalConfig.isRegist = false;
		T809Manager.setMainLinkState(false, "����·�����Ͽ�");
	}

	// *******************һ��Ϊ�Զ�Ӧ��ӿ�***************************************/

	/**
	 * �Դ���·����������Ϣ��Ӧ��
	 */
	public static boolean DownConnectRsp() {

		DownConnectRsp up9002 = new DownConnectRsp();
		JT809Message mess = up9002.wrapper();
		return SendFromSubLink(mess);
	}


	// ���ϼ�ƽ̨��������
		private static boolean SendFromSubLink(String msg) {
			// ����ʹ������·��������
			boolean res = LocalServer.Send(msg);
			if (res = false) {
				PlatformClient pc = PlatformClient.getInstance();
				res = PlatformClient.Send(msg);
			}
			return res;
		}
		
		// ���ϼ�ƽ̨��������
		public static boolean SendFromSubLink(JT809Message msg) {
			String strMsg = Tools.getHeaderAndFlag(GlobalConfig.getSN(),
					msg.getMessageBody(), msg.getMsgType(),
					msg.getMsgGNSSCenterID(), T809Manager.encrypt);
			msg.setPacketDescr(strMsg);
			GlobalConfig.putMsg(msg);
			return SendFromSubLink(strMsg);
		}
		
		
	/**
	 * �Դ���·ע��9003������Ϣ��Ӧ��
	 */
	public static boolean DownDisconnectRsp() {

		DownDisconnectRsp up9004 = new DownDisconnectRsp();
		JT809Message mess = up9004.wrapper();
		return SendFromSubLink(mess);
	}

	/**
	 * �Դ���·������Ϣ��Ӧ��
	 */
	public static boolean DownLinkTestRsp() {

		DownLinkTestRsp up9006 = new DownLinkTestRsp();
		JT809Message mess = up9006.wrapper();
		return SendFromSubLink(mess);
	}

	/**
	 * ���ϼ�ƽ̨��������������λ��Ϣ��Ӧ��
	 * 
	 * @param plateNo
	 * @param plateColor
	 * @return
	 */
	public static boolean UpExgMsgReturnStartUpAck(String plateNo,
			int plateColor) {

		UpExgMsgReturnStartUpAck up1205 = new UpExgMsgReturnStartUpAck(plateNo,
				plateColor);
		JT809Message mess = up1205.wrapper();
		return Send(mess);
	}

	/**
	 * ���ϼ�ƽ̨��������������λ��Ϣ��Ӧ��
	 * 
	 * @param plateNo
	 * @param plateColor
	 * @return
	 */
	public static boolean UpExgMsgReturnEndAck(String plateNo, int plateColor) {

		UpExgMsgReturnEndAck up1206 = new UpExgMsgReturnEndAck(plateNo,
				plateColor);
		JT809Message mess = up1206.wrapper();
		return Send(mess);
	}

	/**
	 * ���ϼ�ƽ̨������Ϣ��Ӧ��
	 * 
	 * @param plateNo
	 * @param plateColor
	 * @return
	 */
	public static boolean UpCtrlMsgTextInfoAck(String plateNo, int plateColor,
			int msgId, byte result) {

		UpCtrlMsgTextInfoAck up1503 = new UpCtrlMsgTextInfoAck(plateNo,
				plateColor, msgId, result);
		JT809Message mess = up1503.wrapper();
		return Send(mess);
	}

	/**
	 * ���ϼ�ƽ̨���������Ӧ��
	 * 
	 * @param plateNo
	 * @param plateColor
	 * @param result
	 * @return
	 */
	public static boolean UpCtrlMsgMonitorVehicleAck(String plateNo,
			int plateColor, byte result) {

		UpCtrlMsgMonitorVehicleAck up1501 = new UpCtrlMsgMonitorVehicleAck(
				plateNo, plateColor, result);
		JT809Message mess = up1501.wrapper();
		return Send(mess);
	}

	/**
	 * ���ϼ�ƽ̨����Ӧ�������Ӧ��
	 * 
	 * @param plateNo
	 * @param plateColor
	 * @param result
	 * @return
	 */
	public static boolean UpCtrlMsgEmergencyMonitoringAck(String plateNo,
			int plateColor, byte result) {

		UpCtrlMsgEmergencyMonitoringAck up1505 = new UpCtrlMsgEmergencyMonitoringAck(
				plateNo, plateColor, result);
		JT809Message mess = up1505.wrapper();
		return Send(mess);
	}

	/**
	 * ���ϼ�ƽ̨�����г���¼�������Ӧ�� 1504
	 * 
	 * @param plateNo
	 * @param plateColor
	 * @param cmdType
	 *            �г���¼��������
	 * @return
	 */
	public static boolean UpCtrlMsgTakeTravelAck(String plateNo,
			int plateColor, byte cmdType, byte[] cmdData) {

		UpCtrlMsgTakeTravelAck up1504 = new UpCtrlMsgTakeTravelAck(plateNo,
				plateColor, cmdType, cmdData);
		JT809Message mess = up1504.wrapper();
		return Send(mess);
	}

	/**
	 * ���ϼ�ƽ̨�����ʻԱ��ݵ�Ӧ��
	 * 
	 * @param dm
	 * @return
	 */
	public static boolean UpExgMsgReportDriverAck(DriverModel dm) {
		UpExgMsgReportDriverAck up120a = new UpExgMsgReportDriverAck(dm);
		JT809Message mess = up120a.wrapper();
		return Send(mess);
	}

	/**
	 * �����ϱ���ʻԱ���
	 * 
	 * @param dm
	 * @return
	 */
	public static boolean UpExgMsgReportDriverInfo(DriverModel dm) {
		UpExgMsgReportDriverInfo up120c = new UpExgMsgReportDriverInfo(dm);
		JT809Message mess = up120c.wrapper();
		return Send(mess);
	}

	/**
	 * �ϱ������˵�������ϢӦ�� 120B
	 * 
	 * @param dm
	 * @return
	 */
	public static boolean UpExgMsgTakeEWayBillAck(String plateNo,
			int plateColor, String eContent) {
		UpExgMsgTakeEWayBillAck up120B = new UpExgMsgTakeEWayBillAck(plateNo,
				plateColor, eContent);
		JT809Message mess = up120B.wrapper();
		return Send(mess);
	}

	/**
	 * ������������ϢӦ�� 1601
	 * 
	 * @param dm
	 * @return
	 */
	public static boolean UpBaseMsgVehicleAddedAck(VehicleModel dm) {
		UpBaseMsgVehicleAddedAck up1601 = new UpBaseMsgVehicleAddedAck(dm);
		JT809Message mess = up1601.wrapper();
		return Send(mess);
	}

	/**
	 * ƽ̨���Ӧ�� 1301
	 * 
	 * @param dm
	 * @return
	 */
	public static boolean UpPlatFormMsgPostQueryAck(CheckRecord dm) {
		UpPlatFormMsgPostQueryAck up1301 = new UpPlatFormMsgPostQueryAck(dm);
		JT809Message mess = up1301.wrapper();
		return Send(mess);
	}

	/**
	 * ƽ̨�䱨����ϢӦ��1302
	 * 
	 * @param infoId
	 * @return
	 */
	public static boolean UpPlatFormMsgInfoAck(int infoId) {
		UpPlatFormMsgInfoAck up1302 = new UpPlatFormMsgInfoAck(infoId);
		JT809Message mess = up1302.wrapper();
		return Send(mess);
	}

	public static boolean UpCtrlMsgTakePhotoAck(TakePhotoModel _photo) {
		UpCtrlMsgTakePhotoAck up1502 = new UpCtrlMsgTakePhotoAck(_photo);
		JT809Message mm = up1502.wrapper();
		return Send(mm);
	}

	/**
	 * ��������Ӧ��
	 * 
	 * @param plateNo
	 * @param plateColor
	 * @param superviseId
	 *            ����ID
	 * @param result
	 *            ������
	 * @return
	 */
	public static boolean UpWarnMsgUrgeToDoAck(String plateNo, int plateColor,
			int superviseId, byte result) {

		UpWarnMsgUrgeToDoAck up1401 = new UpWarnMsgUrgeToDoAck(plateNo,
				plateColor, superviseId, result);
		JT809Message mess = up1401.wrapper();
		return Send(mess);
	}
}

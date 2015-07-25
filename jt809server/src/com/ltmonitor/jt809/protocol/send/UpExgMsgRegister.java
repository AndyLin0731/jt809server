package com.ltmonitor.jt809.protocol.send;

import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.ltmonitor.jt809.app.GlobalConfig;
import com.ltmonitor.jt809.entity.VehicleRegisterInfo;
import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.model.ParameterModel;
import com.ltmonitor.jt809.model.VehicleModel;
import com.ltmonitor.jt809.protocol.ISendProtocol;
import com.ltmonitor.jt809.server.PlatformClient;
import com.ltmonitor.jt809.tool.Tools;


public class UpExgMsgRegister implements ISendProtocol {
	private static Logger logger = Logger.getLogger(UpExgMsgRegister.class);
	// �Ƿ�ע�ᳵ�����е����г���
	private boolean RegisterAllVehicle = false;
	// Э����������
	private int msgType = 0x1200;
	// ������
	private int subType = 0x1201;
	// ��������
	private VehicleRegisterInfo vehicleModel;

	public UpExgMsgRegister(VehicleRegisterInfo _vm) {
		vehicleModel = _vm;
	}

	public JT809Message wrapper() {
		String vehicleNo = Tools.ToHexString(vehicleModel.getPlateNo(), 21);
		String vehicleColor = Tools
				.ToHexString(vehicleModel.getPlateColor(), 1);

		ParameterModel pm = GlobalConfig.parModel;
		int dataLength = 11 + 11 + 20 + 7 + 12; // �������ݳ���
		StringBuilder data = new StringBuilder();
		data.append(vehicleNo).append(vehicleColor)
		.append(Tools.ToHexString(subType, 2))
		.append(Tools.ToHexString(dataLength, 4))
		.append(Tools.ToHexString(vehicleModel.getPlateformId(), 11)) // ƽ̨Ψһ����
				.append(Tools.ToHexString(vehicleModel.getTerminalVendorId(), 11)) // �ն˳���Ψһ����
				.append(Tools.ToHexString(vehicleModel.getTerminalModel(), 20)) // �ն��ͺ�
				.append(Tools.ToHexString(vehicleModel.getTerminalId(), 7)) // �ն˱��
				.append(Tools.ToHexString(vehicleModel.getSimNo(), 12));

		JT809Message mm = new JT809Message(msgType,  subType, data.toString());
		mm.setPlateColor(vehicleModel.getPlateColor());
		mm.setPlateNo(vehicleModel.getPlateNo());
		return mm;
	}

	public boolean isRegisterAllVehicle() {
		return RegisterAllVehicle;
	}

	public void setRegisterAllVehicle(boolean registerAllVehicle) {
		RegisterAllVehicle = registerAllVehicle;
	}
}

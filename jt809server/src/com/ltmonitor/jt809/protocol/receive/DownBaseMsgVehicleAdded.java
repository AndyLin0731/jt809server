package com.ltmonitor.jt809.protocol.receive;

import org.apache.log4j.Logger;

import com.ltmonitor.entity.MemberInfo;
import com.ltmonitor.entity.VehicleData;
import com.ltmonitor.jt809.app.ServiceLauncher;
import com.ltmonitor.jt809.app.T809Manager;
import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.model.VehicleModel;
import com.ltmonitor.jt809.protocol.IReceiveProtocol;

public class DownBaseMsgVehicleAdded implements IReceiveProtocol {
	Logger logger = Logger.getLogger(DownBaseMsgVehicleAdded.class);

	public String handle(JT809Message message) {
		String dataBody = message.getMessageBody();
		MessageParser mp = new MessageParser(dataBody);
		// 1205Ӧ��
		VehicleModel vm = getVehicleModel(message.getPlateNo());
		if (vm != null)
			T809Manager.UpBaseMsgVehicleAddedAck(vm);
		else
			logger.error("�Ҳ����ó�����Ϣ" + message.getPlateNo());

		this.logger.info("���뽻��ָ��������λ��ϢӦ������ɹ�" + dataBody);

		return "";
	}

	private VehicleModel getVehicleModel(String plateNo) {
		try {
			String hql = "from VehicleData where plateNo = ?";
			VehicleData vd = (VehicleData) ServiceLauncher.getBaseDao().find(
					hql, plateNo);
			if (vd != null) {
				VehicleModel vm = new VehicleModel();
				vm.setPlateNo(plateNo);
				vm.setPlateColor(vd.getPlateColor());
				vm.setVehicleType(vd.getVehicleType()); // ��������
				vm.setNationallity(vd.getRegion()); // ������
				vm.setTransType(vd.getIndustry()); // ������ҵ����

				try {
					// ��������ҵ����Ϣ
					MemberInfo mi = (MemberInfo) ServiceLauncher.getBaseDao()
							.load(MemberInfo.class, vd.getMemberId());
					vm.setOwnerName(mi.getName());
					vm.setOwnerTel(mi.getContactPhone());
					vm.setOwnerId("" + mi.getEntityId());
				} catch (Exception ex) {
					vm.setOwnerName("�Ĵ��˱��ڿ��������޹�˾");
					vm.setOwnerTel("15814030918");
					vm.setOwnerId("12345678");
				}

				return vm;
			}
		} catch (Exception ex) {

		}
		return null;
	}

}

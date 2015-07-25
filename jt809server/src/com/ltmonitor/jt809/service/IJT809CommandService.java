package com.ltmonitor.jt809.service;

import com.ltmonitor.dao.IBaseDao;
import com.ltmonitor.entity.JT809Command;


public interface IJT809CommandService {

	public abstract IBaseDao getBaseDao();

	public abstract void setBaseDao(IBaseDao value);

	public abstract ICommandHandler getOnRecvCommand();

	public abstract void setOnRecvCommand(ICommandHandler value);

	public abstract int getInterval();

	public abstract void setInterval(int value);

	// ������������̣߳��Զ�������������͸��ն�
	public abstract void Start();

	public abstract void Stop();

	public abstract void ParseCommand();

	// ������Ϣ����ˮ��������״̬
	//public abstract JT809Command UpdateStatus(String GpsId, int SN,
			//String status);

	public abstract void UpdateCommand(JT809Command tc);

	//public abstract JT809Command getCommandBySn(int sn);

	void save(JT809Command jc);

	// ���ԷǷ������ʽ���н�����������¼��ʱȷ����ʽ��ȷ
	//public abstract T808Message Parse(JT809Command tc);

}
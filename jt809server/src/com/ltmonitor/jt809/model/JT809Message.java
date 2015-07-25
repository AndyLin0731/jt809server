package com.ltmonitor.jt809.model;

import com.ltmonitor.jt809.app.GlobalConfig;
import com.ltmonitor.jt809.tool.Tools;


public class JT809Message {
	// 0x5b��ʼ��־
	private String headFlag;
	// ����809���ĳ��� 1 + 4 + 4 + 2 + 4 + 3 + 1 + 4 + 2 + 1 + ������ĳ���
	private long msgLength; // 4���ֽ��޷��ţ�ֻ�����һ��������ת��
	// 4���ֽڵ���ˮ��
	private long msgSN;
	// 2���ֽ�
	private int msgType;
	// �¼�ƽ̨������
	private long msgGNSSCenterID;
	// �汾�����ֽ�
	private byte[] versionFlag;
	// ���ܱ�־ 0 ������ 1����
	private byte encryptFlag;

	// ������Կ 4���ֽ� �޷�������
	private long encryptKey;
	// ������
	private String messageBody;
	// 2���ֽ�
	private String CRCCode;
	// 0x5d������־
	private String endFlag;
	//�����ͱ�ʶ
	private int subType;
	//���ƺ�
	private String plateNo;
	//������ɫ
	private int plateColor;
	
	private int contentLength;
	//��Ϣ��������
	private String msgDescr;
	
	private String PacketDescr;

	public JT809Message(int _msgType) {
		msgType = _msgType;
		headFlag = "5b";
		endFlag = "5d";
		versionFlag = new byte[] { 1, 1, 1 };
		msgGNSSCenterID = GlobalConfig.parModel.getPlatformCenterId();
	}
	
	public JT809Message(int _msgType, String body)
	{		
		msgType = _msgType;
		messageBody = body;
		headFlag = "5b";
		endFlag = "5d";
		versionFlag = new byte[] { 1, 1, 1 };
		msgGNSSCenterID = GlobalConfig.parModel.getPlatformCenterId();		
	}
	
	public JT809Message(int _msgType, int _subType,String body)
	{		
		msgType = _msgType;
		subType = _subType;
		messageBody = body;
		headFlag = "5b";
		endFlag = "5d";
		versionFlag = new byte[] { 1, 1, 1 };
		msgGNSSCenterID = GlobalConfig.parModel.getPlatformCenterId();		
	}
	

	public String ToHexString() {
		return Tools.getHeaderAndFlag(GlobalConfig.getSN(), messageBody, msgType,
				msgGNSSCenterID, false);
	}

	public String getCRCCode() {
		return this.CRCCode;
	}

	public void setCRCCode(String CRCCode) {
		this.CRCCode = CRCCode;
	}

	public byte getEncryptFlag() {
		return this.encryptFlag;
	}

	public void setEncryptFlag(byte encryptFlag) {
		this.encryptFlag = encryptFlag;
	}

	public long getEncryptKey() {
		return this.encryptKey;
	}

	public void setEncryptKey(long encryptKey) {
		this.encryptKey = encryptKey;
	}

	public String getHeadFlag() {
		return this.headFlag;
	}

	public void setHeadFlag(String headFlag) {
		this.headFlag = headFlag;
	}

	public String getMessageBody() {
		return this.messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public long getMsgGNSSCenterID() {
		return this.msgGNSSCenterID;
	}

	public void setMsgGNSSCenterID(long msgGNSSCenterID) {
		this.msgGNSSCenterID = msgGNSSCenterID;
	}

	public int getMsgType() {
		return this.msgType;
	}

	public void setMsgType(int msgID) {
		this.msgType = msgID;
	}

	public long getMsgLength() {
		return this.msgLength;
	}

	public void setMsgLength(long msgLength) {
		this.msgLength = msgLength;
	}

	public long getMsgSN() {
		return this.msgSN;
	}

	public void setMsgSN(long msgSN) {
		this.msgSN = msgSN;
	}

	public byte[] getVersionFlag() {
		return this.versionFlag;
	}

	public void setVersionFlag(byte[] versionFlag) {
		this.versionFlag = versionFlag;
	}

	public String getEndFlag() {
		return this.endFlag;
	}

	public void setEndFlag(String endFlag) {
		this.endFlag = endFlag;
	}

	public int getSubType() {
		return subType;
	}

	public void setSubType(int subType) {
		this.subType = subType;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public int getPlateColor() {
		return plateColor;
	}

	public void setPlateColor(int plateColor) {
		this.plateColor = plateColor;
	}

	public String getDescr() {
		return msgDescr;
	}

	public void setMsgDescr(String msgDescr) {
		this.msgDescr = msgDescr;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setPacketDescr(String packetDescr) {
		PacketDescr = packetDescr;
	}

	public String getPacketDescr() {
		return PacketDescr;
	}
}

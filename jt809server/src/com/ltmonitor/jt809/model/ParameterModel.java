 package com.ltmonitor.jt809.model;
 
 public class ParameterModel
 {
	 /**
	  * ����·�˿�
	  */
   private int platformPort;
   /**
    * ����ƽ̨Id
    */
   private long platformCenterId;
   /**
    * ����ƽ̨������
    */
   private String platformIP;
   /**
    * ƽ̨����
    */
   private String platformPass;
   /**
    * ƽ̨�˺�
    */
   private int platformUserName;
   /**
    * ���ض˿� ����·�˿�
    */
   private int localPort;
   /**
    * ����·����Ip
    */
   private String localIp;
   private int idleTime;
   /**
    * ������Կ
    */
   private long miyaoM;
   private long miyaoA;
   private long miyaoC;
   
   private String protocolVer;
   private String licenseNo;
   /**
    * ����ƽ̨�Ñ������ڱ����Ñ�
    */
   private String username;
   //���������Ƿ����
   private boolean enrypt;
   
   private int encryptKey;
   
   public ParameterModel()
   {
	   enrypt = false;
	   encryptKey = 1234;
   }
 
   public String getLicenseNo()
   {
     return this.licenseNo;
   }
 
   public void setLicenseNo(String licenseNo) {
     this.licenseNo = licenseNo;
   }
 
   public String getUsername() {
     return this.username;
   }
 
   public void setUsername(String username) {
     this.username = username;
   }
 
   public String getLocalIp() {
     return this.localIp;
   }
 
   public void setLocalIp(String localIp) {
     this.localIp = localIp;
   }
 
   public String getProtocolVer() {
     return this.protocolVer;
   }
 
   public void setProtocolVer(String protocolVer) {
     this.protocolVer = protocolVer;
   }
 
   public long getMiyaoM() {
     return this.miyaoM;
   }
 
   public void setMiyaoM(long miyaoM) {
     this.miyaoM = miyaoM;
   }
 
   public long getMiyaoA() {
     return this.miyaoA;
   }
 
   public void setMiyaoA(long miyaoA) {
     this.miyaoA = miyaoA;
   }
 
   public long getMiyaoC() {
     return this.miyaoC;
   }
 
   public void setMiyaoC(long miyaoC) {
     this.miyaoC = miyaoC;
   }
 
   public int getIdleTime() {
     return this.idleTime;
   }
 
   public void setIdleTime(int idleTime) {
     this.idleTime = idleTime;
   }
 
   public int getLocalPort() {
     return this.localPort;
   }
 
   public void setLocalPort(int localPort) {
     this.localPort = localPort;
   }
 
   public long getPlatformCenterId() {
     return this.platformCenterId;
   }
 
   public void setPlatformCenterId(long platformCenterId) {
     this.platformCenterId = platformCenterId;
   }
 
   public String getPlatformIP() {
     return this.platformIP;
   }
 
   public void setPlatformIP(String platformIP) {
     this.platformIP = platformIP;
   }
 
   public String getPlatformPass() {
     return this.platformPass;
   }
 
   public void setPlatformPass(String platformPass) {
     this.platformPass = platformPass;
   }
 
   public int getPlatformPort() {
     return this.platformPort;
   }
 
   public void setPlatformPort(int platformPort) {
     this.platformPort = platformPort;
   }
 
   public int getPlatformUserName() {
     return this.platformUserName;
   }
 
   public void setPlatformUserName(int platformUserName) {
     this.platformUserName = platformUserName;
   }

public boolean isEnrypt() {
	return enrypt;
}

public void setEnrypt(boolean enrypt) {
	this.enrypt = enrypt;
}

public int getEncryptKey() {
	return encryptKey;
}

public void setEncryptKey(int encryptKey) {
	this.encryptKey = encryptKey;
}
 }


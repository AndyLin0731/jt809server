 package com.ltmonitor.jt809.protocol.send;
 
 import org.apache.log4j.Logger;

import com.ltmonitor.jt809.app.GlobalConfig;
import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.model.ParameterModel;
import com.ltmonitor.jt809.protocol.ISendProtocol;
import com.ltmonitor.jt809.tool.Tools;

 
 public class UpConnectReq implements ISendProtocol {
   private static Logger logger = Logger.getLogger(UpConnectReq.class);
 
   public JT809Message wrapper()
   {
     String outPut = "";
 
     String message = "";
 
     String dataBody = null;
 
     ParameterModel parModel = GlobalConfig.parModel;
 
     long userName = parModel.getPlatformUserName();
     String passWord = parModel.getPlatformPass();
     try
     { 
       String endUserName = Tools.ToHexString(userName, 4);
        String endPassWord = Tools.ToHexString(passWord, 8);
 
       //����·���ؼ���������IP
       String endTurnPlatFormIp = Tools.ToHexString(parModel.getLocalIp(), 32);
 
       //����·�����˿�
       String endPlatFormPort = Tools.ToHexString(parModel.getLocalPort(), 2);
 
       dataBody = endUserName + endPassWord + endTurnPlatFormIp + endPlatFormPort;
 
       //message = Tools.getHeaderAndFlag(GNSSImpl.getSN(), dataBody, 1001, parModel.getPlatformCenterId());
       
       //outPut = "���ݰ�������" + message;
       return new JT809Message(0x1001, dataBody);
     }
     catch (Exception e) {
       e.printStackTrace();
       outPut = "���ݰ��������";
       logger.info(outPut, e);
     }
     return null;
   }
 }


 package com.ltmonitor.jt809.protocol.receive;
 
 import org.apache.log4j.Logger;

import com.ltmonitor.jt809.app.T809Manager;
import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.protocol.IReceiveProtocol;

/**
 * ����·��¼Ӧ��  0X1002 
 * @author tianfei
 *
 */
 public class UpConnectRsp
   implements IReceiveProtocol
 {
   private Logger logger = Logger.getLogger(UpConnectRsp.class);
 
   public String handle(JT809Message message)
   {
     String content = message.getMessageBody();
     int flag = Integer.valueOf(content.substring(0, 2), 16).intValue();
     String result = "";
     boolean isSuccess = false;
     switch (flag) {
     case 0:
       com.ltmonitor.jt809.app.GlobalConfig.isOpenPlat = true;
       result = "������ƽ̨�ɹ���";
       isSuccess = true;
       break;
     case 1:
       result = "IP��ַ����ȷ��";
       break;
     case 2:
       result = "�����벻��ȷ��";
       break;
     case 3:
       result = "�û�û��ע��!";
       break;
     case 4:
       result = "�������!";
       break;
     case 5:
       result = "��Դ�����Ժ������ӣ�";
       break;
     case 6:
       result = "�����쳣��";
       break;
     default:
       result = "Default�쳣";
     }
     logger.warn(result);
     message.setMsgDescr(result);
     T809Manager.setMainLinkState(isSuccess, result);
 
     return "";
   }
 }


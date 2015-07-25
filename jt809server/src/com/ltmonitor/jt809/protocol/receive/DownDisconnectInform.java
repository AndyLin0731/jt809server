 package com.ltmonitor.jt809.protocol.receive;
 
 import org.apache.log4j.Logger;

import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.protocol.IReceiveProtocol;

 
 public class DownDisconnectInform
   implements IReceiveProtocol
 {
   private static Logger logger = Logger.getLogger(DownDisconnectInform.class);
 
   public String handle(JT809Message message)
   {
     String content = message.getMessageBody();
     int errorCode = Integer.valueOf(content.substring(0, 2), 16).intValue();
     switch (errorCode) {
     case 0:
       logger.warn("�������00���޷������¼�ƽָ̨���ķ���IP��˿ڣ�");
       break;
     case 1:
       logger.warn("�������01���ϼ�ƽ̨�ͻ������¼�ƽ̨����˶Ͽ���");
       break;
     case 2:
       logger.warn("�������02������ԭ��");
       break;
     default:
       logger.warn("�������" + errorCode);
     }
 
     return "";
   }
 }


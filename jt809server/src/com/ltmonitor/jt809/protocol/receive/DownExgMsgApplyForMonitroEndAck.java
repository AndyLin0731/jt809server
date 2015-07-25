 package com.ltmonitor.jt809.protocol.receive;
 
 import org.apache.log4j.Logger;

import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.protocol.IReceiveProtocol;

 
 public class DownExgMsgApplyForMonitroEndAck
   implements IReceiveProtocol
 {
   private static Logger logger = Logger.getLogger(DownExgMsgApplyForMonitroEndAck.class);
 
   public String handle(JT809Message message)
   {
     String dataBody = message.getMessageBody(); 
     //������ͷ������28. 
     int result = Integer.parseInt(dataBody.substring(56, 58));
     
     String descr = "";
     if(result == 0)
    	 descr = "ȡ������ɹ�";
     else if(result == 1)
         descr = "֮ǰû�ж�Ӧ����";
     else if(result == 2)
         descr = "δ֪����";
     
     message.setMsgDescr(descr);
     return null;
   }
 }


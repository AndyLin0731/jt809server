 package com.ltmonitor.jt809.protocol.receive;
 
 import org.apache.log4j.Logger;

import com.ltmonitor.jt809.model.JT809Message;
import com.ltmonitor.jt809.protocol.IReceiveProtocol;

 
 public class DownExgMsgApplyForMonitorStartupAck
   implements IReceiveProtocol
 {
   private Logger logger = Logger.getLogger(DownExgMsgApplyForMonitorStartupAck.class);
 
   public String handle(JT809Message message)
   {
     String dataBody = message.getMessageBody(); 
     //������ͷ������28. 
     int result = Integer.parseInt(dataBody.substring(56, 58));
     
     String descr = "";
     if(result == 0)
    	 descr = "����ɹ�";
     else if(result == 1)
         descr = "�ϼ�ƽ̨û�иó���";
     else if(result == 2)
         descr = "����ʱ��δ���";
     else if(result == 3)
         descr = "δ֪����";
     
     message.setMsgDescr(descr);
     
     return "";
   }

 }


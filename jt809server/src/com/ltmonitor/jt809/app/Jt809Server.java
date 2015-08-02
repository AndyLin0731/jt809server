package com.ltmonitor.jt809.app;

import org.apache.log4j.Logger;

/**
 * ��̨����809����
 * @author tianfei
 *
 */
public class Jt809Server {
	
	private static final Logger LOGGER = Logger.getLogger(Jt809Server.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//���������ļ� 
		ServiceLauncher.launch();
		//����ƽ̨����
		GlobalConfig.initSystem();
		
		try {
			//����JT809������·
			Boolean res = T809Manager.StartServer();
			if (res == false) {
				LOGGER.error("�޷������ϼ�ƽ̨");
				return;
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(),ex);
		}
	}

}

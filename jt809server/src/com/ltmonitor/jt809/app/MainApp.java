package com.ltmonitor.jt809.app;

import javax.swing.JFrame;
import javax.swing.UIManager;
import org.apache.log4j.Logger;
import com.ltmonitor.jt809.frame.MainFrame;


public class MainApp {

	private static Logger logger = Logger.getLogger(MainApp.class);
	public static void main(String[] args) {
		try {
			/**
			 * ����: Windows, WindowsClassic,Metal,
			 * 
			 */
			UIManager.LookAndFeelInfo[] arrayOfLookAndFeelInfo;
			int j = (arrayOfLookAndFeelInfo = UIManager
					.getInstalledLookAndFeels()).length;
			for (int i = 0; i < j; i++) {
				UIManager.LookAndFeelInfo info = arrayOfLookAndFeelInfo[i];
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}

			ServiceLauncher.launch();

			GlobalConfig.initSystem();
			
		} catch(Exception ex) {
			//Logger.getLogger(ParameterFrame.class.getName()).log(Level.SEVERE,
					//null, ex);
			
			logger.error(ex.getMessage(), ex);
		}
		
		
		MainFrame mainFrame = new MainFrame();

		// ���log����������ı��ؼ�����ʾ
		//System.setOut(new PrintStream(new GUIPrintStream(System.out,
				//MainFrame.dataArea)));
		mainFrame.setLocationRelativeTo(null);
		
		//Logger.getLogger(ParameterFrame.class.getName()).log(Level.INFO,"test");

		mainFrame.setVisible(true);
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

	}
}

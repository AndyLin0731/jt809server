package com.ltmonitor.jt809.server;

import java.net.InetSocketAddress;
import org.apache.log4j.Logger;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import com.ltmonitor.jt809.app.GlobalConfig;
import com.ltmonitor.jt809.app.ServiceLauncher;

/**
 * JT809����·����
 * @author tianfei
 *
 */
public class LocalServer {
	private static Logger logger = Logger.getLogger(LocalServer.class);
	public static boolean isOpen = false;

	private static LocalServer instance = null;

	public static IoAcceptor dataAccepter = null;
	private LocalServerHandler handler = new LocalServerHandler();
	public static IoSession session;

	public static void Stop() {
		try {
			isOpen = false;
			if (null != dataAccepter) {
				dataAccepter.unbind();
				dataAccepter.getFilterChain().clear(); // ���Filter
														// chain����ֹ�´���������ʱ������������
				dataAccepter.dispose(); // ������дһ����洢IoAccept��ͨ��spring����������������dispose��Ҳ�����´���һ���µġ����߿�����init�����ڲ����д�����
				dataAccepter = null;
				// System.exit(0); ����������ֹͣ
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	public static boolean Send(String msg) {
		try {
			if (session != null && session.isConnected()) {
				LocalServer ls = LocalServer.getInstance();
				WriteFuture wf = session.write(msg);
				return true;
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return false;
	}

	public static final synchronized LocalServer getInstance() {
		if (instance == null) {
			instance = new LocalServer();
		}
		return instance;
	}
	/**
	 * ����JT809����·����
	 * @return
	 */
	public boolean start() {
		try {
			if (isOpen)
				return true;
			dataAccepter = new NioSocketAcceptor();

			LoggingFilter log = new LoggingFilter();
			log.setMessageReceivedLogLevel(LogLevel.WARN);
			dataAccepter.getFilterChain().addLast("logger", log);

			dataAccepter.getFilterChain().addLast("codec",
					new ProtocolCodecFilter(new TiamaesMessageCodecFactory()));

			IoSessionConfig config = dataAccepter.getSessionConfig();

			config.setReadBufferSize(2048);

			config.setIdleTime(IdleStatus.BOTH_IDLE, 60);

			dataAccepter.setHandler(this.handler);
			this.handler.setJt809CommandService(ServiceLauncher
					.getCommandService());

			dataAccepter.bind(new InetSocketAddress(GlobalConfig.parModel
					.getLocalPort()));
			logger.info("���ݷ����������ɹ�!�˿ں�:" + GlobalConfig.parModel.getLocalPort());
			isOpen = true;
		} catch (Exception e) {
			isOpen = false;
			logger.error("����·����������ʧ��:" + e.getMessage(), e);
			// e.printStackTrace();
		}
		return isOpen;
	}
}

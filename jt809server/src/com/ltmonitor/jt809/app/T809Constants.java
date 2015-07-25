package com.ltmonitor.jt809.app;

import java.util.HashMap;
import java.util.Hashtable;

public class T809Constants {
	
	public static HashMap<Integer, String> msgMap = new HashMap<Integer, String>();
	
	public static String getMsgDescr(Integer msgId)
	{
		if(msgMap.isEmpty())
			init();
		
		return "" + msgMap.get(msgId);
	}
	
	
	private static void init()
	{		
		msgMap.put(0x1001,"����·��¼������Ϣ");
		msgMap.put(0x1002,"����·��¼Ӧ����Ϣ");
		msgMap.put(0x1003,"����·ע��������Ϣ");
		msgMap.put(0x1004,"����·ע��Ӧ����Ϣ");
		msgMap.put(0x1005,"����·���ӱ���������Ϣ");
		msgMap.put(0x1006,"����·���ӱ���Ӧ����Ϣ");
		msgMap.put(0x1007,"����·�Ͽ�֪ͨ��Ϣ");
		msgMap.put(0x1008,"�¼�ƽ̨�����ر���·֪ͨ��Ϣ");
		msgMap.put(0x9001,"����·����������Ϣ");
		msgMap.put(0x9002,"����·����Ӧ����Ϣ");
		msgMap.put(0x9003,"����·ע��������Ϣ");
		msgMap.put(0x9004,"����·ע��Ӧ����Ϣ");
		msgMap.put(0x9005,"����·���ӱ���������Ϣ");
		msgMap.put(0x9006,"����·���ӱ���Ӧ����Ϣ");
		msgMap.put(0x9007,"����·�Ͽ�֪ͨ��Ϣ");
		msgMap.put(0x9008,"�ϼ�ƽ̨�����ر���·֪ͨ��Ϣ");
		msgMap.put(0x9101,"���ն�λ��Ϣ����֪ͨ��Ϣ");
		msgMap.put(0x1200,"����·��̬��Ϣ������Ϣ");
		msgMap.put(0x9200,"����·��̬��Ϣ������Ϣ");
		msgMap.put(0x1300,"����·ƽ̨����Ϣ������Ϣ");
		msgMap.put(0x9300,"����·ƽ̨����Ϣ������Ϣ");
		msgMap.put(0x1400,"����·������Ϣ������Ϣ");
		msgMap.put(0x9400,"����·������Ϣ������Ϣ");
		msgMap.put(0x1500,"����·���������Ϣ");
		msgMap.put(0x9500,"����·���������Ϣ");
		msgMap.put(0x1600,"����·��̬��Ϣ������Ϣ");
		msgMap.put(0x9600,"����·��̬��Ϣ������Ϣ");


		msgMap.put(0x1201,"�ϴ�����ע����Ϣ");
		msgMap.put(0x1202,"��ʵ�ϴ�������λ��Ϣ");
		msgMap.put(0x1203,"������λ��Ϣ�Զ�����");
		msgMap.put(0x1205,"����������λ��Ϣ����Ӧ��");
		msgMap.put(0x1206,"����������λ��Ϣ����Ӧ��");
		msgMap.put(0x1207,"���뽻��ָ��������");
		msgMap.put(0x1208,"ȡ������ָ��������λ��Ϣ����");
		msgMap.put(0x1209,"����������λ��Ϣ����");
		msgMap.put(0x120A,"�ϱ�������ʻԱ���ʶ����ϢӦ��");
		msgMap.put(0x120B,"�ϱ����������˵�Ӧ��");
		msgMap.put(0x120C,"�����ϱ���ʻԱ�����Ϣ");
		msgMap.put(0x120D,"�����ϱ����������˵���Ϣ");

		msgMap.put(0x9202,"����������λ��Ϣ");
		msgMap.put(0x9203,"������λ��Ϣ��������");
		msgMap.put(0x9204,"����������̬��Ϣ");
		msgMap.put(0x9205,"����������λ��Ϣ��������");
		msgMap.put(0x9206,"����������λ��Ϣ��������");
		msgMap.put(0x9207,"���뽻��ָ��������λӦ��");
		msgMap.put(0x9208,"ȡ������ָ��������λӦ��");
		msgMap.put(0x9209,"����������λ��ϢӦ��");
		msgMap.put(0x920A,"�ϱ�������ʻԱ���ʶ����Ϣ����");
		msgMap.put(0x920B,"�ϱ����������˵�����");

		msgMap.put(0x1301,"ƽ̨���Ӧ��");
		msgMap.put(0x1302,"�·�ƽ̨�䱨��Ӧ��");
		msgMap.put(0x9301,"ƽ̨���Ӧ��");
		msgMap.put(0x9302,"�·�ƽ̨�䱨������");
		msgMap.put(0x1401,"��������Ӧ��");
		msgMap.put(0x1402,"�ϱ�������Ϣ");
		msgMap.put(0x1402,"�����ϱ�������������Ϣ");

		msgMap.put(0x9401,"������������");
		msgMap.put(0x9402,"����Ԥ��");
		msgMap.put(0x9403,"ʵʱ����������Ϣ");

		msgMap.put(0x1501,"�����������Ӧ��");
		msgMap.put(0x1502,"��������Ӧ��");
		msgMap.put(0x1503,"�·���������Ӧ��");
		msgMap.put(0x1504,"�ϱ�������ʻ��¼Ӧ��");
		msgMap.put(0x1505,"����Ӧ��������ƽ̨Ӧ����Ϣ");

		msgMap.put(0x9501,"���������������");
		msgMap.put(0x9502,"������������");
		msgMap.put(0x9503,"�·�������������");
		msgMap.put(0x9504,"�ϱ�������ʻ��¼Ӧ��");

		msgMap.put(0x9505,"����Ӧ��������ƽ̨������Ϣ");
		msgMap.put(0x1601,"����������̬��ϢӦ��");
		msgMap.put(0x9601,"����������̬��Ϣ����");
	}
	
	
	
}


package com.ltmonitor.jt809.app;

public enum VehiclePlateColor {
	 /// <summary>
    /// ��ɫ
    /// </summary>
    Blue(1),
    /// <summary>
    /// ��ɫ
    /// </summary>
    Yellow(2),
    /// <summary>
    /// ��ɫ
    /// </summary>
    Black(3),
    /// <summary>
    /// ��ɫ
    /// </summary>
    White(4),
    /// <summary>
    /// ����
    /// </summary>
    Other(5);
    
    private int nCode ;
 
       // ���캯����ö������ֻ��Ϊ˽��
       private VehiclePlateColor( int _nCode) {
           this . nCode = _nCode;
       }
 
       @Override
       public String toString() {
           return String.valueOf ( this . nCode );
       }
}

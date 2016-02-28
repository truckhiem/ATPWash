package com.atp.wash;

public class Contanst {
	public static final int TOTAL_MACHINE = 8;
	public static final String MACHINE_01 = "WM01";
	public static final String MACHINE_02 = "WM02";
	public static final String MACHINE_03 = "WM03";
	public static final String MACHINE_04 = "WM04";
	public static final String MACHINE_05 = "WM05";
	public static final String MACHINE_06 = "WM06";
	public static final String MACHINE_07 = "WM07";
	public static final String MACHINE_08 = "WM08";
	public static final String MACHINE_09 = "WM09";
	public static final String MACHINE_10 = "WM10";
	
//	Shareference
	public static class Shareference {
		public static final String IS_LOGIN = "IS_LOGIN";
		public static final String USER_NAME = "USERNAME";
		public static final String PASSWORD = "PASSWORD";
		public static final String LIST_MACHINE_STATUS = "LIST_MACHINE_STATUS";
		public static final String LIST_MACHINE_UNSAVE = "LIST_MACHINE_UNSAVE";
	}

	public static class ActivateMachine{
		public static boolean WASHING_MACHINE_1;
		public static boolean WASHING_MACHINE_2;
		public static boolean WASHING_MACHINE_3;
		public static boolean WASHING_MACHINE_4;
		public static boolean WASHING_MACHINE_5;
		public static boolean WASHING_MACHINE_6;
		public static boolean WASHING_MACHINE_7;
		public static boolean WASHING_MACHINE_8;
	}

	public static class Prices{
		public static long ExtraWashing = 50000;
		public static long ExtraSoft = 50000;
		public static long WashingPrice = 50000;
		public static long DryPrice = 50000;
	}

	public static class ExtraIntent{
		public static String INTENT_WS_ITEM = "INTENT_WS_ITEM";
	}

}

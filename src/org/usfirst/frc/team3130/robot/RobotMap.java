package org.usfirst.frc.team3130.robot;

public class RobotMap {
	//Constant Ratios
			public static final int RATIO_WINCHCODESPERREV = 1024;
			public static final int RATIO_DRIVECODESPERREV = 360;

		//DIO Ports
			//Prefix DIO_

		//Relay
			public static final int RLY_HEADLIGHT = 3;
			
		//Motors-PWM
			//Prefix PWM_
			
		//Motors-CAN
			public static final int CAN_PNMMODULE = 1;
			public static final int CAN_SHOOTERMOTOR = 2;
			public static final int CAN_LEFTMOTORFRONT = 3;
			public static final int CAN_LEFTMOTORREAR = 4;
			public static final int CAN_RIGHTMOTORFRONT = 5;
			public static final int CAN_RIGHTMOTORREAR = 6;
			public static final int CAN_INTAKEMOTOR = 7;
			public static final int CAN_CLIMBERTAPE = 8;
			public static final int CAN_CLIMBERWINCH = 9;
			public static final int CAN_CLIMBERWINCH2 = 10;
		
		//Pnuematics Ports
			public static final int PNM_INTAKEPIN = 0;
			public static final int PNM_SHOOTERLOCK = 1;
			public static final int PNM_GEARSHIFTER = 2;
			public static final int PNM_INTAKEACTUATEOUT = 3;
			public static final int PNM_INTAKEACTUATEUP = 4;
		
		//Analog Input
			//Prefix ANG_
		
		//Buttons and Axes
			
			//Button List
			public static final int LST_BTN_A = 1;
			public static final int LST_BTN_B = 2;
			public static final int LST_BTN_X = 3;
			public static final int LST_BTN_Y = 4;
			public static final int LST_BTN_LBUMPER = 5;
			public static final int LST_BTN_RBUMPER = 6;
			public static final int LST_BTN_BACK = 7;
			public static final int LST_BTN_START = 8;
			public static final int LST_BTN_RJOYSTICKPRESS = 9;
			public static final int LST_BTN_LJOYSTICKPRESS = 10;

			//Axis List
			public static final int LST_AXS_LJOYSTICKX = 0;
			public static final int LST_AXS_LJOYSTICKY = 1;
			public static final int LST_AXS_LTRIGGER = 2;
			public static final int LST_AXS_RTRIGGER = 3;
			public static final int LST_AXS_RJOYSTICKX = 4;
			public static final int LST_AXS_RJOYSTICKY = 5;

			//POV Degress
			public static final int LST_POV_UNPRESSED = -1;
			public static final int LST_POV_N = 0;
			public static final int LST_POV_NE = 45;
			public static final int LST_POV_E = 90;
			public static final int LST_POV_SE = 135;
			public static final int LST_POV_S = 180;
			public static final int LST_POV_SW = 225;
			public static final int LST_POV_W = 270;
			public static final int LST_POV_NW = 315;
			

			//Vision
			public static final int BTN_AIMLEFT = 3;
			public static final int BTN_AIMRIGHT = 4;
			public static final int BTN_HEADLIGHT = 2;

			//Drive
			public static final int BTN_SHIFT = 1;
}

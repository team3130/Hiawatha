package org.usfirst.frc.team3130.robot;

public class RobotMap {
	//Constant Ratios
			public static final int RATIO_WINCHCODESPERREV = 1024;
			public static final int RATIO_DRIVECODESPERREV = 360;
			public static final double DIM_ROBOTWHEELTOWHEEL = 31.63;

		//DIO Ports
			public static final int DIO_JETSONPWRON = 0;

		//Relay
			//Prefix RLY_
			
		//Motors-PWM
			public static final int PWM_SHOOTERALTITUDE = 1;
			
		//Motors-CAN
			public static final int CAN_PNMMODULE = 1;
			public static final int CAN_LEFTMOTORFRONT = 2;
			public static final int CAN_LEFTMOTORREAR = 3;
			public static final int CAN_RIGHTMOTORFRONT = 4;
			public static final int CAN_RIGHTMOTORREAR = 5;
			public static final int CAN_CLIMBERMOTOR1 = 6;
			public static final int CAN_CLIMBERMOTOR2 = 7;
			public static final int CAN_INTAKEMOTOR = 8;
			public static final int CAN_HOPPERSTIR = 9;
			public static final int CAN_INDEXMOTORLEFT = 10;
			public static final int CAN_SHOOTERWHEELSLEFT = 11;
			public static final int CAN_SHOOTERWHEELSRIGHT = 12;
			public static final int CAN_INDEXMOTORRIGHT = 45;

		
		//Pnuematics Ports
			public static final int PNM_GEARSHIFTER = 0;
			public static final int PNM_GEARLIFT = 1;
			public static final int PNM_GEARPINCH = 6;
			public static final int PNM_GEARDOOR = 3;
			public static final int PNM_TOPGEARSHIELD = 4;
		
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

			//POV Degress List
			public static final int LST_POV_UNPRESSED = -1;
			public static final int LST_POV_N = 0;
			public static final int LST_POV_NE = 45;
			public static final int LST_POV_E = 90;
			public static final int LST_POV_SE = 135;
			public static final int LST_POV_S = 180;
			public static final int LST_POV_SW = 225;
			public static final int LST_POV_W = 270;
			public static final int LST_POV_NW = 315;
			
			
			//Intake
			public static final int BTN_INTAKEUP = LST_BTN_A;
			public static final int BTN_INTAKEDOWN = LST_BTN_B;
			public static final int BTN_CLIMBERUP = 80;	//TODO: Get Actual Button
			public static final int BTN_CLIMBERDOWN = 80;//TODO: Get Actual Button
			
			//Hopper
			public static final int BTN_HOPPERDRIVE = LST_BTN_RBUMPER;//TODO: Get Actual Button
			
			//Shooter
			public static final int BTN_TESTSHOOTERWHEELS = LST_BTN_LBUMPER;
			public static final int BTN_RUNINDEXER = LST_BTN_START;
			
			//Gear Control
			public static final int AXS_SHIELDGEAR = LST_AXS_LTRIGGER;
			public static final int BTN_PINCHGEAR = LST_BTN_Y;
			public static final int BTN_LIFTGEAR = LST_BTN_X;
			public static final int AXS_DOORGEAR = LST_AXS_RTRIGGER;
			
			public static final int BTN_SHIFT = 1;
}
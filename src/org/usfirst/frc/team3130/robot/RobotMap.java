package org.usfirst.frc.team3130.robot;

/**
 * This class is where we store all the constants, port numbers, and 
 * button numbers
 * @author Ashley
 *
 */
public class RobotMap {
	//Constant Ratios
			public static final int RATIO_WINCHCODESPERREV = 1024;
			public static final int RATIO_DRIVECODESPERREV = 2048;
			public static final double RATIO_VISIONTOINCHES = 1;
			public static final double DIM_ROBOTWHEELTOWHEEL = 31.63;


		//DIO Ports
			public static final int DIO_JETSONPWRON = 0;

		//Relay
			public static final int RLY_FLASHLIGHT = 1;
			//Prefix RLY_
			
		//Motors-CAN
			public static final int CAN_PNMMODULE = 1;
			public static final int CAN_RIGHTMOTORFRONT = 2; //2
			public static final int CAN_LEFTMOTORFRONT = 3; //3
            public static final int CAN_RIGHTMOTORREAR = 4; //4
            public static final int CAN_LEFTMOTORREAR = 5; //5
			public static final int CAN_CLIMBERMOTOR1 = 6;
			public static final int CAN_CLIMBERMOTOR2 = 7;
			public static final int CAN_TURRETHOPL = 8; 
			public static final int CAN_TURRETHOPR = 9; 
            public static final int CAN_BALLINTAKERIGHT = 10;
            public static final int CAN_BALLINTAKELEFT = 11;
			public static final int CAN_GEARBAR = 12;
			public static final int CAN_TURRETANGLE = 13;
			public static final int CAN_TURRETINDEX = 14;
			public static final int CAN_INDEXMOTORRIGHT = 45;
			
			public static final int CAN_SHOOTERMASTER = 16; //TODO: assign this number
			public static final int CAN_SHOOTERSLAVE = 15; //TODO: assign this number
		
			
		//Not currently in use anymore
		    public static final int CAN_INTAKEMOTOR = 666;
		    public static final int CAN_INDEXMOTORLEFT = 666;
		    public static final int CAN_SHOOTERWHEELSRIGHT = 666; 
			public static final int CAN_HOPPERSTIR = 666; 
			public static final int CAN_HOPPER2 = 666;
			
		//Pnuematics Ports
			public static final int PNM_GEARSHIFTER = 0;
			public static final int PNM_GEARLIFT = 4;
			public static final int PNM_GEARPINCH = 2;
			
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
			
			//Hopper
			public static final int BTN_HOPPERDRIVE = LST_BTN_RBUMPER;//TODO: Get Actual Button

			
			//Shooter
			//public static final int BTN_TESTSHOOTERWHEELS = LST_BTN_LBUMPER;
			public static final int BTN_RUNINDEXER = LST_BTN_RBUMPER;
			//public static final int BTN_TESTCURVEPREFERENCES = LST_BTN_START;
			public static final int BTN_AIMSHOOT = 4;		//Joystick R
			public static final int BTN_AIMDRIVE = 5;		//Joystick R
			
			//Turret
			public static final int BTN_TURRETINDEX = 666; //TODO: determine this #
			public static final int BTN_TURRETHOPL = 666; //TODO: determine this #
			public static final int BTN_TURRETHOPR = 666; //TODO: determine this #
			public static final int BTN_TURRETAUTOFLY = LST_BTN_START;
			public static final int BTN_TURRETINTAKE = LST_BTN_X;
			public static final int BTN_TURRETAUTOAIM = LST_BTN_LBUMPER;
			public static final int BTN_TURRETMANUALFLY = LST_BTN_RBUMPER;
			
			//Gear Control
			public static final int BTN_PINCHGEAR = 1;	//Joystick L
			public static final int BTN_LOWERGEARACTIVE = 1;	//Joystick R
			
			public static final int BTN_SHIFT = 1;
			
			public static final int BTN_GEARASSIST = 3;	//Joystick R
			
			public static final int BTN_SHIFTDOWN = 3;	//Joystick L
			public static final int BTN_SHIFTUP = 2;	//Joystick R
			
			//Drive
			public static final int BTN_REVERSEDRIVE = 5; //Joystick R
			//public static final int BTN_DRIVEBACK = LST_BTN_Y;
			
			//Climb
			public static final int AXS_CLIMB = LST_AXS_LTRIGGER;
			
			//Flashlight
			public static final int BTN_FLASHLIGHTTOGGLE = LST_BTN_X;
			
			


}
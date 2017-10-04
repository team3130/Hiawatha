package org.usfirst.frc.team3130.robot;


import org.usfirst.frc.team3130.robot.autoCommands.*;
import org.usfirst.frc.team3130.robot.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is to bind commands to buttons
 * @author Ashley
 *
 */
public class OI {
	@SuppressWarnings("unused")
	private class JoystickTrigger extends Trigger{

		private Joystick stick;
		private int axis;
		private double threshold;
		
		private JoystickTrigger(Joystick stick, int axis){
			this.stick = stick;
			this.axis = axis;
			threshold = 0.1;
		}
		
		private JoystickTrigger(Joystick stick, int axis, double threshold){
			this.stick = stick;
			this.axis = axis;
			this.threshold = threshold;
		}
		
		@Override
		public boolean get() {
			return stick.getRawAxis(axis) > threshold;
		}
		
	}
	
	private class POVTrigger extends Trigger{

		private Joystick stick;
		private int POV;
		
		public POVTrigger(Joystick stick, int POV) {
			this.stick = stick;
			this.POV = POV;
		}
		
		@Override
		public boolean get() {
			return stick.getPOV(0)==POV;
		}
		
	}
	
	//Instance Handling
    private static OI m_pInstance;
    public static OI GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new OI();
    	return m_pInstance;
    }
	
	//Define Joysticks
	public static Joystick stickL;
	public static Joystick stickR;
	public static Joystick gamepad;
	
	//Define Joystick Buttons
	private static JoystickButton intakeIn;
	private static JoystickButton intakeOut;
	private static JoystickButton hopperRun;
	private static JoystickButton hopperRun2;
	private static JoystickButton pinchGear;
	private static JoystickButton lowerGearActive;
	private static JoystickButton spinIndexer;

	private static JoystickButton flashlightToggle;
	
	private static JoystickButton shiftUp;
	private static JoystickButton shiftDown;
	private static JoystickButton gearAssist;
	private static DriveToGear gearDrive;
	
	private static JoystickButton aim;
	private static JoystickButton aimDrive;
	
	private static JoystickButton turretShoot;
	private static JoystickButton turretIntake;
	private static JoystickButton turretAutoAim;
	private static JoystickButton turretHoldAngle;
	private static JoystickButton turretManualShoot;
	
	//private static JoystickButton driveBack;
	//private static JoystickButton driveBackEnd;
	
	//Define Commands
	WipeStopPoints wipePoints;
	AddPoint	   addPoint;
	TestSpeedPoints	testCurve;
	AutoDriveStraightToPoint driveBackwards;
	private CameraAim buttonAimer;
	
	private static JoystickButton btn10L;
	private static TestContinuous testL;
	
	private static JoystickButton btn10R;
	private static HoldAngleTest testR;
	
	public static SendableChooser<String> gearStartPos;
	public static SendableChooser<String> fieldSide;
	private OI()
	{
		//Create Joysticks
		stickL = new Joystick(0);
		stickR = new Joystick(1);
		gamepad = new Joystick(2);
		
		//Create Joystick Buttons
		intakeIn = new JoystickButton(gamepad, RobotMap.BTN_INTAKEUP);
		intakeOut = new JoystickButton(gamepad, RobotMap.BTN_INTAKEDOWN);
		hopperRun = new JoystickButton(gamepad, RobotMap.BTN_HOPPERDRIVE);
		hopperRun2 = new JoystickButton(gamepad, RobotMap.BTN_HOPPERDRIVE);
		pinchGear = new JoystickButton(stickL, RobotMap.BTN_PINCHGEAR);
		lowerGearActive = new JoystickButton(stickR, RobotMap.BTN_LOWERGEARACTIVE);
		spinIndexer = new JoystickButton(gamepad, RobotMap.BTN_RUNINDEXER);
		
		gearAssist = new JoystickButton(stickR, RobotMap.BTN_GEARASSIST);
		shiftUp = new JoystickButton(stickR, RobotMap.BTN_SHIFTUP);
		shiftDown = new JoystickButton(stickL, RobotMap.BTN_SHIFTDOWN);
		
		aim = new JoystickButton(stickR, RobotMap.BTN_AIMSHOOT);
		aimDrive = new JoystickButton(stickR, RobotMap.BTN_AIMDRIVE);
		
		//driveBack = new JoystickButton(gamepad, RobotMap.BTN_DRIVEBACK);
		//driveBackEnd = new JoystickButton(gamepad, RobotMap.BTN_DRIVEBACK);
		

		flashlightToggle = new JoystickButton(gamepad, RobotMap.BTN_FLASHLIGHTTOGGLE);
		
		turretShoot = new JoystickButton(gamepad, RobotMap.BTN_TURRETAUTOFLY);
		turretIntake = new JoystickButton(gamepad, RobotMap.BTN_TURRETINTAKE);
		turretHoldAngle = new JoystickButton(gamepad, RobotMap.BTN_TURRETAUTOFLY);
		turretAutoAim = new JoystickButton(gamepad, RobotMap.BTN_TURRETAUTOAIM);
		turretManualShoot = new JoystickButton(gamepad, RobotMap.BTN_TURRETMANUALFLY);
		
		//Create Commands
		wipePoints	= new WipeStopPoints(Robot.wscTurret);
		addPoint	= new AddPoint(Robot.wscTurret);
		testCurve	= new TestSpeedPoints();
		driveBackwards = new AutoDriveStraightToPoint();
		buttonAimer = new CameraAim();
		
		driveBackwards.SetParam(Preferences.getInstance().getDouble("Drive Back Dist", -30), 10, 0.8, false);
		
		gearDrive 	= new DriveToGear();
		gearDrive.setParam(Preferences.getInstance().getDouble("Gear Test Drive Speed", .2));
		
		btn10L = new JoystickButton(stickL, 10);
		testL = new TestContinuous();
		//testL.SetParam(-17, 10, .4, false);
		
		btn10R = new JoystickButton(stickR,10);
		testR = new HoldAngleTest();
		testR.SetParam(45);
		
		//Bind Joystick Buttons to Commands
		intakeIn.whileHeld(new BallIntakeIn());
        intakeOut.whileHeld(new BallIntakeOut());
		hopperRun.whileHeld(new BasicSpinMotor(Robot.btHopper, Preferences.getInstance().getDouble("Hopper Stirrer PercentVBus", 0.5)));
		hopperRun2.whileHeld(new BasicSpinMotor(Robot.btHopper2, Preferences.getInstance().getDouble("Hopper2 PercentVBus", 0.8)));
		pinchGear.whileHeld(new BasicActuate(Robot.bcGearPinch));
		lowerGearActive.whileHeld(new LowerGearPickup());
		spinIndexer.whileHeld(new RunIndexer(buttonAimer));
		gearAssist.whileHeld(gearDrive);
		
		flashlightToggle.whenPressed(new FlashlightToggle());
		
		turretShoot.whileHeld(new AutoFlywheel());
		turretIntake.whileHeld(new ManualTurretIntake());
		turretAutoAim.whileHeld(new TurretAim());
		turretHoldAngle.whileHeld(new HoldAngle());
		turretManualShoot.whileHeld(new ManualFlywheel());
		
		shiftUp.whenPressed(new DriveShiftUp());
		shiftDown.whenPressed(new DriveShiftDown());
		
		aim.whileHeld(buttonAimer);
		aimDrive.whileHeld(new CameraDrive());
		
		//driveBack.whenPressed(driveBackwards);
		//driveBackEnd.whenReleased(new DefaultDrive());
		
		//btn10L.whileHeld(testL);
		//btn10R.whileHeld(testR);
		
		
		gearStartPos = new SendableChooser<String>();
		//If hardcoding required, manually choose peg below
		//gearStartPos.addObject("Left Peg", "Left");
		//gearStartPos.addObject("Center Peg", "Center");
		gearStartPos.addDefault("Center Peg", "Center");
		SmartDashboard.putData("Gear Pos Chooser",gearStartPos);
		
		fieldSide = new SendableChooser<String>();
		//If hardcoding required, manually choose fieldSide below
		//fieldSide.addObject("Red Side", "Red");
		fieldSide.addDefault("Blue Side", "Blue");
		SmartDashboard.putData("Field Side",fieldSide);
		
		//Place Commands on SMD
		SmartDashboard.putData("Wipe Shooter Points", wipePoints);
		SmartDashboard.putData("Add Shooter Point", addPoint);
		SmartDashboard.putData("Test Speed Curve", testCurve);
	}
}
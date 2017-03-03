package org.usfirst.frc.team3130.robot;


import org.usfirst.frc.team3130.robot.autoCommands.CameraAim;
import org.usfirst.frc.team3130.robot.autoCommands.CameraDrive;
import org.usfirst.frc.team3130.robot.autoCommands.DriveToGear;

import org.usfirst.frc.team3130.robot.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class OI {
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
	private static JoystickTrigger climberUp;
	private static JoystickButton hopperRun;
	private static JoystickButton testShooterWheels;
	private static JoystickButton pinchGear;
	private static JoystickButton lowerGearActive;
	private static JoystickButton actuateGearLift;
	private static JoystickButton spinIndexer;

	private static JoystickButton testCurvePreferences;
	private static JoystickButton reverseDrive;

	private static JoystickButton shiftUp;
	private static JoystickButton shiftDown;
	private static JoystickButton gearAssist;
	
	private static JoystickButton aim;
	private static JoystickButton aimDrive;
	
	//Define Commands
	WipeStopPointsL wipeLPoints;
	WipeStopPointsR wipeRPoints;
	AddPointL		addLPoint;
	AddPointR		addRPoint;
	TestSpeedPoints	testCurve;
	
	private static JoystickButton btn10;
	private static HoldAngleTest turn;
	
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
		climberUp = new JoystickTrigger(gamepad, RobotMap.BTN_CLIMBERUP);
		hopperRun = new JoystickButton(gamepad, RobotMap.BTN_HOPPERDRIVE);
		testShooterWheels = new JoystickButton(gamepad, RobotMap.BTN_TESTSHOOTERWHEELS);
		pinchGear = new JoystickButton(stickL, RobotMap.BTN_PINCHGEAR);
		lowerGearActive = new JoystickButton(stickR, RobotMap.BTN_LOWERGEARACTIVE);
		actuateGearLift = new JoystickButton(gamepad, RobotMap.BTN_GEARLIFT);
		spinIndexer = new JoystickButton(gamepad, RobotMap.BTN_RUNINDEXER);
		testCurvePreferences = new JoystickButton(gamepad, RobotMap.BTN_TESTCURVEPREFERENCES);
		reverseDrive = new JoystickButton(stickR, RobotMap.BTN_REVERSEDRIVE);
		
		gearAssist = new JoystickButton(stickR, RobotMap.BTN_GEARASSIST);
		shiftUp = new JoystickButton(stickR, RobotMap.BTN_SHIFTUP);
		shiftDown = new JoystickButton(stickL, RobotMap.BTN_SHIFTDOWN);
		
		aim = new JoystickButton(stickR, RobotMap.BTN_AIMSHOOT);
		aim = new JoystickButton(stickR, RobotMap.BTN_AIMDRIVE);
		
		//Create Commands
		wipeLPoints	= new WipeStopPointsL();
		wipeRPoints	= new WipeStopPointsR();
		addLPoint	= new AddPointL();
		addRPoint	= new AddPointR();
		testCurve	= new TestSpeedPoints();
		
		btn10 = new JoystickButton(stickR, 10);
		turn = new HoldAngleTest();
		turn.SetParam(Preferences.getInstance().getDouble("TestAngle Angle", 40));
		
		//Bind Joystick Buttons to Commands
		intakeIn.whileHeld(new BasicSpinMotor(Robot.btIntake, Preferences.getInstance().getDouble("Intake Up Speed", .6)));
		intakeOut.whileHeld(new BasicSpinMotor(Robot.btIntake, Preferences.getInstance().getDouble("Intake Down Speed", -.6)));
		climberUp.whileActive(new ClimbUp());
		hopperRun.whileHeld(new BasicSpinMotor(Robot.btHopper, Preferences.getInstance().getDouble("Hopper Stirrer PercentVBus", 0.5)));
		testShooterWheels.whileHeld(new RunWheelsManual());
		pinchGear.whileHeld(new BasicActuate(Robot.bcGearPinch));
		lowerGearActive.whileHeld(new BasicActuate(Robot.bcGearLift));
		lowerGearActive.whileHeld(new LowerGearPickup());
		spinIndexer.whileHeld(new RunIndexer());
		testCurvePreferences.whileHeld(new SpeedCurveShoot());
		reverseDrive.whenPressed(new ReverseDrive());
		gearAssist.whileHeld(new DriveToGear());
		
		shiftUp.whenPressed(new DriveShiftUp());
		shiftDown.whenPressed(new DriveShiftDown());
		
		aim.whileHeld(new CameraAim());
		aimDrive.whileHeld(new CameraDrive());
		
		btn10.whileHeld(turn);
		
		
		gearStartPos = new SendableChooser<>();
		gearStartPos.addObject("Left Peg", "Left");
		gearStartPos.addDefault("Center Peg", "Center");
		gearStartPos.addObject("Right Peg", "Right");
		SmartDashboard.putData("Gear Pos Chooser",gearStartPos);
		
		fieldSide = new SendableChooser<>();
		fieldSide.addObject("Blue Side", "Blue");
		fieldSide.addObject("Red Side", "Red");
		SmartDashboard.putData("Field Side",fieldSide);
		
		
		//Place Commands on SMD
		SmartDashboard.putData("Wipe Left Points", wipeLPoints);
		SmartDashboard.putData("Wipe Right Points", wipeRPoints);
		SmartDashboard.putData("Add Left Point", addLPoint);
		SmartDashboard.putData("Add Right Point", addRPoint);
		SmartDashboard.putData("Test Speed Curve", testCurve);
	}
}


package org.usfirst.frc.team3130.robot;


import org.usfirst.frc.team3130.robot.autoCommands.DriveToGear;
import org.usfirst.frc.team3130.robot.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
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
	private static JoystickTrigger climberDown;
	private static JoystickButton hopperRun;
	private static JoystickButton testShooterWheels;
	private static JoystickButton shieldGear;
	private static JoystickButton pinchGear;
	private static JoystickButton lowerGearActive;
	private static JoystickButton spinIndexer;

	private static JoystickButton shiftUp;
	private static JoystickButton shiftDown;
	private static JoystickButton gearAssist;
	
	//Define Commands
	WipeStopPointsL wipeLPoints;
	WipeStopPointsR wipeRPoints;
	AddPointL		addLPoint;
	AddPointR		addRPoint;
	TestSpeedPoints	testCurve;
	
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
		climberDown = new JoystickTrigger(gamepad, RobotMap.BTN_CLIMBERDOWN);
		hopperRun = new JoystickButton(gamepad, RobotMap.BTN_HOPPERDRIVE);
		testShooterWheels = new JoystickButton(gamepad, RobotMap.BTN_TESTSHOOTERWHEELS);
		shieldGear = new JoystickButton(gamepad, RobotMap.AXS_SHIELDGEAR);
		pinchGear = new JoystickButton(stickL, RobotMap.BTN_PINCHGEAR);
		lowerGearActive = new JoystickButton(stickR, RobotMap.BTN_LOWERGEARACTIVE);
		spinIndexer = new JoystickButton(gamepad, RobotMap.BTN_RUNINDEXER);
		
		gearAssist = new JoystickButton(stickR, RobotMap.BTN_GEARASSIST);
		shiftUp = new JoystickButton(stickR, RobotMap.BTN_SHIFTUP);
		shiftDown = new JoystickButton(stickL, RobotMap.BTN_SHIFTDOWN);
		
		//Create Commands
		wipeLPoints	= new WipeStopPointsL();
		wipeRPoints	= new WipeStopPointsR();
		addLPoint	= new AddPointL();
		addRPoint	= new AddPointR();
		testCurve	= new TestSpeedPoints();
		
		
		//Bind Joystick Buttons to Commands
		intakeIn.whileHeld(new IntakeUp());
		intakeOut.whileHeld(new IntakeDown());
		climberUp.whileActive(new ClimbUp());
		climberDown.whileActive(new ClimbDown());
		hopperRun.whileHeld(new DriveHopper());
		testShooterWheels.whileHeld(new RunWheelsManual());
		shieldGear.toggleWhenPressed(new BasicActuate(Robot.bcGearShield));
		pinchGear.whileHeld(new BasicActuate(Robot.bcGearPinch));
		lowerGearActive.whileHeld(new LowerGearPickup());
		spinIndexer.whileHeld(new RunIndexer());
		
		shiftUp.whenPressed(new DriveShiftUp());
		shiftDown.whenPressed(new DriveShiftDown());
		gearAssist.whileHeld(new DriveToGear());
		
		//Place Commands on SMD
		SmartDashboard.putData("Wipe Left Points", wipeLPoints);
		SmartDashboard.putData("Wipe Right Points", wipeRPoints);
		SmartDashboard.putData("Add Left Point", addLPoint);
		SmartDashboard.putData("Add Right Point", addRPoint);
		SmartDashboard.putData("Test Speed Curve", testCurve);
	}
}


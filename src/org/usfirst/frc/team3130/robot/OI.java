package org.usfirst.frc.team3130.robot;


import org.usfirst.frc.team3130.robot.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class OI {
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
	private static JoystickButton climberUp;
	private static JoystickButton climberDown;
	private static JoystickButton hopperRun;
	private static JoystickButton testShooterWheels;
	private static JoystickButton shieldGear;
	private static JoystickButton liftGear;
	private static JoystickButton pinchGear;
	private static JoystickButton doorGear;
	private static JoystickButton spinIndexer;
	
	private OI()
	{
		//Create Joysticks
		stickL = new Joystick(0);
		stickR = new Joystick(1);
		gamepad = new Joystick(2);
		
		//Create Joystick Buttons
		intakeIn = new JoystickButton(gamepad, RobotMap.BTN_INTAKEUP);
		intakeOut = new JoystickButton(gamepad, RobotMap.BTN_INTAKEDOWN);
		climberUp = new JoystickButton(gamepad, RobotMap.BTN_CLIMBERUP);
		climberDown = new JoystickButton(gamepad, RobotMap.BTN_CLIMBERDOWN);
		hopperRun = new JoystickButton(gamepad, RobotMap.BTN_HOPPERDRIVE);
		testShooterWheels = new JoystickButton(gamepad, RobotMap.BTN_TESTSHOOTERWHEELS);
		shieldGear = new JoystickButton(gamepad, RobotMap.BTN_SHIELDGEAR);
		liftGear = new JoystickButton(gamepad, RobotMap.BTN_LIFTGEAR);
		pinchGear = new JoystickButton(gamepad, RobotMap.BTN_PINCHGEAR);
		doorGear = new JoystickButton(gamepad, RobotMap.BTN_DOORGEAR);
		spinIndexer = new JoystickButton(gamepad, RobotMap.BTN_TESTSHOOTERWHEELS);
		
		//Bind Joystick Buttons to Commands
		intakeIn.whileHeld(new IntakeUp());
		intakeOut.whileHeld(new IntakeDown());
		climberUp.whileHeld(new ClimbUp());
		climberDown.whileHeld(new ClimbDown());
		hopperRun.whileHeld(new DriveHopper());
		testShooterWheels.whileHeld(new RunWheelsManual());
		shieldGear.toggleWhenActive(new ToggleGearTopShield());
		liftGear.whileHeld(new ActuateGearLift());
		pinchGear.toggleWhenActive(new ActuateGearPinch());
		doorGear.whileHeld(new ActuateGearDoors());
		spinIndexer.whileHeld(new RunIndexer());
	}

}


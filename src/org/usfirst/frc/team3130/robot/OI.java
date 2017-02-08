package org.usfirst.frc.team3130.robot;


import org.usfirst.frc.team3130.robot.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


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
		
		//Bind Joystick Buttons to Commands
		intakeIn.whileHeld(new IntakeUp());
		intakeOut.whileHeld(new IntakeDown());
		climberUp.whileHeld(new ClimbUp());
		climberDown.whileHeld(new ClimbDown());
		hopperRun.whileHeld(new DriveHopper());
		testShooterWheels.whileHeld(new TestWheels());
		shieldGear.toggleWhenActive(new ToggleGearTopShield());
	}

}


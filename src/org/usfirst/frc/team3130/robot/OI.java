package org.usfirst.frc.team3130.robot;

import org.usfirst.frc.team3130.robot.commands.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {
	//Instance Handling
    private static OI m_pInstance;
    public static OI GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new OI();
    	return m_pInstance;
    }
	
	//Create Joysticks
	public static Joystick stickL;
	public static Joystick stickR;
	public static Joystick gamepad;
	
	private OI()
	{
		stickL = new Joystick(0);
		stickR = new Joystick(1);
		gamepad = new Joystick(2);
		
	}

}


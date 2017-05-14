package org.usfirst.frc.team3130.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team3130.robot.RobotMap;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;

public class Flashlight extends Subsystem {
    
	private static Flashlight m_pInstance;
	
	public static Flashlight GetInstance()
	{
		if(m_pInstance == null) m_pInstance = new Flashlight();
    	return m_pInstance;
	}
	
	
	//Create and define necessary Objects
	private static Relay flashlight;
	
	private Flashlight()
	{
		if(flashlight == null)	flashlight = new Relay(RobotMap.RLY_FLASHLIGHT);
	}
	
	public static void Activate(boolean on)
	{
		if(on){
			flashlight.set(Value.kForward);
		}else{
			flashlight.set(Value.kOff);
		}
	}
	// get current state of the relay
	public static boolean getActivate(){
		if( flashlight.get()==Value.kOff ){
			return false;
		}else{
			return true;
		}
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
package org.usfirst.frc.team3130.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.*;
import org.usfirst.frc.team3130.robot.RobotMap;
import org.usfirst.frc.team3130.robot.commands.ShooterAltitudeAdjust;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

public class ShooterAltitude extends Subsystem {
	
	//Define constants
	private static final double RATIO_TICKSPERINCH = 7*71*Math.PI*(1.9+.05);	//Winch Diamater ~1.9, Rope Radius ~.05
	public static final double TOP_ALT = 26;

	//Instance Handling
    private static ShooterAltitude m_pInstance;
    public static ShooterAltitude GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new ShooterAltitude();
    	return m_pInstance;
    }
    
    //Define Objects
    private static CANTalon m_altitudeController;
    
    
    //Define basic memory types
    
    
    
    private ShooterAltitude() {
    	//Initialize Basic data types
    	
    	
    	//Set up Talon
    	m_altitudeController = new CANTalon(RobotMap.CAN_SHOOTERALTITUDE);
    	m_altitudeController.changeControlMode(TalonControlMode.Position);
    	m_altitudeController.enableForwardSoftLimit(true);
    	m_altitudeController.setForwardSoftLimit(TOP_ALT);
    	
    	
    }

    //Set the default command to ShooterAltitudeAdjust
    public void initDefaultCommand() {
    	setDefaultCommand(new ShooterAltitudeAdjust());
    }
    
  //Getters
    //Check if shooter is at the bottom altitude
    public static boolean isAtBottom()
    {
    	return m_altitudeController.isRevLimitSwitchClosed();
    }
    
    //Get position of the canTalon
    public static double getAltitude(){
    	return m_altitudeController.getPosition();
    }
    
    //Make Talon go to a certain position 
    public static void setAltitude(double goal){
    	
    }
    
    //Increase the Altitude by set amount
    public static void increaseAltitude(double increase){
    	setAltitude(getAltitude() + increase);
    }
    
    //Decrease the Altitude by set amount
    public static void decreaseAltitude(double decrease){
    	setAltitude(getAltitude() - decrease);
    }
    
}


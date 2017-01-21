package org.usfirst.frc.team3130.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CANSpeedController.ControlMode;

import org.usfirst.frc.team3130.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

public class ShooterWheels extends Subsystem {

	//Instance Handling
    private static ShooterWheels m_pInstance;
    /**
     * A system for getting an instance of this class.
     * The function provides a method by which the class is setup as a singleton
     * with only a single copy of it existing in memory.
     * <p> It will return a reference to the class, which is shared amoungst all callers of GetInstance()
     * 
     * @return the reference to the class refered to in GetInstance. In this case, ShooterWheels.
     */
    public static ShooterWheels GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new ShooterWheels();
    	return m_pInstance;
    }
    
    private static CANTalon m_wheelControl;
    
    private ShooterWheels() {
    	m_wheelControl = new CANTalon(RobotMap.CAN_SHOOTERWHEELS);
    	m_wheelControl.changeControlMode(TalonControlMode.Speed);
    	
    	LiveWindow.addActuator("Shooter", "Wheels", m_wheelControl);
    }

    public void initDefaultCommand() {
        
    }
    
    public static double getSpeed()
    {
    	return m_wheelControl.get();
    }
    
    /**
     * Sets the speed to spin the Wheels at. 
     * <p> <b> THIS IS NOT THE USUAL VOLTAGE PERCENTAGE </b>
     * <br>This function instead sets an actual rotational velocity.
     * @param speed is the speed in rotations per second
     */
    public static void setSpeed(double speed)
    {
    	m_wheelControl.set(speed*100);	//Convert from 10 milliseconds to 1 second
    }
}


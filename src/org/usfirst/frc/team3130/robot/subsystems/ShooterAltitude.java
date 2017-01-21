package org.usfirst.frc.team3130.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.*;
import org.usfirst.frc.team3130.robot.RobotMap;

import com.ctre.CANTalon;

public class ShooterAltitude extends Subsystem {

	//Instance Handling
    private static ShooterAltitude m_pInstance;
    /**
     * A system for getting an instance of this class.
     * The function provides a method by which the class is setup as a singleton
     * with only a single copy of it existing in memory.
     * <p> It will return a reference to the class, which is shared amoungst all callers of GetInstance()
     * 
     * @return the reference to the class refered to in GetInstance. In this case, ShooterAltitude.
     */
    public static ShooterAltitude GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new ShooterAltitude();
    	return m_pInstance;
    }
    
    CANTalon m_altitudeControler;
    
    private ShooterAltitude() {
    	m_altitudeControler = new CANTalon(RobotMap.CAN_SHOOTERALTITUDE);
    }

    public void initDefaultCommand() {
        
    }
}


package org.usfirst.frc.team3130.robot.subsystems;

import org.usfirst.frc.team3130.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.*;

import com.ctre.CANTalon;

public class ShooterTurret extends Subsystem {

	//Instance Handling
    private static ShooterTurret m_pInstance;
    /**
     * A system for getting an instance of this class.
     * The function provides a method by which the class is setup as a singleton
     * with only a single copy of it existing in memory.
     * <p> It will return a reference to the class, which is shared amoungst all callers of GetInstance()
     * 
     * @return the reference to the class refered to in GetInstance. In this case, ShooterTurret.
     */
    public static ShooterTurret GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new ShooterTurret();
    	return m_pInstance;
    }
    
    private static CANTalon m_shooterTurret;
    
    private ShooterTurret() {
    	
    	m_shooterTurret = new CANTalon(RobotMap.CAN_SHOOTERTURRET);
    	
    }
	
    public void initDefaultCommand() {
        
    }
}


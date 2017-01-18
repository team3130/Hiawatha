package org.usfirst.frc.team3130.robot.subsystems;

import org.usfirst.frc.team3130.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.*;

import com.ctre.CANTalon;

public class ShooterTurret extends Subsystem {

	private static ShooterTurret m_pInstance;
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


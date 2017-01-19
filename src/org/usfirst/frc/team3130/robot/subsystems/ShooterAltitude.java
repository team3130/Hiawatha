package org.usfirst.frc.team3130.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.*;
import org.usfirst.frc.team3130.robot.RobotMap;

public class ShooterAltitude extends Subsystem {

	private static ShooterAltitude m_pInstance;
    public static ShooterAltitude GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new ShooterAltitude();
    	return m_pInstance;
    }
    
    private ShooterAltitude() {
    	
    }

    public void initDefaultCommand() {
        
    }
}


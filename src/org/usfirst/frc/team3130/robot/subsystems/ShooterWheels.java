package org.usfirst.frc.team3130.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.*;
import org.usfirst.frc.team3130.robot.RobotMap;

public class ShooterWheels extends Subsystem {

	private static ShooterWheels m_pInstance;
    public static ShooterWheels GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new ShooterWheels();
    	return m_pInstance;
    }
    
    private ShooterWheels() {
    	
    }

    public void initDefaultCommand() {
        
    }
}


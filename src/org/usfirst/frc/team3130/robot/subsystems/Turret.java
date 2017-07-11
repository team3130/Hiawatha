package org.usfirst.frc.team3130.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Turret extends Subsystem {

	//Instance Handling
    private static Turret m_pInstance;
    /**
     * A system for getting an instance of this class.
     * The function provides a method by which the class is setup as a singleton
     * with only a single copy of it existing in memory.
     * <p> It will return a reference to the class, which is shared amongst all callers of GetInstance()
     * 
     * @return the reference to the class referred to in GetInstance. In this case, ShooterWheelsRight.
     */
    public static Turret GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new Turret();
    	return m_pInstance;
    }

    public void initDefaultCommand() {
        
    }
}


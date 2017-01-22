package org.usfirst.frc.team3130.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearGrabber extends Subsystem {

	//Instance Handling
    private static GearGrabber m_pInstance;
    /**
     * A system for getting an instance of this class.
     * The function provides a method by which the class is setup as a singleton
     * with only a single copy of it existing in memory.
     * <p> It will return a reference to the class, which is shared amoungst all callers of GetInstance()
     * 
     * @return the reference to the class refered to in GetInstance. In this case, GearGrabber.
     */
    public static GearGrabber GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new GearGrabber();
    	return m_pInstance;
    }
    
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}


package org.usfirst.frc.team3130.robot.subsystems;

import org.usfirst.frc.team3130.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearTopShield extends Subsystem {

	//Instance Handling
    private static GearTopShield m_pInstance;
    /**
     * A system for getting an instance of this class.
     * The function provides a method by which the class is setup as a singleton
     * with only a single copy of it existing in memory.
     * <p> It will return a reference to the class, which is shared amoungst all callers of GetInstance()
     * 
     * @return the reference to the class refered to in GetInstance. In this case, GearTopShield.
     */
    public static GearTopShield GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new GearTopShield();
    	return m_pInstance;
    }
    
    //Creation of the Required Objects
    private static Solenoid m_liftWheel;
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    private GearTopShield()
    {
    	m_liftWheel = new Solenoid(RobotMap.CAN_PNMMODULE, RobotMap.PNM_TOPGEARSHIELD);
    }
    
    /**
     * Extends the shield to allow balls over the gear intake
     * <p>Actuates the cylinder that moves a shield over the gear intake drop slot, 
     * allowing balls to be intook into the hopper over the passive gear intake, 
     * allowing the robot to intake both balls and gears passively from the same side.
     * @param extend to extend the cylinder or not, true extends, false retracts
     */
    public static void ActuateSheild(boolean extend)
    {
    	m_liftWheel.set(extend);
    }
}


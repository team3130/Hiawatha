package org.usfirst.frc.team3130.robot.subsystems;

import org.usfirst.frc.team3130.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
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
    
    private static Solenoid m_pinchSolenoid;
    private static Solenoid m_doorSolenoid;
    private static Solenoid m_liftSolenoid;
    
    private GearGrabber()
    {
    	m_pinchSolenoid = new Solenoid(RobotMap.PNM_GEARPINCH);
    	m_doorSolenoid  = new Solenoid(RobotMap.PNM_GEARDOOR);
    	m_liftSolenoid = new Solenoid(RobotMap.PNM_GEARLIFT);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    /**
     * Actuates the gear pinch system
     * <p>Controls the movement of the gear pinch cylinder. The value passed to the function changes the state of the cylinder, 
     * with true extending the cylinder, and false retracting it.
     * </p>
     * @param extend to extend the cylinder or not, true extends, false retracts
     */
    public static void pinchActuate(boolean actuate)
    {
    	m_pinchSolenoid.set(actuate);
    }
    
    /**
     * Actuates the gear door system
     * <p>Controls the movement of the gear door cylinder. The value passed to the function changes the state of the cylinder, 
     * with true extending the cylinder, and false retracting it.
     * </p>
     * @param extend to extend the cylinder or not, true extends, false retracts
     */
    public static void doorActuate(boolean actuate)
    {
    	m_doorSolenoid.set(actuate);
    }
    
    /**
     * Actuates the gear lift system
     * <p>Controls the movement of the gear lift cylinder. The value passed to the function changes the state of the cylinder, 
     * with true extending the cylinder, and false retracting it.
     * </p>
     * @param extend to extend the cylinder or not, true extends, false retracts
     */
    public static void liftActuate(boolean actuate)
    {
    	m_liftSolenoid.set(actuate);
    }
}


package org.usfirst.frc.team3130.robot.subsystems;

import org.usfirst.frc.team3130.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class TurnWheelExtender extends Subsystem {

	//Instance Handling
    private static TurnWheelExtender m_pInstance;
    /**
     * A system for getting an instance of this class.
     * The function provides a method by which the class is setup as a singleton
     * with only a single copy of it existing in memory.
     * <p> It will return a reference to the class, which is shared amoungst all callers of GetInstance()
     * 
     * @return the reference to the class refered to in GetInstance. In this case, TurnWheelExtender.
     */
    public static TurnWheelExtender GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new TurnWheelExtender();
    	return m_pInstance;
    }
    
    //Creation of the Required Objects
    private static Solenoid m_liftWheel;
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    private TurnWheelExtender()
    {
    	m_liftWheel = new Solenoid(RobotMap.CAN_PNMMODULE, RobotMap.PNM_LIFTWHEEL);
    }
    
    /**
     * Extends the wheel that facilitates easier turning
     * <p>Actuates the cylinder that has the wheel mounted to it, so as to make turning the robot easier, at the cost of traction
     * @param extend to extend the cylinder or not, true extends, false retracts
     */
    public static void ActuateWheel(boolean extend)
    {
    	m_liftWheel.set(extend);
    }
}


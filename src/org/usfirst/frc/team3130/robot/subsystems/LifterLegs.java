package org.usfirst.frc.team3130.robot.subsystems;

import org.usfirst.frc.team3130.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LifterLegs extends Subsystem {

	//Instance Handling
    private static LifterLegs m_pInstance;
    /**
     * A system for getting an instance of this class.
     * The function provides a method by which the class is setup as a singleton
     * with only a single copy of it existing in memory.
     * <p> It will return a reference to the class, which is shared amoungst all callers of GetInstance()
     * 
     * @return the reference to the class refered to in GetInstance. In this case, LifterLegs.
     */
    public static LifterLegs GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new LifterLegs();
    	return m_pInstance;
    }
    
    //Creation of the Required Objects
    private static Solenoid m_leg1;
    private static Solenoid m_leg2;
    private static Solenoid m_leg3;
    private static Solenoid m_leg4;
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    private LifterLegs()
    {
    	m_leg1 = new Solenoid(RobotMap.CAN_PNMMODULE, RobotMap.PNM_LIFTLEG1);
    	m_leg2 = new Solenoid(RobotMap.CAN_PNMMODULE, RobotMap.PNM_LIFTLEG2);
    	m_leg3 = new Solenoid(RobotMap.CAN_PNMMODULE, RobotMap.PNM_LIFTLEG3);
    	m_leg4 = new Solenoid(RobotMap.CAN_PNMMODULE, RobotMap.PNM_LIFTLEG4);
    }
    
    public static void ActuateLegs(boolean extend)
    {
    	m_leg1.set(extend);
    	m_leg2.set(extend);
    	m_leg3.set(extend);
    	m_leg4.set(extend);
    }
}


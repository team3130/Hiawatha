package org.usfirst.frc.team3130.robot.subsystems;

import org.usfirst.frc.team3130.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Hopper extends Subsystem {

	//Instance Handling
    private static Hopper m_pInstance;
    /**
     * A system for getting an instance of this class.
     * The function provides a method by which the class is setup as a singleton
     * with only a single copy of it existing in memory.
     * <p> It will return a reference to the class, which is shared amoungst all callers of GetInstance()
     * 
     * @return the reference to the class refered to in GetInstance. In this case, Hopper.
     */
    public static Hopper GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new Hopper();
    	return m_pInstance;
    }

    //Define necessary objects
    private static CANTalon m_hopperStirrer;
    private static CANTalon m_hopperIndexer;
    
    private Hopper()
    {
    	//instantiate necessary objects
    	m_hopperStirrer = new CANTalon(RobotMap.CAN_HOPPERSTIR);
    	m_hopperIndexer = new CANTalon(RobotMap.CAN_HOPPERINDEX);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    /**
     * Drives the hopper stirrer
     * <p> This function will drive the hopper stirrer. The function takes a value from -1.0 to 1.0 which is the percentage of the 
     * voltage provided to the talon which should be passed on to the stirrer motor.
     * @param percent the percentage of the voltage available to the talon to drive at
     */
    public static void driveHopperStirrer(double percent)
    {
    	m_hopperStirrer.set(percent);
    }
    
    /**
     * Drives the hopper indexer
     * <p> This function will drive the hopper indexer. The function takes a value from -1.0 to 1.0 which is the percentage of the 
     * voltage provided to the talon which should be passed on to the indexer motor.
     * @param percent the percentage of the voltage available to the talon to drive at
     */
    public static void driveHopperIndexer(double percent)
    {
    	m_hopperIndexer.set(percent);
    }
}


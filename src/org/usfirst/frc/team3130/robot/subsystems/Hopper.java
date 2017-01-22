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
    private static CANTalon m_hopperMotor1;
    private static CANTalon m_hopperMotor2;
    private static CANTalon m_fuelTransfer;
    
    private Hopper()
    {
    	//instantiate necessary objects
    	m_hopperMotor1 = new CANTalon(RobotMap.CAN_HOPPERMOTOR1);
    	m_hopperMotor2 = new CANTalon(RobotMap.CAN_HOPPERMOTOR2);
    	m_fuelTransfer = new CANTalon(RobotMap.CAN_FUELTRANSFERMOTOR);
    	m_hopperMotor2.reverseOutput(true);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    /**
     * Drives the hopper belts
     * <p> This function will drive the hopper belts in opposite directions, with the bottom one being driven forward, 
     * and the top being driven backwards. The function takes a value from -1.0 to 1.0 which is the percentage of the 
     * voltage provided to the talon which should be passed on to the belt motors.
     * <br> It also drives the motor transporting balls between the hopper and the shooter
     * @param percent the percentage of the voltage available to the talon to drive at
     */
    public static void driveHopper(double percent)
    {
    	m_hopperMotor1.set(percent);
    	m_hopperMotor2.set(percent);
    	m_fuelTransfer.set(percent);
    }
}


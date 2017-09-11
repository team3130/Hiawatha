package org.usfirst.frc.team3130.robot.subsystems;

import org.usfirst.frc.team3130.robot.RobotMap;
import org.usfirst.frc.team3130.robot.commands.ClimbUp;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Climber
 */
public class Climber extends Subsystem {

	//Instance Handling
    private static Climber m_pInstance;
    /**
     * A system for getting an instance of this class.
     * The function provides a method by which the class is setup as a singleton
     * with only a single copy of it existing in memory.
     * <p> It will return a reference to the class, which is shared amoungst all callers of GetInstance()
     * 
     * @return the reference to the class refered to in GetInstance. In this case, Climber.
     */
    public static Climber GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new Climber();
    	return m_pInstance;
    }

    //Creation of Required Objects
    private static CANTalon m_liftMotor1;
    private static CANTalon m_liftMotor2;
    
    public void initDefaultCommand() {
        setDefaultCommand(new ClimbUp());
    }
    
    //constructor
    private Climber()
    {
    	m_liftMotor1 = new CANTalon(RobotMap.CAN_CLIMBERMOTOR1);
    	m_liftMotor2 = new CANTalon(RobotMap.CAN_CLIMBERMOTOR2);
    	m_liftMotor1.enableBrakeMode(false);
    	m_liftMotor2.enableBrakeMode(false);
    }
    
    /**
     * Controls the spinning of the Climber Mechanism.
     * @param percentage the percentage of the provided voltage to the motor controllers that is passed to the motors
     */
    public static void Climb(double percentage)
    {
    	m_liftMotor1.set(percentage);
    	m_liftMotor2.set(percentage);
    }
    
    /**
     * Returns the current in the lifting motor
     * @return Current in amperes of the climbing motor
     */
    public static double getLiftAmp() {
    	return m_liftMotor1.getOutputCurrent();
    }
}


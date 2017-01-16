package org.usfirst.frc.team3130.robot.subsystems;

import org.usfirst.frc.team3130.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {

	//Instance Handling
    private static Intake m_pInstance;
    /**
     * A system for getting an instance of this class.
     * The function provides a method by which the class is setup as a singleton
     * with only a single copy of it existing in memory.
     * <p> It will return a reference to the class, which is shared amoungst all callers of GetInstance()
     * 
     * @return the reference to the class refered to in GetInstance. In this case, Intake.
     */
    public static Intake GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new Intake();
    	return m_pInstance;
    }
	
	private static CANTalon m_intakeMotor1;
	private static CANTalon m_intakeMotor2;

	private Intake()
	{
		m_intakeMotor1 = new CANTalon(RobotMap.CAN_INTAKEMOTOR1);
		m_intakeMotor2 = new CANTalon(RobotMap.CAN_INTAKEMOTOR2);
		m_intakeMotor2.reverseOutput(true);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    /**
     * Sets the speed of the intakeWheels.
     * The wheels will spin in opposite directions, such that they are both spinning upwards at the gap.
     * @param speed the percentage of the voltage being fed to the controlers to pass on to the motors.
     */
    public static void spinIntake(double speed)
    {
    	m_intakeMotor1.set(speed);
    	m_intakeMotor2.set(speed);
    }
}


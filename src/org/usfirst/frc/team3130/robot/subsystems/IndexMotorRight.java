package org.usfirst.frc.team3130.robot.subsystems;

import org.usfirst.frc.team3130.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class IndexMotorRight extends Subsystem {

	//Instance Handling
    private static IndexMotorRight m_pInstance;
    /**
     * A system for getting an instance of this class.
     * The function provides a method by which the class is setup as a singleton
     * with only a single copy of it existing in memory.
     * <p> It will return a reference to the class, which is shared amoungst all callers of GetInstance()
     * 
     * @return the reference to the class refered to in GetInstance. In this case, IndexMotorLeft.
     */
    public static IndexMotorRight GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new IndexMotorRight();
    	return m_pInstance;
    }

    //Define necessary objects
    private static CANTalon m_indexMotor;
    
    private IndexMotorRight()
    {
    	//instantiate necessary objects
    	m_indexMotor = new CANTalon(RobotMap.CAN_INDEXMOTORRIGHT);
    	LiveWindow.addActuator("Index", "Index Motor Right", m_indexMotor);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    /**
     * Drives the index motor
     * <p> This function will drive the index motor. The function takes a value from -1.0 to 1.0 which is the percentage of the 
     * voltage provided to the talon which should be passed on to the index motor.
     * @param percent the percentage of the voltage available to the talon to drive at
     */
    public static void driveIndexMotor(double percent)
    {
    	m_indexMotor.set(-percent);
    }
}


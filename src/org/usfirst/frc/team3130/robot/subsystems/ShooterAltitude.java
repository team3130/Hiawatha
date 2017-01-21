package org.usfirst.frc.team3130.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3130.robot.RobotMap;
import org.usfirst.frc.team3130.robot.commands.ShooterAltitudeAdjust;

public class ShooterAltitude extends Subsystem {
	
	//Define constants
	public static final double TOP_ALT = 26;

	//Instance Handling
    private static ShooterAltitude m_pInstance;
    /**
     * A system for getting an instance of this class.
     * The function provides a method by which the class is setup as a singleton
     * with only a single copy of it existing in memory.
     * <p> It will return a reference to the class, which is shared amoungst all callers of GetInstance()
     * 
     * @return the reference to the class refered to in GetInstance. In this case, ShooterAltitude.
     */
    public static ShooterAltitude GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new ShooterAltitude();
    	return m_pInstance;
    }
    
    //Define Objects
    private static Servo m_altitudeController;
    
    
    //Define basic memory types
    
    
    
    private ShooterAltitude() {
    	//Initialize Basic data types
    	
    	
    	//Set up Servo
    	m_altitudeController = new Servo(RobotMap.PWM_SHOOTERALTITUDE);

    }

    //Set the default command to ShooterAltitudeAdjust
    public void initDefaultCommand() {
    	setDefaultCommand(new ShooterAltitudeAdjust());
    }
    
    
    /**
     * Returns the current position of the servo is degrees
     * @return the current angle of the servo
     */
    public static double getAltitude()
    {
    	return m_altitudeController.getAngle();
    }
    
    /**
     * Sets the angle of the Shooter Altitude Axis
     * @param angle is the angle to set the servo to
     */
    public static void setAltitude(double angle)
    {
    	m_altitudeController.setAngle(angle);
    }
    
    /**
     * Sets the angle of the altitude relative to the angle it is currently at
     * <p> A positive value of adjust will lead to the angle increasing,
     * <br> while a negative value will decrease the angle.
     * @param adjust is the degrees by which to change the altitude
     */
    public static void adjustAltitude(double adjust)
    {
    	setAltitude(adjust + getAltitude());
    }
    
}


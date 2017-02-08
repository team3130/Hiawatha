package org.usfirst.frc.team3130.robot.subsystems;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team3130.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

public class ShooterWheels extends Subsystem {

	//Instance Handling
    private static ShooterWheels m_pInstance;
    /**
     * A system for getting an instance of this class.
     * The function provides a method by which the class is setup as a singleton
     * with only a single copy of it existing in memory.
     * <p> It will return a reference to the class, which is shared amoungst all callers of GetInstance()
     * 
     * @return the reference to the class refered to in GetInstance. In this case, ShooterWheels.
     */
    public static ShooterWheels GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new ShooterWheels();
    	return m_pInstance;
    }
    
    private static CANTalon m_wheelControl;
    
    private ShooterWheels() {
    	m_wheelControl = new CANTalon(RobotMap.CAN_SHOOTERWHEELS);
    	m_wheelControl.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	m_wheelControl.configEncoderCodesPerRev(18);	//After going through gear ratio 18 ticks per rev
    	
    	LiveWindow.addActuator("Shooter", "Wheels", m_wheelControl);
    }

    public void initDefaultCommand() {
        
    }
    
    /**
     * Returns the current RPS of the wheels
     * @return the current angular velocity in revolutions per something????
     */
    public static double getSpeed()
    {
    	return m_wheelControl.getSpeed() * (600.0/18.0);	//convert /centiseconds to /seconds
    }
    
    
    /**
     * Sets the speed to spin the Wheels at. 
     * <p> <b> THIS IS NOT THE USUAL VOLTAGE PERCENTAGE </b>
     * <br>This function instead sets an actual rotational velocity.
     * @param speed is the speed in rotations per something????
     */
    public static void setSpeed(double speed)
    {
    	m_wheelControl.changeControlMode(TalonControlMode.Speed);
    	m_wheelControl.set(speed / (600.0/18.0));	//Convert from a speed in seconds to centiseconds
    }
    
    /**
     * Returns the voltage being output by the shooter wheels talon
     * @return voltage output of talon, in volts
     */
    public static double GetVolt() {
    	return m_wheelControl.getOutputVoltage();
    }
    
    /**
     * Returns the current going through the shooter wheels talon
     * @return current going through talon, in Amperes
     */
    public static double GetCurrent() {
    	return m_wheelControl.getOutputCurrent();
    }
    
    public static double GetSetpoint() {
    	return m_wheelControl.getSetpoint() * (600.0/18.0);
    }
    
    public static void setPID() {
    	System.out.println("setting PID...");
    	m_wheelControl.setPID(
    			Preferences.getInstance().getDouble("Shooter P", 1), 
    			Preferences.getInstance().getDouble("Shooter I", 0), 
    			Preferences.getInstance().getDouble("Shooter D", 0),
    			Preferences.getInstance().getDouble("Shooter F", 0),
    			0,
    			Preferences.getInstance().getDouble("Shooter Max Ramp", 0),
    			0
    		); //TODO:Tune PID Numbers
    }
    
    public static void stop()
    {
    	m_wheelControl.changeControlMode(TalonControlMode.PercentVbus);
    	m_wheelControl.set(0);
    }
    
}


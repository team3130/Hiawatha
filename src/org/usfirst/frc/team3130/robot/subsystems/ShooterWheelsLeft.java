package org.usfirst.frc.team3130.robot.subsystems;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team3130.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

public class ShooterWheelsLeft extends Subsystem {

	//Instance Handling
    private static ShooterWheelsLeft m_pInstance;
    /**
     * A system for getting an instance of this class.
     * The function provides a method by which the class is setup as a singleton
     * with only a single copy of it existing in memory.
     * <p> It will return a reference to the class, which is shared amoungst all callers of GetInstance()
     * 
     * @return the reference to the class refered to in GetInstance. In this case, ShooterWheelsLeft.
     */
    public static ShooterWheelsLeft GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new ShooterWheelsLeft();
    	return m_pInstance;
    }
    
    private static CANTalon m_wheelControl;
    
    private ShooterWheelsLeft() {
    	m_wheelControl = new CANTalon(RobotMap.CAN_SHOOTERWHEELSLEFT);
    	m_wheelControl.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	m_wheelControl.configEncoderCodesPerRev(18);	//After going through gear ratio 18 ticks per rev
    	m_wheelControl.reverseSensor(true);
    	
    	LiveWindow.addActuator("Shooter", "Wheels Left", m_wheelControl);
    }

    public void initDefaultCommand() {
        
    }
    
    /**
     * Returns the current RPS of the wheels
     * @return the current angular velocity in revolutions per something????
     */
    public static double getSpeed()
    {
    	return m_wheelControl.getSpeed() * -4.0;	//convert /centiseconds to /seconds
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
    	m_wheelControl.set(speed / -4.0);	//Convert from a speed in seconds to centiseconds
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
    	return m_wheelControl.getSetpoint() * -4.0;
    }
    
    public static double GetError() {
    	return GetSetpoint() - getSpeed();
    }
    
    public static double GetPosition()
    {
    	return m_wheelControl.getPosition();
    }
    
    public static void setPID() {
    	m_wheelControl.setPID(
    			Preferences.getInstance().getDouble("Left Shooter P", 40.0), //These are the numbers for the shooter with red tape
    			Preferences.getInstance().getDouble("Left Shooter I", 0.0001), 
    			Preferences.getInstance().getDouble("Left Shooter D", 4000),
    			Preferences.getInstance().getDouble("Left Shooter F", 4.0),
    			0,
    			Preferences.getInstance().getDouble("Left Shooter Max Ramp", 0),
    			0
    		); //TODO:Tune PID Numbers
    	
    }
    
    public static void stop()
    {
    	m_wheelControl.changeControlMode(TalonControlMode.PercentVbus);
    	m_wheelControl.set(0);
    }
    
}


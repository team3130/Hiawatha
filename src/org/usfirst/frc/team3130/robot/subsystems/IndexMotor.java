package org.usfirst.frc.team3130.robot.subsystems;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team3130.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

public class IndexMotor extends Subsystem {

	//Instance Handling
    private static IndexMotor m_pInstance;
    /**
     * A system for getting an instance of this class.
     * The function provides a method by which the class is setup as a singleton
     * with only a single copy of it existing in memory.
     * <p> It will return a reference to the class, which is shared amoungst all callers of GetInstance()
     * 
     * @return the reference to the class refered to in GetInstance. In this case, IndexMotor.
     */
    public static IndexMotor GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new IndexMotor();
    	return m_pInstance;
    }
    
    private static CANTalon m_indexControl;
    
    private IndexMotor() {
    	m_indexControl = new CANTalon(RobotMap.CAN_INDEXMOTOR);
    	m_indexControl.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	m_indexControl.configEncoderCodesPerRev(18);	//After going through gear ratio 18 ticks per rev
    	m_indexControl.reverseSensor(true);
    	
    	LiveWindow.addActuator("Shooter", "Wheels", m_indexControl);
    }

    public void initDefaultCommand() {
        
    }
    
    /**
     * Returns the current RPS of the wheels
     * @return the current angular velocity in revolutions per something????
     */
    public static double getSpeed()
    {
    	return m_indexControl.getSpeed() * 4.0;	//convert /centiseconds to /seconds
    }
    
    
    /**
     * Sets the speed to spin the Wheels at. 
     * <p> <b> THIS IS NOT THE USUAL VOLTAGE PERCENTAGE </b>
     * <br>This function instead sets an actual rotational velocity.
     * @param speed is the speed in rotations per something????
     */
    public static void setSpeed(double speed)
    {
    	m_indexControl.changeControlMode(TalonControlMode.Speed);
    	m_indexControl.set(speed / 4.0);	//Convert from a speed in seconds to centiseconds
    }
    
    /**
     * Returns the voltage being output by the shooter wheels talon
     * @return voltage output of talon, in volts
     */
    public static double GetVolt() {
    	return m_indexControl.getOutputVoltage();
    }
    
    /**
     * Returns the current going through the shooter wheels talon
     * @return current going through talon, in Amperes
     */
    public static double GetCurrent() {
    	return m_indexControl.getOutputCurrent();
    }
    
    public static double GetSetpoint() {
    	return m_indexControl.getSetpoint() * 4.0;
    }
    
    public static void setPID() {
    	System.out.println("setting PID...");
    	m_indexControl.setPID(
    			Preferences.getInstance().getDouble("Shooter P", 10.0), 
    			Preferences.getInstance().getDouble("Shooter I", 0.01), 
    			Preferences.getInstance().getDouble("Shooter D", 0),
    			Preferences.getInstance().getDouble("Shooter F", 4.0),
    			0,
    			Preferences.getInstance().getDouble("Shooter Max Ramp", 0),
    			0
    		); //TODO:Tune PID Numbers
    }
    
    public static void stop()
    {
    	m_indexControl.changeControlMode(TalonControlMode.PercentVbus);
    	m_indexControl.set(0);
    }
}


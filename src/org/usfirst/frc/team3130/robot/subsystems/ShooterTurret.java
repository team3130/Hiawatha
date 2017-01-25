package org.usfirst.frc.team3130.robot.subsystems;

import org.usfirst.frc.team3130.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.*;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

public class ShooterTurret extends Subsystem {

	//Instance Handling
    private static ShooterTurret m_pInstance;
    /**
     * A system for getting an instance of this class.
     * The function provides a method by which the class is setup as a singleton
     * with only a single copy of it existing in memory.
     * <p> It will return a reference to the class, which is shared amoungst all callers of GetInstance()
     * 
     * @return the reference to the class refered to in GetInstance. In this case, ShooterTurret.
     */
    public static ShooterTurret GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new ShooterTurret();
    	return m_pInstance;
    }
    
    private static final int RATIO_TICKSTODEGREE = 0;	//TODO: Find actual ratio
    
    private static CANTalon m_shooterTurret;
    
    
    private ShooterTurret() {
    	
    	m_shooterTurret = new CANTalon(RobotMap.CAN_SHOOTERTURRET);
    	m_shooterTurret.configEncoderCodesPerRev(RATIO_TICKSTODEGREE);
    	m_shooterTurret.setForwardSoftLimit(Preferences.getInstance().getDouble("Turret programatic angle limit", 10));
    	m_shooterTurret.setReverseSoftLimit(-Preferences.getInstance().getDouble("Turret programatic angle limit", 10));
    	
    }
	
    public void initDefaultCommand() {
        
    }
    
    /**
     * Allows manual control of the turret
     * <p> Manually drives the turret in PercentVbus, so the units provided are the percentage of the voltage available 
     * to the srx. The turret won't turn past 190 degrees from zero in each direction, the same limit as is defined by setSetpoint().
     * @param percentage the percentage of the provided voltage to the motor controllers that is passed to the motors
     */
    public static void moveTurret(double percentage)
    {
    	m_shooterTurret.changeControlMode(TalonControlMode.PercentVbus);
    	m_shooterTurret.set(percentage);
    }
    
    /**
     * Sets the angle to turn the turret to.
     * <p> This function will set the angle to the relative position from where it currently is.
     * <br> The function will return false if the angle needed to wrap around rather than being set due to the resultant angle being out of range.
     * @param angle
     * @return
     */
    public static boolean setSetpoint(double angle)
    {
    	m_shooterTurret.changeControlMode(TalonControlMode.Position);
    	double setpoint = m_shooterTurret.getPosition() + angle;
    	if(Math.abs(setpoint) > Preferences.getInstance().getDouble("Turret programatic angle limit", 10)){
    		return false;
    	}
    	m_shooterTurret.setSetpoint(setpoint);
    	return true;
    }
}


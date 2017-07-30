package org.usfirst.frc.team3130.robot.subsystems;

import org.usfirst.frc.team3130.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class TurretAdjust extends Subsystem {

	//Instance Handling
	private static TurretAdjust m_pInstance;
	/**
	 * A system for getting an instance of this class.
	 * The function provides a method by which the class is setup as a singleton
	 * with only a single copy of it existing in memory.
	 * <p> It will return a reference to the class, which is shared amongst all callers of GetInstance()
	 * 
	 * @return the reference to the class referred to in GetInstance. In this case, ShooterWheelsRight.
	 */
	public static TurretAdjust GetInstance()
	{
		if(m_pInstance == null) m_pInstance = new TurretAdjust();
		return m_pInstance;
	}
	
	private static CANTalon turretAdjust;
	
	public void initDefaultCommand() {


	}
	
	private TurretAdjust(){
		turretAdjust = new CANTalon(RobotMap.CAN_TURRETADJUST);
		
		
		turretAdjust.changeControlMode(CANTalon.TalonControlMode.PercentVbus);

	}


	/**
	 * Goes to a position
	 * @param angle the angle to go to in radians
	 */
	void ToSetpoint(int angle)
	{
		turretAdjust.changeControlMode(CANTalon.TalonControlMode.Position);
	}
	
	/**
	 * manual turret movement
	 * @param pVBus the percent vBus to run into the turret
	 */
	void MoveTurret(double pVBus)
	{
		//motor run
	}
	
	boolean GetLimitSwitchLeft()
	{
		return turretAdjust.isFwdLimitSwitchClosed();
	}
	
	boolean GetLimitSwitchRight()
	{
		return turretAdjust.isRevLimitSwitchClosed();
	}
	
	double GetPosition()
	{
		return turretAdjust.getPosition();
	}
	
	double getSpeed()
	{
		return turretAdjust.getSpeed();
	}

	boolean checkZero()
	{
		if(GetLimitSwitchLeft()){
			turretAdjust.setPosition(0);
			return true;
		}
		return false;
	}
}


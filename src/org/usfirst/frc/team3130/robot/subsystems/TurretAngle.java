package org.usfirst.frc.team3130.robot.subsystems;

import org.usfirst.frc.team3130.robot.Constants;
import org.usfirst.frc.team3130.util.Rotation2d;

import org.usfirst.frc.team3130.robot.commands.ManualTurretAim;
import org.usfirst.frc.team3130.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The TurretAngle subsystem controls the angle the ball is fired. The Turret can only 
 * rotate within 200 degrees (+/- 100) because of mechanical hard stops.
 * 
 * The balls are fed from the hopper to the Turret via the ball elevator, the TurretAngle 
 * subsystem set the angle of the Turret. Then the balls are shot out by the TurretFlywheel.
 * 
 * @see TurretFlywheel
 * 
 */
public class TurretAngle extends Subsystem {
	private static CANTalon m_turret;

  //Instance Handling
  	private static TurretAngle m_pInstance;
  	/**
  	 * A system for getting an instance of this class.
  	 * The function provides a method by which the class is setup as a singleton
  	 * with only a single copy of it existing in memory.
  	 * <p> It will return a reference to the class, which is shared amongst all callers of GetInstance()
  	 * 
  	 * @return the reference to the class referred to in GetInstance. In this case, TurretAngle.
  	 */
  	public static TurretAngle GetInstance()
  	{
  		if(m_pInstance == null) m_pInstance = new TurretAngle();
  		return m_pInstance;
  	}
  	

  	
	private TurretAngle() {
		// The turret has one Talon to control angle.
		m_turret = new CANTalon(RobotMap.CAN_TURRETANGLE);
		m_turret.enableBrakeMode(true);
		m_turret.enableLimitSwitch(false, false);
		m_turret.setStatusFrameRateMs(CANTalon.StatusFrameRate.Feedback, 10);
		m_turret.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		m_turret.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative); //TODO: check if this is accurate
		if (m_turret.isSensorPresent(
				CANTalon.FeedbackDevice.CtreMagEncoder_Relative) != CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent) {
			DriverStation.reportError("Could not detect turret encoder!", false);
		}

		m_turret.changeControlMode(CANTalon.TalonControlMode.Position);

		m_turret.setPID(Constants.kTurretKp, Constants.kTurretKi, Constants.kTurretKd);
		/*m_turret.setPID(Constants.kTurretKp, Constants.kTurretKi, Constants.kTurretKd, Constants.kTurretKf,
				Constants.kTurretIZone, Constants.kTurretRampRate, 0);*/
		m_turret.setProfile(0);
		m_turret.reverseSensor(false); //TODO:Set true if turret turns in opposite direction of motor @author Eastan
		m_turret.reverseOutput(false);
		


		// We use soft limits to make sure the turret doesn't try to spin too
		// far.
		m_turret.enableForwardSoftLimit(true);
		m_turret.enableReverseSoftLimit(true);
		m_turret.setForwardSoftLimit(Constants.kSoftMaxTurretAngle / (360.0 * Constants.kTurretRotationsPerTick));
		m_turret.setReverseSoftLimit(Constants.kSoftMinTurretAngle / (360.0 * Constants.kTurretRotationsPerTick));
		m_turret.enableForwardSoftLimit(true);
		m_turret.enableReverseSoftLimit(true);

		m_turret.clearStickyFaults();
	}

	// Set the desired angle of the turret (and put it into position control
	// mode if it isn't already).
	public synchronized static void setAngle(double angle_deg) {
		m_turret.changeControlMode(CANTalon.TalonControlMode.Position);
		// In Position mode, outputValue set is in encoder ticks 
		m_turret.set(angle_deg / (360.0 * Constants.kTurretRotationsPerTick));
	}

	// Manually move the turret (and put it into vbus mode if it isn't already). Input range -1.0 to 1.0
	public synchronized static void setOpenLoop(double speed) {
		m_turret.changeControlMode(CANTalon.TalonControlMode.PercentVbus); 
		m_turret.set(-0.12*speed); //scale to max of 12%
	}

	// Tell the Talon it is at a given position.
	synchronized static void reset(Rotation2d actual_rotation) {
		m_turret.setPosition(actual_rotation.getRadians() / (2 * Math.PI * Constants.kTurretRotationsPerTick));
	}


	public synchronized static double getAngleDegrees() {
		double tangle = Constants.kTurretRotationsPerTick * m_turret.getEncPosition() * 2.0 * Math.PI;
		return Math.toDegrees(tangle);
	}

	public synchronized static double getSetpoint() {
		return m_turret.getSetpoint() * Constants.kTurretRotationsPerTick * 360.0;
	}

	private synchronized static double getError() {
		return getAngleDegrees() - getSetpoint();
	}

	// We are "OnTarget" if we are in position mode and close to the setpoint.
	public synchronized static boolean isOnTarget() {
		return (m_turret.getControlMode() == CANTalon.TalonControlMode.Position
				&& Math.abs(getError()) < Constants.kTurretOnTargetTolerance);
	}

	/**
	 * @return If the turret is within its mechanical limits and in the right
	 *		 state.
	 */

	public synchronized static void stop() {
		setOpenLoop(0);
	}



	public static void outputToSmartDashboard() {
		SmartDashboard.putNumber("turret_error", getError());
		SmartDashboard.putNumber("turret_angle", getAngleDegrees());
		SmartDashboard.putNumber("turret_setpoint", getSetpoint());
		SmartDashboard.putBoolean("turret_on_target", isOnTarget());
	}

	public static void zeroSensors() {
		reset(new Rotation2d());
	}
	@Override
	protected void initDefaultCommand() {

		
	}
}

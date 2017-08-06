package org.usfirst.frc.team3130.robot.subsystems;

import org.usfirst.frc.team3130.robot.Constants;
import org.usfirst.frc.team3130.util.Rotation2d;

import org.usfirst.frc.team3130.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The Turret subsystem controls the direction the ball is fired. On the Turret
 * assembly is the Hood and Flywheel. The Turret can only rotate within 240
 * degrees (+/- 120), and mechanical bumper switches indicate when the
 * mechanical limits are reached. This is part of the Superstructure superclass.
 * 
 * The ball is first picked up with the Intake then is fed to the Flywheel with
 * the HoodRoller. The Turret controls the direction that the ball is fired at.
 * Finally, the Hood controls the output angle and trajectory of the shot.
 * 
 * @see Flywheel
 * @see Hood
 * @see HoodRoller
 * @see Intake
 * @see Superstructure
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
  	 * @return the reference to the class referred to in GetInstance. In this case, ShooterWheelsRight.
  	 */
  	public static TurretAngle GetInstance()
  	{
  		if(m_pInstance == null) m_pInstance = new TurretAngle();
  		return m_pInstance;
  	}
  	
    TurretAngle() {
        // The turret has one Talon to control angle.
        m_turret = new CANTalon(RobotMap.CAN_TURRETANGLE);
        m_turret.enableBrakeMode(true);
        m_turret.enableLimitSwitch(true, true);
        m_turret.ConfigFwdLimitSwitchNormallyOpen(true);
        m_turret.ConfigRevLimitSwitchNormallyOpen(true);
        m_turret.setStatusFrameRateMs(CANTalon.StatusFrameRate.Feedback, 10);
        m_turret.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
        if (m_turret.isSensorPresent(
                CANTalon.FeedbackDevice.CtreMagEncoder_Relative) != CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent) {
            DriverStation.reportError("Could not detect turret encoder!", false);
        }

        m_turret.changeControlMode(CANTalon.TalonControlMode.PercentVbus);

        m_turret.setPID(Constants.kTurretKp, Constants.kTurretKi, Constants.kTurretKd, Constants.kTurretKf,
                Constants.kTurretIZone, Constants.kTurretRampRate, 0);
        m_turret.setProfile(0);
        m_turret.reverseSensor(true); //TODO:Set true if turret turns in opposite direction of motor @author Eastan
        m_turret.reverseOutput(false);

        // We use soft limits to make sure the turret doesn't try to spin too
        // far.
        m_turret.enableForwardSoftLimit(true);
        m_turret.enableReverseSoftLimit(true);
        m_turret.setForwardSoftLimit(Constants.kSoftMaxTurretAngle / (360.0 * Constants.kTurretRotationsPerTick));
        m_turret.setReverseSoftLimit(Constants.kSoftMinTurretAngle / (360.0 * Constants.kTurretRotationsPerTick));
    }

    // Set the desired angle of the turret (and put it into position control
    // mode if it isn't already).
    public synchronized void setAngle(double angle_deg) {
    	Rotation2d angle = Rotation2d.fromDegrees(angle_deg);
        m_turret.changeControlMode(CANTalon.TalonControlMode.Position);
        // In Position mode, outputValue set is in encoder ticks 
        m_turret.set(angle.getRadians() / (2 * Math.PI * Constants.kTurretRotationsPerTick));
    }

    // Manually move the turret (and put it into vbus mode if it isn't already).
    synchronized void setOpenLoop(double speed) {
        m_turret.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        m_turret.set(speed);
    }

    // Tell the Talon it is at a given position.
    synchronized void reset(Rotation2d actual_rotation) {
        m_turret.setPosition(actual_rotation.getRadians() / (2 * Math.PI * Constants.kTurretRotationsPerTick));
    }

    public synchronized Rotation2d getAngle() {
        return Rotation2d.fromRadians(Constants.kTurretRotationsPerTick * m_turret.getPosition() * 2 * Math.PI);
    }

    public synchronized double getAngleDegrees() {
        return Math.toDegrees(Constants.kTurretRotationsPerTick * m_turret.getPosition() * 2 * Math.PI);
    }
    
    public synchronized boolean getForwardLimitSwitch() {
        return m_turret.isFwdLimitSwitchClosed();
    }

    public synchronized boolean getReverseLimitSwitch() {
        return m_turret.isRevLimitSwitchClosed();
    }

    public synchronized double getSetpoint() {
        return m_turret.getSetpoint() * Constants.kTurretRotationsPerTick * 360.0;
    }

    private synchronized double getError() {
        return getAngle().getDegrees() - getSetpoint();
    }

    // We are "OnTarget" if we are in position mode and close to the setpoint.
    public synchronized boolean isOnTarget() {
        return (m_turret.getControlMode() == CANTalon.TalonControlMode.Position
                && Math.abs(getError()) < Constants.kTurretOnTargetTolerance);
    }

    /**
     * @return If the turret is within its mechanical limits and in the right
     *         state.
     */
    public synchronized boolean isSafe() {
        return (m_turret.getControlMode() == CANTalon.TalonControlMode.Position && m_turret.getSetpoint() == 0 && Math.abs(
                getAngle().getDegrees() * Constants.kTurretRotationsPerTick * 360.0) < Constants.kTurretSafeTolerance);
    }

    public synchronized void stop() {
        setOpenLoop(0);
    }

    //TODO:Decide if needed @author Eastan
 /*   @Override
    public void outputToSmartDashboard() {
        SmartDashboard.putNumber("turret_error", getError());
        SmartDashboard.putNumber("turret_angle", getAngle().getDegrees());
        SmartDashboard.putNumber("turret_setpoint", getSetpoint());
        SmartDashboard.putBoolean("turret_fwd_limit", getForwardLimitSwitch());
        SmartDashboard.putBoolean("turret_rev_limit", getReverseLimitSwitch());
        SmartDashboard.putBoolean("turret_on_target", isOnTarget());
    }
*/
    public void zeroSensors() {
        reset(new Rotation2d());
    }

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}

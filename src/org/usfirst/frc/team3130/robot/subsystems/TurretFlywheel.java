package org.usfirst.frc.team3130.robot.subsystems;

import org.usfirst.frc.team3130.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The flywheel has several parameters: the RPM speed, the setpoint, and the RPM
 * tolerance. When told to, the flywheel will try to spin up to the setpoint
 * within the set RPM tolerance.
 * 
 * The ball is first picked up with the Intake then is fed to the Flywheel with
 * the HoodRoller. The Turret controls the direction that the ball is fired at.
 * Finally, the Hood controls the output angle and, conversely, trajectory.
 * 
 * This code was modeled after team 254's 2016 turret code
 */
public class TurretFlywheel extends Subsystem {
    private static CANTalon master_talon;
    private static CANTalon slave_talon;
    private static double P_DEFAULT = 1.0; //TODO: tune this
    private static double I_DEFAULT = 0.0; //TODO: tune this
    private static double D_DEFAULT = 0.0; //TODO: tune this
    private static double F_DEFAULT = 0.0; //TODO: tune this
    private static int IZONE_DEFAULT = 0; //TODO: tune this
    private static double RAMP_DEFAULT = 0.0; //TODO: tune this
    private static int SPEEDTOLERANCE = 100;
    
    //Instance Handling
  	private static TurretFlywheel m_pInstance;
  	/**
  	 * A system for getting an instance of this class.
  	 * The function provides a method by which the class is setup as a singleton
  	 * with only a single copy of it existing in memory.
  	 * <p> It will return a reference to the class, which is shared amongst all callers of GetInstance()
  	 * 
  	 * @return the reference to the class referred to in GetInstance. In this case, ShooterWheelsRight.
  	 */
  	public static TurretFlywheel GetInstance()
  	{
  		if(m_pInstance == null) m_pInstance = new TurretFlywheel();
  		return m_pInstance;
  	}

    private TurretFlywheel() {
        master_talon = new CANTalon(RobotMap.CAN_SHOOTERMASTER);
        slave_talon = new CANTalon(RobotMap.CAN_SHOOTERSLAVE);
        
        master_talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        //master_talon.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
        master_talon.configEncoderCodesPerRev(24);
        /*
        if (master_talon.isSensorPresent(
                CANTalon.FeedbackDevice.CtreMagEncoder_Relative) != CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent) {
            DriverStation.reportError("Could not detect shooter encoder!", false);
        }
		*/
        //master_talon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        slave_talon.changeControlMode(CANTalon.TalonControlMode.Follower);
        slave_talon.set(RobotMap.CAN_SHOOTERMASTER);
        master_talon.setProfile(0);
        master_talon.setPID(
        		//Preferences.getInstance().getDouble("TurretFlyP",
        		P_DEFAULT,
        		//Preferences.getInstance().getDouble("TurretFlyI",
        		I_DEFAULT,
        		//Preferences.getInstance().getDouble("TurretFlyD",
        		D_DEFAULT,
        		//Preferences.getInstance().getDouble("TurretFlyF",
        		F_DEFAULT,
        		//Preferences.getInstance().getInt("TurretFlyIZone",
        		IZONE_DEFAULT,
        		//Preferences.getInstance().getDouble("TurretFlyRamp",
        		RAMP_DEFAULT,
        		0);

        master_talon.reverseSensor(true);
        master_talon.reverseOutput(false);
        slave_talon.reverseOutput(true);

        //master_talon.setVoltageRampRate(36.0);
        //slave_talon.setVoltageRampRate(36.0);

        master_talon.enableBrakeMode(false);
        slave_talon.enableBrakeMode(false);

        master_talon.clearStickyFaults();
        slave_talon.clearStickyFaults();
    }

    public static double getSpeed() {
        return master_talon.getSpeed(); //* 48.0 * (13.0/36.0); //Turret Flywheel uses a RS7 encoder with resolution of 12 ticks per rotation (counts per rotation, CPR). RS7 is a quadrature encoder so the multipler is 4xCPR.
    }

    /**
     * Sets the RPM of the flywheel. The flywheel will then spin up to the set
     * speed within a preset tolerance.
     * 
     * @param Set
     *            flywheel RPM
     */
    public static void setSpeed(double rpm) {
        master_talon.changeControlMode(CANTalon.TalonControlMode.Speed);
        master_talon.set(rpm);
    }

    public static double getVBus(){
    	return master_talon.getBusVoltage();
    }
    
    public static void setOpenLoop(double speed) {
        master_talon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        master_talon.set(speed);
    }

    public static double getSetpoint() {
        return master_talon.getSetpoint();
    }

    /**
     * @return If the flywheel RPM is within the tolerance to the specified set
     *         point.
     */
    public static boolean isOnTarget() {
        return (master_talon.getControlMode() == CANTalon.TalonControlMode.Speed
                && Math.abs(getSpeed() - getSetpoint()) < SPEEDTOLERANCE);
    }

    public static void stop() {
        setOpenLoop(0);
    }

    
    public static void outputToSmartDashboard() {
        SmartDashboard.putNumber("flywheel_rpm", getSpeed());
        SmartDashboard.putNumber("flywheel_setpoint", master_talon.getSetpoint());
        SmartDashboard.putBoolean("flywheel_on_target", isOnTarget());
        SmartDashboard.putNumber("flywheel_master_current", master_talon.getOutputCurrent());
        SmartDashboard.putNumber("flywheel_slave_current", slave_talon.getOutputCurrent());
    }

    public static void zeroSensors() {
        // no-op
    }

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
}
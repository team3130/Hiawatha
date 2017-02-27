package org.usfirst.frc.team3130.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import org.usfirst.frc.team3130.misc.LinearInterp;
import org.usfirst.frc.team3130.robot.*;
import org.usfirst.frc.team3130.robot.commands.DefaultDrive;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import com.ctre.CANTalon;

import com.ctre.CANTalon.FeedbackDevice;

import com.ctre.CANTalon.TalonControlMode;

/**
 * Basically a copy of Greenwood's chassis at this point
 */

public class Chassis extends PIDSubsystem {
	 
	//Instance Handling
	private static Chassis m_pInstance;
	public static Chassis GetInstance()
	{
		if(m_pInstance == null) m_pInstance = new Chassis();
		return m_pInstance;
	}
	
	
	//Create and define necessary objects
	private static RobotDrive m_drive;
	private static CANTalon m_leftMotorFront;
	private static CANTalon m_leftMotorRear;
	private static CANTalon m_rightMotorFront;
	private static CANTalon m_rightMotorRear;
	private static Solenoid m_shifter;
	private static AHRS m_navX;
	
	//Define an enum to control the direction to be turned
	public static enum TurnDirection{kLeft, kRight, kStraight};
	private static TurnDirection m_dir;
	
	//Create and define all standard data types needed
	private static boolean m_bShiftedHigh;
	private static double moveSpeed;
	private static double prevAbsBias;
	private static boolean m_bNavXPresent;

	public static final double robotWidth = 26.0;

	public static final double InchesPerRev = 4 * Math.PI;

	
	//PID Preferences Defaults
	private static final double TALON_CURVEDRIVE_LOW_POSITION_P_DEFAULT = 0.1;
	private static final double TALON_CURVEDRIVE_LOW_POSITION_I_DEFAULT = 0.0;
	private static final double TALON_CURVEDRIVE_LOW_POSITION_D_DEFAULT = 0.0;
	
	private static final double TALON_CURVEDRIVE_HIGH_POSITION_P_DEFAULT = 0.1;
	private static final double TALON_CURVEDRIVE_HIGH_POSITION_I_DEFAULT = 0.0;
	private static final double TALON_CURVEDRIVE_HIGH_POSITION_D_DEFAULT = 0.0;
	
	private static final double TALON_CURVEDRIVE_LOW_SPEED_P_DEFAULT = 0.1;
	private static final double TALON_CURVEDRIVE_LOW_SPEED_I_DEFAULT = 0.0;
	private static final double TALON_CURVEDRIVE_LOW_SPEED_D_DEFAULT = 0.0;
	
	private static final double TALON_CURVEDRIVE_HIGH_SPEED_P_DEFAULT = 0.1;
	private static final double TALON_CURVEDRIVE_HIGH_SPEED_I_DEFAULT = 0.0;
	private static final double TALON_CURVEDRIVE_HIGH_SPEED_D_DEFAULT = 0.0;
	
	
	private static final double SUBSYSTEM_CURVE_HIGH_P_DEFAULT = 0.075;
	private static final double SUBSYSTEM_CURVE_HIGH_I_DEFAULT = 0.01;
	private static final double SUBSYSTEM_CURVE_HIGH_D_DEFAULT = 0.09;

	private static final double SUBSYSTEM_CURVE_LOW_P_DEFAULT = 0.085;
	private static final double SUBSYSTEM_CURVE_LOW_I_DEFAULT = 0.02;
	private static final double SUBSYSTEM_CURVE_LOW_D_DEFAULT = 0.125;

	private static final double SUBSYSTEM_STRAIGHT_HIGH_P_DEFAULT = 0.075;
	private static final double SUBSYSTEM_STRAIGHT_HIGH_I_DEFAULT = 0.01;
	private static final double SUBSYSTEM_STRAIGHT_HIGH_D_DEFAULT = 0.09;

	private static final double SUBSYSTEM_STRAIGHT_LOW_P_DEFAULT = 0.085;
	private static final double SUBSYSTEM_STRAIGHT_LOW_D_DEFAULT = 0.125;

	
	private static int m_driveMultiplier;
	
	private Chassis()
	{
		super("Chassis", 0.05, 0.01, 0.15);
		
		m_leftMotorFront = new CANTalon(RobotMap.CAN_LEFTMOTORFRONT);
		m_leftMotorRear = new CANTalon(RobotMap.CAN_LEFTMOTORREAR);
		m_rightMotorFront = new CANTalon(RobotMap.CAN_RIGHTMOTORFRONT);
		m_rightMotorRear = new CANTalon(RobotMap.CAN_RIGHTMOTORREAR);
		
		//Configure encoders
		m_leftMotorFront.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		m_rightMotorFront.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		m_leftMotorFront.reverseSensor(false);
		m_rightMotorFront.reverseSensor(true);
		
		m_leftMotorFront.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		m_rightMotorFront.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		
		m_leftMotorFront.configEncoderCodesPerRev(RobotMap.RATIO_DRIVECODESPERREV);
		m_rightMotorFront.configEncoderCodesPerRev(RobotMap.RATIO_DRIVECODESPERREV);
		

		//Slave the Talons
		m_leftMotorRear.changeControlMode(TalonControlMode.Follower);
		m_leftMotorRear.set(RobotMap.CAN_LEFTMOTORFRONT);
		
		m_rightMotorRear.changeControlMode(TalonControlMode.Follower);
		m_rightMotorRear.set(RobotMap.CAN_RIGHTMOTORFRONT);
		

		m_drive = new RobotDrive(m_leftMotorFront, m_rightMotorFront);
		m_drive.setSafetyEnabled(false);

		m_shifter = new Solenoid(RobotMap.CAN_PNMMODULE, RobotMap.PNM_GEARSHIFTER);
		m_bShiftedHigh = false;
		
		
		try{
			//Connect to navX Gyro on MXP port.
			m_navX = new AHRS(SPI.Port.kMXP);
			m_bNavXPresent = true;
			LiveWindow.addSensor("Chassis", "NavX", m_navX);
		} catch(Exception ex){
			//If connection fails log the error and fall back to encoder based angles.
			String str_error = "Error instantiating navX from MXP: ";
			str_error += ex.getLocalizedMessage();
			DriverStation.reportError(str_error, true);
			m_bNavXPresent = false;
		}
		
		//Add systems to LiveWindow
		LiveWindow.addActuator("Chassis", "Left Front Talon", m_leftMotorFront);
		LiveWindow.addActuator("Chassis", "Left Rear TalonSRX", m_leftMotorRear);
		LiveWindow.addActuator("Chassis", "Right Front TalonSRX", m_rightMotorFront);
		LiveWindow.addActuator("Chassis", "Right Rear TalonSRX", m_rightMotorRear);
		
		moveSpeed = 0;
		prevAbsBias = 0;
		
		m_dir = TurnDirection.kStraight;
		
		m_driveMultiplier = 1;
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DefaultDrive());
    }
    
    //Drive methods for the two forms of control used. Two of each type exist to allow a 2 arg call to default to non-squared inputs
    public static void DriveTank(double moveL, double moveR, boolean squaredInputs)
    {
    	m_drive.tankDrive(moveL, moveR, squaredInputs);
    }
    
    public static void DriveTank(double moveL, double moveR)
    {
    	m_drive.tankDrive(moveL, moveR, false);
    }
    
    public static void DriveArcade(double move, double turn, boolean squaredInputs)
    {
    	m_drive.arcadeDrive(move, turn, squaredInputs);
    }
    
    public static void DriveArcade(double move, double turn)
    {
    	m_drive.arcadeDrive(move, turn, false);
    }
    
    public static void Shift(boolean shiftDown)
    {
    	m_shifter.set(shiftDown);
    	m_bShiftedHigh = shiftDown;
    }
    
    public static boolean GetShiftedDown(){return m_bShiftedHigh;}
    
    
    /**
     * Returns the current speed of the front left motor
     * @return Current speed of the front left motor (unknown units)
     */
    public static double GetSpeedL()
    {
    	return m_leftMotorFront.getSpeed() * InchesPerRev / 50.0;
    }
    
    /**
     * Returns the current speed of the front right motor
     * @return Current speed of the front right motor (unknown units)
     */
    public static double GetSpeedR()
    {
    	return m_rightMotorFront.getSpeed() * InchesPerRev / 50.0;	
    }
    
    public static double GetSpeed()
    {
    	//The right encoder is nonfunctional, just use the left speed.
    	//return (GetSpeedL() + GetSpeedR())/2.0;
    	return GetSpeedL();
    }

    
    /**
     * Returns the current voltage being output by the front left talon
     * @return voltage being output by talon in volts
     */
    public static double GetFrontVoltL() {
    	return m_leftMotorFront.getOutputVoltage();
    }
    
    /**
     * Returns the current voltage being output by the front right talon
     * @return voltage being output by talon in volts
     */
    public static double GetFrontVoltR() {
    	return m_rightMotorFront.getOutputVoltage();
    }
    
    /**
     * Returns the current voltage being output by the rear left talon
     * @return voltage being output by talon in volts
     */
    public static double GetRearVoltL() {
    	return m_leftMotorRear.getOutputVoltage();
    }
    
    /**
     * Returns the current voltage being output by the rear right talon
     * @return voltage being output by talon in volts
     */
    public static double GetRearVoltR() {
    	return m_rightMotorRear.getOutputVoltage();
    }
    
    /**
     * Returns the current going through the front left talon
     * @return current going through talon, in Amperes
     */
    public static double GetFrontCurrentL() {
    	return m_leftMotorFront.getOutputCurrent();
    }
    
    /**
     * Returns the current going through the front right talon
     * @return current going through talon, in Amperes
     */
    public static double GetFrontCurrentR() {
    	return m_rightMotorFront.getOutputCurrent();
    }
    
    /**
     * Returns the current going through the rear left talon
     * @return current going through talon, in Amperes
     */
    public static double GetRearCurrentL() {
    	return m_leftMotorRear.getOutputCurrent();
    }
    
    /**
     * Returns the current going through the rear right talon
     * @return current going through talon, in Amperes
     */
    public static double GetRearCurrentR() {
    	return m_rightMotorRear.getOutputCurrent();
    }

	/**
	 * 
	 * @return Current distance of the front left motor in inches
	 */
	public static double GetDistanceL()
	{
		return m_leftMotorFront.getPosition() * InchesPerRev;
	}
	
	/**
	 * 
	 * @return Current distance of the front right motor in inches
	 */
	public static double GetDistanceR()
	{
		return m_rightMotorFront.getPosition() * InchesPerRev;
	}
	
	
	public static double GetDistance()
	{
		//Returns the average of the left and right speeds
		return (GetDistanceL() + GetDistanceR()) / 2.0;
	}
	
	public static double GetAngle()
	{
		if(m_bNavXPresent)
		{
			//Angle use wants a faster, more accurate, but drifting angle, for quick use.
			return -m_navX.getAngle();
		}else {
			//Means that angle use wants a driftless angle measure that lasts.
			return ( GetDistanceR() - GetDistanceL() ) * 180 / (Preferences.getInstance().getDouble("ChassisWidth",28.55) * Math.PI);
			/*
			 *  Angle is 180 degrees times encoder difference over Pi * the distance between the wheels
			 *	Made from geometry and relation between angle fraction and arc fraction with semicircles.
			 */
		}
	}
	
	/**
	 * Returns the current rate of change of the robots heading
	 * 
	 * <p> GetRate() returns the rate of change of the angle the robot is facing, 
	 * with a return of negative one if the gyro isn't present on the robot, 
	 * as calculating the rate of change of the angle using encoders is not currently being done.
	 * @return the rate of change of the heading of the robot.
	 */
	public static double GetRate()
	{
		if(m_bNavXPresent) return m_navX.getRate();
		return -1;
	}
	
	//Angle PID Controls
	@Override
	public double returnPIDInput()
	{
		if(m_dir.equals(TurnDirection.kStraight))return GetAngle();
		return GetRate();
	}
	
	@Override
	public void usePIDOutput(double bias)
	{
		//TODO: Replace this with a system that works under the curve drive, currently implemented under the else
		if(m_dir.equals(TurnDirection.kStraight)){	//Maintnance of the Old Drive Straight Angle control.
			//Chassis ramp rate is the limit on the voltage change per cycle to prevent skidding.
			final double speedLimit = prevAbsBias + Preferences.getInstance().getDouble("ChassisRampRate", 0.25);
			if (bias >  speedLimit) bias = speedLimit;
			if (bias < -speedLimit) bias = -speedLimit;
			double speed_L = moveSpeed-bias;
			double speed_R = moveSpeed+bias;
			DriveTank(speed_L, speed_R, false);
			prevAbsBias = Math.abs(bias);
		}else{
			setSpeedTalon(bias);
		}
	}
	
	public static double GetPIDError()
	{
		return GetInstance().getSetpoint() - GetInstance().getPosition();
	}
	
	public static void ReleaseAngle()
	{
		GetInstance().getPIDController().disable();
		prevAbsBias = 0;
		m_leftMotorFront.changeControlMode(TalonControlMode.PercentVbus);
		m_rightMotorFront.changeControlMode(TalonControlMode.PercentVbus);
	}
	
	//Shouldn't be used unless absolutely necessary, takes an excessive amount of time to run
	public static void ResetEncoders()
	{
		m_leftMotorFront.setPosition(0);
		m_rightMotorFront.setPosition(0);
	}
	
	
	private static double GetI(double angle)
	{
		double workingAngle = Math.abs(angle);
		if(workingAngle > 20) return 7.0E-7;
		return (-.000999965*workingAngle + 0.02);
	}
	
	public static void SetPIDValues(double angle)
	{
		if(m_bShiftedHigh){
			if(m_dir.equals(TurnDirection.kStraight)){
				GetInstance().getPIDController().setPID(
					Preferences.getInstance().getDouble("ChassisHighP",SUBSYSTEM_STRAIGHT_HIGH_P_DEFAULT),
					Preferences.getInstance().getDouble("ChassisHighI",SUBSYSTEM_STRAIGHT_HIGH_I_DEFAULT),
					Preferences.getInstance().getDouble("ChassisHighD",SUBSYSTEM_STRAIGHT_HIGH_D_DEFAULT)
				);
			}else{
				GetInstance().getPIDController().setPID(
						Preferences.getInstance().getDouble("Chassis High Curve P",SUBSYSTEM_CURVE_HIGH_P_DEFAULT),
						Preferences.getInstance().getDouble("Chassis High Curve I",SUBSYSTEM_CURVE_HIGH_I_DEFAULT),
						Preferences.getInstance().getDouble("Chassis High Curve D",SUBSYSTEM_CURVE_HIGH_D_DEFAULT)
				);
			}
		}else{
			if(m_dir.equals(TurnDirection.kStraight)){
				GetInstance().getPIDController().setPID(
					Preferences.getInstance().getDouble("ChassisLowP",SUBSYSTEM_STRAIGHT_LOW_P_DEFAULT),
					GetI(angle),
					Preferences.getInstance().getDouble("ChassisLowD",SUBSYSTEM_STRAIGHT_LOW_D_DEFAULT)
				);
			}else{
				GetInstance().getPIDController().setPID(
					Preferences.getInstance().getDouble("Chassis Low Curve P",SUBSYSTEM_CURVE_LOW_P_DEFAULT),
					Preferences.getInstance().getDouble("Chassis Low Curve I",SUBSYSTEM_CURVE_LOW_I_DEFAULT),
					Preferences.getInstance().getDouble("Chassis Low Curve D",SUBSYSTEM_CURVE_LOW_D_DEFAULT)
				);
			}
		}
	}
	
	public static void HoldAngle(double angle)
	{
		SetPIDValues(angle);
		if(m_dir.equals(TurnDirection.kStraight))GetInstance().getPIDController().setSetpoint(GetAngle() + angle);
		else GetInstance().getPIDController().setSetpoint(angle);
		GetInstance().getPIDController().enable();
	}
	
	public static void DriveStraight(double move) { moveSpeed = move; }
	
	/**
	 * Sets the control modes and switches the mode of the subsystem for turning
	 * 
	 * <p>
	 * Sets the PID for both talons, changes the control mode for the subsystem, and changes the control mode of the talons.
	 * @param dir the direction that the robot will be turning
	 */
	public static void setTurnDir(TurnDirection dir)
	{
		m_dir = dir;
		if(dir.equals(TurnDirection.kLeft)){
			m_leftMotorFront.changeControlMode(TalonControlMode.Speed);
			m_rightMotorFront.changeControlMode(TalonControlMode.Position);
		}else if(dir.equals(TurnDirection.kRight)){
			m_rightMotorFront.changeControlMode(TalonControlMode.Speed);
			m_leftMotorFront.changeControlMode(TalonControlMode.Position);
		}else{
			//TODO: Rethink this area, it was done on a whim to make a pseudo functioning system
			m_leftMotorFront.changeControlMode(TalonControlMode.PercentVbus);
			m_rightMotorFront.changeControlMode(TalonControlMode.PercentVbus);
		}
		setTalonPID();
	}
	

	
	/**
	 * Sets the PID Values of both talons
	 * <p>The PID Values to be used can be defined in Preferences, and have defaults set in the constants at the top of Chassis.
	 * The PID Values can differ for high and low gear, as well as for if the talons are in speed or position mode, determined by 
	 * turn direction.
	 */
	private static void setTalonPID()
	{
		if(m_bShiftedHigh){
			switch(m_dir){
			case kLeft:
				m_rightMotorFront.setPID(
						Preferences.getInstance().getDouble("CurveDrive Low Position P", TALON_CURVEDRIVE_LOW_POSITION_P_DEFAULT), 
						Preferences.getInstance().getDouble("CurveDrive Low Position I", TALON_CURVEDRIVE_LOW_POSITION_I_DEFAULT),
						Preferences.getInstance().getDouble("CurveDrive Low Position D", TALON_CURVEDRIVE_LOW_POSITION_D_DEFAULT)
				);
				m_leftMotorFront.setPID(
						Preferences.getInstance().getDouble("CurveDrive Low Speed P", TALON_CURVEDRIVE_LOW_SPEED_P_DEFAULT), 
						Preferences.getInstance().getDouble("CurveDrive Low Speed I", TALON_CURVEDRIVE_LOW_SPEED_I_DEFAULT),
						Preferences.getInstance().getDouble("CurveDrive Low Speed D", TALON_CURVEDRIVE_LOW_SPEED_D_DEFAULT)
				);
				break;
			case kRight:
				m_leftMotorFront.setPID(
						Preferences.getInstance().getDouble("CurveDrive Low Position P", TALON_CURVEDRIVE_LOW_POSITION_P_DEFAULT), 
						Preferences.getInstance().getDouble("CurveDrive Low Position I", TALON_CURVEDRIVE_LOW_POSITION_I_DEFAULT),
						Preferences.getInstance().getDouble("CurveDrive Low Position D", TALON_CURVEDRIVE_LOW_POSITION_D_DEFAULT)
				);
				m_rightMotorFront.setPID(
						Preferences.getInstance().getDouble("CurveDrive Low Speed P", TALON_CURVEDRIVE_LOW_SPEED_P_DEFAULT), 
						Preferences.getInstance().getDouble("CurveDrive Low Speed I", TALON_CURVEDRIVE_LOW_SPEED_I_DEFAULT),
						Preferences.getInstance().getDouble("CurveDrive Low Speed D", TALON_CURVEDRIVE_LOW_SPEED_D_DEFAULT)
				);
				break;
			default:
				break;
			}
		}else{
			switch(m_dir){
			case kLeft:
				m_rightMotorFront.setPID(
						Preferences.getInstance().getDouble("CurveDrive High Position P", TALON_CURVEDRIVE_HIGH_POSITION_P_DEFAULT), 
						Preferences.getInstance().getDouble("CurveDrive High Position I", TALON_CURVEDRIVE_HIGH_POSITION_I_DEFAULT),
						Preferences.getInstance().getDouble("CurveDrive High Position D", TALON_CURVEDRIVE_HIGH_POSITION_D_DEFAULT)
				);
				m_leftMotorFront.setPID(
						Preferences.getInstance().getDouble("CurveDrive High Speed P", TALON_CURVEDRIVE_HIGH_SPEED_P_DEFAULT), 
						Preferences.getInstance().getDouble("CurveDrive High Speed I", TALON_CURVEDRIVE_HIGH_SPEED_I_DEFAULT),
						Preferences.getInstance().getDouble("CurveDrive High Speed D", TALON_CURVEDRIVE_HIGH_SPEED_D_DEFAULT)
				);
				break;
			case kRight:
				m_leftMotorFront.setPID(
						Preferences.getInstance().getDouble("CurveDrive High Position P", TALON_CURVEDRIVE_HIGH_POSITION_P_DEFAULT), 
						Preferences.getInstance().getDouble("CurveDrive High Position I", TALON_CURVEDRIVE_HIGH_POSITION_I_DEFAULT),
						Preferences.getInstance().getDouble("CurveDrive High Position D", TALON_CURVEDRIVE_HIGH_POSITION_D_DEFAULT)
				);
				m_rightMotorFront.setPID(
						Preferences.getInstance().getDouble("CurveDrive High Speed P", TALON_CURVEDRIVE_HIGH_SPEED_P_DEFAULT), 
						Preferences.getInstance().getDouble("CurveDrive High Speed I", TALON_CURVEDRIVE_HIGH_SPEED_I_DEFAULT),
						Preferences.getInstance().getDouble("CurveDrive High Speed D", TALON_CURVEDRIVE_HIGH_SPEED_D_DEFAULT)
				);
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * Calls the .set() function on the Position talon
	 * <p>
	 * Sets the appropriate output on the talon, depending on the mode.
	 * </p>
	 * 
	 * <p>In PercentVbus, the output is between -1.0 and 1.0, with 0.0 as stopped. 
	 * <br/>In Follower mode, the output is the integer device ID of the talon to duplicate. 
	 * <br/>In Voltage mode, outputValue is in volts. In Current mode, outputValue is in amperes. 
	 * <br/>In Speed mode, outputValue is in position change / 10ms. 
	 * <br/><b>In Position mode, outputValue is in encoder ticks or an analog value, depending on the sensor.</b></p>
	 * 
	 * @param set - The setpoint value, as described above.
	 */
	public static void setPositionTalon(double set)
	{
		if(m_dir.equals(TurnDirection.kRight))m_leftMotorFront.set(set);
		else if(m_dir.equals(TurnDirection.kLeft))m_rightMotorFront.set(set);
	}
	
	
	/**
	 * Calls the .set() function on the Speed talon
	 * <p>
	 * Sets the appropriate output on the talon, depending on the mode.
	 * </p>
	 * 
	 * <p>In PercentVbus, the output is between -1.0 and 1.0, with 0.0 as stopped. 
	 * <br/>In Follower mode, the output is the integer device ID of the talon to duplicate. 
	 * <br/>In Voltage mode, outputValue is in volts. In Current mode, outputValue is in amperes. 
	 * <br/><b>In Speed mode, outputValue is in position change / 10ms.</b> 
	 * <br/>In Position mode, outputValue is in encoder ticks or an analog value, depending on the sensor.</p>
	 * 
	 * @param set - The setpoint value, as described above.
	 */
	public static void setSpeedTalon(double set)
	{
		if(m_dir.equals(TurnDirection.kRight))m_rightMotorFront.set(set);
		else if(m_dir.equals(TurnDirection.kLeft))m_leftMotorFront.set(set);
	}
	
	/**
	 * Returns the difference between the setpoint and the current position
	 * @return the error on the position talon returns negative 1 in in straight
	 */
	public static double getPositionTalonError()
	{
		if(m_dir.equals(TurnDirection.kRight)) return m_leftMotorFront.getError();
		else if(m_dir.equals(TurnDirection.kLeft))return m_rightMotorFront.getError();
		return -1;
	}
	
	/**
	 * Returns the difference between the setpoint and the current position
	 * @return the error on the speed talon, returns negative 1 if in straight mode
	 */
	public static double getSpeedTalonError()
	{
		if(m_dir.equals(TurnDirection.kRight)) return m_rightMotorFront.getError();
		else if(m_dir.equals(TurnDirection.kLeft))return m_leftMotorFront.getError();
		return -1;
	}
	
	/**
	 * Returns the current speed of the postion talon
	 * 
	 * Determines which side is being driven in position mode, and returns the speed of that side. Returns -1 if in kStraight mode
	 * @return the speed of the position talon
	 */
	public static double getPositionTalonSpeed()
	{
		if(m_dir.equals(TurnDirection.kRight)) return m_leftMotorFront.getSpeed();
		else if(m_dir.equals(TurnDirection.kLeft))return m_rightMotorFront.getSpeed();
		return -1;
	}
	
	public static void TalonsToCoast(boolean coast)
	{
		m_leftMotorFront.enableBrakeMode(!coast);
		m_leftMotorRear.enableBrakeMode(!coast);
		m_rightMotorFront.enableBrakeMode(!coast);
		m_rightMotorRear.enableBrakeMode(!coast);

	}
    
    public static void ReverseDrive(){
    	m_driveMultiplier *= -1;
    }
    
    public static int getReverseMultiplier()
    {
    	return m_driveMultiplier;
    }
}




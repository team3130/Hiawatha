package org.usfirst.frc.team3130.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

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
	
	//Create and define all standard data types needed
	private static boolean m_bShiftedLow;
	private static double moveSpeed;
	private static double prevAbsBias;
	private static boolean m_bNavXPresent;
	
	/* Wheel sprockets: 22
	 * Encoder shaft sprockets: 15
	 * Wheel diameter: 7.625
	 * Calibrating ratio: 0.955
	 */
	public static final double InchesPerRev = 0.995 * Math.PI * 7.625 * 15 / 22;
	public static final double InchesWheelToWheel = 26.0;
	
	
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
		
		m_leftMotorFront.configEncoderCodesPerRev(RobotMap.RATIO_DRIVECODESPERREV);
		m_rightMotorFront.configEncoderCodesPerRev(RobotMap.RATIO_DRIVECODESPERREV);
		
		//Slave the rear motors to the front motors
		m_leftMotorRear.changeControlMode(TalonControlMode.Follower);
		m_leftMotorRear.set(RobotMap.CAN_LEFTMOTORFRONT);
		
		m_rightMotorRear.changeControlMode(TalonControlMode.Follower);
		m_rightMotorRear.set(RobotMap.CAN_RIGHTMOTORFRONT);
		
		//Set up the drive
		m_drive = new RobotDrive(m_leftMotorFront, m_rightMotorFront);
		m_drive.setSafetyEnabled(true);
		m_shifter = new Solenoid(RobotMap.CAN_PNMMODULE, RobotMap.PNM_GEARSHIFTER);
		m_bShiftedLow = false;
		
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
    	DriveTank(moveL, moveR, false);
    }
    
    public static void DriveArcade(double move, double turn, boolean squaredInputs)
    {
    	m_drive.arcadeDrive(move, turn, squaredInputs);
    }
    
    public static void DriveArcade(double move, double turn)
    {
    	DriveArcade(move, turn, false);
    }
    
    public static void Shift(boolean shiftDown)
    {
    	m_shifter.set(shiftDown);
    	m_bShiftedLow = shiftDown;
    }
    
    //Getters
    public static boolean GetShiftedDown(){return m_bShiftedLow;}

    public static double GetSpeedL()
    {
    	return m_leftMotorFront.getSpeed() * InchesPerRev;
    }
    
    public static double GetSpeedR()
    {
    	return m_rightMotorFront.getSpeed() * InchesPerRev;	
    }
    
    public static double GetSpeed()
    {
    	//The right encoder is nonfunctional, just use the left speed.
    	//return (GetSpeedL() + GetSpeedR())/2.0;
    	return GetSpeedL();
    }
    
    public static double GetDistanceL()
    {
    	return m_leftMotorFront.getPosition() * InchesPerRev;
    }
    
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
    
    //Angle PID Controls
    @Override
    public double returnPIDInput()
    {
    	return GetAngle();
    }
    
    @Override
    public void usePIDOutput(double bias)
    {
    	//Chassis ramp rate is the limit on the voltage change per cycle to prevent skidding.
    	final double speedLimit = prevAbsBias + Preferences.getInstance().getDouble("ChassisRampRate", 0.25);
    	if (bias >  speedLimit) bias = speedLimit;
    	if (bias < -speedLimit) bias = -speedLimit;
    	double speed_L = moveSpeed-bias;
    	double speed_R = moveSpeed+bias;
    	DriveTank(speed_L, speed_R, false);
    	prevAbsBias = Math.abs(bias);
    }
    
    public static double GetPIDError()
    {
    	return GetInstance().getSetpoint() - GetInstance().getPosition();
    }
    
    public static void ReleaseAngle()
    {
    	GetInstance().getPIDController().disable();
    	prevAbsBias = 0;
    }
    
    //Shouldn't be used unless absolutely necessary, takes an excessive amount of time to run
    public static void ResetEncoders()
    {
    	m_leftMotorFront.setPosition(0);
    	m_rightMotorFront.setPosition(0);
    }
    
    public static void SetPIDValues()
    {
    	if(m_bShiftedLow){
    		GetInstance().getPIDController().setPID(
    				Preferences.getInstance().getDouble("ChassisHighP",0.075),
    				Preferences.getInstance().getDouble("ChassisHighI",0.01),
    				Preferences.getInstance().getDouble("ChassisHighD",0.09)
    		);
    	}else{
    		GetInstance().getPIDController().setPID(
    				Preferences.getInstance().getDouble("ChassisLowP", 0.085),
    				Preferences.getInstance().getDouble("ChassisLowI", 0.02),
    				Preferences.getInstance().getDouble("ChassisLowD", 0.125)
    		);
    	}
    }
    
    public static void HoldAngle(double angle)
    {
    	SetPIDValues();
    	GetInstance().getPIDController().setSetpoint(GetAngle() + angle);
    	GetInstance().getPIDController().enable();
    }
    
    public static void DriveStraight(double move) { moveSpeed = move; }
    
    
    //PID Accessor functions for the Talons
    
	    /**
	     * Sets the control mode of the left talon
	     * @param mode the control method to be used to drive the left talon
	     */
	    public static void setLeftMotorMode(TalonControlMode mode)
	    {
	    	m_leftMotorFront.changeControlMode(mode);
	    }
    
    /**
     * Sets the control mode of the left talon
     * @param mode the control method to be used to drive the right talon
     */
    public static void setRightMotorMode(TalonControlMode mode)
    {
    	m_rightMotorFront.changeControlMode(mode);
    }
    
    /**
     * Sets the PID Values of both talons
     * <p>The PID Values to be used can be defined in Preferences, and 
     */
    public static void setTalonPID()
    {
    	//Left Motor PID Setup
    	switch(m_leftMotorFront.getControlMode()){
    		case Position:
	    		m_leftMotorFront.setPID(
	    				Preferences.getInstance().getDouble("CurveDrive Position P", 0.1), 
	    				Preferences.getInstance().getDouble("CurveDrive Position I", 0.0),
	    				Preferences.getInstance().getDouble("CurveDrive Position D", 0.0)
	    		);
	    		break;
	    		
    		case Speed:
    			m_leftMotorFront.setPID(
	    				Preferences.getInstance().getDouble("CurveDrive Speed P", 0.1), 
	    				Preferences.getInstance().getDouble("CurveDrive Speed I", 0.0),
	    				Preferences.getInstance().getDouble("CurveDrive Speed D", 0.0)
	    		);
    			break;
    			
    		//Don't do anything for PID if the motors aren't in the Speed or Position control modes
			default:
				break; 
    	}
    	
    	//Right Motor PID Setup
    	switch(m_rightMotorFront.getControlMode()){
			case Position:
	    		m_leftMotorFront.setPID(
	    				Preferences.getInstance().getDouble("CurveDrive Position P", 0.1), 
	    				Preferences.getInstance().getDouble("CurveDrive Position I", 0.0),
	    				Preferences.getInstance().getDouble("CurveDrive Position D", 0.0)
	    		);
	    		break;
	    		
			case Speed:
				m_leftMotorFront.setPID(
	    				Preferences.getInstance().getDouble("CurveDrive Speed P", 0.1), 
	    				Preferences.getInstance().getDouble("CurveDrive Speed I", 0.0),
	    				Preferences.getInstance().getDouble("CurveDrive Speed D", 0.0)
	    		);
				break;
				
			//Don't do anything for PID if the motors aren't in the Speed or Position control modes
			default:
				break; 
    	}
    }
    
    /**
     * Calls the .set() function on the left talon
     * <p>
     * Sets the appropriate output on the talon, depending on the mode.
     * </p>
     * 
     * <p>In PercentVbus, the output is between -1.0 and 1.0, with 0.0 as stopped. 
     * In Follower mode, the output is the integer device ID of the talon to duplicate. 
     * In Voltage mode, outputValue is in volts. In Current mode, outputValue is in amperes. 
     * In Speed mode, outputValue is in position change / 10ms. 
     * In Position mode, outputValue is in encoder ticks or an analog value, depending on the sensor.</p>
     * 
     * @param set - The setpoint value, as described above.
     */
    public void setLeftTalon(double set)
    {
    	m_leftMotorFront.set(set);
    }
    
    
    /**
     * Calls the .set() function on the right talon
     * <p>
     * Sets the appropriate output on the talon, depending on the mode.
     * </p>
     * 
     * <p>In PercentVbus, the output is between -1.0 and 1.0, with 0.0 as stopped. 
     * In Follower mode, the output is the integer device ID of the talon to duplicate. 
     * In Voltage mode, outputValue is in volts. In Current mode, outputValue is in amperes. 
     * In Speed mode, outputValue is in position change / 10ms. 
     * In Position mode, outputValue is in encoder ticks or an analog value, depending on the sensor.</p>
     * 
     * @param set - The setpoint value, as described above.
     */
    public void setRightTalon(double set)
    {
    	m_rightMotorFront.set(set);
    }
}



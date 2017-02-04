package org.usfirst.frc.team3130.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import org.usfirst.frc.team3130.robot.*;
import org.usfirst.frc.team3130.robot.commands.DefaultDrive;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import com.ctre.CANTalon;

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
	
	
	private Chassis()
	{
		super("Chassis", 0.05, 0.01, 0.15);
		
		m_leftMotorFront = new CANTalon(RobotMap.CAN_LEFTMOTORFRONT);
		m_leftMotorRear = new CANTalon(RobotMap.CAN_LEFTMOTORREAR);
		m_rightMotorFront = new CANTalon(RobotMap.CAN_RIGHTMOTORFRONT);
		m_rightMotorRear = new CANTalon(RobotMap.CAN_RIGHTMOTORREAR);
		m_leftMotorFront.reverseSensor(false);
		m_rightMotorFront.reverseSensor(true);
		m_leftMotorFront.configEncoderCodesPerRev(RobotMap.RATIO_DRIVECODESPERREV);
		m_rightMotorFront.configEncoderCodesPerRev(RobotMap.RATIO_DRIVECODESPERREV);
		
		m_drive = new RobotDrive(m_leftMotorFront, m_leftMotorRear, m_rightMotorFront, m_rightMotorRear);
		m_drive.setSafetyEnabled(false);
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
    
    public static boolean GetShiftedDown(){return m_bShiftedLow;}
    
    
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
    
    //Shouldn't be used unless absolutely necessary, takes an ecessive amount of time to run
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
    
    public static void TalonsToCoast(boolean coast)
    {
    	m_leftMotorFront.enableBrakeMode(!coast);
    	m_leftMotorRear.enableBrakeMode(!coast);
    	m_rightMotorFront.enableBrakeMode(!coast);
    	m_rightMotorRear.enableBrakeMode(!coast);
    }
}



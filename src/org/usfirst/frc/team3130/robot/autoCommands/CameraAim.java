package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.RobotMap;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.JetsonInterface;

import org.usfirst.frc.team3130.robot.subsystems.Chassis.TurnDirection;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class CameraAim extends Command {

	private double m_yaw = 0 ;
	private final double DEFAULTTHRESHOLD = 2;
	private final double SHOOTERTHRESHOLD = 150;
	private final double DEFAULTBOILERDISTANCE = 120;
	private Timer timer;
	boolean hasAimed;
	boolean hasTurned;
	boolean isActive;
	private String instance = "";
	private double m_dist;
	private double m_posStart;
	public enum AimingMode { kVision, kEncoders }
	private AimingMode m_mode;
	private double m_angle;
	
    public CameraAim() {
        requires(Chassis.GetInstance());
        //requires(ShooterWheelsLeft.GetInstance());
        //requires(ShooterWheelsRight.GetInstance());
        requires(Robot.wscLeft);
        requires(Robot.wscRight);
        timer = new Timer();
    }
    
    public CameraAim(String instance){
        requires(Chassis.GetInstance());
        //requires(ShooterWheelsLeft.GetInstance());
        //requires(ShooterWheelsRight.GetInstance());
        requires(Robot.wscLeft);
        requires(Robot.wscRight);
        timer = new Timer();
        this.instance = instance;
    }

    public void setMode(AimingMode mode)
    {
    	m_mode = mode;
    }

    /**
     * Tells if the Robot is on target and ready to shoot.
     * @return if aimed correctly and wheel up to speed
     */
    public boolean onTarget()
    {
    	boolean onTarget = (isActive
        		&&	(Math.abs(m_yaw) < ((Preferences.getInstance().getDouble("Boiler Threshold", DEFAULTTHRESHOLD)) * (Math.PI/180.0))));
        		//&&	(Math.abs(ShooterWheelsLeft.GetError()) < Preferences.getInstance().getDouble("ShooterWheel Tolerance", SHOOTERTHRESHOLD)));
        		//&&	(Math.abs(ShooterWheelsRight.GetError()) < Preferences.getInstance().getDouble("ShooterWheel Tolerance", SHOOTERTHRESHOLD)));
        
    	if(instance != ""){
    		if(Math.abs(JetsonInterface.getDouble("Boiler Sys Time", 9999) - JetsonInterface.getDouble("Boiler Time", 0)) < 0.25){
    			SmartDashboard.putBoolean("Boiler Seen" + instance, true);
    		}
    		else{
    			SmartDashboard.putBoolean("Boiler Seen" + instance, false);
    		}
    	}
    	
        SmartDashboard.putBoolean("Ready to Shoot" + instance, onTarget);
    	//SmartDashboard.putBoolean("LeftShooter Upto Speed" + instance, (Math.abs(ShooterWheelsLeft.GetError()) < Preferences.getInstance().getDouble("ShooterWheel Tolerance", SHOOTERTHRESHOLD)));
    	//SmartDashboard.putBoolean("RightShooter Upto Speed" + instance, (Math.abs(ShooterWheelsRight.GetError()) < Preferences.getInstance().getDouble("ShooterWheel Tolerance", SHOOTERTHRESHOLD)));
    	
    	return onTarget;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	Chassis.setTurnDir(TurnDirection.kStraight);
    	//ShooterWheelsLeft.setPID();
    	//ShooterWheelsRight.setPID();
    	Chassis.SetPIDValues(21);
        Chassis.TalonsToCoast(false);
    	hasAimed = false;
    	hasTurned = false;
    	isActive = false;
    	m_mode = AimingMode.kVision;
    	m_angle = Chassis.GetAngle();
        timer.start();
        
        m_dist = JetsonInterface.getDouble("Boiler Groundrange", DEFAULTBOILERDISTANCE)
        		*(1/Preferences.getInstance().getDouble("Vision to Inches", RobotMap.RATIO_VISIONTOINCHES));
        m_posStart = Chassis.GetDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	m_yaw = JetsonInterface.getDouble("Boiler Yaw", 0);
    	//SmartDashboard.putNumber("Boiler yaw", m_yaw * (180/Math.PI));
    	if(hasAimed) {
    		if(hasTurned) {
    	    	if(timer.get() > Preferences.getInstance().getDouble("Aim Timeout", .5)){
					hasTurned = false;
					hasAimed = false;
    	    	}
    		}
   	    	else
    		if(Math.abs(Chassis.GetRate()) <= Preferences.getInstance().getDouble("Turning stopped", .1)) {
        		timer.reset();
        		timer.start();
        		hasTurned = true;
        		if(m_mode == AimingMode.kVision) {
        			m_angle = (Math.PI/180f)*Chassis.GetAngle();
        	        m_dist = JetsonInterface.getDouble("Boiler Groundrange", DEFAULTBOILERDISTANCE)
        	        		*(1/Preferences.getInstance().getDouble("Vision to Inches", RobotMap.RATIO_VISIONTOINCHES));
        	        m_posStart = Chassis.GetDistance();
        		}
    		}
    	}
    	else{
    		switch(m_mode) {
    			case kVision:
    				if(Math.abs(JetsonInterface.getDouble("Boiler Sys Time", 9999) - JetsonInterface.getDouble("Boiler Time", 0)) < 0.25){
    					Chassis.HoldAngle(m_yaw);
    					hasAimed = true;
    					isActive = true;
    				}
    				break;
    			case kEncoders:
    				m_yaw = (Math.PI/180f)*Chassis.GetAngle() - m_angle;
    				if(Math.abs(m_yaw) > Preferences.getInstance().getDouble("Boiler Threshold", DEFAULTTHRESHOLD)) {
    					Chassis.HoldAngle(m_yaw);
    					hasAimed = true;
    					isActive = true;
    				}
    				break;
    		}
    	}    	
    	
		double dist = m_dist + (m_posStart - Chassis.GetDistance());
    	//ShooterWheelsLeft.setSpeed(Robot.wscLeft.GetSpeed(dist));
    	//ShooterWheelsRight.setSpeed(Robot.wscRight.GetSpeed(dist));

    	SmartDashboard.putBoolean("Boiler aim", onTarget());
    	if(isActive) {
    		Chassis.DriveStraight(-OI.stickL.getY());
    	}
    	else {
    		Chassis.DriveArcade(-OI.stickL.getY(), 0, true);
    	}
    	
    	onTarget();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	isActive = false;
    	//ShooterWheelsLeft.stop();
    	//ShooterWheelsRight.stop();
    	SmartDashboard.putBoolean("Boiler aim", false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

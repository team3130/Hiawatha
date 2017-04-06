package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.JetsonInterface;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsLeft;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsRight;
import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculationsLeft;
import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculationsRight;
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
	private final double DEFAULTTHRESHOLD = 0.8;
	private final double SHOOTERTHRESHOLD = 150;
	private final double DEFAULTBOILERDISTANCE = 120;
	private Timer timer;
	boolean hasAimed;
	boolean hasTurned;
	boolean isActive;
	
    public CameraAim() {
        requires(Chassis.GetInstance());
        requires(ShooterWheelsLeft.GetInstance());
        requires(ShooterWheelsRight.GetInstance());
        requires(WheelSpeedCalculationsLeft.GetInstance());
        requires(WheelSpeedCalculationsRight.GetInstance());
        requires(Robot.bcShooterAltitude);
        timer = new Timer();
    }

    /**
     * Tells if the Robot is on target and ready to shoot.
     * @return if aimed correctly and wheel up to speed
     */
    public boolean onTarget()
    {
    	return (
    			isActive
    		&&	(Math.abs(m_yaw) < (Preferences.getInstance().getDouble("Boiler Threshold", DEFAULTTHRESHOLD)) * (Math.PI/180.0))
    		&&	(Math.abs(ShooterWheelsLeft.GetError()) < Preferences.getInstance().getDouble("ShooterWheel Tolerance", SHOOTERTHRESHOLD))
    		&&	(Math.abs(ShooterWheelsRight.GetError()) < Preferences.getInstance().getDouble("ShooterWheel Tolerance", SHOOTERTHRESHOLD))
    	);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	Chassis.setTurnDir(TurnDirection.kStraight);
    	ShooterWheelsLeft.setPID();
    	ShooterWheelsRight.setPID();
    	Chassis.SetPIDValues(21);
        Chassis.TalonsToCoast(false);
    	hasAimed = false;
    	hasTurned = false;
    	isActive = false;
    	Robot.bcShooterAltitude.actuate(true);
        timer.start();
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
    		}
    	}
    	else
		if(Math.abs(JetsonInterface.getDouble("Boiler Sys Time", 9999) - JetsonInterface.getDouble("Boiler Time", 0)) < 0.25){
	    	Chassis.HoldAngle(m_yaw);
    		hasAimed = true;
    		isActive = true;
	   	}
    	
    	double dist = JetsonInterface.getDouble("Boiler Distance", DEFAULTBOILERDISTANCE);
    	ShooterWheelsLeft.setSpeed(WheelSpeedCalculationsLeft.GetSpeed(dist));
    	ShooterWheelsRight.setSpeed(WheelSpeedCalculationsRight.GetSpeed(dist));

    	SmartDashboard.putBoolean("Boiler aim", onTarget());
    	Chassis.DriveStraight(-OI.stickL.getY());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	isActive = false;
    	ShooterWheelsLeft.stop();
    	ShooterWheelsRight.stop();
    	SmartDashboard.putBoolean("Boiler aim", false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

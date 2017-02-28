package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.JetsonInterface;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsLeft;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsRight;
import org.usfirst.frc.team3130.robot.subsystems.Chassis.TurnDirection;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToGear extends Command {

	private double speed;
	private boolean setSpeed;
	
	private Timer timer;
	private boolean hasAimed;
	
    public DriveToGear() {
        requires(Chassis.GetInstance());
        setSpeed = false;
        timer = new Timer();
    }

    public void setParam(double speed)
    {
    	this.speed = -speed;
    	setSpeed = true;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	Chassis.setTurnDir(TurnDirection.kStraight);
    	ShooterWheelsLeft.setPID();
    	ShooterWheelsRight.setPID();
    	Chassis.SetPIDValues(21);
        Chassis.TalonsToCoast(false);
        timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Implemented from https://i.imgur.com/B9THPiA.png
    	double cr = -JetsonInterface.getDouble("Peg Crossrange", 0);	//TODO: get implemented on Jetson end
    	double dr = JetsonInterface.getDouble("Peg Downrange", 1);	//TODO: switch to x offset once updated on Jetson end
    	double yaw = -JetsonInterface.getDouble("Peg Yaw", 0);
    	
    	double alpha = Math.atan2(cr, dr);
    	double beta = Math.atan2(2*cr, dr);
    	
    	double angle = alpha - beta - yaw;	//Extends theta's endpoint to be coincident to alpha's, then goes back alpha degrees
    	if(!hasAimed || timer.get() > Preferences.getInstance().getDouble("Gear Timeout", .5)){
    		if(Math.abs(JetsonInterface.getDouble("Gear Sys Time", 0) - JetsonInterface.getDouble("Gear Time", 0)) < Preferences.getInstance().getDouble("Gear Time", 0.25)){
    			Chassis.HoldAngle(angle * (180/Math.PI));
    		}
    		timer.reset();
    		timer.start();
    		hasAimed = true;
    	}
    	//if(!setSpeed) speed = -OI.stickL.getY();
    	Chassis.DriveStraight(-OI.stickL.getY());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return JetsonInterface.getDouble("Peg Downrange", 12) < 3;
    }

    // Called once after isFinished returns true
    protected void end() {
    	timer.stop();
    	timer.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

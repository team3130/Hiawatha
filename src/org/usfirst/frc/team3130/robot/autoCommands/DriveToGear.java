package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.JetsonInterface;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToGear extends Command {

	private double speed;
	private boolean setSpeed;
	
    public DriveToGear() {
        requires(Chassis.GetInstance());
        setSpeed = false;
    }

    public void setParam(double speed)
    {
    	this.speed = -speed;
    	setSpeed = true;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Implemented from https://i.imgur.com/B9THPiA.png
    	double y = JetsonInterface.getDouble("Peg Crossrange", 0);	//TODO: get implemented on Jetson end
    	double x = JetsonInterface.getDouble("Peg Downrange", 1);	//TODO: switch to x offset once updated on Jetson end
    	double theta = JetsonInterface.getDouble("Peg Yaw", 0);
    	
    	double C = y/(x*x);
    	double alpha = Math.atan(2*C*x);
    	
    	double angle = theta+Math.atan(C*x)-alpha;	//Extends theta's endpoint to be coincident to alpha's, then goes back alpha degrees
    	Chassis.HoldAngle(angle);
    	if(!setSpeed) speed = -OI.stickL.getY();
    	Chassis.DriveStraight(-OI.stickL.getY());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return JetsonInterface.getDouble("Peg Downrange", 12) < 3;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToGear extends Command {

    public DriveToGear() {
        requires(Chassis.GetInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Implemented from https://i.imgur.com/B9THPiA.png
    	double x =0;	//TODO: Get from Jetson Interface
    	double y =0;	//TODO: Get from Jetson Interface
    	double theta =0;//TODO: Get from Jetson Interface
    	
    	double C = y/(x*x);
    	double alpha = Math.atan(2*C*x);
    	
    	double angle = theta+Math.atan(C*x)-alpha;	//Extends theta's endpoint to be coincident to alpha's, then goes back alpha degrees
    	Chassis.HoldAngle(angle);
    	Chassis.DriveStraight(-OI.stickL.getY());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

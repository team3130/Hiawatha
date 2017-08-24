package org.usfirst.frc.team3130.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;

/**
 *
 */
public class DefaultDrive extends Command {

    public DefaultDrive() {
        requires(Chassis.GetInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Chassis.ReleaseAngle();
    	Chassis.TalonsToCoast(false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	int dirMultiplier = -1* Chassis.getReverseMultiplier();
    	double moveSpeed = dirMultiplier * OI.stickL.getY();
    	double turnSpeed = -OI.stickR.getX();
    	double turnThrottle = (-0.5 * OI.stickR.getRawAxis(3)) + 0.5;
    	
    	//Explicitly turning on Quadratic inputs for drivers, as all other systems will use nonQuadratic
    	Chassis.DriveArcade(moveSpeed, turnSpeed * turnThrottle, true);
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

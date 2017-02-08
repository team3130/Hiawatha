package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.GearGrabber;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ActuateGearLift extends Command {

    public ActuateGearLift() {
        requires(GearGrabber.GetInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	GearGrabber.liftActuate(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	GearGrabber.liftActuate(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
